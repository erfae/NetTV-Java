package com.android.volley;

/* JADX INFO: loaded from: classes.dex */
public abstract class RequestTask<T> implements Runnable {
    public final Request<T> mRequest;

    public RequestTask(Request<T> request) {
        this.mRequest = request;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public int compareTo(RequestTask<?> requestTask) {
        return this.mRequest.compareTo((Request) requestTask.mRequest);
    }
}
