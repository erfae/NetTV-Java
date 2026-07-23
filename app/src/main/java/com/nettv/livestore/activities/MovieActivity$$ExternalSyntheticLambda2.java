package com.nettv.livestore.activities;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.constraintlayout.core.state.Interpolator;
import androidx.constraintlayout.core.state.Transition;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.video.VideoFrameReleaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;
import com.nettv.livestore.dlgfragment.SelectColorDlgFragment;
import com.nettv.livestore.dlgfragment.SubtitleSettingDlgFragment;
import com.nettv.livestore.remote.GetSubtitleLoginRequest;
import org.json.JSONObject;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MovieActivity$$ExternalSyntheticLambda2 implements Interpolator, InputConnectionCompat.OnCommitContentListener, BinarySearchSeeker.SeekTimestampConverter, TrackSelectionUtil.AdaptiveTrackSelectionFactory, VideoFrameReleaseHelper.DisplayHelper.Listener, AccessibilityViewCommand, ActivityResultCallback, NoConnectionDlgFragment.OnRetryClickListener, GetSubtitleLoginRequest.OnGetLinkModelListener, SelectColorDlgFragment.ChangeColorListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MovieActivity$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.nettv.livestore.remote.GetSubtitleLoginRequest.OnGetLinkModelListener
    public final void OnGetLinkModelResult(JSONObject jSONObject, int i) {
        switch (this.$r8$classId) {
            case 0:
                ((MovieActivity) this.f$0).lambda$GetLoginFromSubtitle$1(jSONObject, i);
                break;
            default:
                ((SeriesActivity) this.f$0).lambda$GetLoginFromSubtitle$1(jSONObject, i);
                break;
        }
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelectionUtil.AdaptiveTrackSelectionFactory
    public final ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition definition) {
        return ((RandomTrackSelection.Factory) this.f$0).lambda$createTrackSelections$0(definition);
    }

    @Override // androidx.constraintlayout.core.state.Interpolator
    public final float getInterpolation(float f) {
        return Transition.lambda$getInterpolator$0((String) this.f$0, f);
    }

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(Object obj) {
        ((CategoryActivity) this.f$0).lambda$new$3((ActivityResult) obj);
    }

    @Override // com.nettv.livestore.dlgfragment.SelectColorDlgFragment.ChangeColorListener
    public final void onChangeColor() {
        ((SubtitleSettingDlgFragment) this.f$0).lambda$showSelectColorDlgFragment$0();
    }

    @Override // androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener
    public final boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle) {
        return InputConnectionCompat.lambda$createOnCommitContentListenerUsingPerformReceiveContent$0((View) this.f$0, inputContentInfoCompat, i, bundle);
    }

    @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper.Listener
    public final void onDefaultDisplayChanged(Display display) {
        ((VideoFrameReleaseHelper) this.f$0).updateDefaultDisplayRefreshRateParams(display);
    }

    @Override // com.nettv.livestore.dlgfragment.NoConnectionDlgFragment.OnRetryClickListener
    public final void onRetryClick() {
        switch (this.$r8$classId) {
            case 8:
                ((ChangePlaylistActivity) this.f$0).lambda$showNoConnectionDlgFragment$2();
                break;
            default:
                ((AddPlaylistDlgFragment) this.f$0).lambda$showNoConnectionDlgFragment$1();
                break;
        }
    }

    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
    public final boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
        return ((BottomSheetDragHandleView) this.f$0).lambda$onBottomSheetStateChanged$0(view, commandArguments);
    }

    @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.SeekTimestampConverter
    public final long timeUsToTargetTime(long j) {
        return ((FlacStreamMetadata) this.f$0).getSampleNumber(j);
    }
}
