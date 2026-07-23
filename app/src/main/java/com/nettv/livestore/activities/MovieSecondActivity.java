package com.nettv.livestore.activities;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.util.MimeTypes;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.mobile.MovieMobilePlayer;
import com.nettv.livestore.adapter.SortSpinnerAdapter;
import com.nettv.livestore.adapter.VodRecyclerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.LockDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.CustomSpinner;
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class MovieSecondActivity extends AppCompatActivity implements View.OnClickListener {
    public List<CategoryModel> categoryModels;
    public EditText et_search;
    public LockDlgFragment lockDlgFragment;
    public RealmResults<MovieModel> movieModels;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_movie;
    public CustomSpinner sort_spinner;
    public TextView txt_back;
    public TextView txt_category;
    public TextView txt_home;
    public TextView txt_live;
    public TextView txt_movie;
    public TextView txt_series;
    public VodRecyclerAdapter vodAdapter;
    public WordModels wordModels;
    public List<String> sortLists = new ArrayList();
    public int category_pos = 0;
    public int sort_pos = 0;
    public int pre_movie_pos = 0;

    /* JADX INFO: renamed from: com.nettv.livestore.activities.MovieSecondActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements VodRecyclerAdapter.ItemClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFavClick$0(int i) {
            MovieSecondActivity.this.vodAdapter.notifyItemChanged(i);
            MovieSecondActivity.this.preferenceHelper.setSharedPreferenceVodFavNames(RealmController.with().getFavMovieNames());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnFavClick$1(int i) {
            MovieSecondActivity.this.vodAdapter.notifyItemChanged(i);
            MovieSecondActivity.this.preferenceHelper.setSharedPreferenceVodFavNames(RealmController.with().getFavMovieNames());
        }

        @Override // com.nettv.livestore.adapter.VodRecyclerAdapter.ItemClickListener
        public void onFavClick(MovieModel movieModel, int i) {
            List<String> list = Constants.xxx_vod_categories;
            MovieSecondActivity movieSecondActivity = MovieSecondActivity.this;
            if (list.contains(movieSecondActivity.categoryModels.get(movieSecondActivity.category_pos).getId())) {
                return;
            }
            RealmController.with().addToFavMovie(movieModel.getName(), true, new MovieSecondActivity$1$$ExternalSyntheticLambda0(this, i, 1));
        }

        @Override // com.nettv.livestore.adapter.VodRecyclerAdapter.ItemClickListener
        public void onFocusPosition(int i) {
            MovieSecondActivity.this.pre_movie_pos = i;
        }

        @Override // com.nettv.livestore.adapter.VodRecyclerAdapter.ItemClickListener
        public void onItemClick(MovieModel movieModel, int i) {
            MovieSecondActivity movieSecondActivity = MovieSecondActivity.this;
            if (movieSecondActivity.category_pos <= 1 && movieSecondActivity.checkAdultMovie(movieModel.getCategory_name().toLowerCase(), movieModel.getCategory_id())) {
                MovieSecondActivity.this.showMovieLockDlgFragment(movieModel, i);
                return;
            }
            Objects.requireNonNull(MovieSecondActivity.this);
            MovieSecondActivity movieSecondActivity2 = MovieSecondActivity.this;
            if (movieSecondActivity2.categoryModels.get(movieSecondActivity2.category_pos).getId().equalsIgnoreCase(Constants.resume_id)) {
                MovieSecondActivity.this.playRecentMovie(movieModel);
                return;
            }
            Intent intent = new Intent(MovieSecondActivity.this, (Class<?>) MovieInfoActivity.class);
            intent.putExtra("name", movieModel.getName());
            intent.putExtra("stream_id", movieModel.getStream_id());
            if (MovieSecondActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                intent.putExtra("category_name", movieModel.getCategory_name());
            } else {
                intent.putExtra("category_name", MovieSecondActivity.this.getMovieCategoryName(movieModel.getCategory_id()));
            }
            MovieSecondActivity.this.startActivity(intent);
        }

        @Override // com.nettv.livestore.adapter.VodRecyclerAdapter.ItemClickListener
        public void onUnFavClick(MovieModel movieModel, int i) {
            RealmController.with().addToFavMovie(movieModel.getName(), false, new MovieSecondActivity$1$$ExternalSyntheticLambda0(this, i, 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAdultMovie(String str, String str2) {
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            return str.contains("xxx") || str.contains("porn") || str.contains("adult");
        }
        return Constants.xxx_vod_categories.contains(str2);
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

    /* JADX INFO: Access modifiers changed from: private */
    public String getMovieCategoryName(String str) {
        for (CategoryModel categoryModel : LTVApp.vod_categories_filter) {
            if (str.equalsIgnoreCase(categoryModel.getId())) {
                return categoryModel.getName();
            }
        }
        return "UnNamed Category";
    }

    private void goToLiveActivity() {
        Intent intent = new Intent();
        intent.putExtra("is_changed", "live");
        setResult(-1, intent);
        finish();
    }

    private void goToSeriesActivity() {
        Intent intent = new Intent();
        intent.putExtra("is_changed", "series");
        setResult(-1, intent);
        finish();
    }

    private void initView() {
        this.txt_home = (TextView) findViewById(R.id.txt_home);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.txt_back = (TextView) findViewById(R.id.txt_back);
        this.txt_category = (TextView) findViewById(R.id.txt_category);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.recycler_movie = (LiveVerticalGridView) findViewById(R.id.recycler_movie);
        this.sort_spinner = (CustomSpinner) findViewById(R.id.sort_spinner);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_movie.setNumColumns(6);
            this.recycler_movie.setLoop(false);
            this.recycler_movie.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_movie.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.MovieSecondActivity.4
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
            this.recycler_movie.setLayoutManager(new GridLayoutManager(this, 6));
            this.recycler_movie.setHasFixedSize(true);
        }
        this.txt_home.setText(this.wordModels.getHome());
        this.txt_live.setText(this.wordModels.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.txt_back.setText(this.wordModels.getBack());
        this.txt_back.setOnClickListener(this);
        this.txt_home.setOnClickListener(this);
        this.txt_movie.setOnClickListener(this);
        this.txt_live.setOnClickListener(this);
        this.txt_series.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExternalPlayerDialog$0(int i, DialogInterface dialogInterface, int i2) {
        Intent data;
        if (i != 1) {
            data = i != 2 ? null : new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad"));
        } else {
            data = new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=en_US"));
        }
        startActivity(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playRecentMovie(MovieModel movieModel) {
        int sharedPreferenceExternalPlayer = this.preferenceHelper.getSharedPreferenceExternalPlayer();
        String url = this.preferenceHelper.getSharedPreferenceISM3U() ? movieModel.getUrl() : GetSharedInfo.getMovieUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), movieModel.getStream_id(), movieModel.getExtension());
        if (sharedPreferenceExternalPlayer == 0) {
            Intent intent = GetSharedInfo.isTVDevice(this) ? new Intent(this, (Class<?>) MoviePlayerActivity.class) : new Intent(this, (Class<?>) MovieMobilePlayer.class);
            intent.putExtra("name", movieModel.getName());
            intent.putExtra("stream_id", movieModel.getStream_id());
            intent.putExtra("description", "");
            intent.putExtra("category_name", this.preferenceHelper.getSharedPreferenceISM3U() ? movieModel.getCategory_name() : getMovieCategoryName(movieModel.getCategory_id()));
            startActivity(intent);
            return;
        }
        if (sharedPreferenceExternalPlayer == 1) {
            if (Utils.getVlcPackageInfo(this) != null) {
                externalvlcplayer(url, movieModel.getName());
                return;
            } else {
                showExternalPlayerDialog(1);
                return;
            }
        }
        if (sharedPreferenceExternalPlayer != 2) {
            return;
        }
        Utils.MXPackageInfo mXPackageInfo = Utils.getMXPackageInfo(this);
        if (mXPackageInfo != null) {
            externalMXplayer(mXPackageInfo.packageName, mXPackageInfo.activityName, url);
        } else {
            showExternalPlayerDialog(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchModels(String str) {
        RealmResults<MovieModel> movieModelsByCategory = RealmController.with().getMovieModelsByCategory(this.categoryModels.get(this.category_pos), str, this.preferenceHelper.getSharedPreferenceISM3U(), this.sort_pos);
        this.movieModels = movieModelsByCategory;
        this.vodAdapter.updateData(movieModelsByCategory);
        this.recycler_movie.setSelectedPosition(0);
    }

    private void setFocusTopView(boolean z) {
        this.txt_home.setFocusable(z);
        this.txt_live.setFocusable(z);
        this.txt_movie.setFocusable(z);
        this.txt_series.setFocusable(z);
        this.et_search.setFocusable(z);
        this.txt_back.setFocusable(z);
        this.sort_spinner.setFocusable(z);
    }

    private void showExternalPlayerDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.wordModels.getInstall_external_player());
        builder.setMessage(this.wordModels.getWant_external_player()).setCancelable(false).setPositiveButton(this.wordModels.getOk(), new MovieActivity$$ExternalSyntheticLambda0(this, i, 2)).setNegativeButton(this.wordModels.getCancel(), MovieActivity$$ExternalSyntheticLambda1.INSTANCE$2);
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMovieLockDlgFragment(MovieModel movieModel, int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_lock");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LockDlgFragment lockDlgFragmentNewInstance = LockDlgFragment.newInstance(this.preferenceHelper.getSharedPreferenceParentPassword());
        this.lockDlgFragment = lockDlgFragmentNewInstance;
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener(i, movieModel) { // from class: com.nettv.livestore.activities.MovieSecondActivity.5
            public final /* synthetic */ MovieModel val$movieModel;

            {
                this.val$movieModel = movieModel;
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                Objects.requireNonNull(MovieSecondActivity.this);
                Intent intent = new Intent(MovieSecondActivity.this, (Class<?>) MovieInfoActivity.class);
                intent.putExtra("name", this.val$movieModel.getName());
                intent.putExtra("stream_id", this.val$movieModel.getStream_id());
                if (MovieSecondActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                    intent.putExtra("category_name", this.val$movieModel.getCategory_name());
                } else {
                    intent.putExtra("category_name", MovieSecondActivity.this.getMovieCategoryName(this.val$movieModel.getCategory_id()));
                }
                MovieSecondActivity.this.startActivity(intent);
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                MovieSecondActivity movieSecondActivity = MovieSecondActivity.this;
                Toast.makeText(movieSecondActivity, movieSecondActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                MovieSecondActivity movieSecondActivity = MovieSecondActivity.this;
                Toast.makeText(movieSecondActivity, movieSecondActivity.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(supportFragmentManager, "fragment_lock");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int i;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                finish();
            } else if (keyCode != 89) {
                if (keyCode != 90) {
                    switch (keyCode) {
                        case 19:
                            if (this.recycler_movie.hasFocus() && this.pre_movie_pos < 6) {
                                setFocusTopView(true);
                                this.sort_spinner.requestFocus();
                                return true;
                            }
                            break;
                        case 20:
                            if (this.sort_spinner.hasFocus()) {
                                setFocusTopView(false);
                                this.recycler_movie.requestFocus();
                                return true;
                            }
                            break;
                        case 21:
                            if (this.txt_back.hasFocus() || this.sort_spinner.hasFocus()) {
                                return true;
                            }
                            break;
                        case 22:
                            if (this.sort_spinner.hasFocus() || this.et_search.hasFocus()) {
                                return true;
                            }
                            break;
                    }
                } else if (this.recycler_movie.hasFocus() && this.pre_movie_pos < this.movieModels.size() - 13) {
                    int i2 = this.pre_movie_pos + 12;
                    this.pre_movie_pos = i2;
                    this.recycler_movie.setSelectedPosition(i2);
                }
            } else if (this.recycler_movie.hasFocus() && (i = this.pre_movie_pos) > 12) {
                int i3 = i - 12;
                this.pre_movie_pos = i3;
                this.recycler_movie.setSelectedPosition(i3);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_back /* 2131428284 */:
                finish();
                break;
            case R.id.txt_home /* 2131428313 */:
                Intent intent = new Intent();
                intent.putExtra("is_changed", "home");
                setResult(-1, intent);
                finish();
                break;
            case R.id.txt_live /* 2131428316 */:
                goToLiveActivity();
                break;
            case R.id.txt_movie /* 2131428322 */:
                finish();
                break;
            case R.id.txt_series /* 2131428343 */:
                goToSeriesActivity();
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_second);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.sortLists = GetSharedInfo.getVodSortLists(this.wordModels);
        Constants.getVodGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleVodCategories(), this);
        this.categoryModels = LTVApp.vod_categories_filter;
        this.category_pos = getIntent().getIntExtra("category_pos", 0);
        this.sort_pos = this.preferenceHelper.getSharedPreferenceVodOrder();
        this.movieModels = RealmController.with().getMovieModelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.sort_pos);
        this.txt_category.setText(this.categoryModels.get(this.category_pos).getName() + "(" + this.movieModels.size() + ")");
        VodRecyclerAdapter vodRecyclerAdapter = new VodRecyclerAdapter(this, this.movieModels, true);
        this.vodAdapter = vodRecyclerAdapter;
        vodRecyclerAdapter.setItemClickListener(new AnonymousClass1());
        this.recycler_movie.setAdapter(this.vodAdapter);
        this.sort_spinner.setAdapter((SpinnerAdapter) new SortSpinnerAdapter(this, this.sortLists));
        this.sort_spinner.setSelection(this.sort_pos);
        this.sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nettv.livestore.activities.MovieSecondActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                MovieSecondActivity movieSecondActivity = MovieSecondActivity.this;
                if (movieSecondActivity.sort_pos != i) {
                    movieSecondActivity.sort_pos = i;
                    movieSecondActivity.preferenceHelper.setSharedPreferenceVodOrder(i);
                    VodRecyclerAdapter vodRecyclerAdapter2 = MovieSecondActivity.this.vodAdapter;
                    RealmController realmControllerWith = RealmController.with();
                    MovieSecondActivity movieSecondActivity2 = MovieSecondActivity.this;
                    vodRecyclerAdapter2.updateData(realmControllerWith.getMovieModelsByCategory(movieSecondActivity2.categoryModels.get(movieSecondActivity2.category_pos), "", MovieSecondActivity.this.preferenceHelper.getSharedPreferenceISM3U(), MovieSecondActivity.this.sort_pos));
                    MovieSecondActivity.this.recycler_movie.setSelectedPosition(0);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.activities.MovieSecondActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    return;
                }
                MovieSecondActivity.this.searchModels(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        if (GetSharedInfo.isTVDevice(this)) {
            setFocusTopView(false);
        }
    }
}
