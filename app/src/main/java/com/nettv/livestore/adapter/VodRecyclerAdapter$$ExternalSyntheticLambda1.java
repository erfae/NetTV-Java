package com.nettv.livestore.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class VodRecyclerAdapter$$ExternalSyntheticLambda1 implements View.OnFocusChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecyclerView.Adapter f$0;
    public final /* synthetic */ RecyclerView.ViewHolder f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ VodRecyclerAdapter$$ExternalSyntheticLambda1(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$1 = viewHolder;
        this.f$2 = i;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        switch (this.$r8$classId) {
            case 0:
                ((VodRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((VodRecyclerAdapter.VodViewHolder) this.f$1, this.f$2, view, z);
                break;
            case 1:
                ((AddCategoryRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((AddCategoryRecyclerAdapter.CategoryHolder) this.f$1, this.f$2, view, z);
                break;
            case 2:
                ((AddChannelRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((AddChannelRecyclerAdapter.ChannelHolder) this.f$1, this.f$2, view, z);
                break;
            case 3:
                ((DateRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((DateRecyclerAdapter.XCDateViewHolder) this.f$1, this.f$2, view, z);
                break;
            case 4:
                ((MyChannelRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((MyChannelRecyclerAdapter.ChannelHolder) this.f$1, this.f$2, view, z);
                break;
            case 5:
                ((SeasonRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((SeasonRecyclerAdapter.XCSeasonViewHolder) this.f$1, this.f$2, view, z);
                break;
            default:
                ((SeriesRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((SeriesRecyclerAdapter.VodViewHolder) this.f$1, this.f$2, view, z);
                break;
        }
    }
}
