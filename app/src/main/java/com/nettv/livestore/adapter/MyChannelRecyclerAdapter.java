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
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.utils.ImageLoaderJava;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class MyChannelRecyclerAdapter extends RecyclerView.Adapter<ChannelHolder> {
    private List<String> channelNames;
    private Function3<String, Integer, Boolean, Unit> clickListenerFunction;
    public Context context;
    public boolean is_parent;

    public class ChannelHolder extends RecyclerView.ViewHolder {
        public ImageView image_channel;
        public ImageView image_delete;
        public ImageView image_logo;
        public TextView txt_name;
        public TextView txt_num;

        public ChannelHolder(@Nullable MyChannelRecyclerAdapter myChannelRecyclerAdapter, View view) {
            super(view);
            this.txt_num = (TextView) view.findViewById(R.id.txt_num);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.image_channel = (ImageView) view.findViewById(R.id.image_channel);
            this.image_delete = (ImageView) view.findViewById(R.id.image_delete);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
        }
    }

    public MyChannelRecyclerAdapter(Context context, List<String> list, boolean z, Function3<String, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.channelNames = list;
        this.is_parent = z;
        this.clickListenerFunction = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(ChannelHolder channelHolder, int i, View view, boolean z) {
        setBackground(channelHolder, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(String str, int i, View view) {
        this.clickListenerFunction.invoke(str, Integer.valueOf(i), Boolean.TRUE);
    }

    private void setBackground(ChannelHolder channelHolder, int i, boolean z) {
        if (i >= getItemCount()) {
            return;
        }
        String str = this.channelNames.get(i);
        channelHolder.txt_num.setVisibility(0);
        channelHolder.txt_num.setText(String.valueOf(i + 1));
        if (!z) {
            channelHolder.itemView.setBackgroundResource(R.color.item_channel_bg);
            channelHolder.txt_name.setSelected(false);
            channelHolder.image_delete.setVisibility(8);
        } else {
            this.clickListenerFunction.invoke(str, Integer.valueOf(i), Boolean.FALSE);
            channelHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
            channelHolder.txt_name.setSelected(true);
            if (this.is_parent) {
                return;
            }
            channelHolder.image_delete.setVisibility(0);
        }
    }

    private void setChannels(List<String> list, boolean z) {
        if (z) {
            this.channelNames.addAll(new ArrayList(list));
        } else {
            this.channelNames = new ArrayList(list);
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<String> list = this.channelNames;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setChannelData(List<String> list, boolean z) {
        setChannels(list, z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull ChannelHolder channelHolder, @SuppressLint({"RecyclerView"}) int i) {
        String str = this.channelNames.get(i);
        EPGChannel epgChannelByName = RealmController.with().getEpgChannelByName(str);
        ImageLoaderJava.imageLoadUrlWithVodHolder(this.context, channelHolder.image_channel, epgChannelByName.getStream_icon(), R.drawable.icon_live, channelHolder.image_logo);
        channelHolder.txt_name.setText(epgChannelByName.getName());
        channelHolder.itemView.setOnFocusChangeListener(new VodRecyclerAdapter$$ExternalSyntheticLambda1(this, channelHolder, i, 4));
        setBackground(channelHolder, i, channelHolder.itemView.isFocused());
        channelHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, str, i, 8));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ChannelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChannelHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_add_channel, viewGroup, false));
    }
}
