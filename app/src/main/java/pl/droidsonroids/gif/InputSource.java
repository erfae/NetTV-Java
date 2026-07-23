package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class InputSource {

    public static class AssetFileDescriptorSource extends InputSource {
        private final AssetFileDescriptor mAssetFileDescriptor;

        public AssetFileDescriptorSource(@NonNull AssetFileDescriptor assetFileDescriptor) {
            super();
            this.mAssetFileDescriptor = assetFileDescriptor;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mAssetFileDescriptor);
        }
    }

    public static final class AssetSource extends InputSource {
        private final AssetManager mAssetManager;
        private final String mAssetName;

        public AssetSource(@NonNull AssetManager assetManager, @NonNull String str) {
            super();
            this.mAssetManager = assetManager;
            this.mAssetName = str;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mAssetManager.openFd(this.mAssetName));
        }
    }

    public static final class ByteArraySource extends InputSource {
        private final byte[] bytes;

        public ByteArraySource(@NonNull byte[] bArr) {
            super();
            this.bytes = bArr;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws GifIOException {
            return new GifInfoHandle(this.bytes);
        }
    }

    public static final class DirectByteBufferSource extends InputSource {
        private final ByteBuffer byteBuffer;

        public DirectByteBufferSource(@NonNull ByteBuffer byteBuffer) {
            super();
            this.byteBuffer = byteBuffer;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws GifIOException {
            return new GifInfoHandle(this.byteBuffer);
        }
    }

    public static final class FileDescriptorSource extends InputSource {
        private final FileDescriptor mFd;

        public FileDescriptorSource(@NonNull FileDescriptor fileDescriptor) {
            super();
            this.mFd = fileDescriptor;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mFd);
        }
    }

    public static final class InputStreamSource extends InputSource {
        private final InputStream inputStream;

        public InputStreamSource(@NonNull InputStream inputStream) {
            super();
            this.inputStream = inputStream;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.inputStream);
        }
    }

    public static class ResourcesSource extends InputSource {
        private final int mResourceId;
        private final Resources mResources;

        public ResourcesSource(@NonNull Resources resources, @DrawableRes @RawRes int i) {
            super();
            this.mResources = resources;
            this.mResourceId = i;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mResources.openRawResourceFd(this.mResourceId));
        }
    }

    public static final class UriSource extends InputSource {
        private final ContentResolver mContentResolver;
        private final Uri mUri;

        public UriSource(@Nullable ContentResolver contentResolver, @NonNull Uri uri) {
            super();
            this.mContentResolver = contentResolver;
            this.mUri = uri;
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws IOException {
            return GifInfoHandle.openUri(this.mContentResolver, this.mUri);
        }
    }

    public abstract GifInfoHandle open() throws IOException;

    public static final class FileSource extends InputSource {
        private final String mPath;

        public FileSource(@NonNull File file) {
            super();
            this.mPath = file.getPath();
        }

        @Override // pl.droidsonroids.gif.InputSource
        public final GifInfoHandle open() throws GifIOException {
            return new GifInfoHandle(this.mPath);
        }

        public FileSource(@NonNull String str) {
            super();
            this.mPath = str;
        }
    }

    private InputSource() {
    }
}
