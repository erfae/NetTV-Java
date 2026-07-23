package com.google.android.material.search;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda4 implements ListenerSet.Event, OnApplyWindowInsetsListener {
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SearchView$$ExternalSyntheticLambda4(Object obj, int i, int i2) {
        this.f$0 = obj;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        ((AnalyticsListener) obj).onSurfaceSizeChanged((AnalyticsListener.EventTime) this.f$0, this.f$1, this.f$2);
    }

    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return SearchView.lambda$setUpDividerInsetListener$6((ViewGroup.MarginLayoutParams) this.f$0, this.f$1, this.f$2, view, windowInsetsCompat);
    }
}
