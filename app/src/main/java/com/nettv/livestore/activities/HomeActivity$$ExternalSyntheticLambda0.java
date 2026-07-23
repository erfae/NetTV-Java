package com.nettv.livestore.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import com.nettv.livestore.dlgfragment.AccountDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class HomeActivity$$ExternalSyntheticLambda0 implements ActivityResultCallback, AccountDlgFragment.PayButtonClickListener, NoConnectionDlgFragment.OnRetryClickListener {
    public final /* synthetic */ HomeActivity f$0;

    public /* synthetic */ HomeActivity$$ExternalSyntheticLambda0(HomeActivity homeActivity) {
        this.f$0 = homeActivity;
    }

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(Object obj) {
        this.f$0.lambda$new$0((ActivityResult) obj);
    }

    @Override // com.nettv.livestore.dlgfragment.AccountDlgFragment.PayButtonClickListener
    public final void onPayButtonClicked() {
        this.f$0.lambda$showAccountDlgFragment$1();
    }

    @Override // com.nettv.livestore.dlgfragment.NoConnectionDlgFragment.OnRetryClickListener
    public final void onRetryClick() {
        this.f$0.lambda$showNoConnectionDlgFragment$2();
    }
}
