package com.nettv.livestore.activities;

import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class AddGroupActivity$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AddGroupActivity f$0;

    public /* synthetic */ AddGroupActivity$$ExternalSyntheticLambda0(AddGroupActivity addGroupActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = addGroupActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$initView$8(view);
                break;
            default:
                this.f$0.lambda$onCreate$2(view);
                break;
        }
    }
}
