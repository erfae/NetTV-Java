package org.bson.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
abstract class CopyOnWriteMap<K, V> extends AbstractCopyOnWriteMap<K, V, Map<K, V>> {
    private static final long serialVersionUID = 7935514534647505917L;

    public static class Builder<K, V> {
        private AbstractCopyOnWriteMap.View.Type viewType = AbstractCopyOnWriteMap.View.Type.STABLE;
        private final Map<K, V> initialValues = new HashMap();

        public Builder<K, V> addAll(Map<? extends K, ? extends V> map) {
            this.initialValues.putAll(map);
            return this;
        }

        public Builder<K, V> liveViews() {
            this.viewType = AbstractCopyOnWriteMap.View.Type.LIVE;
            return this;
        }

        public CopyOnWriteMap<K, V> newHashMap() {
            return new Hash(this.initialValues, this.viewType);
        }

        public CopyOnWriteMap<K, V> newLinkedMap() {
            return new Linked(this.initialValues, this.viewType);
        }

        public Builder<K, V> stableViews() {
            this.viewType = AbstractCopyOnWriteMap.View.Type.STABLE;
            return this;
        }
    }

    public static class Hash<K, V> extends CopyOnWriteMap<K, V> {
        private static final long serialVersionUID = 5221824943734164497L;

        public Hash(Map<? extends K, ? extends V> map, AbstractCopyOnWriteMap.View.Type type) {
            super(map, type);
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap
        public <N extends Map<? extends K, ? extends V>> Map<K, V> copy(N n) {
            return new HashMap(n);
        }
    }

    public static class Linked<K, V> extends CopyOnWriteMap<K, V> {
        private static final long serialVersionUID = -8659999465009072124L;

        public Linked(Map<? extends K, ? extends V> map, AbstractCopyOnWriteMap.View.Type type) {
            super(map, type);
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap
        public <N extends Map<? extends K, ? extends V>> Map<K, V> copy(N n) {
            return new LinkedHashMap(n);
        }
    }

    public CopyOnWriteMap() {
        super(Collections.emptyMap(), AbstractCopyOnWriteMap.View.Type.LIVE);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> CopyOnWriteMap<K, V> newHashMap() {
        return builder().newHashMap();
    }

    public static <K, V> CopyOnWriteMap<K, V> newLinkedMap() {
        return builder().newLinkedMap();
    }

    public CopyOnWriteMap(Map<? extends K, ? extends V> map, AbstractCopyOnWriteMap.View.Type type) {
        super(map, type);
    }

    public static <K, V> CopyOnWriteMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return builder().addAll(map).newHashMap();
    }

    public static <K, V> CopyOnWriteMap<K, V> newLinkedMap(Map<? extends K, ? extends V> map) {
        return builder().addAll(map).newLinkedMap();
    }
}
