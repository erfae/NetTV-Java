package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.ResumeModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class EpisodeRecyclerAdapter extends RecyclerView.Adapter<XCEpisodeViewHolder> {
    public Function3<EpisodeModel, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public List<EpisodeModel> episodeModels;
    public String season_name;
    public String series_name;
    public PreferenceHelper sharedPreferenceHelper;

    public class XCEpisodeViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_logo;
        public ProgressBar seekbar;
        public TextView txt_description;
        public TextView txt_name;
        public TextView txt_num;

        public XCEpisodeViewHolder(@NonNull EpisodeRecyclerAdapter episodeRecyclerAdapter, View view) {
            super(view);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_num = (TextView) view.findViewById(R.id.txt_num);
            this.txt_description = (TextView) view.findViewById(R.id.txt_description);
            this.seekbar = (ProgressBar) view.findViewById(R.id.seekbar);
        }
    }

    public EpisodeRecyclerAdapter(Context context, List<EpisodeModel> list, String str, String str2, Function3<EpisodeModel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.episodeModels = list;
        this.series_name = str;
        this.season_name = str2;
        this.clickFunctionListener = function3;
        this.sharedPreferenceHelper = new PreferenceHelper(context);
    }

    private int getCurrentProgress(String str) {
        for (ResumeModel resumeModel : this.sharedPreferenceHelper.getSharedPreferenceSeriesResumeModel()) {
            if (resumeModel.getName().equalsIgnoreCase(str)) {
                return resumeModel.getPro();
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(EpisodeModel episodeModel, int i, XCEpisodeViewHolder xCEpisodeViewHolder, View view, boolean z) {
        if (z) {
            this.clickFunctionListener.invoke(episodeModel, Integer.valueOf(i), Boolean.FALSE);
            xCEpisodeViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
            if (getCurrentProgress(this.series_name + this.season_name + episodeModel.getTitle()) > 0) {
                xCEpisodeViewHolder.seekbar.setVisibility(0);
                return;
            }
            return;
        }
        xCEpisodeViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
        if (getCurrentProgress(this.series_name + this.season_name + episodeModel.getTitle()) > 0) {
            xCEpisodeViewHolder.seekbar.setVisibility(0);
        } else {
            xCEpisodeViewHolder.seekbar.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(EpisodeModel episodeModel, int i, View view) {
        this.clickFunctionListener.invoke(episodeModel, Integer.valueOf(i), Boolean.TRUE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<EpisodeModel> list = this.episodeModels;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setEpisodeModels(List<EpisodeModel> list, String str) {
        this.episodeModels = list;
        this.season_name = str;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull XCEpisodeViewHolder xCEpisodeViewHolder, int i) {
        EpisodeModel episodeModel = this.episodeModels.get(i);
        xCEpisodeViewHolder.txt_num.setText(episodeModel.getEpisode_num());
        xCEpisodeViewHolder.txt_name.setText(episodeModel.getTitle());
        if (episodeModel.getInfo() != null) {
            xCEpisodeViewHolder.txt_description.setText(episodeModel.getInfo().getPlot());
        }
        if (episodeModel.getInfo() == null || episodeModel.getInfo().getMovie_image() == null || episodeModel.getInfo().getMovie_image().isEmpty()) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.episode_icon)).error(R.drawable.episode_icon).into(xCEpisodeViewHolder.image_logo);
        } else {
            Glide.with(this.context).load(episodeModel.getInfo().getMovie_image()).error(R.drawable.episode_icon).placeholder(R.drawable.episode_icon).into(xCEpisodeViewHolder.image_logo);
        }
        xCEpisodeViewHolder.seekbar.setProgress(getCurrentProgress(this.series_name + this.season_name + episodeModel.getTitle()));
        if (getCurrentProgress(this.series_name + this.season_name + episodeModel.getTitle()) > 0) {
            xCEpisodeViewHolder.seekbar.setVisibility(0);
        } else {
            xCEpisodeViewHolder.seekbar.setVisibility(8);
        }
        xCEpisodeViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, episodeModel, i, xCEpisodeViewHolder, 2));
        xCEpisodeViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, episodeModel, i, 6));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public XCEpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XCEpisodeViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_episode, viewGroup, false));
    }
}
