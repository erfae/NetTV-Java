package com.nettv.livestore.dlg;

import android.view.View;
import com.google.android.material.snackbar.Snackbar;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SuccessDlg$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SuccessDlg$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((SuccessDlg) this.f$0).lambda$new$0((SuccessDlg.OkButtonClickListener) this.f$1, view);
                break;
            case 1:
                ((SuccessDlg) this.f$0).lambda$new$1((SuccessDlg.OkButtonClickListener) this.f$1, view);
                break;
            default:
                ((Snackbar) this.f$0).lambda$setAction$0((View.OnClickListener) this.f$1, view);
                break;
        }
    }
}
