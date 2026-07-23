package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class HttpClientStack implements HttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    public final HttpClient mClient;

    public static final class HttpPatch extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "PATCH";

        public HttpPatch() {
        }

        @Override // org.apache.http.client.methods.HttpRequestBase, org.apache.http.client.methods.HttpUriRequest
        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpPatch(URI uri) {
            setURI(uri);
        }

        public HttpPatch(String str) {
            setURI(URI.create(str));
        }
    }

    public HttpClientStack(HttpClient httpClient) {
        this.mClient = httpClient;
    }

    private static List<NameValuePair> getPostParameterPairs(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (String str : map.keySet()) {
            arrayList.add(new BasicNameValuePair(str, map.get(str)));
        }
        return arrayList;
    }

    private static void setEntityIfNonEmptyBody(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, Request<?> request) throws AuthFailureError {
        byte[] body = request.getBody();
        if (body != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(body));
        }
    }

    private static void setHeaders(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String str : map.keySet()) {
            httpUriRequest.setHeader(str, map.get(str));
        }
    }

    @Override // com.android.volley.toolbox.HttpStack
    public org.apache.http.HttpResponse performRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        HttpUriRequest httpGet;
        switch (request.getMethod()) {
            case -1:
                byte[] postBody = request.getPostBody();
                if (postBody == null) {
                    httpGet = new HttpGet(request.getUrl());
                } else {
                    HttpPost httpPost = new HttpPost(request.getUrl());
                    httpPost.addHeader("Content-Type", request.getPostBodyContentType());
                    httpPost.setEntity(new ByteArrayEntity(postBody));
                    httpGet = httpPost;
                }
                break;
            case 0:
                httpGet = new HttpGet(request.getUrl());
                break;
            case 1:
                HttpPost httpPost2 = new HttpPost(request.getUrl());
                httpPost2.addHeader("Content-Type", request.getBodyContentType());
                setEntityIfNonEmptyBody(httpPost2, request);
                httpGet = httpPost2;
                break;
            case 2:
                HttpPut httpPut = new HttpPut(request.getUrl());
                httpPut.addHeader("Content-Type", request.getBodyContentType());
                setEntityIfNonEmptyBody(httpPut, request);
                httpGet = httpPut;
                break;
            case 3:
                httpGet = new HttpDelete(request.getUrl());
                break;
            case 4:
                httpGet = new HttpHead(request.getUrl());
                break;
            case 5:
                httpGet = new HttpOptions(request.getUrl());
                break;
            case 6:
                httpGet = new HttpTrace(request.getUrl());
                break;
            case 7:
                HttpPatch httpPatch = new HttpPatch(request.getUrl());
                httpPatch.addHeader("Content-Type", request.getBodyContentType());
                setEntityIfNonEmptyBody(httpPatch, request);
                httpGet = httpPatch;
                break;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        setHeaders(httpGet, map);
        setHeaders(httpGet, request.getHeaders());
        HttpParams params = httpGet.getParams();
        int timeoutMs = request.getTimeoutMs();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, timeoutMs);
        return this.mClient.execute(httpGet);
    }
}
