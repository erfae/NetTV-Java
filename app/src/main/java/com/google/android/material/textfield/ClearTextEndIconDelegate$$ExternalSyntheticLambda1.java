package com.google.android.material.textfield;

import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ClearTextEndIconDelegate$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ClearTextEndIconDelegate$$ExternalSyntheticLambda1(EndIconDelegate endIconDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = endIconDelegate;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((ClearTextEndIconDelegate) this.f$0).lambda$new$0(view);
                break;
            case 1:
                ((DropdownMenuEndIconDelegate) this.f$0).lambda$new$0(view);
                break;
            default:
                ((PasswordToggleEndIconDelegate) this.f$0).lambda$new$0(view);
                break;
        }
    }
}
