package com.nettv.livestore.dlgfragment;

import android.content.DialogInterface;
import android.view.KeyEvent;
import androidx.fragment.app.DialogFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ExitDlgFragment$$ExternalSyntheticLambda0 implements DialogInterface.OnKeyListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DialogFragment f$0;

    public /* synthetic */ ExitDlgFragment$$ExternalSyntheticLambda0(DialogFragment dialogFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = dialogFragment;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        switch (this.$r8$classId) {
            case 0:
                return ((ExitDlgFragment) this.f$0).lambda$onCreateView$0(dialogInterface, i, keyEvent);
            case 1:
                return ((AddChannelDlgFragment) this.f$0).lambda$onCreateView$2(dialogInterface, i, keyEvent);
            case 2:
                return ((AddPlaylistDlgFragment) this.f$0).lambda$onCreateView$0(dialogInterface, i, keyEvent);
            case 3:
                return ((EpisodeDlgFragment) this.f$0).lambda$onCreateView$1(dialogInterface, i, keyEvent);
            case 4:
                return ((HideCategoryDlgFragment) this.f$0).lambda$onCreateView$1(dialogInterface, i, keyEvent);
            case 5:
                return ((LanguageDlgFragment) this.f$0).lambda$onCreateView$3(dialogInterface, i, keyEvent);
            case 6:
                return ((LiveSearchDlgFragment) this.f$0).lambda$onCreateView$0(dialogInterface, i, keyEvent);
            case 7:
                return ((NoConnectionDlgFragment) this.f$0).lambda$onCreateView$0(dialogInterface, i, keyEvent);
            case 8:
                return ((ParentControlDlgFragment) this.f$0).lambda$onCreateView$0(dialogInterface, i, keyEvent);
            case 9:
                return ((SearchChannelDlgFragment) this.f$0).lambda$onCreateView$1(dialogInterface, i, keyEvent);
            default:
                return ((SelectColorDlgFragment) this.f$0).lambda$onCreateView$1(dialogInterface, i, keyEvent);
        }
    }
}
