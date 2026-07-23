package com.nettv.livestore.helper;

import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RealmController$$ExternalSyntheticLambda1 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ RealmController$$ExternalSyntheticLambda1(String str, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
        this.f$1 = z;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                RealmController.lambda$addToFavChannels$0(this.f$0, this.f$1, realm);
                break;
            case 1:
                RealmController.lambda$addToRecentChannels$2(this.f$0, this.f$1, realm);
                break;
            case 2:
                RealmController.lambda$addToFavSeries$5(this.f$0, this.f$1, realm);
                break;
            case 3:
                RealmController.lambda$addToLockChannels$1(this.f$0, this.f$1, realm);
                break;
            default:
                RealmController.lambda$addToFavMovie$3(this.f$0, this.f$1, realm);
                break;
        }
    }
}
