package com.google.android.play.core.internal;

import androidx.annotation.GuardedBy;
import com.google.android.play.core.listener.StateUpdatedListener;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaf {

    @GuardedBy("this")
    public final HashSet zza = new HashSet();

    public final synchronized void zza(StateUpdatedListener stateUpdatedListener) {
        this.zza.add(stateUpdatedListener);
    }

    public final synchronized void zzb(StateUpdatedListener stateUpdatedListener) {
        this.zza.remove(stateUpdatedListener);
    }

    public final synchronized void zzc(Object obj) {
        Iterator it = this.zza.iterator();
        while (it.hasNext()) {
            ((StateUpdatedListener) it.next()).onStateUpdate(obj);
        }
    }
}
