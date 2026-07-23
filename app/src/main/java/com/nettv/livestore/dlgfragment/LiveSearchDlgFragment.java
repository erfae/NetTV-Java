package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.LiveActivity$$ExternalSyntheticLambda4;
import com.nettv.livestore.adapter.RecyclerLiveChannelAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.RealmResults;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class LiveSearchDlgFragment extends DialogFragment {
    public CategoryModel categoryModel;
    public Context context;
    public EditText et_search;
    public boolean is_m3u;
    public SelectCurrentChannelListener listener;
    public LockDlgFragment lockDlgFragment;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_channels;
    public RealmResults<EPGChannel> searchChannels;
    public int category_pos = 0;
    public int focus_channel_pos = -1;
    public WordModels wordModels = new WordModels();

    public interface SelectCurrentChannelListener {
        void onSelectCurrentChannel(EPGChannel ePGChannel);
    }

    private void initView(View view) {
        this.et_search = (EditText) view.findViewById(R.id.et_search);
        this.recycler_channels = (LiveVerticalGridView) view.findViewById(R.id.recycler_channels);
        if (!GetSharedInfo.isTVDevice(this.context)) {
            this.recycler_channels.setLayoutManager(new LinearLayoutManager(getContext()));
            return;
        }
        this.recycler_channels.setNumColumns(1);
        this.recycler_channels.setLoop(false);
        this.recycler_channels.setPreserveFocusAfterLayout(true);
        final View[] viewArr = {null};
        this.recycler_channels.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.dlgfragment.LiveSearchDlgFragment.3
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
    }

    private boolean isAdultChannel(String str, String str2) {
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            return str2.contains("adult") || str2.contains("xxx") || str2.contains("porn");
        }
        return Constants.xxx_live_categories.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 19) {
                if (keyCode == 20 && this.et_search.hasFocus()) {
                    this.et_search.setFocusable(false);
                    this.recycler_channels.requestFocus();
                    return true;
                }
            } else if (this.recycler_channels.hasFocus() && this.focus_channel_pos == 0) {
                this.et_search.setFocusable(true);
                this.et_search.requestFocus();
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$searchLiveChannels$1(EPGChannel ePGChannel, Integer num, Boolean bool, Boolean bool2) {
        this.focus_channel_pos = num.intValue();
        if (!bool.booleanValue()) {
            return null;
        }
        if ((this.category_pos != 4 && ePGChannel.is_locked()) || (this.category_pos == 2 && isAdultChannel(ePGChannel.getCategory_id(), ePGChannel.getCategory_name()))) {
            showLockDlgFragment(ePGChannel);
            return null;
        }
        this.listener.onSelectCurrentChannel(ePGChannel);
        dismiss();
        return null;
    }

    public static LiveSearchDlgFragment newInstance(Context context, CategoryModel categoryModel, int i, boolean z) {
        LiveSearchDlgFragment liveSearchDlgFragment = new LiveSearchDlgFragment();
        liveSearchDlgFragment.context = context;
        liveSearchDlgFragment.categoryModel = categoryModel;
        liveSearchDlgFragment.is_m3u = z;
        liveSearchDlgFragment.category_pos = i;
        return liveSearchDlgFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchLiveChannels(String str) {
        int i = this.category_pos;
        int i2 = 5;
        if (i <= 4 || i >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
            this.searchChannels = RealmController.with().getLiveChannelsByCategory(this.categoryModel, str, this.is_m3u, 0);
        } else if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() > 0) {
            this.searchChannels = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(this.category_pos - 5)), str);
        }
        this.recycler_channels.setAdapter(new RecyclerLiveChannelAdapter(this.context, this.searchChannels, -1, new LiveActivity$$ExternalSyntheticLambda4(this, i2)));
    }

    private void showLockDlgFragment(final EPGChannel ePGChannel) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = childFragmentManager.findFragmentByTag("fragment_channel_lock");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LockDlgFragment lockDlgFragmentNewInstance = LockDlgFragment.newInstance(this.preferenceHelper.getSharedPreferenceParentPassword());
        this.lockDlgFragment = lockDlgFragmentNewInstance;
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.dlgfragment.LiveSearchDlgFragment.2
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                LiveSearchDlgFragment.this.listener.onSelectCurrentChannel(ePGChannel);
                LiveSearchDlgFragment.this.dismiss();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                LiveSearchDlgFragment liveSearchDlgFragment = LiveSearchDlgFragment.this;
                Toast.makeText(liveSearchDlgFragment.context, liveSearchDlgFragment.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                LiveSearchDlgFragment liveSearchDlgFragment = LiveSearchDlgFragment.this;
                Toast.makeText(liveSearchDlgFragment.context, liveSearchDlgFragment.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(childFragmentManager, "fragment_channel_lock");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_search, viewGroup, false);
        this.preferenceHelper = new PreferenceHelper(this.context);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        initView(viewInflate);
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.dlgfragment.LiveSearchDlgFragment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                LiveSearchDlgFragment.this.searchLiveChannels(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        searchLiveChannels("");
        if (GetSharedInfo.isTVDevice(this.context)) {
            this.et_search.setFocusable(false);
        }
        this.recycler_channels.requestFocus();
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 6));
        return viewInflate;
    }

    public void setSelectCurrentChannelListener(SelectCurrentChannelListener selectCurrentChannelListener) {
        this.listener = selectCurrentChannelListener;
    }
}
