package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.gms.common.internal.ImagesContract;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.models.EPGChannel;
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
public class com_nettv_livestore_models_EPGChannelRealmProxy extends EPGChannel implements RealmObjectProxy {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private EPGChannelColumnInfo columnInfo;
    private ProxyState<EPGChannel> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "EPGChannel";
    }

    public static final class EPGChannelColumnInfo extends ColumnInfo {
        public long IdColKey;
        public long addedColKey;
        public long category_idColKey;
        public long category_nameColKey;
        public long cellColKey;
        public long channelIDColKey;
        public long custom_sidColKey;
        public long direct_sourceColKey;
        public long is_favoriteColKey;
        public long is_lockedColKey;
        public long is_recentColKey;
        public long nameColKey;
        public long numColKey;
        public long recent_posColKey;
        public long selectedColKey;
        public long stream_iconColKey;
        public long stream_idColKey;
        public long stream_typeColKey;
        public long tv_archiveColKey;
        public long tv_archive_durationColKey;
        public long urlColKey;

        public EPGChannelColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(21);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.numColKey = addColumnDetails("num", "num", objectSchemaInfo);
            this.nameColKey = addColumnDetails("name", "name", objectSchemaInfo);
            this.stream_typeColKey = addColumnDetails("stream_type", "stream_type", objectSchemaInfo);
            this.stream_idColKey = addColumnDetails("stream_id", "stream_id", objectSchemaInfo);
            this.stream_iconColKey = addColumnDetails("stream_icon", "stream_icon", objectSchemaInfo);
            this.IdColKey = addColumnDetails("Id", "Id", objectSchemaInfo);
            this.addedColKey = addColumnDetails("added", "added", objectSchemaInfo);
            this.category_idColKey = addColumnDetails("category_id", "category_id", objectSchemaInfo);
            this.custom_sidColKey = addColumnDetails("custom_sid", "custom_sid", objectSchemaInfo);
            this.tv_archiveColKey = addColumnDetails("tv_archive", "tv_archive", objectSchemaInfo);
            this.direct_sourceColKey = addColumnDetails("direct_source", "direct_source", objectSchemaInfo);
            this.tv_archive_durationColKey = addColumnDetails("tv_archive_duration", "tv_archive_duration", objectSchemaInfo);
            this.urlColKey = addColumnDetails(ImagesContract.URL, ImagesContract.URL, objectSchemaInfo);
            this.category_nameColKey = addColumnDetails("category_name", "category_name", objectSchemaInfo);
            this.is_lockedColKey = addColumnDetails("is_locked", "is_locked", objectSchemaInfo);
            this.is_favoriteColKey = addColumnDetails("is_favorite", "is_favorite", objectSchemaInfo);
            this.is_recentColKey = addColumnDetails("is_recent", "is_recent", objectSchemaInfo);
            this.recent_posColKey = addColumnDetails("recent_pos", "recent_pos", objectSchemaInfo);
            this.cellColKey = addColumnDetails("cell", "cell", objectSchemaInfo);
            this.channelIDColKey = addColumnDetails("channelID", "channelID", objectSchemaInfo);
            this.selectedColKey = addColumnDetails("selected", "selected", objectSchemaInfo);
        }

        @Override // io.realm.internal.ColumnInfo
        public final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            EPGChannelColumnInfo ePGChannelColumnInfo = (EPGChannelColumnInfo) columnInfo;
            EPGChannelColumnInfo ePGChannelColumnInfo2 = (EPGChannelColumnInfo) columnInfo2;
            ePGChannelColumnInfo2.numColKey = ePGChannelColumnInfo.numColKey;
            ePGChannelColumnInfo2.nameColKey = ePGChannelColumnInfo.nameColKey;
            ePGChannelColumnInfo2.stream_typeColKey = ePGChannelColumnInfo.stream_typeColKey;
            ePGChannelColumnInfo2.stream_idColKey = ePGChannelColumnInfo.stream_idColKey;
            ePGChannelColumnInfo2.stream_iconColKey = ePGChannelColumnInfo.stream_iconColKey;
            ePGChannelColumnInfo2.IdColKey = ePGChannelColumnInfo.IdColKey;
            ePGChannelColumnInfo2.addedColKey = ePGChannelColumnInfo.addedColKey;
            ePGChannelColumnInfo2.category_idColKey = ePGChannelColumnInfo.category_idColKey;
            ePGChannelColumnInfo2.custom_sidColKey = ePGChannelColumnInfo.custom_sidColKey;
            ePGChannelColumnInfo2.tv_archiveColKey = ePGChannelColumnInfo.tv_archiveColKey;
            ePGChannelColumnInfo2.direct_sourceColKey = ePGChannelColumnInfo.direct_sourceColKey;
            ePGChannelColumnInfo2.tv_archive_durationColKey = ePGChannelColumnInfo.tv_archive_durationColKey;
            ePGChannelColumnInfo2.urlColKey = ePGChannelColumnInfo.urlColKey;
            ePGChannelColumnInfo2.category_nameColKey = ePGChannelColumnInfo.category_nameColKey;
            ePGChannelColumnInfo2.is_lockedColKey = ePGChannelColumnInfo.is_lockedColKey;
            ePGChannelColumnInfo2.is_favoriteColKey = ePGChannelColumnInfo.is_favoriteColKey;
            ePGChannelColumnInfo2.is_recentColKey = ePGChannelColumnInfo.is_recentColKey;
            ePGChannelColumnInfo2.recent_posColKey = ePGChannelColumnInfo.recent_posColKey;
            ePGChannelColumnInfo2.cellColKey = ePGChannelColumnInfo.cellColKey;
            ePGChannelColumnInfo2.channelIDColKey = ePGChannelColumnInfo.channelIDColKey;
            ePGChannelColumnInfo2.selectedColKey = ePGChannelColumnInfo.selectedColKey;
        }
    }

    public com_nettv_livestore_models_EPGChannelRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static EPGChannel copy(Realm realm, EPGChannelColumnInfo ePGChannelColumnInfo, EPGChannel ePGChannel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(ePGChannel);
        if (realmObjectProxy != null) {
            return (EPGChannel) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(EPGChannel.class), set);
        osObjectBuilder.addString(ePGChannelColumnInfo.numColKey, ePGChannel.realmGet$num());
        osObjectBuilder.addString(ePGChannelColumnInfo.nameColKey, ePGChannel.realmGet$name());
        osObjectBuilder.addString(ePGChannelColumnInfo.stream_typeColKey, ePGChannel.realmGet$stream_type());
        osObjectBuilder.addString(ePGChannelColumnInfo.stream_idColKey, ePGChannel.realmGet$stream_id());
        osObjectBuilder.addString(ePGChannelColumnInfo.stream_iconColKey, ePGChannel.realmGet$stream_icon());
        osObjectBuilder.addString(ePGChannelColumnInfo.IdColKey, ePGChannel.realmGet$Id());
        osObjectBuilder.addString(ePGChannelColumnInfo.addedColKey, ePGChannel.realmGet$added());
        osObjectBuilder.addString(ePGChannelColumnInfo.category_idColKey, ePGChannel.realmGet$category_id());
        osObjectBuilder.addString(ePGChannelColumnInfo.custom_sidColKey, ePGChannel.realmGet$custom_sid());
        osObjectBuilder.addString(ePGChannelColumnInfo.tv_archiveColKey, ePGChannel.realmGet$tv_archive());
        osObjectBuilder.addString(ePGChannelColumnInfo.direct_sourceColKey, ePGChannel.realmGet$direct_source());
        osObjectBuilder.addString(ePGChannelColumnInfo.tv_archive_durationColKey, ePGChannel.realmGet$tv_archive_duration());
        osObjectBuilder.addString(ePGChannelColumnInfo.urlColKey, ePGChannel.realmGet$url());
        osObjectBuilder.addString(ePGChannelColumnInfo.category_nameColKey, ePGChannel.realmGet$category_name());
        osObjectBuilder.addBoolean(ePGChannelColumnInfo.is_lockedColKey, Boolean.valueOf(ePGChannel.realmGet$is_locked()));
        osObjectBuilder.addBoolean(ePGChannelColumnInfo.is_favoriteColKey, Boolean.valueOf(ePGChannel.realmGet$is_favorite()));
        osObjectBuilder.addBoolean(ePGChannelColumnInfo.is_recentColKey, Boolean.valueOf(ePGChannel.realmGet$is_recent()));
        osObjectBuilder.addInteger(ePGChannelColumnInfo.recent_posColKey, Long.valueOf(ePGChannel.realmGet$recent_pos()));
        osObjectBuilder.addInteger(ePGChannelColumnInfo.cellColKey, Integer.valueOf(ePGChannel.realmGet$cell()));
        osObjectBuilder.addInteger(ePGChannelColumnInfo.channelIDColKey, Integer.valueOf(ePGChannel.realmGet$channelID()));
        osObjectBuilder.addBoolean(ePGChannelColumnInfo.selectedColKey, Boolean.valueOf(ePGChannel.realmGet$selected()));
        UncheckedRow uncheckedRowCreateNewObject = osObjectBuilder.createNewObject();
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(realm, uncheckedRowCreateNewObject, realm.getSchema().getColumnInfo(EPGChannel.class), false, Collections.emptyList());
        com_nettv_livestore_models_EPGChannelRealmProxy com_nettv_livestore_models_epgchannelrealmproxy = new com_nettv_livestore_models_EPGChannelRealmProxy();
        realmObjectContext.clear();
        map.put(ePGChannel, com_nettv_livestore_models_epgchannelrealmproxy);
        return com_nettv_livestore_models_epgchannelrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EPGChannel copyOrUpdate(Realm realm, EPGChannelColumnInfo ePGChannelColumnInfo, EPGChannel ePGChannel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((ePGChannel instanceof RealmObjectProxy) && !RealmObject.isFrozen(ePGChannel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) ePGChannel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return ePGChannel;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(ePGChannel);
        return realmModel != null ? (EPGChannel) realmModel : copy(realm, ePGChannelColumnInfo, ePGChannel, z, map, set);
    }

    public static EPGChannelColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new EPGChannelColumnInfo(osSchemaInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EPGChannel createDetachedCopy(EPGChannel ePGChannel, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        EPGChannel ePGChannel2;
        if (i > i2 || ePGChannel == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(ePGChannel);
        if (cacheData == null) {
            ePGChannel2 = new EPGChannel();
            map.put(ePGChannel, new RealmObjectProxy.CacheData<>(i, ePGChannel2));
        } else {
            if (i >= cacheData.minDepth) {
                return (EPGChannel) cacheData.object;
            }
            EPGChannel ePGChannel3 = (EPGChannel) cacheData.object;
            cacheData.minDepth = i;
            ePGChannel2 = ePGChannel3;
        }
        ePGChannel2.realmSet$num(ePGChannel.realmGet$num());
        ePGChannel2.realmSet$name(ePGChannel.realmGet$name());
        ePGChannel2.realmSet$stream_type(ePGChannel.realmGet$stream_type());
        ePGChannel2.realmSet$stream_id(ePGChannel.realmGet$stream_id());
        ePGChannel2.realmSet$stream_icon(ePGChannel.realmGet$stream_icon());
        ePGChannel2.realmSet$Id(ePGChannel.realmGet$Id());
        ePGChannel2.realmSet$added(ePGChannel.realmGet$added());
        ePGChannel2.realmSet$category_id(ePGChannel.realmGet$category_id());
        ePGChannel2.realmSet$custom_sid(ePGChannel.realmGet$custom_sid());
        ePGChannel2.realmSet$tv_archive(ePGChannel.realmGet$tv_archive());
        ePGChannel2.realmSet$direct_source(ePGChannel.realmGet$direct_source());
        ePGChannel2.realmSet$tv_archive_duration(ePGChannel.realmGet$tv_archive_duration());
        ePGChannel2.realmSet$url(ePGChannel.realmGet$url());
        ePGChannel2.realmSet$category_name(ePGChannel.realmGet$category_name());
        ePGChannel2.realmSet$is_locked(ePGChannel.realmGet$is_locked());
        ePGChannel2.realmSet$is_favorite(ePGChannel.realmGet$is_favorite());
        ePGChannel2.realmSet$is_recent(ePGChannel.realmGet$is_recent());
        ePGChannel2.realmSet$recent_pos(ePGChannel.realmGet$recent_pos());
        ePGChannel2.realmSet$cell(ePGChannel.realmGet$cell());
        ePGChannel2.realmSet$channelID(ePGChannel.realmGet$channelID());
        ePGChannel2.realmSet$selected(ePGChannel.realmGet$selected());
        return ePGChannel2;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 21, 0);
        RealmFieldType realmFieldType = RealmFieldType.STRING;
        builder.addPersistedProperty("", "num", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "name", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "stream_type", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "stream_id", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "stream_icon", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "Id", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "added", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "category_id", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "custom_sid", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "tv_archive", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "direct_source", realmFieldType, false, false, false);
        builder.addPersistedProperty("", "tv_archive_duration", realmFieldType, false, false, false);
        builder.addPersistedProperty("", ImagesContract.URL, realmFieldType, false, false, false);
        builder.addPersistedProperty("", "category_name", realmFieldType, false, false, false);
        RealmFieldType realmFieldType2 = RealmFieldType.BOOLEAN;
        builder.addPersistedProperty("", "is_locked", realmFieldType2, false, false, true);
        builder.addPersistedProperty("", "is_favorite", realmFieldType2, false, false, true);
        builder.addPersistedProperty("", "is_recent", realmFieldType2, false, false, true);
        RealmFieldType realmFieldType3 = RealmFieldType.INTEGER;
        builder.addPersistedProperty("", "recent_pos", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "cell", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "channelID", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "selected", realmFieldType2, false, false, true);
        return builder.build();
    }

    public static EPGChannel createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        EPGChannel ePGChannel = (EPGChannel) realm.createObjectInternal(EPGChannel.class, Collections.emptyList());
        if (jSONObject.has("num")) {
            if (jSONObject.isNull("num")) {
                ePGChannel.realmSet$num(null);
            } else {
                ePGChannel.realmSet$num(jSONObject.getString("num"));
            }
        }
        if (jSONObject.has("name")) {
            if (jSONObject.isNull("name")) {
                ePGChannel.realmSet$name(null);
            } else {
                ePGChannel.realmSet$name(jSONObject.getString("name"));
            }
        }
        if (jSONObject.has("stream_type")) {
            if (jSONObject.isNull("stream_type")) {
                ePGChannel.realmSet$stream_type(null);
            } else {
                ePGChannel.realmSet$stream_type(jSONObject.getString("stream_type"));
            }
        }
        if (jSONObject.has("stream_id")) {
            if (jSONObject.isNull("stream_id")) {
                ePGChannel.realmSet$stream_id(null);
            } else {
                ePGChannel.realmSet$stream_id(jSONObject.getString("stream_id"));
            }
        }
        if (jSONObject.has("stream_icon")) {
            if (jSONObject.isNull("stream_icon")) {
                ePGChannel.realmSet$stream_icon(null);
            } else {
                ePGChannel.realmSet$stream_icon(jSONObject.getString("stream_icon"));
            }
        }
        if (jSONObject.has("Id")) {
            if (jSONObject.isNull("Id")) {
                ePGChannel.realmSet$Id(null);
            } else {
                ePGChannel.realmSet$Id(jSONObject.getString("Id"));
            }
        }
        if (jSONObject.has("added")) {
            if (jSONObject.isNull("added")) {
                ePGChannel.realmSet$added(null);
            } else {
                ePGChannel.realmSet$added(jSONObject.getString("added"));
            }
        }
        if (jSONObject.has("category_id")) {
            if (jSONObject.isNull("category_id")) {
                ePGChannel.realmSet$category_id(null);
            } else {
                ePGChannel.realmSet$category_id(jSONObject.getString("category_id"));
            }
        }
        if (jSONObject.has("custom_sid")) {
            if (jSONObject.isNull("custom_sid")) {
                ePGChannel.realmSet$custom_sid(null);
            } else {
                ePGChannel.realmSet$custom_sid(jSONObject.getString("custom_sid"));
            }
        }
        if (jSONObject.has("tv_archive")) {
            if (jSONObject.isNull("tv_archive")) {
                ePGChannel.realmSet$tv_archive(null);
            } else {
                ePGChannel.realmSet$tv_archive(jSONObject.getString("tv_archive"));
            }
        }
        if (jSONObject.has("direct_source")) {
            if (jSONObject.isNull("direct_source")) {
                ePGChannel.realmSet$direct_source(null);
            } else {
                ePGChannel.realmSet$direct_source(jSONObject.getString("direct_source"));
            }
        }
        if (jSONObject.has("tv_archive_duration")) {
            if (jSONObject.isNull("tv_archive_duration")) {
                ePGChannel.realmSet$tv_archive_duration(null);
            } else {
                ePGChannel.realmSet$tv_archive_duration(jSONObject.getString("tv_archive_duration"));
            }
        }
        if (jSONObject.has(ImagesContract.URL)) {
            if (jSONObject.isNull(ImagesContract.URL)) {
                ePGChannel.realmSet$url(null);
            } else {
                ePGChannel.realmSet$url(jSONObject.getString(ImagesContract.URL));
            }
        }
        if (jSONObject.has("category_name")) {
            if (jSONObject.isNull("category_name")) {
                ePGChannel.realmSet$category_name(null);
            } else {
                ePGChannel.realmSet$category_name(jSONObject.getString("category_name"));
            }
        }
        if (jSONObject.has("is_locked")) {
            if (jSONObject.isNull("is_locked")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_locked' to null.");
            }
            ePGChannel.realmSet$is_locked(jSONObject.getBoolean("is_locked"));
        }
        if (jSONObject.has("is_favorite")) {
            if (jSONObject.isNull("is_favorite")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_favorite' to null.");
            }
            ePGChannel.realmSet$is_favorite(jSONObject.getBoolean("is_favorite"));
        }
        if (jSONObject.has("is_recent")) {
            if (jSONObject.isNull("is_recent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_recent' to null.");
            }
            ePGChannel.realmSet$is_recent(jSONObject.getBoolean("is_recent"));
        }
        if (jSONObject.has("recent_pos")) {
            if (jSONObject.isNull("recent_pos")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'recent_pos' to null.");
            }
            ePGChannel.realmSet$recent_pos(jSONObject.getLong("recent_pos"));
        }
        if (jSONObject.has("cell")) {
            if (jSONObject.isNull("cell")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'cell' to null.");
            }
            ePGChannel.realmSet$cell(jSONObject.getInt("cell"));
        }
        if (jSONObject.has("channelID")) {
            if (jSONObject.isNull("channelID")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'channelID' to null.");
            }
            ePGChannel.realmSet$channelID(jSONObject.getInt("channelID"));
        }
        if (jSONObject.has("selected")) {
            if (jSONObject.isNull("selected")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'selected' to null.");
            }
            ePGChannel.realmSet$selected(jSONObject.getBoolean("selected"));
        }
        return ePGChannel;
    }

    @TargetApi(11)
    public static EPGChannel createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        EPGChannel ePGChannel = new EPGChannel();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("num")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$num(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$num(null);
                }
            } else if (strNextName.equals("name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$name(null);
                }
            } else if (strNextName.equals("stream_type")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$stream_type(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$stream_type(null);
                }
            } else if (strNextName.equals("stream_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$stream_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$stream_id(null);
                }
            } else if (strNextName.equals("stream_icon")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$stream_icon(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$stream_icon(null);
                }
            } else if (strNextName.equals("Id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$Id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$Id(null);
                }
            } else if (strNextName.equals("added")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$added(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$added(null);
                }
            } else if (strNextName.equals("category_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$category_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$category_id(null);
                }
            } else if (strNextName.equals("custom_sid")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$custom_sid(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$custom_sid(null);
                }
            } else if (strNextName.equals("tv_archive")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$tv_archive(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$tv_archive(null);
                }
            } else if (strNextName.equals("direct_source")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$direct_source(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$direct_source(null);
                }
            } else if (strNextName.equals("tv_archive_duration")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$tv_archive_duration(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$tv_archive_duration(null);
                }
            } else if (strNextName.equals(ImagesContract.URL)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$url(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$url(null);
                }
            } else if (strNextName.equals("category_name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    ePGChannel.realmSet$category_name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    ePGChannel.realmSet$category_name(null);
                }
            } else if (strNextName.equals("is_locked")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_locked' to null.");
                }
                ePGChannel.realmSet$is_locked(jsonReader.nextBoolean());
            } else if (strNextName.equals("is_favorite")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_favorite' to null.");
                }
                ePGChannel.realmSet$is_favorite(jsonReader.nextBoolean());
            } else if (strNextName.equals("is_recent")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_recent' to null.");
                }
                ePGChannel.realmSet$is_recent(jsonReader.nextBoolean());
            } else if (strNextName.equals("recent_pos")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'recent_pos' to null.");
                }
                ePGChannel.realmSet$recent_pos(jsonReader.nextLong());
            } else if (strNextName.equals("cell")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'cell' to null.");
                }
                ePGChannel.realmSet$cell(jsonReader.nextInt());
            } else if (strNextName.equals("channelID")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'channelID' to null.");
                }
                ePGChannel.realmSet$channelID(jsonReader.nextInt());
            } else if (!strNextName.equals("selected")) {
                jsonReader.skipValue();
            } else {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'selected' to null.");
                }
                ePGChannel.realmSet$selected(jsonReader.nextBoolean());
            }
        }
        jsonReader.endObject();
        return (EPGChannel) realm.copyToRealm(ePGChannel, new ImportFlag[0]);
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, EPGChannel ePGChannel, Map<RealmModel, Long> map) {
        if ((ePGChannel instanceof RealmObjectProxy) && !RealmObject.isFrozen(ePGChannel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) ePGChannel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(EPGChannel.class);
        long nativePtr = table.getNativePtr();
        EPGChannelColumnInfo ePGChannelColumnInfo = (EPGChannelColumnInfo) realm.getSchema().getColumnInfo(EPGChannel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(ePGChannel, Long.valueOf(jCreateRow));
        String strRealmGet$num = ePGChannel.realmGet$num();
        if (strRealmGet$num != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.numColKey, jCreateRow, strRealmGet$num, false);
        }
        String strRealmGet$name = ePGChannel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        }
        String strRealmGet$stream_type = ePGChannel.realmGet$stream_type();
        if (strRealmGet$stream_type != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
        }
        String strRealmGet$stream_id = ePGChannel.realmGet$stream_id();
        if (strRealmGet$stream_id != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
        }
        String strRealmGet$stream_icon = ePGChannel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        }
        String strRealmGet$Id = ePGChannel.realmGet$Id();
        if (strRealmGet$Id != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.IdColKey, jCreateRow, strRealmGet$Id, false);
        }
        String strRealmGet$added = ePGChannel.realmGet$added();
        if (strRealmGet$added != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
        }
        String strRealmGet$category_id = ePGChannel.realmGet$category_id();
        if (strRealmGet$category_id != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
        }
        String strRealmGet$custom_sid = ePGChannel.realmGet$custom_sid();
        if (strRealmGet$custom_sid != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
        }
        String strRealmGet$tv_archive = ePGChannel.realmGet$tv_archive();
        if (strRealmGet$tv_archive != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archiveColKey, jCreateRow, strRealmGet$tv_archive, false);
        }
        String strRealmGet$direct_source = ePGChannel.realmGet$direct_source();
        if (strRealmGet$direct_source != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.direct_sourceColKey, jCreateRow, strRealmGet$direct_source, false);
        }
        String strRealmGet$tv_archive_duration = ePGChannel.realmGet$tv_archive_duration();
        if (strRealmGet$tv_archive_duration != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archive_durationColKey, jCreateRow, strRealmGet$tv_archive_duration, false);
        }
        String strRealmGet$url = ePGChannel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        }
        String strRealmGet$category_name = ePGChannel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        }
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_lockedColKey, jCreateRow, ePGChannel.realmGet$is_locked(), false);
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_favoriteColKey, jCreateRow, ePGChannel.realmGet$is_favorite(), false);
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_recentColKey, jCreateRow, ePGChannel.realmGet$is_recent(), false);
        Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.recent_posColKey, jCreateRow, ePGChannel.realmGet$recent_pos(), false);
        Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.cellColKey, jCreateRow, ePGChannel.realmGet$cell(), false);
        Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.channelIDColKey, jCreateRow, ePGChannel.realmGet$channelID(), false);
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.selectedColKey, jCreateRow, ePGChannel.realmGet$selected(), false);
        return jCreateRow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, EPGChannel ePGChannel, Map<RealmModel, Long> map) {
        if ((ePGChannel instanceof RealmObjectProxy) && !RealmObject.isFrozen(ePGChannel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) ePGChannel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(EPGChannel.class);
        long nativePtr = table.getNativePtr();
        EPGChannelColumnInfo ePGChannelColumnInfo = (EPGChannelColumnInfo) realm.getSchema().getColumnInfo(EPGChannel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(ePGChannel, Long.valueOf(jCreateRow));
        String strRealmGet$num = ePGChannel.realmGet$num();
        if (strRealmGet$num != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.numColKey, jCreateRow, strRealmGet$num, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.numColKey, jCreateRow, false);
        }
        String strRealmGet$name = ePGChannel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.nameColKey, jCreateRow, false);
        }
        String strRealmGet$stream_type = ePGChannel.realmGet$stream_type();
        if (strRealmGet$stream_type != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.stream_typeColKey, jCreateRow, false);
        }
        String strRealmGet$stream_id = ePGChannel.realmGet$stream_id();
        if (strRealmGet$stream_id != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.stream_idColKey, jCreateRow, false);
        }
        String strRealmGet$stream_icon = ePGChannel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.stream_iconColKey, jCreateRow, false);
        }
        String strRealmGet$Id = ePGChannel.realmGet$Id();
        if (strRealmGet$Id != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.IdColKey, jCreateRow, strRealmGet$Id, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.IdColKey, jCreateRow, false);
        }
        String strRealmGet$added = ePGChannel.realmGet$added();
        if (strRealmGet$added != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.addedColKey, jCreateRow, false);
        }
        String strRealmGet$category_id = ePGChannel.realmGet$category_id();
        if (strRealmGet$category_id != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.category_idColKey, jCreateRow, false);
        }
        String strRealmGet$custom_sid = ePGChannel.realmGet$custom_sid();
        if (strRealmGet$custom_sid != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.custom_sidColKey, jCreateRow, false);
        }
        String strRealmGet$tv_archive = ePGChannel.realmGet$tv_archive();
        if (strRealmGet$tv_archive != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archiveColKey, jCreateRow, strRealmGet$tv_archive, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.tv_archiveColKey, jCreateRow, false);
        }
        String strRealmGet$direct_source = ePGChannel.realmGet$direct_source();
        if (strRealmGet$direct_source != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.direct_sourceColKey, jCreateRow, strRealmGet$direct_source, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.direct_sourceColKey, jCreateRow, false);
        }
        String strRealmGet$tv_archive_duration = ePGChannel.realmGet$tv_archive_duration();
        if (strRealmGet$tv_archive_duration != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archive_durationColKey, jCreateRow, strRealmGet$tv_archive_duration, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.tv_archive_durationColKey, jCreateRow, false);
        }
        String strRealmGet$url = ePGChannel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.urlColKey, jCreateRow, false);
        }
        String strRealmGet$category_name = ePGChannel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        } else {
            Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.category_nameColKey, jCreateRow, false);
        }
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_lockedColKey, jCreateRow, ePGChannel.realmGet$is_locked(), false);
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_favoriteColKey, jCreateRow, ePGChannel.realmGet$is_favorite(), false);
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_recentColKey, jCreateRow, ePGChannel.realmGet$is_recent(), false);
        Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.recent_posColKey, jCreateRow, ePGChannel.realmGet$recent_pos(), false);
        Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.cellColKey, jCreateRow, ePGChannel.realmGet$cell(), false);
        Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.channelIDColKey, jCreateRow, ePGChannel.realmGet$channelID(), false);
        Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.selectedColKey, jCreateRow, ePGChannel.realmGet$selected(), false);
        return jCreateRow;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com_nettv_livestore_models_EPGChannelRealmProxy com_nettv_livestore_models_epgchannelrealmproxy = (com_nettv_livestore_models_EPGChannelRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_nettv_livestore_models_epgchannelrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_nettv_livestore_models_epgchannelrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_nettv_livestore_models_epgchannelrealmproxy.proxyState.getRow$realm().getObjectKey();
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
        this.columnInfo = (EPGChannelColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<EPGChannel> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$Id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.IdColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$added() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.addedColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$category_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_idColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$category_name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_nameColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public int realmGet$cell() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.cellColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public int realmGet$channelID() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.channelIDColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$custom_sid() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.custom_sidColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$direct_source() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.direct_sourceColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$is_favorite() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_favoriteColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$is_locked() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_lockedColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$is_recent() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_recentColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.nameColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$num() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.numColKey);
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public long realmGet$recent_pos() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.recent_posColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$selected() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.selectedColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$stream_icon() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_iconColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$stream_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_idColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$stream_type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_typeColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$tv_archive() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tv_archiveColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$tv_archive_duration() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tv_archive_durationColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$url() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.urlColKey);
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$Id(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.IdColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.IdColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.IdColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.IdColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$added(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.addedColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.addedColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.addedColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.addedColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$category_id(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.category_idColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.category_idColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.category_idColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.category_idColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$cell(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.cellColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.cellColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$channelID(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.channelIDColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.channelIDColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$custom_sid(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.custom_sidColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.custom_sidColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.custom_sidColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.custom_sidColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$direct_source(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.direct_sourceColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.direct_sourceColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.direct_sourceColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.direct_sourceColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$is_favorite(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_favoriteColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_favoriteColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$is_locked(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_lockedColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_lockedColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$is_recent(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_recentColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_recentColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$num(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.numColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.numColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.numColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.numColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$recent_pos(long j) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.recent_posColKey, j);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.recent_posColKey, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$selected(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.selectedColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.selectedColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$stream_id(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.stream_idColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.stream_idColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.stream_idColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.stream_idColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$stream_type(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.stream_typeColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.stream_typeColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.stream_typeColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.stream_typeColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$tv_archive(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.tv_archiveColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.tv_archiveColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.tv_archiveColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.tv_archiveColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$tv_archive_duration(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.tv_archive_durationColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.tv_archive_durationColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.tv_archive_durationColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.tv_archive_durationColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.EPGChannel, io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
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
        StringBuilder sb = new StringBuilder("EPGChannel = proxy[");
        sb.append("{num:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$num() != null ? realmGet$num() : "null", "}", ",", "{name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$name() != null ? realmGet$name() : "null", "}", ",", "{stream_type:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_type() != null ? realmGet$stream_type() : "null", "}", ",", "{stream_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_id() != null ? realmGet$stream_id() : "null", "}", ",", "{stream_icon:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_icon() != null ? realmGet$stream_icon() : "null", "}", ",", "{Id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$Id() != null ? realmGet$Id() : "null", "}", ",", "{added:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$added() != null ? realmGet$added() : "null", "}", ",", "{category_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_id() != null ? realmGet$category_id() : "null", "}", ",", "{custom_sid:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$custom_sid() != null ? realmGet$custom_sid() : "null", "}", ",", "{tv_archive:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$tv_archive() != null ? realmGet$tv_archive() : "null", "}", ",", "{direct_source:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$direct_source() != null ? realmGet$direct_source() : "null", "}", ",", "{tv_archive_duration:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$tv_archive_duration() != null ? realmGet$tv_archive_duration() : "null", "}", ",", "{url:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$url() != null ? realmGet$url() : "null", "}", ",", "{category_name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_name() != null ? realmGet$category_name() : "null", "}", ",", "{is_locked:");
        sb.append(realmGet$is_locked());
        sb.append("}");
        sb.append(",");
        sb.append("{is_favorite:");
        sb.append(realmGet$is_favorite());
        sb.append("}");
        sb.append(",");
        sb.append("{is_recent:");
        sb.append(realmGet$is_recent());
        sb.append("}");
        sb.append(",");
        sb.append("{recent_pos:");
        sb.append(realmGet$recent_pos());
        sb.append("}");
        sb.append(",");
        sb.append("{cell:");
        sb.append(realmGet$cell());
        sb.append("}");
        sb.append(",");
        sb.append("{channelID:");
        sb.append(realmGet$channelID());
        sb.append("}");
        sb.append(",");
        sb.append("{selected:");
        sb.append(realmGet$selected());
        return Insets$$ExternalSyntheticOutline0.m(sb, "}", "]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(EPGChannel.class);
        long nativePtr = table.getNativePtr();
        EPGChannelColumnInfo ePGChannelColumnInfo = (EPGChannelColumnInfo) realm.getSchema().getColumnInfo(EPGChannel.class);
        while (it.hasNext()) {
            EPGChannel ePGChannel = (EPGChannel) it.next();
            if (!map.containsKey(ePGChannel)) {
                if ((ePGChannel instanceof RealmObjectProxy) && !RealmObject.isFrozen(ePGChannel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) ePGChannel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(ePGChannel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(ePGChannel, Long.valueOf(jCreateRow));
                String strRealmGet$num = ePGChannel.realmGet$num();
                if (strRealmGet$num != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.numColKey, jCreateRow, strRealmGet$num, false);
                }
                String strRealmGet$name = ePGChannel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                }
                String strRealmGet$stream_type = ePGChannel.realmGet$stream_type();
                if (strRealmGet$stream_type != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
                }
                String strRealmGet$stream_id = ePGChannel.realmGet$stream_id();
                if (strRealmGet$stream_id != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
                }
                String strRealmGet$stream_icon = ePGChannel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                }
                String strRealmGet$Id = ePGChannel.realmGet$Id();
                if (strRealmGet$Id != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.IdColKey, jCreateRow, strRealmGet$Id, false);
                }
                String strRealmGet$added = ePGChannel.realmGet$added();
                if (strRealmGet$added != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
                }
                String strRealmGet$category_id = ePGChannel.realmGet$category_id();
                if (strRealmGet$category_id != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
                }
                String strRealmGet$custom_sid = ePGChannel.realmGet$custom_sid();
                if (strRealmGet$custom_sid != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
                }
                String strRealmGet$tv_archive = ePGChannel.realmGet$tv_archive();
                if (strRealmGet$tv_archive != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archiveColKey, jCreateRow, strRealmGet$tv_archive, false);
                }
                String strRealmGet$direct_source = ePGChannel.realmGet$direct_source();
                if (strRealmGet$direct_source != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.direct_sourceColKey, jCreateRow, strRealmGet$direct_source, false);
                }
                String strRealmGet$tv_archive_duration = ePGChannel.realmGet$tv_archive_duration();
                if (strRealmGet$tv_archive_duration != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archive_durationColKey, jCreateRow, strRealmGet$tv_archive_duration, false);
                }
                String strRealmGet$url = ePGChannel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                }
                String strRealmGet$category_name = ePGChannel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                }
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_lockedColKey, jCreateRow, ePGChannel.realmGet$is_locked(), false);
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_favoriteColKey, jCreateRow, ePGChannel.realmGet$is_favorite(), false);
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_recentColKey, jCreateRow, ePGChannel.realmGet$is_recent(), false);
                Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.recent_posColKey, jCreateRow, ePGChannel.realmGet$recent_pos(), false);
                Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.cellColKey, jCreateRow, ePGChannel.realmGet$cell(), false);
                Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.channelIDColKey, jCreateRow, ePGChannel.realmGet$channelID(), false);
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.selectedColKey, jCreateRow, ePGChannel.realmGet$selected(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(EPGChannel.class);
        long nativePtr = table.getNativePtr();
        EPGChannelColumnInfo ePGChannelColumnInfo = (EPGChannelColumnInfo) realm.getSchema().getColumnInfo(EPGChannel.class);
        while (it.hasNext()) {
            EPGChannel ePGChannel = (EPGChannel) it.next();
            if (!map.containsKey(ePGChannel)) {
                if ((ePGChannel instanceof RealmObjectProxy) && !RealmObject.isFrozen(ePGChannel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) ePGChannel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(ePGChannel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(ePGChannel, Long.valueOf(jCreateRow));
                String strRealmGet$num = ePGChannel.realmGet$num();
                if (strRealmGet$num != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.numColKey, jCreateRow, strRealmGet$num, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.numColKey, jCreateRow, false);
                }
                String strRealmGet$name = ePGChannel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.nameColKey, jCreateRow, false);
                }
                String strRealmGet$stream_type = ePGChannel.realmGet$stream_type();
                if (strRealmGet$stream_type != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.stream_typeColKey, jCreateRow, false);
                }
                String strRealmGet$stream_id = ePGChannel.realmGet$stream_id();
                if (strRealmGet$stream_id != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.stream_idColKey, jCreateRow, false);
                }
                String strRealmGet$stream_icon = ePGChannel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.stream_iconColKey, jCreateRow, false);
                }
                String strRealmGet$Id = ePGChannel.realmGet$Id();
                if (strRealmGet$Id != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.IdColKey, jCreateRow, strRealmGet$Id, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.IdColKey, jCreateRow, false);
                }
                String strRealmGet$added = ePGChannel.realmGet$added();
                if (strRealmGet$added != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.addedColKey, jCreateRow, false);
                }
                String strRealmGet$category_id = ePGChannel.realmGet$category_id();
                if (strRealmGet$category_id != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.category_idColKey, jCreateRow, false);
                }
                String strRealmGet$custom_sid = ePGChannel.realmGet$custom_sid();
                if (strRealmGet$custom_sid != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.custom_sidColKey, jCreateRow, false);
                }
                String strRealmGet$tv_archive = ePGChannel.realmGet$tv_archive();
                if (strRealmGet$tv_archive != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archiveColKey, jCreateRow, strRealmGet$tv_archive, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.tv_archiveColKey, jCreateRow, false);
                }
                String strRealmGet$direct_source = ePGChannel.realmGet$direct_source();
                if (strRealmGet$direct_source != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.direct_sourceColKey, jCreateRow, strRealmGet$direct_source, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.direct_sourceColKey, jCreateRow, false);
                }
                String strRealmGet$tv_archive_duration = ePGChannel.realmGet$tv_archive_duration();
                if (strRealmGet$tv_archive_duration != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.tv_archive_durationColKey, jCreateRow, strRealmGet$tv_archive_duration, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.tv_archive_durationColKey, jCreateRow, false);
                }
                String strRealmGet$url = ePGChannel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.urlColKey, jCreateRow, false);
                }
                String strRealmGet$category_name = ePGChannel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, ePGChannelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                } else {
                    Table.nativeSetNull(nativePtr, ePGChannelColumnInfo.category_nameColKey, jCreateRow, false);
                }
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_lockedColKey, jCreateRow, ePGChannel.realmGet$is_locked(), false);
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_favoriteColKey, jCreateRow, ePGChannel.realmGet$is_favorite(), false);
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.is_recentColKey, jCreateRow, ePGChannel.realmGet$is_recent(), false);
                Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.recent_posColKey, jCreateRow, ePGChannel.realmGet$recent_pos(), false);
                Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.cellColKey, jCreateRow, ePGChannel.realmGet$cell(), false);
                Table.nativeSetLong(nativePtr, ePGChannelColumnInfo.channelIDColKey, jCreateRow, ePGChannel.realmGet$channelID(), false);
                Table.nativeSetBoolean(nativePtr, ePGChannelColumnInfo.selectedColKey, jCreateRow, ePGChannel.realmGet$selected(), false);
            }
        }
    }
}
