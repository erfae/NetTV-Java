package com.google.android.exoplayer2.text;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import io.realm.Realm$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class CueGroup implements Bundleable {
    public final ImmutableList<Cue> cues;
    public final long presentationTimeUs;
    public static final CueGroup EMPTY_TIME_ZERO = new CueGroup(ImmutableList.of(), 0);
    private static final String FIELD_CUES = Util.intToStringMaxRadix(0);
    private static final String FIELD_PRESENTATION_TIME_US = Util.intToStringMaxRadix(1);
    public static final Bundleable.Creator<CueGroup> CREATOR = Realm$$ExternalSyntheticLambda0.INSTANCE$9;

    public CueGroup(List<Cue> list, long j) {
        this.cues = ImmutableList.copyOf((Collection) list);
        this.presentationTimeUs = j;
    }

    private static ImmutableList<Cue> filterOutBitmapCues(List<Cue> list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).bitmap == null) {
                builder.add(list.get(i));
            }
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CueGroup fromBundle(Bundle bundle) {
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(FIELD_CUES);
        return new CueGroup(parcelableArrayList == null ? ImmutableList.of() : BundleableUtil.fromBundleList(Cue.CREATOR, parcelableArrayList), bundle.getLong(FIELD_PRESENTATION_TIME_US));
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FIELD_CUES, BundleableUtil.toBundleArrayList(filterOutBitmapCues(this.cues)));
        bundle.putLong(FIELD_PRESENTATION_TIME_US, this.presentationTimeUs);
        return bundle;
    }
}
