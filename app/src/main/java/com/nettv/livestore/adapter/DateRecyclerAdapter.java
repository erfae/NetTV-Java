package com.nettv.livestore.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.CatchupModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class DateRecyclerAdapter extends RecyclerView.Adapter<XCDateViewHolder> {
    public List<CatchupModel> catchupModels;
    public Function3<CatchupModel, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public int disabled_pos = -1;
    public boolean is_disable = false;
    public int selected_pos = -1;

    public class XCDateViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_date;
        public TextView txt_week;

        public XCDateViewHolder(@NonNull DateRecyclerAdapter dateRecyclerAdapter, View view) {
            super(view);
            this.txt_week = (TextView) view.findViewById(R.id.txt_week);
            this.txt_date = (TextView) view.findViewById(R.id.txt_date);
        }
    }

    public DateRecyclerAdapter(Context context, List<CatchupModel> list, Function3<CatchupModel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.catchupModels = list;
        this.clickFunctionListener = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(XCDateViewHolder xCDateViewHolder, int i, View view, boolean z) {
        setBackgroundColor(xCDateViewHolder, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, CatchupModel catchupModel, View view) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        if (i2 != -1) {
            notifyItemChanged(i2);
        }
        notifyItemChanged(this.selected_pos);
        this.clickFunctionListener.invoke(catchupModel, Integer.valueOf(i), Boolean.TRUE);
    }

    private void setBackgroundColor(XCDateViewHolder xCDateViewHolder, int i, boolean z) {
        CatchupModel catchupModel = this.catchupModels.get(i);
        if (z) {
            this.is_disable = false;
            this.disabled_pos = -1;
            this.clickFunctionListener.invoke(catchupModel, Integer.valueOf(i), Boolean.FALSE);
            xCDateViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
        } else {
            xCDateViewHolder.itemView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }
        if (i == this.selected_pos) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, xCDateViewHolder.txt_date);
        } else {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, xCDateViewHolder.txt_date);
        }
        if (i == this.disabled_pos && this.is_disable) {
            xCDateViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
            xCDateViewHolder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CC33253C")));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CatchupModel> list = this.catchupModels;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setCatchupModels(List<CatchupModel> list, int i) {
        this.catchupModels = list;
        this.selected_pos = i;
        notifyDataSetChanged();
    }

    public void setFocusDisable(int i, boolean z) {
        this.disabled_pos = i;
        this.is_disable = z;
        notifyItemChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull XCDateViewHolder xCDateViewHolder, int i) {
        CatchupModel catchupModel = this.catchupModels.get(i);
        xCDateViewHolder.txt_week.setText(catchupModel.getDayofweek());
        xCDateViewHolder.txt_date.setText(catchupModel.getName());
        xCDateViewHolder.itemView.setOnFocusChangeListener(new VodRecyclerAdapter$$ExternalSyntheticLambda1(this, xCDateViewHolder, i, 3));
        xCDateViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, catchupModel, 4));
        setBackgroundColor(xCDateViewHolder, i, xCDateViewHolder.itemView.isFocused());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public XCDateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XCDateViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_date, viewGroup, false));
    }
}
