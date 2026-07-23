package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.material.search.SearchBar$$ExternalSyntheticLambda0;
import java.io.IOException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ProgressiveDownloader implements Downloader {
    private final CacheWriter cacheWriter;
    private final CacheDataSource dataSource;
    private final DataSpec dataSpec;
    private volatile RunnableFutureTask<Void, IOException> downloadRunnable;
    private final Executor executor;
    private volatile boolean isCanceled;

    @Nullable
    private final PriorityTaskManager priorityTaskManager;

    @Nullable
    private Downloader.ProgressListener progressListener;

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, DownloadManager$$ExternalSyntheticLambda2.INSTANCE$2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProgress(long j, long j2, long j3) {
        Downloader.ProgressListener progressListener = this.progressListener;
        if (progressListener == null) {
            return;
        }
        progressListener.onProgress(j, j2, (j == -1 || j == 0) ? -1.0f : (j2 * 100.0f) / j);
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void cancel() {
        this.isCanceled = true;
        RunnableFutureTask<Void, IOException> runnableFutureTask = this.downloadRunnable;
        if (runnableFutureTask != null) {
            runnableFutureTask.cancel(true);
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0049 */
    @Override // com.google.android.exoplayer2.offline.Downloader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void download(@androidx.annotation.Nullable com.google.android.exoplayer2.offline.Downloader.ProgressListener r4) throws java.lang.InterruptedException, java.io.IOException {
        /*
            r3 = this;
            r3.progressListener = r4
            com.google.android.exoplayer2.util.PriorityTaskManager r4 = r3.priorityTaskManager
            r0 = -1000(0xfffffffffffffc18, float:NaN)
            if (r4 == 0) goto Lb
            r4.add(r0)
        Lb:
            r4 = 0
        Lc:
            if (r4 != 0) goto L5d
            boolean r1 = r3.isCanceled     // Catch: java.lang.Throwable -> L49
            if (r1 != 0) goto L5d
            com.google.android.exoplayer2.offline.ProgressiveDownloader$1 r1 = new com.google.android.exoplayer2.offline.ProgressiveDownloader$1     // Catch: java.lang.Throwable -> L49
            r1.<init>()     // Catch: java.lang.Throwable -> L49
            r3.downloadRunnable = r1     // Catch: java.lang.Throwable -> L49
            com.google.android.exoplayer2.util.PriorityTaskManager r1 = r3.priorityTaskManager     // Catch: java.lang.Throwable -> L49
            if (r1 == 0) goto L20
            r1.proceed(r0)     // Catch: java.lang.Throwable -> L49
        L20:
            java.util.concurrent.Executor r1 = r3.executor     // Catch: java.lang.Throwable -> L49
            com.google.android.exoplayer2.util.RunnableFutureTask<java.lang.Void, java.io.IOException> r2 = r3.downloadRunnable     // Catch: java.lang.Throwable -> L49
            r1.execute(r2)     // Catch: java.lang.Throwable -> L49
            com.google.android.exoplayer2.util.RunnableFutureTask<java.lang.Void, java.io.IOException> r1 = r3.downloadRunnable     // Catch: java.util.concurrent.ExecutionException -> L2e java.lang.Throwable -> L49
            r1.get()     // Catch: java.util.concurrent.ExecutionException -> L2e java.lang.Throwable -> L49
            r4 = 1
            goto Lc
        L2e:
            r1 = move-exception
            java.lang.Throwable r1 = r1.getCause()     // Catch: java.lang.Throwable -> L49
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)     // Catch: java.lang.Throwable -> L49
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch: java.lang.Throwable -> L49
            boolean r2 = r1 instanceof com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException     // Catch: java.lang.Throwable -> L49
            if (r2 == 0) goto L3e
            goto Lc
        L3e:
            boolean r2 = r1 instanceof java.io.IOException     // Catch: java.lang.Throwable -> L49
            if (r2 != 0) goto L46
            com.google.android.exoplayer2.util.Util.sneakyThrow(r1)     // Catch: java.lang.Throwable -> L49
            goto Lc
        L46:
            java.io.IOException r1 = (java.io.IOException) r1     // Catch: java.lang.Throwable -> L49
            throw r1     // Catch: java.lang.Throwable -> L49
        L49:
            r4 = move-exception
            com.google.android.exoplayer2.util.RunnableFutureTask<java.lang.Void, java.io.IOException> r1 = r3.downloadRunnable
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            com.google.android.exoplayer2.util.RunnableFutureTask r1 = (com.google.android.exoplayer2.util.RunnableFutureTask) r1
            r1.blockUntilFinished()
            com.google.android.exoplayer2.util.PriorityTaskManager r1 = r3.priorityTaskManager
            if (r1 == 0) goto L5c
            r1.remove(r0)
        L5c:
            throw r4
        L5d:
            com.google.android.exoplayer2.util.RunnableFutureTask<java.lang.Void, java.io.IOException> r4 = r3.downloadRunnable
            java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4)
            com.google.android.exoplayer2.util.RunnableFutureTask r4 = (com.google.android.exoplayer2.util.RunnableFutureTask) r4
            r4.blockUntilFinished()
            com.google.android.exoplayer2.util.PriorityTaskManager r4 = r3.priorityTaskManager
            if (r4 == 0) goto L6f
            r4.remove(r0)
        L6f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.ProgressiveDownloader.download(com.google.android.exoplayer2.offline.Downloader$ProgressListener):void");
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void remove() {
        this.dataSource.getCache().removeResource(this.dataSource.getCacheKeyFactory().buildCacheKey(this.dataSpec));
    }

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this.executor = (Executor) Assertions.checkNotNull(executor);
        Assertions.checkNotNull(mediaItem.localConfiguration);
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(mediaItem.localConfiguration.uri).setKey(mediaItem.localConfiguration.customCacheKey).setFlags(4).build();
        this.dataSpec = dataSpecBuild;
        CacheDataSource cacheDataSourceCreateDataSourceForDownloading = factory.createDataSourceForDownloading();
        this.dataSource = cacheDataSourceCreateDataSourceForDownloading;
        this.cacheWriter = new CacheWriter(cacheDataSourceCreateDataSourceForDownloading, dataSpecBuild, null, new SearchBar$$ExternalSyntheticLambda0(this));
        this.priorityTaskManager = factory.getUpstreamPriorityTaskManager();
    }
}
