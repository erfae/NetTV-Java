package androidx.activity;

import android.view.View;
import androidx.constraintlayout.helper.widget.Carousel;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.nettv.livestore.activities.CatchUpPlayerActivity;
import com.nettv.livestore.activities.mobile.LiveChannelMobileActivity;
import com.nettv.livestore.activities.mobile.LiveMobileActivity;
import com.nettv.livestore.activities.mobile.MovieMobilePlayer;
import com.nettv.livestore.activities.mobile.SeriesMobilePlayer;
import com.nettv.livestore.dlgfragment.SearchChannelDlgFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ComponentDialog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComponentDialog$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ComponentDialog.m1onBackPressedDispatcher$lambda1((ComponentDialog) this.f$0);
                break;
            case 1:
                ((ComponentActivity) this.f$0).invalidateMenu();
                break;
            case 2:
                ((OnBackPressedDispatcher) this.f$0).onBackPressed();
                break;
            case 3:
                ((Carousel) this.f$0).lambda$updateItems$0();
                break;
            case 4:
                ((DefaultAnalyticsCollector) this.f$0).releaseInternal();
                break;
            case 5:
                ((View) this.f$0).requestLayout();
                break;
            case 6:
            default:
                ((SearchChannelDlgFragment) this.f$0).lambda$searchTimer$2();
                break;
            case 7:
                ((CatchUpPlayerActivity) this.f$0).lambda$listTimer$0();
                break;
            case 8:
                ((LiveChannelMobileActivity) this.f$0).lambda$mInfoHideTimer$6();
                break;
            case 9:
                ((LiveMobileActivity) this.f$0).lambda$mInfoHideTimer$7();
                break;
            case 10:
                ((MovieMobilePlayer) this.f$0).lambda$listTimer$2();
                break;
            case 11:
                ((SeriesMobilePlayer) this.f$0).lambda$listTimer$0();
                break;
        }
    }
}
