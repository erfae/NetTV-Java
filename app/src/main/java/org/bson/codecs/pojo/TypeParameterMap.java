package org.bson.codecs.pojo;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
final class TypeParameterMap {
    private final Map<Integer, Integer> propertyToClassParamIndexMap;

    public static final class Builder {
        private final Map<Integer, Integer> propertyToClassParamIndexMap;

        public final Builder addIndex(int i) {
            this.propertyToClassParamIndexMap.put(-1, Integer.valueOf(i));
            return this;
        }

        public final TypeParameterMap build() {
            if (this.propertyToClassParamIndexMap.size() <= 1 || !this.propertyToClassParamIndexMap.containsKey(-1)) {
                return new TypeParameterMap(this.propertyToClassParamIndexMap);
            }
            throw new IllegalStateException("You cannot have a generic field that also has type parameters.");
        }

        private Builder() {
            this.propertyToClassParamIndexMap = new HashMap();
        }

        public final Builder addIndex(int i, int i2) {
            this.propertyToClassParamIndexMap.put(Integer.valueOf(i), Integer.valueOf(i2));
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && TypeParameterMap.class == obj.getClass() && this.propertyToClassParamIndexMap.equals(((TypeParameterMap) obj).propertyToClassParamIndexMap);
    }

    public final Map<Integer, Integer> getPropertyToClassParamIndexMap() {
        return this.propertyToClassParamIndexMap;
    }

    public final boolean hasTypeParameters() {
        return !this.propertyToClassParamIndexMap.isEmpty();
    }

    public int hashCode() {
        return this.propertyToClassParamIndexMap.hashCode();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("TypeParameterMap{fieldToClassParamIndexMap=");
        sbM.append(this.propertyToClassParamIndexMap);
        sbM.append("}");
        return sbM.toString();
    }

    private TypeParameterMap(Map<Integer, Integer> map) {
        this.propertyToClassParamIndexMap = Collections.unmodifiableMap(map);
    }
}
