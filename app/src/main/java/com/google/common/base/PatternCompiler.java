package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
interface PatternCompiler {
    CommonPattern compile(String str);

    boolean isPcreLike();
}
