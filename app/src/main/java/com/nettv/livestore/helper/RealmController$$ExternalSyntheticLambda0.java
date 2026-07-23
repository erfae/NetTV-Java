package com.nettv.livestore.helper;

import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RealmController$$ExternalSyntheticLambda0 implements Realm.Transaction.OnSuccess {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RealmChangeItemListener f$0;

    public /* synthetic */ RealmController$$ExternalSyntheticLambda0(RealmChangeItemListener realmChangeItemListener, int i) {
        this.$r8$classId = i;
        this.f$0 = realmChangeItemListener;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0002. Please report as an issue. */
    @Override // io.realm.Realm.Transaction.OnSuccess
    public final void onSuccess() {
        switch (this.$r8$classId) {
        }
        this.f$0.onItemChanged();
    }
}
