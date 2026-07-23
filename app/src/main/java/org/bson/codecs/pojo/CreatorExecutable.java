package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

/* JADX INFO: loaded from: classes2.dex */
final class CreatorExecutable<T> {
    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final Integer idPropertyIndex;
    private final Method method;
    private final List<Type> parameterGenericTypes;
    private final List<Class<?>> parameterTypes;
    private final List<BsonProperty> properties;

    public CreatorExecutable(Class<T> cls, Constructor<T> constructor) {
        this(cls, constructor, null);
    }

    private void checkHasAnExecutable() {
        if (this.constructor == null && this.method == null) {
            throw new CodecConfigurationException(String.format("Cannot find a public constructor for '%s'.", this.clazz.getSimpleName()));
        }
    }

    public final CodecConfigurationException getError(Class<?> cls, String str) {
        return getError(cls, this.constructor != null, str);
    }

    public final Integer getIdPropertyIndex() {
        return this.idPropertyIndex;
    }

    public final T getInstance() {
        checkHasAnExecutable();
        try {
            Constructor<T> constructor = this.constructor;
            return constructor != null ? constructor.newInstance(new Object[0]) : (T) this.method.invoke(this.clazz, new Object[0]);
        } catch (Exception e) {
            throw new CodecConfigurationException(e.getMessage(), e);
        }
    }

    public final List<Type> getParameterGenericTypes() {
        return this.parameterGenericTypes;
    }

    public final List<Class<?>> getParameterTypes() {
        return this.parameterTypes;
    }

    public final List<BsonProperty> getProperties() {
        return this.properties;
    }

    public final Class<T> getType() {
        return this.clazz;
    }

    public CreatorExecutable(Class<T> cls, Method method) {
        this(cls, null, method);
    }

    private static CodecConfigurationException getError(Class<?> cls, boolean z, String str) {
        Object[] objArr = new Object[3];
        objArr[0] = z ? "constructor" : "method";
        objArr[1] = cls.getSimpleName();
        objArr[2] = str;
        return new CodecConfigurationException(String.format("Invalid @BsonCreator %s in %s. %s", objArr));
    }

    private CreatorExecutable(Class<T> cls, Constructor<T> constructor, Method method) {
        Annotation[][] parameterAnnotations;
        this.properties = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.parameterTypes = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.parameterGenericTypes = arrayList2;
        this.clazz = cls;
        this.constructor = constructor;
        this.method = method;
        Integer num = null;
        if (constructor != null || method != null) {
            Class<?>[] parameterTypes = constructor != null ? constructor.getParameterTypes() : method.getParameterTypes();
            Type[] genericParameterTypes = constructor != null ? constructor.getGenericParameterTypes() : method.getGenericParameterTypes();
            arrayList.addAll(Arrays.asList(parameterTypes));
            arrayList2.addAll(Arrays.asList(genericParameterTypes));
            if (constructor != null) {
                parameterAnnotations = constructor.getParameterAnnotations();
            } else {
                parameterAnnotations = method.getParameterAnnotations();
            }
            Integer numValueOf = null;
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation.annotationType().equals(BsonProperty.class)) {
                        this.properties.add((BsonProperty) annotation);
                        break;
                    } else {
                        if (annotation.annotationType().equals(BsonId.class)) {
                            this.properties.add(null);
                            numValueOf = Integer.valueOf(i);
                            break;
                        }
                    }
                }
            }
            num = numValueOf;
        }
        this.idPropertyIndex = num;
    }

    public final T getInstance(Object[] objArr) {
        checkHasAnExecutable();
        try {
            Constructor<T> constructor = this.constructor;
            if (constructor != null) {
                return constructor.newInstance(objArr);
            }
            return (T) this.method.invoke(this.clazz, objArr);
        } catch (Exception e) {
            throw new CodecConfigurationException(e.getMessage(), e);
        }
    }
}
