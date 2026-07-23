package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public final class zah extends zad<Boolean> {
    public final ListenerHolder.ListenerKey<?> zab;

    public zah(ListenerHolder.ListenerKey<?> listenerKey, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zab = listenerKey;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final boolean zaa(zabq<?> zabqVar) {
        zaci zaciVar = zabqVar.zah().get(this.zab);
        return zaciVar != null && zaciVar.zaa.zab();
    }

    @Override // com.google.android.gms.common.api.internal.zac
    @Nullable
    public final Feature[] zab(zabq<?> zabqVar) {
        zaci zaciVar = zabqVar.zah().get(this.zab);
        if (zaciVar == null) {
            return null;
        }
        return zaciVar.zaa.getRequiredFeatures();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*  JADX ERROR: JadxRuntimeException in pass: FinishTypeInference
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r0v4 boolean
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.lambda$visit$0(FinishTypeInference.java:27)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.visit(FinishTypeInference.java:22)
        */
    @Override // com.google.android.gms.common.api.internal.zad
    public final void zac(com.google.android.gms.common.api.internal.zabq<?> r4) throws android.os.RemoteException {
        /*
            r3 = this;
            java.util.Map r0 = r4.zah()
            com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey<?> r1 = r3.zab
            java.lang.Object r0 = r0.remove(r1)
            com.google.android.gms.common.api.internal.zaci r0 = (com.google.android.gms.common.api.internal.zaci) r0
            if (r0 == 0) goto L1f
            com.google.android.gms.common.api.internal.UnregisterListenerMethod<com.google.android.gms.common.api.Api$AnyClient, ?> r1 = r0.zab
            com.google.android.gms.common.api.Api$Client r4 = r4.zaf()
            com.google.android.gms.tasks.TaskCompletionSource<T> r2 = r3.zaa
            r1.unregisterListener(r4, r2)
            com.google.android.gms.common.api.internal.RegisterListenerMethod<com.google.android.gms.common.api.Api$AnyClient, ?> r4 = r0.zaa
            r4.clearListener()
            return
        L1f:
            com.google.android.gms.tasks.TaskCompletionSource<T> r4 = r3.zaa
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            r4.trySetResult(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zah.zac(com.google.android.gms.common.api.internal.zabq):void");
    }

    @Override // com.google.android.gms.common.api.internal.zad, com.google.android.gms.common.api.internal.zai
    public final /* bridge */ /* synthetic */ void zag(@NonNull zaad zaadVar, boolean z) {
    }
}
