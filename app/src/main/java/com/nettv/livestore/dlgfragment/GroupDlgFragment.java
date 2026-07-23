package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.LiveActivity$$ExternalSyntheticLambda3;
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.adapter.MyGroupRecyclerAdapter;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.view.LiveVerticalGridView;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class GroupDlgFragment extends DialogFragment {
    public ImageButton btn_back;
    public List<CategoryModel> categoryModelList;
    public LiveVerticalGridView category_list;
    public int category_pos = 0;
    public onSelectCategoryListener listener;
    public MyGroupRecyclerAdapter myGroupRecyclerAdapter;
    public TextView txt_add;
    public WordModels wordModels;

    public interface onSelectCategoryListener {
        void onSelectCategory(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$1(CategoryModel categoryModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        this.listener.onSelectCategory(num.intValue());
        dismiss();
        return null;
    }

    public static GroupDlgFragment newInstance(List<CategoryModel> list, int i) {
        GroupDlgFragment groupDlgFragment = new GroupDlgFragment();
        groupDlgFragment.categoryModelList = list;
        groupDlgFragment.category_pos = i;
        return groupDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_group, viewGroup);
        this.wordModels = GetSharedInfo.getWordModel(getContext());
        this.btn_back = (ImageButton) viewInflate.findViewById(R.id.btn_back);
        this.txt_add = (TextView) viewInflate.findViewById(R.id.txt_add);
        this.btn_back.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 9));
        this.category_list = (LiveVerticalGridView) viewInflate.findViewById(R.id.category_list);
        this.txt_add.setText(this.wordModels.getStr_my_group());
        this.myGroupRecyclerAdapter = new MyGroupRecyclerAdapter(getContext(), this.categoryModelList, new LiveActivity$$ExternalSyntheticLambda3(this, 10));
        if (GetSharedInfo.isTVDevice(getContext())) {
            this.category_list.setSelectedPosition(this.category_pos);
            this.category_list.setNumColumns(1);
            this.category_list.setLoop(false);
            this.category_list.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.category_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.dlgfragment.GroupDlgFragment.1
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
            this.category_list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        this.category_list.setAdapter(this.myGroupRecyclerAdapter);
        return viewInflate;
    }

    public void setOnSelectCategoryListener(onSelectCategoryListener onselectcategorylistener) {
        this.listener = onselectcategorylistener;
    }
}
