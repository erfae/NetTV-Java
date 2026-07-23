package com.nettv.livestore.activities;

import android.view.View;
import com.nettv.livestore.activities.mobile.LiveChannelMobileActivity;
import com.nettv.livestore.activities.mobile.LiveMobileActivity;
import com.nettv.livestore.activities.mobile.MovieMobilePlayer;
import com.nettv.livestore.dlgfragment.ConnectDlgFragment;
import com.nettv.livestore.dlgfragment.GroupDlgFragment;
import com.nettv.livestore.dlgfragment.LockDlgFragment;
import com.nettv.livestore.dlgfragment.MovieInfoDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;
import com.nettv.livestore.dlgfragment.RenameGroupDlgFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchActivity$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SearchActivity$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((SearchActivity) this.f$0).lambda$initView$4(view);
                break;
            case 1:
                ((CatchUpActivity) this.f$0).lambda$initView$2(view);
                break;
            case 2:
                ((CatchUpPlayerActivity) this.f$0).lambda$initView$1(view);
                break;
            case 3:
                ((ChangePlaylistActivity) this.f$0).lambda$initView$3(view);
                break;
            case 4:
                ((SeasonActivity) this.f$0).lambda$initView$5(view);
                break;
            case 5:
                ((LiveChannelMobileActivity) this.f$0).lambda$initView$8(view);
                break;
            case 6:
                ((LiveMobileActivity) this.f$0).lambda$initView$9(view);
                break;
            case 7:
                ((MovieMobilePlayer) this.f$0).lambda$initView$3(view);
                break;
            case 8:
                ((ConnectDlgFragment) this.f$0).lambda$onCreateView$1(view);
                break;
            case 9:
                ((GroupDlgFragment) this.f$0).lambda$onCreateView$0(view);
                break;
            case 10:
                ((LockDlgFragment) this.f$0).lambda$onCreateView$0(view);
                break;
            case 11:
                ((MovieInfoDlgFragment) this.f$0).lambda$initView$0(view);
                break;
            case 12:
                ((NoConnectionDlgFragment) this.f$0).lambda$initView$1(view);
                break;
            default:
                ((RenameGroupDlgFragment) this.f$0).lambda$onCreateView$1(view);
                break;
        }
    }
}
