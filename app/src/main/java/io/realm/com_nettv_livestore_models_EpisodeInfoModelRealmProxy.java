package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.models.EpisodeInfoModel;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class com_nettv_livestore_models_EpisodeInfoModelRealmProxy extends EpisodeInfoModel implements RealmObjectProxy {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private EpisodeInfoModelColumnInfo columnInfo;
    private ProxyState<EpisodeInfoModel> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "EpisodeInfoModel";
    }

    public static final class EpisodeInfoModelColumnInfo extends ColumnInfo {
        public long bitrateColKey;
        public long durationColKey;
        public long duration_secsColKey;
        public long movie_imageColKey;
        public long nameColKey;
        public long plotColKey;
        public long ratingColKey;
        public long releasedateColKey;
        public long tmdb_idColKey;

        public EpisodeInfoModelColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(9);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.bitrateColKey = addColumnDetails("bitrate", "bitrate", objectSchemaInfo);
            this.durationColKey = addColumnDetails(TypedValues.TransitionType.S_DURATION, TypedValues.TransitionType.S_DURATION, objectSchemaInfo);
            this.duration_secsColKey = addColumnDetails("duration_secs", "duration_secs", objectSchemaInfo);
            this.nameColKey = addColumnDetails("name", "name", objectSchemaInfo);
            this.ratingColKey = addColumnDetails("rating", "rating", objectSchemaInfo);
            this.releasedateColKey = addColumnDetails("releasedate", "releasedate", objectSchemaInfo);
            this.plotColKey = addColumnDetails("plot", "plot", objectSchemaInfo);
            this.movie_imageColKey = addColumnDetails("movie_image", "movie_image", objectSchemaInfo);
            this.tmdb_idColKey = addColumnDetails("tmdb_id", "tmdb_id", objectSchemaInfo);
        }

        @Override // io.realm.internal.ColumnInfo
        public final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo = (EpisodeInfoModelColumnInfo) columnInfo;
            EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo2 = (EpisodeInfoModelColumnInfo) columnInfo2;
            episodeInfoModelColumnInfo2.bitrateColKey = episodeInfoModelColumnInfo.bitrateColKey;
            episodeInfoModelColumnInfo2.durationColKey = episodeInfoModelColumnInfo.durationColKey;
            episodeInfoModelColumnInfo2.duration_secsColKey = episodeInfoModelColumnInfo.duration_secsColKey;
            episodeInfoModelColumnInfo2.nameColKey = episodeInfoModelColumnInfo.nameColKey;
            episodeInfoModelColumnInfo2.ratingColKey = episodeInfoModelColumnInfo.ratingColKey;
            episodeInfoModelColumnInfo2.releasedateColKey = episodeInfoModelColumnInfo.releasedateColKey;
            episodeInfoModelColumnInfo2.plotColKey = episodeInfoModelColumnInfo.plotColKey;
            episodeInfoModelColumnInfo2.movie_imageColKey = episodeInfoModelColumnInfo.movie_imageColKey;
            episodeInfoModelColumnInfo2.tmdb_idColKey = episodeInfoModelColumnInfo.tmdb_idColKey;
        }
    }

    public com_nettv_livestore_models_EpisodeInfoModelRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static EpisodeInfoModel copy(Realm realm, EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo, EpisodeInfoModel episodeInfoModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(episodeInfoModel);
        if (realmObjectProxy != null) {
            return (EpisodeInfoModel) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(EpisodeInfoModel.class), set);
        osObjectBuilder.addInteger(episodeInfoModelColumnInfo.bitrateColKey, Integer.valueOf(episodeInfoModel.realmGet$bitrate()));
        osObjectBuilder.addString(episodeInfoModelColumnInfo.durationColKey, episodeInfoModel.realmGet$duration());
        osObjectBuilder.addInteger(episodeInfoModelColumnInfo.duration_secsColKey, Integer.valueOf(episodeInfoModel.realmGet$duration_secs()));
        osObjectBuilder.addString(episodeInfoModelColumnInfo.nameColKey, episodeInfoModel.realmGet$name());
        osObjectBuilder.addString(episodeInfoModelColumnInfo.ratingColKey, episodeInfoModel.realmGet$rating());
        osObjectBuilder.addString(episodeInfoModelColumnInfo.releasedateColKey, episodeInfoModel.realmGet$releasedate());
        osObjectBuilder.addString(episodeInfoModelColumnInfo.plotColKey, episodeInfoModel.realmGet$plot());
        osObjectBuilder.addString(episodeInfoModelColumnInfo.movie_imageColKey, episodeInfoModel.realmGet$movie_image());
        osObjectBuilder.addString(episodeInfoModelColumnInfo.tmdb_idColKey, episodeInfoModel.realmGet$tmdb_id());
        UncheckedRow uncheckedRowCreateNewObject = osObjectBuilder.createNewObject();
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(realm, uncheckedRowCreateNewObject, realm.getSchema().getColumnInfo(EpisodeInfoModel.class), false, Collections.emptyList());
        com_nettv_livestore_models_EpisodeInfoModelRealmProxy com_nettv_livestore_models_episodeinfomodelrealmproxy = new com_nettv_livestore_models_EpisodeInfoModelRealmProxy();
        realmObjectContext.clear();
        map.put(episodeInfoModel, com_nettv_livestore_models_episodeinfomodelrealmproxy);
        return com_nettv_livestore_models_episodeinfomodelrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EpisodeInfoModel copyOrUpdate(Realm realm, EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo, EpisodeInfoModel episodeInfoModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((episodeInfoModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeInfoModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeInfoModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return episodeInfoModel;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(episodeInfoModel);
        return realmModel != null ? (EpisodeInfoModel) realmModel : copy(realm, episodeInfoModelColumnInfo, episodeInfoModel, z, map, set);
    }

    public static EpisodeInfoModelColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new EpisodeInfoModelColumnInfo(osSchemaInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EpisodeInfoModel createDetachedCopy(EpisodeInfoModel episodeInfoModel, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        EpisodeInfoModel episodeInfoModel2;
        if (i > i2 || episodeInfoModel == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(episodeInfoModel);
        if (cacheData == null) {
            episodeInfoModel2 = new EpisodeInfoModel();
            map.put(episodeInfoModel, new RealmObjectProxy.CacheData<>(i, episodeInfoModel2));
        } else {
            if (i >= cacheData.minDepth) {
                return (EpisodeInfoModel) cacheData.object;
            }
            EpisodeInfoModel episodeInfoModel3 = (EpisodeInfoModel) cacheData.object;
            cacheData.minDepth = i;
            episodeInfoModel2 = episodeInfoModel3;
        }
        episodeInfoModel2.realmSet$bitrate(episodeInfoModel.realmGet$bitrate());
        episodeInfoModel2.realmSet$duration(episodeInfoModel.realmGet$duration());
        episodeInfoModel2.realmSet$duration_secs(episodeInfoModel.realmGet$duration_secs());
        episodeInfoModel2.realmSet$name(episodeInfoModel.realmGet$name());
        episodeInfoModel2.realmSet$rating(episodeInfoModel.realmGet$rating());
        episodeInfoModel2.realmSet$releasedate(episodeInfoModel.realmGet$releasedate());
        episodeInfoModel2.realmSet$plot(episodeInfoModel.realmGet$plot());
        episodeInfoModel2.realmSet$movie_image(episodeInfoModel.realmGet$movie_image());
        episodeInfoModel2.realmSet$tmdb_id(episodeInfoModel.realmGet$tmdb_id());
        return episodeInfoModel2;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 9, 0);
        RealmFieldType realmFieldType = RealmFieldType.INTEGER;
        builder.addPersistedProperty("", "bitrate", realmFieldType, false, false, true);
        RealmFieldType realmFieldType2 = RealmFieldType.STRING;
        builder.addPersistedProperty("", TypedValues.TransitionType.S_DURATION, realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "duration_secs", realmFieldType, false, false, true);
        builder.addPersistedProperty("", "name", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "rating", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "releasedate", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "plot", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "movie_image", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "tmdb_id", realmFieldType2, false, false, false);
        return builder.build();
    }

    public static EpisodeInfoModel createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        EpisodeInfoModel episodeInfoModel = (EpisodeInfoModel) realm.createObjectInternal(EpisodeInfoModel.class, Collections.emptyList());
        if (jSONObject.has("bitrate")) {
            if (jSONObject.isNull("bitrate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'bitrate' to null.");
            }
            episodeInfoModel.realmSet$bitrate(jSONObject.getInt("bitrate"));
        }
        if (jSONObject.has(TypedValues.TransitionType.S_DURATION)) {
            if (jSONObject.isNull(TypedValues.TransitionType.S_DURATION)) {
                episodeInfoModel.realmSet$duration(null);
            } else {
                episodeInfoModel.realmSet$duration(jSONObject.getString(TypedValues.TransitionType.S_DURATION));
            }
        }
        if (jSONObject.has("duration_secs")) {
            if (jSONObject.isNull("duration_secs")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'duration_secs' to null.");
            }
            episodeInfoModel.realmSet$duration_secs(jSONObject.getInt("duration_secs"));
        }
        if (jSONObject.has("name")) {
            if (jSONObject.isNull("name")) {
                episodeInfoModel.realmSet$name(null);
            } else {
                episodeInfoModel.realmSet$name(jSONObject.getString("name"));
            }
        }
        if (jSONObject.has("rating")) {
            if (jSONObject.isNull("rating")) {
                episodeInfoModel.realmSet$rating(null);
            } else {
                episodeInfoModel.realmSet$rating(jSONObject.getString("rating"));
            }
        }
        if (jSONObject.has("releasedate")) {
            if (jSONObject.isNull("releasedate")) {
                episodeInfoModel.realmSet$releasedate(null);
            } else {
                episodeInfoModel.realmSet$releasedate(jSONObject.getString("releasedate"));
            }
        }
        if (jSONObject.has("plot")) {
            if (jSONObject.isNull("plot")) {
                episodeInfoModel.realmSet$plot(null);
            } else {
                episodeInfoModel.realmSet$plot(jSONObject.getString("plot"));
            }
        }
        if (jSONObject.has("movie_image")) {
            if (jSONObject.isNull("movie_image")) {
                episodeInfoModel.realmSet$movie_image(null);
            } else {
                episodeInfoModel.realmSet$movie_image(jSONObject.getString("movie_image"));
            }
        }
        if (jSONObject.has("tmdb_id")) {
            if (jSONObject.isNull("tmdb_id")) {
                episodeInfoModel.realmSet$tmdb_id(null);
            } else {
                episodeInfoModel.realmSet$tmdb_id(jSONObject.getString("tmdb_id"));
            }
        }
        return episodeInfoModel;
    }

    @TargetApi(11)
    public static EpisodeInfoModel createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        EpisodeInfoModel episodeInfoModel = new EpisodeInfoModel();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("bitrate")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'bitrate' to null.");
                }
                episodeInfoModel.realmSet$bitrate(jsonReader.nextInt());
            } else if (strNextName.equals(TypedValues.TransitionType.S_DURATION)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeInfoModel.realmSet$duration(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeInfoModel.realmSet$duration(null);
                }
            } else if (strNextName.equals("duration_secs")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'duration_secs' to null.");
                }
                episodeInfoModel.realmSet$duration_secs(jsonReader.nextInt());
            } else if (strNextName.equals("name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeInfoModel.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeInfoModel.realmSet$name(null);
                }
            } else if (strNextName.equals("rating")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeInfoModel.realmSet$rating(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeInfoModel.realmSet$rating(null);
                }
            } else if (strNextName.equals("releasedate")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeInfoModel.realmSet$releasedate(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeInfoModel.realmSet$releasedate(null);
                }
            } else if (strNextName.equals("plot")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeInfoModel.realmSet$plot(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeInfoModel.realmSet$plot(null);
                }
            } else if (strNextName.equals("movie_image")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeInfoModel.realmSet$movie_image(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeInfoModel.realmSet$movie_image(null);
                }
            } else if (!strNextName.equals("tmdb_id")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                episodeInfoModel.realmSet$tmdb_id(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                episodeInfoModel.realmSet$tmdb_id(null);
            }
        }
        jsonReader.endObject();
        return (EpisodeInfoModel) realm.copyToRealm(episodeInfoModel, new ImportFlag[0]);
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, EpisodeInfoModel episodeInfoModel, Map<RealmModel, Long> map) {
        if ((episodeInfoModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeInfoModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeInfoModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(EpisodeInfoModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo = (EpisodeInfoModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeInfoModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(episodeInfoModel, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.bitrateColKey, jCreateRow, episodeInfoModel.realmGet$bitrate(), false);
        String strRealmGet$duration = episodeInfoModel.realmGet$duration();
        if (strRealmGet$duration != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.durationColKey, jCreateRow, strRealmGet$duration, false);
        }
        Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.duration_secsColKey, jCreateRow, episodeInfoModel.realmGet$duration_secs(), false);
        String strRealmGet$name = episodeInfoModel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        }
        String strRealmGet$rating = episodeInfoModel.realmGet$rating();
        if (strRealmGet$rating != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
        }
        String strRealmGet$releasedate = episodeInfoModel.realmGet$releasedate();
        if (strRealmGet$releasedate != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.releasedateColKey, jCreateRow, strRealmGet$releasedate, false);
        }
        String strRealmGet$plot = episodeInfoModel.realmGet$plot();
        if (strRealmGet$plot != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
        }
        String strRealmGet$movie_image = episodeInfoModel.realmGet$movie_image();
        if (strRealmGet$movie_image != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.movie_imageColKey, jCreateRow, strRealmGet$movie_image, false);
        }
        String strRealmGet$tmdb_id = episodeInfoModel.realmGet$tmdb_id();
        if (strRealmGet$tmdb_id != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
        }
        return jCreateRow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, EpisodeInfoModel episodeInfoModel, Map<RealmModel, Long> map) {
        if ((episodeInfoModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeInfoModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeInfoModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(EpisodeInfoModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo = (EpisodeInfoModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeInfoModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(episodeInfoModel, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.bitrateColKey, jCreateRow, episodeInfoModel.realmGet$bitrate(), false);
        String strRealmGet$duration = episodeInfoModel.realmGet$duration();
        if (strRealmGet$duration != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.durationColKey, jCreateRow, strRealmGet$duration, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.durationColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.duration_secsColKey, jCreateRow, episodeInfoModel.realmGet$duration_secs(), false);
        String strRealmGet$name = episodeInfoModel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.nameColKey, jCreateRow, false);
        }
        String strRealmGet$rating = episodeInfoModel.realmGet$rating();
        if (strRealmGet$rating != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.ratingColKey, jCreateRow, false);
        }
        String strRealmGet$releasedate = episodeInfoModel.realmGet$releasedate();
        if (strRealmGet$releasedate != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.releasedateColKey, jCreateRow, strRealmGet$releasedate, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.releasedateColKey, jCreateRow, false);
        }
        String strRealmGet$plot = episodeInfoModel.realmGet$plot();
        if (strRealmGet$plot != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.plotColKey, jCreateRow, false);
        }
        String strRealmGet$movie_image = episodeInfoModel.realmGet$movie_image();
        if (strRealmGet$movie_image != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.movie_imageColKey, jCreateRow, strRealmGet$movie_image, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.movie_imageColKey, jCreateRow, false);
        }
        String strRealmGet$tmdb_id = episodeInfoModel.realmGet$tmdb_id();
        if (strRealmGet$tmdb_id != null) {
            Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.tmdb_idColKey, jCreateRow, false);
        }
        return jCreateRow;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com_nettv_livestore_models_EpisodeInfoModelRealmProxy com_nettv_livestore_models_episodeinfomodelrealmproxy = (com_nettv_livestore_models_EpisodeInfoModelRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_nettv_livestore_models_episodeinfomodelrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_nettv_livestore_models_episodeinfomodelrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_nettv_livestore_models_episodeinfomodelrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }

    public int hashCode() {
        String path = this.proxyState.getRealm$realm().getPath();
        String name = this.proxyState.getRow$realm().getTable().getName();
        long objectKey = this.proxyState.getRow$realm().getObjectKey();
        return ((((527 + (path != null ? path.hashCode() : 0)) * 31) + (name != null ? name.hashCode() : 0)) * 31) + ((int) ((objectKey >>> 32) ^ objectKey));
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (EpisodeInfoModelColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<EpisodeInfoModel> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public int realmGet$bitrate() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.bitrateColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$duration() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.durationColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public int realmGet$duration_secs() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.duration_secsColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$movie_image() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.movie_imageColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.nameColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$plot() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.plotColKey);
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$rating() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.ratingColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$releasedate() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.releasedateColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$tmdb_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tmdb_idColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$bitrate(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.bitrateColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.bitrateColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$duration(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.durationColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.durationColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.durationColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.durationColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$duration_secs(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.duration_secsColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.duration_secsColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$movie_image(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.movie_imageColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.movie_imageColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.movie_imageColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.movie_imageColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$name(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.nameColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.nameColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.nameColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.nameColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$plot(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.plotColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.plotColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.plotColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.plotColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$rating(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.ratingColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.ratingColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.ratingColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.ratingColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$releasedate(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.releasedateColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.releasedateColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.releasedateColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.releasedateColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeInfoModel, io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$tmdb_id(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.tmdb_idColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.tmdb_idColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.tmdb_idColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.tmdb_idColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("EpisodeInfoModel = proxy[");
        sb.append("{bitrate:");
        sb.append(realmGet$bitrate());
        sb.append("}");
        sb.append(",");
        sb.append("{duration:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$duration() != null ? realmGet$duration() : "null", "}", ",", "{duration_secs:");
        sb.append(realmGet$duration_secs());
        sb.append("}");
        sb.append(",");
        sb.append("{name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$name() != null ? realmGet$name() : "null", "}", ",", "{rating:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$rating() != null ? realmGet$rating() : "null", "}", ",", "{releasedate:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$releasedate() != null ? realmGet$releasedate() : "null", "}", ",", "{plot:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$plot() != null ? realmGet$plot() : "null", "}", ",", "{movie_image:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$movie_image() != null ? realmGet$movie_image() : "null", "}", ",", "{tmdb_id:");
        return Insets$$ExternalSyntheticOutline0.m(sb, realmGet$tmdb_id() != null ? realmGet$tmdb_id() : "null", "}", "]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(EpisodeInfoModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo = (EpisodeInfoModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeInfoModel.class);
        while (it.hasNext()) {
            EpisodeInfoModel episodeInfoModel = (EpisodeInfoModel) it.next();
            if (!map.containsKey(episodeInfoModel)) {
                if ((episodeInfoModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeInfoModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeInfoModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(episodeInfoModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(episodeInfoModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.bitrateColKey, jCreateRow, episodeInfoModel.realmGet$bitrate(), false);
                String strRealmGet$duration = episodeInfoModel.realmGet$duration();
                if (strRealmGet$duration != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.durationColKey, jCreateRow, strRealmGet$duration, false);
                }
                Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.duration_secsColKey, jCreateRow, episodeInfoModel.realmGet$duration_secs(), false);
                String strRealmGet$name = episodeInfoModel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                }
                String strRealmGet$rating = episodeInfoModel.realmGet$rating();
                if (strRealmGet$rating != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
                }
                String strRealmGet$releasedate = episodeInfoModel.realmGet$releasedate();
                if (strRealmGet$releasedate != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.releasedateColKey, jCreateRow, strRealmGet$releasedate, false);
                }
                String strRealmGet$plot = episodeInfoModel.realmGet$plot();
                if (strRealmGet$plot != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
                }
                String strRealmGet$movie_image = episodeInfoModel.realmGet$movie_image();
                if (strRealmGet$movie_image != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.movie_imageColKey, jCreateRow, strRealmGet$movie_image, false);
                }
                String strRealmGet$tmdb_id = episodeInfoModel.realmGet$tmdb_id();
                if (strRealmGet$tmdb_id != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(EpisodeInfoModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeInfoModelColumnInfo episodeInfoModelColumnInfo = (EpisodeInfoModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeInfoModel.class);
        while (it.hasNext()) {
            EpisodeInfoModel episodeInfoModel = (EpisodeInfoModel) it.next();
            if (!map.containsKey(episodeInfoModel)) {
                if ((episodeInfoModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeInfoModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeInfoModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(episodeInfoModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(episodeInfoModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.bitrateColKey, jCreateRow, episodeInfoModel.realmGet$bitrate(), false);
                String strRealmGet$duration = episodeInfoModel.realmGet$duration();
                if (strRealmGet$duration != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.durationColKey, jCreateRow, strRealmGet$duration, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.durationColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, episodeInfoModelColumnInfo.duration_secsColKey, jCreateRow, episodeInfoModel.realmGet$duration_secs(), false);
                String strRealmGet$name = episodeInfoModel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.nameColKey, jCreateRow, false);
                }
                String strRealmGet$rating = episodeInfoModel.realmGet$rating();
                if (strRealmGet$rating != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.ratingColKey, jCreateRow, false);
                }
                String strRealmGet$releasedate = episodeInfoModel.realmGet$releasedate();
                if (strRealmGet$releasedate != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.releasedateColKey, jCreateRow, strRealmGet$releasedate, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.releasedateColKey, jCreateRow, false);
                }
                String strRealmGet$plot = episodeInfoModel.realmGet$plot();
                if (strRealmGet$plot != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.plotColKey, jCreateRow, false);
                }
                String strRealmGet$movie_image = episodeInfoModel.realmGet$movie_image();
                if (strRealmGet$movie_image != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.movie_imageColKey, jCreateRow, strRealmGet$movie_image, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.movie_imageColKey, jCreateRow, false);
                }
                String strRealmGet$tmdb_id = episodeInfoModel.realmGet$tmdb_id();
                if (strRealmGet$tmdb_id != null) {
                    Table.nativeSetString(nativePtr, episodeInfoModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeInfoModelColumnInfo.tmdb_idColKey, jCreateRow, false);
                }
            }
        }
    }
}
