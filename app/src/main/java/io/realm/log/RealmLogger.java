package io.realm.log;

import io.realm.internal.Keep;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public interface RealmLogger {
    void log(int i, String str, @Nullable Throwable th, @Nullable String str2);
}
