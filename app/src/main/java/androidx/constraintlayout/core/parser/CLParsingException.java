package androidx.constraintlayout.core.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.os.EnvironmentCompat;

/* JADX INFO: loaded from: classes.dex */
public class CLParsingException extends Exception {
    private final String mElementClass;
    private final int mLineNumber;
    private final String mReason;

    public CLParsingException(String str, CLElement cLElement) {
        this.mReason = str;
        if (cLElement != null) {
            this.mElementClass = cLElement.getStrClass();
            this.mLineNumber = cLElement.getLine();
        } else {
            this.mElementClass = EnvironmentCompat.MEDIA_UNKNOWN;
            this.mLineNumber = 0;
        }
    }

    public String reason() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mReason);
        sb.append(" (");
        sb.append(this.mElementClass);
        sb.append(" at line ");
        return Insets$$ExternalSyntheticOutline0.m(sb, this.mLineNumber, ")");
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("CLParsingException (");
        sbM.append(hashCode());
        sbM.append(") : ");
        sbM.append(reason());
        return sbM.toString();
    }
}
