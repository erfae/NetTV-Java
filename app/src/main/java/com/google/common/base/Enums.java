package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
public final class Enums {

    @GwtIncompatible
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap();

    public static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Class<T> enumClass;

        public StringConverter(Class<T> cls) {
            this.enumClass = (Class) Preconditions.checkNotNull(cls);
        }

        @Override // com.google.common.base.Converter
        public final String doBackward(Object obj) {
            return ((Enum) obj).name();
        }

        @Override // com.google.common.base.Converter
        public final Object doForward(String str) {
            return Enum.valueOf(this.enumClass, str);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof StringConverter) {
                return this.enumClass.equals(((StringConverter) obj).enumClass);
            }
            return false;
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            String name = this.enumClass.getName();
            return MainActivity$$ExternalSyntheticOutline0.m(name.length() + 29, "Enums.stringConverter(", name, ".class)");
        }
    }

    private Enums() {
    }

    @GwtIncompatible
    public static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> cls) {
        Map<String, WeakReference<? extends Enum<?>>> mapPopulateCache;
        Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> map = enumConstantCache;
        synchronized (map) {
            mapPopulateCache = map.get(cls);
            if (mapPopulateCache == null) {
                mapPopulateCache = populateCache(cls);
            }
        }
        return mapPopulateCache;
    }

    @GwtIncompatible
    public static Field getField(Enum<?> r1) {
        try {
            return r1.getDeclaringClass().getDeclaredField(r1.name());
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> cls, String str) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(str);
        int i = Platform.$r8$clinit;
        WeakReference<? extends Enum<?>> weakReference = getEnumConstants(cls).get(str);
        return weakReference == null ? Optional.absent() : Optional.of(cls.cast(weakReference.get()));
    }

    @GwtIncompatible
    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> cls) {
        HashMap map = new HashMap();
        for (Enum r2 : EnumSet.allOf(cls)) {
            map.put(r2.name(), new WeakReference(r2));
        }
        enumConstantCache.put(cls, map);
        return map;
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> cls) {
        return new StringConverter(cls);
    }
}
