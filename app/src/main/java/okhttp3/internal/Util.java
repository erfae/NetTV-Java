package okhttp3.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.common.net.HttpHeaders;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Header;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Sink;
import okio.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Util.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000¸\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u001a\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u0017\u001a'\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00112\u0012\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0!\"\u00020\"¢\u0006\u0002\u0010#\u001a\u001a\u0010$\u001a\u00020\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0&H\u0086\bø\u0001\u0000\u001a-\u0010'\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)2\u0012\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u0002H)0!\"\u0002H)H\u0007¢\u0006\u0002\u0010+\u001a1\u0010,\u001a\u0004\u0018\u0001H)\"\u0004\b\u0000\u0010)2\u0006\u0010-\u001a\u00020\"2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H)0/2\u0006\u00100\u001a\u00020\u0011¢\u0006\u0002\u00101\u001a\u0016\u00102\u001a\u0002032\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u000f\u001a\"\u00105\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u00112\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0&H\u0086\bø\u0001\u0000\u001a%\u00106\u001a\u00020\u001b\"\u0004\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H7082\u0006\u00109\u001a\u0002H7H\u0000¢\u0006\u0002\u0010:\u001a\u0015\u0010;\u001a\u00020\u0014*\u00020<2\u0006\u0010=\u001a\u00020\u0014H\u0086\u0004\u001a\u0015\u0010;\u001a\u00020\u0017*\u00020\u00142\u0006\u0010=\u001a\u00020\u0017H\u0086\u0004\u001a\u0015\u0010;\u001a\u00020\u0014*\u00020>2\u0006\u0010=\u001a\u00020\u0014H\u0086\u0004\u001a\n\u0010?\u001a\u00020@*\u00020A\u001a\r\u0010B\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\r\u0010C\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\n\u0010D\u001a\u00020\u000f*\u00020\u0011\u001a\u0012\u0010E\u001a\u00020\u000f*\u00020F2\u0006\u0010G\u001a\u00020F\u001a\n\u0010H\u001a\u00020\u001b*\u00020I\u001a\n\u0010H\u001a\u00020\u001b*\u00020J\u001a\n\u0010H\u001a\u00020\u001b*\u00020K\u001a#\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00110!*\b\u0012\u0004\u0012\u00020\u00110!2\u0006\u0010M\u001a\u00020\u0011¢\u0006\u0002\u0010N\u001a&\u0010O\u001a\u00020\u0014*\u00020\u00112\u0006\u0010P\u001a\u00020Q2\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a&\u0010O\u001a\u00020\u0014*\u00020\u00112\u0006\u0010T\u001a\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u001a\u0010U\u001a\u00020\u000f*\u00020V2\u0006\u0010W\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0019\u001a;\u0010Y\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0Z2\u0017\u0010[\u001a\u0013\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020\u000f0\\¢\u0006\u0002\b]H\u0086\bø\u0001\u0000\u001a5\u0010^\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00110!2\u000e\u0010G\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010!2\u000e\u0010_\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110`¢\u0006\u0002\u0010a\u001a\n\u0010b\u001a\u00020\u0017*\u00020c\u001a+\u0010d\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00110!2\u0006\u0010M\u001a\u00020\u00112\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00110`¢\u0006\u0002\u0010e\u001a\n\u0010f\u001a\u00020\u0014*\u00020\u0011\u001a\u001e\u0010g\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u001e\u0010h\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u0014\u0010i\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u0014\u001a9\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00110!*\b\u0012\u0004\u0012\u00020\u00110!2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00110!2\u000e\u0010_\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110`¢\u0006\u0002\u0010k\u001a\u0012\u0010l\u001a\u00020\u000f*\u00020m2\u0006\u0010n\u001a\u00020o\u001a\u0012\u0010p\u001a\u00020\u000f*\u00020K2\u0006\u0010q\u001a\u00020r\u001a\r\u0010s\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\r\u0010t\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\n\u0010u\u001a\u00020\u0014*\u00020Q\u001a\n\u0010v\u001a\u00020\u0011*\u00020K\u001a\u0012\u0010w\u001a\u00020x*\u00020r2\u0006\u0010y\u001a\u00020x\u001a\n\u0010z\u001a\u00020\u0014*\u00020r\u001a\u0012\u0010{\u001a\u00020\u0014*\u00020|2\u0006\u0010}\u001a\u00020<\u001a\u001a\u0010{\u001a\u00020\u000f*\u00020V2\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0019\u001a\u0010\u0010~\u001a\b\u0012\u0004\u0012\u00020\u007f0(*\u00020\u0003\u001a\u0011\u0010\u0080\u0001\u001a\u00020\u0003*\b\u0012\u0004\u0012\u00020\u007f0(\u001a\u000b\u0010\u0081\u0001\u001a\u00020\u0011*\u00020\u0014\u001a\u000b\u0010\u0081\u0001\u001a\u00020\u0011*\u00020\u0017\u001a\u0016\u0010\u0082\u0001\u001a\u00020\u0011*\u00020F2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u000f\u001a\u001d\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0(\u001a7\u0010\u0085\u0001\u001a\u0011\u0012\u0005\u0012\u0003H\u0087\u0001\u0012\u0005\u0012\u0003H\u0088\u00010\u0086\u0001\"\u0005\b\u0000\u0010\u0087\u0001\"\u0005\b\u0001\u0010\u0088\u0001*\u0011\u0012\u0005\u0012\u0003H\u0087\u0001\u0012\u0005\u0012\u0003H\u0088\u00010\u0086\u0001\u001a\u0014\u0010\u0089\u0001\u001a\u00020\u0017*\u00020\u00112\u0007\u0010\u008a\u0001\u001a\u00020\u0017\u001a\u0016\u0010\u008b\u0001\u001a\u00020\u0014*\u0004\u0018\u00010\u00112\u0007\u0010\u008a\u0001\u001a\u00020\u0014\u001a\u001f\u0010\u008c\u0001\u001a\u00020\u0011*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u000e\u0010\u008d\u0001\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a'\u0010\u008e\u0001\u001a\u00030\u008f\u0001*\b0\u0090\u0001j\u0003`\u0091\u00012\u0013\u0010\u0092\u0001\u001a\u000e\u0012\n\u0012\b0\u0090\u0001j\u0003`\u0091\u00010(\u001a\u0015\u0010\u0093\u0001\u001a\u00020\u001b*\u00030\u0094\u00012\u0007\u0010\u0095\u0001\u001a\u00020\u0014\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0010\u001a\u00020\u00118\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0096\u0001"}, d2 = {"EMPTY_BYTE_ARRAY", "", "EMPTY_HEADERS", "Lokhttp3/Headers;", "EMPTY_REQUEST", "Lokhttp3/RequestBody;", "EMPTY_RESPONSE", "Lokhttp3/ResponseBody;", "UNICODE_BOMS", "Lokio/Options;", "UTC", "Ljava/util/TimeZone;", "VERIFY_AS_IP_ADDRESS", "Lkotlin/text/Regex;", "assertionsEnabled", "", "okHttpName", "", "userAgent", "checkDuration", "", "name", TypedValues.TransitionType.S_DURATION, "", "unit", "Ljava/util/concurrent/TimeUnit;", "checkOffsetAndCount", "", "arrayLength", TypedValues.CycleType.S_WAVE_OFFSET, "count", "format", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "ignoreIoExceptions", "block", "Lkotlin/Function0;", "immutableListOf", "", ExifInterface.GPS_DIRECTION_TRUE, "elements", "([Ljava/lang/Object;)Ljava/util/List;", "readFieldOrNull", "instance", "fieldType", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "daemon", "threadName", "addIfAbsent", ExifInterface.LONGITUDE_EAST, "", "element", "(Ljava/util/List;Ljava/lang/Object;)V", "and", "", "mask", "", "asFactory", "Lokhttp3/EventListener$Factory;", "Lokhttp3/EventListener;", "assertThreadDoesntHoldLock", "assertThreadHoldsLock", "canParseAsIpAddress", "canReuseConnectionFor", "Lokhttp3/HttpUrl;", "other", "closeQuietly", "Ljava/io/Closeable;", "Ljava/net/ServerSocket;", "Ljava/net/Socket;", "concat", "value", "([Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "delimiterOffset", TtmlNode.RUBY_DELIMITER, "", "startIndex", "endIndex", "delimiters", "discard", "Lokio/Source;", "timeout", "timeUnit", "filterList", "", "predicate", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "hasIntersection", "comparator", "Ljava/util/Comparator;", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)Z", "headersContentLength", "Lokhttp3/Response;", "indexOf", "([Ljava/lang/String;Ljava/lang/String;Ljava/util/Comparator;)I", "indexOfControlOrNonAscii", "indexOfFirstNonAsciiWhitespace", "indexOfLastNonAsciiWhitespace", "indexOfNonWhitespace", "intersect", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)[Ljava/lang/String;", "isCivilized", "Lokhttp3/internal/io/FileSystem;", "file", "Ljava/io/File;", "isHealthy", "source", "Lokio/BufferedSource;", "notify", "notifyAll", "parseHexDigit", "peerName", "readBomAsCharset", "Ljava/nio/charset/Charset;", "default", "readMedium", "skipAll", "Lokio/Buffer;", "b", "toHeaderList", "Lokhttp3/internal/http2/Header;", "toHeaders", "toHexString", "toHostHeader", "includeDefaultPort", "toImmutableList", "toImmutableMap", "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "toLongOrDefault", "defaultValue", "toNonNegativeInt", "trimSubstring", "wait", "withSuppressed", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "suppressed", "writeMedium", "Lokio/BufferedSink;", "medium", "okhttp"}, k = 2, mv = {1, 4, 1})
@JvmName(name = "Util")
public final class Util {

    @JvmField
    @NotNull
    public static final byte[] EMPTY_BYTE_ARRAY;

    @JvmField
    @NotNull
    public static final Headers EMPTY_HEADERS = Headers.INSTANCE.of(new String[0]);

    @JvmField
    @NotNull
    public static final RequestBody EMPTY_REQUEST;

    @JvmField
    @NotNull
    public static final ResponseBody EMPTY_RESPONSE;
    private static final Options UNICODE_BOMS;

    @JvmField
    @NotNull
    public static final TimeZone UTC;
    private static final Regex VERIFY_AS_IP_ADDRESS;

    @JvmField
    public static final boolean assertionsEnabled;

    @JvmField
    @NotNull
    public static final String okHttpName;

    @NotNull
    public static final String userAgent = "okhttp/5.0.0-alpha.2";

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        EMPTY_RESPONSE = ResponseBody.Companion.create$default(ResponseBody.INSTANCE, bArr, (MediaType) null, 1, (Object) null);
        EMPTY_REQUEST = RequestBody.Companion.create$default(RequestBody.INSTANCE, bArr, (MediaType) null, 0, 0, 7, (Object) null);
        Options.Companion companion = Options.INSTANCE;
        ByteString.Companion companion2 = ByteString.INSTANCE;
        UNICODE_BOMS = companion.of(companion2.decodeHex("efbbbf"), companion2.decodeHex("feff"), companion2.decodeHex("fffe"), companion2.decodeHex("0000ffff"), companion2.decodeHex("ffff0000"));
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        Intrinsics.checkNotNull(timeZone);
        UTC = timeZone;
        VERIFY_AS_IP_ADDRESS = new Regex("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
        assertionsEnabled = false;
        String name = OkHttpClient.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "OkHttpClient::class.java.name");
        okHttpName = StringsKt.removeSuffix(StringsKt.removePrefix(name, (CharSequence) "okhttp3."));
    }

    public static final <E> void addIfAbsent(@NotNull List<E> addIfAbsent, E e) {
        Intrinsics.checkNotNullParameter(addIfAbsent, "$this$addIfAbsent");
        if (addIfAbsent.contains(e)) {
            return;
        }
        addIfAbsent.add(e);
    }

    public static final int and(byte b, int i) {
        return b & i;
    }

    public static final int and(short s, int i) {
        return s & i;
    }

    public static final long and(int i, long j) {
        return ((long) i) & j;
    }

    @NotNull
    public static final EventListener.Factory asFactory(@NotNull final EventListener asFactory) {
        Intrinsics.checkNotNullParameter(asFactory, "$this$asFactory");
        return new EventListener.Factory() { // from class: okhttp3.internal.Util.asFactory.1
            @Override // okhttp3.EventListener.Factory
            @NotNull
            public final EventListener create(@NotNull Call it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return asFactory;
            }
        };
    }

    public static final void assertThreadDoesntHoldLock(@NotNull Object assertThreadDoesntHoldLock) {
        Intrinsics.checkNotNullParameter(assertThreadDoesntHoldLock, "$this$assertThreadDoesntHoldLock");
        if (assertionsEnabled && Thread.holdsLock(assertThreadDoesntHoldLock)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Thread ");
            Thread threadCurrentThread = Thread.currentThread();
            Intrinsics.checkNotNullExpressionValue(threadCurrentThread, "Thread.currentThread()");
            sbM.append(threadCurrentThread.getName());
            sbM.append(" MUST NOT hold lock on ");
            sbM.append(assertThreadDoesntHoldLock);
            throw new AssertionError(sbM.toString());
        }
    }

    public static final void assertThreadHoldsLock(@NotNull Object assertThreadHoldsLock) {
        Intrinsics.checkNotNullParameter(assertThreadHoldsLock, "$this$assertThreadHoldsLock");
        if (!assertionsEnabled || Thread.holdsLock(assertThreadHoldsLock)) {
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Thread ");
        Thread threadCurrentThread = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(threadCurrentThread, "Thread.currentThread()");
        sbM.append(threadCurrentThread.getName());
        sbM.append(" MUST hold lock on ");
        sbM.append(assertThreadHoldsLock);
        throw new AssertionError(sbM.toString());
    }

    public static final boolean canParseAsIpAddress(@NotNull String canParseAsIpAddress) {
        Intrinsics.checkNotNullParameter(canParseAsIpAddress, "$this$canParseAsIpAddress");
        return VERIFY_AS_IP_ADDRESS.matches(canParseAsIpAddress);
    }

    public static final boolean canReuseConnectionFor(@NotNull HttpUrl canReuseConnectionFor, @NotNull HttpUrl other) {
        Intrinsics.checkNotNullParameter(canReuseConnectionFor, "$this$canReuseConnectionFor");
        Intrinsics.checkNotNullParameter(other, "other");
        return Intrinsics.areEqual(canReuseConnectionFor.host(), other.host()) && canReuseConnectionFor.port() == other.port() && Intrinsics.areEqual(canReuseConnectionFor.scheme(), other.scheme());
    }

    public static final int checkDuration(@NotNull String name, long j, @Nullable TimeUnit timeUnit) {
        Intrinsics.checkNotNullParameter(name, "name");
        boolean z = true;
        if (!(j >= 0)) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(name, " < 0").toString());
        }
        if (!(timeUnit != null)) {
            throw new IllegalStateException("unit == null".toString());
        }
        long millis = timeUnit.toMillis(j);
        if (!(millis <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(name, " too large.").toString());
        }
        if (millis == 0 && j > 0) {
            z = false;
        }
        if (z) {
            return (int) millis;
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(name, " too small.").toString());
    }

    public static final void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static final void closeQuietly(@NotNull Closeable closeQuietly) {
        Intrinsics.checkNotNullParameter(closeQuietly, "$this$closeQuietly");
        try {
            closeQuietly.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception unused) {
        }
    }

    @NotNull
    public static final String[] concat(@NotNull String[] concat, @NotNull String value) {
        Intrinsics.checkNotNullParameter(concat, "$this$concat");
        Intrinsics.checkNotNullParameter(value, "value");
        Object[] objArrCopyOf = Arrays.copyOf(concat, concat.length + 1);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        String[] strArr = (String[]) objArrCopyOf;
        strArr[ArraysKt.getLastIndex(strArr)] = value;
        return strArr;
    }

    public static final int delimiterOffset(@NotNull String delimiterOffset, @NotNull String delimiters, int i, int i2) {
        Intrinsics.checkNotNullParameter(delimiterOffset, "$this$delimiterOffset");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        while (i < i2) {
            if (StringsKt.contains$default(delimiters, delimiterOffset.charAt(i))) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int delimiterOffset$default(String str, String str2, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = str.length();
        }
        return delimiterOffset(str, str2, i, i2);
    }

    public static final boolean discard(@NotNull Source discard, int i, @NotNull TimeUnit timeUnit) {
        Intrinsics.checkNotNullParameter(discard, "$this$discard");
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        try {
            return skipAll(discard, i, timeUnit);
        } catch (IOException unused) {
            return false;
        }
    }

    @NotNull
    public static final <T> List<T> filterList(@NotNull Iterable<? extends T> filterList, @NotNull Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filterList, "$this$filterList");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        List<T> listEmptyList = CollectionsKt.emptyList();
        for (T t : filterList) {
            if (predicate.invoke(t).booleanValue()) {
                if (listEmptyList.isEmpty()) {
                    listEmptyList = new ArrayList<>();
                }
                TypeIntrinsics.asMutableList(listEmptyList).add(t);
            }
        }
        return listEmptyList;
    }

    @NotNull
    public static final String format(@NotNull String format, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.US;
        Object[] objArrCopyOf = Arrays.copyOf(args, args.length);
        String str = String.format(locale, format, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        return str;
    }

    public static final boolean hasIntersection(@NotNull String[] hasIntersection, @Nullable String[] strArr, @NotNull Comparator<? super String> comparator) {
        Intrinsics.checkNotNullParameter(hasIntersection, "$this$hasIntersection");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        if (!(hasIntersection.length == 0) && strArr != null) {
            if (!(strArr.length == 0)) {
                for (String str : hasIntersection) {
                    for (String str2 : strArr) {
                        if (comparator.compare(str, str2) == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static final long headersContentLength(@NotNull Response headersContentLength) {
        Intrinsics.checkNotNullParameter(headersContentLength, "$this$headersContentLength");
        String str = headersContentLength.headers().get(HttpHeaders.CONTENT_LENGTH);
        if (str != null) {
            return toLongOrDefault(str, -1L);
        }
        return -1L;
    }

    public static final void ignoreIoExceptions(@NotNull Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            block.invoke();
        } catch (IOException unused) {
        }
    }

    @SafeVarargs
    @NotNull
    public static final <T> List<T> immutableListOf(@NotNull T... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Object[] objArr = (Object[]) elements.clone();
        List<T> listUnmodifiableList = Collections.unmodifiableList(CollectionsKt.listOf(Arrays.copyOf(objArr, objArr.length)));
        Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "Collections.unmodifiable…istOf(*elements.clone()))");
        return listUnmodifiableList;
    }

    public static final int indexOf(@NotNull String[] indexOf, @NotNull String value, @NotNull Comparator<String> comparator) {
        Intrinsics.checkNotNullParameter(indexOf, "$this$indexOf");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        int length = indexOf.length;
        for (int i = 0; i < length; i++) {
            if (comparator.compare(indexOf[i], value) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static final int indexOfControlOrNonAscii(@NotNull String indexOfControlOrNonAscii) {
        Intrinsics.checkNotNullParameter(indexOfControlOrNonAscii, "$this$indexOfControlOrNonAscii");
        int length = indexOfControlOrNonAscii.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = indexOfControlOrNonAscii.charAt(i);
            if (Intrinsics.compare((int) cCharAt, 31) <= 0 || Intrinsics.compare((int) cCharAt, 127) >= 0) {
                return i;
            }
        }
        return -1;
    }

    public static final int indexOfFirstNonAsciiWhitespace(@NotNull String indexOfFirstNonAsciiWhitespace, int i, int i2) {
        Intrinsics.checkNotNullParameter(indexOfFirstNonAsciiWhitespace, "$this$indexOfFirstNonAsciiWhitespace");
        while (i < i2) {
            char cCharAt = indexOfFirstNonAsciiWhitespace.charAt(i);
            if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int indexOfFirstNonAsciiWhitespace$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return indexOfFirstNonAsciiWhitespace(str, i, i2);
    }

    public static final int indexOfLastNonAsciiWhitespace(@NotNull String indexOfLastNonAsciiWhitespace, int i, int i2) {
        Intrinsics.checkNotNullParameter(indexOfLastNonAsciiWhitespace, "$this$indexOfLastNonAsciiWhitespace");
        int i3 = i2 - 1;
        if (i3 >= i) {
            while (true) {
                char cCharAt = indexOfLastNonAsciiWhitespace.charAt(i3);
                if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                    return i3 + 1;
                }
                if (i3 == i) {
                    break;
                }
                i3--;
            }
        }
        return i;
    }

    public static /* synthetic */ int indexOfLastNonAsciiWhitespace$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return indexOfLastNonAsciiWhitespace(str, i, i2);
    }

    public static final int indexOfNonWhitespace(@NotNull String indexOfNonWhitespace, int i) {
        Intrinsics.checkNotNullParameter(indexOfNonWhitespace, "$this$indexOfNonWhitespace");
        int length = indexOfNonWhitespace.length();
        while (i < length) {
            char cCharAt = indexOfNonWhitespace.charAt(i);
            if (cCharAt != ' ' && cCharAt != '\t') {
                return i;
            }
            i++;
        }
        return indexOfNonWhitespace.length();
    }

    public static /* synthetic */ int indexOfNonWhitespace$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return indexOfNonWhitespace(str, i);
    }

    @NotNull
    public static final String[] intersect(@NotNull String[] intersect, @NotNull String[] other, @NotNull Comparator<? super String> comparator) {
        Intrinsics.checkNotNullParameter(intersect, "$this$intersect");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        ArrayList arrayList = new ArrayList();
        for (String str : intersect) {
            for (String str2 : other) {
                if (comparator.compare(str, str2) == 0) {
                    arrayList.add(str);
                    break;
                }
            }
        }
        Object[] array = arrayList.toArray(new String[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
        return (String[]) array;
    }

    public static final boolean isCivilized(@NotNull FileSystem isCivilized, @NotNull File file) throws IOException {
        Intrinsics.checkNotNullParameter(isCivilized, "$this$isCivilized");
        Intrinsics.checkNotNullParameter(file, "file");
        Sink sink = isCivilized.sink(file);
        try {
            try {
                isCivilized.delete(file);
                CloseableKt.closeFinally(sink, null);
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(sink, th);
                    throw th2;
                }
            }
        } catch (IOException unused) {
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(sink, null);
            isCivilized.delete(file);
            return false;
        }
    }

    public static final boolean isHealthy(@NotNull Socket isHealthy, @NotNull BufferedSource source) {
        Intrinsics.checkNotNullParameter(isHealthy, "$this$isHealthy");
        Intrinsics.checkNotNullParameter(source, "source");
        try {
            int soTimeout = isHealthy.getSoTimeout();
            try {
                isHealthy.setSoTimeout(1);
                return !source.exhausted();
            } finally {
                isHealthy.setSoTimeout(soTimeout);
            }
        } catch (SocketTimeoutException unused) {
            return true;
        } catch (IOException unused2) {
            return false;
        }
    }

    public static final void notify(@NotNull Object notify) {
        Intrinsics.checkNotNullParameter(notify, "$this$notify");
        notify.notify();
    }

    public static final void notifyAll(@NotNull Object notifyAll) {
        Intrinsics.checkNotNullParameter(notifyAll, "$this$notifyAll");
        notifyAll.notifyAll();
    }

    public static final int parseHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return c - '0';
        }
        char c2 = 'a';
        if ('a' > c || 'f' < c) {
            c2 = 'A';
            if ('A' > c || 'F' < c) {
                return -1;
            }
        }
        return (c - c2) + 10;
    }

    @NotNull
    public static final String peerName(@NotNull Socket peerName) {
        Intrinsics.checkNotNullParameter(peerName, "$this$peerName");
        SocketAddress remoteSocketAddress = peerName.getRemoteSocketAddress();
        if (!(remoteSocketAddress instanceof InetSocketAddress)) {
            return remoteSocketAddress.toString();
        }
        String hostName = ((InetSocketAddress) remoteSocketAddress).getHostName();
        Intrinsics.checkNotNullExpressionValue(hostName, "address.hostName");
        return hostName;
    }

    @NotNull
    public static final Charset readBomAsCharset(@NotNull BufferedSource readBomAsCharset, @NotNull Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(readBomAsCharset, "$this$readBomAsCharset");
        Intrinsics.checkNotNullParameter(charset, "default");
        int iSelect = readBomAsCharset.select(UNICODE_BOMS);
        if (iSelect == -1) {
            return charset;
        }
        if (iSelect == 0) {
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            return UTF_8;
        }
        if (iSelect == 1) {
            Charset UTF_16BE = StandardCharsets.UTF_16BE;
            Intrinsics.checkNotNullExpressionValue(UTF_16BE, "UTF_16BE");
            return UTF_16BE;
        }
        if (iSelect == 2) {
            Charset UTF_16LE = StandardCharsets.UTF_16LE;
            Intrinsics.checkNotNullExpressionValue(UTF_16LE, "UTF_16LE");
            return UTF_16LE;
        }
        if (iSelect == 3) {
            return Charsets.INSTANCE.UTF32_BE();
        }
        if (iSelect == 4) {
            return Charsets.INSTANCE.UTF32_LE();
        }
        throw new AssertionError();
    }

    @Nullable
    public static final <T> T readFieldOrNull(@NotNull Object instance, @NotNull Class<T> fieldType, @NotNull String fieldName) {
        Object fieldOrNull;
        Intrinsics.checkNotNullParameter(instance, "instance");
        Intrinsics.checkNotNullParameter(fieldType, "fieldType");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Class<?> superclass = instance.getClass();
        while (true) {
            T tCast = null;
            if (!(!Intrinsics.areEqual(superclass, Object.class))) {
                if (!(!Intrinsics.areEqual(fieldName, "delegate")) || (fieldOrNull = readFieldOrNull(instance, Object.class, "delegate")) == null) {
                    return null;
                }
                return (T) readFieldOrNull(fieldOrNull, fieldType, fieldName);
            }
            try {
                Field field = superclass.getDeclaredField(fieldName);
                Intrinsics.checkNotNullExpressionValue(field, "field");
                field.setAccessible(true);
                Object obj = field.get(instance);
                if (fieldType.isInstance(obj)) {
                    tCast = fieldType.cast(obj);
                }
                return tCast;
            } catch (NoSuchFieldException unused) {
                superclass = superclass.getSuperclass();
                Intrinsics.checkNotNullExpressionValue(superclass, "c.superclass");
            }
        }
    }

    public static final int readMedium(@NotNull BufferedSource readMedium) throws IOException {
        Intrinsics.checkNotNullParameter(readMedium, "$this$readMedium");
        return and(readMedium.readByte(), 255) | (and(readMedium.readByte(), 255) << 16) | (and(readMedium.readByte(), 255) << 8);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x005b A[PHI: r12
  0x005b: PHI (r12v6 boolean) = (r12v5 boolean), (r12v10 boolean) binds: [B:23:0x007e, B:13:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Undo finally extract visitor
    java.lang.NullPointerException: Cannot invoke "Object.hashCode()" because "this.second" is null
    	at jadx.core.utils.Pair.hashCode(Pair.java:35)
    	at java.base/java.util.HashMap.hash(Unknown Source)
    	at java.base/java.util.HashMap.getNode(Unknown Source)
    	at java.base/java.util.HashMap.containsKey(Unknown Source)
    	at jadx.core.dex.visitors.finaly.traverser.state.TraverserGlobalCommonState.hasBlocksBeenCached(TraverserGlobalCommonState.java:35)
    	at jadx.core.dex.visitors.finaly.traverser.handlers.MergePathActivePathTraverserHandler.handle(MergePathActivePathTraverserHandler.java:174)
    	at jadx.core.dex.visitors.finaly.traverser.handlers.AbstractActivePathTraverserHandler.process(AbstractActivePathTraverserHandler.java:19)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.processHandlerImplementations(TraverserController.java:43)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.advance(TraverserController.java:156)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.process(TraverserController.java:79)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.findCommonInsns(MarkFinallyVisitor.java:404)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.extractFinally(MarkFinallyVisitor.java:284)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.processTryBlock(MarkFinallyVisitor.java:202)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.visit(MarkFinallyVisitor.java:135)
     */
    public static final boolean skipAll(@NotNull Source skipAll, int i, @NotNull TimeUnit timeUnit) throws IOException {
        boolean z;
        Intrinsics.checkNotNullParameter(skipAll, "$this$skipAll");
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        long jNanoTime = System.nanoTime();
        long jDeadlineNanoTime = skipAll.timeout().getHasDeadline() ? skipAll.timeout().deadlineNanoTime() - jNanoTime : Long.MAX_VALUE;
        skipAll.timeout().deadlineNanoTime(Math.min(jDeadlineNanoTime, timeUnit.toNanos(i)) + jNanoTime);
        try {
            Buffer buffer = new Buffer();
            while (skipAll.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1) {
                buffer.clear();
            }
            z = true;
            if (jDeadlineNanoTime == Long.MAX_VALUE) {
                skipAll.timeout().clearDeadline();
            } else {
                skipAll.timeout().deadlineNanoTime(jNanoTime + jDeadlineNanoTime);
            }
        } catch (InterruptedIOException unused) {
            z = false;
            if (jDeadlineNanoTime != Long.MAX_VALUE) {
                skipAll.timeout().deadlineNanoTime(jNanoTime + jDeadlineNanoTime);
            }
        } catch (Throwable th) {
            if (jDeadlineNanoTime == Long.MAX_VALUE) {
                skipAll.timeout().clearDeadline();
            } else {
                skipAll.timeout().deadlineNanoTime(jNanoTime + jDeadlineNanoTime);
            }
            throw th;
        }
        return z;
    }

    @NotNull
    public static final ThreadFactory threadFactory(@NotNull final String name, final boolean z) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new ThreadFactory() { // from class: okhttp3.internal.Util.threadFactory.1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, name);
                thread.setDaemon(z);
                return thread;
            }
        };
    }

    public static final void threadName(@NotNull String name, @NotNull Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(block, "block");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(currentThread, "currentThread");
        String name2 = currentThread.getName();
        currentThread.setName(name);
        try {
            block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            currentThread.setName(name2);
            InlineMarker.finallyEnd(1);
        }
    }

    @NotNull
    public static final List<Header> toHeaderList(@NotNull Headers toHeaderList) {
        Intrinsics.checkNotNullParameter(toHeaderList, "$this$toHeaderList");
        IntRange intRangeUntil = RangesKt.until(0, toHeaderList.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            arrayList.add(new Header(toHeaderList.name(iNextInt), toHeaderList.value(iNextInt)));
        }
        return arrayList;
    }

    @NotNull
    public static final Headers toHeaders(@NotNull List<Header> toHeaders) {
        Intrinsics.checkNotNullParameter(toHeaders, "$this$toHeaders");
        Headers.Builder builder = new Headers.Builder();
        for (Header header : toHeaders) {
            builder.addLenient$okhttp(header.getName().utf8(), header.getValue().utf8());
        }
        return builder.build();
    }

    @NotNull
    public static final String toHexString(long j) {
        String hexString = Long.toHexString(j);
        Intrinsics.checkNotNullExpressionValue(hexString, "java.lang.Long.toHexString(this)");
        return hexString;
    }

    @NotNull
    public static final String toHostHeader(@NotNull HttpUrl toHostHeader, boolean z) {
        String strHost;
        Intrinsics.checkNotNullParameter(toHostHeader, "$this$toHostHeader");
        if (StringsKt.contains$default(toHostHeader.host(), ":")) {
            strHost = '[' + toHostHeader.host() + ']';
        } else {
            strHost = toHostHeader.host();
        }
        if (!z && toHostHeader.port() == HttpUrl.INSTANCE.defaultPort(toHostHeader.scheme())) {
            return strHost;
        }
        return strHost + ':' + toHostHeader.port();
    }

    public static /* synthetic */ String toHostHeader$default(HttpUrl httpUrl, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toHostHeader(httpUrl, z);
    }

    @NotNull
    public static final <T> List<T> toImmutableList(@NotNull List<? extends T> toImmutableList) {
        Intrinsics.checkNotNullParameter(toImmutableList, "$this$toImmutableList");
        List<T> listUnmodifiableList = Collections.unmodifiableList(CollectionsKt.toMutableList((Collection) toImmutableList));
        Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "Collections.unmodifiableList(toMutableList())");
        return listUnmodifiableList;
    }

    @NotNull
    public static final <K, V> Map<K, V> toImmutableMap(@NotNull Map<K, ? extends V> toImmutableMap) {
        Intrinsics.checkNotNullParameter(toImmutableMap, "$this$toImmutableMap");
        if (toImmutableMap.isEmpty()) {
            return MapsKt.emptyMap();
        }
        Map<K, V> mapUnmodifiableMap = Collections.unmodifiableMap(new LinkedHashMap(toImmutableMap));
        Intrinsics.checkNotNullExpressionValue(mapUnmodifiableMap, "Collections.unmodifiableMap(LinkedHashMap(this))");
        return mapUnmodifiableMap;
    }

    public static final long toLongOrDefault(@NotNull String toLongOrDefault, long j) {
        Intrinsics.checkNotNullParameter(toLongOrDefault, "$this$toLongOrDefault");
        try {
            return Long.parseLong(toLongOrDefault);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static final int toNonNegativeInt(@Nullable String str, int i) {
        if (str != null) {
            try {
                long j = Long.parseLong(str);
                if (j > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                if (j < 0) {
                    return 0;
                }
                return (int) j;
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    @NotNull
    public static final String trimSubstring(@NotNull String trimSubstring, int i, int i2) {
        Intrinsics.checkNotNullParameter(trimSubstring, "$this$trimSubstring");
        int iIndexOfFirstNonAsciiWhitespace = indexOfFirstNonAsciiWhitespace(trimSubstring, i, i2);
        String strSubstring = trimSubstring.substring(iIndexOfFirstNonAsciiWhitespace, indexOfLastNonAsciiWhitespace(trimSubstring, iIndexOfFirstNonAsciiWhitespace, i2));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public static /* synthetic */ String trimSubstring$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return trimSubstring(str, i, i2);
    }

    public static final void wait(@NotNull Object wait) throws InterruptedException {
        Intrinsics.checkNotNullParameter(wait, "$this$wait");
        wait.wait();
    }

    @NotNull
    public static final Throwable withSuppressed(@NotNull Exception withSuppressed, @NotNull List<? extends Exception> suppressed) {
        Intrinsics.checkNotNullParameter(withSuppressed, "$this$withSuppressed");
        Intrinsics.checkNotNullParameter(suppressed, "suppressed");
        Iterator<? extends Exception> it = suppressed.iterator();
        while (it.hasNext()) {
            ExceptionsKt.addSuppressed(withSuppressed, it.next());
        }
        return withSuppressed;
    }

    public static final void writeMedium(@NotNull BufferedSink writeMedium, int i) throws IOException {
        Intrinsics.checkNotNullParameter(writeMedium, "$this$writeMedium");
        writeMedium.writeByte((i >>> 16) & 255);
        writeMedium.writeByte((i >>> 8) & 255);
        writeMedium.writeByte(i & 255);
    }

    public static final int delimiterOffset(@NotNull String delimiterOffset, char c, int i, int i2) {
        Intrinsics.checkNotNullParameter(delimiterOffset, "$this$delimiterOffset");
        while (i < i2) {
            if (delimiterOffset.charAt(i) == c) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int delimiterOffset$default(String str, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = str.length();
        }
        return delimiterOffset(str, c, i, i2);
    }

    @NotNull
    public static final String toHexString(int i) {
        String hexString = Integer.toHexString(i);
        Intrinsics.checkNotNullExpressionValue(hexString, "Integer.toHexString(this)");
        return hexString;
    }

    public static final void closeQuietly(@NotNull Socket closeQuietly) {
        Intrinsics.checkNotNullParameter(closeQuietly, "$this$closeQuietly");
        try {
            closeQuietly.close();
        } catch (AssertionError e) {
            throw e;
        } catch (RuntimeException e2) {
            if (!Intrinsics.areEqual(e2.getMessage(), "bio == null")) {
                throw e2;
            }
        } catch (Exception unused) {
        }
    }

    public static final void closeQuietly(@NotNull ServerSocket closeQuietly) {
        Intrinsics.checkNotNullParameter(closeQuietly, "$this$closeQuietly");
        try {
            closeQuietly.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception unused) {
        }
    }

    public static final int skipAll(@NotNull Buffer skipAll, byte b) throws EOFException {
        Intrinsics.checkNotNullParameter(skipAll, "$this$skipAll");
        int i = 0;
        while (!skipAll.exhausted() && skipAll.getByte(0L) == b) {
            i++;
            skipAll.readByte();
        }
        return i;
    }
}
