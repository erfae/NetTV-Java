package com.nettv.livestore.remote;

import android.content.Context;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.net.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import org.androidannotations.api.rest.MediaType;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class GetSubtitleLinkRequest {
    public Context context;
    public OnGetLinkModelListener listener;
    public int requestCode;

    public interface OnGetLinkModelListener {
        void OnGetLinkModelResult(JSONObject jSONObject, int i);
    }

    public GetSubtitleLinkRequest(Context context, int i) {
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

    public void getResponse(JSONObject jSONObject, String str, final String str2, final String str3) {
        try {
            Volley.newRequestQueue(this.context).add(new JsonObjectRequest(str, jSONObject, new GetSubtitleLinkRequest$$ExternalSyntheticLambda0(this), new GetSubtitleLinkRequest$$ExternalSyntheticLambda0(this)) { // from class: com.nettv.livestore.remote.GetSubtitleLinkRequest.1
                @Override // com.android.volley.Request
                public Map<String, String> getHeaders() {
                    HashMap map = new HashMap();
                    map.put("Api-Key", str2);
                    map.put(HttpHeaders.AUTHORIZATION, "Bearer " + str3);
                    map.put("Content-Type", MediaType.APPLICATION_JSON);
                    map.put(HttpHeaders.ACCEPT, MediaType.ALL);
                    map.put(HttpHeaders.USER_AGENT, "PostmanRuntime/7.33.0");
                    return map;
                }
            });
        } catch (Exception unused) {
            this.listener.OnGetLinkModelResult(null, this.requestCode);
        }
    }

    public void setOnGetLinkModelListener(OnGetLinkModelListener onGetLinkModelListener) {
        this.listener = onGetLinkModelListener;
    }
}
