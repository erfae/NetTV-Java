package com.google.android.exoplayer2.ext.ffmpeg;

import com.google.android.exoplayer2.decoder.DecoderException;

/* JADX INFO: loaded from: classes.dex */
public final class FfmpegDecoderException extends DecoderException {
    public FfmpegDecoderException(String str) {
        super(str);
    }

    public FfmpegDecoderException(Throwable th) {
        super("Unexpected decode error", th);
    }
}
