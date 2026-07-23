package io.realm.internal;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.realm.ProxyState;
import io.realm.RealmModel;

/* JADX INFO: loaded from: classes2.dex */
public interface RealmObjectProxy extends RealmModel {

    public static class CacheData<E extends RealmModel> {

        @SuppressFBWarnings({"URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD"})
        public int minDepth;
        public final E object;

        public CacheData(int i, E e) {
            this.minDepth = i;
            this.object = e;
        }
    }

    void realm$injectObjectContext();

    ProxyState realmGet$proxyState();
}
