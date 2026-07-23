package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(serializable = true)
@ElementTypesAreNonnullByDefault
final class PairwiseEquivalence<E, T extends E> extends Equivalence<Iterable<T>> implements Serializable {
    private static final long serialVersionUID = 1;
    public final Equivalence<E> elementEquivalence;

    public PairwiseEquivalence(Equivalence<E> equivalence) {
        this.elementEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
    }

    @Override // com.google.common.base.Equivalence
    public final boolean doEquivalent(Object obj, Object obj2) {
        Iterator it = ((Iterable) obj).iterator();
        Iterator it2 = ((Iterable) obj2).iterator();
        while (it.hasNext() && it2.hasNext()) {
            if (!this.elementEquivalence.equivalent((E) it.next(), (E) it2.next())) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    @Override // com.google.common.base.Equivalence
    public final int doHash(Object obj) {
        Iterator it = ((Iterable) obj).iterator();
        int iHash = 78721;
        while (it.hasNext()) {
            iHash = (iHash * 24943) + this.elementEquivalence.hash((E) it.next());
        }
        return iHash;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj instanceof PairwiseEquivalence) {
            return this.elementEquivalence.equals(((PairwiseEquivalence) obj).elementEquivalence);
        }
        return false;
    }

    public int hashCode() {
        return this.elementEquivalence.hashCode() ^ 1185147655;
    }

    public String toString() {
        String strValueOf = String.valueOf(this.elementEquivalence);
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 11, strValueOf, ".pairwise()");
    }
}
