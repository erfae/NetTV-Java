package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

/* JADX INFO: loaded from: classes2.dex */
final class ConventionAnnotationImpl implements Convention {
    private <T, S> PropertyModelBuilder<S> addCreatorPropertyToClassModelBuilder(ClassModelBuilder<T> classModelBuilder, String str, Class<S> cls) {
        PropertyModelBuilder<T> propertyModelBuilderWriteName = PojoBuilderHelper.createPropertyModelBuilder(new PropertyMetadata(str, classModelBuilder.getType().getSimpleName(), TypeData.builder(cls).build())).readName(null).writeName(str);
        classModelBuilder.addProperty(propertyModelBuilderWriteName);
        return propertyModelBuilderWriteName;
    }

    private void cleanPropertyBuilders(ClassModelBuilder<?> classModelBuilder) {
        ArrayList arrayList = new ArrayList();
        for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
            if (!propertyModelBuilder.isReadable() && !propertyModelBuilder.isWritable()) {
                arrayList.add(propertyModelBuilder.getName());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            classModelBuilder.removeProperty((String) it.next());
        }
    }

    private void processClassAnnotation(ClassModelBuilder<?> classModelBuilder, Annotation annotation) {
        if (annotation instanceof BsonDiscriminator) {
            BsonDiscriminator bsonDiscriminator = (BsonDiscriminator) annotation;
            String strKey = bsonDiscriminator.key();
            if (!strKey.equals("")) {
                classModelBuilder.discriminatorKey(strKey);
            }
            String strValue = bsonDiscriminator.value();
            if (!strValue.equals("")) {
                classModelBuilder.discriminator(strValue);
            }
            classModelBuilder.enableDiscriminator(true);
        }
    }

    private <T> void processCreatorAnnotation(ClassModelBuilder<T> classModelBuilder) {
        PropertyModelBuilder<?> propertyModelBuilderAddCreatorPropertyToClassModelBuilder;
        Class<T> type = classModelBuilder.getType();
        CreatorExecutable creatorExecutable = null;
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (Modifier.isPublic(constructor.getModifiers()) && !constructor.isSynthetic()) {
                for (Annotation annotation : constructor.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(BsonCreator.class)) {
                        if (creatorExecutable != null) {
                            throw new CodecConfigurationException("Found multiple constructors annotated with @BsonCreator");
                        }
                        creatorExecutable = new CreatorExecutable(type, constructor);
                    }
                }
            }
        }
        boolean z = false;
        for (Class<T> superclass = type; superclass != null && !z; superclass = superclass.getSuperclass()) {
            for (Method method : superclass.getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers()) && !method.isSynthetic() && !method.isBridge()) {
                    for (Annotation annotation2 : method.getDeclaredAnnotations()) {
                        if (annotation2.annotationType().equals(BsonCreator.class)) {
                            if (creatorExecutable != null) {
                                throw new CodecConfigurationException("Found multiple constructors / methods annotated with @BsonCreator");
                            }
                            if (!superclass.isAssignableFrom(method.getReturnType())) {
                                throw new CodecConfigurationException(String.format("Invalid method annotated with @BsonCreator. Returns '%s', expected %s", method.getReturnType(), superclass));
                            }
                            creatorExecutable = new CreatorExecutable(type, method);
                            z = true;
                        }
                    }
                }
            }
        }
        if (creatorExecutable != null) {
            List<BsonProperty> properties = creatorExecutable.getProperties();
            List<Class<?>> parameterTypes = creatorExecutable.getParameterTypes();
            List<Type> parameterGenericTypes = creatorExecutable.getParameterGenericTypes();
            if (properties.size() != parameterTypes.size()) {
                throw creatorExecutable.getError(type, "All parameters in the @BsonCreator method / constructor must be annotated with a @BsonProperty.");
            }
            for (int i = 0; i < properties.size(); i++) {
                boolean z2 = creatorExecutable.getIdPropertyIndex() != null && creatorExecutable.getIdPropertyIndex().equals(Integer.valueOf(i));
                Class<?> cls = parameterTypes.get(i);
                Type type2 = parameterGenericTypes.get(i);
                if (z2) {
                    propertyModelBuilderAddCreatorPropertyToClassModelBuilder = classModelBuilder.getProperty(classModelBuilder.getIdPropertyName());
                } else {
                    BsonProperty bsonProperty = properties.get(i);
                    PropertyModelBuilder<?> propertyModelBuilder = null;
                    for (PropertyModelBuilder<?> propertyModelBuilder2 : classModelBuilder.getPropertyModelBuilders()) {
                        if (bsonProperty.value().equals(propertyModelBuilder2.getWriteName())) {
                            propertyModelBuilder = propertyModelBuilder2;
                            break;
                        } else if (bsonProperty.value().equals(propertyModelBuilder2.getReadName())) {
                            propertyModelBuilder = propertyModelBuilder2;
                        }
                    }
                    PropertyModelBuilder<?> property = propertyModelBuilder == null ? classModelBuilder.getProperty(bsonProperty.value()) : propertyModelBuilder;
                    if (property == null) {
                        propertyModelBuilderAddCreatorPropertyToClassModelBuilder = addCreatorPropertyToClassModelBuilder(classModelBuilder, bsonProperty.value(), cls);
                    } else {
                        if (!bsonProperty.value().equals(property.getName())) {
                            property.writeName(bsonProperty.value());
                        }
                        tryToExpandToGenericType(cls, property, type2);
                        propertyModelBuilderAddCreatorPropertyToClassModelBuilder = property;
                    }
                }
                if (!propertyModelBuilderAddCreatorPropertyToClassModelBuilder.getTypeData().isAssignableFrom(cls)) {
                    throw creatorExecutable.getError(type, String.format("Invalid Property type for '%s'. Expected %s, found %s.", propertyModelBuilderAddCreatorPropertyToClassModelBuilder.getWriteName(), propertyModelBuilderAddCreatorPropertyToClassModelBuilder.getTypeData().getType(), cls));
                }
            }
            classModelBuilder.instanceCreatorFactory(new InstanceCreatorFactoryImpl(creatorExecutable));
        }
    }

    private void processPropertyAnnotations(ClassModelBuilder<?> classModelBuilder, PropertyModelBuilder<?> propertyModelBuilder) {
        for (Annotation annotation : propertyModelBuilder.getReadAnnotations()) {
            if (annotation instanceof BsonProperty) {
                BsonProperty bsonProperty = (BsonProperty) annotation;
                if (!"".equals(bsonProperty.value())) {
                    propertyModelBuilder.readName(bsonProperty.value());
                }
                propertyModelBuilder.discriminatorEnabled(bsonProperty.useDiscriminator());
                if (propertyModelBuilder.getName().equals(classModelBuilder.getIdPropertyName())) {
                    classModelBuilder.idPropertyName(null);
                }
            } else if (annotation instanceof BsonId) {
                classModelBuilder.idPropertyName(propertyModelBuilder.getName());
            } else if (annotation instanceof BsonIgnore) {
                propertyModelBuilder.readName(null);
            }
        }
        for (Annotation annotation2 : propertyModelBuilder.getWriteAnnotations()) {
            if (annotation2 instanceof BsonProperty) {
                BsonProperty bsonProperty2 = (BsonProperty) annotation2;
                if (!"".equals(bsonProperty2.value())) {
                    propertyModelBuilder.writeName(bsonProperty2.value());
                }
            } else if (annotation2 instanceof BsonIgnore) {
                propertyModelBuilder.writeName(null);
            }
        }
    }

    private static <T> void tryToExpandToGenericType(Class<?> cls, PropertyModelBuilder<T> propertyModelBuilder, Type type) {
        if (cls.isAssignableFrom(propertyModelBuilder.getTypeData().getType())) {
            propertyModelBuilder.typeData(TypeData.newInstance(type, cls));
        }
    }

    @Override // org.bson.codecs.pojo.Convention
    public void apply(ClassModelBuilder<?> classModelBuilder) {
        Iterator<Annotation> it = classModelBuilder.getAnnotations().iterator();
        while (it.hasNext()) {
            processClassAnnotation(classModelBuilder, it.next());
        }
        Iterator<PropertyModelBuilder<?>> it2 = classModelBuilder.getPropertyModelBuilders().iterator();
        while (it2.hasNext()) {
            processPropertyAnnotations(classModelBuilder, it2.next());
        }
        processCreatorAnnotation(classModelBuilder);
        cleanPropertyBuilders(classModelBuilder);
    }
}
