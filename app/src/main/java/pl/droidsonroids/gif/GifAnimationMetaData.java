package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Locale;
import kotlin.jvm.internal.CharCompanionObject;
import okhttp3.internal.ws.WebSocketProtocol;
import pl.droidsonroids.gif.annotations.Beta;

/* JADX INFO: loaded from: classes2.dex */
public class GifAnimationMetaData implements Serializable, Parcelable {
    public static final Parcelable.Creator<GifAnimationMetaData> CREATOR = new Parcelable.Creator<GifAnimationMetaData>() { // from class: pl.droidsonroids.gif.GifAnimationMetaData.1
        @Override // android.os.Parcelable.Creator
        public GifAnimationMetaData createFromParcel(Parcel parcel) {
            return new GifAnimationMetaData(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GifAnimationMetaData[] newArray(int i) {
            return new GifAnimationMetaData[i];
        }
    };
    private static final long serialVersionUID = 5692363926580237325L;
    private final int mDuration;
    private final int mHeight;
    private final int mImageCount;
    private final int mLoopCount;
    private final long mMetadataBytesCount;
    private final long mPixelsBytesCount;
    private final int mWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getAllocationByteCount() {
        return this.mPixelsBytesCount;
    }

    @Beta
    public long getDrawableAllocationByteCount(@Nullable GifDrawable gifDrawable, @IntRange(from = 1, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i) {
        if (i >= 1 && i <= 65535) {
            int i2 = i * i;
            return (this.mPixelsBytesCount / ((long) i2)) + ((long) ((gifDrawable == null || gifDrawable.mBuffer.isRecycled()) ? ((this.mWidth * this.mHeight) * 4) / i2 : gifDrawable.mBuffer.getAllocationByteCount()));
        }
        throw new IllegalStateException("Sample size " + i + " out of range <1, " + CharCompanionObject.MAX_VALUE + ">");
    }

    public int getDuration() {
        return this.mDuration;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getLoopCount() {
        return this.mLoopCount;
    }

    public long getMetadataAllocationByteCount() {
        return this.mMetadataBytesCount;
    }

    public int getNumberOfFrames() {
        return this.mImageCount;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public boolean isAnimated() {
        return this.mImageCount > 1 && this.mDuration > 0;
    }

    @NonNull
    public String toString() {
        int i = this.mLoopCount;
        String str = String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, loops: %s, duration: %d", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mImageCount), i == 0 ? "Infinity" : Integer.toString(i), Integer.valueOf(this.mDuration));
        return isAnimated() ? Insets$$ExternalSyntheticOutline0.m("Animated ", str) : str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLoopCount);
        parcel.writeInt(this.mDuration);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mImageCount);
        parcel.writeLong(this.mMetadataBytesCount);
        parcel.writeLong(this.mPixelsBytesCount);
    }

    public GifAnimationMetaData(@NonNull Resources resources, @DrawableRes @RawRes int i) throws Resources.NotFoundException, IOException {
        this(resources.openRawResourceFd(i));
    }

    public GifAnimationMetaData(@NonNull AssetManager assetManager, @NonNull String str) throws IOException {
        this(assetManager.openFd(str));
    }

    public GifAnimationMetaData(@NonNull String str) throws IOException {
        this(new GifInfoHandle(str));
    }

    public GifAnimationMetaData(@NonNull File file) throws IOException {
        this(file.getPath());
    }

    public GifAnimationMetaData(@NonNull InputStream inputStream) throws IOException {
        this(new GifInfoHandle(inputStream));
    }

    public GifAnimationMetaData(@NonNull AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor));
    }

    public GifAnimationMetaData(@NonNull FileDescriptor fileDescriptor) throws IOException {
        this(new GifInfoHandle(fileDescriptor));
    }

    public GifAnimationMetaData(@NonNull byte[] bArr) throws IOException {
        this(new GifInfoHandle(bArr));
    }

    public GifAnimationMetaData(@NonNull ByteBuffer byteBuffer) throws IOException {
        this(new GifInfoHandle(byteBuffer));
    }

    public GifAnimationMetaData(@Nullable ContentResolver contentResolver, @NonNull Uri uri) throws IOException {
        this(GifInfoHandle.openUri(contentResolver, uri));
    }

    private GifAnimationMetaData(GifInfoHandle gifInfoHandle) {
        this.mLoopCount = gifInfoHandle.getLoopCount();
        this.mDuration = gifInfoHandle.getDuration();
        this.mWidth = gifInfoHandle.getWidth();
        this.mHeight = gifInfoHandle.getHeight();
        this.mImageCount = gifInfoHandle.getNumberOfFrames();
        this.mMetadataBytesCount = gifInfoHandle.getMetadataByteCount();
        this.mPixelsBytesCount = gifInfoHandle.getAllocationByteCount();
        gifInfoHandle.recycle();
    }

    private GifAnimationMetaData(Parcel parcel) {
        this.mLoopCount = parcel.readInt();
        this.mDuration = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mWidth = parcel.readInt();
        this.mImageCount = parcel.readInt();
        this.mMetadataBytesCount = parcel.readLong();
        this.mPixelsBytesCount = parcel.readLong();
    }
}
