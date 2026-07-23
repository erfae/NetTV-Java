package org.bson.assertions;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public final class Assertions {
    private Assertions() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T convertToType(Class<T> cls, Object obj, String str) {
        if (cls.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        throw new IllegalArgumentException(str);
    }

    public static void isTrue(String str, boolean z) {
        if (!z) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("state should be: ", str));
        }
    }

    public static void isTrueArgument(String str, boolean z) {
        if (!z) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("state should be: ", str));
        }
    }

    public static <T> T notNull(String str, T t) {
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(str, " can not be null"));
    }

    public static <T> T isTrueArgument(String str, T t, boolean z) {
        if (z) {
            return t;
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("state should be: ", str));
    }
}
