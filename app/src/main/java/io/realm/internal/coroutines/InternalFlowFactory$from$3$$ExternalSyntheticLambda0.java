package io.realm.internal.coroutines;

import io.realm.DynamicRealmObject;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class InternalFlowFactory$from$3$$ExternalSyntheticLambda0 implements RealmChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ InternalFlowFactory f$1;

    public /* synthetic */ InternalFlowFactory$from$3$$ExternalSyntheticLambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, int i) {
        this.$r8$classId = i;
        this.f$0 = producerScope;
        this.f$1 = internalFlowFactory;
    }

    @Override // io.realm.RealmChangeListener
    public final void onChange(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                InternalFlowFactory.C00643.m324invokeSuspend$lambda0(this.f$0, this.f$1, (RealmResults) obj);
                break;
            case 1:
                InternalFlowFactory.C00654.m326invokeSuspend$lambda0(this.f$0, this.f$1, (RealmResults) obj);
                break;
            case 2:
                InternalFlowFactory.C00665.m327invokeSuspend$lambda0(this.f$0, this.f$1, (RealmList) obj);
                break;
            case 3:
                InternalFlowFactory.C00676.m328invokeSuspend$lambda0(this.f$0, this.f$1, (RealmList) obj);
                break;
            case 4:
                InternalFlowFactory.AnonymousClass7.m329invokeSuspend$lambda0(this.f$0, this.f$1, (RealmModel) obj);
                break;
            default:
                InternalFlowFactory.AnonymousClass8.m330invokeSuspend$lambda0(this.f$0, this.f$1, (DynamicRealmObject) obj);
                break;
        }
    }
}
