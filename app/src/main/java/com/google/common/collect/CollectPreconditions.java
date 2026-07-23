package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
final class CollectPreconditions {
    public static void checkEntryNotNull(Object obj, Object obj2) {
        if (obj == null) {
            String strValueOf = String.valueOf(obj2);
            throw new NullPointerException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 24, "null key in entry: null=", strValueOf));
        }
        if (obj2 != null) {
            return;
        }
        String strValueOf2 = String.valueOf(obj);
        throw new NullPointerException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + 26, "null value in entry: ", strValueOf2, "=null"));
    }

    @CanIgnoreReturnValue
    public static int checkNonnegative(int i, String str) {
        if (i >= 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder(str.length() + 40);
        sb.append(str);
        sb.append(" cannot be negative but was: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public static void checkPositive(int i, String str) {
        if (i > 0) {
            return;
        }
        StringBuilder sb = new StringBuilder(str.length() + 38);
        sb.append(str);
        sb.append(" must be positive but was: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public static void checkRemove(boolean z) {
        Preconditions.checkState(z, "no calls to next() since the last call to remove()");
    }

    @CanIgnoreReturnValue
    public static long checkNonnegative(long j) {
        if (j >= 0) {
            return j;
        }
        StringBuilder sb = new StringBuilder("distance".length() + 49);
        sb.append("distance");
        sb.append(" cannot be negative but was: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }
}
