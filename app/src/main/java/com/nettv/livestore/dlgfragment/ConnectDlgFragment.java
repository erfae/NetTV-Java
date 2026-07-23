package com.nettv.livestore.dlgfragment;

import android.content.Context;
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
import com.nettv.livestore.models.WordModels;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectDlgFragment extends DialogFragment {
    public BlurView blur_view;
    public Context context;
    public ImageButton image_back;
    public List<SideMenu> list;
    public String portal_name;
    public RecyclerView recyclerView;
    public SelectList selectListener;
    public WordModels wordModels = new WordModels();

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

    public static ConnectDlgFragment newInstance(Context context, String str) {
        ConnectDlgFragment connectDlgFragment = new ConnectDlgFragment();
        connectDlgFragment.context = context;
        connectDlgFragment.portal_name = str;
        return connectDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    /* JADX WARN: Type inference failed for: r6v4, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.apps.SideMenu>] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.apps.SideMenu>] */
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_connect, viewGroup);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        ArrayList arrayList = new ArrayList();
        this.list = arrayList;
        arrayList.add(new SideMenu(this.wordModels.getConnect(), 0, 0));
        this.list.add(new SideMenu(this.wordModels.getEdit(), 1, 0));
        this.list.add(new SideMenu(this.wordModels.getDelete(), 2, 0));
        ((TextView) viewInflate.findViewById(R.id.txt_portal_name)).setText(this.portal_name);
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        this.image_back = (ImageButton) viewInflate.findViewById(R.id.image_back);
        this.blur_view = (BlurView) viewInflate.findViewById(R.id.blur_view);
        View decorView = getActivity().getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) decorView.findViewById(android.R.id.content);
        this.blur_view.setupWith(viewGroup2).setFrameClearDrawable(decorView.getBackground()).setBlurAlgorithm(new RenderScriptBlur(getContext())).setBlurRadius(20.0f).setBlurAutoUpdate(true).setHasFixedTransformationMatrix(true);
        this.recyclerView.setAdapter(new ConnectPlaylistAdapter(getContext(), this.list, new LiveActivity$$ExternalSyntheticLambda3(this, 9)));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.image_back.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 8));
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void setSelectListener(SelectList selectList) {
        this.selectListener = selectList;
    }
}
