package retrofit2;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes2.dex */
abstract class ParameterHandler<T> {

    public static final class Body<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final Method method;
        private final int p;

        public Body(Method method, int i, Converter<T, RequestBody> converter) {
            this.method = method;
            this.p = i;
            this.converter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) {
            if (t == null) {
                throw Utils.parameterError(this.method, this.p, "Body parameter value must not be null.", new Object[0]);
            }
            try {
                requestBuilder.setBody(this.converter.convert(t));
            } catch (IOException e) {
                throw Utils.parameterError(this.method, e, this.p, "Unable to convert " + t + " to RequestBody", new Object[0]);
            }
        }
    }

    public static final class Field<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        public Field(String str, Converter<T, String> converter, boolean z) {
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
            this.encoded = z;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException {
            String strConvert;
            if (t == null || (strConvert = this.valueConverter.convert(t)) == null) {
                return;
            }
            requestBuilder.addFormField(this.name, strConvert, this.encoded);
        }
    }

    public static final class FieldMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Method method;
        private final int p;
        private final Converter<T, String> valueConverter;

        public FieldMap(Method method, int i, Converter<T, String> converter, boolean z) {
            this.method = method;
            this.p = i;
            this.valueConverter = converter;
            this.encoded = z;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
            Map map = (Map) obj;
            if (map == null) {
                throw Utils.parameterError(this.method, this.p, "Field map was null.", new Object[0]);
            }
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null) {
                    throw Utils.parameterError(this.method, this.p, "Field map contained null key.", new Object[0]);
                }
                Object value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.p, Insets$$ExternalSyntheticOutline0.m("Field map contained null value for key '", str, "'."), new Object[0]);
                }
                String strConvert = this.valueConverter.convert((T) value);
                if (strConvert == null) {
                    throw Utils.parameterError(this.method, this.p, "Field map value '" + value + "' converted to null by " + this.valueConverter.getClass().getName() + " for key '" + str + "'.", new Object[0]);
                }
                requestBuilder.addFormField(str, strConvert, this.encoded);
            }
        }
    }

    public static final class Header<T> extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        public Header(String str, Converter<T, String> converter) {
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException {
            String strConvert;
            if (t == null || (strConvert = this.valueConverter.convert(t)) == null) {
                return;
            }
            requestBuilder.addHeader(this.name, strConvert);
        }
    }

    public static final class HeaderMap<T> extends ParameterHandler<Map<String, T>> {
        private final Method method;
        private final int p;
        private final Converter<T, String> valueConverter;

        public HeaderMap(Method method, int i, Converter<T, String> converter) {
            this.method = method;
            this.p = i;
            this.valueConverter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
            Map map = (Map) obj;
            if (map == null) {
                throw Utils.parameterError(this.method, this.p, "Header map was null.", new Object[0]);
            }
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null) {
                    throw Utils.parameterError(this.method, this.p, "Header map contained null key.", new Object[0]);
                }
                Object value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.p, Insets$$ExternalSyntheticOutline0.m("Header map contained null value for key '", str, "'."), new Object[0]);
                }
                requestBuilder.addHeader(str, this.valueConverter.convert((T) value));
            }
        }
    }

    public static final class Headers extends ParameterHandler<okhttp3.Headers> {
        private final Method method;
        private final int p;

        public Headers(Method method, int i) {
            this.method = method;
            this.p = i;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable okhttp3.Headers headers) throws IOException {
            okhttp3.Headers headers2 = headers;
            if (headers2 == null) {
                throw Utils.parameterError(this.method, this.p, "Headers parameter must not be null.", new Object[0]);
            }
            requestBuilder.addHeaders(headers2);
        }
    }

    public static final class Part<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final okhttp3.Headers headers;
        private final Method method;
        private final int p;

        public Part(Method method, int i, okhttp3.Headers headers, Converter<T, RequestBody> converter) {
            this.method = method;
            this.p = i;
            this.headers = headers;
            this.converter = converter;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) {
            if (t == null) {
                return;
            }
            try {
                requestBuilder.addPart(this.headers, this.converter.convert(t));
            } catch (IOException e) {
                throw Utils.parameterError(this.method, this.p, "Unable to convert " + t + " to RequestBody", e);
            }
        }
    }

    public static final class PartMap<T> extends ParameterHandler<Map<String, T>> {
        private final Method method;
        private final int p;
        private final String transferEncoding;
        private final Converter<T, RequestBody> valueConverter;

        public PartMap(Method method, int i, Converter<T, RequestBody> converter, String str) {
            this.method = method;
            this.p = i;
            this.valueConverter = converter;
            this.transferEncoding = str;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
            Map map = (Map) obj;
            if (map == null) {
                throw Utils.parameterError(this.method, this.p, "Part map was null.", new Object[0]);
            }
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null) {
                    throw Utils.parameterError(this.method, this.p, "Part map contained null key.", new Object[0]);
                }
                Object value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.p, Insets$$ExternalSyntheticOutline0.m("Part map contained null value for key '", str, "'."), new Object[0]);
                }
                requestBuilder.addPart(okhttp3.Headers.of(HttpHeaders.CONTENT_DISPOSITION, Insets$$ExternalSyntheticOutline0.m("form-data; name=\"", str, "\""), "Content-Transfer-Encoding", this.transferEncoding), this.valueConverter.convert((T) value));
            }
        }
    }

    public static final class Path<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Method method;
        private final String name;
        private final int p;
        private final Converter<T, String> valueConverter;

        public Path(Method method, int i, String str, Converter<T, String> converter, boolean z) {
            this.method = method;
            this.p = i;
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
            this.encoded = z;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException {
            if (t == null) {
                throw Utils.parameterError(this.method, this.p, Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("Path parameter \""), this.name, "\" value must not be null."), new Object[0]);
            }
            requestBuilder.addPathParam(this.name, this.valueConverter.convert(t), this.encoded);
        }
    }

    public static final class Query<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        public Query(String str, Converter<T, String> converter, boolean z) {
            Objects.requireNonNull(str, "name == null");
            this.name = str;
            this.valueConverter = converter;
            this.encoded = z;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException {
            String strConvert;
            if (t == null || (strConvert = this.valueConverter.convert(t)) == null) {
                return;
            }
            requestBuilder.addQueryParam(this.name, strConvert, this.encoded);
        }
    }

    public static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Method method;
        private final int p;
        private final Converter<T, String> valueConverter;

        public QueryMap(Method method, int i, Converter<T, String> converter, boolean z) {
            this.method = method;
            this.p = i;
            this.valueConverter = converter;
            this.encoded = z;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
            Map map = (Map) obj;
            if (map == null) {
                throw Utils.parameterError(this.method, this.p, "Query map was null", new Object[0]);
            }
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null) {
                    throw Utils.parameterError(this.method, this.p, "Query map contained null key.", new Object[0]);
                }
                Object value = entry.getValue();
                if (value == null) {
                    throw Utils.parameterError(this.method, this.p, Insets$$ExternalSyntheticOutline0.m("Query map contained null value for key '", str, "'."), new Object[0]);
                }
                String strConvert = this.valueConverter.convert((T) value);
                if (strConvert == null) {
                    throw Utils.parameterError(this.method, this.p, "Query map value '" + value + "' converted to null by " + this.valueConverter.getClass().getName() + " for key '" + str + "'.", new Object[0]);
                }
                requestBuilder.addQueryParam(str, strConvert, this.encoded);
            }
        }
    }

    public static final class QueryName<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Converter<T, String> nameConverter;

        public QueryName(Converter<T, String> converter, boolean z) {
            this.nameConverter = converter;
            this.encoded = z;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException {
            if (t == null) {
                return;
            }
            requestBuilder.addQueryParam(this.nameConverter.convert(t), null, this.encoded);
        }
    }

    public static final class RawPart extends ParameterHandler<MultipartBody.Part> {
        public static final RawPart INSTANCE = new RawPart();

        private RawPart() {
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable MultipartBody.Part part) throws IOException {
            MultipartBody.Part part2 = part;
            if (part2 != null) {
                requestBuilder.addPart(part2);
            }
        }
    }

    public static final class RelativeUrl extends ParameterHandler<Object> {
        private final Method method;
        private final int p;

        public RelativeUrl(Method method, int i) {
            this.method = method;
            this.p = i;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) {
            if (obj == null) {
                throw Utils.parameterError(this.method, this.p, "@Url parameter is null.", new Object[0]);
            }
            requestBuilder.setRelativeUrl(obj);
        }
    }

    public static final class Tag<T> extends ParameterHandler<T> {
        public final Class<T> cls;

        public Tag(Class<T> cls) {
            this.cls = cls;
        }

        @Override // retrofit2.ParameterHandler
        public final void apply(RequestBuilder requestBuilder, @Nullable T t) {
            requestBuilder.addTag(this.cls, t);
        }
    }

    public abstract void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException;
}
