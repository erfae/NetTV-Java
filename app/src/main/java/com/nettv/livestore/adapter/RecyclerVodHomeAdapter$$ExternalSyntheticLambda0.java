package com.nettv.livestore.adapter;

import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SeriesModel;
import io.realm.RealmObject;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RecyclerVodHomeAdapter$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecyclerView.Adapter f$0;
    public final /* synthetic */ RecyclerView.ViewHolder f$1;
    public final /* synthetic */ RealmObject f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ RecyclerVodHomeAdapter$$ExternalSyntheticLambda0(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, RealmObject realmObject, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$1 = viewHolder;
        this.f$2 = realmObject;
        this.f$3 = i;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        switch (this.$r8$classId) {
            case 0:
                return ((RecyclerVodHomeAdapter) this.f$0).lambda$onBindViewHolder$2((RecyclerVodHomeAdapter.LiveHomeViewHolder) this.f$1, (MovieModel) this.f$2, this.f$3, view, motionEvent);
            case 1:
                return ((RecyclerLiveHomeAdapter) this.f$0).lambda$onBindViewHolder$2((RecyclerLiveHomeAdapter.LiveHomeViewHolder) this.f$1, (EPGChannel) this.f$2, this.f$3, view, motionEvent);
            default:
                return ((RecyclerSeriesHomeAdapter) this.f$0).lambda$onBindViewHolder$2((RecyclerSeriesHomeAdapter.LiveHomeViewHolder) this.f$1, (SeriesModel) this.f$2, this.f$3, view, motionEvent);
        }
    }
}
