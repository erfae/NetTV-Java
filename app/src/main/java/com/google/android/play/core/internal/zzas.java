package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzas {
    private static final Map zza = new HashMap();
    private final Context zzb;
    private final zzag zzc;
    private final String zzd;
    private boolean zzh;
    private final Intent zzi;
    private final zzan zzj;

    @Nullable
    private ServiceConnection zzn;

    @Nullable
    private IInterface zzo;
    private final List zze = new ArrayList();

    @GuardedBy("attachedRemoteTasksLock")
    private final Set zzf = new HashSet();
    private final Object zzg = new Object();
    private final IBinder.DeathRecipient zzl = new IBinder.DeathRecipient() { // from class: com.google.android.play.core.internal.zzai
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            zzas.zzi(this.zza);
        }
    };

    @GuardedBy("attachedRemoteTasksLock")
    private final AtomicInteger zzm = new AtomicInteger(0);
    private final WeakReference zzk = new WeakReference(null);

    public zzas(Context context, zzag zzagVar, String str, Intent intent, zzan zzanVar, @Nullable zzam zzamVar) {
        this.zzb = context;
        this.zzc = zzagVar;
        this.zzd = str;
        this.zzi = intent;
        this.zzj = zzanVar;
    }

    public static /* synthetic */ void zzi(zzas zzasVar) {
        zzasVar.zzc.zzd("reportBinderDeath", new Object[0]);
        zzam zzamVar = (zzam) zzasVar.zzk.get();
        if (zzamVar != null) {
            zzasVar.zzc.zzd("calling onBinderDied", new Object[0]);
            zzamVar.zza();
        } else {
            zzasVar.zzc.zzd("%s : Binder has died.", zzasVar.zzd);
            Iterator it = zzasVar.zze.iterator();
            while (it.hasNext()) {
                ((zzah) it.next()).zzc(zzasVar.zzt());
            }
            zzasVar.zze.clear();
        }
        zzasVar.zzu();
    }

    public static /* bridge */ /* synthetic */ void zzn(zzas zzasVar, zzah zzahVar) {
        if (zzasVar.zzo != null || zzasVar.zzh) {
            if (!zzasVar.zzh) {
                zzahVar.run();
                return;
            } else {
                zzasVar.zzc.zzd("Waiting to bind to the service.", new Object[0]);
                zzasVar.zze.add(zzahVar);
                return;
            }
        }
        zzasVar.zzc.zzd("Initiate binding to the service.", new Object[0]);
        zzasVar.zze.add(zzahVar);
        zzar zzarVar = new zzar(zzasVar);
        zzasVar.zzn = zzarVar;
        zzasVar.zzh = true;
        if (zzasVar.zzb.bindService(zzasVar.zzi, zzarVar, 1)) {
            return;
        }
        zzasVar.zzc.zzd("Failed to bind to the service.", new Object[0]);
        zzasVar.zzh = false;
        Iterator it = zzasVar.zze.iterator();
        while (it.hasNext()) {
            ((zzah) it.next()).zzc(new zzat());
        }
        zzasVar.zze.clear();
    }

    public static /* bridge */ /* synthetic */ void zzo(zzas zzasVar) {
        zzasVar.zzc.zzd("linkToDeath", new Object[0]);
        try {
            zzasVar.zzo.asBinder().linkToDeath(zzasVar.zzl, 0);
        } catch (RemoteException e) {
            zzasVar.zzc.zzc(e, "linkToDeath failed", new Object[0]);
        }
    }

    public static /* bridge */ /* synthetic */ void zzp(zzas zzasVar) {
        zzasVar.zzc.zzd("unlinkToDeath", new Object[0]);
        zzasVar.zzo.asBinder().unlinkToDeath(zzasVar.zzl, 0);
    }

    private final RemoteException zzt() {
        return new RemoteException(String.valueOf(this.zzd).concat(" : Binder has died."));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzu() {
        synchronized (this.zzg) {
            Iterator it = this.zzf.iterator();
            while (it.hasNext()) {
                ((com.google.android.play.core.tasks.zzi) it.next()).zzd(zzt());
            }
            this.zzf.clear();
        }
    }

    public final Handler zzc() {
        Handler handler;
        Map map = zza;
        synchronized (map) {
            if (!map.containsKey(this.zzd)) {
                HandlerThread handlerThread = new HandlerThread(this.zzd, 10);
                handlerThread.start();
                map.put(this.zzd, new Handler(handlerThread.getLooper()));
            }
            handler = (Handler) map.get(this.zzd);
        }
        return handler;
    }

    @Nullable
    public final IInterface zze() {
        return this.zzo;
    }

    public final void zzq(zzah zzahVar, @Nullable final com.google.android.play.core.tasks.zzi zziVar) {
        synchronized (this.zzg) {
            this.zzf.add(zziVar);
            zziVar.zza().addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.play.core.internal.zzaj
                @Override // com.google.android.play.core.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    this.zza.zzr(zziVar);
                }
            });
        }
        synchronized (this.zzg) {
            if (this.zzm.getAndIncrement() > 0) {
                this.zzc.zza("Already connected to the service.", new Object[0]);
            }
        }
        zzc().post(new zzak(this, zzahVar.zzb(), zzahVar));
    }

    public final /* synthetic */ void zzr(com.google.android.play.core.tasks.zzi zziVar) {
        synchronized (this.zzg) {
            this.zzf.remove(zziVar);
        }
    }

    public final void zzs(com.google.android.play.core.tasks.zzi zziVar) {
        synchronized (this.zzg) {
            this.zzf.remove(zziVar);
        }
        synchronized (this.zzg) {
            if (this.zzm.get() > 0 && this.zzm.decrementAndGet() > 0) {
                this.zzc.zzd("Leaving the connection open for other ongoing calls.", new Object[0]);
            } else {
                zzc().post(new zzal(this));
            }
        }
    }
}
