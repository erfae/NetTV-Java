package okhttp3;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.gms.common.internal.ImagesContract;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okio.Buffer;
import okio.Utf8;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: HttpUrl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 J2\u00020\u0001:\u0002IJBa\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003¢\u0006\u0002\u0010\u000eJ\u000f\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b!J\r\u0010\u0011\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\"J\r\u0010\u0012\u001a\u00020\u0003H\u0007¢\u0006\u0002\b#J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007¢\u0006\u0002\b$J\u000f\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b%J\r\u0010\u0016\u001a\u00020\u0003H\u0007¢\u0006\u0002\b&J\u0013\u0010'\u001a\u00020\u00182\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u000f\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b)J\b\u0010*\u001a\u00020\bH\u0016J\r\u0010\u0006\u001a\u00020\u0003H\u0007¢\u0006\u0002\b+J\u0006\u0010,\u001a\u00020-J\u0010\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020\u0003J\r\u0010\u0005\u001a\u00020\u0003H\u0007¢\u0006\u0002\b/J\u0013\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007¢\u0006\u0002\b0J\r\u0010\u001a\u001a\u00020\bH\u0007¢\u0006\u0002\b1J\r\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\b2J\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b3J\u0010\u00104\u001a\u0004\u0018\u00010\u00032\u0006\u00105\u001a\u00020\u0003J\u000e\u00106\u001a\u00020\u00032\u0006\u00107\u001a\u00020\bJ\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001eH\u0007¢\u0006\u0002\b8J\u0010\u00109\u001a\u0004\u0018\u00010\u00032\u0006\u00107\u001a\u00020\bJ\u0016\u0010:\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n2\u0006\u00105\u001a\u00020\u0003J\r\u0010 \u001a\u00020\bH\u0007¢\u0006\u0002\b;J\u0006\u0010<\u001a\u00020\u0003J\u0010\u0010=\u001a\u0004\u0018\u00010\u00002\u0006\u0010.\u001a\u00020\u0003J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b>J\b\u0010?\u001a\u00020\u0003H\u0016J\r\u0010@\u001a\u00020AH\u0007¢\u0006\u0002\bBJ\r\u0010C\u001a\u00020DH\u0007¢\u0006\u0002\b\rJ\b\u0010E\u001a\u0004\u0018\u00010\u0003J\r\u0010B\u001a\u00020AH\u0007¢\u0006\u0002\bFJ\r\u0010\r\u001a\u00020DH\u0007¢\u0006\u0002\bGJ\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\bHR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0012\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\n8G¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0016\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u0015\u0010\f\u001a\u0004\u0018\u00010\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0010R\u0013\u0010\u0006\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0019R\u0013\u0010\u0005\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0014R\u0011\u0010\u001a\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0007\u001a\u00020\b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001bR\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001e8G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0011\u0010 \u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b \u0010\u001bR\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0010R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010¨\u0006K"}, d2 = {"Lokhttp3/HttpUrl;", "", "scheme", "", "username", "password", "host", "port", "", "pathSegments", "", "queryNamesAndValues", "fragment", ImagesContract.URL, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "encodedFragment", "()Ljava/lang/String;", "encodedPassword", "encodedPath", "encodedPathSegments", "()Ljava/util/List;", "encodedQuery", "encodedUsername", "isHttps", "", "()Z", "pathSize", "()I", SearchIntents.EXTRA_QUERY, "queryParameterNames", "", "()Ljava/util/Set;", "querySize", "-deprecated_encodedFragment", "-deprecated_encodedPassword", "-deprecated_encodedPath", "-deprecated_encodedPathSegments", "-deprecated_encodedQuery", "-deprecated_encodedUsername", "equals", "other", "-deprecated_fragment", "hashCode", "-deprecated_host", "newBuilder", "Lokhttp3/HttpUrl$Builder;", "link", "-deprecated_password", "-deprecated_pathSegments", "-deprecated_pathSize", "-deprecated_port", "-deprecated_query", "queryParameter", "name", "queryParameterName", "index", "-deprecated_queryParameterNames", "queryParameterValue", "queryParameterValues", "-deprecated_querySize", "redact", "resolve", "-deprecated_scheme", "toString", "toUri", "Ljava/net/URI;", "uri", "toUrl", "Ljava/net/URL;", "topPrivateDomain", "-deprecated_uri", "-deprecated_url", "-deprecated_username", "Builder", "Companion", "okhttp"}, k = 1, mv = {1, 4, 1})
public final class HttpUrl {

    @NotNull
    public static final String FORM_ENCODE_SET = " !\"#$&'()+,/:;<=>?@[\\]^`{|}~";

    @NotNull
    public static final String FRAGMENT_ENCODE_SET = "";

    @NotNull
    public static final String FRAGMENT_ENCODE_SET_URI = " \"#<>\\^`{|}";

    @NotNull
    public static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";

    @NotNull
    public static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";

    @NotNull
    public static final String PATH_SEGMENT_ENCODE_SET_URI = "[]";

    @NotNull
    public static final String QUERY_COMPONENT_ENCODE_SET = " !\"#$&'(),/:;<=>?@[]\\^`{|}~";

    @NotNull
    public static final String QUERY_COMPONENT_ENCODE_SET_URI = "\\^`{|}";

    @NotNull
    public static final String QUERY_COMPONENT_REENCODE_SET = " \"'<>#&=";

    @NotNull
    public static final String QUERY_ENCODE_SET = " \"'<>#";

    @NotNull
    public static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";

    @Nullable
    private final String fragment;

    @NotNull
    private final String host;
    private final boolean isHttps;

    @NotNull
    private final String password;

    @NotNull
    private final List<String> pathSegments;
    private final int port;
    private final List<String> queryNamesAndValues;

    @NotNull
    private final String scheme;
    private final String url;

    @NotNull
    private final String username;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* JADX INFO: compiled from: HttpUrl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0017\u0018\u0000 V2\u00020\u0001:\u0001VB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0004J\u0018\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u0004J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0004J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0004J\u0018\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\u0006\u00102\u001a\u000203J\b\u00104\u001a\u00020\u001bH\u0002J\u0010\u0010\u0003\u001a\u00020\u00002\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0004J\u000e\u00105\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u0004J\u0010\u00106\u001a\u00020\u00002\b\u00106\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u00107\u001a\u00020\u00002\b\u00107\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0004J\u0010\u00108\u001a\u00020.2\u0006\u00109\u001a\u00020\u0004H\u0002J\u0010\u0010:\u001a\u00020.2\u0006\u00109\u001a\u00020\u0004H\u0002J\u001f\u0010;\u001a\u00020\u00002\b\u0010<\u001a\u0004\u0018\u0001032\u0006\u00109\u001a\u00020\u0004H\u0000¢\u0006\u0002\b=J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\u0004J\b\u0010?\u001a\u00020@H\u0002J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bJ0\u0010A\u001a\u00020@2\u0006\u00109\u001a\u00020\u00042\u0006\u0010B\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001b2\u0006\u0010D\u001a\u00020.2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010E\u001a\u00020\u00002\b\u0010E\u001a\u0004\u0018\u00010\u0004J\r\u0010F\u001a\u00020\u0000H\u0000¢\u0006\u0002\bGJ\u0010\u0010H\u001a\u00020@2\u0006\u0010I\u001a\u00020\u0004H\u0002J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u0004J\u000e\u0010K\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0004J\u000e\u0010L\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001bJ \u0010N\u001a\u00020@2\u0006\u00109\u001a\u00020\u00042\u0006\u0010O\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001bH\u0002J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u0004J\u0016\u0010P\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u0004J\u0018\u0010Q\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u0004J\u0016\u0010R\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0004J\u0018\u0010S\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\b\u0010T\u001a\u00020\u0004H\u0016J\u000e\u0010U\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u001bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\b¨\u0006W"}, d2 = {"Lokhttp3/HttpUrl$Builder;", "", "()V", "encodedFragment", "", "getEncodedFragment$okhttp", "()Ljava/lang/String;", "setEncodedFragment$okhttp", "(Ljava/lang/String;)V", "encodedPassword", "getEncodedPassword$okhttp", "setEncodedPassword$okhttp", "encodedPathSegments", "", "getEncodedPathSegments$okhttp", "()Ljava/util/List;", "encodedQueryNamesAndValues", "getEncodedQueryNamesAndValues$okhttp", "setEncodedQueryNamesAndValues$okhttp", "(Ljava/util/List;)V", "encodedUsername", "getEncodedUsername$okhttp", "setEncodedUsername$okhttp", "host", "getHost$okhttp", "setHost$okhttp", "port", "", "getPort$okhttp", "()I", "setPort$okhttp", "(I)V", "scheme", "getScheme$okhttp", "setScheme$okhttp", "addEncodedPathSegment", "encodedPathSegment", "addEncodedPathSegments", "addEncodedQueryParameter", "encodedName", "encodedValue", "addPathSegment", "pathSegment", "addPathSegments", "pathSegments", "alreadyEncoded", "", "addQueryParameter", "name", "value", "build", "Lokhttp3/HttpUrl;", "effectivePort", "encodedPath", "encodedQuery", "fragment", "isDot", "input", "isDotDot", "parse", "base", "parse$okhttp", "password", "pop", "", "push", "pos", "limit", "addTrailingSlash", SearchIntents.EXTRA_QUERY, "reencodeForUri", "reencodeForUri$okhttp", "removeAllCanonicalQueryParameters", "canonicalName", "removeAllEncodedQueryParameters", "removeAllQueryParameters", "removePathSegment", "index", "resolvePath", "startPos", "setEncodedPathSegment", "setEncodedQueryParameter", "setPathSegment", "setQueryParameter", "toString", "username", "Companion", "okhttp"}, k = 1, mv = {1, 4, 1})
    public static final class Builder {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        @NotNull
        public static final Companion INSTANCE = new Companion(null);

        @NotNull
        public static final String INVALID_HOST = "Invalid URL host";

        @Nullable
        private String encodedFragment;

        @NotNull
        private final List<String> encodedPathSegments;

        @Nullable
        private List<String> encodedQueryNamesAndValues;

        @Nullable
        private String host;

        @Nullable
        private String scheme;

        @NotNull
        private String encodedUsername = "";

        @NotNull
        private String encodedPassword = "";
        private int port = -1;

        /* JADX INFO: compiled from: HttpUrl.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J \u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J \u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u001c\u0010\f\u001a\u00020\u0006*\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lokhttp3/HttpUrl$Builder$Companion;", "", "()V", "INVALID_HOST", "", "parsePort", "", "input", "pos", "limit", "portColonOffset", "schemeDelimiterOffset", "slashCount", "okhttp"}, k = 1, mv = {1, 4, 1})
        public static final class Companion {
            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final int parsePort(String input, int pos, int limit) {
                try {
                    int i = Integer.parseInt(Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, input, pos, limit, "", false, false, false, false, null, 248, null));
                    if (1 <= i && 65535 >= i) {
                        return i;
                    }
                    return -1;
                } catch (NumberFormatException unused) {
                    return -1;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final int portColonOffset(String input, int pos, int limit) {
                while (pos < limit) {
                    char cCharAt = input.charAt(pos);
                    if (cCharAt == ':') {
                        return pos;
                    }
                    if (cCharAt == '[') {
                        do {
                            pos++;
                            if (pos >= limit) {
                                break;
                            }
                        } while (input.charAt(pos) != ']');
                    }
                    pos++;
                }
                return limit;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final int schemeDelimiterOffset(String input, int pos, int limit) {
                if (limit - pos < 2) {
                    return -1;
                }
                char cCharAt = input.charAt(pos);
                if ((Intrinsics.compare((int) cCharAt, 97) < 0 || Intrinsics.compare((int) cCharAt, 122) > 0) && (Intrinsics.compare((int) cCharAt, 65) < 0 || Intrinsics.compare((int) cCharAt, 90) > 0)) {
                    return -1;
                }
                while (true) {
                    pos++;
                    if (pos >= limit) {
                        return -1;
                    }
                    char cCharAt2 = input.charAt(pos);
                    if ('a' > cCharAt2 || 'z' < cCharAt2) {
                        if ('A' > cCharAt2 || 'Z' < cCharAt2) {
                            if ('0' > cCharAt2 || '9' < cCharAt2) {
                                if (cCharAt2 != '+' && cCharAt2 != '-' && cCharAt2 != '.') {
                                    if (cCharAt2 == ':') {
                                        return pos;
                                    }
                                    return -1;
                                }
                            }
                        }
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final int slashCount(String str, int i, int i2) {
                int i3 = 0;
                while (i < i2) {
                    char cCharAt = str.charAt(i);
                    if (cCharAt != '\\' && cCharAt != '/') {
                        break;
                    }
                    i3++;
                    i++;
                }
                return i3;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Builder() {
            ArrayList arrayList = new ArrayList();
            this.encodedPathSegments = arrayList;
            arrayList.add("");
        }

        private final int effectivePort() {
            int i = this.port;
            if (i != -1) {
                return i;
            }
            Companion companion = HttpUrl.INSTANCE;
            String str = this.scheme;
            Intrinsics.checkNotNull(str);
            return companion.defaultPort(str);
        }

        private final boolean isDot(String input) {
            return Intrinsics.areEqual(input, ".") || StringsKt.equals(input, "%2e");
        }

        private final boolean isDotDot(String input) {
            return Intrinsics.areEqual(input, "..") || StringsKt.equals(input, "%2e.") || StringsKt.equals(input, ".%2e") || StringsKt.equals(input, "%2e%2e");
        }

        private final void pop() {
            List<String> list = this.encodedPathSegments;
            if (!(list.remove(list.size() - 1).length() == 0) || !(!this.encodedPathSegments.isEmpty())) {
                this.encodedPathSegments.add("");
            } else {
                List<String> list2 = this.encodedPathSegments;
                list2.set(list2.size() - 1, "");
            }
        }

        private final void push(String input, int pos, int limit, boolean addTrailingSlash, boolean alreadyEncoded) {
            String strCanonicalize$okhttp$default = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, input, pos, limit, HttpUrl.PATH_SEGMENT_ENCODE_SET, alreadyEncoded, false, false, false, null, PsExtractor.VIDEO_STREAM_MASK, null);
            if (isDot(strCanonicalize$okhttp$default)) {
                return;
            }
            if (isDotDot(strCanonicalize$okhttp$default)) {
                pop();
                return;
            }
            List<String> list = this.encodedPathSegments;
            if (list.get(list.size() - 1).length() == 0) {
                List<String> list2 = this.encodedPathSegments;
                list2.set(list2.size() - 1, strCanonicalize$okhttp$default);
            } else {
                this.encodedPathSegments.add(strCanonicalize$okhttp$default);
            }
            if (addTrailingSlash) {
                this.encodedPathSegments.add("");
            }
        }

        private final void removeAllCanonicalQueryParameters(String canonicalName) {
            List<String> list = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list);
            IntProgression intProgressionStep = RangesKt.step(RangesKt.downTo(list.size() - 2, 0), 2);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if (step >= 0) {
                if (first > last) {
                    return;
                }
            } else if (first < last) {
                return;
            }
            while (true) {
                List<String> list2 = this.encodedQueryNamesAndValues;
                Intrinsics.checkNotNull(list2);
                if (Intrinsics.areEqual(canonicalName, list2.get(first))) {
                    List<String> list3 = this.encodedQueryNamesAndValues;
                    Intrinsics.checkNotNull(list3);
                    list3.remove(first + 1);
                    List<String> list4 = this.encodedQueryNamesAndValues;
                    Intrinsics.checkNotNull(list4);
                    list4.remove(first);
                    List<String> list5 = this.encodedQueryNamesAndValues;
                    Intrinsics.checkNotNull(list5);
                    if (list5.isEmpty()) {
                        this.encodedQueryNamesAndValues = null;
                        return;
                    }
                }
                if (first == last) {
                    return;
                } else {
                    first += step;
                }
            }
        }

        private final void resolvePath(String input, int startPos, int limit) {
            if (startPos == limit) {
                return;
            }
            char cCharAt = input.charAt(startPos);
            if (cCharAt == '/' || cCharAt == '\\') {
                this.encodedPathSegments.clear();
                this.encodedPathSegments.add("");
                startPos++;
            } else {
                List<String> list = this.encodedPathSegments;
                list.set(list.size() - 1, "");
            }
            while (true) {
                int i = startPos;
                if (i >= limit) {
                    return;
                }
                startPos = Util.delimiterOffset(input, "/\\", i, limit);
                boolean z = startPos < limit;
                push(input, i, startPos, z, true);
                if (z) {
                    startPos++;
                }
            }
        }

        @NotNull
        public final Builder addEncodedPathSegment(@NotNull String encodedPathSegment) {
            Intrinsics.checkNotNullParameter(encodedPathSegment, "encodedPathSegment");
            push(encodedPathSegment, 0, encodedPathSegment.length(), false, true);
            return this;
        }

        @NotNull
        public final Builder addEncodedPathSegments(@NotNull String encodedPathSegments) {
            Intrinsics.checkNotNullParameter(encodedPathSegments, "encodedPathSegments");
            return addPathSegments(encodedPathSegments, true);
        }

        @NotNull
        public final Builder addEncodedQueryParameter(@NotNull String encodedName, @Nullable String encodedValue) {
            Intrinsics.checkNotNullParameter(encodedName, "encodedName");
            if (this.encodedQueryNamesAndValues == null) {
                this.encodedQueryNamesAndValues = new ArrayList();
            }
            List<String> list = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list);
            Companion companion = HttpUrl.INSTANCE;
            list.add(Companion.canonicalize$okhttp$default(companion, encodedName, 0, 0, HttpUrl.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, null, 211, null));
            List<String> list2 = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list2);
            list2.add(encodedValue != null ? Companion.canonicalize$okhttp$default(companion, encodedValue, 0, 0, HttpUrl.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, null, 211, null) : null);
            return this;
        }

        @NotNull
        public final Builder addPathSegment(@NotNull String pathSegment) {
            Intrinsics.checkNotNullParameter(pathSegment, "pathSegment");
            push(pathSegment, 0, pathSegment.length(), false, false);
            return this;
        }

        @NotNull
        public final Builder addPathSegments(@NotNull String pathSegments) {
            Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
            return addPathSegments(pathSegments, false);
        }

        @NotNull
        public final Builder addQueryParameter(@NotNull String name, @Nullable String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (this.encodedQueryNamesAndValues == null) {
                this.encodedQueryNamesAndValues = new ArrayList();
            }
            List<String> list = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list);
            Companion companion = HttpUrl.INSTANCE;
            list.add(Companion.canonicalize$okhttp$default(companion, name, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, null, 219, null));
            List<String> list2 = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list2);
            list2.add(value != null ? Companion.canonicalize$okhttp$default(companion, value, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, null, 219, null) : null);
            return this;
        }

        @NotNull
        public final HttpUrl build() {
            ArrayList arrayList;
            String str = this.scheme;
            if (str == null) {
                throw new IllegalStateException("scheme == null");
            }
            Companion companion = HttpUrl.INSTANCE;
            String strPercentDecode$okhttp$default = Companion.percentDecode$okhttp$default(companion, this.encodedUsername, 0, 0, false, 7, null);
            String strPercentDecode$okhttp$default2 = Companion.percentDecode$okhttp$default(companion, this.encodedPassword, 0, 0, false, 7, null);
            String str2 = this.host;
            if (str2 == null) {
                throw new IllegalStateException("host == null");
            }
            int iEffectivePort = effectivePort();
            List<String> list = this.encodedPathSegments;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(Companion.percentDecode$okhttp$default(HttpUrl.INSTANCE, (String) it.next(), 0, 0, false, 7, null));
            }
            List<String> list2 = this.encodedQueryNamesAndValues;
            if (list2 != null) {
                arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2));
                for (String str3 : list2) {
                    arrayList.add(str3 != null ? Companion.percentDecode$okhttp$default(HttpUrl.INSTANCE, str3, 0, 0, true, 3, null) : null);
                }
            } else {
                arrayList = null;
            }
            String str4 = this.encodedFragment;
            return new HttpUrl(str, strPercentDecode$okhttp$default, strPercentDecode$okhttp$default2, str2, iEffectivePort, arrayList2, arrayList, str4 != null ? Companion.percentDecode$okhttp$default(HttpUrl.INSTANCE, str4, 0, 0, false, 7, null) : null, toString());
        }

        @NotNull
        public final Builder encodedFragment(@Nullable String encodedFragment) {
            this.encodedFragment = encodedFragment != null ? Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, encodedFragment, 0, 0, "", true, false, false, true, null, 179, null) : null;
            return this;
        }

        @NotNull
        public final Builder encodedPassword(@NotNull String encodedPassword) {
            Intrinsics.checkNotNullParameter(encodedPassword, "encodedPassword");
            this.encodedPassword = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, encodedPassword, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null);
            return this;
        }

        @NotNull
        public final Builder encodedPath(@NotNull String encodedPath) {
            Intrinsics.checkNotNullParameter(encodedPath, "encodedPath");
            if (!StringsKt.startsWith$default(encodedPath, "/")) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("unexpected encodedPath: ", encodedPath).toString());
            }
            resolvePath(encodedPath, 0, encodedPath.length());
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:6:0x001d  */
        @NotNull
        public final Builder encodedQuery(@Nullable String encodedQuery) {
            List<String> queryNamesAndValues$okhttp;
            if (encodedQuery != null) {
                Companion companion = HttpUrl.INSTANCE;
                String strCanonicalize$okhttp$default = Companion.canonicalize$okhttp$default(companion, encodedQuery, 0, 0, HttpUrl.QUERY_ENCODE_SET, true, false, true, false, null, 211, null);
                if (strCanonicalize$okhttp$default != null) {
                    queryNamesAndValues$okhttp = companion.toQueryNamesAndValues$okhttp(strCanonicalize$okhttp$default);
                } else {
                    queryNamesAndValues$okhttp = null;
                }
            } else {
                queryNamesAndValues$okhttp = null;
            }
            this.encodedQueryNamesAndValues = queryNamesAndValues$okhttp;
            return this;
        }

        @NotNull
        public final Builder encodedUsername(@NotNull String encodedUsername) {
            Intrinsics.checkNotNullParameter(encodedUsername, "encodedUsername");
            this.encodedUsername = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, encodedUsername, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null);
            return this;
        }

        @NotNull
        public final Builder fragment(@Nullable String fragment) {
            this.encodedFragment = fragment != null ? Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, fragment, 0, 0, "", false, false, false, true, null, 187, null) : null;
            return this;
        }

        @Nullable
        /* JADX INFO: renamed from: getEncodedFragment$okhttp, reason: from getter */
        public final String getEncodedFragment() {
            return this.encodedFragment;
        }

        @NotNull
        /* JADX INFO: renamed from: getEncodedPassword$okhttp, reason: from getter */
        public final String getEncodedPassword() {
            return this.encodedPassword;
        }

        @NotNull
        public final List<String> getEncodedPathSegments$okhttp() {
            return this.encodedPathSegments;
        }

        @Nullable
        public final List<String> getEncodedQueryNamesAndValues$okhttp() {
            return this.encodedQueryNamesAndValues;
        }

        @NotNull
        /* JADX INFO: renamed from: getEncodedUsername$okhttp, reason: from getter */
        public final String getEncodedUsername() {
            return this.encodedUsername;
        }

        @Nullable
        /* JADX INFO: renamed from: getHost$okhttp, reason: from getter */
        public final String getHost() {
            return this.host;
        }

        /* JADX INFO: renamed from: getPort$okhttp, reason: from getter */
        public final int getPort() {
            return this.port;
        }

        @Nullable
        /* JADX INFO: renamed from: getScheme$okhttp, reason: from getter */
        public final String getScheme() {
            return this.scheme;
        }

        @NotNull
        public final Builder host(@NotNull String host) {
            Intrinsics.checkNotNullParameter(host, "host");
            String canonicalHost = HostnamesKt.toCanonicalHost(Companion.percentDecode$okhttp$default(HttpUrl.INSTANCE, host, 0, 0, false, 7, null));
            if (canonicalHost == null) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("unexpected host: ", host));
            }
            this.host = canonicalHost;
            return this;
        }

        @NotNull
        public final Builder parse$okhttp(@Nullable HttpUrl base, @NotNull String input) {
            int iDelimiterOffset;
            int i;
            int i2;
            String input2 = input;
            Intrinsics.checkNotNullParameter(input2, "input");
            int iIndexOfFirstNonAsciiWhitespace$default = Util.indexOfFirstNonAsciiWhitespace$default(input2, 0, 0, 3, null);
            int iIndexOfLastNonAsciiWhitespace$default = Util.indexOfLastNonAsciiWhitespace$default(input2, iIndexOfFirstNonAsciiWhitespace$default, 0, 2, null);
            Companion companion = INSTANCE;
            int iSchemeDelimiterOffset = companion.schemeDelimiterOffset(input2, iIndexOfFirstNonAsciiWhitespace$default, iIndexOfLastNonAsciiWhitespace$default);
            byte b = -1;
            String str = "(this as java.lang.Strin…ing(startIndex, endIndex)";
            if (iSchemeDelimiterOffset != -1) {
                if (StringsKt.startsWith(input2, "https:", iIndexOfFirstNonAsciiWhitespace$default)) {
                    this.scheme = "https";
                    iIndexOfFirstNonAsciiWhitespace$default += 6;
                } else {
                    if (!StringsKt.startsWith(input2, "http:", iIndexOfFirstNonAsciiWhitespace$default)) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Expected URL scheme 'http' or 'https' but was '");
                        String strSubstring = input2.substring(0, iSchemeDelimiterOffset);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        sbM.append(strSubstring);
                        sbM.append("'");
                        throw new IllegalArgumentException(sbM.toString());
                    }
                    this.scheme = "http";
                    iIndexOfFirstNonAsciiWhitespace$default += 5;
                }
            } else {
                if (base == null) {
                    if (input.length() > 6) {
                        input2 = StringsKt.take(input2, 6) + "...";
                    }
                    throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Expected URL scheme 'http' or 'https' but no scheme was found for ", input2));
                }
                this.scheme = base.scheme();
            }
            int iSlashCount = companion.slashCount(input2, iIndexOfFirstNonAsciiWhitespace$default, iIndexOfLastNonAsciiWhitespace$default);
            byte b2 = Utf8.REPLACEMENT_BYTE;
            byte b3 = 35;
            if (iSlashCount >= 2 || base == null || (!Intrinsics.areEqual(base.scheme(), this.scheme))) {
                int i3 = iIndexOfFirstNonAsciiWhitespace$default + iSlashCount;
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    iDelimiterOffset = Util.delimiterOffset(input2, "@/\\?#", i3, iIndexOfLastNonAsciiWhitespace$default);
                    byte bCharAt = iDelimiterOffset != iIndexOfLastNonAsciiWhitespace$default ? input2.charAt(iDelimiterOffset) : (byte) -1;
                    if (bCharAt == b || bCharAt == b3 || bCharAt == 47 || bCharAt == 92 || bCharAt == b2) {
                        break;
                    }
                    if (bCharAt != 64) {
                        iIndexOfLastNonAsciiWhitespace$default = iIndexOfLastNonAsciiWhitespace$default;
                        str = str;
                    } else {
                        if (z) {
                            i2 = iDelimiterOffset;
                            this.encodedPassword += "%40" + Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, input, i3, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, PsExtractor.VIDEO_STREAM_MASK, null);
                        } else {
                            int iDelimiterOffset2 = Util.delimiterOffset(input2, ':', i3, iDelimiterOffset);
                            Companion companion2 = HttpUrl.INSTANCE;
                            i2 = iDelimiterOffset;
                            String strCanonicalize$okhttp$default = Companion.canonicalize$okhttp$default(companion2, input, i3, iDelimiterOffset2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, PsExtractor.VIDEO_STREAM_MASK, null);
                            if (z2) {
                                strCanonicalize$okhttp$default = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.encodedUsername, "%40", strCanonicalize$okhttp$default);
                            }
                            this.encodedUsername = strCanonicalize$okhttp$default;
                            if (iDelimiterOffset2 != i2) {
                                this.encodedPassword = Companion.canonicalize$okhttp$default(companion2, input, iDelimiterOffset2 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, PsExtractor.VIDEO_STREAM_MASK, null);
                                z = true;
                            }
                            z2 = true;
                        }
                        i3 = i2 + 1;
                    }
                    b3 = 35;
                    b2 = Utf8.REPLACEMENT_BYTE;
                    b = -1;
                    str = str;
                    iIndexOfLastNonAsciiWhitespace$default = iIndexOfLastNonAsciiWhitespace$default;
                }
                i = iIndexOfLastNonAsciiWhitespace$default;
                String str2 = str;
                Companion companion3 = INSTANCE;
                int iPortColonOffset = companion3.portColonOffset(input2, i3, iDelimiterOffset);
                int i4 = iPortColonOffset + 1;
                if (i4 < iDelimiterOffset) {
                    this.host = HostnamesKt.toCanonicalHost(Companion.percentDecode$okhttp$default(HttpUrl.INSTANCE, input, i3, iPortColonOffset, false, 4, null));
                    int port = companion3.parsePort(input2, i4, iDelimiterOffset);
                    this.port = port;
                    if (!(port != -1)) {
                        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Invalid URL port: \"");
                        String strSubstring2 = input2.substring(i4, iDelimiterOffset);
                        Intrinsics.checkNotNullExpressionValue(strSubstring2, str2);
                        sbM2.append(strSubstring2);
                        sbM2.append(Typography.quote);
                        throw new IllegalArgumentException(sbM2.toString().toString());
                    }
                } else {
                    Companion companion4 = HttpUrl.INSTANCE;
                    this.host = HostnamesKt.toCanonicalHost(Companion.percentDecode$okhttp$default(companion4, input, i3, iPortColonOffset, false, 4, null));
                    String str3 = this.scheme;
                    Intrinsics.checkNotNull(str3);
                    this.port = companion4.defaultPort(str3);
                }
                if (!(this.host != null)) {
                    StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Invalid URL host: \"");
                    String strSubstring3 = input2.substring(i3, iPortColonOffset);
                    Intrinsics.checkNotNullExpressionValue(strSubstring3, str2);
                    sbM3.append(strSubstring3);
                    sbM3.append(Typography.quote);
                    throw new IllegalArgumentException(sbM3.toString().toString());
                }
                iIndexOfFirstNonAsciiWhitespace$default = iDelimiterOffset;
            } else {
                this.encodedUsername = base.encodedUsername();
                this.encodedPassword = base.encodedPassword();
                this.host = base.host();
                this.port = base.port();
                this.encodedPathSegments.clear();
                this.encodedPathSegments.addAll(base.encodedPathSegments());
                if (iIndexOfFirstNonAsciiWhitespace$default == iIndexOfLastNonAsciiWhitespace$default || input2.charAt(iIndexOfFirstNonAsciiWhitespace$default) == '#') {
                    encodedQuery(base.encodedQuery());
                }
                i = iIndexOfLastNonAsciiWhitespace$default;
            }
            int i5 = i;
            int iDelimiterOffset3 = Util.delimiterOffset(input2, "?#", iIndexOfFirstNonAsciiWhitespace$default, i5);
            resolvePath(input2, iIndexOfFirstNonAsciiWhitespace$default, iDelimiterOffset3);
            if (iDelimiterOffset3 < i5 && input2.charAt(iDelimiterOffset3) == '?') {
                int iDelimiterOffset4 = Util.delimiterOffset(input2, '#', iDelimiterOffset3, i5);
                Companion companion5 = HttpUrl.INSTANCE;
                this.encodedQueryNamesAndValues = companion5.toQueryNamesAndValues$okhttp(Companion.canonicalize$okhttp$default(companion5, input, iDelimiterOffset3 + 1, iDelimiterOffset4, HttpUrl.QUERY_ENCODE_SET, true, false, true, false, null, 208, null));
                iDelimiterOffset3 = iDelimiterOffset4;
            }
            if (iDelimiterOffset3 < i5 && input2.charAt(iDelimiterOffset3) == '#') {
                this.encodedFragment = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, input, iDelimiterOffset3 + 1, i5, "", true, false, false, true, null, 176, null);
            }
            return this;
        }

        @NotNull
        public final Builder password(@NotNull String password) {
            Intrinsics.checkNotNullParameter(password, "password");
            this.encodedPassword = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, password, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null);
            return this;
        }

        @NotNull
        public final Builder port(int port) {
            if (!(1 <= port && 65535 >= port)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("unexpected port: ", port).toString());
            }
            this.port = port;
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:6:0x001d  */
        @NotNull
        public final Builder query(@Nullable String query) {
            List<String> queryNamesAndValues$okhttp;
            if (query != null) {
                Companion companion = HttpUrl.INSTANCE;
                String strCanonicalize$okhttp$default = Companion.canonicalize$okhttp$default(companion, query, 0, 0, HttpUrl.QUERY_ENCODE_SET, false, false, true, false, null, 219, null);
                if (strCanonicalize$okhttp$default != null) {
                    queryNamesAndValues$okhttp = companion.toQueryNamesAndValues$okhttp(strCanonicalize$okhttp$default);
                } else {
                    queryNamesAndValues$okhttp = null;
                }
            } else {
                queryNamesAndValues$okhttp = null;
            }
            this.encodedQueryNamesAndValues = queryNamesAndValues$okhttp;
            return this;
        }

        @NotNull
        public final Builder reencodeForUri$okhttp() {
            String str = this.host;
            this.host = str != null ? new Regex("[\"<>^`{|}]").replace(str, "") : null;
            int size = this.encodedPathSegments.size();
            for (int i = 0; i < size; i++) {
                List<String> list = this.encodedPathSegments;
                list.set(i, Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, list.get(i), 0, 0, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, true, true, false, false, null, 227, null));
            }
            List<String> list2 = this.encodedQueryNamesAndValues;
            if (list2 != null) {
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str2 = list2.get(i2);
                    list2.set(i2, str2 != null ? Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, str2, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET_URI, true, true, true, false, null, 195, null) : null);
                }
            }
            String str3 = this.encodedFragment;
            this.encodedFragment = str3 != null ? Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, str3, 0, 0, HttpUrl.FRAGMENT_ENCODE_SET_URI, true, true, false, true, null, 163, null) : null;
            return this;
        }

        @NotNull
        public final Builder removeAllEncodedQueryParameters(@NotNull String encodedName) {
            Intrinsics.checkNotNullParameter(encodedName, "encodedName");
            if (this.encodedQueryNamesAndValues == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, encodedName, 0, 0, HttpUrl.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, null, 211, null));
            return this;
        }

        @NotNull
        public final Builder removeAllQueryParameters(@NotNull String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (this.encodedQueryNamesAndValues == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, name, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, null, 219, null));
            return this;
        }

        @NotNull
        public final Builder removePathSegment(int index) {
            this.encodedPathSegments.remove(index);
            if (this.encodedPathSegments.isEmpty()) {
                this.encodedPathSegments.add("");
            }
            return this;
        }

        @NotNull
        public final Builder scheme(@NotNull String scheme) {
            Intrinsics.checkNotNullParameter(scheme, "scheme");
            if (StringsKt.equals(scheme, "http")) {
                this.scheme = "http";
            } else {
                if (!StringsKt.equals(scheme, "https")) {
                    throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("unexpected scheme: ", scheme));
                }
                this.scheme = "https";
            }
            return this;
        }

        public final void setEncodedFragment$okhttp(@Nullable String str) {
            this.encodedFragment = str;
        }

        public final void setEncodedPassword$okhttp(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.encodedPassword = str;
        }

        @NotNull
        public final Builder setEncodedPathSegment(int index, @NotNull String encodedPathSegment) {
            Intrinsics.checkNotNullParameter(encodedPathSegment, "encodedPathSegment");
            String strCanonicalize$okhttp$default = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, encodedPathSegment, 0, 0, HttpUrl.PATH_SEGMENT_ENCODE_SET, true, false, false, false, null, 243, null);
            this.encodedPathSegments.set(index, strCanonicalize$okhttp$default);
            if ((isDot(strCanonicalize$okhttp$default) || isDotDot(strCanonicalize$okhttp$default)) ? false : true) {
                return this;
            }
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("unexpected path segment: ", encodedPathSegment).toString());
        }

        public final void setEncodedQueryNamesAndValues$okhttp(@Nullable List<String> list) {
            this.encodedQueryNamesAndValues = list;
        }

        @NotNull
        public final Builder setEncodedQueryParameter(@NotNull String encodedName, @Nullable String encodedValue) {
            Intrinsics.checkNotNullParameter(encodedName, "encodedName");
            removeAllEncodedQueryParameters(encodedName);
            addEncodedQueryParameter(encodedName, encodedValue);
            return this;
        }

        public final void setEncodedUsername$okhttp(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.encodedUsername = str;
        }

        public final void setHost$okhttp(@Nullable String str) {
            this.host = str;
        }

        @NotNull
        public final Builder setPathSegment(int index, @NotNull String pathSegment) {
            Intrinsics.checkNotNullParameter(pathSegment, "pathSegment");
            String strCanonicalize$okhttp$default = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, pathSegment, 0, 0, HttpUrl.PATH_SEGMENT_ENCODE_SET, false, false, false, false, null, 251, null);
            if (!((isDot(strCanonicalize$okhttp$default) || isDotDot(strCanonicalize$okhttp$default)) ? false : true)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("unexpected path segment: ", pathSegment).toString());
            }
            this.encodedPathSegments.set(index, strCanonicalize$okhttp$default);
            return this;
        }

        public final void setPort$okhttp(int i) {
            this.port = i;
        }

        @NotNull
        public final Builder setQueryParameter(@NotNull String name, @Nullable String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            removeAllQueryParameters(name);
            addQueryParameter(name, value);
            return this;
        }

        public final void setScheme$okhttp(@Nullable String str) {
            this.scheme = str;
        }

        /* JADX WARN: Code duplicated, block: B:17:0x0035  */
        /* JADX WARN: Code duplicated, block: B:19:0x0042  */
        /* JADX WARN: Code duplicated, block: B:21:0x0045  */
        /* JADX WARN: Code duplicated, block: B:37:0x0090  */
        @NotNull
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = this.scheme;
            if (str != null) {
                sb.append(str);
                sb.append("://");
            } else {
                sb.append("//");
            }
            if (this.encodedUsername.length() > 0) {
                sb.append(this.encodedUsername);
                if (this.encodedPassword.length() > 0) {
                    sb.append(':');
                    sb.append(this.encodedPassword);
                }
                sb.append('@');
            } else {
                if (this.encodedPassword.length() > 0) {
                    sb.append(this.encodedUsername);
                    if (this.encodedPassword.length() > 0) {
                        sb.append(':');
                        sb.append(this.encodedPassword);
                    }
                    sb.append('@');
                }
            }
            String str2 = this.host;
            if (str2 != null) {
                Intrinsics.checkNotNull(str2);
                if (StringsKt.contains$default((CharSequence) str2, ':')) {
                    sb.append('[');
                    sb.append(this.host);
                    sb.append(']');
                } else {
                    sb.append(this.host);
                }
            }
            if (this.port != -1 || this.scheme != null) {
                int iEffectivePort = effectivePort();
                String str3 = this.scheme;
                if (str3 != null) {
                    Companion companion = HttpUrl.INSTANCE;
                    Intrinsics.checkNotNull(str3);
                    if (iEffectivePort != companion.defaultPort(str3)) {
                        sb.append(':');
                        sb.append(iEffectivePort);
                    }
                } else {
                    sb.append(':');
                    sb.append(iEffectivePort);
                }
            }
            Companion companion2 = HttpUrl.INSTANCE;
            companion2.toPathString$okhttp(this.encodedPathSegments, sb);
            if (this.encodedQueryNamesAndValues != null) {
                sb.append('?');
                List<String> list = this.encodedQueryNamesAndValues;
                Intrinsics.checkNotNull(list);
                companion2.toQueryString$okhttp(list, sb);
            }
            if (this.encodedFragment != null) {
                sb.append('#');
                sb.append(this.encodedFragment);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
            return string;
        }

        @NotNull
        public final Builder username(@NotNull String username) {
            Intrinsics.checkNotNullParameter(username, "username");
            this.encodedUsername = Companion.canonicalize$okhttp$default(HttpUrl.INSTANCE, username, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null);
            return this;
        }

        private final Builder addPathSegments(String pathSegments, boolean alreadyEncoded) {
            int i = 0;
            do {
                int iDelimiterOffset = Util.delimiterOffset(pathSegments, "/\\", i, pathSegments.length());
                push(pathSegments, i, iDelimiterOffset, iDelimiterOffset < pathSegments.length(), alreadyEncoded);
                i = iDelimiterOffset + 1;
            } while (i <= pathSegments.length());
            return this;
        }
    }

    /* JADX INFO: compiled from: HttpUrl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007¢\u0006\u0002\b\u0018J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0007¢\u0006\u0002\b\u0018J\u0015\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u0018J\u0017\u0010\u001b\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u001cJa\u0010\u001d\u001a\u00020\u0004*\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00042\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\"2\b\b\u0002\u0010$\u001a\u00020\"2\b\b\u0002\u0010%\u001a\u00020\"2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'H\u0000¢\u0006\u0002\b(J\u001c\u0010)\u001a\u00020\"*\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\u0002J/\u0010*\u001a\u00020\u0004*\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u001f\u001a\u00020\u00122\b\b\u0002\u0010$\u001a\u00020\"H\u0000¢\u0006\u0002\b+J\u0011\u0010,\u001a\u00020\u0015*\u00020\u0004H\u0007¢\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u0017H\u0007¢\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u001aH\u0007¢\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u0004H\u0007¢\u0006\u0002\b\u001bJ#\u0010.\u001a\u00020/*\b\u0012\u0004\u0012\u00020\u0004002\n\u00101\u001a\u000602j\u0002`3H\u0000¢\u0006\u0002\b4J\u0019\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000406*\u00020\u0004H\u0000¢\u0006\u0002\b7J%\u00108\u001a\u00020/*\n\u0012\u0006\u0012\u0004\u0018\u00010\u0004002\n\u00101\u001a\u000602j\u0002`3H\u0000¢\u0006\u0002\b9JV\u0010:\u001a\u00020/*\u00020;2\u0006\u0010<\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\"2\b\u0010&\u001a\u0004\u0018\u00010'H\u0002J,\u0010=\u001a\u00020/*\u00020;2\u0006\u0010>\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lokhttp3/HttpUrl$Companion;", "", "()V", "FORM_ENCODE_SET", "", "FRAGMENT_ENCODE_SET", "FRAGMENT_ENCODE_SET_URI", "HEX_DIGITS", "", "PASSWORD_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET_URI", "QUERY_COMPONENT_ENCODE_SET", "QUERY_COMPONENT_ENCODE_SET_URI", "QUERY_COMPONENT_REENCODE_SET", "QUERY_ENCODE_SET", "USERNAME_ENCODE_SET", "defaultPort", "", "scheme", "get", "Lokhttp3/HttpUrl;", "uri", "Ljava/net/URI;", "-deprecated_get", ImagesContract.URL, "Ljava/net/URL;", "parse", "-deprecated_parse", "canonicalize", "pos", "limit", "encodeSet", "alreadyEncoded", "", "strict", "plusIsSpace", "unicodeAllowed", "charset", "Ljava/nio/charset/Charset;", "canonicalize$okhttp", "isPercentEncoded", "percentDecode", "percentDecode$okhttp", "toHttpUrl", "toHttpUrlOrNull", "toPathString", "", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "toPathString$okhttp", "toQueryNamesAndValues", "", "toQueryNamesAndValues$okhttp", "toQueryString", "toQueryString$okhttp", "writeCanonicalized", "Lokio/Buffer;", "input", "writePercentDecoded", "encoded", "okhttp"}, k = 1, mv = {1, 4, 1})
    public static final class Companion {
        private Companion() {
        }

        public static /* synthetic */ String canonicalize$okhttp$default(Companion companion, String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset, int i3, Object obj) {
            return companion.canonicalize$okhttp(str, (i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? str.length() : i2, str2, (i3 & 8) != 0 ? false : z, (i3 & 16) != 0 ? false : z2, (i3 & 32) != 0 ? false : z3, (i3 & 64) != 0 ? false : z4, (i3 & 128) != 0 ? null : charset);
        }

        private final boolean isPercentEncoded(String str, int i, int i2) {
            int i3 = i + 2;
            return i3 < i2 && str.charAt(i) == '%' && Util.parseHexDigit(str.charAt(i + 1)) != -1 && Util.parseHexDigit(str.charAt(i3)) != -1;
        }

        public static /* synthetic */ String percentDecode$okhttp$default(Companion companion, String str, int i, int i2, boolean z, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = str.length();
            }
            if ((i3 & 4) != 0) {
                z = false;
            }
            return companion.percentDecode$okhttp(str, i, i2, z);
        }

        /* JADX WARN: Code duplicated, block: B:42:0x006b  */
        /* JADX WARN: Code duplicated, block: B:44:0x0070  */
        /* JADX WARN: Code duplicated, block: B:46:0x0073  */
        /* JADX WARN: Code duplicated, block: B:52:0x008c  */
        /* JADX WARN: Code duplicated, block: B:55:0x0095 A[LOOP:1: B:53:0x008f->B:55:0x0095, LOOP_END] */
        private final void writeCanonicalized(Buffer buffer, String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
            Buffer buffer2 = null;
            int iCharCount = i;
            while (iCharCount < i2) {
                Objects.requireNonNull(str, "null cannot be cast to non-null type java.lang.String");
                int iCodePointAt = str.codePointAt(iCharCount);
                if (!z || (iCodePointAt != 9 && iCodePointAt != 10 && iCodePointAt != 12 && iCodePointAt != 13)) {
                    if (iCodePointAt == 32 && str2 == HttpUrl.FORM_ENCODE_SET) {
                        buffer.writeUtf8("+");
                    } else if (iCodePointAt == 43 && z3) {
                        buffer.writeUtf8(z ? "+" : "%2B");
                    } else if (iCodePointAt < 32 || iCodePointAt == 127 || ((iCodePointAt >= 128 && !z4) || StringsKt.contains$default(str2, (char) iCodePointAt))) {
                        if (buffer2 == null) {
                            buffer2 = new Buffer();
                        }
                        if (charset != null || Intrinsics.areEqual(charset, StandardCharsets.UTF_8)) {
                            buffer2.writeUtf8CodePoint(iCodePointAt);
                        } else {
                            buffer2.writeString(str, iCharCount, Character.charCount(iCodePointAt) + iCharCount, charset);
                        }
                        while (!buffer2.exhausted()) {
                            int i3 = buffer2.readByte() & 255;
                            buffer.writeByte(37);
                            buffer.writeByte((int) HttpUrl.HEX_DIGITS[(i3 >> 4) & 15]);
                            buffer.writeByte((int) HttpUrl.HEX_DIGITS[i3 & 15]);
                        }
                    } else {
                        if (iCodePointAt == 37) {
                            if (z) {
                                if (z2) {
                                    if (!isPercentEncoded(str, iCharCount, i2)) {
                                    }
                                }
                            }
                            if (buffer2 == null) {
                                buffer2 = new Buffer();
                            }
                            if (charset != null) {
                                buffer2.writeUtf8CodePoint(iCodePointAt);
                            } else {
                                buffer2.writeUtf8CodePoint(iCodePointAt);
                            }
                            while (!buffer2.exhausted()) {
                                int i4 = buffer2.readByte() & 255;
                                buffer.writeByte(37);
                                buffer.writeByte((int) HttpUrl.HEX_DIGITS[(i4 >> 4) & 15]);
                                buffer.writeByte((int) HttpUrl.HEX_DIGITS[i4 & 15]);
                            }
                        }
                        buffer.writeUtf8CodePoint(iCodePointAt);
                    }
                }
                iCharCount += Character.charCount(iCodePointAt);
            }
        }

        private final void writePercentDecoded(Buffer buffer, String str, int i, int i2, boolean z) {
            int i3;
            while (i < i2) {
                Objects.requireNonNull(str, "null cannot be cast to non-null type java.lang.String");
                int iCodePointAt = str.codePointAt(i);
                if (iCodePointAt == 37 && (i3 = i + 2) < i2) {
                    int hexDigit = Util.parseHexDigit(str.charAt(i + 1));
                    int hexDigit2 = Util.parseHexDigit(str.charAt(i3));
                    if (hexDigit == -1 || hexDigit2 == -1) {
                        buffer.writeUtf8CodePoint(iCodePointAt);
                        i += Character.charCount(iCodePointAt);
                    } else {
                        buffer.writeByte((hexDigit << 4) + hexDigit2);
                        i = Character.charCount(iCodePointAt) + i3;
                    }
                } else if (iCodePointAt == 43 && z) {
                    buffer.writeByte(32);
                    i++;
                } else {
                    buffer.writeUtf8CodePoint(iCodePointAt);
                    i += Character.charCount(iCodePointAt);
                }
            }
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrl()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrl"}))
        @JvmName(name = "-deprecated_get")
        @NotNull
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final HttpUrl m1951deprecated_get(@NotNull String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return get(url);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrlOrNull()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        @JvmName(name = "-deprecated_parse")
        @Nullable
        /* JADX INFO: renamed from: -deprecated_parse, reason: not valid java name */
        public final HttpUrl m1954deprecated_parse(@NotNull String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return parse(url);
        }

        /* JADX WARN: Code duplicated, block: B:20:0x003b  */
        @NotNull
        public final String canonicalize$okhttp(@NotNull String canonicalize, int i, int i2, @NotNull String encodeSet, boolean z, boolean z2, boolean z3, boolean z4, @Nullable Charset charset) {
            Intrinsics.checkNotNullParameter(canonicalize, "$this$canonicalize");
            Intrinsics.checkNotNullParameter(encodeSet, "encodeSet");
            int iCharCount = i;
            while (true) {
                if (iCharCount >= i2) {
                    String strSubstring = canonicalize.substring(i, i2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    return strSubstring;
                }
                int iCodePointAt = canonicalize.codePointAt(iCharCount);
                if (iCodePointAt < 32 || iCodePointAt == 127 || ((iCodePointAt >= 128 && !z4) || StringsKt.contains$default(encodeSet, (char) iCodePointAt))) {
                    break;
                }
                if (iCodePointAt == 37) {
                    if (!z) {
                        break;
                    }
                    if (z2) {
                        if (isPercentEncoded(canonicalize, iCharCount, i2)) {
                        }
                    }
                    if (iCodePointAt == 43) {
                    }
                    iCharCount += Character.charCount(iCodePointAt);
                } else if (iCodePointAt == 43 || !z3) {
                    iCharCount += Character.charCount(iCodePointAt);
                }
                Buffer buffer = new Buffer();
                buffer.writeUtf8(canonicalize, i, iCharCount);
                writeCanonicalized(buffer, canonicalize, iCharCount, i2, encodeSet, z, z2, z3, z4, charset);
                return buffer.readUtf8();
            }
            Buffer buffer2 = new Buffer();
            buffer2.writeUtf8(canonicalize, i, iCharCount);
            writeCanonicalized(buffer2, canonicalize, iCharCount, i2, encodeSet, z, z2, z3, z4, charset);
            return buffer2.readUtf8();
        }

        @JvmStatic
        public final int defaultPort(@NotNull String scheme) {
            Intrinsics.checkNotNullParameter(scheme, "scheme");
            int iHashCode = scheme.hashCode();
            if (iHashCode != 3213448) {
                if (iHashCode == 99617003 && scheme.equals("https")) {
                    return 443;
                }
            } else if (scheme.equals("http")) {
                return 80;
            }
            return -1;
        }

        @JvmStatic
        @JvmName(name = "get")
        @NotNull
        public final HttpUrl get(@NotNull String toHttpUrl) {
            Intrinsics.checkNotNullParameter(toHttpUrl, "$this$toHttpUrl");
            return new Builder().parse$okhttp(null, toHttpUrl).build();
        }

        @JvmStatic
        @JvmName(name = "parse")
        @Nullable
        public final HttpUrl parse(@NotNull String toHttpUrlOrNull) {
            Intrinsics.checkNotNullParameter(toHttpUrlOrNull, "$this$toHttpUrlOrNull");
            try {
                return get(toHttpUrlOrNull);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        @NotNull
        public final String percentDecode$okhttp(@NotNull String percentDecode, int i, int i2, boolean z) {
            Intrinsics.checkNotNullParameter(percentDecode, "$this$percentDecode");
            for (int i3 = i; i3 < i2; i3++) {
                char cCharAt = percentDecode.charAt(i3);
                if (cCharAt == '%' || (cCharAt == '+' && z)) {
                    Buffer buffer = new Buffer();
                    buffer.writeUtf8(percentDecode, i, i3);
                    writePercentDecoded(buffer, percentDecode, i3, i2, z);
                    return buffer.readUtf8();
                }
            }
            String strSubstring = percentDecode.substring(i, i2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return strSubstring;
        }

        public final void toPathString$okhttp(@NotNull List<String> toPathString, @NotNull StringBuilder out) {
            Intrinsics.checkNotNullParameter(toPathString, "$this$toPathString");
            Intrinsics.checkNotNullParameter(out, "out");
            int size = toPathString.size();
            for (int i = 0; i < size; i++) {
                out.append('/');
                out.append(toPathString.get(i));
            }
        }

        @NotNull
        public final List<String> toQueryNamesAndValues$okhttp(@NotNull String toQueryNamesAndValues) {
            Intrinsics.checkNotNullParameter(toQueryNamesAndValues, "$this$toQueryNamesAndValues");
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i <= toQueryNamesAndValues.length()) {
                int iIndexOf$default = StringsKt.indexOf$default(toQueryNamesAndValues, Typography.amp, i, 4);
                if (iIndexOf$default == -1) {
                    iIndexOf$default = toQueryNamesAndValues.length();
                }
                int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) toQueryNamesAndValues, '=', i, 4);
                if (iIndexOf$default2 == -1 || iIndexOf$default2 > iIndexOf$default) {
                    String strSubstring = toQueryNamesAndValues.substring(i, iIndexOf$default);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    arrayList.add(strSubstring);
                    arrayList.add(null);
                } else {
                    String strSubstring2 = toQueryNamesAndValues.substring(i, iIndexOf$default2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    arrayList.add(strSubstring2);
                    String strSubstring3 = toQueryNamesAndValues.substring(iIndexOf$default2 + 1, iIndexOf$default);
                    Intrinsics.checkNotNullExpressionValue(strSubstring3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    arrayList.add(strSubstring3);
                }
                i = iIndexOf$default + 1;
            }
            return arrayList;
        }

        public final void toQueryString$okhttp(@NotNull List<String> toQueryString, @NotNull StringBuilder out) {
            Intrinsics.checkNotNullParameter(toQueryString, "$this$toQueryString");
            Intrinsics.checkNotNullParameter(out, "out");
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, toQueryString.size()), 2);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if (step >= 0) {
                if (first > last) {
                    return;
                }
            } else if (first < last) {
                return;
            }
            while (true) {
                String str = toQueryString.get(first);
                String str2 = toQueryString.get(first + 1);
                if (first > 0) {
                    out.append(Typography.amp);
                }
                out.append(str);
                if (str2 != null) {
                    out.append('=');
                    out.append(str2);
                }
                if (first == last) {
                    return;
                } else {
                    first += step;
                }
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrlOrNull()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        @JvmName(name = "-deprecated_get")
        @Nullable
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final HttpUrl m1953deprecated_get(@NotNull URL url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return get(url);
        }

        @JvmStatic
        @JvmName(name = "get")
        @Nullable
        public final HttpUrl get(@NotNull URL toHttpUrlOrNull) {
            Intrinsics.checkNotNullParameter(toHttpUrlOrNull, "$this$toHttpUrlOrNull");
            String string = toHttpUrlOrNull.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString()");
            return parse(string);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "uri.toHttpUrlOrNull()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        @JvmName(name = "-deprecated_get")
        @Nullable
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final HttpUrl m1952deprecated_get(@NotNull URI uri) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            return get(uri);
        }

        @JvmStatic
        @JvmName(name = "get")
        @Nullable
        public final HttpUrl get(@NotNull URI toHttpUrlOrNull) {
            Intrinsics.checkNotNullParameter(toHttpUrlOrNull, "$this$toHttpUrlOrNull");
            String string = toHttpUrlOrNull.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString()");
            return parse(string);
        }
    }

    public HttpUrl(@NotNull String scheme, @NotNull String username, @NotNull String password, @NotNull String host, int i, @NotNull List<String> pathSegments, @Nullable List<String> list, @Nullable String str, @NotNull String url) {
        Intrinsics.checkNotNullParameter(scheme, "scheme");
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        Intrinsics.checkNotNullParameter(url, "url");
        this.scheme = scheme;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = i;
        this.pathSegments = pathSegments;
        this.queryNamesAndValues = list;
        this.fragment = str;
        this.url = url;
        this.isHttps = Intrinsics.areEqual(scheme, "https");
    }

    @JvmStatic
    public static final int defaultPort(@NotNull String str) {
        return INSTANCE.defaultPort(str);
    }

    @JvmStatic
    @JvmName(name = "get")
    @NotNull
    public static final HttpUrl get(@NotNull String str) {
        return INSTANCE.get(str);
    }

    @JvmStatic
    @JvmName(name = "get")
    @Nullable
    public static final HttpUrl get(@NotNull URI uri) {
        return INSTANCE.get(uri);
    }

    @JvmStatic
    @JvmName(name = "get")
    @Nullable
    public static final HttpUrl get(@NotNull URL url) {
        return INSTANCE.get(url);
    }

    @JvmStatic
    @JvmName(name = "parse")
    @Nullable
    public static final HttpUrl parse(@NotNull String str) {
        return INSTANCE.parse(str);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedFragment", imports = {}))
    @JvmName(name = "-deprecated_encodedFragment")
    @Nullable
    /* JADX INFO: renamed from: -deprecated_encodedFragment, reason: not valid java name */
    public final String m1932deprecated_encodedFragment() {
        return encodedFragment();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPassword", imports = {}))
    @JvmName(name = "-deprecated_encodedPassword")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_encodedPassword, reason: not valid java name */
    public final String m1933deprecated_encodedPassword() {
        return encodedPassword();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPath", imports = {}))
    @JvmName(name = "-deprecated_encodedPath")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_encodedPath, reason: not valid java name */
    public final String m1934deprecated_encodedPath() {
        return encodedPath();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPathSegments", imports = {}))
    @JvmName(name = "-deprecated_encodedPathSegments")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_encodedPathSegments, reason: not valid java name */
    public final List<String> m1935deprecated_encodedPathSegments() {
        return encodedPathSegments();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedQuery", imports = {}))
    @JvmName(name = "-deprecated_encodedQuery")
    @Nullable
    /* JADX INFO: renamed from: -deprecated_encodedQuery, reason: not valid java name */
    public final String m1936deprecated_encodedQuery() {
        return encodedQuery();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedUsername", imports = {}))
    @JvmName(name = "-deprecated_encodedUsername")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_encodedUsername, reason: not valid java name */
    public final String m1937deprecated_encodedUsername() {
        return encodedUsername();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "fragment", imports = {}))
    @JvmName(name = "-deprecated_fragment")
    @Nullable
    /* JADX INFO: renamed from: -deprecated_fragment, reason: not valid java name and from getter */
    public final String getFragment() {
        return this.fragment;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "host", imports = {}))
    @JvmName(name = "-deprecated_host")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_host, reason: not valid java name and from getter */
    public final String getHost() {
        return this.host;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "password", imports = {}))
    @JvmName(name = "-deprecated_password")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_password, reason: not valid java name and from getter */
    public final String getPassword() {
        return this.password;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pathSegments", imports = {}))
    @JvmName(name = "-deprecated_pathSegments")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_pathSegments, reason: not valid java name */
    public final List<String> m1941deprecated_pathSegments() {
        return this.pathSegments;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pathSize", imports = {}))
    @JvmName(name = "-deprecated_pathSize")
    /* JADX INFO: renamed from: -deprecated_pathSize, reason: not valid java name */
    public final int m1942deprecated_pathSize() {
        return pathSize();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "port", imports = {}))
    @JvmName(name = "-deprecated_port")
    /* JADX INFO: renamed from: -deprecated_port, reason: not valid java name and from getter */
    public final int getPort() {
        return this.port;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = SearchIntents.EXTRA_QUERY, imports = {}))
    @JvmName(name = "-deprecated_query")
    @Nullable
    /* JADX INFO: renamed from: -deprecated_query, reason: not valid java name */
    public final String m1944deprecated_query() {
        return query();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "queryParameterNames", imports = {}))
    @JvmName(name = "-deprecated_queryParameterNames")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_queryParameterNames, reason: not valid java name */
    public final Set<String> m1945deprecated_queryParameterNames() {
        return queryParameterNames();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "querySize", imports = {}))
    @JvmName(name = "-deprecated_querySize")
    /* JADX INFO: renamed from: -deprecated_querySize, reason: not valid java name */
    public final int m1946deprecated_querySize() {
        return querySize();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "scheme", imports = {}))
    @JvmName(name = "-deprecated_scheme")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_scheme, reason: not valid java name and from getter */
    public final String getScheme() {
        return this.scheme;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to toUri()", replaceWith = @ReplaceWith(expression = "toUri()", imports = {}))
    @JvmName(name = "-deprecated_uri")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_uri, reason: not valid java name */
    public final URI m1948deprecated_uri() {
        return uri();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to toUrl()", replaceWith = @ReplaceWith(expression = "toUrl()", imports = {}))
    @JvmName(name = "-deprecated_url")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_url, reason: not valid java name */
    public final URL m1949deprecated_url() {
        return url();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "username", imports = {}))
    @JvmName(name = "-deprecated_username")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_username, reason: not valid java name and from getter */
    public final String getUsername() {
        return this.username;
    }

    @JvmName(name = "encodedFragment")
    @Nullable
    public final String encodedFragment() {
        if (this.fragment == null) {
            return null;
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '#', 0, 6) + 1;
        String str = this.url;
        Objects.requireNonNull(str, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = str.substring(iIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    @JvmName(name = "encodedPassword")
    @NotNull
    public final String encodedPassword() {
        if (this.password.length() == 0) {
            return "";
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, ':', this.scheme.length() + 3, 4) + 1;
        int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) this.url, '@', 0, 6);
        String str = this.url;
        Objects.requireNonNull(str, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = str.substring(iIndexOf$default, iIndexOf$default2);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return strSubstring;
    }

    @JvmName(name = "encodedPath")
    @NotNull
    public final String encodedPath() {
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '/', this.scheme.length() + 3, 4);
        String str = this.url;
        int iDelimiterOffset = Util.delimiterOffset(str, "?#", iIndexOf$default, str.length());
        String str2 = this.url;
        Objects.requireNonNull(str2, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = str2.substring(iIndexOf$default, iDelimiterOffset);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return strSubstring;
    }

    @JvmName(name = "encodedPathSegments")
    @NotNull
    public final List<String> encodedPathSegments() {
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '/', this.scheme.length() + 3, 4);
        String str = this.url;
        int iDelimiterOffset = Util.delimiterOffset(str, "?#", iIndexOf$default, str.length());
        ArrayList arrayList = new ArrayList();
        while (iIndexOf$default < iDelimiterOffset) {
            int i = iIndexOf$default + 1;
            int iDelimiterOffset2 = Util.delimiterOffset(this.url, '/', i, iDelimiterOffset);
            String str2 = this.url;
            Objects.requireNonNull(str2, "null cannot be cast to non-null type java.lang.String");
            String strSubstring = str2.substring(i, iDelimiterOffset2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            arrayList.add(strSubstring);
            iIndexOf$default = iDelimiterOffset2;
        }
        return arrayList;
    }

    @JvmName(name = "encodedQuery")
    @Nullable
    public final String encodedQuery() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '?', 0, 6) + 1;
        String str = this.url;
        int iDelimiterOffset = Util.delimiterOffset(str, '#', iIndexOf$default, str.length());
        String str2 = this.url;
        Objects.requireNonNull(str2, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = str2.substring(iIndexOf$default, iDelimiterOffset);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return strSubstring;
    }

    @JvmName(name = "encodedUsername")
    @NotNull
    public final String encodedUsername() {
        if (this.username.length() == 0) {
            return "";
        }
        int length = this.scheme.length() + 3;
        String str = this.url;
        int iDelimiterOffset = Util.delimiterOffset(str, ":@", length, str.length());
        String str2 = this.url;
        Objects.requireNonNull(str2, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = str2.substring(length, iDelimiterOffset);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof HttpUrl) && Intrinsics.areEqual(((HttpUrl) other).url, this.url);
    }

    @JvmName(name = "fragment")
    @Nullable
    public final String fragment() {
        return this.fragment;
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    @JvmName(name = "host")
    @NotNull
    public final String host() {
        return this.host;
    }

    /* JADX INFO: renamed from: isHttps, reason: from getter */
    public final boolean getIsHttps() {
        return this.isHttps;
    }

    @NotNull
    public final Builder newBuilder() {
        Builder builder = new Builder();
        builder.setScheme$okhttp(this.scheme);
        builder.setEncodedUsername$okhttp(encodedUsername());
        builder.setEncodedPassword$okhttp(encodedPassword());
        builder.setHost$okhttp(this.host);
        builder.setPort$okhttp(this.port != INSTANCE.defaultPort(this.scheme) ? this.port : -1);
        builder.getEncodedPathSegments$okhttp().clear();
        builder.getEncodedPathSegments$okhttp().addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.setEncodedFragment$okhttp(encodedFragment());
        return builder;
    }

    @JvmName(name = "password")
    @NotNull
    public final String password() {
        return this.password;
    }

    @JvmName(name = "pathSegments")
    @NotNull
    public final List<String> pathSegments() {
        return this.pathSegments;
    }

    @JvmName(name = "pathSize")
    public final int pathSize() {
        return this.pathSegments.size();
    }

    @JvmName(name = "port")
    public final int port() {
        return this.port;
    }

    @JvmName(name = SearchIntents.EXTRA_QUERY)
    @Nullable
    public final String query() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        INSTANCE.toQueryString$okhttp(this.queryNamesAndValues, sb);
        return sb.toString();
    }

    @Nullable
    public final String queryParameter(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        List<String> list = this.queryNamesAndValues;
        if (list == null) {
            return null;
        }
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, list.size()), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (!Intrinsics.areEqual(name, this.queryNamesAndValues.get(first))) {
                if (first != last) {
                    first += step;
                }
            }
            return this.queryNamesAndValues.get(first + 1);
        }
        return null;
    }

    @NotNull
    public final String queryParameterName(int index) {
        List<String> list = this.queryNamesAndValues;
        if (list == null) {
            throw new IndexOutOfBoundsException();
        }
        String str = list.get(index * 2);
        Intrinsics.checkNotNull(str);
        return str;
    }

    @JvmName(name = "queryParameterNames")
    @NotNull
    public final Set<String> queryParameterNames() {
        if (this.queryNamesAndValues == null) {
            return SetsKt.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (true) {
                String str = this.queryNamesAndValues.get(first);
                Intrinsics.checkNotNull(str);
                linkedHashSet.add(str);
                if (first == last) {
                    break;
                }
                first += step;
            }
        }
        Set<String> setUnmodifiableSet = Collections.unmodifiableSet(linkedHashSet);
        Intrinsics.checkNotNullExpressionValue(setUnmodifiableSet, "Collections.unmodifiableSet(result)");
        return setUnmodifiableSet;
    }

    @Nullable
    public final String queryParameterValue(int index) {
        List<String> list = this.queryNamesAndValues;
        if (list != null) {
            return list.get((index * 2) + 1);
        }
        throw new IndexOutOfBoundsException();
    }

    @NotNull
    public final List<String> queryParameterValues(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (this.queryNamesAndValues == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (true) {
                if (Intrinsics.areEqual(name, this.queryNamesAndValues.get(first))) {
                    arrayList.add(this.queryNamesAndValues.get(first + 1));
                }
                if (first == last) {
                    break;
                }
                first += step;
            }
        }
        List<String> listUnmodifiableList = Collections.unmodifiableList(arrayList);
        Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "Collections.unmodifiableList(result)");
        return listUnmodifiableList;
    }

    @JvmName(name = "querySize")
    public final int querySize() {
        List<String> list = this.queryNamesAndValues;
        if (list != null) {
            return list.size() / 2;
        }
        return 0;
    }

    @NotNull
    public final String redact() {
        Builder builderNewBuilder = newBuilder("/...");
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder.username("").password("").build().getUrl();
    }

    @Nullable
    public final HttpUrl resolve(@NotNull String link) {
        Intrinsics.checkNotNullParameter(link, "link");
        Builder builderNewBuilder = newBuilder(link);
        if (builderNewBuilder != null) {
            return builderNewBuilder.build();
        }
        return null;
    }

    @JvmName(name = "scheme")
    @NotNull
    public final String scheme() {
        return this.scheme;
    }

    @NotNull
    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getUrl() {
        return this.url;
    }

    @Nullable
    public final String topPrivateDomain() {
        if (Util.canParseAsIpAddress(this.host)) {
            return null;
        }
        return PublicSuffixDatabase.INSTANCE.get().getEffectiveTldPlusOne(this.host);
    }

    @JvmName(name = "uri")
    @NotNull
    public final URI uri() {
        String string = newBuilder().reencodeForUri$okhttp().toString();
        try {
            return new URI(string);
        } catch (URISyntaxException e) {
            try {
                URI uriCreate = URI.create(new Regex("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]").replace(string, ""));
                Intrinsics.checkNotNullExpressionValue(uriCreate, "try {\n        val stripp…e) // Unexpected!\n      }");
                return uriCreate;
            } catch (Exception unused) {
                throw new RuntimeException(e);
            }
        }
    }

    @JvmName(name = ImagesContract.URL)
    @NotNull
    public final URL url() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @JvmName(name = "username")
    @NotNull
    public final String username() {
        return this.username;
    }

    @Nullable
    public final Builder newBuilder(@NotNull String link) {
        Intrinsics.checkNotNullParameter(link, "link");
        try {
            return new Builder().parse$okhttp(this, link);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
