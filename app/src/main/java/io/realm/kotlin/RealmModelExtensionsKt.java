package io.realm.kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmObjectChangeListener;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: RealmModelExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\u001a'\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a'\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007¢\u0006\u0002\u0010\b\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0003\u001a\u0019\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\u0003*\u00020\u0003¢\u0006\u0002\u0010\f\u001a\n\u0010\r\u001a\u00020\u000e*\u00020\u0003\u001a\n\u0010\u000f\u001a\u00020\u000e*\u00020\u0003\u001a\n\u0010\u0010\u001a\u00020\u000e*\u00020\u0003\u001a\n\u0010\u0011\u001a\u00020\u000e*\u00020\u0003\u001a\n\u0010\u0012\u001a\u00020\u000e*\u00020\u0003\u001a\n\u0010\u0013\u001a\u00020\u0001*\u00020\u0003\u001a'\u0010\u0014\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a'\u0010\u0014\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\u0015"}, d2 = {"addChangeListener", "", ExifInterface.LONGITUDE_EAST, "Lio/realm/RealmModel;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lio/realm/RealmChangeListener;", "(Lio/realm/RealmModel;Lio/realm/RealmChangeListener;)V", "Lio/realm/RealmObjectChangeListener;", "(Lio/realm/RealmModel;Lio/realm/RealmObjectChangeListener;)V", "deleteFromRealm", "freeze", ExifInterface.GPS_DIRECTION_TRUE, "(Lio/realm/RealmModel;)Lio/realm/RealmModel;", "isFrozen", "", "isLoaded", "isManaged", "isValid", "load", "removeAllChangeListeners", "removeChangeListener", "realm-kotlin-extensions_baseRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class RealmModelExtensionsKt {
    public static final <E extends RealmModel> void addChangeListener(@NotNull E e, @NotNull RealmChangeListener<E> listener) {
        Intrinsics.checkNotNullParameter(e, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        RealmObject.addChangeListener(e, listener);
    }

    public static final void deleteFromRealm(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        RealmObject.deleteFromRealm(realmModel);
    }

    @NotNull
    public static final <T extends RealmModel> T freeze(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        T t = (T) RealmObject.freeze(realmModel);
        Objects.requireNonNull(t, "null cannot be cast to non-null type T of io.realm.kotlin.RealmModelExtensionsKt.freeze");
        return t;
    }

    public static final boolean isFrozen(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        return RealmObject.isFrozen(realmModel);
    }

    public static final boolean isLoaded(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        return RealmObject.isLoaded(realmModel);
    }

    public static final boolean isManaged(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        return RealmObject.isManaged(realmModel);
    }

    public static final boolean isValid(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        return RealmObject.isValid(realmModel);
    }

    public static final boolean load(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        return RealmObject.load(realmModel);
    }

    public static final void removeAllChangeListeners(@NotNull RealmModel realmModel) {
        Intrinsics.checkNotNullParameter(realmModel, "<this>");
        RealmObject.removeAllChangeListeners(realmModel);
    }

    public static final <E extends RealmModel> void removeChangeListener(@NotNull E e, @NotNull RealmChangeListener<E> listener) {
        Intrinsics.checkNotNullParameter(e, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        RealmObject.removeChangeListener(e, listener);
    }

    public static final <E extends RealmModel> void addChangeListener(@NotNull E e, @NotNull RealmObjectChangeListener<E> listener) {
        Intrinsics.checkNotNullParameter(e, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        RealmObject.addChangeListener(e, listener);
    }

    public static final <E extends RealmModel> void removeChangeListener(@NotNull E e, @NotNull RealmObjectChangeListener<E> listener) {
        Intrinsics.checkNotNullParameter(e, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        RealmObject.removeChangeListener(e, listener);
    }
}
