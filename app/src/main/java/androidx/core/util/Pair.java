package androidx.core.util;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    @NonNull
    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<>(a, b);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return ObjectsCompat.equals(pair.first, this.first) && ObjectsCompat.equals(pair.second, this.second);
    }

    public int hashCode() {
        F f = this.first;
        int iHashCode = f == null ? 0 : f.hashCode();
        S s = this.second;
        return iHashCode ^ (s != null ? s.hashCode() : 0);
    }

    @NonNull
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Pair{");
        sbM.append(this.first);
        sbM.append(" ");
        sbM.append(this.second);
        sbM.append("}");
        return sbM.toString();
    }
}
