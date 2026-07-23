package androidx.constraintlayout.core.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CLKey extends CLContainer {
    private static ArrayList<String> sections;

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        sections = arrayList;
        arrayList.add("ConstraintSets");
        sections.add("Variables");
        sections.add("Generate");
        sections.add(TypedValues.TransitionType.NAME);
        sections.add("KeyFrames");
        sections.add(TypedValues.AttributesType.NAME);
        sections.add("KeyPositions");
        sections.add("KeyCycles");
    }

    public CLKey(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLKey(cArr);
    }

    public String getName() {
        return content();
    }

    public CLElement getValue() {
        if (this.mElements.size() > 0) {
            return this.mElements.get(0);
        }
        return null;
    }

    public void set(CLElement cLElement) {
        if (this.mElements.size() > 0) {
            this.mElements.set(0, cLElement);
        } else {
            this.mElements.add(cLElement);
        }
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public final String toFormattedJSON(int i, int i2) {
        StringBuilder sb = new StringBuilder("");
        addIndent(sb, i);
        String strContent = content();
        if (this.mElements.size() <= 0) {
            return Insets$$ExternalSyntheticOutline0.m(strContent, ": <> ");
        }
        sb.append(strContent);
        sb.append(": ");
        if (sections.contains(strContent)) {
            i2 = 3;
        }
        if (i2 > 0) {
            sb.append(this.mElements.get(0).toFormattedJSON(i, i2 - 1));
        } else {
            String json = this.mElements.get(0).toJSON();
            if (json.length() + i < 80) {
                sb.append(json);
            } else {
                sb.append(this.mElements.get(0).toFormattedJSON(i, i2 - 1));
            }
        }
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public final String toJSON() {
        if (this.mElements.size() <= 0) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("");
            sbM.append(content());
            sbM.append(": <> ");
            return sbM.toString();
        }
        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("");
        sbM2.append(content());
        sbM2.append(": ");
        sbM2.append(this.mElements.get(0).toJSON());
        return sbM2.toString();
    }

    public static CLElement allocate(String str, CLElement cLElement) {
        CLKey cLKey = new CLKey(str.toCharArray());
        cLKey.setStart(0L);
        cLKey.setEnd(str.length() - 1);
        cLKey.set(cLElement);
        return cLKey;
    }
}
