package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public interface zaca {
    ConnectionResult zab();

    ConnectionResult zac(long j, TimeUnit timeUnit);

    @Nullable
    ConnectionResult zad(@NonNull Api<?> api);

    <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zae(@NonNull T t);

    <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zaf(@NonNull T t);

    void zaq();

    void zar();

    void zas(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr);

    void zat();

    void zau();

    boolean zaw();

    boolean zax();

    boolean zay(SignInConnectionListener signInConnectionListener);
}
