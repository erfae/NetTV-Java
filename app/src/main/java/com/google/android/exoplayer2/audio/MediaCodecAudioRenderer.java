package com.google.android.exoplayer2.audio;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import androidx.annotation.CallSuper;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MediaCodecAudioRenderer extends MediaCodecRenderer implements MediaClock {
    private static final String TAG = "MediaCodecAudioRenderer";
    private static final String VIVO_BITS_PER_SAMPLE_KEY = "v-bits-per-sample";
    private boolean allowFirstBufferPositionDiscontinuity;
    private boolean allowPositionDiscontinuity;
    private final AudioSink audioSink;
    private boolean audioSinkNeedsReset;
    private int codecMaxInputSize;
    private boolean codecNeedsDiscardChannelsWorkaround;
    private final Context context;
    private long currentPositionUs;

    @Nullable
    private Format decryptOnlyCodecFormat;
    private final AudioRendererEventListener.EventDispatcher eventDispatcher;
    private boolean experimentalKeepAudioTrackOnSeek;

    @Nullable
    private Format inputFormat;

    @Nullable
    private Renderer.WakeupListener wakeupListener;

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
            Log.e(MediaCodecAudioRenderer.TAG, "Audio sink error", exc);
            MediaCodecAudioRenderer.this.eventDispatcher.audioSinkError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onOffloadBufferEmptying() {
            if (MediaCodecAudioRenderer.this.wakeupListener != null) {
                MediaCodecAudioRenderer.this.wakeupListener.onWakeup();
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onOffloadBufferFull() {
            if (MediaCodecAudioRenderer.this.wakeupListener != null) {
                MediaCodecAudioRenderer.this.wakeupListener.onSleep();
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionAdvancing(long j) {
            MediaCodecAudioRenderer.this.eventDispatcher.positionAdvancing(j);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionDiscontinuity() {
            MediaCodecAudioRenderer.this.onPositionDiscontinuity();
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onSkipSilenceEnabledChanged(boolean z) {
            MediaCodecAudioRenderer.this.eventDispatcher.skipSilenceEnabledChanged(z);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onUnderrun(int i, long j, long j2) {
            MediaCodecAudioRenderer.this.eventDispatcher.underrun(i, j, j2);
        }
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, null, null);
    }

    private static boolean codecNeedsDiscardChannelsWorkaround(String str) {
        if (Util.SDK_INT < 24 && "OMX.SEC.aac.dec".equals(str) && "samsung".equals(Util.MANUFACTURER)) {
            String str2 = Util.DEVICE;
            if (str2.startsWith("zeroflte") || str2.startsWith("herolte") || str2.startsWith("heroqlte")) {
                return true;
            }
        }
        return false;
    }

    private static boolean deviceDoesntSupportOperatingRate() {
        if (Util.SDK_INT == 23) {
            String str = Util.MODEL;
            if ("ZTE B2017G".equals(str) || "AXON 7 mini".equals(str)) {
                return true;
            }
        }
        return false;
    }

    private int getCodecMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        int i;
        if (!"OMX.google.raw.decoder".equals(mediaCodecInfo.name) || (i = Util.SDK_INT) >= 24 || (i == 23 && Util.isTv(this.context))) {
            return format.maxInputSize;
        }
        return -1;
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

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final DecoderReuseEvaluation canReuseCodec(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        DecoderReuseEvaluation decoderReuseEvaluationCanReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i = decoderReuseEvaluationCanReuseCodec.discardReasons;
        if (getCodecMaxInputSize(mediaCodecInfo, format2) > this.codecMaxInputSize) {
            i |= 64;
        }
        int i2 = i;
        return new DecoderReuseEvaluation(mediaCodecInfo.name, format, format2, i2 != 0 ? 0 : decoderReuseEvaluationCanReuseCodec.result, i2);
    }

    public void experimentalSetEnableKeepAudioTrackOnSeek(boolean z) {
        this.experimentalKeepAudioTrackOnSeek = z;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final float getCodecOperatingRateV23(float f, Format[] formatArr) {
        int iMax = -1;
        for (Format format : formatArr) {
            int i = format.sampleRate;
            if (i != -1) {
                iMax = Math.max(iMax, i);
            }
        }
        if (iMax == -1) {
            return -1.0f;
        }
        return f * iMax;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException {
        return MediaCodecUtil.getDecoderInfosSortedByFormatSupport(getDecoderInfos(mediaCodecSelector, format, z, this.audioSink), format);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    @Nullable
    public MediaClock getMediaClock() {
        return this;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f) {
        Format[] streamFormats = getStreamFormats();
        int codecMaxInputSize = getCodecMaxInputSize(mediaCodecInfo, format);
        boolean z = false;
        if (streamFormats.length != 1) {
            for (Format format2 : streamFormats) {
                if (mediaCodecInfo.canReuseCodec(format, format2).result != 0) {
                    codecMaxInputSize = Math.max(codecMaxInputSize, getCodecMaxInputSize(mediaCodecInfo, format2));
                }
            }
        }
        this.codecMaxInputSize = codecMaxInputSize;
        this.codecNeedsDiscardChannelsWorkaround = codecNeedsDiscardChannelsWorkaround(mediaCodecInfo.name);
        String str = mediaCodecInfo.codecMimeType;
        int i = this.codecMaxInputSize;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger("channel-count", format.channelCount);
        mediaFormat.setInteger("sample-rate", format.sampleRate);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", i);
        int i2 = Util.SDK_INT;
        if (i2 >= 23) {
            mediaFormat.setInteger("priority", 0);
            if (f != -1.0f && !deviceDoesntSupportOperatingRate()) {
                mediaFormat.setFloat("operating-rate", f);
            }
        }
        if (i2 <= 28 && MimeTypes.AUDIO_AC4.equals(format.sampleMimeType)) {
            mediaFormat.setInteger("ac4-is-sync", 1);
        }
        if (i2 >= 24 && this.audioSink.getFormatSupport(Util.getPcmFormat(4, format.channelCount, format.sampleRate)) == 2) {
            mediaFormat.setInteger("pcm-encoding", 4);
        }
        if (i2 >= 32) {
            mediaFormat.setInteger("max-output-channel-count", 99);
        }
        if (MimeTypes.AUDIO_RAW.equals(mediaCodecInfo.mimeType) && !MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            z = true;
        }
        this.decryptOnlyCodecFormat = z ? format : null;
        return MediaCodecAdapter.Configuration.createForAudioDecoding(mediaCodecInfo, mediaFormat, format, mediaCrypto);
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

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

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 2) {
            this.audioSink.setVolume(((Float) obj).floatValue());
        }
        if (i == 3) {
            this.audioSink.setAudioAttributes((AudioAttributes) obj);
            return;
        }
        if (i == 6) {
            this.audioSink.setAuxEffectInfo((AuxEffectInfo) obj);
            return;
        }
        switch (i) {
            case 9:
                this.audioSink.setSkipSilenceEnabled(((Boolean) obj).booleanValue());
                break;
            case 10:
                this.audioSink.setAudioSessionId(((Integer) obj).intValue());
                break;
            case 11:
                this.wakeupListener = (Renderer.WakeupListener) obj;
                break;
            case 12:
                if (Util.SDK_INT >= 23) {
                    Api23.setAudioSinkPreferredDevice(this.audioSink, obj);
                }
                break;
            default:
                super.handleMessage(i, obj);
                break;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return super.isEnded() && this.audioSink.isEnded();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return this.audioSink.hasPendingData() || super.isReady();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onCodecError(Exception exc) {
        Log.e(TAG, "Audio codec error", exc);
        this.eventDispatcher.audioCodecError(exc);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onCodecReleased(String str) {
        this.eventDispatcher.decoderReleased(str);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onDisabled() {
        this.audioSinkNeedsReset = true;
        this.inputFormat = null;
        try {
            this.audioSink.flush();
            try {
                super.onDisabled();
            } finally {
                this.eventDispatcher.disabled(this.decoderCounters);
            }
        } catch (Throwable th) {
            try {
                super.onDisabled();
                throw th;
            } finally {
                this.eventDispatcher.disabled(this.decoderCounters);
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        super.onEnabled(z, z2);
        this.eventDispatcher.enabled(this.decoderCounters);
        if (getConfiguration().tunneling) {
            this.audioSink.enableTunnelingV21();
        } else {
            this.audioSink.disableTunneling();
        }
        this.audioSink.setPlayerId(getPlayerId());
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @Nullable
    public final DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        this.inputFormat = (Format) Assertions.checkNotNull(formatHolder.format);
        DecoderReuseEvaluation decoderReuseEvaluationOnInputFormatChanged = super.onInputFormatChanged(formatHolder);
        this.eventDispatcher.inputFormatChanged(this.inputFormat, decoderReuseEvaluationOnInputFormatChanged);
        return decoderReuseEvaluationOnInputFormatChanged;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onOutputFormatChanged(Format format, @Nullable MediaFormat mediaFormat) throws ExoPlaybackException {
        int pcmEncoding;
        int i;
        Format format2 = this.decryptOnlyCodecFormat;
        int[] iArr = null;
        if (format2 != null) {
            format = format2;
        } else if (getCodec() != null) {
            if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
                pcmEncoding = format.pcmEncoding;
            } else if (Util.SDK_INT < 24 || !mediaFormat.containsKey("pcm-encoding")) {
                pcmEncoding = mediaFormat.containsKey(VIVO_BITS_PER_SAMPLE_KEY) ? Util.getPcmEncoding(mediaFormat.getInteger(VIVO_BITS_PER_SAMPLE_KEY)) : 2;
            } else {
                pcmEncoding = mediaFormat.getInteger("pcm-encoding");
            }
            Format formatBuild = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setPcmEncoding(pcmEncoding).setEncoderDelay(format.encoderDelay).setEncoderPadding(format.encoderPadding).setChannelCount(mediaFormat.getInteger("channel-count")).setSampleRate(mediaFormat.getInteger("sample-rate")).build();
            if (this.codecNeedsDiscardChannelsWorkaround && formatBuild.channelCount == 6 && (i = format.channelCount) < 6) {
                iArr = new int[i];
                for (int i2 = 0; i2 < format.channelCount; i2++) {
                    iArr[i2] = i2;
                }
            }
            format = formatBuild;
        }
        try {
            this.audioSink.configure(format, 0, iArr);
        } catch (AudioSink.ConfigurationException e) {
            throw createRendererException(e, e.format, false, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onOutputStreamOffsetUsChanged(long j) {
        this.audioSink.setOutputStreamOffsetUs(j);
    }

    @CallSuper
    public final void onPositionDiscontinuity() {
        this.allowPositionDiscontinuity = true;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        super.onPositionReset(j, z);
        if (this.experimentalKeepAudioTrackOnSeek) {
            this.audioSink.experimentalFlushWithoutAudioTrackRelease();
        } else {
            this.audioSink.flush();
        }
        this.currentPositionUs = j;
        this.allowFirstBufferPositionDiscontinuity = true;
        this.allowPositionDiscontinuity = true;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onProcessedStreamChange() {
        this.audioSink.handleDiscontinuity();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        if (!this.allowFirstBufferPositionDiscontinuity || decoderInputBuffer.isDecodeOnly()) {
            return;
        }
        if (Math.abs(decoderInputBuffer.timeUs - this.currentPositionUs) > 500000) {
            this.currentPositionUs = decoderInputBuffer.timeUs;
        }
        this.allowFirstBufferPositionDiscontinuity = false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onReset() {
        try {
            super.onReset();
        } finally {
            if (this.audioSinkNeedsReset) {
                this.audioSinkNeedsReset = false;
                this.audioSink.reset();
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onStarted() {
        this.audioSink.play();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onStopped() {
        updateCurrentPosition();
        this.audioSink.pause();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final boolean processOutputBuffer(long j, long j2, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i, int i2, int i3, long j3, boolean z, boolean z2, Format format) throws ExoPlaybackException {
        Assertions.checkNotNull(byteBuffer);
        if (this.decryptOnlyCodecFormat != null && (i2 & 2) != 0) {
            ((MediaCodecAdapter) Assertions.checkNotNull(mediaCodecAdapter)).releaseOutputBuffer(i, false);
            return true;
        }
        if (z) {
            if (mediaCodecAdapter != null) {
                mediaCodecAdapter.releaseOutputBuffer(i, false);
            }
            this.decoderCounters.skippedOutputBufferCount += i3;
            this.audioSink.handleDiscontinuity();
            return true;
        }
        try {
            if (!this.audioSink.handleBuffer(byteBuffer, j3, i3)) {
                return false;
            }
            if (mediaCodecAdapter != null) {
                mediaCodecAdapter.releaseOutputBuffer(i, false);
            }
            this.decoderCounters.renderedOutputBufferCount += i3;
            return true;
        } catch (AudioSink.InitializationException e) {
            throw createRendererException(e, this.inputFormat, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
        } catch (AudioSink.WriteException e2) {
            throw createRendererException(e2, format, e2.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void renderToEndOfStream() throws ExoPlaybackException {
        try {
            this.audioSink.playToEndOfStream();
        } catch (AudioSink.WriteException e) {
            throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
        }
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.audioSink.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final boolean shouldUseBypass(Format format) {
        return this.audioSink.supportsFormat(format);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        boolean z;
        if (!MimeTypes.isAudio(format.sampleMimeType)) {
            return RendererCapabilities.CC.create(0);
        }
        int i = Util.SDK_INT >= 21 ? 32 : 0;
        int i2 = format.cryptoType;
        boolean z2 = true;
        boolean z3 = i2 != 0;
        boolean z4 = i2 == 0 || i2 == 2;
        int i3 = 8;
        if (z4 && this.audioSink.supportsFormat(format) && (!z3 || MediaCodecUtil.getDecryptOnlyDecoderInfo() != null)) {
            return RendererCapabilities.CC.create(4, 8, i);
        }
        if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType) && !this.audioSink.supportsFormat(format)) {
            return RendererCapabilities.CC.create(1);
        }
        if (!this.audioSink.supportsFormat(Util.getPcmFormat(2, format.channelCount, format.sampleRate))) {
            return RendererCapabilities.CC.create(1);
        }
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mediaCodecSelector, format, false, this.audioSink);
        if (decoderInfos.isEmpty()) {
            return RendererCapabilities.CC.create(1);
        }
        if (!z4) {
            return RendererCapabilities.CC.create(2);
        }
        MediaCodecInfo mediaCodecInfo = decoderInfos.get(0);
        boolean zIsFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (!zIsFormatSupported) {
            int i4 = 1;
            while (true) {
                if (i4 >= decoderInfos.size()) {
                    z2 = zIsFormatSupported;
                    z = true;
                    break;
                }
                MediaCodecInfo mediaCodecInfo2 = decoderInfos.get(i4);
                if (mediaCodecInfo2.isFormatSupported(format)) {
                    mediaCodecInfo = mediaCodecInfo2;
                    z = false;
                    break;
                }
                i4++;
            }
        } else {
            z2 = zIsFormatSupported;
            z = true;
            break;
        }
        int i5 = z2 ? 4 : 3;
        if (z2 && mediaCodecInfo.isSeamlessAdaptationSupported(format)) {
            i3 = 16;
        }
        return RendererCapabilities.CC.create(i5, i3, i, mediaCodecInfo.hardwareAccelerated ? 64 : 0, z ? 128 : 0);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener) {
        this(context, mediaCodecSelector, handler, audioRendererEventListener, AudioCapabilities.DEFAULT_AUDIO_CAPABILITIES, new AudioProcessor[0]);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        this(context, mediaCodecSelector, handler, audioRendererEventListener, new DefaultAudioSink.Builder().setAudioCapabilities((AudioCapabilities) MoreObjects.firstNonNull(audioCapabilities, AudioCapabilities.DEFAULT_AUDIO_CAPABILITIES)).setAudioProcessors(audioProcessorArr).build());
    }

    private static List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z, AudioSink audioSink) throws MediaCodecUtil.DecoderQueryException {
        MediaCodecInfo decryptOnlyDecoderInfo;
        String str = format.sampleMimeType;
        if (str == null) {
            return ImmutableList.of();
        }
        if (audioSink.supportsFormat(format) && (decryptOnlyDecoderInfo = MediaCodecUtil.getDecryptOnlyDecoderInfo()) != null) {
            return ImmutableList.of(decryptOnlyDecoderInfo);
        }
        List<MediaCodecInfo> decoderInfos = mediaCodecSelector.getDecoderInfos(str, z, false);
        String alternativeCodecMimeType = MediaCodecUtil.getAlternativeCodecMimeType(format);
        if (alternativeCodecMimeType == null) {
            return ImmutableList.copyOf((Collection) decoderInfos);
        }
        return ImmutableList.builder().addAll((Iterable) decoderInfos).addAll((Iterable) mediaCodecSelector.getDecoderInfos(alternativeCodecMimeType, z, false)).build();
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, false, handler, audioRendererEventListener, audioSink);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, boolean z, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, z, handler, audioRendererEventListener, audioSink);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, boolean z, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(1, factory, mediaCodecSelector, z, 44100.0f);
        this.context = context.getApplicationContext();
        this.audioSink = audioSink;
        this.eventDispatcher = new AudioRendererEventListener.EventDispatcher(handler, audioRendererEventListener);
        audioSink.setListener(new AudioSinkListener());
    }
}
