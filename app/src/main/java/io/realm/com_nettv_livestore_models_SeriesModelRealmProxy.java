package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.gms.common.internal.ImagesContract;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.models.SeriesModel;
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
public class com_nettv_livestore_models_SeriesModelRealmProxy extends SeriesModel implements RealmObjectProxy {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private SeriesModelColumnInfo columnInfo;
    private ProxyState<SeriesModel> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "SeriesModel";
    }

    public static final class SeriesModelColumnInfo extends ColumnInfo {
        public long castColKey;
        public long category_idColKey;
        public long category_nameColKey;
        public long directorColKey;
        public long episode_posColKey;
        public long genreColKey;
        public long is_favoriteColKey;
        public long is_recentColKey;
        public long is_watchedColKey;
        public long last_modifiedColKey;
        public long nameColKey;
        public long numColKey;
        public long plotColKey;
        public long ratingColKey;
        public long rating_5basedColKey;
        public long releaseDateColKey;
        public long season_posColKey;
        public long series_idColKey;
        public long stream_iconColKey;
        public long stream_typeColKey;
        public long tmdbColKey;
        public long urlColKey;
        public long youtubeColKey;

        public SeriesModelColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(23);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.numColKey = addColumnDetails("num", "num", objectSchemaInfo);
            this.nameColKey = addColumnDetails("name", "name", objectSchemaInfo);
            this.stream_typeColKey = addColumnDetails("stream_type", "stream_type", objectSchemaInfo);
            this.series_idColKey = addColumnDetails("series_id", "series_id", objectSchemaInfo);
            this.stream_iconColKey = addColumnDetails("stream_icon", "stream_icon", objectSchemaInfo);
            this.youtubeColKey = addColumnDetails("youtube", "youtube", objectSchemaInfo);
            this.plotColKey = addColumnDetails("plot", "plot", objectSchemaInfo);
            this.castColKey = addColumnDetails("cast", "cast", objectSchemaInfo);
            this.directorColKey = addColumnDetails("director", "director", objectSchemaInfo);
            this.genreColKey = addColumnDetails("genre", "genre", objectSchemaInfo);
            this.releaseDateColKey = addColumnDetails("releaseDate", "releaseDate", objectSchemaInfo);
            this.ratingColKey = addColumnDetails("rating", "rating", objectSchemaInfo);
            this.rating_5basedColKey = addColumnDetails("rating_5based", "rating_5based", objectSchemaInfo);
            this.category_idColKey = addColumnDetails("category_id", "category_id", objectSchemaInfo);
            this.last_modifiedColKey = addColumnDetails("last_modified", "last_modified", objectSchemaInfo);
            this.tmdbColKey = addColumnDetails("tmdb", "tmdb", objectSchemaInfo);
            this.season_posColKey = addColumnDetails("season_pos", "season_pos", objectSchemaInfo);
            this.episode_posColKey = addColumnDetails("episode_pos", "episode_pos", objectSchemaInfo);
            this.is_watchedColKey = addColumnDetails("is_watched", "is_watched", objectSchemaInfo);
            this.is_favoriteColKey = addColumnDetails("is_favorite", "is_favorite", objectSchemaInfo);
            this.is_recentColKey = addColumnDetails("is_recent", "is_recent", objectSchemaInfo);
            this.category_nameColKey = addColumnDetails("category_name", "category_name", objectSchemaInfo);
            this.urlColKey = addColumnDetails(ImagesContract.URL, ImagesContract.URL, objectSchemaInfo);
        }

        @Override // io.realm.internal.ColumnInfo
        public final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            SeriesModelColumnInfo seriesModelColumnInfo = (SeriesModelColumnInfo) columnInfo;
            SeriesModelColumnInfo seriesModelColumnInfo2 = (SeriesModelColumnInfo) columnInfo2;
            seriesModelColumnInfo2.numColKey = seriesModelColumnInfo.numColKey;
            seriesModelColumnInfo2.nameColKey = seriesModelColumnInfo.nameColKey;
            seriesModelColumnInfo2.stream_typeColKey = seriesModelColumnInfo.stream_typeColKey;
            seriesModelColumnInfo2.series_idColKey = seriesModelColumnInfo.series_idColKey;
            seriesModelColumnInfo2.stream_iconColKey = seriesModelColumnInfo.stream_iconColKey;
            seriesModelColumnInfo2.youtubeColKey = seriesModelColumnInfo.youtubeColKey;
            seriesModelColumnInfo2.plotColKey = seriesModelColumnInfo.plotColKey;
            seriesModelColumnInfo2.castColKey = seriesModelColumnInfo.castColKey;
            seriesModelColumnInfo2.directorColKey = seriesModelColumnInfo.directorColKey;
            seriesModelColumnInfo2.genreColKey = seriesModelColumnInfo.genreColKey;
            seriesModelColumnInfo2.releaseDateColKey = seriesModelColumnInfo.releaseDateColKey;
            seriesModelColumnInfo2.ratingColKey = seriesModelColumnInfo.ratingColKey;
            seriesModelColumnInfo2.rating_5basedColKey = seriesModelColumnInfo.rating_5basedColKey;
            seriesModelColumnInfo2.category_idColKey = seriesModelColumnInfo.category_idColKey;
            seriesModelColumnInfo2.last_modifiedColKey = seriesModelColumnInfo.last_modifiedColKey;
            seriesModelColumnInfo2.tmdbColKey = seriesModelColumnInfo.tmdbColKey;
            seriesModelColumnInfo2.season_posColKey = seriesModelColumnInfo.season_posColKey;
            seriesModelColumnInfo2.episode_posColKey = seriesModelColumnInfo.episode_posColKey;
            seriesModelColumnInfo2.is_watchedColKey = seriesModelColumnInfo.is_watchedColKey;
            seriesModelColumnInfo2.is_favoriteColKey = seriesModelColumnInfo.is_favoriteColKey;
            seriesModelColumnInfo2.is_recentColKey = seriesModelColumnInfo.is_recentColKey;
            seriesModelColumnInfo2.category_nameColKey = seriesModelColumnInfo.category_nameColKey;
            seriesModelColumnInfo2.urlColKey = seriesModelColumnInfo.urlColKey;
        }
    }

    public com_nettv_livestore_models_SeriesModelRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static SeriesModel copy(Realm realm, SeriesModelColumnInfo seriesModelColumnInfo, SeriesModel seriesModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(seriesModel);
        if (realmObjectProxy != null) {
            return (SeriesModel) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(SeriesModel.class), set);
        osObjectBuilder.addInteger(seriesModelColumnInfo.numColKey, Integer.valueOf(seriesModel.realmGet$num()));
        osObjectBuilder.addString(seriesModelColumnInfo.nameColKey, seriesModel.realmGet$name());
        osObjectBuilder.addString(seriesModelColumnInfo.stream_typeColKey, seriesModel.realmGet$stream_type());
        osObjectBuilder.addString(seriesModelColumnInfo.series_idColKey, seriesModel.realmGet$series_id());
        osObjectBuilder.addString(seriesModelColumnInfo.stream_iconColKey, seriesModel.realmGet$stream_icon());
        osObjectBuilder.addString(seriesModelColumnInfo.youtubeColKey, seriesModel.realmGet$youtube());
        osObjectBuilder.addString(seriesModelColumnInfo.plotColKey, seriesModel.realmGet$plot());
        osObjectBuilder.addString(seriesModelColumnInfo.castColKey, seriesModel.realmGet$cast());
        osObjectBuilder.addString(seriesModelColumnInfo.directorColKey, seriesModel.realmGet$director());
        osObjectBuilder.addString(seriesModelColumnInfo.genreColKey, seriesModel.realmGet$genre());
        osObjectBuilder.addString(seriesModelColumnInfo.releaseDateColKey, seriesModel.realmGet$releaseDate());
        osObjectBuilder.addString(seriesModelColumnInfo.ratingColKey, seriesModel.realmGet$rating());
        osObjectBuilder.addFloat(seriesModelColumnInfo.rating_5basedColKey, Float.valueOf(seriesModel.realmGet$rating_5based()));
        osObjectBuilder.addString(seriesModelColumnInfo.category_idColKey, seriesModel.realmGet$category_id());
        osObjectBuilder.addString(seriesModelColumnInfo.last_modifiedColKey, seriesModel.realmGet$last_modified());
        osObjectBuilder.addString(seriesModelColumnInfo.tmdbColKey, seriesModel.realmGet$tmdb());
        osObjectBuilder.addInteger(seriesModelColumnInfo.season_posColKey, Integer.valueOf(seriesModel.realmGet$season_pos()));
        osObjectBuilder.addInteger(seriesModelColumnInfo.episode_posColKey, Integer.valueOf(seriesModel.realmGet$episode_pos()));
        osObjectBuilder.addBoolean(seriesModelColumnInfo.is_watchedColKey, Boolean.valueOf(seriesModel.realmGet$is_watched()));
        osObjectBuilder.addBoolean(seriesModelColumnInfo.is_favoriteColKey, Boolean.valueOf(seriesModel.realmGet$is_favorite()));
        osObjectBuilder.addBoolean(seriesModelColumnInfo.is_recentColKey, Boolean.valueOf(seriesModel.realmGet$is_recent()));
        osObjectBuilder.addString(seriesModelColumnInfo.category_nameColKey, seriesModel.realmGet$category_name());
        osObjectBuilder.addString(seriesModelColumnInfo.urlColKey, seriesModel.realmGet$url());
        UncheckedRow uncheckedRowCreateNewObject = osObjectBuilder.createNewObject();
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(realm, uncheckedRowCreateNewObject, realm.getSchema().getColumnInfo(SeriesModel.class), false, Collections.emptyList());
        com_nettv_livestore_models_SeriesModelRealmProxy com_nettv_livestore_models_seriesmodelrealmproxy = new com_nettv_livestore_models_SeriesModelRealmProxy();
        realmObjectContext.clear();
        map.put(seriesModel, com_nettv_livestore_models_seriesmodelrealmproxy);
        return com_nettv_livestore_models_seriesmodelrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SeriesModel copyOrUpdate(Realm realm, SeriesModelColumnInfo seriesModelColumnInfo, SeriesModel seriesModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((seriesModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(seriesModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) seriesModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return seriesModel;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(seriesModel);
        return realmModel != null ? (SeriesModel) realmModel : copy(realm, seriesModelColumnInfo, seriesModel, z, map, set);
    }

    public static SeriesModelColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new SeriesModelColumnInfo(osSchemaInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SeriesModel createDetachedCopy(SeriesModel seriesModel, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        SeriesModel seriesModel2;
        if (i > i2 || seriesModel == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(seriesModel);
        if (cacheData == null) {
            seriesModel2 = new SeriesModel();
            map.put(seriesModel, new RealmObjectProxy.CacheData<>(i, seriesModel2));
        } else {
            if (i >= cacheData.minDepth) {
                return (SeriesModel) cacheData.object;
            }
            SeriesModel seriesModel3 = (SeriesModel) cacheData.object;
            cacheData.minDepth = i;
            seriesModel2 = seriesModel3;
        }
        seriesModel2.realmSet$num(seriesModel.realmGet$num());
        seriesModel2.realmSet$name(seriesModel.realmGet$name());
        seriesModel2.realmSet$stream_type(seriesModel.realmGet$stream_type());
        seriesModel2.realmSet$series_id(seriesModel.realmGet$series_id());
        seriesModel2.realmSet$stream_icon(seriesModel.realmGet$stream_icon());
        seriesModel2.realmSet$youtube(seriesModel.realmGet$youtube());
        seriesModel2.realmSet$plot(seriesModel.realmGet$plot());
        seriesModel2.realmSet$cast(seriesModel.realmGet$cast());
        seriesModel2.realmSet$director(seriesModel.realmGet$director());
        seriesModel2.realmSet$genre(seriesModel.realmGet$genre());
        seriesModel2.realmSet$releaseDate(seriesModel.realmGet$releaseDate());
        seriesModel2.realmSet$rating(seriesModel.realmGet$rating());
        seriesModel2.realmSet$rating_5based(seriesModel.realmGet$rating_5based());
        seriesModel2.realmSet$category_id(seriesModel.realmGet$category_id());
        seriesModel2.realmSet$last_modified(seriesModel.realmGet$last_modified());
        seriesModel2.realmSet$tmdb(seriesModel.realmGet$tmdb());
        seriesModel2.realmSet$season_pos(seriesModel.realmGet$season_pos());
        seriesModel2.realmSet$episode_pos(seriesModel.realmGet$episode_pos());
        seriesModel2.realmSet$is_watched(seriesModel.realmGet$is_watched());
        seriesModel2.realmSet$is_favorite(seriesModel.realmGet$is_favorite());
        seriesModel2.realmSet$is_recent(seriesModel.realmGet$is_recent());
        seriesModel2.realmSet$category_name(seriesModel.realmGet$category_name());
        seriesModel2.realmSet$url(seriesModel.realmGet$url());
        return seriesModel2;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 23, 0);
        RealmFieldType realmFieldType = RealmFieldType.INTEGER;
        builder.addPersistedProperty("", "num", realmFieldType, false, false, true);
        RealmFieldType realmFieldType2 = RealmFieldType.STRING;
        builder.addPersistedProperty("", "name", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "stream_type", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "series_id", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "stream_icon", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "youtube", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "plot", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "cast", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "director", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "genre", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "releaseDate", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "rating", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "rating_5based", RealmFieldType.FLOAT, false, false, true);
        builder.addPersistedProperty("", "category_id", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "last_modified", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "tmdb", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "season_pos", realmFieldType, false, false, true);
        builder.addPersistedProperty("", "episode_pos", realmFieldType, false, false, true);
        RealmFieldType realmFieldType3 = RealmFieldType.BOOLEAN;
        builder.addPersistedProperty("", "is_watched", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "is_favorite", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "is_recent", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "category_name", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", ImagesContract.URL, realmFieldType2, false, false, false);
        return builder.build();
    }

    public static SeriesModel createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        SeriesModel seriesModel = (SeriesModel) realm.createObjectInternal(SeriesModel.class, Collections.emptyList());
        if (jSONObject.has("num")) {
            if (jSONObject.isNull("num")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'num' to null.");
            }
            seriesModel.realmSet$num(jSONObject.getInt("num"));
        }
        if (jSONObject.has("name")) {
            if (jSONObject.isNull("name")) {
                seriesModel.realmSet$name(null);
            } else {
                seriesModel.realmSet$name(jSONObject.getString("name"));
            }
        }
        if (jSONObject.has("stream_type")) {
            if (jSONObject.isNull("stream_type")) {
                seriesModel.realmSet$stream_type(null);
            } else {
                seriesModel.realmSet$stream_type(jSONObject.getString("stream_type"));
            }
        }
        if (jSONObject.has("series_id")) {
            if (jSONObject.isNull("series_id")) {
                seriesModel.realmSet$series_id(null);
            } else {
                seriesModel.realmSet$series_id(jSONObject.getString("series_id"));
            }
        }
        if (jSONObject.has("stream_icon")) {
            if (jSONObject.isNull("stream_icon")) {
                seriesModel.realmSet$stream_icon(null);
            } else {
                seriesModel.realmSet$stream_icon(jSONObject.getString("stream_icon"));
            }
        }
        if (jSONObject.has("youtube")) {
            if (jSONObject.isNull("youtube")) {
                seriesModel.realmSet$youtube(null);
            } else {
                seriesModel.realmSet$youtube(jSONObject.getString("youtube"));
            }
        }
        if (jSONObject.has("plot")) {
            if (jSONObject.isNull("plot")) {
                seriesModel.realmSet$plot(null);
            } else {
                seriesModel.realmSet$plot(jSONObject.getString("plot"));
            }
        }
        if (jSONObject.has("cast")) {
            if (jSONObject.isNull("cast")) {
                seriesModel.realmSet$cast(null);
            } else {
                seriesModel.realmSet$cast(jSONObject.getString("cast"));
            }
        }
        if (jSONObject.has("director")) {
            if (jSONObject.isNull("director")) {
                seriesModel.realmSet$director(null);
            } else {
                seriesModel.realmSet$director(jSONObject.getString("director"));
            }
        }
        if (jSONObject.has("genre")) {
            if (jSONObject.isNull("genre")) {
                seriesModel.realmSet$genre(null);
            } else {
                seriesModel.realmSet$genre(jSONObject.getString("genre"));
            }
        }
        if (jSONObject.has("releaseDate")) {
            if (jSONObject.isNull("releaseDate")) {
                seriesModel.realmSet$releaseDate(null);
            } else {
                seriesModel.realmSet$releaseDate(jSONObject.getString("releaseDate"));
            }
        }
        if (jSONObject.has("rating")) {
            if (jSONObject.isNull("rating")) {
                seriesModel.realmSet$rating(null);
            } else {
                seriesModel.realmSet$rating(jSONObject.getString("rating"));
            }
        }
        if (jSONObject.has("rating_5based")) {
            if (jSONObject.isNull("rating_5based")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'rating_5based' to null.");
            }
            seriesModel.realmSet$rating_5based((float) jSONObject.getDouble("rating_5based"));
        }
        if (jSONObject.has("category_id")) {
            if (jSONObject.isNull("category_id")) {
                seriesModel.realmSet$category_id(null);
            } else {
                seriesModel.realmSet$category_id(jSONObject.getString("category_id"));
            }
        }
        if (jSONObject.has("last_modified")) {
            if (jSONObject.isNull("last_modified")) {
                seriesModel.realmSet$last_modified(null);
            } else {
                seriesModel.realmSet$last_modified(jSONObject.getString("last_modified"));
            }
        }
        if (jSONObject.has("tmdb")) {
            if (jSONObject.isNull("tmdb")) {
                seriesModel.realmSet$tmdb(null);
            } else {
                seriesModel.realmSet$tmdb(jSONObject.getString("tmdb"));
            }
        }
        if (jSONObject.has("season_pos")) {
            if (jSONObject.isNull("season_pos")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'season_pos' to null.");
            }
            seriesModel.realmSet$season_pos(jSONObject.getInt("season_pos"));
        }
        if (jSONObject.has("episode_pos")) {
            if (jSONObject.isNull("episode_pos")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'episode_pos' to null.");
            }
            seriesModel.realmSet$episode_pos(jSONObject.getInt("episode_pos"));
        }
        if (jSONObject.has("is_watched")) {
            if (jSONObject.isNull("is_watched")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_watched' to null.");
            }
            seriesModel.realmSet$is_watched(jSONObject.getBoolean("is_watched"));
        }
        if (jSONObject.has("is_favorite")) {
            if (jSONObject.isNull("is_favorite")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_favorite' to null.");
            }
            seriesModel.realmSet$is_favorite(jSONObject.getBoolean("is_favorite"));
        }
        if (jSONObject.has("is_recent")) {
            if (jSONObject.isNull("is_recent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_recent' to null.");
            }
            seriesModel.realmSet$is_recent(jSONObject.getBoolean("is_recent"));
        }
        if (jSONObject.has("category_name")) {
            if (jSONObject.isNull("category_name")) {
                seriesModel.realmSet$category_name(null);
            } else {
                seriesModel.realmSet$category_name(jSONObject.getString("category_name"));
            }
        }
        if (jSONObject.has(ImagesContract.URL)) {
            if (jSONObject.isNull(ImagesContract.URL)) {
                seriesModel.realmSet$url(null);
            } else {
                seriesModel.realmSet$url(jSONObject.getString(ImagesContract.URL));
            }
        }
        return seriesModel;
    }

    @TargetApi(11)
    public static SeriesModel createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        SeriesModel seriesModel = new SeriesModel();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("num")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'num' to null.");
                }
                seriesModel.realmSet$num(jsonReader.nextInt());
            } else if (strNextName.equals("name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$name(null);
                }
            } else if (strNextName.equals("stream_type")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$stream_type(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$stream_type(null);
                }
            } else if (strNextName.equals("series_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$series_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$series_id(null);
                }
            } else if (strNextName.equals("stream_icon")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$stream_icon(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$stream_icon(null);
                }
            } else if (strNextName.equals("youtube")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$youtube(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$youtube(null);
                }
            } else if (strNextName.equals("plot")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$plot(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$plot(null);
                }
            } else if (strNextName.equals("cast")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$cast(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$cast(null);
                }
            } else if (strNextName.equals("director")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$director(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$director(null);
                }
            } else if (strNextName.equals("genre")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$genre(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$genre(null);
                }
            } else if (strNextName.equals("releaseDate")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$releaseDate(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$releaseDate(null);
                }
            } else if (strNextName.equals("rating")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$rating(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$rating(null);
                }
            } else if (strNextName.equals("rating_5based")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'rating_5based' to null.");
                }
                seriesModel.realmSet$rating_5based((float) jsonReader.nextDouble());
            } else if (strNextName.equals("category_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$category_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$category_id(null);
                }
            } else if (strNextName.equals("last_modified")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$last_modified(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$last_modified(null);
                }
            } else if (strNextName.equals("tmdb")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$tmdb(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$tmdb(null);
                }
            } else if (strNextName.equals("season_pos")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'season_pos' to null.");
                }
                seriesModel.realmSet$season_pos(jsonReader.nextInt());
            } else if (strNextName.equals("episode_pos")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'episode_pos' to null.");
                }
                seriesModel.realmSet$episode_pos(jsonReader.nextInt());
            } else if (strNextName.equals("is_watched")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_watched' to null.");
                }
                seriesModel.realmSet$is_watched(jsonReader.nextBoolean());
            } else if (strNextName.equals("is_favorite")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_favorite' to null.");
                }
                seriesModel.realmSet$is_favorite(jsonReader.nextBoolean());
            } else if (strNextName.equals("is_recent")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_recent' to null.");
                }
                seriesModel.realmSet$is_recent(jsonReader.nextBoolean());
            } else if (strNextName.equals("category_name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    seriesModel.realmSet$category_name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    seriesModel.realmSet$category_name(null);
                }
            } else if (!strNextName.equals(ImagesContract.URL)) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                seriesModel.realmSet$url(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                seriesModel.realmSet$url(null);
            }
        }
        jsonReader.endObject();
        return (SeriesModel) realm.copyToRealm(seriesModel, new ImportFlag[0]);
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, SeriesModel seriesModel, Map<RealmModel, Long> map) {
        if ((seriesModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(seriesModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) seriesModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(SeriesModel.class);
        long nativePtr = table.getNativePtr();
        SeriesModelColumnInfo seriesModelColumnInfo = (SeriesModelColumnInfo) realm.getSchema().getColumnInfo(SeriesModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(seriesModel, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, seriesModelColumnInfo.numColKey, jCreateRow, seriesModel.realmGet$num(), false);
        String strRealmGet$name = seriesModel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        }
        String strRealmGet$stream_type = seriesModel.realmGet$stream_type();
        if (strRealmGet$stream_type != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
        }
        String strRealmGet$series_id = seriesModel.realmGet$series_id();
        if (strRealmGet$series_id != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.series_idColKey, jCreateRow, strRealmGet$series_id, false);
        }
        String strRealmGet$stream_icon = seriesModel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        }
        String strRealmGet$youtube = seriesModel.realmGet$youtube();
        if (strRealmGet$youtube != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.youtubeColKey, jCreateRow, strRealmGet$youtube, false);
        }
        String strRealmGet$plot = seriesModel.realmGet$plot();
        if (strRealmGet$plot != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
        }
        String strRealmGet$cast = seriesModel.realmGet$cast();
        if (strRealmGet$cast != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.castColKey, jCreateRow, strRealmGet$cast, false);
        }
        String strRealmGet$director = seriesModel.realmGet$director();
        if (strRealmGet$director != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.directorColKey, jCreateRow, strRealmGet$director, false);
        }
        String strRealmGet$genre = seriesModel.realmGet$genre();
        if (strRealmGet$genre != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.genreColKey, jCreateRow, strRealmGet$genre, false);
        }
        String strRealmGet$releaseDate = seriesModel.realmGet$releaseDate();
        if (strRealmGet$releaseDate != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.releaseDateColKey, jCreateRow, strRealmGet$releaseDate, false);
        }
        String strRealmGet$rating = seriesModel.realmGet$rating();
        if (strRealmGet$rating != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
        }
        Table.nativeSetFloat(nativePtr, seriesModelColumnInfo.rating_5basedColKey, jCreateRow, seriesModel.realmGet$rating_5based(), false);
        String strRealmGet$category_id = seriesModel.realmGet$category_id();
        if (strRealmGet$category_id != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
        }
        String strRealmGet$last_modified = seriesModel.realmGet$last_modified();
        if (strRealmGet$last_modified != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.last_modifiedColKey, jCreateRow, strRealmGet$last_modified, false);
        }
        String strRealmGet$tmdb = seriesModel.realmGet$tmdb();
        if (strRealmGet$tmdb != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.tmdbColKey, jCreateRow, strRealmGet$tmdb, false);
        }
        Table.nativeSetLong(nativePtr, seriesModelColumnInfo.season_posColKey, jCreateRow, seriesModel.realmGet$season_pos(), false);
        Table.nativeSetLong(nativePtr, seriesModelColumnInfo.episode_posColKey, jCreateRow, seriesModel.realmGet$episode_pos(), false);
        Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_watchedColKey, jCreateRow, seriesModel.realmGet$is_watched(), false);
        Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_favoriteColKey, jCreateRow, seriesModel.realmGet$is_favorite(), false);
        Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_recentColKey, jCreateRow, seriesModel.realmGet$is_recent(), false);
        String strRealmGet$category_name = seriesModel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        }
        String strRealmGet$url = seriesModel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        }
        return jCreateRow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, SeriesModel seriesModel, Map<RealmModel, Long> map) {
        if ((seriesModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(seriesModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) seriesModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(SeriesModel.class);
        long nativePtr = table.getNativePtr();
        SeriesModelColumnInfo seriesModelColumnInfo = (SeriesModelColumnInfo) realm.getSchema().getColumnInfo(SeriesModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(seriesModel, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, seriesModelColumnInfo.numColKey, jCreateRow, seriesModel.realmGet$num(), false);
        String strRealmGet$name = seriesModel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.nameColKey, jCreateRow, false);
        }
        String strRealmGet$stream_type = seriesModel.realmGet$stream_type();
        if (strRealmGet$stream_type != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.stream_typeColKey, jCreateRow, false);
        }
        String strRealmGet$series_id = seriesModel.realmGet$series_id();
        if (strRealmGet$series_id != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.series_idColKey, jCreateRow, strRealmGet$series_id, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.series_idColKey, jCreateRow, false);
        }
        String strRealmGet$stream_icon = seriesModel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.stream_iconColKey, jCreateRow, false);
        }
        String strRealmGet$youtube = seriesModel.realmGet$youtube();
        if (strRealmGet$youtube != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.youtubeColKey, jCreateRow, strRealmGet$youtube, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.youtubeColKey, jCreateRow, false);
        }
        String strRealmGet$plot = seriesModel.realmGet$plot();
        if (strRealmGet$plot != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.plotColKey, jCreateRow, false);
        }
        String strRealmGet$cast = seriesModel.realmGet$cast();
        if (strRealmGet$cast != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.castColKey, jCreateRow, strRealmGet$cast, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.castColKey, jCreateRow, false);
        }
        String strRealmGet$director = seriesModel.realmGet$director();
        if (strRealmGet$director != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.directorColKey, jCreateRow, strRealmGet$director, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.directorColKey, jCreateRow, false);
        }
        String strRealmGet$genre = seriesModel.realmGet$genre();
        if (strRealmGet$genre != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.genreColKey, jCreateRow, strRealmGet$genre, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.genreColKey, jCreateRow, false);
        }
        String strRealmGet$releaseDate = seriesModel.realmGet$releaseDate();
        if (strRealmGet$releaseDate != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.releaseDateColKey, jCreateRow, strRealmGet$releaseDate, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.releaseDateColKey, jCreateRow, false);
        }
        String strRealmGet$rating = seriesModel.realmGet$rating();
        if (strRealmGet$rating != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.ratingColKey, jCreateRow, false);
        }
        Table.nativeSetFloat(nativePtr, seriesModelColumnInfo.rating_5basedColKey, jCreateRow, seriesModel.realmGet$rating_5based(), false);
        String strRealmGet$category_id = seriesModel.realmGet$category_id();
        if (strRealmGet$category_id != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.category_idColKey, jCreateRow, false);
        }
        String strRealmGet$last_modified = seriesModel.realmGet$last_modified();
        if (strRealmGet$last_modified != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.last_modifiedColKey, jCreateRow, strRealmGet$last_modified, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.last_modifiedColKey, jCreateRow, false);
        }
        String strRealmGet$tmdb = seriesModel.realmGet$tmdb();
        if (strRealmGet$tmdb != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.tmdbColKey, jCreateRow, strRealmGet$tmdb, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.tmdbColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, seriesModelColumnInfo.season_posColKey, jCreateRow, seriesModel.realmGet$season_pos(), false);
        Table.nativeSetLong(nativePtr, seriesModelColumnInfo.episode_posColKey, jCreateRow, seriesModel.realmGet$episode_pos(), false);
        Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_watchedColKey, jCreateRow, seriesModel.realmGet$is_watched(), false);
        Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_favoriteColKey, jCreateRow, seriesModel.realmGet$is_favorite(), false);
        Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_recentColKey, jCreateRow, seriesModel.realmGet$is_recent(), false);
        String strRealmGet$category_name = seriesModel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.category_nameColKey, jCreateRow, false);
        }
        String strRealmGet$url = seriesModel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, seriesModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        } else {
            Table.nativeSetNull(nativePtr, seriesModelColumnInfo.urlColKey, jCreateRow, false);
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
        com_nettv_livestore_models_SeriesModelRealmProxy com_nettv_livestore_models_seriesmodelrealmproxy = (com_nettv_livestore_models_SeriesModelRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_nettv_livestore_models_seriesmodelrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_nettv_livestore_models_seriesmodelrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_nettv_livestore_models_seriesmodelrealmproxy.proxyState.getRow$realm().getObjectKey();
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
        this.columnInfo = (SeriesModelColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<SeriesModel> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$cast() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.castColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$category_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_idColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$category_name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_nameColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$director() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.directorColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public int realmGet$episode_pos() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.episode_posColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$genre() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.genreColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public boolean realmGet$is_favorite() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_favoriteColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public boolean realmGet$is_recent() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_recentColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public boolean realmGet$is_watched() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_watchedColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$last_modified() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.last_modifiedColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.nameColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public int realmGet$num() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.numColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$plot() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.plotColKey);
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$rating() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.ratingColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public float realmGet$rating_5based() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getFloat(this.columnInfo.rating_5basedColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$releaseDate() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.releaseDateColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public int realmGet$season_pos() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.season_posColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$series_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.series_idColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$stream_icon() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_iconColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$stream_type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_typeColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$tmdb() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tmdbColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$url() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.urlColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$youtube() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.youtubeColKey);
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$cast(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.castColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.castColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.castColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.castColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$director(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.directorColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.directorColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.directorColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.directorColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$episode_pos(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.episode_posColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.episode_posColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$genre(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.genreColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.genreColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.genreColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.genreColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$is_favorite(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_favoriteColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_favoriteColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$is_recent(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_recentColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_recentColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$is_watched(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_watchedColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_watchedColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$last_modified(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.last_modifiedColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.last_modifiedColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.last_modifiedColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.last_modifiedColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$num(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.numColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.numColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$rating_5based(float f) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setFloat(this.columnInfo.rating_5basedColKey, f);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setFloat(this.columnInfo.rating_5basedColKey, row$realm.getObjectKey(), f, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$releaseDate(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.releaseDateColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.releaseDateColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.releaseDateColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.releaseDateColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$season_pos(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.season_posColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.season_posColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$series_id(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.series_idColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.series_idColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.series_idColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.series_idColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$tmdb(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.tmdbColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.tmdbColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.tmdbColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.tmdbColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.SeriesModel, io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$youtube(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.youtubeColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.youtubeColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.youtubeColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.youtubeColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("SeriesModel = proxy[");
        sb.append("{num:");
        sb.append(realmGet$num());
        sb.append("}");
        sb.append(",");
        sb.append("{name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$name() != null ? realmGet$name() : "null", "}", ",", "{stream_type:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_type() != null ? realmGet$stream_type() : "null", "}", ",", "{series_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$series_id() != null ? realmGet$series_id() : "null", "}", ",", "{stream_icon:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_icon() != null ? realmGet$stream_icon() : "null", "}", ",", "{youtube:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$youtube() != null ? realmGet$youtube() : "null", "}", ",", "{plot:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$plot() != null ? realmGet$plot() : "null", "}", ",", "{cast:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$cast() != null ? realmGet$cast() : "null", "}", ",", "{director:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$director() != null ? realmGet$director() : "null", "}", ",", "{genre:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$genre() != null ? realmGet$genre() : "null", "}", ",", "{releaseDate:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$releaseDate() != null ? realmGet$releaseDate() : "null", "}", ",", "{rating:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$rating() != null ? realmGet$rating() : "null", "}", ",", "{rating_5based:");
        sb.append(realmGet$rating_5based());
        sb.append("}");
        sb.append(",");
        sb.append("{category_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_id() != null ? realmGet$category_id() : "null", "}", ",", "{last_modified:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$last_modified() != null ? realmGet$last_modified() : "null", "}", ",", "{tmdb:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$tmdb() != null ? realmGet$tmdb() : "null", "}", ",", "{season_pos:");
        sb.append(realmGet$season_pos());
        sb.append("}");
        sb.append(",");
        sb.append("{episode_pos:");
        sb.append(realmGet$episode_pos());
        sb.append("}");
        sb.append(",");
        sb.append("{is_watched:");
        sb.append(realmGet$is_watched());
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
        sb.append("{category_name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_name() != null ? realmGet$category_name() : "null", "}", ",", "{url:");
        return Insets$$ExternalSyntheticOutline0.m(sb, realmGet$url() != null ? realmGet$url() : "null", "}", "]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(SeriesModel.class);
        long nativePtr = table.getNativePtr();
        SeriesModelColumnInfo seriesModelColumnInfo = (SeriesModelColumnInfo) realm.getSchema().getColumnInfo(SeriesModel.class);
        while (it.hasNext()) {
            SeriesModel seriesModel = (SeriesModel) it.next();
            if (!map.containsKey(seriesModel)) {
                if ((seriesModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(seriesModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) seriesModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(seriesModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(seriesModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, seriesModelColumnInfo.numColKey, jCreateRow, seriesModel.realmGet$num(), false);
                String strRealmGet$name = seriesModel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                }
                String strRealmGet$stream_type = seriesModel.realmGet$stream_type();
                if (strRealmGet$stream_type != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
                }
                String strRealmGet$series_id = seriesModel.realmGet$series_id();
                if (strRealmGet$series_id != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.series_idColKey, jCreateRow, strRealmGet$series_id, false);
                }
                String strRealmGet$stream_icon = seriesModel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                }
                String strRealmGet$youtube = seriesModel.realmGet$youtube();
                if (strRealmGet$youtube != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.youtubeColKey, jCreateRow, strRealmGet$youtube, false);
                }
                String strRealmGet$plot = seriesModel.realmGet$plot();
                if (strRealmGet$plot != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
                }
                String strRealmGet$cast = seriesModel.realmGet$cast();
                if (strRealmGet$cast != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.castColKey, jCreateRow, strRealmGet$cast, false);
                }
                String strRealmGet$director = seriesModel.realmGet$director();
                if (strRealmGet$director != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.directorColKey, jCreateRow, strRealmGet$director, false);
                }
                String strRealmGet$genre = seriesModel.realmGet$genre();
                if (strRealmGet$genre != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.genreColKey, jCreateRow, strRealmGet$genre, false);
                }
                String strRealmGet$releaseDate = seriesModel.realmGet$releaseDate();
                if (strRealmGet$releaseDate != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.releaseDateColKey, jCreateRow, strRealmGet$releaseDate, false);
                }
                String strRealmGet$rating = seriesModel.realmGet$rating();
                if (strRealmGet$rating != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
                }
                Table.nativeSetFloat(nativePtr, seriesModelColumnInfo.rating_5basedColKey, jCreateRow, seriesModel.realmGet$rating_5based(), false);
                String strRealmGet$category_id = seriesModel.realmGet$category_id();
                if (strRealmGet$category_id != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
                }
                String strRealmGet$last_modified = seriesModel.realmGet$last_modified();
                if (strRealmGet$last_modified != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.last_modifiedColKey, jCreateRow, strRealmGet$last_modified, false);
                }
                String strRealmGet$tmdb = seriesModel.realmGet$tmdb();
                if (strRealmGet$tmdb != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.tmdbColKey, jCreateRow, strRealmGet$tmdb, false);
                }
                Table.nativeSetLong(nativePtr, seriesModelColumnInfo.season_posColKey, jCreateRow, seriesModel.realmGet$season_pos(), false);
                Table.nativeSetLong(nativePtr, seriesModelColumnInfo.episode_posColKey, jCreateRow, seriesModel.realmGet$episode_pos(), false);
                Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_watchedColKey, jCreateRow, seriesModel.realmGet$is_watched(), false);
                Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_favoriteColKey, jCreateRow, seriesModel.realmGet$is_favorite(), false);
                Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_recentColKey, jCreateRow, seriesModel.realmGet$is_recent(), false);
                String strRealmGet$category_name = seriesModel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                }
                String strRealmGet$url = seriesModel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(SeriesModel.class);
        long nativePtr = table.getNativePtr();
        SeriesModelColumnInfo seriesModelColumnInfo = (SeriesModelColumnInfo) realm.getSchema().getColumnInfo(SeriesModel.class);
        while (it.hasNext()) {
            SeriesModel seriesModel = (SeriesModel) it.next();
            if (!map.containsKey(seriesModel)) {
                if ((seriesModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(seriesModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) seriesModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(seriesModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(seriesModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, seriesModelColumnInfo.numColKey, jCreateRow, seriesModel.realmGet$num(), false);
                String strRealmGet$name = seriesModel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.nameColKey, jCreateRow, false);
                }
                String strRealmGet$stream_type = seriesModel.realmGet$stream_type();
                if (strRealmGet$stream_type != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.stream_typeColKey, jCreateRow, false);
                }
                String strRealmGet$series_id = seriesModel.realmGet$series_id();
                if (strRealmGet$series_id != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.series_idColKey, jCreateRow, strRealmGet$series_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.series_idColKey, jCreateRow, false);
                }
                String strRealmGet$stream_icon = seriesModel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.stream_iconColKey, jCreateRow, false);
                }
                String strRealmGet$youtube = seriesModel.realmGet$youtube();
                if (strRealmGet$youtube != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.youtubeColKey, jCreateRow, strRealmGet$youtube, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.youtubeColKey, jCreateRow, false);
                }
                String strRealmGet$plot = seriesModel.realmGet$plot();
                if (strRealmGet$plot != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.plotColKey, jCreateRow, strRealmGet$plot, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.plotColKey, jCreateRow, false);
                }
                String strRealmGet$cast = seriesModel.realmGet$cast();
                if (strRealmGet$cast != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.castColKey, jCreateRow, strRealmGet$cast, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.castColKey, jCreateRow, false);
                }
                String strRealmGet$director = seriesModel.realmGet$director();
                if (strRealmGet$director != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.directorColKey, jCreateRow, strRealmGet$director, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.directorColKey, jCreateRow, false);
                }
                String strRealmGet$genre = seriesModel.realmGet$genre();
                if (strRealmGet$genre != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.genreColKey, jCreateRow, strRealmGet$genre, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.genreColKey, jCreateRow, false);
                }
                String strRealmGet$releaseDate = seriesModel.realmGet$releaseDate();
                if (strRealmGet$releaseDate != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.releaseDateColKey, jCreateRow, strRealmGet$releaseDate, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.releaseDateColKey, jCreateRow, false);
                }
                String strRealmGet$rating = seriesModel.realmGet$rating();
                if (strRealmGet$rating != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.ratingColKey, jCreateRow, false);
                }
                Table.nativeSetFloat(nativePtr, seriesModelColumnInfo.rating_5basedColKey, jCreateRow, seriesModel.realmGet$rating_5based(), false);
                String strRealmGet$category_id = seriesModel.realmGet$category_id();
                if (strRealmGet$category_id != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.category_idColKey, jCreateRow, false);
                }
                String strRealmGet$last_modified = seriesModel.realmGet$last_modified();
                if (strRealmGet$last_modified != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.last_modifiedColKey, jCreateRow, strRealmGet$last_modified, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.last_modifiedColKey, jCreateRow, false);
                }
                String strRealmGet$tmdb = seriesModel.realmGet$tmdb();
                if (strRealmGet$tmdb != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.tmdbColKey, jCreateRow, strRealmGet$tmdb, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.tmdbColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, seriesModelColumnInfo.season_posColKey, jCreateRow, seriesModel.realmGet$season_pos(), false);
                Table.nativeSetLong(nativePtr, seriesModelColumnInfo.episode_posColKey, jCreateRow, seriesModel.realmGet$episode_pos(), false);
                Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_watchedColKey, jCreateRow, seriesModel.realmGet$is_watched(), false);
                Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_favoriteColKey, jCreateRow, seriesModel.realmGet$is_favorite(), false);
                Table.nativeSetBoolean(nativePtr, seriesModelColumnInfo.is_recentColKey, jCreateRow, seriesModel.realmGet$is_recent(), false);
                String strRealmGet$category_name = seriesModel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.category_nameColKey, jCreateRow, false);
                }
                String strRealmGet$url = seriesModel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, seriesModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                } else {
                    Table.nativeSetNull(nativePtr, seriesModelColumnInfo.urlColKey, jCreateRow, false);
                }
            }
        }
    }
}
