package com.android.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class NetworkDispatcher extends Thread {
    private final Cache mCache;
    private final ResponseDelivery mDelivery;
    private final Network mNetwork;
    private final BlockingQueue<Request<?>> mQueue;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<Request<?>> blockingQueue, Network network, Cache cache, ResponseDelivery responseDelivery) {
        this.mQueue = blockingQueue;
        this.mNetwork = network;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
    }

    @TargetApi(14)
    private void addTrafficStatsTag(Request<?> request) {
        TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
    }

    private void parseAndDeliverNetworkError(Request<?> request, VolleyError volleyError) {
        Objects.requireNonNull(request);
        this.mDelivery.postError(request, volleyError);
    }

    private void processRequest() throws InterruptedException {
        Request<?> requestTake = this.mQueue.take();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        requestTake.sendEvent(3);
        try {
            try {
                try {
                    requestTake.addMarker("network-queue-take");
                    if (requestTake.isCanceled()) {
                        requestTake.finish("network-discard-cancelled");
                        requestTake.notifyListenerResponseNotUsable();
                    } else {
                        addTrafficStatsTag(requestTake);
                        NetworkResponse networkResponsePerformRequest = this.mNetwork.performRequest(requestTake);
                        requestTake.addMarker("network-http-complete");
                        if (networkResponsePerformRequest.notModified && requestTake.hasHadResponseDelivered()) {
                            requestTake.finish("not-modified");
                            requestTake.notifyListenerResponseNotUsable();
                        } else {
                            Response<?> networkResponse = requestTake.parseNetworkResponse(networkResponsePerformRequest);
                            requestTake.addMarker("network-parse-complete");
                            if (requestTake.shouldCache() && networkResponse.cacheEntry != null) {
                                this.mCache.put(requestTake.getCacheKey(), networkResponse.cacheEntry);
                                requestTake.addMarker("network-cache-written");
                            }
                            requestTake.markDelivered();
                            this.mDelivery.postResponse(requestTake, networkResponse);
                            requestTake.notifyListenerResponseReceived(networkResponse);
                        }
                    }
                } catch (Exception e) {
                    VolleyLog.e(e, "Unhandled exception %s", e.toString());
                    VolleyError volleyError = new VolleyError(e);
                    volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime() - jElapsedRealtime);
                    this.mDelivery.postError(requestTake, volleyError);
                    requestTake.notifyListenerResponseNotUsable();
                }
            } catch (VolleyError e2) {
                e2.setNetworkTimeMs(SystemClock.elapsedRealtime() - jElapsedRealtime);
                parseAndDeliverNetworkError(requestTake, e2);
                requestTake.notifyListenerResponseNotUsable();
            }
        } finally {
            requestTake.sendEvent(4);
        }
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException unused) {
                if (this.mQuit) {
                    Thread.currentThread().interrupt();
                    return;
                }
                VolleyLog.e("Ignoring spurious interrupt of NetworkDispatcher thread; use quit() to terminate it", new Object[0]);
            }
        }
    }
}
