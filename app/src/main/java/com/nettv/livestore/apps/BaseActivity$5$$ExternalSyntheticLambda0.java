package com.nettv.livestore.apps;

import io.realm.Realm;
import retrofit2.Response;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$5$$ExternalSyntheticLambda0 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Response f$0;

    public /* synthetic */ BaseActivity$5$$ExternalSyntheticLambda0(Response response, int i) {
        this.$r8$classId = i;
        this.f$0 = response;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.AnonymousClass5.lambda$onResponse$0(this.f$0, realm);
                break;
            case 1:
                BaseActivity.AnonymousClass10.lambda$onResponse$0(this.f$0, realm);
                break;
            case 2:
                BaseActivity.AnonymousClass6.lambda$onResponse$0(this.f$0, realm);
                break;
            case 3:
                BaseActivity.AnonymousClass7.lambda$onResponse$0(this.f$0, realm);
                break;
            case 4:
                BaseActivity.AnonymousClass8.lambda$onResponse$0(this.f$0, realm);
                break;
            default:
                BaseActivity.AnonymousClass9.lambda$onResponse$0(this.f$0, realm);
                break;
        }
    }
}
