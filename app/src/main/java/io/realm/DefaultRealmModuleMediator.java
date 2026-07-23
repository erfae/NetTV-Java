package io.realm;

import android.util.JsonReader;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.EpisodeInfoModel;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SeriesModel;
import io.realm.annotations.RealmModule;
import io.realm.exceptions.RealmException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
@RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;

    static {
        HashSet hashSet = new HashSet(5);
        hashSet.add(SeriesModel.class);
        hashSet.add(MovieModel.class);
        hashSet.add(EpisodeModel.class);
        hashSet.add(EpisodeInfoModel.class);
        hashSet.add(EPGChannel.class);
        MODEL_CLASSES = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Class<?> superclass = e instanceof RealmObjectProxy ? e.getClass().getSuperclass() : e.getClass();
        if (superclass.equals(SeriesModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_SeriesModelRealmProxy.copyOrUpdate(realm, (com_nettv_livestore_models_SeriesModelRealmProxy.SeriesModelColumnInfo) realm.getSchema().getColumnInfo(SeriesModel.class), (SeriesModel) e, z, map, set));
        }
        if (superclass.equals(MovieModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_MovieModelRealmProxy.copyOrUpdate(realm, (com_nettv_livestore_models_MovieModelRealmProxy.MovieModelColumnInfo) realm.getSchema().getColumnInfo(MovieModel.class), (MovieModel) e, z, map, set));
        }
        if (superclass.equals(EpisodeModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_EpisodeModelRealmProxy.copyOrUpdate(realm, (com_nettv_livestore_models_EpisodeModelRealmProxy.EpisodeModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeModel.class), (EpisodeModel) e, z, map, set));
        }
        if (superclass.equals(EpisodeInfoModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.copyOrUpdate(realm, (com_nettv_livestore_models_EpisodeInfoModelRealmProxy.EpisodeInfoModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeInfoModel.class), (EpisodeInfoModel) e, z, map, set));
        }
        if (superclass.equals(EPGChannel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_EPGChannelRealmProxy.copyOrUpdate(realm, (com_nettv_livestore_models_EPGChannelRealmProxy.EPGChannelColumnInfo) realm.getSchema().getColumnInfo(EPGChannel.class), (EPGChannel) e, z, map, set));
        }
        throw RealmProxyMediator.getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        Objects.requireNonNull(cls, "A class extending RealmObject must be provided");
        if (cls.equals(SeriesModel.class)) {
            return com_nettv_livestore_models_SeriesModelRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(MovieModel.class)) {
            return com_nettv_livestore_models_MovieModelRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(EpisodeModel.class)) {
            return com_nettv_livestore_models_EpisodeModelRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(EpisodeInfoModel.class)) {
            return com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(EPGChannel.class)) {
            return com_nettv_livestore_models_EPGChannelRealmProxy.createColumnInfo(osSchemaInfo);
        }
        throw RealmProxyMediator.getMissingProxyClassException(cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(SeriesModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_SeriesModelRealmProxy.createDetachedCopy((SeriesModel) e, 0, i, map));
        }
        if (superclass.equals(MovieModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_MovieModelRealmProxy.createDetachedCopy((MovieModel) e, 0, i, map));
        }
        if (superclass.equals(EpisodeModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_EpisodeModelRealmProxy.createDetachedCopy((EpisodeModel) e, 0, i, map));
        }
        if (superclass.equals(EpisodeInfoModel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createDetachedCopy((EpisodeInfoModel) e, 0, i, map));
        }
        if (superclass.equals(EPGChannel.class)) {
            return (E) superclass.cast(com_nettv_livestore_models_EPGChannelRealmProxy.createDetachedCopy((EPGChannel) e, 0, i, map));
        }
        throw RealmProxyMediator.getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        Objects.requireNonNull(cls, "A class extending RealmObject must be provided");
        if (cls.equals(SeriesModel.class)) {
            return cls.cast(com_nettv_livestore_models_SeriesModelRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(MovieModel.class)) {
            return cls.cast(com_nettv_livestore_models_MovieModelRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(EpisodeModel.class)) {
            return cls.cast(com_nettv_livestore_models_EpisodeModelRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(EpisodeInfoModel.class)) {
            return cls.cast(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(EPGChannel.class)) {
            return cls.cast(com_nettv_livestore_models_EPGChannelRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw RealmProxyMediator.getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        Objects.requireNonNull(cls, "A class extending RealmObject must be provided");
        if (cls.equals(SeriesModel.class)) {
            return cls.cast(com_nettv_livestore_models_SeriesModelRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(MovieModel.class)) {
            return cls.cast(com_nettv_livestore_models_MovieModelRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(EpisodeModel.class)) {
            return cls.cast(com_nettv_livestore_models_EpisodeModelRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(EpisodeInfoModel.class)) {
            return cls.cast(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(EPGChannel.class)) {
            return cls.cast(com_nettv_livestore_models_EPGChannelRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw RealmProxyMediator.getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Class<? extends RealmModel> getClazzImpl(String str) {
        if (str == null || str.isEmpty()) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
        if (str.equals(com_nettv_livestore_models_SeriesModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return SeriesModel.class;
        }
        if (str.equals(com_nettv_livestore_models_MovieModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return MovieModel.class;
        }
        if (str.equals(com_nettv_livestore_models_EpisodeModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return EpisodeModel.class;
        }
        if (str.equals(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return EpisodeInfoModel.class;
        }
        if (str.equals(com_nettv_livestore_models_EPGChannelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return EPGChannel.class;
        }
        throw new RealmException(String.format("'%s' is not part of the schema for this Realm.", str));
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap map = new HashMap(5);
        map.put(SeriesModel.class, com_nettv_livestore_models_SeriesModelRealmProxy.getExpectedObjectSchemaInfo());
        map.put(MovieModel.class, com_nettv_livestore_models_MovieModelRealmProxy.getExpectedObjectSchemaInfo());
        map.put(EpisodeModel.class, com_nettv_livestore_models_EpisodeModelRealmProxy.getExpectedObjectSchemaInfo());
        map.put(EpisodeInfoModel.class, com_nettv_livestore_models_EpisodeInfoModelRealmProxy.getExpectedObjectSchemaInfo());
        map.put(EPGChannel.class, com_nettv_livestore_models_EPGChannelRealmProxy.getExpectedObjectSchemaInfo());
        return map;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        Objects.requireNonNull(cls, "A class extending RealmObject must be provided");
        if (cls.equals(SeriesModel.class)) {
            return com_nettv_livestore_models_SeriesModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(MovieModel.class)) {
            return com_nettv_livestore_models_MovieModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(EpisodeModel.class)) {
            return com_nettv_livestore_models_EpisodeModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(EpisodeInfoModel.class)) {
            return com_nettv_livestore_models_EpisodeInfoModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(EPGChannel.class)) {
            return com_nettv_livestore_models_EPGChannelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        throw RealmProxyMediator.getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public boolean hasPrimaryKeyImpl(Class<? extends RealmModel> cls) {
        return false;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public long insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(SeriesModel.class)) {
            return com_nettv_livestore_models_SeriesModelRealmProxy.insert(realm, (SeriesModel) realmModel, map);
        }
        if (superclass.equals(MovieModel.class)) {
            return com_nettv_livestore_models_MovieModelRealmProxy.insert(realm, (MovieModel) realmModel, map);
        }
        if (superclass.equals(EpisodeModel.class)) {
            return com_nettv_livestore_models_EpisodeModelRealmProxy.insert(realm, (EpisodeModel) realmModel, map);
        }
        if (superclass.equals(EpisodeInfoModel.class)) {
            return com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insert(realm, (EpisodeInfoModel) realmModel, map);
        }
        if (superclass.equals(EPGChannel.class)) {
            return com_nettv_livestore_models_EPGChannelRealmProxy.insert(realm, (EPGChannel) realmModel, map);
        }
        throw RealmProxyMediator.getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public long insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(SeriesModel.class)) {
            return com_nettv_livestore_models_SeriesModelRealmProxy.insertOrUpdate(realm, (SeriesModel) realmModel, map);
        }
        if (superclass.equals(MovieModel.class)) {
            return com_nettv_livestore_models_MovieModelRealmProxy.insertOrUpdate(realm, (MovieModel) realmModel, map);
        }
        if (superclass.equals(EpisodeModel.class)) {
            return com_nettv_livestore_models_EpisodeModelRealmProxy.insertOrUpdate(realm, (EpisodeModel) realmModel, map);
        }
        if (superclass.equals(EpisodeInfoModel.class)) {
            return com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insertOrUpdate(realm, (EpisodeInfoModel) realmModel, map);
        }
        if (superclass.equals(EPGChannel.class)) {
            return com_nettv_livestore_models_EPGChannelRealmProxy.insertOrUpdate(realm, (EPGChannel) realmModel, map);
        }
        throw RealmProxyMediator.getMissingProxyClassException(superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> boolean isEmbedded(Class<E> cls) {
        if (cls.equals(SeriesModel.class) || cls.equals(MovieModel.class) || cls.equals(EpisodeModel.class) || cls.equals(EpisodeInfoModel.class) || cls.equals(EPGChannel.class)) {
            return false;
        }
        throw RealmProxyMediator.getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            Objects.requireNonNull(cls, "A class extending RealmObject must be provided");
            if (cls.equals(SeriesModel.class)) {
                E eCast = cls.cast(new com_nettv_livestore_models_SeriesModelRealmProxy());
                realmObjectContext.clear();
                return eCast;
            }
            if (cls.equals(MovieModel.class)) {
                E eCast2 = cls.cast(new com_nettv_livestore_models_MovieModelRealmProxy());
                realmObjectContext.clear();
                return eCast2;
            }
            if (cls.equals(EpisodeModel.class)) {
                E eCast3 = cls.cast(new com_nettv_livestore_models_EpisodeModelRealmProxy());
                realmObjectContext.clear();
                return eCast3;
            }
            if (cls.equals(EpisodeInfoModel.class)) {
                E eCast4 = cls.cast(new com_nettv_livestore_models_EpisodeInfoModelRealmProxy());
                realmObjectContext.clear();
                return eCast4;
            }
            if (!cls.equals(EPGChannel.class)) {
                throw RealmProxyMediator.getMissingProxyClassException(cls);
            }
            E eCast5 = cls.cast(new com_nettv_livestore_models_EPGChannelRealmProxy());
            realmObjectContext.clear();
            return eCast5;
        } catch (Throwable th) {
            realmObjectContext.clear();
            throw th;
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> void updateEmbeddedObject(Realm realm, E e, E e2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Class<? super Object> superclass = e2.getClass().getSuperclass();
        if (superclass.equals(SeriesModel.class)) {
            throw RealmProxyMediator.getNotEmbeddedClassException("com.nettv.livestore.models.SeriesModel");
        }
        if (superclass.equals(MovieModel.class)) {
            throw RealmProxyMediator.getNotEmbeddedClassException("com.nettv.livestore.models.MovieModel");
        }
        if (superclass.equals(EpisodeModel.class)) {
            throw RealmProxyMediator.getNotEmbeddedClassException("com.nettv.livestore.models.EpisodeModel");
        }
        if (superclass.equals(EpisodeInfoModel.class)) {
            throw RealmProxyMediator.getNotEmbeddedClassException("com.nettv.livestore.models.EpisodeInfoModel");
        }
        if (!superclass.equals(EPGChannel.class)) {
            throw RealmProxyMediator.getMissingProxyClassException(superclass);
        }
        throw RealmProxyMediator.getNotEmbeddedClassException("com.nettv.livestore.models.EPGChannel");
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap map = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel next = it.next();
            Class<?> superclass = next instanceof RealmObjectProxy ? next.getClass().getSuperclass() : next.getClass();
            if (superclass.equals(SeriesModel.class)) {
                com_nettv_livestore_models_SeriesModelRealmProxy.insert(realm, (SeriesModel) next, map);
            } else if (superclass.equals(MovieModel.class)) {
                com_nettv_livestore_models_MovieModelRealmProxy.insert(realm, (MovieModel) next, map);
            } else if (superclass.equals(EpisodeModel.class)) {
                com_nettv_livestore_models_EpisodeModelRealmProxy.insert(realm, (EpisodeModel) next, map);
            } else if (superclass.equals(EpisodeInfoModel.class)) {
                com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insert(realm, (EpisodeInfoModel) next, map);
            } else if (superclass.equals(EPGChannel.class)) {
                com_nettv_livestore_models_EPGChannelRealmProxy.insert(realm, (EPGChannel) next, map);
            } else {
                throw RealmProxyMediator.getMissingProxyClassException(superclass);
            }
            if (it.hasNext()) {
                if (superclass.equals(SeriesModel.class)) {
                    com_nettv_livestore_models_SeriesModelRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(MovieModel.class)) {
                    com_nettv_livestore_models_MovieModelRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(EpisodeModel.class)) {
                    com_nettv_livestore_models_EpisodeModelRealmProxy.insert(realm, it, map);
                } else if (superclass.equals(EpisodeInfoModel.class)) {
                    com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insert(realm, it, map);
                } else {
                    if (superclass.equals(EPGChannel.class)) {
                        com_nettv_livestore_models_EPGChannelRealmProxy.insert(realm, it, map);
                        return;
                    }
                    throw RealmProxyMediator.getMissingProxyClassException(superclass);
                }
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap map = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel next = it.next();
            Class<?> superclass = next instanceof RealmObjectProxy ? next.getClass().getSuperclass() : next.getClass();
            if (superclass.equals(SeriesModel.class)) {
                com_nettv_livestore_models_SeriesModelRealmProxy.insertOrUpdate(realm, (SeriesModel) next, map);
            } else if (superclass.equals(MovieModel.class)) {
                com_nettv_livestore_models_MovieModelRealmProxy.insertOrUpdate(realm, (MovieModel) next, map);
            } else if (superclass.equals(EpisodeModel.class)) {
                com_nettv_livestore_models_EpisodeModelRealmProxy.insertOrUpdate(realm, (EpisodeModel) next, map);
            } else if (superclass.equals(EpisodeInfoModel.class)) {
                com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insertOrUpdate(realm, (EpisodeInfoModel) next, map);
            } else if (superclass.equals(EPGChannel.class)) {
                com_nettv_livestore_models_EPGChannelRealmProxy.insertOrUpdate(realm, (EPGChannel) next, map);
            } else {
                throw RealmProxyMediator.getMissingProxyClassException(superclass);
            }
            if (it.hasNext()) {
                if (superclass.equals(SeriesModel.class)) {
                    com_nettv_livestore_models_SeriesModelRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(MovieModel.class)) {
                    com_nettv_livestore_models_MovieModelRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(EpisodeModel.class)) {
                    com_nettv_livestore_models_EpisodeModelRealmProxy.insertOrUpdate(realm, it, map);
                } else if (superclass.equals(EpisodeInfoModel.class)) {
                    com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insertOrUpdate(realm, it, map);
                } else {
                    if (superclass.equals(EPGChannel.class)) {
                        com_nettv_livestore_models_EPGChannelRealmProxy.insertOrUpdate(realm, it, map);
                        return;
                    }
                    throw RealmProxyMediator.getMissingProxyClassException(superclass);
                }
            }
        }
    }
}
