package io.realm.internal.coroutines;

import io.realm.BaseRealm;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class InternalFlowFactory$from$1$$ExternalSyntheticLambda0 implements RealmChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ InternalFlowFactory f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ InternalFlowFactory$from$1$$ExternalSyntheticLambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, BaseRealm baseRealm, int i) {
        this.$r8$classId = i;
        this.f$0 = producerScope;
        this.f$1 = internalFlowFactory;
        this.f$2 = baseRealm;
    }

    @Override // io.realm.RealmChangeListener
    public final void onChange(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                InternalFlowFactory.C00621.m321invokeSuspend$lambda0(this.f$0, this.f$1, (Realm) this.f$2, (Realm) obj);
                break;
            default:
                InternalFlowFactory.C00632.m322invokeSuspend$lambda0(this.f$0, this.f$1, (DynamicRealm) this.f$2, (DynamicRealm) obj);
                break;
        }
    }
}
