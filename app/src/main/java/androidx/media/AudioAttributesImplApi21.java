package androidx.media;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class AudioAttributesImplApi21 implements AudioAttributesImpl {

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributes mAudioAttributes;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mLegacyStreamType = -1;

    @RequiresApi(21)
    public static class Builder implements AudioAttributesImpl.Builder {
        public final AudioAttributes.Builder mFwkBuilder;

        public Builder() {
            this.mFwkBuilder = new AudioAttributes.Builder();
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public AudioAttributesImpl build() {
            return new AudioAttributesImplApi21(this.mFwkBuilder.build());
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setContentType(int i) {
            this.mFwkBuilder.setContentType(i);
            return this;
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setFlags(int i) {
            this.mFwkBuilder.setFlags(i);
            return this;
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public Builder setLegacyStreamType(int i) {
            this.mFwkBuilder.setLegacyStreamType(i);
            return this;
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        @SuppressLint({"WrongConstant"})
        public Builder setUsage(int i) {
            if (i == 16) {
                i = 12;
            }
            this.mFwkBuilder.setUsage(i);
            return this;
        }

        public Builder(Object obj) {
            this.mFwkBuilder = new AudioAttributes.Builder((AudioAttributes) obj);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributesImplApi21() {
    }

    public boolean equals(Object obj) {
        if (obj instanceof AudioAttributesImplApi21) {
            return this.mAudioAttributes.equals(((AudioAttributesImplApi21) obj).mAudioAttributes);
        }
        return false;
    }

    @Override // androidx.media.AudioAttributesImpl
    @Nullable
    public Object getAudioAttributes() {
        return this.mAudioAttributes;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getContentType() {
        return this.mAudioAttributes.getContentType();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getFlags() {
        return this.mAudioAttributes.getFlags();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getLegacyStreamType() {
        int i = this.mLegacyStreamType;
        return i != -1 ? i : AudioAttributesCompat.toVolumeStreamType(false, getFlags(), getUsage());
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getRawLegacyStreamType() {
        return this.mLegacyStreamType;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getUsage() {
        return this.mAudioAttributes.getUsage();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getVolumeControlStream() {
        return AudioAttributesCompat.toVolumeStreamType(true, getFlags(), getUsage());
    }

    public int hashCode() {
        return this.mAudioAttributes.hashCode();
    }

    @NonNull
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("AudioAttributesCompat: audioattributes=");
        sbM.append(this.mAudioAttributes);
        return sbM.toString();
    }

    public AudioAttributesImplApi21(AudioAttributes audioAttributes) {
        this.mAudioAttributes = audioAttributes;
    }

    public AudioAttributesImplApi21(AudioAttributes audioAttributes, int i) {
        this.mAudioAttributes = audioAttributes;
    }
}
