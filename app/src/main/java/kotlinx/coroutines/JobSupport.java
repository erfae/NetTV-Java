package kotlinx.coroutines;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import io.realm.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause0;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: JobSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
@Metadata(bv = {}, d1 = {"\u0000Ü\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\r\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0006Á\u0001Â\u0001Ã\u0001B\u0012\u0012\u0007\u0010¾\u0001\u001a\u00020\u001b¢\u0006\u0006\b¿\u0001\u0010À\u0001J&\u0010\n\u001a\u00020\t2\u0014\u0010\b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\u0004\u0012\u00020\u00070\u0005H\u0082\b¢\u0006\u0004\b\n\u0010\u000bJ#\u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u000f\u0010\u0010J'\u0010\u0014\u001a\u0004\u0018\u00010\u00122\u0006\u0010\r\u001a\u00020\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002¢\u0006\u0004\b\u0014\u0010\u0015J%\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J!\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ!\u0010\u001e\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ\u001f\u0010#\u001a\u00020\u00072\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u0012H\u0002¢\u0006\u0004\b#\u0010$J\u0017\u0010%\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u0012H\u0002¢\u0006\u0004\b%\u0010&J\u001d\u0010'\u001a\u00020\u0007*\u00020 2\b\u0010\"\u001a\u0004\u0018\u00010\u0012H\u0002¢\u0006\u0004\b'\u0010$J.\u0010*\u001a\u00020\u0007\"\n\b\u0000\u0010)\u0018\u0001*\u00020(2\u0006\u0010!\u001a\u00020 2\b\u0010\"\u001a\u0004\u0018\u00010\u0012H\u0082\b¢\u0006\u0004\b*\u0010$J\u0019\u0010,\u001a\u00020+2\b\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b,\u0010-J@\u00103\u001a\u00020(2'\u00101\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0012¢\u0006\f\b.\u0012\b\b/\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`02\u0006\u00102\u001a\u00020\u001bH\u0002¢\u0006\u0004\b3\u00104J'\u00107\u001a\u00020\u001b2\u0006\u00105\u001a\u00020\u00062\u0006\u0010!\u001a\u00020 2\u0006\u00106\u001a\u00020(H\u0002¢\u0006\u0004\b7\u00108J\u0017\u0010:\u001a\u00020\u00072\u0006\u0010\r\u001a\u000209H\u0002¢\u0006\u0004\b:\u0010;J\u0017\u0010<\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020(H\u0002¢\u0006\u0004\b<\u0010=J\u000f\u0010>\u001a\u00020\u001bH\u0002¢\u0006\u0004\b>\u0010?J\u0013\u0010@\u001a\u00020\u0007H\u0082@ø\u0001\u0000¢\u0006\u0004\b@\u0010AJ\u001b\u0010B\u001a\u0004\u0018\u00010\u00062\b\u0010\"\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\bB\u0010CJ\u0019\u0010D\u001a\u00020\u00122\b\u0010\"\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\bD\u0010EJ\u001b\u0010F\u001a\u0004\u0018\u00010\u00062\b\u0010\"\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\bF\u0010CJ\u0019\u0010G\u001a\u0004\u0018\u00010 2\u0006\u0010\r\u001a\u00020\u0019H\u0002¢\u0006\u0004\bG\u0010HJ\u001f\u0010I\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0012H\u0002¢\u0006\u0004\bI\u0010JJ%\u0010K\u001a\u0004\u0018\u00010\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\bK\u0010LJ#\u0010M\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u00192\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\bM\u0010NJ\u0019\u0010P\u001a\u0004\u0018\u00010O2\u0006\u0010\r\u001a\u00020\u0019H\u0002¢\u0006\u0004\bP\u0010QJ*\u0010S\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010R\u001a\u00020O2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0082\u0010¢\u0006\u0004\bS\u0010TJ)\u0010V\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\f2\u0006\u0010U\u001a\u00020O2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\bV\u0010WJ\u0015\u0010Y\u001a\u0004\u0018\u00010O*\u00020XH\u0002¢\u0006\u0004\bY\u0010ZJ\u0019\u0010\\\u001a\u00020[2\b\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\\\u0010]J\u0015\u0010^\u001a\u0004\u0018\u00010\u0006H\u0082@ø\u0001\u0000¢\u0006\u0004\b^\u0010AJ\r\u0010_\u001a\u00020\u001b¢\u0006\u0004\b_\u0010?J\u0011\u0010b\u001a\u00060`j\u0002`a¢\u0006\u0004\bb\u0010cJ6\u0010e\u001a\u00020d2'\u00101\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0012¢\u0006\f\b.\u0012\b\b/\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`0¢\u0006\u0004\be\u0010fJF\u0010e\u001a\u00020d2\u0006\u00102\u001a\u00020\u001b2\u0006\u0010g\u001a\u00020\u001b2'\u00101\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0012¢\u0006\f\b.\u0012\b\b/\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`0¢\u0006\u0004\be\u0010hJ\u0013\u0010i\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0004\bi\u0010AJB\u0010n\u001a\u00020\u0007\"\u0004\b\u0000\u0010j2\f\u0010l\u001a\b\u0012\u0004\u0012\u00028\u00000k2\u001c\u0010\b\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000m\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005ø\u0001\u0000¢\u0006\u0004\bn\u0010oJ\u0017\u0010q\u001a\u00020\u00072\u0006\u00106\u001a\u00020(H\u0000¢\u0006\u0004\bp\u0010=J\u001f\u0010r\u001a\u00020\u00072\u000e\u0010\"\u001a\n\u0018\u00010`j\u0004\u0018\u0001`aH\u0016¢\u0006\u0004\br\u0010sJ\u0019\u0010r\u001a\u00020\u001b2\b\u0010\"\u001a\u0004\u0018\u00010\u0012H\u0017¢\u0006\u0004\br\u0010&J\u0017\u0010t\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0012H\u0016¢\u0006\u0004\bt\u0010uJ\u0015\u0010w\u001a\u00020\u00072\u0006\u0010v\u001a\u00020\u0003¢\u0006\u0004\bw\u0010xJ\u0017\u0010y\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u0012H\u0016¢\u0006\u0004\by\u0010&J\u0017\u0010z\u001a\u00020\u001b2\b\u0010\"\u001a\u0004\u0018\u00010\u0012¢\u0006\u0004\bz\u0010&J\u0019\u0010}\u001a\u00020\u001b2\b\u0010\"\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0004\b{\u0010|J+\u0010\u0082\u0001\u001a\u00020\u007f2\n\b\u0002\u0010~\u001a\u0004\u0018\u00010[2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0012H\u0080\b¢\u0006\u0006\b\u0080\u0001\u0010\u0081\u0001J\u0015\u0010\u0083\u0001\u001a\u00060`j\u0002`aH\u0016¢\u0006\u0005\b\u0083\u0001\u0010cJ\u001b\u0010\u0085\u0001\u001a\u00020\u001b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0005\b\u0084\u0001\u0010|J\u001d\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0005\b\u0086\u0001\u0010CJ\u0019\u0010\u0089\u0001\u001a\u00030\u0088\u00012\u0006\u0010R\u001a\u00020\u0002¢\u0006\u0006\b\u0089\u0001\u0010\u008a\u0001J\u001a\u0010\u008d\u0001\u001a\u00020\u00072\u0007\u0010\u008b\u0001\u001a\u00020\u0012H\u0010¢\u0006\u0005\b\u008c\u0001\u0010uJ\u0012\u0010\u008e\u0001\u001a\u00020[H\u0016¢\u0006\u0006\b\u008e\u0001\u0010\u008f\u0001J\u0012\u0010\u0090\u0001\u001a\u00020[H\u0007¢\u0006\u0006\b\u0090\u0001\u0010\u008f\u0001J\u0012\u0010\u0092\u0001\u001a\u00020[H\u0010¢\u0006\u0006\b\u0091\u0001\u0010\u008f\u0001J\u0012\u0010\u0093\u0001\u001a\u0004\u0018\u00010\u0012¢\u0006\u0006\b\u0093\u0001\u0010\u0094\u0001J\u0014\u0010\u0097\u0001\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0006\b\u0095\u0001\u0010\u0096\u0001J\u0017\u0010\u0099\u0001\u001a\u0004\u0018\u00010\u0006H\u0080@ø\u0001\u0000¢\u0006\u0005\b\u0098\u0001\u0010AJT\u0010\u009d\u0001\u001a\u00020\u0007\"\u0004\b\u0000\u0010)\"\u0004\b\u0001\u0010j2\f\u0010l\u001a\b\u0012\u0004\u0012\u00028\u00010k2#\u0010\b\u001a\u001f\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010m\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u009a\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u009b\u0001\u0010\u009c\u0001JT\u0010\u009f\u0001\u001a\u00020\u0007\"\u0004\b\u0000\u0010)\"\u0004\b\u0001\u0010j2\f\u0010l\u001a\b\u0012\u0004\u0012\u00028\u00010k2#\u0010\b\u001a\u001f\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010m\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u009a\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u009e\u0001\u0010\u009c\u0001R\u001e\u0010¡\u0001\u001a\u0004\u0018\u00010\u0012*\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0007\u001a\u0005\b \u0001\u0010ER\u001b\u0010¢\u0001\u001a\u00020\u001b*\u00020\u00198BX\u0082\u0004¢\u0006\b\u001a\u0006\b¢\u0001\u0010£\u0001R\u0019\u0010§\u0001\u001a\u0007\u0012\u0002\b\u00030¤\u00018F¢\u0006\b\u001a\u0006\b¥\u0001\u0010¦\u0001R0\u0010\u00ad\u0001\u001a\u0005\u0018\u00010\u0088\u00012\n\u0010¨\u0001\u001a\u0005\u0018\u00010\u0088\u00018@@@X\u0080\u000e¢\u0006\u0010\u001a\u0006\b©\u0001\u0010ª\u0001\"\u0006\b«\u0001\u0010¬\u0001R\u0018\u0010\r\u001a\u0004\u0018\u00010\u00068@X\u0080\u0004¢\u0006\b\u001a\u0006\b®\u0001\u0010\u0096\u0001R\u0016\u0010¯\u0001\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¯\u0001\u0010?R\u0013\u0010°\u0001\u001a\u00020\u001b8F¢\u0006\u0007\u001a\u0005\b°\u0001\u0010?R\u0013\u0010±\u0001\u001a\u00020\u001b8F¢\u0006\u0007\u001a\u0005\b±\u0001\u0010?R\u0014\u0010´\u0001\u001a\u00020\u00048F¢\u0006\b\u001a\u0006\b²\u0001\u0010³\u0001R\u0016\u0010¶\u0001\u001a\u00020\u001b8PX\u0090\u0004¢\u0006\u0007\u001a\u0005\bµ\u0001\u0010?R\u001b\u0010º\u0001\u001a\t\u0012\u0004\u0012\u00020\u00010·\u00018F¢\u0006\b\u001a\u0006\b¸\u0001\u0010¹\u0001R\u0016\u0010¼\u0001\u001a\u00020\u001b8PX\u0090\u0004¢\u0006\u0007\u001a\u0005\b»\u0001\u0010?R\u0013\u0010½\u0001\u001a\u00020\u001b8F¢\u0006\u0007\u001a\u0005\b½\u0001\u0010?\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006Ä\u0001"}, d2 = {"Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/ChildJob;", "Lkotlinx/coroutines/ParentJob;", "Lkotlinx/coroutines/selects/SelectClause0;", "Lkotlin/Function1;", "", "", "block", "", "loopOnState", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Void;", "Lkotlinx/coroutines/JobSupport$Finishing;", "state", "proposedUpdate", "finalizeFinishingState", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/lang/Object;)Ljava/lang/Object;", "", "", "exceptions", "getFinalRootCause", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/util/List;)Ljava/lang/Throwable;", "rootCause", "addSuppressedExceptions", "(Ljava/lang/Throwable;Ljava/util/List;)V", "Lkotlinx/coroutines/Incomplete;", "update", "", "tryFinalizeSimpleState", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)Z", "completeStateFinalization", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)V", "Lkotlinx/coroutines/NodeList;", CollectionUtils.LIST_TYPE, "cause", "notifyCancelling", "(Lkotlinx/coroutines/NodeList;Ljava/lang/Throwable;)V", "cancelParent", "(Ljava/lang/Throwable;)Z", "notifyCompletion", "Lkotlinx/coroutines/JobNode;", ExifInterface.GPS_DIRECTION_TRUE, "notifyHandlers", "", "startInternal", "(Ljava/lang/Object;)I", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "onCancelling", "makeNode", "(Lkotlin/jvm/functions/Function1;Z)Lkotlinx/coroutines/JobNode;", "expect", "node", "addLastAtomic", "(Ljava/lang/Object;Lkotlinx/coroutines/NodeList;Lkotlinx/coroutines/JobNode;)Z", "Lkotlinx/coroutines/Empty;", "promoteEmptyToNodeList", "(Lkotlinx/coroutines/Empty;)V", "promoteSingleToNodeList", "(Lkotlinx/coroutines/JobNode;)V", "joinInternal", "()Z", "joinSuspend", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelMakeCompleting", "(Ljava/lang/Object;)Ljava/lang/Object;", "createCauseException", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "makeCancelling", "getOrPromoteCancellingList", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/NodeList;", "tryMakeCancelling", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Throwable;)Z", "tryMakeCompleting", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "tryMakeCompletingSlowPath", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/ChildHandleNode;", "firstChild", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/ChildHandleNode;", "child", "tryWaitForChild", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)Z", "lastChild", "continueCompleting", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "nextChild", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/ChildHandleNode;", "", "stateString", "(Ljava/lang/Object;)Ljava/lang/String;", "awaitSuspend", TtmlNode.START, "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "getCancellationException", "()Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnCompletion", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "invokeImmediately", "(ZZLkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "join", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/coroutines/Continuation;", "registerSelectClause0", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "removeNode$kotlinx_coroutines_core", "removeNode", "cancel", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "(Ljava/lang/Throwable;)V", "parentJob", "parentCancelled", "(Lkotlinx/coroutines/ParentJob;)V", "childCancelled", "cancelCoroutine", "cancelImpl$kotlinx_coroutines_core", "(Ljava/lang/Object;)Z", "cancelImpl", "message", "Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException$kotlinx_coroutines_core", "(Ljava/lang/String;Ljava/lang/Throwable;)Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException", "getChildJobCancellationCause", "makeCompleting$kotlinx_coroutines_core", "makeCompleting", "makeCompletingOnce$kotlinx_coroutines_core", "makeCompletingOnce", "Lkotlinx/coroutines/ChildHandle;", "attachChild", "(Lkotlinx/coroutines/ChildJob;)Lkotlinx/coroutines/ChildHandle;", "exception", "handleOnCompletionException$kotlinx_coroutines_core", "handleOnCompletionException", "toString", "()Ljava/lang/String;", "toDebugString", "nameString$kotlinx_coroutines_core", "nameString", "getCompletionExceptionOrNull", "()Ljava/lang/Throwable;", "getCompletedInternal$kotlinx_coroutines_core", "()Ljava/lang/Object;", "getCompletedInternal", "awaitInternal$kotlinx_coroutines_core", "awaitInternal", "Lkotlin/Function2;", "registerSelectClause1Internal$kotlinx_coroutines_core", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "registerSelectClause1Internal", "selectAwaitCompletion$kotlinx_coroutines_core", "selectAwaitCompletion", "getExceptionOrNull", "exceptionOrNull", "isCancelling", "(Lkotlinx/coroutines/Incomplete;)Z", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "key", "value", "getParentHandle$kotlinx_coroutines_core", "()Lkotlinx/coroutines/ChildHandle;", "setParentHandle$kotlinx_coroutines_core", "(Lkotlinx/coroutines/ChildHandle;)V", "parentHandle", "getState$kotlinx_coroutines_core", "isActive", "isCompleted", "isCancelled", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "onJoin", "getOnCancelComplete$kotlinx_coroutines_core", "onCancelComplete", "Lkotlin/sequences/Sequence;", "getChildren", "()Lkotlin/sequences/Sequence;", "children", "getHandlesException$kotlinx_coroutines_core", "handlesException", "isCompletedExceptionally", "active", "<init>", "(Z)V", "AwaitContinuation", "ChildCompletion", "Finishing", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public class JobSupport implements Job, ChildJob, ParentJob, SelectClause0 {
    private static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(JobSupport.class, Object.class, "_state");

    @NotNull
    private volatile /* synthetic */ Object _parentHandle;

    @NotNull
    private volatile /* synthetic */ Object _state;

    /* JADX INFO: compiled from: JobSupport.kt */
    @Metadata(bv = {}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u001d\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/JobSupport$AwaitContinuation;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CancellableContinuationImpl;", "Lkotlinx/coroutines/Job;", "parent", "", "getContinuationCancellationCause", "Lkotlinx/coroutines/JobSupport;", "job", "Lkotlinx/coroutines/JobSupport;", "Lkotlin/coroutines/Continuation;", "delegate", "<init>", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/JobSupport;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
    public static final class AwaitContinuation<T> extends CancellableContinuationImpl<T> {

        @NotNull
        private final JobSupport job;

        public AwaitContinuation(@NotNull Continuation<? super T> continuation, @NotNull JobSupport jobSupport) {
            super(continuation, 1);
            this.job = jobSupport;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        @NotNull
        public Throwable getContinuationCancellationCause(@NotNull Job parent) {
            Throwable rootCause;
            Object state$kotlinx_coroutines_core = this.job.getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Finishing) || (rootCause = ((Finishing) state$kotlinx_coroutines_core).getRootCause()) == null) {
                return state$kotlinx_coroutines_core instanceof CompletedExceptionally ? ((CompletedExceptionally) state$kotlinx_coroutines_core).cause : parent.getCancellationException();
            }
            return rootCause;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        @NotNull
        public final String nameString() {
            return "AwaitContinuation";
        }
    }

    /* JADX INFO: compiled from: JobSupport.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/JobSupport$ChildCompletion;", "Lkotlinx/coroutines/JobNode;", "parent", "Lkotlinx/coroutines/JobSupport;", "state", "Lkotlinx/coroutines/JobSupport$Finishing;", "child", "Lkotlinx/coroutines/ChildHandleNode;", "proposedUpdate", "", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class ChildCompletion extends JobNode {

        @NotNull
        private final ChildHandleNode child;

        @NotNull
        private final JobSupport parent;

        @Nullable
        private final Object proposedUpdate;

        @NotNull
        private final Finishing state;

        public ChildCompletion(@NotNull JobSupport jobSupport, @NotNull Finishing finishing, @NotNull ChildHandleNode childHandleNode, @Nullable Object obj) {
            this.parent = jobSupport;
            this.state = finishing;
            this.child = childHandleNode;
            this.proposedUpdate = obj;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.CompletionHandlerBase
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public void invoke2(@Nullable Throwable cause) {
            this.parent.continueCompleting(this.state, this.child, this.proposedUpdate);
        }
    }

    /* JADX INFO: compiled from: JobSupport.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00060\u0018j\u0002`,2\u00020-B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00050\rj\b\u0012\u0004\u0012\u00020\u0005`\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0016\u0010\u0017R(\u0010\u001e\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00188B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010 \"\u0004\b\"\u0010#R\u0011\u0010$\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b$\u0010 R\u001a\u0010\u0002\u001a\u00020\u00018\u0016X\u0096\u0004¢\u0006\f\n\u0004\b\u0002\u0010%\u001a\u0004\b&\u0010'R(\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u00058F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010\f¨\u0006+"}, d2 = {"Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/NodeList;", CollectionUtils.LIST_TYPE, "", "isCompleting", "", "rootCause", "<init>", "(Lkotlinx/coroutines/NodeList;ZLjava/lang/Throwable;)V", "exception", "", "addExceptionLocked", "(Ljava/lang/Throwable;)V", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "allocateList", "()Ljava/util/ArrayList;", "proposedException", "", "sealLocked", "(Ljava/lang/Throwable;)Ljava/util/List;", "", "toString", "()Ljava/lang/String;", "", "value", "getExceptionsHolder", "()Ljava/lang/Object;", "setExceptionsHolder", "(Ljava/lang/Object;)V", "exceptionsHolder", "isActive", "()Z", "isCancelling", "setCompleting", "(Z)V", "isSealed", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "getRootCause", "()Ljava/lang/Throwable;", "setRootCause", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/SynchronizedObject;", "Lkotlinx/coroutines/Incomplete;"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Finishing implements Incomplete {

        @NotNull
        private volatile /* synthetic */ Object _exceptionsHolder = null;

        @NotNull
        private volatile /* synthetic */ int _isCompleting;

        @NotNull
        private volatile /* synthetic */ Object _rootCause;

        @NotNull
        private final NodeList list;

        public Finishing(@NotNull NodeList nodeList, boolean z, @Nullable Throwable th) {
            this.list = nodeList;
            this._isCompleting = z ? 1 : 0;
            this._rootCause = th;
        }

        private final ArrayList<Throwable> allocateList() {
            return new ArrayList<>(4);
        }

        /* JADX INFO: renamed from: getExceptionsHolder, reason: from getter */
        private final Object get_exceptionsHolder() {
            return this._exceptionsHolder;
        }

        private final void setExceptionsHolder(Object obj) {
            this._exceptionsHolder = obj;
        }

        public final void addExceptionLocked(@NotNull Throwable exception) {
            Throwable rootCause = getRootCause();
            if (rootCause == null) {
                setRootCause(exception);
                return;
            }
            if (exception == rootCause) {
                return;
            }
            Object obj = get_exceptionsHolder();
            if (obj == null) {
                setExceptionsHolder(exception);
                return;
            }
            if (!(obj instanceof Throwable)) {
                if (!(obj instanceof ArrayList)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("State is ", obj).toString());
                }
                ((ArrayList) obj).add(exception);
            } else {
                if (exception == obj) {
                    return;
                }
                ArrayList<Throwable> arrayListAllocateList = allocateList();
                arrayListAllocateList.add(obj);
                arrayListAllocateList.add(exception);
                setExceptionsHolder(arrayListAllocateList);
            }
        }

        @Override // kotlinx.coroutines.Incomplete
        @NotNull
        public NodeList getList() {
            return this.list;
        }

        @Nullable
        public final Throwable getRootCause() {
            return (Throwable) this._rootCause;
        }

        @Override // kotlinx.coroutines.Incomplete
        /* JADX INFO: renamed from: isActive */
        public boolean getIsActive() {
            return getRootCause() == null;
        }

        public final boolean isCancelling() {
            return getRootCause() != null;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
        public final boolean isCompleting() {
            return this._isCompleting;
        }

        public final boolean isSealed() {
            return get_exceptionsHolder() == JobSupportKt.SEALED;
        }

        @NotNull
        public final List<Throwable> sealLocked(@Nullable Throwable proposedException) {
            ArrayList<Throwable> arrayListAllocateList;
            Object obj = get_exceptionsHolder();
            if (obj == null) {
                arrayListAllocateList = allocateList();
            } else if (obj instanceof Throwable) {
                ArrayList<Throwable> arrayListAllocateList2 = allocateList();
                arrayListAllocateList2.add(obj);
                arrayListAllocateList = arrayListAllocateList2;
            } else {
                if (!(obj instanceof ArrayList)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("State is ", obj).toString());
                }
                arrayListAllocateList = (ArrayList) obj;
            }
            Throwable rootCause = getRootCause();
            if (rootCause != null) {
                arrayListAllocateList.add(0, rootCause);
            }
            if (proposedException != null && !Intrinsics.areEqual(proposedException, rootCause)) {
                arrayListAllocateList.add(proposedException);
            }
            setExceptionsHolder(JobSupportKt.SEALED);
            return arrayListAllocateList;
        }

        public final void setCompleting(boolean z) {
            this._isCompleting = z ? 1 : 0;
        }

        public final void setRootCause(@Nullable Throwable th) {
            this._rootCause = th;
        }

        @NotNull
        public String toString() {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Finishing[cancelling=");
            sbM.append(isCancelling());
            sbM.append(", completing=");
            sbM.append(isCompleting());
            sbM.append(", rootCause=");
            sbM.append(getRootCause());
            sbM.append(", exceptions=");
            sbM.append(get_exceptionsHolder());
            sbM.append(", list=");
            sbM.append(getList());
            sbM.append(']');
            return sbM.toString();
        }
    }

    public JobSupport(boolean z) {
        this._state = z ? JobSupportKt.EMPTY_ACTIVE : JobSupportKt.EMPTY_NEW;
        this._parentHandle = null;
    }

    private final boolean addLastAtomic(final Object expect, NodeList list, final JobNode node) {
        int iTryCondAddNext;
        LockFreeLinkedListNode.CondAddOp condAddOp = new LockFreeLinkedListNode.CondAddOp(node) { // from class: kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1
            @Override // kotlinx.coroutines.internal.AtomicOp
            @Nullable
            public Object prepare(@NotNull LockFreeLinkedListNode affected) {
                if (this.getState$kotlinx_coroutines_core() == expect) {
                    return null;
                }
                return LockFreeLinkedListKt.getCONDITION_FALSE();
            }
        };
        do {
            iTryCondAddNext = list.getPrevNode().tryCondAddNext(node, list, condAddOp);
            if (iTryCondAddNext == 1) {
                return true;
            }
        } while (iTryCondAddNext != 2);
        return false;
    }

    private final void addSuppressedExceptions(Throwable rootCause, List<? extends Throwable> exceptions) {
        if (exceptions.size() <= 1) {
            return;
        }
        Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap(exceptions.size()));
        for (Throwable th : exceptions) {
            if (th != rootCause && th != rootCause && !(th instanceof CancellationException) && setNewSetFromMap.add(th)) {
                kotlin.ExceptionsKt.addSuppressed(rootCause, th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object awaitSuspend(Continuation<Object> continuation) {
        AwaitContinuation awaitContinuation = new AwaitContinuation(IntrinsicsKt.intercepted(continuation), this);
        awaitContinuation.initCancellability();
        CancellableContinuationKt.disposeOnCancellation(awaitContinuation, invokeOnCompletion(new ResumeAwaitOnCompletion(awaitContinuation)));
        Object result = awaitContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    private final Object cancelMakeCompleting(Object cause) {
        Object objTryMakeCompleting;
        do {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Incomplete) || ((state$kotlinx_coroutines_core instanceof Finishing) && ((Finishing) state$kotlinx_coroutines_core).isCompleting())) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
            objTryMakeCompleting = tryMakeCompleting(state$kotlinx_coroutines_core, new CompletedExceptionally(createCauseException(cause), false, 2, null));
        } while (objTryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        return objTryMakeCompleting;
    }

    private final boolean cancelParent(Throwable cause) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean z = cause instanceof CancellationException;
        ChildHandle parentHandle$kotlinx_coroutines_core = getParentHandle$kotlinx_coroutines_core();
        if (parentHandle$kotlinx_coroutines_core == null || parentHandle$kotlinx_coroutines_core == NonDisposableHandle.INSTANCE) {
            return z;
        }
        return parentHandle$kotlinx_coroutines_core.childCancelled(cause) || z;
    }

    private final void completeStateFinalization(Incomplete state, Object update) throws Throwable {
        ChildHandle parentHandle$kotlinx_coroutines_core = getParentHandle$kotlinx_coroutines_core();
        if (parentHandle$kotlinx_coroutines_core != null) {
            parentHandle$kotlinx_coroutines_core.dispose();
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
        }
        CompletedExceptionally completedExceptionally = update instanceof CompletedExceptionally ? (CompletedExceptionally) update : null;
        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
        if (!(state instanceof JobNode)) {
            NodeList list = state.getList();
            if (list == null) {
                return;
            }
            notifyCompletion(list, th);
            return;
        }
        try {
            ((JobNode) state).invoke(th);
        } catch (Throwable th2) {
            handleOnCompletionException$kotlinx_coroutines_core(new CompletionHandlerException("Exception in completion handler " + state + " for " + this, th2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void continueCompleting(Finishing state, ChildHandleNode lastChild, Object proposedUpdate) {
        ChildHandleNode childHandleNodeNextChild = nextChild(lastChild);
        if (childHandleNodeNextChild == null || !tryWaitForChild(state, childHandleNodeNextChild, proposedUpdate)) {
            afterCompletion(finalizeFinishingState(state, proposedUpdate));
        }
    }

    private final Throwable createCauseException(Object cause) {
        if (cause == null ? true : cause instanceof Throwable) {
            Throwable th = (Throwable) cause;
            return th == null ? new JobCancellationException(cancellationExceptionMessage(), null, this) : th;
        }
        Objects.requireNonNull(cause, "null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
        return ((ParentJob) cause).getChildJobCancellationCause();
    }

    public static /* synthetic */ JobCancellationException defaultCancellationException$kotlinx_coroutines_core$default(JobSupport jobSupport, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: defaultCancellationException");
        }
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            th = null;
        }
        if (str == null) {
            str = jobSupport.cancellationExceptionMessage();
        }
        return new JobCancellationException(str, th, jobSupport);
    }

    private final Object finalizeFinishingState(Finishing state, Object proposedUpdate) throws Throwable {
        boolean zIsCancelling;
        Throwable finalRootCause;
        CompletedExceptionally completedExceptionally = proposedUpdate instanceof CompletedExceptionally ? (CompletedExceptionally) proposedUpdate : null;
        Throwable th = completedExceptionally == null ? null : completedExceptionally.cause;
        synchronized (state) {
            zIsCancelling = state.isCancelling();
            List<Throwable> listSealLocked = state.sealLocked(th);
            finalRootCause = getFinalRootCause(state, listSealLocked);
            if (finalRootCause != null) {
                addSuppressedExceptions(finalRootCause, listSealLocked);
            }
        }
        if (finalRootCause != null && finalRootCause != th) {
            proposedUpdate = new CompletedExceptionally(finalRootCause, false, 2, null);
        }
        if (finalRootCause != null) {
            if (cancelParent(finalRootCause) || handleJobException(finalRootCause)) {
                Objects.requireNonNull(proposedUpdate, "null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                ((CompletedExceptionally) proposedUpdate).makeHandled();
            }
        }
        if (!zIsCancelling) {
            onCancelling(finalRootCause);
        }
        onCompletionInternal(proposedUpdate);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        Object objBoxIncomplete = JobSupportKt.boxIncomplete(proposedUpdate);
        while (!atomicReferenceFieldUpdater.compareAndSet(this, state, objBoxIncomplete) && atomicReferenceFieldUpdater.get(this) == state) {
        }
        completeStateFinalization(state, proposedUpdate);
        return proposedUpdate;
    }

    private final ChildHandleNode firstChild(Incomplete state) {
        ChildHandleNode childHandleNode = state instanceof ChildHandleNode ? (ChildHandleNode) state : null;
        if (childHandleNode != null) {
            return childHandleNode;
        }
        NodeList list = state.getList();
        if (list == null) {
            return null;
        }
        return nextChild(list);
    }

    private final Throwable getExceptionOrNull(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally == null) {
            return null;
        }
        return completedExceptionally.cause;
    }

    private final Throwable getFinalRootCause(Finishing state, List<? extends Throwable> exceptions) {
        Object next;
        Object obj = null;
        if (exceptions.isEmpty()) {
            if (state.isCancelling()) {
                return new JobCancellationException(cancellationExceptionMessage(), null, this);
            }
            return null;
        }
        Iterator<T> it = exceptions.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(!(((Throwable) next) instanceof CancellationException)));
        Throwable th = (Throwable) next;
        if (th != null) {
            return th;
        }
        Throwable th2 = exceptions.get(0);
        if (th2 instanceof TimeoutCancellationException) {
            for (Object obj2 : exceptions) {
                Throwable th3 = (Throwable) obj2;
                if (th3 != th2 && (th3 instanceof TimeoutCancellationException)) {
                    obj = obj2;
                    break;
                }
            }
            Throwable th4 = (Throwable) obj;
            if (th4 != null) {
                return th4;
            }
        }
        return th2;
    }

    private final NodeList getOrPromoteCancellingList(Incomplete state) {
        NodeList list = state.getList();
        if (list != null) {
            return list;
        }
        if (state instanceof Empty) {
            return new NodeList();
        }
        if (!(state instanceof JobNode)) {
            throw new IllegalStateException(Intrinsics.stringPlus("State should have list: ", state).toString());
        }
        promoteSingleToNodeList((JobNode) state);
        return null;
    }

    private final boolean isCancelling(Incomplete incomplete) {
        return (incomplete instanceof Finishing) && ((Finishing) incomplete).isCancelling();
    }

    private final boolean joinInternal() {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                return false;
            }
        } while (startInternal(state$kotlinx_coroutines_core) < 0);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object joinSuspend(Continuation<? super Unit> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationKt.disposeOnCancellation(cancellableContinuationImpl, invokeOnCompletion(new ResumeOnCompletion(cancellableContinuationImpl)));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    private final Void loopOnState(Function1<Object, Unit> block) {
        while (true) {
            block.invoke(getState$kotlinx_coroutines_core());
        }
    }

    private final Object makeCancelling(Object cause) throws Throwable {
        Throwable thCreateCauseException = null;
        while (true) {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (state$kotlinx_coroutines_core instanceof Finishing) {
                synchronized (state$kotlinx_coroutines_core) {
                    if (((Finishing) state$kotlinx_coroutines_core).isSealed()) {
                        return JobSupportKt.TOO_LATE_TO_CANCEL;
                    }
                    boolean zIsCancelling = ((Finishing) state$kotlinx_coroutines_core).isCancelling();
                    if (cause != null || !zIsCancelling) {
                        if (thCreateCauseException == null) {
                            thCreateCauseException = createCauseException(cause);
                        }
                        ((Finishing) state$kotlinx_coroutines_core).addExceptionLocked(thCreateCauseException);
                    }
                    Throwable rootCause = zIsCancelling ^ true ? ((Finishing) state$kotlinx_coroutines_core).getRootCause() : null;
                    if (rootCause != null) {
                        notifyCancelling(((Finishing) state$kotlinx_coroutines_core).getList(), rootCause);
                    }
                    return JobSupportKt.COMPLETING_ALREADY;
                }
            }
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                return JobSupportKt.TOO_LATE_TO_CANCEL;
            }
            if (thCreateCauseException == null) {
                thCreateCauseException = createCauseException(cause);
            }
            Incomplete incomplete = (Incomplete) state$kotlinx_coroutines_core;
            if (!incomplete.getIsActive()) {
                Object objTryMakeCompleting = tryMakeCompleting(state$kotlinx_coroutines_core, new CompletedExceptionally(thCreateCauseException, false, 2, null));
                if (objTryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                    throw new IllegalStateException(Intrinsics.stringPlus("Cannot happen in ", state$kotlinx_coroutines_core).toString());
                }
                if (objTryMakeCompleting != JobSupportKt.COMPLETING_RETRY) {
                    return objTryMakeCompleting;
                }
            } else if (tryMakeCancelling(incomplete, thCreateCauseException)) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
        }
    }

    private final JobNode makeNode(Function1<? super Throwable, Unit> handler, boolean onCancelling) {
        JobNode invokeOnCompletion;
        if (onCancelling) {
            invokeOnCompletion = handler instanceof JobCancellingNode ? (JobCancellingNode) handler : null;
            if (invokeOnCompletion == null) {
                invokeOnCompletion = new InvokeOnCancelling(handler);
            }
        } else {
            JobNode jobNode = handler instanceof JobNode ? (JobNode) handler : null;
            invokeOnCompletion = jobNode != null ? jobNode : null;
            if (invokeOnCompletion == null) {
                invokeOnCompletion = new InvokeOnCompletion(handler);
            }
        }
        invokeOnCompletion.setJob(this);
        return invokeOnCompletion;
    }

    private final ChildHandleNode nextChild(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getPrevNode();
        }
        while (true) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode();
            if (!lockFreeLinkedListNode.isRemoved()) {
                if (lockFreeLinkedListNode instanceof ChildHandleNode) {
                    return (ChildHandleNode) lockFreeLinkedListNode;
                }
                if (lockFreeLinkedListNode instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    private final void notifyCancelling(NodeList list, Throwable cause) throws Throwable {
        CompletionHandlerException completionHandlerException;
        onCancelling(cause);
        CompletionHandlerException completionHandlerException2 = null;
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) list.getNext(); !Intrinsics.areEqual(nextNode, list); nextNode = nextNode.getNextNode()) {
            if (nextNode instanceof JobCancellingNode) {
                JobNode jobNode = (JobNode) nextNode;
                try {
                    jobNode.invoke(cause);
                } catch (Throwable th) {
                    if (completionHandlerException2 == null) {
                        completionHandlerException = null;
                    } else {
                        kotlin.ExceptionsKt.addSuppressed(completionHandlerException2, th);
                        completionHandlerException = completionHandlerException2;
                    }
                    if (completionHandlerException == null) {
                        completionHandlerException2 = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th);
                    }
                }
            }
        }
        if (completionHandlerException2 != null) {
            handleOnCompletionException$kotlinx_coroutines_core(completionHandlerException2);
        }
        cancelParent(cause);
    }

    private final void notifyCompletion(NodeList nodeList, Throwable th) throws Throwable {
        CompletionHandlerException completionHandlerException;
        CompletionHandlerException completionHandlerException2 = null;
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) nodeList.getNext(); !Intrinsics.areEqual(nextNode, nodeList); nextNode = nextNode.getNextNode()) {
            if (nextNode instanceof JobNode) {
                JobNode jobNode = (JobNode) nextNode;
                try {
                    jobNode.invoke(th);
                } catch (Throwable th2) {
                    if (completionHandlerException2 == null) {
                        completionHandlerException = null;
                    } else {
                        kotlin.ExceptionsKt.addSuppressed(completionHandlerException2, th2);
                        completionHandlerException = completionHandlerException2;
                    }
                    if (completionHandlerException == null) {
                        completionHandlerException2 = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th2);
                    }
                }
            }
        }
        if (completionHandlerException2 == null) {
            return;
        }
        handleOnCompletionException$kotlinx_coroutines_core(completionHandlerException2);
    }

    private final /* synthetic */ <T extends JobNode> void notifyHandlers(NodeList list, Throwable cause) throws Throwable {
        CompletionHandlerException completionHandlerException;
        CompletionHandlerException completionHandlerException2 = null;
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) list.getNext(); !Intrinsics.areEqual(nextNode, list); nextNode = nextNode.getNextNode()) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (nextNode instanceof LockFreeLinkedListNode) {
                JobNode jobNode = (JobNode) nextNode;
                try {
                    jobNode.invoke(cause);
                } catch (Throwable th) {
                    if (completionHandlerException2 == null) {
                        completionHandlerException = null;
                    } else {
                        kotlin.ExceptionsKt.addSuppressed(completionHandlerException2, th);
                        completionHandlerException = completionHandlerException2;
                    }
                    if (completionHandlerException == null) {
                        completionHandlerException2 = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th);
                    }
                }
            }
        }
        if (completionHandlerException2 == null) {
            return;
        }
        handleOnCompletionException$kotlinx_coroutines_core(completionHandlerException2);
    }

    private final void promoteEmptyToNodeList(Empty state) {
        NodeList nodeList = new NodeList();
        Object inactiveNodeList = nodeList;
        if (!state.getIsActive()) {
            inactiveNodeList = new InactiveNodeList(nodeList);
        }
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, state, inactiveNodeList) && atomicReferenceFieldUpdater.get(this) == state) {
        }
    }

    private final void promoteSingleToNodeList(JobNode state) {
        state.addOneIfEmpty(new NodeList());
        LockFreeLinkedListNode nextNode = state.getNextNode();
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, state, nextNode) && atomicReferenceFieldUpdater.get(this) == state) {
        }
    }

    private final int startInternal(Object state) {
        boolean z = false;
        if (state instanceof Empty) {
            if (((Empty) state).getIsActive()) {
                return 0;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            Empty empty = JobSupportKt.EMPTY_ACTIVE;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(this, state, empty)) {
                    z = true;
                    break;
                }
            } while (atomicReferenceFieldUpdater.get(this) == state);
            if (!z) {
                return -1;
            }
            onStart();
            return 1;
        }
        if (!(state instanceof InactiveNodeList)) {
            return 0;
        }
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = _state$FU;
        NodeList list = ((InactiveNodeList) state).getList();
        do {
            if (atomicReferenceFieldUpdater2.compareAndSet(this, state, list)) {
                z = true;
                break;
            }
        } while (atomicReferenceFieldUpdater2.get(this) == state);
        if (!z) {
            return -1;
        }
        onStart();
        return 1;
    }

    private final String stateString(Object state) {
        if (!(state instanceof Finishing)) {
            if (state instanceof Incomplete) {
                return ((Incomplete) state).getIsActive() ? "Active" : "New";
            }
            return state instanceof CompletedExceptionally ? "Cancelled" : "Completed";
        }
        Finishing finishing = (Finishing) state;
        if (finishing.isCancelling()) {
            return "Cancelling";
        }
        return finishing.isCompleting() ? "Completing" : "Active";
    }

    public static /* synthetic */ CancellationException toCancellationException$default(JobSupport jobSupport, Throwable th, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
        }
        if ((i & 1) != 0) {
            str = null;
        }
        return jobSupport.toCancellationException(th, str);
    }

    private final boolean tryFinalizeSimpleState(Incomplete state, Object update) throws Throwable {
        boolean z;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        Object objBoxIncomplete = JobSupportKt.boxIncomplete(update);
        while (true) {
            if (atomicReferenceFieldUpdater.compareAndSet(this, state, objBoxIncomplete)) {
                z = true;
                break;
            }
            if (atomicReferenceFieldUpdater.get(this) != state) {
                z = false;
                break;
            }
        }
        if (!z) {
            return false;
        }
        onCancelling(null);
        onCompletionInternal(update);
        completeStateFinalization(state, update);
        return true;
    }

    private final boolean tryMakeCancelling(Incomplete state, Throwable rootCause) throws Throwable {
        boolean z;
        NodeList orPromoteCancellingList = getOrPromoteCancellingList(state);
        if (orPromoteCancellingList == null) {
            return false;
        }
        Finishing finishing = new Finishing(orPromoteCancellingList, false, rootCause);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        while (true) {
            if (atomicReferenceFieldUpdater.compareAndSet(this, state, finishing)) {
                z = true;
                break;
            }
            if (atomicReferenceFieldUpdater.get(this) != state) {
                z = false;
                break;
            }
        }
        if (!z) {
            return false;
        }
        notifyCancelling(orPromoteCancellingList, rootCause);
        return true;
    }

    private final Object tryMakeCompleting(Object state, Object proposedUpdate) {
        if (!(state instanceof Incomplete)) {
            return JobSupportKt.COMPLETING_ALREADY;
        }
        if ((!(state instanceof Empty) && !(state instanceof JobNode)) || (state instanceof ChildHandleNode) || (proposedUpdate instanceof CompletedExceptionally)) {
            return tryMakeCompletingSlowPath((Incomplete) state, proposedUpdate);
        }
        return tryFinalizeSimpleState((Incomplete) state, proposedUpdate) ? proposedUpdate : JobSupportKt.COMPLETING_RETRY;
    }

    private final Object tryMakeCompletingSlowPath(Incomplete state, Object proposedUpdate) throws Throwable {
        NodeList orPromoteCancellingList = getOrPromoteCancellingList(state);
        if (orPromoteCancellingList == null) {
            return JobSupportKt.COMPLETING_RETRY;
        }
        Finishing finishing = state instanceof Finishing ? (Finishing) state : null;
        boolean z = false;
        if (finishing == null) {
            finishing = new Finishing(orPromoteCancellingList, false, null);
        }
        synchronized (finishing) {
            if (finishing.isCompleting()) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
            finishing.setCompleting(true);
            if (finishing != state) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, state, finishing)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == state);
                if (!z) {
                    return JobSupportKt.COMPLETING_RETRY;
                }
            }
            boolean zIsCancelling = finishing.isCancelling();
            CompletedExceptionally completedExceptionally = proposedUpdate instanceof CompletedExceptionally ? (CompletedExceptionally) proposedUpdate : null;
            if (completedExceptionally != null) {
                finishing.addExceptionLocked(completedExceptionally.cause);
            }
            Throwable rootCause = zIsCancelling ^ true ? finishing.getRootCause() : null;
            Unit unit = Unit.INSTANCE;
            if (rootCause != null) {
                notifyCancelling(orPromoteCancellingList, rootCause);
            }
            ChildHandleNode childHandleNodeFirstChild = firstChild(state);
            return (childHandleNodeFirstChild == null || !tryWaitForChild(finishing, childHandleNodeFirstChild, proposedUpdate)) ? finalizeFinishingState(finishing, proposedUpdate) : JobSupportKt.COMPLETING_WAITING_CHILDREN;
        }
    }

    private final boolean tryWaitForChild(Finishing state, ChildHandleNode child, Object proposedUpdate) {
        while (Job.DefaultImpls.invokeOnCompletion$default(child.childJob, false, false, new ChildCompletion(this, state, child, proposedUpdate), 1, null) == NonDisposableHandle.INSTANCE) {
            child = nextChild(child);
            if (child == null) {
                return false;
            }
        }
        return true;
    }

    public void afterCompletion(@Nullable Object obj) {
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final ChildHandle attachChild(@NotNull ChildJob child) {
        return (ChildHandle) Job.DefaultImpls.invokeOnCompletion$default(this, true, false, new ChildHandleNode(child), 2, null);
    }

    @Nullable
    public final Object awaitInternal$kotlinx_coroutines_core(@NotNull Continuation<Object> continuation) throws Throwable {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
                    throw ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
                }
                return JobSupportKt.unboxState(state$kotlinx_coroutines_core);
            }
        } while (startInternal(state$kotlinx_coroutines_core) < 0);
        return awaitSuspend(continuation);
    }

    @Override // kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    public final boolean cancelCoroutine(@Nullable Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    public final boolean cancelImpl$kotlinx_coroutines_core(@Nullable Object cause) throws Throwable {
        Object objMakeCancelling = JobSupportKt.COMPLETING_ALREADY;
        if (getOnCancelComplete$kotlinx_coroutines_core() && (objMakeCancelling = cancelMakeCompleting(cause)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        if (objMakeCancelling == JobSupportKt.COMPLETING_ALREADY) {
            objMakeCancelling = makeCancelling(cause);
        }
        if (objMakeCancelling == JobSupportKt.COMPLETING_ALREADY || objMakeCancelling == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        if (objMakeCancelling == JobSupportKt.TOO_LATE_TO_CANCEL) {
            return false;
        }
        afterCompletion(objMakeCancelling);
        return true;
    }

    public void cancelInternal(@NotNull Throwable cause) throws Throwable {
        cancelImpl$kotlinx_coroutines_core(cause);
    }

    @NotNull
    public String cancellationExceptionMessage() {
        return "Job was cancelled";
    }

    public boolean childCancelled(@NotNull Throwable cause) {
        if (cause instanceof CancellationException) {
            return true;
        }
        return cancelImpl$kotlinx_coroutines_core(cause) && getHandlesException();
    }

    @NotNull
    public final JobCancellationException defaultCancellationException$kotlinx_coroutines_core(@Nullable String message, @Nullable Throwable cause) {
        if (message == null) {
            message = cancellationExceptionMessage();
        }
        return new JobCancellationException(message, cause, this);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    public <R> R fold(R r, @NotNull Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return (R) Job.DefaultImpls.fold(this, r, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    @Nullable
    public <E extends CoroutineContext.Element> E get(@NotNull CoroutineContext.Key<E> key) {
        return (E) Job.DefaultImpls.get(this, key);
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final CancellationException getCancellationException() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (!(state$kotlinx_coroutines_core instanceof Finishing)) {
            if (state$kotlinx_coroutines_core instanceof Incomplete) {
                throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
            }
            return state$kotlinx_coroutines_core instanceof CompletedExceptionally ? toCancellationException$default(this, ((CompletedExceptionally) state$kotlinx_coroutines_core).cause, null, 1, null) : new JobCancellationException(Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " has completed normally"), null, this);
        }
        Throwable rootCause = ((Finishing) state$kotlinx_coroutines_core).getRootCause();
        CancellationException cancellationException = rootCause != null ? toCancellationException(rootCause, Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " is cancelling")) : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
    }

    @Override // kotlinx.coroutines.ParentJob
    @NotNull
    public CancellationException getChildJobCancellationCause() {
        Throwable rootCause;
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof Finishing) {
            rootCause = ((Finishing) state$kotlinx_coroutines_core).getRootCause();
        } else if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            rootCause = ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        } else {
            if (state$kotlinx_coroutines_core instanceof Incomplete) {
                throw new IllegalStateException(Intrinsics.stringPlus("Cannot be cancelling child in this state: ", state$kotlinx_coroutines_core).toString());
            }
            rootCause = null;
        }
        CancellationException cancellationException = rootCause instanceof CancellationException ? (CancellationException) rootCause : null;
        return cancellationException == null ? new JobCancellationException(Intrinsics.stringPlus("Parent job is ", stateString(state$kotlinx_coroutines_core)), rootCause, this) : cancellationException;
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final Sequence<Job> getChildren() {
        return SequencesKt.sequence(new JobSupport$children$1(this, null));
    }

    @Nullable
    public final Object getCompletedInternal$kotlinx_coroutines_core() throws Throwable {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (!(!(state$kotlinx_coroutines_core instanceof Incomplete))) {
            throw new IllegalStateException("This job has not completed yet".toString());
        }
        if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        }
        return JobSupportKt.unboxState(state$kotlinx_coroutines_core);
    }

    @Nullable
    public final Throwable getCompletionExceptionOrNull() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
            return getExceptionOrNull(state$kotlinx_coroutines_core);
        }
        throw new IllegalStateException("This job has not completed yet".toString());
    }

    /* JADX INFO: renamed from: getHandlesException$kotlinx_coroutines_core */
    public boolean getHandlesException() {
        return true;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    @NotNull
    public final CoroutineContext.Key<?> getKey() {
        return Job.INSTANCE;
    }

    public boolean getOnCancelComplete$kotlinx_coroutines_core() {
        return false;
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final SelectClause0 getOnJoin() {
        return this;
    }

    @Nullable
    public final ChildHandle getParentHandle$kotlinx_coroutines_core() {
        return (ChildHandle) this._parentHandle;
    }

    @Nullable
    public final Object getState$kotlinx_coroutines_core() {
        while (true) {
            Object obj = this._state;
            if (!(obj instanceof OpDescriptor)) {
                return obj;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    public boolean handleJobException(@NotNull Throwable th) {
        return false;
    }

    public void handleOnCompletionException$kotlinx_coroutines_core(@NotNull Throwable exception) throws Throwable {
        throw exception;
    }

    public final void initParentJob(@Nullable Job job) {
        if (job == null) {
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
            return;
        }
        job.start();
        ChildHandle childHandleAttachChild = job.attachChild(this);
        setParentHandle$kotlinx_coroutines_core(childHandleAttachChild);
        if (isCompleted()) {
            childHandleAttachChild.dispose();
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final DisposableHandle invokeOnCompletion(@NotNull Function1<? super Throwable, Unit> handler) {
        return invokeOnCompletion(false, true, handler);
    }

    @Override // kotlinx.coroutines.Job
    public boolean isActive() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        return (state$kotlinx_coroutines_core instanceof Incomplete) && ((Incomplete) state$kotlinx_coroutines_core).getIsActive();
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCancelled() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        return (state$kotlinx_coroutines_core instanceof CompletedExceptionally) || ((state$kotlinx_coroutines_core instanceof Finishing) && ((Finishing) state$kotlinx_coroutines_core).isCancelling());
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof Incomplete);
    }

    public final boolean isCompletedExceptionally() {
        return getState$kotlinx_coroutines_core() instanceof CompletedExceptionally;
    }

    public boolean isScopedCoroutine() {
        return this instanceof BlockingCoroutine;
    }

    @Override // kotlinx.coroutines.Job
    @Nullable
    public final Object join(@NotNull Continuation<? super Unit> continuation) {
        if (joinInternal()) {
            Object objJoinSuspend = joinSuspend(continuation);
            return objJoinSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objJoinSuspend : Unit.INSTANCE;
        }
        JobKt.ensureActive(continuation.getContext());
        return Unit.INSTANCE;
    }

    public final boolean makeCompleting$kotlinx_coroutines_core(@Nullable Object proposedUpdate) {
        Object objTryMakeCompleting;
        do {
            objTryMakeCompleting = tryMakeCompleting(getState$kotlinx_coroutines_core(), proposedUpdate);
            if (objTryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                return false;
            }
            if (objTryMakeCompleting == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return true;
            }
        } while (objTryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        afterCompletion(objTryMakeCompleting);
        return true;
    }

    @Nullable
    public final Object makeCompletingOnce$kotlinx_coroutines_core(@Nullable Object proposedUpdate) {
        Object objTryMakeCompleting;
        do {
            objTryMakeCompleting = tryMakeCompleting(getState$kotlinx_coroutines_core(), proposedUpdate);
            if (objTryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                throw new IllegalStateException("Job " + this + " is already complete or completing, but is being completed with " + proposedUpdate, getExceptionOrNull(proposedUpdate));
            }
        } while (objTryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        return objTryMakeCompleting;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext minusKey(@NotNull CoroutineContext.Key<?> key) {
        return Job.DefaultImpls.minusKey(this, key);
    }

    @NotNull
    public String nameString$kotlinx_coroutines_core() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    public void onCancelling(@Nullable Throwable th) {
    }

    public void onCompletionInternal(@Nullable Object obj) {
    }

    public void onStart() {
    }

    @Override // kotlinx.coroutines.ChildJob
    public final void parentCancelled(@NotNull ParentJob parentJob) throws Throwable {
        cancelImpl$kotlinx_coroutines_core(parentJob);
    }

    @Override // kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext coroutineContext) {
        return Job.DefaultImpls.plus(this, coroutineContext);
    }

    @Override // kotlinx.coroutines.selects.SelectClause0
    public final <R> void registerSelectClause0(@NotNull SelectInstance<? super R> select, @NotNull Function1<? super Continuation<? super R>, ? extends Object> block) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (select.isSelected()) {
                return;
            }
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                if (select.trySelect()) {
                    UndispatchedKt.startCoroutineUnintercepted(block, select.getCompletion());
                    return;
                }
                return;
            }
        } while (startInternal(state$kotlinx_coroutines_core) != 0);
        select.disposeOnSelect(invokeOnCompletion(new SelectJoinOnCompletion(select, block)));
    }

    public final <T, R> void registerSelectClause1Internal$kotlinx_coroutines_core(@NotNull SelectInstance<? super R> select, @NotNull Function2<? super T, ? super Continuation<? super R>, ? extends Object> block) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (select.isSelected()) {
                return;
            }
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                if (select.trySelect()) {
                    if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
                        select.resumeSelectWithException(((CompletedExceptionally) state$kotlinx_coroutines_core).cause);
                        return;
                    } else {
                        UndispatchedKt.startCoroutineUnintercepted(block, JobSupportKt.unboxState(state$kotlinx_coroutines_core), select.getCompletion());
                        return;
                    }
                }
                return;
            }
        } while (startInternal(state$kotlinx_coroutines_core) != 0);
        select.disposeOnSelect(invokeOnCompletion(new SelectAwaitOnCompletion(select, block)));
    }

    public final void removeNode$kotlinx_coroutines_core(@NotNull JobNode node) {
        boolean z;
        do {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof JobNode)) {
                if (!(state$kotlinx_coroutines_core instanceof Incomplete) || ((Incomplete) state$kotlinx_coroutines_core).getList() == null) {
                    return;
                }
                node.mo1867remove();
                return;
            }
            if (state$kotlinx_coroutines_core != node) {
                return;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            Empty empty = JobSupportKt.EMPTY_ACTIVE;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(this, state$kotlinx_coroutines_core, empty)) {
                    z = true;
                    break;
                } else if (atomicReferenceFieldUpdater.get(this) != state$kotlinx_coroutines_core) {
                    z = false;
                    break;
                }
            }
        } while (!z);
    }

    public final <T, R> void selectAwaitCompletion$kotlinx_coroutines_core(@NotNull SelectInstance<? super R> select, @NotNull Function2<? super T, ? super Continuation<? super R>, ? extends Object> block) throws Throwable {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            select.resumeSelectWithException(((CompletedExceptionally) state$kotlinx_coroutines_core).cause);
        } else {
            CancellableKt.startCoroutineCancellable$default(block, JobSupportKt.unboxState(state$kotlinx_coroutines_core), select.getCompletion(), null, 4, null);
        }
    }

    public final void setParentHandle$kotlinx_coroutines_core(@Nullable ChildHandle childHandle) {
        this._parentHandle = childHandle;
    }

    @Override // kotlinx.coroutines.Job
    public final boolean start() {
        int iStartInternal;
        do {
            iStartInternal = startInternal(getState$kotlinx_coroutines_core());
            if (iStartInternal == 0) {
                return false;
            }
        } while (iStartInternal != 1);
        return true;
    }

    @NotNull
    public final CancellationException toCancellationException(@NotNull Throwable th, @Nullable String str) {
        CancellationException jobCancellationException = th instanceof CancellationException ? (CancellationException) th : null;
        if (jobCancellationException == null) {
            if (str == null) {
                str = cancellationExceptionMessage();
            }
            jobCancellationException = new JobCancellationException(str, th, this);
        }
        return jobCancellationException;
    }

    @InternalCoroutinesApi
    @NotNull
    public final String toDebugString() {
        return nameString$kotlinx_coroutines_core() + '{' + stateString(getState$kotlinx_coroutines_core()) + '}';
    }

    @NotNull
    public String toString() {
        return toDebugString() + '@' + DebugStringsKt.getHexAddress(this);
    }

    @Override // kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Added since 1.2.0 for binary compatibility with versions <= 1.1.x")
    public /* synthetic */ boolean cancel(Throwable cause) throws Throwable {
        CancellationException cancellationException$default = cause == null ? null : toCancellationException$default(this, cause, null, 1, null);
        if (cancellationException$default == null) {
            cancellationException$default = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cancellationException$default);
        return true;
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final DisposableHandle invokeOnCompletion(boolean onCancelling, boolean invokeImmediately, @NotNull Function1<? super Throwable, Unit> handler) {
        boolean z;
        JobNode jobNodeMakeNode = makeNode(handler, onCancelling);
        while (true) {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (state$kotlinx_coroutines_core instanceof Empty) {
                Empty empty = (Empty) state$kotlinx_coroutines_core;
                if (empty.getIsActive()) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
                    while (true) {
                        if (atomicReferenceFieldUpdater.compareAndSet(this, state$kotlinx_coroutines_core, jobNodeMakeNode)) {
                            z = true;
                            break;
                        }
                        if (atomicReferenceFieldUpdater.get(this) != state$kotlinx_coroutines_core) {
                            z = false;
                            break;
                        }
                    }
                    if (z) {
                        return jobNodeMakeNode;
                    }
                } else {
                    promoteEmptyToNodeList(empty);
                }
            } else {
                Throwable rootCause = null;
                if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                    if (invokeImmediately) {
                        CompletedExceptionally completedExceptionally = state$kotlinx_coroutines_core instanceof CompletedExceptionally ? (CompletedExceptionally) state$kotlinx_coroutines_core : null;
                        handler.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
                    }
                    return NonDisposableHandle.INSTANCE;
                }
                NodeList list = ((Incomplete) state$kotlinx_coroutines_core).getList();
                if (list == null) {
                    Objects.requireNonNull(state$kotlinx_coroutines_core, "null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                    promoteSingleToNodeList((JobNode) state$kotlinx_coroutines_core);
                } else {
                    DisposableHandle disposableHandle = NonDisposableHandle.INSTANCE;
                    if (onCancelling && (state$kotlinx_coroutines_core instanceof Finishing)) {
                        synchronized (state$kotlinx_coroutines_core) {
                            rootCause = ((Finishing) state$kotlinx_coroutines_core).getRootCause();
                            if (rootCause == null || ((handler instanceof ChildHandleNode) && !((Finishing) state$kotlinx_coroutines_core).isCompleting())) {
                                if (addLastAtomic(state$kotlinx_coroutines_core, list, jobNodeMakeNode)) {
                                    if (rootCause == null) {
                                        return jobNodeMakeNode;
                                    }
                                    disposableHandle = jobNodeMakeNode;
                                }
                            }
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                    if (rootCause != null) {
                        if (invokeImmediately) {
                            handler.invoke(rootCause);
                        }
                        return disposableHandle;
                    }
                    if (addLastAtomic(state$kotlinx_coroutines_core, list, jobNodeMakeNode)) {
                        return jobNodeMakeNode;
                    }
                }
            }
        }
    }

    @Override // kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    @NotNull
    public Job plus(@NotNull Job job) {
        return Job.DefaultImpls.plus((Job) this, job);
    }

    @Override // kotlinx.coroutines.Job
    public void cancel(@Nullable CancellationException cause) throws Throwable {
        if (cause == null) {
            cause = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cause);
    }
}
