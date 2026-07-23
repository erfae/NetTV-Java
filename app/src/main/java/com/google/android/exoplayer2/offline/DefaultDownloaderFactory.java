package com.google.android.exoplayer2.offline;

import android.util.SparseArray;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.dash.offline.DashDownloader;
import com.google.android.exoplayer2.source.hls.offline.HlsDownloader;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class DefaultDownloaderFactory implements DownloaderFactory {
    private static final SparseArray<Constructor<? extends Downloader>> CONSTRUCTORS = createDownloaderConstructors();
    private final CacheDataSource.Factory cacheDataSourceFactory;
    private final Executor executor;

    @Deprecated
    public DefaultDownloaderFactory(CacheDataSource.Factory factory) {
        this(factory, DownloadManager$$ExternalSyntheticLambda2.INSTANCE$1);
    }

    private static SparseArray<Constructor<? extends Downloader>> createDownloaderConstructors() {
        SparseArray<Constructor<? extends Downloader>> sparseArray = new SparseArray<>();
        try {
            sparseArray.put(0, getDownloaderConstructor(DashDownloader.class));
        } catch (ClassNotFoundException unused) {
        }
        try {
            sparseArray.put(2, getDownloaderConstructor(HlsDownloader.class));
        } catch (ClassNotFoundException unused2) {
        }
        try {
            sparseArray.put(1, getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloader")));
        } catch (ClassNotFoundException unused3) {
        }
        return sparseArray;
    }

    private static Constructor<? extends Downloader> getDownloaderConstructor(Class<?> cls) {
        try {
            return cls.asSubclass(Downloader.class).getConstructor(MediaItem.class, CacheDataSource.Factory.class, Executor.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Downloader constructor missing", e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.DownloaderFactory
    public Downloader createDownloader(DownloadRequest downloadRequest) {
        int iInferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(downloadRequest.uri, downloadRequest.mimeType);
        if (iInferContentTypeForUriAndMimeType == 0 || iInferContentTypeForUriAndMimeType == 1 || iInferContentTypeForUriAndMimeType == 2) {
            return createDownloader(downloadRequest, iInferContentTypeForUriAndMimeType);
        }
        if (iInferContentTypeForUriAndMimeType == 4) {
            return new ProgressiveDownloader(new MediaItem.Builder().setUri(downloadRequest.uri).setCustomCacheKey(downloadRequest.customCacheKey).build(), this.cacheDataSourceFactory, this.executor);
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Unsupported type: ", iInferContentTypeForUriAndMimeType));
    }

    public DefaultDownloaderFactory(CacheDataSource.Factory factory, Executor executor) {
        this.cacheDataSourceFactory = (CacheDataSource.Factory) Assertions.checkNotNull(factory);
        this.executor = (Executor) Assertions.checkNotNull(executor);
    }

    private Downloader createDownloader(DownloadRequest downloadRequest, int i) {
        Constructor<? extends Downloader> constructor = CONSTRUCTORS.get(i);
        if (constructor != null) {
            try {
                return constructor.newInstance(new MediaItem.Builder().setUri(downloadRequest.uri).setStreamKeys(downloadRequest.streamKeys).setCustomCacheKey(downloadRequest.customCacheKey).build(), this.cacheDataSourceFactory, this.executor);
            } catch (Exception e) {
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m23m("Failed to instantiate downloader for content type ", i), e);
            }
        }
        throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m23m("Module missing for content type ", i));
    }
}
