package com.google.android.exoplayer2.text;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;

/* JADX INFO: loaded from: classes.dex */
public final class TextRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MSG_UPDATE_OUTPUT = 0;
    private static final int REPLACEMENT_STATE_NONE = 0;
    private static final int REPLACEMENT_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REPLACEMENT_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "TextRenderer";

    @Nullable
    private SubtitleDecoder decoder;
    private final SubtitleDecoderFactory decoderFactory;
    private int decoderReplacementState;
    private long finalStreamEndPositionUs;
    private final FormatHolder formatHolder;
    private boolean inputStreamEnded;
    private long lastRendererPositionUs;

    @Nullable
    private SubtitleInputBuffer nextInputBuffer;

    @Nullable
    private SubtitleOutputBuffer nextSubtitle;
    private int nextSubtitleEventIndex;
    private final TextOutput output;

    @Nullable
    private final Handler outputHandler;
    private boolean outputStreamEnded;
    private long outputStreamOffsetUs;

    @Nullable
    private Format streamFormat;

    @Nullable
    private SubtitleOutputBuffer subtitle;
    private boolean waitingForKeyFrame;

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper) {
        this(textOutput, looper, SubtitleDecoderFactory.DEFAULT);
    }

    private void clearOutput() {
        updateOutput(new CueGroup(ImmutableList.of(), getPresentationTimeUs(this.lastRendererPositionUs)));
    }

    @RequiresNonNull({"subtitle"})
    @SideEffectFree
    private long getCurrentEventTimeUs(long j) {
        int nextEventTimeIndex = this.subtitle.getNextEventTimeIndex(j);
        if (nextEventTimeIndex == 0 || this.subtitle.getEventTimeCount() == 0) {
            return this.subtitle.timeUs;
        }
        if (nextEventTimeIndex != -1) {
            return this.subtitle.getEventTime(nextEventTimeIndex - 1);
        }
        SubtitleOutputBuffer subtitleOutputBuffer = this.subtitle;
        return subtitleOutputBuffer.getEventTime(subtitleOutputBuffer.getEventTimeCount() - 1);
    }

    private long getNextEventTime() {
        if (this.nextSubtitleEventIndex == -1) {
            return Long.MAX_VALUE;
        }
        Assertions.checkNotNull(this.subtitle);
        if (this.nextSubtitleEventIndex >= this.subtitle.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
    }

    @SideEffectFree
    private long getPresentationTimeUs(long j) {
        Assertions.checkState(j != C.TIME_UNSET);
        Assertions.checkState(this.outputStreamOffsetUs != C.TIME_UNSET);
        return j - this.outputStreamOffsetUs;
    }

    private void handleDecoderError(SubtitleDecoderException subtitleDecoderException) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Subtitle decoding failed. streamFormat=");
        sbM.append(this.streamFormat);
        Log.e(TAG, sbM.toString(), subtitleDecoderException);
        clearOutput();
        replaceDecoder();
    }

    private void initDecoder() {
        this.waitingForKeyFrame = true;
        this.decoder = this.decoderFactory.createDecoder((Format) Assertions.checkNotNull(this.streamFormat));
    }

    private void invokeUpdateOutputInternal(CueGroup cueGroup) {
        this.output.onCues(cueGroup.cues);
        this.output.onCues(cueGroup);
    }

    private void releaseBuffers() {
        this.nextInputBuffer = null;
        this.nextSubtitleEventIndex = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.subtitle;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.subtitle = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.nextSubtitle;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.nextSubtitle = null;
        }
    }

    private void releaseDecoder() {
        releaseBuffers();
        ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).release();
        this.decoder = null;
        this.decoderReplacementState = 0;
    }

    private void replaceDecoder() {
        releaseDecoder();
        initDecoder();
    }

    private void updateOutput(CueGroup cueGroup) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, cueGroup).sendToTarget();
        } else {
            invokeUpdateOutputInternal(cueGroup);
        }
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            throw new IllegalStateException();
        }
        invokeUpdateOutputInternal((CueGroup) message.obj);
        return true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onDisabled() {
        this.streamFormat = null;
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        clearOutput();
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastRendererPositionUs = C.TIME_UNSET;
        releaseDecoder();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onPositionReset(long j, boolean z) {
        this.lastRendererPositionUs = j;
        clearOutput();
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        if (this.decoderReplacementState != 0) {
            replaceDecoder();
        } else {
            releaseBuffers();
            ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).flush();
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onStreamChanged(Format[] formatArr, long j, long j2) {
        this.outputStreamOffsetUs = j2;
        this.streamFormat = formatArr[0];
        if (this.decoder != null) {
            this.decoderReplacementState = 1;
        } else {
            initDecoder();
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) throws DecoderException {
        boolean z;
        this.lastRendererPositionUs = j;
        if (isCurrentStreamFinal()) {
            long j3 = this.finalStreamEndPositionUs;
            if (j3 != C.TIME_UNSET && j >= j3) {
                releaseBuffers();
                this.outputStreamEnded = true;
            }
        }
        if (this.outputStreamEnded) {
            return;
        }
        if (this.nextSubtitle == null) {
            ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).setPositionUs(j);
            try {
                this.nextSubtitle = ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).dequeueOutputBuffer();
            } catch (SubtitleDecoderException e) {
                handleDecoderError(e);
                return;
            }
        }
        if (getState() != 2) {
            return;
        }
        if (this.subtitle != null) {
            long nextEventTime = getNextEventTime();
            z = false;
            while (nextEventTime <= j) {
                this.nextSubtitleEventIndex++;
                nextEventTime = getNextEventTime();
                z = true;
            }
        } else {
            z = false;
        }
        SubtitleOutputBuffer subtitleOutputBuffer = this.nextSubtitle;
        if (subtitleOutputBuffer != null) {
            if (subtitleOutputBuffer.isEndOfStream()) {
                if (!z && getNextEventTime() == Long.MAX_VALUE) {
                    if (this.decoderReplacementState == 2) {
                        replaceDecoder();
                    } else {
                        releaseBuffers();
                        this.outputStreamEnded = true;
                    }
                }
            } else if (subtitleOutputBuffer.timeUs <= j) {
                SubtitleOutputBuffer subtitleOutputBuffer2 = this.subtitle;
                if (subtitleOutputBuffer2 != null) {
                    subtitleOutputBuffer2.release();
                }
                this.nextSubtitleEventIndex = subtitleOutputBuffer.getNextEventTimeIndex(j);
                this.subtitle = subtitleOutputBuffer;
                this.nextSubtitle = null;
                z = true;
            }
        }
        if (z) {
            Assertions.checkNotNull(this.subtitle);
            updateOutput(new CueGroup(this.subtitle.getCues(j), getPresentationTimeUs(getCurrentEventTimeUs(j))));
        }
        if (this.decoderReplacementState == 2) {
            return;
        }
        while (!this.inputStreamEnded) {
            try {
                SubtitleInputBuffer subtitleInputBufferDequeueInputBuffer = this.nextInputBuffer;
                if (subtitleInputBufferDequeueInputBuffer == null) {
                    subtitleInputBufferDequeueInputBuffer = ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).dequeueInputBuffer();
                    if (subtitleInputBufferDequeueInputBuffer == null) {
                        return;
                    } else {
                        this.nextInputBuffer = subtitleInputBufferDequeueInputBuffer;
                    }
                }
                if (this.decoderReplacementState == 1) {
                    subtitleInputBufferDequeueInputBuffer.setFlags(4);
                    ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(subtitleInputBufferDequeueInputBuffer);
                    this.nextInputBuffer = null;
                    this.decoderReplacementState = 2;
                    return;
                }
                int source = readSource(this.formatHolder, subtitleInputBufferDequeueInputBuffer, 0);
                if (source == -4) {
                    if (subtitleInputBufferDequeueInputBuffer.isEndOfStream()) {
                        this.inputStreamEnded = true;
                        this.waitingForKeyFrame = false;
                    } else {
                        Format format = this.formatHolder.format;
                        if (format == null) {
                            return;
                        }
                        subtitleInputBufferDequeueInputBuffer.subsampleOffsetUs = format.subsampleOffsetUs;
                        subtitleInputBufferDequeueInputBuffer.flip();
                        this.waitingForKeyFrame &= !subtitleInputBufferDequeueInputBuffer.isKeyFrame();
                    }
                    if (!this.waitingForKeyFrame) {
                        ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(subtitleInputBufferDequeueInputBuffer);
                        this.nextInputBuffer = null;
                    }
                } else if (source == -3) {
                    return;
                }
            } catch (SubtitleDecoderException e2) {
                handleDecoderError(e2);
                return;
            }
        }
    }

    public void setFinalStreamEndPositionUs(long j) {
        Assertions.checkState(isCurrentStreamFinal());
        this.finalStreamEndPositionUs = j;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return RendererCapabilities.CC.create(format.cryptoType == 0 ? 4 : 2);
        }
        return MimeTypes.isText(format.sampleMimeType) ? RendererCapabilities.CC.create(1) : RendererCapabilities.CC.create(0);
    }

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper, SubtitleDecoderFactory subtitleDecoderFactory) {
        super(3);
        this.output = (TextOutput) Assertions.checkNotNull(textOutput);
        this.outputHandler = looper == null ? null : Util.createHandler(looper, this);
        this.decoderFactory = subtitleDecoderFactory;
        this.formatHolder = new FormatHolder();
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastRendererPositionUs = C.TIME_UNSET;
    }
}
