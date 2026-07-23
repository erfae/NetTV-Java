package com.android.volley;

import android.os.Process;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request<?>> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request<?>> mNetworkQueue;
    private volatile boolean mQuit = false;
    private final WaitingRequestManager mWaitingRequestManager;

    public CacheDispatcher(BlockingQueue<Request<?>> blockingQueue, BlockingQueue<Request<?>> blockingQueue2, Cache cache, ResponseDelivery responseDelivery) {
        this.mCacheQueue = blockingQueue;
        this.mNetworkQueue = blockingQueue2;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
        this.mWaitingRequestManager = new WaitingRequestManager(this, blockingQueue2, responseDelivery);
    }

    private void processRequest() throws InterruptedException {
        final Request<?> requestTake = this.mCacheQueue.take();
        requestTake.addMarker("cache-queue-take");
        requestTake.sendEvent(1);
        try {
            if (requestTake.isCanceled()) {
                requestTake.finish("cache-discard-canceled");
            } else {
                Cache.Entry entry = this.mCache.get(requestTake.getCacheKey());
                if (entry == null) {
                    requestTake.addMarker("cache-miss");
                    if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(requestTake)) {
                        this.mNetworkQueue.put(requestTake);
                    }
                } else {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (entry.isExpired(jCurrentTimeMillis)) {
                        requestTake.addMarker("cache-hit-expired");
                        requestTake.setCacheEntry(entry);
                        if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(requestTake)) {
                            this.mNetworkQueue.put(requestTake);
                        }
                    } else {
                        requestTake.addMarker("cache-hit");
                        Response<?> networkResponse = requestTake.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                        requestTake.addMarker("cache-hit-parsed");
                        if (!networkResponse.isSuccess()) {
                            requestTake.addMarker("cache-parsing-failed");
                            this.mCache.invalidate(requestTake.getCacheKey(), true);
                            requestTake.setCacheEntry(null);
                            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(requestTake)) {
                                this.mNetworkQueue.put(requestTake);
                            }
                        } else if (entry.refreshNeeded(jCurrentTimeMillis)) {
                            requestTake.addMarker("cache-hit-refresh-needed");
                            requestTake.setCacheEntry(entry);
                            networkResponse.intermediate = true;
                            if (this.mWaitingRequestManager.maybeAddToWaitingRequests(requestTake)) {
                                this.mDelivery.postResponse(requestTake, networkResponse);
                            } else {
                                this.mDelivery.postResponse(requestTake, networkResponse, new Runnable() { // from class: com.android.volley.CacheDispatcher.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        try {
                                            CacheDispatcher.this.mNetworkQueue.put(requestTake);
                                        } catch (InterruptedException unused) {
                                            Thread.currentThread().interrupt();
                                        }
                                    }
                                });
                            }
                        } else {
                            this.mDelivery.postResponse(requestTake, networkResponse);
                        }
                    }
                }
            }
        } finally {
            requestTake.sendEvent(2);
        }
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (DEBUG) {
            VolleyLog.v("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.mCache.initialize();
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException unused) {
                if (this.mQuit) {
                    Thread.currentThread().interrupt();
                    return;
                }
                VolleyLog.e("Ignoring spurious interrupt of CacheDispatcher thread; use quit() to terminate it", new Object[0]);
            }
        }
    }
}
