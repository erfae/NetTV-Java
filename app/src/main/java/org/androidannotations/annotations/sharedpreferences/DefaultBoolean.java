package org.androidannotations.annotations.sharedpreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes2.dex */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface DefaultBoolean {
    int keyRes() default -1;

    boolean value();
}
