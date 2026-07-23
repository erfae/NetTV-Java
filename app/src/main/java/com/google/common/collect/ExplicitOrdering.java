package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.List;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(serializable = true)
@ElementTypesAreNonnullByDefault
final class ExplicitOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final ImmutableMap<T, Integer> rankMap;

    public ExplicitOrdering(List<T> list) {
        this.rankMap = Maps.indexMap(list);
    }

    private int rank(T t) {
        Integer num = this.rankMap.get(t);
        if (num != null) {
            return num.intValue();
        }
        throw new Ordering.IncomparableValueException(t);
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(T t, T t2) {
        return rank(t) - rank(t2);
    }

    @Override // java.util.Comparator
    public boolean equals(@CheckForNull Object obj) {
        if (obj instanceof ExplicitOrdering) {
            return this.rankMap.equals(((ExplicitOrdering) obj).rankMap);
        }
        return false;
    }

    public int hashCode() {
        return this.rankMap.hashCode();
    }

    public String toString() {
        String strValueOf = String.valueOf(this.rankMap.keySet());
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 19, "Ordering.explicit(", strValueOf, ")");
    }
}
