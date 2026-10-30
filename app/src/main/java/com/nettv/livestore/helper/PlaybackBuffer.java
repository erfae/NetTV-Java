package com.nettv.livestore.helper;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;

/* JADX INFO: loaded from: classes2.dex */
public final class PlaybackBuffer {
    public static final int AFTER_REBUFFER_MS = 8000;
    public static final int MAX_BUFFER_MS = 90000;
    public static final int MIN_BUFFER_MS = 60000;
    public static final int START_PLAYBACK_MS = 2500;

    private PlaybackBuffer() {
    }

    public static LoadControl live() {
        return new DefaultLoadControl.Builder().setBufferDurationsMs(MIN_BUFFER_MS, MAX_BUFFER_MS, START_PLAYBACK_MS, AFTER_REBUFFER_MS).setPrioritizeTimeOverSizeThresholds(true).build();
    }
}
