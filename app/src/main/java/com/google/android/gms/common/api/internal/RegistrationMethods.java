package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public class RegistrationMethods<A extends Api.AnyClient, L> {

    @NonNull
    @KeepForSdk
    public final RegisterListenerMethod<A, L> register;

    @NonNull
    public final UnregisterListenerMethod<A, L> zaa;

    @NonNull
    public final Runnable zab;

    /* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
    @KeepForSdk
    public static class Builder<A extends Api.AnyClient, L> {
        private RemoteCall<A, TaskCompletionSource<Void>> zaa;
        private RemoteCall<A, TaskCompletionSource<Boolean>> zab;
        private ListenerHolder<L> zad;
        private Feature[] zae;
        private int zag;
        private Runnable zac = zacj.zaa;
        private boolean zaf = true;

        private Builder() {
        }

        public /* synthetic */ Builder(zacm zacmVar) {
        }

        @NonNull
        @KeepForSdk
        public RegistrationMethods<A, L> build() {
            Preconditions.checkArgument(this.zaa != null, "Must set register function");
            Preconditions.checkArgument(this.zab != null, "Must set unregister function");
            Preconditions.checkArgument(this.zad != null, "Must set holder");
            return new RegistrationMethods<>(new zack(this, this.zad, this.zae, this.zaf, this.zag), new zacl(this, (ListenerHolder.ListenerKey) Preconditions.checkNotNull(this.zad.getListenerKey(), "Key must not be null")), this.zac);
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> onConnectionSuspended(@NonNull Runnable runnable) {
            this.zac = runnable;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> register(@NonNull RemoteCall<A, TaskCompletionSource<Void>> remoteCall) {
            this.zaa = remoteCall;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> setAutoResolveMissingFeatures(boolean z) {
            this.zaf = z;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> setFeatures(@NonNull Feature... featureArr) {
            this.zae = featureArr;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> setMethodKey(int i) {
            this.zag = i;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> unregister(@NonNull RemoteCall<A, TaskCompletionSource<Boolean>> remoteCall) {
            this.zab = remoteCall;
            return this;
        }

        @NonNull
        @KeepForSdk
        public Builder<A, L> withHolder(@NonNull ListenerHolder<L> listenerHolder) {
            this.zad = listenerHolder;
            return this;
        }
    }

    public /* synthetic */ RegistrationMethods(RegisterListenerMethod registerListenerMethod, UnregisterListenerMethod unregisterListenerMethod, Runnable runnable) {
        this.register = registerListenerMethod;
        this.zaa = unregisterListenerMethod;
        this.zab = runnable;
    }

    @NonNull
    @KeepForSdk
    public static <A extends Api.AnyClient, L> Builder<A, L> builder() {
        return new Builder<>(null);
    }
}
