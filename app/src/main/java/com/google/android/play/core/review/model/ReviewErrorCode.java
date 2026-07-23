package com.google.android.play.core.review.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface ReviewErrorCode {
    public static final int INVALID_REQUEST = -2;
    public static final int NO_ERROR = 0;
    public static final int PLAY_STORE_NOT_FOUND = -1;
}
