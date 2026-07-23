package kotlinx.coroutines.debug.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineId;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DebugProbesImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ö\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\bÀ\u0002\u0018\u00002\u00020\u0014:\u0002\u0095\u0001B\t\b\u0002¢\u0006\u0004\b\u0001\u0010\u0002J3\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\u0004\b\u0015\u0010\u0016J@\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f\"\b\b\u0000\u0010\u0017*\u00020\u00142\u001e\b\u0004\u0010\u001b\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0019\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00028\u00000\u0018H\u0082\b¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\u001e\u0010\u000eJ\u0013\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f¢\u0006\u0004\b \u0010\u0012J)\u0010$\u001a\b\u0012\u0004\u0012\u00020\"0\u000f2\u0006\u0010!\u001a\u00020\u00102\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000f¢\u0006\u0004\b$\u0010%J\u0015\u0010'\u001a\u00020&2\u0006\u0010!\u001a\u00020\u0010¢\u0006\u0004\b'\u0010(J5\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0\u000f2\u0006\u0010)\u001a\u00020&2\b\u0010+\u001a\u0004\u0018\u00010*2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\b,\u0010-J?\u00102\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.012\u0006\u0010/\u001a\u00020.2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\"0\u00132\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\b2\u00103J3\u00105\u001a\u00020.2\u0006\u00104\u001a\u00020.2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\"0\u00132\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\b5\u00106J\u001d\u00109\u001a\u0010\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\f\u0018\u000107H\u0002¢\u0006\u0004\b9\u0010:J\u0015\u0010=\u001a\u00020&2\u0006\u0010<\u001a\u00020;¢\u0006\u0004\b=\u0010>J\r\u0010?\u001a\u00020\f¢\u0006\u0004\b?\u0010\u0002J%\u0010A\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\bA\u0010BJ\u001b\u0010D\u001a\u00020\f2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0002¢\u0006\u0004\bD\u0010EJ)\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010K\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bI\u0010JJ\u001b\u0010M\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bL\u0010JJ'\u0010P\u001a\b\u0012\u0004\u0012\u00020\"0\u000f\"\b\b\u0000\u0010\u0003*\u00020N2\u0006\u0010O\u001a\u00028\u0000H\u0002¢\u0006\u0004\bP\u0010QJ\u000f\u0010R\u001a\u00020\fH\u0002¢\u0006\u0004\bR\u0010\u0002J\u000f\u0010S\u001a\u00020\fH\u0002¢\u0006\u0004\bS\u0010\u0002J\r\u0010T\u001a\u00020\f¢\u0006\u0004\bT\u0010\u0002J\u001f\u0010V\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020U2\u0006\u0010)\u001a\u00020&H\u0002¢\u0006\u0004\bV\u0010WJ#\u0010X\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010)\u001a\u00020&H\u0002¢\u0006\u0004\bX\u0010YJ/\u0010X\u001a\u00020\f2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030\u00192\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010)\u001a\u00020&H\u0002¢\u0006\u0004\bX\u0010ZJ;\u0010b\u001a\u00020\f*\u00020;2\u0012\u0010]\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020\\0[2\n\u0010`\u001a\u00060^j\u0002`_2\u0006\u0010a\u001a\u00020&H\u0002¢\u0006\u0004\bb\u0010cJ\u0017\u0010d\u001a\u000208*\u0006\u0012\u0002\b\u00030\u0019H\u0002¢\u0006\u0004\bd\u0010eJ\u001d\u0010C\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0019*\u0006\u0012\u0002\b\u00030\u0004H\u0002¢\u0006\u0004\bC\u0010fJ\u001a\u0010C\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0019*\u00020UH\u0082\u0010¢\u0006\u0004\bC\u0010gJ\u0016\u0010h\u001a\u0004\u0018\u00010U*\u00020UH\u0082\u0010¢\u0006\u0004\bh\u0010iJ\u001b\u0010j\u001a\u0004\u0018\u00010\u0006*\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\bj\u0010kJ\u0013\u0010l\u001a\u00020&*\u00020\u0014H\u0002¢\u0006\u0004\bl\u0010mR\u0014\u0010n\u001a\u00020&8\u0002X\u0082T¢\u0006\u0006\n\u0004\bn\u0010oR \u0010q\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\\0p8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bq\u0010rR\u001e\u0010v\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190s8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bt\u0010uR$\u0010w\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0019\u0012\u0004\u0012\u0002080p8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bw\u0010rR\u0014\u0010y\u001a\u00020x8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\by\u0010zR\u0014\u0010|\u001a\u00020{8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b|\u0010}R\"\u0010~\u001a\u0010\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\f\u0018\u0001078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b~\u0010\u007fR)\u0010\u0080\u0001\u001a\u0002088\u0006@\u0006X\u0086\u000e¢\u0006\u0018\n\u0006\b\u0080\u0001\u0010\u0081\u0001\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001\"\u0006\b\u0084\u0001\u0010\u0085\u0001R\u0019\u0010\u0086\u0001\u001a\u00020.8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0086\u0001\u0010\u0087\u0001R\u0017\u0010\u0089\u0001\u001a\u0002088@X\u0080\u0004¢\u0006\b\u001a\u0006\b\u0088\u0001\u0010\u0083\u0001R)\u0010\u008a\u0001\u001a\u0002088\u0006@\u0006X\u0086\u000e¢\u0006\u0018\n\u0006\b\u008a\u0001\u0010\u0081\u0001\u001a\u0006\b\u008b\u0001\u0010\u0083\u0001\"\u0006\b\u008c\u0001\u0010\u0085\u0001R\u001b\u0010\u008d\u0001\u001a\u0004\u0018\u00010*8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008d\u0001\u0010\u008e\u0001R\"\u0010\u0092\u0001\u001a\u00020&*\u00020;8BX\u0082\u0004¢\u0006\u000f\u0012\u0006\b\u0090\u0001\u0010\u0091\u0001\u001a\u0005\b\u008f\u0001\u0010>R\u001b\u0010\u0093\u0001\u001a\u000208*\u00020\"8BX\u0082\u0004¢\u0006\b\u001a\u0006\b\u0093\u0001\u0010\u0094\u0001¨\u0006\u0096\u0001"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl;", "<init>", "()V", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/coroutines/Continuation;", "completion", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", TypedValues.AttributesType.S_FRAME, "createOwner", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/StackTraceFrame;)Lkotlin/coroutines/Continuation;", "Ljava/io/PrintStream;", "out", "", "dumpCoroutines", "(Ljava/io/PrintStream;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "dumpCoroutinesInfo", "()Ljava/util/List;", "", "", "dumpCoroutinesInfoAsJsonAndReferences", "()[Ljava/lang/Object;", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "Lkotlin/coroutines/CoroutineContext;", "create", "dumpCoroutinesInfoImpl", "(Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "dumpCoroutinesSynchronized", "Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "dumpDebuggerInfo", "info", "Ljava/lang/StackTraceElement;", "coroutineTrace", "enhanceStackTraceWithThreadDump", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;Ljava/util/List;)Ljava/util/List;", "", "enhanceStackTraceWithThreadDumpAsJson", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;)Ljava/lang/String;", "state", "Ljava/lang/Thread;", "thread", "enhanceStackTraceWithThreadDumpImpl", "(Ljava/lang/String;Ljava/lang/Thread;Ljava/util/List;)Ljava/util/List;", "", "indexOfResumeWith", "actualTrace", "Lkotlin/Pair;", "findContinuationStartIndex", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;", "frameIndex", "findIndexOfFrame", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)I", "Lkotlin/Function1;", "", "getDynamicAttach", "()Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/Job;", "job", "hierarchyToString", "(Lkotlinx/coroutines/Job;)Ljava/lang/String;", "install", "frames", "printStackTrace", "(Ljava/io/PrintStream;Ljava/util/List;)V", "owner", "probeCoroutineCompleted", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)V", "probeCoroutineCreated$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "probeCoroutineCreated", "probeCoroutineResumed$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)V", "probeCoroutineResumed", "probeCoroutineSuspended$kotlinx_coroutines_core", "probeCoroutineSuspended", "", "throwable", "sanitizeStackTrace", "(Ljava/lang/Throwable;)Ljava/util/List;", "startWeakRefCleanerThread", "stopWeakRefCleanerThread", "uninstall", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateRunningState", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/String;)V", "updateState", "(Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "map", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "builder", "indent", "build", "(Lkotlinx/coroutines/Job;Ljava/util/Map;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "isFinished", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Z", "(Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "realCaller", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "toStackTraceFrame", "(Ljava/util/List;)Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "toStringWithQuotes", "(Ljava/lang/Object;)Ljava/lang/String;", "ARTIFICIAL_FRAME_MESSAGE", "Ljava/lang/String;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "callerInfoCache", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "getCapturedCoroutines", "()Ljava/util/Set;", "capturedCoroutines", "capturedCoroutinesMap", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "coroutineStateLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "Ljava/text/SimpleDateFormat;", "dateFormat", "Ljava/text/SimpleDateFormat;", "dynamicAttach", "Lkotlin/jvm/functions/Function1;", "enableCreationStackTraces", "Z", "getEnableCreationStackTraces", "()Z", "setEnableCreationStackTraces", "(Z)V", "installations", "I", "isInstalled$kotlinx_coroutines_core", "isInstalled", "sanitizeStackTraces", "getSanitizeStackTraces", "setSanitizeStackTraces", "weakRefCleanerThread", "Ljava/lang/Thread;", "getDebugString", "getDebugString$annotations", "(Lkotlinx/coroutines/Job;)V", "debugString", "isInternalMethod", "(Ljava/lang/StackTraceElement;)Z", "CoroutineOwner", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class DebugProbesImpl {

    @NotNull
    private static final String ARTIFICIAL_FRAME_MESSAGE = "Coroutine creation stacktrace";

    @NotNull
    public static final DebugProbesImpl INSTANCE;

    @NotNull
    private static final ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> callerInfoCache;

    @NotNull
    private static final ConcurrentWeakMap<CoroutineOwner<?>, Boolean> capturedCoroutinesMap;

    @NotNull
    private static final ReentrantReadWriteLock coroutineStateLock;

    @NotNull
    private static final SimpleDateFormat dateFormat;

    @NotNull
    private static final /* synthetic */ SequenceNumberRefVolatile debugProbesImpl$SequenceNumberRefVolatile;

    @Nullable
    private static final Function1<Boolean, Unit> dynamicAttach;
    private static boolean enableCreationStackTraces;
    private static volatile int installations;
    private static boolean sanitizeStackTraces;
    private static final /* synthetic */ AtomicLongFieldUpdater sequenceNumber$FU;

    @Nullable
    private static Thread weakRefCleanerThread;

    /* JADX INFO: compiled from: DebugProbesImpl.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001e\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "delegate", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", TypedValues.AttributesType.S_FRAME, "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class CoroutineOwner<T> implements Continuation<T>, CoroutineStackFrame {

        @JvmField
        @NotNull
        public final Continuation<T> delegate;

        @Nullable
        private final CoroutineStackFrame frame;

        @JvmField
        @NotNull
        public final DebugCoroutineInfoImpl info;

        /* JADX WARN: Multi-variable type inference failed */
        public CoroutineOwner(@NotNull Continuation<? super T> continuation, @NotNull DebugCoroutineInfoImpl debugCoroutineInfoImpl, @Nullable CoroutineStackFrame coroutineStackFrame) {
            this.delegate = continuation;
            this.info = debugCoroutineInfoImpl;
            this.frame = coroutineStackFrame;
        }

        @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
        @Nullable
        public CoroutineStackFrame getCallerFrame() {
            CoroutineStackFrame coroutineStackFrame = this.frame;
            if (coroutineStackFrame == null) {
                return null;
            }
            return coroutineStackFrame.getCallerFrame();
        }

        @Override // kotlin.coroutines.Continuation
        @NotNull
        public CoroutineContext getContext() {
            return this.delegate.getContext();
        }

        @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
        @Nullable
        public StackTraceElement getStackTraceElement() {
            CoroutineStackFrame coroutineStackFrame = this.frame;
            if (coroutineStackFrame == null) {
                return null;
            }
            return coroutineStackFrame.getStackTraceElement();
        }

        @Override // kotlin.coroutines.Continuation
        public void resumeWith(@NotNull Object result) {
            DebugProbesImpl.INSTANCE.probeCoroutineCompleted(this);
            this.delegate.resumeWith(result);
        }

        @NotNull
        public String toString() {
            return this.delegate.toString();
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [kotlinx.coroutines.debug.internal.DebugProbesImpl$SequenceNumberRefVolatile] */
    static {
        DebugProbesImpl debugProbesImpl = new DebugProbesImpl();
        INSTANCE = debugProbesImpl;
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        capturedCoroutinesMap = new ConcurrentWeakMap<>(false, 1, null);
        debugProbesImpl$SequenceNumberRefVolatile = new Object(0L) { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl.SequenceNumberRefVolatile
            public volatile long sequenceNumber;

            {
                this.sequenceNumber = j;
            }
        };
        coroutineStateLock = new ReentrantReadWriteLock();
        sanitizeStackTraces = true;
        enableCreationStackTraces = true;
        dynamicAttach = debugProbesImpl.getDynamicAttach();
        callerInfoCache = new ConcurrentWeakMap<>(true);
        sequenceNumber$FU = AtomicLongFieldUpdater.newUpdater(SequenceNumberRefVolatile.class, "sequenceNumber");
    }

    private DebugProbesImpl() {
    }

    private final void build(Job job, Map<Job, DebugCoroutineInfoImpl> map, StringBuilder sb, String str) {
        DebugCoroutineInfoImpl debugCoroutineInfoImpl = map.get(job);
        if (debugCoroutineInfoImpl != null) {
            StackTraceElement stackTraceElement = (StackTraceElement) CollectionsKt.firstOrNull((List) debugCoroutineInfoImpl.lastObservedStackTrace());
            String str2 = debugCoroutineInfoImpl.get_state();
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(str);
            Insets$$ExternalSyntheticOutline0.m29m(sbM, getDebugString(job), ", continuation is ", str2, " at line ");
            sbM.append(stackTraceElement);
            sbM.append('\n');
            sb.append(sbM.toString());
            str = Intrinsics.stringPlus(str, "\t");
        } else if (!(job instanceof ScopeCoroutine)) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m(str);
            sbM2.append(getDebugString(job));
            sbM2.append('\n');
            sb.append(sbM2.toString());
            str = Intrinsics.stringPlus(str, "\t");
        }
        Iterator<Job> it = job.getChildren().iterator();
        while (it.hasNext()) {
            build(it.next(), map, sb, str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <T> Continuation<T> createOwner(Continuation<? super T> completion, StackTraceFrame frame) {
        if (!isInstalled$kotlinx_coroutines_core()) {
            return completion;
        }
        CoroutineOwner<?> coroutineOwner = new CoroutineOwner<>(completion, new DebugCoroutineInfoImpl(completion.getContext(), frame, sequenceNumber$FU.incrementAndGet(debugProbesImpl$SequenceNumberRefVolatile)), frame);
        ConcurrentWeakMap<CoroutineOwner<?>, Boolean> concurrentWeakMap = capturedCoroutinesMap;
        concurrentWeakMap.put(coroutineOwner, Boolean.TRUE);
        if (!isInstalled$kotlinx_coroutines_core()) {
            concurrentWeakMap.clear();
        }
        return coroutineOwner;
    }

    private final <R> List<R> dumpCoroutinesInfoImpl(final Function2<? super CoroutineOwner<?>, ? super CoroutineContext, ? extends R> create) {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (!debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                throw new IllegalStateException("Debug probes are not installed".toString());
            }
            List<R> list = SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.sortedWith(CollectionsKt.asSequence(debugProbesImpl.getCapturedCoroutines()), new DebugProbesImpl$dumpCoroutinesInfoImpl$lambda12$$inlined$sortedBy$1()), new Function1<CoroutineOwner<?>, R>() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final R invoke(@NotNull DebugProbesImpl.CoroutineOwner<?> coroutineOwner) {
                    CoroutineContext context;
                    if (DebugProbesImpl.INSTANCE.isFinished(coroutineOwner) || (context = coroutineOwner.info.getContext()) == null) {
                        return null;
                    }
                    return create.invoke(coroutineOwner, context);
                }
            }));
            InlineMarker.finallyStart(1);
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            return list;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    private final void dumpCoroutinesSynchronized(PrintStream out) {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (!debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                throw new IllegalStateException("Debug probes are not installed".toString());
            }
            out.print(Intrinsics.stringPlus("Coroutines dump ", dateFormat.format(Long.valueOf(System.currentTimeMillis()))));
            for (CoroutineOwner coroutineOwner : SequencesKt.sortedWith(SequencesKt.filter(CollectionsKt.asSequence(debugProbesImpl.getCapturedCoroutines()), new Function1<CoroutineOwner<?>, Boolean>() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesSynchronized$1$2
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Boolean invoke(@NotNull DebugProbesImpl.CoroutineOwner<?> coroutineOwner2) {
                    return Boolean.valueOf(!DebugProbesImpl.INSTANCE.isFinished(coroutineOwner2));
                }
            }), new Comparator() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesSynchronized$lambda-19$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((DebugProbesImpl.CoroutineOwner) t).info.sequenceNumber), Long.valueOf(((DebugProbesImpl.CoroutineOwner) t2).info.sequenceNumber));
                }
            })) {
                DebugCoroutineInfoImpl debugCoroutineInfoImpl = coroutineOwner.info;
                List<StackTraceElement> listLastObservedStackTrace = debugCoroutineInfoImpl.lastObservedStackTrace();
                DebugProbesImpl debugProbesImpl2 = INSTANCE;
                List<StackTraceElement> listEnhanceStackTraceWithThreadDumpImpl = debugProbesImpl2.enhanceStackTraceWithThreadDumpImpl(debugCoroutineInfoImpl.get_state(), debugCoroutineInfoImpl.lastObservedThread, listLastObservedStackTrace);
                out.print("\n\nCoroutine " + coroutineOwner.delegate + ", state: " + ((Intrinsics.areEqual(debugCoroutineInfoImpl.get_state(), DebugCoroutineInfoImplKt.RUNNING) && listEnhanceStackTraceWithThreadDumpImpl == listLastObservedStackTrace) ? Intrinsics.stringPlus(debugCoroutineInfoImpl.get_state(), " (Last suspension stacktrace, not an actual stacktrace)") : debugCoroutineInfoImpl.get_state()));
                if (listLastObservedStackTrace.isEmpty()) {
                    out.print(Intrinsics.stringPlus("\n\tat ", StackTraceRecoveryKt.artificialFrame(ARTIFICIAL_FRAME_MESSAGE)));
                    debugProbesImpl2.printStackTrace(out, debugCoroutineInfoImpl.getCreationStackTrace());
                } else {
                    debugProbesImpl2.printStackTrace(out, listEnhanceStackTraceWithThreadDumpImpl);
                }
            }
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
        } catch (Throwable th) {
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    private final List<StackTraceElement> enhanceStackTraceWithThreadDumpImpl(String state, Thread thread, List<StackTraceElement> coroutineTrace) {
        Object objM336constructorimpl;
        if (!Intrinsics.areEqual(state, DebugCoroutineInfoImplKt.RUNNING) || thread == null) {
            return coroutineTrace;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            objM336constructorimpl = Result.m336constructorimpl(thread.getStackTrace());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM336constructorimpl = Result.m336constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m342isFailureimpl(objM336constructorimpl)) {
            objM336constructorimpl = null;
        }
        StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) objM336constructorimpl;
        if (stackTraceElementArr == null) {
            return coroutineTrace;
        }
        int length = stackTraceElementArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            }
            int i2 = i + 1;
            StackTraceElement stackTraceElement = stackTraceElementArr[i];
            if (Intrinsics.areEqual(stackTraceElement.getClassName(), "kotlin.coroutines.jvm.internal.BaseContinuationImpl") && Intrinsics.areEqual(stackTraceElement.getMethodName(), "resumeWith") && Intrinsics.areEqual(stackTraceElement.getFileName(), "ContinuationImpl.kt")) {
                break;
            }
            i = i2;
        }
        Pair<Integer, Integer> pairFindContinuationStartIndex = findContinuationStartIndex(i, stackTraceElementArr, coroutineTrace);
        int iIntValue = pairFindContinuationStartIndex.component1().intValue();
        int iIntValue2 = pairFindContinuationStartIndex.component2().intValue();
        if (iIntValue == -1) {
            return coroutineTrace;
        }
        ArrayList arrayList = new ArrayList((((coroutineTrace.size() + i) - iIntValue) - 1) - iIntValue2);
        int i3 = i - iIntValue2;
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(stackTraceElementArr[i4]);
        }
        int size = coroutineTrace.size();
        for (int i5 = iIntValue + 1; i5 < size; i5++) {
            arrayList.add(coroutineTrace.get(i5));
        }
        return arrayList;
    }

    private final Pair<Integer, Integer> findContinuationStartIndex(int indexOfResumeWith, StackTraceElement[] actualTrace, List<StackTraceElement> coroutineTrace) {
        int i = 0;
        while (i < 3) {
            int i2 = i + 1;
            int iFindIndexOfFrame = INSTANCE.findIndexOfFrame((indexOfResumeWith - 1) - i, actualTrace, coroutineTrace);
            if (iFindIndexOfFrame != -1) {
                return TuplesKt.to(Integer.valueOf(iFindIndexOfFrame), Integer.valueOf(i));
            }
            i = i2;
        }
        return TuplesKt.to(-1, 0);
    }

    private final int findIndexOfFrame(int frameIndex, StackTraceElement[] actualTrace, List<StackTraceElement> coroutineTrace) {
        StackTraceElement stackTraceElement = (StackTraceElement) ArraysKt.getOrNull(actualTrace, frameIndex);
        if (stackTraceElement == null) {
            return -1;
        }
        int i = 0;
        for (StackTraceElement stackTraceElement2 : coroutineTrace) {
            if (Intrinsics.areEqual(stackTraceElement2.getFileName(), stackTraceElement.getFileName()) && Intrinsics.areEqual(stackTraceElement2.getClassName(), stackTraceElement.getClassName()) && Intrinsics.areEqual(stackTraceElement2.getMethodName(), stackTraceElement.getMethodName())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private final Set<CoroutineOwner<?>> getCapturedCoroutines() {
        return capturedCoroutinesMap.keySet();
    }

    private final String getDebugString(Job job) {
        return job instanceof JobSupport ? ((JobSupport) job).toDebugString() : job.toString();
    }

    private static /* synthetic */ void getDebugString$annotations(Job job) {
    }

    private final Function1<Boolean, Unit> getDynamicAttach() {
        Object objM336constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            Object objNewInstance = Class.forName("kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach").getConstructors()[0].newInstance(new Object[0]);
            if (objNewInstance == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Function1<kotlin.Boolean, kotlin.Unit>");
            }
            objM336constructorimpl = Result.m336constructorimpl((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(objNewInstance, 1));
            if (Result.m342isFailureimpl(objM336constructorimpl)) {
                objM336constructorimpl = null;
            }
            return (Function1) objM336constructorimpl;
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM336constructorimpl = Result.m336constructorimpl(ResultKt.createFailure(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isFinished(CoroutineOwner<?> coroutineOwner) {
        CoroutineContext context = coroutineOwner.info.getContext();
        Job job = context == null ? null : (Job) context.get(Job.INSTANCE);
        if (job == null || !job.isCompleted()) {
            return false;
        }
        capturedCoroutinesMap.remove(coroutineOwner);
        return true;
    }

    private final boolean isInternalMethod(StackTraceElement stackTraceElement) {
        return StringsKt.startsWith$default(stackTraceElement.getClassName(), "kotlinx.coroutines");
    }

    private final CoroutineOwner<?> owner(Continuation<?> continuation) {
        CoroutineStackFrame coroutineStackFrame = continuation instanceof CoroutineStackFrame ? (CoroutineStackFrame) continuation : null;
        if (coroutineStackFrame == null) {
            return null;
        }
        return owner(coroutineStackFrame);
    }

    private final void printStackTrace(PrintStream out, List<StackTraceElement> frames) {
        Iterator<T> it = frames.iterator();
        while (it.hasNext()) {
            out.print(Intrinsics.stringPlus("\n\tat ", (StackTraceElement) it.next()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void probeCoroutineCompleted(CoroutineOwner<?> owner) {
        capturedCoroutinesMap.remove(owner);
        CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = owner.info.getLastObservedFrame$kotlinx_coroutines_core();
        CoroutineStackFrame coroutineStackFrameRealCaller = lastObservedFrame$kotlinx_coroutines_core == null ? null : realCaller(lastObservedFrame$kotlinx_coroutines_core);
        if (coroutineStackFrameRealCaller == null) {
            return;
        }
        callerInfoCache.remove(coroutineStackFrameRealCaller);
    }

    private final CoroutineStackFrame realCaller(CoroutineStackFrame coroutineStackFrame) {
        do {
            coroutineStackFrame = coroutineStackFrame.getCallerFrame();
            if (coroutineStackFrame == null) {
                return null;
            }
        } while (coroutineStackFrame.getStackTraceElement() == null);
        return coroutineStackFrame;
    }

    private final <T extends Throwable> List<StackTraceElement> sanitizeStackTrace(T throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int length = stackTrace.length;
        int i = -1;
        int length2 = stackTrace.length - 1;
        if (length2 >= 0) {
            while (true) {
                int i2 = length2 - 1;
                if (Intrinsics.areEqual(stackTrace[length2].getClassName(), "kotlin.coroutines.jvm.internal.DebugProbesKt")) {
                    i = length2;
                    break;
                }
                if (i2 < 0) {
                    break;
                }
                length2 = i2;
            }
        }
        if (!sanitizeStackTraces) {
            int i3 = length - i;
            ArrayList arrayList = new ArrayList(i3);
            int i4 = 0;
            while (i4 < i3) {
                int i5 = i4 + 1;
                arrayList.add(i4 == 0 ? StackTraceRecoveryKt.artificialFrame(ARTIFICIAL_FRAME_MESSAGE) : stackTrace[i4 + i]);
                i4 = i5;
            }
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList((length - i) + 1);
        arrayList2.add(StackTraceRecoveryKt.artificialFrame(ARTIFICIAL_FRAME_MESSAGE));
        while (true) {
            i++;
            while (i < length) {
                if (isInternalMethod(stackTrace[i])) {
                    arrayList2.add(stackTrace[i]);
                    int i6 = i + 1;
                    while (i6 < length && isInternalMethod(stackTrace[i6])) {
                        i6++;
                    }
                    int i7 = i6 - 1;
                    int i8 = i7;
                    while (i8 > i && stackTrace[i8].getFileName() == null) {
                        i8--;
                    }
                    if (i8 > i && i8 < i7) {
                        arrayList2.add(stackTrace[i8]);
                    }
                    arrayList2.add(stackTrace[i7]);
                    i = i6;
                } else {
                    arrayList2.add(stackTrace[i]);
                }
            }
            return arrayList2;
        }
    }

    private final void startWeakRefCleanerThread() {
        weakRefCleanerThread = ThreadsKt.thread((21 & 1) != 0, (21 & 2) != 0 ? false : true, (21 & 4) != 0 ? null : null, (21 & 8) != 0 ? null : "Coroutines Debugger Cleaner", (21 & 16) != 0 ? -1 : 0, new Function0<Unit>() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl.startWeakRefCleanerThread.1
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                DebugProbesImpl.callerInfoCache.runWeakRefQueueCleaningLoopUntilInterrupted();
            }
        });
    }

    private final void stopWeakRefCleanerThread() throws InterruptedException {
        Thread thread = weakRefCleanerThread;
        if (thread == null) {
            return;
        }
        weakRefCleanerThread = null;
        thread.interrupt();
        thread.join();
    }

    private final StackTraceFrame toStackTraceFrame(List<StackTraceElement> list) {
        StackTraceFrame stackTraceFrame = null;
        if (!list.isEmpty()) {
            ListIterator<StackTraceElement> listIterator = list.listIterator(list.size());
            while (listIterator.hasPrevious()) {
                stackTraceFrame = new StackTraceFrame(stackTraceFrame, listIterator.previous());
            }
        }
        return stackTraceFrame;
    }

    private final String toStringWithQuotes(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(Typography.quote);
        sb.append(obj);
        sb.append(Typography.quote);
        return sb.toString();
    }

    private final void updateRunningState(CoroutineStackFrame frame, String state) {
        ReentrantReadWriteLock.ReadLock lock = coroutineStateLock.readLock();
        lock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> concurrentWeakMap = callerInfoCache;
                DebugCoroutineInfoImpl debugCoroutineInfoImplRemove = concurrentWeakMap.remove(frame);
                if (debugCoroutineInfoImplRemove == null) {
                    CoroutineOwner<?> coroutineOwnerOwner = debugProbesImpl.owner(frame);
                    CoroutineStackFrame coroutineStackFrameRealCaller = null;
                    debugCoroutineInfoImplRemove = coroutineOwnerOwner == null ? null : coroutineOwnerOwner.info;
                    if (debugCoroutineInfoImplRemove == null) {
                        return;
                    }
                    CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = debugCoroutineInfoImplRemove.getLastObservedFrame$kotlinx_coroutines_core();
                    if (lastObservedFrame$kotlinx_coroutines_core != null) {
                        coroutineStackFrameRealCaller = debugProbesImpl.realCaller(lastObservedFrame$kotlinx_coroutines_core);
                    }
                    if (coroutineStackFrameRealCaller != null) {
                        concurrentWeakMap.remove(coroutineStackFrameRealCaller);
                    }
                }
                debugCoroutineInfoImplRemove.updateState$kotlinx_coroutines_core(state, (Continuation) frame);
                CoroutineStackFrame coroutineStackFrameRealCaller2 = debugProbesImpl.realCaller(frame);
                if (coroutineStackFrameRealCaller2 == null) {
                    return;
                }
                concurrentWeakMap.put(coroutineStackFrameRealCaller2, debugCoroutineInfoImplRemove);
                Unit unit = Unit.INSTANCE;
            }
        } finally {
            lock.unlock();
        }
    }

    private final void updateState(Continuation<?> frame, String state) {
        if (isInstalled$kotlinx_coroutines_core()) {
            if (Intrinsics.areEqual(state, DebugCoroutineInfoImplKt.RUNNING) && KotlinVersion.CURRENT.isAtLeast(1, 3, 30)) {
                CoroutineStackFrame coroutineStackFrame = frame instanceof CoroutineStackFrame ? (CoroutineStackFrame) frame : null;
                if (coroutineStackFrame == null) {
                    return;
                }
                updateRunningState(coroutineStackFrame, state);
                return;
            }
            CoroutineOwner<?> coroutineOwnerOwner = owner(frame);
            if (coroutineOwnerOwner == null) {
                return;
            }
            updateState(coroutineOwnerOwner, frame, state);
        }
    }

    public final void dumpCoroutines(@NotNull PrintStream out) {
        synchronized (out) {
            INSTANCE.dumpCoroutinesSynchronized(out);
            Unit unit = Unit.INSTANCE;
        }
    }

    @NotNull
    public final List<DebugCoroutineInfo> dumpCoroutinesInfo() {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (!debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                throw new IllegalStateException("Debug probes are not installed".toString());
            }
            List<DebugCoroutineInfo> list = SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.sortedWith(CollectionsKt.asSequence(debugProbesImpl.getCapturedCoroutines()), new DebugProbesImpl$dumpCoroutinesInfoImpl$lambda12$$inlined$sortedBy$1()), new Function1<CoroutineOwner<?>, DebugCoroutineInfo>() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfo$$inlined$dumpCoroutinesInfoImpl$1
                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final DebugCoroutineInfo invoke(@NotNull DebugProbesImpl.CoroutineOwner<?> coroutineOwner) {
                    CoroutineContext context;
                    if (DebugProbesImpl.INSTANCE.isFinished(coroutineOwner) || (context = coroutineOwner.info.getContext()) == null) {
                        return null;
                    }
                    return new DebugCoroutineInfo(coroutineOwner.info, context);
                }
            }));
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            return list;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    @NotNull
    public final Object[] dumpCoroutinesInfoAsJsonAndReferences() {
        String name;
        List<DebugCoroutineInfo> listDumpCoroutinesInfo = dumpCoroutinesInfo();
        int size = listDumpCoroutinesInfo.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        ArrayList arrayList3 = new ArrayList(size);
        for (DebugCoroutineInfo debugCoroutineInfo : listDumpCoroutinesInfo) {
            CoroutineContext context = debugCoroutineInfo.getContext();
            CoroutineName coroutineName = (CoroutineName) context.get(CoroutineName.INSTANCE);
            Long lValueOf = null;
            String stringWithQuotes = (coroutineName == null || (name = coroutineName.getName()) == null) ? null : toStringWithQuotes(name);
            CoroutineDispatcher coroutineDispatcher = (CoroutineDispatcher) context.get(CoroutineDispatcher.INSTANCE);
            String stringWithQuotes2 = coroutineDispatcher == null ? null : toStringWithQuotes(coroutineDispatcher);
            StringBuilder sb = new StringBuilder();
            sb.append("\n                {\n                    \"name\": ");
            sb.append((Object) stringWithQuotes);
            sb.append(",\n                    \"id\": ");
            CoroutineId coroutineId = (CoroutineId) context.get(CoroutineId.INSTANCE);
            if (coroutineId != null) {
                lValueOf = Long.valueOf(coroutineId.getId());
            }
            sb.append(lValueOf);
            sb.append(",\n                    \"dispatcher\": ");
            sb.append((Object) stringWithQuotes2);
            sb.append(",\n                    \"sequenceNumber\": ");
            sb.append(debugCoroutineInfo.getSequenceNumber());
            sb.append(",\n                    \"state\": \"");
            sb.append(debugCoroutineInfo.getState());
            sb.append("\"\n                } \n                ");
            arrayList3.add(StringsKt.trimIndent(sb.toString()));
            arrayList2.add(debugCoroutineInfo.getLastObservedFrame());
            arrayList.add(debugCoroutineInfo.getLastObservedThread());
        }
        Object[] array = arrayList.toArray(new Thread[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        Object[] array2 = arrayList2.toArray(new CoroutineStackFrame[0]);
        Objects.requireNonNull(array2, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        Object[] array3 = listDumpCoroutinesInfo.toArray(new DebugCoroutineInfo[0]);
        Objects.requireNonNull(array3, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        return new Object[]{'[' + CollectionsKt___CollectionsKt.joinToString$default(arrayList3, null, null, null, 0, null, null, 63, null) + ']', array, array2, array3};
    }

    @NotNull
    public final List<DebuggerInfo> dumpDebuggerInfo() {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (!debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                throw new IllegalStateException("Debug probes are not installed".toString());
            }
            List<DebuggerInfo> list = SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.sortedWith(CollectionsKt.asSequence(debugProbesImpl.getCapturedCoroutines()), new DebugProbesImpl$dumpCoroutinesInfoImpl$lambda12$$inlined$sortedBy$1()), new Function1<CoroutineOwner<?>, DebuggerInfo>() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpDebuggerInfo$$inlined$dumpCoroutinesInfoImpl$1
                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final DebuggerInfo invoke(@NotNull DebugProbesImpl.CoroutineOwner<?> coroutineOwner) {
                    CoroutineContext context;
                    if (DebugProbesImpl.INSTANCE.isFinished(coroutineOwner) || (context = coroutineOwner.info.getContext()) == null) {
                        return null;
                    }
                    return new DebuggerInfo(coroutineOwner.info, context);
                }
            }));
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            return list;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    @NotNull
    public final List<StackTraceElement> enhanceStackTraceWithThreadDump(@NotNull DebugCoroutineInfo info, @NotNull List<StackTraceElement> coroutineTrace) {
        return enhanceStackTraceWithThreadDumpImpl(info.getState(), info.getLastObservedThread(), coroutineTrace);
    }

    @NotNull
    public final String enhanceStackTraceWithThreadDumpAsJson(@NotNull DebugCoroutineInfo info) {
        List<StackTraceElement> listEnhanceStackTraceWithThreadDump = enhanceStackTraceWithThreadDump(info, info.lastObservedStackTrace());
        ArrayList arrayList = new ArrayList();
        for (StackTraceElement stackTraceElement : listEnhanceStackTraceWithThreadDump) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("\n                {\n                    \"declaringClass\": \"");
            sbM.append((Object) stackTraceElement.getClassName());
            sbM.append("\",\n                    \"methodName\": \"");
            sbM.append((Object) stackTraceElement.getMethodName());
            sbM.append("\",\n                    \"fileName\": ");
            String fileName = stackTraceElement.getFileName();
            sbM.append((Object) (fileName == null ? null : toStringWithQuotes(fileName)));
            sbM.append(",\n                    \"lineNumber\": ");
            sbM.append(stackTraceElement.getLineNumber());
            sbM.append("\n                }\n                ");
            arrayList.add(StringsKt.trimIndent(sbM.toString()));
        }
        return '[' + CollectionsKt___CollectionsKt.joinToString$default(arrayList, null, null, null, 0, null, null, 63, null) + ']';
    }

    public final boolean getEnableCreationStackTraces() {
        return enableCreationStackTraces;
    }

    public final boolean getSanitizeStackTraces() {
        return sanitizeStackTraces;
    }

    @NotNull
    public final String hierarchyToString(@NotNull Job job) {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (!debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                throw new IllegalStateException("Debug probes are not installed".toString());
            }
            Set<CoroutineOwner<?>> capturedCoroutines = debugProbesImpl.getCapturedCoroutines();
            ArrayList arrayList = new ArrayList();
            for (Object obj : capturedCoroutines) {
                if (((CoroutineOwner) obj).delegate.getContext().get(Job.INSTANCE) != null) {
                    arrayList.add(obj);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList)), 16));
            for (Object obj2 : arrayList) {
                linkedHashMap.put(JobKt.getJob(((CoroutineOwner) obj2).delegate.getContext()), ((CoroutineOwner) obj2).info);
            }
            StringBuilder sb = new StringBuilder();
            INSTANCE.build(job, linkedHashMap, sb, "");
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            return string;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    public final void install() {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            installations++;
            if (installations > 1) {
                while (i < readHoldCount) {
                    i++;
                    lock.lock();
                }
                writeLock.unlock();
                return;
            }
            debugProbesImpl.startWeakRefCleanerThread();
            if (AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core()) {
                while (i < readHoldCount) {
                    i++;
                    lock.lock();
                }
                writeLock.unlock();
                return;
            }
            Function1<Boolean, Unit> function1 = dynamicAttach;
            if (function1 != null) {
                function1.invoke(Boolean.TRUE);
            }
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
        } catch (Throwable th) {
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    public final boolean isInstalled$kotlinx_coroutines_core() {
        return installations > 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final <T> Continuation<T> probeCoroutineCreated$kotlinx_coroutines_core(@NotNull Continuation<? super T> completion) {
        if (isInstalled$kotlinx_coroutines_core() && owner(completion) == null) {
            return createOwner(completion, enableCreationStackTraces ? toStackTraceFrame(sanitizeStackTrace(new Exception())) : null);
        }
        return completion;
    }

    public final void probeCoroutineResumed$kotlinx_coroutines_core(@NotNull Continuation<?> frame) {
        updateState(frame, DebugCoroutineInfoImplKt.RUNNING);
    }

    public final void probeCoroutineSuspended$kotlinx_coroutines_core(@NotNull Continuation<?> frame) {
        updateState(frame, DebugCoroutineInfoImplKt.SUSPENDED);
    }

    public final void setEnableCreationStackTraces(boolean z) {
        enableCreationStackTraces = z;
    }

    public final void setSanitizeStackTraces(boolean z) {
        sanitizeStackTraces = z;
    }

    public final void uninstall() {
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        int i2 = 0;
        while (i2 < readHoldCount) {
            i2++;
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (!debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                throw new IllegalStateException("Agent was not installed".toString());
            }
            installations--;
            if (installations != 0) {
                while (i < readHoldCount) {
                    i++;
                    lock.lock();
                }
                writeLock.unlock();
                return;
            }
            debugProbesImpl.stopWeakRefCleanerThread();
            capturedCoroutinesMap.clear();
            callerInfoCache.clear();
            if (AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core()) {
                while (i < readHoldCount) {
                    i++;
                    lock.lock();
                }
                writeLock.unlock();
                return;
            }
            Function1<Boolean, Unit> function1 = dynamicAttach;
            if (function1 != null) {
                function1.invoke(Boolean.FALSE);
            }
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
        } catch (Throwable th) {
            while (i < readHoldCount) {
                i++;
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    private final CoroutineOwner<?> owner(CoroutineStackFrame coroutineStackFrame) {
        while (!(coroutineStackFrame instanceof CoroutineOwner)) {
            coroutineStackFrame = coroutineStackFrame.getCallerFrame();
            if (coroutineStackFrame == null) {
                return null;
            }
        }
        return (CoroutineOwner) coroutineStackFrame;
    }

    private final void updateState(CoroutineOwner<?> owner, Continuation<?> frame, String state) {
        ReentrantReadWriteLock.ReadLock lock = coroutineStateLock.readLock();
        lock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                owner.info.updateState$kotlinx_coroutines_core(state, frame);
                Unit unit = Unit.INSTANCE;
            }
        } finally {
            lock.unlock();
        }
    }
}
