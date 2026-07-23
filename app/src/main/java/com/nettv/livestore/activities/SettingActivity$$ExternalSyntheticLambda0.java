package com.nettv.livestore.activities;

import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.nettv.livestore.dlgfragment.ExternalPlayerDlgFragment;
import com.nettv.livestore.dlgfragment.LiveSortDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;
import com.nettv.livestore.dlgfragment.UpdateDlgFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SettingActivity$$ExternalSyntheticLambda0 implements NoConnectionDlgFragment.OnRetryClickListener, ExternalPlayerDlgFragment.ItemPositionListener, LiveSortDlgFragment.ItemPositionListener, UpdateDlgFragment.UpdateAvailableListener, OnCompleteListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingActivity f$0;

    public /* synthetic */ SettingActivity$$ExternalSyntheticLambda0(SettingActivity settingActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = settingActivity;
    }

    @Override // com.google.android.play.core.tasks.OnCompleteListener
    public final void onComplete(Task task) {
        switch (this.$r8$classId) {
            case 4:
                this.f$0.lambda$startReviewFlow$12(task);
                break;
            default:
                this.f$0.lambda$activateReviewInfo$1(task);
                break;
        }
    }

    @Override // com.nettv.livestore.dlgfragment.ExternalPlayerDlgFragment.ItemPositionListener, com.nettv.livestore.dlgfragment.LiveSortDlgFragment.ItemPositionListener
    public final void onItemPosition(int i) {
        switch (this.$r8$classId) {
            case 1:
                this.f$0.lambda$showExternalDlgFragment$10(i);
                break;
            case 2:
                this.f$0.lambda$showChangeTimeFormatDlgFragment$6(i);
                break;
            case 3:
            case 4:
            default:
                this.f$0.lambda$showLiveChannelSortDlgFragment$7(i);
                break;
            case 5:
                this.f$0.lambda$showLiveStreamFormatDlgFragment$8(i);
                break;
            case 6:
                this.f$0.lambda$showChangeLayoutDlgFragment$3(i);
                break;
            case 7:
                this.f$0.lambda$showDeviceTypeDlgFragment$9(i);
                break;
            case 8:
                this.f$0.lambda$showAutomationDlgFragment$11(i);
                break;
        }
    }

    @Override // com.nettv.livestore.dlgfragment.NoConnectionDlgFragment.OnRetryClickListener
    public final void onRetryClick() {
        this.f$0.lambda$showNoConnectionDlgFragment$13();
    }

    @Override // com.nettv.livestore.dlgfragment.UpdateDlgFragment.UpdateAvailableListener
    public final void onUpdateAvailable() {
        this.f$0.goToUpdate();
    }
}
