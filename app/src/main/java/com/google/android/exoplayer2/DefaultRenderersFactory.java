package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer;
import com.google.android.exoplayer2.mediacodec.DefaultMediaCodecAdapterFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionRenderer;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    public static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private static final String TAG = "DefaultRenderersFactory";
    private final Context context;
    private boolean enableAudioTrackPlaybackParams;
    private boolean enableDecoderFallback;
    private boolean enableFloatOutput;
    private boolean enableOffload;
    private final DefaultMediaCodecAdapterFactory codecAdapterFactory = new DefaultMediaCodecAdapterFactory();
    private int extensionRendererMode = 0;
    private long allowedVideoJoiningTimeMs = 5000;
    private MediaCodecSelector mediaCodecSelector = MediaCodecSelector.DEFAULT;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    public DefaultRenderersFactory(Context context) {
        this.context = context;
    }

    /* JADX WARN: Code duplicated, block: B:66:0x01f2  */
    @Override // com.google.android.exoplayer2.RenderersFactory
    public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ArrayList arrayList = new ArrayList();
        Context context = this.context;
        int i6 = this.extensionRendererMode;
        MediaCodecSelector mediaCodecSelector = this.mediaCodecSelector;
        boolean z = this.enableDecoderFallback;
        long j = this.allowedVideoJoiningTimeMs;
        arrayList.add(new MediaCodecVideoRenderer(context, this.codecAdapterFactory, mediaCodecSelector, j, z, handler, videoRendererEventListener, 50));
        if (i6 != 0) {
            int size = arrayList.size();
            if (i6 == 2) {
                size--;
            }
            try {
                try {
                    i5 = size + 1;
                    try {
                        arrayList.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j), handler, videoRendererEventListener, 50));
                        Log.i(TAG, "Loaded LibvpxVideoRenderer.");
                    } catch (ClassNotFoundException unused) {
                        size = i5;
                        i5 = size;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error instantiating VP9 extension", e);
                }
            } catch (ClassNotFoundException unused2) {
            }
            try {
                arrayList.add(i5, (Renderer) Class.forName("com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j), handler, videoRendererEventListener, 50));
                Log.i(TAG, "Loaded Libgav1VideoRenderer.");
            } catch (ClassNotFoundException unused3) {
            } catch (Exception e2) {
                throw new RuntimeException("Error instantiating AV1 extension", e2);
            }
        }
        DefaultAudioSink defaultAudioSinkBuild = new DefaultAudioSink.Builder().setAudioCapabilities(AudioCapabilities.getCapabilities(this.context)).setEnableFloatOutput(this.enableFloatOutput).setEnableAudioTrackPlaybackParams(this.enableAudioTrackPlaybackParams).setOffloadMode(this.enableOffload ? 1 : 0).build();
        if (defaultAudioSinkBuild != null) {
            Context context2 = this.context;
            int i7 = this.extensionRendererMode;
            arrayList.add(new MediaCodecAudioRenderer(context2, this.codecAdapterFactory, this.mediaCodecSelector, this.enableDecoderFallback, handler, audioRendererEventListener, defaultAudioSinkBuild));
            if (i7 == 0) {
                i = 0;
            } else {
                int size2 = arrayList.size();
                if (i7 == 2) {
                    size2--;
                }
                try {
                    try {
                        i = 0;
                        try {
                            i2 = size2 + 1;
                            try {
                                arrayList.add(size2, (Renderer) Class.forName("com.google.android.exoplayer2.decoder.midi.MidiRenderer").getConstructor(new Class[0]).newInstance(new Object[0]));
                                Log.i(TAG, "Loaded MidiRenderer.");
                            } catch (ClassNotFoundException unused4) {
                                size2 = i2;
                                i2 = size2;
                            }
                        } catch (ClassNotFoundException unused5) {
                        }
                    } catch (Exception e3) {
                        throw new RuntimeException("Error instantiating MIDI extension", e3);
                    }
                } catch (ClassNotFoundException unused6) {
                    i = 0;
                }
                try {
                    try {
                        Class<?> cls = Class.forName("com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer");
                        Class<?>[] clsArr = new Class[3];
                        clsArr[i] = Handler.class;
                        clsArr[1] = AudioRendererEventListener.class;
                        clsArr[2] = AudioSink.class;
                        Constructor<?> constructor = cls.getConstructor(clsArr);
                        Object[] objArr = new Object[3];
                        objArr[i] = handler;
                        objArr[1] = audioRendererEventListener;
                        objArr[2] = defaultAudioSinkBuild;
                        i3 = i2 + 1;
                        try {
                            arrayList.add(i2, (Renderer) constructor.newInstance(objArr));
                            Log.i(TAG, "Loaded LibopusAudioRenderer.");
                        } catch (ClassNotFoundException unused7) {
                            i2 = i3;
                            i3 = i2;
                        }
                    } catch (Exception e4) {
                        throw new RuntimeException("Error instantiating Opus extension", e4);
                    }
                } catch (ClassNotFoundException unused8) {
                }
                try {
                    try {
                        Class<?> cls2 = Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer");
                        Class<?>[] clsArr2 = new Class[3];
                        clsArr2[i] = Handler.class;
                        clsArr2[1] = AudioRendererEventListener.class;
                        clsArr2[2] = AudioSink.class;
                        Constructor<?> constructor2 = cls2.getConstructor(clsArr2);
                        Object[] objArr2 = new Object[3];
                        objArr2[i] = handler;
                        objArr2[1] = audioRendererEventListener;
                        objArr2[2] = defaultAudioSinkBuild;
                        i4 = i3 + 1;
                        try {
                            arrayList.add(i3, (Renderer) constructor2.newInstance(objArr2));
                            Log.i(TAG, "Loaded LibflacAudioRenderer.");
                        } catch (ClassNotFoundException unused9) {
                            i3 = i4;
                            i4 = i3;
                        }
                    } catch (Exception e5) {
                        throw new RuntimeException("Error instantiating FLAC extension", e5);
                    }
                } catch (ClassNotFoundException unused10) {
                }
                try {
                    Class[] clsArr3 = new Class[3];
                    clsArr3[i] = Handler.class;
                    clsArr3[1] = AudioRendererEventListener.class;
                    clsArr3[2] = AudioSink.class;
                    Constructor constructor3 = FfmpegAudioRenderer.class.getConstructor(clsArr3);
                    Object[] objArr3 = new Object[3];
                    objArr3[i] = handler;
                    objArr3[1] = audioRendererEventListener;
                    objArr3[2] = defaultAudioSinkBuild;
                    arrayList.add(i4, (Renderer) constructor3.newInstance(objArr3));
                    Log.i(TAG, "Loaded FfmpegAudioRenderer.");
                } catch (ClassNotFoundException unused11) {
                } catch (Exception e6) {
                    throw new RuntimeException("Error instantiating FFmpeg extension", e6);
                }
            }
        } else {
            i = 0;
        }
        arrayList.add(new TextRenderer(textOutput, handler.getLooper()));
        arrayList.add(new MetadataRenderer(metadataOutput, handler.getLooper()));
        arrayList.add(new CameraMotionRenderer());
        return (Renderer[]) arrayList.toArray(new Renderer[i]);
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z) {
        this.codecAdapterFactory.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(z);
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory forceDisableMediaCodecAsynchronousQueueing() {
        this.codecAdapterFactory.forceDisableAsynchronous();
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory forceEnableMediaCodecAsynchronousQueueing() {
        this.codecAdapterFactory.forceEnableAsynchronous();
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setAllowedVideoJoiningTimeMs(long j) {
        this.allowedVideoJoiningTimeMs = j;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setEnableAudioFloatOutput(boolean z) {
        this.enableFloatOutput = z;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setEnableAudioOffload(boolean z) {
        this.enableOffload = z;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setEnableAudioTrackPlaybackParams(boolean z) {
        this.enableAudioTrackPlaybackParams = z;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setEnableDecoderFallback(boolean z) {
        this.enableDecoderFallback = z;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setExtensionRendererMode(int i) {
        this.extensionRendererMode = i;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultRenderersFactory setMediaCodecSelector(MediaCodecSelector mediaCodecSelector) {
        this.mediaCodecSelector = mediaCodecSelector;
        return this;
    }
}
