package com.nettv.livestore.models;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class EpisodeInfoModel extends RealmObject implements Serializable, com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface {

    @SerializedName("bitrate")
    private int bitrate;

    @SerializedName(TypedValues.TransitionType.S_DURATION)
    private String duration;

    @SerializedName("duration_secs")
    private int duration_secs;

    @SerializedName("movie_image")
    private String movie_image;

    @SerializedName("name")
    private String name;

    @SerializedName("plot")
    private String plot;

    @SerializedName("rating")
    private String rating;

    @SerializedName("releasedate")
    private String releasedate;

    @SerializedName("tmdb_id")
    public String tmdb_id;

    /* JADX WARN: Multi-variable type inference failed */
    public EpisodeInfoModel() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public int getBitrate() {
        return realmGet$bitrate();
    }

    public String getDuration() {
        return realmGet$duration();
    }

    public int getDuration_secs() {
        return realmGet$duration_secs();
    }

    public String getMovie_image() {
        return realmGet$movie_image() == null ? "" : realmGet$movie_image();
    }

    public String getName() {
        return realmGet$name();
    }

    public String getPlot() {
        return realmGet$plot() == null ? "" : realmGet$plot();
    }

    public float getRating() {
        if (realmGet$rating() == null || realmGet$rating().isEmpty()) {
            return 0.0f;
        }
        return Float.parseFloat(realmGet$rating());
    }

    public String getReleasedate() {
        return realmGet$releasedate();
    }

    public String getTmdb_id() {
        return realmGet$tmdb_id() == null ? "" : realmGet$tmdb_id();
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public int realmGet$bitrate() {
        return this.bitrate;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$duration() {
        return this.duration;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public int realmGet$duration_secs() {
        return this.duration_secs;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$movie_image() {
        return this.movie_image;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$name() {
        return this.name;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$plot() {
        return this.plot;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$rating() {
        return this.rating;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$releasedate() {
        return this.releasedate;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public String realmGet$tmdb_id() {
        return this.tmdb_id;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$bitrate(int i) {
        this.bitrate = i;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$duration(String str) {
        this.duration = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$duration_secs(int i) {
        this.duration_secs = i;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$movie_image(String str) {
        this.movie_image = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$name(String str) {
        this.name = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$plot(String str) {
        this.plot = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$rating(String str) {
        this.rating = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$releasedate(String str) {
        this.releasedate = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EpisodeInfoModelRealmProxyInterface
    public void realmSet$tmdb_id(String str) {
        this.tmdb_id = str;
    }

    public void setMovie_image(String str) {
        realmSet$movie_image(str);
    }

    public void setPlot(String str) {
        realmSet$plot(str);
    }

    public void setTmdb_id(String str) {
        realmSet$tmdb_id(str);
    }
}
