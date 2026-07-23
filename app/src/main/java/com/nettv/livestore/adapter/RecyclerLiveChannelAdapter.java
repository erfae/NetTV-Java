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
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.utils.ImageLoaderJava;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

/* JADX INFO: loaded from: classes2.dex */
public class RecyclerLiveChannelAdapter extends RecyclerView.Adapter<LiveChannelViewHolder> {
    public Function4<EPGChannel, Integer, Boolean, Boolean, Unit> clickFunctionListener;
    public Context context;
    public List<EPGChannel> epgChannels;
    public int selected_pos;

    public static class LiveChannelViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_catch;
        public ImageView image_channel;
        public ImageView image_fav;
        public ImageView image_lock;
        public ImageView image_logo;
        public TextView txt_name;
        public TextView txt_num;

        public LiveChannelViewHolder(@NonNull View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_num = (TextView) view.findViewById(R.id.txt_num);
            this.image_channel = (ImageView) view.findViewById(R.id.image_channel);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
            this.image_fav = (ImageView) view.findViewById(R.id.image_fav);
            this.image_catch = (ImageView) view.findViewById(R.id.image_catch);
            this.image_lock = (ImageView) view.findViewById(R.id.image_lock);
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public RecyclerLiveChannelAdapter(Context context, @Nullable List<EPGChannel> list, int i, Function4<EPGChannel, Integer, Boolean, Boolean, Unit> function4) {
        this.context = context;
        this.epgChannels = list;
        this.selected_pos = i;
        this.clickFunctionListener = function4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, EPGChannel ePGChannel, View view) {
        int i2 = this.selected_pos;
        this.selected_pos = i;
        notifyItemChanged(i2);
        notifyItemChanged(this.selected_pos);
        this.clickFunctionListener.invoke(ePGChannel, Integer.valueOf(i), Boolean.TRUE, Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(LiveChannelViewHolder liveChannelViewHolder, EPGChannel ePGChannel, int i, View view, boolean z) {
        if (!z) {
            liveChannelViewHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
            return;
        }
        liveChannelViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
        Function4<EPGChannel, Integer, Boolean, Boolean, Unit> function4 = this.clickFunctionListener;
        Integer numValueOf = Integer.valueOf(i);
        Boolean bool = Boolean.FALSE;
        function4.invoke(ePGChannel, numValueOf, bool, bool);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onBindViewHolder$2(EPGChannel ePGChannel, int i, View view) {
        this.clickFunctionListener.invoke(ePGChannel, Integer.valueOf(i), Boolean.FALSE, Boolean.TRUE);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<EPGChannel> list = this.epgChannels;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setSelectedPosition(int i) {
        this.selected_pos = i;
        notifyDataSetChanged();
    }

    public void updateData(List<EPGChannel> list, int i) {
        this.epgChannels = list;
        this.selected_pos = i;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull LiveChannelViewHolder liveChannelViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        EPGChannel ePGChannel = this.epgChannels.get(i);
        liveChannelViewHolder.txt_name.setText(ePGChannel.getName());
        liveChannelViewHolder.txt_num.setText(String.valueOf(ePGChannel.getNum()));
        if (ePGChannel.is_favorite()) {
            liveChannelViewHolder.image_fav.setVisibility(0);
        } else {
            liveChannelViewHolder.image_fav.setVisibility(8);
        }
        if (ePGChannel.is_locked()) {
            liveChannelViewHolder.image_lock.setVisibility(0);
        } else {
            liveChannelViewHolder.image_lock.setVisibility(8);
        }
        if (ePGChannel.getTv_archive().equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
            liveChannelViewHolder.image_catch.setVisibility(0);
        } else {
            liveChannelViewHolder.image_catch.setVisibility(8);
        }
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, liveChannelViewHolder.image_channel, ePGChannel.getStream_icon(), R.drawable.tv_icon, liveChannelViewHolder.image_logo);
        liveChannelViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, ePGChannel, 12));
        liveChannelViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, liveChannelViewHolder, ePGChannel, i, 6));
        liveChannelViewHolder.itemView.setOnLongClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda2(this, ePGChannel, i, 2));
        if (this.selected_pos == i) {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.yellow, liveChannelViewHolder.txt_name);
        } else {
            MainActivity$$ExternalSyntheticOutline0.m(this.context, R.color.white, liveChannelViewHolder.txt_name);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public LiveChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LiveChannelViewHolder(MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_live_channel, viewGroup, false));
    }
}
