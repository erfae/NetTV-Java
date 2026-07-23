package io.realm.internal;

import io.realm.RealmQuery;
import io.realm.mongodb.sync.Subscription;
import java.util.Date;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class UnmanagedSubscription implements Subscription {

    @Nullable
    private final String name;
    private final String objectType;
    private final String queryDesc;
    private final long queryPtr;
    private final Date createdAt = null;
    private final Date updatedAt = null;

    public UnmanagedSubscription(@Nullable String str, RealmQuery realmQuery) {
        this.name = str;
        this.objectType = realmQuery.getTypeQueried();
        this.queryDesc = realmQuery.getDescription();
        this.queryPtr = realmQuery.getQueryPointer();
    }

    public Date getCreatedAt() {
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public String getQuery() {
        return this.queryDesc;
    }

    public long getQueryPointer() {
        return this.queryPtr;
    }

    public Date getUpdatedAt() {
        return null;
    }
}
