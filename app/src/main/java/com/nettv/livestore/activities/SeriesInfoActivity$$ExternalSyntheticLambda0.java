package com.nettv.livestore.activities;

import com.android.volley.Response;
import com.nettv.livestore.helper.RealmChangeItemListener;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SeriesInfoActivity$$ExternalSyntheticLambda0 implements Response.Listener, RealmChangeItemListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeriesInfoActivity f$0;

    public /* synthetic */ SeriesInfoActivity$$ExternalSyntheticLambda0(SeriesInfoActivity seriesInfoActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = seriesInfoActivity;
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        switch (this.$r8$classId) {
            case 1:
                this.f$0.lambda$onClick$2();
                break;
            default:
                this.f$0.lambda$onClick$3();
                break;
        }
    }

    @Override // com.android.volley.Response.Listener
    public final void onResponse(Object obj) {
        this.f$0.lambda$getSeriesInfo$0((String) obj);
    }
}
