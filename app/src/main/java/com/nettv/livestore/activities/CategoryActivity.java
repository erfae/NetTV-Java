package com.nettv.livestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.mobile.LiveChannelMobileActivity;
import com.nettv.livestore.adapter.RecyclerLiveCategoryAdapter;
import com.nettv.livestore.adapter.RecyclerVodCategoryAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.HomeType;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.LockDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveVerticalGridView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    public List<CategoryModel> categoryModels;
    public EditText et_search;
    public LockDlgFragment lockDlgFragment;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_category;
    public TextView txt_home;
    public TextView txt_live;
    public TextView txt_movie;
    public TextView txt_series;
    public WordModels wordModels = new WordModels();
    public int category_position = 0;
    public int pre_category_pos = 0;
    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new MovieActivity$$ExternalSyntheticLambda2(this, 7));

    /* JADX INFO: renamed from: com.nettv.livestore.activities.CategoryActivity$7, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass7 {
        public static final /* synthetic */ int[] $SwitchMap$com$vilhao$player$apps$HomeType;

        static {
            int[] iArr = new int[HomeType.values().length];
            $SwitchMap$com$vilhao$player$apps$HomeType = iArr;
            try {
                iArr[HomeType.movies.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$vilhao$player$apps$HomeType[HomeType.series.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$vilhao$player$apps$HomeType[HomeType.live.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAdultCategory(String str) {
        return str.contains("xxx") || str.contains("porn") || str.contains("adult");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCategoryPosition(CategoryModel categoryModel) {
        if (this.categoryModels.size() > 0) {
            for (int i = 0; i < this.categoryModels.size(); i++) {
                if (categoryModel.getName().equalsIgnoreCase(this.categoryModels.get(i).getName())) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void initView() {
        this.txt_home = (TextView) findViewById(R.id.txt_home);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.txt_home.setText(this.wordModels.getHome());
        this.txt_live.setText(this.wordModels.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.txt_home.setOnClickListener(this);
        this.txt_live.setOnClickListener(this);
        this.txt_movie.setOnClickListener(this);
        this.txt_series.setOnClickListener(this);
        this.recycler_category = (LiveVerticalGridView) findViewById(R.id.recycler_category);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_category.setNumColumns(3);
            this.recycler_category.setLoop(false);
            this.recycler_category.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_category.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.CategoryActivity.1
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
            this.recycler_category.setLayoutManager(new GridLayoutManager(this, 3));
            this.recycler_category.setHasFixedSize(true);
        }
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.activities.CategoryActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                CategoryActivity.this.searchCategoryModel(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ActivityResult activityResult) {
        Intent data;
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null) {
            return;
        }
        String lowerCase = data.getStringExtra("is_changed").toLowerCase();
        Objects.requireNonNull(lowerCase);
        switch (lowerCase) {
            case "series":
                LTVApp.homeType = HomeType.series;
                setTextColor();
                setCategoryAdapter(0);
                break;
            case "home":
                finish();
                break;
            case "live":
            case "add_group":
                LTVApp.homeType = HomeType.live;
                setTextColor();
                setCategoryAdapter(0);
                break;
            case "movie":
                LTVApp.homeType = HomeType.movies;
                setTextColor();
                setCategoryAdapter(0);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$setCategoryAdapter$0(CategoryModel categoryModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            this.pre_category_pos = num.intValue();
            return null;
        }
        if (checkAdultCategory(categoryModel.getName().toLowerCase())) {
            showLockDlgFragment(categoryModel);
            return null;
        }
        Intent intent = new Intent(this, (Class<?>) MovieSecondActivity.class);
        intent.putExtra("category_pos", num);
        this.someActivityResultLauncher.launch(intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$setCategoryAdapter$1(CategoryModel categoryModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            this.pre_category_pos = num.intValue();
            return null;
        }
        if (checkAdultCategory(categoryModel.getName().toLowerCase())) {
            showLockDlgFragment(categoryModel);
            return null;
        }
        Intent intent = new Intent(this, (Class<?>) SeriesSecondActivity.class);
        intent.putExtra("category_pos", num);
        this.someActivityResultLauncher.launch(intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$setCategoryAdapter$2(CategoryModel categoryModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            this.pre_category_pos = num.intValue();
            return null;
        }
        if (num.intValue() == 0) {
            this.someActivityResultLauncher.launch(new Intent(this, (Class<?>) AddGroupActivity.class));
            return null;
        }
        if (checkAdultCategory(categoryModel.getName().toLowerCase()) || categoryModel.getId().equalsIgnoreCase(Constants.lock_id)) {
            if (!categoryModel.getId().equalsIgnoreCase(Constants.lock_id)) {
                showLockDlgFragment(categoryModel);
                return null;
            }
            if (this.preferenceHelper.getSharedPreferenceLiveLockChannels().size() > 0) {
                showLockDlgFragment(categoryModel);
                return null;
            }
            Toast.makeText(this, this.wordModels.getStr_no_lock_channels(), 0).show();
            return null;
        }
        this.preferenceHelper.setSharedPreferenceCategoryPos(num.intValue());
        if (GetSharedInfo.isTVDevice(this)) {
            Intent intent = new Intent(this, (Class<?>) LiveChannelActivity.class);
            intent.putExtra("is_full", false);
            this.someActivityResultLauncher.launch(intent);
            return null;
        }
        Intent intent2 = new Intent(this, (Class<?>) LiveChannelMobileActivity.class);
        intent2.putExtra("is_full", false);
        this.someActivityResultLauncher.launch(intent2);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchCategoryModel(String str) {
        if (str.isEmpty()) {
            setCategoryAdapter(this.category_position);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (CategoryModel categoryModel : this.categoryModels) {
            if (categoryModel.getName().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(categoryModel);
            }
        }
        int i = AnonymousClass7.$SwitchMap$com$vilhao$player$apps$HomeType[LTVApp.homeType.ordinal()];
        if (i == 1) {
            this.recycler_category.setAdapter(new RecyclerVodCategoryAdapter(this, arrayList, 0, true, this.preferenceHelper.getSharedPreferenceISM3U(), true, new Function3<CategoryModel, Integer, Boolean, Unit>() { // from class: com.nettv.livestore.activities.CategoryActivity.3
                @Override // kotlin.jvm.functions.Function3
                public Unit invoke(CategoryModel categoryModel2, Integer num, Boolean bool) {
                    if (!bool.booleanValue()) {
                        CategoryActivity.this.pre_category_pos = num.intValue();
                        return null;
                    }
                    CategoryActivity.this.category_position = num.intValue();
                    if (CategoryActivity.this.checkAdultCategory(categoryModel2.getName().toLowerCase())) {
                        CategoryActivity.this.showLockDlgFragment(categoryModel2);
                        return null;
                    }
                    Intent intent = new Intent(CategoryActivity.this, (Class<?>) MovieSecondActivity.class);
                    intent.putExtra("category_pos", CategoryActivity.this.getCategoryPosition(categoryModel2));
                    CategoryActivity.this.someActivityResultLauncher.launch(intent);
                    return null;
                }
            }));
        } else if (i != 2) {
            this.recycler_category.setAdapter(new RecyclerLiveCategoryAdapter(this, arrayList, this.preferenceHelper.getSharedPreferenceISM3U(), true, this.category_position, new Function3<CategoryModel, Integer, Boolean, Unit>() { // from class: com.nettv.livestore.activities.CategoryActivity.5
                @Override // kotlin.jvm.functions.Function3
                public Unit invoke(CategoryModel categoryModel2, Integer num, Boolean bool) {
                    if (!bool.booleanValue()) {
                        CategoryActivity.this.pre_category_pos = num.intValue();
                        return null;
                    }
                    CategoryActivity.this.category_position = num.intValue();
                    if (CategoryActivity.this.checkAdultCategory(categoryModel2.getName().toLowerCase())) {
                        CategoryActivity.this.showLockDlgFragment(categoryModel2);
                        return null;
                    }
                    CategoryActivity categoryActivity = CategoryActivity.this;
                    categoryActivity.preferenceHelper.setSharedPreferenceCategoryPos(categoryActivity.getCategoryPosition(categoryModel2));
                    if (GetSharedInfo.isTVDevice(CategoryActivity.this)) {
                        Intent intent = new Intent(CategoryActivity.this, (Class<?>) LiveChannelActivity.class);
                        intent.putExtra("is_full", false);
                        CategoryActivity.this.someActivityResultLauncher.launch(intent);
                        return null;
                    }
                    Intent intent2 = new Intent(CategoryActivity.this, (Class<?>) LiveChannelMobileActivity.class);
                    intent2.putExtra("is_full", false);
                    CategoryActivity.this.someActivityResultLauncher.launch(intent2);
                    return null;
                }
            }));
        } else {
            this.recycler_category.setAdapter(new RecyclerVodCategoryAdapter(this, arrayList, 0, true, this.preferenceHelper.getSharedPreferenceISM3U(), false, new Function3<CategoryModel, Integer, Boolean, Unit>() { // from class: com.nettv.livestore.activities.CategoryActivity.4
                @Override // kotlin.jvm.functions.Function3
                public Unit invoke(CategoryModel categoryModel2, Integer num, Boolean bool) {
                    if (!bool.booleanValue()) {
                        CategoryActivity.this.pre_category_pos = num.intValue();
                        return null;
                    }
                    CategoryActivity.this.category_position = num.intValue();
                    if (CategoryActivity.this.checkAdultCategory(categoryModel2.getName().toLowerCase())) {
                        CategoryActivity.this.showLockDlgFragment(categoryModel2);
                        return null;
                    }
                    Intent intent = new Intent(CategoryActivity.this, (Class<?>) SeriesSecondActivity.class);
                    intent.putExtra("category_pos", CategoryActivity.this.getCategoryPosition(categoryModel2));
                    CategoryActivity.this.someActivityResultLauncher.launch(intent);
                    return null;
                }
            }));
        }
    }

    private void setCategoryAdapter(int i) {
        int i2 = AnonymousClass7.$SwitchMap$com$vilhao$player$apps$HomeType[LTVApp.homeType.ordinal()];
        final int i3 = 1;
        if (i2 == 1) {
            Constants.getVodGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleVodCategories(), this);
            List<CategoryModel> list = LTVApp.vod_categories_filter;
            this.categoryModels = list;
            final int i4 = 0;
            this.recycler_category.setAdapter(new RecyclerVodCategoryAdapter(this, list, i, true, this.preferenceHelper.getSharedPreferenceISM3U(), true, new Function3(this) { // from class: com.nettv.livestore.activities.CategoryActivity$$ExternalSyntheticLambda0
                public final /* synthetic */ CategoryActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    switch (i4) {
                        case 0:
                            return this.f$0.lambda$setCategoryAdapter$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                        case 1:
                            return this.f$0.lambda$setCategoryAdapter$1((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                        default:
                            return this.f$0.lambda$setCategoryAdapter$2((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                    }
                }
            }));
            return;
        }
        final int i5 = 2;
        if (i2 == 2) {
            Constants.getSeriesGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleSeriesCategories(), this);
            List<CategoryModel> list2 = LTVApp.series_categories_filter;
            this.categoryModels = list2;
            this.recycler_category.setAdapter(new RecyclerVodCategoryAdapter(this, list2, i, true, this.preferenceHelper.getSharedPreferenceISM3U(), false, new Function3(this) { // from class: com.nettv.livestore.activities.CategoryActivity$$ExternalSyntheticLambda0
                public final /* synthetic */ CategoryActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    switch (i3) {
                        case 0:
                            return this.f$0.lambda$setCategoryAdapter$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                        case 1:
                            return this.f$0.lambda$setCategoryAdapter$1((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                        default:
                            return this.f$0.lambda$setCategoryAdapter$2((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                    }
                }
            }));
            return;
        }
        Constants.getLiveGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleLiveCategories(), this);
        List<CategoryModel> list3 = LTVApp.live_categories_filter;
        this.categoryModels = list3;
        this.recycler_category.setAdapter(new RecyclerLiveCategoryAdapter(this, list3, this.preferenceHelper.getSharedPreferenceISM3U(), true, i, new Function3(this) { // from class: com.nettv.livestore.activities.CategoryActivity$$ExternalSyntheticLambda0
            public final /* synthetic */ CategoryActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i5) {
                    case 0:
                        return this.f$0.lambda$setCategoryAdapter$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                    case 1:
                        return this.f$0.lambda$setCategoryAdapter$1((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$setCategoryAdapter$2((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        }));
        this.recycler_category.setSelectedPosition(i);
    }

    private void setFocusTopView(boolean z) {
        this.txt_home.setFocusable(z);
        this.txt_live.setFocusable(z);
        this.txt_movie.setFocusable(z);
        this.txt_series.setFocusable(z);
        this.et_search.setFocusable(z);
    }

    private void setTextColor() {
        int i = AnonymousClass7.$SwitchMap$com$vilhao$player$apps$HomeType[LTVApp.homeType.ordinal()];
        if (i == 1) {
            this.txt_live.setTextColor(getResources().getColor(R.color.white));
            this.txt_movie.setTextColor(getResources().getColor(R.color.yellow));
            this.txt_series.setTextColor(getResources().getColor(R.color.white));
        } else if (i == 2) {
            this.txt_live.setTextColor(getResources().getColor(R.color.white));
            this.txt_movie.setTextColor(getResources().getColor(R.color.white));
            this.txt_series.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            if (i != 3) {
                return;
            }
            this.txt_live.setTextColor(getResources().getColor(R.color.yellow));
            this.txt_movie.setTextColor(getResources().getColor(R.color.white));
            this.txt_series.setTextColor(getResources().getColor(R.color.white));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLockDlgFragment(final CategoryModel categoryModel) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_lock");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LockDlgFragment lockDlgFragmentNewInstance = LockDlgFragment.newInstance(this.preferenceHelper.getSharedPreferenceParentPassword());
        this.lockDlgFragment = lockDlgFragmentNewInstance;
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.activities.CategoryActivity.6
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                int i = AnonymousClass7.$SwitchMap$com$vilhao$player$apps$HomeType[LTVApp.homeType.ordinal()];
                if (i == 1) {
                    Intent intent = new Intent(CategoryActivity.this, (Class<?>) MovieSecondActivity.class);
                    intent.putExtra("category_pos", CategoryActivity.this.getCategoryPosition(categoryModel));
                    CategoryActivity.this.someActivityResultLauncher.launch(intent);
                    return;
                }
                if (i == 2) {
                    Intent intent2 = new Intent(CategoryActivity.this, (Class<?>) SeriesSecondActivity.class);
                    intent2.putExtra("category_pos", CategoryActivity.this.getCategoryPosition(categoryModel));
                    CategoryActivity.this.someActivityResultLauncher.launch(intent2);
                    return;
                }
                CategoryActivity categoryActivity = CategoryActivity.this;
                categoryActivity.preferenceHelper.setSharedPreferenceCategoryPos(categoryActivity.getCategoryPosition(categoryModel));
                if (GetSharedInfo.isTVDevice(CategoryActivity.this)) {
                    Intent intent3 = new Intent(CategoryActivity.this, (Class<?>) LiveChannelActivity.class);
                    intent3.putExtra("is_full", false);
                    CategoryActivity.this.someActivityResultLauncher.launch(intent3);
                } else {
                    Intent intent4 = new Intent(CategoryActivity.this, (Class<?>) LiveChannelMobileActivity.class);
                    intent4.putExtra("is_full", false);
                    CategoryActivity.this.someActivityResultLauncher.launch(intent4);
                }
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                CategoryActivity categoryActivity = CategoryActivity.this;
                Toast.makeText(categoryActivity, categoryActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                CategoryActivity categoryActivity = CategoryActivity.this;
                Toast.makeText(categoryActivity, categoryActivity.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(supportFragmentManager, "fragment_lock");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                finish();
            } else if (keyCode != 19) {
                if (keyCode == 20 && (this.txt_home.hasFocus() || this.txt_live.hasFocus() || this.txt_movie.hasFocus() || this.txt_series.hasFocus() || this.et_search.hasFocus())) {
                    setFocusTopView(false);
                    this.recycler_category.requestFocus();
                    return true;
                }
            } else {
                if (this.recycler_category.hasFocus() && this.pre_category_pos < 2) {
                    setFocusTopView(true);
                    this.txt_live.requestFocus();
                    return true;
                }
                if (this.recycler_category.hasFocus() && this.pre_category_pos == 2) {
                    setFocusTopView(true);
                    this.et_search.requestFocus();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_home /* 2131428313 */:
                finish();
                break;
            case R.id.txt_live /* 2131428316 */:
                LTVApp.homeType = HomeType.live;
                setTextColor();
                this.et_search.setText("");
                setCategoryAdapter(0);
                break;
            case R.id.txt_movie /* 2131428322 */:
                LTVApp.homeType = HomeType.movies;
                setTextColor();
                this.et_search.setText("");
                setCategoryAdapter(0);
                break;
            case R.id.txt_series /* 2131428343 */:
                LTVApp.homeType = HomeType.series;
                setTextColor();
                this.et_search.setText("");
                setCategoryAdapter(0);
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_category);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        if (GetSharedInfo.isTVDevice(this)) {
            setFocusTopView(false);
        }
        setTextColor();
        setCategoryAdapter(this.category_position);
    }
}
