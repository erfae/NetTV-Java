package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.gms.common.internal.ImagesContract;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.models.MovieModel;
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
public class com_nettv_livestore_models_MovieModelRealmProxy extends MovieModel implements RealmObjectProxy {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private MovieModelColumnInfo columnInfo;
    private ProxyState<MovieModel> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "MovieModel";
    }

    public static final class MovieModelColumnInfo extends ColumnInfo {
        public long addedColKey;
        public long category_idColKey;
        public long category_nameColKey;
        public long custom_sidColKey;
        public long extensionColKey;
        public long is_favoriteColKey;
        public long is_lockedColKey;
        public long is_recentColKey;
        public long nameColKey;
        public long numColKey;
        public long proColKey;
        public long ratingColKey;
        public long recent_milColKey;
        public long stream_iconColKey;
        public long stream_idColKey;
        public long stream_typeColKey;
        public long timeColKey;
        public long tmdb_idColKey;
        public long typeColKey;
        public long urlColKey;

        public MovieModelColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(20);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.numColKey = addColumnDetails("num", "num", objectSchemaInfo);
            this.nameColKey = addColumnDetails("name", "name", objectSchemaInfo);
            this.stream_typeColKey = addColumnDetails("stream_type", "stream_type", objectSchemaInfo);
            this.stream_idColKey = addColumnDetails("stream_id", "stream_id", objectSchemaInfo);
            this.stream_iconColKey = addColumnDetails("stream_icon", "stream_icon", objectSchemaInfo);
            this.extensionColKey = addColumnDetails("extension", "extension", objectSchemaInfo);
            this.typeColKey = addColumnDetails("type", "type", objectSchemaInfo);
            this.ratingColKey = addColumnDetails("rating", "rating", objectSchemaInfo);
            this.category_idColKey = addColumnDetails("category_id", "category_id", objectSchemaInfo);
            this.custom_sidColKey = addColumnDetails("custom_sid", "custom_sid", objectSchemaInfo);
            this.addedColKey = addColumnDetails("added", "added", objectSchemaInfo);
            this.tmdb_idColKey = addColumnDetails("tmdb_id", "tmdb_id", objectSchemaInfo);
            this.proColKey = addColumnDetails("pro", "pro", objectSchemaInfo);
            this.timeColKey = addColumnDetails("time", "time", objectSchemaInfo);
            this.recent_milColKey = addColumnDetails("recent_mil", "recent_mil", objectSchemaInfo);
            this.is_lockedColKey = addColumnDetails("is_locked", "is_locked", objectSchemaInfo);
            this.is_favoriteColKey = addColumnDetails("is_favorite", "is_favorite", objectSchemaInfo);
            this.is_recentColKey = addColumnDetails("is_recent", "is_recent", objectSchemaInfo);
            this.category_nameColKey = addColumnDetails("category_name", "category_name", objectSchemaInfo);
            this.urlColKey = addColumnDetails(ImagesContract.URL, ImagesContract.URL, objectSchemaInfo);
        }

        @Override // io.realm.internal.ColumnInfo
        public final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            MovieModelColumnInfo movieModelColumnInfo = (MovieModelColumnInfo) columnInfo;
            MovieModelColumnInfo movieModelColumnInfo2 = (MovieModelColumnInfo) columnInfo2;
            movieModelColumnInfo2.numColKey = movieModelColumnInfo.numColKey;
            movieModelColumnInfo2.nameColKey = movieModelColumnInfo.nameColKey;
            movieModelColumnInfo2.stream_typeColKey = movieModelColumnInfo.stream_typeColKey;
            movieModelColumnInfo2.stream_idColKey = movieModelColumnInfo.stream_idColKey;
            movieModelColumnInfo2.stream_iconColKey = movieModelColumnInfo.stream_iconColKey;
            movieModelColumnInfo2.extensionColKey = movieModelColumnInfo.extensionColKey;
            movieModelColumnInfo2.typeColKey = movieModelColumnInfo.typeColKey;
            movieModelColumnInfo2.ratingColKey = movieModelColumnInfo.ratingColKey;
            movieModelColumnInfo2.category_idColKey = movieModelColumnInfo.category_idColKey;
            movieModelColumnInfo2.custom_sidColKey = movieModelColumnInfo.custom_sidColKey;
            movieModelColumnInfo2.addedColKey = movieModelColumnInfo.addedColKey;
            movieModelColumnInfo2.tmdb_idColKey = movieModelColumnInfo.tmdb_idColKey;
            movieModelColumnInfo2.proColKey = movieModelColumnInfo.proColKey;
            movieModelColumnInfo2.timeColKey = movieModelColumnInfo.timeColKey;
            movieModelColumnInfo2.recent_milColKey = movieModelColumnInfo.recent_milColKey;
            movieModelColumnInfo2.is_lockedColKey = movieModelColumnInfo.is_lockedColKey;
            movieModelColumnInfo2.is_favoriteColKey = movieModelColumnInfo.is_favoriteColKey;
            movieModelColumnInfo2.is_recentColKey = movieModelColumnInfo.is_recentColKey;
            movieModelColumnInfo2.category_nameColKey = movieModelColumnInfo.category_nameColKey;
            movieModelColumnInfo2.urlColKey = movieModelColumnInfo.urlColKey;
        }
    }

    public com_nettv_livestore_models_MovieModelRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static MovieModel copy(Realm realm, MovieModelColumnInfo movieModelColumnInfo, MovieModel movieModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(movieModel);
        if (realmObjectProxy != null) {
            return (MovieModel) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(MovieModel.class), set);
        osObjectBuilder.addInteger(movieModelColumnInfo.numColKey, Integer.valueOf(movieModel.realmGet$num()));
        osObjectBuilder.addString(movieModelColumnInfo.nameColKey, movieModel.realmGet$name());
        osObjectBuilder.addString(movieModelColumnInfo.stream_typeColKey, movieModel.realmGet$stream_type());
        osObjectBuilder.addString(movieModelColumnInfo.stream_idColKey, movieModel.realmGet$stream_id());
        osObjectBuilder.addString(movieModelColumnInfo.stream_iconColKey, movieModel.realmGet$stream_icon());
        osObjectBuilder.addString(movieModelColumnInfo.extensionColKey, movieModel.realmGet$extension());
        osObjectBuilder.addString(movieModelColumnInfo.typeColKey, movieModel.realmGet$type());
        osObjectBuilder.addString(movieModelColumnInfo.ratingColKey, movieModel.realmGet$rating());
        osObjectBuilder.addString(movieModelColumnInfo.category_idColKey, movieModel.realmGet$category_id());
        osObjectBuilder.addString(movieModelColumnInfo.custom_sidColKey, movieModel.realmGet$custom_sid());
        osObjectBuilder.addString(movieModelColumnInfo.addedColKey, movieModel.realmGet$added());
        osObjectBuilder.addString(movieModelColumnInfo.tmdb_idColKey, movieModel.realmGet$tmdb_id());
        osObjectBuilder.addInteger(movieModelColumnInfo.proColKey, Integer.valueOf(movieModel.realmGet$pro()));
        osObjectBuilder.addInteger(movieModelColumnInfo.timeColKey, Long.valueOf(movieModel.realmGet$time()));
        osObjectBuilder.addInteger(movieModelColumnInfo.recent_milColKey, Long.valueOf(movieModel.realmGet$recent_mil()));
        osObjectBuilder.addBoolean(movieModelColumnInfo.is_lockedColKey, Boolean.valueOf(movieModel.realmGet$is_locked()));
        osObjectBuilder.addBoolean(movieModelColumnInfo.is_favoriteColKey, Boolean.valueOf(movieModel.realmGet$is_favorite()));
        osObjectBuilder.addBoolean(movieModelColumnInfo.is_recentColKey, Boolean.valueOf(movieModel.realmGet$is_recent()));
        osObjectBuilder.addString(movieModelColumnInfo.category_nameColKey, movieModel.realmGet$category_name());
        osObjectBuilder.addString(movieModelColumnInfo.urlColKey, movieModel.realmGet$url());
        UncheckedRow uncheckedRowCreateNewObject = osObjectBuilder.createNewObject();
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(realm, uncheckedRowCreateNewObject, realm.getSchema().getColumnInfo(MovieModel.class), false, Collections.emptyList());
        com_nettv_livestore_models_MovieModelRealmProxy com_nettv_livestore_models_moviemodelrealmproxy = new com_nettv_livestore_models_MovieModelRealmProxy();
        realmObjectContext.clear();
        map.put(movieModel, com_nettv_livestore_models_moviemodelrealmproxy);
        return com_nettv_livestore_models_moviemodelrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static MovieModel copyOrUpdate(Realm realm, MovieModelColumnInfo movieModelColumnInfo, MovieModel movieModel, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((movieModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(movieModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) movieModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return movieModel;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(movieModel);
        return realmModel != null ? (MovieModel) realmModel : copy(realm, movieModelColumnInfo, movieModel, z, map, set);
    }

    public static MovieModelColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new MovieModelColumnInfo(osSchemaInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static MovieModel createDetachedCopy(MovieModel movieModel, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        MovieModel movieModel2;
        if (i > i2 || movieModel == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(movieModel);
        if (cacheData == null) {
            movieModel2 = new MovieModel();
            map.put(movieModel, new RealmObjectProxy.CacheData<>(i, movieModel2));
        } else {
            if (i >= cacheData.minDepth) {
                return (MovieModel) cacheData.object;
            }
            MovieModel movieModel3 = (MovieModel) cacheData.object;
            cacheData.minDepth = i;
            movieModel2 = movieModel3;
        }
        movieModel2.realmSet$num(movieModel.realmGet$num());
        movieModel2.realmSet$name(movieModel.realmGet$name());
        movieModel2.realmSet$stream_type(movieModel.realmGet$stream_type());
        movieModel2.realmSet$stream_id(movieModel.realmGet$stream_id());
        movieModel2.realmSet$stream_icon(movieModel.realmGet$stream_icon());
        movieModel2.realmSet$extension(movieModel.realmGet$extension());
        movieModel2.realmSet$type(movieModel.realmGet$type());
        movieModel2.realmSet$rating(movieModel.realmGet$rating());
        movieModel2.realmSet$category_id(movieModel.realmGet$category_id());
        movieModel2.realmSet$custom_sid(movieModel.realmGet$custom_sid());
        movieModel2.realmSet$added(movieModel.realmGet$added());
        movieModel2.realmSet$tmdb_id(movieModel.realmGet$tmdb_id());
        movieModel2.realmSet$pro(movieModel.realmGet$pro());
        movieModel2.realmSet$time(movieModel.realmGet$time());
        movieModel2.realmSet$recent_mil(movieModel.realmGet$recent_mil());
        movieModel2.realmSet$is_locked(movieModel.realmGet$is_locked());
        movieModel2.realmSet$is_favorite(movieModel.realmGet$is_favorite());
        movieModel2.realmSet$is_recent(movieModel.realmGet$is_recent());
        movieModel2.realmSet$category_name(movieModel.realmGet$category_name());
        movieModel2.realmSet$url(movieModel.realmGet$url());
        return movieModel2;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 20, 0);
        RealmFieldType realmFieldType = RealmFieldType.INTEGER;
        builder.addPersistedProperty("", "num", realmFieldType, false, false, true);
        RealmFieldType realmFieldType2 = RealmFieldType.STRING;
        builder.addPersistedProperty("", "name", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "stream_type", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "stream_id", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "stream_icon", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "extension", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "type", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "rating", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "category_id", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "custom_sid", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "added", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "tmdb_id", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", "pro", realmFieldType, false, false, true);
        builder.addPersistedProperty("", "time", realmFieldType, false, false, true);
        builder.addPersistedProperty("", "recent_mil", realmFieldType, false, false, true);
        RealmFieldType realmFieldType3 = RealmFieldType.BOOLEAN;
        builder.addPersistedProperty("", "is_locked", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "is_favorite", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "is_recent", realmFieldType3, false, false, true);
        builder.addPersistedProperty("", "category_name", realmFieldType2, false, false, false);
        builder.addPersistedProperty("", ImagesContract.URL, realmFieldType2, false, false, false);
        return builder.build();
    }

    public static MovieModel createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        MovieModel movieModel = (MovieModel) realm.createObjectInternal(MovieModel.class, Collections.emptyList());
        if (jSONObject.has("num")) {
            if (jSONObject.isNull("num")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'num' to null.");
            }
            movieModel.realmSet$num(jSONObject.getInt("num"));
        }
        if (jSONObject.has("name")) {
            if (jSONObject.isNull("name")) {
                movieModel.realmSet$name(null);
            } else {
                movieModel.realmSet$name(jSONObject.getString("name"));
            }
        }
        if (jSONObject.has("stream_type")) {
            if (jSONObject.isNull("stream_type")) {
                movieModel.realmSet$stream_type(null);
            } else {
                movieModel.realmSet$stream_type(jSONObject.getString("stream_type"));
            }
        }
        if (jSONObject.has("stream_id")) {
            if (jSONObject.isNull("stream_id")) {
                movieModel.realmSet$stream_id(null);
            } else {
                movieModel.realmSet$stream_id(jSONObject.getString("stream_id"));
            }
        }
        if (jSONObject.has("stream_icon")) {
            if (jSONObject.isNull("stream_icon")) {
                movieModel.realmSet$stream_icon(null);
            } else {
                movieModel.realmSet$stream_icon(jSONObject.getString("stream_icon"));
            }
        }
        if (jSONObject.has("extension")) {
            if (jSONObject.isNull("extension")) {
                movieModel.realmSet$extension(null);
            } else {
                movieModel.realmSet$extension(jSONObject.getString("extension"));
            }
        }
        if (jSONObject.has("type")) {
            if (jSONObject.isNull("type")) {
                movieModel.realmSet$type(null);
            } else {
                movieModel.realmSet$type(jSONObject.getString("type"));
            }
        }
        if (jSONObject.has("rating")) {
            if (jSONObject.isNull("rating")) {
                movieModel.realmSet$rating(null);
            } else {
                movieModel.realmSet$rating(jSONObject.getString("rating"));
            }
        }
        if (jSONObject.has("category_id")) {
            if (jSONObject.isNull("category_id")) {
                movieModel.realmSet$category_id(null);
            } else {
                movieModel.realmSet$category_id(jSONObject.getString("category_id"));
            }
        }
        if (jSONObject.has("custom_sid")) {
            if (jSONObject.isNull("custom_sid")) {
                movieModel.realmSet$custom_sid(null);
            } else {
                movieModel.realmSet$custom_sid(jSONObject.getString("custom_sid"));
            }
        }
        if (jSONObject.has("added")) {
            if (jSONObject.isNull("added")) {
                movieModel.realmSet$added(null);
            } else {
                movieModel.realmSet$added(jSONObject.getString("added"));
            }
        }
        if (jSONObject.has("tmdb_id")) {
            if (jSONObject.isNull("tmdb_id")) {
                movieModel.realmSet$tmdb_id(null);
            } else {
                movieModel.realmSet$tmdb_id(jSONObject.getString("tmdb_id"));
            }
        }
        if (jSONObject.has("pro")) {
            if (jSONObject.isNull("pro")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'pro' to null.");
            }
            movieModel.realmSet$pro(jSONObject.getInt("pro"));
        }
        if (jSONObject.has("time")) {
            if (jSONObject.isNull("time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
            }
            movieModel.realmSet$time(jSONObject.getLong("time"));
        }
        if (jSONObject.has("recent_mil")) {
            if (jSONObject.isNull("recent_mil")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'recent_mil' to null.");
            }
            movieModel.realmSet$recent_mil(jSONObject.getLong("recent_mil"));
        }
        if (jSONObject.has("is_locked")) {
            if (jSONObject.isNull("is_locked")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_locked' to null.");
            }
            movieModel.realmSet$is_locked(jSONObject.getBoolean("is_locked"));
        }
        if (jSONObject.has("is_favorite")) {
            if (jSONObject.isNull("is_favorite")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_favorite' to null.");
            }
            movieModel.realmSet$is_favorite(jSONObject.getBoolean("is_favorite"));
        }
        if (jSONObject.has("is_recent")) {
            if (jSONObject.isNull("is_recent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_recent' to null.");
            }
            movieModel.realmSet$is_recent(jSONObject.getBoolean("is_recent"));
        }
        if (jSONObject.has("category_name")) {
            if (jSONObject.isNull("category_name")) {
                movieModel.realmSet$category_name(null);
            } else {
                movieModel.realmSet$category_name(jSONObject.getString("category_name"));
            }
        }
        if (jSONObject.has(ImagesContract.URL)) {
            if (jSONObject.isNull(ImagesContract.URL)) {
                movieModel.realmSet$url(null);
            } else {
                movieModel.realmSet$url(jSONObject.getString(ImagesContract.URL));
            }
        }
        return movieModel;
    }

    @TargetApi(11)
    public static MovieModel createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        MovieModel movieModel = new MovieModel();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("num")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'num' to null.");
                }
                movieModel.realmSet$num(jsonReader.nextInt());
            } else if (strNextName.equals("name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$name(null);
                }
            } else if (strNextName.equals("stream_type")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$stream_type(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$stream_type(null);
                }
            } else if (strNextName.equals("stream_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$stream_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$stream_id(null);
                }
            } else if (strNextName.equals("stream_icon")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$stream_icon(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$stream_icon(null);
                }
            } else if (strNextName.equals("extension")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$extension(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$extension(null);
                }
            } else if (strNextName.equals("type")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$type(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$type(null);
                }
            } else if (strNextName.equals("rating")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$rating(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$rating(null);
                }
            } else if (strNextName.equals("category_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$category_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$category_id(null);
                }
            } else if (strNextName.equals("custom_sid")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$custom_sid(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$custom_sid(null);
                }
            } else if (strNextName.equals("added")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$added(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$added(null);
                }
            } else if (strNextName.equals("tmdb_id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$tmdb_id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$tmdb_id(null);
                }
            } else if (strNextName.equals("pro")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'pro' to null.");
                }
                movieModel.realmSet$pro(jsonReader.nextInt());
            } else if (strNextName.equals("time")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'time' to null.");
                }
                movieModel.realmSet$time(jsonReader.nextLong());
            } else if (strNextName.equals("recent_mil")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'recent_mil' to null.");
                }
                movieModel.realmSet$recent_mil(jsonReader.nextLong());
            } else if (strNextName.equals("is_locked")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_locked' to null.");
                }
                movieModel.realmSet$is_locked(jsonReader.nextBoolean());
            } else if (strNextName.equals("is_favorite")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_favorite' to null.");
                }
                movieModel.realmSet$is_favorite(jsonReader.nextBoolean());
            } else if (strNextName.equals("is_recent")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    throw MainActivity$$ExternalSyntheticOutline0.m(jsonReader, "Trying to set non-nullable field 'is_recent' to null.");
                }
                movieModel.realmSet$is_recent(jsonReader.nextBoolean());
            } else if (strNextName.equals("category_name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    movieModel.realmSet$category_name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    movieModel.realmSet$category_name(null);
                }
            } else if (!strNextName.equals(ImagesContract.URL)) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                movieModel.realmSet$url(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                movieModel.realmSet$url(null);
            }
        }
        jsonReader.endObject();
        return (MovieModel) realm.copyToRealm(movieModel, new ImportFlag[0]);
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, MovieModel movieModel, Map<RealmModel, Long> map) {
        if ((movieModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(movieModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) movieModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(MovieModel.class);
        long nativePtr = table.getNativePtr();
        MovieModelColumnInfo movieModelColumnInfo = (MovieModelColumnInfo) realm.getSchema().getColumnInfo(MovieModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(movieModel, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.numColKey, jCreateRow, movieModel.realmGet$num(), false);
        String strRealmGet$name = movieModel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        }
        String strRealmGet$stream_type = movieModel.realmGet$stream_type();
        if (strRealmGet$stream_type != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
        }
        String strRealmGet$stream_id = movieModel.realmGet$stream_id();
        if (strRealmGet$stream_id != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
        }
        String strRealmGet$stream_icon = movieModel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        }
        String strRealmGet$extension = movieModel.realmGet$extension();
        if (strRealmGet$extension != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.extensionColKey, jCreateRow, strRealmGet$extension, false);
        }
        String strRealmGet$type = movieModel.realmGet$type();
        if (strRealmGet$type != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.typeColKey, jCreateRow, strRealmGet$type, false);
        }
        String strRealmGet$rating = movieModel.realmGet$rating();
        if (strRealmGet$rating != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
        }
        String strRealmGet$category_id = movieModel.realmGet$category_id();
        if (strRealmGet$category_id != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
        }
        String strRealmGet$custom_sid = movieModel.realmGet$custom_sid();
        if (strRealmGet$custom_sid != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
        }
        String strRealmGet$added = movieModel.realmGet$added();
        if (strRealmGet$added != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
        }
        String strRealmGet$tmdb_id = movieModel.realmGet$tmdb_id();
        if (strRealmGet$tmdb_id != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
        }
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.proColKey, jCreateRow, movieModel.realmGet$pro(), false);
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.timeColKey, jCreateRow, movieModel.realmGet$time(), false);
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.recent_milColKey, jCreateRow, movieModel.realmGet$recent_mil(), false);
        Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_lockedColKey, jCreateRow, movieModel.realmGet$is_locked(), false);
        Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_favoriteColKey, jCreateRow, movieModel.realmGet$is_favorite(), false);
        Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_recentColKey, jCreateRow, movieModel.realmGet$is_recent(), false);
        String strRealmGet$category_name = movieModel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        }
        String strRealmGet$url = movieModel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        }
        return jCreateRow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, MovieModel movieModel, Map<RealmModel, Long> map) {
        if ((movieModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(movieModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) movieModel;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(MovieModel.class);
        long nativePtr = table.getNativePtr();
        MovieModelColumnInfo movieModelColumnInfo = (MovieModelColumnInfo) realm.getSchema().getColumnInfo(MovieModel.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(movieModel, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.numColKey, jCreateRow, movieModel.realmGet$num(), false);
        String strRealmGet$name = movieModel.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.nameColKey, jCreateRow, false);
        }
        String strRealmGet$stream_type = movieModel.realmGet$stream_type();
        if (strRealmGet$stream_type != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.stream_typeColKey, jCreateRow, false);
        }
        String strRealmGet$stream_id = movieModel.realmGet$stream_id();
        if (strRealmGet$stream_id != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.stream_idColKey, jCreateRow, false);
        }
        String strRealmGet$stream_icon = movieModel.realmGet$stream_icon();
        if (strRealmGet$stream_icon != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.stream_iconColKey, jCreateRow, false);
        }
        String strRealmGet$extension = movieModel.realmGet$extension();
        if (strRealmGet$extension != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.extensionColKey, jCreateRow, strRealmGet$extension, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.extensionColKey, jCreateRow, false);
        }
        String strRealmGet$type = movieModel.realmGet$type();
        if (strRealmGet$type != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.typeColKey, jCreateRow, strRealmGet$type, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.typeColKey, jCreateRow, false);
        }
        String strRealmGet$rating = movieModel.realmGet$rating();
        if (strRealmGet$rating != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.ratingColKey, jCreateRow, false);
        }
        String strRealmGet$category_id = movieModel.realmGet$category_id();
        if (strRealmGet$category_id != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.category_idColKey, jCreateRow, false);
        }
        String strRealmGet$custom_sid = movieModel.realmGet$custom_sid();
        if (strRealmGet$custom_sid != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.custom_sidColKey, jCreateRow, false);
        }
        String strRealmGet$added = movieModel.realmGet$added();
        if (strRealmGet$added != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.addedColKey, jCreateRow, false);
        }
        String strRealmGet$tmdb_id = movieModel.realmGet$tmdb_id();
        if (strRealmGet$tmdb_id != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.tmdb_idColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.proColKey, jCreateRow, movieModel.realmGet$pro(), false);
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.timeColKey, jCreateRow, movieModel.realmGet$time(), false);
        Table.nativeSetLong(nativePtr, movieModelColumnInfo.recent_milColKey, jCreateRow, movieModel.realmGet$recent_mil(), false);
        Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_lockedColKey, jCreateRow, movieModel.realmGet$is_locked(), false);
        Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_favoriteColKey, jCreateRow, movieModel.realmGet$is_favorite(), false);
        Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_recentColKey, jCreateRow, movieModel.realmGet$is_recent(), false);
        String strRealmGet$category_name = movieModel.realmGet$category_name();
        if (strRealmGet$category_name != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.category_nameColKey, jCreateRow, false);
        }
        String strRealmGet$url = movieModel.realmGet$url();
        if (strRealmGet$url != null) {
            Table.nativeSetString(nativePtr, movieModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
        } else {
            Table.nativeSetNull(nativePtr, movieModelColumnInfo.urlColKey, jCreateRow, false);
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
        com_nettv_livestore_models_MovieModelRealmProxy com_nettv_livestore_models_moviemodelrealmproxy = (com_nettv_livestore_models_MovieModelRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_nettv_livestore_models_moviemodelrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_nettv_livestore_models_moviemodelrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_nettv_livestore_models_moviemodelrealmproxy.proxyState.getRow$realm().getObjectKey();
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
        this.columnInfo = (MovieModelColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<MovieModel> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$added() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.addedColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$category_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_idColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$category_name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.category_nameColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$custom_sid() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.custom_sidColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$extension() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.extensionColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public boolean realmGet$is_favorite() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_favoriteColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public boolean realmGet$is_locked() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_lockedColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public boolean realmGet$is_recent() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.is_recentColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.nameColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public int realmGet$num() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.numColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public int realmGet$pro() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.proColKey);
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$rating() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.ratingColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public long realmGet$recent_mil() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.recent_milColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$stream_icon() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_iconColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$stream_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_idColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$stream_type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.stream_typeColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public long realmGet$time() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.timeColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$tmdb_id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tmdb_idColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.typeColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public String realmGet$url() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.urlColKey);
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$extension(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.extensionColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.extensionColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.extensionColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.extensionColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$is_favorite(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_favoriteColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_favoriteColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$is_locked(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_lockedColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_lockedColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$is_recent(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.is_recentColKey, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.is_recentColKey, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$num(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.numColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.numColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$pro(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.proColKey, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.proColKey, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$recent_mil(long j) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.recent_milColKey, j);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.recent_milColKey, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$time(long j) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.timeColKey, j);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.timeColKey, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
    public void realmSet$type(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.typeColKey);
                return;
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.typeColKey, str);
                return;
            }
        }
        if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.typeColKey, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.typeColKey, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.nettv.livestore.models.MovieModel, io.realm.com_nettv_livestore_models_MovieModelRealmProxyInterface
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
        StringBuilder sb = new StringBuilder("MovieModel = proxy[");
        sb.append("{num:");
        sb.append(realmGet$num());
        sb.append("}");
        sb.append(",");
        sb.append("{name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$name() != null ? realmGet$name() : "null", "}", ",", "{stream_type:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_type() != null ? realmGet$stream_type() : "null", "}", ",", "{stream_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_id() != null ? realmGet$stream_id() : "null", "}", ",", "{stream_icon:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$stream_icon() != null ? realmGet$stream_icon() : "null", "}", ",", "{extension:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$extension() != null ? realmGet$extension() : "null", "}", ",", "{type:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$type() != null ? realmGet$type() : "null", "}", ",", "{rating:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$rating() != null ? realmGet$rating() : "null", "}", ",", "{category_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_id() != null ? realmGet$category_id() : "null", "}", ",", "{custom_sid:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$custom_sid() != null ? realmGet$custom_sid() : "null", "}", ",", "{added:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$added() != null ? realmGet$added() : "null", "}", ",", "{tmdb_id:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$tmdb_id() != null ? realmGet$tmdb_id() : "null", "}", ",", "{pro:");
        sb.append(realmGet$pro());
        sb.append("}");
        sb.append(",");
        sb.append("{time:");
        sb.append(realmGet$time());
        sb.append("}");
        sb.append(",");
        sb.append("{recent_mil:");
        sb.append(realmGet$recent_mil());
        sb.append("}");
        sb.append(",");
        sb.append("{is_locked:");
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
        sb.append("{category_name:");
        Insets$$ExternalSyntheticOutline0.m29m(sb, realmGet$category_name() != null ? realmGet$category_name() : "null", "}", ",", "{url:");
        return Insets$$ExternalSyntheticOutline0.m(sb, realmGet$url() != null ? realmGet$url() : "null", "}", "]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(MovieModel.class);
        long nativePtr = table.getNativePtr();
        MovieModelColumnInfo movieModelColumnInfo = (MovieModelColumnInfo) realm.getSchema().getColumnInfo(MovieModel.class);
        while (it.hasNext()) {
            MovieModel movieModel = (MovieModel) it.next();
            if (!map.containsKey(movieModel)) {
                if ((movieModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(movieModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) movieModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(movieModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(movieModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.numColKey, jCreateRow, movieModel.realmGet$num(), false);
                String strRealmGet$name = movieModel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                }
                String strRealmGet$stream_type = movieModel.realmGet$stream_type();
                if (strRealmGet$stream_type != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
                }
                String strRealmGet$stream_id = movieModel.realmGet$stream_id();
                if (strRealmGet$stream_id != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
                }
                String strRealmGet$stream_icon = movieModel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                }
                String strRealmGet$extension = movieModel.realmGet$extension();
                if (strRealmGet$extension != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.extensionColKey, jCreateRow, strRealmGet$extension, false);
                }
                String strRealmGet$type = movieModel.realmGet$type();
                if (strRealmGet$type != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.typeColKey, jCreateRow, strRealmGet$type, false);
                }
                String strRealmGet$rating = movieModel.realmGet$rating();
                if (strRealmGet$rating != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
                }
                String strRealmGet$category_id = movieModel.realmGet$category_id();
                if (strRealmGet$category_id != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
                }
                String strRealmGet$custom_sid = movieModel.realmGet$custom_sid();
                if (strRealmGet$custom_sid != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
                }
                String strRealmGet$added = movieModel.realmGet$added();
                if (strRealmGet$added != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
                }
                String strRealmGet$tmdb_id = movieModel.realmGet$tmdb_id();
                if (strRealmGet$tmdb_id != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
                }
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.proColKey, jCreateRow, movieModel.realmGet$pro(), false);
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.timeColKey, jCreateRow, movieModel.realmGet$time(), false);
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.recent_milColKey, jCreateRow, movieModel.realmGet$recent_mil(), false);
                Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_lockedColKey, jCreateRow, movieModel.realmGet$is_locked(), false);
                Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_favoriteColKey, jCreateRow, movieModel.realmGet$is_favorite(), false);
                Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_recentColKey, jCreateRow, movieModel.realmGet$is_recent(), false);
                String strRealmGet$category_name = movieModel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                }
                String strRealmGet$url = movieModel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(MovieModel.class);
        long nativePtr = table.getNativePtr();
        MovieModelColumnInfo movieModelColumnInfo = (MovieModelColumnInfo) realm.getSchema().getColumnInfo(MovieModel.class);
        while (it.hasNext()) {
            MovieModel movieModel = (MovieModel) it.next();
            if (!map.containsKey(movieModel)) {
                if ((movieModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(movieModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) movieModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(movieModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(movieModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.numColKey, jCreateRow, movieModel.realmGet$num(), false);
                String strRealmGet$name = movieModel.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.nameColKey, jCreateRow, false);
                }
                String strRealmGet$stream_type = movieModel.realmGet$stream_type();
                if (strRealmGet$stream_type != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_typeColKey, jCreateRow, strRealmGet$stream_type, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.stream_typeColKey, jCreateRow, false);
                }
                String strRealmGet$stream_id = movieModel.realmGet$stream_id();
                if (strRealmGet$stream_id != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_idColKey, jCreateRow, strRealmGet$stream_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.stream_idColKey, jCreateRow, false);
                }
                String strRealmGet$stream_icon = movieModel.realmGet$stream_icon();
                if (strRealmGet$stream_icon != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.stream_iconColKey, jCreateRow, strRealmGet$stream_icon, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.stream_iconColKey, jCreateRow, false);
                }
                String strRealmGet$extension = movieModel.realmGet$extension();
                if (strRealmGet$extension != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.extensionColKey, jCreateRow, strRealmGet$extension, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.extensionColKey, jCreateRow, false);
                }
                String strRealmGet$type = movieModel.realmGet$type();
                if (strRealmGet$type != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.typeColKey, jCreateRow, strRealmGet$type, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.typeColKey, jCreateRow, false);
                }
                String strRealmGet$rating = movieModel.realmGet$rating();
                if (strRealmGet$rating != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.ratingColKey, jCreateRow, strRealmGet$rating, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.ratingColKey, jCreateRow, false);
                }
                String strRealmGet$category_id = movieModel.realmGet$category_id();
                if (strRealmGet$category_id != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.category_idColKey, jCreateRow, strRealmGet$category_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.category_idColKey, jCreateRow, false);
                }
                String strRealmGet$custom_sid = movieModel.realmGet$custom_sid();
                if (strRealmGet$custom_sid != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.custom_sidColKey, jCreateRow, strRealmGet$custom_sid, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.custom_sidColKey, jCreateRow, false);
                }
                String strRealmGet$added = movieModel.realmGet$added();
                if (strRealmGet$added != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.addedColKey, jCreateRow, strRealmGet$added, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.addedColKey, jCreateRow, false);
                }
                String strRealmGet$tmdb_id = movieModel.realmGet$tmdb_id();
                if (strRealmGet$tmdb_id != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.tmdb_idColKey, jCreateRow, strRealmGet$tmdb_id, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.tmdb_idColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.proColKey, jCreateRow, movieModel.realmGet$pro(), false);
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.timeColKey, jCreateRow, movieModel.realmGet$time(), false);
                Table.nativeSetLong(nativePtr, movieModelColumnInfo.recent_milColKey, jCreateRow, movieModel.realmGet$recent_mil(), false);
                Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_lockedColKey, jCreateRow, movieModel.realmGet$is_locked(), false);
                Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_favoriteColKey, jCreateRow, movieModel.realmGet$is_favorite(), false);
                Table.nativeSetBoolean(nativePtr, movieModelColumnInfo.is_recentColKey, jCreateRow, movieModel.realmGet$is_recent(), false);
                String strRealmGet$category_name = movieModel.realmGet$category_name();
                if (strRealmGet$category_name != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.category_nameColKey, jCreateRow, strRealmGet$category_name, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.category_nameColKey, jCreateRow, false);
                }
                String strRealmGet$url = movieModel.realmGet$url();
                if (strRealmGet$url != null) {
                    Table.nativeSetString(nativePtr, movieModelColumnInfo.urlColKey, jCreateRow, strRealmGet$url, false);
                } else {
                    Table.nativeSetNull(nativePtr, movieModelColumnInfo.urlColKey, jCreateRow, false);
                }
            }
        }
    }
}
