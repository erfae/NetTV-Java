package com.android.volley.toolbox;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class StringRequest extends Request<String> {

    @Nullable
    @GuardedBy("mLock")
    private Response.Listener<String> mListener;
    private final Object mLock;

    public StringRequest(int i, String str, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(i, str, errorListener);
        this.mLock = new Object();
        this.mListener = listener;
    }

    @Override // com.android.volley.Request
    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    @Override // com.android.volley.Request
    public final void deliverResponse(String str) {
        Response.Listener<String> listener;
        String str2 = str;
        synchronized (this.mLock) {
            listener = this.mListener;
        }
        if (listener != null) {
            listener.onResponse(str2);
        }
    }

    @Override // com.android.volley.Request
    public final Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        String str;
        try {
            str = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException unused) {
            str = new String(networkResponse.data);
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    public StringRequest(String str, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        this(0, str, listener, errorListener);
    }
}
