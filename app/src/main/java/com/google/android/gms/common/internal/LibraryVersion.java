package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public class LibraryVersion {
    private static final GmsLogger zza = new GmsLogger("LibraryVersion", "");
    private static LibraryVersion zzb = new LibraryVersion();
    private ConcurrentHashMap<String, String> zzc = new ConcurrentHashMap<>();

    @VisibleForTesting
    public LibraryVersion() {
    }

    @NonNull
    @KeepForSdk
    public static LibraryVersion getInstance() {
        return zzb;
    }

    @NonNull
    @KeepForSdk
    public String getVersion(@NonNull String str) throws Throwable {
        String str2;
        Preconditions.checkNotEmpty(str, "Please provide a valid libraryName");
        if (this.zzc.containsKey(str)) {
            return this.zzc.get(str);
        }
        Properties properties = new Properties();
        InputStream inputStream = null;
        String property = null;
        InputStream inputStream2 = null;
        try {
            try {
                InputStream resourceAsStream = LibraryVersion.class.getResourceAsStream(String.format("/%s.properties", str));
                try {
                    if (resourceAsStream != null) {
                        properties.load(resourceAsStream);
                        property = properties.getProperty("version", null);
                        GmsLogger gmsLogger = zza;
                        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 12 + String.valueOf(property).length());
                        sb.append(str);
                        sb.append(" version is ");
                        sb.append(property);
                        gmsLogger.v("LibraryVersion", sb.toString());
                    } else {
                        GmsLogger gmsLogger2 = zza;
                        String strValueOf = String.valueOf(str);
                        gmsLogger2.w("LibraryVersion", strValueOf.length() != 0 ? "Failed to get app version for libraryName: ".concat(strValueOf) : new String("Failed to get app version for libraryName: "));
                    }
                    if (resourceAsStream != null) {
                        IOUtils.closeQuietly(resourceAsStream);
                    }
                } catch (IOException e) {
                    e = e;
                    inputStream = resourceAsStream;
                    str2 = null;
                    GmsLogger gmsLogger3 = zza;
                    String strValueOf2 = String.valueOf(str);
                    gmsLogger3.e("LibraryVersion", strValueOf2.length() != 0 ? "Failed to get app version for libraryName: ".concat(strValueOf2) : new String("Failed to get app version for libraryName: "), e);
                    if (inputStream != null) {
                        IOUtils.closeQuietly(inputStream);
                    }
                    property = str2;
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = resourceAsStream;
                    if (inputStream2 != null) {
                        IOUtils.closeQuietly(inputStream2);
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                str2 = null;
            }
            if (property == null) {
                zza.d("LibraryVersion", ".properties file is dropped during release process. Failure to read app version is expected during Google internal testing where locally-built libraries are used");
                property = "UNKNOWN";
            }
            this.zzc.put(str, property);
            return property;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
