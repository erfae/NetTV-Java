package com.android.volley.toolbox;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyLog;
import com.google.common.net.HttpHeaders;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class HttpHeaderParser {
    private static final String DEFAULT_CONTENT_CHARSET = "ISO-8859-1";

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String RFC1123_OUTPUT_FORMAT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    private static final String RFC1123_PARSE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

    public static Map<String, String> getCacheHeaders(Cache.Entry entry) {
        if (entry == null) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap();
        String str = entry.etag;
        if (str != null) {
            map.put(HttpHeaders.IF_NONE_MATCH, str);
        }
        long j = entry.lastModified;
        if (j > 0) {
            map.put(HttpHeaders.IF_MODIFIED_SINCE, newUsGmtFormatter(RFC1123_OUTPUT_FORMAT).format(new Date(j)));
        }
        return map;
    }

    private static SimpleDateFormat newUsGmtFormatter(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }

    @Nullable
    public static Cache.Entry parseCacheHeaders(NetworkResponse networkResponse) {
        boolean z;
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = networkResponse.headers;
        if (map == null) {
            return null;
        }
        String str = map.get(HttpHeaders.DATE);
        long dateAsEpoch = str != null ? parseDateAsEpoch(str) : 0L;
        String str2 = map.get(HttpHeaders.CACHE_CONTROL);
        int i = 0;
        if (str2 != null) {
            String[] strArrSplit = str2.split(",", 0);
            int i2 = 0;
            j = 0;
            j2 = 0;
            while (i < strArrSplit.length) {
                String strTrim = strArrSplit[i].trim();
                if (strTrim.equals("no-cache") || strTrim.equals("no-store")) {
                    return null;
                }
                if (strTrim.startsWith("max-age=")) {
                    try {
                        j = Long.parseLong(strTrim.substring(8));
                    } catch (Exception unused) {
                    }
                } else if (strTrim.startsWith("stale-while-revalidate=")) {
                    j2 = Long.parseLong(strTrim.substring(23));
                } else if (strTrim.equals("must-revalidate") || strTrim.equals("proxy-revalidate")) {
                    i2 = 1;
                }
                i++;
            }
            i = i2;
            z = true;
        } else {
            z = false;
            j = 0;
            j2 = 0;
        }
        String str3 = map.get(HttpHeaders.EXPIRES);
        long dateAsEpoch2 = str3 != null ? parseDateAsEpoch(str3) : 0L;
        String str4 = map.get(HttpHeaders.LAST_MODIFIED);
        long dateAsEpoch3 = str4 != null ? parseDateAsEpoch(str4) : 0L;
        String str5 = map.get(HttpHeaders.ETAG);
        if (z) {
            j4 = jCurrentTimeMillis + (j * 1000);
            if (i != 0) {
                j5 = j4;
            } else {
                Long.signum(j2);
                j5 = (j2 * 1000) + j4;
            }
            j3 = j5;
        } else {
            j3 = 0;
            if (dateAsEpoch <= 0 || dateAsEpoch2 < dateAsEpoch) {
                j4 = 0;
            } else {
                j4 = jCurrentTimeMillis + (dateAsEpoch2 - dateAsEpoch);
                j3 = j4;
            }
        }
        Cache.Entry entry = new Cache.Entry();
        entry.data = networkResponse.data;
        entry.etag = str5;
        entry.softTtl = j4;
        entry.ttl = j3;
        entry.serverDate = dateAsEpoch;
        entry.lastModified = dateAsEpoch3;
        entry.responseHeaders = map;
        entry.allResponseHeaders = networkResponse.allHeaders;
        return entry;
    }

    public static String parseCharset(@Nullable Map<String, String> map, String str) {
        String str2;
        if (map != null && (str2 = map.get("Content-Type")) != null) {
            String[] strArrSplit = str2.split(";", 0);
            for (int i = 1; i < strArrSplit.length; i++) {
                String[] strArrSplit2 = strArrSplit[i].trim().split("=", 0);
                if (strArrSplit2.length == 2 && strArrSplit2[0].equals("charset")) {
                    return strArrSplit2[1];
                }
            }
        }
        return str;
    }

    public static long parseDateAsEpoch(String str) {
        try {
            return newUsGmtFormatter(RFC1123_PARSE_FORMAT).parse(str).getTime();
        } catch (ParseException e) {
            if ("0".equals(str) || "-1".equals(str)) {
                VolleyLog.v("Unable to parse dateStr: %s, falling back to 0", str);
                return 0L;
            }
            VolleyLog.e(e, "Unable to parse dateStr: %s, falling back to 0", str);
            return 0L;
        }
    }

    public static String parseCharset(@Nullable Map<String, String> map) {
        return parseCharset(map, "ISO-8859-1");
    }
}
