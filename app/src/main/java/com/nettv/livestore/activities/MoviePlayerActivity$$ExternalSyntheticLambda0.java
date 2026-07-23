package com.nettv.livestore.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.nettv.livestore.dlgfragment.SubtitleTrackDlgFragment;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MoviePlayerActivity$$ExternalSyntheticLambda0 implements SubtitleTrackDlgFragment.ItemPositionListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppCompatActivity f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ MoviePlayerActivity$$ExternalSyntheticLambda0(AppCompatActivity appCompatActivity, List list, List list2, int i) {
        this.$r8$classId = i;
        this.f$0 = appCompatActivity;
        this.f$1 = list;
        this.f$2 = list2;
    }

    @Override // com.nettv.livestore.dlgfragment.SubtitleTrackDlgFragment.ItemPositionListener
    public final void onItemPosition(int i) {
        switch (this.$r8$classId) {
            case 0:
                ((MoviePlayerActivity) this.f$0).lambda$showOpenSubtitleDlgFragment$6(this.f$1, this.f$2, i);
                break;
            default:
                ((SeriesPlayerActivity) this.f$0).lambda$showOpenSubtitleDlgFragment$5(this.f$1, this.f$2, i);
                break;
        }
    }
}
