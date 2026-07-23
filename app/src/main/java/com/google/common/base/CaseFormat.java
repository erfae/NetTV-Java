package com.google.common.base;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtCompatible;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public enum CaseFormat {
    LOWER_HYPHEN { // from class: com.google.common.base.CaseFormat.1
        @Override // com.google.common.base.CaseFormat
        public final String convert(CaseFormat caseFormat, String str) {
            if (caseFormat == CaseFormat.LOWER_UNDERSCORE) {
                return str.replace('-', '_');
            }
            return caseFormat == CaseFormat.UPPER_UNDERSCORE ? Ascii.toUpperCase(str.replace('-', '_')) : super.convert(caseFormat, str);
        }

        @Override // com.google.common.base.CaseFormat
        public final String normalizeWord(String str) {
            return Ascii.toLowerCase(str);
        }
    },
    LOWER_UNDERSCORE { // from class: com.google.common.base.CaseFormat.2
        @Override // com.google.common.base.CaseFormat
        public final String convert(CaseFormat caseFormat, String str) {
            if (caseFormat == CaseFormat.LOWER_HYPHEN) {
                return str.replace('_', '-');
            }
            return caseFormat == CaseFormat.UPPER_UNDERSCORE ? Ascii.toUpperCase(str) : super.convert(caseFormat, str);
        }

        @Override // com.google.common.base.CaseFormat
        public final String normalizeWord(String str) {
            return Ascii.toLowerCase(str);
        }
    },
    LOWER_CAMEL { // from class: com.google.common.base.CaseFormat.3
        @Override // com.google.common.base.CaseFormat
        public final String normalizeFirstWord(String str) {
            return Ascii.toLowerCase(str);
        }

        @Override // com.google.common.base.CaseFormat
        public final String normalizeWord(String str) {
            return CaseFormat.firstCharOnlyToUpper(str);
        }
    },
    UPPER_CAMEL { // from class: com.google.common.base.CaseFormat.4
        @Override // com.google.common.base.CaseFormat
        public final String normalizeWord(String str) {
            return CaseFormat.firstCharOnlyToUpper(str);
        }
    },
    UPPER_UNDERSCORE { // from class: com.google.common.base.CaseFormat.5
        @Override // com.google.common.base.CaseFormat
        public final String convert(CaseFormat caseFormat, String str) {
            if (caseFormat == CaseFormat.LOWER_HYPHEN) {
                return Ascii.toLowerCase(str.replace('_', '-'));
            }
            return caseFormat == CaseFormat.LOWER_UNDERSCORE ? Ascii.toLowerCase(str) : super.convert(caseFormat, str);
        }

        @Override // com.google.common.base.CaseFormat
        public final String normalizeWord(String str) {
            return Ascii.toUpperCase(str);
        }
    };

    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    public static final class StringConverter extends Converter<String, String> implements Serializable {
        private static final long serialVersionUID = 0;
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;

        public StringConverter(CaseFormat caseFormat, CaseFormat caseFormat2) {
            this.sourceFormat = (CaseFormat) Preconditions.checkNotNull(caseFormat);
            this.targetFormat = (CaseFormat) Preconditions.checkNotNull(caseFormat2);
        }

        @Override // com.google.common.base.Converter
        public final String doBackward(String str) {
            return this.targetFormat.to(this.sourceFormat, str);
        }

        @Override // com.google.common.base.Converter
        public final String doForward(String str) {
            return this.sourceFormat.to(this.targetFormat, str);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof StringConverter)) {
                return false;
            }
            StringConverter stringConverter = (StringConverter) obj;
            return this.sourceFormat.equals(stringConverter.sourceFormat) && this.targetFormat.equals(stringConverter.targetFormat);
        }

        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }

        public String toString() {
            String strValueOf = String.valueOf(this.sourceFormat);
            String strValueOf2 = String.valueOf(this.targetFormat);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 14, strValueOf, ".converterTo(", strValueOf2, ")");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String firstCharOnlyToUpper(String str) {
        if (str.isEmpty()) {
            return str;
        }
        char upperCase = Ascii.toUpperCase(str.charAt(0));
        String lowerCase = Ascii.toLowerCase(str.substring(1));
        StringBuilder sb = new StringBuilder(Insets$$ExternalSyntheticOutline0.m(lowerCase, 1));
        sb.append(upperCase);
        sb.append(lowerCase);
        return sb.toString();
    }

    public String convert(CaseFormat caseFormat, String str) {
        StringBuilder sb = null;
        int length = 0;
        int iIndexIn = -1;
        while (true) {
            iIndexIn = this.wordBoundary.indexIn(str, iIndexIn + 1);
            if (iIndexIn == -1) {
                break;
            }
            if (length == 0) {
                sb = new StringBuilder((caseFormat.wordSeparator.length() * 4) + str.length());
                sb.append(caseFormat.normalizeFirstWord(str.substring(length, iIndexIn)));
            } else {
                java.util.Objects.requireNonNull(sb);
                sb.append(caseFormat.normalizeWord(str.substring(length, iIndexIn)));
            }
            sb.append(caseFormat.wordSeparator);
            length = this.wordSeparator.length() + iIndexIn;
        }
        if (length == 0) {
            return caseFormat.normalizeFirstWord(str);
        }
        java.util.Objects.requireNonNull(sb);
        sb.append(caseFormat.normalizeWord(str.substring(length)));
        return sb.toString();
    }

    public Converter<String, String> converterTo(CaseFormat caseFormat) {
        return new StringConverter(this, caseFormat);
    }

    public String normalizeFirstWord(String str) {
        return normalizeWord(str);
    }

    public abstract String normalizeWord(String str);

    public final String to(CaseFormat caseFormat, String str) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(str);
        return caseFormat == this ? str : convert(caseFormat, str);
    }

    CaseFormat(CharMatcher charMatcher, String str) {
        this.wordBoundary = charMatcher;
        this.wordSeparator = str;
    }
}
