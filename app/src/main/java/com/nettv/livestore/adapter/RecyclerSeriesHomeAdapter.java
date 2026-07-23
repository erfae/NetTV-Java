package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.SeriesModel;
import com.nettv.livestore.utils.ImageLoaderJava;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class RecyclerSeriesHomeAdapter extends RecyclerView.Adapter<LiveHomeViewHolder> {
    public Function3<SeriesModel, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public List<SeriesModel> models;

    public class LiveHomeViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_logo;
        public ImageView image_movie;
        public TextView txt_name;

        public LiveHomeViewHolder(@NonNull RecyclerSeriesHomeAdapter recyclerSeriesHomeAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.image_movie = (ImageView) view.findViewById(R.id.image_channel);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
        }
    }

    public RecyclerSeriesHomeAdapter(Context context, List<SeriesModel> list, Function3<SeriesModel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.models = list;
        this.clickFunctionListener = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(SeriesModel seriesModel, int i, LiveHomeViewHolder liveHomeViewHolder, View view, boolean z) {
        if (!z) {
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.search_item_bg);
        } else {
            this.clickFunctionListener.invoke(seriesModel, Integer.valueOf(i), Boolean.FALSE);
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.search_focused_bg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(SeriesModel seriesModel, int i, View view) {
        this.clickFunctionListener.invoke(seriesModel, Integer.valueOf(i), Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onBindViewHolder$2(LiveHomeViewHolder liveHomeViewHolder, SeriesModel seriesModel, int i, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3) {
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.search_item_bg);
        }
        if (motionEvent.getAction() == 0) {
            liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.search_focused_bg);
            return true;
        }
        if (motionEvent.getAction() != 1) {
            return false;
        }
        liveHomeViewHolder.itemView.setBackgroundResource(R.drawable.search_item_bg);
        this.clickFunctionListener.invoke(seriesModel, Integer.valueOf(i), Boolean.TRUE);
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<SeriesModel> list = this.models;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull LiveHomeViewHolder liveHomeViewHolder, int i) {
        SeriesModel seriesModel = this.models.get(i);
        liveHomeViewHolder.txt_name.setText(seriesModel.getName());
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, liveHomeViewHolder.image_movie, seriesModel.getStream_icon(), R.drawable.default_series, liveHomeViewHolder.image_logo);
        liveHomeViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, seriesModel, i, liveHomeViewHolder, 8));
        liveHomeViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, seriesModel, i, 14));
        liveHomeViewHolder.itemView.setOnTouchListener(new RecyclerVodHomeAdapter$$ExternalSyntheticLambda0(this, liveHomeViewHolder, seriesModel, i, 2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public LiveHomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LiveHomeViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_home_vod, viewGroup, false));
    }
}
