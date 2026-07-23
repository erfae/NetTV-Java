package androidx.core.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
class FontRequestWorker {
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE;
    public static final Object LOCK;

    @GuardedBy("LOCK")
    public static final SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> PENDING_REPLIES;
    public static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new RequestExecutor.DefaultThreadFactory());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        DEFAULT_EXECUTOR_SERVICE = threadPoolExecutor;
        LOCK = new Object();
        PENDING_REPLIES = new SimpleArrayMap<>();
    }

    private FontRequestWorker() {
    }

    private static String createCacheId(@NonNull FontRequest fontRequest, int i) {
        return fontRequest.getId() + "-" + i;
    }

    @SuppressLint({"WrongConstant"})
    private static int getFontFamilyResultStatus(@NonNull FontsContractCompat.FontFamilyResult fontFamilyResult) {
        int i = 1;
        if (fontFamilyResult.getStatusCode() != 0) {
            return fontFamilyResult.getStatusCode() != 1 ? -3 : -2;
        }
        FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
        if (fonts != null && fonts.length != 0) {
            i = 0;
            for (FontsContractCompat.FontInfo fontInfo : fonts) {
                int resultCode = fontInfo.getResultCode();
                if (resultCode != 0) {
                    if (resultCode < 0) {
                        return -3;
                    }
                    return resultCode;
                }
            }
        }
        return i;
    }

    @NonNull
    public static TypefaceResult getFontSync(@NonNull String str, @NonNull Context context, @NonNull FontRequest fontRequest, int i) {
        LruCache<String, Typeface> lruCache = sTypefaceCache;
        Typeface typeface = lruCache.get(str);
        if (typeface != null) {
            return new TypefaceResult(typeface);
        }
        try {
            FontsContractCompat.FontFamilyResult fontFamilyResult = FontProvider.getFontFamilyResult(context, fontRequest, null);
            int fontFamilyResultStatus = getFontFamilyResultStatus(fontFamilyResult);
            if (fontFamilyResultStatus != 0) {
                return new TypefaceResult(fontFamilyResultStatus);
            }
            Typeface typefaceCreateFromFontInfo = TypefaceCompat.createFromFontInfo(context, null, fontFamilyResult.getFonts(), i);
            if (typefaceCreateFromFontInfo == null) {
                return new TypefaceResult(-3);
            }
            lruCache.put(str, typefaceCreateFromFontInfo);
            return new TypefaceResult(typefaceCreateFromFontInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            return new TypefaceResult(-1);
        }
    }

    public static Typeface requestFontAsync(@NonNull final Context context, @NonNull final FontRequest fontRequest, final int i, @Nullable Executor executor, @NonNull final CallbackWithHandler callbackWithHandler) {
        final String strCreateCacheId = createCacheId(fontRequest, i);
        Typeface typeface = sTypefaceCache.get(strCreateCacheId);
        if (typeface != null) {
            callbackWithHandler.onTypefaceResult(new TypefaceResult(typeface));
            return typeface;
        }
        Consumer<TypefaceResult> consumer = new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.2
            @Override // androidx.core.util.Consumer
            public void accept(TypefaceResult typefaceResult) {
                if (typefaceResult == null) {
                    typefaceResult = new TypefaceResult(-3);
                }
                callbackWithHandler.onTypefaceResult(typefaceResult);
            }
        };
        synchronized (LOCK) {
            SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> simpleArrayMap = PENDING_REPLIES;
            ArrayList<Consumer<TypefaceResult>> arrayList = simpleArrayMap.get(strCreateCacheId);
            if (arrayList != null) {
                arrayList.add(consumer);
                return null;
            }
            ArrayList<Consumer<TypefaceResult>> arrayList2 = new ArrayList<>();
            arrayList2.add(consumer);
            simpleArrayMap.put(strCreateCacheId, arrayList2);
            Callable<TypefaceResult> callable = new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public TypefaceResult call() {
                    try {
                        return FontRequestWorker.getFontSync(strCreateCacheId, context, fontRequest, i);
                    } catch (Throwable unused) {
                        return new TypefaceResult(-3);
                    }
                }
            };
            if (executor == null) {
                executor = DEFAULT_EXECUTOR_SERVICE;
            }
            executor.execute(new RequestExecutor.ReplyRunnable(CalleeHandler.create(), callable, new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.4
                @Override // androidx.core.util.Consumer
                public void accept(TypefaceResult typefaceResult) {
                    synchronized (FontRequestWorker.LOCK) {
                        SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> simpleArrayMap2 = FontRequestWorker.PENDING_REPLIES;
                        ArrayList<Consumer<TypefaceResult>> arrayList3 = simpleArrayMap2.get(strCreateCacheId);
                        if (arrayList3 == null) {
                            return;
                        }
                        simpleArrayMap2.remove(strCreateCacheId);
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            arrayList3.get(i2).accept(typefaceResult);
                        }
                    }
                }
            }));
            return null;
        }
    }

    public static Typeface requestFontSync(@NonNull final Context context, @NonNull final FontRequest fontRequest, @NonNull CallbackWithHandler callbackWithHandler, final int i, int i2) {
        final String strCreateCacheId = createCacheId(fontRequest, i);
        Typeface typeface = sTypefaceCache.get(strCreateCacheId);
        if (typeface != null) {
            callbackWithHandler.onTypefaceResult(new TypefaceResult(typeface));
            return typeface;
        }
        if (i2 == -1) {
            TypefaceResult fontSync = getFontSync(strCreateCacheId, context, fontRequest, i);
            callbackWithHandler.onTypefaceResult(fontSync);
            return fontSync.mTypeface;
        }
        try {
            try {
                try {
                    TypefaceResult typefaceResult = (TypefaceResult) DEFAULT_EXECUTOR_SERVICE.submit(new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.concurrent.Callable
                        public TypefaceResult call() {
                            return FontRequestWorker.getFontSync(strCreateCacheId, context, fontRequest, i);
                        }
                    }).get(i2, TimeUnit.MILLISECONDS);
                    callbackWithHandler.onTypefaceResult(typefaceResult);
                    return typefaceResult.mTypeface;
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException unused) {
                    throw new InterruptedException("timeout");
                }
            } catch (InterruptedException e2) {
                throw e2;
            }
        } catch (InterruptedException unused2) {
            callbackWithHandler.onTypefaceResult(new TypefaceResult(-3));
            return null;
        }
    }

    public static final class TypefaceResult {
        public final int mResult;
        public final Typeface mTypeface;

        public TypefaceResult(int i) {
            this.mTypeface = null;
            this.mResult = i;
        }

        @SuppressLint({"WrongConstant"})
        public TypefaceResult(@NonNull Typeface typeface) {
            this.mTypeface = typeface;
            this.mResult = 0;
        }
    }
}
