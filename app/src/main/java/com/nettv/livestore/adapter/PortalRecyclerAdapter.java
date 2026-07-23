package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.WordModels;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class PortalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<AppInfoModel.UrlModel> channels;
    public Function3<AppInfoModel.UrlModel, Integer, Boolean, Unit> clickeListenerFunction;
    public int playlist_position;
    public WordModels wordModels;

    public class AddViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name;

        public AddViewHolder(@NonNull PortalRecyclerAdapter portalRecyclerAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public class PlaylistHolder extends RecyclerView.ViewHolder {
        public TextView txt_connected;
        public TextView txt_name;
        public TextView txt_url;

        public PlaylistHolder(@NonNull PortalRecyclerAdapter portalRecyclerAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_url = (TextView) view.findViewById(R.id.txt_url);
            this.txt_connected = (TextView) view.findViewById(R.id.txt_connected);
        }
    }

    public PortalRecyclerAdapter(List<AppInfoModel.UrlModel> list, Context context, int i, Function3<AppInfoModel.UrlModel, Integer, Boolean, Unit> function3) {
        this.playlist_position = -1;
        this.wordModels = new WordModels();
        this.channels = new ArrayList(list);
        this.clickeListenerFunction = function3;
        this.playlist_position = i;
        this.wordModels = GetSharedInfo.getWordModel(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        this.clickeListenerFunction.invoke(null, Integer.valueOf(i), Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, RecyclerView.ViewHolder viewHolder, View view, boolean z) {
        if (!z) {
            viewHolder.itemView.setScaleX(0.9f);
            viewHolder.itemView.setScaleY(0.9f);
        } else {
            this.clickeListenerFunction.invoke(null, Integer.valueOf(i), Boolean.FALSE);
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(int i, View view) {
        this.clickeListenerFunction.invoke(this.channels.get(i), Integer.valueOf(i), Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(int i, RecyclerView.ViewHolder viewHolder, View view, boolean z) {
        if (!z) {
            viewHolder.itemView.setScaleX(0.9f);
            viewHolder.itemView.setScaleY(0.9f);
        } else {
            this.clickeListenerFunction.invoke(null, Integer.valueOf(i), Boolean.FALSE);
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<AppInfoModel.UrlModel> list = this.channels;
        if (list == null) {
            return 0;
        }
        return list.size() + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i == this.channels.size() ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = viewHolder.getItemViewType();
        if (itemViewType == 0) {
            return;
        }
        final int i2 = 1;
        if (itemViewType != 1) {
            return;
        }
        PlaylistHolder playlistHolder = (PlaylistHolder) viewHolder;
        if (this.channels.get(i).getIs_protected().equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
            playlistHolder.txt_url.setText("Protected");
        } else {
            TextView textView = playlistHolder.txt_url;
            this.channels.get(i).getUrl();
            textView.setText("https://*****.***");
        }
        playlistHolder.txt_name.setText(this.channels.get(i).getName());
        if (this.playlist_position == i) {
            playlistHolder.txt_connected.setVisibility(0);
        } else {
            playlistHolder.txt_connected.setVisibility(8);
        }
        playlistHolder.itemView.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.adapter.PortalRecyclerAdapter$$ExternalSyntheticLambda0
            public final /* synthetic */ PortalRecyclerAdapter f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$onBindViewHolder$0(i, view);
                        break;
                    default:
                        this.f$0.lambda$onBindViewHolder$2(i, view);
                        break;
                }
            }
        });
        playlistHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener(this) { // from class: com.nettv.livestore.adapter.PortalRecyclerAdapter$$ExternalSyntheticLambda1
            public final /* synthetic */ PortalRecyclerAdapter f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$onBindViewHolder$1(i, viewHolder, view, z);
                        break;
                    default:
                        this.f$0.lambda$onBindViewHolder$3(i, viewHolder, view, z);
                        break;
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return i == 0 ? new AddViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.portal_item_add, viewGroup, false)) : new PlaylistHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.portal_item, viewGroup, false));
    }

    public void setData(List<AppInfoModel.UrlModel> list) {
        this.channels = list;
        notifyDataSetChanged();
    }
}
