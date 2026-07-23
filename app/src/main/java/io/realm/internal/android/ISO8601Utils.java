package io.realm.internal.android;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes2.dex */
public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC;
    private static final TimeZone TIMEZONE_Z;
    private static final String UTC_ID = "UTC";

    static {
        TimeZone timeZone = TimeZone.getTimeZone(UTC_ID);
        TIMEZONE_UTC = timeZone;
        TIMEZONE_Z = timeZone;
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00d3 A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x0006, B:5:0x0018, B:6:0x001a, B:8:0x0026, B:9:0x0028, B:11:0x0037, B:13:0x003d, B:17:0x0052, B:19:0x0062, B:20:0x0064, B:22:0x0070, B:23:0x0072, B:25:0x0078, B:29:0x0082, B:34:0x0092, B:36:0x009a, B:47:0x00cd, B:49:0x00d3, B:51:0x00d9, B:74:0x0184, B:56:0x00e5, B:57:0x00fe, B:58:0x00ff, B:60:0x010f, B:61:0x0120, B:63:0x0128, B:66:0x0131, B:68:0x0150, B:71:0x015f, B:72:0x0181, B:73:0x0182, B:76:0x01b6, B:77:0x01bd, B:40:0x00b2, B:41:0x00b5), top: B:93:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00d9 A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x0006, B:5:0x0018, B:6:0x001a, B:8:0x0026, B:9:0x0028, B:11:0x0037, B:13:0x003d, B:17:0x0052, B:19:0x0062, B:20:0x0064, B:22:0x0070, B:23:0x0072, B:25:0x0078, B:29:0x0082, B:34:0x0092, B:36:0x009a, B:47:0x00cd, B:49:0x00d3, B:51:0x00d9, B:74:0x0184, B:56:0x00e5, B:57:0x00fe, B:58:0x00ff, B:60:0x010f, B:61:0x0120, B:63:0x0128, B:66:0x0131, B:68:0x0150, B:71:0x015f, B:72:0x0181, B:73:0x0182, B:76:0x01b6, B:77:0x01bd, B:40:0x00b2, B:41:0x00b5), top: B:93:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00de  */
    /* JADX WARN: Code duplicated, block: B:60:0x010f A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x0006, B:5:0x0018, B:6:0x001a, B:8:0x0026, B:9:0x0028, B:11:0x0037, B:13:0x003d, B:17:0x0052, B:19:0x0062, B:20:0x0064, B:22:0x0070, B:23:0x0072, B:25:0x0078, B:29:0x0082, B:34:0x0092, B:36:0x009a, B:47:0x00cd, B:49:0x00d3, B:51:0x00d9, B:74:0x0184, B:56:0x00e5, B:57:0x00fe, B:58:0x00ff, B:60:0x010f, B:61:0x0120, B:63:0x0128, B:66:0x0131, B:68:0x0150, B:71:0x015f, B:72:0x0181, B:73:0x0182, B:76:0x01b6, B:77:0x01bd, B:40:0x00b2, B:41:0x00b5), top: B:93:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x0182 A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x0006, B:5:0x0018, B:6:0x001a, B:8:0x0026, B:9:0x0028, B:11:0x0037, B:13:0x003d, B:17:0x0052, B:19:0x0062, B:20:0x0064, B:22:0x0070, B:23:0x0072, B:25:0x0078, B:29:0x0082, B:34:0x0092, B:36:0x009a, B:47:0x00cd, B:49:0x00d3, B:51:0x00d9, B:74:0x0184, B:56:0x00e5, B:57:0x00fe, B:58:0x00ff, B:60:0x010f, B:61:0x0120, B:63:0x0128, B:66:0x0131, B:68:0x0150, B:71:0x015f, B:72:0x0181, B:73:0x0182, B:76:0x01b6, B:77:0x01bd, B:40:0x00b2, B:41:0x00b5), top: B:93:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:76:0x01b6 A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x0006, B:5:0x0018, B:6:0x001a, B:8:0x0026, B:9:0x0028, B:11:0x0037, B:13:0x003d, B:17:0x0052, B:19:0x0062, B:20:0x0064, B:22:0x0070, B:23:0x0072, B:25:0x0078, B:29:0x0082, B:34:0x0092, B:36:0x009a, B:47:0x00cd, B:49:0x00d3, B:51:0x00d9, B:74:0x0184, B:56:0x00e5, B:57:0x00fe, B:58:0x00ff, B:60:0x010f, B:61:0x0120, B:63:0x0128, B:66:0x0131, B:68:0x0150, B:71:0x015f, B:72:0x0181, B:73:0x0182, B:76:0x01b6, B:77:0x01bd, B:40:0x00b2, B:41:0x00b5), top: B:93:0x0006 }] */
    /* JADX WARN: Instruction removed from duplicated block: B:60:0x010f, please report this as an issue */
    public static Date parse(String str, ParsePosition parsePosition) throws ParseException {
        String str2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        char cCharAt;
        String strSubstring;
        int length;
        TimeZone timeZone;
        char cCharAt2;
        try {
            int index = parsePosition.getIndex();
            int i7 = index + 4;
            int i8 = parseInt(str, index, i7);
            if (checkOffset(str, i7, '-')) {
                i7++;
            }
            int i9 = i7 + 2;
            int i10 = parseInt(str, i7, i9);
            if (checkOffset(str, i9, '-')) {
                i9++;
            }
            int i11 = i9 + 2;
            int i12 = parseInt(str, i9, i11);
            boolean zCheckOffset = checkOffset(str, i11, 'T');
            if (!zCheckOffset && str.length() <= i11) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(i8, i10 - 1, i12);
                parsePosition.setIndex(i11);
                return gregorianCalendar.getTime();
            }
            if (zCheckOffset) {
                int i13 = i11 + 1;
                int i14 = i13 + 2;
                i5 = parseInt(str, i13, i14);
                if (checkOffset(str, i14, ':')) {
                    i14++;
                }
                int i15 = i14 + 2;
                i2 = parseInt(str, i14, i15);
                if (checkOffset(str, i15, ':')) {
                    i15++;
                }
                if (str.length() <= i15 || (cCharAt2 = str.charAt(i15)) == 'Z' || cCharAt2 == '+' || cCharAt2 == '-') {
                    i = i5;
                    i11 = i15;
                } else {
                    i6 = i15 + 2;
                    i3 = parseInt(str, i15, i6);
                    if (i3 > 59 && i3 < 63) {
                        i3 = 59;
                    }
                    if (checkOffset(str, i6, '.')) {
                        int i16 = i6 + 1;
                        int iIndexOfNonDigit = indexOfNonDigit(str, i16 + 1);
                        int iMin = Math.min(iIndexOfNonDigit, i16 + 3);
                        int i17 = parseInt(str, i16, iMin);
                        int i18 = iMin - i16;
                        if (i18 == 1) {
                            i17 *= 100;
                        } else if (i18 == 2) {
                            i17 *= 10;
                        }
                        i6 = iIndexOfNonDigit;
                        i4 = i17;
                    } else {
                        i4 = 0;
                    }
                }
                if (str.length() > i6) {
                    throw new IllegalArgumentException("No time zone indicator");
                }
                cCharAt = str.charAt(i6);
                if (cCharAt == 'Z') {
                    timeZone = TIMEZONE_Z;
                    length = i6 + 1;
                } else {
                    if (cCharAt != '+' && cCharAt != '-') {
                        throw new IndexOutOfBoundsException("Invalid time zone indicator '" + cCharAt + "'");
                    }
                    strSubstring = str.substring(i6);
                    length = i6 + strSubstring.length();
                    if (strSubstring.length() == 3) {
                        strSubstring = strSubstring + "00";
                    }
                    if (!"+0000".equals(strSubstring) || "+00:00".equals(strSubstring)) {
                        timeZone = TIMEZONE_Z;
                    } else {
                        String str3 = "GMT" + strSubstring;
                        timeZone = TimeZone.getTimeZone(str3);
                        String id = timeZone.getID();
                        if (!id.equals(str3) && !id.replace(":", "").equals(str3)) {
                            throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str3 + " given, resolves to " + timeZone.getID());
                        }
                    }
                }
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
                gregorianCalendar2.setLenient(false);
                gregorianCalendar2.set(1, i8);
                gregorianCalendar2.set(2, i10 - 1);
                gregorianCalendar2.set(5, i12);
                gregorianCalendar2.set(11, i5);
                gregorianCalendar2.set(12, i2);
                gregorianCalendar2.set(13, i3);
                gregorianCalendar2.set(14, i4);
                parsePosition.setIndex(length);
                return gregorianCalendar2.getTime();
            }
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            int i19 = i11;
            i5 = i;
            i6 = i19;
            if (str.length() > i6) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            cCharAt = str.charAt(i6);
            if (cCharAt == 'Z') {
                timeZone = TIMEZONE_Z;
                length = i6 + 1;
            } else {
                if (cCharAt != '+') {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator '" + cCharAt + "'");
                }
                strSubstring = str.substring(i6);
                length = i6 + strSubstring.length();
                if (strSubstring.length() == 3) {
                    strSubstring = strSubstring + "00";
                }
                if ("+0000".equals(strSubstring)) {
                    timeZone = TIMEZONE_Z;
                } else {
                    timeZone = TIMEZONE_Z;
                }
            }
            GregorianCalendar gregorianCalendar3 = new GregorianCalendar(timeZone);
            gregorianCalendar3.setLenient(false);
            gregorianCalendar3.set(1, i8);
            gregorianCalendar3.set(2, i10 - 1);
            gregorianCalendar3.set(5, i12);
            gregorianCalendar3.set(11, i5);
            gregorianCalendar3.set(12, i2);
            gregorianCalendar3.set(13, i3);
            gregorianCalendar3.set(14, i4);
            parsePosition.setIndex(length);
            return gregorianCalendar3.getTime();
        } catch (NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException e) {
            if (str == null) {
                str2 = null;
            } else {
                str2 = Typography.quote + str + "'";
            }
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("(");
                sbM.append(e.getClass().getName());
                sbM.append(")");
                message = sbM.toString();
            }
            ParseException parseException = new ParseException("Failed to parse date [" + str2 + "]: " + message, parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int iDigit = Character.digit(str.charAt(i), 10);
            if (iDigit < 0) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Invalid number: ");
                sbM.append(str.substring(i, i2));
                throw new NumberFormatException(sbM.toString());
            }
            i3 = -iDigit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int iDigit2 = Character.digit(str.charAt(i4), 10);
            if (iDigit2 < 0) {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Invalid number: ");
                sbM2.append(str.substring(i, i2));
                throw new NumberFormatException(sbM2.toString());
            }
            i3 = (i3 * 10) - iDigit2;
            i4 = i5;
        }
        return -i3;
    }
}
