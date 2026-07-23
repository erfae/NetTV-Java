package org.checkerframework.checker.nullness.compatqual;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes2.dex */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyForDecl {
    String[] value();
}
