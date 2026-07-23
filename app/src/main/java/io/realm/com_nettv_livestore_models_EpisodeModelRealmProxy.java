package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.models.EpisodeInfoModel;
import com.nettv.livestore.models.EpisodeModel;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class com_nettv_livestore_models_EpisodeModelRealmProxy extends EpisodeModel implements RealmObjectProxy {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private EpisodeModelColumnInfo columnInfo;
    private ProxyState<EpisodeModel> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "EpisodeModel";
    }

    public static final class EpisodeModelColumnInfo extends ColumnInfo {
        public long category_nameColKey;
        public long container_extensionColKey;
        public long episode_numColKey;
        public long idColKey;
        public long infoColKey;
        public long seasonColKey;
        public long season_nameColKey;
        public long series_nameColKey;
        public long stream_iconColKey;
        public long titleColKey;
        public long urlColKey;

        public EpisodeModelColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(11);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.idColKey = addColumnDetails(TtmlNode.ATTR_ID, TtmlNode.ATTR_ID, objectSchemaInfo);
            this.episode_numColKey = addColumnDetails("episode_num", "episode_num", objectSchemaInfo);
            this.titleColKey = addColumnDetails("title", "title", objectSchemaInfo);
            this.container_extensionColKey = addColumnDetails("container_extension", "container_extension", objectSchemaInfo);
            this.seasonColKey = addColumnDetails("season", "season", objectSchemaInfo);
            this.infoColKey = addColumnDetails("info", "info", objectSchemaInfo);
            this.category_nameColKey = addColumnDetails("category_name", "category_name", objectSchemaInfo);
            this.stream_iconColKey = addColumnDetails("stream_icon", "stream_icon", objectSchemaInfo);
            this.season_nameColKey = addColumnDetails("season_name", "season_name", objectSchemaInfo);
            this.series_nameColKey = addColumnDetails("series_name", "series_name", objectSchemaInfo);
            this.urlColKey = addColumnDetails(ImagesContract.URL, ImagesContract.URL, objectSchemaInfo);
        }

        @Override // io.realm.internal.ColumnInfo
        public final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            EpisodeModelColumnInfo episodeModelColumnInfo = (EpisodeModelColumnInfo) columnInfo;
            EpisodeModelColumnInfo episodeModelColumnInfo2 = (EpisodeModelColumnInfo) columnInfo2;
            episodeModelColumnInfo2.idColKey = episodeModelColumnInfo.idColKey;
            episodeModelColumnInfo2.episode_numColKey = episodeModelColumnInfo.episode_numColKey;
            episodeModelColumnInfo2.titleColKey = episodeModelColumnInfo.titleColKey;
            episodeModelColumnInfo2.container_extensionColKey = episodeModelColumnInfo.container_extensionColKey;
            episodeModelColumnInfo2.seasonColKey = episodeModelColumnInfo.seasonColKey;
            episodeModelColumnInfo2.infoColKey = episodeModelColumnInfo.infoColKey;
            episodeModelColumnInfo2.category_nameColKey = episodeModelColumnInfo.category_nameColKey;
            episodeModelColumnInfo2.stream_iconColKey = episodeModelColumnInfo.stream_iconColKey;
            episodeModelColumnInfo2.season_nameColKey = episodeModelColumnInfo.season_nameColKey;
            episodeModelColumnInfo2.series_nameColKey = episodeModelColumnInfo.series_nameColKey;
            episodeModelColumnInfo2.urlColKey = episodeModelColumnInfo.urlColKey;
        }
    }

    public com_nettv_livestore_models_EpisodeModelRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static EpisodeModel copy(Realm realm, EpisodeModelColumnInfo episodeModelColumnInfo, EpisodeModel episodeModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(episodeModel);
        if (realmObjectProxy != null) {
            return (EpisodeModel) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(EpisodeModel.class), set);
        osObjectBuilder.addString(episodeModelColumnInfo.idColKey, episodeModel.realmGet$id());
        osObjectBuilder.addString(episodeModelColumnInfo.episode_numColKey, episodeModel.realmGet$episode_num());
        osObjectBuilder.addString(episodeModelColumnInfo.titleColKey, episodeModel.realmGet$title());
        osObjectBuilder.addString(episodeModelColumnInfo.container_extensionColKey, episodeModel.realmGet$container_extension());
        osObjectBuilder.addInteger(episodeModelColumnInfo.seasonColKey, Integer.valueOf(episodeModel.realmGet$season()));
        osObjectBuilder.addString(episodeModelColumnInfo.category_nameColKey, episodeModel.realmGet$category_name());
        osObjectBuilder.addString(episodeModelColumnInfo.stream_iconColKey, episodeModel.realmGet$stream_icon());
        osObjectBuilder.addString(episodeModelColumnInfo.season_nameColKey, episodeModel.realmGet$season_name());
        osObjectBuilder.addString(episodeModelColumnInfo.series_nameColKey, episodeModel.realmGet$series_name());
        osObjectBuilder.addString(episodeModelColumnInfo.urlColKey, episodeModel.realmGet$url());
        UncheckedRow uncheckedRowCreateNewObject = osObjectBuilder.createNewObject();
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(realm, uncheckedRowCreateNewObject, realm.getSchema().getColumnInfo(EpisodeModel.class), false, Collections.emptyList());
        com_nettv_livestore_models_EpisodeModelRealmProxy com_nettv_livestore_models_episodemodelrealmproxy = new com_nettv_livestore_models_EpisodeModelRealmProxy();
        realmObjectContext.clear();
        map.put(episodeModel, com_nettv_livestore_models_episodemodelrealmproxy);
        EpisodeInfoModel episodeInfoModelRealmGet$info = episodeModel.realmGet$info();
        if (episodeInfoModelRealmGet$info == null) {
            com_nettv_livestore_models_episodemodelrealmproxy.realmSet$info(null);
        } else {
            EpisodeInfoModel episodeInfoModel = (EpisodeInfoModel) map.get(episodeInfoModelRealmGet$info);
            if (episodeInfoModel != null) {
                com_nettv_livestore_models_episodemodelrealmproxy.realmSet$info(episodeInfoModel);
            } else {
                com_nettv_livestore_models_episodemodelrealmproxy.realmSet$info(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.copyOrUpdate(realm, (com_nettv_livestore_models_EpisodeInfoModelRealmProxy.EpisodeInfoModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeInfoModel.class), episodeInfoModelRealmGet$info, z, map, set));
            }
        }
        return com_nettv_livestore_models_episodemodelrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EpisodeModel copyOrUpdate(Realm realm, EpisodeModelColumnInfo episodeModelColumnInfo, EpisodeModel episodeModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((episodeModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return episodeModel;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(episodeModel);
        return realmModel != null ? (EpisodeModel) realmModel : copy(realm, episodeModelColumnInfo, episodeModel, z, map, set);
    }

    public static EpisodeModelColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new EpisodeModelColumnInfo(osSchemaInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EpisodeModel createDetachedCopy(EpisodeModel episodeModel, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        EpisodeModel episodeModel2;
        if (i > i2 || episodeModel == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(episodeModel);
        if (cacheData == null) {
            episodeModel2 = new EpisodeModel();
            map.put(episodeModel, new RealmObjectProxy.CacheData<>(i, episodeModel2));
        } else {
            if (i >= cacheData.minDepth) {
                return (EpisodeModel) cacheData.object;
            }
            EpisodeModel episodeModel3 = (EpisodeModel) cacheData.object;
            cacheData.minDepth = i;
            episodeModel2 = episodeModel3;
        }
        episodeModel2.realmSet$id(episodeModel.realmGet$id());
        episodeModel2.realmSet$episode_num(episodeModel.realmGet$episode_num());
        episodeModel2.realmSet$title(episodeModel.realmGet$title());
        episodeModel2.realmSet$container_extension(episodeModel.realmGet$container_extension());
        episodeModel2.realmSet$season(episodeModel.realmGet$season());
        episodeModel2.realmSet$info(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createDetachedCopy(episodeModel.realmGet$info(), i + 1, i2, map));
        episodeModel2.realmSet$category_name(episodeModel.realmGet$category_name());
        episodeModel2.realmSet$stream_icon(episodeModel.realmGet$stream_icon());
        episodeModel2.realmSet$season_name(episodeModel.realmGet$season_name());
        episodeModel2.realmSet$series_name(episodeModel.realmGet$series_name());
        episodeModel2.realmSet$url(episodeModel.realmGet$url());
        return episodeModel2;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 11, 0);
        RealmFieldType realmFieldType = RealmFieldType.STRING;
        builder.addPersistedProperty("", TtmlNode.ATTR_ID, realmFieldType, false, false, false);
        builder.addPersistedProperty("", "episode_num", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "title", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "container_extension", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "season", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedLinkProperty("", "info", RealmFieldType.OBJECT, com_nettv_livestore_models_EpisodeInfoModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        builder.addPersistedProperty("", "category_name", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "stream_icon", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "season_name", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "series_name", realmFieldType, false, false, false);
        builder.addPersistedProperty("", ImagesContract.URL, realmFieldType, false, false, false);
        return builder.build();
    }

    public static EpisodeModel createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        ArrayList arrayList = new ArrayList(1);
        if (jSONObject.has("info")) {
            arrayList.add("info");
        }
        EpisodeModel episodeModel = (EpisodeModel) realm.createObjectInternal(EpisodeModel.class, arrayList);
        if (jSONObject.has(TtmlNode.ATTR_ID)) {
            if (jSONObject.isNull(TtmlNode.ATTR_ID)) {
                episodeModel.realmSet$id(null);
            } else {
                episodeModel.realmSet$id(jSONObject.getString(TtmlNode.ATTR_ID));
            }
        }
        if (jSONObject.has("episode_num")) {
            if (jSONObject.isNull("episode_num")) {
                episodeModel.realmSet$episode_num(null);
            } else {
                episodeModel.realmSet$episode_num(jSONObject.getString("episode_num"));
            }
        }
        if (jSONObject.has("title")) {
            if (jSONObject.isNull("title")) {
                episodeModel.realmSet$title(null);
            } else {
                episodeModel.realmSet$title(jSONObject.getString("title"));
            }
        }
        if (jSONObject.has("container_extension")) {
            if (jSONObject.isNull("container_extension")) {
                episodeModel.realmSet$container_extension(null);
            } else {
                episodeModel.realmSet$container_extension(jSONObject.getString("container_extension"));
            }
        }
        if (jSONObject.has("season")) {
            if (jSONObject.isNull("season")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'season' to null.");
            }
            episodeModel.realmSet$season(jSONObject.getInt("season"));
        }
        if (jSONObject.has("info")) {
            if (jSONObject.isNull("info")) {
                episodeModel.realmSet$info(null);
            } else {
                episodeModel.realmSet$info(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject.getJSONObject("info"), z));
            }
        }
        if (jSONObject.has("category_name")) {
            if (jSONObject.isNull("category_name")) {
                episodeModel.realmSet$category_name(null);
            } else {
                episodeModel.realmSet$category_name(jSONObject.getString("category_name"));
            }
        }
        if (jSONObject.has("stream_icon")) {
            if (jSONObject.isNull("stream_icon")) {
                episodeModel.realmSet$stream_icon(null);
            } else {
                episodeModel.realmSet$stream_icon(jSONObject.getString("stream_icon"));
            }
        }
        if (jSONObject.has("season_name")) {
            if (jSONObject.isNull("season_name")) {
                episodeModel.realmSet$season_name(null);
            } else {
                episodeModel.realmSet$season_name(jSONObject.getString("season_name"));
            }
        }
        if (jSONObject.has("series_name")) {
            if (jSONObject.isNull("series_name")) {
                episodeModel.realmSet$series_name(null);
            } else {
                episodeModel.realmSet$series_name(jSONObject.getString("series_name"));
            }
        }
        if (jSONObject.has(ImagesContract.URL)) {
            if (jSONObject.isNull(ImagesContract.URL)) {
                episodeModel.realmSet$url(null);
            } else {
                episodeModel.realmSet$url(jSONObject.getString(ImagesContract.URL));
            }
        }
        return episodeModel;
    }

    @TargetApi(11)
    public static EpisodeModel createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        EpisodeModel episodeModel = new EpisodeModel();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(TtmlNode.ATTR_ID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$id(null);
                }
            } else if (strNextName.equals("episode_num")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$episode_num(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$episode_num(null);
                }
            } else if (strNextName.equals("title")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$title(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$title(null);
                }
            } else if (strNextName.equals("container_extension")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$container_extension(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$container_extension(null);
                }
            } else if (strNextName.equals("season")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'season' to null.");
                }
                episodeModel.realmSet$season(jsonReader.nextInt());
            } else if (strNextName.equals("info")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    episodeModel.realmSet$info(null);
                } else {
                    episodeModel.realmSet$info(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.createUsingJsonStream(realm, jsonReader));
                }
            } else if (strNextName.equals("category_name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$category_name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$category_name(null);
                }
            } else if (strNextName.equals("stream_icon")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$stream_icon(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$stream_icon(null);
                }
            } else if (strNextName.equals("season_name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$season_name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$season_name(null);
                }
            } else if (strNextName.equals("series_name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    episodeModel.realmSet$series_name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    episodeModel.realmSet$series_name(null);
                }
            } else if (!strNextName.equals(ImagesContract.URL)) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                episodeModel.realmSet$url(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                episodeModel.realmSet$url(null);
            }
        }
        jsonReader.endObject();
        return (EpisodeModel) realm.copyToRealm(episodeModel, new ImportFlag[0]);
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, EpisodeModel episodeModel, Map<RealmModel, Long> map) {
        if ((episodeModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(EpisodeModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeModelColumnInfo episodeModelColumnInfo = (EpisodeModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(episodeModel, Long.valueOf(jCreateRow));
        String strRealmGet$id = episodeModel.realmGet$id();
        if (strRealmGet$id != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.idColKey, jCreateRow, strRealmGet$id, false);
        }
        String strRealmGet$episode_num = episodeModel.realmGet$episode_num();
        if (strRealmGet$episode_num != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.episode_numColKey, jCreateRow, strRealmGet$episode_num, false);
        }
        String strRealmGet$title = episodeModel.realmGet$title();
        if (strRealmGet$title != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.titleColKey, jCreateRow, strRealmGet$title, false);
        }
        String strRealmGet$container_extension = episodeModel.realmGet$container_extension();
        if (strRealmGet$container_extension != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.container_extensionColKey, jCreateRow, strRealmGet$container_extension, false);
        }
        Table.nativeSetLong(nativePtr, episodeModelColumnInfo.seasonColKey, jCreateRow, episodeModel.realmGet$season(), false);
        EpisodeInfoModel episodeInfoModelRealmGet$info = episodeModel.realmGet$info();
        if (episodeInfoModelRealmGet$info != null) {
            Long lValueOf = map.get(episodeInfoModelRealmGet$info);
            if (lValueOf == null) {
                lValueOf = Long.valueOf(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insert(realm, episodeInfoModelRealmGet$info, map));
            }
            Table.nativeSetLink(nativePtr, episodeModelColumnInfo.infoColKey, jCreateRow, lValueOf.longValue(), false);
        }
        String strRealmGet$category_name = episodeModel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        }
        String strRealmGet$stream_icon = episodeModel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        }
        String strRealmGet$season_name = episodeModel.realmGet$season_name();
        if (strRealmGet$season_name != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.season_nameColKey, jCreateRow, strRealmGet$season_name, false);
        }
        String strRealmGet$series_name = episodeModel.realmGet$series_name();
        if (strRealmGet$series_name != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.series_nameColKey, jCreateRow, strRealmGet$series_name, false);
        }
        String strRealmGet$url = episodeModel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        }
        return jCreateRow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, EpisodeModel episodeModel, Map<RealmModel, Long> map) {
        if ((episodeModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(EpisodeModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeModelColumnInfo episodeModelColumnInfo = (EpisodeModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(episodeModel, Long.valueOf(jCreateRow));
        String strRealmGet$id = episodeModel.realmGet$id();
        if (strRealmGet$id != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.idColKey, jCreateRow, strRealmGet$id, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.idColKey, jCreateRow, false);
        }
        String strRealmGet$episode_num = episodeModel.realmGet$episode_num();
        if (strRealmGet$episode_num != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.episode_numColKey, jCreateRow, strRealmGet$episode_num, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.episode_numColKey, jCreateRow, false);
        }
        String strRealmGet$title = episodeModel.realmGet$title();
        if (strRealmGet$title != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.titleColKey, jCreateRow, strRealmGet$title, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.titleColKey, jCreateRow, false);
        }
        String strRealmGet$container_extension = episodeModel.realmGet$container_extension();
        if (strRealmGet$container_extension != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.container_extensionColKey, jCreateRow, strRealmGet$container_extension, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.container_extensionColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, episodeModelColumnInfo.seasonColKey, jCreateRow, episodeModel.realmGet$season(), false);
        EpisodeInfoModel episodeInfoModelRealmGet$info = episodeModel.realmGet$info();
        if (episodeInfoModelRealmGet$info != null) {
            Long lValueOf = map.get(episodeInfoModelRealmGet$info);
            if (lValueOf == null) {
                lValueOf = Long.valueOf(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insertOrUpdate(realm, episodeInfoModelRealmGet$info, map));
            }
            Table.nativeSetLink(nativePtr, episodeModelColumnInfo.infoColKey, jCreateRow, lValueOf.longValue(), false);
        } else {
            Table.nativeNullifyLink(nativePtr, episodeModelColumnInfo.infoColKey, jCreateRow);
        }
        String strRealmGet$category_name = episodeModel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.category_nameColKey, jCreateRow, false);
        }
        String strRealmGet$stream_icon = episodeModel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.stream_iconColKey, jCreateRow, false);
        }
        String strRealmGet$season_name = episodeModel.realmGet$season_name();
        if (strRealmGet$season_name != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.season_nameColKey, jCreateRow, strRealmGet$season_name, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.season_nameColKey, jCreateRow, false);
        }
        String strRealmGet$series_name = episodeModel.realmGet$series_name();
        if (strRealmGet$series_name != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.series_nameColKey, jCreateRow, strRealmGet$series_name, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.series_nameColKey, jCreateRow, false);
        }
        String strRealmGet$url = episodeModel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, episodeModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        } else {
            Table.nativeSetNull(nativePtr, episodeModelColumnInfo.urlColKey, jCreateRow, false);
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
        com_nettv_livestore_models_EpisodeModelRealmProxy com_nettv_livestore_models_episodemodelrealmproxy = (com_nettv_livestore_models_EpisodeModelRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_nettv_livestore_models_episodemodelrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_nettv_livestore_models_episodemodelrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_nettv_livestore_models_episodemodelrealmproxy.proxyState.getRow$realm().getObjectKey();
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
        this.columnInfo = (EpisodeModelColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<EpisodeModel> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$category_name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_nameColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$container_extension() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.container_extensionColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$episode_num() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.episode_numColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.idColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public EpisodeInfoModel realmGet$info() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.infoColKey)) {
            return null;
        }
        return (EpisodeInfoModel) this.proxyState.getRealm$realm().get(EpisodeInfoModel.class, this.proxyState.getRow$realm().getLink(this.columnInfo.infoColKey), Collections.emptyList());
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public int realmGet$season() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.seasonColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$season_name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.season_nameColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$series_name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.series_nameColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$stream_icon() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_iconColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$title() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.titleColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public String realmGet$url() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.urlColKey);
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$category_name(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.category_nameColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.category_nameColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.category_nameColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.category_nameColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$container_extension(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.container_extensionColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.container_extensionColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.container_extensionColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.container_extensionColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$episode_num(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.episode_numColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.episode_numColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.episode_numColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.episode_numColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$id(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.idColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.idColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.idColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.idColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$info(EpisodeInfoModel episodeInfoModel) {
        EpisodeInfoModel episodeInfoModel2;
        Realm realm = (Realm) this.proxyState.getRealm$realm();
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (episodeInfoModel == 0) {
                this.proxyState.getRow$realm().nullifyLink(this.columnInfo.infoColKey);
                return;
            } else {
                this.proxyState.checkValidObject(episodeInfoModel);
                this.proxyState.getRow$realm().setLink(this.columnInfo.infoColKey, ((RealmObjectProxy) episodeInfoModel).realmGet$proxyState().getRow$realm().getObjectKey());
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            if (this.proxyState.getExcludeFields$realm().contains("info")) {
                episodeInfoModel2 = episodeInfoModel;
                return;
            }
            if (episodeInfoModel != 0) {
                boolean zIsManaged = RealmObject.isManaged(episodeInfoModel);
                episodeInfoModel2 = episodeInfoModel;
                if (!zIsManaged) {
                    episodeInfoModel2 = (EpisodeInfoModel) realm.copyToRealm(episodeInfoModel, new ImportFlag[0]);
                }
            }
            Row row$realm = this.proxyState.getRow$realm();
            if (episodeInfoModel2 == 0) {
                row$realm.nullifyLink(this.columnInfo.infoColKey);
            } else {
                this.proxyState.checkValidObject(episodeInfoModel2);
                row$realm.getTable().setLink(this.columnInfo.infoColKey, row$realm.getObjectKey(), ((RealmObjectProxy) episodeInfoModel2).realmGet$proxyState().getRow$realm().getObjectKey(), true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$season(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.seasonColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.seasonColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$season_name(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.season_nameColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.season_nameColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.season_nameColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.season_nameColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$series_name(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.series_nameColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.series_nameColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.series_nameColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.series_nameColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$stream_icon(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.stream_iconColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.stream_iconColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.stream_iconColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.stream_iconColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$title(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.titleColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.titleColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.titleColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.titleColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EpisodeModel, io.realm.com_nettv_livestore_models_EpisodeModelRealmProxyInterface
    public void realmSet$url(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.urlColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.urlColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.urlColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.urlColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("EpisodeModel = proxy[");
        sb.append("{id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$id() != null ? realmGet$id() : "null", "}", ",", "{episode_num:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$episode_num() != null ? realmGet$episode_num() : "null", "}", ",", "{title:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$title() != null ? realmGet$title() : "null", "}", ",", "{container_extension:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$container_extension() != null ? realmGet$container_extension() : "null", "}", ",", "{season:");
        sb.append(realmGet$season());
        sb.append("}");
        sb.append(",");
        sb.append("{info:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$info() != null ? com_nettv_livestore_models_EpisodeInfoModelRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME : "null", "}", ",", "{category_name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_name() != null ? realmGet$category_name() : "null", "}", ",", "{stream_icon:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_icon() != null ? realmGet$stream_icon() : "null", "}", ",", "{season_name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$season_name() != null ? realmGet$season_name() : "null", "}", ",", "{series_name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$series_name() != null ? realmGet$series_name() : "null", "}", ",", "{url:");
        return Insets$$ExternalSyntheticOutline0.m(sb, realmGet$url() != null ? realmGet$url() : "null", "}", "]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(EpisodeModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeModelColumnInfo episodeModelColumnInfo = (EpisodeModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeModel.class);
        while (it.hasNext()) {
            EpisodeModel episodeModel = (EpisodeModel) it.next();
            if (!map.containsKey(episodeModel)) {
                if ((episodeModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(episodeModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(episodeModel, Long.valueOf(jCreateRow));
                String strRealmGet$id = episodeModel.realmGet$id();
                if (strRealmGet$id != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.idColKey, jCreateRow, strRealmGet$id, false);
                }
                String strRealmGet$episode_num = episodeModel.realmGet$episode_num();
                if (strRealmGet$episode_num != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.episode_numColKey, jCreateRow, strRealmGet$episode_num, false);
                }
                String strRealmGet$title = episodeModel.realmGet$title();
                if (strRealmGet$title != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.titleColKey, jCreateRow, strRealmGet$title, false);
                }
                String strRealmGet$container_extension = episodeModel.realmGet$container_extension();
                if (strRealmGet$container_extension != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.container_extensionColKey, jCreateRow, strRealmGet$container_extension, false);
                }
                Table.nativeSetLong(nativePtr, episodeModelColumnInfo.seasonColKey, jCreateRow, episodeModel.realmGet$season(), false);
                EpisodeInfoModel episodeInfoModelRealmGet$info = episodeModel.realmGet$info();
                if (episodeInfoModelRealmGet$info != null) {
                    Long lValueOf = map.get(episodeInfoModelRealmGet$info);
                    if (lValueOf == null) {
                        lValueOf = Long.valueOf(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insert(realm, episodeInfoModelRealmGet$info, map));
                    }
                    Table.nativeSetLink(nativePtr, episodeModelColumnInfo.infoColKey, jCreateRow, lValueOf.longValue(), false);
                }
                String strRealmGet$category_name = episodeModel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                }
                String strRealmGet$stream_icon = episodeModel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                }
                String strRealmGet$season_name = episodeModel.realmGet$season_name();
                if (strRealmGet$season_name != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.season_nameColKey, jCreateRow, strRealmGet$season_name, false);
                }
                String strRealmGet$series_name = episodeModel.realmGet$series_name();
                if (strRealmGet$series_name != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.series_nameColKey, jCreateRow, strRealmGet$series_name, false);
                }
                String strRealmGet$url = episodeModel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(EpisodeModel.class);
        long nativePtr = table.getNativePtr();
        EpisodeModelColumnInfo episodeModelColumnInfo = (EpisodeModelColumnInfo) realm.getSchema().getColumnInfo(EpisodeModel.class);
        while (it.hasNext()) {
            EpisodeModel episodeModel = (EpisodeModel) it.next();
            if (!map.containsKey(episodeModel)) {
                if ((episodeModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(episodeModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) episodeModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(episodeModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(episodeModel, Long.valueOf(jCreateRow));
                String strRealmGet$id = episodeModel.realmGet$id();
                if (strRealmGet$id != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.idColKey, jCreateRow, strRealmGet$id, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.idColKey, jCreateRow, false);
                }
                String strRealmGet$episode_num = episodeModel.realmGet$episode_num();
                if (strRealmGet$episode_num != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.episode_numColKey, jCreateRow, strRealmGet$episode_num, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.episode_numColKey, jCreateRow, false);
                }
                String strRealmGet$title = episodeModel.realmGet$title();
                if (strRealmGet$title != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.titleColKey, jCreateRow, strRealmGet$title, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.titleColKey, jCreateRow, false);
                }
                String strRealmGet$container_extension = episodeModel.realmGet$container_extension();
                if (strRealmGet$container_extension != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.container_extensionColKey, jCreateRow, strRealmGet$container_extension, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.container_extensionColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, episodeModelColumnInfo.seasonColKey, jCreateRow, episodeModel.realmGet$season(), false);
                EpisodeInfoModel episodeInfoModelRealmGet$info = episodeModel.realmGet$info();
                if (episodeInfoModelRealmGet$info != null) {
                    Long lValueOf = map.get(episodeInfoModelRealmGet$info);
                    if (lValueOf == null) {
                        lValueOf = Long.valueOf(com_nettv_livestore_models_EpisodeInfoModelRealmProxy.insertOrUpdate(realm, episodeInfoModelRealmGet$info, map));
                    }
                    Table.nativeSetLink(nativePtr, episodeModelColumnInfo.infoColKey, jCreateRow, lValueOf.longValue(), false);
                } else {
                    Table.nativeNullifyLink(nativePtr, episodeModelColumnInfo.infoColKey, jCreateRow);
                }
                String strRealmGet$category_name = episodeModel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.category_nameColKey, jCreateRow, false);
                }
                String strRealmGet$stream_icon = episodeModel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.stream_iconColKey, jCreateRow, false);
                }
                String strRealmGet$season_name = episodeModel.realmGet$season_name();
                if (strRealmGet$season_name != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.season_nameColKey, jCreateRow, strRealmGet$season_name, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.season_nameColKey, jCreateRow, false);
                }
                String strRealmGet$series_name = episodeModel.realmGet$series_name();
                if (strRealmGet$series_name != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.series_nameColKey, jCreateRow, strRealmGet$series_name, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.series_nameColKey, jCreateRow, false);
                }
                String strRealmGet$url = episodeModel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, episodeModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                } else {
                    Table.nativeSetNull(nativePtr, episodeModelColumnInfo.urlColKey, jCreateRow, false);
                }
            }
        }
    }
}
