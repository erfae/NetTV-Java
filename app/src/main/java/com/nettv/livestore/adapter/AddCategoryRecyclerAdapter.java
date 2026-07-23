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
import java.util.List;
import javax.annotation.Nullable;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

/* JADX INFO: loaded from: classes2.dex */
public class AddCategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryHolder> {
    public List<String> categoryModels;
    public Function4<String, Integer, Boolean, Boolean, Unit> clickListenerFunction;
    public Context context;
    public int selected_pos = -1;

    public class CategoryHolder extends RecyclerView.ViewHolder {
        public TextView category_name;

        public CategoryHolder(@Nullable AddCategoryRecyclerAdapter addCategoryRecyclerAdapter, View view) {
            super(view);
            this.category_name = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public AddCategoryRecyclerAdapter(Context context, List<String> list, boolean z, Function4<String, Integer, Boolean, Boolean, Unit> function4) {
        this.context = context;
        this.categoryModels = list;
        this.clickListenerFunction = function4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(CategoryHolder categoryHolder, int i, View view, boolean z) {
        setBackgroundColor(categoryHolder, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, String str, View view) {
        setSelectedItem(i, true);
        this.clickListenerFunction.invoke(str, Integer.valueOf(i), Boolean.TRUE, Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onBindViewHolder$2(String str, int i, View view) {
        this.clickListenerFunction.invoke(str, Integer.valueOf(i), Boolean.FALSE, Boolean.TRUE);
        return true;
    }

    private void setBackgroundColor(CategoryHolder categoryHolder, int i, boolean z) {
        if (!z) {
            categoryHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
            return;
        }
        String str = this.categoryModels.get(i);
        Function4<String, Integer, Boolean, Boolean, Unit> function4 = this.clickListenerFunction;
        Integer numValueOf = Integer.valueOf(i);
        Boolean bool = Boolean.FALSE;
        function4.invoke(str, numValueOf, bool, bool);
        categoryHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.categoryModels.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setData(List<String> list, int i) {
        this.categoryModels = list;
        this.selected_pos = i;
        notifyDataSetChanged();
    }

    public void setSelectedItem(int i, boolean z) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        if (i2 != -1) {
            notifyItemChanged(i2);
        }
        notifyItemChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, @SuppressLint({"RecyclerView"}) int i) {
        String str = this.categoryModels.get(i);
        categoryHolder.category_name.setText(str);
        int i2 = 1;
        categoryHolder.itemView.setOnFocusChangeListener(new VodRecyclerAdapter$$ExternalSyntheticLambda1(this, categoryHolder, i, i2));
        setBackgroundColor(categoryHolder, i, categoryHolder.itemView.isFocused());
        categoryHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, str, i2));
        categoryHolder.itemView.setOnLongClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda2(this, str, i, i2));
        if (this.selected_pos != i || i == 0) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, categoryHolder.category_name);
        } else {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, categoryHolder.category_name);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoryHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_live_category, viewGroup, false));
    }
}
