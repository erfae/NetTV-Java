package com.google.android.exoplayer2.ext.cronet;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.upstream.BaseDataSource;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.HttpUtil;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;
import com.google.common.net.HttpHeaders;
import com.google.common.primitives.Longs;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.NetworkException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

/* JADX INFO: loaded from: classes.dex */
public class CronetDataSource extends BaseDataSource implements HttpDataSource {
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private static final int READ_BUFFER_SIZE_BYTES = 32768;
    private long bytesRemaining;
    private final Clock clock;
    private final int connectTimeoutMs;

    @Nullable
    private Predicate<String> contentTypePredicate;
    private final CronetEngine cronetEngine;
    private volatile long currentConnectTimeoutMs;

    @Nullable
    private DataSpec currentDataSpec;

    @Nullable
    private UrlRequest currentUrlRequest;

    @Nullable
    private final HttpDataSource.RequestProperties defaultRequestProperties;

    @Nullable
    private IOException exception;
    private final Executor executor;
    private boolean finished;
    private final boolean handleSetCookieRequests;
    private final boolean keepPostFor302Redirects;
    private boolean opened;
    private final ConditionVariable operation;

    @Nullable
    private ByteBuffer readBuffer;
    private final int readTimeoutMs;
    private final int requestPriority;
    private final HttpDataSource.RequestProperties requestProperties;
    private final boolean resetTimeoutOnRedirects;

    @Nullable
    private UrlResponseInfo responseInfo;
    public final UrlRequest.Callback urlRequestCallback;

    @Nullable
    private final String userAgent;

    public static final class Factory implements HttpDataSource.Factory {
        private int connectTimeoutMs;

        @Nullable
        private Predicate<String> contentTypePredicate;

        @Nullable
        private final CronetEngine cronetEngine;
        private final HttpDataSource.RequestProperties defaultRequestProperties;
        private final Executor executor;

        @Nullable
        private HttpDataSource.Factory fallbackFactory;
        private boolean handleSetCookieRequests;

        @Nullable
        private final DefaultHttpDataSource.Factory internalFallbackFactory;
        private boolean keepPostFor302Redirects;
        private int readTimeoutMs;
        private int requestPriority;
        private boolean resetTimeoutOnRedirects;

        @Nullable
        private TransferListener transferListener;

        @Nullable
        private String userAgent;

        public Factory(CronetEngine cronetEngine, Executor executor) {
            this.cronetEngine = (CronetEngine) Assertions.checkNotNull(cronetEngine);
            this.executor = executor;
            this.defaultRequestProperties = new HttpDataSource.RequestProperties();
            this.internalFallbackFactory = null;
            this.requestPriority = 3;
            this.connectTimeoutMs = 8000;
            this.readTimeoutMs = 8000;
        }

        @CanIgnoreReturnValue
        public Factory setConnectionTimeoutMs(int i) {
            this.connectTimeoutMs = i;
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setConnectTimeoutMs(i);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setContentTypePredicate(@Nullable Predicate<String> predicate) {
            this.contentTypePredicate = predicate;
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setContentTypePredicate(predicate);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        @CanIgnoreReturnValue
        public /* bridge */ /* synthetic */ HttpDataSource.Factory setDefaultRequestProperties(Map map) {
            return setDefaultRequestProperties((Map<String, String>) map);
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Factory setFallbackFactory(@Nullable HttpDataSource.Factory factory) {
            this.fallbackFactory = factory;
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setHandleSetCookieRequests(boolean z) {
            this.handleSetCookieRequests = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setKeepPostFor302Redirects(boolean z) {
            this.keepPostFor302Redirects = z;
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setKeepPostFor302Redirects(z);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setReadTimeoutMs(int i) {
            this.readTimeoutMs = i;
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setReadTimeoutMs(i);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setRequestPriority(int i) {
            this.requestPriority = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setResetTimeoutOnRedirects(boolean z) {
            this.resetTimeoutOnRedirects = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setTransferListener(@Nullable TransferListener transferListener) {
            this.transferListener = transferListener;
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setTransferListener(transferListener);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setUserAgent(@Nullable String str) {
            this.userAgent = str;
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setUserAgent(str);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory, com.google.android.exoplayer2.upstream.DataSource.Factory
        public HttpDataSource createDataSource() {
            if (this.cronetEngine == null) {
                HttpDataSource.Factory factory = this.fallbackFactory;
                return factory != null ? factory.createDataSource() : ((DefaultHttpDataSource.Factory) Assertions.checkNotNull(this.internalFallbackFactory)).createDataSource();
            }
            CronetDataSource cronetDataSource = new CronetDataSource(this.cronetEngine, this.executor, this.requestPriority, this.connectTimeoutMs, this.readTimeoutMs, this.resetTimeoutOnRedirects, this.handleSetCookieRequests, this.userAgent, this.defaultRequestProperties, this.contentTypePredicate, this.keepPostFor302Redirects);
            TransferListener transferListener = this.transferListener;
            if (transferListener != null) {
                cronetDataSource.addTransferListener(transferListener);
            }
            return cronetDataSource;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        @CanIgnoreReturnValue
        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.defaultRequestProperties.clearAndSet(map);
            DefaultHttpDataSource.Factory factory = this.internalFallbackFactory;
            if (factory != null) {
                factory.setDefaultRequestProperties(map);
            }
            return this;
        }

        @Deprecated
        public Factory(CronetEngineWrapper cronetEngineWrapper, Executor executor) {
            this.cronetEngine = cronetEngineWrapper.getCronetEngine();
            this.executor = executor;
            this.defaultRequestProperties = new HttpDataSource.RequestProperties();
            this.internalFallbackFactory = new DefaultHttpDataSource.Factory();
            this.connectTimeoutMs = 8000;
            this.readTimeoutMs = 8000;
        }
    }

    public final class UrlRequestCallback extends UrlRequest.Callback {
        private UrlRequestCallback() {
        }

        @Override // org.chromium.net.UrlRequest.Callback
        public synchronized void onFailed(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, CronetException cronetException) {
            if (urlRequest != CronetDataSource.this.currentUrlRequest) {
                return;
            }
            if ((cronetException instanceof NetworkException) && ((NetworkException) cronetException).getErrorCode() == 1) {
                CronetDataSource.this.exception = new UnknownHostException();
            } else {
                CronetDataSource.this.exception = cronetException;
            }
            CronetDataSource.this.operation.open();
        }

        @Override // org.chromium.net.UrlRequest.Callback
        public synchronized void onReadCompleted(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, ByteBuffer byteBuffer) {
            if (urlRequest != CronetDataSource.this.currentUrlRequest) {
                return;
            }
            CronetDataSource.this.operation.open();
        }

        @Override // org.chromium.net.UrlRequest.Callback
        public synchronized void onRedirectReceived(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, String str) {
            if (urlRequest != CronetDataSource.this.currentUrlRequest) {
                return;
            }
            UrlRequest urlRequest2 = (UrlRequest) Assertions.checkNotNull(CronetDataSource.this.currentUrlRequest);
            DataSpec dataSpec = (DataSpec) Assertions.checkNotNull(CronetDataSource.this.currentDataSpec);
            int httpStatusCode = urlResponseInfo.getHttpStatusCode();
            if (dataSpec.httpMethod == 2 && (httpStatusCode == 307 || httpStatusCode == 308)) {
                CronetDataSource.this.exception = new HttpDataSource.InvalidResponseCodeException(httpStatusCode, urlResponseInfo.getHttpStatusText(), null, urlResponseInfo.getAllHeaders(), dataSpec, Util.EMPTY_BYTE_ARRAY);
                CronetDataSource.this.operation.open();
                return;
            }
            if (CronetDataSource.this.resetTimeoutOnRedirects) {
                CronetDataSource.this.resetConnectTimeout();
            }
            boolean z = CronetDataSource.this.keepPostFor302Redirects && dataSpec.httpMethod == 2 && httpStatusCode == 302;
            if (!z && !CronetDataSource.this.handleSetCookieRequests) {
                urlRequest.followRedirect();
                return;
            }
            String cookies = CronetDataSource.parseCookies(urlResponseInfo.getAllHeaders().get(HttpHeaders.SET_COOKIE));
            if (!z && TextUtils.isEmpty(cookies)) {
                urlRequest.followRedirect();
                return;
            }
            urlRequest2.cancel();
            try {
                UrlRequest.Builder builderBuildRequestBuilder = CronetDataSource.this.buildRequestBuilder((z || dataSpec.httpMethod != 2) ? dataSpec.withUri(Uri.parse(str)) : dataSpec.buildUpon().setUri(str).setHttpMethod(1).setHttpBody(null).build());
                CronetDataSource.attachCookies(builderBuildRequestBuilder, cookies);
                CronetDataSource.this.currentUrlRequest = builderBuildRequestBuilder.build();
                CronetDataSource.this.currentUrlRequest.start();
            } catch (IOException e) {
                CronetDataSource.this.exception = e;
            }
        }

        @Override // org.chromium.net.UrlRequest.Callback
        public synchronized void onResponseStarted(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo) {
            if (urlRequest != CronetDataSource.this.currentUrlRequest) {
                return;
            }
            CronetDataSource.this.responseInfo = urlResponseInfo;
            CronetDataSource.this.operation.open();
        }

        @Override // org.chromium.net.UrlRequest.Callback
        public synchronized void onSucceeded(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo) {
            if (urlRequest != CronetDataSource.this.currentUrlRequest) {
                return;
            }
            CronetDataSource.this.finished = true;
            CronetDataSource.this.operation.open();
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.cronet");
    }

    public CronetDataSource(CronetEngine cronetEngine, Executor executor, int i, int i2, int i3, boolean z, boolean z2, @Nullable String str, @Nullable HttpDataSource.RequestProperties requestProperties, @Nullable Predicate<String> predicate, boolean z3) {
        super(true);
        this.cronetEngine = (CronetEngine) Assertions.checkNotNull(cronetEngine);
        this.executor = (Executor) Assertions.checkNotNull(executor);
        this.requestPriority = i;
        this.connectTimeoutMs = i2;
        this.readTimeoutMs = i3;
        this.resetTimeoutOnRedirects = z;
        this.handleSetCookieRequests = z2;
        this.userAgent = str;
        this.defaultRequestProperties = requestProperties;
        this.contentTypePredicate = predicate;
        this.keepPostFor302Redirects = z3;
        this.clock = Clock.DEFAULT;
        this.urlRequestCallback = new UrlRequestCallback();
        this.requestProperties = new HttpDataSource.RequestProperties();
        this.operation = new ConditionVariable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void attachCookies(UrlRequest.Builder builder, @Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        builder.addHeader(HttpHeaders.COOKIE, str);
    }

    private boolean blockUntilConnectTimeout() throws InterruptedException {
        long jElapsedRealtime = this.clock.elapsedRealtime();
        boolean zBlock = false;
        while (!zBlock && jElapsedRealtime < this.currentConnectTimeoutMs) {
            zBlock = this.operation.block((this.currentConnectTimeoutMs - jElapsedRealtime) + 5);
            jElapsedRealtime = this.clock.elapsedRealtime();
        }
        return zBlock;
    }

    private static int copyByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int iMin = Math.min(byteBuffer.remaining(), byteBuffer2.remaining());
        int iLimit = byteBuffer.limit();
        byteBuffer.limit(byteBuffer.position() + iMin);
        byteBuffer2.put(byteBuffer);
        byteBuffer.limit(iLimit);
        return iMin;
    }

    @Nullable
    private static String getFirstHeader(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private ByteBuffer getOrCreateReadBuffer() {
        if (this.readBuffer == null) {
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(32768);
            this.readBuffer = byteBufferAllocateDirect;
            byteBufferAllocateDirect.limit(0);
        }
        return this.readBuffer;
    }

    private static int getStatus(UrlRequest urlRequest) throws InterruptedException {
        final ConditionVariable conditionVariable = new ConditionVariable();
        final int[] iArr = new int[1];
        urlRequest.getStatus(new UrlRequest.StatusListener() { // from class: com.google.android.exoplayer2.ext.cronet.CronetDataSource.1
            @Override // org.chromium.net.UrlRequest.StatusListener
            public void onStatus(int i) {
                iArr[0] = i;
                conditionVariable.open();
            }
        });
        conditionVariable.block();
        return iArr[0];
    }

    private static boolean isCompressed(UrlResponseInfo urlResponseInfo) {
        for (Map.Entry<String, String> entry : urlResponseInfo.getAllHeadersAsList()) {
            if (entry.getKey().equalsIgnoreCase(HttpHeaders.CONTENT_ENCODING)) {
                return !entry.getValue().equalsIgnoreCase("identity");
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static String parseCookies(@Nullable List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return TextUtils.join(";", list);
    }

    private void readInternal(ByteBuffer byteBuffer, DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        ((UrlRequest) Util.castNonNull(this.currentUrlRequest)).read(byteBuffer);
        try {
            if (!this.operation.block(this.readTimeoutMs)) {
                throw new SocketTimeoutException();
            }
        } catch (InterruptedException unused) {
            if (byteBuffer == this.readBuffer) {
                this.readBuffer = null;
            }
            Thread.currentThread().interrupt();
            this.exception = new InterruptedIOException();
        } catch (SocketTimeoutException e) {
            if (byteBuffer == this.readBuffer) {
                this.readBuffer = null;
            }
            this.exception = new HttpDataSource.HttpDataSourceException(e, dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT, 2);
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            if (!(iOException instanceof HttpDataSource.HttpDataSourceException)) {
                throw HttpDataSource.HttpDataSourceException.createForIOException(iOException, dataSpec, 2);
            }
            throw ((HttpDataSource.HttpDataSourceException) iOException);
        }
    }

    private byte[] readResponseBody() throws IOException {
        byte[] bArrCopyOf = Util.EMPTY_BYTE_ARRAY;
        ByteBuffer orCreateReadBuffer = getOrCreateReadBuffer();
        while (!this.finished) {
            this.operation.close();
            orCreateReadBuffer.clear();
            readInternal(orCreateReadBuffer, (DataSpec) Util.castNonNull(this.currentDataSpec));
            orCreateReadBuffer.flip();
            if (orCreateReadBuffer.remaining() > 0) {
                int length = bArrCopyOf.length;
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, orCreateReadBuffer.remaining() + bArrCopyOf.length);
                orCreateReadBuffer.get(bArrCopyOf, length, orCreateReadBuffer.remaining());
            }
        }
        return bArrCopyOf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetConnectTimeout() {
        this.currentConnectTimeoutMs = this.clock.elapsedRealtime() + ((long) this.connectTimeoutMs);
    }

    private void skipFully(long j, DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        if (j == 0) {
            return;
        }
        ByteBuffer orCreateReadBuffer = getOrCreateReadBuffer();
        while (j > 0) {
            try {
                this.operation.close();
                orCreateReadBuffer.clear();
                readInternal(orCreateReadBuffer, dataSpec);
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedIOException();
                }
                if (this.finished) {
                    throw new OpenException(dataSpec, 2008, 14);
                }
                orCreateReadBuffer.flip();
                Assertions.checkState(orCreateReadBuffer.hasRemaining());
                int iMin = (int) Math.min(orCreateReadBuffer.remaining(), j);
                orCreateReadBuffer.position(orCreateReadBuffer.position() + iMin);
                j -= (long) iMin;
            } catch (IOException e) {
                if (e instanceof HttpDataSource.HttpDataSourceException) {
                    throw ((HttpDataSource.HttpDataSourceException) e);
                }
                throw new OpenException(e, dataSpec, e instanceof SocketTimeoutException ? PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT : PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, 14);
            }
        }
    }

    public final UrlRequest.Builder buildRequestBuilder(DataSpec dataSpec) throws IOException {
        UrlRequest.Builder builderAllowDirectExecutor = this.cronetEngine.newUrlRequestBuilder(dataSpec.uri.toString(), this.urlRequestCallback, this.executor).setPriority(this.requestPriority).allowDirectExecutor();
        HashMap map = new HashMap();
        HttpDataSource.RequestProperties requestProperties = this.defaultRequestProperties;
        if (requestProperties != null) {
            map.putAll(requestProperties.getSnapshot());
        }
        map.putAll(this.requestProperties.getSnapshot());
        map.putAll(dataSpec.httpRequestHeaders);
        for (Map.Entry entry : map.entrySet()) {
            builderAllowDirectExecutor.addHeader((String) entry.getKey(), (String) entry.getValue());
        }
        if (dataSpec.httpBody != null && !map.containsKey("Content-Type")) {
            throw new OpenException("HTTP request with non-empty body must set Content-Type", dataSpec, 1004, 0);
        }
        String strBuildRangeRequestHeader = HttpUtil.buildRangeRequestHeader(dataSpec.position, dataSpec.length);
        if (strBuildRangeRequestHeader != null) {
            builderAllowDirectExecutor.addHeader(HttpHeaders.RANGE, strBuildRangeRequestHeader);
        }
        String str = this.userAgent;
        if (str != null) {
            builderAllowDirectExecutor.addHeader(HttpHeaders.USER_AGENT, str);
        }
        builderAllowDirectExecutor.setHttpMethod(dataSpec.getHttpMethodString());
        byte[] bArr = dataSpec.httpBody;
        if (bArr != null) {
            builderAllowDirectExecutor.setUploadDataProvider(new ByteArrayUploadDataProvider(bArr), this.executor);
        }
        return builderAllowDirectExecutor;
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearRequestProperty(String str) {
        this.requestProperties.remove(str);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.HttpDataSource
    public synchronized void close() {
        UrlRequest urlRequest = this.currentUrlRequest;
        if (urlRequest != null) {
            urlRequest.cancel();
            this.currentUrlRequest = null;
        }
        ByteBuffer byteBuffer = this.readBuffer;
        if (byteBuffer != null) {
            byteBuffer.limit(0);
        }
        this.currentDataSpec = null;
        this.responseInfo = null;
        this.exception = null;
        this.finished = false;
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public int getResponseCode() {
        UrlResponseInfo urlResponseInfo = this.responseInfo;
        if (urlResponseInfo == null || urlResponseInfo.getHttpStatusCode() <= 0) {
            return -1;
        }
        return this.responseInfo.getHttpStatusCode();
    }

    @Override // com.google.android.exoplayer2.upstream.BaseDataSource, com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.HttpDataSource
    public Map<String, List<String>> getResponseHeaders() {
        UrlResponseInfo urlResponseInfo = this.responseInfo;
        return urlResponseInfo == null ? Collections.emptyMap() : urlResponseInfo.getAllHeaders();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        UrlResponseInfo urlResponseInfo = this.responseInfo;
        if (urlResponseInfo == null) {
            return null;
        }
        return Uri.parse(urlResponseInfo.getUrl());
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.HttpDataSource
    public long open(DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        byte[] responseBody;
        String firstHeader;
        Assertions.checkNotNull(dataSpec);
        Assertions.checkState(!this.opened);
        this.operation.close();
        resetConnectTimeout();
        this.currentDataSpec = dataSpec;
        try {
            UrlRequest urlRequestBuild = buildRequestBuilder(dataSpec).build();
            this.currentUrlRequest = urlRequestBuild;
            urlRequestBuild.start();
            transferInitializing(dataSpec);
            try {
                boolean zBlockUntilConnectTimeout = blockUntilConnectTimeout();
                IOException iOException = this.exception;
                if (iOException != null) {
                    String message = iOException.getMessage();
                    if (message == null || !Ascii.toLowerCase(message).contains("err_cleartext_not_permitted")) {
                        throw new OpenException(iOException, dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED, getStatus(urlRequestBuild));
                    }
                    throw new HttpDataSource.CleartextNotPermittedException(iOException, dataSpec);
                }
                if (!zBlockUntilConnectTimeout) {
                    throw new OpenException(new SocketTimeoutException(), dataSpec, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT, getStatus(urlRequestBuild));
                }
                UrlResponseInfo urlResponseInfo = (UrlResponseInfo) Assertions.checkNotNull(this.responseInfo);
                int httpStatusCode = urlResponseInfo.getHttpStatusCode();
                Map<String, List<String>> allHeaders = urlResponseInfo.getAllHeaders();
                long j = 0;
                if (httpStatusCode < 200 || httpStatusCode > 299) {
                    if (httpStatusCode == 416) {
                        if (dataSpec.position == HttpUtil.getDocumentSize(getFirstHeader(allHeaders, HttpHeaders.CONTENT_RANGE))) {
                            this.opened = true;
                            transferStarted(dataSpec);
                            long j2 = dataSpec.length;
                            if (j2 != -1) {
                                return j2;
                            }
                            return 0L;
                        }
                    }
                    try {
                        responseBody = readResponseBody();
                    } catch (IOException unused) {
                        responseBody = Util.EMPTY_BYTE_ARRAY;
                    }
                    throw new HttpDataSource.InvalidResponseCodeException(httpStatusCode, urlResponseInfo.getHttpStatusText(), httpStatusCode == 416 ? new DataSourceException(2008) : null, allHeaders, dataSpec, responseBody);
                }
                Predicate<String> predicate = this.contentTypePredicate;
                if (predicate != null && (firstHeader = getFirstHeader(allHeaders, "Content-Type")) != null && !predicate.apply(firstHeader)) {
                    throw new HttpDataSource.InvalidContentTypeException(firstHeader, dataSpec);
                }
                if (httpStatusCode == 200) {
                    long j3 = dataSpec.position;
                    if (j3 != 0) {
                        j = j3;
                    }
                }
                if (isCompressed(urlResponseInfo)) {
                    this.bytesRemaining = dataSpec.length;
                } else {
                    long j4 = dataSpec.length;
                    if (j4 != -1) {
                        this.bytesRemaining = j4;
                    } else {
                        long contentLength = HttpUtil.getContentLength(getFirstHeader(allHeaders, HttpHeaders.CONTENT_LENGTH), getFirstHeader(allHeaders, HttpHeaders.CONTENT_RANGE));
                        this.bytesRemaining = contentLength != -1 ? contentLength - j : -1L;
                    }
                }
                this.opened = true;
                transferStarted(dataSpec);
                skipFully(j, dataSpec);
                return this.bytesRemaining;
            } catch (InterruptedException unused2) {
                Thread.currentThread().interrupt();
                throw new OpenException(new InterruptedIOException(), dataSpec, 1004, -1);
            }
        } catch (IOException e) {
            if (e instanceof HttpDataSource.HttpDataSourceException) {
                throw ((HttpDataSource.HttpDataSourceException) e);
            }
            throw new OpenException(e, dataSpec, 2000, 0);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader, com.google.android.exoplayer2.upstream.HttpDataSource
    public int read(byte[] bArr, int i, int i2) throws HttpDataSource.HttpDataSourceException {
        Assertions.checkState(this.opened);
        if (i2 == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        ByteBuffer orCreateReadBuffer = getOrCreateReadBuffer();
        if (!orCreateReadBuffer.hasRemaining()) {
            this.operation.close();
            orCreateReadBuffer.clear();
            readInternal(orCreateReadBuffer, (DataSpec) Util.castNonNull(this.currentDataSpec));
            if (this.finished) {
                this.bytesRemaining = 0L;
                return -1;
            }
            orCreateReadBuffer.flip();
            Assertions.checkState(orCreateReadBuffer.hasRemaining());
        }
        long[] jArr = new long[3];
        long j = this.bytesRemaining;
        if (j == -1) {
            j = Long.MAX_VALUE;
        }
        jArr[0] = j;
        jArr[1] = orCreateReadBuffer.remaining();
        jArr[2] = i2;
        int iMin = (int) Longs.min(jArr);
        orCreateReadBuffer.get(bArr, i, iMin);
        long j2 = this.bytesRemaining;
        if (j2 != -1) {
            this.bytesRemaining = j2 - ((long) iMin);
        }
        bytesTransferred(iMin);
        return iMin;
    }

    @Deprecated
    public void setContentTypePredicate(@Nullable Predicate<String> predicate) {
        this.contentTypePredicate = predicate;
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void setRequestProperty(String str, String str2) {
        this.requestProperties.set(str, str2);
    }

    public static final class OpenException extends HttpDataSource.HttpDataSourceException {
        public final int cronetConnectionStatus;

        @Deprecated
        public OpenException(IOException iOException, DataSpec dataSpec, int i) {
            super(iOException, dataSpec, 2000, 1);
            this.cronetConnectionStatus = i;
        }

        public OpenException(IOException iOException, DataSpec dataSpec, int i, int i2) {
            super(iOException, dataSpec, i, 1);
            this.cronetConnectionStatus = i2;
        }

        @Deprecated
        public OpenException(String str, DataSpec dataSpec, int i) {
            super(str, dataSpec, 2000, 1);
            this.cronetConnectionStatus = i;
        }

        public OpenException(String str, DataSpec dataSpec, int i, int i2) {
            super(str, dataSpec, i, 1);
            this.cronetConnectionStatus = i2;
        }

        public OpenException(DataSpec dataSpec, int i, int i2) {
            super(dataSpec, i, 1);
            this.cronetConnectionStatus = i2;
        }
    }

    public int read(ByteBuffer byteBuffer) throws HttpDataSource.HttpDataSourceException {
        int iCopyByteBuffer;
        Assertions.checkState(this.opened);
        if (byteBuffer.isDirect()) {
            if (!byteBuffer.hasRemaining()) {
                return 0;
            }
            if (this.bytesRemaining == 0) {
                return -1;
            }
            int iRemaining = byteBuffer.remaining();
            ByteBuffer byteBuffer2 = this.readBuffer;
            if (byteBuffer2 != null && (iCopyByteBuffer = copyByteBuffer(byteBuffer2, byteBuffer)) != 0) {
                long j = this.bytesRemaining;
                if (j != -1) {
                    this.bytesRemaining = j - ((long) iCopyByteBuffer);
                }
                bytesTransferred(iCopyByteBuffer);
                return iCopyByteBuffer;
            }
            this.operation.close();
            readInternal(byteBuffer, (DataSpec) Util.castNonNull(this.currentDataSpec));
            if (this.finished) {
                this.bytesRemaining = 0L;
                return -1;
            }
            Assertions.checkState(iRemaining > byteBuffer.remaining());
            int iRemaining2 = iRemaining - byteBuffer.remaining();
            long j2 = this.bytesRemaining;
            if (j2 != -1) {
                this.bytesRemaining = j2 - ((long) iRemaining2);
            }
            bytesTransferred(iRemaining2);
            return iRemaining2;
        }
        throw new IllegalArgumentException("Passed buffer is not a direct ByteBuffer");
    }
}
