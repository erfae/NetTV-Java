package com.android.volley.toolbox;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.ClientError;
import com.android.volley.Header;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.android.exoplayer2.C;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
final class NetworkUtility {
    private static final int SLOW_REQUEST_THRESHOLD_MS = 3000;

    public static class RetryInfo {
        private final VolleyError errorToRetry;
        private final String logPrefix;

        private RetryInfo(String str, VolleyError volleyError) {
            this.logPrefix = str;
            this.errorToRetry = volleyError;
        }
    }

    private NetworkUtility() {
    }

    public static void attemptRetryOnException(Request<?> request, RetryInfo retryInfo) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int timeoutMs = request.getTimeoutMs();
        try {
            retryPolicy.retry(retryInfo.errorToRetry);
            request.addMarker(String.format("%s-retry [timeout=%s]", retryInfo.logPrefix, Integer.valueOf(timeoutMs)));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", retryInfo.logPrefix, Integer.valueOf(timeoutMs)));
            throw e;
        }
    }

    public static NetworkResponse getNotModifiedNetworkResponse(Request<?> request, long j, List<Header> list) {
        Cache.Entry cacheEntry = request.getCacheEntry();
        if (cacheEntry == null) {
            return new NetworkResponse(304, (byte[]) null, true, j, list);
        }
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        if (!list.isEmpty()) {
            Iterator<Header> it = list.iterator();
            while (it.hasNext()) {
                treeSet.add(it.next().getName());
            }
        }
        ArrayList arrayList = new ArrayList(list);
        List<Header> list2 = cacheEntry.allResponseHeaders;
        if (list2 != null) {
            if (!list2.isEmpty()) {
                for (Header header : cacheEntry.allResponseHeaders) {
                    if (!treeSet.contains(header.getName())) {
                        arrayList.add(header);
                    }
                }
            }
        } else if (!cacheEntry.responseHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : cacheEntry.responseHeaders.entrySet()) {
                if (!treeSet.contains(entry.getKey())) {
                    arrayList.add(new Header(entry.getKey(), entry.getValue()));
                }
            }
        }
        return new NetworkResponse(304, cacheEntry.data, true, j, (List<Header>) arrayList);
    }

    public static byte[] inputStreamToBytes(InputStream inputStream, int i, ByteArrayPool byteArrayPool) throws Throwable {
        byte[] buf;
        PoolingByteArrayOutputStream poolingByteArrayOutputStream = new PoolingByteArrayOutputStream(byteArrayPool, i);
        try {
            buf = byteArrayPool.getBuf(1024);
            while (true) {
                try {
                    int i2 = inputStream.read(buf);
                    if (i2 == -1) {
                        break;
                    }
                    poolingByteArrayOutputStream.write(buf, 0, i2);
                } catch (Throwable th) {
                    th = th;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused) {
                            VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                        }
                    }
                    byteArrayPool.returnBuf(buf);
                    poolingByteArrayOutputStream.close();
                    throw th;
                }
            }
            byte[] byteArray = poolingByteArrayOutputStream.toByteArray();
            try {
                inputStream.close();
            } catch (IOException unused2) {
                VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
            }
            byteArrayPool.returnBuf(buf);
            poolingByteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
            buf = null;
        }
    }

    public static void logSlowRequests(long j, Request<?> request, byte[] bArr, int i) {
        if (VolleyLog.DEBUG || j > C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS) {
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(j);
            objArr[2] = bArr != null ? Integer.valueOf(bArr.length) : "null";
            objArr[3] = Integer.valueOf(i);
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", objArr);
        }
    }

    public static RetryInfo shouldRetryException(Request<?> request, IOException iOException, long j, @Nullable HttpResponse httpResponse, @Nullable byte[] bArr) throws VolleyError {
        if (iOException instanceof SocketTimeoutException) {
            return new RetryInfo("socket", new TimeoutError());
        }
        if (iOException instanceof MalformedURLException) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Bad URL ");
            sbM.append(request.getUrl());
            throw new RuntimeException(sbM.toString(), iOException);
        }
        if (httpResponse == null) {
            if (request.shouldRetryConnectionErrors()) {
                return new RetryInfo("connection", new NoConnectionError());
            }
            throw new NoConnectionError(iOException);
        }
        int statusCode = httpResponse.getStatusCode();
        VolleyLog.e("Unexpected response code %d for %s", Integer.valueOf(statusCode), request.getUrl());
        if (bArr == null) {
            return new RetryInfo("network", new NetworkError());
        }
        NetworkResponse networkResponse = new NetworkResponse(statusCode, bArr, false, SystemClock.elapsedRealtime() - j, httpResponse.getHeaders());
        if (statusCode == 401 || statusCode == 403) {
            return new RetryInfo("auth", new AuthFailureError(networkResponse));
        }
        if (statusCode >= 400 && statusCode <= 499) {
            throw new ClientError(networkResponse);
        }
        if (statusCode < 500 || statusCode > 599 || !request.shouldRetryServerErrors()) {
            throw new ServerError(networkResponse);
        }
        return new RetryInfo("server", new ServerError(networkResponse));
    }
}
