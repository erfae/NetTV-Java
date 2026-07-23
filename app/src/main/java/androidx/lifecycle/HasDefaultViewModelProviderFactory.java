package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.viewmodel.CreationExtras;

/* JADX INFO: loaded from: classes.dex */
public interface HasDefaultViewModelProviderFactory {

    /* JADX INFO: renamed from: androidx.lifecycle.HasDefaultViewModelProviderFactory$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }

    @NonNull
    CreationExtras getDefaultViewModelCreationExtras();

    @NonNull
    ViewModelProvider.Factory getDefaultViewModelProviderFactory();
}
