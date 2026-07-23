package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.utils.ImageLoaderJava;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class EpisodeHorizontalRecyclerAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {
    public Function3<EpisodeModel, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public List<EpisodeModel> models;
    public int season_pos;
    public int selected_pos;

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView image_episode;
        public RoundedImageView image_logo;
        public ImageView image_play;
        public TextView txt_name;

        public EpisodeViewHolder(@NonNull EpisodeHorizontalRecyclerAdapter episodeHorizontalRecyclerAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.image_episode = (RoundedImageView) view.findViewById(R.id.image_episode);
            this.image_logo = (RoundedImageView) view.findViewById(R.id.image_logo);
            this.image_play = (ImageView) view.findViewById(R.id.image_play);
        }
    }

    public EpisodeHorizontalRecyclerAdapter(Context context, List<EpisodeModel> list, int i, Function3<EpisodeModel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.models = list;
        this.selected_pos = i;
        this.clickFunctionListener = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(EpisodeModel episodeModel, int i, View view) {
        this.clickFunctionListener.invoke(episodeModel, Integer.valueOf(i), Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(EpisodeModel episodeModel, int i, EpisodeViewHolder episodeViewHolder, View view, boolean z) {
        if (!z) {
            episodeViewHolder.itemView.setBackgroundResource(R.drawable.item_episode_bg);
        } else {
            this.clickFunctionListener.invoke(episodeModel, Integer.valueOf(i), Boolean.FALSE);
            episodeViewHolder.itemView.setBackgroundResource(R.drawable.item_episode_selected_bg);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<EpisodeModel> list = this.models;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setEpisodes(List<EpisodeModel> list, int i) {
        this.models = list;
        this.season_pos = i;
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
    public void onBindViewHolder(@NonNull EpisodeViewHolder episodeViewHolder, int i) {
        EpisodeModel episodeModel = this.models.get(i);
        episodeViewHolder.txt_name.setText(String.format("S%d .E%d", Integer.valueOf(this.season_pos + 1), Integer.valueOf(i + 1)));
        String stream_icon = (new PreferenceHelper(this.context).getSharedPreferenceISM3U() || episodeModel.getInfo() == null) ? episodeModel.getStream_icon() : episodeModel.getInfo().getMovie_image();
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, episodeViewHolder.image_episode, stream_icon, R.drawable.episode_icon, episodeViewHolder.image_logo);
        episodeViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, episodeModel, i, 5));
        episodeViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, episodeModel, i, episodeViewHolder, 1));
        if (this.selected_pos != i) {
            episodeViewHolder.image_play.setVisibility(8);
        } else {
            episodeViewHolder.itemView.requestFocus();
            episodeViewHolder.image_play.setVisibility(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EpisodeViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_episode_play, viewGroup, false));
    }
}
