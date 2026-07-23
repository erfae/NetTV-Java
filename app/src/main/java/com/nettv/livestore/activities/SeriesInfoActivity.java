package com.nettv.livestore.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import io.realm.Realm$$ExternalSyntheticLambda0;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class SeriesInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageButton btn_back;
    public RelativeLayout btn_favorite;
    public RelativeLayout btn_play;
    public RelativeLayout btn_trailer;
    public SeriesModel currentSeries;
    public ImageView image_fav;
    public ImageView movie_logo;
    public PreferenceHelper preferenceHelper;
    public SimpleRatingBar ratingBar;
    public ImageView series_bg;
    public TextView str_added;
    public TextView str_cast;
    public TextView txt_added;
    public TextView txt_cast;
    public TextView txt_description;
    public TextView txt_duration;
    public TextView txt_genre;
    public TextView txt_name;
    public TextView txt_play;
    public TextView txt_rating;
    public TextView txt_trailer;
    public TextView txt_year;
    public String stream_id = "";
    public boolean is_fav = false;
    public String series_background = "";
    public WordModels wordModels = new WordModels();

    private void getSeriesInfo() {
        Volley.newRequestQueue(this).add(new StringRequest(0, this.preferenceHelper.getSharedPreferenceServerUrl() + "/player_api.php?action=get_series_info&username=" + this.preferenceHelper.getSharedPreferenceUsername() + "&password=" + this.preferenceHelper.getSharedPreferencePassword() + "&series_id=" + this.stream_id, new SeriesInfoActivity$$ExternalSyntheticLambda0(this, 0), Realm$$ExternalSyntheticLambda0.INSTANCE$18));
    }

    private void goToSeason() {
        Intent intent = new Intent(this, (Class<?>) SeasonActivity.class);
        intent.putExtra("series_name", this.currentSeries.getName());
        if (!this.preferenceHelper.getSharedPreferenceISM3U()) {
            intent.putExtra("series_id", this.currentSeries.getSeries_id());
        }
        if (!this.series_background.isEmpty()) {
            intent.putExtra("image_url", this.series_background);
        }
        startActivity(intent);
    }

    private void initView() {
        this.series_bg = (ImageView) findViewById(R.id.series_bg);
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
        this.btn_play.setOnClickListener(this);
        this.btn_favorite.setOnClickListener(this);
        this.btn_trailer.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.ratingBar = (SimpleRatingBar) findViewById(R.id.rating_bar);
        this.txt_genre = (TextView) findViewById(R.id.txt_genre);
        this.str_cast = (TextView) findViewById(R.id.str_cast);
        this.txt_cast = (TextView) findViewById(R.id.txt_cast);
        this.str_added = (TextView) findViewById(R.id.str_added);
        this.txt_play.setText(this.wordModels.getWatch_season());
        this.txt_trailer.setText(this.wordModels.getTrailer());
        this.str_cast.setText(this.wordModels.getCast());
        this.str_added.setText(this.wordModels.getDate_added());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSeriesInfo$0(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("info")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("info");
                if (jSONObject2.has("backdrop_path")) {
                    try {
                        this.series_background = jSONObject2.getJSONArray("backdrop_path").getString(0);
                        Glide.with(getApplicationContext()).load(this.series_background).into(this.series_bg);
                    } catch (Exception unused) {
                        this.series_background = "";
                    }
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getSeriesInfo$1(VolleyError volleyError) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2() {
        this.preferenceHelper.setSharedPreferenceSeriesFavNames(RealmController.with().getFavSeriesNames());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3() {
        this.preferenceHelper.setSharedPreferenceSeriesFavNames(RealmController.with().getFavSeriesNames());
    }

    private void setSeriesInfo() {
        this.txt_description.setText(this.currentSeries.getPlot());
        this.txt_genre.setText(this.currentSeries.getGenre());
        this.txt_cast.setText(this.currentSeries.getCast());
        try {
            this.txt_rating.setText(String.valueOf(this.currentSeries.getRating() / 2.0f));
            this.ratingBar.setRating(this.currentSeries.getRating() / 2.0f);
        } catch (Exception unused) {
            this.txt_rating.setText("0.0");
            this.ratingBar.setRating(0.0f);
        }
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
                    if (this.btn_play.hasFocus() || this.btn_trailer.hasFocus() || this.btn_favorite.hasFocus()) {
                        this.btn_back.setFocusable(true);
                        this.btn_back.requestFocus();
                        return true;
                    }
                    break;
                case 20:
                case 22:
                    if (this.btn_back.hasFocus()) {
                        this.btn_back.setFocusable(false);
                        this.btn_play.requestFocus();
                        return true;
                    }
                    break;
                case 21:
                    if (this.btn_play.hasFocus()) {
                        this.btn_back.setFocusable(true);
                        this.btn_back.requestFocus();
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back /* 2131427464 */:
                finish();
                break;
            case R.id.btn_fav /* 2131427469 */:
                int i = 1;
                if (!this.is_fav) {
                    this.is_fav = true;
                    this.image_fav.setImageResource(R.drawable.ic_star_selected);
                    this.image_fav.setColorFilter(getResources().getColor(R.color.yellow));
                    RealmController.with().addToFavSeries(this.currentSeries.getName(), true, new SeriesInfoActivity$$ExternalSyntheticLambda0(this, 2));
                } else {
                    this.is_fav = false;
                    this.image_fav.setImageResource(R.drawable.ic_star);
                    this.image_fav.setColorFilter(getResources().getColor(R.color.white));
                    RealmController.with().addToFavSeries(this.currentSeries.getName(), false, new SeriesInfoActivity$$ExternalSyntheticLambda0(this, i));
                }
                break;
            case R.id.btn_play /* 2131427480 */:
                goToSeason();
                break;
            case R.id.btn_trailer /* 2131427491 */:
                String youtube = this.currentSeries.getYoutube();
                if (youtube == null || youtube.isEmpty()) {
                    Toast.makeText(this, this.wordModels.getNo_trailer(), 0).show();
                } else {
                    watchYoutubeVideo(youtube);
                }
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String string;
        super.onCreate(bundle);
        setContentView(R.layout.activity_series_info);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.stream_id = getIntent().getStringExtra("series_id");
        if (this.preferenceHelper.getSharedPreferenceISM3U() || this.stream_id.isEmpty()) {
            this.currentSeries = RealmController.with().getSeriesByName(getIntent().getStringExtra("name"));
        } else {
            this.currentSeries = RealmController.with().getSeriesById(this.stream_id);
        }
        getIntent().getStringExtra("category_id");
        this.stream_id = this.currentSeries.getSeries_id();
        if (this.currentSeries.isIs_favorite()) {
            this.is_fav = true;
            this.image_fav.setImageResource(R.drawable.ic_star_selected);
            this.image_fav.setColorFilter(getResources().getColor(R.color.yellow));
        } else {
            this.is_fav = false;
            this.image_fav.setImageResource(R.drawable.ic_star);
            this.image_fav.setColorFilter(getResources().getColor(R.color.white));
        }
        this.txt_name.setText(this.currentSeries.getName());
        TextView textView = this.txt_year;
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formateDateFromstring("yyyy-MM-dd", "yyyy", this.currentSeries.getReleaseDate()));
        if (this.currentSeries.getGenre().isEmpty()) {
            string = "";
        } else {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(", ");
            sbM.append(this.currentSeries.getGenre());
            string = sbM.toString();
        }
        sb.append(string);
        textView.setText(sb.toString());
        try {
            this.txt_added.setText(Utils.getDate(Long.parseLong(this.currentSeries.getLast_modified())));
        } catch (Exception unused) {
            this.txt_added.setText(this.currentSeries.getLast_modified());
        }
        this.txt_duration.setText("");
        try {
            Glide.with((FragmentActivity) this).load(this.currentSeries.getStream_icon()).error(R.drawable.default_series).into(this.movie_logo);
        } catch (Exception unused2) {
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.default_series)).error(R.drawable.default_series).into(this.movie_logo);
        }
        setSeriesInfo();
        if (!this.preferenceHelper.getSharedPreferenceISM3U()) {
            getSeriesInfo();
        }
        if (this.currentSeries.getYoutube().isEmpty()) {
            this.btn_trailer.setVisibility(8);
            this.btn_play.setNextFocusRightId(R.id.btn_fav);
            this.btn_favorite.setNextFocusLeftId(R.id.btn_play);
        } else {
            this.btn_trailer.setVisibility(0);
        }
        this.btn_back.setFocusable(false);
        this.btn_play.requestFocus();
    }
}
