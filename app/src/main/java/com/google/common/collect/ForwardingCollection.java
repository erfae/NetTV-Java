package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public abstract class ForwardingCollection<E> extends ForwardingObject implements Collection<E> {
    @CanIgnoreReturnValue
    public boolean add(@ParametricNullness E e) {
        return delegate().add(e);
    }

    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        return delegate().addAll(collection);
    }

    public void clear() {
        delegate().clear();
    }

    public boolean contains(@CheckForNull Object obj) {
        return delegate().contains(obj);
    }

    public boolean containsAll(Collection<?> collection) {
        return delegate().containsAll(collection);
    }

    @Override // com.google.common.collect.ForwardingObject
    public abstract Collection<E> delegate();

    @Override // java.util.Collection
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    public Iterator<E> iterator() {
        return delegate().iterator();
    }

    @CanIgnoreReturnValue
    public boolean remove(@CheckForNull Object obj) {
        return delegate().remove(obj);
    }

    @CanIgnoreReturnValue
    public boolean removeAll(Collection<?> collection) {
        return delegate().removeAll(collection);
    }

    @CanIgnoreReturnValue
    public boolean retainAll(Collection<?> collection) {
        return delegate().retainAll(collection);
    }

    @Override // java.util.Collection
    public int size() {
        return delegate().size();
    }

    public final Object[] standardToArray() {
        return toArray(new Object[size()]);
    }

    public final String standardToString() {
        StringBuilder sbNewStringBuilderForCollection = Collections2.newStringBuilderForCollection(size());
        sbNewStringBuilderForCollection.append('[');
        boolean z = true;
        for (E e : this) {
            if (!z) {
                sbNewStringBuilderForCollection.append(", ");
            }
            z = false;
            if (e == this) {
                sbNewStringBuilderForCollection.append("(this Collection)");
            } else {
                sbNewStringBuilderForCollection.append(e);
            }
        }
        sbNewStringBuilderForCollection.append(']');
        return sbNewStringBuilderForCollection.toString();
    }

    public Object[] toArray() {
        return delegate().toArray();
    }

    @CanIgnoreReturnValue
    public <T> T[] toArray(T[] tArr) {
        return (T[]) delegate().toArray(tArr);
    }
}
