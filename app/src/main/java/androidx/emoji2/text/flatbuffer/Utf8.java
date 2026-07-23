package androidx.emoji2.text.flatbuffer;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class Utf8 {
    private static Utf8 DEFAULT;

    public static class DecodeUtil {
        public static void handleFourBytes(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws IllegalArgumentException {
            if (!isNotTrailingByte(b2)) {
                if ((((b2 + 112) + (b << Ascii.FS)) >> 30) == 0 && !isNotTrailingByte(b3) && !isNotTrailingByte(b4)) {
                    int iTrailingByteValue = ((b & 7) << 18) | (trailingByteValue(b2) << 12) | (trailingByteValue(b3) << 6) | trailingByteValue(b4);
                    cArr[i] = highSurrogate(iTrailingByteValue);
                    cArr[i + 1] = lowSurrogate(iTrailingByteValue);
                    return;
                }
            }
            throw new IllegalArgumentException("Invalid UTF-8");
        }

        public static void handleThreeBytes(byte b, byte b2, byte b3, char[] cArr, int i) throws IllegalArgumentException {
            if (isNotTrailingByte(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || isNotTrailingByte(b3)))) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            cArr[i] = (char) (((b & 15) << 12) | (trailingByteValue(b2) << 6) | trailingByteValue(b3));
        }

        public static void handleTwoBytes(byte b, byte b2, char[] cArr, int i) throws IllegalArgumentException {
            if (b < -62) {
                throw new IllegalArgumentException("Invalid UTF-8: Illegal leading byte in 2 bytes utf");
            }
            if (isNotTrailingByte(b2)) {
                throw new IllegalArgumentException("Invalid UTF-8: Illegal trailing byte in 2 bytes utf");
            }
            cArr[i] = (char) (((b & Ascii.US) << 6) | trailingByteValue(b2));
        }

        private static char highSurrogate(int i) {
            return (char) ((i >>> 10) + okio.Utf8.HIGH_SURROGATE_HEADER);
        }

        private static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }

        private static char lowSurrogate(int i) {
            return (char) ((i & AnalyticsListener.EVENT_DRM_KEYS_LOADED) + okio.Utf8.LOG_SURROGATE_HEADER);
        }

        private static int trailingByteValue(byte b) {
            return b & okio.Utf8.REPLACEMENT_BYTE;
        }
    }

    public static class UnpairedSurrogateException extends IllegalArgumentException {
    }

    public static Utf8 getDefault() {
        if (DEFAULT == null) {
            DEFAULT = new Utf8Safe();
        }
        return DEFAULT;
    }

    public static void setDefault(Utf8 utf8) {
        DEFAULT = utf8;
    }

    public abstract String decodeUtf8(ByteBuffer byteBuffer, int i, int i2);

    public abstract void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer);

    public abstract int encodedLength(CharSequence charSequence);
}
