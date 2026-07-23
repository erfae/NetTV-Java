package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.util.Util;

/* JADX INFO: loaded from: classes.dex */
public abstract class Rating implements Bundleable {
    public static final String FIELD_RATING_TYPE = Util.intToStringMaxRadix(0);
    public static final Bundleable.Creator<Rating> CREATOR = Format$$ExternalSyntheticLambda0.INSTANCE$17;

    /* JADX INFO: Access modifiers changed from: private */
    public static Rating fromBundle(Bundle bundle) {
        int i = bundle.getInt(FIELD_RATING_TYPE, -1);
        if (i == 0) {
            return (Rating) HeartRating.CREATOR.fromBundle(bundle);
        }
        if (i == 1) {
            return (Rating) PercentageRating.CREATOR.fromBundle(bundle);
        }
        if (i == 2) {
            return (Rating) StarRating.CREATOR.fromBundle(bundle);
        }
        if (i == 3) {
            return (Rating) ThumbRating.CREATOR.fromBundle(bundle);
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Unknown RatingType: ", i));
    }

    public abstract boolean isRated();
}
