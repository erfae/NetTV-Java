package okio.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okio.Utf8;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: -Utf8.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k = 2, mv = {1, 4, 1})
public final class _Utf8Kt {
    @NotNull
    public static final byte[] commonAsUtf8ToByteArray(@NotNull String commonAsUtf8ToByteArray) {
        int i;
        int i2;
        char cCharAt;
        Intrinsics.checkNotNullParameter(commonAsUtf8ToByteArray, "$this$commonAsUtf8ToByteArray");
        byte[] bArr = new byte[commonAsUtf8ToByteArray.length() * 4];
        int length = commonAsUtf8ToByteArray.length();
        int i3 = 0;
        while (i3 < length) {
            char cCharAt2 = commonAsUtf8ToByteArray.charAt(i3);
            if (Intrinsics.compare((int) cCharAt2, 128) >= 0) {
                int length2 = commonAsUtf8ToByteArray.length();
                int i4 = i3;
                while (i3 < length2) {
                    char cCharAt3 = commonAsUtf8ToByteArray.charAt(i3);
                    if (Intrinsics.compare((int) cCharAt3, 128) < 0) {
                        int i5 = i4 + 1;
                        bArr[i4] = (byte) cCharAt3;
                        i3++;
                        while (i3 < length2 && Intrinsics.compare((int) commonAsUtf8ToByteArray.charAt(i3), 128) < 0) {
                            bArr[i5] = (byte) commonAsUtf8ToByteArray.charAt(i3);
                            i3++;
                            i5++;
                        }
                        i4 = i5;
                    } else {
                        if (Intrinsics.compare((int) cCharAt3, 2048) < 0) {
                            int i6 = i4 + 1;
                            bArr[i4] = (byte) ((cCharAt3 >> 6) | PsExtractor.AUDIO_STREAM);
                            byte b = (byte) ((cCharAt3 & '?') | 128);
                            i = i6 + 1;
                            bArr[i6] = b;
                        } else if (55296 > cCharAt3 || 57343 < cCharAt3) {
                            int i7 = i4 + 1;
                            bArr[i4] = (byte) ((cCharAt3 >> '\f') | 224);
                            int i8 = i7 + 1;
                            bArr[i7] = (byte) (((cCharAt3 >> 6) & 63) | 128);
                            byte b2 = (byte) ((cCharAt3 & '?') | 128);
                            i = i8 + 1;
                            bArr[i8] = b2;
                        } else {
                            if (Intrinsics.compare((int) cCharAt3, 56319) > 0 || length2 <= (i2 = i3 + 1) || 56320 > (cCharAt = commonAsUtf8ToByteArray.charAt(i2)) || 57343 < cCharAt) {
                                i = i4 + 1;
                                bArr[i4] = Utf8.REPLACEMENT_BYTE;
                            } else {
                                int iCharAt = (commonAsUtf8ToByteArray.charAt(i2) + (cCharAt3 << '\n')) - 56613888;
                                int i9 = i4 + 1;
                                bArr[i4] = (byte) ((iCharAt >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                                int i10 = i9 + 1;
                                bArr[i9] = (byte) (((iCharAt >> 12) & 63) | 128);
                                int i11 = i10 + 1;
                                bArr[i10] = (byte) (((iCharAt >> 6) & 63) | 128);
                                i = i11 + 1;
                                bArr[i11] = (byte) ((iCharAt & 63) | 128);
                                i3 += 2;
                            }
                            i4 = i;
                        }
                        i3++;
                        i4 = i;
                    }
                }
                byte[] bArrCopyOf = Arrays.copyOf(bArr, i4);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
                return bArrCopyOf;
            }
            bArr[i3] = (byte) cCharAt2;
            i3++;
        }
        byte[] bArrCopyOf2 = Arrays.copyOf(bArr, commonAsUtf8ToByteArray.length());
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return bArrCopyOf2;
    }

    @NotNull
    public static final String commonToUtf8String(@NotNull byte[] commonToUtf8String, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        Intrinsics.checkNotNullParameter(commonToUtf8String, "$this$commonToUtf8String");
        if (i < 0 || i2 > commonToUtf8String.length || i > i2) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("size=");
            sbM.append(commonToUtf8String.length);
            sbM.append(" beginIndex=");
            sbM.append(i);
            sbM.append(" endIndex=");
            sbM.append(i2);
            throw new ArrayIndexOutOfBoundsException(sbM.toString());
        }
        char[] cArr = new char[i2 - i];
        int i7 = 0;
        while (i < i2) {
            byte b = commonToUtf8String[i];
            if (b >= 0) {
                i3 = i7 + 1;
                cArr[i7] = (char) b;
                i++;
                while (i < i2 && commonToUtf8String[i] >= 0) {
                    cArr[i3] = (char) commonToUtf8String[i];
                    i++;
                    i3++;
                }
            } else if ((b >> 5) == -2) {
                int i8 = i + 1;
                if (i2 <= i8) {
                    i3 = i7 + 1;
                    cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                } else {
                    byte b2 = commonToUtf8String[i];
                    byte b3 = commonToUtf8String[i8];
                    if ((b3 & 192) == 128) {
                        int i9 = (b3 ^ 3968) ^ (b2 << 6);
                        if (i9 < 128) {
                            i3 = i7 + 1;
                            cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        } else {
                            i3 = i7 + 1;
                            cArr[i7] = (char) i9;
                        }
                        Unit unit = Unit.INSTANCE;
                        i4 = 2;
                        i += i4;
                    } else {
                        i3 = i7 + 1;
                        cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                i4 = 1;
                i += i4;
            } else if ((b >> 4) == -2) {
                int i10 = i + 2;
                if (i2 <= i10) {
                    i3 = i7 + 1;
                    cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                    Unit unit3 = Unit.INSTANCE;
                    int i11 = i + 1;
                    if (i2 > i11) {
                        if ((commonToUtf8String[i11] & 192) == 128) {
                            i4 = 2;
                        }
                    }
                    i4 = 1;
                } else {
                    byte b4 = commonToUtf8String[i];
                    byte b5 = commonToUtf8String[i + 1];
                    if ((b5 & 192) == 128) {
                        byte b6 = commonToUtf8String[i10];
                        if ((b6 & 192) == 128) {
                            int i12 = ((b6 ^ (-123008)) ^ (b5 << 6)) ^ (b4 << 12);
                            if (i12 < 2048) {
                                i3 = i7 + 1;
                                cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            } else if (55296 <= i12 && 57343 >= i12) {
                                i3 = i7 + 1;
                                cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            } else {
                                i3 = i7 + 1;
                                cArr[i7] = (char) i12;
                            }
                            Unit unit4 = Unit.INSTANCE;
                            i4 = 3;
                        } else {
                            i3 = i7 + 1;
                            cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            Unit unit5 = Unit.INSTANCE;
                            i4 = 2;
                        }
                    } else {
                        i3 = i7 + 1;
                        cArr[i7] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        Unit unit6 = Unit.INSTANCE;
                        i4 = 1;
                    }
                }
                i += i4;
            } else {
                if ((b >> 3) == -2) {
                    int i13 = i + 3;
                    if (i2 <= i13) {
                        i5 = i7 + 1;
                        cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                        Unit unit7 = Unit.INSTANCE;
                        int i14 = i + 1;
                        if (i2 > i14) {
                            if ((commonToUtf8String[i14] & 192) == 128) {
                                int i15 = i + 2;
                                if (i2 > i15) {
                                    if ((commonToUtf8String[i15] & 192) == 128) {
                                        i6 = 3;
                                    }
                                }
                                i6 = 2;
                            }
                        }
                        i6 = 1;
                    } else {
                        byte b7 = commonToUtf8String[i];
                        byte b8 = commonToUtf8String[i + 1];
                        if ((b8 & 192) == 128) {
                            byte b9 = commonToUtf8String[i + 2];
                            if ((b9 & 192) == 128) {
                                byte b10 = commonToUtf8String[i13];
                                if ((b10 & 192) == 128) {
                                    int i16 = (((b10 ^ 3678080) ^ (b9 << 6)) ^ (b8 << 12)) ^ (b7 << 18);
                                    if (i16 > 1114111) {
                                        i5 = i7 + 1;
                                        cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                                    } else if ((55296 <= i16 && 57343 >= i16) || i16 < 65536 || i16 == 65533) {
                                        i5 = i7 + 1;
                                        cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                                    } else {
                                        int i17 = i7 + 1;
                                        cArr[i7] = (char) ((i16 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                        char c = (char) ((i16 & AnalyticsListener.EVENT_DRM_KEYS_LOADED) + Utf8.LOG_SURROGATE_HEADER);
                                        i5 = i17 + 1;
                                        cArr[i17] = c;
                                    }
                                    Unit unit8 = Unit.INSTANCE;
                                    i6 = 4;
                                } else {
                                    i5 = i7 + 1;
                                    cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                                    Unit unit9 = Unit.INSTANCE;
                                    i6 = 3;
                                }
                            } else {
                                i5 = i7 + 1;
                                cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                                Unit unit10 = Unit.INSTANCE;
                                i6 = 2;
                            }
                        } else {
                            i5 = i7 + 1;
                            cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                            Unit unit11 = Unit.INSTANCE;
                            i6 = 1;
                        }
                    }
                    i += i6;
                } else {
                    i5 = i7 + 1;
                    cArr[i7] = Utf8.REPLACEMENT_CHARACTER;
                    i++;
                }
                i7 = i5;
            }
            i7 = i3;
        }
        return new String(cArr, 0, i7);
    }

    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        return commonToUtf8String(bArr, i, i2);
    }
}
