package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.view.Surface;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.text.Typography;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes2.dex */
final class GifInfoHandle {
    private volatile long gifInfoPtr;

    static {
        LibraryLoader.loadLibrary();
    }

    public GifInfoHandle() {
    }

    private static native void bindSurface(long j, Surface surface, long[] jArr);

    public static native int createTempNativeFileDescriptor() throws GifIOException;

    public static native int extractNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z) throws GifIOException;

    private static native void free(long j);

    private static native long getAllocationByteCount(long j);

    private static native String getComment(long j);

    private static native int getCurrentFrameIndex(long j);

    private static native int getCurrentLoop(long j);

    private static native int getCurrentPosition(long j);

    private static native int getDuration(long j);

    private static native int getFrameDuration(long j, int i);

    private static native int getHeight(long j);

    private static native int getLoopCount(long j);

    private static native long getMetadataByteCount(long j);

    private static native int getNativeErrorCode(long j);

    @RequiresApi(21)
    private static int getNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z) throws GifIOException, ErrnoException {
        try {
            int iCreateTempNativeFileDescriptor = createTempNativeFileDescriptor();
            Os.dup2(fileDescriptor, iCreateTempNativeFileDescriptor);
            return iCreateTempNativeFileDescriptor;
        } finally {
            if (z) {
                Os.close(fileDescriptor);
            }
        }
    }

    private static native int getNumberOfFrames(long j);

    private static native long[] getSavedState(long j);

    private static native long getSourceLength(long j);

    private static native int getWidth(long j);

    private static native void glTexImage2D(long j, int i, int i2);

    private static native void glTexSubImage2D(long j, int i, int i2);

    private static native void initTexImageDescriptor(long j);

    private static native boolean isAnimationCompleted(long j);

    private static native boolean isOpaque(long j);

    public static native long openByteArray(byte[] bArr) throws GifIOException;

    public static native long openDirectByteBuffer(ByteBuffer byteBuffer) throws GifIOException;

    public static native long openFile(String str) throws GifIOException;

    private static long openFileDescriptor(FileDescriptor fileDescriptor, long j, boolean z) throws GifIOException {
        int nativeFileDescriptor;
        if (Build.VERSION.SDK_INT > 27) {
            try {
                nativeFileDescriptor = getNativeFileDescriptor(fileDescriptor, z);
            } catch (Exception e) {
                throw new GifIOException(GifError.OPEN_FAILED.errorCode, e.getMessage());
            }
        } else {
            nativeFileDescriptor = extractNativeFileDescriptor(fileDescriptor, z);
        }
        return openNativeFileDescriptor(nativeFileDescriptor, j);
    }

    public static native long openNativeFileDescriptor(int i, long j) throws GifIOException;

    public static native long openStream(InputStream inputStream) throws GifIOException;

    public static GifInfoHandle openUri(ContentResolver contentResolver, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme())) {
            return new GifInfoHandle(uri.getPath());
        }
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
        if (assetFileDescriptorOpenAssetFileDescriptor != null) {
            return new GifInfoHandle(assetFileDescriptorOpenAssetFileDescriptor);
        }
        throw new IOException("Could not open AssetFileDescriptor for " + uri);
    }

    private static native void postUnbindSurface(long j);

    private static native long renderFrame(long j, Bitmap bitmap);

    private static native boolean reset(long j);

    private static native long restoreRemainder(long j);

    private static native int restoreSavedState(long j, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j);

    private static native void seekToFrame(long j, int i, Bitmap bitmap);

    private static native void seekToFrameGL(long j, int i);

    private static native void seekToTime(long j, int i, Bitmap bitmap);

    private static native void setLoopCount(long j, char c);

    private static native void setOptions(long j, char c, boolean z);

    private static native void setSpeedFactor(long j, float f);

    private static native void startDecoderThread(long j);

    private static native void stopDecoderThread(long j);

    private void throwIfFrameIndexOutOfBounds(@IntRange(from = 0) int i) {
        int numberOfFrames = getNumberOfFrames(this.gifInfoPtr);
        if (i < 0 || i >= numberOfFrames) {
            throw new IndexOutOfBoundsException("Frame index is not in range <0;" + numberOfFrames + Typography.greater);
        }
    }

    public final void bindSurface(Surface surface, long[] jArr) {
        bindSurface(this.gifInfoPtr, surface, jArr);
    }

    public final void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    public final synchronized long getAllocationByteCount() {
        return getAllocationByteCount(this.gifInfoPtr);
    }

    public final synchronized String getComment() {
        return getComment(this.gifInfoPtr);
    }

    public final synchronized int getCurrentFrameIndex() {
        return getCurrentFrameIndex(this.gifInfoPtr);
    }

    public final synchronized int getCurrentLoop() {
        return getCurrentLoop(this.gifInfoPtr);
    }

    public final synchronized int getCurrentPosition() {
        return getCurrentPosition(this.gifInfoPtr);
    }

    public final synchronized int getDuration() {
        return getDuration(this.gifInfoPtr);
    }

    public final synchronized int getFrameDuration(@IntRange(from = 0) int i) {
        throwIfFrameIndexOutOfBounds(i);
        return getFrameDuration(this.gifInfoPtr, i);
    }

    public final synchronized int getHeight() {
        return getHeight(this.gifInfoPtr);
    }

    public final synchronized int getLoopCount() {
        return getLoopCount(this.gifInfoPtr);
    }

    public final synchronized long getMetadataByteCount() {
        return getMetadataByteCount(this.gifInfoPtr);
    }

    public final synchronized int getNativeErrorCode() {
        return getNativeErrorCode(this.gifInfoPtr);
    }

    public final synchronized int getNumberOfFrames() {
        return getNumberOfFrames(this.gifInfoPtr);
    }

    public final synchronized long[] getSavedState() {
        return getSavedState(this.gifInfoPtr);
    }

    public final synchronized long getSourceLength() {
        return getSourceLength(this.gifInfoPtr);
    }

    public final synchronized int getWidth() {
        return getWidth(this.gifInfoPtr);
    }

    public final void glTexImage2D(int i, int i2) {
        glTexImage2D(this.gifInfoPtr, i, i2);
    }

    public final void glTexSubImage2D(int i, int i2) {
        glTexSubImage2D(this.gifInfoPtr, i, i2);
    }

    public final void initTexImageDescriptor() {
        initTexImageDescriptor(this.gifInfoPtr);
    }

    public final synchronized boolean isAnimationCompleted() {
        return isAnimationCompleted(this.gifInfoPtr);
    }

    public final synchronized boolean isOpaque() {
        return isOpaque(this.gifInfoPtr);
    }

    public final synchronized boolean isRecycled() {
        return this.gifInfoPtr == 0;
    }

    public final synchronized void postUnbindSurface() {
        postUnbindSurface(this.gifInfoPtr);
    }

    public final synchronized void recycle() {
        free(this.gifInfoPtr);
        this.gifInfoPtr = 0L;
    }

    public final synchronized long renderFrame(Bitmap bitmap) {
        return renderFrame(this.gifInfoPtr, bitmap);
    }

    public final synchronized boolean reset() {
        return reset(this.gifInfoPtr);
    }

    public final synchronized long restoreRemainder() {
        return restoreRemainder(this.gifInfoPtr);
    }

    public final synchronized int restoreSavedState(long[] jArr, Bitmap bitmap) {
        return restoreSavedState(this.gifInfoPtr, jArr, bitmap);
    }

    public final synchronized void saveRemainder() {
        saveRemainder(this.gifInfoPtr);
    }

    public final synchronized void seekToFrame(@IntRange(from = 0, to = 2147483647L) int i, Bitmap bitmap) {
        seekToFrame(this.gifInfoPtr, i, bitmap);
    }

    public final void seekToFrameGL(@IntRange(from = 0) int i) {
        throwIfFrameIndexOutOfBounds(i);
        seekToFrameGL(this.gifInfoPtr, i);
    }

    public final synchronized void seekToTime(@IntRange(from = 0, to = 2147483647L) int i, Bitmap bitmap) {
        seekToTime(this.gifInfoPtr, i, bitmap);
    }

    public final void setLoopCount(@IntRange(from = 0, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (this) {
            setLoopCount(this.gifInfoPtr, (char) i);
        }
    }

    public final void setOptions(char c, boolean z) {
        setOptions(this.gifInfoPtr, c, z);
    }

    public final void setSpeedFactor(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f <= 0.0f || Float.isNaN(f)) {
            throw new IllegalArgumentException("Speed factor is not positive");
        }
        if (f < 4.656613E-10f) {
            f = 4.656613E-10f;
        }
        synchronized (this) {
            setSpeedFactor(this.gifInfoPtr, f);
        }
    }

    public final void startDecoderThread() {
        startDecoderThread(this.gifInfoPtr);
    }

    public final void stopDecoderThread() {
        stopDecoderThread(this.gifInfoPtr);
    }

    public GifInfoHandle(FileDescriptor fileDescriptor) throws GifIOException {
        this.gifInfoPtr = openFileDescriptor(fileDescriptor, 0L, true);
    }

    public GifInfoHandle(byte[] bArr) throws GifIOException {
        this.gifInfoPtr = openByteArray(bArr);
    }

    public GifInfoHandle(ByteBuffer byteBuffer) throws GifIOException {
        this.gifInfoPtr = openDirectByteBuffer(byteBuffer);
    }

    public GifInfoHandle(String str) throws GifIOException {
        this.gifInfoPtr = openFile(str);
    }

    public GifInfoHandle(InputStream inputStream) throws GifIOException {
        if (inputStream.markSupported()) {
            this.gifInfoPtr = openStream(inputStream);
            return;
        }
        throw new IllegalArgumentException("InputStream does not support marking");
    }

    public GifInfoHandle(AssetFileDescriptor assetFileDescriptor) throws IOException {
        try {
            this.gifInfoPtr = openFileDescriptor(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), false);
        } finally {
            try {
                assetFileDescriptor.close();
            } catch (IOException unused) {
            }
        }
    }
}
