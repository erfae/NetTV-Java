package com.nettv.livestore.activities;

import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MovieActivity$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppCompatActivity f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MovieActivity$$ExternalSyntheticLambda0(AppCompatActivity appCompatActivity, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = appCompatActivity;
        this.f$1 = i;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                ((MovieActivity) this.f$0).lambda$showExternalPlayerDialog$2(this.f$1, dialogInterface, i);
                break;
            case 1:
                ((MovieInfoActivity) this.f$0).lambda$showExternalPlayerDialog$3(this.f$1, dialogInterface, i);
                break;
            case 2:
                ((MovieSecondActivity) this.f$0).lambda$showExternalPlayerDialog$0(this.f$1, dialogInterface, i);
                break;
            default:
                ((SeasonActivity) this.f$0).lambda$showExternalPlayerDialog$6(this.f$1, dialogInterface, i);
                break;
        }
    }
}
