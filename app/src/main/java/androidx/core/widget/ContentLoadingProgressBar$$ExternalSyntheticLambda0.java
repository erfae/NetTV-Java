package androidx.core.widget;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ContentLoadingProgressBar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContentLoadingProgressBar f$0;

    public /* synthetic */ ContentLoadingProgressBar$$ExternalSyntheticLambda0(ContentLoadingProgressBar contentLoadingProgressBar, int i) {
        this.$r8$classId = i;
        this.f$0 = contentLoadingProgressBar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.showOnUiThread();
                break;
            case 1:
                this.f$0.hideOnUiThread();
                break;
            case 2:
                this.f$0.lambda$new$0();
                break;
            default:
                this.f$0.lambda$new$1();
                break;
        }
    }
}
