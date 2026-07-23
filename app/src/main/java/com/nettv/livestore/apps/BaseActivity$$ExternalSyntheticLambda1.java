package com.nettv.livestore.apps;

import com.nettv.livestore.net.NetworkTask;
import io.realm.RealmResults;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda1 implements NetworkTask.OnCompleteListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseActivity f$0;
    public final /* synthetic */ RealmResults f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ List f$3;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda1(BaseActivity baseActivity, RealmResults realmResults, List list, List list2, int i) {
        this.$r8$classId = i;
        this.f$0 = baseActivity;
        this.f$1 = realmResults;
        this.f$2 = list;
        this.f$3 = list2;
    }

    @Override // com.nettv.livestore.net.NetworkTask.OnCompleteListener
    public final void onComplete(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$getMovieModels$14(this.f$1, this.f$2, this.f$3, (List) obj);
                break;
            default:
                this.f$0.lambda$getChannelModels$9(this.f$1, this.f$2, this.f$3, (List) obj);
                break;
        }
    }
}
