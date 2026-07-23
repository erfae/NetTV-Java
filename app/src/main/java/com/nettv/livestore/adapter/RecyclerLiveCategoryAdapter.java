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
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import io.realm.RealmResults;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class RecyclerLiveCategoryAdapter extends RecyclerView.Adapter<LiveHomeViewHolder> {
    public List<CategoryModel> categoryModels;
    public Function3<CategoryModel, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public RealmResults<EPGChannel> epgChannels;
    public boolean is_first = true;
    public boolean is_grid;
    public boolean is_m3u;
    public PreferenceHelper preferenceHelper;
    public int selected_position;

    public class LiveHomeViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_count;
        public TextView txt_name;

        public LiveHomeViewHolder(@NonNull RecyclerLiveCategoryAdapter recyclerLiveCategoryAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_count = (TextView) view.findViewById(R.id.txt_count);
        }
    }

    public RecyclerLiveCategoryAdapter(Context context, List<CategoryModel> list, boolean z, boolean z2, int i, Function3<CategoryModel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.categoryModels = list;
        this.clickFunctionListener = function3;
        this.is_m3u = z;
        this.selected_position = i;
        this.is_grid = z2;
        this.preferenceHelper = new PreferenceHelper(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(LiveHomeViewHolder liveHomeViewHolder, CategoryModel categoryModel, int i, View view, boolean z) {
        if (!z) {
            if (this.is_grid) {
                liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.item_channel_grid_bg);
            } else {
                liveHomeViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
            }
            liveHomeViewHolder.txt_name.setSelected(false);
            return;
        }
        if (this.is_grid) {
            liveHomeViewHolder.txt_name.setSelected(true);
            this.clickFunctionListener.invoke(categoryModel, Integer.valueOf(i), Boolean.FALSE);
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.item_channel_grid_selected_bg);
        } else {
            if (this.is_first) {
                this.is_first = false;
                return;
            }
            liveHomeViewHolder.txt_name.setSelected(true);
            this.clickFunctionListener.invoke(categoryModel, Integer.valueOf(i), Boolean.FALSE);
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, CategoryModel categoryModel, View view) {
        int i2 = this.selected_position;
        this.selected_position = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.selected_position);
        this.clickFunctionListener.invoke(categoryModel, Integer.valueOf(i), Boolean.TRUE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CategoryModel> list = this.categoryModels;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setCategoryModels(List<CategoryModel> list) {
        this.categoryModels = list;
        notifyDataSetChanged();
    }

    public void setCategoryPosition(int i) {
        int i2 = this.selected_position;
        this.selected_position = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.selected_position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull LiveHomeViewHolder liveHomeViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        CategoryModel categoryModel = this.categoryModels.get(i);
        liveHomeViewHolder.txt_name.setText(categoryModel.getName());
        if (i <= 4 || i >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
            this.epgChannels = RealmController.with().getLiveChannelsByCategory(categoryModel, "", this.is_m3u, 0);
        } else if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() > 0) {
            this.epgChannels = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(i - 5)), "");
        }
        if (this.is_grid) {
            liveHomeViewHolder.txt_count.setText(this.epgChannels.size() + " channels");
        } else {
            liveHomeViewHolder.txt_count.setText(String.valueOf(this.epgChannels.size()));
        }
        if (i == 0) {
            liveHomeViewHolder.txt_count.setVisibility(8);
        } else {
            liveHomeViewHolder.txt_count.setVisibility(0);
        }
        liveHomeViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, liveHomeViewHolder, categoryModel, i, 5));
        liveHomeViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, categoryModel, 11));
        if (this.selected_position != i || i == 0) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, liveHomeViewHolder.txt_name);
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, liveHomeViewHolder.txt_count);
            return;
        }
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, liveHomeViewHolder.txt_name);
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, liveHomeViewHolder.txt_count);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public LiveHomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return this.is_grid ? new LiveHomeViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_live_category_grid, viewGroup, false)) : new LiveHomeViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_live_category, viewGroup, false));
    }
}
