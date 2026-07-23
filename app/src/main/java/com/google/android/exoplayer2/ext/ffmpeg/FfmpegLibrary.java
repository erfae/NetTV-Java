package com.google.android.exoplayer2.ext.ffmpeg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.util.LibraryLoader;
import com.google.android.exoplayer2.util.Log;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class FfmpegLibrary {
    private static final LibraryLoader LOADER;
    private static final String TAG = "FfmpegLibrary";
    private static int inputBufferPaddingSize;
    private static String version;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ffmpeg");
        LOADER = new LibraryLoader("ffmpegJNI") { // from class: com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary.1
            @Override // com.google.android.exoplayer2.util.LibraryLoader
            public final void loadLibrary(String str) {
                System.loadLibrary(str);
            }
        };
        inputBufferPaddingSize = -1;
    }

    private FfmpegLibrary() {
    }

    private static native int ffmpegGetInputBufferPaddingSize();

    private static native String ffmpegGetVersion();

    private static native boolean ffmpegHasDecoder(String str);

    @Nullable
    public static String getCodecName(String str) {
        Objects.requireNonNull(str);
        switch (str) {
            case "audio/eac3-joc":
            case "audio/eac3":
                return "eac3";
            case "audio/amr-wb":
                return "amrwb";
            case "audio/vnd.dts":
            case "audio/vnd.dts.hd":
                return "dca";
            case "audio/vorbis":
                return "vorbis";
            case "audio/mpeg-L1":
            case "audio/mpeg-L2":
            case "audio/mpeg":
                return "mp3";
            case "audio/mp4a-latm":
                return "aac";
            case "audio/ac3":
                return "ac3";
            case "audio/3gpp":
                return "amrnb";
            case "audio/alac":
                return "alac";
            case "audio/flac":
                return "flac";
            case "audio/opus":
                return "opus";
            case "audio/true-hd":
                return "truehd";
            case "audio/g711-alaw":
                return "pcm_alaw";
            case "audio/g711-mlaw":
                return "pcm_mulaw";
            default:
                return null;
        }
    }

    public static int getInputBufferPaddingSize() {
        if (!isAvailable()) {
            return -1;
        }
        if (inputBufferPaddingSize == -1) {
            inputBufferPaddingSize = ffmpegGetInputBufferPaddingSize();
        }
        return inputBufferPaddingSize;
    }

    @Nullable
    public static String getVersion() {
        if (!isAvailable()) {
            return null;
        }
        if (version == null) {
            version = ffmpegGetVersion();
        }
        return version;
    }

    public static boolean isAvailable() {
        return LOADER.isAvailable();
    }

    public static void setLibraries(String... strArr) {
        LOADER.setLibraries(strArr);
    }

    public static boolean supportsFormat(String str) {
        String codecName;
        if (!isAvailable() || (codecName = getCodecName(str)) == null) {
            return false;
        }
        if (ffmpegHasDecoder(codecName)) {
            return true;
        }
        Log.w(TAG, "No " + codecName + " decoder available. Check the FFmpeg build configuration.");
        return false;
    }
}
