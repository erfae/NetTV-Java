package com.nettv.livestore.apps;

import com.nettv.livestore.models.ResumeSeriesModel;
import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda4 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ResumeSeriesModel f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda4(ResumeSeriesModel resumeSeriesModel, int i) {
        this.$r8$classId = i;
        this.f$0 = resumeSeriesModel;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.lambda$getSeriesFromEpisodes$21(this.f$0, realm);
                break;
            case 1:
                BaseActivity.AnonymousClass10.lambda$onResponse$2(this.f$0, realm);
                break;
            default:
                BaseActivity.AnonymousClass9.lambda$onResponse$2(this.f$0, realm);
                break;
        }
    }
}
