package com.nettv.livestore.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.MovieCreditRecyclerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.MovieCreditModel;
import com.nettv.livestore.models.MovieCreditResponse;
import com.nettv.livestore.models.TMDBVideoResponse;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.RetroClass;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveHorizontalGridView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class MovieCreditActivity extends AppCompatActivity implements View.OnClickListener {
    public MovieCreditRecyclerAdapter adapter;
    public ImageButton btn_back;
    public Button btn_play;
    public Button btn_trailer;
    public MovieCreditModel currentMovieModel;
    public ImageView movie_bg;
    public ImageView movie_logo;
    public int person_id;
    public SimpleRatingBar ratingBar;
    public LiveHorizontalGridView recycler_vod;
    public TextView txt_description;
    public TextView txt_name;
    public TextView txt_rating;
    public TextView txt_year;
    public List<MovieCreditModel> modelList = new ArrayList();
    public WordModels wordModels = new WordModels();

    private void getMovieCredit() {
        RetroClass.getAPIService(Constants.PERSON_API).getMovieCreditModels(this.person_id + "/movie_credits?api_key=" + Constants.IMDB_KEY + "&language=en-US").enqueue(new Callback<MovieCreditResponse>() { // from class: com.nettv.livestore.activities.MovieCreditActivity.1
            @Override // retrofit2.Callback
            public void onFailure(Call<MovieCreditResponse> call, Throwable th) {
            }

            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // retrofit2.Callback
            public void onResponse(Call<MovieCreditResponse> call, Response<MovieCreditResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                MovieCreditActivity.this.modelList.addAll(response.body().getCast());
                MovieCreditActivity.this.getNewModelList(response.body().getCast());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.MovieCreditModel>] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.MovieCreditModel>] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.MovieCreditModel>] */
    public void getNewModelList(List<MovieCreditModel> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (MovieCreditModel movieCreditModel : list) {
            if (RealmController.with().getContainMoviesByTitle(movieCreditModel.getTitle()) == null) {
                this.modelList.remove(movieCreditModel);
            }
        }
        this.adapter.setMovieCreditModels(this.modelList);
        if (this.modelList.size() > 0) {
            setMovieInfo((MovieCreditModel) this.modelList.get(0));
        }
    }

    private void getTrailerVideoId() {
        RetroClass.getAPIService(Constants.IMDB_API).getTmdbVideoModels(this.currentMovieModel.getId() + "/videos?api_key=" + Constants.IMDB_KEY).enqueue(new Callback<TMDBVideoResponse>() { // from class: com.nettv.livestore.activities.MovieCreditActivity.2
            @Override // retrofit2.Callback
            public void onFailure(Call<TMDBVideoResponse> call, Throwable th) {
                Toast.makeText(MovieCreditActivity.this, "No Trailer", 0).show();
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<TMDBVideoResponse> call, Response<TMDBVideoResponse> response) {
                if (!response.isSuccessful() || response.body() == null || response.body().getResults().size() <= 0) {
                    Toast.makeText(MovieCreditActivity.this, "No Trailer", 0).show();
                    return;
                }
                String key = response.body().getResults().get(0).getKey();
                if (!GetSharedInfo.isTVDevice(MovieCreditActivity.this)) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("vnd.youtube:" + key));
                        intent.addFlags(276856832);
                        MovieCreditActivity.this.startActivity(intent);
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                }
                Utils.YoutubePackageInfo youtubePackageInfo = Utils.getYoutubePackageInfo(MovieCreditActivity.this);
                if (youtubePackageInfo == null) {
                    Toast.makeText(MovieCreditActivity.this, "Please install youtube App.", 0).show();
                    return;
                }
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + key));
                intent2.setPackage(youtubePackageInfo.packageName);
                MovieCreditActivity.this.startActivity(intent2);
            }
        });
    }

    private void initView() {
        this.movie_bg = (ImageView) findViewById(R.id.movie_bg);
        this.movie_logo = (ImageView) findViewById(R.id.image_logo);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.recycler_vod = (LiveHorizontalGridView) findViewById(R.id.recycler_vod);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.txt_year = (TextView) findViewById(R.id.txt_year);
        this.txt_rating = (TextView) findViewById(R.id.txt_rating);
        this.txt_description = (TextView) findViewById(R.id.txt_description);
        this.ratingBar = (SimpleRatingBar) findViewById(R.id.rating_bar);
        this.btn_play = (Button) findViewById(R.id.btn_play);
        this.btn_trailer = (Button) findViewById(R.id.btn_trailer);
        this.btn_back.setOnClickListener(this);
        this.btn_play.setOnClickListener(this);
        this.btn_trailer.setOnClickListener(this);
        MovieCreditRecyclerAdapter movieCreditRecyclerAdapter = new MovieCreditRecyclerAdapter(this, new ArrayList());
        this.adapter = movieCreditRecyclerAdapter;
        movieCreditRecyclerAdapter.setItemClickListener(new MovieCreditRecyclerAdapter.ItemClickListener() { // from class: com.nettv.livestore.activities.MovieCreditActivity.3
            @Override // com.nettv.livestore.adapter.MovieCreditRecyclerAdapter.ItemClickListener
            public void onFocusPosition(MovieCreditModel movieCreditModel, int i) {
                MovieCreditActivity.this.setMovieInfo(movieCreditModel);
            }

            @Override // com.nettv.livestore.adapter.MovieCreditRecyclerAdapter.ItemClickListener
            public void onItemClick(MovieCreditModel movieCreditModel, int i) {
                MovieCreditActivity.this.setMovieInfo(movieCreditModel);
            }
        });
        this.recycler_vod.setAdapter(this.adapter);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_vod.setLoop(false);
            this.recycler_vod.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_vod.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.MovieCreditActivity.4
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
        } else {
            this.recycler_vod.setLayoutManager(new LinearLayoutManager(this, 0, false));
            this.recycler_vod.setHasFixedSize(true);
        }
        this.btn_play.setText(this.wordModels.getFind_movie());
        this.btn_trailer.setText(this.wordModels.getWatch_trailer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMovieInfo(MovieCreditModel movieCreditModel) {
        this.currentMovieModel = movieCreditModel;
        this.txt_name.setText(movieCreditModel.getTitle());
        this.txt_description.setText(movieCreditModel.getOverview());
        this.txt_year.setText(movieCreditModel.getRelease_date());
        try {
            this.txt_rating.setText("" + (movieCreditModel.getVote_average() / 2.0f));
            this.ratingBar.setRating(movieCreditModel.getVote_average() / 2.0f);
        } catch (Exception unused) {
            this.txt_rating.setText("N/A");
            this.ratingBar.setRating(0.0f);
        }
        if (movieCreditModel.getBackdrop_path() == null || movieCreditModel.getBackdrop_path().isEmpty()) {
            Glide.with(getApplicationContext()).load(Integer.valueOf(R.drawable.background1)).into(this.movie_bg);
        } else {
            Glide.with(getApplicationContext()).load(Constants.IMDB_IMAGE_PREF + movieCreditModel.getBackdrop_path()).into(this.movie_bg);
        }
        if (movieCreditModel.getPoster_path() == null || movieCreditModel.getPoster_path().isEmpty()) {
            Glide.with(getApplicationContext()).load(Integer.valueOf(R.drawable.default_bg)).into(this.movie_logo);
            return;
        }
        Glide.with(getApplicationContext()).load(Constants.IMDB_IMAGE_PREF + movieCreditModel.getPoster_path()).into(this.movie_logo);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 19) {
                if (keyCode == 20 && this.btn_back.hasFocus()) {
                    this.btn_back.setFocusable(false);
                    this.btn_play.requestFocus();
                    return true;
                }
            } else if (this.btn_play.hasFocus() || this.btn_trailer.hasFocus()) {
                this.btn_back.setFocusable(true);
                this.btn_back.requestFocus();
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            finish();
            return;
        }
        if (id != R.id.btn_play) {
            if (id != R.id.btn_trailer) {
                return;
            }
            getTrailerVideoId();
        } else if (this.currentMovieModel != null) {
            Intent intent = new Intent(this, (Class<?>) SearchActivity.class);
            intent.putExtra("search_key", this.currentMovieModel.getTitle());
            startActivity(intent);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_creadit);
        Utils.FullScreenCall(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.person_id = getIntent().getIntExtra("person_id", 0);
        getIntent().getStringExtra("name");
        if (this.person_id > 0) {
            getMovieCredit();
        }
        this.btn_back.setFocusable(false);
        this.btn_play.requestFocus();
    }
}
