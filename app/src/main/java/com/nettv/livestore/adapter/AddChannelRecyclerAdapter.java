package com.nettv.livestore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.utils.ImageLoaderJava;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class AddChannelRecyclerAdapter extends RecyclerView.Adapter<ChannelHolder> {
    public boolean[] checks;
    private Function3<EPGChannel, Integer, Boolean, Unit> clickListenerFunction;
    public Context context;
    public List<EPGChannel> epgChannels;

    public class ChannelHolder extends RecyclerView.ViewHolder {
        public ImageView image_channel;
        public ImageView image_check;
        public ImageView image_logo;
        public ImageView image_round;
        public TextView txt_name;

        public ChannelHolder(@Nullable AddChannelRecyclerAdapter addChannelRecyclerAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.image_channel = (ImageView) view.findViewById(R.id.image_channel);
            this.image_check = (ImageView) view.findViewById(R.id.image_check);
            this.image_round = (ImageView) view.findViewById(R.id.image_round);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
        }
    }

    public AddChannelRecyclerAdapter(Context context, List<EPGChannel> list, boolean[] zArr, Function3<EPGChannel, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.epgChannels = list;
        this.clickListenerFunction = function3;
        this.checks = zArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(ChannelHolder channelHolder, int i, View view, boolean z) {
        setBackground(channelHolder, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, EPGChannel ePGChannel, View view) {
        toggleChecked(i);
        this.clickListenerFunction.invoke(ePGChannel, Integer.valueOf(i), Boolean.valueOf(this.checks[i]));
    }

    private void setBackground(ChannelHolder channelHolder, int i, boolean z) {
        if (i >= getItemCount()) {
            return;
        }
        if (z) {
            channelHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
            channelHolder.txt_name.setSelected(true);
        } else {
            channelHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
            channelHolder.txt_name.setSelected(false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<EPGChannel> list = this.epgChannels;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setChannelData(List<EPGChannel> list, boolean[] zArr) {
        this.epgChannels = new ArrayList(list);
        this.checks = zArr;
        notifyDataSetChanged();
    }

    public void setFocusDisable(int i, boolean z) {
        notifyItemChanged(i);
    }

    public void setSelectedItem(int i) {
        notifyItemChanged(i);
    }

    public void toggleChecked(int i) {
        boolean[] zArr = this.checks;
        zArr[i] = !zArr[i];
        notifyItemChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull ChannelHolder channelHolder, @SuppressLint({"RecyclerView"}) int i) {
        EPGChannel ePGChannel = this.epgChannels.get(i);
        channelHolder.image_round.setVisibility(0);
        if (this.checks[i]) {
            channelHolder.image_check.setVisibility(8);
        } else {
            channelHolder.image_check.setVisibility(0);
        }
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, channelHolder.image_channel, ePGChannel.getStream_icon(), R.drawable.icon_live, channelHolder.image_logo);
        channelHolder.txt_name.setText(ePGChannel.getName());
        int i2 = 2;
        channelHolder.itemView.setOnFocusChangeListener(new VodRecyclerAdapter$$ExternalSyntheticLambda1(this, channelHolder, i, i2));
        setBackground(channelHolder, i, channelHolder.itemView.isFocused());
        channelHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, i, ePGChannel, i2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ChannelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChannelHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_add_channel, viewGroup, false));
    }
}
