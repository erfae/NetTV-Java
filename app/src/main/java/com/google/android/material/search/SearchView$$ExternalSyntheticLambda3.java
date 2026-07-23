package com.google.android.material.search;

import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.internal.ViewUtils;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda3 implements OnApplyWindowInsetsListener, ViewUtils.OnApplyWindowInsetsListener {
    public final /* synthetic */ SearchView f$0;

    public /* synthetic */ SearchView$$ExternalSyntheticLambda3(SearchView searchView) {
        this.f$0 = searchView;
    }

    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return this.f$0.lambda$setUpStatusBarSpacerInsetListener$5(view, windowInsetsCompat);
    }

    @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        return this.f$0.lambda$setUpToolbarInsetListener$4(view, windowInsetsCompat, relativePadding);
    }
}
