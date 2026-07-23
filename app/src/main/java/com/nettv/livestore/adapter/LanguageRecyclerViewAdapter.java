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
import com.nettv.livestore.models.LanguageModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* JADX INFO: loaded from: classes2.dex */
public class LanguageRecyclerViewAdapter extends RecyclerView.Adapter<HideCategoryViewHolder> {
    public List<LanguageModel> category_data;
    public int checked_pos;
    public Function2<Integer, Boolean, Unit> clickFunctionListener;

    public class HideCategoryViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView txt_name;

        public HideCategoryViewHolder(@NonNull LanguageRecyclerViewAdapter languageRecyclerViewAdapter, View view) {
            super(view);
            this.txt_name = (CheckedTextView) view.findViewById(R.id.txt_name);
        }
    }

    public LanguageRecyclerViewAdapter(Context context, List<LanguageModel> list, int i, Function2<Integer, Boolean, Unit> function2) {
        this.category_data = list;
        this.checked_pos = i;
        this.clickFunctionListener = function2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        int i2 = this.checked_pos;
        this.checked_pos = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.checked_pos);
        this.clickFunctionListener.invoke(Integer.valueOf(i), Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$1(HideCategoryViewHolder hideCategoryViewHolder, View view, boolean z) {
        if (z) {
            hideCategoryViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
        } else {
            hideCategoryViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<LanguageModel> list = this.category_data;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull HideCategoryViewHolder hideCategoryViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        hideCategoryViewHolder.txt_name.setText(this.category_data.get(i).getName());
        hideCategoryViewHolder.txt_name.setChecked(this.checked_pos == i);
        hideCategoryViewHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 3));
        hideCategoryViewHolder.itemView.setOnFocusChangeListener(new MyGroupRecyclerAdapter$$ExternalSyntheticLambda0(hideCategoryViewHolder, 2));
        if (this.checked_pos == i) {
            hideCategoryViewHolder.itemView.requestFocus();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public HideCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HideCategoryViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_hide_category, viewGroup, false));
    }
}
