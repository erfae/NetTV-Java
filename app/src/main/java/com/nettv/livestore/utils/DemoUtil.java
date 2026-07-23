package com.nettv.livestore.utils;

import android.content.Context;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.ext.cronet.CronetDataSource;
import com.google.android.exoplayer2.ext.cronet.CronetUtil;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.Executors;
import org.chromium.net.CronetEngine;

/* JADX INFO: loaded from: classes2.dex */
public final class DemoUtil {
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    private static final boolean USE_CRONET_FOR_NETWORKING = true;
    private static DataSource.Factory dataSourceFactory;
    private static DatabaseProvider databaseProvider;
    private static Cache downloadCache;
    private static File downloadDirectory;
    private static HttpDataSource.Factory httpDataSourceFactory;

    private DemoUtil() {
    }

    private static CacheDataSource.Factory buildReadOnlyCacheDataSource(DataSource.Factory factory, Cache cache) {
        return new CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(factory).setCacheWriteDataSinkFactory(null).setFlags(2);
    }

    public static RenderersFactory buildRenderersFactory(Context context, boolean z) {
        int i;
        if (useExtensionRenderers()) {
            i = z ? 2 : 1;
        } else {
            i = 0;
        }
        return new DefaultRenderersFactory(context.getApplicationContext()).setExtensionRendererMode(i);
    }

    public static synchronized DataSource.Factory getDataSourceFactory(Context context) {
        if (dataSourceFactory == null) {
            Context applicationContext = context.getApplicationContext();
            dataSourceFactory = buildReadOnlyCacheDataSource(new DefaultDataSource.Factory(applicationContext, getHttpDataSourceFactory(applicationContext)), getDownloadCache(applicationContext));
        }
        return dataSourceFactory;
    }

    private static synchronized DatabaseProvider getDatabaseProvider(Context context) {
        if (databaseProvider == null) {
            databaseProvider = new StandaloneDatabaseProvider(context);
        }
        return databaseProvider;
    }

    private static synchronized Cache getDownloadCache(Context context) {
        if (downloadCache == null) {
            downloadCache = new SimpleCache(new File(getDownloadDirectory(context), DOWNLOAD_CONTENT_DIRECTORY), new NoOpCacheEvictor(), getDatabaseProvider(context));
        }
        return downloadCache;
    }

    private static synchronized File getDownloadDirectory(Context context) {
        if (downloadDirectory == null) {
            File externalFilesDir = context.getExternalFilesDir(null);
            downloadDirectory = externalFilesDir;
            if (externalFilesDir == null) {
                downloadDirectory = context.getFilesDir();
            }
        }
        return downloadDirectory;
    }

    public static synchronized HttpDataSource.Factory getHttpDataSourceFactory(Context context) {
        if (httpDataSourceFactory == null) {
            CronetEngine cronetEngineBuildCronetEngine = CronetUtil.buildCronetEngine(context.getApplicationContext());
            if (cronetEngineBuildCronetEngine != null) {
                httpDataSourceFactory = new CronetDataSource.Factory(cronetEngineBuildCronetEngine, Executors.newSingleThreadExecutor());
            }
            if (httpDataSourceFactory == null) {
                CookieManager cookieManager = new CookieManager();
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
                CookieHandler.setDefault(cookieManager);
                httpDataSourceFactory = new DefaultHttpDataSource.Factory();
            }
        }
        return httpDataSourceFactory;
    }

    public static boolean useExtensionRenderers() {
        return true;
    }
}
