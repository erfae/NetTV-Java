package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ Location f$1;

    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda1(Consumer consumer, Location location, int i) {
        this.$r8$classId = i;
        this.f$0 = consumer;
        this.f$1 = location;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.accept(this.f$1);
                break;
            default:
                this.f$0.accept(this.f$1);
                break;
        }
    }
}
