package com.google.android.exoplayer2.ext.cronet;

import android.content.Context;
import androidx.annotation.Nullable;
import org.chromium.net.CronetEngine;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class CronetEngineWrapper {

    @Nullable
    private final CronetEngine cronetEngine;

    public CronetEngineWrapper(Context context) {
        this(context, null, false);
    }

    @Nullable
    public final CronetEngine getCronetEngine() {
        return this.cronetEngine;
    }

    public CronetEngineWrapper(Context context, @Nullable String str, boolean z) {
        this.cronetEngine = CronetUtil.buildCronetEngine(context, str, z);
    }

    public CronetEngineWrapper(CronetEngine cronetEngine) {
        this.cronetEngine = cronetEngine;
    }
}
