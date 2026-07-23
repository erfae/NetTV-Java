package com.bumptech.glide.load.data;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.LogTime;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class HttpUrlFetcher implements DataFetcher<InputStream> {

    @VisibleForTesting
    public static final DefaultHttpUrlConnectionFactory DEFAULT_CONNECTION_FACTORY = new DefaultHttpUrlConnectionFactory();
    private static final int MAXIMUM_REDIRECTS = 5;
    private static final String TAG = "HttpUrlFetcher";
    private final HttpUrlConnectionFactory connectionFactory;
    private final GlideUrl glideUrl;
    private volatile boolean isCancelled;
    private InputStream stream;
    private final int timeout;
    private HttpURLConnection urlConnection;

    public static class DefaultHttpUrlConnectionFactory implements HttpUrlConnectionFactory {
        @Override // com.bumptech.glide.load.data.HttpUrlFetcher.HttpUrlConnectionFactory
        public HttpURLConnection build(URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }
    }

    public interface HttpUrlConnectionFactory {
        HttpURLConnection build(URL url) throws IOException;
    }

    public HttpUrlFetcher(GlideUrl glideUrl, int i) {
        DefaultHttpUrlConnectionFactory defaultHttpUrlConnectionFactory = DEFAULT_CONNECTION_FACTORY;
        this.glideUrl = glideUrl;
        this.timeout = i;
        this.connectionFactory = defaultHttpUrlConnectionFactory;
    }

    private HttpURLConnection buildAndConfigureConnection(URL url, Map<String, String> map) throws HttpException {
        try {
            HttpURLConnection httpURLConnectionBuild = this.connectionFactory.build(url);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpURLConnectionBuild.addRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnectionBuild.setConnectTimeout(this.timeout);
            httpURLConnectionBuild.setReadTimeout(this.timeout);
            httpURLConnectionBuild.setUseCaches(false);
            httpURLConnectionBuild.setDoInput(true);
            httpURLConnectionBuild.setInstanceFollowRedirects(false);
            return httpURLConnectionBuild;
        } catch (IOException e) {
            throw new HttpException("URL.openConnection threw", 0, e);
        }
    }

    private static int getHttpStatusCodeOrInvalid(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            if (!Log.isLoggable(TAG, 3)) {
                return -1;
            }
            Log.d(TAG, "Failed to get a response code", e);
            return -1;
        }
    }

    private InputStream getStreamForSuccessfulRequest(HttpURLConnection httpURLConnection) throws HttpException {
        try {
            if (TextUtils.isEmpty(httpURLConnection.getContentEncoding())) {
                this.stream = ContentLengthInputStream.obtain(httpURLConnection.getInputStream(), httpURLConnection.getContentLength());
            } else {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Got non empty content encoding: " + httpURLConnection.getContentEncoding());
                }
                this.stream = httpURLConnection.getInputStream();
            }
            return this.stream;
        } catch (IOException e) {
            throw new HttpException("Failed to obtain InputStream", getHttpStatusCodeOrInvalid(httpURLConnection), e);
        }
    }

    private static boolean isHttpOk(int i) {
        return i / 100 == 2;
    }

    private static boolean isHttpRedirect(int i) {
        return i / 100 == 3;
    }

    private InputStream loadDataWithRedirects(URL url, int i, URL url2, Map<String, String> map) throws HttpException {
        if (i >= 5) {
            throw new HttpException("Too many (> 5) redirects!", -1);
        }
        if (url2 != null) {
            try {
                if (url.toURI().equals(url2.toURI())) {
                    throw new HttpException("In re-direct loop", -1);
                }
            } catch (URISyntaxException unused) {
            }
        }
        HttpURLConnection httpURLConnectionBuildAndConfigureConnection = buildAndConfigureConnection(url, map);
        this.urlConnection = httpURLConnectionBuildAndConfigureConnection;
        try {
            httpURLConnectionBuildAndConfigureConnection.connect();
            this.stream = this.urlConnection.getInputStream();
            if (this.isCancelled) {
                return null;
            }
            int httpStatusCodeOrInvalid = getHttpStatusCodeOrInvalid(this.urlConnection);
            if (isHttpOk(httpStatusCodeOrInvalid)) {
                return getStreamForSuccessfulRequest(this.urlConnection);
            }
            if (!isHttpRedirect(httpStatusCodeOrInvalid)) {
                if (httpStatusCodeOrInvalid == -1) {
                    throw new HttpException(httpStatusCodeOrInvalid);
                }
                try {
                    throw new HttpException(this.urlConnection.getResponseMessage(), httpStatusCodeOrInvalid);
                } catch (IOException e) {
                    throw new HttpException("Failed to get a response message", httpStatusCodeOrInvalid, e);
                }
            }
            String headerField = this.urlConnection.getHeaderField(HttpHeaders.LOCATION);
            if (TextUtils.isEmpty(headerField)) {
                throw new HttpException("Received empty or null redirect url", httpStatusCodeOrInvalid);
            }
            try {
                URL url3 = new URL(url, headerField);
                cleanup();
                return loadDataWithRedirects(url3, i + 1, url, map);
            } catch (MalformedURLException e2) {
                throw new HttpException(Insets$$ExternalSyntheticOutline0.m("Bad redirect url: ", headerField), httpStatusCodeOrInvalid, e2);
            }
        } catch (IOException e3) {
            throw new HttpException("Failed to connect or obtain data", getHttpStatusCodeOrInvalid(this.urlConnection), e3);
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
        this.isCancelled = true;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        InputStream inputStream = this.stream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
        HttpURLConnection httpURLConnection = this.urlConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        this.urlConnection = null;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super InputStream> dataCallback) {
        StringBuilder sb;
        long logTime = LogTime.getLogTime();
        try {
            dataCallback.onDataReady(loadDataWithRedirects(this.glideUrl.toURL(), 0, null, this.glideUrl.getHeaders()));
            if (Log.isLoggable(TAG, 2)) {
                sb = new StringBuilder();
                sb.append("Finished http url fetcher fetch in ");
                sb.append(LogTime.getElapsedMillis(logTime));
                String string = sb.toString();
            }
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to load data for url", e);
            }
            dataCallback.onLoadFailed(e);
            if (!Log.isLoggable(TAG, 2)) {
            } else {
                sb = new StringBuilder();
            }
        } finally {
            if (Log.isLoggable(TAG, 2)) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Finished http url fetcher fetch in ");
                sbM.append(LogTime.getElapsedMillis(logTime));
                Log.v(TAG, sbM.toString());
            }
        }
    }
}
