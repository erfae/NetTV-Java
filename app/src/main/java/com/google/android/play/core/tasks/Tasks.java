package com.google.android.play.core.tasks;

import androidx.annotation.NonNull;
import com.google.android.play.core.internal.zzci;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class Tasks {
    private Tasks() {
    }

    public static <ResultT> ResultT await(@NonNull Task<ResultT> task) throws ExecutionException, InterruptedException {
        zzci.zza(task, "Task must not be null");
        if (task.isComplete()) {
            return (ResultT) zzc(task);
        }
        zzo zzoVar = new zzo(null);
        zzd(task, zzoVar);
        zzoVar.zza();
        return (ResultT) zzc(task);
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return zzb(null);
        }
        Iterator<? extends Task<?>> it = collection.iterator();
        while (it.hasNext()) {
            Objects.requireNonNull(it.next(), "null tasks are not accepted");
        }
        zzm zzmVar = new zzm();
        zzq zzqVar = new zzq(collection.size(), zzmVar);
        Iterator<? extends Task<?>> it2 = collection.iterator();
        while (it2.hasNext()) {
            zzd(it2.next(), zzqVar);
        }
        return zzmVar;
    }

    public static Task zza(Exception exc) {
        zzm zzmVar = new zzm();
        zzmVar.zza(exc);
        return zzmVar;
    }

    public static Task zzb(Object obj) {
        zzm zzmVar = new zzm();
        zzmVar.zzb(obj);
        return zzmVar;
    }

    private static Object zzc(Task task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }

    private static void zzd(Task task, zzp zzpVar) {
        Executor executor = TaskExecutors.zza;
        task.addOnSuccessListener(executor, zzpVar);
        task.addOnFailureListener(executor, zzpVar);
    }

    public static <ResultT> ResultT await(@NonNull Task<ResultT> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzci.zza(task, "Task must not be null");
        zzci.zza(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return (ResultT) zzc(task);
        }
        zzo zzoVar = new zzo(null);
        zzd(task, zzoVar);
        if (zzoVar.zzb(j, timeUnit)) {
            return (ResultT) zzc(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }
}
