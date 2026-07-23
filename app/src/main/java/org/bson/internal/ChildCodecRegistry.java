package org.bson.internal;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes2.dex */
class ChildCodecRegistry<T> implements CodecRegistry {
    private final Class<T> codecClass;
    private final ChildCodecRegistry<?> parent;
    private final CycleDetectingCodecRegistry registry;

    public ChildCodecRegistry(CycleDetectingCodecRegistry cycleDetectingCodecRegistry, Class<T> cls) {
        this.codecClass = cls;
        this.parent = null;
        this.registry = cycleDetectingCodecRegistry;
    }

    private <U> Boolean hasCycles(Class<U> cls) {
        for (ChildCodecRegistry childCodecRegistry = this; childCodecRegistry != null; childCodecRegistry = childCodecRegistry.parent) {
            if (childCodecRegistry.codecClass.equals(cls)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChildCodecRegistry childCodecRegistry = (ChildCodecRegistry) obj;
        if (!this.codecClass.equals(childCodecRegistry.codecClass)) {
            return false;
        }
        ChildCodecRegistry<?> childCodecRegistry2 = this.parent;
        if (childCodecRegistry2 == null ? childCodecRegistry.parent == null : childCodecRegistry2.equals(childCodecRegistry.parent)) {
            return this.registry.equals(childCodecRegistry.registry);
        }
        return false;
    }

    @Override // org.bson.codecs.configuration.CodecRegistry
    public <U> Codec<U> get(Class<U> cls) {
        return hasCycles(cls).booleanValue() ? new LazyCodec(this.registry, cls) : this.registry.get(new ChildCodecRegistry<>((ChildCodecRegistry<?>) this, (Class) cls));
    }

    public Class<T> getCodecClass() {
        return this.codecClass;
    }

    public int hashCode() {
        ChildCodecRegistry<?> childCodecRegistry = this.parent;
        return this.codecClass.hashCode() + ((this.registry.hashCode() + ((childCodecRegistry != null ? childCodecRegistry.hashCode() : 0) * 31)) * 31);
    }

    private ChildCodecRegistry(ChildCodecRegistry<?> childCodecRegistry, Class<T> cls) {
        this.parent = childCodecRegistry;
        this.codecClass = cls;
        this.registry = childCodecRegistry.registry;
    }
}
