package okhttp3.internal;

import java.net.IDN;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: hostnames.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0002\u001a\"\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\r\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u0003*\u00020\u0003¨\u0006\u000f"}, d2 = {"decodeIpv4Suffix", "", "input", "", "pos", "", "limit", "address", "", "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"}, k = 2, mv = {1, 4, 1})
public final class HostnamesKt {
    private static final boolean containsInvalidHostnameAsciiCodes(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (Intrinsics.compare((int) cCharAt, 31) <= 0 || Intrinsics.compare((int) cCharAt, 127) >= 0 || StringsKt.indexOf$default(" #%/:?@[\\]", cCharAt, 0, 6) != -1) {
                return true;
            }
        }
        return false;
    }

    private static final boolean decodeIpv4Suffix(String str, int i, int i2, byte[] bArr, int i3) {
        int i4 = i3;
        while (i < i2) {
            if (i4 == bArr.length) {
                return false;
            }
            if (i4 != i3) {
                if (str.charAt(i) != '.') {
                    return false;
                }
                i++;
            }
            int i5 = i;
            int i6 = 0;
            while (i5 < i2) {
                char cCharAt = str.charAt(i5);
                if (Intrinsics.compare((int) cCharAt, 48) < 0 || Intrinsics.compare((int) cCharAt, 57) > 0) {
                    break;
                }
                if ((i6 == 0 && i != i5) || (i6 = ((i6 * 10) + cCharAt) - 48) > 255) {
                    return false;
                }
                i5++;
            }
            if (i5 - i == 0) {
                return false;
            }
            bArr[i4] = (byte) i6;
            i4++;
            i = i5;
        }
        return i4 == i3 + 4;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x004d  */
    /* JADX WARN: Code duplicated, block: B:34:0x0058 A[LOOP:1: B:30:0x004b->B:34:0x0058, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:55:0x005e A[EDGE_INSN: B:55:0x005e->B:35:0x005e BREAK  A[LOOP:1: B:30:0x004b->B:34:0x0058], SYNTHETIC] */
    private static final InetAddress decodeIpv6(String str, int i, int i2) {
        int i3;
        int i4;
        int hexDigit;
        byte[] bArr = new byte[16];
        int i5 = 0;
        int i6 = -1;
        int i7 = -1;
        while (i < i2) {
            if (i5 == 16) {
                return null;
            }
            int i8 = i + 2;
            if (i8 <= i2 && StringsKt__StringsJVMKt.startsWith$default(str, "::", i, false, 4, null)) {
                if (i6 != -1) {
                    return null;
                }
                i5 += 2;
                i6 = i5;
                if (i8 == i2) {
                    break;
                }
                i7 = i8;
                i = i7;
                i3 = 0;
                while (i < i2) {
                    hexDigit = Util.parseHexDigit(str.charAt(i));
                    if (hexDigit == -1) {
                        break;
                        break;
                    }
                    i3 = (i3 << 4) + hexDigit;
                    i++;
                }
                i4 = i - i7;
                if (i4 != 0) {
                }
                return null;
            }
            if (i5 != 0) {
                if (!StringsKt__StringsJVMKt.startsWith$default(str, ":", i, false, 4, null)) {
                    if (!StringsKt__StringsJVMKt.startsWith$default(str, ".", i, false, 4, null) || !decodeIpv4Suffix(str, i7, i2, bArr, i5 - 2)) {
                        return null;
                    }
                    i5 += 2;
                    break;
                }
                i++;
            }
            i7 = i;
            i = i7;
            i3 = 0;
            while (i < i2) {
                hexDigit = Util.parseHexDigit(str.charAt(i));
                if (hexDigit == -1) {
                    break;
                }
                i3 = (i3 << 4) + hexDigit;
                i++;
            }
            i4 = i - i7;
            if (i4 != 0 || i4 > 4) {
                return null;
            }
            int i9 = i5 + 1;
            bArr[i5] = (byte) ((i3 >>> 8) & 255);
            i5 = i9 + 1;
            bArr[i9] = (byte) (i3 & 255);
        }
        if (i5 != 16) {
            if (i6 == -1) {
                return null;
            }
            int i10 = i5 - i6;
            System.arraycopy(bArr, i6, bArr, 16 - i10, i10);
            Arrays.fill(bArr, i6, (16 - i5) + i6, (byte) 0);
        }
        return InetAddress.getByAddress(bArr);
    }

    private static final String inet6AddressToAscii(byte[] bArr) {
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < bArr.length) {
            int i5 = i3;
            while (i5 < 16 && bArr[i5] == 0 && bArr[i5 + 1] == 0) {
                i5 += 2;
            }
            int i6 = i5 - i3;
            if (i6 > i4 && i6 >= 4) {
                i2 = i3;
                i4 = i6;
            }
            i3 = i5 + 2;
        }
        Buffer buffer = new Buffer();
        while (i < bArr.length) {
            if (i == i2) {
                buffer.writeByte(58);
                i += i4;
                if (i == 16) {
                    buffer.writeByte(58);
                }
            } else {
                if (i > 0) {
                    buffer.writeByte(58);
                }
                buffer.writeHexadecimalUnsignedLong((Util.and(bArr[i], 255) << 8) | Util.and(bArr[i + 1], 255));
                i += 2;
            }
        }
        return buffer.readUtf8();
    }

    @Nullable
    public static final String toCanonicalHost(@NotNull String toCanonicalHost) {
        Intrinsics.checkNotNullParameter(toCanonicalHost, "$this$toCanonicalHost");
        boolean z = true;
        if (StringsKt.contains$default(toCanonicalHost, ":")) {
            InetAddress inetAddressDecodeIpv6 = (StringsKt.startsWith$default(toCanonicalHost, "[") && StringsKt.endsWith$default(toCanonicalHost, "]")) ? decodeIpv6(toCanonicalHost, 1, toCanonicalHost.length() - 1) : decodeIpv6(toCanonicalHost, 0, toCanonicalHost.length());
            if (inetAddressDecodeIpv6 == null) {
                return null;
            }
            byte[] address = inetAddressDecodeIpv6.getAddress();
            if (address.length == 16) {
                Intrinsics.checkNotNullExpressionValue(address, "address");
                return inet6AddressToAscii(address);
            }
            if (address.length == 4) {
                return inetAddressDecodeIpv6.getHostAddress();
            }
            throw new AssertionError("Invalid IPv6 address: '" + toCanonicalHost + '\'');
        }
        try {
            String ascii = IDN.toASCII(toCanonicalHost);
            Intrinsics.checkNotNullExpressionValue(ascii, "IDN.toASCII(host)");
            Locale locale = Locale.US;
            Intrinsics.checkNotNullExpressionValue(locale, "Locale.US");
            if (ascii == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = ascii.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
            if (lowerCase.length() != 0) {
                z = false;
            }
            if (!z && !containsInvalidHostnameAsciiCodes(lowerCase)) {
                return lowerCase;
            }
            return null;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
