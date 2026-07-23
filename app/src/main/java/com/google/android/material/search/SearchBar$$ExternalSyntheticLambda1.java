package com.google.android.material.search;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchBar$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SearchBar$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((SearchBar) this.f$0).lambda$startOnLoadAnimation$1();
                break;
            case 1:
                ((SearchViewAnimationHelper) this.f$0).lambda$startShowAnimationExpand$0();
                break;
            default:
                ((SearchViewAnimationHelper) this.f$0).lambda$startShowAnimationTranslate$1();
                break;
        }
    }
}
