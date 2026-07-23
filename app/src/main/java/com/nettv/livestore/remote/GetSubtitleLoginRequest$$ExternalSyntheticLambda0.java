package com.nettv.livestore.remote;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONObject;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class GetSubtitleLoginRequest$$ExternalSyntheticLambda0 implements Response.Listener, Response.ErrorListener {
    public final /* synthetic */ GetSubtitleLoginRequest f$0;

    public /* synthetic */ GetSubtitleLoginRequest$$ExternalSyntheticLambda0(GetSubtitleLoginRequest getSubtitleLoginRequest) {
        this.f$0 = getSubtitleLoginRequest;
    }

    @Override // com.android.volley.Response.ErrorListener
    public final void onErrorResponse(VolleyError volleyError) {
        this.f$0.lambda$getResponse$1(volleyError);
    }

    @Override // com.android.volley.Response.Listener
    public final void onResponse(Object obj) {
        this.f$0.lambda$getResponse$0((JSONObject) obj);
    }
}
