package com.nettv.livestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.SeriesRecyclerAdapter;
import com.nettv.livestore.adapter.SortSpinnerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.CustomSpinner;
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class SeriesSecondActivity extends AppCompatActivity implements View.OnClickListener {
    public List<CategoryModel> categoryModels;
    public EditText et_search;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_series;
    public SeriesRecyclerAdapter seriesAdapter;
    public RealmResults<SeriesModel> seriesModels;
    public CustomSpinner sort_spinner;
    public TextView txt_back;
    public TextView txt_category;
    public TextView txt_home;
    public TextView txt_live;
    public TextView txt_movie;
    public TextView txt_series;
    public WordModels wordModels;
    public List<String> sortLists = new ArrayList();
    public int category_pos = 0;
    public int sort_pos = 0;
    public int pre_series_pos = 0;

    /* JADX INFO: renamed from: com.nettv.livestore.activities.SeriesSecondActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements SeriesRecyclerAdapter.ItemClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFavClick$0(int i) {
            SeriesSecondActivity.this.seriesAdapter.notifyItemChanged(i);
            SeriesSecondActivity.this.preferenceHelper.setSharedPreferenceSeriesFavNames(RealmController.with().getFavSeriesNames());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnFavClick$1(int i) {
            SeriesSecondActivity.this.seriesAdapter.notifyItemChanged(i);
            SeriesSecondActivity.this.preferenceHelper.setSharedPreferenceSeriesFavNames(RealmController.with().getFavSeriesNames());
        }

        @Override // com.nettv.livestore.adapter.SeriesRecyclerAdapter.ItemClickListener
        public void onFavClick(SeriesModel seriesModel, int i) {
            RealmController.with().addToFavSeries(seriesModel.getName(), true, new SeriesSecondActivity$1$$ExternalSyntheticLambda0(this, i, 0));
        }

        @Override // com.nettv.livestore.adapter.SeriesRecyclerAdapter.ItemClickListener
        public void onFocusPosition(int i) {
            SeriesSecondActivity.this.pre_series_pos = i;
        }

        @Override // com.nettv.livestore.adapter.SeriesRecyclerAdapter.ItemClickListener
        public void onItemClick(SeriesModel seriesModel, int i) {
            Objects.requireNonNull(SeriesSecondActivity.this);
            SeriesSecondActivity seriesSecondActivity = SeriesSecondActivity.this;
            if (seriesSecondActivity.categoryModels.get(seriesSecondActivity.category_pos).getId().equalsIgnoreCase(Constants.resume_id)) {
                Intent intent = new Intent(SeriesSecondActivity.this, (Class<?>) SeasonActivity.class);
                intent.putExtra("series_name", seriesModel.getName());
                intent.putExtra("series_id", seriesModel.getSeries_id());
                SeriesSecondActivity.this.startActivity(intent);
                return;
            }
            Intent intent2 = new Intent(SeriesSecondActivity.this, (Class<?>) SeriesInfoActivity.class);
            intent2.putExtra("name", seriesModel.getName());
            intent2.putExtra("series_id", seriesModel.getSeries_id());
            if (SeriesSecondActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                intent2.putExtra("category_name", seriesModel.getCategory_name());
            } else {
                intent2.putExtra("category_name", SeriesSecondActivity.this.getSeriesCategoryName(seriesModel.getCategory_id()));
            }
            SeriesSecondActivity.this.startActivity(intent2);
        }

        @Override // com.nettv.livestore.adapter.SeriesRecyclerAdapter.ItemClickListener
        public void onUnFavClick(SeriesModel seriesModel, int i) {
            RealmController.with().addToFavSeries(seriesModel.getName(), false, new SeriesSecondActivity$1$$ExternalSyntheticLambda0(this, i, 1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSeriesCategoryName(String str) {
        for (CategoryModel categoryModel : LTVApp.series_categories_filter) {
            if (str.equalsIgnoreCase(categoryModel.getId())) {
                return categoryModel.getName();
            }
        }
        return "UnNamed Category";
    }

    private void initView() {
        this.txt_home = (TextView) findViewById(R.id.txt_home);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.txt_category = (TextView) findViewById(R.id.txt_category);
        this.txt_back = (TextView) findViewById(R.id.txt_back);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.recycler_series = (LiveVerticalGridView) findViewById(R.id.recycler_series);
        this.sort_spinner = (CustomSpinner) findViewById(R.id.sort_spinner);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_series.setNumColumns(6);
            this.recycler_series.setLoop(false);
            this.recycler_series.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_series.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SeriesSecondActivity.4
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
            this.recycler_series.setLayoutManager(new GridLayoutManager(this, 6));
            this.recycler_series.setHasFixedSize(true);
        }
        this.txt_home.setText(this.wordModels.getHome());
        this.txt_back.setText(this.wordModels.getBack());
        this.txt_live.setText(this.wordModels.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.txt_home.setOnClickListener(this);
        this.txt_live.setOnClickListener(this);
        this.txt_movie.setOnClickListener(this);
        this.txt_series.setOnClickListener(this);
        this.txt_back.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchModels(String str) {
        RealmResults<SeriesModel> seriesModelsByCategory = RealmController.with().getSeriesModelsByCategory(this.categoryModels.get(this.category_pos), str, this.preferenceHelper.getSharedPreferenceISM3U(), this.sort_pos);
        this.seriesModels = seriesModelsByCategory;
        this.seriesAdapter.updateData(seriesModelsByCategory);
        this.recycler_series.setSelectedPosition(0);
    }

    private void setFocusTopView(boolean z) {
        this.txt_home.setFocusable(z);
        this.txt_live.setFocusable(z);
        this.txt_movie.setFocusable(z);
        this.txt_series.setFocusable(z);
        this.txt_back.setFocusable(z);
        this.et_search.setFocusable(z);
        this.sort_spinner.setFocusable(z);
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
                            if (this.recycler_series.hasFocus() && this.pre_series_pos < 6) {
                                setFocusTopView(true);
                                this.sort_spinner.requestFocus();
                                return true;
                            }
                            break;
                        case 20:
                            if (this.sort_spinner.hasFocus()) {
                                setFocusTopView(false);
                                this.recycler_series.requestFocus();
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
                } else if (this.recycler_series.hasFocus() && this.pre_series_pos < this.seriesModels.size() - 13) {
                    int i2 = this.pre_series_pos + 12;
                    this.pre_series_pos = i2;
                    this.recycler_series.setSelectedPosition(i2);
                }
            } else if (this.recycler_series.hasFocus() && (i = this.pre_series_pos) > 12) {
                int i3 = i - 12;
                this.pre_series_pos = i3;
                this.recycler_series.setSelectedPosition(i3);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_back /* 2131428284 */:
            case R.id.txt_series /* 2131428343 */:
                finish();
                break;
            case R.id.txt_home /* 2131428313 */:
                Intent intent = new Intent();
                intent.putExtra("is_changed", "home");
                setResult(-1, intent);
                finish();
                break;
            case R.id.txt_live /* 2131428316 */:
                Intent intent2 = new Intent();
                intent2.putExtra("is_changed", "live");
                setResult(-1, intent2);
                finish();
                break;
            case R.id.txt_movie /* 2131428322 */:
                Intent intent3 = new Intent();
                intent3.putExtra("is_changed", "movie");
                setResult(-1, intent3);
                finish();
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_series_second);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.sortLists = GetSharedInfo.getVodSortLists(this.wordModels);
        Constants.getSeriesGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleSeriesCategories(), this);
        this.categoryModels = LTVApp.series_categories_filter;
        this.category_pos = getIntent().getIntExtra("category_pos", 0);
        this.sort_pos = this.preferenceHelper.getSharedPreferenceSeriesOrder();
        this.seriesModels = RealmController.with().getSeriesModelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.sort_pos);
        this.txt_category.setText(this.categoryModels.get(this.category_pos).getName() + "(" + this.seriesModels.size() + ")");
        SeriesRecyclerAdapter seriesRecyclerAdapter = new SeriesRecyclerAdapter(this, this.seriesModels, true);
        this.seriesAdapter = seriesRecyclerAdapter;
        seriesRecyclerAdapter.setItemClickListener(new AnonymousClass1());
        this.recycler_series.setAdapter(this.seriesAdapter);
        this.sort_spinner.setAdapter((SpinnerAdapter) new SortSpinnerAdapter(this, this.sortLists));
        this.sort_spinner.setSelection(this.sort_pos);
        this.sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nettv.livestore.activities.SeriesSecondActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                SeriesSecondActivity seriesSecondActivity = SeriesSecondActivity.this;
                if (seriesSecondActivity.sort_pos != i) {
                    seriesSecondActivity.sort_pos = i;
                    seriesSecondActivity.preferenceHelper.setSharedPreferenceSeriesOrder(i);
                    SeriesSecondActivity seriesSecondActivity2 = SeriesSecondActivity.this;
                    RealmController realmControllerWith = RealmController.with();
                    SeriesSecondActivity seriesSecondActivity3 = SeriesSecondActivity.this;
                    seriesSecondActivity2.seriesModels = realmControllerWith.getSeriesModelsByCategory(seriesSecondActivity3.categoryModels.get(seriesSecondActivity3.category_pos), "", SeriesSecondActivity.this.preferenceHelper.getSharedPreferenceISM3U(), SeriesSecondActivity.this.sort_pos);
                    SeriesSecondActivity seriesSecondActivity4 = SeriesSecondActivity.this;
                    seriesSecondActivity4.seriesAdapter.updateData(seriesSecondActivity4.seriesModels);
                    SeriesSecondActivity.this.recycler_series.setSelectedPosition(0);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.activities.SeriesSecondActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    return;
                }
                SeriesSecondActivity.this.searchModels(editable.toString());
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
