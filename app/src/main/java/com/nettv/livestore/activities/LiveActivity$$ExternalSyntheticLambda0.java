package com.nettv.livestore.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import com.nettv.livestore.dlgfragment.LiveSearchDlgFragment;
import com.nettv.livestore.helper.RealmChangeItemListener;
import com.nettv.livestore.models.EPGChannel;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveActivity$$ExternalSyntheticLambda0 implements LiveSearchDlgFragment.SelectCurrentChannelListener, RealmChangeItemListener, ActivityResultCallback {
    public final /* synthetic */ LiveActivity f$0;

    public /* synthetic */ LiveActivity$$ExternalSyntheticLambda0(LiveActivity liveActivity) {
        this.f$0 = liveActivity;
    }

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(Object obj) {
        this.f$0.lambda$new$9((ActivityResult) obj);
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        this.f$0.lambda$playSelectedChannel$3();
    }

    @Override // com.nettv.livestore.dlgfragment.LiveSearchDlgFragment.SelectCurrentChannelListener
    public final void onSelectCurrentChannel(EPGChannel ePGChannel) {
        this.f$0.lambda$showSearchDlgFragment$2(ePGChannel);
    }
}
