package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import dalvik.system.DelegateLastClassLoader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public final class DynamiteModule {

    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static Boolean zzb = null;

    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static String zzc = null;

    @GuardedBy("DynamiteModule.class")
    private static boolean zzd = false;

    @GuardedBy("DynamiteModule.class")
    private static int zze = -1;

    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static zzq zzj;

    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static zzr zzk;
    private final Context zzi;
    private static final ThreadLocal<zzn> zzf = new ThreadLocal<>();
    private static final ThreadLocal<Long> zzg = new zzd();
    private static final VersionPolicy.IVersions zzh = new zze();

    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE = new zzf();

    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_LOCAL = new zzg();

    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzh();

    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzi();

    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zzj();

    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzk();

    @NonNull
    public static final VersionPolicy zza = new zzl();

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @DynamiteApi
    public static class DynamiteLoaderClassLoader {

        @Nullable
        @GuardedBy("DynamiteLoaderClassLoader.class")
        public static ClassLoader sClassLoader;
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public static class LoadingException extends Exception {
        public /* synthetic */ LoadingException(String str) {
            super(str);
        }

        public /* synthetic */ LoadingException(String str, Throwable th) {
            super(str, th);
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public interface VersionPolicy {

        /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
        public interface IVersions {
            int zza(@NonNull Context context, @NonNull String str);

            int zzb(@NonNull Context context, @NonNull String str, boolean z) throws LoadingException;
        }

        /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
        public static class SelectionResult {

            @KeepForSdk
            public int localVersion = 0;

            @KeepForSdk
            public int remoteVersion = 0;

            @KeepForSdk
            public int selection = 0;
        }

        @NonNull
        @KeepForSdk
        SelectionResult selectModule(@NonNull Context context, @NonNull String str, @NonNull IVersions iVersions) throws LoadingException;
    }

    private DynamiteModule(Context context) {
        Preconditions.checkNotNull(context);
        this.zzi = context;
    }

    @KeepForSdk
    public static int getLocalVersion(@NonNull Context context, @NonNull String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 61);
            sb.append("com.google.android.gms.dynamite.descriptors.");
            sb.append(str);
            sb.append(".");
            sb.append("ModuleDescriptor");
            Class<?> clsLoadClass = classLoader.loadClass(sb.toString());
            Field declaredField = clsLoadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = clsLoadClass.getDeclaredField("MODULE_VERSION");
            if (Objects.equal(declaredField.get(null), str)) {
                return declaredField2.getInt(null);
            }
            String strValueOf = String.valueOf(declaredField.get(null));
            StringBuilder sb2 = new StringBuilder(strValueOf.length() + 51 + String.valueOf(str).length());
            sb2.append("Module descriptor id '");
            sb2.append(strValueOf);
            sb2.append("' didn't match expected id '");
            sb2.append(str);
            sb2.append("'");
            Log.e("DynamiteModule", sb2.toString());
            return 0;
        } catch (ClassNotFoundException unused) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(e.getMessage());
            Log.e("DynamiteModule", strValueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(strValueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    @KeepForSdk
    public static int getRemoteVersion(@NonNull Context context, @NonNull String str) {
        return zza(context, str, false);
    }

    /* JADX WARN: Code duplicated, block: B:131:0x02a6 A[Catch: all -> 0x02f9, TryCatch #4 {all -> 0x02f9, blocks: (B:3:0x0025, B:7:0x0083, B:12:0x008b, B:15:0x0091, B:26:0x00b3, B:103:0x022c, B:104:0x0236, B:106:0x0238, B:108:0x023a, B:109:0x0241, B:131:0x02a6, B:132:0x02be, B:111:0x0243, B:113:0x0255, B:115:0x0260, B:117:0x0267, B:119:0x0278, B:129:0x029e, B:130:0x02a5, B:114:0x025a, B:133:0x02bf, B:134:0x02f8), top: B:150:0x0025, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:145:0x00b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:148:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:151:0x00eb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:15:0x0091 A[Catch: all -> 0x02f9, TRY_LEAVE, TryCatch #4 {all -> 0x02f9, blocks: (B:3:0x0025, B:7:0x0083, B:12:0x008b, B:15:0x0091, B:26:0x00b3, B:103:0x022c, B:104:0x0236, B:106:0x0238, B:108:0x023a, B:109:0x0241, B:131:0x02a6, B:132:0x02be, B:111:0x0243, B:113:0x0255, B:115:0x0260, B:117:0x0267, B:119:0x0278, B:129:0x029e, B:130:0x02a5, B:114:0x025a, B:133:0x02bf, B:134:0x02f8), top: B:150:0x0025, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x009b  */
    /* JADX WARN: Code duplicated, block: B:19:0x009f  */
    /* JADX WARN: Code duplicated, block: B:22:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:25:0x00b1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:31:0x00bb A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TRY_ENTER, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x00c2 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00f0 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TRY_ENTER, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:64:0x0163 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:69:0x016e A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:71:0x019c A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x01a3 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:75:0x01ab A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:76:0x01ba A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x01c2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:79:0x01c4 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x01d4 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:83:0x01e9 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TRY_LEAVE, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:86:0x01fa  */
    /* JADX WARN: Code duplicated, block: B:87:0x01fe  */
    /* JADX WARN: Code duplicated, block: B:90:0x0209  */
    /* JADX WARN: Code duplicated, block: B:93:0x0210 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TRY_ENTER, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:95:0x0218 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x0220 A[Catch: all -> 0x022b, LoadingException -> 0x0237, RemoteException -> 0x0239, TryCatch #7 {RemoteException -> 0x0239, LoadingException -> 0x0237, all -> 0x022b, blocks: (B:27:0x00b5, B:31:0x00bb, B:33:0x00c2, B:34:0x00ea, B:38:0x00f0, B:40:0x00f8, B:42:0x00fc, B:43:0x0107, B:50:0x0114, B:52:0x011a, B:54:0x0141, B:56:0x0149, B:57:0x0150, B:58:0x0157, B:53:0x012e, B:61:0x015a, B:62:0x015b, B:63:0x0162, B:64:0x0163, B:65:0x016a, B:68:0x016d, B:69:0x016e, B:71:0x019c, B:73:0x01a3, B:75:0x01ab, B:81:0x01e3, B:83:0x01e9, B:93:0x0210, B:94:0x0217, B:76:0x01ba, B:77:0x01c1, B:79:0x01c4, B:80:0x01d4, B:95:0x0218, B:96:0x021f, B:97:0x0220, B:98:0x0227, B:101:0x022a), top: B:153:0x00b5 }] */
    @NonNull
    @KeepForSdk
    public static DynamiteModule load(@NonNull Context context, @NonNull VersionPolicy versionPolicy, @NonNull String str) throws LoadingException {
        int i;
        Boolean bool;
        zzq zzqVarZzf;
        int iZze;
        IObjectWrapper iObjectWrapperZzh;
        DynamiteModule dynamiteModule;
        zzn zznVar;
        Cursor cursor;
        zzr zzrVar;
        zzn zznVar2;
        Boolean boolValueOf;
        IObjectWrapper iObjectWrapperZze;
        Cursor cursor2;
        ThreadLocal<zzn> threadLocal = zzf;
        zzn zznVar3 = threadLocal.get();
        zzn zznVar4 = new zzn(null);
        threadLocal.set(zznVar4);
        ThreadLocal<Long> threadLocal2 = zzg;
        long jLongValue = threadLocal2.get().longValue();
        try {
            threadLocal2.set(Long.valueOf(SystemClock.elapsedRealtime()));
            VersionPolicy.SelectionResult selectionResultSelectModule = versionPolicy.selectModule(context, str, zzh);
            int i2 = selectionResultSelectModule.localVersion;
            int i3 = selectionResultSelectModule.remoteVersion;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length());
            sb.append("Considering local module ");
            sb.append(str);
            sb.append(":");
            sb.append(i2);
            sb.append(" and remote module ");
            sb.append(str);
            sb.append(":");
            sb.append(i3);
            Log.i("DynamiteModule", sb.toString());
            int i4 = selectionResultSelectModule.selection;
            if (i4 != 0) {
                if (i4 != -1) {
                    if (i4 == 1 || selectionResultSelectModule.remoteVersion != 0) {
                        if (i4 == -1) {
                            DynamiteModule dynamiteModuleZzc = zzc(context, str);
                            if (jLongValue == 0) {
                                threadLocal2.remove();
                            } else {
                                threadLocal2.set(Long.valueOf(jLongValue));
                            }
                            cursor2 = zznVar4.zza;
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            threadLocal.set(zznVar3);
                            return dynamiteModuleZzc;
                        }
                        if (i4 == 1) {
                            StringBuilder sb2 = new StringBuilder(47);
                            sb2.append("VersionPolicy returned invalid code:");
                            sb2.append(i4);
                            throw new LoadingException(sb2.toString());
                        }
                        try {
                            i = selectionResultSelectModule.remoteVersion;
                            try {
                                synchronized (DynamiteModule.class) {
                                    bool = zzb;
                                }
                                if (bool != null) {
                                    throw new LoadingException("Failed to determine which loading route to use.");
                                }
                                if (bool.booleanValue()) {
                                    StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 51);
                                    sb3.append("Selected remote version of ");
                                    sb3.append(str);
                                    sb3.append(", version >= ");
                                    sb3.append(i);
                                    Log.i("DynamiteModule", sb3.toString());
                                    synchronized (DynamiteModule.class) {
                                        zzrVar = zzk;
                                    }
                                    if (zzrVar != null) {
                                        throw new LoadingException("DynamiteLoaderV2 was not cached.");
                                    }
                                    zznVar2 = threadLocal.get();
                                    if (zznVar2 != null || zznVar2.zza == null) {
                                        throw new LoadingException("No result cursor");
                                    }
                                    Context applicationContext = context.getApplicationContext();
                                    Cursor cursor3 = zznVar2.zza;
                                    ObjectWrapper.wrap(null);
                                    synchronized (DynamiteModule.class) {
                                        boolValueOf = Boolean.valueOf(zze >= 2);
                                    }
                                    if (boolValueOf.booleanValue()) {
                                        Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
                                        iObjectWrapperZze = zzrVar.zzf(ObjectWrapper.wrap(applicationContext), str, i, ObjectWrapper.wrap(cursor3));
                                    } else {
                                        Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
                                        iObjectWrapperZze = zzrVar.zze(ObjectWrapper.wrap(applicationContext), str, i, ObjectWrapper.wrap(cursor3));
                                    }
                                    Context context2 = (Context) ObjectWrapper.unwrap(iObjectWrapperZze);
                                    if (context2 == null) {
                                        throw new LoadingException("Failed to get module context");
                                    }
                                    dynamiteModule = new DynamiteModule(context2);
                                } else {
                                    StringBuilder sb4 = new StringBuilder(String.valueOf(str).length() + 51);
                                    sb4.append("Selected remote version of ");
                                    sb4.append(str);
                                    sb4.append(", version >= ");
                                    sb4.append(i);
                                    Log.i("DynamiteModule", sb4.toString());
                                    zzqVarZzf = zzf(context);
                                    if (zzqVarZzf != null) {
                                        throw new LoadingException("Failed to create IDynamiteLoader.");
                                    }
                                    iZze = zzqVarZzf.zze();
                                    if (iZze >= 3) {
                                        zznVar = threadLocal.get();
                                        if (zznVar != null) {
                                            throw new LoadingException("No cached result cursor holder");
                                        }
                                        iObjectWrapperZzh = zzqVarZzf.zzi(ObjectWrapper.wrap(context), str, i, ObjectWrapper.wrap(zznVar.zza));
                                    } else if (iZze == 2) {
                                        Log.w("DynamiteModule", "IDynamite loader version = 2");
                                        iObjectWrapperZzh = zzqVarZzf.zzj(ObjectWrapper.wrap(context), str, i);
                                    } else {
                                        Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
                                        iObjectWrapperZzh = zzqVarZzf.zzh(ObjectWrapper.wrap(context), str, i);
                                    }
                                    if (ObjectWrapper.unwrap(iObjectWrapperZzh) != null) {
                                        throw new LoadingException("Failed to load remote module.");
                                    }
                                    dynamiteModule = new DynamiteModule((Context) ObjectWrapper.unwrap(iObjectWrapperZzh));
                                }
                                if (jLongValue == 0) {
                                    threadLocal2.remove();
                                } else {
                                    threadLocal2.set(Long.valueOf(jLongValue));
                                }
                                cursor = zznVar4.zza;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                threadLocal.set(zznVar3);
                                return dynamiteModule;
                            } catch (RemoteException e) {
                                throw new LoadingException("Failed to load remote module.", e);
                            } catch (LoadingException e2) {
                                throw e2;
                            } catch (Throwable th) {
                                CrashUtils.addDynamiteErrorToDropBox(context, th);
                                throw new LoadingException("Failed to load remote module.", th);
                            }
                        } catch (LoadingException e3) {
                            String strValueOf = String.valueOf(e3.getMessage());
                            Log.w("DynamiteModule", strValueOf.length() != 0 ? "Failed to load remote module: ".concat(strValueOf) : new String("Failed to load remote module: "));
                            int i5 = selectionResultSelectModule.localVersion;
                            if (i5 == 0 || versionPolicy.selectModule(context, str, new zzo(i5, 0)).selection != -1) {
                                throw new LoadingException("Remote load failed. No local fallback found.", e3);
                            }
                            DynamiteModule dynamiteModuleZzc2 = zzc(context, str);
                            if (jLongValue == 0) {
                                zzg.remove();
                            } else {
                                zzg.set(Long.valueOf(jLongValue));
                            }
                            Cursor cursor4 = zznVar4.zza;
                            if (cursor4 != null) {
                                cursor4.close();
                            }
                            zzf.set(zznVar3);
                            return dynamiteModuleZzc2;
                        }
                    }
                } else if (selectionResultSelectModule.localVersion != 0) {
                    i4 = -1;
                    if (i4 == 1) {
                    }
                    if (i4 == -1) {
                        DynamiteModule dynamiteModuleZzc3 = zzc(context, str);
                        if (jLongValue == 0) {
                            threadLocal2.remove();
                        } else {
                            threadLocal2.set(Long.valueOf(jLongValue));
                        }
                        cursor2 = zznVar4.zza;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        threadLocal.set(zznVar3);
                        return dynamiteModuleZzc3;
                    }
                    if (i4 == 1) {
                        StringBuilder sb5 = new StringBuilder(47);
                        sb5.append("VersionPolicy returned invalid code:");
                        sb5.append(i4);
                        throw new LoadingException(sb5.toString());
                    }
                    i = selectionResultSelectModule.remoteVersion;
                    synchronized (DynamiteModule.class) {
                        bool = zzb;
                        if (bool != null) {
                            throw new LoadingException("Failed to determine which loading route to use.");
                        }
                        if (bool.booleanValue()) {
                            StringBuilder sb6 = new StringBuilder(String.valueOf(str).length() + 51);
                            sb6.append("Selected remote version of ");
                            sb6.append(str);
                            sb6.append(", version >= ");
                            sb6.append(i);
                            Log.i("DynamiteModule", sb6.toString());
                            synchronized (DynamiteModule.class) {
                                zzrVar = zzk;
                                if (zzrVar != null) {
                                    throw new LoadingException("DynamiteLoaderV2 was not cached.");
                                }
                                zznVar2 = threadLocal.get();
                                if (zznVar2 != null) {
                                }
                                throw new LoadingException("No result cursor");
                            }
                        }
                        StringBuilder sb7 = new StringBuilder(String.valueOf(str).length() + 51);
                        sb7.append("Selected remote version of ");
                        sb7.append(str);
                        sb7.append(", version >= ");
                        sb7.append(i);
                        Log.i("DynamiteModule", sb7.toString());
                        zzqVarZzf = zzf(context);
                        if (zzqVarZzf != null) {
                            throw new LoadingException("Failed to create IDynamiteLoader.");
                        }
                        iZze = zzqVarZzf.zze();
                        if (iZze >= 3) {
                            zznVar = threadLocal.get();
                            if (zznVar != null) {
                                throw new LoadingException("No cached result cursor holder");
                            }
                            iObjectWrapperZzh = zzqVarZzf.zzi(ObjectWrapper.wrap(context), str, i, ObjectWrapper.wrap(zznVar.zza));
                        } else if (iZze == 2) {
                            Log.w("DynamiteModule", "IDynamite loader version = 2");
                            iObjectWrapperZzh = zzqVarZzf.zzj(ObjectWrapper.wrap(context), str, i);
                        } else {
                            Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
                            iObjectWrapperZzh = zzqVarZzf.zzh(ObjectWrapper.wrap(context), str, i);
                        }
                        if (ObjectWrapper.unwrap(iObjectWrapperZzh) != null) {
                            throw new LoadingException("Failed to load remote module.");
                        }
                        dynamiteModule = new DynamiteModule((Context) ObjectWrapper.unwrap(iObjectWrapperZzh));
                        if (jLongValue == 0) {
                            threadLocal2.remove();
                        } else {
                            threadLocal2.set(Long.valueOf(jLongValue));
                        }
                        cursor = zznVar4.zza;
                        if (cursor != null) {
                            cursor.close();
                        }
                        threadLocal.set(zznVar3);
                        return dynamiteModule;
                    }
                }
            }
            int i6 = selectionResultSelectModule.localVersion;
            int i7 = selectionResultSelectModule.remoteVersion;
            StringBuilder sb8 = new StringBuilder(String.valueOf(str).length() + 92);
            sb8.append("No acceptable module ");
            sb8.append(str);
            sb8.append(" found. Local version is ");
            sb8.append(i6);
            sb8.append(" and remote version is ");
            sb8.append(i7);
            sb8.append(".");
            throw new LoadingException(sb8.toString());
        } catch (Throwable th2) {
            if (jLongValue == 0) {
                zzg.remove();
            } else {
                zzg.set(Long.valueOf(jLongValue));
            }
            Cursor cursor5 = zznVar4.zza;
            if (cursor5 != null) {
                cursor5.close();
            }
            zzf.set(zznVar3);
            throw th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:42:0x009c A[Catch: all -> 0x00a7, TryCatch #4 {, blocks: (B:9:0x0026, B:11:0x002e, B:13:0x0034, B:43:0x00a5, B:15:0x0038, B:16:0x003b, B:17:0x003e, B:19:0x0042, B:22:0x004b, B:24:0x0053, B:27:0x005a, B:34:0x0084, B:35:0x008c, B:30:0x0061, B:32:0x0067, B:33:0x0076, B:38:0x008f, B:41:0x0092, B:42:0x009c), top: B:133:0x0026, inners: #7 }] */
    /* JADX WARN: Code duplicated, block: B:94:0x0167 A[Catch: all -> 0x01c8, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x01c8, blocks: (B:3:0x0002, B:56:0x00d4, B:59:0x00db, B:68:0x0101, B:90:0x0159, B:94:0x0167, B:119:0x01c1, B:120:0x01c4, B:114:0x01b9, B:62:0x00e1, B:64:0x00f3, B:66:0x00fd, B:65:0x00f8, B:123:0x01c7, B:4:0x0003, B:7:0x0008, B:8:0x0025, B:54:0x00d1, B:36:0x008d, B:39:0x0090, B:47:0x00a9, B:55:0x00d3, B:53:0x00af), top: B:134:0x0002, inners: #2, #3 }] */
    public static int zza(@NonNull Context context, @NonNull String str, boolean z) {
        Throwable th;
        RemoteException e;
        Cursor cursor;
        try {
            synchronized (DynamiteModule.class) {
                Boolean bool = zzb;
                Cursor cursor2 = null;
                if (bool == null) {
                    try {
                        Field declaredField = context.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName()).getDeclaredField("sClassLoader");
                        synchronized (declaredField.getDeclaringClass()) {
                            ClassLoader classLoader = (ClassLoader) declaredField.get(null);
                            if (classLoader != null) {
                                if (classLoader == ClassLoader.getSystemClassLoader()) {
                                    bool = Boolean.FALSE;
                                } else {
                                    try {
                                        zzd(classLoader);
                                    } catch (LoadingException unused) {
                                    }
                                    bool = Boolean.TRUE;
                                }
                            } else if (zzd) {
                                declaredField.set(null, ClassLoader.getSystemClassLoader());
                                bool = Boolean.FALSE;
                            } else {
                                Boolean bool2 = Boolean.TRUE;
                                if (bool2.equals(null)) {
                                    declaredField.set(null, ClassLoader.getSystemClassLoader());
                                    bool = Boolean.FALSE;
                                } else {
                                    try {
                                        int iZzb = zzb(context, str, z);
                                        String str2 = zzc;
                                        if (str2 != null && !str2.isEmpty()) {
                                            ClassLoader classLoaderZza = zzb.zza();
                                            if (classLoaderZza == null) {
                                                if (Build.VERSION.SDK_INT >= 29) {
                                                    String str3 = zzc;
                                                    Preconditions.checkNotNull(str3);
                                                    classLoaderZza = new DelegateLastClassLoader(str3, ClassLoader.getSystemClassLoader());
                                                } else {
                                                    String str4 = zzc;
                                                    Preconditions.checkNotNull(str4);
                                                    classLoaderZza = new zzc(str4, ClassLoader.getSystemClassLoader());
                                                }
                                            }
                                            zzd(classLoaderZza);
                                            declaredField.set(null, classLoaderZza);
                                            zzb = bool2;
                                            return iZzb;
                                        }
                                        return iZzb;
                                    } catch (LoadingException unused2) {
                                        declaredField.set(null, ClassLoader.getSystemClassLoader());
                                        bool = Boolean.FALSE;
                                    }
                                }
                            }
                            zzb = bool;
                        }
                    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e2) {
                        String string = e2.toString();
                        StringBuilder sb = new StringBuilder(string.length() + 30);
                        sb.append("Failed to load module via V2: ");
                        sb.append(string);
                        Log.w("DynamiteModule", sb.toString());
                        bool = Boolean.FALSE;
                    }
                }
                boolean zBooleanValue = bool.booleanValue();
                int iZzf = 0;
                if (zBooleanValue) {
                    try {
                        return zzb(context, str, z);
                    } catch (LoadingException e3) {
                        String strValueOf = String.valueOf(e3.getMessage());
                        Log.w("DynamiteModule", strValueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(strValueOf) : new String("Failed to retrieve remote module version: "));
                        return 0;
                    }
                }
                zzq zzqVarZzf = zzf(context);
                try {
                    if (zzqVarZzf != null) {
                        try {
                            int iZze = zzqVarZzf.zze();
                            if (iZze >= 3) {
                                zzn zznVar = zzf.get();
                                if (zznVar == null || (cursor = zznVar.zza) == null) {
                                    Cursor cursor3 = (Cursor) ObjectWrapper.unwrap(zzqVarZzf.zzk(ObjectWrapper.wrap(context), str, z, zzg.get().longValue()));
                                    if (cursor3 != null) {
                                        try {
                                            if (cursor3.moveToFirst()) {
                                                int i = cursor3.getInt(0);
                                                cursor2 = (i <= 0 || !zze(cursor3)) ? cursor3 : null;
                                                if (cursor2 != null) {
                                                    cursor2.close();
                                                }
                                                iZzf = i;
                                            } else {
                                                Log.w("DynamiteModule", "Failed to retrieve remote module version.");
                                                if (cursor3 != null) {
                                                    cursor3.close();
                                                }
                                            }
                                        } catch (RemoteException e4) {
                                            e = e4;
                                            cursor2 = cursor3;
                                            String strValueOf2 = String.valueOf(e.getMessage());
                                            Log.w("DynamiteModule", strValueOf2.length() != 0 ? "Failed to retrieve remote module version: ".concat(strValueOf2) : new String("Failed to retrieve remote module version: "));
                                            if (cursor2 != null) {
                                                cursor2.close();
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            cursor2 = cursor3;
                                            if (cursor2 != null) {
                                                cursor2.close();
                                            }
                                            throw th;
                                        }
                                    } else {
                                        Log.w("DynamiteModule", "Failed to retrieve remote module version.");
                                        if (cursor3 != null) {
                                            cursor3.close();
                                        }
                                    }
                                } else {
                                    iZzf = cursor.getInt(0);
                                }
                            } else if (iZze == 2) {
                                Log.w("DynamiteModule", "IDynamite loader version = 2, no high precision latency measurement.");
                                iZzf = zzqVarZzf.zzg(ObjectWrapper.wrap(context), str, z);
                            } else {
                                Log.w("DynamiteModule", "IDynamite loader version < 2, falling back to getModuleVersion2");
                                iZzf = zzqVarZzf.zzf(ObjectWrapper.wrap(context), str, z);
                            }
                        } catch (RemoteException e5) {
                            e = e5;
                        }
                    }
                    return iZzf;
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            CrashUtils.addDynamiteErrorToDropBox(context, th4);
            throw th4;
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x008e A[PHI: r11
  0x008e: PHI (r11v9 boolean) = (r11v8 boolean), (r11v15 boolean) binds: [B:11:0x0056, B:27:0x0088] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:53:0x00bf A[Catch: all -> 0x00c8, TryCatch #3 {all -> 0x00c8, blocks: (B:37:0x0097, B:38:0x009e, B:51:0x00ba, B:53:0x00bf, B:54:0x00c0, B:55:0x00c7), top: B:61:0x0097 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x00c0 A[Catch: all -> 0x00c8, TryCatch #3 {all -> 0x00c8, blocks: (B:37:0x0097, B:38:0x009e, B:51:0x00ba, B:53:0x00bf, B:54:0x00c0, B:55:0x00c7), top: B:61:0x0097 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00cc  */
    private static int zzb(Context context, String str, boolean z) throws Throwable {
        Exception e;
        Cursor cursor = null;
        try {
            boolean z2 = true;
            Cursor cursorQuery = context.getContentResolver().query(new Uri.Builder().scheme("content").authority("com.google.android.gms.chimera").path(true != z ? "api" : "api_force_staging").appendPath(str).appendQueryParameter("requestStartTime", String.valueOf(zzg.get().longValue())).build(), null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        boolean z3 = false;
                        int i = cursorQuery.getInt(0);
                        if (i > 0) {
                            synchronized (DynamiteModule.class) {
                                zzc = cursorQuery.getString(2);
                                int columnIndex = cursorQuery.getColumnIndex("loaderVersion");
                                if (columnIndex >= 0) {
                                    zze = cursorQuery.getInt(columnIndex);
                                }
                                int columnIndex2 = cursorQuery.getColumnIndex("disableStandaloneDynamiteLoader");
                                if (columnIndex2 >= 0) {
                                    if (cursorQuery.getInt(columnIndex2) == 0) {
                                        z2 = false;
                                    }
                                    zzd = z2;
                                    z3 = z2;
                                }
                            }
                            cursor = zze(cursorQuery) ? null : cursorQuery;
                        }
                        if (!z3) {
                            if (cursor != null) {
                                cursor.close();
                            }
                            return i;
                        }
                        try {
                            try {
                                throw new LoadingException("forcing fallback to container DynamiteLoader impl");
                            } catch (Throwable th) {
                                th = th;
                                th = th;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            if (e instanceof LoadingException) {
                                throw e;
                            }
                            throw new LoadingException("V2 version check failed", e);
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    if (e instanceof LoadingException) {
                        throw e;
                    }
                    throw new LoadingException("V2 version check failed", e);
                } catch (Throwable th2) {
                    th = th2;
                    cursor = cursorQuery;
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            Log.w("DynamiteModule", "Failed to retrieve remote module version.");
            throw new LoadingException("Failed to connect to dynamite module ContentResolver.");
        } catch (Exception e4) {
            e = e4;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static DynamiteModule zzc(Context context, String str) {
        String strValueOf = String.valueOf(str);
        Log.i("DynamiteModule", strValueOf.length() != 0 ? "Selected local version of ".concat(strValueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    @GuardedBy("DynamiteModule.class")
    private static void zzd(ClassLoader classLoader) throws LoadingException {
        zzr zzrVar;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzrVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                zzrVar = iInterfaceQueryLocalInterface instanceof zzr ? (zzr) iInterfaceQueryLocalInterface : new zzr(iBinder);
            }
            zzk = zzrVar;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new LoadingException("Failed to instantiate dynamite loader", e);
        }
    }

    private static boolean zze(Cursor cursor) {
        zzn zznVar = zzf.get();
        if (zznVar == null || zznVar.zza != null) {
            return false;
        }
        zznVar.zza = cursor;
        return true;
    }

    @Nullable
    private static zzq zzf(Context context) {
        zzq zzqVar;
        synchronized (DynamiteModule.class) {
            zzq zzqVar2 = zzj;
            if (zzqVar2 != null) {
                return zzqVar2;
            }
            try {
                IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                if (iBinder == null) {
                    zzqVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzqVar = iInterfaceQueryLocalInterface instanceof zzq ? (zzq) iInterfaceQueryLocalInterface : new zzq(iBinder);
                }
                if (zzqVar != null) {
                    zzj = zzqVar;
                    return zzqVar;
                }
            } catch (Exception e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.e("DynamiteModule", strValueOf.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(strValueOf) : new String("Failed to load IDynamiteLoader from GmsCore: "));
            }
            return null;
        }
    }

    @NonNull
    @KeepForSdk
    public Context getModuleContext() {
        return this.zzi;
    }

    @NonNull
    @KeepForSdk
    public IBinder instantiate(@NonNull String str) throws LoadingException {
        try {
            return (IBinder) this.zzi.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String strValueOf = String.valueOf(str);
            throw new LoadingException(strValueOf.length() != 0 ? "Failed to instantiate module class: ".concat(strValueOf) : new String("Failed to instantiate module class: "), e);
        }
    }
}
