package com.google.android.material.timepicker;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RadialViewGroup$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RadialViewGroup$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((RadialViewGroup) this.f$0).updateLayoutParams();
                break;
            default:
                ((MaterialTimePicker) this.f$0).lambda$onViewCreated$0();
                break;
        }
    }
}
