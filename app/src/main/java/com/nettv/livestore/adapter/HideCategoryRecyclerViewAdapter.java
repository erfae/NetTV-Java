package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* JADX INFO: loaded from: classes2.dex */
public class HideCategoryRecyclerViewAdapter extends RecyclerView.Adapter<HideCategoryViewHolder> {
    public String[] category_data;
    public boolean[] checks;
    public Function2<Integer, Boolean, Unit> clickFunctionListener;

    public class HideCategoryViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView txt_name;

        public HideCategoryViewHolder(@NonNull HideCategoryRecyclerViewAdapter hideCategoryRecyclerViewAdapter, View view) {
            super(view);
            this.txt_name = (CheckedTextView) view.findViewById(R.id.txt_name);
        }
    }

    public HideCategoryRecyclerViewAdapter(Context context, String[] strArr, boolean[] zArr, Function2<Integer, Boolean, Unit> function2) {
        this.category_data = strArr;
        this.checks = zArr;
        this.clickFunctionListener = function2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        toggleChecked(i);
        this.clickFunctionListener.invoke(Integer.valueOf(i), Boolean.valueOf(this.checks[i]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$1(HideCategoryViewHolder hideCategoryViewHolder, View view, boolean z) {
        if (z) {
            hideCategoryViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
        } else {
            hideCategoryViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
        }
    }

    public void allChecked(boolean z) {
        for (int i = 0; i < this.category_data.length; i++) {
            this.checks[i] = z;
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.category_data.length;
    }

    public void toggleChecked(int i) {
        boolean[] zArr = this.checks;
        zArr[i] = !zArr[i];
        notifyItemChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull HideCategoryViewHolder hideCategoryViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        hideCategoryViewHolder.txt_name.setText(this.category_data[i]);
        hideCategoryViewHolder.txt_name.setChecked(this.checks[i]);
        hideCategoryViewHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 2));
        hideCategoryViewHolder.itemView.setOnFocusChangeListener(new MyGroupRecyclerAdapter$$ExternalSyntheticLambda0(hideCategoryViewHolder, 1));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public HideCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HideCategoryViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_hide_category, viewGroup, false));
    }
}
