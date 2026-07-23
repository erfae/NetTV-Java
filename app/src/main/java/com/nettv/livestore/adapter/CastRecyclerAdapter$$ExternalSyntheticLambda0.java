package com.nettv.livestore.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.models.CastModel;
import com.nettv.livestore.models.CatchUpEpg;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.MovieCreditModel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SeriesModel;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class CastRecyclerAdapter$$ExternalSyntheticLambda0 implements View.OnFocusChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecyclerView.Adapter f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ CastRecyclerAdapter$$ExternalSyntheticLambda0(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$1 = viewHolder;
        this.f$3 = obj;
        this.f$2 = i;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        switch (this.$r8$classId) {
            case 0:
                ((CastRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((CastModel) this.f$1, this.f$2, (CastRecyclerAdapter.VodStalkerHolder) this.f$3, view, z);
                break;
            case 1:
                ((EpisodeHorizontalRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((EpisodeModel) this.f$1, this.f$2, (EpisodeHorizontalRecyclerAdapter.EpisodeViewHolder) this.f$3, view, z);
                break;
            case 2:
                ((EpisodeRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((EpisodeModel) this.f$1, this.f$2, (EpisodeRecyclerAdapter.XCEpisodeViewHolder) this.f$3, view, z);
                break;
            case 3:
                ((MovieCreditRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((MovieCreditRecyclerAdapter.VodViewHolder) this.f$1, (MovieCreditModel) this.f$3, this.f$2, view, z);
                break;
            case 4:
                ((ProgramRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((CatchUpEpg) this.f$1, this.f$2, (ProgramRecyclerAdapter.XCProgramViewHolder) this.f$3, view, z);
                break;
            case 5:
                ((RecyclerLiveCategoryAdapter) this.f$0).lambda$onBindViewHolder$0((RecyclerLiveCategoryAdapter.LiveHomeViewHolder) this.f$1, (CategoryModel) this.f$3, this.f$2, view, z);
                break;
            case 6:
                ((RecyclerLiveChannelAdapter) this.f$0).lambda$onBindViewHolder$1((RecyclerLiveChannelAdapter.LiveChannelViewHolder) this.f$1, (EPGChannel) this.f$3, this.f$2, view, z);
                break;
            case 7:
                ((RecyclerLiveHomeAdapter) this.f$0).lambda$onBindViewHolder$0((EPGChannel) this.f$1, this.f$2, (RecyclerLiveHomeAdapter.LiveHomeViewHolder) this.f$3, view, z);
                break;
            case 8:
                ((RecyclerSeriesHomeAdapter) this.f$0).lambda$onBindViewHolder$0((SeriesModel) this.f$1, this.f$2, (RecyclerSeriesHomeAdapter.LiveHomeViewHolder) this.f$3, view, z);
                break;
            case 9:
                ((RecyclerVodCategoryAdapter) this.f$0).lambda$onBindViewHolder$0((CategoryModel) this.f$1, this.f$2, (RecyclerVodCategoryAdapter.LiveHomeViewHolder) this.f$3, view, z);
                break;
            default:
                ((RecyclerVodHomeAdapter) this.f$0).lambda$onBindViewHolder$0((MovieModel) this.f$1, this.f$2, (RecyclerVodHomeAdapter.LiveHomeViewHolder) this.f$3, view, z);
                break;
        }
    }

    public /* synthetic */ CastRecyclerAdapter$$ExternalSyntheticLambda0(RecyclerView.Adapter adapter, Object obj, int i, RecyclerView.ViewHolder viewHolder, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$1 = obj;
        this.f$2 = i;
        this.f$3 = viewHolder;
    }
}
