package kotlinx.coroutines.channels;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aJ\u0010\u0000\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\u00072\u001a\u0010\b\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\n0\t\"\u0006\u0012\u0002\b\u00030\nH\u0001¢\u0006\u0002\u0010\u000b\u001a!\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a1\u0010\u0010\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\u0007*\u0006\u0012\u0002\b\u00030\nH\u0001\u001a!\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0007\u001aZ\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010\u0018\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a0\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001aT\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u0010!\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\"\u001a\u00020\u0012H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010#\u001a+\u0010$\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\"\u001a\u00020\u0012H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010#\u001aT\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ai\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001727\u0010 \u001a3\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u001aT\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a$\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\b\b\u0000\u0010\u000e*\u00020\u001b*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\nH\u0001\u001aA\u0010+\u001a\u0002H,\"\b\b\u0000\u0010\u000e*\u00020\u001b\"\u0010\b\u0001\u0010,*\n\u0012\u0006\b\u0000\u0012\u0002H\u000e0-*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010/\u001a?\u0010+\u001a\u0002H,\"\b\b\u0000\u0010\u000e*\u00020\u001b\"\u000e\b\u0001\u0010,*\b\u0012\u0004\u0012\u0002H\u000e00*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0087@ø\u0001\u0000¢\u0006\u0002\u00101\u001a!\u00102\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a#\u00103\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a`\u00104\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172(\u00106\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\n0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u00107\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u00108\u001a\u0002H\u000eH\u0087@ø\u0001\u0000¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a)\u0010;\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u00108\u001a\u0002H\u000eH\u0087@ø\u0001\u0000¢\u0006\u0002\u00109\u001a#\u0010<\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001aZ\u0010=\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u00106\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ao\u0010>\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001727\u00106\u001a3\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0001ø\u0001\u0000¢\u0006\u0002\u0010(\u001au\u0010?\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\b\b\u0001\u00105*\u00020\u001b*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001729\u00106\u001a5\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u001a`\u0010@\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\b\b\u0001\u00105*\u00020\u001b*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172$\u00106\u001a \b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a?\u0010A\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u001a\u0010B\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0Cj\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`DH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010E\u001a?\u0010F\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u001a\u0010B\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0Cj\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`DH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010E\u001a!\u0010G\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a$\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\b\b\u0000\u0010\u000e*\u00020\u001b*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\nH\u0007\u001a!\u0010I\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a#\u0010J\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a0\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001aT\u0010L\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a9\u0010M\u001a\u0002H,\"\u0004\b\u0000\u0010\u000e\"\u000e\b\u0001\u0010,*\b\u0012\u0004\u0012\u0002H\u000e00*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0081@ø\u0001\u0000¢\u0006\u0002\u00101\u001a;\u0010N\u001a\u0002H,\"\u0004\b\u0000\u0010\u000e\"\u0010\b\u0001\u0010,*\n\u0012\u0006\b\u0000\u0012\u0002H\u000e0-*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0081@ø\u0001\u0000¢\u0006\u0002\u0010/\u001a?\u0010O\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0P\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010Q*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0R0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001aU\u0010O\u001a\u0002HS\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010Q\"\u0018\b\u0002\u0010S*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0015\u0012\u0006\b\u0000\u0012\u0002HQ0T*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0R0\n2\u0006\u0010.\u001a\u0002HSH\u0081@ø\u0001\u0000¢\u0006\u0002\u0010U\u001a'\u0010V\u001a\b\u0012\u0004\u0012\u0002H\u000e0W\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a'\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u000e0Y\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0081@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a'\u0010Z\u001a\b\u0012\u0004\u0012\u0002H\u000e0[\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a.\u0010\\\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0]0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a?\u0010^\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H50R0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u0002H50\nH\u0087\u0004\u001az\u0010^\u001a\b\u0012\u0004\u0012\u0002HQ0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105\"\u0004\b\u0002\u0010Q*\b\u0012\u0004\u0012\u0002H\u000e0\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u0002H50\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001726\u00106\u001a2\u0012\u0013\u0012\u0011H\u000e¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(`\u0012\u0013\u0012\u0011H5¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(a\u0012\u0004\u0012\u0002HQ0\u0019H\u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006b"}, d2 = {"consumesAll", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "channels", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "([Lkotlinx/coroutines/channels/ReceiveChannel;)Lkotlin/jvm/functions/Function1;", "any", "", ExifInterface.LONGITUDE_EAST, "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "consumes", "count", "", "distinct", "distinctBy", "K", "context", "Lkotlin/coroutines/CoroutineContext;", "selector", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "drop", "n", "dropWhile", "predicate", "elementAt", "index", "(Lkotlinx/coroutines/channels/ReceiveChannel;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "elementAtOrNull", "filter", "filterIndexed", "Lkotlin/Function3;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/channels/ReceiveChannel;", "filterNot", "filterNotNull", "filterNotNullTo", "C", "", "destination", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "first", "firstOrNull", "flatMap", "R", "transform", "indexOf", "element", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "last", "lastIndexOf", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "mapNotNull", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Comparator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "minWith", "none", "requireNoNulls", "single", "singleOrNull", "take", "takeWhile", "toChannel", "toCollection", "toMap", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/Pair;", "M", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toMutableList", "", "toMutableSet", "", "toSet", "", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
final /* synthetic */ class ChannelsKt__DeprecatedKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1, reason: invalid class name */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0}, l = {404}, m = "any", n = {"$this$consume$iv"}, s = {"L$0"})
    public static final class AnonymousClass1<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
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
            return ChannelsKt__DeprecatedKt.any(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {487}, m = "count", n = {"count", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    public static final class C01221<E> extends ContinuationImpl {
        public Ref.IntRef L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01221(Continuation<? super C01221> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.count(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinct$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0004\n\u0002\b\u0003\u0010\u0002\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u00002\u0006\u0010\u0001\u001a\u00028\u0000H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "it", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinct$1", f = "Deprecated.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    public static final class C01231 extends SuspendLambda implements Function2 {
        public /* synthetic */ Object L$0;

        public C01231(Continuation<? super C01231> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01231 c01231 = new C01231(continuation);
            c01231.L$0 = obj;
            return c01231;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(Object obj, @Nullable Continuation continuation) {
            return ((C01231) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ResultKt.throwOnFailure(obj);
            return this.L$0;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [E] */
    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinctBy$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u0002H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "K", "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinctBy$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 1, 2, 2, 2}, l = {387, 388, 390}, m = "invokeSuspend", n = {"$this$produce", "keys", "$this$produce", "keys", "e", "$this$produce", "keys", "k"}, s = {"L$0", "L$1", "L$0", "L$1", "L$3", "L$0", "L$1", "L$3"})
    public static final class C01241<E> extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function2<E, Continuation<? super K>, Object> $selector;
        public final /* synthetic */ ReceiveChannel<E> $this_distinctBy;
        private /* synthetic */ Object L$0;
        public HashSet L$1;
        public ChannelIterator L$2;
        public Object L$3;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01241(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super K>, ? extends Object> function2, Continuation<? super C01241> continuation) {
            super(2, continuation);
            this.$this_distinctBy = receiveChannel;
            this.$selector = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01241 c01241 = new C01241(this.$this_distinctBy, this.$selector, continuation);
            c01241.L$0 = obj;
            return c01241;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C01241) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:15:0x0076 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:16:0x0077  */
        /* JADX WARN: Code duplicated, block: B:19:0x0086  */
        /* JADX WARN: Code duplicated, block: B:21:0x009c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:22:0x009d  */
        /* JADX WARN: Code duplicated, block: B:25:0x00a8  */
        /* JADX WARN: Code duplicated, block: B:27:0x00b8 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:28:0x00b9  */
        /* JADX WARN: Code duplicated, block: B:30:0x00c6  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v2 */
        /* JADX WARN: Type inference failed for: r5v2 */
        /* JADX WARN: Type inference failed for: r5v4 */
        /* JADX WARN: Type inference failed for: r5v6, types: [java.util.HashSet] */
        /* JADX WARN: Type inference failed for: r5v9 */
        /* JADX WARN: Type inference failed for: r6v0 */
        /* JADX WARN: Type inference failed for: r6v10 */
        /* JADX WARN: Type inference failed for: r6v13 */
        /* JADX WARN: Type inference failed for: r6v17 */
        /* JADX WARN: Type inference failed for: r6v2 */
        /* JADX WARN: Type inference failed for: r6v4 */
        /* JADX WARN: Type inference failed for: r6v5, types: [java.util.HashSet] */
        /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r6v8 */
        /* JADX WARN: Type inference failed for: r6v9, types: [java.util.Collection] */
        /* JADX WARN: Type inference failed for: r7v0, types: [java.util.HashSet] */
        /* JADX WARN: Type inference failed for: r7v1 */
        /* JADX WARN: Type inference failed for: r7v11 */
        /* JADX WARN: Type inference failed for: r7v14 */
        /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r7v3 */
        /* JADX WARN: Type inference failed for: r7v6 */
        /* JADX WARN: Type inference failed for: r7v7 */
        /* JADX WARN: Type inference failed for: r7v8 */
        /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.Object, kotlinx.coroutines.channels.SendChannel] */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v4 */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x00b9 -> B:29:0x00bf). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x00c6 -> B:13:0x0065). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r12) {
            /*
                Method dump skipped, instruction units count: 207
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01241.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$drop$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$drop$1", f = "Deprecated.kt", i = {0, 0, 1, 2}, l = {164, 169, 170}, m = "invokeSuspend", n = {"$this$produce", "remaining", "$this$produce", "$this$produce"}, s = {"L$0", "I$0", "L$0", "L$0"})
    public static final class C01251 extends SuspendLambda implements Function2 {
        public final /* synthetic */ int $n;
        public final /* synthetic */ ReceiveChannel $this_drop;
        public int I$0;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01251(int i, ReceiveChannel receiveChannel, Continuation continuation) {
            super(2, continuation);
            this.$n = i;
            this.$this_drop = receiveChannel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01251 c01251 = new C01251(this.$n, this.$this_drop, continuation);
            c01251.L$0 = obj;
            return c01251;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01251) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0073 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:22:0x0074  */
        /* JADX WARN: Code duplicated, block: B:25:0x0086  */
        /* JADX WARN: Code duplicated, block: B:28:0x008e  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0074 -> B:23:0x007e). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x00d2 -> B:32:0x00a5). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                Method dump skipped, instruction units count: 237
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01251.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$dropWhile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$dropWhile$1", f = "Deprecated.kt", i = {0, 1, 1, 2, 3, 4}, l = {181, 182, 183, 187, TsExtractor.TS_PACKET_SIZE}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e", "$this$produce", "$this$produce", "$this$produce"}, s = {"L$0", "L$0", "L$2", "L$0", "L$0", "L$0"})
    public static final class C01261 extends SuspendLambda implements Function2 {
        public final /* synthetic */ Function2 $predicate;
        public final /* synthetic */ ReceiveChannel $this_dropWhile;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public Object L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01261(ReceiveChannel receiveChannel, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_dropWhile = receiveChannel;
            this.$predicate = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01261 c01261 = new C01261(this.$this_dropWhile, this.$predicate, continuation);
            c01261.L$0 = obj;
            return c01261;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01261) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x0083 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:20:0x0084  */
        /* JADX WARN: Code duplicated, block: B:23:0x0092  */
        /* JADX WARN: Code duplicated, block: B:25:0x00a6 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:26:0x00a7  */
        /* JADX WARN: Code duplicated, block: B:29:0x00b3  */
        /* JADX WARN: Code duplicated, block: B:31:0x00c1 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:33:0x00c4  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00a7 -> B:27:0x00ab). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00fb -> B:36:0x00d0). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) {
            /*
                Method dump skipped, instruction units count: 258
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01261.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0}, l = {38}, m = "elementAt", n = {"$this$consume$iv", "index", "count"}, s = {"L$0", "I$0", "I$1"})
    public static final class C01271<E> extends ContinuationImpl {
        public int I$0;
        public int I$1;
        public ReceiveChannel L$0;
        public ChannelIterator L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01271(Continuation<? super C01271> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.elementAt(null, 0, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0}, l = {53}, m = "elementAtOrNull", n = {"$this$consume$iv", "index", "count"}, s = {"L$0", "I$0", "I$1"})
    public static final class C01281<E> extends ContinuationImpl {
        public int I$0;
        public int I$1;
        public ReceiveChannel L$0;
        public ChannelIterator L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01281(Continuation<? super C01281> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.elementAtOrNull(null, 0, this);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [E] */
    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1", f = "Deprecated.kt", i = {0, 1, 1, 2}, l = {198, 199, 199}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e", "$this$produce"}, s = {"L$0", "L$0", "L$2", "L$0"})
    public static final class C01291<E> extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
        public final /* synthetic */ ReceiveChannel<E> $this_filter;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public Object L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01291(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super C01291> continuation) {
            super(2, continuation);
            this.$this_filter = receiveChannel;
            this.$predicate = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01291 c01291 = new C01291(this.$this_filter, this.$predicate, continuation);
            c01291.L$0 = obj;
            return c01291;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C01291) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x005f A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:17:0x0060  */
        /* JADX WARN: Code duplicated, block: B:20:0x006e  */
        /* JADX WARN: Code duplicated, block: B:22:0x0082 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:23:0x0083  */
        /* JADX WARN: Code duplicated, block: B:26:0x008f  */
        /* JADX WARN: Code duplicated, block: B:28:0x009d A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:29:0x009e  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v15 */
        /* JADX WARN: Type inference failed for: r6v16 */
        /* JADX WARN: Type inference failed for: r6v3 */
        /* JADX WARN: Type inference failed for: r6v4 */
        /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r6v8 */
        /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r7v4 */
        /* JADX WARN: Type inference failed for: r7v5 */
        /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.Object, kotlinx.coroutines.channels.SendChannel] */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v4 */
        /* JADX WARN: Type inference failed for: r9v0 */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r10.label
                r2 = 0
                r3 = 1
                r4 = 2
                r5 = 3
                if (r1 == 0) goto L42
                if (r1 == r3) goto L34
                if (r1 == r4) goto L24
                if (r1 != r5) goto L1c
                kotlinx.coroutines.channels.ChannelIterator r1 = r10.L$1
                java.lang.Object r6 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r11)
                goto L50
            L1c:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r0)
                throw r11
            L24:
                java.lang.Object r1 = r10.L$2
                kotlinx.coroutines.channels.ChannelIterator r6 = r10.L$1
                java.lang.Object r7 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                kotlin.ResultKt.throwOnFailure(r11)
                r8 = r7
                r7 = r1
                r1 = r0
                r0 = r10
                goto L87
            L34:
                kotlinx.coroutines.channels.ChannelIterator r1 = r10.L$1
                java.lang.Object r6 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r11)
                r7 = r6
                r6 = r1
                r1 = r0
                r0 = r10
                goto L66
            L42:
                kotlin.ResultKt.throwOnFailure(r11)
                java.lang.Object r11 = r10.L$0
                r6 = r11
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlinx.coroutines.channels.ReceiveChannel<E> r11 = r10.$this_filter
                kotlinx.coroutines.channels.ChannelIterator r1 = r11.iterator()
            L50:
                r11 = r10
            L51:
                r11.L$0 = r6
                r11.L$1 = r1
                r11.L$2 = r2
                r11.label = r3
                java.lang.Object r7 = r1.hasNext(r11)
                if (r7 != r0) goto L60
                return r0
            L60:
                r9 = r0
                r0 = r11
                r11 = r7
                r7 = r6
                r6 = r1
                r1 = r9
            L66:
                java.lang.Boolean r11 = (java.lang.Boolean) r11
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto La3
                java.lang.Object r11 = r6.next()
                kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r8 = r0.$predicate
                r0.L$0 = r7
                r0.L$1 = r6
                r0.L$2 = r11
                r0.label = r4
                java.lang.Object r8 = r8.invoke(r11, r0)
                if (r8 != r1) goto L83
                return r1
            L83:
                r9 = r7
                r7 = r11
                r11 = r8
                r8 = r9
            L87:
                java.lang.Boolean r11 = (java.lang.Boolean) r11
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto L9e
                r0.L$0 = r8
                r0.L$1 = r6
                r0.L$2 = r2
                r0.label = r5
                java.lang.Object r11 = r8.send(r7, r0)
                if (r11 != r1) goto L9e
                return r1
            L9e:
                r11 = r0
                r0 = r1
                r1 = r6
                r6 = r8
                goto L51
            La3:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01291.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 1, 2, 2}, l = {211, 212, 212}, m = "invokeSuspend", n = {"$this$produce", "index", "$this$produce", "e", "index", "$this$produce", "index"}, s = {"L$0", "I$0", "L$0", "L$2", "I$0", "L$0", "I$0"})
    public static final class C01301 extends SuspendLambda implements Function2 {
        public final /* synthetic */ Function3 $predicate;
        public final /* synthetic */ ReceiveChannel $this_filterIndexed;
        public int I$0;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public Object L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01301(ReceiveChannel receiveChannel, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$this_filterIndexed = receiveChannel;
            this.$predicate = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01301 c01301 = new C01301(this.$this_filterIndexed, this.$predicate, continuation);
            c01301.L$0 = obj;
            return c01301;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01301) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:17:0x006c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:18:0x006d  */
        /* JADX WARN: Code duplicated, block: B:21:0x007c  */
        /* JADX WARN: Code duplicated, block: B:23:0x0098 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:24:0x0099  */
        /* JADX WARN: Code duplicated, block: B:27:0x00a5  */
        /* JADX WARN: Code duplicated, block: B:29:0x00b5 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:30:0x00b6  */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) {
            /*
                r12 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r12.label
                r2 = 0
                r3 = 2
                r4 = 3
                r5 = 1
                if (r1 == 0) goto L4c
                if (r1 == r5) goto L3b
                if (r1 == r3) goto L26
                if (r1 != r4) goto L1e
                int r1 = r12.I$0
                kotlinx.coroutines.channels.ChannelIterator r6 = r12.L$1
                java.lang.Object r7 = r12.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                kotlin.ResultKt.throwOnFailure(r13)
                goto L5b
            L1e:
                java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r13.<init>(r0)
                throw r13
            L26:
                int r1 = r12.I$0
                java.lang.Object r6 = r12.L$2
                kotlinx.coroutines.channels.ChannelIterator r7 = r12.L$1
                java.lang.Object r8 = r12.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                kotlin.ResultKt.throwOnFailure(r13)
                r10 = r1
                r1 = r0
                r0 = r12
                r11 = r7
                r7 = r6
            L38:
                r6 = r11
                goto L9d
            L3b:
                int r1 = r12.I$0
                kotlinx.coroutines.channels.ChannelIterator r6 = r12.L$1
                java.lang.Object r7 = r12.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                kotlin.ResultKt.throwOnFailure(r13)
                r8 = r7
                r7 = r6
                r6 = r1
                r1 = r0
                r0 = r12
                goto L74
            L4c:
                kotlin.ResultKt.throwOnFailure(r13)
                java.lang.Object r13 = r12.L$0
                r7 = r13
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                r1 = 0
                kotlinx.coroutines.channels.ReceiveChannel r13 = r12.$this_filterIndexed
                kotlinx.coroutines.channels.ChannelIterator r6 = r13.iterator()
            L5b:
                r13 = r12
            L5c:
                r13.L$0 = r7
                r13.L$1 = r6
                r13.L$2 = r2
                r13.I$0 = r1
                r13.label = r5
                java.lang.Object r8 = r6.hasNext(r13)
                if (r8 != r0) goto L6d
                return r0
            L6d:
                r11 = r0
                r0 = r13
                r13 = r8
                r8 = r7
                r7 = r6
                r6 = r1
                r1 = r11
            L74:
                java.lang.Boolean r13 = (java.lang.Boolean) r13
                boolean r13 = r13.booleanValue()
                if (r13 == 0) goto Lbb
                java.lang.Object r13 = r7.next()
                kotlin.jvm.functions.Function3 r9 = r0.$predicate
                int r10 = r6 + 1
                java.lang.Integer r6 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r6)
                r0.L$0 = r8
                r0.L$1 = r7
                r0.L$2 = r13
                r0.I$0 = r10
                r0.label = r3
                java.lang.Object r6 = r9.invoke(r6, r13, r0)
                if (r6 != r1) goto L99
                return r1
            L99:
                r11 = r7
                r7 = r13
                r13 = r6
                goto L38
            L9d:
                java.lang.Boolean r13 = (java.lang.Boolean) r13
                boolean r13 = r13.booleanValue()
                if (r13 == 0) goto Lb6
                r0.L$0 = r8
                r0.L$1 = r6
                r0.L$2 = r2
                r0.I$0 = r10
                r0.label = r4
                java.lang.Object r13 = r8.send(r7, r0)
                if (r13 != r1) goto Lb6
                return r1
            Lb6:
                r13 = r0
                r0 = r1
                r7 = r8
                r1 = r10
                goto L5c
            Lbb:
                kotlin.Unit r13 = kotlin.Unit.INSTANCE
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01301.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNot$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\n\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u00002\u0006\u0010\u0001\u001a\u00028\u0000H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "it", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNot$1", f = "Deprecated.kt", i = {}, l = {222}, m = "invokeSuspend", n = {}, s = {})
    public static final class C01311 extends SuspendLambda implements Function2 {
        public final /* synthetic */ Function2 $predicate;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01311(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$predicate = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01311 c01311 = new C01311(this.$predicate, continuation);
            c01311.L$0 = obj;
            return c01311;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(Object obj, @Nullable Continuation continuation) {
            return ((C01311) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Object obj2 = this.L$0;
                Function2 function2 = this.$predicate;
                this.label = 1;
                obj = function2.invoke(obj2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Boxing.boxBoolean(!((Boolean) obj).booleanValue());
        }
    }

    /* JADX INFO: Add missing generic type declarations: [E] */
    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0010\u0004\u001a\u00020\u0003\"\b\b\u0000\u0010\u0001*\u00020\u00002\b\u0010\u0002\u001a\u0004\u0018\u00018\u0000H\u008a@"}, d2 = {"", ExifInterface.LONGITUDE_EAST, "it", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNull$1", f = "Deprecated.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    public static final class C01321<E> extends SuspendLambda implements Function2<E, Continuation<? super Boolean>, Object> {
        public /* synthetic */ Object L$0;

        public C01321(Continuation<? super C01321> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01321 c01321 = new C01321(continuation);
            c01321.L$0 = obj;
            return c01321;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@Nullable E e, @Nullable Continuation<? super Boolean> continuation) {
            return ((C01321) create(e, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(this.L$0 != null);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {487}, m = "filterNotNullTo", n = {"destination", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    public static final class C01331<E, C extends Collection<? super E>> extends ContinuationImpl {
        public Collection L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01331(Continuation<? super C01331> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.filterNotNullTo((ReceiveChannel) null, (Collection) null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3, reason: invalid class name */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {487, 242}, m = "filterNotNullTo", n = {"destination", "$this$consume$iv$iv", "destination", "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$0", "L$1"})
    public static final class AnonymousClass3<E, C extends SendChannel<? super E>> extends ContinuationImpl {
        public SendChannel L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.filterNotNullTo((ReceiveChannel) null, (SendChannel) null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {65}, m = "first", n = {"$this$consume$iv", "iterator"}, s = {"L$0", "L$1"})
    public static final class C01341<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public ChannelIterator L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01341(Continuation<? super C01341> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.first(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {75}, m = "firstOrNull", n = {"$this$consume$iv", "iterator"}, s = {"L$0", "L$1"})
    public static final class C01351<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public ChannelIterator L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01351(Continuation<? super C01351> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.firstOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u0002H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "R", "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1", f = "Deprecated.kt", i = {0, 1, 2}, l = {321, 322, 322}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "$this$produce"}, s = {"L$0", "L$0", "L$0"})
    public static final class C01361 extends SuspendLambda implements Function2 {
        public final /* synthetic */ ReceiveChannel $this_flatMap;
        public final /* synthetic */ Function2 $transform;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01361(ReceiveChannel receiveChannel, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_flatMap = receiveChannel;
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01361 c01361 = new C01361(this.$this_flatMap, this.$transform, continuation);
            c01361.L$0 = obj;
            return c01361;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01361) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x005a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:17:0x005b  */
        /* JADX WARN: Code duplicated, block: B:20:0x0069  */
        /* JADX WARN: Code duplicated, block: B:22:0x007b A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:25:0x008a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:26:0x008b  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x008b -> B:14:0x004e). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r9.label
                r2 = 1
                r3 = 2
                r4 = 3
                if (r1 == 0) goto L3f
                if (r1 == r2) goto L31
                if (r1 == r3) goto L23
                if (r1 != r4) goto L1b
                kotlinx.coroutines.channels.ChannelIterator r1 = r9.L$1
                java.lang.Object r5 = r9.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                goto L4d
            L1b:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L23:
                kotlinx.coroutines.channels.ChannelIterator r1 = r9.L$1
                java.lang.Object r5 = r9.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                r6 = r5
                r5 = r1
                r1 = r0
                r0 = r9
                goto L7c
            L31:
                kotlinx.coroutines.channels.ChannelIterator r1 = r9.L$1
                java.lang.Object r5 = r9.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                r6 = r5
                r5 = r1
                r1 = r0
                r0 = r9
                goto L61
            L3f:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                r5 = r10
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlinx.coroutines.channels.ReceiveChannel r10 = r9.$this_flatMap
                kotlinx.coroutines.channels.ChannelIterator r1 = r10.iterator()
            L4d:
                r10 = r9
            L4e:
                r10.L$0 = r5
                r10.L$1 = r1
                r10.label = r2
                java.lang.Object r6 = r1.hasNext(r10)
                if (r6 != r0) goto L5b
                return r0
            L5b:
                r8 = r0
                r0 = r10
                r10 = r6
                r6 = r5
                r5 = r1
                r1 = r8
            L61:
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 == 0) goto L90
                java.lang.Object r10 = r5.next()
                kotlin.jvm.functions.Function2 r7 = r0.$transform
                r0.L$0 = r6
                r0.L$1 = r5
                r0.label = r3
                java.lang.Object r10 = r7.invoke(r10, r0)
                if (r10 != r1) goto L7c
                return r1
            L7c:
                kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
                r0.L$0 = r6
                r0.L$1 = r5
                r0.label = r4
                java.lang.Object r10 = kotlinx.coroutines.channels.ChannelsKt.toChannel(r10, r6, r0)
                if (r10 != r1) goto L8b
                return r1
            L8b:
                r10 = r0
                r0 = r1
                r1 = r5
                r5 = r6
                goto L4e
            L90:
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01361.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0}, l = {487}, m = "indexOf", n = {"element", "index", "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$2"})
    public static final class C01371<E> extends ContinuationImpl {
        public Object L$0;
        public Ref.IntRef L$1;
        public ReceiveChannel L$2;
        public ChannelIterator L$3;
        public int label;
        public /* synthetic */ Object result;

        public C01371(Continuation<? super C01371> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.indexOf(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1, 1}, l = {97, 100}, m = "last", n = {"$this$consume$iv", "iterator", "$this$consume$iv", "iterator", "last"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2"})
    public static final class C01381<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public ChannelIterator L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01381(Continuation<? super C01381> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.last(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0, 0}, l = {487}, m = "lastIndexOf", n = {"element", "lastIndex", "index", "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$2", "L$3"})
    public static final class C01391<E> extends ContinuationImpl {
        public Object L$0;
        public Ref.IntRef L$1;
        public Ref.IntRef L$2;
        public ReceiveChannel L$3;
        public ChannelIterator L$4;
        public int label;
        public /* synthetic */ Object result;

        public C01391(Continuation<? super C01391> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.lastIndexOf(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1, 1}, l = {123, 126}, m = "lastOrNull", n = {"$this$consume$iv", "iterator", "$this$consume$iv", "iterator", "last"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2"})
    public static final class C01401<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public ChannelIterator L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01401(Continuation<? super C01401> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.lastOrNull(null, this);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u0002H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "R", "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 2, 2}, l = {487, 333, 333}, m = "invokeSuspend", n = {"$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv"}, s = {"L$0", "L$2", "L$0", "L$2", "L$0", "L$2"})
    public static final class C01411<R> extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ReceiveChannel<E> $this_map;
        public final /* synthetic */ Function2<E, Continuation<? super R>, Object> $transform;
        private /* synthetic */ Object L$0;
        public Function2 L$1;
        public ReceiveChannel L$2;
        public ChannelIterator L$3;
        public ProducerScope L$4;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01411(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super C01411> continuation) {
            super(2, continuation);
            this.$this_map = receiveChannel;
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01411 c01411 = new C01411(this.$this_map, this.$transform, continuation);
            c01411.L$0 = obj;
            return c01411;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super R> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C01411) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:23:0x0073 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:24:0x0074  */
        /* JADX WARN: Code duplicated, block: B:27:0x0083 A[Catch: all -> 0x00bf, TRY_LEAVE, TryCatch #3 {all -> 0x00bf, blocks: (B:25:0x007b, B:27:0x0083, B:37:0x00b9), top: B:54:0x007b }] */
        /* JADX WARN: Code duplicated, block: B:29:0x0099 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:30:0x009a  */
        /* JADX WARN: Code duplicated, block: B:33:0x00b0 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:34:0x00b1  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x00b1 -> B:21:0x0063). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) {
            /*
                Method dump skipped, instruction units count: 202
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01411.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$mapIndexed$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u0002H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "R", "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$mapIndexed$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 2, 2}, l = {344, 345, 345}, m = "invokeSuspend", n = {"$this$produce", "index", "$this$produce", "index", "$this$produce", "index"}, s = {"L$0", "I$0", "L$0", "I$0", "L$0", "I$0"})
    public static final class C01421<R> extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ReceiveChannel<E> $this_mapIndexed;
        public final /* synthetic */ Function3<Integer, E, Continuation<? super R>, Object> $transform;
        public int I$0;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public ProducerScope L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01421(ReceiveChannel<? extends E> receiveChannel, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super C01421> continuation) {
            super(2, continuation);
            this.$this_mapIndexed = receiveChannel;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01421 c01421 = new C01421(this.$this_mapIndexed, this.$transform, continuation);
            c01421.L$0 = obj;
            return c01421;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super R> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C01421) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0069 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:17:0x006a  */
        /* JADX WARN: Code duplicated, block: B:20:0x0078  */
        /* JADX WARN: Code duplicated, block: B:22:0x0094 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:23:0x0095  */
        /* JADX WARN: Code duplicated, block: B:26:0x00ab A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:27:0x00ac  */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x00ac -> B:14:0x005b). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r12) {
            /*
                r11 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r11.label
                r2 = 2
                r3 = 3
                r4 = 1
                if (r1 == 0) goto L4b
                if (r1 == r4) goto L3b
                if (r1 == r2) goto L25
                if (r1 != r3) goto L1d
                int r1 = r11.I$0
                kotlinx.coroutines.channels.ChannelIterator r5 = r11.L$1
                java.lang.Object r6 = r11.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r12)
                goto L5a
            L1d:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r12.<init>(r0)
                throw r12
            L25:
                int r1 = r11.I$0
                kotlinx.coroutines.channels.ProducerScope r5 = r11.L$2
                kotlinx.coroutines.channels.ChannelIterator r6 = r11.L$1
                java.lang.Object r7 = r11.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                kotlin.ResultKt.throwOnFailure(r12)
                r9 = r1
                r1 = r0
                r0 = r12
                r12 = r11
                r10 = r6
                r6 = r5
                r5 = r10
                goto L9a
            L3b:
                int r1 = r11.I$0
                kotlinx.coroutines.channels.ChannelIterator r5 = r11.L$1
                java.lang.Object r6 = r11.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r12)
                r7 = r5
                r5 = r1
                r1 = r0
                r0 = r11
                goto L70
            L4b:
                kotlin.ResultKt.throwOnFailure(r12)
                java.lang.Object r12 = r11.L$0
                r6 = r12
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                r1 = 0
                kotlinx.coroutines.channels.ReceiveChannel<E> r12 = r11.$this_mapIndexed
                kotlinx.coroutines.channels.ChannelIterator r5 = r12.iterator()
            L5a:
                r12 = r11
            L5b:
                r12.L$0 = r6
                r12.L$1 = r5
                r12.I$0 = r1
                r12.label = r4
                java.lang.Object r7 = r5.hasNext(r12)
                if (r7 != r0) goto L6a
                return r0
            L6a:
                r10 = r0
                r0 = r12
                r12 = r7
                r7 = r5
                r5 = r1
                r1 = r10
            L70:
                java.lang.Boolean r12 = (java.lang.Boolean) r12
                boolean r12 = r12.booleanValue()
                if (r12 == 0) goto Lb0
                java.lang.Object r12 = r7.next()
                kotlin.jvm.functions.Function3<java.lang.Integer, E, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r8 = r0.$transform
                int r9 = r5 + 1
                java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r5)
                r0.L$0 = r6
                r0.L$1 = r7
                r0.L$2 = r6
                r0.I$0 = r9
                r0.label = r2
                java.lang.Object r12 = r8.invoke(r5, r12, r0)
                if (r12 != r1) goto L95
                return r1
            L95:
                r5 = r7
                r7 = r6
                r10 = r0
                r0 = r12
                r12 = r10
            L9a:
                r12.L$0 = r7
                r12.L$1 = r5
                r8 = 0
                r12.L$2 = r8
                r12.I$0 = r9
                r12.label = r3
                java.lang.Object r0 = r6.send(r0, r12)
                if (r0 != r1) goto Lac
                return r1
            Lac:
                r0 = r1
                r6 = r7
                r1 = r9
                goto L5b
            Lb0:
                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01421.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0, 1, 1, 1, 1}, l = {TypedValues.CycleType.TYPE_EASING, TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE}, m = "maxWith", n = {"comparator", "$this$consume$iv", "iterator", "comparator", "$this$consume$iv", "iterator", "max"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3"})
    public static final class C01431<E> extends ContinuationImpl {
        public Comparator L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public Object L$3;
        public int label;
        public /* synthetic */ Object result;

        public C01431(Continuation<? super C01431> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.maxWith(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0, 1, 1, 1, 1}, l = {434, 436}, m = "minWith", n = {"comparator", "$this$consume$iv", "iterator", "comparator", "$this$consume$iv", "iterator", "min"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3"})
    public static final class C01441<E> extends ContinuationImpl {
        public Comparator L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public Object L$3;
        public int label;
        public /* synthetic */ Object result;

        public C01441(Continuation<? super C01441> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.minWith(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0}, l = {447}, m = "none", n = {"$this$consume$iv"}, s = {"L$0"})
    public static final class C01451<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public int label;
        public /* synthetic */ Object result;

        public C01451(Continuation<? super C01451> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.none(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$requireNoNulls$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\b\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\u0003\u001a\u00028\u0000\"\b\b\u0000\u0010\u0001*\u00020\u00002\b\u0010\u0002\u001a\u0004\u0018\u00018\u0000H\u008a@"}, d2 = {"", ExifInterface.LONGITUDE_EAST, "it", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$requireNoNulls$1", f = "Deprecated.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    public static final class C01461 extends SuspendLambda implements Function2 {
        public final /* synthetic */ ReceiveChannel $this_requireNoNulls;
        public /* synthetic */ Object L$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01461(ReceiveChannel receiveChannel, Continuation continuation) {
            super(2, continuation);
            this.$this_requireNoNulls = receiveChannel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01461 c01461 = new C01461(this.$this_requireNoNulls, continuation);
            c01461.L$0 = obj;
            return c01461;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@Nullable Object obj, @Nullable Continuation continuation) {
            return ((C01461) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ResultKt.throwOnFailure(obj);
            Object obj2 = this.L$0;
            if (obj2 != null) {
                return obj2;
            }
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("null element found in ");
            sbM.append(this.$this_requireNoNulls);
            sbM.append('.');
            throw new IllegalArgumentException(sbM.toString());
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {136, 139}, m = "single", n = {"$this$consume$iv", "iterator", "$this$consume$iv", "single"}, s = {"L$0", "L$1", "L$0", "L$1"})
    public static final class C01471<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public Object L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01471(Continuation<? super C01471> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.single(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {149, 152}, m = "singleOrNull", n = {"$this$consume$iv", "iterator", "$this$consume$iv", "single"}, s = {"L$0", "L$1", "L$0", "L$1"})
    public static final class C01481<E> extends ContinuationImpl {
        public ReceiveChannel L$0;
        public Object L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01481(Continuation<? super C01481> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__DeprecatedKt.singleOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$take$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$take$1", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {254, 255}, m = "invokeSuspend", n = {"$this$produce", "remaining", "$this$produce", "remaining"}, s = {"L$0", "I$0", "L$0", "I$0"})
    public static final class C01491 extends SuspendLambda implements Function2 {
        public final /* synthetic */ int $n;
        public final /* synthetic */ ReceiveChannel $this_take;
        public int I$0;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01491(int i, ReceiveChannel receiveChannel, Continuation continuation) {
            super(2, continuation);
            this.$n = i;
            this.$this_take = receiveChannel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01491 c01491 = new C01491(this.$n, this.$this_take, continuation);
            c01491.L$0 = obj;
            return c01491;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01491) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:21:0x005c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:22:0x005d  */
        /* JADX WARN: Code duplicated, block: B:25:0x0068  */
        /* JADX WARN: Code duplicated, block: B:27:0x007a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:28:0x007b  */
        /* JADX WARN: Code duplicated, block: B:31:0x0080  */
        /* JADX WARN: Code duplicated, block: B:33:0x0083  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x007b -> B:29:0x007c). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L32
                if (r1 == r3) goto L24
                if (r1 != r2) goto L1c
                int r1 = r8.I$0
                kotlinx.coroutines.channels.ChannelIterator r4 = r8.L$1
                java.lang.Object r5 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r5
                r5 = r8
                goto L7c
            L1c:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L24:
                int r1 = r8.I$0
                kotlinx.coroutines.channels.ChannelIterator r4 = r8.L$1
                java.lang.Object r5 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r9)
                r6 = r5
                r5 = r8
                goto L60
            L32:
                kotlin.ResultKt.throwOnFailure(r9)
                java.lang.Object r9 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
                int r1 = r8.$n
                if (r1 != 0) goto L40
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            L40:
                if (r1 < 0) goto L44
                r4 = 1
                goto L45
            L44:
                r4 = 0
            L45:
                if (r4 == 0) goto L86
                kotlinx.coroutines.channels.ReceiveChannel r4 = r8.$this_take
                kotlinx.coroutines.channels.ChannelIterator r4 = r4.iterator()
                r5 = r8
            L4e:
                r5.L$0 = r9
                r5.L$1 = r4
                r5.I$0 = r1
                r5.label = r3
                java.lang.Object r6 = r4.hasNext(r5)
                if (r6 != r0) goto L5d
                return r0
            L5d:
                r7 = r6
                r6 = r9
                r9 = r7
            L60:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L83
                java.lang.Object r9 = r4.next()
                r5.L$0 = r6
                r5.L$1 = r4
                r5.I$0 = r1
                r5.label = r2
                java.lang.Object r9 = r6.send(r9, r5)
                if (r9 != r0) goto L7b
                return r0
            L7b:
                r9 = r6
            L7c:
                int r1 = r1 + (-1)
                if (r1 != 0) goto L4e
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            L83:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            L86:
                java.lang.String r9 = "Requested element count "
                java.lang.String r0 = " is less than zero."
                java.lang.String r9 = androidx.core.graphics.Insets$$ExternalSyntheticOutline0.m(r9, r1, r0)
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.String r9 = r9.toString()
                r0.<init>(r9)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01491.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$takeWhile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$takeWhile$1", f = "Deprecated.kt", i = {0, 1, 1, 2}, l = {269, 270, 271}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e", "$this$produce"}, s = {"L$0", "L$0", "L$2", "L$0"})
    public static final class C01501 extends SuspendLambda implements Function2 {
        public final /* synthetic */ Function2 $predicate;
        public final /* synthetic */ ReceiveChannel $this_takeWhile;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public Object L$2;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01501(ReceiveChannel receiveChannel, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_takeWhile = receiveChannel;
            this.$predicate = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01501 c01501 = new C01501(this.$this_takeWhile, this.$predicate, continuation);
            c01501.L$0 = obj;
            return c01501;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01501) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x005c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:17:0x005d  */
        /* JADX WARN: Code duplicated, block: B:20:0x006b  */
        /* JADX WARN: Code duplicated, block: B:22:0x007f A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:23:0x0080  */
        /* JADX WARN: Code duplicated, block: B:26:0x008c  */
        /* JADX WARN: Code duplicated, block: B:28:0x008f  */
        /* JADX WARN: Code duplicated, block: B:30:0x009e A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:31:0x009f  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x009f -> B:14:0x0050). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r9.label
                r2 = 1
                r3 = 2
                r4 = 3
                if (r1 == 0) goto L41
                if (r1 == r2) goto L33
                if (r1 == r3) goto L23
                if (r1 != r4) goto L1b
                kotlinx.coroutines.channels.ChannelIterator r1 = r9.L$1
                java.lang.Object r5 = r9.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                goto L4f
            L1b:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L23:
                java.lang.Object r1 = r9.L$2
                kotlinx.coroutines.channels.ChannelIterator r5 = r9.L$1
                java.lang.Object r6 = r9.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r10)
                r7 = r6
                r6 = r1
                r1 = r0
                r0 = r9
                goto L84
            L33:
                kotlinx.coroutines.channels.ChannelIterator r1 = r9.L$1
                java.lang.Object r5 = r9.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                r6 = r5
                r5 = r1
                r1 = r0
                r0 = r9
                goto L63
            L41:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                r5 = r10
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlinx.coroutines.channels.ReceiveChannel r10 = r9.$this_takeWhile
                kotlinx.coroutines.channels.ChannelIterator r1 = r10.iterator()
            L4f:
                r10 = r9
            L50:
                r10.L$0 = r5
                r10.L$1 = r1
                r10.label = r2
                java.lang.Object r6 = r1.hasNext(r10)
                if (r6 != r0) goto L5d
                return r0
            L5d:
                r8 = r0
                r0 = r10
                r10 = r6
                r6 = r5
                r5 = r1
                r1 = r8
            L63:
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 == 0) goto La4
                java.lang.Object r10 = r5.next()
                kotlin.jvm.functions.Function2 r7 = r0.$predicate
                r0.L$0 = r6
                r0.L$1 = r5
                r0.L$2 = r10
                r0.label = r3
                java.lang.Object r7 = r7.invoke(r10, r0)
                if (r7 != r1) goto L80
                return r1
            L80:
                r8 = r6
                r6 = r10
                r10 = r7
                r7 = r8
            L84:
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 != 0) goto L8f
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            L8f:
                r0.L$0 = r7
                r0.L$1 = r5
                r10 = 0
                r0.L$2 = r10
                r0.label = r4
                java.lang.Object r10 = r7.send(r6, r0)
                if (r10 != r1) goto L9f
                return r1
            L9f:
                r10 = r0
                r0 = r1
                r1 = r5
                r5 = r7
                goto L50
            La4:
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01501.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {487, 278}, m = "toChannel", n = {"destination", "$this$consume$iv$iv", "destination", "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$0", "L$1"})
    public static final class C01511<E, C extends SendChannel<? super E>> extends ContinuationImpl {
        public SendChannel L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01511(Continuation<? super C01511> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toChannel(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {487}, m = "toCollection", n = {"destination", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    public static final class C01521<E, C extends Collection<? super E>> extends ContinuationImpl {
        public Collection L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public C01521(Continuation<? super C01521> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toCollection(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2, reason: invalid class name */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {487}, m = "toMap", n = {"destination", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    public static final class AnonymousClass2<K, V, M extends Map<? super K, ? super V>> extends ContinuationImpl {
        public Map L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toMap(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/collections/IndexedValue;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {370, 371}, m = "invokeSuspend", n = {"$this$produce", "index", "$this$produce", "index"}, s = {"L$0", "I$0", "L$0", "I$0"})
    public static final class C01531 extends SuspendLambda implements Function2 {
        public final /* synthetic */ ReceiveChannel $this_withIndex;
        public int I$0;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01531(ReceiveChannel receiveChannel, Continuation continuation) {
            super(2, continuation);
            this.$this_withIndex = receiveChannel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01531 c01531 = new C01531(this.$this_withIndex, continuation);
            c01531.L$0 = obj;
            return c01531;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope producerScope, @Nullable Continuation continuation) {
            return ((C01531) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:14:0x0051 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:15:0x0052  */
        /* JADX WARN: Code duplicated, block: B:18:0x0061  */
        /* JADX WARN: Code duplicated, block: B:20:0x007a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:21:0x007b  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x007b -> B:12:0x0043). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r10.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L33
                if (r1 == r3) goto L22
                if (r1 != r2) goto L1a
                int r1 = r10.I$0
                kotlinx.coroutines.channels.ChannelIterator r4 = r10.L$1
                java.lang.Object r5 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r11)
                goto L42
            L1a:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r0)
                throw r11
            L22:
                int r1 = r10.I$0
                kotlinx.coroutines.channels.ChannelIterator r4 = r10.L$1
                java.lang.Object r5 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r11)
                r6 = r5
                r5 = r4
                r4 = r1
                r1 = r0
                r0 = r10
                goto L59
            L33:
                kotlin.ResultKt.throwOnFailure(r11)
                java.lang.Object r11 = r10.L$0
                r5 = r11
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                r1 = 0
                kotlinx.coroutines.channels.ReceiveChannel r11 = r10.$this_withIndex
                kotlinx.coroutines.channels.ChannelIterator r4 = r11.iterator()
            L42:
                r11 = r10
            L43:
                r11.L$0 = r5
                r11.L$1 = r4
                r11.I$0 = r1
                r11.label = r3
                java.lang.Object r6 = r4.hasNext(r11)
                if (r6 != r0) goto L52
                return r0
            L52:
                r9 = r0
                r0 = r11
                r11 = r6
                r6 = r5
                r5 = r4
                r4 = r1
                r1 = r9
            L59:
                java.lang.Boolean r11 = (java.lang.Boolean) r11
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto L81
                java.lang.Object r11 = r5.next()
                kotlin.collections.IndexedValue r7 = new kotlin.collections.IndexedValue
                int r8 = r4 + 1
                r7.<init>(r4, r11)
                r0.L$0 = r6
                r0.L$1 = r5
                r0.I$0 = r8
                r0.label = r2
                java.lang.Object r11 = r6.send(r7, r0)
                if (r11 != r1) goto L7b
                return r1
            L7b:
                r11 = r0
                r0 = r1
                r4 = r5
                r5 = r6
                r1 = r8
                goto L43
            L81:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01531.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Add missing generic type declarations: [V] */
    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$zip$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Deprecated.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001\"\u0004\b\u0002\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00020\u0003H\u008a@"}, d2 = {ExifInterface.LONGITUDE_EAST, "R", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlinx/coroutines/channels/ProducerScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$zip$2", f = "Deprecated.kt", i = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2}, l = {487, 469, 471}, m = "invokeSuspend", n = {"$this$produce", "otherIterator", "$this$consume$iv$iv", "$this$produce", "otherIterator", "$this$consume$iv$iv", "element1", "$this$produce", "otherIterator", "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$3", "L$0", "L$1", "L$3", "L$5", "L$0", "L$1", "L$3"})
    public static final class C01552<V> extends SuspendLambda implements Function2<ProducerScope<? super V>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ReceiveChannel<R> $other;
        public final /* synthetic */ ReceiveChannel<E> $this_zip;
        public final /* synthetic */ Function2<E, R, V> $transform;
        private /* synthetic */ Object L$0;
        public ChannelIterator L$1;
        public Function2 L$2;
        public ReceiveChannel L$3;
        public ChannelIterator L$4;
        public Object L$5;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01552(ReceiveChannel<? extends R> receiveChannel, ReceiveChannel<? extends E> receiveChannel2, Function2<? super E, ? super R, ? extends V> function2, Continuation<? super C01552> continuation) {
            super(2, continuation);
            this.$other = receiveChannel;
            this.$this_zip = receiveChannel2;
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C01552 c01552 = new C01552(this.$other, this.$this_zip, this.$transform, continuation);
            c01552.L$0 = obj;
            return c01552;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super V> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C01552) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:25:0x008a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:26:0x008b  */
        /* JADX WARN: Code duplicated, block: B:29:0x009c A[Catch: all -> 0x0045, TRY_LEAVE, TryCatch #0 {all -> 0x0045, blocks: (B:27:0x0094, B:29:0x009c, B:42:0x00ed, B:13:0x0038), top: B:52:0x0038 }] */
        /* JADX WARN: Code duplicated, block: B:31:0x00b4 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:32:0x00b5  */
        /* JADX WARN: Code duplicated, block: B:35:0x00c5  */
        /* JADX WARN: Code duplicated, block: B:36:0x00c6 A[Catch: all -> 0x00eb, TRY_LEAVE, TryCatch #3 {all -> 0x00eb, blocks: (B:33:0x00bd, B:36:0x00c6), top: B:58:0x00bd }] */
        /* JADX WARN: Code duplicated, block: B:38:0x00e2 A[RETURN] */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r14) {
            /*
                Method dump skipped, instruction units count: 253
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01552.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object any(ReceiveChannel receiveChannel, Continuation continuation) throws Throwable {
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
        Object objHasNext = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objHasNext);
                ChannelIterator it = receiveChannel.iterator();
                anonymousClass1.L$0 = receiveChannel;
                anonymousClass1.label = 1;
                objHasNext = it.hasNext(anonymousClass1);
                if (objHasNext == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                receiveChannel = anonymousClass1.L$0;
                ResultKt.throwOnFailure(objHasNext);
            }
            ChannelsKt.cancelConsumed(receiveChannel, null);
            return objHasNext;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                ChannelsKt.cancelConsumed(receiveChannel, th);
                throw th2;
            }
        }
    }

    @PublishedApi
    @NotNull
    public static final Function1<Throwable, Unit> consumes(@NotNull final ReceiveChannel<?> receiveChannel) {
        return new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.consumes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable Throwable th) {
                ChannelsKt.cancelConsumed(receiveChannel, th);
            }
        };
    }

    @PublishedApi
    @NotNull
    public static final Function1<Throwable, Unit> consumesAll(@NotNull final ReceiveChannel<?>... receiveChannelArr) {
        return new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.consumesAll.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) throws Throwable {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable Throwable th) throws Throwable {
                ReceiveChannel<?>[] receiveChannelArr2 = receiveChannelArr;
                int length = receiveChannelArr2.length;
                Throwable th2 = null;
                int i = 0;
                while (i < length) {
                    ReceiveChannel<?> receiveChannel = receiveChannelArr2[i];
                    i++;
                    try {
                        ChannelsKt.cancelConsumed(receiveChannel, th);
                    } catch (Throwable th3) {
                        if (th2 == null) {
                            th2 = th3;
                        } else {
                            ExceptionsKt.addSuppressed(th2, th3);
                        }
                    }
                }
                if (th2 != null) {
                    throw th2;
                }
            }
        };
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0057 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x0061 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:23:0x0058, B:25:0x0061, B:20:0x0049, B:26:0x006a, B:19:0x0044), top: B:37:0x0044 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0055 -> B:23:0x0058). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object count(kotlinx.coroutines.channels.ReceiveChannel r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01221
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01221) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r2 = r0.L$1
            kotlin.jvm.internal.Ref$IntRef r4 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L32
            r6 = r2
            r2 = r7
            r7 = r6
            goto L58
        L32:
            r7 = move-exception
            goto L79
        L34:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3c:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$IntRef r8 = new kotlin.jvm.internal.Ref$IntRef
            r8.<init>()
            kotlinx.coroutines.channels.ChannelIterator r2 = r7.iterator()     // Catch: java.lang.Throwable -> L76
            r4 = r8
        L49:
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L76
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L76
            r0.L$2 = r2     // Catch: java.lang.Throwable -> L76
            r0.label = r3     // Catch: java.lang.Throwable -> L76
            java.lang.Object r8 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> L76
            if (r8 != r1) goto L58
            return r1
        L58:
            r5 = 0
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L76
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L76
            if (r8 == 0) goto L6a
            r2.next()     // Catch: java.lang.Throwable -> L76
            int r8 = r4.element     // Catch: java.lang.Throwable -> L76
            int r8 = r8 + r3
            r4.element = r8     // Catch: java.lang.Throwable -> L76
            goto L49
        L6a:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L76
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            int r7 = r4.element
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)
            return r7
        L76:
            r8 = move-exception
            r2 = r7
            r7 = r8
        L79:
            throw r7     // Catch: java.lang.Throwable -> L7a
        L7a:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.count(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @PublishedApi
    @NotNull
    public static final <E, K> ReceiveChannel<E> distinctBy(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super Continuation<? super K>, ? extends Object> function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01241(receiveChannel, function2, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel distinctBy$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.distinctBy(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel drop(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01251(i, receiveChannel, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel drop$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return drop(receiveChannel, i, coroutineContext);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel dropWhile(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01261(receiveChannel, function2, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel dropWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return dropWhile(receiveChannel, coroutineContext, function2);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x005b  */
    /* JADX WARN: Code duplicated, block: B:29:0x0068 A[Catch: all -> 0x0035, TRY_LEAVE, TryCatch #0 {all -> 0x0035, blocks: (B:12:0x0031, B:27:0x0060, B:29:0x0068, B:35:0x0077, B:36:0x008e), top: B:45:0x0031 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0070  */
    /* JADX WARN: Code duplicated, block: B:34:0x0074  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005b -> B:26:0x005f). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object elementAt(kotlinx.coroutines.channels.ReceiveChannel r10, int r11, kotlin.coroutines.Continuation r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01271
            if (r0 == 0) goto L13
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01271) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1
            r0.<init>(r12)
        L18:
            java.lang.Object r12 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            java.lang.String r3 = "ReceiveChannel doesn't contain element at index "
            r4 = 46
            r5 = 1
            if (r2 == 0) goto L40
            if (r2 != r5) goto L38
            int r10 = r0.I$1
            int r11 = r0.I$0
            kotlinx.coroutines.channels.ChannelIterator r2 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L35
            goto L5f
        L35:
            r10 = move-exception
            goto Lac
        L38:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L40:
            kotlin.ResultKt.throwOnFailure(r12)
            if (r11 < 0) goto L8f
            r12 = 0
            kotlinx.coroutines.channels.ChannelIterator r2 = r10.iterator()     // Catch: java.lang.Throwable -> La7
        L4a:
            r0.L$0 = r10     // Catch: java.lang.Throwable -> La7
            r0.L$1 = r2     // Catch: java.lang.Throwable -> La7
            r0.I$0 = r11     // Catch: java.lang.Throwable -> La7
            r0.I$1 = r12     // Catch: java.lang.Throwable -> La7
            r0.label = r5     // Catch: java.lang.Throwable -> La7
            java.lang.Object r6 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> La7
            if (r6 != r1) goto L5b
            return r1
        L5b:
            r9 = r6
            r6 = r10
            r10 = r12
            r12 = r9
        L5f:
            r7 = 0
            java.lang.Boolean r12 = (java.lang.Boolean) r12     // Catch: java.lang.Throwable -> L35
            boolean r12 = r12.booleanValue()     // Catch: java.lang.Throwable -> L35
            if (r12 == 0) goto L77
            java.lang.Object r12 = r2.next()     // Catch: java.lang.Throwable -> L35
            int r8 = r10 + 1
            if (r11 != r10) goto L74
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r7)
            return r12
        L74:
            r10 = r6
            r12 = r8
            goto L4a
        L77:
            java.lang.IndexOutOfBoundsException r10 = new java.lang.IndexOutOfBoundsException     // Catch: java.lang.Throwable -> L35
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
            r12.<init>()     // Catch: java.lang.Throwable -> L35
            r12.append(r3)     // Catch: java.lang.Throwable -> L35
            r12.append(r11)     // Catch: java.lang.Throwable -> L35
            r12.append(r4)     // Catch: java.lang.Throwable -> L35
            java.lang.String r11 = r12.toString()     // Catch: java.lang.Throwable -> L35
            r10.<init>(r11)     // Catch: java.lang.Throwable -> L35
            throw r10     // Catch: java.lang.Throwable -> L35
        L8f:
            java.lang.IndexOutOfBoundsException r12 = new java.lang.IndexOutOfBoundsException     // Catch: java.lang.Throwable -> La7
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La7
            r0.<init>()     // Catch: java.lang.Throwable -> La7
            r0.append(r3)     // Catch: java.lang.Throwable -> La7
            r0.append(r11)     // Catch: java.lang.Throwable -> La7
            r0.append(r4)     // Catch: java.lang.Throwable -> La7
            java.lang.String r11 = r0.toString()     // Catch: java.lang.Throwable -> La7
            r12.<init>(r11)     // Catch: java.lang.Throwable -> La7
            throw r12     // Catch: java.lang.Throwable -> La7
        La7:
            r11 = move-exception
            r9 = r11
            r11 = r10
            r10 = r9
            r6 = r11
        Lac:
            throw r10     // Catch: java.lang.Throwable -> Lad
        Lad:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.elementAt(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:26:0x005e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:29:0x0067 A[Catch: all -> 0x0079, TRY_LEAVE, TryCatch #1 {all -> 0x0079, blocks: (B:27:0x005f, B:29:0x0067, B:24:0x004e, B:23:0x004a), top: B:45:0x004a }] */
    /* JADX WARN: Code duplicated, block: B:32:0x006f  */
    /* JADX WARN: Code duplicated, block: B:34:0x0073  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005c -> B:27:0x005f). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel r8, int r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01281
            if (r0 == 0) goto L13
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01281) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 != r4) goto L38
            int r8 = r0.I$1
            int r9 = r0.I$0
            kotlinx.coroutines.channels.ChannelIterator r2 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L36
            r7 = r10
            r10 = r8
            r8 = r5
            r5 = r7
            goto L5f
        L36:
            r8 = move-exception
            goto L7e
        L38:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L40:
            kotlin.ResultKt.throwOnFailure(r10)
            if (r9 >= 0) goto L49
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            return r3
        L49:
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r2 = r8.iterator()     // Catch: java.lang.Throwable -> L79
        L4e:
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L79
            r0.L$1 = r2     // Catch: java.lang.Throwable -> L79
            r0.I$0 = r9     // Catch: java.lang.Throwable -> L79
            r0.I$1 = r10     // Catch: java.lang.Throwable -> L79
            r0.label = r4     // Catch: java.lang.Throwable -> L79
            java.lang.Object r5 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> L79
            if (r5 != r1) goto L5f
            return r1
        L5f:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch: java.lang.Throwable -> L79
            boolean r5 = r5.booleanValue()     // Catch: java.lang.Throwable -> L79
            if (r5 == 0) goto L75
            java.lang.Object r5 = r2.next()     // Catch: java.lang.Throwable -> L79
            int r6 = r10 + 1
            if (r9 != r10) goto L73
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            return r5
        L73:
            r10 = r6
            goto L4e
        L75:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            return r3
        L79:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
            r5 = r9
        L7e:
            throw r8     // Catch: java.lang.Throwable -> L7f
        L7f:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @PublishedApi
    @NotNull
    public static final <E> ReceiveChannel<E> filter(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01291(receiveChannel, function2, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel filter$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel filterIndexed(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01301(receiveChannel, function3, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel filterIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return filterIndexed(receiveChannel, coroutineContext, function3);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel filterNot(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ChannelsKt.filter(receiveChannel, coroutineContext, new C01311(function2, null));
    }

    public static /* synthetic */ ReceiveChannel filterNot$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return filterNot(receiveChannel, coroutineContext, function2);
    }

    @PublishedApi
    @NotNull
    public static final <E> ReceiveChannel<E> filterNotNull(@NotNull ReceiveChannel<? extends E> receiveChannel) {
        return filter$default(receiveChannel, null, new C01321(null), 1, null);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0054 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0055  */
    /* JADX WARN: Code duplicated, block: B:27:0x0062 A[Catch: all -> 0x0075, TryCatch #0 {all -> 0x0075, blocks: (B:25:0x0059, B:27:0x0062, B:29:0x0068, B:21:0x0046, B:31:0x006f, B:20:0x0042), top: B:40:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0068 A[Catch: all -> 0x0075, TryCatch #0 {all -> 0x0075, blocks: (B:25:0x0059, B:27:0x0062, B:29:0x0068, B:21:0x0046, B:31:0x006f, B:20:0x0042), top: B:40:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x006f A[Catch: all -> 0x0075, TRY_LEAVE, TryCatch #0 {all -> 0x0075, blocks: (B:25:0x0059, B:27:0x0062, B:29:0x0068, B:21:0x0046, B:31:0x006f, B:20:0x0042), top: B:40:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0055 -> B:14:0x0032). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel r6, java.util.Collection r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01331
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01331) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3f
            if (r2 != r3) goto L37
            kotlinx.coroutines.channels.ChannelIterator r6 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r7 = r0.L$1
            java.util.Collection r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L35
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r2
        L32:
            r2 = r1
            r1 = r5
            goto L59
        L35:
            r6 = move-exception
            goto L79
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L75
        L46:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L75
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L75
            r0.L$2 = r8     // Catch: java.lang.Throwable -> L75
            r0.label = r3     // Catch: java.lang.Throwable -> L75
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> L75
            if (r2 != r1) goto L55
            return r1
        L55:
            r5 = r0
            r0 = r8
            r8 = r2
            goto L32
        L59:
            r4 = 0
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L75
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L75
            if (r8 == 0) goto L6f
            java.lang.Object r8 = r0.next()     // Catch: java.lang.Throwable -> L75
            if (r8 == 0) goto L6b
            r7.add(r8)     // Catch: java.lang.Throwable -> L75
        L6b:
            r8 = r0
            r0 = r1
            r1 = r2
            goto L46
        L6f:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L75
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r7
        L75:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L79:
            throw r6     // Catch: java.lang.Throwable -> L7a
        L7a:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0058 A[Catch: all -> 0x002d, TRY_LEAVE, TryCatch #2 {all -> 0x002d, blocks: (B:12:0x0029, B:24:0x0050, B:26:0x0058, B:29:0x0060, B:30:0x0067), top: B:41:0x0029 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0060 A[Catch: all -> 0x002d, TRY_ENTER, TryCatch #2 {all -> 0x002d, blocks: (B:12:0x0029, B:24:0x0050, B:26:0x0058, B:29:0x0060, B:30:0x0067), top: B:41:0x0029 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object first(ReceiveChannel receiveChannel, Continuation continuation) throws Throwable {
        C01341 c01341;
        ReceiveChannel receiveChannel2;
        Throwable th;
        ChannelIterator channelIterator;
        if (continuation instanceof C01341) {
            c01341 = (C01341) continuation;
            int i = c01341.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01341.label = i - Integer.MIN_VALUE;
            } else {
                c01341 = new C01341(continuation);
            }
        } else {
            c01341 = new C01341(continuation);
        }
        Object obj = c01341.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01341.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            channelIterator = c01341.L$1;
            receiveChannel2 = c01341.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                if (((Boolean) obj).booleanValue()) {
                    throw new NoSuchElementException("ReceiveChannel is empty.");
                }
                Object next = channelIterator.next();
                ChannelsKt.cancelConsumed(receiveChannel2, null);
                return next;
            } catch (Throwable th2) {
                th = th2;
                try {
                    throw th;
                } catch (Throwable th3) {
                    ChannelsKt.cancelConsumed(receiveChannel2, th);
                    throw th3;
                }
            }
        }
        ResultKt.throwOnFailure(obj);
        try {
            ChannelIterator it = receiveChannel.iterator();
            c01341.L$0 = receiveChannel;
            c01341.L$1 = it;
            c01341.label = 1;
            Object objHasNext = it.hasNext(c01341);
            if (objHasNext == coroutine_suspended) {
                return coroutine_suspended;
            }
            receiveChannel2 = receiveChannel;
            channelIterator = it;
            obj = objHasNext;
            if (((Boolean) obj).booleanValue()) {
                throw new NoSuchElementException("ReceiveChannel is empty.");
            }
            Object next2 = channelIterator.next();
            ChannelsKt.cancelConsumed(receiveChannel2, null);
            return next2;
        } catch (Throwable th4) {
            receiveChannel2 = receiveChannel;
            th = th4;
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object firstOrNull(ReceiveChannel receiveChannel, Continuation continuation) throws Throwable {
        C01351 c01351;
        ReceiveChannel receiveChannel2;
        Throwable th;
        ChannelIterator channelIterator;
        if (continuation instanceof C01351) {
            c01351 = (C01351) continuation;
            int i = c01351.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01351.label = i - Integer.MIN_VALUE;
            } else {
                c01351 = new C01351(continuation);
            }
        } else {
            c01351 = new C01351(continuation);
        }
        Object obj = c01351.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01351.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            try {
                ChannelIterator it = receiveChannel.iterator();
                c01351.L$0 = receiveChannel;
                c01351.L$1 = it;
                c01351.label = 1;
                Object objHasNext = it.hasNext(c01351);
                if (objHasNext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                receiveChannel2 = receiveChannel;
                channelIterator = it;
                obj = objHasNext;
            } catch (Throwable th2) {
                receiveChannel2 = receiveChannel;
                th = th2;
                throw th;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            channelIterator = c01351.L$1;
            receiveChannel2 = c01351.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                try {
                    throw th;
                } catch (Throwable th4) {
                    ChannelsKt.cancelConsumed(receiveChannel2, th);
                    throw th4;
                }
            }
        }
        if (!((Boolean) obj).booleanValue()) {
            ChannelsKt.cancelConsumed(receiveChannel2, null);
            return null;
        }
        Object next = channelIterator.next();
        ChannelsKt.cancelConsumed(receiveChannel2, null);
        return next;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel flatMap(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01361(receiveChannel, function2, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel flatMap$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return flatMap(receiveChannel, coroutineContext, function2);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x005b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:23:0x005c  */
    /* JADX WARN: Code duplicated, block: B:26:0x0069 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:24:0x0060, B:26:0x0069, B:28:0x0073, B:31:0x007d, B:20:0x004b, B:32:0x0085, B:19:0x0047), top: B:41:0x0047 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0073 A[Catch: all -> 0x0090, TRY_LEAVE, TryCatch #0 {all -> 0x0090, blocks: (B:24:0x0060, B:26:0x0069, B:28:0x0073, B:31:0x007d, B:20:0x004b, B:32:0x0085, B:19:0x0047), top: B:41:0x0047 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x007d A[Catch: all -> 0x0090, TRY_ENTER, TryCatch #0 {all -> 0x0090, blocks: (B:24:0x0060, B:26:0x0069, B:28:0x0073, B:31:0x007d, B:20:0x004b, B:32:0x0085, B:19:0x0047), top: B:41:0x0047 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0085 A[Catch: all -> 0x0090, TRY_LEAVE, TryCatch #0 {all -> 0x0090, blocks: (B:24:0x0060, B:26:0x0069, B:28:0x0073, B:31:0x007d, B:20:0x004b, B:32:0x0085, B:19:0x0047), top: B:41:0x0047 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x005c -> B:24:0x0060). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object indexOf(kotlinx.coroutines.channels.ReceiveChannel r7, java.lang.Object r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01371
            if (r0 == 0) goto L13
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01371) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3f
            if (r2 != r3) goto L37
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$3
            kotlinx.coroutines.channels.ReceiveChannel r8 = r0.L$2
            kotlin.jvm.internal.Ref$IntRef r2 = r0.L$1
            java.lang.Object r4 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L35
            r6 = r4
            r4 = r7
            r7 = r8
            r8 = r6
            goto L60
        L35:
            r7 = move-exception
            goto L94
        L37:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3f:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlin.jvm.internal.Ref$IntRef r9 = new kotlin.jvm.internal.Ref$IntRef
            r9.<init>()
            kotlinx.coroutines.channels.ChannelIterator r2 = r7.iterator()     // Catch: java.lang.Throwable -> L90
        L4b:
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L90
            r0.L$1 = r9     // Catch: java.lang.Throwable -> L90
            r0.L$2 = r7     // Catch: java.lang.Throwable -> L90
            r0.L$3 = r2     // Catch: java.lang.Throwable -> L90
            r0.label = r3     // Catch: java.lang.Throwable -> L90
            java.lang.Object r4 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> L90
            if (r4 != r1) goto L5c
            return r1
        L5c:
            r6 = r2
            r2 = r9
            r9 = r4
            r4 = r6
        L60:
            r5 = 0
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L90
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L90
            if (r9 == 0) goto L85
            java.lang.Object r9 = r4.next()     // Catch: java.lang.Throwable -> L90
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r9)     // Catch: java.lang.Throwable -> L90
            if (r9 == 0) goto L7d
            int r8 = r2.element     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r8 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r8)     // Catch: java.lang.Throwable -> L90
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            return r8
        L7d:
            int r9 = r2.element     // Catch: java.lang.Throwable -> L90
            int r9 = r9 + r3
            r2.element = r9     // Catch: java.lang.Throwable -> L90
            r9 = r2
            r2 = r4
            goto L4b
        L85:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L90
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            r7 = -1
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)
            return r7
        L90:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L94:
            throw r7     // Catch: java.lang.Throwable -> L95
        L95:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.indexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0080 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:39:0x008a A[Catch: all -> 0x0093, TRY_LEAVE, TryCatch #1 {all -> 0x0093, blocks: (B:37:0x0082, B:39:0x008a), top: B:56:0x0082 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x007e -> B:36:0x0081). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object last(kotlinx.coroutines.channels.ReceiveChannel r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01381
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01381) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 2
            if (r2 == 0) goto L4b
            if (r2 == r3) goto L41
            if (r2 != r4) goto L39
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r3 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L36
            r6 = r8
            r8 = r7
            r7 = r3
            r3 = r6
            goto L81
        L36:
            r7 = move-exception
            goto La6
        L39:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L41:
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L49
            goto L63
        L49:
            r7 = move-exception
            goto L9f
        L4b:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r7.iterator()     // Catch: java.lang.Throwable -> La1
            r0.L$0 = r7     // Catch: java.lang.Throwable -> La1
            r0.L$1 = r8     // Catch: java.lang.Throwable -> La1
            r0.label = r3     // Catch: java.lang.Throwable -> La1
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> La1
            if (r2 != r1) goto L5f
            return r1
        L5f:
            r6 = r2
            r2 = r7
            r7 = r8
            r8 = r6
        L63:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L49
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L49
            if (r8 == 0) goto L97
            java.lang.Object r8 = r7.next()     // Catch: java.lang.Throwable -> L49
            r6 = r2
            r2 = r7
            r7 = r6
        L72:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> La1
            r0.L$1 = r2     // Catch: java.lang.Throwable -> La1
            r0.L$2 = r8     // Catch: java.lang.Throwable -> La1
            r0.label = r4     // Catch: java.lang.Throwable -> La1
            java.lang.Object r3 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> La1
            if (r3 != r1) goto L81
            return r1
        L81:
            r5 = 0
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch: java.lang.Throwable -> L93
            boolean r3 = r3.booleanValue()     // Catch: java.lang.Throwable -> L93
            if (r3 == 0) goto L8f
            java.lang.Object r8 = r2.next()     // Catch: java.lang.Throwable -> L93
            goto L72
        L8f:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            return r8
        L93:
            r8 = move-exception
            r3 = r7
            r7 = r8
            goto La6
        L97:
            java.util.NoSuchElementException r7 = new java.util.NoSuchElementException     // Catch: java.lang.Throwable -> L49
            java.lang.String r8 = "ReceiveChannel is empty."
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L49
            throw r7     // Catch: java.lang.Throwable -> L49
        L9f:
            r3 = r2
            goto La6
        La1:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            r3 = r8
        La6:
            throw r7     // Catch: java.lang.Throwable -> La7
        La7:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.last(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0067 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:23:0x0068  */
    /* JADX WARN: Code duplicated, block: B:26:0x0075 A[Catch: all -> 0x0097, TryCatch #0 {all -> 0x0097, blocks: (B:24:0x006c, B:26:0x0075, B:28:0x007f, B:29:0x0083, B:20:0x0055, B:30:0x008b, B:19:0x0051), top: B:39:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x007f A[Catch: all -> 0x0097, TryCatch #0 {all -> 0x0097, blocks: (B:24:0x006c, B:26:0x0075, B:28:0x007f, B:29:0x0083, B:20:0x0055, B:30:0x008b, B:19:0x0051), top: B:39:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x008b A[Catch: all -> 0x0097, TRY_LEAVE, TryCatch #0 {all -> 0x0097, blocks: (B:24:0x006c, B:26:0x0075, B:28:0x007f, B:29:0x0083, B:20:0x0055, B:30:0x008b, B:19:0x0051), top: B:39:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0068 -> B:24:0x006c). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel r8, java.lang.Object r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01391
            if (r0 == 0) goto L13
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01391) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L41
            if (r2 != r3) goto L39
            kotlinx.coroutines.channels.ChannelIterator r8 = r0.L$4
            kotlinx.coroutines.channels.ReceiveChannel r9 = r0.L$3
            kotlin.jvm.internal.Ref$IntRef r2 = r0.L$2
            kotlin.jvm.internal.Ref$IntRef r4 = r0.L$1
            java.lang.Object r5 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L37
            r7 = r5
            r5 = r8
            r8 = r9
            r9 = r7
            goto L6c
        L37:
            r8 = move-exception
            goto L9b
        L39:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L41:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlin.jvm.internal.Ref$IntRef r10 = new kotlin.jvm.internal.Ref$IntRef
            r10.<init>()
            r2 = -1
            r10.element = r2
            kotlin.jvm.internal.Ref$IntRef r2 = new kotlin.jvm.internal.Ref$IntRef
            r2.<init>()
            kotlinx.coroutines.channels.ChannelIterator r4 = r8.iterator()     // Catch: java.lang.Throwable -> L97
        L55:
            r0.L$0 = r9     // Catch: java.lang.Throwable -> L97
            r0.L$1 = r10     // Catch: java.lang.Throwable -> L97
            r0.L$2 = r2     // Catch: java.lang.Throwable -> L97
            r0.L$3 = r8     // Catch: java.lang.Throwable -> L97
            r0.L$4 = r4     // Catch: java.lang.Throwable -> L97
            r0.label = r3     // Catch: java.lang.Throwable -> L97
            java.lang.Object r5 = r4.hasNext(r0)     // Catch: java.lang.Throwable -> L97
            if (r5 != r1) goto L68
            return r1
        L68:
            r7 = r4
            r4 = r10
            r10 = r5
            r5 = r7
        L6c:
            r6 = 0
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch: java.lang.Throwable -> L97
            boolean r10 = r10.booleanValue()     // Catch: java.lang.Throwable -> L97
            if (r10 == 0) goto L8b
            java.lang.Object r10 = r5.next()     // Catch: java.lang.Throwable -> L97
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r10)     // Catch: java.lang.Throwable -> L97
            if (r10 == 0) goto L83
            int r10 = r2.element     // Catch: java.lang.Throwable -> L97
            r4.element = r10     // Catch: java.lang.Throwable -> L97
        L83:
            int r10 = r2.element     // Catch: java.lang.Throwable -> L97
            int r10 = r10 + r3
            r2.element = r10     // Catch: java.lang.Throwable -> L97
            r10 = r4
            r4 = r5
            goto L55
        L8b:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L97
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r6)
            int r8 = r4.element
            java.lang.Integer r8 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r8)
            return r8
        L97:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
        L9b:
            throw r8     // Catch: java.lang.Throwable -> L9c
        L9c:
            r10 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r8)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0085 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:40:0x008e A[Catch: all -> 0x0097, TRY_LEAVE, TryCatch #1 {all -> 0x0097, blocks: (B:38:0x0086, B:40:0x008e), top: B:55:0x0086 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x0083 -> B:55:0x0086). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object lastOrNull(kotlinx.coroutines.channels.ReceiveChannel r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01401
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01401) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            r5 = 2
            if (r2 == 0) goto L4c
            if (r2 == r3) goto L42
            if (r2 != r5) goto L3a
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r3 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L37
            r6 = r8
            r8 = r7
            r7 = r3
            r3 = r6
            goto L86
        L37:
            r7 = move-exception
            goto La2
        L3a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L42:
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L4a
            goto L64
        L4a:
            r7 = move-exception
            goto L9b
        L4c:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r7.iterator()     // Catch: java.lang.Throwable -> L9d
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L9d
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L9d
            r0.label = r3     // Catch: java.lang.Throwable -> L9d
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> L9d
            if (r2 != r1) goto L60
            return r1
        L60:
            r6 = r2
            r2 = r7
            r7 = r8
            r8 = r6
        L64:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L4a
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L4a
            if (r8 != 0) goto L70
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            return r4
        L70:
            java.lang.Object r8 = r7.next()     // Catch: java.lang.Throwable -> L4a
            r6 = r2
            r2 = r7
            r7 = r6
        L77:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L9d
            r0.L$1 = r2     // Catch: java.lang.Throwable -> L9d
            r0.L$2 = r8     // Catch: java.lang.Throwable -> L9d
            r0.label = r5     // Catch: java.lang.Throwable -> L9d
            java.lang.Object r3 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> L9d
            if (r3 != r1) goto L86
            return r1
        L86:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch: java.lang.Throwable -> L97
            boolean r3 = r3.booleanValue()     // Catch: java.lang.Throwable -> L97
            if (r3 == 0) goto L93
            java.lang.Object r8 = r2.next()     // Catch: java.lang.Throwable -> L97
            goto L77
        L93:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r4)
            return r8
        L97:
            r8 = move-exception
            r3 = r7
            r7 = r8
            goto La2
        L9b:
            r3 = r2
            goto La2
        L9d:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            r3 = r8
        La2:
            throw r7     // Catch: java.lang.Throwable -> La3
        La3:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.lastOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @PublishedApi
    @NotNull
    public static final <E, R> ReceiveChannel<R> map(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01411(receiveChannel, function2, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel map$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.map(receiveChannel, coroutineContext, function2);
    }

    @PublishedApi
    @NotNull
    public static final <E, R> ReceiveChannel<R> mapIndexed(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> function3) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01421(receiveChannel, function3, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel mapIndexedNotNull(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3) {
        return ChannelsKt.filterNotNull(ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3));
    }

    public static /* synthetic */ ReceiveChannel mapIndexedNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return mapIndexedNotNull(receiveChannel, coroutineContext, function3);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel mapNotNull(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ChannelsKt.filterNotNull(ChannelsKt.map(receiveChannel, coroutineContext, function2));
    }

    public static /* synthetic */ ReceiveChannel mapNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return mapNotNull(receiveChannel, coroutineContext, function2);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0090 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:40:0x0099 A[Catch: all -> 0x00a9, TRY_LEAVE, TryCatch #0 {all -> 0x00a9, blocks: (B:38:0x0091, B:40:0x0099, B:35:0x0080, B:25:0x0054), top: B:52:0x0054 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x008e -> B:38:0x0091). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object maxWith(kotlinx.coroutines.channels.ReceiveChannel r8, java.util.Comparator r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01431
            if (r0 == 0) goto L13
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01431) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            r5 = 2
            if (r2 == 0) goto L51
            if (r2 == r3) goto L44
            if (r2 != r5) goto L3c
            java.lang.Object r8 = r0.L$3
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r2 = r0.L$1
            java.util.Comparator r3 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L39
            r7 = r10
            r10 = r8
            r8 = r2
            r2 = r7
            goto L91
        L39:
            r8 = move-exception
            goto Lae
        L3c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L44:
            kotlinx.coroutines.channels.ChannelIterator r8 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r9 = r0.L$1
            java.util.Comparator r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L4e
            goto L6c
        L4e:
            r8 = move-exception
            goto Lad
        L51:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.channels.ChannelIterator r10 = r8.iterator()     // Catch: java.lang.Throwable -> La9
            r0.L$0 = r9     // Catch: java.lang.Throwable -> La9
            r0.L$1 = r8     // Catch: java.lang.Throwable -> La9
            r0.L$2 = r10     // Catch: java.lang.Throwable -> La9
            r0.label = r3     // Catch: java.lang.Throwable -> La9
            java.lang.Object r2 = r10.hasNext(r0)     // Catch: java.lang.Throwable -> La9
            if (r2 != r1) goto L67
            return r1
        L67:
            r7 = r9
            r9 = r8
            r8 = r10
            r10 = r2
            r2 = r7
        L6c:
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch: java.lang.Throwable -> L4e
            boolean r10 = r10.booleanValue()     // Catch: java.lang.Throwable -> L4e
            if (r10 != 0) goto L78
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            return r4
        L78:
            java.lang.Object r10 = r8.next()     // Catch: java.lang.Throwable -> L4e
            r3 = r2
            r7 = r9
            r9 = r8
            r8 = r7
        L80:
            r0.L$0 = r3     // Catch: java.lang.Throwable -> La9
            r0.L$1 = r8     // Catch: java.lang.Throwable -> La9
            r0.L$2 = r9     // Catch: java.lang.Throwable -> La9
            r0.L$3 = r10     // Catch: java.lang.Throwable -> La9
            r0.label = r5     // Catch: java.lang.Throwable -> La9
            java.lang.Object r2 = r9.hasNext(r0)     // Catch: java.lang.Throwable -> La9
            if (r2 != r1) goto L91
            return r1
        L91:
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch: java.lang.Throwable -> La9
            boolean r2 = r2.booleanValue()     // Catch: java.lang.Throwable -> La9
            if (r2 == 0) goto La5
            java.lang.Object r2 = r9.next()     // Catch: java.lang.Throwable -> La9
            int r6 = r3.compare(r10, r2)     // Catch: java.lang.Throwable -> La9
            if (r6 >= 0) goto L80
            r10 = r2
            goto L80
        La5:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            return r10
        La9:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
        Lad:
            r2 = r9
        Lae:
            throw r8     // Catch: java.lang.Throwable -> Laf
        Laf:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.maxWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0090 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:40:0x0099 A[Catch: all -> 0x00a9, TRY_LEAVE, TryCatch #0 {all -> 0x00a9, blocks: (B:38:0x0091, B:40:0x0099, B:35:0x0080, B:25:0x0054), top: B:52:0x0054 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x008e -> B:38:0x0091). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object minWith(kotlinx.coroutines.channels.ReceiveChannel r8, java.util.Comparator r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01441
            if (r0 == 0) goto L13
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01441) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            r5 = 2
            if (r2 == 0) goto L51
            if (r2 == r3) goto L44
            if (r2 != r5) goto L3c
            java.lang.Object r8 = r0.L$3
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r2 = r0.L$1
            java.util.Comparator r3 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L39
            r7 = r10
            r10 = r8
            r8 = r2
            r2 = r7
            goto L91
        L39:
            r8 = move-exception
            goto Lae
        L3c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L44:
            kotlinx.coroutines.channels.ChannelIterator r8 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r9 = r0.L$1
            java.util.Comparator r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L4e
            goto L6c
        L4e:
            r8 = move-exception
            goto Lad
        L51:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.channels.ChannelIterator r10 = r8.iterator()     // Catch: java.lang.Throwable -> La9
            r0.L$0 = r9     // Catch: java.lang.Throwable -> La9
            r0.L$1 = r8     // Catch: java.lang.Throwable -> La9
            r0.L$2 = r10     // Catch: java.lang.Throwable -> La9
            r0.label = r3     // Catch: java.lang.Throwable -> La9
            java.lang.Object r2 = r10.hasNext(r0)     // Catch: java.lang.Throwable -> La9
            if (r2 != r1) goto L67
            return r1
        L67:
            r7 = r9
            r9 = r8
            r8 = r10
            r10 = r2
            r2 = r7
        L6c:
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch: java.lang.Throwable -> L4e
            boolean r10 = r10.booleanValue()     // Catch: java.lang.Throwable -> L4e
            if (r10 != 0) goto L78
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            return r4
        L78:
            java.lang.Object r10 = r8.next()     // Catch: java.lang.Throwable -> L4e
            r3 = r2
            r7 = r9
            r9 = r8
            r8 = r7
        L80:
            r0.L$0 = r3     // Catch: java.lang.Throwable -> La9
            r0.L$1 = r8     // Catch: java.lang.Throwable -> La9
            r0.L$2 = r9     // Catch: java.lang.Throwable -> La9
            r0.L$3 = r10     // Catch: java.lang.Throwable -> La9
            r0.label = r5     // Catch: java.lang.Throwable -> La9
            java.lang.Object r2 = r9.hasNext(r0)     // Catch: java.lang.Throwable -> La9
            if (r2 != r1) goto L91
            return r1
        L91:
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch: java.lang.Throwable -> La9
            boolean r2 = r2.booleanValue()     // Catch: java.lang.Throwable -> La9
            if (r2 == 0) goto La5
            java.lang.Object r2 = r9.next()     // Catch: java.lang.Throwable -> La9
            int r6 = r3.compare(r10, r2)     // Catch: java.lang.Throwable -> La9
            if (r6 <= 0) goto L80
            r10 = r2
            goto L80
        La5:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            return r10
        La9:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
        Lad:
            r2 = r9
        Lae:
            throw r8     // Catch: java.lang.Throwable -> Laf
        Laf:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.minWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object none(ReceiveChannel receiveChannel, Continuation continuation) throws Throwable {
        C01451 c01451;
        if (continuation instanceof C01451) {
            c01451 = (C01451) continuation;
            int i = c01451.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01451.label = i - Integer.MIN_VALUE;
            } else {
                c01451 = new C01451(continuation);
            }
        } else {
            c01451 = new C01451(continuation);
        }
        Object objHasNext = c01451.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01451.label;
        boolean z = true;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objHasNext);
                ChannelIterator it = receiveChannel.iterator();
                c01451.L$0 = receiveChannel;
                c01451.label = 1;
                objHasNext = it.hasNext(c01451);
                if (objHasNext == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                receiveChannel = c01451.L$0;
                ResultKt.throwOnFailure(objHasNext);
            }
            if (((Boolean) objHasNext).booleanValue()) {
                z = false;
            }
            Boolean boolBoxBoolean = Boxing.boxBoolean(z);
            ChannelsKt.cancelConsumed(receiveChannel, null);
            return boolBoxBoolean;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                ChannelsKt.cancelConsumed(receiveChannel, th);
                throw th2;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0067 A[Catch: all -> 0x0045, TRY_LEAVE, TryCatch #1 {all -> 0x0045, blocks: (B:20:0x0041, B:29:0x005f, B:31:0x0067, B:42:0x0091, B:43:0x0098), top: B:53:0x0041 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0077 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:34:0x0078  */
    /* JADX WARN: Code duplicated, block: B:42:0x0091 A[Catch: all -> 0x0045, TRY_ENTER, TryCatch #1 {all -> 0x0045, blocks: (B:20:0x0041, B:29:0x005f, B:31:0x0067, B:42:0x0091, B:43:0x0098), top: B:53:0x0041 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object single(ReceiveChannel receiveChannel, Continuation continuation) throws Throwable {
        C01471 c01471;
        ReceiveChannel receiveChannel2;
        Throwable th;
        ReceiveChannel receiveChannel3;
        ChannelIterator channelIterator;
        Object next;
        Object objHasNext;
        Object obj;
        if (continuation instanceof C01471) {
            c01471 = (C01471) continuation;
            int i = c01471.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01471.label = i - Integer.MIN_VALUE;
            } else {
                c01471 = new C01471(continuation);
            }
        } else {
            c01471 = new C01471(continuation);
        }
        Object obj2 = c01471.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01471.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            try {
                ChannelIterator it = receiveChannel.iterator();
                c01471.L$0 = receiveChannel;
                c01471.L$1 = it;
                c01471.label = 1;
                Object objHasNext2 = it.hasNext(c01471);
                if (objHasNext2 == coroutine_suspended) {
                    return coroutine_suspended;
                }
                receiveChannel3 = receiveChannel;
                channelIterator = it;
                obj2 = objHasNext2;
                if (((Boolean) obj2).booleanValue()) {
                    throw new NoSuchElementException("ReceiveChannel is empty.");
                }
                next = channelIterator.next();
                c01471.L$0 = receiveChannel3;
                c01471.L$1 = next;
                c01471.label = 2;
                objHasNext = channelIterator.hasNext(c01471);
                if (objHasNext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                receiveChannel2 = receiveChannel3;
                obj2 = objHasNext;
                obj = next;
            } catch (Throwable th2) {
                receiveChannel2 = receiveChannel;
                th = th2;
                throw th;
            }
        } else if (i2 == 1) {
            channelIterator = (ChannelIterator) c01471.L$1;
            receiveChannel3 = c01471.L$0;
            try {
                ResultKt.throwOnFailure(obj2);
                if (((Boolean) obj2).booleanValue()) {
                    throw new NoSuchElementException("ReceiveChannel is empty.");
                }
                next = channelIterator.next();
                c01471.L$0 = receiveChannel3;
                c01471.L$1 = next;
                c01471.label = 2;
                objHasNext = channelIterator.hasNext(c01471);
                if (objHasNext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                receiveChannel2 = receiveChannel3;
                obj2 = objHasNext;
                obj = next;
            } catch (Throwable th3) {
                th = th3;
                receiveChannel2 = receiveChannel3;
                throw th;
            }
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            obj = c01471.L$1;
            receiveChannel2 = c01471.L$0;
            try {
                ResultKt.throwOnFailure(obj2);
            } catch (Throwable th4) {
                th = th4;
                try {
                    throw th;
                } catch (Throwable th5) {
                    ChannelsKt.cancelConsumed(receiveChannel2, th);
                    throw th5;
                }
            }
        }
        if (((Boolean) obj2).booleanValue()) {
            throw new IllegalArgumentException("ReceiveChannel has more than one element.");
        }
        ChannelsKt.cancelConsumed(receiveChannel2, null);
        return obj;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0089  */
    /* JADX WARN: Code duplicated, block: B:41:0x008d  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object singleOrNull(ReceiveChannel receiveChannel, Continuation continuation) throws Throwable {
        C01481 c01481;
        ReceiveChannel receiveChannel2;
        Throwable th;
        ReceiveChannel receiveChannel3;
        ChannelIterator channelIterator;
        Object obj;
        if (continuation instanceof C01481) {
            c01481 = (C01481) continuation;
            int i = c01481.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01481.label = i - Integer.MIN_VALUE;
            } else {
                c01481 = new C01481(continuation);
            }
        } else {
            c01481 = new C01481(continuation);
        }
        Object obj2 = c01481.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01481.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            try {
                ChannelIterator it = receiveChannel.iterator();
                c01481.L$0 = receiveChannel;
                c01481.L$1 = it;
                c01481.label = 1;
                Object objHasNext = it.hasNext(c01481);
                if (objHasNext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                receiveChannel3 = receiveChannel;
                channelIterator = it;
                obj2 = objHasNext;
            } catch (Throwable th2) {
                receiveChannel2 = receiveChannel;
                th = th2;
                throw th;
            }
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                obj = c01481.L$1;
                receiveChannel2 = c01481.L$0;
                try {
                    ResultKt.throwOnFailure(obj2);
                    if (((Boolean) obj2).booleanValue()) {
                        ChannelsKt.cancelConsumed(receiveChannel2, null);
                        return null;
                    }
                    ChannelsKt.cancelConsumed(receiveChannel2, null);
                    return obj;
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        ChannelsKt.cancelConsumed(receiveChannel2, th);
                        throw th4;
                    }
                }
            }
            channelIterator = (ChannelIterator) c01481.L$1;
            receiveChannel3 = c01481.L$0;
            try {
                ResultKt.throwOnFailure(obj2);
            } catch (Throwable th5) {
                th = th5;
                receiveChannel2 = receiveChannel3;
                throw th;
            }
        }
        if (!((Boolean) obj2).booleanValue()) {
            ChannelsKt.cancelConsumed(receiveChannel3, null);
            return null;
        }
        Object next = channelIterator.next();
        c01481.L$0 = receiveChannel3;
        c01481.L$1 = next;
        c01481.label = 2;
        Object objHasNext2 = channelIterator.hasNext(c01481);
        if (objHasNext2 == coroutine_suspended) {
            return coroutine_suspended;
        }
        receiveChannel2 = receiveChannel3;
        obj2 = objHasNext2;
        obj = next;
        if (((Boolean) obj2).booleanValue()) {
            ChannelsKt.cancelConsumed(receiveChannel2, null);
            return null;
        }
        ChannelsKt.cancelConsumed(receiveChannel2, null);
        return obj;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel take(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01491(i, receiveChannel, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel take$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return take(receiveChannel, i, coroutineContext);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel takeWhile(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01501(receiveChannel, function2, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel takeWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return takeWhile(receiveChannel, coroutineContext, function2);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x005d  */
    /* JADX WARN: Code duplicated, block: B:29:0x006b A[Catch: all -> 0x007e, TryCatch #2 {all -> 0x007e, blocks: (B:13:0x002e, B:27:0x0063, B:29:0x006b, B:34:0x0080, B:18:0x0043), top: B:47:0x0022 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x007d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2, types: [kotlinx.coroutines.channels.ReceiveChannel] */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x007b -> B:14:0x0031). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.Nullable
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object toChannel(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r7, @org.jetbrains.annotations.NotNull C r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super C> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01511
            if (r0 == 0) goto L13
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01511) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 2
            if (r2 == 0) goto L47
            if (r2 == r3) goto L3d
            if (r2 != r4) goto L35
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r8 = r0.L$1
            kotlinx.coroutines.channels.SendChannel r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L7e
        L31:
            r9 = r7
            r7 = r8
            r8 = r2
            goto L4e
        L35:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3d:
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r8 = r0.L$1
            kotlinx.coroutines.channels.SendChannel r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L7e
            goto L62
        L47:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.channels.ChannelIterator r9 = r7.iterator()     // Catch: java.lang.Throwable -> L86
        L4e:
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L86
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L86
            r0.L$2 = r9     // Catch: java.lang.Throwable -> L86
            r0.label = r3     // Catch: java.lang.Throwable -> L86
            java.lang.Object r2 = r9.hasNext(r0)     // Catch: java.lang.Throwable -> L86
            if (r2 != r1) goto L5d
            return r1
        L5d:
            r6 = r8
            r8 = r7
            r7 = r9
            r9 = r2
            r2 = r6
        L62:
            r5 = 0
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L7e
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L7e
            if (r9 == 0) goto L80
            java.lang.Object r9 = r7.next()     // Catch: java.lang.Throwable -> L7e
            r0.L$0 = r2     // Catch: java.lang.Throwable -> L7e
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L7e
            r0.L$2 = r7     // Catch: java.lang.Throwable -> L7e
            r0.label = r4     // Catch: java.lang.Throwable -> L7e
            java.lang.Object r9 = r2.send(r9, r0)     // Catch: java.lang.Throwable -> L7e
            if (r9 != r1) goto L31
            return r1
        L7e:
            r7 = move-exception
            goto L8a
        L80:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7e
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r5)
            return r2
        L86:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L8a:
            throw r7     // Catch: java.lang.Throwable -> L8b
        L8b:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toChannel(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0054 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0055  */
    /* JADX WARN: Code duplicated, block: B:27:0x0062 A[Catch: all -> 0x0073, TryCatch #0 {all -> 0x0073, blocks: (B:25:0x0059, B:27:0x0062, B:21:0x0046, B:28:0x006d, B:20:0x0042), top: B:37:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0055 -> B:14:0x0032). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.Nullable
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object toCollection(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r6, @org.jetbrains.annotations.NotNull C r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super C> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01521
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.C01521) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3f
            if (r2 != r3) goto L37
            kotlinx.coroutines.channels.ChannelIterator r6 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r7 = r0.L$1
            java.util.Collection r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L35
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r2
        L32:
            r2 = r1
            r1 = r5
            goto L59
        L35:
            r6 = move-exception
            goto L77
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L73
        L46:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L73
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L73
            r0.L$2 = r8     // Catch: java.lang.Throwable -> L73
            r0.label = r3     // Catch: java.lang.Throwable -> L73
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> L73
            if (r2 != r1) goto L55
            return r1
        L55:
            r5 = r0
            r0 = r8
            r8 = r2
            goto L32
        L59:
            r4 = 0
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L73
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L73
            if (r8 == 0) goto L6d
            java.lang.Object r8 = r0.next()     // Catch: java.lang.Throwable -> L73
            r7.add(r8)     // Catch: java.lang.Throwable -> L73
            r8 = r0
            r0 = r1
            r1 = r2
            goto L46
        L6d:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L73
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r7
        L73:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L77:
            throw r6     // Catch: java.lang.Throwable -> L78
        L78:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toCollection(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @PublishedApi
    @Nullable
    public static final <E> Object toMutableSet(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull Continuation<? super Set<E>> continuation) {
        return ChannelsKt.toCollection(receiveChannel, new LinkedHashSet(), continuation);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel withIndex(ReceiveChannel receiveChannel, CoroutineContext coroutineContext) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumes(receiveChannel), new C01531(receiveChannel, null), 6, null);
    }

    public static /* synthetic */ ReceiveChannel withIndex$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return withIndex(receiveChannel, coroutineContext);
    }

    public static /* synthetic */ ReceiveChannel zip$default(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.zip(receiveChannel, receiveChannel2, coroutineContext, function2);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0054 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0055  */
    /* JADX WARN: Code duplicated, block: B:27:0x0062 A[Catch: all -> 0x007d, TryCatch #2 {all -> 0x007d, blocks: (B:25:0x0059, B:27:0x0062, B:21:0x0046, B:28:0x0077, B:20:0x0042), top: B:41:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0055 -> B:14:0x0032). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.Nullable
    public static final <K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object toMap(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>> r6, @org.jetbrains.annotations.NotNull M r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super M> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.AnonymousClass2
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.AnonymousClass2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3f
            if (r2 != r3) goto L37
            kotlinx.coroutines.channels.ChannelIterator r6 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r7 = r0.L$1
            java.util.Map r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L35
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r2
        L32:
            r2 = r1
            r1 = r5
            goto L59
        L35:
            r6 = move-exception
            goto L81
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L7d
        L46:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L7d
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L7d
            r0.L$2 = r8     // Catch: java.lang.Throwable -> L7d
            r0.label = r3     // Catch: java.lang.Throwable -> L7d
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> L7d
            if (r2 != r1) goto L55
            return r1
        L55:
            r5 = r0
            r0 = r8
            r8 = r2
            goto L32
        L59:
            r4 = 0
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L7d
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L7d
            if (r8 == 0) goto L77
            java.lang.Object r8 = r0.next()     // Catch: java.lang.Throwable -> L7d
            kotlin.Pair r8 = (kotlin.Pair) r8     // Catch: java.lang.Throwable -> L7d
            java.lang.Object r4 = r8.getFirst()     // Catch: java.lang.Throwable -> L7d
            java.lang.Object r8 = r8.getSecond()     // Catch: java.lang.Throwable -> L7d
            r7.put(r4, r8)     // Catch: java.lang.Throwable -> L7d
            r8 = r0
            r0 = r1
            r1 = r2
            goto L46
        L77:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7d
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r7
        L7d:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L81:
            throw r6     // Catch: java.lang.Throwable -> L82
        L82:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toMap(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @PublishedApi
    @NotNull
    public static final <E, R, V> ReceiveChannel<V> zip(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull ReceiveChannel<? extends R> receiveChannel2, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super R, ? extends V> function2) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, null, ChannelsKt.consumesAll(receiveChannel, receiveChannel2), new C01552(receiveChannel2, receiveChannel, function2, null), 6, null);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0064 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:28:0x0065  */
    /* JADX WARN: Code duplicated, block: B:31:0x0072 A[Catch: all -> 0x0091, TryCatch #0 {all -> 0x0091, blocks: (B:25:0x0056, B:29:0x0069, B:31:0x0072, B:33:0x0078, B:37:0x008b, B:24:0x0052), top: B:46:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0078 A[Catch: all -> 0x0091, TryCatch #0 {all -> 0x0091, blocks: (B:25:0x0056, B:29:0x0069, B:31:0x0072, B:33:0x0078, B:37:0x008b, B:24:0x0052), top: B:46:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0086 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:36:0x0087  */
    /* JADX WARN: Code duplicated, block: B:37:0x008b A[Catch: all -> 0x0091, TRY_LEAVE, TryCatch #0 {all -> 0x0091, blocks: (B:25:0x0056, B:29:0x0069, B:31:0x0072, B:33:0x0078, B:37:0x008b, B:24:0x0052), top: B:46:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [kotlinx.coroutines.channels.SendChannel] */
    /* JADX WARN: Type inference failed for: r8v10, types: [kotlinx.coroutines.channels.SendChannel] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [kotlinx.coroutines.channels.ReceiveChannel] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.lang.Object, kotlinx.coroutines.channels.SendChannel] */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel r7, kotlinx.coroutines.channels.SendChannel r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.AnonymousClass3
            if (r0 == 0) goto L13
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.AnonymousClass3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 2
            if (r2 == 0) goto L4f
            if (r2 == r3) goto L3d
            if (r2 != r4) goto L35
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r8 = r0.L$1
            kotlinx.coroutines.channels.SendChannel r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L4d
            r9 = r7
            r7 = r8
            r8 = r2
            goto L56
        L35:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3d:
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r8 = r0.L$1
            kotlinx.coroutines.channels.SendChannel r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L4d
            r6 = r0
            r0 = r7
            r7 = r8
            r8 = r2
        L4a:
            r2 = r1
            r1 = r6
            goto L69
        L4d:
            r7 = move-exception
            goto L95
        L4f:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.channels.ChannelIterator r9 = r7.iterator()     // Catch: java.lang.Throwable -> L91
        L56:
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L91
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L91
            r0.L$2 = r9     // Catch: java.lang.Throwable -> L91
            r0.label = r3     // Catch: java.lang.Throwable -> L91
            java.lang.Object r2 = r9.hasNext(r0)     // Catch: java.lang.Throwable -> L91
            if (r2 != r1) goto L65
            return r1
        L65:
            r6 = r0
            r0 = r9
            r9 = r2
            goto L4a
        L69:
            r5 = 0
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L91
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L91
            if (r9 == 0) goto L8b
            java.lang.Object r9 = r0.next()     // Catch: java.lang.Throwable -> L91
            if (r9 == 0) goto L87
            r1.L$0 = r8     // Catch: java.lang.Throwable -> L91
            r1.L$1 = r7     // Catch: java.lang.Throwable -> L91
            r1.L$2 = r0     // Catch: java.lang.Throwable -> L91
            r1.label = r4     // Catch: java.lang.Throwable -> L91
            java.lang.Object r9 = r8.send(r9, r1)     // Catch: java.lang.Throwable -> L91
            if (r9 != r2) goto L87
            return r2
        L87:
            r9 = r0
            r0 = r1
            r1 = r2
            goto L56
        L8b:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L91
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            return r8
        L91:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L95:
            throw r7     // Catch: java.lang.Throwable -> L96
        L96:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
