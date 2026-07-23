package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public final class ListenerHolder<L> {
    private final Executor zaa;

    @Nullable
    private volatile L zab;

    @Nullable
    private volatile ListenerKey<L> zac;

    /* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
    @KeepForSdk
    public static final class ListenerKey<L> {
        private final L zaa;
        private final String zab;

        @KeepForSdk
        public ListenerKey(L l, String str) {
            this.zaa = l;
            this.zab = str;
        }

        @KeepForSdk
        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListenerKey)) {
                return false;
            }
            ListenerKey listenerKey = (ListenerKey) obj;
            return this.zaa == listenerKey.zaa && this.zab.equals(listenerKey.zab);
        }

        @KeepForSdk
        public int hashCode() {
            return this.zab.hashCode() + (System.identityHashCode(this.zaa) * 31);
        }

        @NonNull
        @KeepForSdk
        public String toIdString() {
            String str = this.zab;
            int iIdentityHashCode = System.identityHashCode(this.zaa);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 12);
            sb.append(str);
            sb.append("@");
            sb.append(iIdentityHashCode);
            return sb.toString();
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
    @KeepForSdk
    public interface Notifier<L> {
        @KeepForSdk
        void notifyListener(@NonNull L l);

        @KeepForSdk
        void onNotifyListenerFailed();
    }

    @KeepForSdk
    public ListenerHolder(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zaa = new HandlerExecutor(looper);
        this.zab = (L) Preconditions.checkNotNull(l, "Listener must not be null");
        this.zac = new ListenerKey<>(l, Preconditions.checkNotEmpty(str));
    }

    @KeepForSdk
    public void clear() {
        this.zab = null;
        this.zac = null;
    }

    @Nullable
    @KeepForSdk
    public ListenerKey<L> getListenerKey() {
        return this.zac;
    }

    @KeepForSdk
    public boolean hasListener() {
        return this.zab != null;
    }

    @KeepForSdk
    public void notifyListener(@NonNull final Notifier<? super L> notifier) {
        Preconditions.checkNotNull(notifier, "Notifier must not be null");
        this.zaa.execute(new Runnable() { // from class: com.google.android.gms.common.api.internal.zacb
            @Override // java.lang.Runnable
            public final void run() {
                this.zaa.zaa(notifier);
            }
        });
    }

    public final void zaa(Notifier<? super L> notifier) {
        L l = this.zab;
        if (l == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(l);
        } catch (RuntimeException e) {
            notifier.onNotifyListenerFailed();
            throw e;
        }
    }

    @KeepForSdk
    public ListenerHolder(@NonNull Executor executor, @NonNull L l, @NonNull String str) {
        this.zaa = (Executor) Preconditions.checkNotNull(executor, "Executor must not be null");
        this.zab = (L) Preconditions.checkNotNull(l, "Listener must not be null");
        this.zac = new ListenerKey<>(l, Preconditions.checkNotEmpty(str));
    }
}
