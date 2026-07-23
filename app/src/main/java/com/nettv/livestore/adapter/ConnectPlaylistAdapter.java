package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.SideMenu;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectPlaylistAdapter extends RecyclerView.Adapter<ConnectListViewHolder> {
    public Function3<SideMenu, Integer, Boolean, Unit> clickListenerFunction;
    public List<SideMenu> data;

    public class ConnectListViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name;

        public ConnectListViewHolder(@NonNull ConnectPlaylistAdapter connectPlaylistAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public ConnectPlaylistAdapter(Context context, List<SideMenu> list, Function3<SideMenu, Integer, Boolean, Unit> function3) {
        this.data = list;
        this.clickListenerFunction = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        this.clickListenerFunction.invoke(this.data.get(i), Integer.valueOf(i), Boolean.TRUE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ConnectListViewHolder connectListViewHolder, int i) {
        connectListViewHolder.txt_name.setText(this.data.get(i).getName());
        connectListViewHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 1));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ConnectListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConnectListViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_connect, viewGroup, false));
    }
}
