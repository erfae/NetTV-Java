package com.nettv.livestore.apps;

import io.realm.Realm;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda6 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda6(List list, int i) {
        this.$r8$classId = i;
        this.f$0 = list;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.lambda$getEpisodeModels$16(this.f$0, realm);
                break;
            case 1:
                BaseActivity.lambda$getSeriesFromEpisodes$19(this.f$0, realm);
                break;
            case 2:
                BaseActivity.lambda$getMovieModels$11(this.f$0, realm);
                break;
            default:
                BaseActivity.lambda$getChannelModels$6(this.f$0, realm);
                break;
        }
    }
}
