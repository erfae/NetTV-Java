package androidx.core.location;

import java.util.concurrent.Executor;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda2(Object obj, Executor executor, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = executor;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((LocationManagerCompat.GpsStatusTransport) this.f$0).lambda$onGpsStatusChanged$2(this.f$1, this.f$2);
                break;
            default:
                ((LocationManagerCompat.PreRGnssStatusTransport) this.f$0).lambda$onFirstFix$2(this.f$1, this.f$2);
                break;
        }
    }
}
