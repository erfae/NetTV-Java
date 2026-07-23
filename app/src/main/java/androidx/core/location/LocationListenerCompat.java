package androidx.core.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface LocationListenerCompat extends LocationListener {

    /* JADX INFO: renamed from: androidx.core.location.LocationListenerCompat$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onFlushComplete(LocationListenerCompat locationListenerCompat, int i) {
        }

        public static void $default$onLocationChanged(LocationListenerCompat locationListenerCompat, @NonNull List list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                locationListenerCompat.onLocationChanged((Location) list.get(i));
            }
        }

        public static void $default$onProviderDisabled(LocationListenerCompat locationListenerCompat, @NonNull String str) {
        }

        public static void $default$onProviderEnabled(LocationListenerCompat locationListenerCompat, @NonNull String str) {
        }

        public static void $default$onStatusChanged(LocationListenerCompat locationListenerCompat, @NonNull String str, int i, @Nullable Bundle bundle) {
        }
    }

    @Override // android.location.LocationListener
    void onFlushComplete(int i);

    @Override // android.location.LocationListener
    void onLocationChanged(@NonNull List<Location> list);

    @Override // android.location.LocationListener
    void onProviderDisabled(@NonNull String str);

    @Override // android.location.LocationListener
    void onProviderEnabled(@NonNull String str);

    @Override // android.location.LocationListener
    void onStatusChanged(@NonNull String str, int i, @Nullable Bundle bundle);
}
