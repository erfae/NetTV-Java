package kotlin.jvm.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

/* JADX INFO: loaded from: classes2.dex */
public abstract class PropertyReference extends CallableReference implements KProperty {
    public PropertyReference() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PropertyReference) {
            PropertyReference propertyReference = (PropertyReference) obj;
            return getOwner().equals(propertyReference.getOwner()) && getName().equals(propertyReference.getName()) && getSignature().equals(propertyReference.getSignature()) && Intrinsics.areEqual(getBoundReceiver(), propertyReference.getBoundReceiver());
        }
        if (obj instanceof KProperty) {
            return obj.equals(compute());
        }
        return false;
    }

    public int hashCode() {
        return getSignature().hashCode() + ((getName().hashCode() + (getOwner().hashCode() * 31)) * 31);
    }

    @Override // kotlin.reflect.KProperty
    @SinceKotlin(version = "1.1")
    public boolean isConst() {
        return getReflected().isConst();
    }

    @Override // kotlin.reflect.KProperty
    @SinceKotlin(version = "1.1")
    public boolean isLateinit() {
        return getReflected().isLateinit();
    }

    public String toString() {
        KCallable kCallableCompute = compute();
        if (kCallableCompute != this) {
            return kCallableCompute.toString();
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("property ");
        sbM.append(getName());
        sbM.append(" (Kotlin reflection is not available)");
        return sbM.toString();
    }

    @SinceKotlin(version = "1.4")
    public PropertyReference(Object obj, Class cls, String str, String str2, int i) {
        super(obj, cls, str, str2, (i & 1) == 1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    @SinceKotlin(version = "1.1")
    public final KProperty getReflected() {
        return (KProperty) super.getReflected();
    }

    @SinceKotlin(version = "1.1")
    public PropertyReference(Object obj) {
        super(obj, null, null, null, false);
    }
}
