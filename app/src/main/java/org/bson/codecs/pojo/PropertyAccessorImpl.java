package org.bson.codecs.pojo;

import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes2.dex */
final class PropertyAccessorImpl<T> implements PropertyAccessor<T> {
    private final PropertyMetadata<T> propertyMetadata;

    public PropertyAccessorImpl(PropertyMetadata<T> propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }

    private CodecConfigurationException getError(Exception exc) {
        return new CodecConfigurationException(String.format("Unable to get value for property '%s' in %s", this.propertyMetadata.getName(), this.propertyMetadata.getDeclaringClassName()), exc);
    }

    private CodecConfigurationException setError(Exception exc) {
        return new CodecConfigurationException(String.format("Unable to set value for property '%s' in %s", this.propertyMetadata.getName(), this.propertyMetadata.getDeclaringClassName()), exc);
    }

    @Override // org.bson.codecs.pojo.PropertyAccessor
    public <S> T get(S s) {
        try {
            if (this.propertyMetadata.isSerializable()) {
                return this.propertyMetadata.getGetter() != null ? (T) this.propertyMetadata.getGetter().invoke(s, new Object[0]) : (T) this.propertyMetadata.getField().get(s);
            }
            throw getError(null);
        } catch (Exception e) {
            throw getError(e);
        }
    }

    public final PropertyMetadata<T> getPropertyMetadata() {
        return this.propertyMetadata;
    }

    @Override // org.bson.codecs.pojo.PropertyAccessor
    public <S> void set(S s, T t) {
        try {
            if (this.propertyMetadata.isDeserializable()) {
                if (this.propertyMetadata.getSetter() != null) {
                    this.propertyMetadata.getSetter().invoke(s, t);
                } else {
                    this.propertyMetadata.getField().set(s, t);
                }
            }
        } catch (Exception e) {
            throw setError(e);
        }
    }
}
