package org.bson.codecs.pojo;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes2.dex */
public final class PropertyModel<T> {
    private volatile Codec<T> cachedCodec;
    private final Codec<T> codec;
    private final String error;
    private final String name;
    private final PropertyAccessor<T> propertyAccessor;
    private final PropertySerialization<T> propertySerialization;
    private final String readName;
    private final TypeData<T> typeData;
    private final Boolean useDiscriminator;
    private final String writeName;

    public PropertyModel(String str, String str2, String str3, TypeData<T> typeData, Codec<T> codec, PropertySerialization<T> propertySerialization, Boolean bool, PropertyAccessor<T> propertyAccessor, String str4) {
        this.name = str;
        this.readName = str2;
        this.writeName = str3;
        this.typeData = typeData;
        this.codec = codec;
        this.cachedCodec = codec;
        this.propertySerialization = propertySerialization;
        this.useDiscriminator = bool;
        this.propertyAccessor = propertyAccessor;
        this.error = str4;
    }

    public static <T> PropertyModelBuilder<T> builder() {
        return new PropertyModelBuilder<>();
    }

    public final void cachedCodec(Codec<T> codec) {
        this.cachedCodec = codec;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PropertyModel.class != obj.getClass()) {
            return false;
        }
        PropertyModel propertyModel = (PropertyModel) obj;
        if (getName() == null ? propertyModel.getName() != null : !getName().equals(propertyModel.getName())) {
            return false;
        }
        if (getReadName() == null ? propertyModel.getReadName() != null : !getReadName().equals(propertyModel.getReadName())) {
            return false;
        }
        if (getWriteName() == null ? propertyModel.getWriteName() != null : !getWriteName().equals(propertyModel.getWriteName())) {
            return false;
        }
        if (getTypeData() == null ? propertyModel.getTypeData() != null : !getTypeData().equals(propertyModel.getTypeData())) {
            return false;
        }
        if (getCodec() == null ? propertyModel.getCodec() != null : !getCodec().equals(propertyModel.getCodec())) {
            return false;
        }
        PropertySerialization<T> propertySerialization = this.propertySerialization;
        if (propertySerialization == null ? propertyModel.propertySerialization != null : !propertySerialization.equals(propertyModel.propertySerialization)) {
            return false;
        }
        Boolean bool = this.useDiscriminator;
        if (bool == null ? propertyModel.useDiscriminator != null : !bool.equals(propertyModel.useDiscriminator)) {
            return false;
        }
        if (getPropertyAccessor() == null ? propertyModel.getPropertyAccessor() != null : !getPropertyAccessor().equals(propertyModel.getPropertyAccessor())) {
            return false;
        }
        String str = this.error;
        if (str == null ? propertyModel.error == null : str.equals(propertyModel.error)) {
            return this.cachedCodec == null ? propertyModel.cachedCodec == null : this.cachedCodec.equals(propertyModel.cachedCodec);
        }
        return false;
    }

    public final Codec<T> getCachedCodec() {
        return this.cachedCodec;
    }

    public Codec<T> getCodec() {
        return this.codec;
    }

    public final String getError() {
        return this.error;
    }

    public String getName() {
        return this.name;
    }

    public PropertyAccessor<T> getPropertyAccessor() {
        return this.propertyAccessor;
    }

    public final PropertySerialization<T> getPropertySerialization() {
        return this.propertySerialization;
    }

    public String getReadName() {
        return this.readName;
    }

    public TypeData<T> getTypeData() {
        return this.typeData;
    }

    public String getWriteName() {
        return this.writeName;
    }

    public final boolean hasError() {
        return this.error != null;
    }

    public int hashCode() {
        int iHashCode = (((((((((getName() != null ? getName().hashCode() : 0) * 31) + (getReadName() != null ? getReadName().hashCode() : 0)) * 31) + (getWriteName() != null ? getWriteName().hashCode() : 0)) * 31) + (getTypeData() != null ? getTypeData().hashCode() : 0)) * 31) + (getCodec() != null ? getCodec().hashCode() : 0)) * 31;
        PropertySerialization<T> propertySerialization = this.propertySerialization;
        int iHashCode2 = (iHashCode + (propertySerialization != null ? propertySerialization.hashCode() : 0)) * 31;
        Boolean bool = this.useDiscriminator;
        int iHashCode3 = (((iHashCode2 + (bool != null ? bool.hashCode() : 0)) * 31) + (getPropertyAccessor() != null ? getPropertyAccessor().hashCode() : 0)) * 31;
        String str = this.error;
        return ((iHashCode3 + (str != null ? str.hashCode() : 0)) * 31) + (this.cachedCodec != null ? this.cachedCodec.hashCode() : 0);
    }

    public boolean isReadable() {
        return this.readName != null;
    }

    public boolean isWritable() {
        return this.writeName != null;
    }

    public boolean shouldSerialize(T t) {
        return this.propertySerialization.shouldSerialize(t);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("PropertyModel{propertyName='");
        sbM.append(this.name);
        sbM.append("', readName='");
        sbM.append(this.readName);
        sbM.append("', writeName='");
        sbM.append(this.writeName);
        sbM.append("', typeData=");
        sbM.append(this.typeData);
        sbM.append("}");
        return sbM.toString();
    }

    public Boolean useDiscriminator() {
        return this.useDiscriminator;
    }
}
