package io.realm.kotlin;

import androidx.exifinterface.media.ExifInterface;
import io.realm.BaseRealm;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.rx.CollectionChange;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: RealmListExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003\u001a\"\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003¨\u0006\u0006"}, d2 = {"toChangesetFlow", "Lkotlinx/coroutines/flow/Flow;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;", ExifInterface.GPS_DIRECTION_TRUE, "toFlow", "realm-kotlin-extensions_baseRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class RealmListExtensionsKt {
    @NotNull
    public static final <T> Flow<CollectionChange<RealmList<T>>> toChangesetFlow(@NotNull RealmList<T> realmList) {
        Intrinsics.checkNotNullParameter(realmList, "<this>");
        BaseRealm baseRealm = realmList.baseRealm;
        if (baseRealm instanceof Realm) {
            Realm realm = (Realm) baseRealm;
            Flow<CollectionChange<RealmList<T>>> flowChangesetFrom = realm.getConfiguration().getFlowFactory().changesetFrom(realm, realmList);
            Intrinsics.checkNotNullExpressionValue(flowChangesetFrom, "realmInstance.configurat…From(realmInstance, this)");
            return flowChangesetFrom;
        }
        if (!(baseRealm instanceof DynamicRealm)) {
            throw new IllegalStateException("Wrong type of Realm.");
        }
        DynamicRealm dynamicRealm = (DynamicRealm) baseRealm;
        Flow<CollectionChange<RealmList<T>>> flowChangesetFrom2 = dynamicRealm.getConfiguration().getFlowFactory().changesetFrom(dynamicRealm, realmList);
        Intrinsics.checkNotNullExpressionValue(flowChangesetFrom2, "realmInstance.configurat…From(realmInstance, this)");
        return flowChangesetFrom2;
    }

    @NotNull
    public static final <T> Flow<RealmList<T>> toFlow(@NotNull RealmList<T> realmList) {
        Intrinsics.checkNotNullParameter(realmList, "<this>");
        BaseRealm baseRealm = realmList.baseRealm;
        if (baseRealm instanceof Realm) {
            Realm realm = (Realm) baseRealm;
            Flow<RealmList<T>> flowFrom = realm.getConfiguration().getFlowFactory().from(realm, realmList);
            Intrinsics.checkNotNullExpressionValue(flowFrom, "realmInstance.configurat…from(realmInstance, this)");
            return flowFrom;
        }
        if (!(baseRealm instanceof DynamicRealm)) {
            throw new IllegalStateException("Wrong type of Realm.");
        }
        DynamicRealm dynamicRealm = (DynamicRealm) baseRealm;
        Flow<RealmList<T>> flowFrom2 = dynamicRealm.getConfiguration().getFlowFactory().from(dynamicRealm, realmList);
        Intrinsics.checkNotNullExpressionValue(flowFrom2, "realmInstance.configurat…from(realmInstance, this)");
        return flowFrom2;
    }
}
