package com.nettv.livestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.mobile.LiveMobileActivity;
import com.nettv.livestore.adapter.RecyclerLiveHomeAdapter;
import com.nettv.livestore.adapter.RecyclerSeriesHomeAdapter;
import com.nettv.livestore.adapter.RecyclerVodHomeAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.LockDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveHorizontalGridView;
import io.realm.Realm$$ExternalSyntheticLambda0;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class SearchActivity extends AppCompatActivity {
    public ImageButton btn_back;
    public RealmResults<EPGChannel> epgChannels;
    public EditText et_search;
    public RecyclerLiveHomeAdapter liveAdapter;
    public LockDlgFragment lockDlgFragment;
    public RealmResults<MovieModel> movieModels;
    public PreferenceHelper preferenceHelper;
    public LiveHorizontalGridView recyclerChannels;
    public LiveHorizontalGridView recyclerMovies;
    public LiveHorizontalGridView recyclerSeries;
    public RealmResults<SeriesModel> seriesModels;
    public TextView str_live;
    public TextView str_movies;
    public TextView str_series;
    public RecyclerVodHomeAdapter vodAdapter;
    public WordModels wordModels = new WordModels();
    public boolean is_live = false;
    public String vod_search_key = "";

    private boolean checkAdultMovie(String str, String str2) {
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            return str.contains("xxx") || str.contains("porn") || str.contains("adult");
        }
        return Constants.xxx_vod_categories.contains(str2);
    }

    private String getMovieCategoryName(String str) {
        for (CategoryModel categoryModel : LTVApp.series_categories_filter) {
            if (categoryModel.getId().equalsIgnoreCase(str)) {
                return categoryModel.getName();
            }
        }
        return "UnNamed Category";
    }

    private String getSeriesCategoryName(String str) {
        for (CategoryModel categoryModel : LTVApp.series_categories_filter) {
            if (categoryModel.getId().equalsIgnoreCase(str)) {
                return categoryModel.getName();
            }
        }
        return "UnNamed Category";
    }

    private void initView() {
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.str_live = (TextView) findViewById(R.id.str_live);
        this.str_movies = (TextView) findViewById(R.id.str_movies);
        this.str_series = (TextView) findViewById(R.id.str_series);
        this.btn_back.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 0));
        this.recyclerChannels = (LiveHorizontalGridView) findViewById(R.id.recycler_channels);
        this.recyclerMovies = (LiveHorizontalGridView) findViewById(R.id.recycler_vod);
        this.recyclerSeries = (LiveHorizontalGridView) findViewById(R.id.recycler_series);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recyclerChannels.setLoop(false);
            this.recyclerChannels.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recyclerChannels.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SearchActivity.3
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
            this.recyclerChannels.setLayoutManager(new LinearLayoutManager(this, 0, false));
            this.recyclerChannels.setHasFixedSize(true);
        }
        if (GetSharedInfo.isTVDevice(this)) {
            this.recyclerMovies.setLoop(false);
            this.recyclerMovies.setPreserveFocusAfterLayout(true);
            final View[] viewArr2 = {null};
            this.recyclerMovies.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SearchActivity.4
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
            this.recyclerMovies.setLayoutManager(new LinearLayoutManager(this, 0, false));
            this.recyclerMovies.setHasFixedSize(true);
        }
        if (!GetSharedInfo.isTVDevice(this)) {
            this.recyclerSeries.setLayoutManager(new LinearLayoutManager(this, 0, false));
            this.recyclerSeries.setHasFixedSize(true);
        } else {
            this.recyclerSeries.setLoop(false);
            this.recyclerSeries.setPreserveFocusAfterLayout(true);
            final View[] viewArr3 = {null};
            this.recyclerSeries.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SearchActivity.5
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                    View[] viewArr4 = viewArr3;
                    if (viewArr4[0] != null) {
                        viewArr4[0].setSelected(false);
                        View[] viewArr5 = viewArr3;
                        viewArr5[0] = viewHolder.itemView;
                        viewArr5[0].setSelected(true);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        Intent intent = new Intent();
        intent.putExtra("is_changed", "");
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$searchModels$0() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$searchModels$1(EPGChannel ePGChannel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        RealmController.with().addToRecentChannels(ePGChannel.getName(), true, Realm$$ExternalSyntheticLambda0.INSTANCE$17);
        saveCategoryAndChannelPosition(ePGChannel);
        if (this.is_live) {
            Intent intent = new Intent();
            intent.putExtra("is_changed", "from_search");
            setResult(-1, intent);
            finish();
            return null;
        }
        if (GetSharedInfo.isTVDevice(this)) {
            Intent intent2 = new Intent(this, (Class<?>) LiveActivity.class);
            intent2.putExtra("is_full", true);
            startActivity(intent2);
            return null;
        }
        Intent intent3 = new Intent(this, (Class<?>) LiveMobileActivity.class);
        intent3.putExtra("is_full", true);
        startActivity(intent3);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$searchModels$2(MovieModel movieModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        if (checkAdultMovie(movieModel.getCategory_name(), movieModel.getCategory_id())) {
            showLockDlgFragment(movieModel.getName(), movieModel.getStream_id());
            return null;
        }
        Intent intent = new Intent(this, (Class<?>) MovieInfoActivity.class);
        intent.putExtra("name", movieModel.getName());
        intent.putExtra("stream_id", movieModel.getStream_id());
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            intent.putExtra("category_name", movieModel.getCategory_name());
        } else {
            intent.putExtra("category_name", getMovieCategoryName(movieModel.getCategory_id()));
        }
        startActivity(intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$searchModels$3(SeriesModel seriesModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        Intent intent = new Intent(this, (Class<?>) SeriesInfoActivity.class);
        intent.putExtra("name", seriesModel.getName());
        intent.putExtra("series_id", seriesModel.getSeries_id());
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            intent.putExtra("category_name", seriesModel.getCategory_name());
        } else {
            intent.putExtra("category_name", getSeriesCategoryName(seriesModel.getCategory_id()));
        }
        startActivity(intent);
        return null;
    }

    private void saveCategoryAndChannelPosition(EPGChannel ePGChannel) {
        int i;
        Constants.getLiveGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleLiveCategories(), this);
        List<CategoryModel> list = LTVApp.live_categories_filter;
        int i2 = 0;
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            String category_name = ePGChannel.getCategory_name();
            i = 0;
            while (true) {
                if (i >= list.size()) {
                    i = 0;
                    break;
                } else if (list.get(i).getName().equalsIgnoreCase(category_name)) {
                    break;
                } else {
                    i++;
                }
            }
        } else {
            String category_id = ePGChannel.getCategory_id();
            i = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (list.get(i3).getId().equalsIgnoreCase(category_id)) {
                    i = i3;
                }
            }
        }
        RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(list.get(i), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
        for (int i4 = 0; i4 < liveChannelsByCategory.size(); i4++) {
            if (ePGChannel.getName().equalsIgnoreCase(((EPGChannel) liveChannelsByCategory.get(i4)).getName())) {
                i2 = i4;
                break;
            }
        }
        this.preferenceHelper.setSharedPreferenceCategoryPos(i);
        this.preferenceHelper.setSharedPreferenceChannelPos(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchModels(String str) {
        this.epgChannels = RealmController.with().getLiveChannelsByKey(str, this.preferenceHelper.getSharedPreferenceISM3U());
        this.movieModels = RealmController.with().getMoviesByKey(str, this.preferenceHelper.getSharedPreferenceISM3U());
        this.seriesModels = RealmController.with().getSeriesByKey(str);
        RealmResults<EPGChannel> realmResults = this.epgChannels;
        final int i = 0;
        if (realmResults == null || realmResults.size() <= 0) {
            this.str_live.setVisibility(8);
            this.recyclerChannels.setVisibility(8);
        } else {
            this.str_live.setVisibility(0);
            this.recyclerChannels.setVisibility(0);
            RecyclerLiveHomeAdapter recyclerLiveHomeAdapter = new RecyclerLiveHomeAdapter(this, new ArrayList(), new Function3(this) { // from class: com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda1
                public final /* synthetic */ SearchActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    switch (i) {
                        case 0:
                            return this.f$0.lambda$searchModels$1((EPGChannel) obj, (Integer) obj2, (Boolean) obj3);
                        case 1:
                            return this.f$0.lambda$searchModels$2((MovieModel) obj, (Integer) obj2, (Boolean) obj3);
                        default:
                            return this.f$0.lambda$searchModels$3((SeriesModel) obj, (Integer) obj2, (Boolean) obj3);
                    }
                }
            });
            this.liveAdapter = recyclerLiveHomeAdapter;
            recyclerLiveHomeAdapter.setEpgChannels(this.epgChannels);
            this.recyclerChannels.setAdapter(this.liveAdapter);
        }
        RealmResults<MovieModel> realmResults2 = this.movieModels;
        if (realmResults2 == null || realmResults2.size() <= 0) {
            this.str_movies.setVisibility(8);
            this.recyclerMovies.setVisibility(8);
        } else {
            this.str_movies.setVisibility(0);
            this.recyclerMovies.setVisibility(0);
            final int i2 = 1;
            RecyclerVodHomeAdapter recyclerVodHomeAdapter = new RecyclerVodHomeAdapter(this, new ArrayList(), new Function3(this) { // from class: com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda1
                public final /* synthetic */ SearchActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    switch (i2) {
                        case 0:
                            return this.f$0.lambda$searchModels$1((EPGChannel) obj, (Integer) obj2, (Boolean) obj3);
                        case 1:
                            return this.f$0.lambda$searchModels$2((MovieModel) obj, (Integer) obj2, (Boolean) obj3);
                        default:
                            return this.f$0.lambda$searchModels$3((SeriesModel) obj, (Integer) obj2, (Boolean) obj3);
                    }
                }
            });
            this.vodAdapter = recyclerVodHomeAdapter;
            recyclerVodHomeAdapter.setModels(this.movieModels);
            this.recyclerMovies.setAdapter(this.vodAdapter);
        }
        RealmResults<SeriesModel> realmResults3 = this.seriesModels;
        if (realmResults3 == null || realmResults3.size() <= 0) {
            this.str_series.setVisibility(8);
            this.recyclerSeries.setVisibility(8);
            return;
        }
        this.str_series.setVisibility(0);
        this.recyclerSeries.setVisibility(0);
        final int i3 = 2;
        this.recyclerSeries.setAdapter(new RecyclerSeriesHomeAdapter(this, this.seriesModels, new Function3(this) { // from class: com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ SearchActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i3) {
                    case 0:
                        return this.f$0.lambda$searchModels$1((EPGChannel) obj, (Integer) obj2, (Boolean) obj3);
                    case 1:
                        return this.f$0.lambda$searchModels$2((MovieModel) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$searchModels$3((SeriesModel) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        }));
    }

    private void showLockDlgFragment(final String str, final String str2) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_lock");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LockDlgFragment lockDlgFragmentNewInstance = LockDlgFragment.newInstance(this.preferenceHelper.getSharedPreferenceParentPassword());
        this.lockDlgFragment = lockDlgFragmentNewInstance;
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.activities.SearchActivity.2
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                Intent intent = new Intent(SearchActivity.this, (Class<?>) MovieInfoActivity.class);
                intent.putExtra("name", str);
                intent.putExtra("stream_id", str2);
                SearchActivity.this.startActivity(intent);
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                SearchActivity searchActivity = SearchActivity.this;
                Toast.makeText(searchActivity, searchActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                SearchActivity searchActivity = SearchActivity.this;
                Toast.makeText(searchActivity, searchActivity.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(supportFragmentManager, "fragment_lock");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                Intent intent = new Intent();
                intent.putExtra("is_changed", "");
                setResult(-1, intent);
                finish();
            } else if (keyCode != 19) {
                if (keyCode != 21) {
                    if (keyCode == 22 && this.btn_back.hasFocus()) {
                        this.btn_back.setFocusable(false);
                        this.et_search.requestFocus();
                        return true;
                    }
                } else if (this.et_search.hasFocus()) {
                    this.btn_back.setFocusable(true);
                    this.btn_back.requestFocus();
                    return true;
                }
            } else if (this.recyclerChannels.hasFocus()) {
                this.et_search.requestFocus();
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.is_live = getIntent().getBooleanExtra("is_live", false);
        this.vod_search_key = getIntent().getStringExtra("search_key");
        this.str_live.setText(this.wordModels.getLive_tv());
        this.str_movies.setText(this.wordModels.getMovies());
        this.str_series.setText(this.wordModels.getSeries());
        this.et_search.setHint(this.wordModels.getSearch_by_title());
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.activities.SearchActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    return;
                }
                SearchActivity.this.searchModels(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.btn_back.setFocusable(false);
        String str = this.vod_search_key;
        if (str == null || str.isEmpty()) {
            this.et_search.requestFocus();
            return;
        }
        this.et_search.setText(this.vod_search_key);
        this.recyclerMovies.requestFocus();
        this.et_search.setFocusable(false);
    }
}
