package com.nettv.livestore.remote;

import android.content.Context;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.net.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class GetSubtitleDataRequest {
    public Context context;
    public OnGetResponseListener listener;
    public int requestCode;

    public interface OnGetResponseListener {
        void OnGetResponseResult(JSONObject jSONObject, int i);
    }

    public GetSubtitleDataRequest(Context context, int i) {
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

    public void getResponse(String str, final String str2) {
        Volley.newRequestQueue(this.context);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(str, new GetSubtitleDataRequest$$ExternalSyntheticLambda0(this), new GetSubtitleDataRequest$$ExternalSyntheticLambda0(this)) { // from class: com.nettv.livestore.remote.GetSubtitleDataRequest.1
                @Override // com.android.volley.Request
                public Map<String, String> getHeaders() {
                    HashMap map = new HashMap();
                    map.put("Api-Key", str2);
                    map.put(HttpHeaders.USER_AGENT, "PostmanRuntime/7.33.0");
                    return map;
                }
            };
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
