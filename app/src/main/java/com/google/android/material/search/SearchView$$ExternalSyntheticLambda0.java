package com.google.android.material.search;

import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchView f$0;

    public /* synthetic */ SearchView$$ExternalSyntheticLambda0(SearchView searchView, int i) {
        this.$r8$classId = i;
        this.f$0 = searchView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$setUpBackButton$1(view);
                break;
            case 1:
                this.f$0.lambda$setupWithSearchBar$7(view);
                break;
            default:
                this.f$0.lambda$setUpClearButton$2(view);
                break;
        }
    }
}
