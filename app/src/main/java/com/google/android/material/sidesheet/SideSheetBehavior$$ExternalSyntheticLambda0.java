package com.google.android.material.sidesheet;

import android.view.View;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.nettv.livestore.activities.mobile.LiveChannelMobileActivity;
import com.nettv.livestore.helper.RealmChangeItemListener;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SideSheetBehavior$$ExternalSyntheticLambda0 implements RealmChangeItemListener, AccessibilityViewCommand {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SideSheetBehavior$$ExternalSyntheticLambda0(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        switch (this.$r8$classId) {
            case 1:
                ((LiveChannelMobileActivity) this.f$0).lambda$controlFav$4(this.f$1);
                break;
            default:
                ((LiveChannelMobileActivity) this.f$0).lambda$controlLock$5(this.f$1);
                break;
        }
    }

    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
    public final boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
        return ((SideSheetBehavior) this.f$0).lambda$createAccessibilityViewCommandForState$1(this.f$1, view, commandArguments);
    }
}
