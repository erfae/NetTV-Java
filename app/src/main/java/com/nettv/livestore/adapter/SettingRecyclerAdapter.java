package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.SideMenu;
import java.util.List;
import javax.annotation.Nullable;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class SettingRecyclerAdapter extends RecyclerView.Adapter<SettingHolder> {
    public Function3<SideMenu, Integer, Boolean, Unit> clickFunctionListener;
    public List<SideMenu> menu_list;
    public int update_position;

    public class SettingHolder extends RecyclerView.ViewHolder {
        public ImageView image_setting;
        public ImageView image_update;
        public TextView txt_setting;

        public SettingHolder(@Nullable SettingRecyclerAdapter settingRecyclerAdapter, View view) {
            super(view);
            this.image_setting = (ImageView) view.findViewById(R.id.image_setting);
            this.txt_setting = (TextView) view.findViewById(R.id.txt_setting);
            this.image_update = (ImageView) view.findViewById(R.id.image_update);
        }
    }

    public SettingRecyclerAdapter(Context context, List<SideMenu> list, int i, Function3<SideMenu, Integer, Boolean, Unit> function3) {
        this.menu_list = list;
        this.clickFunctionListener = function3;
        this.update_position = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view, boolean z) {
        if (z) {
            this.clickFunctionListener.invoke(this.menu_list.get(i), Integer.valueOf(i), Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, View view) {
        this.clickFunctionListener.invoke(this.menu_list.get(i), Integer.valueOf(i), Boolean.TRUE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.menu_list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setSettingData(List<SideMenu> list) {
        this.menu_list = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull SettingHolder settingHolder, final int i) {
        settingHolder.image_setting.setImageResource(this.menu_list.get(i).getImage_url());
        settingHolder.txt_setting.setText(this.menu_list.get(i).getName());
        int i2 = this.update_position;
        if (i2 <= 0 || i2 != i) {
            settingHolder.image_update.setVisibility(8);
        } else {
            settingHolder.image_update.setVisibility(0);
        }
        settingHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.nettv.livestore.adapter.SettingRecyclerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                this.f$0.lambda$onBindViewHolder$0(i, view, z);
            }
        });
        settingHolder.itemView.setOnClickListener(new SeasonRecyclerAdapter$$ExternalSyntheticLambda0(this, i, 6));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public SettingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SettingHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_setting, viewGroup, false));
    }
}
