package org.bson.codecs.pojo;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class ClassModel<T> {
    private final String discriminator;
    private final boolean discriminatorEnabled;
    private final String discriminatorKey;
    private final boolean hasTypeParameters;
    private final IdPropertyModelHolder<?> idPropertyModelHolder;
    private final InstanceCreatorFactory<T> instanceCreatorFactory;
    private final String name;
    private final List<PropertyModel<?>> propertyModels;
    private final Map<String, TypeParameterMap> propertyNameToTypeParameterMap;
    private final Class<T> type;

    public ClassModel(Class<T> cls, Map<String, TypeParameterMap> map, InstanceCreatorFactory<T> instanceCreatorFactory, Boolean bool, String str, String str2, IdPropertyModelHolder<?> idPropertyModelHolder, List<PropertyModel<?>> list) {
        this.name = cls.getSimpleName();
        this.type = cls;
        this.hasTypeParameters = cls.getTypeParameters().length > 0;
        this.propertyNameToTypeParameterMap = Collections.unmodifiableMap(new HashMap(map));
        this.instanceCreatorFactory = instanceCreatorFactory;
        this.discriminatorEnabled = bool.booleanValue();
        this.discriminatorKey = str;
        this.discriminator = str2;
        this.idPropertyModelHolder = idPropertyModelHolder;
        this.propertyModels = Collections.unmodifiableList(new ArrayList(list));
    }

    public static <S> ClassModelBuilder<S> builder(Class<S> cls) {
        return new ClassModelBuilder<>(cls);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ClassModel.class != obj.getClass()) {
            return false;
        }
        ClassModel classModel = (ClassModel) obj;
        if (this.discriminatorEnabled != classModel.discriminatorEnabled || !getType().equals(classModel.getType()) || !this.instanceCreatorFactory.equals(classModel.instanceCreatorFactory)) {
            return false;
        }
        if (getDiscriminatorKey() == null ? classModel.getDiscriminatorKey() != null : !getDiscriminatorKey().equals(classModel.getDiscriminatorKey())) {
            return false;
        }
        if (getDiscriminator() == null ? classModel.getDiscriminator() != null : !getDiscriminator().equals(classModel.getDiscriminator())) {
            return false;
        }
        IdPropertyModelHolder<?> idPropertyModelHolder = this.idPropertyModelHolder;
        if (idPropertyModelHolder == null ? classModel.idPropertyModelHolder == null : idPropertyModelHolder.equals(classModel.idPropertyModelHolder)) {
            return getPropertyModels().equals(classModel.getPropertyModels()) && this.propertyNameToTypeParameterMap.equals(classModel.propertyNameToTypeParameterMap);
        }
        return false;
    }

    public String getDiscriminator() {
        return this.discriminator;
    }

    public String getDiscriminatorKey() {
        return this.discriminatorKey;
    }

    public PropertyModel<?> getIdPropertyModel() {
        IdPropertyModelHolder<?> idPropertyModelHolder = this.idPropertyModelHolder;
        if (idPropertyModelHolder != null) {
            return idPropertyModelHolder.getPropertyModel();
        }
        return null;
    }

    public final IdPropertyModelHolder<?> getIdPropertyModelHolder() {
        return this.idPropertyModelHolder;
    }

    public final InstanceCreator<T> getInstanceCreator() {
        return this.instanceCreatorFactory.create();
    }

    public final InstanceCreatorFactory<T> getInstanceCreatorFactory() {
        return this.instanceCreatorFactory;
    }

    public String getName() {
        return this.name;
    }

    public PropertyModel<?> getPropertyModel(String str) {
        for (PropertyModel<?> propertyModel : this.propertyModels) {
            if (propertyModel.getName().equals(str)) {
                return propertyModel;
            }
        }
        return null;
    }

    public List<PropertyModel<?>> getPropertyModels() {
        return this.propertyModels;
    }

    public final Map<String, TypeParameterMap> getPropertyNameToTypeParameterMap() {
        return this.propertyNameToTypeParameterMap;
    }

    public Class<T> getType() {
        return this.type;
    }

    public boolean hasTypeParameters() {
        return this.hasTypeParameters;
    }

    public int hashCode() {
        int iHashCode = (((((((this.instanceCreatorFactory.hashCode() + (getType().hashCode() * 31)) * 31) + (this.discriminatorEnabled ? 1 : 0)) * 31) + (getDiscriminatorKey() != null ? getDiscriminatorKey().hashCode() : 0)) * 31) + (getDiscriminator() != null ? getDiscriminator().hashCode() : 0)) * 31;
        IdPropertyModelHolder<?> idPropertyModelHolder = this.idPropertyModelHolder;
        return this.propertyNameToTypeParameterMap.hashCode() + ((getPropertyModels().hashCode() + ((iHashCode + (idPropertyModelHolder != null ? idPropertyModelHolder.hashCode() : 0)) * 31)) * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("ClassModel{type=");
        sbM.append(this.type);
        sbM.append("}");
        return sbM.toString();
    }

    public boolean useDiscriminator() {
        return this.discriminatorEnabled;
    }
}
