package com.nettv.livestore.dlgfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentDialog$$ExternalSyntheticLambda0;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.LiveActivity$$ExternalSyntheticLambda3;
import com.nettv.livestore.adapter.AddChannelRecyclerAdapter;
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

/* JADX INFO: loaded from: classes2.dex */
public class SearchChannelDlgFragment extends DialogFragment implements View.OnClickListener {
    public AddChannelRecyclerAdapter addChannelRecyclerAdapter;
    public ImageButton btn_back;
    public List<CategoryModel> categoryModelList;
    public int category_pos;
    public LiveVerticalGridView channel_list;
    public boolean[] checkedItems;
    public EditText et_search;
    public onSearchChannelListener listener;
    public LinearLayout ly_all;
    public LinearLayout ly_clear;
    public LinearLayout ly_complete;
    public ComponentDialog$$ExternalSyntheticLambda0 searchTicker;
    public TextView str_clear;
    public TextView str_complete;
    public TextView txt_add;
    public TextView txt_category;
    public WordModels wordModels;
    public List<EPGChannel> epgChannels = new ArrayList();
    public List<String> myChannelNames = new ArrayList();
    public Handler handler = new Handler();
    public boolean is_m3u = false;
    public int search_time = 2;

    public interface onSearchChannelListener {
        void onSearchChannelNames(List<String> list);
    }

    private void initView(View view) {
        this.ly_all = (LinearLayout) view.findViewById(R.id.ly_all);
        this.ly_clear = (LinearLayout) view.findViewById(R.id.ly_clear);
        this.ly_complete = (LinearLayout) view.findViewById(R.id.ly_complete);
        this.btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        this.ly_all.setOnClickListener(this);
        this.ly_clear.setOnClickListener(this);
        this.ly_complete.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.et_search = (EditText) view.findViewById(R.id.et_search);
        this.channel_list = (LiveVerticalGridView) view.findViewById(R.id.channel_list);
        this.txt_add = (TextView) view.findViewById(R.id.txt_add);
        this.txt_category = (TextView) view.findViewById(R.id.txt_category);
        this.str_clear = (TextView) view.findViewById(R.id.str_clear);
        this.str_complete = (TextView) view.findViewById(R.id.str_complete);
        this.txt_add.setText(this.wordModels.getSearch());
        this.txt_category.setText(this.wordModels.getSelect_all());
        this.str_clear.setText(this.wordModels.getClear_all());
        this.str_complete.setText(this.wordModels.getStr_confirm());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(EPGChannel ePGChannel, Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.myChannelNames.contains(ePGChannel.getName())) {
                return null;
            }
            this.myChannelNames.removeAll(Collections.singletonList(ePGChannel.getName()));
            return null;
        }
        if (this.myChannelNames.contains(ePGChannel.getName())) {
            return null;
        }
        this.myChannelNames.add(ePGChannel.getName());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$1(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || i != 19 || !this.channel_list.hasFocus()) {
            return false;
        }
        this.et_search.requestFocus();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchTimer$2() {
        if (this.search_time == 0) {
            searchChannels();
        } else {
            runNextTicker();
        }
    }

    public static SearchChannelDlgFragment newInstance(List<CategoryModel> list, List<String> list2, int i) {
        SearchChannelDlgFragment searchChannelDlgFragment = new SearchChannelDlgFragment();
        searchChannelDlgFragment.categoryModelList = list;
        searchChannelDlgFragment.myChannelNames = list2;
        searchChannelDlgFragment.category_pos = i;
        return searchChannelDlgFragment;
    }

    private void runNextTicker() {
        this.search_time--;
        this.handler.postAtTime(this.searchTicker, SystemClock.uptimeMillis() + 1000);
    }

    private void searchChannels() {
        RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(this.categoryModelList.get(this.category_pos), this.et_search.getText().toString().trim(), this.is_m3u, 0);
        this.epgChannels = liveChannelsByCategory;
        this.checkedItems = new boolean[liveChannelsByCategory.size()];
        for (int i = 0; i < this.epgChannels.size(); i++) {
            this.checkedItems[i] = !this.myChannelNames.contains(this.epgChannels.get(i).getName());
        }
        this.addChannelRecyclerAdapter.setChannelData(this.epgChannels, this.checkedItems);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchTimer() {
        ComponentDialog$$ExternalSyntheticLambda0 componentDialog$$ExternalSyntheticLambda0 = new ComponentDialog$$ExternalSyntheticLambda0(this, 12);
        this.searchTicker = componentDialog$$ExternalSyntheticLambda0;
        componentDialog$$ExternalSyntheticLambda0.run();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back /* 2131427464 */:
            case R.id.ly_complete /* 2131427900 */:
                this.listener.onSearchChannelNames(this.myChannelNames);
                dismiss();
                break;
            case R.id.ly_all /* 2131427889 */:
                if (this.epgChannels.size() > 0) {
                    for (int i = 0; i < this.epgChannels.size(); i++) {
                        this.checkedItems[i] = false;
                        if (!this.myChannelNames.contains(this.epgChannels.get(i).getName())) {
                            this.myChannelNames.add(this.epgChannels.get(i).getName());
                        }
                    }
                    this.addChannelRecyclerAdapter.setChannelData(this.epgChannels, this.checkedItems);
                }
                break;
            case R.id.ly_clear /* 2131427899 */:
                if (this.epgChannels.size() > 0) {
                    for (int i2 = 0; i2 < this.epgChannels.size(); i2++) {
                        this.checkedItems[i2] = true;
                        this.myChannelNames.remove(this.epgChannels.get(i2).getName());
                    }
                    this.addChannelRecyclerAdapter.setChannelData(this.epgChannels, this.checkedItems);
                }
                break;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_search_channel, viewGroup, false);
        this.wordModels = GetSharedInfo.getWordModel(getContext());
        this.is_m3u = new PreferenceHelper(getContext()).getSharedPreferenceISM3U();
        initView(viewInflate);
        this.checkedItems = new boolean[0];
        AddChannelRecyclerAdapter addChannelRecyclerAdapter = new AddChannelRecyclerAdapter(getContext(), new ArrayList(), this.checkedItems, new LiveActivity$$ExternalSyntheticLambda3(this, 12));
        this.addChannelRecyclerAdapter = addChannelRecyclerAdapter;
        this.channel_list.setAdapter(addChannelRecyclerAdapter);
        if (GetSharedInfo.isTVDevice(getContext())) {
            this.channel_list.setSelectedPosition(0);
            this.channel_list.setNumColumns(1);
            this.channel_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.channel_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.dlgfragment.SearchChannelDlgFragment.1
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
            this.channel_list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.dlgfragment.SearchChannelDlgFragment.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                SearchChannelDlgFragment searchChannelDlgFragment = SearchChannelDlgFragment.this;
                searchChannelDlgFragment.handler.removeCallbacks(searchChannelDlgFragment.searchTicker);
                SearchChannelDlgFragment.this.searchTimer();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.et_search.requestFocus();
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 9));
        return viewInflate;
    }

    public void setOnSearchChannelListener(onSearchChannelListener onsearchchannellistener) {
        this.listener = onsearchchannellistener;
    }
}
