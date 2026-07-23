package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible
public class ExecutionError extends Error {
    private static final long serialVersionUID = 0;

    public ExecutionError() {
    }

    public ExecutionError(@CheckForNull String str, @CheckForNull Error error) {
        super(str, error);
    }

    public ExecutionError(@CheckForNull Error error) {
        super(error);
    }
}
