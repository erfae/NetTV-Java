package pl.droidsonroids.gif;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import pl.droidsonroids.relinker.ReLinker;

/* JADX INFO: loaded from: classes2.dex */
public class LibraryLoader {
    private static final String BASE_LIBRARY_NAME = "pl_droidsonroids_gif";

    @SuppressLint({"StaticFieldLeak"})
    private static Context sAppContext;

    private LibraryLoader() {
    }

    private static Context getContext() {
        if (sAppContext == null) {
            try {
                sAppContext = (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("LibraryLoader not initialized. Call LibraryLoader.initialize() before using library classes.", e);
            }
        }
        return sAppContext;
    }

    public static void initialize(@NonNull Context context) {
        sAppContext = context.getApplicationContext();
    }

    public static void loadLibrary() throws Throwable {
        try {
            System.loadLibrary(BASE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError unused) {
            ReLinker.loadLibrary(getContext(), BASE_LIBRARY_NAME);
        }
    }
}
