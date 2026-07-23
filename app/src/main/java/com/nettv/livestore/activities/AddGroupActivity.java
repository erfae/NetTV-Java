package com.nettv.livestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.AddCategoryRecyclerAdapter;
import com.nettv.livestore.adapter.MyChannelRecyclerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.dlgfragment.AddChannelDlgFragment;
import com.nettv.livestore.dlgfragment.AddGroupDlgFragment;
import com.nettv.livestore.dlgfragment.RenameGroupDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.Realm$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class AddGroupActivity extends AppCompatActivity {
    public AddChannelDlgFragment addChannelDlgFragment;
    public AddGroupDlgFragment addGroupDlgFragment;
    public Button btn_add_channel;
    public ImageButton btn_back;
    public AddCategoryRecyclerAdapter categoryRecyclerAdapter;
    public MyChannelRecyclerAdapter channelRecyclerAdapter;
    public ImageView image_add;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_category;
    public LiveVerticalGridView recycler_channel;
    public RenameGroupDlgFragment renameGroupDlgFragment;
    public TextView str_add;
    public TextView str_groups;
    public TextView txt_edit;
    public TextView txt_group_name;
    public int category_pos = 0;
    public int channel_pos = 0;
    public String key = "";
    public List<String> myCategories = new ArrayList();
    public List<CategoryModel> liveCategories = new ArrayList();
    public WordModels wordModels = new WordModels();

    private void changeCategoryModel() {
        List<CategoryModel> sharedLiveCategoryModels = this.preferenceHelper.getSharedLiveCategoryModels();
        ArrayList arrayList = new ArrayList(sharedLiveCategoryModels);
        for (int i = 0; i < sharedLiveCategoryModels.size(); i++) {
            if (this.myCategories.get(this.category_pos).equalsIgnoreCase(sharedLiveCategoryModels.get(i).getName())) {
                arrayList.remove(sharedLiveCategoryModels.get(i));
                break;
            }
        }
        List<String> sharedPreferenceMyGroupCategory = this.preferenceHelper.getSharedPreferenceMyGroupCategory();
        ArrayList arrayList2 = new ArrayList(sharedPreferenceMyGroupCategory);
        for (int i2 = 0; i2 < sharedLiveCategoryModels.size(); i2++) {
            if (this.myCategories.get(this.category_pos).equalsIgnoreCase(sharedPreferenceMyGroupCategory.get(i2))) {
                arrayList2.remove(sharedPreferenceMyGroupCategory.get(i2));
                break;
            }
        }
        this.preferenceHelper.setSharedPreferenceMyGroupCategory(arrayList2);
        this.preferenceHelper.setSharedPreferenceLiveCategory(arrayList);
        Log.e("changeModelSize", String.valueOf(arrayList.size()));
        Log.e("myModelSize", String.valueOf(arrayList2.size()));
        Constants.getLiveGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleLiveCategories(), this);
    }

    private void changeRenameCategoryModel(String str) {
        this.preferenceHelper.setSharedPreferenceMyGroupChannels(str, this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.myCategories.get(this.category_pos)));
        this.preferenceHelper.removeKeys(this.myCategories.get(this.category_pos));
        List<CategoryModel> sharedLiveCategoryModels = this.preferenceHelper.getSharedLiveCategoryModels();
        ArrayList arrayList = new ArrayList(sharedLiveCategoryModels);
        for (int i = 0; i < sharedLiveCategoryModels.size(); i++) {
            if (sharedLiveCategoryModels.get(i).getName().equalsIgnoreCase(this.myCategories.get(this.category_pos))) {
                arrayList.set(i, new CategoryModel(String.valueOf((i + 1) * 1000), str));
                break;
            }
        }
        this.preferenceHelper.setSharedPreferenceLiveCategory(arrayList);
        this.myCategories.set(this.category_pos, str);
        this.categoryRecyclerAdapter.setData(this.myCategories, this.category_pos);
        this.recycler_category.setSelectedPosition(this.category_pos);
        List<String> list = this.myCategories;
        this.preferenceHelper.setSharedPreferenceMyGroupCategory(list.subList(2, list.size()));
    }

    private void initView() {
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.str_groups = (TextView) findViewById(R.id.str_groups);
        this.txt_group_name = (TextView) findViewById(R.id.txt_group_name);
        this.str_add = (TextView) findViewById(R.id.str_add);
        this.txt_edit = (TextView) findViewById(R.id.txt_edit);
        this.recycler_category = (LiveVerticalGridView) findViewById(R.id.recycler_category);
        this.recycler_channel = (LiveVerticalGridView) findViewById(R.id.recycler_channel);
        this.btn_add_channel = (Button) findViewById(R.id.btn_add_channel);
        this.image_add = (ImageView) findViewById(R.id.image_add);
        this.str_groups.setText(this.wordModels.getStr_my_group());
        this.btn_add_channel.setText(this.wordModels.getStr_add_channel());
        this.str_add.setText(this.wordModels.getStr_group_string());
        this.txt_edit.setText(this.wordModels.getEdit_group_name());
        this.btn_back.setOnClickListener(new AddGroupActivity$$ExternalSyntheticLambda0(this, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(View view) {
        Intent intent = new Intent();
        intent.putExtra("is_changed", Constants.add_group);
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(String str, Integer num, Boolean bool, Boolean bool2) {
        if (!bool.booleanValue()) {
            this.category_pos = num.intValue();
            if (!bool2.booleanValue() || num.intValue() <= 1) {
                showMyChannels(str, num.intValue());
                return null;
            }
            showRenameDlgFragment();
            return null;
        }
        if (num.intValue() == 0) {
            showAddGroupDlgFragment(false, "");
            return null;
        }
        if (this.btn_add_channel.getVisibility() == 8) {
            this.btn_add_channel.setVisibility(0);
        }
        this.btn_add_channel.requestFocus();
        if (GetSharedInfo.isTVDevice(this)) {
            return null;
        }
        showMyChannels(str, num.intValue());
        this.category_pos = num.intValue();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$1(String str, Integer num, Boolean bool) {
        this.channel_pos = num.intValue();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        showAddChannelDlgFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showAddChannelDlgFragment$5() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showAddChannelDlgFragment$6() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddChannelDlgFragment$7(List list) {
        if (this.category_pos == 1) {
            Iterator<String> it = RealmController.with().getFavChannelNames().iterator();
            while (it.hasNext()) {
                RealmController.with().addToFavChannels(it.next(), false, Realm$$ExternalSyntheticLambda0.INSTANCE$15);
            }
            this.preferenceHelper.setSharedPreferenceLiveFavChannels(list);
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                RealmController.with().addToFavChannels((String) it2.next(), true, Realm$$ExternalSyntheticLambda0.INSTANCE$16);
            }
        } else {
            this.preferenceHelper.setSharedPreferenceMyGroupChannels(this.key, list);
        }
        if (list.size() == 0) {
            this.image_add.setVisibility(0);
            this.str_add.setVisibility(0);
            this.recycler_channel.setVisibility(8);
        } else {
            this.image_add.setVisibility(8);
            this.str_add.setVisibility(8);
            this.recycler_channel.setVisibility(0);
            this.channelRecyclerAdapter.setChannelData(list, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddGroupDlgFragment$4(boolean z, String str) {
        this.addGroupDlgFragment.dismiss();
        if (z) {
            changeRenameCategoryModel(str);
            return;
        }
        this.myCategories = new ArrayList();
        this.myCategories = this.preferenceHelper.getSharedPreferenceMyGroupCategory();
        this.liveCategories = this.preferenceHelper.getSharedLiveCategoryModels();
        this.liveCategories.add(this.myCategories.size() + 4, new CategoryModel(String.valueOf((this.myCategories.size() * 1000) + 5000), str));
        this.preferenceHelper.setSharedPreferenceLiveCategory(this.liveCategories);
        this.myCategories.add(0, this.wordModels.getAdd_group());
        this.myCategories.add(1, this.wordModels.getFavorite());
        AddCategoryRecyclerAdapter addCategoryRecyclerAdapter = this.categoryRecyclerAdapter;
        List<String> list = this.myCategories;
        addCategoryRecyclerAdapter.setData(list, list.size() - 1);
        this.recycler_category.setSelectedPosition(this.myCategories.size() - 1);
        if (!GetSharedInfo.isTVDevice(this)) {
            this.category_pos = this.myCategories.size() - 1;
            this.channelRecyclerAdapter.setChannelData(new ArrayList(), false);
        }
        if (this.btn_add_channel.getVisibility() == 8) {
            this.btn_add_channel.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRenameDlgFragment$3(int i) {
        if (i == 0) {
            showAddGroupDlgFragment(true, this.myCategories.get(this.category_pos));
            return;
        }
        changeCategoryModel();
        this.preferenceHelper.removeKeys(this.myCategories.get(this.category_pos));
        List<String> list = this.myCategories;
        list.remove(list.get(this.category_pos));
        this.categoryRecyclerAdapter.setData(this.myCategories, 0);
        this.recycler_category.setSelectedPosition(0);
        this.channelRecyclerAdapter.setChannelData(new ArrayList(), false);
    }

    private void showAddChannelDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_add_channel");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            fragmentTransactionBeginTransaction.addToBackStack(null);
        } else {
            AddChannelDlgFragment addChannelDlgFragmentNewInstance = AddChannelDlgFragment.newInstance(this.key, this.category_pos);
            this.addChannelDlgFragment = addChannelDlgFragmentNewInstance;
            addChannelDlgFragmentNewInstance.setOnSendMyGroupNamesListener(new AddGroupActivity$$ExternalSyntheticLambda2(this));
            this.addChannelDlgFragment.show(supportFragmentManager, "fragment_add_channel");
        }
    }

    private void showAddGroupDlgFragment(boolean z, String str) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_add_group");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            fragmentTransactionBeginTransaction.addToBackStack(null);
        } else {
            AddGroupDlgFragment addGroupDlgFragmentNewInstance = AddGroupDlgFragment.newInstance(z, str);
            this.addGroupDlgFragment = addGroupDlgFragmentNewInstance;
            addGroupDlgFragmentNewInstance.setOnAddedGroupListener(new AddGroupActivity$$ExternalSyntheticLambda1(this, z, 0));
            this.addGroupDlgFragment.show(supportFragmentManager, "fragment_add_group");
        }
    }

    private void showMyChannels(String str, int i) {
        if (i == 0) {
            this.btn_add_channel.setVisibility(8);
            this.txt_group_name.setVisibility(8);
            this.image_add.setVisibility(0);
            this.str_add.setVisibility(0);
            this.recycler_channel.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.key = str;
            this.btn_add_channel.setVisibility(0);
            this.txt_group_name.setVisibility(0);
            this.txt_group_name.setText(str);
            List<String> sharedPreferenceLiveFavChannels = this.preferenceHelper.getSharedPreferenceLiveFavChannels();
            if (sharedPreferenceLiveFavChannels.size() == 0) {
                this.image_add.setVisibility(0);
                this.str_add.setVisibility(0);
                this.recycler_channel.setVisibility(8);
                return;
            } else {
                this.image_add.setVisibility(8);
                this.str_add.setVisibility(8);
                this.recycler_channel.setVisibility(0);
                this.channelRecyclerAdapter.setChannelData(sharedPreferenceLiveFavChannels, false);
                return;
            }
        }
        this.key = str;
        this.btn_add_channel.setVisibility(0);
        this.txt_group_name.setVisibility(0);
        this.txt_group_name.setText(str);
        List<String> sharedPreferenceMyGroupChannels = this.preferenceHelper.getSharedPreferenceMyGroupChannels(str);
        if (sharedPreferenceMyGroupChannels.size() == 0) {
            this.image_add.setVisibility(0);
            this.str_add.setVisibility(0);
            this.recycler_channel.setVisibility(8);
        } else {
            this.image_add.setVisibility(8);
            this.str_add.setVisibility(8);
            this.recycler_channel.setVisibility(0);
            this.channelRecyclerAdapter.setChannelData(sharedPreferenceMyGroupChannels, false);
        }
    }

    private void showRenameDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_rename");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            fragmentTransactionBeginTransaction.addToBackStack(null);
        } else {
            RenameGroupDlgFragment renameGroupDlgFragment = new RenameGroupDlgFragment();
            this.renameGroupDlgFragment = renameGroupDlgFragment;
            renameGroupDlgFragment.setSelectListener(new AddGroupActivity$$ExternalSyntheticLambda2(this));
            this.renameGroupDlgFragment.show(supportFragmentManager, "fragment_rename");
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 4) {
                switch (keyCode) {
                    case 19:
                        if (this.recycler_channel.hasFocus() && this.channel_pos == 0) {
                            this.btn_add_channel.requestFocus();
                            return true;
                        }
                        if (this.recycler_category.hasFocus() && this.category_pos == 0) {
                            this.btn_back.setFocusable(true);
                            this.btn_back.requestFocus();
                            return true;
                        }
                        break;
                    case 20:
                        if (this.btn_add_channel.hasFocus() && this.recycler_channel.getVisibility() == 0) {
                            this.recycler_channel.requestFocus();
                            this.recycler_channel.setSelectedPosition(0);
                            return true;
                        }
                        break;
                    case 21:
                        if (this.btn_add_channel.hasFocus()) {
                            this.btn_back.setFocusable(true);
                            this.btn_back.requestFocus();
                            return true;
                        }
                        break;
                    case 22:
                        if (this.recycler_category.hasFocus()) {
                            if (this.recycler_channel.getVisibility() == 0) {
                                this.recycler_channel.requestFocus();
                            } else {
                                this.btn_add_channel.requestFocus();
                            }
                            return true;
                        }
                        break;
                }
            } else {
                Intent intent = new Intent();
                intent.putExtra("is_changed", Constants.add_group);
                setResult(-1, intent);
                finish();
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_group);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.liveCategories = this.preferenceHelper.getSharedLiveCategoryModels();
        List<String> sharedPreferenceMyGroupCategory = this.preferenceHelper.getSharedPreferenceMyGroupCategory();
        this.myCategories = sharedPreferenceMyGroupCategory;
        sharedPreferenceMyGroupCategory.add(0, this.wordModels.getAdd_group());
        int i = 1;
        this.myCategories.add(1, this.wordModels.getFavorite());
        AddCategoryRecyclerAdapter addCategoryRecyclerAdapter = new AddCategoryRecyclerAdapter(this, this.myCategories, true, new LiveActivity$$ExternalSyntheticLambda4(this, i));
        this.categoryRecyclerAdapter = addCategoryRecyclerAdapter;
        this.recycler_category.setAdapter(addCategoryRecyclerAdapter);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_category.setNumColumns(1);
            this.recycler_category.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_category.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.AddGroupActivity.1
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i2, i3);
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
            this.recycler_category.setLayoutManager(new LinearLayoutManager(this));
        }
        MyChannelRecyclerAdapter myChannelRecyclerAdapter = new MyChannelRecyclerAdapter(this, new ArrayList(), true, new LiveActivity$$ExternalSyntheticLambda3(this, i));
        this.channelRecyclerAdapter = myChannelRecyclerAdapter;
        this.recycler_channel.setAdapter(myChannelRecyclerAdapter);
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_channel.setNumColumns(1);
            this.recycler_channel.setLoop(false);
            this.recycler_channel.setPreserveFocusAfterLayout(true);
            final View[] viewArr2 = {null};
            this.recycler_channel.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.AddGroupActivity.2
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i2, i3);
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
            this.recycler_channel.setLayoutManager(new LinearLayoutManager(this));
        }
        this.btn_back.setFocusable(false);
        this.recycler_category.setSelectedPosition(0);
        this.recycler_category.requestFocus();
        this.btn_add_channel.setVisibility(8);
        this.btn_add_channel.setOnClickListener(new AddGroupActivity$$ExternalSyntheticLambda0(this, i));
    }
}
