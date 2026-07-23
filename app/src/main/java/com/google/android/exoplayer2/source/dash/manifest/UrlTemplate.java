package com.google.android.exoplayer2.source.dash.manifest;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class UrlTemplate {
    private static final String BANDWIDTH = "Bandwidth";
    private static final int BANDWIDTH_ID = 3;
    private static final String DEFAULT_FORMAT_TAG = "%01d";
    private static final String ESCAPED_DOLLAR = "$$";
    private static final String NUMBER = "Number";
    private static final int NUMBER_ID = 2;
    private static final String REPRESENTATION = "RepresentationID";
    private static final int REPRESENTATION_ID = 1;
    private static final String TIME = "Time";
    private static final int TIME_ID = 4;
    private final int identifierCount;
    private final String[] identifierFormatTags;
    private final int[] identifiers;
    private final String[] urlPieces;

    private UrlTemplate(String[] strArr, int[] iArr, String[] strArr2, int i) {
        this.urlPieces = strArr;
        this.identifiers = iArr;
        this.identifierFormatTags = strArr2;
        this.identifierCount = i;
    }

    public static UrlTemplate compile(String str) {
        String[] strArr = new String[5];
        int[] iArr = new int[4];
        String[] strArr2 = new String[4];
        return new UrlTemplate(strArr, iArr, strArr2, parseTemplate(str, strArr, iArr, strArr2));
    }

    private static int parseTemplate(String str, String[] strArr, int[] iArr, String[] strArr2) {
        String strSubstring;
        strArr[0] = "";
        int length = 0;
        int i = 0;
        while (length < str.length()) {
            int iIndexOf = str.indexOf("$", length);
            if (iIndexOf == -1) {
                strArr[i] = strArr[i] + str.substring(length);
                length = str.length();
            } else if (iIndexOf != length) {
                strArr[i] = strArr[i] + str.substring(length, iIndexOf);
                length = iIndexOf;
            } else if (str.startsWith(ESCAPED_DOLLAR, length)) {
                strArr[i] = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), strArr[i], "$");
                length += 2;
            } else {
                int i2 = length + 1;
                int iIndexOf2 = str.indexOf("$", i2);
                String strSubstring2 = str.substring(i2, iIndexOf2);
                if (strSubstring2.equals(REPRESENTATION)) {
                    iArr[i] = 1;
                } else {
                    int iIndexOf3 = strSubstring2.indexOf("%0");
                    if (iIndexOf3 != -1) {
                        strSubstring = strSubstring2.substring(iIndexOf3);
                        if (!strSubstring.endsWith("d") && !strSubstring.endsWith("x") && !strSubstring.endsWith("X")) {
                            strSubstring = Insets$$ExternalSyntheticOutline0.m(strSubstring, "d");
                        }
                        strSubstring2 = strSubstring2.substring(0, iIndexOf3);
                    } else {
                        strSubstring = DEFAULT_FORMAT_TAG;
                    }
                    Objects.requireNonNull(strSubstring2);
                    strSubstring2.hashCode();
                    switch (strSubstring2) {
                        case "Number":
                            iArr[i] = 2;
                            break;
                        case "Time":
                            iArr[i] = 4;
                            break;
                        case "Bandwidth":
                            iArr[i] = 3;
                            break;
                        default:
                            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Invalid template: ", str));
                    }
                    strArr2[i] = strSubstring;
                }
                i++;
                strArr[i] = "";
                length = iIndexOf2 + 1;
            }
        }
        return i;
    }

    public String buildUri(String str, long j, int i, long j2) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            int i3 = this.identifierCount;
            if (i2 >= i3) {
                sb.append(this.urlPieces[i3]);
                return sb.toString();
            }
            sb.append(this.urlPieces[i2]);
            int[] iArr = this.identifiers;
            if (iArr[i2] == 1) {
                sb.append(str);
            } else if (iArr[i2] == 2) {
                sb.append(String.format(Locale.US, this.identifierFormatTags[i2], Long.valueOf(j)));
            } else if (iArr[i2] == 3) {
                sb.append(String.format(Locale.US, this.identifierFormatTags[i2], Integer.valueOf(i)));
            } else if (iArr[i2] == 4) {
                sb.append(String.format(Locale.US, this.identifierFormatTags[i2], Long.valueOf(j2)));
            }
            i2++;
        }
    }
}
