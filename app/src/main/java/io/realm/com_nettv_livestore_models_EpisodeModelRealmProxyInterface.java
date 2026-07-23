package io.realm;

import com.nettv.livestore.models.EpisodeInfoModel;

/* JADX INFO: loaded from: classes2.dex */
public interface com_nettv_livestore_models_EpisodeModelRealmProxyInterface {
    String realmGet$category_name();

    String realmGet$container_extension();

    String realmGet$episode_num();

    String realmGet$id();

    EpisodeInfoModel realmGet$info();

    int realmGet$season();

    String realmGet$season_name();

    String realmGet$series_name();

    String realmGet$stream_icon();

    String realmGet$title();

    String realmGet$url();

    void realmSet$category_name(String str);

    void realmSet$container_extension(String str);

    void realmSet$episode_num(String str);

    void realmSet$id(String str);

    void realmSet$info(EpisodeInfoModel episodeInfoModel);

    void realmSet$season(int i);

    void realmSet$season_name(String str);

    void realmSet$series_name(String str);

    void realmSet$stream_icon(String str);

    void realmSet$title(String str);

    void realmSet$url(String str);
}
