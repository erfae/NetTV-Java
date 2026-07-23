package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.CategoryModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class MyGroupRecyclerAdapter extends RecyclerView.Adapter<CategoryListViewHolder> {
    public Function3<CategoryModel, Integer, Boolean, Unit> clickeListenerFunction;
    private List<CategoryModel> datas;
    private int selected_pos = 0;

    public class CategoryListViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name;

        public CategoryListViewHolder(@NonNull MyGroupRecyclerAdapter myGroupRecyclerAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public MyGroupRecyclerAdapter(Context context, List<CategoryModel> list, Function3<CategoryModel, Integer, Boolean, Unit> function3) {
        this.datas = list;
        this.clickeListenerFunction = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$0(CategoryListViewHolder categoryListViewHolder, View view, boolean z) {
        if (z) {
            categoryListViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
        } else {
            categoryListViewHolder.itemView.setBackgroundResource(R.color.trans_parent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, View view) {
        this.clickeListenerFunction.invoke(this.datas.get(i), Integer.valueOf(i), Boolean.TRUE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.datas.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull CategoryListViewHolder categoryListViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        categoryListViewHolder.txt_name.setText(this.datas.get(i).getName());
        categoryListViewHolder.itemView.setOnFocusChangeListener(new MyGroupRecyclerAdapter$$ExternalSyntheticLambda0(categoryListViewHolder, 0));
        categoryListViewHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 5));
        if (this.selected_pos == i) {
            categoryListViewHolder.itemView.requestFocus();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoryListViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_my_group, viewGroup, false));
    }
}
