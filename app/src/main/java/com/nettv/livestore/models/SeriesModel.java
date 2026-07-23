package com.nettv.livestore.models;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class SeriesModel extends RealmObject implements Serializable, com_nettv_livestore_models_SeriesModelRealmProxyInterface {

    @SerializedName("cast")
    private String cast;

    @SerializedName("category_id")
    private String category_id;
    private String category_name;

    @SerializedName("director")
    private String director;
    private int episode_pos;

    @SerializedName("genre")
    private String genre;
    private boolean is_favorite;
    private boolean is_recent;
    private boolean is_watched;

    @SerializedName("last_modified")
    private String last_modified;

    @SerializedName("name")
    private String name;

    @SerializedName("num")
    private int num;

    @SerializedName("plot")
    private String plot;

    @SerializedName("rating")
    private String rating;

    @SerializedName("rating_5based")
    private float rating_5based;

    @SerializedName("releaseDate")
    private String releaseDate;
    private int season_pos;

    @SerializedName("series_id")
    private String series_id;

    @SerializedName("cover")
    private String stream_icon;

    @SerializedName("")
    private String stream_type;

    @SerializedName("tmdb")
    public String tmdb;
    private String url;

    @SerializedName("youtube_trailer")
    private String youtube;

    /* JADX WARN: Multi-variable type inference failed */
    public SeriesModel() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$season_pos(0);
        realmSet$episode_pos(0);
        realmSet$is_watched(false);
        realmSet$is_favorite(false);
        realmSet$is_recent(false);
    }

    public String getCast() {
        return realmGet$cast() == null ? "" : realmGet$cast();
    }

    public String getCategory_id() {
        return realmGet$category_id();
    }

    public String getCategory_name() {
        return realmGet$category_name() == null ? "" : realmGet$category_name();
    }

    public String getDirector() {
        return realmGet$director();
    }

    public int getEpisode_pos() {
        return realmGet$episode_pos();
    }

    public String getGenre() {
        return realmGet$genre() == null ? "" : realmGet$genre();
    }

    public String getLast_modified() {
        return (realmGet$last_modified() == null || realmGet$last_modified().isEmpty()) ? "0" : realmGet$last_modified();
    }

    public String getName() {
        return realmGet$name();
    }

    public int getNum() {
        return realmGet$num();
    }

    public String getPlot() {
        return realmGet$plot();
    }

    public float getRating() {
        try {
            if (realmGet$rating() != null && !realmGet$rating().isEmpty()) {
                return Float.parseFloat(realmGet$rating()) / 2.0f;
            }
            return 0.0f;
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    public float getRating_5based() {
        return realmGet$rating_5based();
    }

    public String getReleaseDate() {
        return realmGet$releaseDate() == null ? "" : realmGet$releaseDate();
    }

    public int getSeason_pos() {
        return realmGet$season_pos();
    }

    public String getSeries_id() {
        return realmGet$series_id() == null ? "" : realmGet$series_id();
    }

    public String getStream_icon() {
        return realmGet$stream_icon() == null ? "" : realmGet$stream_icon();
    }

    public String getStream_type() {
        return realmGet$stream_type();
    }

    public String getTmdb() {
        return realmGet$tmdb() == null ? "" : realmGet$tmdb();
    }

    public String getUrl() {
        return realmGet$url();
    }

    public String getYoutube() {
        return realmGet$youtube() == null ? "" : realmGet$youtube();
    }

    public boolean isIs_favorite() {
        return realmGet$is_favorite();
    }

    public boolean isIs_recent() {
        return realmGet$is_recent();
    }

    public boolean isIs_watched() {
        return realmGet$is_watched();
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$cast() {
        return this.cast;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$category_id() {
        return this.category_id;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$category_name() {
        return this.category_name;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$director() {
        return this.director;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public int realmGet$episode_pos() {
        return this.episode_pos;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$genre() {
        return this.genre;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public boolean realmGet$is_favorite() {
        return this.is_favorite;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public boolean realmGet$is_recent() {
        return this.is_recent;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public boolean realmGet$is_watched() {
        return this.is_watched;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$last_modified() {
        return this.last_modified;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$name() {
        return this.name;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public int realmGet$num() {
        return this.num;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$plot() {
        return this.plot;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$rating() {
        return this.rating;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public float realmGet$rating_5based() {
        return this.rating_5based;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$releaseDate() {
        return this.releaseDate;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public int realmGet$season_pos() {
        return this.season_pos;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$series_id() {
        return this.series_id;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$stream_icon() {
        return this.stream_icon;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$stream_type() {
        return this.stream_type;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$tmdb() {
        return this.tmdb;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$url() {
        return this.url;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public String realmGet$youtube() {
        return this.youtube;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$cast(String str) {
        this.cast = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$category_id(String str) {
        this.category_id = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$category_name(String str) {
        this.category_name = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$director(String str) {
        this.director = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$episode_pos(int i) {
        this.episode_pos = i;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$genre(String str) {
        this.genre = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$is_favorite(boolean z) {
        this.is_favorite = z;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$is_recent(boolean z) {
        this.is_recent = z;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$is_watched(boolean z) {
        this.is_watched = z;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$last_modified(String str) {
        this.last_modified = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$name(String str) {
        this.name = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$num(int i) {
        this.num = i;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$plot(String str) {
        this.plot = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$rating(String str) {
        this.rating = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$rating_5based(float f) {
        this.rating_5based = f;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$releaseDate(String str) {
        this.releaseDate = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$season_pos(int i) {
        this.season_pos = i;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$series_id(String str) {
        this.series_id = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$stream_icon(String str) {
        this.stream_icon = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$stream_type(String str) {
        this.stream_type = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$tmdb(String str) {
        this.tmdb = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$url(String str) {
        this.url = str;
    }

    @Override // io.realm.com_nettv_livestore_models_SeriesModelRealmProxyInterface
    public void realmSet$youtube(String str) {
        this.youtube = str;
    }

    public void setCast(String str) {
        realmSet$cast(str);
    }

    public void setCategory_id(String str) {
        realmSet$category_id(str);
    }

    public void setCategory_name(String str) {
        realmSet$category_name(str);
    }

    public void setDirector(String str) {
        realmSet$director(str);
    }

    public void setEpisode_pos(int i) {
        realmSet$episode_pos(i);
    }

    public void setGenre(String str) {
        realmSet$genre(str);
    }

    public void setIs_favorite(boolean z) {
        realmSet$is_favorite(z);
    }

    public void setIs_recent(boolean z) {
        realmSet$is_recent(z);
    }

    public void setIs_watched(boolean z) {
        realmSet$is_watched(z);
    }

    public void setLast_modified(String str) {
        realmSet$last_modified(str);
    }

    public void setName(String str) {
        realmSet$name(str);
    }

    public void setNum(int i) {
        realmSet$num(i);
    }

    public void setPlot(String str) {
        realmSet$plot(str);
    }

    public void setRating(String str) {
        realmSet$rating(str);
    }

    public void setRating_5based(float f) {
        realmSet$rating_5based(f);
    }

    public void setReleaseDate(String str) {
        realmSet$releaseDate(str);
    }

    public void setSeason_pos(int i) {
        realmSet$season_pos(i);
    }

    public void setSeries_id(String str) {
        realmSet$series_id(str);
    }

    public void setStream_icon(String str) {
        realmSet$stream_icon(str);
    }

    public void setStream_type(String str) {
        realmSet$stream_type(str);
    }

    public void setUrl(String str) {
        realmSet$url(str);
    }

    public void setYoutube(String str) {
        realmSet$youtube(str);
    }
}
