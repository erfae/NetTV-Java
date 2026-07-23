package com.nettv.livestore.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SeasonRecyclerAdapter$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecyclerView.Adapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SeasonRecyclerAdapter$$ExternalSyntheticLambda0(RecyclerView.Adapter adapter, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = adapter;
        this.f$1 = i;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((SeasonRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, view);
                break;
            case 1:
                ((ConnectPlaylistAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, view);
                break;
            case 2:
                ((HideCategoryRecyclerViewAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, view);
                break;
            case 3:
                ((LanguageRecyclerViewAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, view);
                break;
            case 4:
                ((LiveSortRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, view);
                break;
            case 5:
                ((MyGroupRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, view);
                break;
            case 6:
                ((SettingRecyclerAdapter) this.f$0).lambda$onBindViewHolder$1(this.f$1, view);
                break;
            default:
                ((SubtitleColorRecyclerAdapter) this.f$0).lambda$onBindViewHolder$0(this.f$1, view);
                break;
        }
    }
}
