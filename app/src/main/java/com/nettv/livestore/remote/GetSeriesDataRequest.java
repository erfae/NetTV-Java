package com.nettv.livestore.remote;

import android.content.Context;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class GetSeriesDataRequest {
    public Context context;
    public OnGetResponseListener listener;
    public int requestCode;

    public interface OnGetResponseListener {
        void OnGetResponseResult(JSONObject jSONObject, int i);
    }

    public GetSeriesDataRequest(Context context, int i) {
        this.context = context;
        this.requestCode = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getResponse$0(JSONObject jSONObject) {
        try {
            this.listener.OnGetResponseResult(jSONObject, this.requestCode);
        } catch (Exception e) {
            this.listener.OnGetResponseResult(null, this.requestCode);
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getResponse$1(VolleyError volleyError) {
        this.listener.OnGetResponseResult(null, this.requestCode);
    }

    public void getResponse(String str) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, str, null, new GetSeriesDataRequest$$ExternalSyntheticLambda0(this), new GetSeriesDataRequest$$ExternalSyntheticLambda0(this));
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH, 1, 1.0f));
            VolleySingleton.getInstance(this.context).addToRequestQueue(jsonObjectRequest);
        } catch (Exception unused) {
            this.listener.OnGetResponseResult(null, this.requestCode);
        }
    }

    public void setOnGetResponseListener(OnGetResponseListener onGetResponseListener) {
        this.listener = onGetResponseListener;
    }
}
