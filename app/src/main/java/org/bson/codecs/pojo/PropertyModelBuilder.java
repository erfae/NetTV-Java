package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import org.bson.assertions.Assertions;
import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes2.dex */
public final class PropertyModelBuilder<T> {
    private Codec<T> codec;
    private Boolean discriminatorEnabled;
    private String error;
    private String name;
    private PropertyAccessor<T> propertyAccessor;
    private PropertySerialization<T> propertySerialization;
    private String readName;
    private TypeData<T> typeData;
    private String writeName;
    private List<Annotation> readAnnotations = Collections.emptyList();
    private List<Annotation> writeAnnotations = Collections.emptyList();

    public PropertyModel<T> build() {
        if (!isReadable() && !isWritable()) {
            throw new IllegalStateException(String.format("Invalid PropertyModel '%s', neither readable or writable,", this.name));
        }
        String str = this.name;
        PojoBuilderHelper.stateNotNull("propertyName", str);
        String str2 = this.readName;
        String str3 = this.writeName;
        TypeData<T> typeData = this.typeData;
        PojoBuilderHelper.stateNotNull("typeData", typeData);
        Codec<T> codec = this.codec;
        PropertySerialization<T> propertySerialization = this.propertySerialization;
        PojoBuilderHelper.stateNotNull("propertySerialization", propertySerialization);
        PropertySerialization<T> propertySerialization2 = propertySerialization;
        Boolean bool = this.discriminatorEnabled;
        PropertyAccessor<T> propertyAccessor = this.propertyAccessor;
        PojoBuilderHelper.stateNotNull("propertyAccessor", propertyAccessor);
        return new PropertyModel<>(str, str2, str3, typeData, codec, propertySerialization2, bool, propertyAccessor, this.error);
    }

    public PropertyModelBuilder<T> codec(Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    public PropertyModelBuilder<T> discriminatorEnabled(boolean z) {
        this.discriminatorEnabled = Boolean.valueOf(z);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PropertyAccessor<T> getPropertyAccessor() {
        return this.propertyAccessor;
    }

    public PropertySerialization<T> getPropertySerialization() {
        return this.propertySerialization;
    }

    public List<Annotation> getReadAnnotations() {
        return this.readAnnotations;
    }

    public String getReadName() {
        return this.readName;
    }

    public final TypeData<T> getTypeData() {
        return this.typeData;
    }

    public List<Annotation> getWriteAnnotations() {
        return this.writeAnnotations;
    }

    public String getWriteName() {
        return this.writeName;
    }

    public Boolean isDiscriminatorEnabled() {
        return this.discriminatorEnabled;
    }

    public boolean isReadable() {
        return this.readName != null;
    }

    public boolean isWritable() {
        return this.writeName != null;
    }

    public PropertyModelBuilder<T> propertyAccessor(PropertyAccessor<T> propertyAccessor) {
        this.propertyAccessor = propertyAccessor;
        return this;
    }

    public final PropertyModelBuilder<T> propertyName(String str) {
        this.name = (String) Assertions.notNull("propertyName", str);
        return this;
    }

    public PropertyModelBuilder<T> propertySerialization(PropertySerialization<T> propertySerialization) {
        this.propertySerialization = (PropertySerialization) Assertions.notNull("propertySerialization", propertySerialization);
        return this;
    }

    public PropertyModelBuilder<T> readAnnotations(List<Annotation> list) {
        this.readAnnotations = Collections.unmodifiableList((List) Assertions.notNull("annotations", list));
        return this;
    }

    public PropertyModelBuilder<T> readName(String str) {
        this.readName = str;
        return this;
    }

    public final PropertyModelBuilder<T> setError(String str) {
        this.error = str;
        return this;
    }

    public String toString() {
        return String.format("PropertyModelBuilder{propertyName=%s, typeData=%s}", this.name, this.typeData);
    }

    public final PropertyModelBuilder<T> typeData(TypeData<T> typeData) {
        this.typeData = (TypeData) Assertions.notNull("typeData", typeData);
        return this;
    }

    public PropertyModelBuilder<T> writeAnnotations(List<Annotation> list) {
        this.writeAnnotations = list;
        return this;
    }

    public PropertyModelBuilder<T> writeName(String str) {
        this.writeName = str;
        return this;
    }
}
