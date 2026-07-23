package com.google.gson.internal.bind.util;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes2.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
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

    private static void padInt(StringBuilder sb, int i, int i2) {
        String string = Integer.toString(i);
        for (int length = i2 - string.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(string);
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00d3 A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x0050, B:19:0x0060, B:20:0x0062, B:22:0x006e, B:23:0x0070, B:25:0x0076, B:29:0x0080, B:34:0x0090, B:36:0x0098, B:48:0x00cd, B:50:0x00d3, B:52:0x00da, B:76:0x0187, B:56:0x00e4, B:57:0x00ff, B:58:0x0100, B:62:0x011c, B:64:0x0129, B:67:0x0132, B:69:0x0151, B:72:0x0160, B:73:0x0182, B:75:0x0185, B:61:0x010b, B:78:0x01b8, B:79:0x01bf, B:40:0x00b0, B:41:0x00b3), top: B:95:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00da A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x0050, B:19:0x0060, B:20:0x0062, B:22:0x006e, B:23:0x0070, B:25:0x0076, B:29:0x0080, B:34:0x0090, B:36:0x0098, B:48:0x00cd, B:50:0x00d3, B:52:0x00da, B:76:0x0187, B:56:0x00e4, B:57:0x00ff, B:58:0x0100, B:62:0x011c, B:64:0x0129, B:67:0x0132, B:69:0x0151, B:72:0x0160, B:73:0x0182, B:75:0x0185, B:61:0x010b, B:78:0x01b8, B:79:0x01bf, B:40:0x00b0, B:41:0x00b3), top: B:95:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x00df  */
    /* JADX WARN: Code duplicated, block: B:60:0x010a  */
    /* JADX WARN: Code duplicated, block: B:61:0x010b A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x0050, B:19:0x0060, B:20:0x0062, B:22:0x006e, B:23:0x0070, B:25:0x0076, B:29:0x0080, B:34:0x0090, B:36:0x0098, B:48:0x00cd, B:50:0x00d3, B:52:0x00da, B:76:0x0187, B:56:0x00e4, B:57:0x00ff, B:58:0x0100, B:62:0x011c, B:64:0x0129, B:67:0x0132, B:69:0x0151, B:72:0x0160, B:73:0x0182, B:75:0x0185, B:61:0x010b, B:78:0x01b8, B:79:0x01bf, B:40:0x00b0, B:41:0x00b3), top: B:95:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:75:0x0185 A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x0050, B:19:0x0060, B:20:0x0062, B:22:0x006e, B:23:0x0070, B:25:0x0076, B:29:0x0080, B:34:0x0090, B:36:0x0098, B:48:0x00cd, B:50:0x00d3, B:52:0x00da, B:76:0x0187, B:56:0x00e4, B:57:0x00ff, B:58:0x0100, B:62:0x011c, B:64:0x0129, B:67:0x0132, B:69:0x0151, B:72:0x0160, B:73:0x0182, B:75:0x0185, B:61:0x010b, B:78:0x01b8, B:79:0x01bf, B:40:0x00b0, B:41:0x00b3), top: B:95:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x01b8 A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x0050, B:19:0x0060, B:20:0x0062, B:22:0x006e, B:23:0x0070, B:25:0x0076, B:29:0x0080, B:34:0x0090, B:36:0x0098, B:48:0x00cd, B:50:0x00d3, B:52:0x00da, B:76:0x0187, B:56:0x00e4, B:57:0x00ff, B:58:0x0100, B:62:0x011c, B:64:0x0129, B:67:0x0132, B:69:0x0151, B:72:0x0160, B:73:0x0182, B:75:0x0185, B:61:0x010b, B:78:0x01b8, B:79:0x01bf, B:40:0x00b0, B:41:0x00b3), top: B:95:0x0004 }] */
    /* JADX WARN: Instruction removed from duplicated block: B:61:0x010b, please report this as an issue */
    public static Date parse(String str, ParsePosition parsePosition) throws ParseException {
        String str2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        char cCharAt;
        String strSubstring;
        int length;
        TimeZone timeZone;
        char cCharAt2;
        try {
            int index = parsePosition.getIndex();
            int i9 = index + 4;
            int i10 = parseInt(str, index, i9);
            if (checkOffset(str, i9, '-')) {
                i9++;
            }
            int i11 = i9 + 2;
            int i12 = parseInt(str, i9, i11);
            if (checkOffset(str, i11, '-')) {
                i11++;
            }
            int i13 = i11 + 2;
            int i14 = parseInt(str, i11, i13);
            boolean zCheckOffset = checkOffset(str, i13, 'T');
            if (!zCheckOffset && str.length() <= i13) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(i10, i12 - 1, i14);
                parsePosition.setIndex(i13);
                return gregorianCalendar.getTime();
            }
            if (zCheckOffset) {
                int i15 = i13 + 1;
                int i16 = i15 + 2;
                i6 = parseInt(str, i15, i16);
                if (checkOffset(str, i16, ':')) {
                    i16++;
                }
                int i17 = i16 + 2;
                i7 = parseInt(str, i16, i17);
                if (checkOffset(str, i17, ':')) {
                    i17++;
                }
                if (str.length() <= i17 || (cCharAt2 = str.charAt(i17)) == 'Z' || cCharAt2 == '+' || cCharAt2 == '-') {
                    i2 = i7;
                    i3 = 0;
                    i = i6;
                    i13 = i17;
                } else {
                    i5 = i17 + 2;
                    i4 = parseInt(str, i17, i5);
                    if (i4 > 59 && i4 < 63) {
                        i4 = 59;
                    }
                    if (checkOffset(str, i5, '.')) {
                        int i18 = i5 + 1;
                        int iIndexOfNonDigit = indexOfNonDigit(str, i18 + 1);
                        int iMin = Math.min(iIndexOfNonDigit, i18 + 3);
                        i3 = parseInt(str, i18, iMin);
                        int i19 = iMin - i18;
                        if (i19 == 1) {
                            i3 *= 100;
                        } else if (i19 == 2) {
                            i3 *= 10;
                        }
                        i5 = iIndexOfNonDigit;
                        i8 = i3;
                    } else {
                        i8 = 0;
                    }
                }
                if (str.length() > i5) {
                    throw new IllegalArgumentException("No time zone indicator");
                }
                cCharAt = str.charAt(i5);
                if (cCharAt == 'Z') {
                    timeZone = TIMEZONE_UTC;
                    length = i5 + 1;
                } else {
                    if (cCharAt != '+' && cCharAt != '-') {
                        throw new IndexOutOfBoundsException("Invalid time zone indicator '" + cCharAt + "'");
                    }
                    strSubstring = str.substring(i5);
                    if (strSubstring.length() >= 5) {
                        strSubstring = strSubstring + "00";
                    }
                    length = i5 + strSubstring.length();
                    if (!"+0000".equals(strSubstring) || "+00:00".equals(strSubstring)) {
                        timeZone = TIMEZONE_UTC;
                    } else {
                        String str3 = "GMT" + strSubstring;
                        TimeZone timeZone2 = TimeZone.getTimeZone(str3);
                        String id = timeZone2.getID();
                        if (!id.equals(str3) && !id.replace(":", "").equals(str3)) {
                            throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str3 + " given, resolves to " + timeZone2.getID());
                        }
                        timeZone = timeZone2;
                    }
                }
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
                gregorianCalendar2.setLenient(false);
                gregorianCalendar2.set(1, i10);
                gregorianCalendar2.set(2, i12 - 1);
                gregorianCalendar2.set(5, i14);
                gregorianCalendar2.set(11, i6);
                gregorianCalendar2.set(12, i7);
                gregorianCalendar2.set(13, i4);
                gregorianCalendar2.set(14, i8);
                parsePosition.setIndex(length);
                return gregorianCalendar2.getTime();
            }
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = i13;
            i6 = i;
            i7 = i2;
            i8 = i3;
            if (str.length() > i5) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            cCharAt = str.charAt(i5);
            if (cCharAt == 'Z') {
                timeZone = TIMEZONE_UTC;
                length = i5 + 1;
            } else {
                if (cCharAt != '+') {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator '" + cCharAt + "'");
                }
                strSubstring = str.substring(i5);
                if (strSubstring.length() >= 5) {
                    strSubstring = strSubstring + "00";
                }
                length = i5 + strSubstring.length();
                if ("+0000".equals(strSubstring)) {
                    timeZone = TIMEZONE_UTC;
                } else {
                    timeZone = TIMEZONE_UTC;
                }
            }
            GregorianCalendar gregorianCalendar3 = new GregorianCalendar(timeZone);
            gregorianCalendar3.setLenient(false);
            gregorianCalendar3.set(1, i10);
            gregorianCalendar3.set(2, i12 - 1);
            gregorianCalendar3.set(5, i14);
            gregorianCalendar3.set(11, i6);
            gregorianCalendar3.set(12, i7);
            gregorianCalendar3.set(13, i4);
            gregorianCalendar3.set(14, i8);
            parsePosition.setIndex(length);
            return gregorianCalendar3.getTime();
        } catch (IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException e) {
            if (str == null) {
                str2 = null;
            } else {
                str2 = Typography.quote + str + Typography.quote;
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

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / 60000;
            int iAbs = Math.abs(i / 60);
            int iAbs2 = Math.abs(i % 60);
            sb.append(offset >= 0 ? '+' : '-');
            padInt(sb, iAbs, 2);
            sb.append(':');
            padInt(sb, iAbs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }
}
