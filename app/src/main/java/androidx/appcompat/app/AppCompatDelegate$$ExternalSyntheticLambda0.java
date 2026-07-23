package androidx.appcompat.app;

import android.content.Context;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class AppCompatDelegate$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;

    public /* synthetic */ AppCompatDelegate$$ExternalSyntheticLambda0(Context context, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AppCompatDelegate.syncRequestedAndStoredLocales(this.f$0);
                break;
            default:
                AppCompatDelegate.lambda$syncRequestedAndStoredLocales$1(this.f$0);
                break;
        }
    }
}
