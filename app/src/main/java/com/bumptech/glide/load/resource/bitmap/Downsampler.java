package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.PreferredColorSpace;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class Downsampler {
    public static final Option<Boolean> ALLOW_HARDWARE_CONFIG;
    private static final DecodeCallbacks EMPTY_CALLBACKS;
    public static final Option<Boolean> FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS;
    private static final String ICO_MIME_TYPE = "image/x-ico";
    private static final Set<String> NO_DOWNSAMPLE_PRE_N_MIME_TYPES;
    private static final Queue<BitmapFactory.Options> OPTIONS_QUEUE;
    private static final Set<ImageHeaderParser.ImageType> TYPES_THAT_USE_POOL_PRE_KITKAT;
    private static final String WBMP_MIME_TYPE = "image/vnd.wap.wbmp";
    private final BitmapPool bitmapPool;
    private final ArrayPool byteArrayPool;
    private final DisplayMetrics displayMetrics;
    private final HardwareConfigState hardwareConfigState = HardwareConfigState.getInstance();
    private final List<ImageHeaderParser> parsers;
    public static final Option<DecodeFormat> DECODE_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    public static final Option<PreferredColorSpace> PREFERRED_COLOR_SPACE = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.PreferredColorSpace", PreferredColorSpace.SRGB);

    @Deprecated
    public static final Option<DownsampleStrategy> DOWNSAMPLE_STRATEGY = DownsampleStrategy.OPTION;

    public interface DecodeCallbacks {
        void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException;

        void onObtainBounds();
    }

    static {
        Boolean bool = Boolean.FALSE;
        FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", bool);
        ALLOW_HARDWARE_CONFIG = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode", bool);
        NO_DOWNSAMPLE_PRE_N_MIME_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(WBMP_MIME_TYPE, ICO_MIME_TYPE)));
        EMPTY_CALLBACKS = new DecodeCallbacks() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.1
            @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
            public void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) {
            }

            @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
            public void onObtainBounds() {
            }
        };
        TYPES_THAT_USE_POOL_PRE_KITKAT = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
        OPTIONS_QUEUE = Util.createQueue(0);
    }

    public Downsampler(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.parsers = list;
        this.displayMetrics = (DisplayMetrics) Preconditions.checkNotNull(displayMetrics);
        this.bitmapPool = (BitmapPool) Preconditions.checkNotNull(bitmapPool);
        this.byteArrayPool = (ArrayPool) Preconditions.checkNotNull(arrayPool);
    }

    private static int adjustTargetDensityForError(double d) {
        int densityMultiplier = getDensityMultiplier(d);
        int iRound = round(((double) densityMultiplier) * d);
        return round((d / ((double) (iRound / densityMultiplier))) * ((double) iRound));
    }

    private void calculateConfig(ImageReader imageReader, DecodeFormat decodeFormat, boolean z, boolean z2, BitmapFactory.Options options, int i, int i2) {
        if (this.hardwareConfigState.setHardwareConfigIfAllowed(i, i2, options, z, z2)) {
            return;
        }
        if (decodeFormat == DecodeFormat.PREFER_ARGB_8888) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return;
        }
        boolean zHasAlpha = false;
        try {
            zHasAlpha = imageReader.getImageType().hasAlpha();
        } catch (IOException e) {
            if (Log.isLoggable("Downsampler", 3)) {
                Log.d("Downsampler", "Cannot determine whether the image has alpha or not from header, format " + decodeFormat, e);
            }
        }
        Bitmap.Config config = zHasAlpha ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        options.inPreferredConfig = config;
        if (config == Bitmap.Config.RGB_565) {
            options.inDither = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x008a  */
    /* JADX WARN: Code duplicated, block: B:33:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:55:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:59:0x011b  */
    /* JADX WARN: Code duplicated, block: B:60:0x011f  */
    /* JADX WARN: Code duplicated, block: B:63:0x012c  */
    /* JADX WARN: Code duplicated, block: B:73:? A[RETURN, SYNTHETIC] */
    private static void calculateScaling(ImageHeaderParser.ImageType imageType, ImageReader imageReader, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, BitmapFactory.Options options) throws IOException {
        int i6;
        int i7;
        String str;
        int iMax;
        int iFloor;
        double dFloor;
        int iRound;
        double scaleFactor;
        String str2;
        int i8;
        if (i2 <= 0 || i3 <= 0) {
            if (Log.isLoggable("Downsampler", 3)) {
                Log.d("Downsampler", "Unable to determine dimensions for: " + imageType + " with target [" + i4 + "x" + i5 + "]");
                return;
            }
            return;
        }
        if (isRotationRequired(i)) {
            i7 = i2;
            i6 = i3;
        } else {
            i6 = i2;
            i7 = i3;
        }
        float scaleFactor2 = downsampleStrategy.getScaleFactor(i6, i7, i4, i5);
        if (scaleFactor2 <= 0.0f) {
            throw new IllegalArgumentException("Cannot scale with factor: " + scaleFactor2 + " from: " + downsampleStrategy + ", source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "]");
        }
        DownsampleStrategy.SampleSizeRounding sampleSizeRounding = downsampleStrategy.getSampleSizeRounding(i6, i7, i4, i5);
        if (sampleSizeRounding == null) {
            throw new IllegalArgumentException("Cannot round with null rounding");
        }
        float f = i6;
        float f2 = i7;
        int iRound2 = i6 / round(scaleFactor2 * f);
        int iRound3 = i7 / round(scaleFactor2 * f2);
        DownsampleStrategy.SampleSizeRounding sampleSizeRounding2 = DownsampleStrategy.SampleSizeRounding.MEMORY;
        int iMax2 = sampleSizeRounding == sampleSizeRounding2 ? Math.max(iRound2, iRound3) : Math.min(iRound2, iRound3);
        int i9 = Build.VERSION.SDK_INT;
        if (i9 <= 23) {
            str = "Downsampler";
            if (NO_DOWNSAMPLE_PRE_N_MIME_TYPES.contains(options.outMimeType)) {
                iMax = 1;
            }
            options.inSampleSize = iMax;
            if (imageType == ImageHeaderParser.ImageType.JPEG) {
                float fMin = Math.min(iMax, 8);
                iFloor = (int) Math.ceil(f / fMin);
                iRound = (int) Math.ceil(f2 / fMin);
                i8 = iMax / 8;
                if (i8 > 0) {
                    iFloor /= i8;
                    iRound /= i8;
                }
            } else {
                if (imageType != ImageHeaderParser.ImageType.PNG || imageType == ImageHeaderParser.ImageType.PNG_A) {
                    float f3 = iMax;
                    iFloor = (int) Math.floor(f / f3);
                    dFloor = Math.floor(f2 / f3);
                } else if (imageType == ImageHeaderParser.ImageType.WEBP || imageType == ImageHeaderParser.ImageType.WEBP_A) {
                    if (i9 >= 24) {
                        float f4 = iMax;
                        iFloor = Math.round(f / f4);
                        iRound = Math.round(f2 / f4);
                    } else {
                        float f5 = iMax;
                        iFloor = (int) Math.floor(f / f5);
                        dFloor = Math.floor(f2 / f5);
                    }
                } else if (i6 % iMax == 0 && i7 % iMax == 0) {
                    iFloor = i6 / iMax;
                    iRound = i7 / iMax;
                } else {
                    int[] dimensions = getDimensions(imageReader, options, decodeCallbacks, bitmapPool);
                    iFloor = dimensions[0];
                    iRound = dimensions[1];
                }
                iRound = (int) dFloor;
            }
            scaleFactor = downsampleStrategy.getScaleFactor(iFloor, iRound, i4, i5);
            options.inTargetDensity = adjustTargetDensityForError(scaleFactor);
            options.inDensity = getDensityMultiplier(scaleFactor);
            if (isScaling(options)) {
                options.inScaled = true;
            } else {
                options.inTargetDensity = 0;
                options.inDensity = 0;
            }
            str2 = str;
            if (Log.isLoggable(str2, 2)) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Calculate scaling, source: [", i2, "x", i3, "], degreesToRotate: ");
                sbM.append(i);
                sbM.append(", target: [");
                sbM.append(i4);
                sbM.append("x");
                sbM.append(i5);
                sbM.append("], power of two scaled: [");
                sbM.append(iFloor);
                sbM.append("x");
                sbM.append(iRound);
                sbM.append("], exact scale factor: ");
                sbM.append(scaleFactor2);
                sbM.append(", power of 2 sample size: ");
                sbM.append(iMax);
                sbM.append(", adjusted scale factor: ");
                sbM.append(scaleFactor);
                sbM.append(", target density: ");
                sbM.append(options.inTargetDensity);
                sbM.append(", density: ");
                sbM.append(options.inDensity);
                Log.v(str2, sbM.toString());
            }
        }
        str = "Downsampler";
        iMax = Math.max(1, Integer.highestOneBit(iMax2));
        if (sampleSizeRounding == sampleSizeRounding2 && iMax < 1.0f / scaleFactor2) {
            iMax <<= 1;
        }
        options.inSampleSize = iMax;
        if (imageType == ImageHeaderParser.ImageType.JPEG) {
            float fMin2 = Math.min(iMax, 8);
            iFloor = (int) Math.ceil(f / fMin2);
            iRound = (int) Math.ceil(f2 / fMin2);
            i8 = iMax / 8;
            if (i8 > 0) {
                iFloor /= i8;
                iRound /= i8;
            }
        } else if (imageType != ImageHeaderParser.ImageType.PNG) {
            float f6 = iMax;
            iFloor = (int) Math.floor(f / f6);
            dFloor = Math.floor(f2 / f6);
            iRound = (int) dFloor;
        } else {
            float f7 = iMax;
            iFloor = (int) Math.floor(f / f7);
            dFloor = Math.floor(f2 / f7);
            iRound = (int) dFloor;
        }
        scaleFactor = downsampleStrategy.getScaleFactor(iFloor, iRound, i4, i5);
        options.inTargetDensity = adjustTargetDensityForError(scaleFactor);
        options.inDensity = getDensityMultiplier(scaleFactor);
        if (isScaling(options)) {
            options.inScaled = true;
        } else {
            options.inTargetDensity = 0;
            options.inDensity = 0;
        }
        str2 = str;
        if (Log.isLoggable(str2, 2)) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Calculate scaling, source: [", i2, "x", i3, "], degreesToRotate: ");
            sbM2.append(i);
            sbM2.append(", target: [");
            sbM2.append(i4);
            sbM2.append("x");
            sbM2.append(i5);
            sbM2.append("], power of two scaled: [");
            sbM2.append(iFloor);
            sbM2.append("x");
            sbM2.append(iRound);
            sbM2.append("], exact scale factor: ");
            sbM2.append(scaleFactor2);
            sbM2.append(", power of 2 sample size: ");
            sbM2.append(iMax);
            sbM2.append(", adjusted scale factor: ");
            sbM2.append(scaleFactor);
            sbM2.append(", target density: ");
            sbM2.append(options.inTargetDensity);
            sbM2.append(", density: ");
            sbM2.append(options.inDensity);
            Log.v(str2, sbM2.toString());
        }
    }

    private Bitmap decodeFromWrappedStreams(ImageReader imageReader, BitmapFactory.Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, PreferredColorSpace preferredColorSpace, boolean z, int i, int i2, boolean z2, DecodeCallbacks decodeCallbacks) throws IOException {
        int i3;
        int i4;
        int i5;
        ColorSpace colorSpace;
        int iRound;
        int iRound2;
        long logTime = LogTime.getLogTime();
        int[] dimensions = getDimensions(imageReader, options, decodeCallbacks, this.bitmapPool);
        boolean z3 = false;
        int i6 = dimensions[0];
        int i7 = dimensions[1];
        String str = options.outMimeType;
        boolean z4 = (i6 == -1 || i7 == -1) ? false : z;
        int imageOrientation = imageReader.getImageOrientation();
        int exifOrientationDegrees = TransformationUtils.getExifOrientationDegrees(imageOrientation);
        boolean zIsExifOrientationRequired = TransformationUtils.isExifOrientationRequired(imageOrientation);
        if (i == Integer.MIN_VALUE) {
            i3 = i2;
            i4 = isRotationRequired(exifOrientationDegrees) ? i7 : i6;
        } else {
            i3 = i2;
            i4 = i;
        }
        if (i3 == Integer.MIN_VALUE) {
            i5 = isRotationRequired(exifOrientationDegrees) ? i6 : i7;
        } else {
            i5 = i3;
        }
        ImageHeaderParser.ImageType imageType = imageReader.getImageType();
        calculateScaling(imageType, imageReader, decodeCallbacks, this.bitmapPool, downsampleStrategy, exifOrientationDegrees, i6, i7, i4, i5, options);
        calculateConfig(imageReader, decodeFormat, z4, zIsExifOrientationRequired, options, i4, i5);
        int i8 = Build.VERSION.SDK_INT;
        int i9 = options.inSampleSize;
        if (shouldUsePool(imageType)) {
            if (i6 < 0 || i7 < 0 || !z2) {
                float f = isScaling(options) ? options.inTargetDensity / options.inDensity : 1.0f;
                int i10 = options.inSampleSize;
                float f2 = i10;
                int iCeil = (int) Math.ceil(i6 / f2);
                int iCeil2 = (int) Math.ceil(i7 / f2);
                iRound = Math.round(iCeil * f);
                iRound2 = Math.round(iCeil2 * f);
                if (Log.isLoggable("Downsampler", 2)) {
                    StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Calculated target [", iRound, "x", iRound2, "] for source [");
                    sbM.append(i6);
                    sbM.append("x");
                    sbM.append(i7);
                    sbM.append("], sampleSize: ");
                    sbM.append(i10);
                    sbM.append(", targetDensity: ");
                    sbM.append(options.inTargetDensity);
                    sbM.append(", density: ");
                    sbM.append(options.inDensity);
                    sbM.append(", density multiplier: ");
                    sbM.append(f);
                    Log.v("Downsampler", sbM.toString());
                }
            } else {
                iRound = i4;
                iRound2 = i5;
            }
            if (iRound > 0 && iRound2 > 0) {
                setInBitmap(options, this.bitmapPool, iRound, iRound2);
            }
        }
        if (i8 >= 28) {
            if (preferredColorSpace == PreferredColorSpace.DISPLAY_P3 && (colorSpace = options.outColorSpace) != null && colorSpace.isWideGamut()) {
                z3 = true;
            }
            options.inPreferredColorSpace = ColorSpace.get(z3 ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB);
        } else if (i8 >= 26) {
            options.inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        Bitmap bitmapDecodeStream = decodeStream(imageReader, options, decodeCallbacks, this.bitmapPool);
        decodeCallbacks.onDecodeComplete(this.bitmapPool, bitmapDecodeStream);
        if (Log.isLoggable("Downsampler", 2)) {
            logDecode(i6, i7, str, options, bitmapDecodeStream, i, i2, logTime);
        }
        Bitmap bitmapRotateImageExif = null;
        if (bitmapDecodeStream != null) {
            bitmapDecodeStream.setDensity(this.displayMetrics.densityDpi);
            bitmapRotateImageExif = TransformationUtils.rotateImageExif(this.bitmapPool, bitmapDecodeStream, imageOrientation);
            if (!bitmapDecodeStream.equals(bitmapRotateImageExif)) {
                this.bitmapPool.put(bitmapDecodeStream);
            }
        }
        return bitmapRotateImageExif;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:?, code lost:
    
        throw r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Bitmap decodeStream(com.bumptech.glide.load.resource.bitmap.ImageReader r5, android.graphics.BitmapFactory.Options r6, com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks r7, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool r8) throws java.io.IOException {
        /*
            java.lang.String r0 = "Downsampler"
            boolean r1 = r6.inJustDecodeBounds
            if (r1 != 0) goto Lc
            r7.onObtainBounds()
            r5.stopGrowingBuffers()
        Lc:
            int r1 = r6.outWidth
            int r2 = r6.outHeight
            java.lang.String r3 = r6.outMimeType
            java.util.concurrent.locks.Lock r4 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r4.lock()
            android.graphics.Bitmap r5 = r5.decodeBitmap(r6)     // Catch: java.lang.Throwable -> L25 java.lang.IllegalArgumentException -> L27
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            return r5
        L25:
            r5 = move-exception
            goto L50
        L27:
            r4 = move-exception
            java.io.IOException r1 = newIoExceptionForInBitmapAssertion(r4, r1, r2, r3, r6)     // Catch: java.lang.Throwable -> L25
            r2 = 3
            boolean r2 = android.util.Log.isLoggable(r0, r2)     // Catch: java.lang.Throwable -> L25
            if (r2 == 0) goto L38
            java.lang.String r2 = "Failed to decode with inBitmap, trying again without Bitmap re-use"
            android.util.Log.d(r0, r2, r1)     // Catch: java.lang.Throwable -> L25
        L38:
            android.graphics.Bitmap r0 = r6.inBitmap     // Catch: java.lang.Throwable -> L25
            if (r0 == 0) goto L4f
            r8.put(r0)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L4e
            r0 = 0
            r6.inBitmap = r0     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L4e
            android.graphics.Bitmap r5 = decodeStream(r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L4e
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            return r5
        L4e:
            throw r1     // Catch: java.lang.Throwable -> L25
        L4f:
            throw r1     // Catch: java.lang.Throwable -> L25
        L50:
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.Downsampler.decodeStream(com.bumptech.glide.load.resource.bitmap.ImageReader, android.graphics.BitmapFactory$Options, com.bumptech.glide.load.resource.bitmap.Downsampler$DecodeCallbacks, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool):android.graphics.Bitmap");
    }

    @Nullable
    @TargetApi(19)
    private static String getBitmapString(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(" (");
        sbM.append(bitmap.getAllocationByteCount());
        sbM.append(")");
        String string = sbM.toString();
        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("[");
        sbM2.append(bitmap.getWidth());
        sbM2.append("x");
        sbM2.append(bitmap.getHeight());
        sbM2.append("] ");
        sbM2.append(bitmap.getConfig());
        sbM2.append(string);
        return sbM2.toString();
    }

    private static synchronized BitmapFactory.Options getDefaultOptions() {
        BitmapFactory.Options optionsPoll;
        Queue<BitmapFactory.Options> queue = OPTIONS_QUEUE;
        synchronized (queue) {
            optionsPoll = queue.poll();
        }
        if (optionsPoll == null) {
            optionsPoll = new BitmapFactory.Options();
            resetOptions(optionsPoll);
        }
        return optionsPoll;
    }

    private static int getDensityMultiplier(double d) {
        if (d > 1.0d) {
            d = 1.0d / d;
        }
        return (int) Math.round(d * 2.147483647E9d);
    }

    private static int[] getDimensions(ImageReader imageReader, BitmapFactory.Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool) throws IOException {
        options.inJustDecodeBounds = true;
        decodeStream(imageReader, options, decodeCallbacks, bitmapPool);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static String getInBitmapString(BitmapFactory.Options options) {
        return getBitmapString(options.inBitmap);
    }

    private static boolean isRotationRequired(int i) {
        return i == 90 || i == 270;
    }

    private static boolean isScaling(BitmapFactory.Options options) {
        int i;
        int i2 = options.inTargetDensity;
        return i2 > 0 && (i = options.inDensity) > 0 && i2 != i;
    }

    private static void logDecode(int i, int i2, String str, BitmapFactory.Options options, Bitmap bitmap, int i3, int i4, long j) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Decoded ");
        sbM.append(getBitmapString(bitmap));
        sbM.append(" from [");
        sbM.append(i);
        sbM.append("x");
        sbM.append(i2);
        sbM.append("] ");
        sbM.append(str);
        sbM.append(" with inBitmap ");
        sbM.append(getInBitmapString(options));
        sbM.append(" for [");
        sbM.append(i3);
        sbM.append("x");
        sbM.append(i4);
        sbM.append("], sample size: ");
        sbM.append(options.inSampleSize);
        sbM.append(", density: ");
        sbM.append(options.inDensity);
        sbM.append(", target density: ");
        sbM.append(options.inTargetDensity);
        sbM.append(", thread: ");
        sbM.append(Thread.currentThread().getName());
        sbM.append(", duration: ");
        sbM.append(LogTime.getElapsedMillis(j));
        Log.v("Downsampler", sbM.toString());
    }

    private static IOException newIoExceptionForInBitmapAssertion(IllegalArgumentException illegalArgumentException, int i, int i2, String str, BitmapFactory.Options options) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Exception decoding bitmap, outWidth: ", i, ", outHeight: ", i2, ", outMimeType: ");
        sbM.append(str);
        sbM.append(", inBitmap: ");
        sbM.append(getInBitmapString(options));
        return new IOException(sbM.toString(), illegalArgumentException);
    }

    private static void releaseOptions(BitmapFactory.Options options) {
        resetOptions(options);
        Queue<BitmapFactory.Options> queue = OPTIONS_QUEUE;
        synchronized (queue) {
            queue.offer(options);
        }
    }

    private static void resetOptions(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            options.inPreferredColorSpace = null;
            options.outColorSpace = null;
            options.outConfig = null;
        }
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }

    private static int round(double d) {
        return (int) (d + 0.5d);
    }

    @TargetApi(26)
    private static void setInBitmap(BitmapFactory.Options options, BitmapPool bitmapPool, int i, int i2) {
        Bitmap.Config config;
        if (Build.VERSION.SDK_INT < 26) {
            config = null;
        } else if (options.inPreferredConfig == Bitmap.Config.HARDWARE) {
            return;
        } else {
            config = options.outConfig;
        }
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = bitmapPool.getDirty(i, i2, config);
    }

    private boolean shouldUsePool(ImageHeaderParser.ImageType imageType) {
        return true;
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options) throws IOException {
        return decode(inputStream, i, i2, options, EMPTY_CALLBACKS);
    }

    public boolean handles(ParcelFileDescriptor parcelFileDescriptor) {
        return ParcelFileDescriptorRewinder.isSupported();
    }

    public boolean handles(InputStream inputStream) {
        return true;
    }

    public boolean handles(ByteBuffer byteBuffer) {
        return true;
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        return decode(new ImageReader.InputStreamImageReader(inputStream, this.parsers, this.byteArrayPool), i, i2, options, decodeCallbacks);
    }

    @RequiresApi(21)
    public Resource<Bitmap> decode(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, Options options) throws IOException {
        return decode(new ImageReader.ParcelFileDescriptorImageReader(parcelFileDescriptor, this.parsers, this.byteArrayPool), i, i2, options, EMPTY_CALLBACKS);
    }

    private Resource<Bitmap> decode(ImageReader imageReader, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        byte[] bArr = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        BitmapFactory.Options defaultOptions = getDefaultOptions();
        defaultOptions.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) options.get(DECODE_FORMAT);
        PreferredColorSpace preferredColorSpace = (PreferredColorSpace) options.get(PREFERRED_COLOR_SPACE);
        DownsampleStrategy downsampleStrategy = (DownsampleStrategy) options.get(DownsampleStrategy.OPTION);
        boolean zBooleanValue = ((Boolean) options.get(FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS)).booleanValue();
        Option<Boolean> option = ALLOW_HARDWARE_CONFIG;
        try {
            return BitmapResource.obtain(decodeFromWrappedStreams(imageReader, defaultOptions, downsampleStrategy, decodeFormat, preferredColorSpace, options.get(option) != null && ((Boolean) options.get(option)).booleanValue(), i, i2, zBooleanValue, decodeCallbacks), this.bitmapPool);
        } finally {
            releaseOptions(defaultOptions);
            this.byteArrayPool.put(bArr);
        }
    }
}
