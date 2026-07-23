package com.google.android.exoplayer2.video;

import android.media.MediaFormat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;

/* JADX INFO: loaded from: classes.dex */
public interface VideoFrameMetadataListener {
    void onVideoFrameAboutToBeRendered(long j, long j2, Format format, @Nullable MediaFormat mediaFormat);
}
