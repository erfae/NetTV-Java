package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {

    @Beta
    public class StandardElementSet extends Multisets.ElementSet<E> {
        public StandardElementSet() {
        }

        @Override // com.google.common.collect.Multisets.ElementSet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return new Multisets.AnonymousClass5(ForwardingMultiset.this.entrySet().iterator());
        }

        @Override // com.google.common.collect.Multisets.ElementSet
        public final Multiset<E> multiset() {
            return ForwardingMultiset.this;
        }
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(@ParametricNullness E e, int i) {
        return delegate().add(e, i);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@CheckForNull Object obj) {
        return delegate().count(obj);
    }

    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract Multiset<E> delegate();

    public Set<E> elementSet() {
        return delegate().elementSet();
    }

    public Set<Multiset.Entry<E>> entrySet() {
        return delegate().entrySet();
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public boolean equals(@CheckForNull Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@CheckForNull Object obj, int i) {
        return delegate().remove(obj, i);
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(@ParametricNullness E e, int i) {
        return delegate().setCount(e, i);
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public boolean setCount(@ParametricNullness E e, int i, int i2) {
        return delegate().setCount(e, i, i2);
    }
}
