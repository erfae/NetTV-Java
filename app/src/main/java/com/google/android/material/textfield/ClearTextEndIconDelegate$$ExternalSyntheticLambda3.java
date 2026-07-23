package com.google.android.material.textfield;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ClearTextEndIconDelegate$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ClearTextEndIconDelegate$$ExternalSyntheticLambda3(EndIconDelegate endIconDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = endIconDelegate;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ClearTextEndIconDelegate) this.f$0).lambda$tearDown$2();
                break;
            default:
                ((DropdownMenuEndIconDelegate) this.f$0).lambda$afterEditTextChanged$3();
                break;
        }
    }
}
