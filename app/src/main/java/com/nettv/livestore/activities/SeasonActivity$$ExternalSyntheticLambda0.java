package com.nettv.livestore.activities;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nettv.livestore.helper.RealmChangeItemListener;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SeasonActivity$$ExternalSyntheticLambda0 implements Response.Listener, Response.ErrorListener, RealmChangeItemListener {
    public final /* synthetic */ SeasonActivity f$0;

    public /* synthetic */ SeasonActivity$$ExternalSyntheticLambda0(SeasonActivity seasonActivity) {
        this.f$0 = seasonActivity;
    }

    @Override // com.android.volley.Response.ErrorListener
    public final void onErrorResponse(VolleyError volleyError) {
        this.f$0.lambda$getSomeSeriesInfo$4(volleyError);
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        this.f$0.lambda$onCreate$1();
    }

    @Override // com.android.volley.Response.Listener
    public final void onResponse(Object obj) {
        this.f$0.lambda$getSomeSeriesInfo$3((String) obj);
    }
}
