package com.google.android.exoplayer2.util;

import android.view.SurfaceView;
import androidx.annotation.Nullable;
import io.realm.Realm$$ExternalSyntheticLambda0;

/* JADX INFO: loaded from: classes.dex */
public interface DebugViewProvider {
    public static final DebugViewProvider NONE = Realm$$ExternalSyntheticLambda0.INSTANCE$11;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.util.DebugViewProvider$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        static {
            DebugViewProvider debugViewProvider = DebugViewProvider.NONE;
        }

        public static /* synthetic */ SurfaceView lambda$static$0(int i, int i2) {
            return null;
        }
    }

    @Nullable
    SurfaceView getDebugPreviewSurfaceView(int i, int i2);
}
