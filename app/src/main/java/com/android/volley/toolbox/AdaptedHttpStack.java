package com.android.volley.toolbox;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;

/* JADX INFO: loaded from: classes.dex */
class AdaptedHttpStack extends BaseHttpStack {
    private final HttpStack mHttpStack;

    public AdaptedHttpStack(HttpStack httpStack) {
        this.mHttpStack = httpStack;
    }

    @Override // com.android.volley.toolbox.BaseHttpStack
    public HttpResponse executeRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        try {
            org.apache.http.HttpResponse httpResponsePerformRequest = this.mHttpStack.performRequest(request, map);
            int statusCode = httpResponsePerformRequest.getStatusLine().getStatusCode();
            Header[] allHeaders = httpResponsePerformRequest.getAllHeaders();
            ArrayList arrayList = new ArrayList(allHeaders.length);
            for (Header header : allHeaders) {
                arrayList.add(new com.android.volley.Header(header.getName(), header.getValue()));
            }
            if (httpResponsePerformRequest.getEntity() == null) {
                return new HttpResponse(statusCode, arrayList);
            }
            long contentLength = httpResponsePerformRequest.getEntity().getContentLength();
            if (((int) contentLength) == contentLength) {
                return new HttpResponse(statusCode, arrayList, (int) httpResponsePerformRequest.getEntity().getContentLength(), httpResponsePerformRequest.getEntity().getContent());
            }
            throw new IOException(Insets$$ExternalSyntheticOutline0.m("Response too large: ", contentLength));
        } catch (ConnectTimeoutException e) {
            throw new SocketTimeoutException(e.getMessage());
        }
    }
}
