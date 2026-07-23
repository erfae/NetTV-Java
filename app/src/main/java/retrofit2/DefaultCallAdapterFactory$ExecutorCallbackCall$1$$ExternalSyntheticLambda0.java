package retrofit2;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class DefaultCallAdapterFactory$ExecutorCallbackCall$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultCallAdapterFactory.ExecutorCallbackCall.AnonymousClass1 f$0;
    public final /* synthetic */ Callback f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DefaultCallAdapterFactory$ExecutorCallbackCall$1$$ExternalSyntheticLambda0(DefaultCallAdapterFactory.ExecutorCallbackCall.AnonymousClass1 anonymousClass1, Callback callback, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = callback;
        this.f$2 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onResponse$0(this.f$1, (Response) this.f$2);
                break;
            default:
                this.f$0.lambda$onFailure$1(this.f$1, (Throwable) this.f$2);
                break;
        }
    }
}
