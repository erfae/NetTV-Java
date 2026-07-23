package androidx.core.content.res;

import com.google.android.material.sidesheet.SideSheetBehavior;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ResourcesCompat$FontCallback$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ResourcesCompat.FontCallback) this.f$0).lambda$callbackFailAsync$1(this.f$1);
                break;
            default:
                ((SideSheetBehavior) this.f$0).lambda$setState$0(this.f$1);
                break;
        }
    }
}
