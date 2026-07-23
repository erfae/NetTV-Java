package kotlin.jvm.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin(version = "1.7")
public class FunInterfaceConstructorReference extends FunctionReference {
    private final Class funInterface;

    public FunInterfaceConstructorReference(Class cls) {
        super(1);
        this.funInterface = cls;
    }

    @Override // kotlin.jvm.internal.FunctionReference
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FunInterfaceConstructorReference) {
            return this.funInterface.equals(((FunInterfaceConstructorReference) obj).funInterface);
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionReference, kotlin.jvm.internal.CallableReference
    public final /* bridge */ /* synthetic */ KCallable getReflected() {
        getReflected();
        throw null;
    }

    @Override // kotlin.jvm.internal.FunctionReference
    public int hashCode() {
        return this.funInterface.hashCode();
    }

    @Override // kotlin.jvm.internal.FunctionReference
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("fun interface ");
        sbM.append(this.funInterface.getName());
        return sbM.toString();
    }

    @Override // kotlin.jvm.internal.FunctionReference, kotlin.jvm.internal.CallableReference
    public final KFunction getReflected() {
        throw new UnsupportedOperationException("Functional interface constructor does not support reflection");
    }
}
