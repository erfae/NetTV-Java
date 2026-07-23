package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public final class ServiceSpecificExtraArgs {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface CastExtraArgs {

        @NonNull
        @KeepForSdk
        public static final String LISTENER = "listener";
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface GamesExtraArgs {

        @NonNull
        @KeepForSdk
        public static final String DESIRED_LOCALE = "com.google.android.gms.games.key.desiredLocale";

        @NonNull
        @KeepForSdk
        public static final String GAME_PACKAGE_NAME = "com.google.android.gms.games.key.gamePackageName";

        @NonNull
        @KeepForSdk
        public static final String SIGNIN_OPTIONS = "com.google.android.gms.games.key.signInOptions";

        @NonNull
        @KeepForSdk
        public static final String WINDOW_TOKEN = "com.google.android.gms.games.key.popupWindowToken";
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface PlusExtraArgs {

        @NonNull
        @KeepForSdk
        public static final String PLUS_AUTH_PACKAGE = "auth_package";
    }

    private ServiceSpecificExtraArgs() {
    }
}
