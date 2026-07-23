package com.android.volley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: classes.dex */
class WaitingRequestManager implements Request.NetworkRequestCompleteListener {

    @Nullable
    private final CacheDispatcher mCacheDispatcher;

    @Nullable
    private final BlockingQueue<Request<?>> mNetworkQueue;

    @Nullable
    private final RequestQueue mRequestQueue;
    private final ResponseDelivery mResponseDelivery;
    private final Map<String, List<Request<?>>> mWaitingRequests;

    public WaitingRequestManager(@NonNull RequestQueue requestQueue) {
        this.mWaitingRequests = new HashMap();
        this.mRequestQueue = requestQueue;
        this.mResponseDelivery = requestQueue.getResponseDelivery();
        this.mCacheDispatcher = null;
        this.mNetworkQueue = null;
    }

    public final synchronized boolean maybeAddToWaitingRequests(Request<?> request) {
        String cacheKey = request.getCacheKey();
        if (!this.mWaitingRequests.containsKey(cacheKey)) {
            this.mWaitingRequests.put(cacheKey, null);
            request.setNetworkRequestCompleteListener(this);
            if (VolleyLog.DEBUG) {
                VolleyLog.d("new request, sending to network %s", cacheKey);
            }
            return false;
        }
        List<Request<?>> arrayList = this.mWaitingRequests.get(cacheKey);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        request.addMarker("waiting-for-response");
        arrayList.add(request);
        this.mWaitingRequests.put(cacheKey, arrayList);
        if (VolleyLog.DEBUG) {
            VolleyLog.d("Request for cacheKey=%s is in flight, putting on hold.", cacheKey);
        }
        return true;
    }

    @Override // com.android.volley.Request.NetworkRequestCompleteListener
    public synchronized void onNoUsableResponseReceived(Request<?> request) {
        BlockingQueue<Request<?>> blockingQueue;
        String cacheKey = request.getCacheKey();
        List<Request<?>> listRemove = this.mWaitingRequests.remove(cacheKey);
        if (listRemove != null && !listRemove.isEmpty()) {
            if (VolleyLog.DEBUG) {
                VolleyLog.v("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(listRemove.size()), cacheKey);
            }
            Request<?> requestRemove = listRemove.remove(0);
            this.mWaitingRequests.put(cacheKey, listRemove);
            requestRemove.setNetworkRequestCompleteListener(this);
            RequestQueue requestQueue = this.mRequestQueue;
            if (requestQueue != null) {
                requestQueue.sendRequestOverNetwork(requestRemove);
            } else if (this.mCacheDispatcher != null && (blockingQueue = this.mNetworkQueue) != null) {
                try {
                    blockingQueue.put(requestRemove);
                } catch (InterruptedException e) {
                    VolleyLog.e("Couldn't add request to queue. %s", e.toString());
                    Thread.currentThread().interrupt();
                    this.mCacheDispatcher.quit();
                }
            }
        }
    }

    @Override // com.android.volley.Request.NetworkRequestCompleteListener
    public void onResponseReceived(Request<?> request, Response<?> response) {
        List<Request<?>> listRemove;
        Cache.Entry entry = response.cacheEntry;
        if (entry == null || entry.isExpired()) {
            onNoUsableResponseReceived(request);
            return;
        }
        String cacheKey = request.getCacheKey();
        synchronized (this) {
            listRemove = this.mWaitingRequests.remove(cacheKey);
        }
        if (listRemove != null) {
            if (VolleyLog.DEBUG) {
                VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(listRemove.size()), cacheKey);
            }
            Iterator<Request<?>> it = listRemove.iterator();
            while (it.hasNext()) {
                this.mResponseDelivery.postResponse(it.next(), response);
            }
        }
    }

    public WaitingRequestManager(@NonNull CacheDispatcher cacheDispatcher, @NonNull BlockingQueue<Request<?>> blockingQueue, ResponseDelivery responseDelivery) {
        this.mWaitingRequests = new HashMap();
        this.mRequestQueue = null;
        this.mResponseDelivery = responseDelivery;
        this.mCacheDispatcher = cacheDispatcher;
        this.mNetworkQueue = blockingQueue;
    }
}
