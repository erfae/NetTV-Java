package androidx.leanback.app;

import android.app.Fragment;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class BackgroundFragment extends Fragment {
    private BackgroundManager mBackgroundManager;

    public final BackgroundManager getBackgroundManager() {
        return this.mBackgroundManager;
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.detach();
        }
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.postChangeRunnable();
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.onActivityStart();
        }
    }

    @Override // android.app.Fragment
    public void onStop() {
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null && backgroundManager.isAutoReleaseOnStop()) {
            backgroundManager.release();
        }
        super.onStop();
    }

    public final void setBackgroundManager(BackgroundManager backgroundManager) {
        this.mBackgroundManager = backgroundManager;
    }
}
