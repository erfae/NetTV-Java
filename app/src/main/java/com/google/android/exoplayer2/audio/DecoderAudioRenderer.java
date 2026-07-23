package com.google.android.exoplayer2.audio;

import android.media.AudioDeviceInfo;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.CallSuper;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.decoder.SimpleDecoderOutputBuffer;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.ForOverride;

/* JADX INFO: loaded from: classes.dex */
public abstract class DecoderAudioRenderer<T extends Decoder<DecoderInputBuffer, ? extends SimpleDecoderOutputBuffer, ? extends DecoderException>> extends BaseRenderer implements MediaClock {
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int REINITIALIZATION_STATE_NONE = 0;
    private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "DecoderAudioRenderer";
    private boolean allowFirstBufferPositionDiscontinuity;
    private boolean allowPositionDiscontinuity;
    private final AudioSink audioSink;
    private boolean audioTrackNeedsConfigure;
    private long currentPositionUs;

    @Nullable
    private T decoder;
    private DecoderCounters decoderCounters;

    @Nullable
    private DrmSession decoderDrmSession;
    private boolean decoderReceivedBuffers;
    private int decoderReinitializationState;
    private int encoderDelay;
    private int encoderPadding;
    private final AudioRendererEventListener.EventDispatcher eventDispatcher;
    private boolean experimentalKeepAudioTrackOnSeek;
    private boolean firstStreamSampleRead;
    private final DecoderInputBuffer flagsOnlyBuffer;

    @Nullable
    private DecoderInputBuffer inputBuffer;
    private Format inputFormat;
    private boolean inputStreamEnded;

    @Nullable
    private SimpleDecoderOutputBuffer outputBuffer;
    private boolean outputStreamEnded;
    private long outputStreamOffsetUs;
    private int pendingOutputStreamOffsetCount;
    private final long[] pendingOutputStreamOffsetsUs;

    @Nullable
    private DrmSession sourceDrmSession;

    @RequiresApi(23)
    public static final class Api23 {
        private Api23() {
        }

        @DoNotInline
        public static void setAudioSinkPreferredDevice(AudioSink audioSink, @Nullable Object obj) {
            audioSink.setPreferredDevice((AudioDeviceInfo) obj);
        }
    }

    public final class AudioSinkListener implements AudioSink.Listener {
        private AudioSinkListener() {
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onAudioSinkError(Exception exc) {
            Log.e(DecoderAudioRenderer.TAG, "Audio sink error", exc);
            DecoderAudioRenderer.this.eventDispatcher.audioSinkError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public final /* synthetic */ void onOffloadBufferEmptying() {
            AudioSink.Listener.CC.$default$onOffloadBufferEmptying(this);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public final /* synthetic */ void onOffloadBufferFull() {
            AudioSink.Listener.CC.$default$onOffloadBufferFull(this);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionAdvancing(long j) {
            DecoderAudioRenderer.this.eventDispatcher.positionAdvancing(j);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionDiscontinuity() {
            DecoderAudioRenderer.this.onPositionDiscontinuity();
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onSkipSilenceEnabledChanged(boolean z) {
            DecoderAudioRenderer.this.eventDispatcher.skipSilenceEnabledChanged(z);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onUnderrun(int i, long j, long j2) {
            DecoderAudioRenderer.this.eventDispatcher.underrun(i, j, j2);
        }
    }

    public DecoderAudioRenderer() {
        this((Handler) null, (AudioRendererEventListener) null, new AudioProcessor[0]);
    }

    private boolean drainOutputBuffer() throws ExoPlaybackException, AudioSink.WriteException, AudioSink.InitializationException, AudioSink.ConfigurationException, DecoderException {
        if (this.outputBuffer == null) {
            SimpleDecoderOutputBuffer simpleDecoderOutputBuffer = (SimpleDecoderOutputBuffer) this.decoder.dequeueOutputBuffer();
            this.outputBuffer = simpleDecoderOutputBuffer;
            if (simpleDecoderOutputBuffer == null) {
                return false;
            }
            int i = simpleDecoderOutputBuffer.skippedOutputBufferCount;
            if (i > 0) {
                this.decoderCounters.skippedOutputBufferCount += i;
                this.audioSink.handleDiscontinuity();
            }
            if (this.outputBuffer.isFirstSample()) {
                processFirstSampleOfStream();
            }
        }
        if (this.outputBuffer.isEndOfStream()) {
            if (this.decoderReinitializationState == 2) {
                releaseDecoder();
                maybeInitDecoder();
                this.audioTrackNeedsConfigure = true;
            } else {
                this.outputBuffer.release();
                this.outputBuffer = null;
                try {
                    processEndOfStream();
                } catch (AudioSink.WriteException e) {
                    throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
                }
            }
            return false;
        }
        if (this.audioTrackNeedsConfigure) {
            this.audioSink.configure(getOutputFormat(this.decoder).buildUpon().setEncoderDelay(this.encoderDelay).setEncoderPadding(this.encoderPadding).build(), 0, null);
            this.audioTrackNeedsConfigure = false;
        }
        AudioSink audioSink = this.audioSink;
        SimpleDecoderOutputBuffer simpleDecoderOutputBuffer2 = this.outputBuffer;
        if (!audioSink.handleBuffer(simpleDecoderOutputBuffer2.data, simpleDecoderOutputBuffer2.timeUs, 1)) {
            return false;
        }
        this.decoderCounters.renderedOutputBufferCount++;
        this.outputBuffer.release();
        this.outputBuffer = null;
        return true;
    }

    private boolean feedInputBuffer() throws ExoPlaybackException, DecoderException {
        T t = this.decoder;
        if (t == null || this.decoderReinitializationState == 2 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputBuffer == null) {
            DecoderInputBuffer decoderInputBuffer = (DecoderInputBuffer) t.dequeueInputBuffer();
            this.inputBuffer = decoderInputBuffer;
            if (decoderInputBuffer == null) {
                return false;
            }
        }
        if (this.decoderReinitializationState == 1) {
            this.inputBuffer.setFlags(4);
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.inputBuffer = null;
            this.decoderReinitializationState = 2;
            return false;
        }
        FormatHolder formatHolder = getFormatHolder();
        int source = readSource(formatHolder, this.inputBuffer, 0);
        if (source == -5) {
            onInputFormatChanged(formatHolder);
            return true;
        }
        if (source != -4) {
            if (source == -3) {
                return false;
            }
            throw new IllegalStateException();
        }
        if (this.inputBuffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.inputBuffer = null;
            return false;
        }
        if (!this.firstStreamSampleRead) {
            this.firstStreamSampleRead = true;
            this.inputBuffer.addFlag(C.BUFFER_FLAG_FIRST_SAMPLE);
        }
        this.inputBuffer.flip();
        DecoderInputBuffer decoderInputBuffer2 = this.inputBuffer;
        decoderInputBuffer2.format = this.inputFormat;
        if (this.allowFirstBufferPositionDiscontinuity && !decoderInputBuffer2.isDecodeOnly()) {
            if (Math.abs(decoderInputBuffer2.timeUs - this.currentPositionUs) > 500000) {
                this.currentPositionUs = decoderInputBuffer2.timeUs;
            }
            this.allowFirstBufferPositionDiscontinuity = false;
        }
        this.decoder.queueInputBuffer(this.inputBuffer);
        this.decoderReceivedBuffers = true;
        this.decoderCounters.queuedInputBufferCount++;
        this.inputBuffer = null;
        return true;
    }

    private void flushDecoder() throws ExoPlaybackException {
        if (this.decoderReinitializationState != 0) {
            releaseDecoder();
            maybeInitDecoder();
            return;
        }
        this.inputBuffer = null;
        SimpleDecoderOutputBuffer simpleDecoderOutputBuffer = this.outputBuffer;
        if (simpleDecoderOutputBuffer != null) {
            simpleDecoderOutputBuffer.release();
            this.outputBuffer = null;
        }
        this.decoder.flush();
        this.decoderReceivedBuffers = false;
    }

    private void maybeInitDecoder() throws ExoPlaybackException {
        if (this.decoder != null) {
            return;
        }
        setDecoderDrmSession(this.sourceDrmSession);
        DrmSession drmSession = this.decoderDrmSession;
        if (drmSession != null && drmSession.getCryptoConfig() == null && this.decoderDrmSession.getError() == null) {
            return;
        }
        try {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            TraceUtil.beginSection("createAudioDecoder");
            this.decoder = (T) createDecoder(this.inputFormat);
            TraceUtil.endSection();
            long jElapsedRealtime2 = SystemClock.elapsedRealtime();
            this.eventDispatcher.decoderInitialized(this.decoder.getName(), jElapsedRealtime2, jElapsedRealtime2 - jElapsedRealtime);
            this.decoderCounters.decoderInitCount++;
        } catch (DecoderException e) {
            Log.e(TAG, "Audio codec error", e);
            this.eventDispatcher.audioCodecError(e);
            throw createRendererException(e, this.inputFormat, false, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
        } catch (OutOfMemoryError e2) {
            throw createRendererException(e2, this.inputFormat, false, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
        }
    }

    private void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        setSourceDrmSession(formatHolder.drmSession);
        Format format2 = this.inputFormat;
        this.inputFormat = format;
        this.encoderDelay = format.encoderDelay;
        this.encoderPadding = format.encoderPadding;
        T t = this.decoder;
        if (t == null) {
            maybeInitDecoder();
            this.eventDispatcher.inputFormatChanged(this.inputFormat, null);
            return;
        }
        DecoderReuseEvaluation decoderReuseEvaluation = this.sourceDrmSession != this.decoderDrmSession ? new DecoderReuseEvaluation(t.getName(), format2, format, 0, 128) : new DecoderReuseEvaluation(t.getName(), format2, format, 0, 1);
        if (decoderReuseEvaluation.result == 0) {
            if (this.decoderReceivedBuffers) {
                this.decoderReinitializationState = 1;
            } else {
                releaseDecoder();
                maybeInitDecoder();
                this.audioTrackNeedsConfigure = true;
            }
        }
        this.eventDispatcher.inputFormatChanged(this.inputFormat, decoderReuseEvaluation);
    }

    private void processEndOfStream() throws AudioSink.WriteException {
        this.outputStreamEnded = true;
        this.audioSink.playToEndOfStream();
    }

    private void processFirstSampleOfStream() {
        this.audioSink.handleDiscontinuity();
        if (this.pendingOutputStreamOffsetCount != 0) {
            setOutputStreamOffsetUs(this.pendingOutputStreamOffsetsUs[0]);
            int i = this.pendingOutputStreamOffsetCount - 1;
            this.pendingOutputStreamOffsetCount = i;
            long[] jArr = this.pendingOutputStreamOffsetsUs;
            System.arraycopy(jArr, 1, jArr, 0, i);
        }
    }

    private void releaseDecoder() {
        this.inputBuffer = null;
        this.outputBuffer = null;
        this.decoderReinitializationState = 0;
        this.decoderReceivedBuffers = false;
        T t = this.decoder;
        if (t != null) {
            this.decoderCounters.decoderReleaseCount++;
            t.release();
            this.eventDispatcher.decoderReleased(this.decoder.getName());
            this.decoder = null;
        }
        setDecoderDrmSession(null);
    }

    private void setDecoderDrmSession(@Nullable DrmSession drmSession) {
        DrmSession.CC.replaceSession(this.decoderDrmSession, drmSession);
        this.decoderDrmSession = drmSession;
    }

    private void setOutputStreamOffsetUs(long j) {
        this.outputStreamOffsetUs = j;
        if (j != C.TIME_UNSET) {
            this.audioSink.setOutputStreamOffsetUs(j);
        }
    }

    private void setSourceDrmSession(@Nullable DrmSession drmSession) {
        DrmSession.CC.replaceSession(this.sourceDrmSession, drmSession);
        this.sourceDrmSession = drmSession;
    }

    private void updateCurrentPosition() {
        long currentPositionUs = this.audioSink.getCurrentPositionUs(isEnded());
        if (currentPositionUs != Long.MIN_VALUE) {
            if (!this.allowPositionDiscontinuity) {
                currentPositionUs = Math.max(this.currentPositionUs, currentPositionUs);
            }
            this.currentPositionUs = currentPositionUs;
            this.allowPositionDiscontinuity = false;
        }
    }

    @ForOverride
    public abstract Decoder createDecoder(Format format) throws DecoderException;

    public void experimentalSetEnableKeepAudioTrackOnSeek(boolean z) {
        this.experimentalKeepAudioTrackOnSeek = z;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    @Nullable
    public MediaClock getMediaClock() {
        return this;
    }

    @ForOverride
    public abstract Format getOutputFormat(T t);

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return this.audioSink.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        if (getState() == 2) {
            updateCurrentPosition();
        }
        return this.currentPositionUs;
    }

    public final int getSinkFormatSupport(Format format) {
        return this.audioSink.getFormatSupport(format);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 2) {
            this.audioSink.setVolume(((Float) obj).floatValue());
            return;
        }
        if (i == 3) {
            this.audioSink.setAudioAttributes((AudioAttributes) obj);
            return;
        }
        if (i == 6) {
            this.audioSink.setAuxEffectInfo((AuxEffectInfo) obj);
            return;
        }
        if (i == 12) {
            if (Util.SDK_INT >= 23) {
                Api23.setAudioSinkPreferredDevice(this.audioSink, obj);
            }
        } else if (i == 9) {
            this.audioSink.setSkipSilenceEnabled(((Boolean) obj).booleanValue());
        } else if (i != 10) {
            super.handleMessage(i, obj);
        } else {
            this.audioSink.setAudioSessionId(((Integer) obj).intValue());
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded && this.audioSink.isEnded();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return this.audioSink.hasPendingData() || (this.inputFormat != null && (isSourceReady() || this.outputBuffer != null));
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onDisabled() {
        this.inputFormat = null;
        this.audioTrackNeedsConfigure = true;
        setOutputStreamOffsetUs(C.TIME_UNSET);
        try {
            setSourceDrmSession(null);
            releaseDecoder();
            this.audioSink.reset();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        DecoderCounters decoderCounters = new DecoderCounters();
        this.decoderCounters = decoderCounters;
        this.eventDispatcher.enabled(decoderCounters);
        if (getConfiguration().tunneling) {
            this.audioSink.enableTunnelingV21();
        } else {
            this.audioSink.disableTunneling();
        }
        this.audioSink.setPlayerId(getPlayerId());
    }

    @CallSuper
    @ForOverride
    public final void onPositionDiscontinuity() {
        this.allowPositionDiscontinuity = true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        if (this.experimentalKeepAudioTrackOnSeek) {
            this.audioSink.experimentalFlushWithoutAudioTrackRelease();
        } else {
            this.audioSink.flush();
        }
        this.currentPositionUs = j;
        this.allowFirstBufferPositionDiscontinuity = true;
        this.allowPositionDiscontinuity = true;
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        if (this.decoder != null) {
            flushDecoder();
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onStarted() {
        this.audioSink.play();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onStopped() {
        updateCurrentPosition();
        this.audioSink.pause();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onStreamChanged(Format[] formatArr, long j, long j2) throws ExoPlaybackException {
        this.firstStreamSampleRead = false;
        if (this.outputStreamOffsetUs == C.TIME_UNSET) {
            setOutputStreamOffsetUs(j2);
            return;
        }
        int i = this.pendingOutputStreamOffsetCount;
        if (i == this.pendingOutputStreamOffsetsUs.length) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Too many stream changes, so dropping offset: ");
            sbM.append(this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1]);
            Log.w(TAG, sbM.toString());
        } else {
            this.pendingOutputStreamOffsetCount = i + 1;
        }
        this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1] = j2;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) throws ExoPlaybackException {
        if (this.outputStreamEnded) {
            try {
                this.audioSink.playToEndOfStream();
                return;
            } catch (AudioSink.WriteException e) {
                throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
            }
        }
        if (this.inputFormat == null) {
            FormatHolder formatHolder = getFormatHolder();
            this.flagsOnlyBuffer.clear();
            int source = readSource(formatHolder, this.flagsOnlyBuffer, 2);
            if (source != -5) {
                if (source == -4) {
                    Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                    this.inputStreamEnded = true;
                    try {
                        processEndOfStream();
                        return;
                    } catch (AudioSink.WriteException e2) {
                        throw createRendererException(e2, null, false, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
                    }
                }
                return;
            }
            onInputFormatChanged(formatHolder);
        }
        maybeInitDecoder();
        if (this.decoder != null) {
            try {
                TraceUtil.beginSection("drainAndFeed");
                while (drainOutputBuffer()) {
                }
                while (feedInputBuffer()) {
                }
                TraceUtil.endSection();
                this.decoderCounters.ensureUpdated();
            } catch (AudioSink.ConfigurationException e3) {
                throw createRendererException(e3, e3.format, false, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
            } catch (AudioSink.InitializationException e4) {
                throw createRendererException(e4, e4.format, e4.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
            } catch (AudioSink.WriteException e5) {
                throw createRendererException(e5, e5.format, e5.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
            } catch (DecoderException e6) {
                Log.e(TAG, "Audio codec error", e6);
                this.eventDispatcher.audioCodecError(e6);
                throw createRendererException(e6, this.inputFormat, false, PlaybackException.ERROR_CODE_DECODING_FAILED);
            }
        }
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.audioSink.setPlaybackParameters(playbackParameters);
    }

    public final boolean sinkSupportsFormat(Format format) {
        return this.audioSink.supportsFormat(format);
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public final int supportsFormat(Format format) {
        if (!MimeTypes.isAudio(format.sampleMimeType)) {
            return RendererCapabilities.CC.create(0);
        }
        int iSupportsFormatInternal = supportsFormatInternal(format);
        if (iSupportsFormatInternal <= 2) {
            return RendererCapabilities.CC.create(iSupportsFormatInternal);
        }
        return RendererCapabilities.CC.create(iSupportsFormatInternal, 8, Util.SDK_INT >= 21 ? 32 : 0);
    }

    @ForOverride
    public abstract int supportsFormatInternal(Format format);

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, null, audioProcessorArr);
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, new DefaultAudioSink.Builder().setAudioCapabilities((AudioCapabilities) MoreObjects.firstNonNull(audioCapabilities, AudioCapabilities.DEFAULT_AUDIO_CAPABILITIES)).setAudioProcessors(audioProcessorArr).build());
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(1);
        this.eventDispatcher = new AudioRendererEventListener.EventDispatcher(handler, audioRendererEventListener);
        this.audioSink = audioSink;
        audioSink.setListener(new AudioSinkListener());
        this.flagsOnlyBuffer = DecoderInputBuffer.newNoDataInstance();
        this.decoderReinitializationState = 0;
        this.audioTrackNeedsConfigure = true;
        setOutputStreamOffsetUs(C.TIME_UNSET);
        this.pendingOutputStreamOffsetsUs = new long[10];
    }
}
