package com.google.android.exoplayer2.ui;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StyledPlayerControlViewLayoutManager f$0;

    public /* synthetic */ StyledPlayerControlViewLayoutManager$$ExternalSyntheticLambda1(StyledPlayerControlViewLayoutManager styledPlayerControlViewLayoutManager, int i) {
        this.$r8$classId = i;
        this.f$0 = styledPlayerControlViewLayoutManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateLayoutForSizeChange();
                break;
            case 1:
                this.f$0.onLayoutWidthChanged();
                break;
            case 2:
                this.f$0.showAllBars();
                break;
            case 3:
                this.f$0.hideAllBars();
                break;
            case 4:
                this.f$0.hideProgressBar();
                break;
            case 5:
                this.f$0.hideMainBar();
                break;
            default:
                this.f$0.hideController();
                break;
        }
    }
}
