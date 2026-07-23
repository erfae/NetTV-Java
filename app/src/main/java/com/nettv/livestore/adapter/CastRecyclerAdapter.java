package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.models.CastModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes2.dex */
public class CastRecyclerAdapter extends RecyclerView.Adapter<VodStalkerHolder> {
    public Function3<CastModel, Integer, Boolean, Unit> clickListenerFunction;
    public Context context;
    private List<CastModel> datas;

    public class VodStalkerHolder extends RecyclerView.ViewHolder {
        public RoundedImageView image_vod;
        public TextView txt_name;

        public VodStalkerHolder(@NonNull CastRecyclerAdapter castRecyclerAdapter, View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.image_vod = (RoundedImageView) view.findViewById(R.id.image_vod);
        }
    }

    public CastRecyclerAdapter(Context context, List<CastModel> list, Function3<CastModel, Integer, Boolean, Unit> function3) {
        this.datas = list;
        this.context = context;
        this.clickListenerFunction = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(CastModel castModel, int i, VodStalkerHolder vodStalkerHolder, View view, boolean z) {
        if (!z) {
            vodStalkerHolder.itemView.setScaleX(0.95f);
            vodStalkerHolder.itemView.setScaleY(0.95f);
            vodStalkerHolder.itemView.setBackgroundResource(R.color.trans_parent);
        } else {
            this.clickListenerFunction.invoke(castModel, Integer.valueOf(i), Boolean.FALSE);
            vodStalkerHolder.itemView.setScaleX(1.0f);
            vodStalkerHolder.itemView.setScaleY(1.0f);
            vodStalkerHolder.itemView.setBackgroundResource(R.drawable.portal_bg_up);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(CastModel castModel, int i, View view) {
        this.clickListenerFunction.invoke(castModel, Integer.valueOf(i), Boolean.TRUE);
    }

    public List<CastModel> getData() {
        return this.datas;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CastModel> list = this.datas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setMovieData(List<CastModel> list) {
        this.datas = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull @NotNull VodStalkerHolder vodStalkerHolder, int i) {
        CastModel castModel = this.datas.get(i);
        if (castModel.getProfile_path() == null || castModel.getProfile_path().equals("")) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.cast_icon)).into(vodStalkerHolder.image_vod);
        } else {
            Glide.with(this.context).load(Constants.IMDB_IMAGE_PREF + castModel.getProfile_path()).placeholder(R.drawable.cast_icon).error(R.drawable.cast_icon).into(vodStalkerHolder.image_vod);
        }
        vodStalkerHolder.txt_name.setText(castModel.getName());
        vodStalkerHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, castModel, i, vodStalkerHolder, 0));
        vodStalkerHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, castModel, i, 3));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    @NotNull
    public VodStalkerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        return new VodStalkerHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_cast, viewGroup, false));
    }
}
