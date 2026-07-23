package com.google.android.exoplayer2.ext.cronet;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.util.concurrent.Executor;
import org.chromium.net.CronetEngine;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class CronetDataSourceFactory extends HttpDataSource.BaseFactory {
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private final int connectTimeoutMs;
    private final CronetEngineWrapper cronetEngineWrapper;
    private final Executor executor;
    private final HttpDataSource.Factory fallbackFactory;
    private final int readTimeoutMs;
    private final boolean resetTimeoutOnRedirects;

    @Nullable
    private final TransferListener transferListener;

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, HttpDataSource.Factory factory) {
        this(cronetEngineWrapper, executor, (TransferListener) null, 8000, 8000, false, factory);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource.BaseFactory
    public final HttpDataSource createDataSourceInternal(HttpDataSource.RequestProperties requestProperties) {
        CronetEngine cronetEngine = this.cronetEngineWrapper.getCronetEngine();
        if (cronetEngine == null) {
            return this.fallbackFactory.createDataSource();
        }
        CronetDataSource cronetDataSource = new CronetDataSource(cronetEngine, this.executor, 3, this.connectTimeoutMs, this.readTimeoutMs, this.resetTimeoutOnRedirects, false, null, requestProperties, null, false);
        TransferListener transferListener = this.transferListener;
        if (transferListener != null) {
            cronetDataSource.addTransferListener(transferListener);
        }
        return cronetDataSource;
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor) {
        this(cronetEngineWrapper, executor, (String) null);
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, @Nullable String str) {
        this(cronetEngineWrapper, executor, (TransferListener) null, 8000, 8000, false, (HttpDataSource.Factory) new DefaultHttpDataSource.Factory().setUserAgent(str));
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, int i, int i2, boolean z, @Nullable String str) {
        this(cronetEngineWrapper, executor, (TransferListener) null, i, i2, z, new DefaultHttpDataSource.Factory().setUserAgent(str).setConnectTimeoutMs(i).setReadTimeoutMs(i2));
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, int i, int i2, boolean z, HttpDataSource.Factory factory) {
        this(cronetEngineWrapper, executor, (TransferListener) null, i, i2, z, factory);
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, @Nullable TransferListener transferListener, HttpDataSource.Factory factory) {
        this(cronetEngineWrapper, executor, transferListener, 8000, 8000, false, factory);
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, @Nullable TransferListener transferListener) {
        this(cronetEngineWrapper, executor, transferListener, (String) null);
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, @Nullable TransferListener transferListener, @Nullable String str) {
        this(cronetEngineWrapper, executor, transferListener, 8000, 8000, false, (HttpDataSource.Factory) new DefaultHttpDataSource.Factory().setUserAgent(str).setTransferListener(transferListener));
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, @Nullable TransferListener transferListener, int i, int i2, boolean z, @Nullable String str) {
        this(cronetEngineWrapper, executor, transferListener, i, i2, z, new DefaultHttpDataSource.Factory().setUserAgent(str).setTransferListener(transferListener).setConnectTimeoutMs(i).setReadTimeoutMs(i2));
    }

    public CronetDataSourceFactory(CronetEngineWrapper cronetEngineWrapper, Executor executor, @Nullable TransferListener transferListener, int i, int i2, boolean z, HttpDataSource.Factory factory) {
        this.cronetEngineWrapper = cronetEngineWrapper;
        this.executor = executor;
        this.transferListener = transferListener;
        this.connectTimeoutMs = i;
        this.readTimeoutMs = i2;
        this.resetTimeoutOnRedirects = z;
        this.fallbackFactory = factory;
    }
}
