package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C;

/* JADX INFO: loaded from: classes.dex */
public final class FrameProcessingException extends Exception {
    public final long presentationTimeUs;

    public FrameProcessingException(String str) {
        this(str, C.TIME_UNSET);
    }

    public static FrameProcessingException from(Exception exc) {
        return from(exc, C.TIME_UNSET);
    }

    public FrameProcessingException(String str, long j) {
        super(str);
        this.presentationTimeUs = j;
    }

    public static FrameProcessingException from(Exception exc, long j) {
        return exc instanceof FrameProcessingException ? (FrameProcessingException) exc : new FrameProcessingException(exc, j);
    }

    public FrameProcessingException(String str, Throwable th) {
        this(str, th, C.TIME_UNSET);
    }

    public FrameProcessingException(String str, Throwable th, long j) {
        super(str, th);
        this.presentationTimeUs = j;
    }

    public FrameProcessingException(Throwable th) {
        this(th, C.TIME_UNSET);
    }

    public FrameProcessingException(Throwable th, long j) {
        super(th);
        this.presentationTimeUs = j;
    }
}
