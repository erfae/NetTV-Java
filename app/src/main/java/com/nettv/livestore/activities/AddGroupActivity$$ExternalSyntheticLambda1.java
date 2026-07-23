package com.nettv.livestore.activities;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.google.android.exoplayer2.util.ListenerSet;
import com.nettv.livestore.dlgfragment.AddGroupDlgFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class AddGroupActivity$$ExternalSyntheticLambda1 implements ListenerSet.Event, AddGroupDlgFragment.OnAddedGroupListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ AddGroupActivity$$ExternalSyntheticLambda1(Object obj, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 1:
                ((AnalyticsListener) obj).onShuffleModeChanged((AnalyticsListener.EventTime) this.f$0, this.f$1);
                break;
            case 2:
                ((AnalyticsListener) obj).onSkipSilenceEnabledChanged((AnalyticsListener.EventTime) this.f$0, this.f$1);
                break;
            case 3:
                ((AnalyticsListener) obj).onIsPlayingChanged((AnalyticsListener.EventTime) this.f$0, this.f$1);
                break;
            default:
                DefaultAnalyticsCollector.lambda$onIsLoadingChanged$32((AnalyticsListener.EventTime) this.f$0, this.f$1, (AnalyticsListener) obj);
                break;
        }
    }

    @Override // com.nettv.livestore.dlgfragment.AddGroupDlgFragment.OnAddedGroupListener
    public final void onAddedGroup(String str) {
        ((AddGroupActivity) this.f$0).lambda$showAddGroupDlgFragment$4(this.f$1, str);
    }
}
