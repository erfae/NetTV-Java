package pl.droidsonroids.gif;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes2.dex */
public class GifOptions {
    public boolean inIsOpaque;
    public char inSampleSize;

    public GifOptions() {
        reset();
    }

    private void reset() {
        this.inSampleSize = (char) 1;
        this.inIsOpaque = false;
    }

    public final void setFrom(@Nullable GifOptions gifOptions) {
        if (gifOptions == null) {
            reset();
        } else {
            this.inIsOpaque = gifOptions.inIsOpaque;
            this.inSampleSize = gifOptions.inSampleSize;
        }
    }

    public void setInIsOpaque(boolean z) {
        this.inIsOpaque = z;
    }

    public void setInSampleSize(@IntRange(from = 1, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i) {
        if (i < 1 || i > 65535) {
            this.inSampleSize = (char) 1;
        } else {
            this.inSampleSize = (char) i;
        }
    }
}
