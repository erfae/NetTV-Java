package io.realm;

import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
class ProxyUtils {
    private static void throwWrongElementType(@Nullable Class cls) {
        throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Element type '%s' is not handled.", cls));
    }
}
