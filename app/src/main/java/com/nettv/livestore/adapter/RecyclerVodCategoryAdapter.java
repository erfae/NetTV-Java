package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SeriesModel;
import io.realm.RealmResults;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class RecyclerVodCategoryAdapter extends RecyclerView.Adapter<LiveHomeViewHolder> {
    public List<CategoryModel> categoryModels;
    public Function3<CategoryModel, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public boolean is_grid;
    public boolean is_m3u;
    public boolean is_movie;
    public int selected_position;

    public static class LiveHomeViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_count;
        public TextView txt_name;

        public LiveHomeViewHolder(@NonNull View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_count = (TextView) view.findViewById(R.id.txt_count);
        }
    }

    public RecyclerVodCategoryAdapter(Context context, List<CategoryModel> list, int i, boolean z, boolean z2, boolean z3, Function3<CategoryModel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.categoryModels = list;
        this.clickFunctionListener = function3;
        this.selected_position = i;
        this.is_grid = z;
        this.is_movie = z3;
        this.is_m3u = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(CategoryModel categoryModel, int i, LiveHomeViewHolder liveHomeViewHolder, View view, boolean z) {
        if (!z) {
            if (this.is_grid) {
                liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.item_channel_grid_bg);
                return;
            } else {
                liveHomeViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
                return;
            }
        }
        this.clickFunctionListener.invoke(categoryModel, Integer.valueOf(i), Boolean.FALSE);
        if (this.is_grid) {
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.item_channel_grid_selected_bg);
        } else {
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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull LiveHomeViewHolder liveHomeViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        CategoryModel categoryModel = this.categoryModels.get(i);
        liveHomeViewHolder.txt_name.setText(categoryModel.getName());
        if (this.is_movie) {
            RealmResults<MovieModel> movieModelsByCategory = RealmController.with().getMovieModelsByCategory(categoryModel, "", this.is_m3u, 0);
            if (this.is_grid) {
                liveHomeViewHolder.txt_count.setText(movieModelsByCategory.size() + " movies");
            } else {
                TextView textView = liveHomeViewHolder.txt_count;
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("");
                sbM.append(movieModelsByCategory.size());
                textView.setText(sbM.toString());
            }
        } else {
            RealmResults<SeriesModel> seriesModelsByCategory = RealmController.with().getSeriesModelsByCategory(categoryModel, "", this.is_m3u, 0);
            if (this.is_grid) {
                liveHomeViewHolder.txt_count.setText(seriesModelsByCategory.size() + " series");
            } else {
                TextView textView2 = liveHomeViewHolder.txt_count;
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("");
                sbM2.append(seriesModelsByCategory.size());
                textView2.setText(sbM2.toString());
            }
        }
        liveHomeViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, categoryModel, i, liveHomeViewHolder, 9));
        liveHomeViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, categoryModel, 15));
        if (this.selected_position == i) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, liveHomeViewHolder.txt_name);
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, liveHomeViewHolder.txt_count);
            return;
        }
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, liveHomeViewHolder.txt_name);
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, liveHomeViewHolder.txt_count);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public LiveHomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return this.is_grid ? new LiveHomeViewHolder(MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_live_category_grid, viewGroup, false)) : new LiveHomeViewHolder(MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_live_category, viewGroup, false));
    }
}
