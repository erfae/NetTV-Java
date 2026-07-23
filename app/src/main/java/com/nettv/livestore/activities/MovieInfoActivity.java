package com.nettv.livestore.activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.MimeTypes;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.mobile.MovieMobilePlayer;
import com.nettv.livestore.adapter.CastRecyclerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.FocusStatus;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmChangeItemListener;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CastModel;
import com.nettv.livestore.models.CastResponse;
import com.nettv.livestore.models.MovieInfoResponse;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.RetroClass;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveHorizontalGridView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class MovieInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public CastRecyclerAdapter adapter;
    public ImageButton btn_back;
    public RelativeLayout btn_favorite;
    public RelativeLayout btn_play;
    public Button btn_show_cast;
    public RelativeLayout btn_trailer;
    public LiveHorizontalGridView cast_list;
    public MovieModel currentMovie;
    public ImageView image_fav;
    public ConstraintLayout main_lay;
    public MovieInfoResponse movieInfoResponse;
    public ImageView movie_bg;
    public ImageView movie_logo;
    public PreferenceHelper preferenceHelper;
    public SimpleRatingBar ratingBar;
    public TextView str_added;
    public TextView str_cast;
    public TextView txt_added;
    public TextView txt_description;
    public TextView txt_duration;
    public TextView txt_genre;
    public TextView txt_name;
    public TextView txt_play;
    public TextView txt_rating;
    public TextView txt_trailer;
    public TextView txt_year;
    public List<CastModel> castModels = new ArrayList();
    public String tmdb_id = "";
    public String category_name = "";
    public String stream_id = "";
    public String vod_url = "";
    public FocusStatus focusStatus = FocusStatus.second;
    public boolean is_fav = false;
    public WordModels wordModels = new WordModels();

    /* JADX INFO: renamed from: com.nettv.livestore.activities.MovieInfoActivity$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$vilhao$player$apps$FocusStatus;

        static {
            int[] iArr = new int[FocusStatus.values().length];
            $SwitchMap$com$vilhao$player$apps$FocusStatus = iArr;
            try {
                iArr[FocusStatus.second.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$vilhao$player$apps$FocusStatus[FocusStatus.third.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$vilhao$player$apps$FocusStatus[FocusStatus.first.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private boolean checkAdultMovies(String str) {
        return str.contains("xxx") || str.contains("porn") || str.contains("adult");
    }

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
        try {
            Uri uri = Uri.parse(str);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setPackage("org.videolan.vlc");
            intent.setDataAndTypeAndNormalize(uri, "video/*");
            intent.setFlags(268435456);
            startActivity(intent);
        } catch (Exception unused) {
        }
    }

    private void getCastModels() {
        RetroClass.getAPIService(Constants.IMDB_API).getCastModels(Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.tmdb_id, "/credits?api_key=", Constants.IMDB_KEY)).enqueue(new Callback<CastResponse>() { // from class: com.nettv.livestore.activities.MovieInfoActivity.2
            @Override // retrofit2.Callback
            public void onFailure(Call<CastResponse> call, Throwable th) {
                MovieInfoActivity.this.setCastAdapter(new ArrayList());
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    MovieInfoActivity.this.setCastAdapter(new ArrayList());
                } else {
                    MovieInfoActivity.this.setCastAdapter(response.body().getCast());
                }
            }
        });
    }

    private void getMovieInfo() {
        RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_vod_info(this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.stream_id).enqueue(new Callback<MovieInfoResponse>() { // from class: com.nettv.livestore.activities.MovieInfoActivity.1
            @Override // retrofit2.Callback
            public void onFailure(Call<MovieInfoResponse> call, Throwable th) {
                MovieInfoActivity.this.setNoDescriptionData();
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<MovieInfoResponse> call, Response<MovieInfoResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                MovieInfoActivity.this.movieInfoResponse = response.body();
                try {
                    MovieInfoActivity.this.setModelInfo();
                } catch (Exception unused) {
                    MovieInfoActivity.this.setNoDescriptionData();
                }
            }
        });
    }

    private void initView() {
        this.main_lay = (ConstraintLayout) findViewById(R.id.fullContainer);
        this.movie_bg = (ImageView) findViewById(R.id.movie_bg);
        this.movie_logo = (ImageView) findViewById(R.id.image_logo);
        this.image_fav = (ImageView) findViewById(R.id.image_fav);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.txt_year = (TextView) findViewById(R.id.txt_year);
        this.txt_rating = (TextView) findViewById(R.id.txt_rating);
        this.txt_duration = (TextView) findViewById(R.id.txt_duration);
        this.txt_added = (TextView) findViewById(R.id.txt_added);
        this.txt_description = (TextView) findViewById(R.id.txt_description);
        this.txt_play = (TextView) findViewById(R.id.txt_play);
        this.txt_trailer = (TextView) findViewById(R.id.txt_trailer);
        this.btn_play = (RelativeLayout) findViewById(R.id.btn_play);
        this.btn_favorite = (RelativeLayout) findViewById(R.id.btn_fav);
        this.btn_trailer = (RelativeLayout) findViewById(R.id.btn_trailer);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_show_cast = (Button) findViewById(R.id.btn_show_cast);
        this.str_added = (TextView) findViewById(R.id.str_added);
        this.btn_show_cast.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.btn_play.setOnClickListener(this);
        this.btn_favorite.setOnClickListener(this);
        this.btn_trailer.setOnClickListener(this);
        this.ratingBar = (SimpleRatingBar) findViewById(R.id.rating_bar);
        this.txt_genre = (TextView) findViewById(R.id.txt_genre);
        this.str_cast = (TextView) findViewById(R.id.str_cast);
        this.cast_list = (LiveHorizontalGridView) findViewById(R.id.cast_list);
        CastRecyclerAdapter castRecyclerAdapter = new CastRecyclerAdapter(this, new ArrayList(), new LiveActivity$$ExternalSyntheticLambda3(this, 4));
        this.adapter = castRecyclerAdapter;
        this.cast_list.setAdapter(castRecyclerAdapter);
        if (GetSharedInfo.isTVDevice(this)) {
            this.cast_list.setLoop(false);
            this.cast_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.cast_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.MovieInfoActivity.3
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
            this.btn_show_cast.setVisibility(8);
        } else {
            this.cast_list.setLayoutManager(new LinearLayoutManager(this, 0, false));
        }
        this.str_added.setText(this.wordModels.getDate_added() + ":");
        this.txt_play.setText(this.wordModels.getPlay());
        this.txt_trailer.setText(this.wordModels.getTrailer());
        this.str_cast.setText(this.wordModels.getCast());
        this.btn_back.setFocusable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initView$0(CastModel castModel, Integer num, Boolean bool) {
        if (!bool.booleanValue() || castModel.getId() <= 0) {
            return null;
        }
        Intent intent = new Intent(this, (Class<?>) MovieCreditActivity.class);
        intent.putExtra("person_id", castModel.getId());
        intent.putExtra("name", this.currentMovie.getName());
        startActivity(intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1() {
        this.preferenceHelper.setSharedPreferenceVodFavNames(RealmController.with().getFavMovieNames());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2() {
        this.preferenceHelper.setSharedPreferenceVodFavNames(RealmController.with().getFavMovieNames());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExternalPlayerDialog$3(int i, DialogInterface dialogInterface, int i2) {
        Intent data;
        if (i != 1) {
            data = i != 2 ? null : new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad"));
        } else {
            data = new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=en_US"));
        }
        startActivity(data);
    }

    private void playMovie() {
        Intent intent = GetSharedInfo.isTVDevice(this) ? new Intent(this, (Class<?>) MoviePlayerActivity.class) : new Intent(this, (Class<?>) MovieMobilePlayer.class);
        intent.putExtra("name", this.currentMovie.getName());
        intent.putExtra("stream_id", this.currentMovie.getStream_id());
        intent.putExtra("description", this.txt_description.getText().toString());
        intent.putExtra("category_name", this.category_name);
        intent.putExtra("tmdb_id", this.tmdb_id);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCastAdapter(List<CastModel> list) {
        this.castModels = list;
        if (list.size() <= 0) {
            this.btn_show_cast.setVisibility(8);
            this.cast_list.setVisibility(8);
            this.str_cast.setVisibility(8);
            this.adapter.setMovieData(new ArrayList());
            return;
        }
        if (!GetSharedInfo.isTVDevice(this)) {
            this.btn_show_cast.setVisibility(0);
        }
        this.cast_list.setVisibility(0);
        this.str_cast.setVisibility(0);
        this.adapter.setMovieData(list);
    }

    private void setFull() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.main_lay);
        int i = AnonymousClass4.$SwitchMap$com$vilhao$player$apps$FocusStatus[getFocusStatus().ordinal()];
        if (i == 1) {
            constraintSet.setGuidelinePercent(R.id.guide_line1, 0.38f);
            constraintSet.setGuidelinePercent(R.id.guide_line2, 0.85f);
            constraintSet.setGuidelinePercent(R.id.guide_line3, 1.25f);
            constraintSet.connect(R.id.image_logo, 4, R.id.guide_line1, 4, 0);
            constraintSet.connect(R.id.image_logo, 3, R.id.guide_line1, 3, 0);
        } else if (i == 2) {
            constraintSet.setGuidelinePercent(R.id.guide_line1, 0.0f);
            constraintSet.setGuidelinePercent(R.id.guide_line2, 0.6f);
            constraintSet.setGuidelinePercent(R.id.guide_line3, 1.0f);
            constraintSet.connect(R.id.image_logo, 3, R.id.btn_view, 4, 0);
            constraintSet.connect(R.id.image_logo, 4, R.id.guide_line2, 4, 0);
            constraintSet.connect(R.id.str_cast, 3, R.id.guide_line2, 3, 0);
        }
        constraintSet.applyTo(this.main_lay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setModelInfo() {
        this.txt_added.setText(Utils.getDate(Long.parseLong(this.currentMovie.getAdded())));
        this.txt_description.setText(this.movieInfoResponse.getInfo().getDescription());
        try {
            this.txt_rating.setText("" + (Float.parseFloat(this.currentMovie.getRating()) / 2.0f));
            this.ratingBar.setRating(Float.parseFloat(this.currentMovie.getRating()) / 2.0f);
        } catch (Exception unused) {
            this.txt_rating.setText("N/A");
            this.ratingBar.setRating(0.0f);
        }
        this.txt_year.setText(Utils.formateDateFromstring("yyyy-MM-dd", "yyyy", this.movieInfoResponse.getInfo().getReleasedate()));
        this.txt_duration.setText(this.movieInfoResponse.getInfo().getDuration());
        this.txt_genre.setText(this.movieInfoResponse.getInfo().getGenre());
        if (!this.movieInfoResponse.getInfo().getCover_big().isEmpty()) {
            Glide.with(getApplicationContext()).load(this.movieInfoResponse.getInfo().getCover_big()).into(this.movie_bg);
        }
        if (this.movieInfoResponse.getInfo().getYoutube_trailer().isEmpty()) {
            this.btn_trailer.setVisibility(8);
            this.btn_favorite.setNextFocusLeftId(R.id.btn_play);
            this.btn_play.setNextFocusRightId(R.id.btn_fav);
        } else {
            this.btn_trailer.setVisibility(0);
        }
        if (this.movieInfoResponse.getInfo().getTmdb_id().isEmpty()) {
            return;
        }
        this.tmdb_id = this.movieInfoResponse.getInfo().getTmdb_id();
        getCastModels();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNoDescriptionData() {
        this.txt_rating.setText("N/A");
        this.txt_genre.setText("N/A");
        this.ratingBar.setRating(0.0f);
        this.txt_year.setText("N/A");
        this.txt_duration.setText("N/A");
        this.txt_description.setText(this.wordModels.getNo_information());
        this.txt_added.setText("N/A");
        this.btn_trailer.setVisibility(8);
        this.btn_play.setNextFocusRightId(R.id.btn_fav);
        this.btn_favorite.setNextFocusLeftId(R.id.btn_play);
    }

    private void showExternalPlayerDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.wordModels.getInstall_external_player());
        builder.setMessage(this.wordModels.getWant_external_player()).setCancelable(false).setPositiveButton(this.wordModels.getOk(), new MovieActivity$$ExternalSyntheticLambda0(this, i, 1)).setNegativeButton(this.wordModels.getCancel(), MovieActivity$$ExternalSyntheticLambda1.INSTANCE$1);
        builder.create().show();
    }

    private void watchYoutubeVideo(String str) {
        if (!GetSharedInfo.isTVDevice(this)) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("vnd.youtube:" + str));
                intent.addFlags(276856832);
                startActivity(intent);
                return;
            } catch (Exception unused) {
                return;
            }
        }
        Utils.YoutubePackageInfo youtubePackageInfo = Utils.getYoutubePackageInfo(this);
        if (youtubePackageInfo == null) {
            Toast.makeText(this, "Please install youtube App.", 0).show();
            return;
        }
        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + str));
        intent2.setPackage(youtubePackageInfo.packageName);
        startActivity(intent2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            switch (keyEvent.getKeyCode()) {
                case 19:
                    int i = AnonymousClass4.$SwitchMap$com$vilhao$player$apps$FocusStatus[getFocusStatus().ordinal()];
                    if (i == 1) {
                        setFocusStatus(FocusStatus.first);
                        this.btn_back.setFocusable(true);
                        this.btn_back.requestFocus();
                        return true;
                    }
                    if (i == 2) {
                        setFocusStatus(FocusStatus.second);
                        this.btn_play.setFocusable(true);
                        this.btn_favorite.setFocusable(true);
                        this.btn_trailer.setFocusable(true);
                        this.btn_play.requestFocus();
                        setFull();
                        return true;
                    }
                    if (i == 3) {
                        return true;
                    }
                    break;
                case 20:
                    int i2 = AnonymousClass4.$SwitchMap$com$vilhao$player$apps$FocusStatus[getFocusStatus().ordinal()];
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 == 3) {
                                setFocusStatus(FocusStatus.second);
                                this.btn_back.setFocusable(false);
                                this.btn_play.requestFocus();
                            }
                        }
                        return true;
                    }
                    List<CastModel> list = this.castModels;
                    if (list != null && list.size() > 0) {
                        setFocusStatus(FocusStatus.third);
                        this.btn_play.setFocusable(false);
                        this.btn_favorite.setFocusable(false);
                        this.btn_trailer.setFocusable(false);
                        setFull();
                        this.cast_list.requestFocus();
                    }
                    return true;
                case 21:
                    int i3 = AnonymousClass4.$SwitchMap$com$vilhao$player$apps$FocusStatus[getFocusStatus().ordinal()];
                    if (i3 != 1) {
                        if (i3 == 3) {
                            return true;
                        }
                    } else if (this.btn_play.hasFocus()) {
                        setFocusStatus(FocusStatus.first);
                        this.btn_back.setFocusable(true);
                        this.btn_back.requestFocus();
                        return true;
                    }
                case 22:
                    if (AnonymousClass4.$SwitchMap$com$vilhao$player$apps$FocusStatus[getFocusStatus().ordinal()] == 3 && this.btn_back.hasFocus()) {
                        setFocusStatus(FocusStatus.second);
                        this.btn_back.setFocusable(false);
                        this.btn_play.requestFocus();
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public FocusStatus getFocusStatus() {
        return this.focusStatus;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        final int i = 0;
        final int i2 = 1;
        switch (view.getId()) {
            case R.id.btn_back /* 2131427464 */:
                finish();
                break;
            case R.id.btn_fav /* 2131427469 */:
                if (checkAdultMovies(this.category_name)) {
                    Toast.makeText(this, this.wordModels.getCant_add_this_movie(), 0).show();
                } else if (!this.is_fav) {
                    this.is_fav = true;
                    this.image_fav.setImageResource(R.drawable.ic_star_selected);
                    this.image_fav.setColorFilter(getResources().getColor(R.color.yellow));
                    RealmController.with().addToFavMovie(this.currentMovie.getName(), true, new RealmChangeItemListener(this) { // from class: com.nettv.livestore.activities.MovieInfoActivity$$ExternalSyntheticLambda0
                        public final /* synthetic */ MovieInfoActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // com.nettv.livestore.helper.RealmChangeItemListener
                        public final void onItemChanged() {
                            switch (i2) {
                                case 0:
                                    this.f$0.lambda$onClick$1();
                                    break;
                                default:
                                    this.f$0.lambda$onClick$2();
                                    break;
                            }
                        }
                    });
                } else {
                    this.is_fav = false;
                    this.image_fav.setImageResource(R.drawable.ic_star);
                    this.image_fav.setColorFilter(getResources().getColor(R.color.white));
                    RealmController.with().addToFavMovie(this.currentMovie.getName(), false, new RealmChangeItemListener(this) { // from class: com.nettv.livestore.activities.MovieInfoActivity$$ExternalSyntheticLambda0
                        public final /* synthetic */ MovieInfoActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // com.nettv.livestore.helper.RealmChangeItemListener
                        public final void onItemChanged() {
                            switch (i) {
                                case 0:
                                    this.f$0.lambda$onClick$1();
                                    break;
                                default:
                                    this.f$0.lambda$onClick$2();
                                    break;
                            }
                        }
                    });
                }
                break;
            case R.id.btn_play /* 2131427480 */:
                int sharedPreferenceExternalPlayer = this.preferenceHelper.getSharedPreferenceExternalPlayer();
                if (this.preferenceHelper.getSharedPreferenceISM3U()) {
                    this.vod_url = this.currentMovie.getUrl();
                } else {
                    this.vod_url = GetSharedInfo.getMovieUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.currentMovie.getStream_id(), this.currentMovie.getExtension());
                }
                if (sharedPreferenceExternalPlayer == 0) {
                    playMovie();
                    break;
                } else if (sharedPreferenceExternalPlayer == 1) {
                    if (Utils.getVlcPackageInfo(this) == null) {
                        showExternalPlayerDialog(1);
                    } else {
                        externalvlcplayer(this.vod_url, this.currentMovie.getName());
                    }
                    break;
                } else if (sharedPreferenceExternalPlayer == 2) {
                    Utils.MXPackageInfo mXPackageInfo = Utils.getMXPackageInfo(this);
                    if (mXPackageInfo == null) {
                        showExternalPlayerDialog(2);
                    } else {
                        externalMXplayer(mXPackageInfo.packageName, mXPackageInfo.activityName, this.vod_url);
                    }
                    break;
                }
                break;
            case R.id.btn_show_cast /* 2131427488 */:
                int i3 = AnonymousClass4.$SwitchMap$com$vilhao$player$apps$FocusStatus[getFocusStatus().ordinal()];
                if (i3 == 1) {
                    List<CastModel> list = this.castModels;
                    if (list != null && list.size() > 0) {
                        setFocusStatus(FocusStatus.third);
                        this.btn_play.setFocusable(false);
                        this.btn_favorite.setFocusable(false);
                        this.btn_trailer.setFocusable(false);
                        setFull();
                        this.btn_show_cast.setText("Hide Cast");
                        this.cast_list.requestFocus();
                        break;
                    }
                } else if (i3 == 2) {
                    setFocusStatus(FocusStatus.second);
                    this.btn_play.setFocusable(true);
                    this.btn_favorite.setFocusable(true);
                    this.btn_trailer.setFocusable(true);
                    this.btn_play.requestFocus();
                    this.btn_show_cast.setText("Show Cast");
                    setFull();
                    break;
                }
                break;
            case R.id.btn_trailer /* 2131427491 */:
                MovieInfoResponse movieInfoResponse = this.movieInfoResponse;
                if (movieInfoResponse == null) {
                    Toast.makeText(this, this.wordModels.getNo_trailer(), 0).show();
                } else {
                    String youtube_trailer = movieInfoResponse.getInfo().getYoutube_trailer();
                    if (youtube_trailer == null || youtube_trailer.isEmpty()) {
                        Toast.makeText(this, this.wordModels.getNo_trailer(), 0).show();
                    } else {
                        watchYoutubeVideo(youtube_trailer);
                    }
                }
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_info);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.stream_id = getIntent().getStringExtra("stream_id");
        if (this.preferenceHelper.getSharedPreferenceISM3U() || this.stream_id.isEmpty()) {
            this.currentMovie = RealmController.with().getMovieByName(getIntent().getStringExtra("name"));
        } else {
            this.currentMovie = RealmController.with().getMovieById(this.stream_id);
        }
        this.category_name = getIntent().getStringExtra("category_name");
        this.stream_id = this.currentMovie.getStream_id();
        boolean zIsIs_favorite = this.currentMovie.isIs_favorite();
        this.is_fav = zIsIs_favorite;
        if (zIsIs_favorite) {
            this.image_fav.setImageResource(R.drawable.ic_star_selected);
            this.image_fav.setColorFilter(getResources().getColor(R.color.yellow));
        } else {
            this.image_fav.setImageResource(R.drawable.ic_star);
            this.image_fav.setColorFilter(getResources().getColor(R.color.white));
        }
        this.txt_name.setText(this.currentMovie.getName());
        try {
            Glide.with((FragmentActivity) this).load(this.currentMovie.getStream_icon()).error(R.drawable.default_bg).into(this.movie_logo);
        } catch (Exception unused) {
            Glide.with(getApplicationContext()).load(Integer.valueOf(R.drawable.default_bg)).error(R.drawable.default_bg).into(this.movie_logo);
        }
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            setNoDescriptionData();
        } else {
            getMovieInfo();
        }
        this.btn_play.requestFocus();
    }

    public void setFocusStatus(FocusStatus focusStatus) {
        this.focusStatus = focusStatus;
    }
}
