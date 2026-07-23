package pl.droidsonroids.gif;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class GifTexImage2D {
    private final GifInfoHandle mGifInfoHandle;

    public GifTexImage2D(InputSource inputSource, @Nullable GifOptions gifOptions) throws IOException {
        gifOptions = gifOptions == null ? new GifOptions() : gifOptions;
        GifInfoHandle gifInfoHandleOpen = inputSource.open();
        this.mGifInfoHandle = gifInfoHandleOpen;
        gifInfoHandleOpen.setOptions(gifOptions.inSampleSize, gifOptions.inIsOpaque);
        gifInfoHandleOpen.initTexImageDescriptor();
    }

    public final void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    public int getCurrentFrameIndex() {
        return this.mGifInfoHandle.getCurrentFrameIndex();
    }

    public int getDuration() {
        return this.mGifInfoHandle.getDuration();
    }

    public int getFrameDuration(@IntRange(from = 0) int i) {
        return this.mGifInfoHandle.getFrameDuration(i);
    }

    public int getHeight() {
        return this.mGifInfoHandle.getHeight();
    }

    public int getNumberOfFrames() {
        return this.mGifInfoHandle.getNumberOfFrames();
    }

    public int getWidth() {
        return this.mGifInfoHandle.getWidth();
    }

    public void glTexImage2D(int i, int i2) {
        this.mGifInfoHandle.glTexImage2D(i, i2);
    }

    public void glTexSubImage2D(int i, int i2) {
        this.mGifInfoHandle.glTexSubImage2D(i, i2);
    }

    public void recycle() {
        GifInfoHandle gifInfoHandle = this.mGifInfoHandle;
        if (gifInfoHandle != null) {
            gifInfoHandle.recycle();
        }
    }

    public void seekToFrame(@IntRange(from = 0) int i) {
        this.mGifInfoHandle.seekToFrameGL(i);
    }

    public void setSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        this.mGifInfoHandle.setSpeedFactor(f);
    }

    public void startDecoderThread() {
        this.mGifInfoHandle.startDecoderThread();
    }

    public void stopDecoderThread() {
        this.mGifInfoHandle.stopDecoderThread();
    }
}
