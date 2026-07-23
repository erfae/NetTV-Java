package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.utils.ImageLoaderJava;
import io.realm.RealmResults;

/* JADX INFO: loaded from: classes2.dex */
public class SeriesRecyclerAdapter extends RealmRecyclerViewAdapter<SeriesModel, VodViewHolder> {
    public Context context;
    public boolean is_grid;
    public ItemClickListener mItemClickListener;
    public int selected_pos;

    public interface ItemClickListener {
        void onFavClick(SeriesModel seriesModel, int i);

        void onFocusPosition(int i);

        void onItemClick(SeriesModel seriesModel, int i);

        void onUnFavClick(SeriesModel seriesModel, int i);
    }

    public class VodViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_fav;
        public RoundedImageView image_logo;
        public RoundedImageView image_vod;
        public TextView txt_name;

        public VodViewHolder(@NonNull SeriesRecyclerAdapter seriesRecyclerAdapter, View view) {
            super(view);
            this.image_vod = (RoundedImageView) view.findViewById(R.id.image_vod);
            this.image_logo = (RoundedImageView) view.findViewById(R.id.image_logo);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.image_fav = (ImageView) view.findViewById(R.id.image_fav);
        }
    }

    public SeriesRecyclerAdapter(Context context, @Nullable RealmResults<SeriesModel> realmResults, boolean z) {
        super(realmResults, true, true);
        this.selected_pos = -1;
        this.context = context;
        this.is_grid = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, SeriesModel seriesModel, View view) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        notifyItemChanged(i2);
        notifyItemChanged(i);
        this.mItemClickListener.onItemClick(seriesModel, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(VodViewHolder vodViewHolder, int i, View view, boolean z) {
        if (!z) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, vodViewHolder.txt_name);
            vodViewHolder.itemView.setBackgroundResource(R.drawable.item_group_bg);
            vodViewHolder.itemView.setScaleX(0.95f);
            vodViewHolder.itemView.setScaleY(0.95f);
            vodViewHolder.txt_name.setSelected(false);
            return;
        }
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.black, vodViewHolder.txt_name);
        vodViewHolder.itemView.setBackgroundResource(R.drawable.item_vod_selected_bg);
        vodViewHolder.txt_name.setSelected(true);
        vodViewHolder.itemView.setScaleX(1.0f);
        vodViewHolder.itemView.setScaleY(1.0f);
        this.mItemClickListener.onFocusPosition(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onBindViewHolder$2(SeriesModel seriesModel, int i, View view) {
        if (seriesModel.isIs_favorite()) {
            this.mItemClickListener.onUnFavClick(seriesModel, i);
            return true;
        }
        this.mItemClickListener.onFavClick(seriesModel, i);
        return true;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull VodViewHolder vodViewHolder, int i) {
        SeriesModel item = getItem(i);
        vodViewHolder.txt_name.setText(item.getName());
        if (item.isIs_favorite()) {
            vodViewHolder.image_fav.setVisibility(0);
        } else {
            vodViewHolder.image_fav.setVisibility(8);
        }
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, vodViewHolder.image_vod, item.getStream_icon(), R.drawable.default_bg, vodViewHolder.image_logo);
        vodViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, item, 17));
        vodViewHolder.itemView.setOnFocusChangeListener(new VodRecyclerAdapter$$ExternalSyntheticLambda1(this, vodViewHolder, i, 6));
        vodViewHolder.itemView.setOnLongClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda2(this, item, i, 3));
        if (this.selected_pos != i) {
            vodViewHolder.itemView.setBackgroundResource(R.drawable.item_group_bg);
            vodViewHolder.itemView.setScaleX(0.95f);
            vodViewHolder.itemView.setScaleY(0.95f);
            vodViewHolder.txt_name.setSelected(false);
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, vodViewHolder.txt_name);
            return;
        }
        vodViewHolder.itemView.setBackgroundResource(R.drawable.item_vod_selected_bg);
        vodViewHolder.txt_name.setSelected(true);
        vodViewHolder.itemView.setScaleX(1.0f);
        vodViewHolder.itemView.setScaleY(1.0f);
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.black, vodViewHolder.txt_name);
        this.mItemClickListener.onFocusPosition(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public VodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return this.is_grid ? new VodViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_vod_grid, viewGroup, false)) : new VodViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_vod, viewGroup, false));
    }
}
