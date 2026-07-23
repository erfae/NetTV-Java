package io.realm.internal.coroutines;

import io.realm.DynamicRealmObject;
import io.realm.ObjectChangeSet;
import io.realm.RealmModel;
import io.realm.RealmObjectChangeListener;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0 implements RealmObjectChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ InternalFlowFactory f$1;

    public /* synthetic */ InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, int i) {
        this.$r8$classId = i;
        this.f$0 = producerScope;
        this.f$1 = internalFlowFactory;
    }

    @Override // io.realm.RealmObjectChangeListener
    public final void onChange(RealmModel realmModel, ObjectChangeSet objectChangeSet) {
        switch (this.$r8$classId) {
            case 0:
                InternalFlowFactory.AnonymousClass5.m318invokeSuspend$lambda0(this.f$0, this.f$1, realmModel, objectChangeSet);
                break;
            default:
                InternalFlowFactory.AnonymousClass6.m320invokeSuspend$lambda0(this.f$0, this.f$1, (DynamicRealmObject) realmModel, objectChangeSet);
                break;
        }
    }
}
