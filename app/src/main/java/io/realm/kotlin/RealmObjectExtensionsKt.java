package io.realm.kotlin;

import androidx.exifinterface.media.ExifInterface;
import io.realm.BaseRealm;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.internal.RealmObjectProxy;
import io.realm.rx.ObjectChange;
import java.util.Objects;
import kotlin.Metadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: RealmObjectExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a)\u0010\u0000\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004*\u0004\u0018\u0001H\u0003¢\u0006\u0002\u0010\u0005\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004*\u0004\u0018\u0001H\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0007"}, d2 = {"toChangesetFlow", "Lkotlinx/coroutines/flow/Flow;", "Lio/realm/rx/ObjectChange;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/realm/RealmModel;", "(Lio/realm/RealmModel;)Lkotlinx/coroutines/flow/Flow;", "toFlow", "realm-kotlin-extensions_baseRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class RealmObjectExtensionsKt {
    @NotNull
    public static final <T extends RealmModel> Flow<ObjectChange<T>> toChangesetFlow(@Nullable T t) {
        Flow<ObjectChange<T>> flowChangesetFrom;
        if (t == null) {
            flowChangesetFrom = null;
        } else {
            if (!(t instanceof RealmObjectProxy)) {
                return FlowKt.flowOf(new ObjectChange(t, null));
            }
            BaseRealm realm$realm = ((RealmObjectProxy) t).realmGet$proxyState().getRealm$realm();
            if (realm$realm instanceof Realm) {
                Realm realm = (Realm) realm$realm;
                flowChangesetFrom = realm.getConfiguration().getFlowFactory().changesetFrom(realm, t);
            } else {
                if (!(realm$realm instanceof DynamicRealm)) {
                    throw new UnsupportedOperationException(realm$realm.getClass() + " is not supported as a candidate for 'toFlow'. Only subclasses of RealmModel/RealmObject can be used.");
                }
                DynamicRealm dynamicRealm = (DynamicRealm) realm$realm;
                flowChangesetFrom = dynamicRealm.getConfiguration().getFlowFactory().changesetFrom(dynamicRealm, (DynamicRealmObject) t);
                Objects.requireNonNull(flowChangesetFrom, "null cannot be cast to non-null type kotlinx.coroutines.flow.Flow<io.realm.rx.ObjectChange<T of io.realm.kotlin.RealmObjectExtensionsKt.toChangesetFlow$lambda-3$lambda-2>?>");
            }
        }
        return flowChangesetFrom == null ? FlowKt.flowOf((Object) null) : flowChangesetFrom;
    }

    @NotNull
    public static final <T extends RealmModel> Flow<T> toFlow(@Nullable T t) {
        Flow<T> flowFrom;
        if (t == null) {
            flowFrom = null;
        } else {
            if (!(t instanceof RealmObjectProxy)) {
                return FlowKt.flowOf(t);
            }
            BaseRealm realm$realm = ((RealmObjectProxy) t).realmGet$proxyState().getRealm$realm();
            if (realm$realm instanceof Realm) {
                Realm realm = (Realm) realm$realm;
                flowFrom = realm.getConfiguration().getFlowFactory().from(realm, t);
            } else {
                if (!(realm$realm instanceof DynamicRealm)) {
                    throw new UnsupportedOperationException(realm$realm.getClass() + " is not supported as a candidate for 'toFlow'. Only subclasses of RealmModel/RealmObject can be used.");
                }
                DynamicRealm dynamicRealm = (DynamicRealm) realm$realm;
                flowFrom = dynamicRealm.getConfiguration().getFlowFactory().from(dynamicRealm, (DynamicRealmObject) t);
                Objects.requireNonNull(flowFrom, "null cannot be cast to non-null type kotlinx.coroutines.flow.Flow<T of io.realm.kotlin.RealmObjectExtensionsKt.toFlow$lambda-1$lambda-0?>");
            }
        }
        return flowFrom == null ? FlowKt.flowOf((Object) null) : flowFrom;
    }
}
