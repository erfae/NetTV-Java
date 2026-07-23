package com.nettv.livestore.apps;

import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda5 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda5(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.lambda$getSeriesFromEpisodes$20(this.f$0, realm);
                break;
            case 1:
                BaseActivity.AnonymousClass10.lambda$onResponse$1(this.f$0, realm);
                break;
            case 2:
                BaseActivity.AnonymousClass5.lambda$onResponse$1(this.f$0, realm);
                break;
            case 3:
                BaseActivity.AnonymousClass5.lambda$onResponse$2(this.f$0, realm);
                break;
            case 4:
                BaseActivity.AnonymousClass6.lambda$onResponse$1(this.f$0, realm);
                break;
            case 5:
                BaseActivity.AnonymousClass6.lambda$onResponse$2(this.f$0, realm);
                break;
            case 6:
                BaseActivity.AnonymousClass7.lambda$onResponse$1(this.f$0, realm);
                break;
            case 7:
                BaseActivity.AnonymousClass8.lambda$onResponse$1(this.f$0, realm);
                break;
            case 8:
                BaseActivity.AnonymousClass9.lambda$onResponse$1(this.f$0, realm);
                break;
            case 9:
                BaseActivity.lambda$getMovieModels$12(this.f$0, realm);
                break;
            case 10:
                BaseActivity.lambda$getChannelModels$7(this.f$0, realm);
                break;
            default:
                BaseActivity.lambda$getChannelModels$8(this.f$0, realm);
                break;
        }
    }
}
