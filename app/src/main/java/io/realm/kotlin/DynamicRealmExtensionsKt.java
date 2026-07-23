package io.realm.kotlin;

import io.realm.DynamicRealm;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: DynamicRealmExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toflow", "Lkotlinx/coroutines/flow/Flow;", "Lio/realm/DynamicRealm;", "realm-kotlin-extensions_baseRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class DynamicRealmExtensionsKt {
    @NotNull
    public static final Flow<DynamicRealm> toflow(@NotNull DynamicRealm dynamicRealm) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "<this>");
        Flow<DynamicRealm> flowFrom = dynamicRealm.getConfiguration().getFlowFactory().from(dynamicRealm);
        Intrinsics.checkNotNullExpressionValue(flowFrom, "configuration.flowFactory.from(this)");
        return flowFrom;
    }
}
