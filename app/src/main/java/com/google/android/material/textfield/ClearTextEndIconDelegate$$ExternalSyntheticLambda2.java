package com.google.android.material.textfield;

import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ClearTextEndIconDelegate$$ExternalSyntheticLambda2 implements View.OnFocusChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ClearTextEndIconDelegate$$ExternalSyntheticLambda2(EndIconDelegate endIconDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = endIconDelegate;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        switch (this.$r8$classId) {
            case 0:
                ((ClearTextEndIconDelegate) this.f$0).lambda$new$1(view, z);
                break;
            default:
                ((DropdownMenuEndIconDelegate) this.f$0).lambda$new$1(view, z);
                break;
        }
    }
}
