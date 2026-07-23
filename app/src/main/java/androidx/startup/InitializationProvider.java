package androidx.startup;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class InitializationProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public final int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        throw new IllegalStateException("Not allowed.");
    }

    @Override // android.content.ContentProvider
    @Nullable
    public final String getType(@NonNull Uri uri) {
        throw new IllegalStateException("Not allowed.");
    }

    @Override // android.content.ContentProvider
    @Nullable
    public final Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        throw new IllegalStateException("Not allowed.");
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x003e */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onCreate() {
        /*
            r4 = this;
            android.content.Context r0 = r4.getContext()
            if (r0 == 0) goto L4d
            android.content.Context r1 = r0.getApplicationContext()
            if (r1 == 0) goto L4b
            androidx.startup.AppInitializer r0 = androidx.startup.AppInitializer.getInstance(r0)
            java.util.Objects.requireNonNull(r0)
            java.lang.String r1 = "Startup"
            androidx.tracing.Trace.beginSection(r1)     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            android.content.ComponentName r1 = new android.content.ComponentName     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            android.content.Context r2 = r0.mContext     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            java.lang.String r2 = r2.getPackageName()     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            java.lang.Class<androidx.startup.InitializationProvider> r3 = androidx.startup.InitializationProvider.class
            java.lang.String r3 = r3.getName()     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            r1.<init>(r2, r3)     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            android.content.Context r2 = r0.mContext     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            r3 = 128(0x80, float:1.8E-43)
            android.content.pm.ProviderInfo r1 = r2.getProviderInfo(r1, r3)     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            android.os.Bundle r1 = r1.metaData     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            r0.discoverAndInitialize(r1)     // Catch: java.lang.Throwable -> L3e android.content.pm.PackageManager.NameNotFoundException -> L40
            androidx.tracing.Trace.endSection()
            goto L4b
        L3e:
            r0 = move-exception
            goto L47
        L40:
            r0 = move-exception
            androidx.startup.StartupException r1 = new androidx.startup.StartupException     // Catch: java.lang.Throwable -> L3e
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L3e
            throw r1     // Catch: java.lang.Throwable -> L3e
        L47:
            androidx.tracing.Trace.endSection()
            throw r0
        L4b:
            r0 = 1
            return r0
        L4d:
            androidx.startup.StartupException r0 = new androidx.startup.StartupException
            java.lang.String r1 = "Context cannot be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.startup.InitializationProvider.onCreate():boolean");
    }

    @Override // android.content.ContentProvider
    @Nullable
    public final Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        throw new IllegalStateException("Not allowed.");
    }

    @Override // android.content.ContentProvider
    public final int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        throw new IllegalStateException("Not allowed.");
    }
}
