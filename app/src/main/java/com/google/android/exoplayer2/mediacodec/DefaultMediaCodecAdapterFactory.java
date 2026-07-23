package com.google.android.exoplayer2.mediacodec;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class DefaultMediaCodecAdapterFactory implements MediaCodecAdapter.Factory {
    private static final int MODE_DEFAULT = 0;
    private static final int MODE_DISABLED = 2;
    private static final int MODE_ENABLED = 1;
    private static final String TAG = "DMCodecAdapterFactory";
    private int asynchronousMode = 0;
    private boolean enableSynchronizeCodecInteractionsWithQueueing;

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
    public MediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws IOException {
        int i;
        int i2 = Util.SDK_INT;
        if (i2 < 23 || ((i = this.asynchronousMode) != 1 && (i != 0 || i2 < 31))) {
            return new SynchronousMediaCodecAdapter.Factory().createAdapter(configuration);
        }
        int trackType = MimeTypes.getTrackType(configuration.format.sampleMimeType);
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Creating an asynchronous MediaCodec adapter for track type ");
        sbM.append(Util.getTrackTypeString(trackType));
        Log.i(TAG, sbM.toString());
        return new AsynchronousMediaCodecAdapter.Factory(trackType, this.enableSynchronizeCodecInteractionsWithQueueing).createAdapter(configuration);
    }

    public void experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z) {
        this.enableSynchronizeCodecInteractionsWithQueueing = z;
    }

    @CanIgnoreReturnValue
    public DefaultMediaCodecAdapterFactory forceDisableAsynchronous() {
        this.asynchronousMode = 2;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaCodecAdapterFactory forceEnableAsynchronous() {
        this.asynchronousMode = 1;
        return this;
    }
}
