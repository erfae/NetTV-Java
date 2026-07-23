package com.google.android.play.core.install.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
@Retention(RetentionPolicy.CLASS)
public @interface UpdateAvailability {
    public static final int DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS = 3;
    public static final int UNKNOWN = 0;
    public static final int UPDATE_AVAILABLE = 2;
    public static final int UPDATE_NOT_AVAILABLE = 1;
}
