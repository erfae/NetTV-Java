package com.nettv.livestore.dlgfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.AddChannelRecyclerAdapter;
import com.nettv.livestore.adapter.MyChannelRecyclerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class AddChannelDlgFragment extends DialogFragment implements View.OnClickListener {
    public ImageButton btn_back;
    public List<CategoryModel> categoryModelList;
    public int category_pos;
    public AddChannelRecyclerAdapter channelRecyclerAdapter;
    public LiveVerticalGridView channel_list;
    public boolean[] checkedItems;
    public RealmResults<EPGChannel> epgChannels;
    public GroupDlgFragment groupDlgFragment;
    public ImageView image_add;
    public String key;
    public onSendMyGroupNamesListener listener;
    public LinearLayout ly_category;
    public LinearLayout ly_complete;
    public LinearLayout ly_search;
    public MyChannelRecyclerAdapter myChannelRecyclerAdapter;
    public LiveVerticalGridView my_channel_list;
    public PreferenceHelper preferenceHelper;
    public SearchChannelDlgFragment searchChannelDlgFragment;
    public TextView str_add_channel;
    public TextView str_check;
    public TextView str_complete;
    public TextView str_search;
    public TextView txt_category;
    public TextView txt_category_name;
    public TextView txt_count;
    public TextView txt_my_category;
    public WordModels wordModels;
    public List<String> myChannelNames = new ArrayList();
    public int selected_category = 0;
    public boolean is_m3u = false;

    public interface onSendMyGroupNamesListener {
        void onSendMyGroup(List<String> list);
    }

    private void initView(View view) {
        this.ly_category = (LinearLayout) view.findViewById(R.id.ly_category);
        this.ly_search = (LinearLayout) view.findViewById(R.id.ly_search);
        this.ly_complete = (LinearLayout) view.findViewById(R.id.ly_complete);
        this.btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        this.ly_category.setOnClickListener(this);
        this.ly_search.setOnClickListener(this);
        this.ly_complete.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.txt_category = (TextView) view.findViewById(R.id.txt_category);
        this.txt_category_name = (TextView) view.findViewById(R.id.txt_category_name);
        this.txt_count = (TextView) view.findViewById(R.id.txt_count);
        this.txt_my_category = (TextView) view.findViewById(R.id.txt_my_category);
        this.str_search = (TextView) view.findViewById(R.id.str_search);
        this.str_complete = (TextView) view.findViewById(R.id.str_complete);
        this.str_add_channel = (TextView) view.findViewById(R.id.txt_add);
        this.channel_list = (LiveVerticalGridView) view.findViewById(R.id.channel_list);
        this.my_channel_list = (LiveVerticalGridView) view.findViewById(R.id.my_channel_list);
        this.str_check = (TextView) view.findViewById(R.id.str_check);
        this.image_add = (ImageView) view.findViewById(R.id.image_add);
        this.str_search.setText(this.wordModels.getStr_search_channels());
        this.str_complete.setText(this.wordModels.getStr_complete());
        this.str_add_channel.setText(this.wordModels.getStr_add_channels());
        this.str_check.setText(this.wordModels.getStr_mark_channels());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(EPGChannel ePGChannel, Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            if (this.myChannelNames.contains(ePGChannel.getName())) {
                this.myChannelNames.removeAll(Collections.singletonList(ePGChannel.getName()));
            }
        } else if (!this.myChannelNames.contains(ePGChannel.getName())) {
            this.myChannelNames.add(ePGChannel.getName());
        }
        if (this.myChannelNames.size() > 0) {
            this.my_channel_list.setVisibility(0);
            this.str_check.setVisibility(8);
            this.image_add.setVisibility(8);
        } else {
            this.my_channel_list.setVisibility(8);
            this.str_check.setVisibility(0);
            this.image_add.setVisibility(0);
        }
        this.myChannelRecyclerAdapter.setChannelData(this.myChannelNames, false);
        this.txt_count.setText(this.myChannelNames.size() + "/300");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$1(String str, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        this.myChannelNames.remove(str);
        this.myChannelRecyclerAdapter.setChannelData(this.myChannelNames, false);
        this.txt_count.setText(this.myChannelNames.size() + "/300");
        this.preferenceHelper.setSharedPreferenceMyGroupChannels(this.key, this.myChannelNames);
        this.checkedItems = new boolean[this.epgChannels.size()];
        for (int i = 0; i < this.epgChannels.size(); i++) {
            this.checkedItems[i] = !this.myChannelNames.contains(((EPGChannel) this.epgChannels.get(i)).getName());
        }
        this.channelRecyclerAdapter.setChannelData(this.epgChannels, this.checkedItems);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$2(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return false;
        }
        if (i == 22) {
            if (this.ly_category.hasFocus() || this.ly_search.hasFocus() || this.ly_complete.hasFocus()) {
                this.channel_list.requestFocus();
                return true;
            }
            if (this.channel_list.hasFocus() && this.myChannelNames.size() > 0) {
                this.my_channel_list.requestFocus();
            }
            return true;
        }
        if (i != 21) {
            if (i != 4) {
                return false;
            }
            this.listener.onSendMyGroup(this.myChannelNames);
            return false;
        }
        if (this.my_channel_list.hasFocus()) {
            this.channel_list.requestFocus();
            return true;
        }
        if (this.channel_list.hasFocus()) {
            this.ly_category.requestFocus();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    public /* synthetic */ void lambda$showGroupDlgFragment$3(int i) {
        this.selected_category = i;
        this.txt_category.setText(((CategoryModel) this.categoryModelList.get(i)).getName());
        this.txt_category_name.setText(((CategoryModel) this.categoryModelList.get(i)).getName());
        RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory((CategoryModel) this.categoryModelList.get(i), "", this.is_m3u, 0);
        this.epgChannels = liveChannelsByCategory;
        this.checkedItems = new boolean[liveChannelsByCategory.size()];
        for (int i2 = 0; i2 < this.epgChannels.size(); i2++) {
            this.checkedItems[i2] = !this.myChannelNames.contains(((EPGChannel) this.epgChannels.get(i2)).getName());
        }
        this.channelRecyclerAdapter.setChannelData(this.epgChannels, this.checkedItems);
        if (this.epgChannels.size() > 0) {
            this.channel_list.setSelectedPosition(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearchDlgFragment$4(List list) {
        this.myChannelNames = list;
        this.txt_count.setText(this.myChannelNames.size() + "/300");
        this.myChannelRecyclerAdapter.setChannelData(this.myChannelNames, false);
        this.preferenceHelper.setSharedPreferenceMyGroupChannels(this.key, this.myChannelNames);
        this.checkedItems = new boolean[this.epgChannels.size()];
        for (int i = 0; i < this.epgChannels.size(); i++) {
            this.checkedItems[i] = !this.myChannelNames.contains(((EPGChannel) this.epgChannels.get(i)).getName());
        }
        this.channelRecyclerAdapter.setChannelData(this.epgChannels, this.checkedItems);
    }

    public static AddChannelDlgFragment newInstance(String str, int i) {
        AddChannelDlgFragment addChannelDlgFragment = new AddChannelDlgFragment();
        addChannelDlgFragment.key = str;
        addChannelDlgFragment.category_pos = i;
        return addChannelDlgFragment;
    }

    private void showGroupDlgFragment() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = childFragmentManager.findFragmentByTag("fragment_my_group");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            fragmentTransactionBeginTransaction.addToBackStack(null);
        } else {
            GroupDlgFragment groupDlgFragmentNewInstance = GroupDlgFragment.newInstance(this.categoryModelList, this.selected_category);
            this.groupDlgFragment = groupDlgFragmentNewInstance;
            groupDlgFragmentNewInstance.setOnSelectCategoryListener(new AddChannelDlgFragment$$ExternalSyntheticLambda0(this));
            this.groupDlgFragment.show(childFragmentManager, "fragment_my_group");
        }
    }

    private void showSearchDlgFragment() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = childFragmentManager.findFragmentByTag("fragment_search_channel");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            fragmentTransactionBeginTransaction.addToBackStack(null);
        } else {
            SearchChannelDlgFragment searchChannelDlgFragmentNewInstance = SearchChannelDlgFragment.newInstance(this.categoryModelList, this.myChannelNames, this.selected_category);
            this.searchChannelDlgFragment = searchChannelDlgFragmentNewInstance;
            searchChannelDlgFragmentNewInstance.setOnSearchChannelListener(new AddChannelDlgFragment$$ExternalSyntheticLambda0(this));
            this.searchChannelDlgFragment.show(childFragmentManager, "fragment_search_channel");
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back /* 2131427464 */:
            case R.id.ly_complete /* 2131427900 */:
                this.listener.onSendMyGroup(this.myChannelNames);
                dismiss();
                break;
            case R.id.ly_category /* 2131427896 */:
                showGroupDlgFragment();
                break;
            case R.id.ly_search /* 2131427918 */:
                showSearchDlgFragment();
                break;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    /* JADX WARN: Type inference failed for: r8v10, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    /* JADX WARN: Type inference failed for: r8v14, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CategoryModel>] */
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_add_channel, viewGroup);
        this.preferenceHelper = new PreferenceHelper(getContext());
        this.wordModels = GetSharedInfo.getWordModel(getContext());
        initView(viewInflate);
        this.is_m3u = this.preferenceHelper.getSharedPreferenceISM3U();
        this.categoryModelList = new ArrayList();
        for (int size = this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5; size < this.preferenceHelper.getSharedLiveCategoryModels().size(); size++) {
            CategoryModel categoryModel = this.preferenceHelper.getSharedLiveCategoryModels().get(size);
            if (!Constants.xxx_live_categories.contains(categoryModel.getId())) {
                this.categoryModelList.add(categoryModel);
            }
        }
        final int i = 0;
        this.txt_category.setText(((CategoryModel) this.categoryModelList.get(0)).getName());
        this.txt_category_name.setText(((CategoryModel) this.categoryModelList.get(0)).getName());
        this.epgChannels = RealmController.with().getLiveChannelsByCategory((CategoryModel) this.categoryModelList.get(0), "", this.is_m3u, 0);
        final int i2 = 1;
        if (this.category_pos == 1) {
            this.myChannelNames = this.preferenceHelper.getSharedPreferenceLiveFavChannels();
            this.txt_my_category.setText(this.wordModels.getFavorite());
        } else {
            this.myChannelNames = this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.key);
            this.txt_my_category.setText(this.key);
        }
        this.txt_count.setText(this.myChannelNames.size() + "/300");
        if (this.myChannelNames.size() > 0) {
            this.my_channel_list.setVisibility(0);
            this.str_check.setVisibility(8);
            this.image_add.setVisibility(8);
        } else {
            this.my_channel_list.setVisibility(8);
            this.str_check.setVisibility(0);
            this.image_add.setVisibility(0);
        }
        this.checkedItems = new boolean[this.epgChannels.size()];
        for (int i3 = 0; i3 < this.epgChannels.size(); i3++) {
            this.checkedItems[i3] = !this.myChannelNames.contains(((EPGChannel) this.epgChannels.get(i3)).getName());
        }
        AddChannelRecyclerAdapter addChannelRecyclerAdapter = new AddChannelRecyclerAdapter(getContext(), this.epgChannels, this.checkedItems, new Function3(this) { // from class: com.nettv.livestore.dlgfragment.AddChannelDlgFragment$$ExternalSyntheticLambda1
            public final /* synthetic */ AddChannelDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i) {
                    case 0:
                        return this.f$0.lambda$onCreateView$0((EPGChannel) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$onCreateView$1((String) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        });
        this.channelRecyclerAdapter = addChannelRecyclerAdapter;
        this.channel_list.setAdapter(addChannelRecyclerAdapter);
        if (GetSharedInfo.isTVDevice(getContext())) {
            this.channel_list.setSelectedPosition(0);
            this.channel_list.setNumColumns(1);
            this.channel_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.channel_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.dlgfragment.AddChannelDlgFragment.1
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i4, int i5) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i4, i5);
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
            this.channel_list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        MyChannelRecyclerAdapter myChannelRecyclerAdapter = new MyChannelRecyclerAdapter(getContext(), this.myChannelNames, false, new Function3(this) { // from class: com.nettv.livestore.dlgfragment.AddChannelDlgFragment$$ExternalSyntheticLambda1
            public final /* synthetic */ AddChannelDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i2) {
                    case 0:
                        return this.f$0.lambda$onCreateView$0((EPGChannel) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$onCreateView$1((String) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        });
        this.myChannelRecyclerAdapter = myChannelRecyclerAdapter;
        this.my_channel_list.setAdapter(myChannelRecyclerAdapter);
        if (GetSharedInfo.isTVDevice(getContext())) {
            this.my_channel_list.setNumColumns(1);
            this.my_channel_list.setLoop(false);
            this.my_channel_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr2 = {null};
            this.my_channel_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.dlgfragment.AddChannelDlgFragment.2
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i4, int i5) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i4, i5);
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
            this.my_channel_list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, i2));
        this.ly_category.requestFocus();
        return viewInflate;
    }

    public void setOnSendMyGroupNamesListener(onSendMyGroupNamesListener onsendmygroupnameslistener) {
        this.listener = onsendmygroupnameslistener;
    }
}
