package org.androidannotations.api.rest;

import org.springframework.core.NestedRuntimeException;

/* JADX INFO: loaded from: classes2.dex */
public interface RestErrorHandler {
    void onRestClientExceptionThrown(NestedRuntimeException nestedRuntimeException);
}
