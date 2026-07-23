package com.nettv.livestore.activities;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.mobile.SeriesMobilePlayer;
import com.nettv.livestore.adapter.EpisodeRecyclerAdapter;
import com.nettv.livestore.adapter.SeasonRecyclerAdapter;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.Episode;
import com.nettv.livestore.models.EpisodeInfoModel;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.Info;
import com.nettv.livestore.models.InfoSerie;
import com.nettv.livestore.models.Season;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.RetroClass;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveVerticalGridView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class SeasonActivity extends AppCompatActivity {
    public BlurView blurView;
    public SeriesModel currentSeries;
    public EpisodeRecyclerAdapter episodeAdapter;
    public LiveVerticalGridView episode_list;
    public ImageView image_back;
    public ImageView image_bg;
    public String image_url;
    public PreferenceHelper preferenceHelper;
    public SeasonRecyclerAdapter seasonAdapter;
    public LiveVerticalGridView season_list;
    public String season_name;
    public String series_name;
    public TextView txt_name;
    public List<Season> seasonModelList = new ArrayList();
    public List<EpisodeModel> episodeModels = new ArrayList();
    public String series_id = "";
    public String vod_url = "";
    public WordModels wordModels = new WordModels();
    public int season_pos = 0;
    public int episode_pos = 0;
    public int pre_season_pos = 0;

    private void externalMXplayer(String str, String str2, String str3) {
        try {
            Uri uri = Uri.parse(str3);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setPackage(str);
            intent.setClassName(str, str2);
            intent.setDataAndType(uri, MimeTypes.APPLICATION_M3U8);
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
        }
    }

    private void externalvlcplayer(String str, String str2) {
        Uri uri = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setPackage("org.videolan.vlc");
        intent.setDataAndTypeAndNormalize(uri, "video/*");
        intent.putExtra("title", str2);
        intent.putExtra("from_start", true);
        intent.putExtra("position", 90000L);
        intent.setComponent(new ComponentName("org.videolan.vlc", "org.videolan.vlc.gui.video.VideoPlayerActivity"));
        startActivity(intent);
    }

    private List<EpisodeModel> getEpisodesFromInfoSerie(InfoSerie infoSerie, int i) {
        ArrayList arrayList = new ArrayList();
        List<Episode> episodesBySaison = infoSerie.getEpisodes().getEpisodesBySaison(i);
        for (int i2 = 0; i2 < episodesBySaison.size(); i2++) {
            Episode episode = episodesBySaison.get(i2);
            EpisodeModel episodeModel = new EpisodeModel();
            episodeModel.setTitle(episode.getTitle());
            episodeModel.setSeason(episode.getSeason());
            episodeModel.setEpisode_num(episode.getEpisode_num());
            episodeModel.setId(episode.getId());
            episodeModel.setContainer_extension(episode.getContainer_extension());
            EpisodeInfoModel episodeInfoModel = new EpisodeInfoModel();
            Gson gsonCreate = new GsonBuilder().create();
            if (episode.getInfo() != null) {
                try {
                    Info info = (Info) gsonCreate.fromJson(gsonCreate.toJson(episode.getInfo()), Info.class);
                    episodeInfoModel.setTmdb_id(info.getTmdb_id());
                    episodeInfoModel.setMovie_image(info.getMovie_image());
                    episodeInfoModel.setPlot(info.getPlot());
                } catch (Exception unused) {
                }
            }
            episodeModel.setInfo(episodeInfoModel);
            arrayList.add(episodeModel);
        }
        return arrayList;
    }

    private Season getSeasonByKey(List<Season> list, String str) {
        for (Season season : list) {
            if (str.equals(String.valueOf(season.getSeason_number()))) {
                return season;
            }
        }
        return new Season(Insets$$ExternalSyntheticOutline0.m("Season  ", str), Integer.parseInt(str), "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSeasonFromInfoSerie(InfoSerie infoSerie) {
        this.seasonModelList = new ArrayList();
        List<Season> listSerieDisp = infoSerie.getEpisodes().getListSerieDisp(infoSerie.getSeasons());
        if (listSerieDisp == null || listSerieDisp.size() <= 0) {
            return;
        }
        for (int i = 0; i < listSerieDisp.size(); i++) {
            Season season = listSerieDisp.get(i);
            season.setEpisodeModels(getEpisodesFromInfoSerie(infoSerie, listSerieDisp.get(i).getSeason_number()));
            this.seasonModelList.add(season);
        }
        this.seasonAdapter.setSeasonList(this.seasonModelList);
        int season_pos = this.currentSeries.getSeason_pos();
        this.pre_season_pos = season_pos;
        if (season_pos > this.seasonModelList.size() - 1) {
            this.pre_season_pos = 0;
        }
        List<EpisodeModel> episodeModels = this.seasonModelList.get(this.pre_season_pos).getEpisodeModels();
        this.episodeModels = episodeModels;
        this.episodeAdapter.setEpisodeModels(episodeModels, this.seasonModelList.get(this.pre_season_pos).getName());
        int episode_pos = this.currentSeries.getEpisode_pos();
        this.episode_pos = episode_pos;
        if (episode_pos > this.episodeModels.size() - 1) {
            this.episode_pos = 0;
        }
        this.episode_list.scrollToPosition(this.episode_pos);
        this.seasonAdapter.setSelectedItem(this.pre_season_pos);
        this.season_list.setSelectedPosition(this.pre_season_pos);
        this.season_list.requestFocus();
    }

    private void getSeriesInfo() {
        RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_series_info(this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.series_id).enqueue(new Callback<InfoSerie>() { // from class: com.nettv.livestore.activities.SeasonActivity.1
            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<InfoSerie> call, @NonNull Throwable th) {
                SeasonActivity.this.image_back.setFocusable(true);
                SeasonActivity.this.image_back.requestFocus();
                SeasonActivity.this.getSomeSeriesInfo();
            }

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<InfoSerie> call, @NonNull Response<InfoSerie> response) {
                if (response.body() != null) {
                    SeasonActivity seasonActivity = SeasonActivity.this;
                    response.body();
                    Objects.requireNonNull(seasonActivity);
                    SeasonActivity.this.getSeasonFromInfoSerie(response.body());
                    return;
                }
                SeasonActivity.this.image_back.setFocusable(true);
                SeasonActivity.this.image_back.requestFocus();
                SeasonActivity seasonActivity2 = SeasonActivity.this;
                Toast.makeText(seasonActivity2, seasonActivity2.wordModels.getNo_episode(), 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSomeSeriesInfo() {
        Volley.newRequestQueue(this).add(new StringRequest(0, this.preferenceHelper.getSharedPreferenceServerUrl() + "/player_api.php?action=get_series_info&username=" + this.preferenceHelper.getSharedPreferenceUsername() + "&password=" + this.preferenceHelper.getSharedPreferencePassword() + "&series_id=" + this.series_id, new SeasonActivity$$ExternalSyntheticLambda0(this), new SeasonActivity$$ExternalSyntheticLambda0(this)));
    }

    private void initView() {
        this.image_back = (ImageView) findViewById(R.id.image_back);
        this.image_bg = (ImageView) findViewById(R.id.image_bg);
        this.blurView = (BlurView) findViewById(R.id.blur_view);
        View decorView = getWindow().getDecorView();
        this.blurView.setupWith((ViewGroup) decorView.findViewById(android.R.id.content)).setFrameClearDrawable(decorView.getBackground()).setBlurAlgorithm(new RenderScriptBlur(this)).setBlurRadius(20.0f).setBlurAutoUpdate(true).setOverlayColor(getResources().getColor(R.color.black_50)).setHasFixedTransformationMatrix(true);
        this.season_list = (LiveVerticalGridView) findViewById(R.id.season_list);
        this.episode_list = (LiveVerticalGridView) findViewById(R.id.episode_list);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        if (GetSharedInfo.isTVDevice(this)) {
            this.season_list.setNumColumns(1);
            this.season_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.season_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SeasonActivity.3
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                    View[] viewArr2 = viewArr;
                    if (viewArr2[0] != null) {
                        viewArr2[0].setSelected(false);
                        View[] viewArr3 = viewArr;
                        viewArr3[0] = viewHolder.itemView;
                        viewArr3[0].setSelected(true);
                    }
                }
            });
            this.episode_list.setLoop(false);
            this.episode_list.setNumColumns(1);
            this.episode_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr2 = {null};
            this.episode_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SeasonActivity.4
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                    View[] viewArr3 = viewArr2;
                    if (viewArr3[0] != null) {
                        viewArr3[0].setSelected(false);
                        View[] viewArr4 = viewArr2;
                        viewArr4[0] = viewHolder.itemView;
                        viewArr4[0].setSelected(true);
                    }
                }
            });
        } else {
            this.season_list.setLayoutManager(new LinearLayoutManager(this));
            this.season_list.setHasFixedSize(true);
            this.episode_list.setLayoutManager(new LinearLayoutManager(this));
            this.episode_list.setHasFixedSize(true);
        }
        this.image_back.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 4));
        this.image_back.setFocusable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSomeSeriesInfo$3(String str) {
        try {
            Gson gson = new Gson();
            JSONObject jSONObject = new JSONObject(str);
            JSONArray jSONArray = jSONObject.getJSONArray("seasons");
            this.seasonModelList = new ArrayList();
            ArrayList arrayList = new ArrayList((Collection) gson.fromJson(jSONArray.toString(), new TypeToken<List<Season>>() { // from class: com.nettv.livestore.activities.SeasonActivity.2
            }.getType()));
            JSONArray jSONArray2 = jSONObject.getJSONArray("episodes");
            int i = 0;
            while (i < jSONArray2.length()) {
                int i2 = i + 1;
                Season seasonByKey = getSeasonByKey(new ArrayList(arrayList), String.valueOf(i2));
                try {
                    JSONArray jSONArray3 = jSONArray2.getJSONArray(i);
                    ArrayList arrayList2 = new ArrayList();
                    for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                        try {
                            JSONObject jSONObject2 = jSONArray3.getJSONObject(i3);
                            EpisodeModel episodeModel = (EpisodeModel) gson.fromJson(jSONObject2.toString(), EpisodeModel.class);
                            if (jSONObject2.has("info")) {
                                try {
                                    Info info = (Info) gson.fromJson(gson.toJson(jSONObject2.getJSONObject("info").toString()), Info.class);
                                    EpisodeInfoModel episodeInfoModel = new EpisodeInfoModel();
                                    episodeInfoModel.setMovie_image(info.getMovie_image());
                                    episodeInfoModel.setPlot(info.getPlot());
                                    episodeInfoModel.setTmdb_id(info.getTmdb_id());
                                    episodeModel.setInfo(episodeInfoModel);
                                } catch (JSONException unused) {
                                    Log.e("info error", "info _error");
                                }
                            }
                            arrayList2.add(episodeModel);
                        } catch (JSONException unused2) {
                            Log.e("episode error", "episode error");
                        }
                    }
                    seasonByKey.setEpisodeModels(arrayList2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.seasonModelList.add(seasonByKey);
                i = i2;
            }
            if (this.seasonModelList.size() > 0) {
                this.seasonAdapter.setSeasonList(this.seasonModelList);
                int season_pos = this.currentSeries.getSeason_pos();
                this.pre_season_pos = season_pos;
                if (season_pos > this.seasonModelList.size() - 1) {
                    this.pre_season_pos = 0;
                }
                List<EpisodeModel> episodeModels = this.seasonModelList.get(this.pre_season_pos).getEpisodeModels();
                this.episodeModels = episodeModels;
                this.episodeAdapter.setEpisodeModels(episodeModels, this.seasonModelList.get(this.pre_season_pos).getName());
                int episode_pos = this.currentSeries.getEpisode_pos();
                this.episode_pos = episode_pos;
                if (episode_pos > this.episodeModels.size() - 1) {
                    this.episode_pos = 0;
                }
                this.episode_list.scrollToPosition(this.episode_pos);
                this.seasonAdapter.setSelectedItem(this.pre_season_pos);
                this.season_list.setSelectedPosition(this.pre_season_pos);
                this.season_list.requestFocus();
            }
        } catch (Exception unused3) {
            this.image_back.setFocusable(true);
            this.image_back.requestFocus();
            Toast.makeText(this, this.wordModels.getNo_episode(), 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSomeSeriesInfo$4(VolleyError volleyError) {
        this.image_back.setFocusable(true);
        this.image_back.requestFocus();
        Toast.makeText(this, this.wordModels.getNo_episode(), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(Season season, Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            this.pre_season_pos = num.intValue();
            this.season_name = season.getName();
            this.seasonAdapter.setFocusDisable(this.pre_season_pos, false);
            List<EpisodeModel> episodeModels = season.getEpisodeModels();
            this.episodeModels = episodeModels;
            this.episodeAdapter.setEpisodeModels(episodeModels, this.season_name);
            this.episode_list.setSelectedPosition(0);
            this.episode_list.scrollToPosition(0);
        }
        this.season_pos = num.intValue();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1() {
        this.preferenceHelper.setSharedPreferenceRecentSeriesNames(RealmController.with().getResentSeriesNames());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$2(EpisodeModel episodeModel, Integer num, Boolean bool) {
        this.episode_pos = num.intValue();
        if (!bool.booleanValue()) {
            return null;
        }
        RealmController.with().addToRecentSeries(this.series_name, true, this.pre_season_pos, num.intValue(), new SeasonActivity$$ExternalSyntheticLambda0(this));
        int sharedPreferenceExternalPlayer = this.preferenceHelper.getSharedPreferenceExternalPlayer();
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            this.vod_url = episodeModel.getUrl();
        } else {
            this.vod_url = GetSharedInfo.getEpisodeUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), episodeModel.getId(), episodeModel.getContainer_extension());
        }
        if (sharedPreferenceExternalPlayer == 0) {
            if (!this.preferenceHelper.getSharedPreferenceISM3U()) {
                this.preferenceHelper.setSharedPreferenceEpisodeModels(this.episodeModels);
            }
            Intent intent = GetSharedInfo.isTVDevice(this) ? new Intent(this, (Class<?>) SeriesPlayerActivity.class) : new Intent(this, (Class<?>) SeriesMobilePlayer.class);
            intent.putExtra("season_pos", this.pre_season_pos);
            intent.putExtra("position", num);
            intent.putExtra("series_name", this.series_name);
            intent.putExtra("season_name", this.seasonModelList.get(this.pre_season_pos).getName());
            intent.putExtra("tmdb_id", this.currentSeries.getTmdb());
            startActivity(intent);
            return null;
        }
        if (sharedPreferenceExternalPlayer == 1) {
            if (Utils.getVlcPackageInfo(this) != null) {
                externalvlcplayer(this.vod_url, episodeModel.getTitle());
                return null;
            }
            showExternalPlayerDialog(1);
            return null;
        }
        if (sharedPreferenceExternalPlayer != 2) {
            return null;
        }
        Utils.MXPackageInfo mXPackageInfo = Utils.getMXPackageInfo(this);
        if (mXPackageInfo != null) {
            externalMXplayer(mXPackageInfo.packageName, mXPackageInfo.activityName, this.vod_url);
            return null;
        }
        showExternalPlayerDialog(2);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExternalPlayerDialog$6(int i, DialogInterface dialogInterface, int i2) {
        Intent data;
        if (i != 1) {
            data = i != 2 ? null : new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad"));
        } else {
            data = new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=en_US"));
        }
        startActivity(data);
    }

    private void showExternalPlayerDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.wordModels.getInstall_external_player());
        builder.setMessage(this.wordModels.getWant_external_player()).setCancelable(false).setPositiveButton(this.wordModels.getOk(), new MovieActivity$$ExternalSyntheticLambda0(this, i, 3)).setNegativeButton(this.wordModels.getCancel(), MovieActivity$$ExternalSyntheticLambda1.INSTANCE$3);
        builder.create().show();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            switch (keyEvent.getKeyCode()) {
                case 19:
                    if (this.season_list.hasFocus() && this.season_pos == 0) {
                        this.image_back.setFocusable(true);
                        this.image_back.requestFocus();
                        return true;
                    }
                    if (this.episode_list.hasFocus() && this.episode_pos == 0) {
                        this.image_back.setFocusable(true);
                        this.image_back.requestFocus();
                        return true;
                    }
                    break;
                case 20:
                    if (this.image_back.hasFocus()) {
                        this.image_back.setFocusable(false);
                        this.season_list.requestFocus();
                        return true;
                    }
                    break;
                case 21:
                    if (this.episode_list.hasFocus()) {
                        this.seasonAdapter.setSelectedItem(this.pre_season_pos);
                        this.season_list.requestFocus();
                        return true;
                    }
                    break;
                case 22:
                    if (this.season_list.hasFocus()) {
                        this.seasonAdapter.setSelectedItem(this.pre_season_pos);
                        this.seasonAdapter.setFocusDisable(this.pre_season_pos, true);
                        this.episode_list.requestFocus();
                        return true;
                    }
                    if (this.image_back.hasFocus()) {
                        this.image_back.setFocusable(false);
                        this.episode_list.requestFocus();
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_season);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.image_url = getIntent().getStringExtra("image_url");
        this.series_name = getIntent().getStringExtra("series_name");
        this.series_id = getIntent().getStringExtra("series_id");
        if (this.preferenceHelper.getSharedPreferenceISM3U() || this.series_id.isEmpty()) {
            this.currentSeries = RealmController.with().getSeriesByName(this.series_name);
        } else {
            this.currentSeries = RealmController.with().getSeriesById(this.series_id);
        }
        this.series_id = this.currentSeries.getSeries_id();
        this.txt_name.setText(this.series_name);
        final int i = 0;
        SeasonRecyclerAdapter seasonRecyclerAdapter = new SeasonRecyclerAdapter(this, new ArrayList(), new Function3(this) { // from class: com.nettv.livestore.activities.SeasonActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ SeasonActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i) {
                    case 0:
                        return this.f$0.lambda$onCreate$0((Season) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$onCreate$2((EpisodeModel) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        });
        this.seasonAdapter = seasonRecyclerAdapter;
        this.season_list.setAdapter(seasonRecyclerAdapter);
        final int i2 = 1;
        EpisodeRecyclerAdapter episodeRecyclerAdapter = new EpisodeRecyclerAdapter(this, new ArrayList(), this.series_name, this.season_name, new Function3(this) { // from class: com.nettv.livestore.activities.SeasonActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ SeasonActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i2) {
                    case 0:
                        return this.f$0.lambda$onCreate$0((Season) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$onCreate$2((EpisodeModel) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        });
        this.episodeAdapter = episodeRecyclerAdapter;
        this.episode_list.setAdapter(episodeRecyclerAdapter);
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            List<Season> seasonBySeriesName = RealmController.with().getSeasonBySeriesName(this.series_name);
            this.seasonModelList = seasonBySeriesName;
            if (seasonBySeriesName != null && seasonBySeriesName.size() > 0) {
                int season_pos = this.currentSeries.getSeason_pos();
                this.pre_season_pos = season_pos;
                if (season_pos > this.seasonModelList.size() - 1) {
                    this.pre_season_pos = 0;
                }
                this.seasonAdapter.setSeasonList(this.seasonModelList);
                List<EpisodeModel> episodeModels = this.seasonModelList.get(this.pre_season_pos).getEpisodeModels();
                this.episodeModels = episodeModels;
                this.episodeAdapter.setEpisodeModels(episodeModels, this.seasonModelList.get(this.pre_season_pos).getName());
                int episode_pos = this.currentSeries.getEpisode_pos();
                this.episode_pos = episode_pos;
                if (episode_pos > this.episodeModels.size() - 1) {
                    this.episode_pos = 0;
                }
                this.episode_list.scrollToPosition(this.episode_pos);
                this.seasonAdapter.setSelectedItem(this.pre_season_pos);
                this.season_list.setSelectedPosition(this.pre_season_pos);
                this.season_list.requestFocus();
            }
        } else {
            getSeriesInfo();
        }
        String str = this.image_url;
        if (str == null || str.isEmpty()) {
            return;
        }
        Glide.with((FragmentActivity) this).load(this.image_url).into(this.image_bg);
    }
}
