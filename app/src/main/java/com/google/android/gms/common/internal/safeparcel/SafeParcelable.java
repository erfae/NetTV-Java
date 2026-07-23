package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcelable;
import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public interface SafeParcelable extends Parcelable {

    @NonNull
    public static final String NULL = "SAFE_PARCELABLE_NULL_STRING";

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface Class {
        @NonNull
        String creator();

        boolean doNotParcelTypeDefaultValues() default false;

        boolean validate() default false;
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface Constructor {
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface Field {
        @NonNull
        String defaultValue() default "SAFE_PARCELABLE_NULL_STRING";

        @NonNull
        String defaultValueUnchecked() default "SAFE_PARCELABLE_NULL_STRING";

        @NonNull
        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        int id();

        @NonNull
        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface Indicator {
        @NonNull
        String getter() default "SAFE_PARCELABLE_NULL_STRING";
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface Param {
        int id();
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface Reserved {
        @NonNull
        int[] value();
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public @interface VersionField {
        @NonNull
        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        int id();

        @NonNull
        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }
}
