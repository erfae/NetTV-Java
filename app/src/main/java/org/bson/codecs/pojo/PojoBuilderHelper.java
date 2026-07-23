package org.bson.codecs.pojo;

import io.realm.CollectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes2.dex */
final class PojoBuilderHelper {
    private PojoBuilderHelper() {
    }

    private static <T, S> void cachePropertyTypeData(PropertyMetadata<T> propertyMetadata, Map<String, TypeParameterMap> map, TypeData<S> typeData, List<String> list, Type type) {
        TypeParameterMap typeParameterMap = getTypeParameterMap(list, type);
        map.put(propertyMetadata.getName(), typeParameterMap);
        propertyMetadata.typeParameterInfo(typeParameterMap, typeData);
    }

    public static <T> void configureClassModelBuilder(ClassModelBuilder<T> classModelBuilder, Class<T> cls) {
        Method[] methodArr;
        classModelBuilder.type((Class) Assertions.notNull("clazz", cls));
        ArrayList arrayList = new ArrayList();
        TreeSet treeSet = new TreeSet();
        Map<String, TypeParameterMap> map = new HashMap<>();
        String simpleName = cls.getSimpleName();
        HashMap map2 = new HashMap();
        Class<T> superclass = cls;
        TypeData typeDataNewInstance = null;
        while (!superclass.isEnum() && superclass.getSuperclass() != null) {
            arrayList.addAll(Arrays.asList(superclass.getDeclaredAnnotations()));
            ArrayList arrayList2 = new ArrayList();
            for (TypeVariable<Class<T>> typeVariable : superclass.getTypeParameters()) {
                arrayList2.add(typeVariable.getName());
            }
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            Method[] declaredMethods = superclass.getDeclaredMethods();
            int length = declaredMethods.length;
            int i = 0;
            while (i < length) {
                Method method = declaredMethods[i];
                if (!Modifier.isPublic(method.getModifiers()) || method.isBridge()) {
                    methodArr = declaredMethods;
                } else if (PropertyReflectionUtils.isGetter(method)) {
                    arrayList4.add(method);
                    methodArr = declaredMethods;
                } else {
                    methodArr = declaredMethods;
                    if ((method.getName().startsWith(CollectionUtils.SET_TYPE) && method.getName().length() > 3 && method.getParameterTypes().length == 1) ? Character.isUpperCase(method.getName().charAt(3)) : false) {
                        arrayList3.add(method);
                    }
                }
                i++;
                declaredMethods = methodArr;
            }
            PropertyReflectionUtils.PropertyMethods propertyMethods = new PropertyReflectionUtils.PropertyMethods(arrayList4, arrayList3);
            for (Method method2 : propertyMethods.getSetterMethods()) {
                String propertyName = PropertyReflectionUtils.toPropertyName(method2);
                treeSet.add(propertyName);
                ArrayList arrayList5 = arrayList2;
                Class<T> cls2 = superclass;
                PropertyMetadata orCreateMethodPropertyMetadata = getOrCreateMethodPropertyMetadata(propertyName, simpleName, map2, TypeData.newInstance(method2), map, typeDataNewInstance, arrayList2, getGenericType(method2));
                if (orCreateMethodPropertyMetadata.getSetter() == null) {
                    orCreateMethodPropertyMetadata.setSetter(method2);
                    for (Annotation annotation : method2.getDeclaredAnnotations()) {
                        orCreateMethodPropertyMetadata.addWriteAnnotation(annotation);
                    }
                }
                arrayList2 = arrayList5;
                superclass = cls2;
            }
            ArrayList arrayList6 = arrayList2;
            Class<T> cls3 = superclass;
            for (Method method3 : propertyMethods.getGetterMethods()) {
                String propertyName2 = PropertyReflectionUtils.toPropertyName(method3);
                treeSet.add(propertyName2);
                PropertyMetadata propertyMetadata = (PropertyMetadata) map2.get(propertyName2);
                if (propertyMetadata == null || propertyMetadata.getGetter() == null) {
                    PropertyMetadata orCreateMethodPropertyMetadata2 = getOrCreateMethodPropertyMetadata(propertyName2, simpleName, map2, TypeData.newInstance(method3), map, typeDataNewInstance, arrayList6, getGenericType(method3));
                    if (orCreateMethodPropertyMetadata2.getGetter() == null) {
                        orCreateMethodPropertyMetadata2.setGetter(method3);
                        for (Annotation annotation2 : method3.getDeclaredAnnotations()) {
                            orCreateMethodPropertyMetadata2.addReadAnnotation(annotation2);
                        }
                    }
                }
            }
            Field[] declaredFields = cls3.getDeclaredFields();
            int length2 = declaredFields.length;
            int i2 = 0;
            while (i2 < length2) {
                Field field = declaredFields[i2];
                treeSet.add(field.getName());
                Field[] fieldArr = declaredFields;
                int i3 = i2;
                PropertyMetadata orCreateFieldPropertyMetadata = getOrCreateFieldPropertyMetadata(field.getName(), simpleName, map2, TypeData.newInstance(field), map, typeDataNewInstance, arrayList6, field.getGenericType());
                if (orCreateFieldPropertyMetadata != null && orCreateFieldPropertyMetadata.getField() == null) {
                    orCreateFieldPropertyMetadata.field(field);
                    for (Annotation annotation3 : field.getDeclaredAnnotations()) {
                        orCreateFieldPropertyMetadata.addReadAnnotation(annotation3);
                        orCreateFieldPropertyMetadata.addWriteAnnotation(annotation3);
                    }
                }
                i2 = i3 + 1;
                declaredFields = fieldArr;
            }
            typeDataNewInstance = TypeData.newInstance(cls3.getGenericSuperclass(), cls3);
            superclass = cls3.getSuperclass();
        }
        Class<T> cls4 = superclass;
        if (cls4.isInterface()) {
            arrayList.addAll(Arrays.asList(cls4.getDeclaredAnnotations()));
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            PropertyMetadata propertyMetadata2 = (PropertyMetadata) map2.get((String) it.next());
            if (propertyMetadata2.isSerializable() || propertyMetadata2.isDeserializable()) {
                classModelBuilder.addProperty(createPropertyModelBuilder(propertyMetadata2));
            }
        }
        Collections.reverse(arrayList);
        classModelBuilder.annotations(arrayList);
        classModelBuilder.propertyNameToTypeParameterMap(map);
        Constructor<?> constructor = null;
        for (Constructor<?> constructor2 : cls.getDeclaredConstructors()) {
            if (constructor2.getParameterTypes().length == 0 && (Modifier.isPublic(constructor2.getModifiers()) || Modifier.isProtected(constructor2.getModifiers()))) {
                constructor2.setAccessible(true);
                constructor = constructor2;
            }
        }
        classModelBuilder.instanceCreatorFactory(new InstanceCreatorFactoryImpl<>(new CreatorExecutable(cls, constructor)));
    }

    public static <T> PropertyModelBuilder<T> createPropertyModelBuilder(PropertyMetadata<T> propertyMetadata) {
        PropertyModelBuilder propertyModelBuilderBuilder = PropertyModel.builder();
        propertyModelBuilderBuilder.propertyName(propertyMetadata.getName());
        PropertyModelBuilder<T> propertyModelBuilderWriteName = propertyModelBuilderBuilder.readName(propertyMetadata.getName()).writeName(propertyMetadata.getName());
        propertyModelBuilderWriteName.typeData(propertyMetadata.getTypeData());
        PropertyModelBuilder<T> propertyModelBuilderPropertyAccessor = propertyModelBuilderWriteName.readAnnotations(propertyMetadata.getReadAnnotations()).writeAnnotations(propertyMetadata.getWriteAnnotations()).propertySerialization(new PropertyModelSerializationImpl()).propertyAccessor(new PropertyAccessorImpl(propertyMetadata));
        propertyModelBuilderPropertyAccessor.setError(propertyMetadata.getError());
        if (propertyMetadata.getTypeParameters() != null) {
            specializePropertyModelBuilder(propertyModelBuilderPropertyAccessor, propertyMetadata);
        }
        return propertyModelBuilderPropertyAccessor;
    }

    private static Type getGenericType(Method method) {
        return PropertyReflectionUtils.isGetter(method) ? method.getGenericReturnType() : method.getGenericParameterTypes()[0];
    }

    private static <T, S> PropertyMetadata<T> getOrCreateFieldPropertyMetadata(String str, String str2, Map<String, PropertyMetadata<?>> map, TypeData<T> typeData, Map<String, TypeParameterMap> map2, TypeData<S> typeData2, List<String> list, Type type) {
        PropertyMetadata<T> orCreatePropertyMetadata = getOrCreatePropertyMetadata(str, str2, map, typeData);
        if (!orCreatePropertyMetadata.getTypeData().getType().isAssignableFrom(typeData.getType())) {
            return null;
        }
        cachePropertyTypeData(orCreatePropertyMetadata, map2, typeData2, list, type);
        return orCreatePropertyMetadata;
    }

    private static <T, S> PropertyMetadata<T> getOrCreateMethodPropertyMetadata(String str, String str2, Map<String, PropertyMetadata<?>> map, TypeData<T> typeData, Map<String, TypeParameterMap> map2, TypeData<S> typeData2, List<String> list, Type type) {
        PropertyMetadata<T> orCreatePropertyMetadata = getOrCreatePropertyMetadata(str, str2, map, typeData);
        if (!isAssignableClass(orCreatePropertyMetadata.getTypeData().getType(), typeData.getType())) {
            orCreatePropertyMetadata.setError(String.format("Property '%s' in %s, has differing data types: %s and %s.", str, str2, orCreatePropertyMetadata.getTypeData(), typeData));
        }
        cachePropertyTypeData(orCreatePropertyMetadata, map2, typeData2, list, type);
        return orCreatePropertyMetadata;
    }

    private static <T> PropertyMetadata<T> getOrCreatePropertyMetadata(String str, String str2, Map<String, PropertyMetadata<?>> map, TypeData<T> typeData) {
        PropertyMetadata<T> propertyMetadata = (PropertyMetadata) map.get(str);
        if (propertyMetadata != null) {
            return propertyMetadata;
        }
        PropertyMetadata<T> propertyMetadata2 = new PropertyMetadata<>(str, str2, typeData);
        map.put(str, propertyMetadata2);
        return propertyMetadata2;
    }

    private static TypeParameterMap getTypeParameterMap(List<String> list, Type type) {
        int iIndexOf = list.indexOf(type.toString());
        TypeParameterMap.Builder builder = new TypeParameterMap.Builder();
        if (iIndexOf != -1) {
            builder.addIndex(iIndexOf);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            for (int i = 0; i < parameterizedType.getActualTypeArguments().length; i++) {
                int iIndexOf2 = list.indexOf(parameterizedType.getActualTypeArguments()[i].toString());
                if (iIndexOf2 != -1) {
                    builder.addIndex(i, iIndexOf2);
                }
            }
        }
        return builder.build();
    }

    private static boolean isAssignableClass(Class<?> cls, Class<?> cls2) {
        return cls.isAssignableFrom(cls2) || cls2.isAssignableFrom(cls);
    }

    private static <V> void specializePropertyModelBuilder(PropertyModelBuilder<V> propertyModelBuilder, PropertyMetadata<V> propertyMetadata) {
        TypeData<V> typeDataBuild;
        if (!propertyMetadata.getTypeParameterMap().hasTypeParameters() || propertyMetadata.getTypeParameters().isEmpty()) {
            return;
        }
        Map<Integer, Integer> propertyToClassParamIndexMap = propertyMetadata.getTypeParameterMap().getPropertyToClassParamIndexMap();
        Integer num = propertyToClassParamIndexMap.get(-1);
        if (num != null) {
            typeDataBuild = (TypeData) propertyMetadata.getTypeParameters().get(num.intValue());
        } else {
            TypeData.Builder builder = TypeData.builder(propertyModelBuilder.getTypeData().getType());
            ArrayList arrayList = new ArrayList(propertyModelBuilder.getTypeData().getTypeParameters());
            for (int i = 0; i < arrayList.size(); i++) {
                for (Map.Entry<Integer, Integer> entry : propertyToClassParamIndexMap.entrySet()) {
                    if (entry.getKey().equals(Integer.valueOf(i))) {
                        arrayList.set(i, propertyMetadata.getTypeParameters().get(entry.getValue().intValue()));
                    }
                }
            }
            builder.addTypeParameters(arrayList);
            typeDataBuild = builder.build();
        }
        propertyModelBuilder.typeData(typeDataBuild);
    }

    public static <V> V stateNotNull(String str, V v) {
        if (v != null) {
            return v;
        }
        throw new IllegalStateException(String.format("%s cannot be null", str));
    }
}
