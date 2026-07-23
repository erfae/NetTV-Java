package com.google.android.material.search;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchBarAnimationHelper$$ExternalSyntheticLambda2 implements SearchBarAnimationHelper.OnLoadAnimationInvocation {
    public static final /* synthetic */ SearchBarAnimationHelper$$ExternalSyntheticLambda2 INSTANCE = new SearchBarAnimationHelper$$ExternalSyntheticLambda2(0);
    public static final /* synthetic */ SearchBarAnimationHelper$$ExternalSyntheticLambda2 INSTANCE$1 = new SearchBarAnimationHelper$$ExternalSyntheticLambda2(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SearchBarAnimationHelper$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.material.search.SearchBarAnimationHelper.OnLoadAnimationInvocation
    public final void invoke(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback) {
        switch (this.$r8$classId) {
            case 0:
                onLoadAnimationCallback.onAnimationStart();
                break;
            default:
                onLoadAnimationCallback.onAnimationEnd();
                break;
        }
    }
}
