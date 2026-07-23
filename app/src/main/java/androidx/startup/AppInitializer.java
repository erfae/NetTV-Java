package androidx.startup;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.tracing.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class AppInitializer {
    private static final String SECTION_NAME = "Startup";
    private static volatile AppInitializer sInstance;
    private static final Object sLock = new Object();

    @NonNull
    public final Context mContext;

    @NonNull
    public final Set<Class<? extends Initializer<?>>> mDiscovered = new HashSet();

    @NonNull
    public final Map<Class<?>, Object> mInitialized = new HashMap();

    public AppInitializer(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.HashMap, java.util.Map<java.lang.Class<?>, java.lang.Object>] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.HashMap, java.util.Map<java.lang.Class<?>, java.lang.Object>] */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.util.HashMap, java.util.Map<java.lang.Class<?>, java.lang.Object>] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.util.HashMap, java.util.Map<java.lang.Class<?>, java.lang.Object>] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @NonNull
    private <T> T doInitialize(@NonNull Class<? extends Initializer<?>> cls, @NonNull Set<Class<?>> set) {
        T t;
        if (Trace.isEnabled()) {
            try {
                Trace.beginSection(cls.getSimpleName());
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
        if (set.contains(cls)) {
            throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", cls.getName()));
        }
        if (this.mInitialized.containsKey(cls)) {
            t = (T) this.mInitialized.get(cls);
        } else {
            set.add(cls);
            try {
                Initializer<?> initializerNewInstance = cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                List<Class<? extends Initializer<?>>> listDependencies = initializerNewInstance.dependencies();
                if (!listDependencies.isEmpty()) {
                    for (Class<? extends Initializer<?>> cls2 : listDependencies) {
                        if (!this.mInitialized.containsKey(cls2)) {
                            doInitialize(cls2, set);
                        }
                    }
                }
                t = (T) initializerNewInstance.create(this.mContext);
                set.remove(cls);
                this.mInitialized.put(cls, t);
            } catch (Throwable th2) {
                throw new StartupException(th2);
            }
        }
        Trace.endSection();
        return t;
    }

    @NonNull
    public static AppInitializer getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new AppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public final void discoverAndInitialize(@Nullable Bundle bundle) {
        String string = this.mContext.getString(R.string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet = new HashSet();
                for (String str : bundle.keySet()) {
                    if (string.equals(bundle.getString(str, null))) {
                        Class<?> cls = Class.forName(str);
                        if (Initializer.class.isAssignableFrom(cls)) {
                            this.mDiscovered.add((Class<? extends Initializer<?>>) cls);
                        }
                    }
                }
                Iterator<Class<? extends Initializer<?>>> it = this.mDiscovered.iterator();
                while (it.hasNext()) {
                    doInitialize(it.next(), hashSet);
                }
            } catch (ClassNotFoundException e) {
                throw new StartupException(e);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.HashMap, java.util.Map<java.lang.Class<?>, java.lang.Object>] */
    @NonNull
    public <T> T initializeComponent(@NonNull Class<? extends Initializer<T>> cls) {
        T t;
        synchronized (sLock) {
            t = (T) this.mInitialized.get(cls);
            if (t == null) {
                t = (T) doInitialize(cls, new HashSet());
            }
        }
        return t;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.HashSet, java.util.Set<java.lang.Class<? extends androidx.startup.Initializer<?>>>] */
    public boolean isEagerlyInitialized(@NonNull Class<? extends Initializer<?>> cls) {
        return this.mDiscovered.contains(cls);
    }
}
