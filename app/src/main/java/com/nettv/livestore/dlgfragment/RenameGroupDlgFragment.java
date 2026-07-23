package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.LiveActivity$$ExternalSyntheticLambda3;
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.adapter.ConnectPlaylistAdapter;
import com.nettv.livestore.apps.SideMenu;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.WordModels;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class RenameGroupDlgFragment extends DialogFragment {
    public ImageButton btn_back;
    public RecyclerView recycler_rename;
    public List<SideMenu> renameLists;
    public SelectList selectListener;
    public TextView txt_name;
    public WordModels wordModels;

    public interface SelectList {
        void onSelect(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(SideMenu sideMenu, Integer num, Boolean bool) {
        dismiss();
        this.selectListener.onSelect(num.intValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    /* JADX WARN: Type inference failed for: r5v15, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.apps.SideMenu>] */
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_rename, viewGroup);
        this.recycler_rename = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        this.btn_back = (ImageButton) viewInflate.findViewById(R.id.image_back);
        this.txt_name = (TextView) viewInflate.findViewById(R.id.txt_name);
        new PreferenceHelper(getContext());
        WordModels wordModel = GetSharedInfo.getWordModel(getContext());
        this.wordModels = wordModel;
        this.txt_name.setText(wordModel.getChange_my_group());
        ArrayList arrayList = new ArrayList();
        this.renameLists = arrayList;
        arrayList.add(new SideMenu(this.wordModels.getRename(), 0, 0));
        this.renameLists.add(new SideMenu(this.wordModels.getStr_delete(), 1, 1));
        this.recycler_rename.setAdapter(new ConnectPlaylistAdapter(getContext(), this.renameLists, new LiveActivity$$ExternalSyntheticLambda3(this, 11)));
        this.recycler_rename.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.btn_back.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 13));
        return viewInflate;
    }

    public void setSelectListener(SelectList selectList) {
        this.selectListener = selectList;
    }
}
