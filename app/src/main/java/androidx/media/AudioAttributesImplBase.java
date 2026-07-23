package androidx.media;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class AudioAttributesImplBase implements AudioAttributesImpl {

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mContentType;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mFlags;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mLegacyStream;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mUsage;

    public static class Builder implements AudioAttributesImpl.Builder {
        private int mContentType;
        private int mFlags;
        private int mLegacyStream;
        private int mUsage;

        public Builder() {
            this.mUsage = 0;
            this.mContentType = 0;
            this.mFlags = 0;
            this.mLegacyStream = -1;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private Builder setInternalLegacyStreamType(int i) {
            int i2 = 2;
            switch (i) {
                case 0:
                    this.mContentType = 1;
                    break;
                case 1:
                    this.mContentType = 4;
                    break;
                case 2:
                    this.mContentType = 4;
                    break;
                case 3:
                    this.mContentType = 2;
                    break;
                case 4:
                    this.mContentType = 4;
                    break;
                case 5:
                    this.mContentType = 4;
                    break;
                case 6:
                    this.mContentType = 1;
                    this.mFlags |= 4;
                    break;
                case 7:
                    this.mFlags |= 1;
                    this.mContentType = 4;
                    break;
                case 8:
                    this.mContentType = 4;
                    break;
                case 9:
                    this.mContentType = 4;
                    break;
                case 10:
                    this.mContentType = 1;
                    break;
                default:
                    Log.e("AudioAttributesCompat", "Invalid stream type " + i + " for AudioAttributesCompat");
                    break;
            }
            switch (i) {
                case 0:
                case 6:
                    break;
                case 1:
                case 7:
                    i2 = 13;
                    break;
                case 2:
                    i2 = 6;
                    break;
                case 3:
                    i2 = 1;
                    break;
                case 4:
                    i2 = 4;
                    break;
                case 5:
                    i2 = 5;
                    break;
                case 8:
                    i2 = 3;
                    break;
                case 9:
                default:
                    i2 = 0;
                    break;
                case 10:
                    i2 = 11;
                    break;
            }
            this.mUsage = i2;
            return this;
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public AudioAttributesImpl build() {
            return new AudioAttributesImplBase(this.mContentType, this.mFlags, this.mUsage, this.mLegacyStream);
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setContentType(int i) {
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
                this.mContentType = i;
            } else {
                this.mContentType = 0;
            }
            return this;
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setFlags(int i) {
            this.mFlags = (i & AnalyticsListener.EVENT_DRM_KEYS_LOADED) | this.mFlags;
            return this;
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setLegacyStreamType(int i) {
            if (i == 10) {
                throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
            }
            this.mLegacyStream = i;
            return setInternalLegacyStreamType(i);
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setUsage(int i) {
            switch (i) {
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
                    this.mUsage = i;
                    return this;
                case 16:
                    this.mUsage = 12;
                    return this;
                default:
                    this.mUsage = 0;
                    return this;
            }
        }

        public Builder(AudioAttributesCompat audioAttributesCompat) {
            this.mUsage = 0;
            this.mContentType = 0;
            this.mFlags = 0;
            this.mLegacyStream = -1;
            this.mUsage = audioAttributesCompat.getUsage();
            this.mContentType = audioAttributesCompat.getContentType();
            this.mFlags = audioAttributesCompat.getFlags();
            this.mLegacyStream = audioAttributesCompat.mImpl.getRawLegacyStreamType();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributesImplBase() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mFlags = 0;
        this.mLegacyStream = -1;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesImplBase)) {
            return false;
        }
        AudioAttributesImplBase audioAttributesImplBase = (AudioAttributesImplBase) obj;
        return this.mContentType == audioAttributesImplBase.getContentType() && this.mFlags == audioAttributesImplBase.getFlags() && this.mUsage == audioAttributesImplBase.getUsage() && this.mLegacyStream == audioAttributesImplBase.mLegacyStream;
    }

    @Override // androidx.media.AudioAttributesImpl
    @Nullable
    public Object getAudioAttributes() {
        return null;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getContentType() {
        return this.mContentType;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getFlags() {
        int i = this.mFlags;
        int legacyStreamType = getLegacyStreamType();
        if (legacyStreamType == 6) {
            i |= 4;
        } else if (legacyStreamType == 7) {
            i |= 1;
        }
        return i & 273;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getLegacyStreamType() {
        int i = this.mLegacyStream;
        return i != -1 ? i : AudioAttributesCompat.toVolumeStreamType(false, this.mFlags, this.mUsage);
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getRawLegacyStreamType() {
        return this.mLegacyStream;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getUsage() {
        return this.mUsage;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getVolumeControlStream() {
        return AudioAttributesCompat.toVolumeStreamType(true, this.mFlags, this.mUsage);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), Integer.valueOf(this.mLegacyStream)});
    }

    @NonNull
    public String toString() {
        String strM23m;
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (this.mLegacyStream != -1) {
            sb.append(" stream=");
            sb.append(this.mLegacyStream);
            sb.append(" derived");
        }
        sb.append(" usage=");
        int i = this.mUsage;
        int i2 = AudioAttributesCompat.CONTENT_TYPE_UNKNOWN;
        switch (i) {
            case 0:
                strM23m = "USAGE_UNKNOWN";
                break;
            case 1:
                strM23m = "USAGE_MEDIA";
                break;
            case 2:
                strM23m = "USAGE_VOICE_COMMUNICATION";
                break;
            case 3:
                strM23m = "USAGE_VOICE_COMMUNICATION_SIGNALLING";
                break;
            case 4:
                strM23m = "USAGE_ALARM";
                break;
            case 5:
                strM23m = "USAGE_NOTIFICATION";
                break;
            case 6:
                strM23m = "USAGE_NOTIFICATION_RINGTONE";
                break;
            case 7:
                strM23m = "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
                break;
            case 8:
                strM23m = "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
                break;
            case 9:
                strM23m = "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
                break;
            case 10:
                strM23m = "USAGE_NOTIFICATION_EVENT";
                break;
            case 11:
                strM23m = "USAGE_ASSISTANCE_ACCESSIBILITY";
                break;
            case 12:
                strM23m = "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
                break;
            case 13:
                strM23m = "USAGE_ASSISTANCE_SONIFICATION";
                break;
            case 14:
                strM23m = "USAGE_GAME";
                break;
            case 15:
            default:
                strM23m = Insets$$ExternalSyntheticOutline0.m23m("unknown usage ", i);
                break;
            case 16:
                strM23m = "USAGE_ASSISTANT";
                break;
        }
        sb.append(strM23m);
        sb.append(" content=");
        sb.append(this.mContentType);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.mFlags).toUpperCase());
        return sb.toString();
    }

    public AudioAttributesImplBase(int i, int i2, int i3, int i4) {
        this.mContentType = i;
        this.mFlags = i2;
        this.mUsage = i3;
        this.mLegacyStream = i4;
    }
}
