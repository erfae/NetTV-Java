package com.nettv.livestore.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.models.CastModel;
import com.nettv.livestore.models.CatchUpEpg;
import com.nettv.livestore.models.CatchupModel;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.MovieCreditModel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SeriesModel;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class VodRecyclerAdapter$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecyclerView.Adapter f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ VodRecyclerAdapter$$ExternalSyntheticLambda0(RecyclerView.Adapter adapter, int i, Object obj, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$1 = i;
        this.f$2 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((VodRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, (MovieModel) this.f$2, view);
                break;
            case 1:
                ((AddCategoryRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, (String) this.f$2, view);
                break;
            case 2:
                ((AddChannelRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, (EPGChannel) this.f$2, view);
                break;
            case 3:
                ((CastRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((CastModel) this.f$2, this.f$1, view);
                break;
            case 4:
                ((DateRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, (CatchupModel) this.f$2, view);
                break;
            case 5:
                ((EpisodeHorizontalRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((EpisodeModel) this.f$2, this.f$1, view);
                break;
            case 6:
                ((EpisodeRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((EpisodeModel) this.f$2, this.f$1, view);
                break;
            case 7:
                ((MovieCreditRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, (MovieCreditModel) this.f$2, view);
                break;
            case 8:
                ((MyChannelRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((String) this.f$2, this.f$1, view);
                break;
            case 9:
                ((PlayEpisodeRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0((EpisodeModel) this.f$2, this.f$1, view);
                break;
            case 10:
                ((ProgramRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1((CatchUpEpg) this.f$2, this.f$1, view);
                break;
            case 11:
                ((RecyclerLiveCategoryAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, (CategoryModel) this.f$2, view);
                break;
            case 12:
                ((RecyclerLiveChannelAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, (EPGChannel) this.f$2, view);
                break;
            case 13:
                ((RecyclerLiveHomeAdapter) this.f$0).lambda$onBindViewHolder$1((EPGChannel) this.f$2, this.f$1, view);
                break;
            case 14:
                ((RecyclerSeriesHomeAdapter) this.f$0).lambda$onBindViewHolder$1((SeriesModel) this.f$2, this.f$1, view);
                break;
            case 15:
                ((RecyclerVodCategoryAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, (CategoryModel) this.f$2, view);
                break;
            case 16:
                ((RecyclerVodHomeAdapter) this.f$0).lambda$onBindViewHolder$1((MovieModel) this.f$2, this.f$1, view);
                break;
            default:
                ((SeriesRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, (SeriesModel) this.f$2, view);
                break;
        }
    }

    public /* synthetic */ VodRecyclerAdapter$$ExternalSyntheticLambda0(RecyclerView.Adapter adapter, Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$2 = obj;
        this.f$1 = i;
    }
}
