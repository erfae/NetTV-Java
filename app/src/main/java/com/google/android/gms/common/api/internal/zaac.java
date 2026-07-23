package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zaac implements OnCompleteListener {
    public final /* synthetic */ TaskCompletionSource zaa;
    public final /* synthetic */ zaad zab;

    public zaac(zaad zaadVar, TaskCompletionSource taskCompletionSource) {
        this.zab = zaadVar;
        this.zaa = taskCompletionSource;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task task) {
        this.zab.zab.remove(this.zaa);
    }
}
