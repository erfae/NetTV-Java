package com.google.android.material.timepicker;

import com.google.android.material.button.MaterialButtonToggleGroup;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class TimePickerView$$ExternalSyntheticLambda0 implements MaterialButtonToggleGroup.OnButtonCheckedListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TimePickerView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
    public final void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean z) {
        switch (this.$r8$classId) {
            case 0:
                ((TimePickerView) this.f$0).lambda$new$0(materialButtonToggleGroup, i, z);
                break;
            default:
                ((TimePickerTextInputPresenter) this.f$0).lambda$setupPeriodToggle$0(materialButtonToggleGroup, i, z);
                break;
        }
    }
}
