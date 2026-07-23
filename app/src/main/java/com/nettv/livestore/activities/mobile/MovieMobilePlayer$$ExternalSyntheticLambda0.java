package com.nettv.livestore.activities.mobile;

import com.nettv.livestore.helper.RealmChangeItemListener;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MovieMobilePlayer$$ExternalSyntheticLambda0 implements RealmChangeItemListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MovieMobilePlayer f$0;

    public /* synthetic */ MovieMobilePlayer$$ExternalSyntheticLambda0(MovieMobilePlayer movieMobilePlayer, int i) {
        this.$r8$classId = i;
        this.f$0 = movieMobilePlayer;
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onClick$4();
                break;
            case 1:
                this.f$0.lambda$onClick$5();
                break;
            case 2:
                this.f$0.lambda$releaseMediaPlayer$0();
                break;
            default:
                this.f$0.lambda$releaseMediaPlayer$1();
                break;
        }
    }
}
