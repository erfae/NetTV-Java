package com.nettv.livestore.apps;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.PlaybackException;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.LoginResponse;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.ResumeModel;
import com.nettv.livestore.models.ResumeSeriesModel;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.net.FetchChannelsTask;
import com.nettv.livestore.net.FetchEpisodeTask;
import com.nettv.livestore.net.FetchM3uItemsTask;
import com.nettv.livestore.net.FetchVideosTask;
import com.nettv.livestore.net.NetworkTask;
import com.nettv.livestore.remote.RetroClass;
import com.nettv.livestore.utils.Utils;
import io.realm.Realm;
import io.realm.Realm$$ExternalSyntheticLambda0;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmResults;
import iptv.m3u.parser.M3UItem;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class BaseActivity extends AppCompatActivity {
    public static boolean busy;
    private HashMap<String, String> categoryHashMap;
    private HashMap<String, List<EpisodeModel>> episodeModelHashMap;
    private NetworkTask<Void, Void, List<EPGChannel>> fetchChannelsTask;
    private NetworkTask<Void, Void, List<EpisodeModel>> fetchEpisodesTask;
    private NetworkTask<Void, Void, List<M3UItem>> fetchM3uItemsTask;
    private NetworkTask<Void, Void, List<MovieModel>> fetchVideosTask;
    public String password;
    public PreferenceHelper preferenceHelper;
    public Realm realm;
    public String user;
    public String user_id;
    public WordModels wordModels = new WordModels();
    public boolean is_stop = false;
    public int error_account = 0;
    private final LTVApp model = LTVApp.getInstance();

    /* JADX INFO: renamed from: com.nettv.livestore.apps.BaseActivity$10, reason: invalid class name */
    public class AnonymousClass10 implements Callback<List<SeriesModel>> {
        public final /* synthetic */ List val$series_favorites;
        public final /* synthetic */ List val$series_recent;

        public AnonymousClass10(List list, List list2) {
            this.val$series_favorites = list;
            this.val$series_recent = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$0(Response response, Realm realm) {
            realm.where(SeriesModel.class).findAll().deleteAllFromRealm();
            realm.insertOrUpdate((Collection<? extends RealmModel>) response.body());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$1(String str, Realm realm) {
            SeriesModel seriesModel = (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(realm, SeriesModel.class, "name", str);
            if (seriesModel != null) {
                seriesModel.setIs_favorite(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$2(ResumeSeriesModel resumeSeriesModel, Realm realm) {
            SeriesModel seriesModel = (SeriesModel) realm.where(SeriesModel.class).equalTo("name", resumeSeriesModel.getName()).findFirst();
            if (seriesModel != null) {
                seriesModel.setIs_recent(true);
                seriesModel.setSeason_pos(resumeSeriesModel.getSeason_pos());
                seriesModel.setEpisode_pos(resumeSeriesModel.getEpisode_pos());
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<List<SeriesModel>> call, @NonNull Throwable th) {
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            int i = baseActivity.error_account;
            if (i >= 2) {
                baseActivity.doNextTask(true);
            } else {
                baseActivity.error_account = i + 1;
                baseActivity.getSecondSeriesStreams();
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(@NonNull Call<List<SeriesModel>> call, @NonNull Response<List<SeriesModel>> response) {
            int i = 1;
            if (response.body() != null) {
                BaseActivity.this.realm.executeTransaction(new BaseActivity$5$$ExternalSyntheticLambda0(response, i));
                if (this.val$series_favorites.size() > 0) {
                    Iterator it = this.val$series_favorites.iterator();
                    while (it.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), i));
                    }
                }
                if (this.val$series_recent.size() > 0) {
                    Iterator it2 = this.val$series_recent.iterator();
                    while (it2.hasNext()) {
                        BaseActivity.this.realm.executeTransactionAsync(new BaseActivity$$ExternalSyntheticLambda4((ResumeSeriesModel) it2.next(), i));
                    }
                }
            }
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            baseActivity.doNextTask(true);
        }
    }

    /* JADX INFO: renamed from: com.nettv.livestore.apps.BaseActivity$5, reason: invalid class name */
    public class AnonymousClass5 implements Callback<List<EPGChannel>> {
        public final /* synthetic */ List val$live_favorites;
        public final /* synthetic */ List val$lock_channels;

        public AnonymousClass5(List list, List list2) {
            this.val$live_favorites = list;
            this.val$lock_channels = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$0(Response response, Realm realm) {
            realm.where(EPGChannel.class).findAll().deleteAllFromRealm();
            realm.insertOrUpdate((Collection<? extends RealmModel>) response.body());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$1(String str, Realm realm) {
            EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
            if (ePGChannel != null) {
                ePGChannel.setIs_favorite(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$2(String str, Realm realm) {
            EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
            if (ePGChannel != null) {
                ePGChannel.setIs_locked(true);
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<List<EPGChannel>> call, @NonNull Throwable th) {
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            int i = baseActivity.error_account;
            if (i >= 2) {
                baseActivity.getSecondLiveStreams();
            } else {
                baseActivity.error_account = i + 1;
                baseActivity.getLiveStreams();
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(@NonNull Call<List<EPGChannel>> call, @NonNull Response<List<EPGChannel>> response) {
            if (response.body() == null) {
                BaseActivity baseActivity = BaseActivity.this;
                if (baseActivity.is_stop) {
                    return;
                }
                baseActivity.getSecondLiveStreams();
                return;
            }
            int i = 0;
            BaseActivity.this.error_account = 0;
            if (response.body().size() <= 0) {
                BaseActivity.this.getSecondLiveStreams();
                return;
            }
            BaseActivity.this.realm.executeTransaction(new BaseActivity$5$$ExternalSyntheticLambda0(response, i));
            if (this.val$live_favorites.size() > 0) {
                Iterator it = this.val$live_favorites.iterator();
                while (it.hasNext()) {
                    BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 2));
                }
            }
            if (this.val$lock_channels.size() > 0) {
                Iterator it2 = this.val$lock_channels.iterator();
                while (it2.hasNext()) {
                    BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it2.next(), 3));
                }
            }
            BaseActivity baseActivity2 = BaseActivity.this;
            if (baseActivity2.is_stop) {
                return;
            }
            baseActivity2.getVodStreams();
        }
    }

    /* JADX INFO: renamed from: com.nettv.livestore.apps.BaseActivity$6, reason: invalid class name */
    public class AnonymousClass6 implements Callback<List<EPGChannel>> {
        public final /* synthetic */ List val$live_favorites;
        public final /* synthetic */ List val$lock_channels;

        public AnonymousClass6(List list, List list2) {
            this.val$live_favorites = list;
            this.val$lock_channels = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$0(Response response, Realm realm) {
            realm.where(EPGChannel.class).findAll().deleteAllFromRealm();
            realm.insertOrUpdate((Collection<? extends RealmModel>) response.body());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$1(String str, Realm realm) {
            EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
            if (ePGChannel != null) {
                ePGChannel.setIs_favorite(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$2(String str, Realm realm) {
            EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
            if (ePGChannel != null) {
                ePGChannel.setIs_locked(true);
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<List<EPGChannel>> call, @NonNull Throwable th) {
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            int i = baseActivity.error_account;
            if (i >= 2) {
                baseActivity.getSecondVodStreams();
            } else {
                baseActivity.error_account = i + 1;
                baseActivity.getSecondLiveStreams();
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(@NonNull Call<List<EPGChannel>> call, @NonNull Response<List<EPGChannel>> response) {
            if (response.body() != null) {
                BaseActivity baseActivity = BaseActivity.this;
                baseActivity.error_account = 0;
                baseActivity.realm.executeTransaction(new BaseActivity$5$$ExternalSyntheticLambda0(response, 2));
                if (this.val$live_favorites.size() > 0) {
                    Iterator it = this.val$live_favorites.iterator();
                    while (it.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 4));
                    }
                }
                if (this.val$lock_channels.size() > 0) {
                    Iterator it2 = this.val$lock_channels.iterator();
                    while (it2.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it2.next(), 5));
                    }
                }
            }
            BaseActivity baseActivity2 = BaseActivity.this;
            if (baseActivity2.is_stop) {
                return;
            }
            baseActivity2.getSecondVodStreams();
        }
    }

    /* JADX INFO: renamed from: com.nettv.livestore.apps.BaseActivity$7, reason: invalid class name */
    public class AnonymousClass7 implements Callback<List<MovieModel>> {
        public final /* synthetic */ List val$resumeModels;
        public final /* synthetic */ List val$vod_favorites;

        public AnonymousClass7(List list, List list2) {
            this.val$vod_favorites = list;
            this.val$resumeModels = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$0(Response response, Realm realm) {
            realm.where(MovieModel.class).findAll().deleteAllFromRealm();
            realm.insertOrUpdate((Collection<? extends RealmModel>) response.body());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$1(String str, Realm realm) {
            MovieModel movieModel = (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(realm, MovieModel.class, "name", str);
            if (movieModel != null) {
                movieModel.setIs_favorite(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$2(ResumeModel resumeModel, Realm realm) {
            MovieModel movieModel = (MovieModel) realm.where(MovieModel.class).equalTo("name", resumeModel.getName()).findFirst();
            if (movieModel != null) {
                movieModel.setIs_recent(true);
                movieModel.setPro(resumeModel.getPro());
                movieModel.setTime(resumeModel.getLast_position());
                movieModel.setTmdb_id(resumeModel.getTmdb_id());
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<List<MovieModel>> call, @NonNull Throwable th) {
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            int i = baseActivity.error_account;
            if (i >= 2) {
                baseActivity.getSeriesStreams();
            } else {
                baseActivity.error_account = i + 1;
                baseActivity.getVodStreams();
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(@NonNull Call<List<MovieModel>> call, @NonNull Response<List<MovieModel>> response) {
            if (response.body() != null) {
                BaseActivity baseActivity = BaseActivity.this;
                baseActivity.error_account = 0;
                baseActivity.realm.executeTransaction(new BaseActivity$5$$ExternalSyntheticLambda0(response, 3));
                if (this.val$vod_favorites.size() > 0) {
                    Iterator it = this.val$vod_favorites.iterator();
                    while (it.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 6));
                    }
                }
                if (this.val$resumeModels.size() > 0) {
                    Iterator it2 = this.val$resumeModels.iterator();
                    while (it2.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda3((ResumeModel) it2.next(), 1));
                    }
                }
            }
            BaseActivity baseActivity2 = BaseActivity.this;
            if (baseActivity2.is_stop) {
                return;
            }
            baseActivity2.getSeriesStreams();
        }
    }

    /* JADX INFO: renamed from: com.nettv.livestore.apps.BaseActivity$8, reason: invalid class name */
    public class AnonymousClass8 implements Callback<List<MovieModel>> {
        public final /* synthetic */ List val$resumeModels;
        public final /* synthetic */ List val$vod_favorites;

        public AnonymousClass8(List list, List list2) {
            this.val$vod_favorites = list;
            this.val$resumeModels = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$0(Response response, Realm realm) {
            realm.where(MovieModel.class).findAll().deleteAllFromRealm();
            realm.insertOrUpdate((Collection<? extends RealmModel>) response.body());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$1(String str, Realm realm) {
            MovieModel movieModel = (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(realm, MovieModel.class, "name", str);
            if (movieModel != null) {
                movieModel.setIs_favorite(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$2(ResumeModel resumeModel, Realm realm) {
            MovieModel movieModel = (MovieModel) realm.where(MovieModel.class).equalTo("name", resumeModel.getName()).findFirst();
            if (movieModel != null) {
                movieModel.setIs_recent(true);
                movieModel.setPro(resumeModel.getPro());
                movieModel.setTime(resumeModel.getLast_position());
                movieModel.setTmdb_id(resumeModel.getTmdb_id());
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<List<MovieModel>> call, @NonNull Throwable th) {
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            int i = baseActivity.error_account;
            if (i >= 2) {
                baseActivity.getSecondSeriesStreams();
            } else {
                baseActivity.error_account = i + 1;
                baseActivity.getSecondVodStreams();
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(@NonNull Call<List<MovieModel>> call, @NonNull Response<List<MovieModel>> response) {
            if (response.body() != null) {
                BaseActivity baseActivity = BaseActivity.this;
                baseActivity.error_account = 0;
                baseActivity.realm.executeTransaction(new BaseActivity$5$$ExternalSyntheticLambda0(response, 4));
                if (this.val$vod_favorites.size() > 0) {
                    Iterator it = this.val$vod_favorites.iterator();
                    while (it.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 7));
                    }
                }
                if (this.val$resumeModels.size() > 0) {
                    Iterator it2 = this.val$resumeModels.iterator();
                    while (it2.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda3((ResumeModel) it2.next(), 2));
                    }
                }
            }
            BaseActivity baseActivity2 = BaseActivity.this;
            if (baseActivity2.is_stop) {
                return;
            }
            baseActivity2.getSecondSeriesStreams();
        }
    }

    /* JADX INFO: renamed from: com.nettv.livestore.apps.BaseActivity$9, reason: invalid class name */
    public class AnonymousClass9 implements Callback<List<SeriesModel>> {
        public final /* synthetic */ List val$series_favorites;
        public final /* synthetic */ List val$series_recent;

        public AnonymousClass9(List list, List list2) {
            this.val$series_favorites = list;
            this.val$series_recent = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$0(Response response, Realm realm) {
            realm.where(SeriesModel.class).findAll().deleteAllFromRealm();
            realm.insertOrUpdate((Collection<? extends RealmModel>) response.body());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$1(String str, Realm realm) {
            SeriesModel seriesModel = (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(realm, SeriesModel.class, "name", str);
            if (seriesModel != null) {
                seriesModel.setIs_favorite(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onResponse$2(ResumeSeriesModel resumeSeriesModel, Realm realm) {
            SeriesModel seriesModel = (SeriesModel) realm.where(SeriesModel.class).equalTo("name", resumeSeriesModel.getName()).findFirst();
            if (seriesModel != null) {
                seriesModel.setIs_recent(true);
                seriesModel.setSeason_pos(resumeSeriesModel.getSeason_pos());
                seriesModel.setEpisode_pos(resumeSeriesModel.getEpisode_pos());
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(@NonNull Call<List<SeriesModel>> call, @NonNull Throwable th) {
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            int i = baseActivity.error_account;
            if (i >= 2) {
                baseActivity.doNextTask(true);
            } else {
                baseActivity.error_account = i + 1;
                baseActivity.getSeriesStreams();
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(@NonNull Call<List<SeriesModel>> call, @NonNull Response<List<SeriesModel>> response) {
            if (response.body() != null) {
                BaseActivity.this.realm.executeTransaction(new BaseActivity$5$$ExternalSyntheticLambda0(response, 5));
                if (this.val$series_favorites.size() > 0) {
                    Iterator it = this.val$series_favorites.iterator();
                    while (it.hasNext()) {
                        BaseActivity.this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 8));
                    }
                }
                if (this.val$series_recent.size() > 0) {
                    Iterator it2 = this.val$series_recent.iterator();
                    while (it2.hasNext()) {
                        BaseActivity.this.realm.executeTransactionAsync(new BaseActivity$$ExternalSyntheticLambda4((ResumeSeriesModel) it2.next(), 2));
                    }
                }
            }
            BaseActivity baseActivity = BaseActivity.this;
            if (baseActivity.is_stop) {
                return;
            }
            baseActivity.doNextTask(true);
        }
    }

    private void addChannelToCategory(EPGChannel ePGChannel) {
        String keyFromCategoryName = getKeyFromCategoryName(ePGChannel.getCategory_name());
        if (this.categoryHashMap.get(keyFromCategoryName) == null) {
            keyFromCategoryName = new Date().getTime() + "!@#%" + ePGChannel.getCategory_name();
        }
        this.categoryHashMap.put(keyFromCategoryName, ePGChannel.getCategory_name());
    }

    private void addEpisodeToSeries(EpisodeModel episodeModel) {
        String series_name = episodeModel.getSeries_name();
        if (series_name == null || series_name.equals("null")) {
            series_name = "All";
        }
        List<EpisodeModel> arrayList = this.episodeModelHashMap.get(series_name);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(episodeModel);
        this.episodeModelHashMap.put(series_name, arrayList);
    }

    private void addMovieToCategory(MovieModel movieModel) {
        String keyFromCategoryName = getKeyFromCategoryName(movieModel.getCategory_name());
        if (this.categoryHashMap.get(keyFromCategoryName) == null) {
            keyFromCategoryName = new Date().getTime() + "!@#%" + movieModel.getCategory_name();
        }
        this.categoryHashMap.put(keyFromCategoryName, movieModel.getCategory_name());
    }

    private void addSeriesToCategory(SeriesModel seriesModel) {
        String keyFromCategoryName = getKeyFromCategoryName(seriesModel.getCategory_name());
        if (this.categoryHashMap.get(keyFromCategoryName) == null) {
            keyFromCategoryName = new Date().getTime() + "!@#%" + seriesModel.getCategory_name();
        }
        this.categoryHashMap.put(keyFromCategoryName, seriesModel.getCategory_name());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void authentication(final String str) {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).authentication(this.user, this.password).enqueue(new Callback<LoginResponse>() { // from class: com.nettv.livestore.apps.BaseActivity.1
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable th) {
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    int i = baseActivity.error_account;
                    if (i < 2) {
                        baseActivity.error_account = i + 1;
                        baseActivity.authentication(str);
                    } else {
                        baseActivity.preferenceHelper.setSharedPreferenceISM3U(true);
                        BaseActivity baseActivity2 = BaseActivity.this;
                        baseActivity2.reloadM3UData(str, baseActivity2.wordModels);
                    }
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.body() == null || response.body().getUser_info() == null || response.body().getUser_info().getStatus() == null) {
                        BaseActivity.this.preferenceHelper.setSharedPreferenceISM3U(true);
                        BaseActivity baseActivity = BaseActivity.this;
                        baseActivity.reloadM3UData(str, baseActivity.wordModels);
                        return;
                    }
                    BaseActivity.this.error_account = 0;
                    if (!response.body().getUser_info().getStatus().equalsIgnoreCase("Active")) {
                        Toast.makeText(BaseActivity.this.getApplicationContext(), BaseActivity.this.wordModels.getAccount_expired(), 1).show();
                        BaseActivity.this.doNextTask(false);
                        return;
                    }
                    BaseActivity.this.preferenceHelper.setSharedPreferenceLoginModel(response.body().getUser_info());
                    BaseActivity baseActivity2 = BaseActivity.this;
                    baseActivity2.preferenceHelper.setSharedPreferenceUsername(baseActivity2.user);
                    BaseActivity baseActivity3 = BaseActivity.this;
                    baseActivity3.preferenceHelper.setSharedPreferencePassword(baseActivity3.password);
                    Constants.setServerTimeOffset(response.body().getServerModel().getTimestamp_now(), response.body().getServerModel().getTime_now());
                    BaseActivity baseActivity4 = BaseActivity.this;
                    if (baseActivity4.is_stop) {
                        return;
                    }
                    baseActivity4.getLiveCategory();
                }
            });
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            this.preferenceHelper.setSharedPreferenceISM3U(true);
            reloadM3UData(str, this.wordModels);
        }
    }

    private void fetchM3UItems(String str) {
        NetworkTask<Void, Void, List<M3UItem>> networkTask = this.fetchM3uItemsTask;
        if (networkTask != null && !networkTask.isComplete()) {
            this.fetchM3uItemsTask.abort();
        }
        FetchM3uItemsTask fetchM3uItemsTask = new FetchM3uItemsTask(str, null);
        this.fetchM3uItemsTask = fetchM3uItemsTask;
        fetchM3uItemsTask.setOnCompleteListener(new BaseActivity$$ExternalSyntheticLambda2(this, 0));
        this.fetchM3uItemsTask.setOnGenericExceptionListener(new BaseActivity$$ExternalSyntheticLambda2(this, 1));
        this.fetchM3uItemsTask.setOnNetworkUnavailableListener(new BaseActivity$$ExternalSyntheticLambda2(this, 2));
        this.fetchM3uItemsTask.execute();
    }

    private String getCategoryNameFromKey(String str) {
        return str.contains("!@#%") ? str.split("!@#%")[1] : "All";
    }

    private void getChannelModels() {
        RealmResults realmResultsFindAll = this.realm.where(EPGChannel.class).findAll();
        List<String> sharedPreferenceLiveFavChannels = this.preferenceHelper.getSharedPreferenceLiveFavChannels();
        List<String> sharedPreferenceLiveLockChannels = this.preferenceHelper.getSharedPreferenceLiveLockChannels();
        if (realmResultsFindAll.size() != 0) {
            if (System.currentTimeMillis() / 1000 <= (((long) this.preferenceHelper.getSharedPreferenceUpdatePeriod()) * Constants.date_mils) + this.preferenceHelper.getSharedPreferenceLastPlaylistDate()) {
                if (this.is_stop) {
                    return;
                }
                getLiveCategoryModels(new ArrayList(realmResultsFindAll));
                return;
            }
        }
        NetworkTask<Void, Void, List<EPGChannel>> networkTask = this.fetchChannelsTask;
        if (networkTask != null && !networkTask.isComplete()) {
            this.fetchChannelsTask.abort();
        }
        FetchChannelsTask fetchChannelsTask = new FetchChannelsTask();
        this.fetchChannelsTask = fetchChannelsTask;
        fetchChannelsTask.setOnCompleteListener(new BaseActivity$$ExternalSyntheticLambda1(this, realmResultsFindAll, sharedPreferenceLiveFavChannels, sharedPreferenceLiveLockChannels, 1));
        this.fetchChannelsTask.setOnGenericExceptionListener(new BaseActivity$$ExternalSyntheticLambda2(this, 5));
        this.fetchChannelsTask.execute();
    }

    private void getEpisodeModels() {
        RealmResults realmResultsFindAll = this.realm.where(EpisodeModel.class).findAll();
        if (realmResultsFindAll.size() != 0) {
            if (System.currentTimeMillis() / 1000 <= (((long) this.preferenceHelper.getSharedPreferenceUpdatePeriod()) * Constants.date_mils) + this.preferenceHelper.getSharedPreferenceLastPlaylistDate()) {
                if (this.is_stop) {
                    return;
                }
                getSeriesFromEpisodes(new ArrayList(realmResultsFindAll));
                return;
            }
        }
        NetworkTask<Void, Void, List<EpisodeModel>> networkTask = this.fetchEpisodesTask;
        if (networkTask != null && !networkTask.isComplete()) {
            this.fetchEpisodesTask.abort();
        }
        FetchEpisodeTask fetchEpisodeTask = new FetchEpisodeTask();
        this.fetchEpisodesTask = fetchEpisodeTask;
        fetchEpisodeTask.setOnCompleteListener(new BaseActivity$$ExternalSyntheticLambda0(this, realmResultsFindAll, 0));
        this.fetchEpisodesTask.setOnGenericExceptionListener(new BaseActivity$$ExternalSyntheticLambda2(this, 4));
        this.fetchEpisodesTask.execute();
    }

    private String getKeyFromCategoryName(String str) {
        for (String str2 : new TreeSet(this.categoryHashMap.keySet())) {
            if (getCategoryNameFromKey(str2).equalsIgnoreCase(str)) {
                return str2;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLiveCategory() {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_live_categories(this.user, this.password).enqueue(new Callback<List<CategoryModel>>() { // from class: com.nettv.livestore.apps.BaseActivity.2
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable th) {
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    int i = baseActivity.error_account;
                    if (i >= 2) {
                        baseActivity.getVodCategory();
                    } else {
                        baseActivity.error_account = i + 1;
                        baseActivity.getLiveCategory();
                    }
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull Response<List<CategoryModel>> response) {
                    BaseActivity.this.error_account = 0;
                    List<CategoryModel> listBody = response.body();
                    if (listBody == null) {
                        listBody = new ArrayList<>();
                    }
                    listBody.add(0, new CategoryModel(Constants.add_group, BaseActivity.this.wordModels.getAdd_group()));
                    listBody.add(1, new CategoryModel(Constants.resume_id, BaseActivity.this.wordModels.getRecently_viewed()));
                    listBody.add(2, new CategoryModel(Constants.all_id, BaseActivity.this.wordModels.getAll()));
                    listBody.add(3, new CategoryModel(Constants.fav_id, BaseActivity.this.wordModels.getFavorite()));
                    listBody.add(4, new CategoryModel(Constants.lock_id, BaseActivity.this.wordModels.getLock()));
                    int i = PlaybackException.ERROR_CODE_DRM_UNSPECIFIED;
                    List<String> sharedPreferenceMyGroupCategory = BaseActivity.this.preferenceHelper.getSharedPreferenceMyGroupCategory();
                    for (int i2 = 0; i2 < sharedPreferenceMyGroupCategory.size(); i2++) {
                        listBody.add(i2 + 5, new CategoryModel(String.valueOf(i), sharedPreferenceMyGroupCategory.get(i2)));
                        i += 1000;
                    }
                    for (CategoryModel categoryModel : listBody) {
                        String lowerCase = categoryModel.getName().toLowerCase();
                        if (lowerCase.contains("adult") || lowerCase.contains("xxx") || lowerCase.contains("porn")) {
                            Constants.xxx_live_categories.add(categoryModel.getId());
                        }
                    }
                    BaseActivity.this.preferenceHelper.setSharedPreferenceLiveCategory(listBody);
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    baseActivity.getVodCategory();
                }
            });
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getVodCategory();
        }
    }

    private void getLiveCategoryModels(List<EPGChannel> list) {
        this.categoryHashMap = new HashMap<>();
        Iterator<EPGChannel> it = list.iterator();
        while (it.hasNext()) {
            addChannelToCategory(it.next());
        }
        TreeSet treeSet = new TreeSet(this.categoryHashMap.keySet());
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CategoryModel(Constants.add_group, this.wordModels.getAdd_group()));
        arrayList.add(new CategoryModel(Constants.resume_id, this.wordModels.getRecently_viewed()));
        arrayList.add(new CategoryModel(Constants.all_id, this.wordModels.getAll()));
        arrayList.add(new CategoryModel(Constants.fav_id, this.wordModels.getFavorite()));
        arrayList.add(new CategoryModel(Constants.lock_id, this.wordModels.getLock()));
        int i = PlaybackException.ERROR_CODE_DRM_UNSPECIFIED;
        List<String> sharedPreferenceMyGroupCategory = this.preferenceHelper.getSharedPreferenceMyGroupCategory();
        int i2 = 0;
        for (int i3 = 0; i3 < sharedPreferenceMyGroupCategory.size(); i3++) {
            arrayList.add(new CategoryModel(String.valueOf(i), sharedPreferenceMyGroupCategory.get(i3)));
            i += 1000;
        }
        Iterator it2 = treeSet.iterator();
        while (it2.hasNext()) {
            arrayList.add(new CategoryModel(String.valueOf(i2), (String) it2.next()));
            i2++;
        }
        this.preferenceHelper.setSharedPreferenceLiveCategory(arrayList);
        if (this.is_stop) {
            return;
        }
        getMovieModels();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLiveStreams() {
        if (this.realm.where(EPGChannel.class).findAll().size() != 0) {
            if (System.currentTimeMillis() / 1000 <= (((long) this.preferenceHelper.getSharedPreferenceUpdatePeriod()) * Constants.date_mils) + this.preferenceHelper.getSharedPreferenceLastPlaylistDate()) {
                if (this.is_stop) {
                    return;
                }
                getVodStreams();
                return;
            }
        }
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_live_streams(this.user, this.password).enqueue(new AnonymousClass5(this.preferenceHelper.getSharedPreferenceLiveFavChannels(), this.preferenceHelper.getSharedPreferenceLiveLockChannels()));
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getSecondLiveStreams();
        }
    }

    private int getMediaType(M3UItem m3UItem) {
        String streamURL = m3UItem.getStreamURL();
        if (streamURL == null || streamURL.length() <= 0) {
            return -1;
        }
        if (streamURL.contains("movie/") || streamURL.contains("=movie") || streamURL.contains("==movie") || streamURL.contains("movies/") || streamURL.contains("vod/") || streamURL.contains("video/")) {
            return 1;
        }
        return streamURL.contains("series/") ? 2 : 0;
    }

    private void getMovieCategoryModels(List<MovieModel> list) {
        this.categoryHashMap = new HashMap<>();
        Iterator<MovieModel> it = list.iterator();
        while (it.hasNext()) {
            addMovieToCategory(it.next());
        }
        TreeSet treeSet = new TreeSet(this.categoryHashMap.keySet());
        int i = 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CategoryModel(Constants.resume_id, this.wordModels.getResume_to_watch()));
        arrayList.add(new CategoryModel(Constants.all_id, this.wordModels.getAll()));
        arrayList.add(new CategoryModel(Constants.fav_id, this.wordModels.getFavorite()));
        Iterator it2 = treeSet.iterator();
        while (it2.hasNext()) {
            arrayList.add(new CategoryModel(String.valueOf(i), (String) it2.next()));
            i++;
        }
        this.preferenceHelper.setSharedPreferenceVodCategory(arrayList);
        if (this.is_stop) {
            return;
        }
        getEpisodeModels();
    }

    private void getMovieModels() {
        RealmResults realmResultsFindAll = this.realm.where(MovieModel.class).findAll();
        List<String> sharedPreferenceVodFavNames = this.preferenceHelper.getSharedPreferenceVodFavNames();
        List<ResumeModel> sharedPreferenceResumeModel = this.preferenceHelper.getSharedPreferenceResumeModel();
        if (realmResultsFindAll.size() != 0) {
            if (System.currentTimeMillis() / 1000 <= (((long) this.preferenceHelper.getSharedPreferenceUpdatePeriod()) * Constants.date_mils) + this.preferenceHelper.getSharedPreferenceLastPlaylistDate()) {
                if (this.is_stop) {
                    return;
                }
                getMovieCategoryModels(new ArrayList(realmResultsFindAll));
                return;
            }
        }
        NetworkTask<Void, Void, List<MovieModel>> networkTask = this.fetchVideosTask;
        if (networkTask != null && !networkTask.isComplete()) {
            this.fetchVideosTask.abort();
        }
        FetchVideosTask fetchVideosTask = new FetchVideosTask();
        this.fetchVideosTask = fetchVideosTask;
        fetchVideosTask.setOnCompleteListener(new BaseActivity$$ExternalSyntheticLambda1(this, realmResultsFindAll, sharedPreferenceVodFavNames, sharedPreferenceResumeModel, 0));
        this.fetchVideosTask.setOnGenericExceptionListener(new BaseActivity$$ExternalSyntheticLambda2(this, 3));
        this.fetchVideosTask.execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSecondLiveStreams() {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_second_live_streams(this.user, this.password).enqueue(new AnonymousClass6(this.preferenceHelper.getSharedPreferenceLiveFavChannels(), this.preferenceHelper.getSharedPreferenceLiveLockChannels()));
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getSecondVodStreams();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSecondSeriesStreams() {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_second_series(this.user, this.password).enqueue(new AnonymousClass10(this.preferenceHelper.getSharedPreferenceSeriesFavNames(), this.preferenceHelper.getSharedPreferenceRecentSeriesNames()));
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            doNextTask(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSecondVodStreams() {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_second_vod_streams(this.user, this.password).enqueue(new AnonymousClass8(this.preferenceHelper.getSharedPreferenceVodFavNames(), this.preferenceHelper.getSharedPreferenceResumeModel()));
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getSecondSeriesStreams();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSeriesCategory() {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_series_categories(this.user, this.password).enqueue(new Callback<List<CategoryModel>>() { // from class: com.nettv.livestore.apps.BaseActivity.4
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable th) {
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    int i = baseActivity.error_account;
                    if (i >= 2) {
                        baseActivity.getLiveStreams();
                    } else {
                        baseActivity.error_account = i + 1;
                        baseActivity.getSeriesCategory();
                    }
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull Response<List<CategoryModel>> response) {
                    BaseActivity.this.error_account = 0;
                    List<CategoryModel> listBody = response.body();
                    if (listBody == null) {
                        listBody = new ArrayList<>();
                    }
                    listBody.add(0, new CategoryModel(Constants.resume_id, BaseActivity.this.wordModels.getRecently_viewed()));
                    listBody.add(1, new CategoryModel(Constants.all_id, BaseActivity.this.wordModels.getAll()));
                    listBody.add(2, new CategoryModel(Constants.fav_id, BaseActivity.this.wordModels.getFavorite()));
                    BaseActivity.this.preferenceHelper.setSharedPreferenceSeriesCategory(listBody);
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    baseActivity.getLiveStreams();
                }
            });
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getLiveStreams();
        }
    }

    private void getSeriesCategoryModels(List<SeriesModel> list) {
        this.categoryHashMap = new HashMap<>();
        Iterator<SeriesModel> it = list.iterator();
        while (it.hasNext()) {
            addSeriesToCategory(it.next());
        }
        this.categoryHashMap.keySet();
        TreeSet treeSet = new TreeSet(this.categoryHashMap.keySet());
        int i = 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CategoryModel(Constants.resume_id, this.wordModels.getRecently_viewed()));
        arrayList.add(new CategoryModel(Constants.all_id, this.wordModels.getAll()));
        arrayList.add(new CategoryModel(Constants.fav_id, this.wordModels.getFavorite()));
        Iterator it2 = treeSet.iterator();
        while (it2.hasNext()) {
            arrayList.add(new CategoryModel(String.valueOf(i), (String) it2.next()));
            i++;
        }
        this.preferenceHelper.setSharedPreferenceSeriesCategory(arrayList);
        this.preferenceHelper.setSharedPreferenceLastPlaylistDate(System.currentTimeMillis() / 1000);
        if (this.is_stop) {
            return;
        }
        doNextTask(true);
    }

    private void getSeriesFromEpisodes(List<EpisodeModel> list) {
        int i;
        List<EpisodeModel> list2;
        List<String> sharedPreferenceSeriesFavNames = this.preferenceHelper.getSharedPreferenceSeriesFavNames();
        List<ResumeSeriesModel> sharedPreferenceRecentSeriesNames = this.preferenceHelper.getSharedPreferenceRecentSeriesNames();
        this.episodeModelHashMap = new HashMap<>();
        Iterator<EpisodeModel> it = list.iterator();
        while (it.hasNext()) {
            addEpisodeToSeries(it.next());
        }
        ArrayList arrayList = new ArrayList();
        this.episodeModelHashMap.keySet();
        Iterator it2 = new TreeSet(this.episodeModelHashMap.keySet()).iterator();
        while (true) {
            i = 0;
            if (!it2.hasNext()) {
                break;
            }
            String str = (String) it2.next();
            if (str != null && (list2 = this.episodeModelHashMap.get(str)) != null && list2.size() > 0) {
                SeriesModel seriesModel = new SeriesModel();
                seriesModel.setName(str);
                seriesModel.setCategory_name(list2.get(0).getCategory_name());
                seriesModel.setStream_icon(list2.get(0).getStream_icon());
                arrayList.add(seriesModel);
            }
        }
        this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda6(arrayList, 1));
        if (sharedPreferenceSeriesFavNames.size() > 0) {
            Iterator<String> it3 = sharedPreferenceSeriesFavNames.iterator();
            while (it3.hasNext()) {
                this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5(it3.next(), i));
            }
        }
        if (sharedPreferenceRecentSeriesNames.size() > 0) {
            Iterator<ResumeSeriesModel> it4 = sharedPreferenceRecentSeriesNames.iterator();
            while (it4.hasNext()) {
                this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda4(it4.next(), i));
            }
        }
        getSeriesCategoryModels(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSeriesStreams() {
        if (this.realm.where(SeriesModel.class).findAll().size() != 0) {
            if (System.currentTimeMillis() / 1000 <= (((long) this.preferenceHelper.getSharedPreferenceUpdatePeriod()) * Constants.date_mils) + this.preferenceHelper.getSharedPreferenceLastPlaylistDate()) {
                if (this.is_stop) {
                    return;
                }
                doNextTask(true);
                return;
            }
        }
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_series(this.user, this.password).enqueue(new AnonymousClass9(this.preferenceHelper.getSharedPreferenceSeriesFavNames(), this.preferenceHelper.getSharedPreferenceRecentSeriesNames()));
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            doNextTask(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVodCategory() {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_vod_categories(this.user, this.password).enqueue(new Callback<List<CategoryModel>>() { // from class: com.nettv.livestore.apps.BaseActivity.3
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable th) {
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    int i = baseActivity.error_account;
                    if (i >= 2) {
                        baseActivity.getSeriesCategory();
                    } else {
                        baseActivity.error_account = i + 1;
                        baseActivity.getVodCategory();
                    }
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull Response<List<CategoryModel>> response) {
                    BaseActivity.this.error_account = 0;
                    List<CategoryModel> listBody = response.body();
                    if (listBody == null) {
                        listBody = new ArrayList<>();
                    }
                    listBody.add(0, new CategoryModel(Constants.resume_id, BaseActivity.this.wordModels.getResume_to_watch()));
                    listBody.add(1, new CategoryModel(Constants.all_id, BaseActivity.this.wordModels.getAll()));
                    listBody.add(2, new CategoryModel(Constants.fav_id, BaseActivity.this.wordModels.getFavorite()));
                    BaseActivity.this.preferenceHelper.setSharedPreferenceVodCategory(listBody);
                    for (CategoryModel categoryModel : listBody) {
                        String lowerCase = categoryModel.getName().toLowerCase();
                        if (lowerCase.contains("adult") || lowerCase.contains("xxx") || lowerCase.contains("porn")) {
                            Constants.xxx_vod_categories.add(categoryModel.getId());
                        }
                    }
                    BaseActivity baseActivity = BaseActivity.this;
                    if (baseActivity.is_stop) {
                        return;
                    }
                    baseActivity.getSeriesCategory();
                }
            });
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getSeriesCategory();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVodStreams() {
        if (this.realm.where(MovieModel.class).findAll().size() != 0) {
            if (System.currentTimeMillis() / 1000 <= (((long) this.preferenceHelper.getSharedPreferenceUpdatePeriod()) * Constants.date_mils) + this.preferenceHelper.getSharedPreferenceLastPlaylistDate()) {
                if (this.is_stop) {
                    return;
                }
                getSeriesStreams();
                return;
            }
        }
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_vod_streams(this.user, this.password).enqueue(new AnonymousClass7(this.preferenceHelper.getSharedPreferenceVodFavNames(), this.preferenceHelper.getSharedPreferenceResumeModel()));
        } catch (Exception unused) {
            if (this.is_stop) {
                return;
            }
            getSeriesStreams();
        }
    }

    public static synchronized boolean isBusy() {
        return busy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fetchM3UItems$3(List list) {
        if (list.size() == 0) {
            doNextTask(false);
            Toast.makeText(getApplicationContext(), this.wordModels.getUser_incorrect(), 0).show();
        } else if (!this.is_stop) {
            prepareData(list);
            getChannelModels();
        }
        setBusy(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fetchM3UItems$4(Exception exc) {
        if (!this.is_stop) {
            doNextTask(false);
            Toast.makeText(getApplicationContext(), this.wordModels.getUser_incorrect(), 0).show();
        }
        setBusy(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fetchM3UItems$5(NetworkErrorException networkErrorException) {
        if (!this.is_stop) {
            doNextTask(false);
            Toast.makeText(getApplicationContext(), this.wordModels.getUser_incorrect(), 0).show();
        }
        setBusy(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChannelModels$10(Exception exc) {
        getMovieModels();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getChannelModels$6(List list, Realm realm) {
        realm.where(EPGChannel.class).findAll().deleteAllFromRealm();
        realm.insertOrUpdate(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getChannelModels$7(String str, Realm realm) {
        EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
        if (ePGChannel != null) {
            ePGChannel.setIs_favorite(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getChannelModels$8(String str, Realm realm) {
        EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
        if (ePGChannel != null) {
            ePGChannel.setIs_locked(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChannelModels$9(RealmResults realmResults, List list, List list2, List list3) {
        if (realmResults.size() != list3.size()) {
            this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda6(list3, 3));
            if (list.size() > 0) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 10));
                }
            }
            if (list2.size() > 0) {
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it2.next(), 11));
                }
            }
        }
        if (this.is_stop) {
            return;
        }
        getLiveCategoryModels(list3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getEpisodeModels$16(List list, Realm realm) {
        realm.where(EpisodeModel.class).findAll().deleteAllFromRealm();
        realm.where(SeriesModel.class).findAll().deleteAllFromRealm();
        realm.insertOrUpdate(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getEpisodeModels$17(RealmResults realmResults, List list) {
        if (realmResults.size() != list.size()) {
            this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda6(list, 0));
        }
        if (this.is_stop) {
            return;
        }
        getSeriesFromEpisodes(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getEpisodeModels$18(Exception exc) {
        setBusy(false);
        if (this.is_stop) {
            return;
        }
        doNextTask(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getMovieModels$11(List list, Realm realm) {
        realm.where(MovieModel.class).findAll().deleteAllFromRealm();
        realm.insertOrUpdate(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getMovieModels$12(String str, Realm realm) {
        MovieModel movieModel = (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(realm, MovieModel.class, "name", str);
        if (movieModel != null) {
            movieModel.setIs_favorite(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getMovieModels$13(ResumeModel resumeModel, Realm realm) {
        MovieModel movieModel = (MovieModel) realm.where(MovieModel.class).equalTo("name", resumeModel.getName()).findFirst();
        if (movieModel != null) {
            movieModel.setIs_recent(true);
            movieModel.setPro(resumeModel.getPro());
            movieModel.setTime(resumeModel.getLast_position());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMovieModels$14(RealmResults realmResults, List list, List list2, List list3) {
        if (realmResults.size() != list3.size()) {
            this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda6(list3, 2));
            if (list.size() > 0) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda5((String) it.next(), 9));
                }
            }
            if (list2.size() > 0) {
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    this.realm.executeTransaction(new BaseActivity$$ExternalSyntheticLambda3((ResumeModel) it2.next(), 0));
                }
            }
        }
        if (this.is_stop) {
            return;
        }
        getMovieCategoryModels(list3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMovieModels$15(Exception exc) {
        getEpisodeModels();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getSeriesFromEpisodes$19(List list, Realm realm) {
        realm.where(SeriesModel.class).findAll().deleteAllFromRealm();
        realm.insertOrUpdate(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getSeriesFromEpisodes$20(String str, Realm realm) {
        SeriesModel seriesModel = (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(realm, SeriesModel.class, "name", str);
        if (seriesModel != null) {
            seriesModel.setIs_favorite(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getSeriesFromEpisodes$21(ResumeSeriesModel resumeSeriesModel, Realm realm) {
        SeriesModel seriesModel = (SeriesModel) realm.where(SeriesModel.class).equalTo("name", resumeSeriesModel.getName()).findFirst();
        if (seriesModel != null) {
            seriesModel.setIs_recent(true);
            seriesModel.setSeason_pos(resumeSeriesModel.getSeason_pos());
            seriesModel.setEpisode_pos(resumeSeriesModel.getEpisode_pos());
        }
    }

    private void prepareData(List<M3UItem> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (M3UItem m3UItem : list) {
            int mediaType = getMediaType(m3UItem);
            if (mediaType != -1) {
                if (mediaType == 0) {
                    arrayList.add(m3UItem);
                } else if (mediaType == 1) {
                    arrayList2.add(m3UItem);
                } else {
                    arrayList3.add(m3UItem);
                }
            }
        }
        this.model.setM3UChannelsItems(arrayList);
        this.model.setM3UVideosItems(arrayList2);
        this.model.setM3USeriesItems(arrayList3);
    }

    public static synchronized void setBusy(boolean z) {
        busy = z;
    }

    public void doNextTask(boolean z) {
    }

    public void goToLogin(String str, WordModels wordModels) {
        this.preferenceHelper = new PreferenceHelper(this);
        if (this.is_stop) {
            return;
        }
        try {
            this.wordModels = wordModels;
            String strReplaceAll = str.replaceAll(" ", "");
            URL url = new URL(strReplaceAll.trim());
            this.preferenceHelper.setSharedPreferenceServerUrl(url.getProtocol() + "://" + url.getAuthority());
            String[] strArrSplit = url.getQuery().split("&");
            this.user = strArrSplit[0].split("=")[1];
            this.password = strArrSplit[1].split("=")[1];
            this.user_id = Utils.getUserId(strReplaceAll);
            if (!this.preferenceHelper.getSharedPreferenceUserId().equalsIgnoreCase(this.user_id)) {
                this.realm.executeTransaction(Realm$$ExternalSyntheticLambda0.INSTANCE$23);
                this.preferenceHelper.setSharedPreferenceUserId(this.user_id);
                this.preferenceHelper.setSharedPreferenceLiveCategory(GetSharedInfo.getDefaultLiveCategory());
                this.preferenceHelper.setSharedPreferenceVodCategory(GetSharedInfo.getDefaultVodCategory());
                this.preferenceHelper.setSharedPreferenceSeriesCategory(GetSharedInfo.getDefaultLiveCategory());
            }
            authentication(strReplaceAll);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, wordModels.getAdd_correct_alert(), 0).show();
            doNextTask(false);
        }
    }

    public void goToXUILogin(String str, WordModels wordModels) {
        this.preferenceHelper = new PreferenceHelper(this);
        if (this.is_stop) {
            return;
        }
        try {
            this.wordModels = wordModels;
            String strReplaceAll = str.replaceAll(" ", "");
            URL url = new URL(strReplaceAll.trim());
            this.preferenceHelper.setSharedPreferenceServerUrl(url.getProtocol() + "://" + url.getAuthority());
            this.user = GetSharedInfo.getXUIUsername(strReplaceAll);
            this.password = GetSharedInfo.getXUIPassword(strReplaceAll);
            if (!this.preferenceHelper.getSharedPreferenceUserId().equalsIgnoreCase(this.user_id)) {
                this.realm.executeTransaction(Realm$$ExternalSyntheticLambda0.INSTANCE$21);
                this.preferenceHelper.setSharedPreferenceUserId(this.user_id);
                this.preferenceHelper.setSharedPreferenceLiveCategory(GetSharedInfo.getDefaultLiveCategory());
                this.preferenceHelper.setSharedPreferenceVodCategory(GetSharedInfo.getDefaultVodCategory());
                this.preferenceHelper.setSharedPreferenceSeriesCategory(GetSharedInfo.getDefaultLiveCategory());
            }
            authentication(strReplaceAll);
        } catch (Exception unused) {
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Realm.init(this);
        RealmConfiguration realmConfigurationBuild = new RealmConfiguration.Builder().name("MTV.realm").schemaVersion(1L).deleteRealmIfMigrationNeeded().allowWritesOnUiThread(true).build();
        Realm.setDefaultConfiguration(realmConfigurationBuild);
        this.realm = Realm.getInstance(realmConfigurationBuild);
    }

    public void reloadM3UData(String str, WordModels wordModels) {
        this.wordModels = wordModels;
        this.preferenceHelper = new PreferenceHelper(this);
        if (isBusy()) {
            return;
        }
        setBusy(true);
        String strTrim = str.replaceAll(" ", "").trim();
        this.user_id = Utils.getUserId(strTrim);
        this.preferenceHelper.setSharedPreferenceServerUrl(strTrim);
        if (!this.preferenceHelper.getSharedPreferenceUserId().equalsIgnoreCase(this.user_id)) {
            this.realm.executeTransaction(Realm$$ExternalSyntheticLambda0.INSTANCE$22);
            this.preferenceHelper.setSharedPreferenceUserId(this.user_id);
        }
        if (this.is_stop) {
            return;
        }
        fetchM3UItems(strTrim);
    }

    public void setStop(boolean z) {
        this.is_stop = z;
    }
}
