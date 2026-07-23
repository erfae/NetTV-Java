package com.nettv.livestore.remote;

import android.content.Context;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.net.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import org.androidannotations.api.rest.MediaType;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class GetSubtitleLoginRequest {
    public Context context;
    public OnGetLinkModelListener listener;
    public int requestCode;

    public interface OnGetLinkModelListener {
        void OnGetLinkModelResult(JSONObject jSONObject, int i);
    }

    public GetSubtitleLoginRequest(Context context, int i) {
        this.context = context;
        this.requestCode = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getResponse$0(JSONObject jSONObject) {
        try {
            this.listener.OnGetLinkModelResult(jSONObject, this.requestCode);
        } catch (Exception e) {
            this.listener.OnGetLinkModelResult(null, this.requestCode);
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getResponse$1(VolleyError volleyError) {
        this.listener.OnGetLinkModelResult(null, this.requestCode);
    }

    public void getResponse(JSONObject jSONObject, String str, final String str2) {
        RequestQueue requestQueueNewRequestQueue = Volley.newRequestQueue(this.context);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(str, jSONObject, new GetSubtitleLoginRequest$$ExternalSyntheticLambda0(this), new GetSubtitleLoginRequest$$ExternalSyntheticLambda0(this)) { // from class: com.nettv.livestore.remote.GetSubtitleLoginRequest.1
                @Override // com.android.volley.Request
                public Map<String, String> getHeaders() {
                    HashMap map = new HashMap();
                    map.put("Api-Key", str2);
                    map.put("Content-Type", MediaType.APPLICATION_JSON);
                    map.put(HttpHeaders.USER_AGENT, "PostmanRuntime/7.33.0");
                    return map;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH, 1, 1.0f));
            requestQueueNewRequestQueue.add(jsonObjectRequest);
        } catch (Exception unused) {
            this.listener.OnGetLinkModelResult(null, this.requestCode);
        }
    }

    public void setOnGetLinkModelListener(OnGetLinkModelListener onGetLinkModelListener) {
        this.listener = onGetLinkModelListener;
    }
}
