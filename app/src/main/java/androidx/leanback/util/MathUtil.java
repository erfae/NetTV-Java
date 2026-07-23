package androidx.leanback.util;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class MathUtil {
    private MathUtil() {
    }

    public static int safeLongToInt(long j) {
        int i = (int) j;
        if (i == j) {
            return i;
        }
        throw new ArithmeticException("Input overflows int.\n");
    }
}
