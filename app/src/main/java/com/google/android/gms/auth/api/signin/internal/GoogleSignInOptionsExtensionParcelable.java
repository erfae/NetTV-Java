package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class(creator = "GoogleSignInOptionsExtensionCreator")
public class GoogleSignInOptionsExtensionParcelable extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<GoogleSignInOptionsExtensionParcelable> CREATOR = new zaa();

    @SafeParcelable.VersionField(id = 1)
    public final int zaa;

    @SafeParcelable.Field(getter = "getType", id = 2)
    private int zab;

    @SafeParcelable.Field(getter = "getBundle", id = 3)
    private Bundle zac;

    @SafeParcelable.Constructor
    public GoogleSignInOptionsExtensionParcelable(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) Bundle bundle) {
        this.zaa = i;
        this.zab = i2;
        this.zac = bundle;
    }

    @KeepForSdk
    public int getType() {
        return this.zab;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeInt(parcel, 2, getType());
        SafeParcelWriter.writeBundle(parcel, 3, this.zac, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public GoogleSignInOptionsExtensionParcelable(@NonNull GoogleSignInOptionsExtension googleSignInOptionsExtension) {
        int extensionType = googleSignInOptionsExtension.getExtensionType();
        Bundle bundle = googleSignInOptionsExtension.toBundle();
        this.zaa = 1;
        this.zab = extensionType;
        this.zac = bundle;
    }
}
