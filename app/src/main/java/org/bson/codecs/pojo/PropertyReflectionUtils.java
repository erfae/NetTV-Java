package org.bson.codecs.pojo;

import java.lang.reflect.Method;
import java.util.Collection;

/* JADX INFO: loaded from: classes2.dex */
final class PropertyReflectionUtils {
    private static final String GET_PREFIX = "get";
    private static final String IS_PREFIX = "is";
    private static final String SET_PREFIX = "set";

    public static class PropertyMethods {
        private final Collection<Method> getterMethods;
        private final Collection<Method> setterMethods;

        public PropertyMethods(Collection<Method> collection, Collection<Method> collection2) {
            this.getterMethods = collection;
            this.setterMethods = collection2;
        }

        public final Collection<Method> getGetterMethods() {
            return this.getterMethods;
        }

        public final Collection<Method> getSetterMethods() {
            return this.setterMethods;
        }
    }

    private PropertyReflectionUtils() {
    }

    public static boolean isGetter(Method method) {
        if (method.getParameterTypes().length > 0) {
            return false;
        }
        if (method.getName().startsWith(GET_PREFIX) && method.getName().length() > 3) {
            return Character.isUpperCase(method.getName().charAt(3));
        }
        if (!method.getName().startsWith(IS_PREFIX) || method.getName().length() <= 2) {
            return false;
        }
        return Character.isUpperCase(method.getName().charAt(2));
    }

    public static String toPropertyName(Method method) {
        String name = method.getName();
        char[] charArray = name.substring(name.startsWith(IS_PREFIX) ? 2 : 3, name.length()).toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }
}
