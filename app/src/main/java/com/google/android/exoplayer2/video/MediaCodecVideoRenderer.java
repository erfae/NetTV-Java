package com.google.android.exoplayer2.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Display;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.common.Scopes;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.SignedBytes;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import okio.Utf8;

/* JADX INFO: loaded from: classes.dex */
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final int HEVC_MAX_INPUT_SIZE_THRESHOLD = 2097152;
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static final long TUNNELING_EOS_PRESENTATION_TIME_US = Long.MAX_VALUE;
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private boolean codecHandlesHdr10PlusOutOfBandMetadata;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsNoPostProcessWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;

    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private final VideoFrameReleaseHelper frameReleaseHelper;
    private boolean haveReportedFirstFrameRenderedForCurrentSurface;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastBufferPresentationTimeUs;
    private long lastFrameReleaseTimeNs;
    private long lastRenderRealtimeUs;
    private final int maxDroppedFramesToNotify;
    private boolean mayRenderFirstFrameAfterEnableIfNotStarted;

    @Nullable
    private PlaceholderSurface placeholderSurface;
    private boolean renderedFirstFrameAfterEnable;
    private boolean renderedFirstFrameAfterReset;

    @Nullable
    private VideoSize reportedVideoSize;
    private int scalingMode;

    @Nullable
    private Surface surface;
    private long totalVideoFrameProcessingOffsetUs;
    private boolean tunneling;
    private int tunnelingAudioSessionId;

    @Nullable
    public OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;
    private int videoFrameProcessingOffsetCount;

    @RequiresApi(26)
    public static final class Api26 {
        private Api26() {
        }

        @DoNotInline
        public static boolean doesDisplaySupportDolbyVision(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            Display display = displayManager != null ? displayManager.getDisplay(0) : null;
            if (display == null || !display.isHdr()) {
                return false;
            }
            for (int i : display.getHdrCapabilities().getSupportedHdrTypes()) {
                if (i == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.inputSize = i3;
        }
    }

    @RequiresApi(23)
    public final class OnFrameRenderedListenerV23 implements MediaCodecAdapter.OnFrameRenderedListener, Handler.Callback {
        private static final int HANDLE_FRAME_RENDERED = 0;
        private final Handler handler;

        public OnFrameRenderedListenerV23(MediaCodecAdapter mediaCodecAdapter) {
            Handler handlerCreateHandlerForCurrentLooper = Util.createHandlerForCurrentLooper(this);
            this.handler = handlerCreateHandlerForCurrentLooper;
            mediaCodecAdapter.setOnFrameRenderedListener(this, handlerCreateHandlerForCurrentLooper);
        }

        private void handleFrameRendered(long j) {
            MediaCodecVideoRenderer mediaCodecVideoRenderer = MediaCodecVideoRenderer.this;
            if (this != mediaCodecVideoRenderer.tunnelingOnFrameRenderedListener || mediaCodecVideoRenderer.getCodec() == null) {
                return;
            }
            if (j == Long.MAX_VALUE) {
                MediaCodecVideoRenderer.this.onProcessedTunneledEndOfStream();
                return;
            }
            try {
                MediaCodecVideoRenderer.this.onProcessedTunneledBuffer(j);
            } catch (ExoPlaybackException e) {
                MediaCodecVideoRenderer.this.setPendingPlaybackException(e);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            handleFrameRendered(Util.toLong(message.arg1, message.arg2));
            return true;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.OnFrameRenderedListener
        public void onFrameRendered(MediaCodecAdapter mediaCodecAdapter, long j, long j2) {
            if (Util.SDK_INT >= 30) {
                handleFrameRendered(j);
            } else {
                this.handler.sendMessageAtFrontOfQueue(Message.obtain(this.handler, 0, (int) (j >> 32), (int) j));
            }
        }
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0L);
    }

    private void clearRenderedFirstFrame() {
        MediaCodecAdapter codec;
        this.renderedFirstFrameAfterReset = false;
        if (Util.SDK_INT < 23 || !this.tunneling || (codec = getCodec()) == null) {
            return;
        }
        this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
    }

    private void clearReportedVideoSize() {
        this.reportedVideoSize = null;
    }

    @RequiresApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int i) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i);
    }

    private static boolean deviceNeedsNoPostProcessWorkaround() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:49:0x0097  */
    /* JADX WARN: Code duplicated, block: B:6:0x001b  */
    /* JADX WARN: Code duplicated, block: B:91:0x0111  */
    private static boolean evaluateDeviceNeedsSetOutputSurfaceWorkaround() {
        int i = Util.SDK_INT;
        byte b = Ascii.FS;
        if (i <= 28) {
            String str = Util.DEVICE;
            Objects.requireNonNull(str);
            switch (str) {
                case "dangal":
                case "dangalFHD":
                case "dangalUHD":
                case "oneday":
                case "aquaman":
                case "magnolia":
                case "once":
                case "machuca":
                    return true;
            }
        }
        if (i <= 27 && "HWEML".equals(Util.DEVICE)) {
            return true;
        }
        String str2 = Util.MODEL;
        Objects.requireNonNull(str2);
        switch (str2) {
            case "AFTJMST12":
            case "AFTKMST12":
            case "AFTA":
            case "AFTN":
            case "AFTR":
            case "AFTEU011":
            case "AFTEU014":
            case "AFTSO001":
            case "AFTEUFF014":
                return true;
            default:
                if (i <= 26) {
                    String str3 = Util.DEVICE;
                    Objects.requireNonNull(str3);
                    switch (str3.hashCode()) {
                        case -2144781245:
                            if (!str3.equals("GIONEE_SWW1609")) {
                                b = -1;
                            } else {
                                b = 0;
                            }
                            break;
                        case -2144781185:
                            if (!str3.equals("GIONEE_SWW1627")) {
                                b = -1;
                            } else {
                                b = 1;
                            }
                            break;
                        case -2144781160:
                            if (!str3.equals("GIONEE_SWW1631")) {
                                b = -1;
                            } else {
                                b = 2;
                            }
                            break;
                        case -2097309513:
                            if (!str3.equals("K50a40")) {
                                b = -1;
                            } else {
                                b = 3;
                            }
                            break;
                        case -2022874474:
                            if (!str3.equals("CP8676_I02")) {
                                b = -1;
                            } else {
                                b = 4;
                            }
                            break;
                        case -1978993182:
                            if (!str3.equals("NX541J")) {
                                b = -1;
                            } else {
                                b = 5;
                            }
                            break;
                        case -1978990237:
                            if (!str3.equals("NX573J")) {
                                b = -1;
                            } else {
                                b = 6;
                            }
                            break;
                        case -1936688988:
                            if (!str3.equals("PGN528")) {
                                b = -1;
                            } else {
                                b = 7;
                            }
                            break;
                        case -1936688066:
                            if (!str3.equals("PGN610")) {
                                b = -1;
                            } else {
                                b = 8;
                            }
                            break;
                        case -1936688065:
                            if (!str3.equals("PGN611")) {
                                b = -1;
                            } else {
                                b = 9;
                            }
                            break;
                        case -1931988508:
                            if (!str3.equals("AquaPowerM")) {
                                b = -1;
                            } else {
                                b = 10;
                            }
                            break;
                        case -1885099851:
                            if (!str3.equals("RAIJIN")) {
                                b = -1;
                            } else {
                                b = 11;
                            }
                            break;
                        case -1696512866:
                            if (!str3.equals("XT1663")) {
                                b = -1;
                            } else {
                                b = 12;
                            }
                            break;
                        case -1680025915:
                            if (!str3.equals("ComioS1")) {
                                b = -1;
                            } else {
                                b = 13;
                            }
                            break;
                        case -1615810839:
                            if (!str3.equals("Phantom6")) {
                                b = -1;
                            } else {
                                b = 14;
                            }
                            break;
                        case -1600724499:
                            if (!str3.equals("pacificrim")) {
                                b = -1;
                            } else {
                                b = 15;
                            }
                            break;
                        case -1554255044:
                            if (!str3.equals("vernee_M5")) {
                                b = -1;
                            } else {
                                b = 16;
                            }
                            break;
                        case -1481772737:
                            if (!str3.equals("panell_dl")) {
                                b = -1;
                            } else {
                                b = 17;
                            }
                            break;
                        case -1481772730:
                            if (!str3.equals("panell_ds")) {
                                b = -1;
                            } else {
                                b = 18;
                            }
                            break;
                        case -1481772729:
                            if (!str3.equals("panell_dt")) {
                                b = -1;
                            } else {
                                b = 19;
                            }
                            break;
                        case -1320080169:
                            if (!str3.equals("GiONEE_GBL7319")) {
                                b = -1;
                            } else {
                                b = Ascii.DC4;
                            }
                            break;
                        case -1217592143:
                            if (!str3.equals("BRAVIA_ATV2")) {
                                b = -1;
                            } else {
                                b = Ascii.NAK;
                            }
                            break;
                        case -1180384755:
                            if (!str3.equals("iris60")) {
                                b = -1;
                            } else {
                                b = Ascii.SYN;
                            }
                            break;
                        case -1139198265:
                            if (!str3.equals("Slate_Pro")) {
                                b = -1;
                            } else {
                                b = Ascii.ETB;
                            }
                            break;
                        case -1052835013:
                            if (!str3.equals("namath")) {
                                b = -1;
                            } else {
                                b = Ascii.CAN;
                            }
                            break;
                        case -993250464:
                            if (!str3.equals("A10-70F")) {
                                b = -1;
                            } else {
                                b = Ascii.EM;
                            }
                            break;
                        case -993250458:
                            if (!str3.equals("A10-70L")) {
                                b = -1;
                            } else {
                                b = Ascii.SUB;
                            }
                            break;
                        case -965403638:
                            if (!str3.equals("s905x018")) {
                                b = -1;
                            } else {
                                b = Ascii.ESC;
                            }
                            break;
                        case -958336948:
                            if (!str3.equals("ELUGA_Ray_X")) {
                                b = -1;
                            }
                            break;
                        case -879245230:
                            if (!str3.equals("tcl_eu")) {
                                b = -1;
                            } else {
                                b = Ascii.GS;
                            }
                            break;
                        case -842500323:
                            if (!str3.equals("nicklaus_f")) {
                                b = -1;
                            } else {
                                b = Ascii.RS;
                            }
                            break;
                        case -821392978:
                            if (!str3.equals("A7000-a")) {
                                b = -1;
                            } else {
                                b = Ascii.US;
                            }
                            break;
                        case -797483286:
                            if (!str3.equals("SVP-DTV15")) {
                                b = -1;
                            } else {
                                b = 32;
                            }
                            break;
                        case -794946968:
                            if (!str3.equals("watson")) {
                                b = -1;
                            } else {
                                b = 33;
                            }
                            break;
                        case -788334647:
                            if (!str3.equals("whyred")) {
                                b = -1;
                            } else {
                                b = 34;
                            }
                            break;
                        case -782144577:
                            if (!str3.equals("OnePlus5T")) {
                                b = -1;
                            } else {
                                b = 35;
                            }
                            break;
                        case -575125681:
                            if (!str3.equals("GiONEE_CBL7513")) {
                                b = -1;
                            } else {
                                b = 36;
                            }
                            break;
                        case -521118391:
                            if (!str3.equals("GIONEE_GBL7360")) {
                                b = -1;
                            } else {
                                b = 37;
                            }
                            break;
                        case -430914369:
                            if (!str3.equals("Pixi4-7_3G")) {
                                b = -1;
                            } else {
                                b = 38;
                            }
                            break;
                        case -290434366:
                            if (!str3.equals("taido_row")) {
                                b = -1;
                            } else {
                                b = 39;
                            }
                            break;
                        case -282781963:
                            if (!str3.equals("BLACK-1X")) {
                                b = -1;
                            } else {
                                b = 40;
                            }
                            break;
                        case -277133239:
                            if (!str3.equals("Z12_PRO")) {
                                b = -1;
                            } else {
                                b = 41;
                            }
                            break;
                        case -173639913:
                            if (!str3.equals("ELUGA_A3_Pro")) {
                                b = -1;
                            } else {
                                b = 42;
                            }
                            break;
                        case -56598463:
                            if (!str3.equals("woods_fn")) {
                                b = -1;
                            } else {
                                b = 43;
                            }
                            break;
                        case 2126:
                            if (!str3.equals("C1")) {
                                b = -1;
                            } else {
                                b = 44;
                            }
                            break;
                        case 2564:
                            if (!str3.equals("Q5")) {
                                b = -1;
                            } else {
                                b = 45;
                            }
                            break;
                        case 2715:
                            if (!str3.equals("V1")) {
                                b = -1;
                            } else {
                                b = 46;
                            }
                            break;
                        case 2719:
                            if (!str3.equals("V5")) {
                                b = -1;
                            } else {
                                b = 47;
                            }
                            break;
                        case 3091:
                            if (!str3.equals("b5")) {
                                b = -1;
                            } else {
                                b = 48;
                            }
                            break;
                        case 3483:
                            if (!str3.equals("mh")) {
                                b = -1;
                            } else {
                                b = 49;
                            }
                            break;
                        case 73405:
                            if (!str3.equals("JGZ")) {
                                b = -1;
                            } else {
                                b = 50;
                            }
                            break;
                        case 75537:
                            if (!str3.equals("M04")) {
                                b = -1;
                            } else {
                                b = 51;
                            }
                            break;
                        case 75739:
                            if (!str3.equals("M5c")) {
                                b = -1;
                            } else {
                                b = 52;
                            }
                            break;
                        case 76779:
                            if (!str3.equals("MX6")) {
                                b = -1;
                            } else {
                                b = 53;
                            }
                            break;
                        case 78669:
                            if (!str3.equals("P85")) {
                                b = -1;
                            } else {
                                b = 54;
                            }
                            break;
                        case 79305:
                            if (!str3.equals("PLE")) {
                                b = -1;
                            } else {
                                b = 55;
                            }
                            break;
                        case 80618:
                            if (!str3.equals("QX1")) {
                                b = -1;
                            } else {
                                b = 56;
                            }
                            break;
                        case 88274:
                            if (!str3.equals("Z80")) {
                                b = -1;
                            } else {
                                b = 57;
                            }
                            break;
                        case 98846:
                            if (!str3.equals("cv1")) {
                                b = -1;
                            } else {
                                b = 58;
                            }
                            break;
                        case 98848:
                            if (!str3.equals("cv3")) {
                                b = -1;
                            } else {
                                b = 59;
                            }
                            break;
                        case 99329:
                            if (!str3.equals("deb")) {
                                b = -1;
                            } else {
                                b = 60;
                            }
                            break;
                        case 101481:
                            if (!str3.equals("flo")) {
                                b = -1;
                            } else {
                                b = 61;
                            }
                            break;
                        case 1513190:
                            if (!str3.equals("1601")) {
                                b = -1;
                            } else {
                                b = 62;
                            }
                            break;
                        case 1514184:
                            if (!str3.equals("1713")) {
                                b = -1;
                            } else {
                                b = Utf8.REPLACEMENT_BYTE;
                            }
                            break;
                        case 1514185:
                            if (!str3.equals("1714")) {
                                b = -1;
                            } else {
                                b = SignedBytes.MAX_POWER_OF_TWO;
                            }
                            break;
                        case 2133089:
                            if (!str3.equals("F01H")) {
                                b = -1;
                            } else {
                                b = 65;
                            }
                            break;
                        case 2133091:
                            if (!str3.equals("F01J")) {
                                b = -1;
                            } else {
                                b = 66;
                            }
                            break;
                        case 2133120:
                            if (!str3.equals("F02H")) {
                                b = -1;
                            } else {
                                b = 67;
                            }
                            break;
                        case 2133151:
                            if (!str3.equals("F03H")) {
                                b = -1;
                            } else {
                                b = 68;
                            }
                            break;
                        case 2133182:
                            if (!str3.equals("F04H")) {
                                b = -1;
                            } else {
                                b = 69;
                            }
                            break;
                        case 2133184:
                            if (!str3.equals("F04J")) {
                                b = -1;
                            } else {
                                b = 70;
                            }
                            break;
                        case 2436959:
                            if (!str3.equals("P681")) {
                                b = -1;
                            } else {
                                b = 71;
                            }
                            break;
                        case 2463773:
                            if (!str3.equals("Q350")) {
                                b = -1;
                            } else {
                                b = 72;
                            }
                            break;
                        case 2464648:
                            if (!str3.equals("Q427")) {
                                b = -1;
                            } else {
                                b = 73;
                            }
                            break;
                        case 2689555:
                            if (!str3.equals("XE2X")) {
                                b = -1;
                            } else {
                                b = 74;
                            }
                            break;
                        case 3154429:
                            if (!str3.equals("fugu")) {
                                b = -1;
                            } else {
                                b = 75;
                            }
                            break;
                        case 3284551:
                            if (!str3.equals("kate")) {
                                b = -1;
                            } else {
                                b = 76;
                            }
                            break;
                        case 3351335:
                            if (!str3.equals("mido")) {
                                b = -1;
                            } else {
                                b = 77;
                            }
                            break;
                        case 3386211:
                            if (!str3.equals("p212")) {
                                b = -1;
                            } else {
                                b = 78;
                            }
                            break;
                        case 41325051:
                            if (!str3.equals("MEIZU_M5")) {
                                b = -1;
                            } else {
                                b = 79;
                            }
                            break;
                        case 51349633:
                            if (!str3.equals("601LV")) {
                                b = -1;
                            } else {
                                b = 80;
                            }
                            break;
                        case 51350594:
                            if (!str3.equals("602LV")) {
                                b = -1;
                            } else {
                                b = 81;
                            }
                            break;
                        case 55178625:
                            if (!str3.equals("Aura_Note_2")) {
                                b = -1;
                            } else {
                                b = 82;
                            }
                            break;
                        case 61542055:
                            if (!str3.equals("A1601")) {
                                b = -1;
                            } else {
                                b = 83;
                            }
                            break;
                        case 65355429:
                            if (!str3.equals("E5643")) {
                                b = -1;
                            } else {
                                b = 84;
                            }
                            break;
                        case 66214468:
                            if (!str3.equals("F3111")) {
                                b = -1;
                            } else {
                                b = 85;
                            }
                            break;
                        case 66214470:
                            if (!str3.equals("F3113")) {
                                b = -1;
                            } else {
                                b = 86;
                            }
                            break;
                        case 66214473:
                            if (!str3.equals("F3116")) {
                                b = -1;
                            } else {
                                b = 87;
                            }
                            break;
                        case 66215429:
                            if (!str3.equals("F3211")) {
                                b = -1;
                            } else {
                                b = 88;
                            }
                            break;
                        case 66215431:
                            if (!str3.equals("F3213")) {
                                b = -1;
                            } else {
                                b = 89;
                            }
                            break;
                        case 66215433:
                            if (!str3.equals("F3215")) {
                                b = -1;
                            } else {
                                b = 90;
                            }
                            break;
                        case 66216390:
                            if (!str3.equals("F3311")) {
                                b = -1;
                            } else {
                                b = 91;
                            }
                            break;
                        case 76402249:
                            if (!str3.equals("PRO7S")) {
                                b = -1;
                            } else {
                                b = 92;
                            }
                            break;
                        case 76404105:
                            if (!str3.equals("Q4260")) {
                                b = -1;
                            } else {
                                b = 93;
                            }
                            break;
                        case 76404911:
                            if (!str3.equals("Q4310")) {
                                b = -1;
                            } else {
                                b = 94;
                            }
                            break;
                        case 80963634:
                            if (!str3.equals("V23GB")) {
                                b = -1;
                            } else {
                                b = 95;
                            }
                            break;
                        case 82882791:
                            if (!str3.equals("X3_HK")) {
                                b = -1;
                            } else {
                                b = 96;
                            }
                            break;
                        case 98715550:
                            if (!str3.equals("i9031")) {
                                b = -1;
                            } else {
                                b = 97;
                            }
                            break;
                        case 101370885:
                            if (!str3.equals("l5460")) {
                                b = -1;
                            } else {
                                b = 98;
                            }
                            break;
                        case 102844228:
                            if (!str3.equals("le_x6")) {
                                b = -1;
                            } else {
                                b = 99;
                            }
                            break;
                        case 165221241:
                            if (!str3.equals("A2016a40")) {
                                b = -1;
                            } else {
                                b = 100;
                            }
                            break;
                        case 182191441:
                            if (!str3.equals("CPY83_I00")) {
                                b = -1;
                            } else {
                                b = 101;
                            }
                            break;
                        case 245388979:
                            if (!str3.equals("marino_f")) {
                                b = -1;
                            } else {
                                b = 102;
                            }
                            break;
                        case 287431619:
                            if (!str3.equals("griffin")) {
                                b = -1;
                            } else {
                                b = 103;
                            }
                            break;
                        case 307593612:
                            if (!str3.equals("A7010a48")) {
                                b = -1;
                            } else {
                                b = 104;
                            }
                            break;
                        case 308517133:
                            if (!str3.equals("A7020a48")) {
                                b = -1;
                            } else {
                                b = 105;
                            }
                            break;
                        case 316215098:
                            if (!str3.equals("TB3-730F")) {
                                b = -1;
                            } else {
                                b = 106;
                            }
                            break;
                        case 316215116:
                            if (!str3.equals("TB3-730X")) {
                                b = -1;
                            } else {
                                b = 107;
                            }
                            break;
                        case 316246811:
                            if (!str3.equals("TB3-850F")) {
                                b = -1;
                            } else {
                                b = 108;
                            }
                            break;
                        case 316246818:
                            if (!str3.equals("TB3-850M")) {
                                b = -1;
                            } else {
                                b = 109;
                            }
                            break;
                        case 407160593:
                            if (!str3.equals("Pixi5-10_4G")) {
                                b = -1;
                            } else {
                                b = 110;
                            }
                            break;
                        case 507412548:
                            if (!str3.equals("QM16XE_U")) {
                                b = -1;
                            } else {
                                b = 111;
                            }
                            break;
                        case 793982701:
                            if (!str3.equals("GIONEE_WBL5708")) {
                                b = -1;
                            } else {
                                b = 112;
                            }
                            break;
                        case 794038622:
                            if (!str3.equals("GIONEE_WBL7365")) {
                                b = -1;
                            } else {
                                b = 113;
                            }
                            break;
                        case 794040393:
                            if (!str3.equals("GIONEE_WBL7519")) {
                                b = -1;
                            } else {
                                b = 114;
                            }
                            break;
                        case 835649806:
                            if (!str3.equals("manning")) {
                                b = -1;
                            } else {
                                b = 115;
                            }
                            break;
                        case 917340916:
                            if (!str3.equals("A7000plus")) {
                                b = -1;
                            } else {
                                b = 116;
                            }
                            break;
                        case 958008161:
                            if (!str3.equals("j2xlteins")) {
                                b = -1;
                            } else {
                                b = 117;
                            }
                            break;
                        case 1060579533:
                            if (!str3.equals("panell_d")) {
                                b = -1;
                            } else {
                                b = 118;
                            }
                            break;
                        case 1150207623:
                            if (!str3.equals("LS-5017")) {
                                b = -1;
                            } else {
                                b = 119;
                            }
                            break;
                        case 1176899427:
                            if (!str3.equals("itel_S41")) {
                                b = -1;
                            } else {
                                b = 120;
                            }
                            break;
                        case 1280332038:
                            if (!str3.equals("hwALE-H")) {
                                b = -1;
                            } else {
                                b = 121;
                            }
                            break;
                        case 1306947716:
                            if (!str3.equals("EverStar_S")) {
                                b = -1;
                            } else {
                                b = 122;
                            }
                            break;
                        case 1349174697:
                            if (!str3.equals("htc_e56ml_dtul")) {
                                b = -1;
                            } else {
                                b = 123;
                            }
                            break;
                        case 1522194893:
                            if (!str3.equals("woods_f")) {
                                b = -1;
                            } else {
                                b = 124;
                            }
                            break;
                        case 1691543273:
                            if (!str3.equals("CPH1609")) {
                                b = -1;
                            } else {
                                b = 125;
                            }
                            break;
                        case 1691544261:
                            if (!str3.equals("CPH1715")) {
                                b = -1;
                            } else {
                                b = 126;
                            }
                            break;
                        case 1709443163:
                            if (!str3.equals("iball8735_9806")) {
                                b = -1;
                            } else {
                                b = 127;
                            }
                            break;
                        case 1865889110:
                            if (!str3.equals("santoni")) {
                                b = -1;
                            } else {
                                b = 128;
                            }
                            break;
                        case 1906253259:
                            if (!str3.equals("PB2-670M")) {
                                b = -1;
                            } else {
                                b = 129;
                            }
                            break;
                        case 1977196784:
                            if (!str3.equals("Infinix-X572")) {
                                b = -1;
                            } else {
                                b = 130;
                            }
                            break;
                        case 2006372676:
                            if (!str3.equals("BRAVIA_ATV3_4K")) {
                                b = -1;
                            } else {
                                b = 131;
                            }
                            break;
                        case 2019281702:
                            if (!str3.equals("DM-01K")) {
                                b = -1;
                            } else {
                                b = 132;
                            }
                            break;
                        case 2029784656:
                            if (!str3.equals("HWBLN-H")) {
                                b = -1;
                            } else {
                                b = 133;
                            }
                            break;
                        case 2030379515:
                            if (!str3.equals("HWCAM-H")) {
                                b = -1;
                            } else {
                                b = 134;
                            }
                            break;
                        case 2033393791:
                            if (!str3.equals("ASUS_X00AD_2")) {
                                b = -1;
                            } else {
                                b = 135;
                            }
                            break;
                        case 2047190025:
                            if (!str3.equals("ELUGA_Note")) {
                                b = -1;
                            } else {
                                b = 136;
                            }
                            break;
                        case 2047252157:
                            if (!str3.equals("ELUGA_Prim")) {
                                b = -1;
                            } else {
                                b = 137;
                            }
                            break;
                        case 2048319463:
                            if (!str3.equals("HWVNS-H")) {
                                b = -1;
                            } else {
                                b = 138;
                            }
                            break;
                        case 2048855701:
                            if (!str3.equals("HWWAS-H")) {
                                b = -1;
                            } else {
                                b = 139;
                            }
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    switch (b) {
                        default:
                            Objects.requireNonNull(str2);
                            if (!str2.equals("JSN-L21")) {
                            }
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                        case 62:
                        case 63:
                        case 64:
                        case 65:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                        case 76:
                        case 77:
                        case 78:
                        case 79:
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                        case 84:
                        case 85:
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                        case 92:
                        case 93:
                        case 94:
                        case 95:
                        case 96:
                        case 97:
                        case 98:
                        case 99:
                        case 100:
                        case 101:
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                        case 108:
                        case 109:
                        case 110:
                        case 111:
                        case 112:
                        case 113:
                        case 114:
                        case 115:
                        case 116:
                        case 117:
                        case 118:
                        case 119:
                        case 120:
                        case 121:
                        case 122:
                        case 123:
                        case 124:
                        case 125:
                        case 126:
                        case 127:
                        case 128:
                        case TsExtractor.TS_STREAM_TYPE_AC3 /* 129 */:
                        case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /* 130 */:
                        case 131:
                        case 132:
                        case 133:
                        case TsExtractor.TS_STREAM_TYPE_SPLICE_INFO /* 134 */:
                        case TsExtractor.TS_STREAM_TYPE_E_AC3 /* 135 */:
                        case 136:
                        case 137:
                        case TsExtractor.TS_STREAM_TYPE_DTS /* 138 */:
                        case 139:
                            return true;
                    }
                }
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:18:0x0042  */
    public static int getCodecMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        int iIntValue;
        int i = format.width;
        int i2 = format.height;
        if (i == -1 || i2 == -1) {
            return -1;
        }
        String str = format.sampleMimeType;
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(str)) {
            Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
            str = (codecProfileAndLevel == null || !((iIntValue = ((Integer) codecProfileAndLevel.first).intValue()) == 512 || iIntValue == 1 || iIntValue == 2)) ? MimeTypes.VIDEO_H265 : MimeTypes.VIDEO_H264;
        }
        Objects.requireNonNull(str);
        switch (str) {
            case "video/3gpp":
            case "video/av01":
            case "video/mp4v-es":
            case "video/x-vnd.on2.vp8":
                return getMaxSampleSize(i * i2, 2);
            case "video/hevc":
                return Math.max(2097152, getMaxSampleSize(i * i2, 2));
            case "video/avc":
                String str2 = Util.MODEL;
                if ("BRAVIA 4K 2015".equals(str2) || ("Amazon".equals(Util.MANUFACTURER) && ("KFSOWI".equals(str2) || ("AFTS".equals(str2) && mediaCodecInfo.secure)))) {
                    return -1;
                }
                return getMaxSampleSize(Util.ceilDivide(i2, 16) * Util.ceilDivide(i, 16) * 16 * 16, 2);
            case "video/x-vnd.on2.vp9":
                return getMaxSampleSize(i * i2, 4);
            default:
                return -1;
        }
    }

    @Nullable
    private static Point getCodecMaxSize(MediaCodecInfo mediaCodecInfo, Format format) {
        int i = format.height;
        int i2 = format.width;
        boolean z = i > i2;
        int i3 = z ? i : i2;
        if (z) {
            i = i2;
        }
        float f = i / i3;
        for (int i4 : STANDARD_LONG_EDGE_VIDEO_PX) {
            int i5 = (int) (i4 * f);
            if (i4 <= i3 || i5 <= i) {
                break;
            }
            if (Util.SDK_INT >= 21) {
                int i6 = z ? i5 : i4;
                if (!z) {
                    i4 = i5;
                }
                Point pointAlignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i6, i4);
                if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(pointAlignVideoSizeV21.x, pointAlignVideoSizeV21.y, format.frameRate)) {
                    return pointAlignVideoSizeV21;
                }
            } else {
                try {
                    int iCeilDivide = Util.ceilDivide(i4, 16) * 16;
                    int iCeilDivide2 = Util.ceilDivide(i5, 16) * 16;
                    if (iCeilDivide * iCeilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i7 = z ? iCeilDivide2 : iCeilDivide;
                        if (!z) {
                            iCeilDivide = iCeilDivide2;
                        }
                        return new Point(i7, iCeilDivide);
                    }
                } catch (MediaCodecUtil.DecoderQueryException unused) {
                }
            }
        }
        return null;
    }

    public static int getMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return getCodecMaxInputSize(mediaCodecInfo, format);
        }
        int size = format.initializationData.size();
        int length = 0;
        for (int i = 0; i < size; i++) {
            length += format.initializationData.get(i).length;
        }
        return format.maxInputSize + length;
    }

    private static int getMaxSampleSize(int i, int i2) {
        return (i * 3) / (i2 * 2);
    }

    private static boolean isBufferLate(long j) {
        return j < -30000;
    }

    private static boolean isBufferVeryLate(long j) {
        return j < -500000;
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, jElapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = jElapsedRealtime;
        }
    }

    private void maybeNotifyVideoFrameProcessingOffset() {
        int i = this.videoFrameProcessingOffsetCount;
        if (i != 0) {
            this.eventDispatcher.reportVideoFrameProcessingOffset(this.totalVideoFrameProcessingOffsetUs, i);
            this.totalVideoFrameProcessingOffsetUs = 0L;
            this.videoFrameProcessingOffsetCount = 0;
        }
    }

    private void maybeNotifyVideoSizeChanged() {
        int i = this.currentWidth;
        if (i == -1 && this.currentHeight == -1) {
            return;
        }
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null && videoSize.width == i && videoSize.height == this.currentHeight && videoSize.unappliedRotationDegrees == this.currentUnappliedRotationDegrees && videoSize.pixelWidthHeightRatio == this.currentPixelWidthHeightRatio) {
            return;
        }
        VideoSize videoSize2 = new VideoSize(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
        this.reportedVideoSize = videoSize2;
        this.eventDispatcher.videoSizeChanged(videoSize2);
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.haveReportedFirstFrameRenderedForCurrentSurface) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null) {
            this.eventDispatcher.videoSizeChanged(videoSize);
        }
    }

    private void notifyFrameMetadataListener(long j, long j2, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format, getCodecOutputMediaFormat());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProcessedTunneledEndOfStream() {
        setPendingOutputEndOfStream();
    }

    @RequiresApi(17)
    private void releasePlaceholderSurface() {
        Surface surface = this.surface;
        PlaceholderSurface placeholderSurface = this.placeholderSurface;
        if (surface == placeholderSurface) {
            this.surface = null;
        }
        placeholderSurface.release();
        this.placeholderSurface = null;
    }

    @RequiresApi(29)
    private static void setHdr10PlusInfoV29(MediaCodecAdapter mediaCodecAdapter, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("hdr10-plus-info", bArr);
        mediaCodecAdapter.setParameters(bundle);
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.exoplayer2.video.VideoFrameReleaseHelper] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.exoplayer2.mediacodec.MediaCodecAdapter] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.google.android.exoplayer2.video.PlaceholderSurface] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private void setOutput(@Nullable Object obj) throws ExoPlaybackException {
        ?? NewInstanceV17;
        Surface surface;
        if (obj instanceof Surface) {
            surface = (Surface) obj;
        } else {
            NewInstanceV17 = 0;
        }
        if (NewInstanceV17 == 0) {
            PlaceholderSurface placeholderSurface = this.placeholderSurface;
            if (placeholderSurface != null) {
                NewInstanceV17 = surface;
                NewInstanceV17 = placeholderSurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUsePlaceholderSurface(codecInfo)) {
                    NewInstanceV17 = surface;
                    NewInstanceV17 = PlaceholderSurface.newInstanceV17(this.context, codecInfo.secure);
                    this.placeholderSurface = NewInstanceV17;
                }
            }
        }
        NewInstanceV17 = surface;
        NewInstanceV17 = surface;
        NewInstanceV17 = surface;
        if (this.surface == NewInstanceV17) {
            if (NewInstanceV17 == 0 || NewInstanceV17 == this.placeholderSurface) {
                return;
            }
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
            return;
        }
        this.surface = NewInstanceV17;
        this.frameReleaseHelper.onSurfaceChanged(NewInstanceV17);
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        int state = getState();
        ?? codec = getCodec();
        if (codec != 0) {
            if (Util.SDK_INT < 23 || NewInstanceV17 == 0 || this.codecNeedsSetOutputSurfaceWorkaround) {
                releaseCodec();
                maybeInitCodecOrBypass();
            } else {
                codec.setOutputSurface(NewInstanceV17);
            }
        }
        if (NewInstanceV17 == 0 || NewInstanceV17 == this.placeholderSurface) {
            clearReportedVideoSize();
            clearRenderedFirstFrame();
            return;
        }
        maybeRenotifyVideoSizeChanged();
        clearRenderedFirstFrame();
        if (state == 2) {
            setJoiningDeadlineMs();
        }
    }

    private boolean shouldUsePlaceholderSurface(MediaCodecInfo mediaCodecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !codecNeedsSetOutputSurfaceWorkaround(mediaCodecInfo.name) && (!mediaCodecInfo.secure || PlaceholderSurface.isSecureSupported(this.context));
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final DecoderReuseEvaluation canReuseCodec(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        DecoderReuseEvaluation decoderReuseEvaluationCanReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i = decoderReuseEvaluationCanReuseCodec.discardReasons;
        int i2 = format2.width;
        CodecMaxValues codecMaxValues = this.codecMaxValues;
        if (i2 > codecMaxValues.width || format2.height > codecMaxValues.height) {
            i |= 256;
        }
        if (getMaxInputSize(mediaCodecInfo, format2) > this.codecMaxValues.inputSize) {
            i |= 64;
        }
        int i3 = i;
        return new DecoderReuseEvaluation(mediaCodecInfo.name, format, format2, i3 != 0 ? 0 : decoderReuseEvaluationCanReuseCodec.result, i3);
    }

    public final boolean codecNeedsSetOutputSurfaceWorkaround(String str) {
        if (str.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            if (!evaluatedDeviceNeedsSetOutputSurfaceWorkaround) {
                deviceNeedsSetOutputSurfaceWorkaround = evaluateDeviceNeedsSetOutputSurfaceWorkaround();
                evaluatedDeviceNeedsSetOutputSurfaceWorkaround = true;
            }
        }
        return deviceNeedsSetOutputSurfaceWorkaround;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final MediaCodecDecoderException createDecoderException(Throwable th, @Nullable MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecVideoDecoderException(th, mediaCodecInfo, this.surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final boolean getCodecNeedsEosPropagation() {
        return this.tunneling && Util.SDK_INT < 23;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final float getCodecOperatingRateV23(float f, Format[] formatArr) {
        float fMax = -1.0f;
        for (Format format : formatArr) {
            float f2 = format.frameRate;
            if (f2 != -1.0f) {
                fMax = Math.max(fMax, f2);
            }
        }
        if (fMax == -1.0f) {
            return -1.0f;
        }
        return fMax * f;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException {
        return MediaCodecUtil.getDecoderInfosSortedByFormatSupport(getDecoderInfos(this.context, mediaCodecSelector, format, z, this.tunneling), format);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @TargetApi(17)
    public final MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f) {
        CodecMaxValues codecMaxValues;
        Pair<Integer, Integer> codecProfileAndLevel;
        int codecMaxInputSize;
        PlaceholderSurface placeholderSurface = this.placeholderSurface;
        if (placeholderSurface != null && placeholderSurface.secure != mediaCodecInfo.secure) {
            releasePlaceholderSurface();
        }
        String str = mediaCodecInfo.codecMimeType;
        Format[] streamFormats = getStreamFormats();
        int iMax = format.width;
        int iMax2 = format.height;
        int maxInputSize = getMaxInputSize(mediaCodecInfo, format);
        if (streamFormats.length == 1) {
            if (maxInputSize != -1 && (codecMaxInputSize = getCodecMaxInputSize(mediaCodecInfo, format)) != -1) {
                maxInputSize = Math.min((int) (maxInputSize * INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR), codecMaxInputSize);
            }
            codecMaxValues = new CodecMaxValues(iMax, iMax2, maxInputSize);
        } else {
            int length = streamFormats.length;
            boolean z = false;
            for (int i = 0; i < length; i++) {
                Format formatBuild = streamFormats[i];
                if (format.colorInfo != null && formatBuild.colorInfo == null) {
                    formatBuild = formatBuild.buildUpon().setColorInfo(format.colorInfo).build();
                }
                if (mediaCodecInfo.canReuseCodec(format, formatBuild).result != 0) {
                    int i2 = formatBuild.width;
                    z |= i2 == -1 || formatBuild.height == -1;
                    iMax = Math.max(iMax, i2);
                    iMax2 = Math.max(iMax2, formatBuild.height);
                    maxInputSize = Math.max(maxInputSize, getMaxInputSize(mediaCodecInfo, formatBuild));
                }
            }
            if (z) {
                Log.w(TAG, "Resolutions unknown. Codec max resolution: " + iMax + "x" + iMax2);
                Point codecMaxSize = getCodecMaxSize(mediaCodecInfo, format);
                if (codecMaxSize != null) {
                    iMax = Math.max(iMax, codecMaxSize.x);
                    iMax2 = Math.max(iMax2, codecMaxSize.y);
                    maxInputSize = Math.max(maxInputSize, getCodecMaxInputSize(mediaCodecInfo, format.buildUpon().setWidth(iMax).setHeight(iMax2).build()));
                    Log.w(TAG, "Codec max resolution adjusted to: " + iMax + "x" + iMax2);
                }
            }
            codecMaxValues = new CodecMaxValues(iMax, iMax2, maxInputSize);
        }
        this.codecMaxValues = codecMaxValues;
        boolean z2 = this.deviceNeedsNoPostProcessWorkaround;
        int i3 = this.tunneling ? this.tunnelingAudioSessionId : 0;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger("width", format.width);
        mediaFormat.setInteger("height", format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, Scopes.PROFILE, ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger("priority", 0);
            if (f != -1.0f) {
                mediaFormat.setFloat("operating-rate", f);
            }
        }
        if (z2) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (i3 != 0) {
            configureTunnelingV21(mediaFormat, i3);
        }
        if (this.surface == null) {
            if (!shouldUsePlaceholderSurface(mediaCodecInfo)) {
                throw new IllegalStateException();
            }
            if (this.placeholderSurface == null) {
                this.placeholderSurface = PlaceholderSurface.newInstanceV17(this.context, mediaCodecInfo.secure);
            }
            this.surface = this.placeholderSurface;
        }
        return MediaCodecAdapter.Configuration.createForVideoDecoding(mediaCodecInfo, mediaFormat, format, this.surface, mediaCrypto);
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @TargetApi(29)
    public final void handleInputBufferSupplementalData(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        if (this.codecHandlesHdr10PlusOutOfBandMetadata) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.supplementalData);
            if (byteBuffer.remaining() >= 7) {
                byte b = byteBuffer.get();
                short s = byteBuffer.getShort();
                short s2 = byteBuffer.getShort();
                byte b2 = byteBuffer.get();
                byte b3 = byteBuffer.get();
                byteBuffer.position(0);
                if (b == -75 && s == 60 && s2 == 1 && b2 == 4) {
                    if (b3 == 0 || b3 == 1) {
                        byte[] bArr = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bArr);
                        byteBuffer.position(0);
                        setHdr10PlusInfoV29(getCodec(), bArr);
                    }
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 1) {
            setOutput(obj);
            return;
        }
        if (i == 7) {
            this.frameMetadataListener = (VideoFrameMetadataListener) obj;
            return;
        }
        if (i == 10) {
            int iIntValue = ((Integer) obj).intValue();
            if (this.tunnelingAudioSessionId != iIntValue) {
                this.tunnelingAudioSessionId = iIntValue;
                if (this.tunneling) {
                    releaseCodec();
                    return;
                }
                return;
            }
            return;
        }
        if (i != 4) {
            if (i != 5) {
                super.handleMessage(i, obj);
                return;
            } else {
                this.frameReleaseHelper.setChangeFrameRateStrategy(((Integer) obj).intValue());
                return;
            }
        }
        this.scalingMode = ((Integer) obj).intValue();
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            codec.setVideoScalingMode(this.scalingMode);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        PlaceholderSurface placeholderSurface;
        if (super.isReady() && (this.renderedFirstFrameAfterReset || (((placeholderSurface = this.placeholderSurface) != null && this.surface == placeholderSurface) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C.TIME_UNSET;
            return true;
        }
        if (this.joiningDeadlineMs == C.TIME_UNSET) {
            return false;
        }
        if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
            return true;
        }
        this.joiningDeadlineMs = C.TIME_UNSET;
        return false;
    }

    public final void maybeNotifyRenderedFirstFrame() {
        this.renderedFirstFrameAfterEnable = true;
        if (this.renderedFirstFrameAfterReset) {
            return;
        }
        this.renderedFirstFrameAfterReset = true;
        this.eventDispatcher.renderedFirstFrame(this.surface);
        this.haveReportedFirstFrameRenderedForCurrentSurface = true;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onCodecError(Exception exc) {
        Log.e(TAG, "Video codec error", exc);
        this.eventDispatcher.videoCodecError(exc);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(str);
        this.codecHandlesHdr10PlusOutOfBandMetadata = ((MediaCodecInfo) Assertions.checkNotNull(getCodecInfo())).isHdr10PlusOutOfBandMetadataSupported();
        if (Util.SDK_INT < 23 || !this.tunneling) {
            return;
        }
        this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23((MediaCodecAdapter) Assertions.checkNotNull(getCodec()));
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onCodecReleased(String str) {
        this.eventDispatcher.decoderReleased(str);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onDisabled() {
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        this.tunnelingOnFrameRenderedListener = null;
        try {
            super.onDisabled();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        super.onEnabled(z, z2);
        boolean z3 = getConfiguration().tunneling;
        Assertions.checkState((z3 && this.tunnelingAudioSessionId == 0) ? false : true);
        if (this.tunneling != z3) {
            this.tunneling = z3;
            releaseCodec();
        }
        this.eventDispatcher.enabled(this.decoderCounters);
        this.mayRenderFirstFrameAfterEnableIfNotStarted = z2;
        this.renderedFirstFrameAfterEnable = false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @Nullable
    public final DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluationOnInputFormatChanged = super.onInputFormatChanged(formatHolder);
        this.eventDispatcher.inputFormatChanged(formatHolder.format, decoderReuseEvaluationOnInputFormatChanged);
        return decoderReuseEvaluationOnInputFormatChanged;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onOutputFormatChanged(Format format, @Nullable MediaFormat mediaFormat) {
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            codec.setVideoScalingMode(this.scalingMode);
        }
        if (this.tunneling) {
            this.currentWidth = format.width;
            this.currentHeight = format.height;
        } else {
            Assertions.checkNotNull(mediaFormat);
            boolean z = mediaFormat.containsKey(KEY_CROP_RIGHT) && mediaFormat.containsKey(KEY_CROP_LEFT) && mediaFormat.containsKey(KEY_CROP_BOTTOM) && mediaFormat.containsKey(KEY_CROP_TOP);
            this.currentWidth = z ? (mediaFormat.getInteger(KEY_CROP_RIGHT) - mediaFormat.getInteger(KEY_CROP_LEFT)) + 1 : mediaFormat.getInteger("width");
            this.currentHeight = z ? (mediaFormat.getInteger(KEY_CROP_BOTTOM) - mediaFormat.getInteger(KEY_CROP_TOP)) + 1 : mediaFormat.getInteger("height");
        }
        float f = format.pixelWidthHeightRatio;
        this.currentPixelWidthHeightRatio = f;
        if (Util.SDK_INT >= 21) {
            int i = format.rotationDegrees;
            if (i == 90 || i == 270) {
                int i2 = this.currentWidth;
                this.currentWidth = this.currentHeight;
                this.currentHeight = i2;
                this.currentPixelWidthHeightRatio = 1.0f / f;
            }
        } else {
            this.currentUnappliedRotationDegrees = format.rotationDegrees;
        }
        this.frameReleaseHelper.onFormatChanged(format.frameRate);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        super.onPositionReset(j, z);
        clearRenderedFirstFrame();
        this.frameReleaseHelper.onPositionReset();
        this.lastBufferPresentationTimeUs = C.TIME_UNSET;
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        if (z) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public final void onProcessedOutputBuffer(long j) {
        super.onProcessedOutputBuffer(j);
        if (this.tunneling) {
            return;
        }
        this.buffersInCodecCount--;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final void onProcessedStreamChange() {
        clearRenderedFirstFrame();
    }

    public final void onProcessedTunneledBuffer(long j) throws ExoPlaybackException {
        updateOutputFormatForTime(j);
        maybeNotifyVideoSizeChanged();
        this.decoderCounters.renderedOutputBufferCount++;
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(j);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public final void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        boolean z = this.tunneling;
        if (!z) {
            this.buffersInCodecCount++;
        }
        if (Util.SDK_INT >= 23 || !z) {
            return;
        }
        onProcessedTunneledBuffer(decoderInputBuffer.timeUs);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    @TargetApi(17)
    public final void onReset() {
        try {
            super.onReset();
        } finally {
            if (this.placeholderSurface != null) {
                releasePlaceholderSurface();
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onStarted() {
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.totalVideoFrameProcessingOffsetUs = 0L;
        this.videoFrameProcessingOffsetCount = 0;
        this.frameReleaseHelper.onStarted();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public final void onStopped() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        maybeNotifyVideoFrameProcessingOffset();
        this.frameReleaseHelper.onStopped();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final boolean processOutputBuffer(long j, long j2, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i, int i2, int i3, long j3, boolean z, boolean z2, Format format) throws ExoPlaybackException {
        boolean z3;
        boolean z4;
        boolean z5;
        Assertions.checkNotNull(mediaCodecAdapter);
        if (this.initialPositionUs == C.TIME_UNSET) {
            this.initialPositionUs = j;
        }
        if (j3 != this.lastBufferPresentationTimeUs) {
            this.frameReleaseHelper.onNextFrame(j3);
            this.lastBufferPresentationTimeUs = j3;
        }
        long outputStreamOffsetUs = getOutputStreamOffsetUs();
        long j4 = j3 - outputStreamOffsetUs;
        if (z && !z2) {
            skipOutputBuffer(mediaCodecAdapter, i);
            return true;
        }
        double playbackSpeed = getPlaybackSpeed();
        boolean z6 = getState() == 2;
        long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long j5 = (long) ((j3 - j) / playbackSpeed);
        if (z6) {
            j5 -= jElapsedRealtime - j2;
        }
        if (this.surface == this.placeholderSurface) {
            if (!isBufferLate(j5)) {
                return false;
            }
            skipOutputBuffer(mediaCodecAdapter, i);
            updateVideoFrameProcessingOffsetCounters(j5);
            return true;
        }
        long j6 = jElapsedRealtime - this.lastRenderRealtimeUs;
        boolean z7 = this.renderedFirstFrameAfterEnable ? !this.renderedFirstFrameAfterReset : z6 || this.mayRenderFirstFrameAfterEnableIfNotStarted;
        if (this.joiningDeadlineMs != C.TIME_UNSET || j < outputStreamOffsetUs) {
            z3 = false;
        } else {
            if (!z7) {
                if (z6) {
                    if (isBufferLate(j5) && j6 > 100000) {
                    }
                }
                z3 = false;
            }
            z3 = true;
        }
        if (z3) {
            long jNanoTime = System.nanoTime();
            notifyFrameMetadataListener(j4, jNanoTime, format);
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(mediaCodecAdapter, i, jNanoTime);
            } else {
                renderOutputBuffer(mediaCodecAdapter, i);
            }
            updateVideoFrameProcessingOffsetCounters(j5);
            return true;
        }
        if (z6 && j != this.initialPositionUs) {
            long jNanoTime2 = System.nanoTime();
            long jAdjustReleaseTime = this.frameReleaseHelper.adjustReleaseTime((j5 * 1000) + jNanoTime2);
            long j7 = (jAdjustReleaseTime - jNanoTime2) / 1000;
            boolean z8 = this.joiningDeadlineMs != C.TIME_UNSET;
            if (isBufferVeryLate(j7) && !z2) {
                int iSkipSource = skipSource(j);
                if (iSkipSource == 0) {
                    z5 = false;
                } else {
                    if (z8) {
                        DecoderCounters decoderCounters = this.decoderCounters;
                        decoderCounters.skippedInputBufferCount += iSkipSource;
                        decoderCounters.skippedOutputBufferCount += this.buffersInCodecCount;
                    } else {
                        this.decoderCounters.droppedToKeyframeCount++;
                        updateDroppedBufferCounters(iSkipSource, this.buffersInCodecCount);
                    }
                    if (flushOrReleaseCodec()) {
                        maybeInitCodecOrBypass();
                    }
                    z5 = true;
                }
                if (z5) {
                    return false;
                }
            }
            if (isBufferLate(j7) && !z2) {
                if (z8) {
                    skipOutputBuffer(mediaCodecAdapter, i);
                    z4 = true;
                } else {
                    TraceUtil.beginSection("dropVideoBuffer");
                    mediaCodecAdapter.releaseOutputBuffer(i, false);
                    TraceUtil.endSection();
                    z4 = true;
                    updateDroppedBufferCounters(0, 1);
                }
                updateVideoFrameProcessingOffsetCounters(j7);
                return z4;
            }
            if (Util.SDK_INT >= 21) {
                if (j7 < 50000) {
                    if (jAdjustReleaseTime == this.lastFrameReleaseTimeNs) {
                        skipOutputBuffer(mediaCodecAdapter, i);
                    } else {
                        notifyFrameMetadataListener(j4, jAdjustReleaseTime, format);
                        renderOutputBufferV21(mediaCodecAdapter, i, jAdjustReleaseTime);
                    }
                    updateVideoFrameProcessingOffsetCounters(j7);
                    this.lastFrameReleaseTimeNs = jAdjustReleaseTime;
                    return true;
                }
            } else if (j7 < 30000) {
                if (j7 > 11000) {
                    try {
                        Thread.sleep((j7 - 10000) / 1000);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
                notifyFrameMetadataListener(j4, jAdjustReleaseTime, format);
                renderOutputBuffer(mediaCodecAdapter, i);
                updateVideoFrameProcessingOffsetCounters(j7);
                return true;
            }
        }
        return false;
    }

    public final void renderOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, true);
        TraceUtil.endSection();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    @RequiresApi(21)
    public final void renderOutputBufferV21(MediaCodecAdapter mediaCodecAdapter, int i, long j) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, j);
        TraceUtil.endSection();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public final void resetCodecStateForFlush() {
        super.resetCodecStateForFlush();
        this.buffersInCodecCount = 0;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        super.setPlaybackSpeed(f, f2);
        this.frameReleaseHelper.onPlaybackSpeed(f);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return this.surface != null || shouldUsePlaceholderSurface(mediaCodecInfo);
    }

    public final void skipOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public final int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        boolean z;
        int i = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return RendererCapabilities.CC.create(0);
        }
        boolean z2 = format.drmInitData != null;
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(this.context, mediaCodecSelector, format, z2, false);
        if (z2 && decoderInfos.isEmpty()) {
            decoderInfos = getDecoderInfos(this.context, mediaCodecSelector, format, false, false);
        }
        if (decoderInfos.isEmpty()) {
            return RendererCapabilities.CC.create(1);
        }
        int i2 = format.cryptoType;
        if (!(i2 == 0 || i2 == 2)) {
            return RendererCapabilities.CC.create(2);
        }
        MediaCodecInfo mediaCodecInfo = decoderInfos.get(0);
        boolean zIsFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (!zIsFormatSupported) {
            int i3 = 1;
            while (true) {
                if (i3 >= decoderInfos.size()) {
                    z = true;
                    break;
                }
                MediaCodecInfo mediaCodecInfo2 = decoderInfos.get(i3);
                if (mediaCodecInfo2.isFormatSupported(format)) {
                    mediaCodecInfo = mediaCodecInfo2;
                    z = false;
                    zIsFormatSupported = true;
                    break;
                }
                i3++;
            }
        } else {
            z = true;
            break;
        }
        int i4 = zIsFormatSupported ? 4 : 3;
        int i5 = mediaCodecInfo.isSeamlessAdaptationSupported(format) ? 16 : 8;
        int i6 = mediaCodecInfo.hardwareAccelerated ? 64 : 0;
        int i7 = z ? 128 : 0;
        if (Util.SDK_INT >= 26 && MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && !Api26.doesDisplaySupportDolbyVision(this.context)) {
            i7 = 256;
        }
        if (zIsFormatSupported) {
            List<MediaCodecInfo> decoderInfos2 = getDecoderInfos(this.context, mediaCodecSelector, format, z2, true);
            if (!decoderInfos2.isEmpty()) {
                MediaCodecInfo mediaCodecInfo3 = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(decoderInfos2, format).get(0);
                if (mediaCodecInfo3.isFormatSupported(format) && mediaCodecInfo3.isSeamlessAdaptationSupported(format)) {
                    i = 32;
                }
            }
        }
        return RendererCapabilities.CC.create(i4, i5, i, i6, i7);
    }

    public final void updateDroppedBufferCounters(int i, int i2) {
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.droppedInputBufferCount += i;
        int i3 = i + i2;
        decoderCounters.droppedBufferCount += i3;
        this.droppedFrames += i3;
        int i4 = this.consecutiveDroppedFrameCount + i3;
        this.consecutiveDroppedFrameCount = i4;
        decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(i4, decoderCounters.maxConsecutiveDroppedBufferCount);
        int i5 = this.maxDroppedFramesToNotify;
        if (i5 <= 0 || this.droppedFrames < i5) {
            return;
        }
        maybeNotifyDroppedFrames();
    }

    public final void updateVideoFrameProcessingOffsetCounters(long j) {
        this.decoderCounters.addVideoFrameProcessingOffset(j);
        this.totalVideoFrameProcessingOffsetUs += j;
        this.videoFrameProcessingOffsetCount++;
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j) {
        this(context, mediaCodecSelector, j, null, null, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, j, false, handler, videoRendererEventListener, i, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, j, z, handler, videoRendererEventListener, i, 30.0f);
    }

    private static List<MediaCodecInfo> getDecoderInfos(Context context, MediaCodecSelector mediaCodecSelector, Format format, boolean z, boolean z2) throws MediaCodecUtil.DecoderQueryException {
        String str = format.sampleMimeType;
        if (str == null) {
            return ImmutableList.of();
        }
        List<MediaCodecInfo> decoderInfos = mediaCodecSelector.getDecoderInfos(str, z, z2);
        String alternativeCodecMimeType = MediaCodecUtil.getAlternativeCodecMimeType(format);
        if (alternativeCodecMimeType == null) {
            return ImmutableList.copyOf((Collection) decoderInfos);
        }
        List<MediaCodecInfo> decoderInfos2 = mediaCodecSelector.getDecoderInfos(alternativeCodecMimeType, z, z2);
        if (Util.SDK_INT >= 26 && MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && !decoderInfos2.isEmpty() && !Api26.doesDisplaySupportDolbyVision(context)) {
            return ImmutableList.copyOf((Collection) decoderInfos2);
        }
        return ImmutableList.builder().addAll((Iterable) decoderInfos).addAll((Iterable) decoderInfos2).build();
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, factory, mediaCodecSelector, j, z, handler, videoRendererEventListener, i, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i, float f) {
        super(2, factory, mediaCodecSelector, z, f);
        this.allowedJoiningTimeMs = j;
        this.maxDroppedFramesToNotify = i;
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.frameReleaseHelper = new VideoFrameReleaseHelper(applicationContext);
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
        this.deviceNeedsNoPostProcessWorkaround = deviceNeedsNoPostProcessWorkaround();
        this.joiningDeadlineMs = C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        this.tunnelingAudioSessionId = 0;
        clearReportedVideoSize();
    }
}
