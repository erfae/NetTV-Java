package com.google.android.material.search;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchView f$0;

    public /* synthetic */ SearchView$$ExternalSyntheticLambda5(SearchView searchView, int i) {
        this.$r8$classId = i;
        this.f$0 = searchView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$requestFocusAndShowKeyboard$8();
                break;
            case 1:
                this.f$0.lambda$clearFocusAndHideKeyboard$9();
                break;
            default:
                this.f$0.requestFocusAndShowKeyboardIfNeeded();
                break;
        }
    }
}
