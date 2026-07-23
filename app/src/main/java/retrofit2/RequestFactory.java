package retrofit2;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.android.volley.toolbox.HttpClientStack;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Tag;
import retrofit2.http.Url;

/* JADX INFO: loaded from: classes2.dex */
final class RequestFactory {
    private final HttpUrl baseUrl;

    @Nullable
    private final MediaType contentType;
    private final boolean hasBody;

    @Nullable
    private final Headers headers;
    public final String httpMethod;
    private final boolean isFormEncoded;
    public final boolean isKotlinSuspendFunction;
    private final boolean isMultipart;
    private final Method method;
    private final ParameterHandler<?>[] parameterHandlers;

    @Nullable
    private final String relativeUrl;

    public static final class Builder {

        @Nullable
        public MediaType contentType;
        public boolean gotBody;
        public boolean gotField;
        public boolean gotPart;
        public boolean gotPath;
        public boolean gotQuery;
        public boolean gotQueryMap;
        public boolean gotQueryName;
        public boolean gotUrl;
        public boolean hasBody;

        @Nullable
        public Headers headers;

        @Nullable
        public String httpMethod;
        public boolean isFormEncoded;
        public boolean isKotlinSuspendFunction;
        public boolean isMultipart;
        public final Method method;
        public final Annotation[] methodAnnotations;
        public final Annotation[][] parameterAnnotationsArray;

        @Nullable
        public ParameterHandler<?>[] parameterHandlers;
        public final Type[] parameterTypes;

        @Nullable
        public String relativeUrl;

        @Nullable
        public Set<String> relativeUrlParamNames;
        public final Retrofit retrofit;
        private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
        private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        private static Class<?> boxIfPrimitive(Class<?> cls) {
            if (Boolean.TYPE == cls) {
                return Boolean.class;
            }
            if (Byte.TYPE == cls) {
                return Byte.class;
            }
            if (Character.TYPE == cls) {
                return Character.class;
            }
            if (Double.TYPE == cls) {
                return Double.class;
            }
            if (Float.TYPE == cls) {
                return Float.class;
            }
            if (Integer.TYPE == cls) {
                return Integer.class;
            }
            if (Long.TYPE == cls) {
                return Long.class;
            }
            return Short.TYPE == cls ? Short.class : cls;
        }

        private Headers parseHeaders(String[] strArr) {
            Headers.Builder builder = new Headers.Builder();
            for (String str : strArr) {
                int iIndexOf = str.indexOf(58);
                if (iIndexOf == -1 || iIndexOf == 0 || iIndexOf == str.length() - 1) {
                    throw Utils.methodError(this.method, null, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", str);
                }
                String strSubstring = str.substring(0, iIndexOf);
                String strTrim = str.substring(iIndexOf + 1).trim();
                if ("Content-Type".equalsIgnoreCase(strSubstring)) {
                    try {
                        this.contentType = MediaType.get(strTrim);
                    } catch (IllegalArgumentException e) {
                        throw Utils.methodError(this.method, e, "Malformed content type: %s", strTrim);
                    }
                } else {
                    builder.add(strSubstring, strTrim);
                }
            }
            return builder.build();
        }

        private void parseHttpMethodAndPath(String str, String str2, boolean z) {
            String str3 = this.httpMethod;
            if (str3 != null) {
                throw Utils.methodError(this.method, null, "Only one HTTP method is allowed. Found: %s and %s.", str3, str);
            }
            this.httpMethod = str;
            this.hasBody = z;
            if (str2.isEmpty()) {
                return;
            }
            int iIndexOf = str2.indexOf(63);
            if (iIndexOf != -1 && iIndexOf < str2.length() - 1) {
                String strSubstring = str2.substring(iIndexOf + 1);
                if (PARAM_URL_REGEX.matcher(strSubstring).find()) {
                    throw Utils.methodError(this.method, null, "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", strSubstring);
                }
            }
            this.relativeUrl = str2;
            Matcher matcher = PARAM_URL_REGEX.matcher(str2);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            while (matcher.find()) {
                linkedHashSet.add(matcher.group(1));
            }
            this.relativeUrlParamNames = linkedHashSet;
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof DELETE) {
                parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
                return;
            }
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
                return;
            }
            if (annotation instanceof HEAD) {
                parseHttpMethodAndPath("HEAD", ((HEAD) annotation).value(), false);
                return;
            }
            if (annotation instanceof PATCH) {
                parseHttpMethodAndPath(HttpClientStack.HttpPatch.METHOD_NAME, ((PATCH) annotation).value(), true);
                return;
            }
            if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
                return;
            }
            if (annotation instanceof PUT) {
                parseHttpMethodAndPath("PUT", ((PUT) annotation).value(), true);
                return;
            }
            if (annotation instanceof OPTIONS) {
                parseHttpMethodAndPath("OPTIONS", ((OPTIONS) annotation).value(), false);
                return;
            }
            if (annotation instanceof HTTP) {
                HTTP http = (HTTP) annotation;
                parseHttpMethodAndPath(http.method(), http.path(), http.hasBody());
                return;
            }
            if (annotation instanceof retrofit2.http.Headers) {
                String[] strArrValue = ((retrofit2.http.Headers) annotation).value();
                if (strArrValue.length == 0) {
                    throw Utils.methodError(this.method, null, "@Headers annotation is empty.", new Object[0]);
                }
                this.headers = parseHeaders(strArrValue);
                return;
            }
            if (annotation instanceof Multipart) {
                if (this.isFormEncoded) {
                    throw Utils.methodError(this.method, null, "Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isMultipart = true;
            } else if (annotation instanceof FormUrlEncoded) {
                if (this.isMultipart) {
                    throw Utils.methodError(this.method, null, "Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isFormEncoded = true;
            }
        }

        @Nullable
        private ParameterHandler<?> parseParameter(int i, Type type, @Nullable Annotation[] annotationArr, boolean z) {
            ParameterHandler<?> parameterHandler;
            if (annotationArr != null) {
                parameterHandler = null;
                for (Annotation annotation : annotationArr) {
                    ParameterHandler<?> parameterAnnotation = parseParameterAnnotation(i, type, annotationArr, annotation);
                    if (parameterAnnotation != null) {
                        if (parameterHandler != null) {
                            throw Utils.parameterError(this.method, i, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                        }
                        parameterHandler = parameterAnnotation;
                    }
                }
            } else {
                parameterHandler = null;
            }
            if (parameterHandler != null) {
                return parameterHandler;
            }
            if (z) {
                try {
                    if (Utils.getRawType(type) == Continuation.class) {
                        this.isKotlinSuspendFunction = true;
                        return null;
                    }
                } catch (NoClassDefFoundError unused) {
                }
            }
            throw Utils.parameterError(this.method, i, "No Retrofit annotation found.", new Object[0]);
        }

        @Nullable
        private ParameterHandler<?> parseParameterAnnotation(int i, Type type, Annotation[] annotationArr, Annotation annotation) {
            if (annotation instanceof Url) {
                validateResolvableType(i, type);
                if (this.gotUrl) {
                    throw Utils.parameterError(this.method, i, "Multiple @Url method annotations found.", new Object[0]);
                }
                if (this.gotPath) {
                    throw Utils.parameterError(this.method, i, "@Path parameters may not be used with @Url.", new Object[0]);
                }
                if (this.gotQuery) {
                    throw Utils.parameterError(this.method, i, "A @Url parameter must not come after a @Query.", new Object[0]);
                }
                if (this.gotQueryName) {
                    throw Utils.parameterError(this.method, i, "A @Url parameter must not come after a @QueryName.", new Object[0]);
                }
                if (this.gotQueryMap) {
                    throw Utils.parameterError(this.method, i, "A @Url parameter must not come after a @QueryMap.", new Object[0]);
                }
                if (this.relativeUrl != null) {
                    throw Utils.parameterError(this.method, i, "@Url cannot be used with @%s URL", this.httpMethod);
                }
                this.gotUrl = true;
                if (type == HttpUrl.class || type == String.class || type == URI.class || ((type instanceof Class) && "android.net.Uri".equals(((Class) type).getName()))) {
                    return new ParameterHandler.RelativeUrl(this.method, i);
                }
                throw Utils.parameterError(this.method, i, "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.", new Object[0]);
            }
            if (annotation instanceof Path) {
                validateResolvableType(i, type);
                if (this.gotQuery) {
                    throw Utils.parameterError(this.method, i, "A @Path parameter must not come after a @Query.", new Object[0]);
                }
                if (this.gotQueryName) {
                    throw Utils.parameterError(this.method, i, "A @Path parameter must not come after a @QueryName.", new Object[0]);
                }
                if (this.gotQueryMap) {
                    throw Utils.parameterError(this.method, i, "A @Path parameter must not come after a @QueryMap.", new Object[0]);
                }
                if (this.gotUrl) {
                    throw Utils.parameterError(this.method, i, "@Path parameters may not be used with @Url.", new Object[0]);
                }
                if (this.relativeUrl == null) {
                    throw Utils.parameterError(this.method, i, "@Path can only be used with relative url on @%s", this.httpMethod);
                }
                this.gotPath = true;
                Path path = (Path) annotation;
                String strValue = path.value();
                validatePathName(i, strValue);
                return new ParameterHandler.Path(this.method, i, strValue, this.retrofit.stringConverter(type, annotationArr), path.encoded());
            }
            if (annotation instanceof Query) {
                validateResolvableType(i, type);
                Query query = (Query) annotation;
                String strValue2 = query.value();
                boolean zEncoded = query.encoded();
                Class<?> rawType = Utils.getRawType(type);
                this.gotQuery = true;
                if (!Iterable.class.isAssignableFrom(rawType)) {
                    if (!rawType.isArray()) {
                        return new ParameterHandler.Query(strValue2, this.retrofit.stringConverter(type, annotationArr), zEncoded);
                    }
                    final ParameterHandler.Query query2 = new ParameterHandler.Query(strValue2, this.retrofit.stringConverter(boxIfPrimitive(rawType.getComponentType()), annotationArr), zEncoded);
                    return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                            if (obj == null) {
                                return;
                            }
                            int length = Array.getLength(obj);
                            for (int i2 = 0; i2 < length; i2++) {
                                ParameterHandler.this.apply(requestBuilder, Array.get(obj, i2));
                            }
                        }
                    };
                }
                if (type instanceof ParameterizedType) {
                    final ParameterHandler.Query query3 = new ParameterHandler.Query(strValue2, this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr), zEncoded);
                    return new ParameterHandler<Iterable<Object>>() { // from class: retrofit2.ParameterHandler.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Iterable<Object> iterable) throws IOException {
                            Iterable<Object> iterable2 = iterable;
                            if (iterable2 == null) {
                                return;
                            }
                            Iterator<Object> it = iterable2.iterator();
                            while (it.hasNext()) {
                                ParameterHandler.this.apply(requestBuilder, it.next());
                            }
                        }
                    };
                }
                throw Utils.parameterError(this.method, i, rawType.getSimpleName() + " must include generic type (e.g., " + rawType.getSimpleName() + "<String>)", new Object[0]);
            }
            if (annotation instanceof QueryName) {
                validateResolvableType(i, type);
                boolean zEncoded2 = ((QueryName) annotation).encoded();
                Class<?> rawType2 = Utils.getRawType(type);
                this.gotQueryName = true;
                if (!Iterable.class.isAssignableFrom(rawType2)) {
                    if (!rawType2.isArray()) {
                        return new ParameterHandler.QueryName(this.retrofit.stringConverter(type, annotationArr), zEncoded2);
                    }
                    final ParameterHandler.QueryName queryName = new ParameterHandler.QueryName(this.retrofit.stringConverter(boxIfPrimitive(rawType2.getComponentType()), annotationArr), zEncoded2);
                    return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                            if (obj == null) {
                                return;
                            }
                            int length = Array.getLength(obj);
                            for (int i2 = 0; i2 < length; i2++) {
                                ParameterHandler.this.apply(requestBuilder, Array.get(obj, i2));
                            }
                        }
                    };
                }
                if (type instanceof ParameterizedType) {
                    final ParameterHandler.QueryName queryName2 = new ParameterHandler.QueryName(this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr), zEncoded2);
                    return new ParameterHandler<Iterable<Object>>() { // from class: retrofit2.ParameterHandler.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Iterable<Object> iterable) throws IOException {
                            Iterable<Object> iterable2 = iterable;
                            if (iterable2 == null) {
                                return;
                            }
                            Iterator<Object> it = iterable2.iterator();
                            while (it.hasNext()) {
                                ParameterHandler.this.apply(requestBuilder, it.next());
                            }
                        }
                    };
                }
                throw Utils.parameterError(this.method, i, rawType2.getSimpleName() + " must include generic type (e.g., " + rawType2.getSimpleName() + "<String>)", new Object[0]);
            }
            if (annotation instanceof QueryMap) {
                validateResolvableType(i, type);
                Class<?> rawType3 = Utils.getRawType(type);
                this.gotQueryMap = true;
                if (!Map.class.isAssignableFrom(rawType3)) {
                    throw Utils.parameterError(this.method, i, "@QueryMap parameter type must be Map.", new Object[0]);
                }
                Type supertype = Utils.getSupertype(type, rawType3, Map.class);
                if (!(supertype instanceof ParameterizedType)) {
                    throw Utils.parameterError(this.method, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                ParameterizedType parameterizedType = (ParameterizedType) supertype;
                Type parameterUpperBound = Utils.getParameterUpperBound(0, parameterizedType);
                if (String.class == parameterUpperBound) {
                    return new ParameterHandler.QueryMap(this.method, i, this.retrofit.stringConverter(Utils.getParameterUpperBound(1, parameterizedType), annotationArr), ((QueryMap) annotation).encoded());
                }
                throw Utils.parameterError(this.method, i, "@QueryMap keys must be of type String: " + parameterUpperBound, new Object[0]);
            }
            if (annotation instanceof Header) {
                validateResolvableType(i, type);
                String strValue3 = ((Header) annotation).value();
                Class<?> rawType4 = Utils.getRawType(type);
                if (!Iterable.class.isAssignableFrom(rawType4)) {
                    if (!rawType4.isArray()) {
                        return new ParameterHandler.Header(strValue3, this.retrofit.stringConverter(type, annotationArr));
                    }
                    final ParameterHandler.Header header = new ParameterHandler.Header(strValue3, this.retrofit.stringConverter(boxIfPrimitive(rawType4.getComponentType()), annotationArr));
                    return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                            if (obj == null) {
                                return;
                            }
                            int length = Array.getLength(obj);
                            for (int i2 = 0; i2 < length; i2++) {
                                ParameterHandler.this.apply(requestBuilder, Array.get(obj, i2));
                            }
                        }
                    };
                }
                if (type instanceof ParameterizedType) {
                    final ParameterHandler.Header header2 = new ParameterHandler.Header(strValue3, this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr));
                    return new ParameterHandler<Iterable<Object>>() { // from class: retrofit2.ParameterHandler.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Iterable<Object> iterable) throws IOException {
                            Iterable<Object> iterable2 = iterable;
                            if (iterable2 == null) {
                                return;
                            }
                            Iterator<Object> it = iterable2.iterator();
                            while (it.hasNext()) {
                                ParameterHandler.this.apply(requestBuilder, it.next());
                            }
                        }
                    };
                }
                throw Utils.parameterError(this.method, i, rawType4.getSimpleName() + " must include generic type (e.g., " + rawType4.getSimpleName() + "<String>)", new Object[0]);
            }
            if (annotation instanceof HeaderMap) {
                if (type == Headers.class) {
                    return new ParameterHandler.Headers(this.method, i);
                }
                validateResolvableType(i, type);
                Class<?> rawType5 = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom(rawType5)) {
                    throw Utils.parameterError(this.method, i, "@HeaderMap parameter type must be Map.", new Object[0]);
                }
                Type supertype2 = Utils.getSupertype(type, rawType5, Map.class);
                if (!(supertype2 instanceof ParameterizedType)) {
                    throw Utils.parameterError(this.method, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                ParameterizedType parameterizedType2 = (ParameterizedType) supertype2;
                Type parameterUpperBound2 = Utils.getParameterUpperBound(0, parameterizedType2);
                if (String.class == parameterUpperBound2) {
                    return new ParameterHandler.HeaderMap(this.method, i, this.retrofit.stringConverter(Utils.getParameterUpperBound(1, parameterizedType2), annotationArr));
                }
                throw Utils.parameterError(this.method, i, "@HeaderMap keys must be of type String: " + parameterUpperBound2, new Object[0]);
            }
            if (annotation instanceof Field) {
                validateResolvableType(i, type);
                if (!this.isFormEncoded) {
                    throw Utils.parameterError(this.method, i, "@Field parameters can only be used with form encoding.", new Object[0]);
                }
                Field field = (Field) annotation;
                String strValue4 = field.value();
                boolean zEncoded3 = field.encoded();
                this.gotField = true;
                Class<?> rawType6 = Utils.getRawType(type);
                if (!Iterable.class.isAssignableFrom(rawType6)) {
                    if (!rawType6.isArray()) {
                        return new ParameterHandler.Field(strValue4, this.retrofit.stringConverter(type, annotationArr), zEncoded3);
                    }
                    final ParameterHandler.Field field2 = new ParameterHandler.Field(strValue4, this.retrofit.stringConverter(boxIfPrimitive(rawType6.getComponentType()), annotationArr), zEncoded3);
                    return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                            if (obj == null) {
                                return;
                            }
                            int length = Array.getLength(obj);
                            for (int i2 = 0; i2 < length; i2++) {
                                ParameterHandler.this.apply(requestBuilder, Array.get(obj, i2));
                            }
                        }
                    };
                }
                if (type instanceof ParameterizedType) {
                    final ParameterHandler.Field field3 = new ParameterHandler.Field(strValue4, this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr), zEncoded3);
                    return new ParameterHandler<Iterable<Object>>() { // from class: retrofit2.ParameterHandler.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Iterable<Object> iterable) throws IOException {
                            Iterable<Object> iterable2 = iterable;
                            if (iterable2 == null) {
                                return;
                            }
                            Iterator<Object> it = iterable2.iterator();
                            while (it.hasNext()) {
                                ParameterHandler.this.apply(requestBuilder, it.next());
                            }
                        }
                    };
                }
                throw Utils.parameterError(this.method, i, rawType6.getSimpleName() + " must include generic type (e.g., " + rawType6.getSimpleName() + "<String>)", new Object[0]);
            }
            if (annotation instanceof FieldMap) {
                validateResolvableType(i, type);
                if (!this.isFormEncoded) {
                    throw Utils.parameterError(this.method, i, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
                }
                Class<?> rawType7 = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom(rawType7)) {
                    throw Utils.parameterError(this.method, i, "@FieldMap parameter type must be Map.", new Object[0]);
                }
                Type supertype3 = Utils.getSupertype(type, rawType7, Map.class);
                if (!(supertype3 instanceof ParameterizedType)) {
                    throw Utils.parameterError(this.method, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                ParameterizedType parameterizedType3 = (ParameterizedType) supertype3;
                Type parameterUpperBound3 = Utils.getParameterUpperBound(0, parameterizedType3);
                if (String.class == parameterUpperBound3) {
                    Converter converterStringConverter = this.retrofit.stringConverter(Utils.getParameterUpperBound(1, parameterizedType3), annotationArr);
                    this.gotField = true;
                    return new ParameterHandler.FieldMap(this.method, i, converterStringConverter, ((FieldMap) annotation).encoded());
                }
                throw Utils.parameterError(this.method, i, "@FieldMap keys must be of type String: " + parameterUpperBound3, new Object[0]);
            }
            if (!(annotation instanceof Part)) {
                if (annotation instanceof PartMap) {
                    validateResolvableType(i, type);
                    if (!this.isMultipart) {
                        throw Utils.parameterError(this.method, i, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
                    }
                    this.gotPart = true;
                    Class<?> rawType8 = Utils.getRawType(type);
                    if (!Map.class.isAssignableFrom(rawType8)) {
                        throw Utils.parameterError(this.method, i, "@PartMap parameter type must be Map.", new Object[0]);
                    }
                    Type supertype4 = Utils.getSupertype(type, rawType8, Map.class);
                    if (!(supertype4 instanceof ParameterizedType)) {
                        throw Utils.parameterError(this.method, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    ParameterizedType parameterizedType4 = (ParameterizedType) supertype4;
                    Type parameterUpperBound4 = Utils.getParameterUpperBound(0, parameterizedType4);
                    if (String.class == parameterUpperBound4) {
                        Type parameterUpperBound5 = Utils.getParameterUpperBound(1, parameterizedType4);
                        if (MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(parameterUpperBound5))) {
                            throw Utils.parameterError(this.method, i, "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.", new Object[0]);
                        }
                        return new ParameterHandler.PartMap(this.method, i, this.retrofit.requestBodyConverter(parameterUpperBound5, annotationArr, this.methodAnnotations), ((PartMap) annotation).encoding());
                    }
                    throw Utils.parameterError(this.method, i, "@PartMap keys must be of type String: " + parameterUpperBound4, new Object[0]);
                }
                if (annotation instanceof Body) {
                    validateResolvableType(i, type);
                    if (this.isFormEncoded || this.isMultipart) {
                        throw Utils.parameterError(this.method, i, "@Body parameters cannot be used with form or multi-part encoding.", new Object[0]);
                    }
                    if (this.gotBody) {
                        throw Utils.parameterError(this.method, i, "Multiple @Body method annotations found.", new Object[0]);
                    }
                    try {
                        Converter converterRequestBodyConverter = this.retrofit.requestBodyConverter(type, annotationArr, this.methodAnnotations);
                        this.gotBody = true;
                        return new ParameterHandler.Body(this.method, i, converterRequestBodyConverter);
                    } catch (RuntimeException e) {
                        throw Utils.parameterError(this.method, e, i, "Unable to create @Body converter for %s", type);
                    }
                }
                if (!(annotation instanceof Tag)) {
                    return null;
                }
                validateResolvableType(i, type);
                Class<?> rawType9 = Utils.getRawType(type);
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    ParameterHandler<?> parameterHandler = this.parameterHandlers[i2];
                    if ((parameterHandler instanceof ParameterHandler.Tag) && ((ParameterHandler.Tag) parameterHandler).cls.equals(rawType9)) {
                        Method method = this.method;
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("@Tag type ");
                        sbM.append(rawType9.getName());
                        sbM.append(" is duplicate of parameter #");
                        sbM.append(i2 + 1);
                        sbM.append(" and would always overwrite its value.");
                        throw Utils.parameterError(method, i, sbM.toString(), new Object[0]);
                    }
                }
                return new ParameterHandler.Tag(rawType9);
            }
            validateResolvableType(i, type);
            if (!this.isMultipart) {
                throw Utils.parameterError(this.method, i, "@Part parameters can only be used with multipart encoding.", new Object[0]);
            }
            Part part = (Part) annotation;
            this.gotPart = true;
            String strValue5 = part.value();
            Class<?> rawType10 = Utils.getRawType(type);
            if (strValue5.isEmpty()) {
                if (!Iterable.class.isAssignableFrom(rawType10)) {
                    if (!rawType10.isArray()) {
                        if (MultipartBody.Part.class.isAssignableFrom(rawType10)) {
                            return ParameterHandler.RawPart.INSTANCE;
                        }
                        throw Utils.parameterError(this.method, i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                    if (!MultipartBody.Part.class.isAssignableFrom(rawType10.getComponentType())) {
                        throw Utils.parameterError(this.method, i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                    final ParameterHandler.RawPart rawPart = ParameterHandler.RawPart.INSTANCE;
                    Objects.requireNonNull(rawPart);
                    return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                            if (obj == null) {
                                return;
                            }
                            int length = Array.getLength(obj);
                            for (int i3 = 0; i3 < length; i3++) {
                                ParameterHandler.this.apply(requestBuilder, Array.get(obj, i3));
                            }
                        }
                    };
                }
                if (type instanceof ParameterizedType) {
                    if (!MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(Utils.getParameterUpperBound(0, (ParameterizedType) type)))) {
                        throw Utils.parameterError(this.method, i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                    final ParameterHandler.RawPart rawPart2 = ParameterHandler.RawPart.INSTANCE;
                    Objects.requireNonNull(rawPart2);
                    return new ParameterHandler<Iterable<Object>>() { // from class: retrofit2.ParameterHandler.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // retrofit2.ParameterHandler
                        public final void apply(RequestBuilder requestBuilder, @Nullable Iterable<Object> iterable) throws IOException {
                            Iterable<Object> iterable2 = iterable;
                            if (iterable2 == null) {
                                return;
                            }
                            Iterator<Object> it = iterable2.iterator();
                            while (it.hasNext()) {
                                ParameterHandler.this.apply(requestBuilder, it.next());
                            }
                        }
                    };
                }
                throw Utils.parameterError(this.method, i, rawType10.getSimpleName() + " must include generic type (e.g., " + rawType10.getSimpleName() + "<String>)", new Object[0]);
            }
            Headers headersOf = Headers.of(HttpHeaders.CONTENT_DISPOSITION, Insets$$ExternalSyntheticOutline0.m("form-data; name=\"", strValue5, "\""), "Content-Transfer-Encoding", part.encoding());
            if (!Iterable.class.isAssignableFrom(rawType10)) {
                if (!rawType10.isArray()) {
                    if (MultipartBody.Part.class.isAssignableFrom(rawType10)) {
                        throw Utils.parameterError(this.method, i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                    }
                    return new ParameterHandler.Part(this.method, i, headersOf, this.retrofit.requestBodyConverter(type, annotationArr, this.methodAnnotations));
                }
                Class<?> clsBoxIfPrimitive = boxIfPrimitive(rawType10.getComponentType());
                if (MultipartBody.Part.class.isAssignableFrom(clsBoxIfPrimitive)) {
                    throw Utils.parameterError(this.method, i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                }
                final ParameterHandler.Part part2 = new ParameterHandler.Part(this.method, i, headersOf, this.retrofit.requestBodyConverter(clsBoxIfPrimitive, annotationArr, this.methodAnnotations));
                return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // retrofit2.ParameterHandler
                    public final void apply(RequestBuilder requestBuilder, @Nullable Object obj) throws IOException {
                        if (obj == null) {
                            return;
                        }
                        int length = Array.getLength(obj);
                        for (int i3 = 0; i3 < length; i3++) {
                            ParameterHandler.this.apply(requestBuilder, Array.get(obj, i3));
                        }
                    }
                };
            }
            if (type instanceof ParameterizedType) {
                Type parameterUpperBound6 = Utils.getParameterUpperBound(0, (ParameterizedType) type);
                if (MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(parameterUpperBound6))) {
                    throw Utils.parameterError(this.method, i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                }
                final ParameterHandler.Part part3 = new ParameterHandler.Part(this.method, i, headersOf, this.retrofit.requestBodyConverter(parameterUpperBound6, annotationArr, this.methodAnnotations));
                return new ParameterHandler<Iterable<Object>>() { // from class: retrofit2.ParameterHandler.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // retrofit2.ParameterHandler
                    public final void apply(RequestBuilder requestBuilder, @Nullable Iterable<Object> iterable) throws IOException {
                        Iterable<Object> iterable2 = iterable;
                        if (iterable2 == null) {
                            return;
                        }
                        Iterator<Object> it = iterable2.iterator();
                        while (it.hasNext()) {
                            ParameterHandler.this.apply(requestBuilder, it.next());
                        }
                    }
                };
            }
            throw Utils.parameterError(this.method, i, rawType10.getSimpleName() + " must include generic type (e.g., " + rawType10.getSimpleName() + "<String>)", new Object[0]);
        }

        private void validatePathName(int i, String str) {
            if (!PARAM_NAME_REGEX.matcher(str).matches()) {
                throw Utils.parameterError(this.method, i, "@Path parameter name must match %s. Found: %s", PARAM_URL_REGEX.pattern(), str);
            }
            if (!this.relativeUrlParamNames.contains(str)) {
                throw Utils.parameterError(this.method, i, "URL \"%s\" does not contain \"{%s}\".", this.relativeUrl, str);
            }
        }

        private void validateResolvableType(int i, Type type) {
            if (Utils.hasUnresolvableType(type)) {
                throw Utils.parameterError(this.method, i, "Parameter type must not include a type variable or wildcard: %s", type);
            }
        }

        public final RequestFactory build() {
            for (Annotation annotation : this.methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
            if (this.httpMethod == null) {
                throw Utils.methodError(this.method, null, "HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
            if (!this.hasBody) {
                if (this.isMultipart) {
                    throw Utils.methodError(this.method, null, "Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                }
                if (this.isFormEncoded) {
                    throw Utils.methodError(this.method, null, "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                }
            }
            int length = this.parameterAnnotationsArray.length;
            this.parameterHandlers = new ParameterHandler[length];
            int i = length - 1;
            int i2 = 0;
            while (true) {
                boolean z = true;
                if (i2 >= length) {
                    break;
                }
                ParameterHandler<?>[] parameterHandlerArr = this.parameterHandlers;
                Type type = this.parameterTypes[i2];
                Annotation[] annotationArr = this.parameterAnnotationsArray[i2];
                if (i2 != i) {
                    z = false;
                }
                parameterHandlerArr[i2] = parseParameter(i2, type, annotationArr, z);
                i2++;
            }
            if (this.relativeUrl == null && !this.gotUrl) {
                throw Utils.methodError(this.method, null, "Missing either @%s URL or @Url parameter.", this.httpMethod);
            }
            boolean z2 = this.isFormEncoded;
            if (!z2 && !this.isMultipart && !this.hasBody && this.gotBody) {
                throw Utils.methodError(this.method, null, "Non-body HTTP method cannot contain @Body.", new Object[0]);
            }
            if (z2 && !this.gotField) {
                throw Utils.methodError(this.method, null, "Form-encoded method must contain at least one @Field.", new Object[0]);
            }
            if (!this.isMultipart || this.gotPart) {
                return new RequestFactory(this);
            }
            throw Utils.methodError(this.method, null, "Multipart method must contain at least one @Part.", new Object[0]);
        }
    }

    public RequestFactory(Builder builder) {
        this.method = builder.method;
        this.baseUrl = builder.retrofit.baseUrl;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.headers = builder.headers;
        this.contentType = builder.contentType;
        this.hasBody = builder.hasBody;
        this.isFormEncoded = builder.isFormEncoded;
        this.isMultipart = builder.isMultipart;
        this.parameterHandlers = builder.parameterHandlers;
        this.isKotlinSuspendFunction = builder.isKotlinSuspendFunction;
    }

    public final Request create(Object[] objArr) throws IOException {
        ParameterHandler<?>[] parameterHandlerArr = this.parameterHandlers;
        int length = objArr.length;
        if (length != parameterHandlerArr.length) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m24m("Argument count (", length, ") doesn't match expected count ("), parameterHandlerArr.length, ")"));
        }
        RequestBuilder requestBuilder = new RequestBuilder(this.httpMethod, this.baseUrl, this.relativeUrl, this.headers, this.contentType, this.hasBody, this.isFormEncoded, this.isMultipart);
        if (this.isKotlinSuspendFunction) {
            length--;
        }
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            arrayList.add(objArr[i]);
            parameterHandlerArr[i].apply(requestBuilder, objArr[i]);
        }
        return requestBuilder.get().tag(Invocation.class, new Invocation(this.method, arrayList)).build();
    }
}
