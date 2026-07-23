package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public final class zak implements Handler.Callback {

    @NotOnlyInitialized
    private final zaj zab;
    private final Handler zah;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zac = new ArrayList<>();

    @VisibleForTesting
    public final ArrayList<GoogleApiClient.ConnectionCallbacks> zaa = new ArrayList<>();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zad = new ArrayList<>();
    private volatile boolean zae = false;
    private final AtomicInteger zaf = new AtomicInteger(0);
    private boolean zag = false;
    private final Object zai = new Object();

    public zak(Looper looper, zaj zajVar) {
        this.zab = zajVar;
        this.zah = new com.google.android.gms.internal.base.zaq(looper, this);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i != 1) {
            StringBuilder sb = new StringBuilder(45);
            sb.append("Don't know how to handle message: ");
            sb.append(i);
            Log.wtf("GmsClientEvents", sb.toString(), new Exception());
            return false;
        }
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) message.obj;
        synchronized (this.zai) {
            if (this.zae && this.zab.isConnected() && this.zac.contains(connectionCallbacks)) {
                connectionCallbacks.onConnected(null);
            }
        }
        return true;
    }

    public final void zaa() {
        this.zae = false;
        this.zaf.incrementAndGet();
    }

    public final void zab() {
        this.zae = true;
    }

    @VisibleForTesting
    public final void zac(ConnectionResult connectionResult) {
        Preconditions.checkHandlerThread(this.zah, "onConnectionFailure must only be called on the Handler thread");
        this.zah.removeMessages(1);
        synchronized (this.zai) {
            ArrayList<GoogleApiClient.OnConnectionFailedListener> arrayList = new ArrayList(this.zad);
            int i = this.zaf.get();
            for (GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener : arrayList) {
                if (this.zae && this.zaf.get() == i) {
                    if (this.zad.contains(onConnectionFailedListener)) {
                        onConnectionFailedListener.onConnectionFailed(connectionResult);
                    }
                }
                return;
            }
        }
    }

    @VisibleForTesting
    public final void zad(@Nullable Bundle bundle) {
        Preconditions.checkHandlerThread(this.zah, "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.zai) {
            Preconditions.checkState(!this.zag);
            this.zah.removeMessages(1);
            this.zag = true;
            Preconditions.checkState(this.zaa.isEmpty());
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList(this.zac);
            int i = this.zaf.get();
            for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : arrayList) {
                if (!this.zae || !this.zab.isConnected() || this.zaf.get() != i) {
                    break;
                    break;
                    break;
                } else if (!this.zaa.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.zaa.clear();
            this.zag = false;
        }
    }

    @VisibleForTesting
    public final void zae(int i) {
        Preconditions.checkHandlerThread(this.zah, "onUnintentionalDisconnection must only be called on the Handler thread");
        this.zah.removeMessages(1);
        synchronized (this.zai) {
            this.zag = true;
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList(this.zac);
            int i2 = this.zaf.get();
            for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : arrayList) {
                if (!this.zae || this.zaf.get() != i2) {
                    break;
                    break;
                } else if (this.zac.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.zaa.clear();
            this.zag = false;
        }
    }

    public final void zaf(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        Preconditions.checkNotNull(connectionCallbacks);
        synchronized (this.zai) {
            if (this.zac.contains(connectionCallbacks)) {
                String strValueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 62);
                sb.append("registerConnectionCallbacks(): listener ");
                sb.append(strValueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.zac.add(connectionCallbacks);
            }
        }
        if (this.zab.isConnected()) {
            Handler handler = this.zah;
            handler.sendMessage(handler.obtainMessage(1, connectionCallbacks));
        }
    }

    public final void zag(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(onConnectionFailedListener);
        synchronized (this.zai) {
            if (this.zad.contains(onConnectionFailedListener)) {
                String strValueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 67);
                sb.append("registerConnectionFailedListener(): listener ");
                sb.append(strValueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.zad.add(onConnectionFailedListener);
            }
        }
    }

    public final void zah(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        Preconditions.checkNotNull(connectionCallbacks);
        synchronized (this.zai) {
            if (!this.zac.remove(connectionCallbacks)) {
                String strValueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 52);
                sb.append("unregisterConnectionCallbacks(): listener ");
                sb.append(strValueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            } else if (this.zag) {
                this.zaa.add(connectionCallbacks);
            }
        }
    }

    public final void zai(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(onConnectionFailedListener);
        synchronized (this.zai) {
            if (!this.zad.remove(onConnectionFailedListener)) {
                String strValueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 57);
                sb.append("unregisterConnectionFailedListener(): listener ");
                sb.append(strValueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            }
        }
    }

    public final boolean zaj(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        boolean zContains;
        Preconditions.checkNotNull(connectionCallbacks);
        synchronized (this.zai) {
            zContains = this.zac.contains(connectionCallbacks);
        }
        return zContains;
    }

    public final boolean zak(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        boolean zContains;
        Preconditions.checkNotNull(onConnectionFailedListener);
        synchronized (this.zai) {
            zContains = this.zad.contains(onConnectionFailedListener);
        }
        return zContains;
    }
}
