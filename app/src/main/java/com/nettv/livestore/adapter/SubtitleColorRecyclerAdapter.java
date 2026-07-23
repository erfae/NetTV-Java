package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* JADX INFO: loaded from: classes2.dex */
public class SubtitleColorRecyclerAdapter extends RecyclerView.Adapter<SubtitleColorViewHolder> {
    public Function2<Integer, Boolean, Unit> clickFunctionListener;
    public String[] colorArray;
    public int selected_pos;

    public class SubtitleColorViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_check;
        public RoundedImageView image_color;

        public SubtitleColorViewHolder(@NonNull SubtitleColorRecyclerAdapter subtitleColorRecyclerAdapter, View view) {
            super(view);
            this.image_color = (RoundedImageView) view.findViewById(R.id.image_color);
            this.image_check = (ImageView) view.findViewById(R.id.image_check);
        }
    }

    public SubtitleColorRecyclerAdapter(String[] strArr, int i, Function2<Integer, Boolean, Unit> function2) {
        this.colorArray = strArr;
        this.selected_pos = i;
        this.clickFunctionListener = function2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.selected_pos);
        this.clickFunctionListener.invoke(Integer.valueOf(i), Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$1(SubtitleColorViewHolder subtitleColorViewHolder, View view, boolean z) {
        if (z) {
            subtitleColorViewHolder.itemView.setBackgroundResource(R.drawable.round_yellow_border);
        } else {
            subtitleColorViewHolder.itemView.setBackgroundResource(R.color.trans_parent);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        String[] strArr = this.colorArray;
        if (strArr == null) {
            return 0;
        }
        return strArr.length;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull SubtitleColorViewHolder subtitleColorViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        subtitleColorViewHolder.image_color.setColorFilter(Color.parseColor(this.colorArray[i]));
        subtitleColorViewHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 7));
        subtitleColorViewHolder.itemView.setOnFocusChangeListener(new MyGroupRecyclerAdapter$$ExternalSyntheticLambda0(subtitleColorViewHolder, 4));
        if (this.selected_pos == i) {
            subtitleColorViewHolder.image_check.setVisibility(0);
        } else {
            subtitleColorViewHolder.image_check.setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public SubtitleColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SubtitleColorViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_subtitle_color, viewGroup, false));
    }
}
