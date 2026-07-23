package com.google.android.exoplayer2.audio;

import android.media.AudioDeviceInfo;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.analytics.PlayerId;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public interface AudioSink {
    public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
    public static final int SINK_FORMAT_SUPPORTED_DIRECTLY = 2;
    public static final int SINK_FORMAT_SUPPORTED_WITH_TRANSCODING = 1;
    public static final int SINK_FORMAT_UNSUPPORTED = 0;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.audio.AudioSink$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$setOutputStreamOffsetUs(AudioSink audioSink, long j) {
        }

        public static void $default$setPlayerId(AudioSink audioSink, @Nullable PlayerId playerId) {
        }

        @RequiresApi(23)
        public static void $default$setPreferredDevice(AudioSink audioSink, @Nullable AudioDeviceInfo audioDeviceInfo) {
        }
    }

    public static final class InitializationException extends Exception {
        public final int audioTrackState;
        public final Format format;
        public final boolean isRecoverable;

        public InitializationException(int i, int i2, int i3, int i4, Format format, boolean z, @Nullable Exception exc) {
            StringBuilder sb = new StringBuilder();
            sb.append("AudioTrack init failed ");
            sb.append(i);
            sb.append(" ");
            sb.append("Config(");
            sb.append(i2);
            sb.append(", ");
            sb.append(i3);
            sb.append(", ");
            sb.append(i4);
            sb.append(")");
            sb.append(" ");
            sb.append(format);
            sb.append(z ? " (recoverable)" : "");
            super(sb.toString(), exc);
            this.audioTrackState = i;
            this.isRecoverable = z;
            this.format = format;
        }
    }

    public interface Listener {

        /* JADX INFO: renamed from: com.google.android.exoplayer2.audio.AudioSink$Listener$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onAudioSinkError(Listener listener, Exception exc) {
            }

            public static void $default$onOffloadBufferEmptying(Listener listener) {
            }

            public static void $default$onOffloadBufferFull(Listener listener) {
            }

            public static void $default$onPositionAdvancing(Listener listener, long j) {
            }
        }

        void onAudioSinkError(Exception exc);

        void onOffloadBufferEmptying();

        void onOffloadBufferFull();

        void onPositionAdvancing(long j);

        void onPositionDiscontinuity();

        void onSkipSilenceEnabledChanged(boolean z);

        void onUnderrun(int i, long j, long j2);
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SinkFormatSupport {
    }

    public static final class UnexpectedDiscontinuityException extends Exception {
        public final long actualPresentationTimeUs;
        public final long expectedPresentationTimeUs;

        public UnexpectedDiscontinuityException(long j, long j2) {
            super("Unexpected audio track timestamp discontinuity: expected " + j2 + ", got " + j);
            this.actualPresentationTimeUs = j;
            this.expectedPresentationTimeUs = j2;
        }
    }

    public static final class WriteException extends Exception {
        public final int errorCode;
        public final Format format;
        public final boolean isRecoverable;

        public WriteException(int i, Format format, boolean z) {
            super(Insets$$ExternalSyntheticOutline0.m23m("AudioTrack write failed: ", i));
            this.isRecoverable = z;
            this.errorCode = i;
            this.format = format;
        }
    }

    void configure(Format format, int i, @Nullable int[] iArr) throws ConfigurationException;

    void disableTunneling();

    void enableTunnelingV21();

    void experimentalFlushWithoutAudioTrackRelease();

    void flush();

    @Nullable
    AudioAttributes getAudioAttributes();

    long getCurrentPositionUs(boolean z);

    int getFormatSupport(Format format);

    PlaybackParameters getPlaybackParameters();

    boolean getSkipSilenceEnabled();

    boolean handleBuffer(ByteBuffer byteBuffer, long j, int i) throws WriteException, InitializationException;

    void handleDiscontinuity();

    boolean hasPendingData();

    boolean isEnded();

    void pause();

    void play();

    void playToEndOfStream() throws WriteException;

    void reset();

    void setAudioAttributes(AudioAttributes audioAttributes);

    void setAudioSessionId(int i);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setListener(Listener listener);

    void setOutputStreamOffsetUs(long j);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setPlayerId(@Nullable PlayerId playerId);

    @RequiresApi(23)
    void setPreferredDevice(@Nullable AudioDeviceInfo audioDeviceInfo);

    void setSkipSilenceEnabled(boolean z);

    void setVolume(float f);

    boolean supportsFormat(Format format);

    public static final class ConfigurationException extends Exception {
        public final Format format;

        public ConfigurationException(Throwable th, Format format) {
            super(th);
            this.format = format;
        }

        public ConfigurationException(String str, Format format) {
            super(str);
            this.format = format;
        }
    }
}
