package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
final class Platform {
    private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>() { // from class: com.google.common.escape.Platform.1
        @Override // java.lang.ThreadLocal
        public final char[] initialValue() {
            return new char[1024];
        }
    };

    private Platform() {
    }

    public static char[] charBufferFromThreadLocal() {
        return DEST_TL.get();
    }
}
