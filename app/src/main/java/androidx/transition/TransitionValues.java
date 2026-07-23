package androidx.transition;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TransitionValues {
    public View view;
    public final Map<String, Object> values = new HashMap();
    public final ArrayList<Transition> mTargetedTransitions = new ArrayList<>();

    @Deprecated
    public TransitionValues() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TransitionValues)) {
            return false;
        }
        TransitionValues transitionValues = (TransitionValues) obj;
        return this.view == transitionValues.view && this.values.equals(transitionValues.values);
    }

    public int hashCode() {
        return this.values.hashCode() + (this.view.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("TransitionValues@");
        sbM.append(Integer.toHexString(hashCode()));
        sbM.append(":\n");
        StringBuilder sbM25m = Insets$$ExternalSyntheticOutline0.m25m(sbM.toString(), "    view = ");
        sbM25m.append(this.view);
        sbM25m.append("\n");
        String strM = Insets$$ExternalSyntheticOutline0.m(sbM25m.toString(), "    values:");
        for (String str : this.values.keySet()) {
            strM = strM + "    " + str + ": " + this.values.get(str) + "\n";
        }
        return strM;
    }

    public TransitionValues(@NonNull View view) {
        this.view = view;
    }
}
