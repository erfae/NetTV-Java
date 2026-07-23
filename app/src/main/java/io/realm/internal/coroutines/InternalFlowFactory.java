package io.realm.internal.coroutines;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import io.realm.CollectionUtils;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.ObjectChangeSet;
import io.realm.OrderedCollectionChangeSet;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.coroutines.FlowFactory;
import io.realm.rx.CollectionChange;
import io.realm.rx.ObjectChange;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: InternalFlowFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016J3\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00070\u0006\"\b\b\u0000\u0010\u000e*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u00062\u0006\u0010\t\u001a\u00020\nH\u0016J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0006\"\b\b\u0000\u0010\u000e*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lio/realm/internal/coroutines/InternalFlowFactory;", "Lio/realm/coroutines/FlowFactory;", "returnFrozenObjects", "", "(Z)V", "changesetFrom", "Lkotlinx/coroutines/flow/Flow;", "Lio/realm/rx/ObjectChange;", "Lio/realm/DynamicRealmObject;", "dynamicRealm", "Lio/realm/DynamicRealm;", "dynamicRealmObject", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;", ExifInterface.GPS_DIRECTION_TRUE, CollectionUtils.LIST_TYPE, "Lio/realm/RealmResults;", "results", "Lio/realm/RealmModel;", "realm", "Lio/realm/Realm;", "realmObject", "(Lio/realm/Realm;Lio/realm/RealmModel;)Lkotlinx/coroutines/flow/Flow;", TypedValues.TransitionType.S_FROM, "realmList", "realm-android-library_baseRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class InternalFlowFactory implements FlowFactory {
    private final boolean returnFrozenObjects;

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmResults;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1", f = "InternalFlowFactory.kt", i = {}, l = {166, PsExtractor.AUDIO_STREAM}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass1<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmResults<T>>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m313invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(new CollectionChange(realmResults.freeze(), orderedCollectionChangeSet));
                } else {
                    producerScope.offer(new CollectionChange(realmResults, orderedCollectionChangeSet));
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$results, this.$config, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super CollectionChange<RealmResults<T>>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$results.isValid()) {
                C00431 c00431 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.1.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, c00431, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 = new InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0(producerScope, this.this$0, 0);
            this.$results.addChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                producerScope.offer(new CollectionChange(this.$results.freeze(), null));
            } else {
                producerScope.offer(new CollectionChange(this.$results, null));
            }
            final RealmResults<T> realmResults = this.$results;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    realmResults.removeChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmResults;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2", f = "InternalFlowFactory.kt", i = {}, l = {266, 292}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass2<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmResults<T>>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m314invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(new CollectionChange(realmResults.freeze(), orderedCollectionChangeSet));
                } else {
                    producerScope.offer(new CollectionChange(realmResults, orderedCollectionChangeSet));
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$results, this.$config, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super CollectionChange<RealmResults<T>>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            int i2 = 1;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$results.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.2.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final DynamicRealm dynamicRealm = DynamicRealm.getInstance(this.$config);
            final InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 = new InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0(producerScope, this.this$0, i2);
            this.$results.addChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                producerScope.offer(new CollectionChange(this.$results.freeze(), null));
            } else {
                producerScope.offer(new CollectionChange(this.$results, null));
            }
            final RealmResults<T> realmResults = this.$results;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.2.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (dynamicRealm.isClosed()) {
                        return;
                    }
                    realmResults.removeChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
                    dynamicRealm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3", f = "InternalFlowFactory.kt", i = {}, l = {366, 394}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass3<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmList<T>>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmList<T> $list;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$list = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m315invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!realmList.isValid()) {
                    SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                } else if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(new CollectionChange(realmList.freeze(), orderedCollectionChangeSet));
                } else {
                    producerScope.offer(new CollectionChange(realmList, orderedCollectionChangeSet));
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$list, this.$config, this.this$0, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super CollectionChange<RealmList<T>>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            int i2 = 2;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$list.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.3.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 = new InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0(producerScope, this.this$0, i2);
            this.$list.addChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                producerScope.offer(new CollectionChange(this.$list.freeze(), null));
            } else {
                producerScope.offer(new CollectionChange(this.$list, null));
            }
            final RealmList<T> realmList = this.$list;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.3.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    realmList.removeChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4", f = "InternalFlowFactory.kt", i = {}, l = {467, 495}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass4<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmList<T>>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmList<T> $list;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
            this.$list = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m317invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!realmList.isValid()) {
                    SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                } else if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(new CollectionChange(realmList.freeze(), orderedCollectionChangeSet));
                } else {
                    producerScope.offer(new CollectionChange(realmList, orderedCollectionChangeSet));
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$list, this.$config, this.this$0, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super CollectionChange<RealmList<T>>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$list.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.4.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final DynamicRealm dynamicRealm = DynamicRealm.getInstance(this.$config);
            final InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 = new InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0(producerScope, this.this$0, 3);
            this.$list.addChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                producerScope.offer(new CollectionChange(this.$list.freeze(), null));
            } else {
                producerScope.offer(new CollectionChange(this.$list, null));
            }
            final RealmList<T> realmList = this.$list;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.4.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (dynamicRealm.isClosed()) {
                        return;
                    }
                    realmList.removeChangeListener(internalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0);
                    dynamicRealm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\b\b\u0000\u0010\u0001*\u00020\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0002H\u008a@"}, d2 = {"Lio/realm/RealmModel;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/ObjectChange;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5", f = "InternalFlowFactory.kt", i = {}, l = {569, 597}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass5<T> extends SuspendLambda implements Function2<ProducerScope<? super ObjectChange<T>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ Realm $realm;

        /* JADX INFO: Incorrect field signature: TT; */
        public final /* synthetic */ RealmModel $realmObject;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Incorrect types in method signature: (Lio/realm/Realm;Lio/realm/RealmConfiguration;TT;Lio/realm/internal/coroutines/InternalFlowFactory;Lkotlin/coroutines/Continuation<-Lio/realm/internal/coroutines/InternalFlowFactory$changesetFrom$5;>;)V */
        public AnonymousClass5(Realm realm, RealmConfiguration realmConfiguration, RealmModel realmModel, InternalFlowFactory internalFlowFactory, Continuation continuation) {
            super(2, continuation);
            this.$realm = realm;
            this.$config = realmConfiguration;
            this.$realmObject = realmModel;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m318invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmModel realmModel, ObjectChangeSet objectChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(new ObjectChange(RealmObject.freeze(realmModel), objectChangeSet));
                } else {
                    producerScope.offer(new ObjectChange(realmModel, objectChangeSet));
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass5 anonymousClass5 = new AnonymousClass5(this.$realm, this.$config, this.$realmObject, this.this$0, continuation);
            anonymousClass5.L$0 = obj;
            return anonymousClass5;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super ObjectChange<T>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (this.$realm.isClosed()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.5.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0 internalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0 = new InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0(producerScope, this.this$0, 0);
            RealmObject.addChangeListener(this.$realmObject, internalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0);
            if (RealmObject.isLoaded(this.$realmObject)) {
                if (this.this$0.returnFrozenObjects) {
                    producerScope.offer(new ObjectChange(RealmObject.freeze(this.$realmObject), null));
                } else {
                    producerScope.offer(new ObjectChange(this.$realmObject, null));
                }
            }
            final RealmModel realmModel = this.$realmObject;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.5.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Incorrect types in method signature: (Lio/realm/Realm;TT;Lio/realm/RealmObjectChangeListener<TT;>;)V */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    RealmObject.removeChangeListener(realmModel, internalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/ObjectChange;", "Lio/realm/DynamicRealmObject;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6", f = "InternalFlowFactory.kt", i = {}, l = {674, TypedValues.TransitionType.TYPE_TO}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass6 extends SuspendLambda implements Function2<ProducerScope<? super ObjectChange<DynamicRealmObject>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ DynamicRealmObject $dynamicRealmObject;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(DynamicRealmObject dynamicRealmObject, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass6> continuation) {
            super(2, continuation);
            this.$dynamicRealmObject = dynamicRealmObject;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m320invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, DynamicRealmObject dynamicRealmObject, ObjectChangeSet objectChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(new ObjectChange(RealmObject.freeze(dynamicRealmObject), objectChangeSet));
                } else {
                    producerScope.offer(new ObjectChange(dynamicRealmObject, objectChangeSet));
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass6 anonymousClass6 = new AnonymousClass6(this.$dynamicRealmObject, this.$config, this.this$0, continuation);
            anonymousClass6.L$0 = obj;
            return anonymousClass6;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super ObjectChange<DynamicRealmObject>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass6) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            int i2 = 1;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!RealmObject.isValid(this.$dynamicRealmObject)) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.6.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0 internalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0 = new InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0(producerScope, this.this$0, i2);
            RealmObject.addChangeListener(this.$dynamicRealmObject, internalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0);
            if (RealmObject.isLoaded(this.$dynamicRealmObject)) {
                if (this.this$0.returnFrozenObjects) {
                    producerScope.offer(new ObjectChange(RealmObject.freeze(this.$dynamicRealmObject), null));
                } else {
                    producerScope.offer(new ObjectChange(this.$dynamicRealmObject, null));
                }
            }
            final DynamicRealmObject dynamicRealmObject = this.$dynamicRealmObject;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.6.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    RealmObject.removeChangeListener(dynamicRealmObject, internalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/Realm;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$1", f = "InternalFlowFactory.kt", i = {}, l = {64}, m = "invokeSuspend", n = {}, s = {})
    public static final class C00621 extends SuspendLambda implements Function2<ProducerScope<? super Realm>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Realm $realm;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00621(Realm realm, InternalFlowFactory internalFlowFactory, Continuation<? super C00621> continuation) {
            super(2, continuation);
            this.$realm = realm;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m321invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, Realm realm, Realm realm2) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(realm2);
                    return;
                }
                Realm realmFreeze = realm.freeze();
                Intrinsics.checkNotNullExpressionValue(realmFreeze, "realm.freeze()");
                producerScope.offer(realmFreeze);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C00621 c00621 = new C00621(this.$realm, this.this$0, continuation);
            c00621.L$0 = obj;
            return c00621;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super Realm> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C00621) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProducerScope producerScope = (ProducerScope) this.L$0;
                final Realm flowRealm = Realm.getInstance(this.$realm.getConfiguration());
                final InternalFlowFactory$from$1$$ExternalSyntheticLambda0 internalFlowFactory$from$1$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$1$$ExternalSyntheticLambda0(producerScope, this.this$0, this.$realm, 0);
                flowRealm.addChangeListener(internalFlowFactory$from$1$$ExternalSyntheticLambda0);
                if (this.this$0.returnFrozenObjects) {
                    Realm realmFreeze = flowRealm.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmFreeze, "flowRealm.freeze()");
                    producerScope.offer(realmFreeze);
                } else {
                    Intrinsics.checkNotNullExpressionValue(flowRealm, "flowRealm");
                    producerScope.offer(flowRealm);
                }
                Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        flowRealm.removeChangeListener(internalFlowFactory$from$1$$ExternalSyntheticLambda0);
                        flowRealm.close();
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/DynamicRealm;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$2", f = "InternalFlowFactory.kt", i = {}, l = {97}, m = "invokeSuspend", n = {}, s = {})
    public static final class C00632 extends SuspendLambda implements Function2<ProducerScope<? super DynamicRealm>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ DynamicRealm $dynamicRealm;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00632(DynamicRealm dynamicRealm, InternalFlowFactory internalFlowFactory, Continuation<? super C00632> continuation) {
            super(2, continuation);
            this.$dynamicRealm = dynamicRealm;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m322invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, DynamicRealm dynamicRealm, DynamicRealm dynamicRealm2) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(dynamicRealm2);
                    return;
                }
                DynamicRealm dynamicRealmFreeze = dynamicRealm.freeze();
                Intrinsics.checkNotNullExpressionValue(dynamicRealmFreeze, "dynamicRealm.freeze()");
                producerScope.offer(dynamicRealmFreeze);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C00632 c00632 = new C00632(this.$dynamicRealm, this.this$0, continuation);
            c00632.L$0 = obj;
            return c00632;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super DynamicRealm> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C00632) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            int i2 = 1;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProducerScope producerScope = (ProducerScope) this.L$0;
                final DynamicRealm flowRealm = DynamicRealm.getInstance(this.$dynamicRealm.getConfiguration());
                final InternalFlowFactory$from$1$$ExternalSyntheticLambda0 internalFlowFactory$from$1$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$1$$ExternalSyntheticLambda0(producerScope, this.this$0, this.$dynamicRealm, i2);
                flowRealm.addChangeListener(internalFlowFactory$from$1$$ExternalSyntheticLambda0);
                if (this.this$0.returnFrozenObjects) {
                    DynamicRealm dynamicRealmFreeze = flowRealm.freeze();
                    Intrinsics.checkNotNullExpressionValue(dynamicRealmFreeze, "flowRealm.freeze()");
                    producerScope.offer(dynamicRealmFreeze);
                } else {
                    Intrinsics.checkNotNullExpressionValue(flowRealm, "flowRealm");
                    producerScope.offer(flowRealm);
                }
                Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        flowRealm.removeChangeListener(internalFlowFactory$from$1$$ExternalSyntheticLambda0);
                        flowRealm.close();
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmResults;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$3", f = "InternalFlowFactory.kt", i = {}, l = {116, 142}, m = "invokeSuspend", n = {}, s = {})
    public static final class C00643<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmResults<T>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00643(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00643> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m324invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(realmResults);
                    return;
                }
                RealmResults realmResultsFreeze = realmResults.freeze();
                Intrinsics.checkNotNullExpressionValue(realmResultsFreeze, "listenerResults.freeze()");
                producerScope.offer(realmResultsFreeze);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C00643 c00643 = new C00643(this.$results, this.$config, this.this$0, continuation);
            c00643.L$0 = obj;
            return c00643;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super RealmResults<T>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C00643) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$results.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.3.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$from$3$$ExternalSyntheticLambda0 internalFlowFactory$from$3$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$3$$ExternalSyntheticLambda0(producerScope, this.this$0, 0);
            this.$results.addChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                RealmResults<T> realmResultsFreeze = this.$results.freeze();
                Intrinsics.checkNotNullExpressionValue(realmResultsFreeze, "results.freeze()");
                producerScope.offer(realmResultsFreeze);
            } else {
                producerScope.offer(this.$results);
            }
            final RealmResults<T> realmResults = this.$results;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.3.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    realmResults.removeChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$4, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmResults;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$4", f = "InternalFlowFactory.kt", i = {}, l = {216, 242}, m = "invokeSuspend", n = {}, s = {})
    public static final class C00654<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmResults<T>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00654(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00654> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m326invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(realmResults);
                    return;
                }
                RealmResults realmResultsFreeze = realmResults.freeze();
                Intrinsics.checkNotNullExpressionValue(realmResultsFreeze, "listenerResults.freeze()");
                producerScope.offer(realmResultsFreeze);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C00654 c00654 = new C00654(this.$results, this.$config, this.this$0, continuation);
            c00654.L$0 = obj;
            return c00654;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super RealmResults<T>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C00654) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            int i2 = 1;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$results.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.4.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final DynamicRealm dynamicRealm = DynamicRealm.getInstance(this.$config);
            final InternalFlowFactory$from$3$$ExternalSyntheticLambda0 internalFlowFactory$from$3$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$3$$ExternalSyntheticLambda0(producerScope, this.this$0, i2);
            this.$results.addChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                RealmResults<T> realmResultsFreeze = this.$results.freeze();
                Intrinsics.checkNotNullExpressionValue(realmResultsFreeze, "results.freeze()");
                producerScope.offer(realmResultsFreeze);
            } else {
                producerScope.offer(this.$results);
            }
            final RealmResults<T> realmResults = this.$results;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.4.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (dynamicRealm.isClosed()) {
                        return;
                    }
                    realmResults.removeChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
                    dynamicRealm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$5, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmList;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$5", f = "InternalFlowFactory.kt", i = {}, l = {314, 342}, m = "invokeSuspend", n = {}, s = {})
    public static final class C00665<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmList<T>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmList<T> $realmList;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00665(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00665> continuation) {
            super(2, continuation);
            this.$realmList = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m327invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!realmList.isValid()) {
                    SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                } else {
                    if (!internalFlowFactory.returnFrozenObjects) {
                        producerScope.offer(realmList);
                        return;
                    }
                    RealmList realmListFreeze = realmList.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmListFreeze, "listenerResults.freeze()");
                    producerScope.offer(realmListFreeze);
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C00665 c00665 = new C00665(this.$realmList, this.$config, this.this$0, continuation);
            c00665.L$0 = obj;
            return c00665;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super RealmList<T>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C00665) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            int i2 = 2;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$realmList.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.5.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$from$3$$ExternalSyntheticLambda0 internalFlowFactory$from$3$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$3$$ExternalSyntheticLambda0(producerScope, this.this$0, i2);
            this.$realmList.addChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                RealmList<T> realmListFreeze = this.$realmList.freeze();
                Intrinsics.checkNotNullExpressionValue(realmListFreeze, "realmList.freeze()");
                producerScope.offer(realmListFreeze);
            } else {
                producerScope.offer(this.$realmList);
            }
            final RealmList<T> realmList = this.$realmList;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.5.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    realmList.removeChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$6, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmList;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$6", f = "InternalFlowFactory.kt", i = {}, l = {415, 443}, m = "invokeSuspend", n = {}, s = {})
    public static final class C00676<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmList<T>>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ RealmList<T> $realmList;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00676(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00676> continuation) {
            super(2, continuation);
            this.$realmList = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m328invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!realmList.isValid()) {
                    SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                } else {
                    if (!internalFlowFactory.returnFrozenObjects) {
                        producerScope.offer(realmList);
                        return;
                    }
                    RealmList realmListFreeze = realmList.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmListFreeze, "listenerResults.freeze()");
                    producerScope.offer(realmListFreeze);
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C00676 c00676 = new C00676(this.$realmList, this.$config, this.this$0, continuation);
            c00676.L$0 = obj;
            return c00676;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super RealmList<T>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C00676) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (!this.$realmList.isValid()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.6.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final DynamicRealm dynamicRealm = DynamicRealm.getInstance(this.$config);
            final InternalFlowFactory$from$3$$ExternalSyntheticLambda0 internalFlowFactory$from$3$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$3$$ExternalSyntheticLambda0(producerScope, this.this$0, 3);
            this.$realmList.addChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
            if (this.this$0.returnFrozenObjects) {
                RealmList<T> realmListFreeze = this.$realmList.freeze();
                Intrinsics.checkNotNullExpressionValue(realmListFreeze, "realmList.freeze()");
                producerScope.offer(realmListFreeze);
            } else {
                producerScope.offer(this.$realmList);
            }
            final RealmList<T> realmList = this.$realmList;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.6.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (dynamicRealm.isClosed()) {
                        return;
                    }
                    realmList.removeChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
                    dynamicRealm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$7, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\b\b\u0000\u0010\u0001*\u00020\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0002H\u008a@"}, d2 = {"Lio/realm/RealmModel;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$7", f = "InternalFlowFactory.kt", i = {}, l = {517, 545}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass7<T> extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ Realm $realm;

        /* JADX INFO: Incorrect field signature: TT; */
        public final /* synthetic */ RealmModel $realmObject;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Incorrect types in method signature: (Lio/realm/Realm;Lio/realm/RealmConfiguration;TT;Lio/realm/internal/coroutines/InternalFlowFactory;Lkotlin/coroutines/Continuation<-Lio/realm/internal/coroutines/InternalFlowFactory$from$7;>;)V */
        public AnonymousClass7(Realm realm, RealmConfiguration realmConfiguration, RealmModel realmModel, InternalFlowFactory internalFlowFactory, Continuation continuation) {
            super(2, continuation);
            this.$realm = realm;
            this.$config = realmConfiguration;
            this.$realmObject = realmModel;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m329invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmModel realmModel) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(realmModel);
                    return;
                }
                RealmModel realmModelFreeze = RealmObject.freeze(realmModel);
                Objects.requireNonNull(realmModelFreeze, "null cannot be cast to non-null type T of io.realm.internal.coroutines.InternalFlowFactory.from.<no name provided>.invokeSuspend$lambda-0");
                producerScope.offer(realmModelFreeze);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass7 anonymousClass7 = new AnonymousClass7(this.$realm, this.$config, this.$realmObject, this.this$0, continuation);
            anonymousClass7.L$0 = obj;
            return anonymousClass7;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super T> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass7) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (this.$realm.isClosed()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.7.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final Realm realm = Realm.getInstance(this.$config);
            final InternalFlowFactory$from$3$$ExternalSyntheticLambda0 internalFlowFactory$from$3$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$3$$ExternalSyntheticLambda0(producerScope, this.this$0, 4);
            RealmObject.addChangeListener(this.$realmObject, internalFlowFactory$from$3$$ExternalSyntheticLambda0);
            if (RealmObject.isLoaded(this.$realmObject)) {
                if (this.this$0.returnFrozenObjects) {
                    RealmModel realmModelFreeze = RealmObject.freeze(this.$realmObject);
                    Intrinsics.checkNotNullExpressionValue(realmModelFreeze, "freeze(realmObject)");
                    producerScope.offer(realmModelFreeze);
                } else {
                    producerScope.offer(this.$realmObject);
                }
            }
            final RealmModel realmModel = this.$realmObject;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.7.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Incorrect types in method signature: (Lio/realm/Realm;TT;Lio/realm/RealmChangeListener<TT;>;)V */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (realm.isClosed()) {
                        return;
                    }
                    RealmObject.removeChangeListener(realmModel, (RealmChangeListener<RealmModel>) internalFlowFactory$from$3$$ExternalSyntheticLambda0);
                    realm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$8, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/DynamicRealmObject;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$8", f = "InternalFlowFactory.kt", i = {}, l = {622, 650}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass8 extends SuspendLambda implements Function2<ProducerScope<? super DynamicRealmObject>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RealmConfiguration $config;
        public final /* synthetic */ DynamicRealm $dynamicRealm;
        public final /* synthetic */ DynamicRealmObject $dynamicRealmObject;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(DynamicRealm dynamicRealm, RealmConfiguration realmConfiguration, DynamicRealmObject dynamicRealmObject, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass8> continuation) {
            super(2, continuation);
            this.$dynamicRealm = dynamicRealm;
            this.$config = realmConfiguration;
            this.$dynamicRealmObject = dynamicRealmObject;
            this.this$0 = internalFlowFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m330invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, DynamicRealmObject dynamicRealmObject) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (!internalFlowFactory.returnFrozenObjects) {
                    producerScope.offer(dynamicRealmObject);
                    return;
                }
                RealmModel realmModelFreeze = dynamicRealmObject.freeze();
                Intrinsics.checkNotNullExpressionValue(realmModelFreeze, "listenerObj.freeze()");
                producerScope.offer(realmModelFreeze);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$dynamicRealm, this.$config, this.$dynamicRealmObject, this.this$0, continuation);
            anonymousClass8.L$0 = obj;
            return anonymousClass8;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super DynamicRealmObject> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass8) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            if (this.$dynamicRealm.isClosed()) {
                AnonymousClass1 anonymousClass1 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.8.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
            final DynamicRealm dynamicRealm = DynamicRealm.getInstance(this.$config);
            final InternalFlowFactory$from$3$$ExternalSyntheticLambda0 internalFlowFactory$from$3$$ExternalSyntheticLambda0 = new InternalFlowFactory$from$3$$ExternalSyntheticLambda0(producerScope, this.this$0, 5);
            this.$dynamicRealmObject.addChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
            if (RealmObject.isLoaded(this.$dynamicRealmObject)) {
                if (this.this$0.returnFrozenObjects) {
                    RealmModel realmModelFreeze = RealmObject.freeze(this.$dynamicRealmObject);
                    Intrinsics.checkNotNullExpressionValue(realmModelFreeze, "freeze(dynamicRealmObject)");
                    producerScope.offer(realmModelFreeze);
                } else {
                    producerScope.offer(this.$dynamicRealmObject);
                }
            }
            final DynamicRealmObject dynamicRealmObject = this.$dynamicRealmObject;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.8.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (dynamicRealm.isClosed()) {
                        return;
                    }
                    dynamicRealmObject.removeChangeListener(internalFlowFactory$from$3$$ExternalSyntheticLambda0);
                    dynamicRealm.close();
                }
            };
            this.label = 2;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public InternalFlowFactory() {
        this(false, 1, null);
    }

    public InternalFlowFactory(boolean z) {
        this.returnFrozenObjects = z;
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<CollectionChange<RealmResults<T>>> changesetFrom(@NotNull Realm realm, @NotNull RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(results, "results");
        return realm.isFrozen() ? FlowKt.flowOf(new CollectionChange(results, null)) : FlowKt.callbackFlow(new AnonymousClass1(results, realm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public Flow<Realm> from(@NotNull Realm realm) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        return realm.isFrozen() ? FlowKt.flowOf(realm) : FlowKt.callbackFlow(new C00621(realm, this, null));
    }

    public /* synthetic */ InternalFlowFactory(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public Flow<DynamicRealm> from(@NotNull DynamicRealm dynamicRealm) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(dynamicRealm);
        }
        return FlowKt.callbackFlow(new C00632(dynamicRealm, this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<CollectionChange<RealmResults<T>>> changesetFrom(@NotNull DynamicRealm dynamicRealm, @NotNull RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(results, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass2(results, dynamicRealm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<RealmResults<T>> from(@NotNull Realm realm, @NotNull RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(results);
        }
        return FlowKt.callbackFlow(new C00643(results, realm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<CollectionChange<RealmList<T>>> changesetFrom(@NotNull Realm realm, @NotNull RealmList<T> list) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(list, "list");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(list, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass3(list, realm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<RealmResults<T>> from(@NotNull DynamicRealm dynamicRealm, @NotNull RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(results);
        }
        return FlowKt.callbackFlow(new C00654(results, dynamicRealm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<CollectionChange<RealmList<T>>> changesetFrom(@NotNull DynamicRealm dynamicRealm, @NotNull RealmList<T> list) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(list, "list");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(list, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass4(list, dynamicRealm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<RealmList<T>> from(@NotNull Realm realm, @NotNull RealmList<T> realmList) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(realmList, "realmList");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(realmList);
        }
        return FlowKt.callbackFlow(new C00665(realmList, realm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T extends RealmModel> Flow<ObjectChange<T>> changesetFrom(@NotNull Realm realm, @NotNull T realmObject) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(realmObject, "realmObject");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(new ObjectChange(realmObject, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass5(realm, realm.getConfiguration(), realmObject, this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T> Flow<RealmList<T>> from(@NotNull DynamicRealm dynamicRealm, @NotNull RealmList<T> realmList) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(realmList, "realmList");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(realmList);
        }
        return FlowKt.callbackFlow(new C00676(realmList, dynamicRealm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public Flow<ObjectChange<DynamicRealmObject>> changesetFrom(@NotNull DynamicRealm dynamicRealm, @NotNull DynamicRealmObject dynamicRealmObject) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(dynamicRealmObject, "dynamicRealmObject");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(new ObjectChange(dynamicRealmObject, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass6(dynamicRealmObject, dynamicRealm.getConfiguration(), this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public <T extends RealmModel> Flow<T> from(@NotNull Realm realm, @NotNull T realmObject) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(realmObject, "realmObject");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(realmObject);
        }
        return FlowKt.callbackFlow(new AnonymousClass7(realm, realm.getConfiguration(), realmObject, this, null));
    }

    @Override // io.realm.coroutines.FlowFactory
    @NotNull
    public Flow<DynamicRealmObject> from(@NotNull DynamicRealm dynamicRealm, @NotNull DynamicRealmObject dynamicRealmObject) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(dynamicRealmObject, "dynamicRealmObject");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(dynamicRealmObject);
        }
        return FlowKt.callbackFlow(new AnonymousClass8(dynamicRealm, dynamicRealm.getConfiguration(), dynamicRealmObject, this, null));
    }
}
