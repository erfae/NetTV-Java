package androidx.core.view;

import android.view.View;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ViewKt$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ViewKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ViewKt.m39postOnAnimationDelayed$lambda1((Function0) this.f$0);
                break;
            default:
                WindowInsetsControllerCompat.Impl20.lambda$showForType$0((View) this.f$0);
                break;
        }
    }
}
