package org.androidannotations.api.roboguice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class RoboGuiceHelper {
    public static void callInjectViews(Object obj) {
        try {
            Method declaredMethod = Class.forName("roboguice.inject.ViewListener$ViewMembersInjector").getDeclaredMethod("injectViews", Object.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, obj);
        } catch (ClassNotFoundException e) {
            propagateRuntimeException(e);
        } catch (IllegalAccessException e2) {
            propagateRuntimeException(e2);
        } catch (IllegalArgumentException e3) {
            propagateRuntimeException(e3);
        } catch (NoSuchMethodException e4) {
            propagateRuntimeException(e4);
        } catch (SecurityException e5) {
            propagateRuntimeException(e5);
        } catch (InvocationTargetException e6) {
            propagateRuntimeException(e6);
        }
    }

    private static void propagateRuntimeException(Throwable th) {
        throw new RuntimeException("Could not invoke RoboGuice method!", th);
    }
}
