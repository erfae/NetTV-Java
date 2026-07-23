package com.google.android.play.core.splitinstall.testing;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.play.core.common.LocalTestingException;
import com.google.android.play.core.internal.zzco;
import com.google.android.play.core.splitcompat.SplitCompat;
import java.io.File;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class FakeSplitInstallManagerFactory {

    @Nullable
    private static FakeSplitInstallManager zza;

    private FakeSplitInstallManagerFactory() {
    }

    public static FakeSplitInstallManager create(Context context) {
        try {
            File fileZzb = com.google.android.play.core.splitinstall.zzu.zza(context).zzb();
            if (fileZzb == null) {
                throw new LocalTestingException("Failed to retrieve local testing directory path");
            }
            if (fileZzb.exists()) {
                return create(context, fileZzb);
            }
            throw new LocalTestingException(String.format("Local testing directory not found: %s", fileZzb));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FakeSplitInstallManager createNewInstance(Context context, final File file) {
        SplitCompat.install(context);
        return new FakeSplitInstallManager(context, file, new com.google.android.play.core.splitinstall.zzs(context, context.getPackageName()), new zzco() { // from class: com.google.android.play.core.splitinstall.testing.zzq
            @Override // com.google.android.play.core.internal.zzco
            public final Object zza() {
                return zzy.zza(file);
            }
        });
    }

    public static synchronized FakeSplitInstallManager create(Context context, File file) {
        FakeSplitInstallManager fakeSplitInstallManager = zza;
        if (fakeSplitInstallManager == null) {
            zza = createNewInstance(context, file);
        } else if (!fakeSplitInstallManager.zzc().getAbsolutePath().equals(file.getAbsolutePath())) {
            throw new RuntimeException(String.format("Different module directories used to initialize FakeSplitInstallManager: '%s' and '%s'", zza.zzc().getAbsolutePath(), file.getAbsolutePath()));
        }
        return zza;
    }
}
