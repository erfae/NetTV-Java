package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    public static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset<>(new ObjectCountHashMap());
    public final transient ObjectCountHashMap<E> contents;

    @CheckForNull
    @LazyInit
    private transient ImmutableSet<E> elementSet;
    private final transient int size;

    public final class ElementSet extends IndexedImmutableSet<E> {
        private ElementSet() {
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return RegularImmutableMultiset.this.contains(obj);
        }

        @Override // com.google.common.collect.IndexedImmutableSet
        public final E get(int i) {
            return RegularImmutableMultiset.this.contents.getKey(i);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return RegularImmutableMultiset.this.contents.size;
        }
    }

    @GwtIncompatible
    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
    }

    public RegularImmutableMultiset(ObjectCountHashMap<E> objectCountHashMap) {
        this.contents = objectCountHashMap;
        long value = 0;
        for (int i = 0; i < objectCountHashMap.size; i++) {
            value += (long) objectCountHashMap.getValue(i);
        }
        this.size = Ints.saturatedCast(value);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@CheckForNull Object obj) {
        return this.contents.get(obj);
    }

    @Override // com.google.common.collect.ImmutableMultiset
    public final Multiset.Entry<E> getEntry(int i) {
        ObjectCountHashMap<E> objectCountHashMap = this.contents;
        Preconditions.checkElementIndex(i, objectCountHashMap.size);
        return new ObjectCountHashMap.MapEntry(i);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> immutableSet = this.elementSet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ElementSet elementSet = new ElementSet();
        this.elementSet = elementSet;
        return elementSet;
    }
}
