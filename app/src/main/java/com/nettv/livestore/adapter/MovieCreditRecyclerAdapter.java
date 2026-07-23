package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.models.MovieCreditModel;
import com.nettv.livestore.utils.ImageLoaderJava;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MovieCreditRecyclerAdapter extends RecyclerView.Adapter<VodViewHolder> {
    public Context context;
    public ItemClickListener mItemClickListener;
    public List<MovieCreditModel> models;
    public int selected_pos = -1;

    public interface ItemClickListener {
        void onFocusPosition(MovieCreditModel movieCreditModel, int i);

        void onItemClick(MovieCreditModel movieCreditModel, int i);
    }

    public class VodViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView image_logo;
        public RoundedImageView image_vod;
        public ProgressBar seekBar;
        public TextView txt_name;

        public VodViewHolder(@NonNull MovieCreditRecyclerAdapter movieCreditRecyclerAdapter, View view) {
            super(view);
            this.image_vod = (RoundedImageView) view.findViewById(R.id.image_vod);
            this.image_logo = (RoundedImageView) view.findViewById(R.id.image_logo);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.seekBar = (ProgressBar) view.findViewById(R.id.seekBar);
        }
    }

    public MovieCreditRecyclerAdapter(Context context, @Nullable List<MovieCreditModel> list) {
        this.context = context;
        this.models = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, MovieCreditModel movieCreditModel, View view) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.selected_pos);
        this.mItemClickListener.onItemClick(movieCreditModel, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(VodViewHolder vodViewHolder, MovieCreditModel movieCreditModel, int i, View view, boolean z) {
        if (!z) {
            vodViewHolder.itemView.setBackgroundResource(R.drawable.item_group_bg);
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, vodViewHolder.txt_name);
            vodViewHolder.itemView.setScaleX(0.95f);
            vodViewHolder.itemView.setScaleY(0.95f);
            vodViewHolder.txt_name.setSelected(false);
            return;
        }
        vodViewHolder.itemView.setBackgroundResource(R.drawable.item_vod_selected_bg);
        vodViewHolder.txt_name.setSelected(true);
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.black, vodViewHolder.txt_name);
        vodViewHolder.itemView.setScaleX(1.0f);
        vodViewHolder.itemView.setScaleY(1.0f);
        this.mItemClickListener.onFocusPosition(movieCreditModel, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MovieCreditModel> list = this.models;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setMovieCreditModels(List<MovieCreditModel> list) {
        this.models = list;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int i) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.selected_pos);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull VodViewHolder vodViewHolder, int i) {
        MovieCreditModel movieCreditModel = this.models.get(i);
        vodViewHolder.txt_name.setText(movieCreditModel.getTitle());
        vodViewHolder.seekBar.setVisibility(8);
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, vodViewHolder.image_vod, Constants.IMDB_IMAGE_PREF + movieCreditModel.getPoster_path(), R.drawable.default_bg, vodViewHolder.image_logo);
        vodViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, movieCreditModel, 7));
        vodViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, vodViewHolder, movieCreditModel, i, 3));
        if (this.selected_pos != i) {
            vodViewHolder.itemView.setBackgroundResource(R.drawable.item_group_bg);
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, vodViewHolder.txt_name);
            vodViewHolder.itemView.setScaleX(0.95f);
            vodViewHolder.itemView.setScaleY(0.95f);
            return;
        }
        vodViewHolder.itemView.setBackgroundResource(R.drawable.item_vod_selected_bg);
        MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.black, vodViewHolder.txt_name);
        vodViewHolder.txt_name.setSelected(true);
        vodViewHolder.itemView.setScaleX(1.0f);
        vodViewHolder.itemView.setScaleY(1.0f);
        this.mItemClickListener.onFocusPosition(movieCreditModel, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public VodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VodViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_vod_credit, viewGroup, false));
    }
}
