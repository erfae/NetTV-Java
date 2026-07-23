package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okio.Base64;
import okio.Buffer;
import okio.ByteString;
import okio.Platform;
import okio.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ByteString.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0002\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0080\b\u001a\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\r\u0010\u0011\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\u0015\u0010\u0012\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\u0080\b\u001a\u000f\u0010\u0014\u001a\u0004\u0018\u00010\n*\u00020\u0010H\u0080\b\u001a\r\u0010\u0015\u001a\u00020\n*\u00020\u0010H\u0080\b\u001a\r\u0010\u0016\u001a\u00020\n*\u00020\u0010H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0007H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\nH\u0080\b\u001a\u0017\u0010\u001a\u001a\u00020\u0018*\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u001bH\u0080\b\u001a\u0015\u0010\u001c\u001a\u00020\u001d*\u00020\n2\u0006\u0010\u001e\u001a\u00020\u0005H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0005*\u00020\nH\u0080\b\u001a\r\u0010 \u001a\u00020\u0005*\u00020\nH\u0080\b\u001a\r\u0010!\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\u001d\u0010\"\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\r\u0010$\u001a\u00020\u0007*\u00020\nH\u0080\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\u0007H\u0080\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0080\b\u001a\u001d\u0010,\u001a\u00020\n*\u00020\n2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0080\b\u001a\r\u0010/\u001a\u00020\n*\u00020\nH\u0080\b\u001a\r\u00100\u001a\u00020\n*\u00020\nH\u0080\b\u001a\r\u00101\u001a\u00020\u0007*\u00020\nH\u0080\b\u001a\u001d\u00102\u001a\u00020\n*\u00020\u00072\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a\r\u00103\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a$\u00105\u001a\u000206*\u00020\n2\u0006\u00107\u001a\u0002082\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u00069"}, d2 = {"HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", TypedValues.CycleType.S_WAVE_OFFSET, "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", "", "buffer", "Lokio/Buffer;", "okio"}, k = 2, mv = {1, 4, 1})
public final class ByteStringKt {

    @NotNull
    private static final char[] HEX_DIGIT_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:138:0x012f  */
    /* JADX WARN: Code duplicated, block: B:141:0x0135  */
    /* JADX WARN: Code duplicated, block: B:143:0x0139  */
    /* JADX WARN: Code duplicated, block: B:144:0x013b  */
    /* JADX WARN: Code duplicated, block: B:209:0x01c5  */
    /* JADX WARN: Code duplicated, block: B:212:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:214:0x01cf  */
    /* JADX WARN: Code duplicated, block: B:215:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:228:0x0068 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:235:0x00b5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:243:0x0134 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:254:0x01ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:42:0x0066 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x0069 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:45:0x006b  */
    /* JADX WARN: Code duplicated, block: B:46:0x006d  */
    /* JADX WARN: Code duplicated, block: B:81:0x00b3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:83:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:85:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:86:0x00bc  */
    public static final int codePointIndexToCharIndex(byte[] bArr, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int length = bArr.length;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i7 < length) {
            byte b = bArr[i7];
            if (b >= 0) {
                int i10 = i9 + 1;
                if (i9 == i) {
                    return i8;
                }
                if (b != 10 && b != 13) {
                    if (!((b >= 0 && 31 >= b) || (127 <= b && 159 >= b))) {
                    }
                    return -1;
                }
                if (b == 65533) {
                    return -1;
                }
                i8 += b < 65536 ? 1 : 2;
                i7++;
                while (true) {
                    i9 = i10;
                    if (i7 >= length || bArr[i7] < 0) {
                        break;
                    }
                    int i11 = i7 + 1;
                    byte b2 = bArr[i7];
                    i10 = i9 + 1;
                    if (i9 == i) {
                        return i8;
                    }
                    if (b2 != 10 && b2 != 13) {
                        if (!((b2 >= 0 && 31 >= b2) || (127 <= b2 && 159 >= b2))) {
                            if (b2 == 65533) {
                                if (b2 < 65536) {
                                    i2 = 1;
                                } else {
                                    i2 = 2;
                                }
                                i8 += i2;
                                i7 = i11;
                            }
                        }
                    } else if (b2 == 65533) {
                        if (b2 < 65536) {
                            i2 = 1;
                        } else {
                            i2 = 2;
                        }
                        i8 += i2;
                        i7 = i11;
                    }
                    return -1;
                }
            }
            if ((b >> 5) == -2) {
                int i12 = i7 + 1;
                if (length <= i12) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                byte b3 = bArr[i7];
                byte b4 = bArr[i12];
                if (!((b4 & 192) == 128)) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                int i13 = (b4 ^ 3968) ^ (b3 << 6);
                if (i13 < 128) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                i3 = i9 + 1;
                if (i9 == i) {
                    return i8;
                }
                if (i13 != 10 && i13 != 13) {
                    if (!((i13 >= 0 && 31 >= i13) || (127 <= i13 && 159 >= i13))) {
                        if (i13 == 65533) {
                            if (i13 < 65536) {
                                i6 = 1;
                            } else {
                                i6 = 2;
                            }
                            i8 += i6;
                            Unit unit = Unit.INSTANCE;
                            i7 += 2;
                        }
                    }
                } else if (i13 == 65533) {
                    if (i13 < 65536) {
                        i6 = 1;
                    } else {
                        i6 = 2;
                    }
                    i8 += i6;
                    Unit unit2 = Unit.INSTANCE;
                    i7 += 2;
                }
                return -1;
            }
            if ((b >> 4) == -2) {
                int i14 = i7 + 2;
                if (length <= i14) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                byte b5 = bArr[i7];
                byte b6 = bArr[i7 + 1];
                if (!((b6 & 192) == 128)) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                byte b7 = bArr[i14];
                if (!((b7 & 192) == 128)) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                int i15 = ((b7 ^ (-123008)) ^ (b6 << 6)) ^ (b5 << 12);
                if (i15 < 2048) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                if (55296 <= i15 && 57343 >= i15) {
                    if (i9 == i) {
                        return i8;
                    }
                    return -1;
                }
                i3 = i9 + 1;
                if (i9 == i) {
                    return i8;
                }
                if (i15 != 10 && i15 != 13) {
                    if (!((i15 >= 0 && 31 >= i15) || (127 <= i15 && 159 >= i15))) {
                        if (i15 == 65533) {
                            if (i15 < 65536) {
                                i5 = 1;
                            } else {
                                i5 = 2;
                            }
                            i8 += i5;
                            Unit unit3 = Unit.INSTANCE;
                            i7 += 3;
                        }
                    }
                } else if (i15 == 65533) {
                    if (i15 < 65536) {
                        i5 = 1;
                    } else {
                        i5 = 2;
                    }
                    i8 += i5;
                    Unit unit4 = Unit.INSTANCE;
                    i7 += 3;
                }
                return -1;
            }
            if ((b >> 3) != -2) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            int i16 = i7 + 3;
            if (length <= i16) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            byte b8 = bArr[i7];
            byte b9 = bArr[i7 + 1];
            if (!((b9 & 192) == 128)) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            byte b10 = bArr[i7 + 2];
            if (!((b10 & 192) == 128)) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            byte b11 = bArr[i16];
            if (!((b11 & 192) == 128)) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            int i17 = (((b11 ^ 3678080) ^ (b10 << 6)) ^ (b9 << 12)) ^ (b8 << 18);
            if (i17 > 1114111) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            if (55296 <= i17 && 57343 >= i17) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            if (i17 < 65536) {
                if (i9 == i) {
                    return i8;
                }
                return -1;
            }
            i3 = i9 + 1;
            if (i9 == i) {
                return i8;
            }
            if (i17 != 10 && i17 != 13) {
                if (!((i17 >= 0 && 31 >= i17) || (127 <= i17 && 159 >= i17))) {
                    if (i17 == 65533) {
                        if (i17 < 65536) {
                            i4 = 1;
                        } else {
                            i4 = 2;
                        }
                        i8 += i4;
                        Unit unit5 = Unit.INSTANCE;
                        i7 += 4;
                    }
                }
            } else if (i17 == 65533) {
                if (i17 < 65536) {
                    i4 = 1;
                } else {
                    i4 = 2;
                }
                i8 += i4;
                Unit unit6 = Unit.INSTANCE;
                i7 += 4;
            }
            return -1;
            i9 = i3;
        }
        return i8;
    }

    @NotNull
    public static final String commonBase64(@NotNull ByteString commonBase64) {
        Intrinsics.checkNotNullParameter(commonBase64, "$this$commonBase64");
        return Base64.encodeBase64$default(commonBase64.getData$okio(), null, 1, null);
    }

    @NotNull
    public static final String commonBase64Url(@NotNull ByteString commonBase64Url) {
        Intrinsics.checkNotNullParameter(commonBase64Url, "$this$commonBase64Url");
        return Base64.encodeBase64(commonBase64Url.getData$okio(), Base64.getBASE64_URL_SAFE());
    }

    public static final int commonCompareTo(@NotNull ByteString commonCompareTo, @NotNull ByteString other) {
        Intrinsics.checkNotNullParameter(commonCompareTo, "$this$commonCompareTo");
        Intrinsics.checkNotNullParameter(other, "other");
        int size = commonCompareTo.size();
        int size2 = other.size();
        int iMin = Math.min(size, size2);
        for (int i = 0; i < iMin; i++) {
            int i2 = commonCompareTo.getByte(i) & 255;
            int i3 = other.getByte(i) & 255;
            if (i2 != i3) {
                return i2 < i3 ? -1 : 1;
            }
        }
        if (size == size2) {
            return 0;
        }
        return size < size2 ? -1 : 1;
    }

    @Nullable
    public static final ByteString commonDecodeBase64(@NotNull String commonDecodeBase64) {
        Intrinsics.checkNotNullParameter(commonDecodeBase64, "$this$commonDecodeBase64");
        byte[] bArrDecodeBase64ToArray = Base64.decodeBase64ToArray(commonDecodeBase64);
        if (bArrDecodeBase64ToArray != null) {
            return new ByteString(bArrDecodeBase64ToArray);
        }
        return null;
    }

    @NotNull
    public static final ByteString commonDecodeHex(@NotNull String commonDecodeHex) {
        Intrinsics.checkNotNullParameter(commonDecodeHex, "$this$commonDecodeHex");
        if (!(commonDecodeHex.length() % 2 == 0)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Unexpected hex string: ", commonDecodeHex).toString());
        }
        int length = commonDecodeHex.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (decodeHexDigit(commonDecodeHex.charAt(i2 + 1)) + (decodeHexDigit(commonDecodeHex.charAt(i2)) << 4));
        }
        return new ByteString(bArr);
    }

    @NotNull
    public static final ByteString commonEncodeUtf8(@NotNull String commonEncodeUtf8) {
        Intrinsics.checkNotNullParameter(commonEncodeUtf8, "$this$commonEncodeUtf8");
        ByteString byteString = new ByteString(Platform.asUtf8ToByteArray(commonEncodeUtf8));
        byteString.setUtf8$okio(commonEncodeUtf8);
        return byteString;
    }

    public static final boolean commonEndsWith(@NotNull ByteString commonEndsWith, @NotNull ByteString suffix) {
        Intrinsics.checkNotNullParameter(commonEndsWith, "$this$commonEndsWith");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return commonEndsWith.rangeEquals(commonEndsWith.size() - suffix.size(), suffix, 0, suffix.size());
    }

    public static final boolean commonEquals(@NotNull ByteString commonEquals, @Nullable Object obj) {
        Intrinsics.checkNotNullParameter(commonEquals, "$this$commonEquals");
        if (obj == commonEquals) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == commonEquals.getData$okio().length && byteString.rangeEquals(0, commonEquals.getData$okio(), 0, commonEquals.getData$okio().length)) {
                return true;
            }
        }
        return false;
    }

    public static final byte commonGetByte(@NotNull ByteString commonGetByte, int i) {
        Intrinsics.checkNotNullParameter(commonGetByte, "$this$commonGetByte");
        return commonGetByte.getData$okio()[i];
    }

    public static final int commonGetSize(@NotNull ByteString commonGetSize) {
        Intrinsics.checkNotNullParameter(commonGetSize, "$this$commonGetSize");
        return commonGetSize.getData$okio().length;
    }

    public static final int commonHashCode(@NotNull ByteString commonHashCode) {
        Intrinsics.checkNotNullParameter(commonHashCode, "$this$commonHashCode");
        int hashCode$okio = commonHashCode.getHashCode$okio();
        if (hashCode$okio != 0) {
            return hashCode$okio;
        }
        int iHashCode = Arrays.hashCode(commonHashCode.getData$okio());
        commonHashCode.setHashCode$okio(iHashCode);
        return iHashCode;
    }

    @NotNull
    public static final String commonHex(@NotNull ByteString commonHex) {
        Intrinsics.checkNotNullParameter(commonHex, "$this$commonHex");
        char[] cArr = new char[commonHex.getData$okio().length * 2];
        int i = 0;
        for (byte b : commonHex.getData$okio()) {
            int i2 = i + 1;
            cArr[i] = getHEX_DIGIT_CHARS()[(b >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = getHEX_DIGIT_CHARS()[b & 15];
        }
        return new String(cArr);
    }

    public static final int commonIndexOf(@NotNull ByteString commonIndexOf, @NotNull byte[] other, int i) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        Intrinsics.checkNotNullParameter(other, "other");
        int length = commonIndexOf.getData$okio().length - other.length;
        int iMax = Math.max(i, 0);
        if (iMax > length) {
            return -1;
        }
        while (!Util.arrayRangeEquals(commonIndexOf.getData$okio(), iMax, other, 0, other.length)) {
            if (iMax == length) {
                return -1;
            }
            iMax++;
        }
        return iMax;
    }

    @NotNull
    public static final byte[] commonInternalArray(@NotNull ByteString commonInternalArray) {
        Intrinsics.checkNotNullParameter(commonInternalArray, "$this$commonInternalArray");
        return commonInternalArray.getData$okio();
    }

    public static final int commonLastIndexOf(@NotNull ByteString commonLastIndexOf, @NotNull ByteString other, int i) {
        Intrinsics.checkNotNullParameter(commonLastIndexOf, "$this$commonLastIndexOf");
        Intrinsics.checkNotNullParameter(other, "other");
        return commonLastIndexOf.lastIndexOf(other.internalArray$okio(), i);
    }

    @NotNull
    public static final ByteString commonOf(@NotNull byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return new ByteString(bArrCopyOf);
    }

    public static final boolean commonRangeEquals(@NotNull ByteString commonRangeEquals, int i, @NotNull ByteString other, int i2, int i3) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(other, "other");
        return other.rangeEquals(i2, commonRangeEquals.getData$okio(), i, i3);
    }

    public static final boolean commonStartsWith(@NotNull ByteString commonStartsWith, @NotNull ByteString prefix) {
        Intrinsics.checkNotNullParameter(commonStartsWith, "$this$commonStartsWith");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return commonStartsWith.rangeEquals(0, prefix, 0, prefix.size());
    }

    @NotNull
    public static final ByteString commonSubstring(@NotNull ByteString commonSubstring, int i, int i2) {
        Intrinsics.checkNotNullParameter(commonSubstring, "$this$commonSubstring");
        if (!(i >= 0)) {
            throw new IllegalArgumentException("beginIndex < 0".toString());
        }
        if (!(i2 <= commonSubstring.getData$okio().length)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("endIndex > length("), commonSubstring.getData$okio().length, ')').toString());
        }
        if (i2 - i >= 0) {
            return (i == 0 && i2 == commonSubstring.getData$okio().length) ? commonSubstring : new ByteString(ArraysKt.copyOfRange(commonSubstring.getData$okio(), i, i2));
        }
        throw new IllegalArgumentException("endIndex < beginIndex".toString());
    }

    @NotNull
    public static final ByteString commonToAsciiLowercase(@NotNull ByteString commonToAsciiLowercase) {
        byte b;
        Intrinsics.checkNotNullParameter(commonToAsciiLowercase, "$this$commonToAsciiLowercase");
        for (int i = 0; i < commonToAsciiLowercase.getData$okio().length; i++) {
            byte b2 = commonToAsciiLowercase.getData$okio()[i];
            byte b3 = (byte) 65;
            if (b2 >= b3 && b2 <= (b = (byte) 90)) {
                byte[] data$okio = commonToAsciiLowercase.getData$okio();
                byte[] bArrCopyOf = Arrays.copyOf(data$okio, data$okio.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
                bArrCopyOf[i] = (byte) (b2 + 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b4 = bArrCopyOf[i2];
                    if (b4 >= b3 && b4 <= b) {
                        bArrCopyOf[i2] = (byte) (b4 + 32);
                    }
                }
                return new ByteString(bArrCopyOf);
            }
        }
        return commonToAsciiLowercase;
    }

    @NotNull
    public static final ByteString commonToAsciiUppercase(@NotNull ByteString commonToAsciiUppercase) {
        byte b;
        Intrinsics.checkNotNullParameter(commonToAsciiUppercase, "$this$commonToAsciiUppercase");
        for (int i = 0; i < commonToAsciiUppercase.getData$okio().length; i++) {
            byte b2 = commonToAsciiUppercase.getData$okio()[i];
            byte b3 = (byte) 97;
            if (b2 >= b3 && b2 <= (b = (byte) 122)) {
                byte[] data$okio = commonToAsciiUppercase.getData$okio();
                byte[] bArrCopyOf = Arrays.copyOf(data$okio, data$okio.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
                bArrCopyOf[i] = (byte) (b2 - 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b4 = bArrCopyOf[i2];
                    if (b4 >= b3 && b4 <= b) {
                        bArrCopyOf[i2] = (byte) (b4 - 32);
                    }
                }
                return new ByteString(bArrCopyOf);
            }
        }
        return commonToAsciiUppercase;
    }

    @NotNull
    public static final byte[] commonToByteArray(@NotNull ByteString commonToByteArray) {
        Intrinsics.checkNotNullParameter(commonToByteArray, "$this$commonToByteArray");
        byte[] data$okio = commonToByteArray.getData$okio();
        byte[] bArrCopyOf = Arrays.copyOf(data$okio, data$okio.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return bArrCopyOf;
    }

    @NotNull
    public static final ByteString commonToByteString(@NotNull byte[] commonToByteString, int i, int i2) {
        Intrinsics.checkNotNullParameter(commonToByteString, "$this$commonToByteString");
        Util.checkOffsetAndCount(commonToByteString.length, i, i2);
        return new ByteString(ArraysKt.copyOfRange(commonToByteString, i, i2 + i));
    }

    @NotNull
    public static final String commonToString(@NotNull ByteString commonToString) {
        Intrinsics.checkNotNullParameter(commonToString, "$this$commonToString");
        if (commonToString.getData$okio().length == 0) {
            return "[size=0]";
        }
        int iCodePointIndexToCharIndex = codePointIndexToCharIndex(commonToString.getData$okio(), 64);
        if (iCodePointIndexToCharIndex == -1) {
            if (commonToString.getData$okio().length <= 64) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("[hex=");
                sbM.append(commonToString.hex());
                sbM.append(']');
                return sbM.toString();
            }
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("[size=");
            sbM2.append(commonToString.getData$okio().length);
            sbM2.append(" hex=");
            if (!(64 <= commonToString.getData$okio().length)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("endIndex > length("), commonToString.getData$okio().length, ')').toString());
            }
            if (64 != commonToString.getData$okio().length) {
                commonToString = new ByteString(ArraysKt.copyOfRange(commonToString.getData$okio(), 0, 64));
            }
            sbM2.append(commonToString.hex());
            sbM2.append("…]");
            return sbM2.toString();
        }
        String strUtf8 = commonToString.utf8();
        Objects.requireNonNull(strUtf8, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = strUtf8.substring(0, iCodePointIndexToCharIndex);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(strSubstring, "\\", "\\\\", false, 4, (Object) null), "\n", "\\n", false, 4, (Object) null), "\r", "\\r", false, 4, (Object) null);
        if (iCodePointIndexToCharIndex >= strUtf8.length()) {
            return "[text=" + strReplace$default + ']';
        }
        StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("[size=");
        sbM3.append(commonToString.getData$okio().length);
        sbM3.append(" text=");
        sbM3.append(strReplace$default);
        sbM3.append("…]");
        return sbM3.toString();
    }

    @NotNull
    public static final String commonUtf8(@NotNull ByteString commonUtf8) {
        Intrinsics.checkNotNullParameter(commonUtf8, "$this$commonUtf8");
        String utf8$okio = commonUtf8.getUtf8$okio();
        if (utf8$okio != null) {
            return utf8$okio;
        }
        String utf8String = Platform.toUtf8String(commonUtf8.internalArray$okio());
        commonUtf8.setUtf8$okio(utf8String);
        return utf8String;
    }

    public static final void commonWrite(@NotNull ByteString commonWrite, @NotNull Buffer buffer, int i, int i2) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        buffer.write(commonWrite.getData$okio(), i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int decodeHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return c - '0';
        }
        char c2 = 'a';
        if ('a' > c || 'f' < c) {
            c2 = 'A';
            if ('A' > c || 'F' < c) {
                throw new IllegalArgumentException("Unexpected hex digit: " + c);
            }
        }
        return (c - c2) + 10;
    }

    @NotNull
    public static final char[] getHEX_DIGIT_CHARS() {
        return HEX_DIGIT_CHARS;
    }

    public static final boolean commonEndsWith(@NotNull ByteString commonEndsWith, @NotNull byte[] suffix) {
        Intrinsics.checkNotNullParameter(commonEndsWith, "$this$commonEndsWith");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return commonEndsWith.rangeEquals(commonEndsWith.size() - suffix.length, suffix, 0, suffix.length);
    }

    public static final int commonLastIndexOf(@NotNull ByteString commonLastIndexOf, @NotNull byte[] other, int i) {
        Intrinsics.checkNotNullParameter(commonLastIndexOf, "$this$commonLastIndexOf");
        Intrinsics.checkNotNullParameter(other, "other");
        for (int iMin = Math.min(i, commonLastIndexOf.getData$okio().length - other.length); iMin >= 0; iMin--) {
            if (Util.arrayRangeEquals(commonLastIndexOf.getData$okio(), iMin, other, 0, other.length)) {
                return iMin;
            }
        }
        return -1;
    }

    public static final boolean commonRangeEquals(@NotNull ByteString commonRangeEquals, int i, @NotNull byte[] other, int i2, int i3) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(other, "other");
        return i >= 0 && i <= commonRangeEquals.getData$okio().length - i3 && i2 >= 0 && i2 <= other.length - i3 && Util.arrayRangeEquals(commonRangeEquals.getData$okio(), i, other, i2, i3);
    }

    public static final boolean commonStartsWith(@NotNull ByteString commonStartsWith, @NotNull byte[] prefix) {
        Intrinsics.checkNotNullParameter(commonStartsWith, "$this$commonStartsWith");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return commonStartsWith.rangeEquals(0, prefix, 0, prefix.length);
    }
}
