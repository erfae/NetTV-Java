package com.android.volley.toolbox;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.android.volley.AsyncNetwork;
import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestTask;
import com.android.volley.VolleyError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
public class BasicAsyncNetwork extends AsyncNetwork {
    private final AsyncHttpStack mAsyncStack;
    private final ByteArrayPool mPool;

    public static class Builder {
        private static final int DEFAULT_POOL_SIZE = 4096;

        @NonNull
        private AsyncHttpStack mAsyncStack;
        private ByteArrayPool mPool = null;

        public Builder(@NonNull AsyncHttpStack asyncHttpStack) {
            this.mAsyncStack = asyncHttpStack;
        }

        public BasicAsyncNetwork build() {
            if (this.mPool == null) {
                this.mPool = new ByteArrayPool(4096);
            }
            return new BasicAsyncNetwork(this.mAsyncStack, this.mPool);
        }

        public Builder setPool(ByteArrayPool byteArrayPool) {
            this.mPool = byteArrayPool;
            return this;
        }
    }

    public class InvokeRetryPolicyTask<T> extends RequestTask<T> {
        public final AsyncNetwork.OnRequestComplete callback;
        public final Request<T> request;
        public final NetworkUtility.RetryInfo retryInfo;

        public InvokeRetryPolicyTask(Request<T> request, NetworkUtility.RetryInfo retryInfo, AsyncNetwork.OnRequestComplete onRequestComplete) {
            super(request);
            this.request = request;
            this.retryInfo = retryInfo;
            this.callback = onRequestComplete;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                NetworkUtility.attemptRetryOnException(this.request, this.retryInfo);
                BasicAsyncNetwork.this.performRequest(this.request, this.callback);
            } catch (VolleyError e) {
                this.callback.onError(e);
            }
        }
    }

    public class ResponseParsingTask<T> extends RequestTask<T> {
        public AsyncNetwork.OnRequestComplete callback;
        public HttpResponse httpResponse;
        public InputStream inputStream;
        public Request<T> request;
        public long requestStartMs;
        public List<Header> responseHeaders;
        public int statusCode;

        public ResponseParsingTask(InputStream inputStream, HttpResponse httpResponse, Request<T> request, AsyncNetwork.OnRequestComplete onRequestComplete, long j, List<Header> list, int i) {
            super(request);
            this.inputStream = inputStream;
            this.httpResponse = httpResponse;
            this.request = request;
            this.callback = onRequestComplete;
            this.requestStartMs = j;
            this.responseHeaders = list;
            this.statusCode = i;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            try {
                BasicAsyncNetwork.this.onResponseRead(this.requestStartMs, this.statusCode, this.httpResponse, this.request, this.callback, this.responseHeaders, NetworkUtility.inputStreamToBytes(this.inputStream, this.httpResponse.getContentLength(), BasicAsyncNetwork.this.mPool));
            } catch (IOException e) {
                BasicAsyncNetwork.this.onRequestFailed(this.request, this.callback, e, this.requestStartMs, this.httpResponse, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestFailed(Request<?> request, AsyncNetwork.OnRequestComplete onRequestComplete, IOException iOException, long j, @Nullable HttpResponse httpResponse, @Nullable byte[] bArr) {
        try {
            getBlockingExecutor().execute(new InvokeRetryPolicyTask(request, NetworkUtility.shouldRetryException(request, iOException, j, httpResponse, bArr), onRequestComplete));
        } catch (VolleyError e) {
            onRequestComplete.onError(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestSucceeded(Request<?> request, long j, HttpResponse httpResponse, AsyncNetwork.OnRequestComplete onRequestComplete) {
        int statusCode = httpResponse.getStatusCode();
        List<Header> headers = httpResponse.getHeaders();
        if (statusCode == 304) {
            onRequestComplete.onSuccess(NetworkUtility.getNotModifiedNetworkResponse(request, SystemClock.elapsedRealtime() - j, headers));
            return;
        }
        byte[] contentBytes = httpResponse.getContentBytes();
        if (contentBytes == null && httpResponse.getContent() == null) {
            contentBytes = new byte[0];
        }
        byte[] bArr = contentBytes;
        if (bArr != null) {
            onResponseRead(j, statusCode, httpResponse, request, onRequestComplete, headers, bArr);
        } else {
            getBlockingExecutor().execute(new ResponseParsingTask(httpResponse.getContent(), httpResponse, request, onRequestComplete, j, headers, statusCode));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onResponseRead(long j, int i, HttpResponse httpResponse, Request<?> request, AsyncNetwork.OnRequestComplete onRequestComplete, List<Header> list, byte[] bArr) {
        NetworkUtility.logSlowRequests(SystemClock.elapsedRealtime() - j, request, bArr, i);
        if (i < 200 || i > 299) {
            onRequestFailed(request, onRequestComplete, new IOException(), j, httpResponse, bArr);
        } else {
            onRequestComplete.onSuccess(new NetworkResponse(i, bArr, false, SystemClock.elapsedRealtime() - j, list));
        }
    }

    @Override // com.android.volley.AsyncNetwork
    public void performRequest(final Request<?> request, final AsyncNetwork.OnRequestComplete onRequestComplete) {
        if (getBlockingExecutor() == null) {
            throw new IllegalStateException("mBlockingExecuter must be set before making a request");
        }
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        this.mAsyncStack.executeRequest(request, HttpHeaderParser.getCacheHeaders(request.getCacheEntry()), new AsyncHttpStack.OnRequestComplete() { // from class: com.android.volley.toolbox.BasicAsyncNetwork.1
            @Override // com.android.volley.toolbox.AsyncHttpStack.OnRequestComplete
            public void onAuthError(AuthFailureError authFailureError) {
                onRequestComplete.onError(authFailureError);
            }

            @Override // com.android.volley.toolbox.AsyncHttpStack.OnRequestComplete
            public void onError(IOException iOException) {
                BasicAsyncNetwork.this.onRequestFailed(request, onRequestComplete, iOException, jElapsedRealtime, null, null);
            }

            @Override // com.android.volley.toolbox.AsyncHttpStack.OnRequestComplete
            public void onSuccess(HttpResponse httpResponse) {
                BasicAsyncNetwork.this.onRequestSucceeded(request, jElapsedRealtime, httpResponse, onRequestComplete);
            }
        });
    }

    @Override // com.android.volley.AsyncNetwork
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setBlockingExecutor(ExecutorService executorService) {
        super.setBlockingExecutor(executorService);
        this.mAsyncStack.setBlockingExecutor(executorService);
    }

    @Override // com.android.volley.AsyncNetwork
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setNonBlockingExecutor(ExecutorService executorService) {
        super.setNonBlockingExecutor(executorService);
        this.mAsyncStack.setNonBlockingExecutor(executorService);
    }

    private BasicAsyncNetwork(AsyncHttpStack asyncHttpStack, ByteArrayPool byteArrayPool) {
        this.mAsyncStack = asyncHttpStack;
        this.mPool = byteArrayPool;
    }
}
