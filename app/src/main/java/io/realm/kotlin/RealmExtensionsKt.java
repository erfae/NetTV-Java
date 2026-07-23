package io.realm.kotlin;

import androidx.exifinterface.media.ExifInterface;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.internal.async.RealmThreadPoolExecutor;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: RealmExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\b¢\u0006\u0002\u0010\u0007\u001a\u001e\u0010\b\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\t\u001a(\u0010\b\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0086\b¢\u0006\u0002\u0010\f\u001a\u0019\u0010\r\u001a\u00020\u000e\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b\u001aB\u0010\u000f\u001a\u00020\u000e*\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112!\u0010\u0012\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0013H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a\u0010\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0019*\u00020\u0003\u001a\u001f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00010\u001b\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001c"}, d2 = {"createEmbeddedObject", ExifInterface.GPS_DIRECTION_TRUE, "Lio/realm/RealmModel;", "Lio/realm/Realm;", "parentObject", "parentProperty", "", "(Lio/realm/Realm;Lio/realm/RealmModel;Ljava/lang/String;)Lio/realm/RealmModel;", "createObject", "(Lio/realm/Realm;)Lio/realm/RealmModel;", "primaryKeyValue", "", "(Lio/realm/Realm;Ljava/lang/Object;)Lio/realm/RealmModel;", "delete", "", "executeTransactionAwait", "context", "Lkotlin/coroutines/CoroutineContext;", "transaction", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "realm", "(Lio/realm/Realm;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toflow", "Lkotlinx/coroutines/flow/Flow;", "where", "Lio/realm/RealmQuery;", "realm-kotlin-extensions_baseRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class RealmExtensionsKt {

    /* JADX INFO: renamed from: io.realm.kotlin.RealmExtensionsKt$executeTransactionAwait$1, reason: invalid class name */
    /* JADX INFO: compiled from: RealmExtensions.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "io.realm.kotlin.RealmExtensionsKt", f = "RealmExtensions.kt", i = {0}, l = {142}, m = "executeTransactionAwait", n = {"$this$executeTransactionAwait"}, s = {"L$0"})
    public static final class AnonymousClass1 extends ContinuationImpl {
        public Realm L$0;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RealmExtensionsKt.executeTransactionAwait(null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: io.realm.kotlin.RealmExtensionsKt$executeTransactionAwait$2, reason: invalid class name */
    /* JADX INFO: compiled from: RealmExtensions.kt */
    @Metadata(bv = {}, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.kotlin.RealmExtensionsKt$executeTransactionAwait$2", f = "RealmExtensions.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Realm $this_executeTransactionAwait;
        public final /* synthetic */ Function1<Realm, Unit> $transaction;
        private /* synthetic */ Object L$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(Realm realm, Function1<? super Realm, Unit> function1, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$this_executeTransactionAwait = realm;
            this.$transaction = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_executeTransactionAwait, this.$transaction, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Realm realm = Realm.getInstance(this.$this_executeTransactionAwait.getConfiguration());
            final Function1<Realm, Unit> function1 = this.$transaction;
            try {
                if (CoroutineScopeKt.isActive(coroutineScope)) {
                    realm.executeTransaction(new Realm.Transaction() { // from class: io.realm.kotlin.RealmExtensionsKt$executeTransactionAwait$2$$ExternalSyntheticLambda0
                        @Override // io.realm.Realm.Transaction
                        public final void execute(Realm realm2) {
                            function1.invoke(realm2);
                        }
                    });
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(realm, null);
                return unit;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(realm, th);
                    throw th2;
                }
            }
        }
    }

    public static final /* synthetic */ <T extends RealmModel> T createEmbeddedObject(Realm realm, RealmModel parentObject, String parentProperty) {
        Intrinsics.checkNotNullParameter(realm, "<this>");
        Intrinsics.checkNotNullParameter(parentObject, "parentObject");
        Intrinsics.checkNotNullParameter(parentProperty, "parentProperty");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        T t = (T) realm.createEmbeddedObject(RealmModel.class, parentObject, parentProperty);
        Intrinsics.checkNotNullExpressionValue(t, "this.createEmbeddedObjec…ntObject, parentProperty)");
        return t;
    }

    public static final /* synthetic */ <T extends RealmModel> T createObject(Realm realm) {
        Intrinsics.checkNotNullParameter(realm, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        T t = (T) realm.createObject(RealmModel.class);
        Intrinsics.checkNotNullExpressionValue(t, "this.createObject(T::class.java)");
        return t;
    }

    public static final /* synthetic */ <T extends RealmModel> void delete(Realm realm) {
        Intrinsics.checkNotNullParameter(realm, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        realm.delete(RealmModel.class);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final Object executeTransactionAwait(@NotNull Realm realm, @NotNull CoroutineContext coroutineContext, @NotNull Function1<? super Realm, Unit> function1, @NotNull Continuation<? super Unit> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(realm, function1, null);
            anonymousClass1.L$0 = realm;
            anonymousClass1.label = 1;
            if (BuildersKt.withContext(coroutineContext, anonymousClass2, anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            realm = anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        realm.refresh();
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object executeTransactionAwait$default(Realm realm, CoroutineContext coroutineContext, Function1 function1, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            RealmThreadPoolExecutor WRITE_EXECUTOR = Realm.WRITE_EXECUTOR;
            Intrinsics.checkNotNullExpressionValue(WRITE_EXECUTOR, "WRITE_EXECUTOR");
            coroutineContext = ExecutorsKt.from((ExecutorService) WRITE_EXECUTOR);
        }
        return executeTransactionAwait(realm, coroutineContext, function1, continuation);
    }

    @NotNull
    public static final Flow<Realm> toflow(@NotNull Realm realm) {
        Intrinsics.checkNotNullParameter(realm, "<this>");
        Flow<Realm> flowFrom = realm.getConfiguration().getFlowFactory().from(realm);
        Intrinsics.checkNotNullExpressionValue(flowFrom, "configuration.flowFactory.from(this)");
        return flowFrom;
    }

    public static final /* synthetic */ <T extends RealmModel> RealmQuery<T> where(Realm realm) {
        Intrinsics.checkNotNullParameter(realm, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        RealmQuery<T> realmQueryWhere = realm.where(RealmModel.class);
        Intrinsics.checkNotNullExpressionValue(realmQueryWhere, "this.where(T::class.java)");
        return realmQueryWhere;
    }

    public static final /* synthetic */ <T extends RealmModel> T createObject(Realm realm, Object obj) {
        Intrinsics.checkNotNullParameter(realm, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        T t = (T) realm.createObject(RealmModel.class, obj);
        Intrinsics.checkNotNullExpressionValue(t, "this.createObject(T::class.java, primaryKeyValue)");
        return t;
    }
}
