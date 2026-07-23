package com.nettv.livestore.dlgfragment;

import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.models.EpisodeModel;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class EpisodeDlgFragment$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DialogFragment f$0;

    public /* synthetic */ EpisodeDlgFragment$$ExternalSyntheticLambda1(DialogFragment dialogFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = dialogFragment;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return ((EpisodeDlgFragment) this.f$0).lambda$onCreateView$0((EpisodeModel) obj, (Integer) obj2);
            case 1:
                return ((AudioTrackDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            case 2:
                return ((ClearHistoryDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            case 3:
                return ((ExternalPlayerDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            case 4:
                return ((HideCategoryDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            case 5:
                return ((LanguageDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            case 6:
                return ((LiveSortDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            case 7:
                return ((SelectColorDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
            default:
                return ((SubtitleTrackDlgFragment) this.f$0).lambda$onCreateView$0((Integer) obj, (Boolean) obj2);
        }
    }
}
