package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.Season;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class SeasonRecyclerAdapter extends RecyclerView.Adapter<XCSeasonViewHolder> {
    public Function3<Season, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public List<Season> seasonList;
    public int selected_pos = 0;

    public class XCSeasonViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_season;

        public XCSeasonViewHolder(@NonNull SeasonRecyclerAdapter seasonRecyclerAdapter, View view) {
            super(view);
            this.txt_season = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public SeasonRecyclerAdapter(Context context, List<Season> list, Function3<Season, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.seasonList = list;
        this.clickFunctionListener = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(XCSeasonViewHolder xCSeasonViewHolder, int i, View view, boolean z) {
        setBackgroundColor(xCSeasonViewHolder, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, View view) {
        setSelectedItem(i);
        this.clickFunctionListener.invoke(this.seasonList.get(i), Integer.valueOf(i), Boolean.TRUE);
    }

    private void setBackgroundColor(XCSeasonViewHolder xCSeasonViewHolder, int i, boolean z) {
        if (z) {
            this.clickFunctionListener.invoke(this.seasonList.get(i), Integer.valueOf(i), Boolean.FALSE);
            xCSeasonViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
        } else {
            xCSeasonViewHolder.itemView.setBackgroundResource(R.color.trans_parent);
        }
        if (i == this.selected_pos) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, xCSeasonViewHolder.txt_season);
        } else {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, xCSeasonViewHolder.txt_season);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Season> list = this.seasonList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setFocusDisable(int i, boolean z) {
        notifyItemChanged(i);
    }

    public void setSeasonList(List<Season> list) {
        this.seasonList = list;
        notifyDataSetChanged();
    }

    public void setSelectedItem(int i) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        if (i2 != -1) {
            notifyItemChanged(i2);
        }
        notifyItemChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull XCSeasonViewHolder xCSeasonViewHolder, int i) {
        xCSeasonViewHolder.txt_season.setText(this.seasonList.get(i).getName());
        xCSeasonViewHolder.itemView.setOnFocusChangeListener(new VodRecyclerAdapter$$ExternalSyntheticLambda1(this, xCSeasonViewHolder, i, 5));
        setBackgroundColor(xCSeasonViewHolder, i, xCSeasonViewHolder.itemView.isFocused());
        xCSeasonViewHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 0));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public XCSeasonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XCSeasonViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_season, viewGroup, false));
    }
}
