package androidx.core.location;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda1(LocationManagerCompat.LocationListenerTransport locationListenerTransport, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = locationListenerTransport;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onProviderEnabled$4(this.f$1);
                break;
            default:
                this.f$0.lambda$onProviderDisabled$5(this.f$1);
                break;
        }
    }
}
