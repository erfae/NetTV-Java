package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public final class TaskExecutors {

    @NonNull
    public static final Executor MAIN_THREAD = new zzu();
    public static final Executor zza = new zzt();

    private TaskExecutors() {
    }
}
