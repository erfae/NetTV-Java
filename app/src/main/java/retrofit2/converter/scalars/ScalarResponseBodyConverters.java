package retrofit2.converter.scalars;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: classes2.dex */
final class ScalarResponseBodyConverters {

    public static final class BooleanResponseBodyConverter implements Converter<ResponseBody, Boolean> {
        public static final BooleanResponseBodyConverter INSTANCE = new BooleanResponseBodyConverter();

        @Override // retrofit2.Converter
        public Boolean convert(ResponseBody responseBody) throws IOException {
            return Boolean.valueOf(responseBody.string());
        }
    }

    public static final class ByteResponseBodyConverter implements Converter<ResponseBody, Byte> {
        public static final ByteResponseBodyConverter INSTANCE = new ByteResponseBodyConverter();

        @Override // retrofit2.Converter
        public Byte convert(ResponseBody responseBody) throws IOException {
            return Byte.valueOf(responseBody.string());
        }
    }

    public static final class CharacterResponseBodyConverter implements Converter<ResponseBody, Character> {
        public static final CharacterResponseBodyConverter INSTANCE = new CharacterResponseBodyConverter();

        @Override // retrofit2.Converter
        public Character convert(ResponseBody responseBody) throws IOException {
            String strString = responseBody.string();
            if (strString.length() == 1) {
                return Character.valueOf(strString.charAt(0));
            }
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Expected body of length 1 for Character conversion but was ");
            sbM.append(strString.length());
            throw new IOException(sbM.toString());
        }
    }

    public static final class DoubleResponseBodyConverter implements Converter<ResponseBody, Double> {
        public static final DoubleResponseBodyConverter INSTANCE = new DoubleResponseBodyConverter();

        @Override // retrofit2.Converter
        public Double convert(ResponseBody responseBody) throws IOException {
            return Double.valueOf(responseBody.string());
        }
    }

    public static final class FloatResponseBodyConverter implements Converter<ResponseBody, Float> {
        public static final FloatResponseBodyConverter INSTANCE = new FloatResponseBodyConverter();

        @Override // retrofit2.Converter
        public Float convert(ResponseBody responseBody) throws IOException {
            return Float.valueOf(responseBody.string());
        }
    }

    public static final class IntegerResponseBodyConverter implements Converter<ResponseBody, Integer> {
        public static final IntegerResponseBodyConverter INSTANCE = new IntegerResponseBodyConverter();

        @Override // retrofit2.Converter
        public Integer convert(ResponseBody responseBody) throws IOException {
            return Integer.valueOf(responseBody.string());
        }
    }

    public static final class LongResponseBodyConverter implements Converter<ResponseBody, Long> {
        public static final LongResponseBodyConverter INSTANCE = new LongResponseBodyConverter();

        @Override // retrofit2.Converter
        public Long convert(ResponseBody responseBody) throws IOException {
            return Long.valueOf(responseBody.string());
        }
    }

    public static final class ShortResponseBodyConverter implements Converter<ResponseBody, Short> {
        public static final ShortResponseBodyConverter INSTANCE = new ShortResponseBodyConverter();

        @Override // retrofit2.Converter
        public Short convert(ResponseBody responseBody) throws IOException {
            return Short.valueOf(responseBody.string());
        }
    }

    public static final class StringResponseBodyConverter implements Converter<ResponseBody, String> {
        public static final StringResponseBodyConverter INSTANCE = new StringResponseBodyConverter();

        @Override // retrofit2.Converter
        public String convert(ResponseBody responseBody) throws IOException {
            return responseBody.string();
        }
    }

    private ScalarResponseBodyConverters() {
    }
}
