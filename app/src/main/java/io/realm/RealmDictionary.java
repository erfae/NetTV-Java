package io.realm;

import io.realm.internal.OsMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class RealmDictionary<V> extends RealmMap<String, V> {
    public RealmDictionary() {
    }

    private static <V> DictionaryManager<V> getManager(Class<V> cls, BaseRealm baseRealm, OsMap osMap) {
        MapValueOperator genericPrimitiveValueOperator;
        SelectorForMap selectorForMap = new SelectorForMap(baseRealm, osMap, cls);
        if (cls == RealmAny.class) {
            genericPrimitiveValueOperator = new RealmAnyValueOperator(baseRealm, osMap, selectorForMap);
        } else if (cls == Long.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Long.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.LONG);
        } else if (cls == Float.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Float.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.FLOAT);
        } else if (cls == Double.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Double.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.DOUBLE);
        } else if (cls == String.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(String.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.STRING);
        } else if (cls == Boolean.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Boolean.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.BOOLEAN);
        } else if (cls == Date.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Date.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.DATE);
        } else if (cls == Decimal128.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Decimal128.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.DECIMAL128);
        } else if (cls == Integer.class) {
            genericPrimitiveValueOperator = new IntegerValueOperator(baseRealm, osMap, selectorForMap);
        } else if (cls == Short.class) {
            genericPrimitiveValueOperator = new ShortValueOperator(baseRealm, osMap, selectorForMap);
        } else if (cls == Byte.class) {
            genericPrimitiveValueOperator = new ByteValueOperator(baseRealm, osMap, selectorForMap);
        } else if (cls == byte[].class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(byte[].class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.BINARY, new BinaryEquals());
        } else if (cls == ObjectId.class) {
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(ObjectId.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.OBJECT_ID);
        } else {
            if (cls != UUID.class) {
                throw new IllegalArgumentException("Only Maps of RealmAny or one of the types that can be boxed inside RealmAny can be used.");
            }
            genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(UUID.class, baseRealm, osMap, selectorForMap, RealmMapEntrySet.IteratorType.UUID);
        }
        return new DictionaryManager<>(baseRealm, genericPrimitiveValueOperator, selectorForMap);
    }

    private static <V extends RealmModel> LinkSelectorForMap<String, V> getRealmSelector(Class<V> cls, BaseRealm baseRealm, OsMap osMap) {
        return new LinkSelectorForMap<>(baseRealm, osMap, cls);
    }

    private static <V> RealmMap.ManagedMapStrategy<String, V> getStrategy(Class<V> cls, BaseRealm baseRealm, OsMap osMap) {
        if (!CollectionUtils.isClassForRealmModel(cls)) {
            return new RealmMap.ManagedMapStrategy<>(getManager(cls, baseRealm, osMap));
        }
        LinkSelectorForMap realmSelector = getRealmSelector(cls, baseRealm, osMap);
        return new RealmMap.ManagedMapStrategy<>(new DictionaryManager(baseRealm, new RealmModelValueOperator(baseRealm, osMap, realmSelector), realmSelector));
    }

    private Map<String, V> toMap() {
        HashMap map = new HashMap();
        for (Map.Entry<String, V> entry : entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public RealmDictionary(Map<String, V> map) {
        this.mapStrategy.putAll(map);
    }

    public RealmDictionary(BaseRealm baseRealm, OsMap osMap, Class<V> cls) {
        super(getStrategy(cls, baseRealm, osMap));
    }

    public RealmDictionary(BaseRealm baseRealm, OsMap osMap, String str) {
        super(getStrategy(str, baseRealm, osMap));
    }

    private static <V> RealmMap.ManagedMapStrategy<String, V> getStrategy(String str, BaseRealm baseRealm, OsMap osMap) {
        return new RealmMap.ManagedMapStrategy<>(getManager(str, baseRealm, osMap));
    }

    private static <V> DictionaryManager<V> getManager(String str, BaseRealm baseRealm, OsMap osMap) {
        MapValueOperator realmModelValueOperator;
        if (str.equals(RealmAny.class.getCanonicalName())) {
            realmModelValueOperator = new RealmAnyValueOperator(baseRealm, osMap, new SelectorForMap(baseRealm, osMap, RealmAny.class));
        } else if (str.equals(Long.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Long.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Long.class), RealmMapEntrySet.IteratorType.LONG);
        } else if (str.equals(Float.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Float.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Float.class), RealmMapEntrySet.IteratorType.FLOAT);
        } else if (str.equals(Double.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Double.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Double.class), RealmMapEntrySet.IteratorType.DOUBLE);
        } else if (str.equals(String.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(String.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, String.class), RealmMapEntrySet.IteratorType.STRING);
        } else if (str.equals(Boolean.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Boolean.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Boolean.class), RealmMapEntrySet.IteratorType.BOOLEAN);
        } else if (str.equals(Date.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Date.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Date.class), RealmMapEntrySet.IteratorType.DATE);
        } else if (str.equals(Decimal128.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Decimal128.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Decimal128.class), RealmMapEntrySet.IteratorType.DECIMAL128);
        } else if (str.equals(Integer.class.getCanonicalName())) {
            realmModelValueOperator = new IntegerValueOperator(baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Integer.class));
        } else if (str.equals(Short.class.getCanonicalName())) {
            realmModelValueOperator = new ShortValueOperator(baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Short.class));
        } else if (str.equals(Byte.class.getCanonicalName())) {
            realmModelValueOperator = new ByteValueOperator(baseRealm, osMap, new SelectorForMap(baseRealm, osMap, Byte.class));
        } else if (str.equals(byte[].class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(byte[].class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, byte[].class), RealmMapEntrySet.IteratorType.BINARY, new BinaryEquals());
        } else if (str.equals(ObjectId.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(ObjectId.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, ObjectId.class), RealmMapEntrySet.IteratorType.OBJECT_ID);
        } else if (str.equals(UUID.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(UUID.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, UUID.class), RealmMapEntrySet.IteratorType.UUID);
        } else {
            realmModelValueOperator = new RealmModelValueOperator(baseRealm, osMap, new DynamicSelectorForMap(baseRealm, osMap, str));
        }
        return new DictionaryManager<>(baseRealm, realmModelValueOperator, realmModelValueOperator.typeSelectorForMap);
    }
}
