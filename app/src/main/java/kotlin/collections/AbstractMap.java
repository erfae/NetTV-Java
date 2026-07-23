package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AbstractMap.kt */
/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin(version = "1.1")
@Metadata(bv = {}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010&\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\r\b'\u0018\u0000 +*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003:\u0001+J\u001c\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\bJ\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002J%\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00042\u0006\u0010\n\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00028\u0001H\u0016¢\u0006\u0004\b\u0011\u0010\u000fJ!\u0010\u0014\u001a\u00020\r2\u0010\u0010\u0005\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0004H\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u0013\u0010\u0016\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\bH\u0096\u0002J\u001a\u0010\u0017\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\b\u0010\u001b\u001a\u00020\rH\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016R\u001e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001c8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001d\u0010\u001eR\u001e\u0010 \u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001f8\b@\bX\u0088\u000e¢\u0006\u0006\n\u0004\b \u0010!R\u0014\u0010$\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u001a\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00010\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006,"}, d2 = {"Lkotlin/collections/AbstractMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "", "entry", "", "toString", "", "o", "key", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "", "containsKey", "(Ljava/lang/Object;)Z", "value", "containsValue", "containsEntry$kotlin_stdlib", "(Ljava/util/Map$Entry;)Z", "containsEntry", "other", "equals", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "", "hashCode", "isEmpty", "", "_keys", "Ljava/util/Set;", "", "_values", "Ljava/util/Collection;", "getSize", "()I", "size", "getKeys", "()Ljava/util/Set;", "keys", "getValues", "()Ljava/util/Collection;", "values", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public abstract class AbstractMap<K, V> implements Map<K, V>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private volatile Set<? extends K> _keys;

    @Nullable
    private volatile Collection<? extends V> _values;

    /* JADX INFO: compiled from: AbstractMap.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean entryEquals$kotlin_stdlib(@NotNull Map.Entry<?, ?> e, @Nullable Object other) {
            Intrinsics.checkNotNullParameter(e, "e");
            if (!(other instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) other;
            return Intrinsics.areEqual(e.getKey(), entry.getKey()) && Intrinsics.areEqual(e.getValue(), entry.getValue());
        }

        public final int entryHashCode$kotlin_stdlib(@NotNull Map.Entry<?, ?> e) {
            Intrinsics.checkNotNullParameter(e, "e");
            Object key = e.getKey();
            int iHashCode = key != null ? key.hashCode() : 0;
            Object value = e.getValue();
            return iHashCode ^ (value != null ? value.hashCode() : 0);
        }

        @NotNull
        public final String entryToString$kotlin_stdlib(@NotNull Map.Entry<?, ?> e) {
            Intrinsics.checkNotNullParameter(e, "e");
            StringBuilder sb = new StringBuilder();
            sb.append(e.getKey());
            sb.append('=');
            sb.append(e.getValue());
            return sb.toString();
        }
    }

    private final Map.Entry<K, V> implFindEntry(K key) {
        Object next;
        Iterator<T> it = entrySet().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((Map.Entry) next).getKey(), key)) {
                return (Map.Entry) next;
            }
        }
        next = null;
        return (Map.Entry) next;
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean containsEntry$kotlin_stdlib(@Nullable Map.Entry<?, ?> entry) {
        if (entry == null) {
            return false;
        }
        Object key = entry.getKey();
        Object value = entry.getValue();
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.get, V of kotlin.collections.MapsKt__MapsKt.get>");
        V v = get(key);
        if (!Intrinsics.areEqual(value, v)) {
            return false;
        }
        if (v != null) {
            return true;
        }
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.containsKey, *>");
        return containsKey(key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public boolean containsKey(Object key) {
        return implFindEntry(key) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object value) {
        Set<Map.Entry<K, V>> setEntrySet = entrySet();
        if ((setEntrySet instanceof Collection) && setEntrySet.isEmpty()) {
            return false;
        }
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((Map.Entry) it.next()).getValue(), value)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Map)) {
            return false;
        }
        Map map = (Map) other;
        if (size() != map.size()) {
            return false;
        }
        Set<Map.Entry<K, V>> setEntrySet = map.entrySet();
        if ((setEntrySet instanceof Collection) && setEntrySet.isEmpty()) {
            return true;
        }
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            if (!containsEntry$kotlin_stdlib((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Nullable
    public V get(Object key) {
        Map.Entry<K, V> entryImplFindEntry = implFindEntry(key);
        if (entryImplFindEntry != null) {
            return entryImplFindEntry.getValue();
        }
        return null;
    }

    public abstract Set getEntries();

    @NotNull
    public Set<K> getKeys() {
        if (this._keys == null) {
            this._keys = new AbstractSet<K>(this) { // from class: kotlin.collections.AbstractMap$keys$1
                public final /* synthetic */ AbstractMap<K, V> this$0;

                /* JADX WARN: Multi-variable type inference failed */
                {
                    this.this$0 = this;
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection
                public boolean contains(Object element) {
                    return this.this$0.containsKey(element);
                }

                @Override // kotlin.collections.AbstractCollection
                public int getSize() {
                    return this.this$0.size();
                }

                @Override // kotlin.collections.AbstractSet, kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
                @NotNull
                public Iterator<K> iterator() {
                    return new AbstractMap$keys$1$iterator$1(this.this$0.entrySet().iterator());
                }
            };
        }
        Set<? extends K> set = this._keys;
        Intrinsics.checkNotNull(set);
        return set;
    }

    public int getSize() {
        return entrySet().size();
    }

    @NotNull
    public Collection<V> getValues() {
        if (this._values == null) {
            this._values = new AbstractCollection<V>(this) { // from class: kotlin.collections.AbstractMap.values.1
                public final /* synthetic */ AbstractMap<K, V> this$0;

                /* JADX WARN: Multi-variable type inference failed */
                {
                    this.this$0 = this;
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection
                public boolean contains(Object element) {
                    return this.this$0.containsValue(element);
                }

                @Override // kotlin.collections.AbstractCollection
                public int getSize() {
                    return this.this$0.size();
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
                @NotNull
                public Iterator<V> iterator() {
                    return new AbstractMap$values$1$iterator$1(this.this$0.entrySet().iterator());
                }
            };
        }
        Collection<? extends V> collection = this._values;
        Intrinsics.checkNotNull(collection);
        return collection;
    }

    @Override // java.util.Map
    public int hashCode() {
        return entrySet().hashCode();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<K> keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @NotNull
    public String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(entrySet(), ", ", "{", "}", 0, null, new Function1<Map.Entry<? extends K, ? extends V>, CharSequence>(this) { // from class: kotlin.collections.AbstractMap.toString.1
            public final /* synthetic */ AbstractMap<K, V> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull Map.Entry<? extends K, ? extends V> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.toString((Map.Entry) it);
            }
        }, 24, null);
    }

    @Override // java.util.Map
    public final /* bridge */ Collection<V> values() {
        return getValues();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toString(Map.Entry<? extends K, ? extends V> entry) {
        return toString(entry.getKey()) + '=' + toString(entry.getValue());
    }

    private final String toString(Object o) {
        return o == this ? "(this Map)" : String.valueOf(o);
    }
}
