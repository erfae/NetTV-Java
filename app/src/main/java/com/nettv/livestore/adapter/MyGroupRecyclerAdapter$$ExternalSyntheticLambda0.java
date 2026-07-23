package com.nettv.livestore.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MyGroupRecyclerAdapter$$ExternalSyntheticLambda0 implements View.OnFocusChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecyclerView.ViewHolder f$0;

    public /* synthetic */ MyGroupRecyclerAdapter$$ExternalSyntheticLambda0(RecyclerView.ViewHolder viewHolder, int i) {
        this.$r8$classId = i;
        this.f$0 = viewHolder;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        switch (this.$r8$classId) {
            case 0:
                MyGroupRecyclerAdapter.lambda$onBindViewHolder$0((MyGroupRecyclerAdapter.CategoryListViewHolder) this.f$0, view, z);
                break;
            case 1:
                HideCategoryRecyclerViewAdapter.lambda$onBindViewHolder$1((HideCategoryRecyclerViewAdapter.HideCategoryViewHolder) this.f$0, view, z);
                break;
            case 2:
                LanguageRecyclerViewAdapter.lambda$onBindViewHolder$1((LanguageRecyclerViewAdapter.HideCategoryViewHolder) this.f$0, view, z);
                break;
            case 3:
                LiveSortRecyclerAdapter.lambda$onBindViewHolder$1((LiveSortRecyclerAdapter.HideCategoryViewHolder) this.f$0, view, z);
                break;
            default:
                SubtitleColorRecyclerAdapter.lambda$onBindViewHolder$1((SubtitleColorRecyclerAdapter.SubtitleColorViewHolder) this.f$0, view, z);
                break;
        }
    }
}
