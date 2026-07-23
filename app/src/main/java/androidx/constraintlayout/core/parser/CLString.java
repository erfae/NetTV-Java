package androidx.constraintlayout.core.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public class CLString extends CLElement {
    public CLString(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLString(cArr);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public final String toFormattedJSON(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        addIndent(sb, i);
        sb.append("'");
        sb.append(content());
        sb.append("'");
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public final String toJSON() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("'");
        sbM.append(content());
        sbM.append("'");
        return sbM.toString();
    }
}
