package okhttp3;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.net.HttpHeaders;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import okhttp3.internal.Util;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CacheControl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u0000 !2\u00020\u0001:\u0002 !Bq\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\u0006\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012J\r\u0010\u000f\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0015J\r\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0016J\r\u0010\u000b\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0017J\r\u0010\f\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0018J\r\u0010\n\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0019J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001aJ\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001bJ\r\u0010\u000e\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001cJ\r\u0010\r\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001dJ\r\u0010\u0007\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u001eJ\b\u0010\u001f\u001a\u00020\u0011H\u0016R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u000f\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0013R\u0013\u0010\u0005\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0014R\u0013\u0010\u000b\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0014R\u0013\u0010\f\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0014R\u0013\u0010\n\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0013R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0013R\u0013\u0010\u000e\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0013R\u0013\u0010\r\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0013R\u0013\u0010\u0007\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0014¨\u0006\""}, d2 = {"Lokhttp3/CacheControl;", "", "noCache", "", "noStore", "maxAgeSeconds", "", "sMaxAgeSeconds", "isPrivate", "isPublic", "mustRevalidate", "maxStaleSeconds", "minFreshSeconds", "onlyIfCached", "noTransform", "immutable", "headerValue", "", "(ZZIIZZZIIZZZLjava/lang/String;)V", "()Z", "()I", "-deprecated_immutable", "-deprecated_maxAgeSeconds", "-deprecated_maxStaleSeconds", "-deprecated_minFreshSeconds", "-deprecated_mustRevalidate", "-deprecated_noCache", "-deprecated_noStore", "-deprecated_noTransform", "-deprecated_onlyIfCached", "-deprecated_sMaxAgeSeconds", "toString", "Builder", "Companion", "okhttp"}, k = 1, mv = {1, 4, 1})
public final class CacheControl {
    private String headerValue;
    private final boolean immutable;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @JvmField
    @NotNull
    public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();

    @JvmField
    @NotNull
    public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();

    /* JADX INFO: compiled from: CacheControl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u0003\u001a\u00020\u0000J\u0016\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\t\u001a\u00020\u0000J\u0006\u0010\n\u001a\u00020\u0000J\u0006\u0010\u000b\u001a\u00020\u0000J\u0006\u0010\f\u001a\u00020\u0000J\f\u0010\u0014\u001a\u00020\u0006*\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lokhttp3/CacheControl$Builder;", "", "()V", "immutable", "", "maxAgeSeconds", "", "maxStaleSeconds", "minFreshSeconds", "noCache", "noStore", "noTransform", "onlyIfCached", "build", "Lokhttp3/CacheControl;", "maxAge", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "maxStale", "minFresh", "clampToInt", "", "okhttp"}, k = 1, mv = {1, 4, 1})
    public static final class Builder {
        private boolean immutable;
        private int maxAgeSeconds = -1;
        private int maxStaleSeconds = -1;
        private int minFreshSeconds = -1;
        private boolean noCache;
        private boolean noStore;
        private boolean noTransform;
        private boolean onlyIfCached;

        private final int clampToInt(long j) {
            if (j > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int) j;
        }

        @NotNull
        public final CacheControl build() {
            return new CacheControl(this.noCache, this.noStore, this.maxAgeSeconds, -1, false, false, false, this.maxStaleSeconds, this.minFreshSeconds, this.onlyIfCached, this.noTransform, this.immutable, null, null);
        }

        @NotNull
        public final Builder immutable() {
            this.immutable = true;
            return this;
        }

        @NotNull
        public final Builder maxAge(int maxAge, @NotNull TimeUnit timeUnit) {
            Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
            if (!(maxAge >= 0)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("maxAge < 0: ", maxAge).toString());
            }
            this.maxAgeSeconds = clampToInt(timeUnit.toSeconds(maxAge));
            return this;
        }

        @NotNull
        public final Builder maxStale(int maxStale, @NotNull TimeUnit timeUnit) {
            Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
            if (!(maxStale >= 0)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("maxStale < 0: ", maxStale).toString());
            }
            this.maxStaleSeconds = clampToInt(timeUnit.toSeconds(maxStale));
            return this;
        }

        @NotNull
        public final Builder minFresh(int minFresh, @NotNull TimeUnit timeUnit) {
            Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
            if (!(minFresh >= 0)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("minFresh < 0: ", minFresh).toString());
            }
            this.minFreshSeconds = clampToInt(timeUnit.toSeconds(minFresh));
            return this;
        }

        @NotNull
        public final Builder noCache() {
            this.noCache = true;
            return this;
        }

        @NotNull
        public final Builder noStore() {
            this.noStore = true;
            return this;
        }

        @NotNull
        public final Builder noTransform() {
            this.noTransform = true;
            return this;
        }

        @NotNull
        public final Builder onlyIfCached() {
            this.onlyIfCached = true;
            return this;
        }
    }

    /* JADX INFO: compiled from: CacheControl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001e\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\nH\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lokhttp3/CacheControl$Companion;", "", "()V", "FORCE_CACHE", "Lokhttp3/CacheControl;", "FORCE_NETWORK", "parse", "headers", "Lokhttp3/Headers;", "indexOfElement", "", "", "characters", "startIndex", "okhttp"}, k = 1, mv = {1, 4, 1})
    public static final class Companion {
        private Companion() {
        }

        private final int indexOfElement(String str, String str2, int i) {
            int length = str.length();
            while (i < length) {
                if (StringsKt.contains$default(str2, str.charAt(i))) {
                    return i;
                }
                i++;
            }
            return str.length();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x004a  */
        /* JADX WARN: Code duplicated, block: B:17:0x006c  */
        /* JADX WARN: Code duplicated, block: B:28:0x00c1  */
        /* JADX WARN: Code duplicated, block: B:32:0x00d1  */
        /* JADX WARN: Code duplicated, block: B:33:0x00d5  */
        /* JADX WARN: Code duplicated, block: B:35:0x00dd  */
        /* JADX WARN: Code duplicated, block: B:36:0x00e1  */
        /* JADX WARN: Code duplicated, block: B:38:0x00e9  */
        /* JADX WARN: Code duplicated, block: B:39:0x00f0  */
        /* JADX WARN: Code duplicated, block: B:41:0x00f9  */
        /* JADX WARN: Code duplicated, block: B:42:0x00fe  */
        /* JADX WARN: Code duplicated, block: B:44:0x0106  */
        /* JADX WARN: Code duplicated, block: B:45:0x0109  */
        /* JADX WARN: Code duplicated, block: B:47:0x0111  */
        /* JADX WARN: Code duplicated, block: B:48:0x0115  */
        /* JADX WARN: Code duplicated, block: B:50:0x011d  */
        /* JADX WARN: Code duplicated, block: B:51:0x0121  */
        /* JADX WARN: Code duplicated, block: B:53:0x0129  */
        /* JADX WARN: Code duplicated, block: B:54:0x0132  */
        /* JADX WARN: Code duplicated, block: B:56:0x013a  */
        /* JADX WARN: Code duplicated, block: B:57:0x0140  */
        /* JADX WARN: Code duplicated, block: B:59:0x0149  */
        /* JADX WARN: Code duplicated, block: B:60:0x014c  */
        /* JADX WARN: Code duplicated, block: B:62:0x0154  */
        /* JADX WARN: Code duplicated, block: B:63:0x0157  */
        /* JADX WARN: Code duplicated, block: B:65:0x015f  */
        /* JADX WARN: Code duplicated, block: B:88:0x0161 A[SYNTHETIC] */
        @JvmStatic
        @NotNull
        public final CacheControl parse(@NotNull Headers headers) {
            int i;
            int iIndexOfElement;
            String string;
            int i2;
            int iIndexOfElement2;
            String string2;
            Headers headers2 = headers;
            Intrinsics.checkNotNullParameter(headers2, "headers");
            int size = headers.size();
            int i3 = 0;
            boolean z = true;
            String str = null;
            boolean z2 = false;
            boolean z3 = false;
            int nonNegativeInt = -1;
            int nonNegativeInt2 = -1;
            boolean z4 = false;
            boolean z5 = false;
            boolean z6 = false;
            int nonNegativeInt3 = -1;
            int nonNegativeInt4 = -1;
            boolean z7 = false;
            boolean z8 = false;
            boolean z9 = false;
            while (i3 < size) {
                String strName = headers2.name(i3);
                String strValue = headers2.value(i3);
                if (StringsKt.equals(strName, HttpHeaders.CACHE_CONTROL)) {
                    if (str == null) {
                        str = strValue;
                    }
                    i = 0;
                    while (i < strValue.length()) {
                        iIndexOfElement = indexOfElement(strValue, "=,;", i);
                        String strSubstring = strValue.substring(i, iIndexOfElement);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        Objects.requireNonNull(strSubstring, "null cannot be cast to non-null type kotlin.CharSequence");
                        string = StringsKt.trim((CharSequence) strSubstring).toString();
                        if (iIndexOfElement != strValue.length()) {
                            i2 = size;
                            if (strValue.charAt(iIndexOfElement) == ',' && strValue.charAt(iIndexOfElement) != ';') {
                                int iIndexOfNonWhitespace = Util.indexOfNonWhitespace(strValue, iIndexOfElement + 1);
                                if (iIndexOfNonWhitespace >= strValue.length() || strValue.charAt(iIndexOfNonWhitespace) != '\"') {
                                    iIndexOfElement2 = indexOfElement(strValue, ",;", iIndexOfNonWhitespace);
                                    String strSubstring2 = strValue.substring(iIndexOfNonWhitespace, iIndexOfElement2);
                                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                    Objects.requireNonNull(strSubstring2, "null cannot be cast to non-null type kotlin.CharSequence");
                                    string2 = StringsKt.trim((CharSequence) strSubstring2).toString();
                                } else {
                                    int i4 = iIndexOfNonWhitespace + 1;
                                    int iIndexOf$default = StringsKt.indexOf$default(strValue, Typography.quote, i4, 4);
                                    string2 = strValue.substring(i4, iIndexOf$default);
                                    Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                    iIndexOfElement2 = iIndexOf$default + 1;
                                }
                            }
                            if (StringsKt.equals("no-cache", string)) {
                                z2 = true;
                            } else if (StringsKt.equals("no-store", string)) {
                                z3 = true;
                            } else if (StringsKt.equals("max-age", string)) {
                                nonNegativeInt = Util.toNonNegativeInt(string2, -1);
                            } else if (StringsKt.equals("s-maxage", string)) {
                                nonNegativeInt2 = Util.toNonNegativeInt(string2, -1);
                            } else if (StringsKt.equals("private", string)) {
                                z4 = true;
                            } else if (StringsKt.equals("public", string)) {
                                z5 = true;
                            } else if (StringsKt.equals("must-revalidate", string)) {
                                z6 = true;
                            } else if (StringsKt.equals("max-stale", string)) {
                                nonNegativeInt3 = Util.toNonNegativeInt(string2, Integer.MAX_VALUE);
                            } else if (StringsKt.equals("min-fresh", string)) {
                                nonNegativeInt4 = Util.toNonNegativeInt(string2, -1);
                            } else if (StringsKt.equals("only-if-cached", string)) {
                                z7 = true;
                            } else if (StringsKt.equals("no-transform", string)) {
                                z8 = true;
                            } else if (StringsKt.equals("immutable", string)) {
                                z9 = true;
                            }
                            i = iIndexOfElement2;
                            size = i2;
                        } else {
                            i2 = size;
                        }
                        iIndexOfElement2 = iIndexOfElement + 1;
                        string2 = null;
                        if (StringsKt.equals("no-cache", string)) {
                            z2 = true;
                        } else if (StringsKt.equals("no-store", string)) {
                            z3 = true;
                        } else if (StringsKt.equals("max-age", string)) {
                            nonNegativeInt = Util.toNonNegativeInt(string2, -1);
                        } else if (StringsKt.equals("s-maxage", string)) {
                            nonNegativeInt2 = Util.toNonNegativeInt(string2, -1);
                        } else if (StringsKt.equals("private", string)) {
                            z4 = true;
                        } else if (StringsKt.equals("public", string)) {
                            z5 = true;
                        } else if (StringsKt.equals("must-revalidate", string)) {
                            z6 = true;
                        } else if (StringsKt.equals("max-stale", string)) {
                            nonNegativeInt3 = Util.toNonNegativeInt(string2, Integer.MAX_VALUE);
                        } else if (StringsKt.equals("min-fresh", string)) {
                            nonNegativeInt4 = Util.toNonNegativeInt(string2, -1);
                        } else if (StringsKt.equals("only-if-cached", string)) {
                            z7 = true;
                        } else if (StringsKt.equals("no-transform", string)) {
                            z8 = true;
                        } else if (StringsKt.equals("immutable", string)) {
                            z9 = true;
                        }
                        i = iIndexOfElement2;
                        size = i2;
                    }
                    i3++;
                    headers2 = headers;
                    size = size;
                } else {
                    if (StringsKt.equals(strName, HttpHeaders.PRAGMA)) {
                    }
                    i3++;
                    headers2 = headers;
                    size = size;
                }
                z = false;
                i = 0;
                while (i < strValue.length()) {
                    iIndexOfElement = indexOfElement(strValue, "=,;", i);
                    String strSubstring3 = strValue.substring(i, iIndexOfElement);
                    Intrinsics.checkNotNullExpressionValue(strSubstring3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    Objects.requireNonNull(strSubstring3, "null cannot be cast to non-null type kotlin.CharSequence");
                    string = StringsKt.trim((CharSequence) strSubstring3).toString();
                    if (iIndexOfElement != strValue.length()) {
                        i2 = size;
                        if (strValue.charAt(iIndexOfElement) == ',') {
                        }
                        if (StringsKt.equals("no-cache", string)) {
                            z2 = true;
                        } else if (StringsKt.equals("no-store", string)) {
                            z3 = true;
                        } else if (StringsKt.equals("max-age", string)) {
                            nonNegativeInt = Util.toNonNegativeInt(string2, -1);
                        } else if (StringsKt.equals("s-maxage", string)) {
                            nonNegativeInt2 = Util.toNonNegativeInt(string2, -1);
                        } else if (StringsKt.equals("private", string)) {
                            z4 = true;
                        } else if (StringsKt.equals("public", string)) {
                            z5 = true;
                        } else if (StringsKt.equals("must-revalidate", string)) {
                            z6 = true;
                        } else if (StringsKt.equals("max-stale", string)) {
                            nonNegativeInt3 = Util.toNonNegativeInt(string2, Integer.MAX_VALUE);
                        } else if (StringsKt.equals("min-fresh", string)) {
                            nonNegativeInt4 = Util.toNonNegativeInt(string2, -1);
                        } else if (StringsKt.equals("only-if-cached", string)) {
                            z7 = true;
                        } else if (StringsKt.equals("no-transform", string)) {
                            z8 = true;
                        } else if (StringsKt.equals("immutable", string)) {
                            z9 = true;
                        }
                        i = iIndexOfElement2;
                        size = i2;
                    } else {
                        i2 = size;
                    }
                    iIndexOfElement2 = iIndexOfElement + 1;
                    string2 = null;
                    if (StringsKt.equals("no-cache", string)) {
                        z2 = true;
                    } else if (StringsKt.equals("no-store", string)) {
                        z3 = true;
                    } else if (StringsKt.equals("max-age", string)) {
                        nonNegativeInt = Util.toNonNegativeInt(string2, -1);
                    } else if (StringsKt.equals("s-maxage", string)) {
                        nonNegativeInt2 = Util.toNonNegativeInt(string2, -1);
                    } else if (StringsKt.equals("private", string)) {
                        z4 = true;
                    } else if (StringsKt.equals("public", string)) {
                        z5 = true;
                    } else if (StringsKt.equals("must-revalidate", string)) {
                        z6 = true;
                    } else if (StringsKt.equals("max-stale", string)) {
                        nonNegativeInt3 = Util.toNonNegativeInt(string2, Integer.MAX_VALUE);
                    } else if (StringsKt.equals("min-fresh", string)) {
                        nonNegativeInt4 = Util.toNonNegativeInt(string2, -1);
                    } else if (StringsKt.equals("only-if-cached", string)) {
                        z7 = true;
                    } else if (StringsKt.equals("no-transform", string)) {
                        z8 = true;
                    } else if (StringsKt.equals("immutable", string)) {
                        z9 = true;
                    }
                    i = iIndexOfElement2;
                    size = i2;
                }
                i3++;
                headers2 = headers;
                size = size;
            }
            return new CacheControl(z2, z3, nonNegativeInt, nonNegativeInt2, z4, z5, z6, nonNegativeInt3, nonNegativeInt4, z7, z8, z9, !z ? null : str, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private CacheControl(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, boolean z5, int i3, int i4, boolean z6, boolean z7, boolean z8, String str) {
        this.noCache = z;
        this.noStore = z2;
        this.maxAgeSeconds = i;
        this.sMaxAgeSeconds = i2;
        this.isPrivate = z3;
        this.isPublic = z4;
        this.mustRevalidate = z5;
        this.maxStaleSeconds = i3;
        this.minFreshSeconds = i4;
        this.onlyIfCached = z6;
        this.noTransform = z7;
        this.immutable = z8;
        this.headerValue = str;
    }

    @JvmStatic
    @NotNull
    public static final CacheControl parse(@NotNull Headers headers) {
        return INSTANCE.parse(headers);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "immutable", imports = {}))
    @JvmName(name = "-deprecated_immutable")
    /* JADX INFO: renamed from: -deprecated_immutable, reason: not valid java name and from getter */
    public final boolean getImmutable() {
        return this.immutable;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "maxAgeSeconds", imports = {}))
    @JvmName(name = "-deprecated_maxAgeSeconds")
    /* JADX INFO: renamed from: -deprecated_maxAgeSeconds, reason: not valid java name and from getter */
    public final int getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "maxStaleSeconds", imports = {}))
    @JvmName(name = "-deprecated_maxStaleSeconds")
    /* JADX INFO: renamed from: -deprecated_maxStaleSeconds, reason: not valid java name and from getter */
    public final int getMaxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "minFreshSeconds", imports = {}))
    @JvmName(name = "-deprecated_minFreshSeconds")
    /* JADX INFO: renamed from: -deprecated_minFreshSeconds, reason: not valid java name and from getter */
    public final int getMinFreshSeconds() {
        return this.minFreshSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "mustRevalidate", imports = {}))
    @JvmName(name = "-deprecated_mustRevalidate")
    /* JADX INFO: renamed from: -deprecated_mustRevalidate, reason: not valid java name and from getter */
    public final boolean getMustRevalidate() {
        return this.mustRevalidate;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noCache", imports = {}))
    @JvmName(name = "-deprecated_noCache")
    /* JADX INFO: renamed from: -deprecated_noCache, reason: not valid java name and from getter */
    public final boolean getNoCache() {
        return this.noCache;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noStore", imports = {}))
    @JvmName(name = "-deprecated_noStore")
    /* JADX INFO: renamed from: -deprecated_noStore, reason: not valid java name and from getter */
    public final boolean getNoStore() {
        return this.noStore;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noTransform", imports = {}))
    @JvmName(name = "-deprecated_noTransform")
    /* JADX INFO: renamed from: -deprecated_noTransform, reason: not valid java name and from getter */
    public final boolean getNoTransform() {
        return this.noTransform;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "onlyIfCached", imports = {}))
    @JvmName(name = "-deprecated_onlyIfCached")
    /* JADX INFO: renamed from: -deprecated_onlyIfCached, reason: not valid java name and from getter */
    public final boolean getOnlyIfCached() {
        return this.onlyIfCached;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sMaxAgeSeconds", imports = {}))
    @JvmName(name = "-deprecated_sMaxAgeSeconds")
    /* JADX INFO: renamed from: -deprecated_sMaxAgeSeconds, reason: not valid java name and from getter */
    public final int getSMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    @JvmName(name = "immutable")
    public final boolean immutable() {
        return this.immutable;
    }

    /* JADX INFO: renamed from: isPrivate, reason: from getter */
    public final boolean getIsPrivate() {
        return this.isPrivate;
    }

    /* JADX INFO: renamed from: isPublic, reason: from getter */
    public final boolean getIsPublic() {
        return this.isPublic;
    }

    @JvmName(name = "maxAgeSeconds")
    public final int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    @JvmName(name = "maxStaleSeconds")
    public final int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    @JvmName(name = "minFreshSeconds")
    public final int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    @JvmName(name = "mustRevalidate")
    public final boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    @JvmName(name = "noCache")
    public final boolean noCache() {
        return this.noCache;
    }

    @JvmName(name = "noStore")
    public final boolean noStore() {
        return this.noStore;
    }

    @JvmName(name = "noTransform")
    public final boolean noTransform() {
        return this.noTransform;
    }

    @JvmName(name = "onlyIfCached")
    public final boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    @JvmName(name = "sMaxAgeSeconds")
    public final int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    @NotNull
    public String toString() {
        String str = this.headerValue;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if (this.noCache) {
            sb.append("no-cache, ");
        }
        if (this.noStore) {
            sb.append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            sb.append("max-age=");
            sb.append(this.maxAgeSeconds);
            sb.append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            sb.append("s-maxage=");
            sb.append(this.sMaxAgeSeconds);
            sb.append(", ");
        }
        if (this.isPrivate) {
            sb.append("private, ");
        }
        if (this.isPublic) {
            sb.append("public, ");
        }
        if (this.mustRevalidate) {
            sb.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            sb.append("max-stale=");
            sb.append(this.maxStaleSeconds);
            sb.append(", ");
        }
        if (this.minFreshSeconds != -1) {
            sb.append("min-fresh=");
            sb.append(this.minFreshSeconds);
            sb.append(", ");
        }
        if (this.onlyIfCached) {
            sb.append("only-if-cached, ");
        }
        if (this.noTransform) {
            sb.append("no-transform, ");
        }
        if (this.immutable) {
            sb.append("immutable, ");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.delete(sb.length() - 2, sb.length());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        this.headerValue = string;
        return string;
    }

    public /* synthetic */ CacheControl(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, boolean z5, int i3, int i4, boolean z6, boolean z7, boolean z8, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, i, i2, z3, z4, z5, i3, i4, z6, z7, z8, str);
    }
}
