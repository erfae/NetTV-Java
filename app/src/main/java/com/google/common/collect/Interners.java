package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
public final class Interners {

    public static class InternerBuilder {
        private final MapMaker mapMaker;
        private boolean strong;

        public <E> Interner<E> build() {
            if (!this.strong) {
                this.mapMaker.weakKeys();
            }
            return new InternerImpl(this.mapMaker);
        }

        public InternerBuilder concurrencyLevel(int i) {
            this.mapMaker.concurrencyLevel(i);
            return this;
        }

        public InternerBuilder strong() {
            this.strong = true;
            return this;
        }

        @GwtIncompatible("java.lang.ref.WeakReference")
        public InternerBuilder weak() {
            this.strong = false;
            return this;
        }

        private InternerBuilder() {
            this.mapMaker = new MapMaker();
            this.strong = true;
        }
    }

    public static class InternerFunction<E> implements Function<E, E> {
        private final Interner<E> interner;

        public InternerFunction(Interner<E> interner) {
            this.interner = interner;
        }

        @Override // com.google.common.base.Function
        public E apply(E e) {
            return this.interner.intern(e);
        }

        @Override // com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof InternerFunction) {
                return this.interner.equals(((InternerFunction) obj).interner);
            }
            return false;
        }

        public int hashCode() {
            return this.interner.hashCode();
        }
    }

    @VisibleForTesting
    public static final class InternerImpl<E> implements Interner<E> {

        @VisibleForTesting
        public final MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> map;

        @Override // com.google.common.collect.Interner
        public E intern(E e) {
            MapMakerInternalMap.InternalEntry entry;
            E e2;
            do {
                MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> mapMakerInternalMap = this.map;
                Objects.requireNonNull(mapMakerInternalMap);
                if (e == null) {
                    entry = null;
                } else {
                    int iHash = mapMakerInternalMap.hash(e);
                    entry = mapMakerInternalMap.segmentFor(iHash).getEntry(e, iHash);
                }
                if (entry != null && (e2 = (E) entry.getKey()) != null) {
                    return e2;
                }
            } while (this.map.putIfAbsent(e, MapMaker.Dummy.VALUE) != null);
            return e;
        }

        private InternerImpl(MapMaker mapMaker) {
            mapMaker.keyEquivalence(Equivalence.equals());
            this.map = MapMakerInternalMap.createWithDummyValues(mapMaker);
        }
    }

    private Interners() {
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction((Interner) Preconditions.checkNotNull(interner));
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder();
    }

    public static <E> Interner<E> newStrongInterner() {
        return newBuilder().strong().build();
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return newBuilder().weak().build();
    }
}
