package com.android.volley.toolbox;

import com.android.volley.Network;

/* JADX INFO: loaded from: classes.dex */
public class BasicNetwork implements Network {
    private static final int DEFAULT_POOL_SIZE = 4096;
    private final BaseHttpStack mBaseHttpStack;

    @Deprecated
    public final HttpStack mHttpStack;
    public final ByteArrayPool mPool;

    @Deprecated
    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x001a, code lost:
    
        r12 = r3.getStatusCode();
        r0 = r3.getHeaders();
     */
    @Override // com.android.volley.Network
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.volley.NetworkResponse performRequest(com.android.volley.Request<?> r19) throws com.android.volley.VolleyError {
        /*
            r18 = this;
            r1 = r18
            r8 = r19
            long r9 = android.os.SystemClock.elapsedRealtime()
        L8:
            java.util.Collections.emptyList()
            r2 = 0
            com.android.volley.Cache$Entry r0 = r19.getCacheEntry()     // Catch: java.io.IOException -> L70
            java.util.Map r0 = com.android.volley.toolbox.HttpHeaderParser.getCacheHeaders(r0)     // Catch: java.io.IOException -> L70
            com.android.volley.toolbox.BaseHttpStack r3 = r1.mBaseHttpStack     // Catch: java.io.IOException -> L70
            com.android.volley.toolbox.HttpResponse r3 = r3.executeRequest(r8, r0)     // Catch: java.io.IOException -> L70
            int r12 = r3.getStatusCode()     // Catch: java.io.IOException -> L6b
            java.util.List r0 = r3.getHeaders()     // Catch: java.io.IOException -> L6b
            r4 = 304(0x130, float:4.26E-43)
            if (r12 != r4) goto L30
            long r4 = android.os.SystemClock.elapsedRealtime()     // Catch: java.io.IOException -> L6b
            long r4 = r4 - r9
            com.android.volley.NetworkResponse r0 = com.android.volley.toolbox.NetworkUtility.getNotModifiedNetworkResponse(r8, r4, r0)     // Catch: java.io.IOException -> L6b
            return r0
        L30:
            java.io.InputStream r4 = r3.getContent()     // Catch: java.io.IOException -> L6b
            if (r4 == 0) goto L41
            int r5 = r3.getContentLength()     // Catch: java.io.IOException -> L6b
            com.android.volley.toolbox.ByteArrayPool r6 = r1.mPool     // Catch: java.io.IOException -> L6b
            byte[] r2 = com.android.volley.toolbox.NetworkUtility.inputStreamToBytes(r4, r5, r6)     // Catch: java.io.IOException -> L6b
            goto L44
        L41:
            r4 = 0
            byte[] r2 = new byte[r4]     // Catch: java.io.IOException -> L6b
        L44:
            long r4 = android.os.SystemClock.elapsedRealtime()     // Catch: java.io.IOException -> L6b
            long r4 = r4 - r9
            com.android.volley.toolbox.NetworkUtility.logSlowRequests(r4, r8, r2, r12)     // Catch: java.io.IOException -> L6b
            r4 = 200(0xc8, float:2.8E-43)
            if (r12 < r4) goto L65
            r4 = 299(0x12b, float:4.19E-43)
            if (r12 > r4) goto L65
            com.android.volley.NetworkResponse r4 = new com.android.volley.NetworkResponse     // Catch: java.io.IOException -> L6b
            r14 = 0
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch: java.io.IOException -> L6b
            long r15 = r5 - r9
            r11 = r4
            r13 = r2
            r17 = r0
            r11.<init>(r12, r13, r14, r15, r17)     // Catch: java.io.IOException -> L6b
            return r4
        L65:
            java.io.IOException r0 = new java.io.IOException     // Catch: java.io.IOException -> L6b
            r0.<init>()     // Catch: java.io.IOException -> L6b
            throw r0     // Catch: java.io.IOException -> L6b
        L6b:
            r0 = move-exception
            r7 = r2
            r6 = r3
            r3 = r0
            goto L74
        L70:
            r0 = move-exception
            r3 = r0
            r6 = r2
            r7 = r6
        L74:
            r2 = r19
            r4 = r9
            com.android.volley.toolbox.NetworkUtility$RetryInfo r0 = com.android.volley.toolbox.NetworkUtility.shouldRetryException(r2, r3, r4, r6, r7)
            com.android.volley.toolbox.NetworkUtility.attemptRetryOnException(r8, r0)
            goto L8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.BasicNetwork.performRequest(com.android.volley.Request):com.android.volley.NetworkResponse");
    }

    @Deprecated
    public BasicNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        this.mBaseHttpStack = new AdaptedHttpStack(httpStack);
        this.mPool = byteArrayPool;
    }

    public BasicNetwork(BaseHttpStack baseHttpStack) {
        this(baseHttpStack, new ByteArrayPool(4096));
    }

    public BasicNetwork(BaseHttpStack baseHttpStack, ByteArrayPool byteArrayPool) {
        this.mBaseHttpStack = baseHttpStack;
        this.mPool = byteArrayPool;
    }
}
