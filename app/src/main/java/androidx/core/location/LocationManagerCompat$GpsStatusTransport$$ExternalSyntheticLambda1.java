package androidx.core.location;

import android.location.GnssStatus;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda1(Object obj, Executor executor, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = executor;
        this.f$2 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((LocationManagerCompat.GpsStatusTransport) this.f$0).lambda$onGpsStatusChanged$3(this.f$1, (GnssStatusCompat) this.f$2);
                break;
            default:
                ((LocationManagerCompat.PreRGnssStatusTransport) this.f$0).lambda$onSatelliteStatusChanged$3(this.f$1, (GnssStatus) this.f$2);
                break;
        }
    }
}
