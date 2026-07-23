package androidx.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes.dex */
public final class MultiDex {
    private static final String CODE_CACHE_NAME = "code_cache";
    private static final String CODE_CACHE_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final boolean IS_VM_MULTIDEX_CAPABLE;
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String NO_KEY_PREFIX = "";
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<File> installedApk = new HashSet();

    public static final class V14 {
        private static final int EXTRACTED_SUFFIX_LENGTH = 4;
        private final ElementConstructor elementConstructor;

        public interface ElementConstructor {
            Object newInstance(File file, DexFile dexFile) throws IllegalAccessException, InstantiationException, IOException, IllegalArgumentException, InvocationTargetException;
        }

        public static class ICSElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            public ICSElementConstructor(Class<?> cls) throws NoSuchMethodException, SecurityException {
                Constructor<?> constructor = cls.getConstructor(File.class, ZipFile.class, DexFile.class);
                this.elementConstructor = constructor;
                constructor.setAccessible(true);
            }

            @Override // androidx.multidex.MultiDex.V14.ElementConstructor
            public Object newInstance(File file, DexFile dexFile) throws IllegalAccessException, InstantiationException, IOException, IllegalArgumentException, InvocationTargetException {
                return this.elementConstructor.newInstance(file, new ZipFile(file), dexFile);
            }
        }

        public static class JBMR11ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            public JBMR11ElementConstructor(Class<?> cls) throws NoSuchMethodException, SecurityException {
                Constructor<?> constructor = cls.getConstructor(File.class, File.class, DexFile.class);
                this.elementConstructor = constructor;
                constructor.setAccessible(true);
            }

            @Override // androidx.multidex.MultiDex.V14.ElementConstructor
            public Object newInstance(File file, DexFile dexFile) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
                return this.elementConstructor.newInstance(file, file, dexFile);
            }
        }

        public static class JBMR2ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            public JBMR2ElementConstructor(Class<?> cls) throws NoSuchMethodException, SecurityException {
                Constructor<?> constructor = cls.getConstructor(File.class, Boolean.TYPE, File.class, DexFile.class);
                this.elementConstructor = constructor;
                constructor.setAccessible(true);
            }

            @Override // androidx.multidex.MultiDex.V14.ElementConstructor
            public Object newInstance(File file, DexFile dexFile) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
                return this.elementConstructor.newInstance(file, Boolean.FALSE, file, dexFile);
            }
        }

        private V14() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
            ElementConstructor jBMR2ElementConstructor;
            Class<?> cls = Class.forName("dalvik.system.DexPathList$Element");
            try {
                try {
                    jBMR2ElementConstructor = new ICSElementConstructor(cls);
                } catch (NoSuchMethodException unused) {
                    jBMR2ElementConstructor = new JBMR2ElementConstructor(cls);
                }
            } catch (NoSuchMethodException unused2) {
                jBMR2ElementConstructor = new JBMR11ElementConstructor(cls);
            }
            this.elementConstructor = jBMR2ElementConstructor;
        }

        private Object[] makeDexElements(List<? extends File> list) throws IllegalAccessException, InstantiationException, IOException, SecurityException, IllegalArgumentException, InvocationTargetException {
            int size = list.size();
            Object[] objArr = new Object[size];
            for (int i = 0; i < size; i++) {
                File file = list.get(i);
                objArr[i] = this.elementConstructor.newInstance(file, DexFile.loadDex(file.getPath(), optimizedPathFor(file), 0));
            }
            return objArr;
        }

        private static String optimizedPathFor(File file) {
            File parentFile = file.getParentFile();
            String name = file.getName();
            return new File(parentFile, name.substring(0, name.length() - EXTRACTED_SUFFIX_LENGTH) + ".dex").getPath();
        }
    }

    public static final class V19 {
        private V19() {
        }

        public static void install(ClassLoader classLoader, List<? extends File> list, File file) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IOException, IllegalArgumentException, InvocationTargetException {
            IOException[] iOExceptionArr;
            Object obj = MultiDex.findField(classLoader, "pathList").get(classLoader);
            ArrayList arrayList = new ArrayList();
            MultiDex.expandFieldArray(obj, "dexElements", makeDexElements(obj, new ArrayList(list), file, arrayList));
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Log.w("MultiDex", "Exception in makeDexElement", (IOException) it.next());
                }
                Field fieldFindField = MultiDex.findField(obj, "dexElementsSuppressedExceptions");
                IOException[] iOExceptionArr2 = (IOException[]) fieldFindField.get(obj);
                if (iOExceptionArr2 == null) {
                    iOExceptionArr = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
                } else {
                    IOException[] iOExceptionArr3 = new IOException[arrayList.size() + iOExceptionArr2.length];
                    arrayList.toArray(iOExceptionArr3);
                    System.arraycopy(iOExceptionArr2, 0, iOExceptionArr3, arrayList.size(), iOExceptionArr2.length);
                    iOExceptionArr = iOExceptionArr3;
                }
                fieldFindField.set(obj, iOExceptionArr);
                IOException iOException = new IOException("I/O exception during makeDexElement");
                iOException.initCause((Throwable) arrayList.get(0));
                throw iOException;
            }
        }

        private static Object[] makeDexElements(Object obj, ArrayList<File> arrayList, File file, ArrayList<IOException> arrayList2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            return (Object[]) MultiDex.access$200(obj, new Class[]{ArrayList.class, File.class, ArrayList.class}).invoke(obj, arrayList, file, arrayList2);
        }
    }

    public static final class V4 {
        private V4() {
        }
    }

    static {
        String property = System.getProperty("java.vm.version");
        boolean z = false;
        if (property != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(property, ".");
            String strNextToken = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
            String strNextToken2 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
            if (strNextToken != null && strNextToken2 != null) {
                try {
                    int i = Integer.parseInt(strNextToken);
                    int i2 = Integer.parseInt(strNextToken2);
                    if (i > 2 || (i == 2 && i2 >= 1)) {
                        z = true;
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
        StringBuilder sbM25m = Insets$$ExternalSyntheticOutline0.m25m("VM with version ", property);
        sbM25m.append(z ? " has multidex support" : " does not have multidex support");
        Log.i("MultiDex", sbM25m.toString());
        IS_VM_MULTIDEX_CAPABLE = z;
    }

    private MultiDex() {
    }

    public static /* synthetic */ Method access$200(Object obj, Class[] clsArr) throws NoSuchMethodException {
        return findMethod(obj, "makeDexElements", clsArr);
    }

    private static void clearOldDexDir(Context context) throws Exception {
        File file = new File(context.getFilesDir(), "secondary-dexes");
        if (file.isDirectory()) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Clearing old secondary dex dir (");
            sbM.append(file.getPath());
            sbM.append(").");
            Log.i("MultiDex", sbM.toString());
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Failed to list secondary dex dir content (");
                sbM2.append(file.getPath());
                sbM2.append(").");
                Log.w("MultiDex", sbM2.toString());
                return;
            }
            for (File file2 : fileArrListFiles) {
                StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Trying to delete old file ");
                sbM3.append(file2.getPath());
                sbM3.append(" of size ");
                sbM3.append(file2.length());
                Log.i("MultiDex", sbM3.toString());
                if (file2.delete()) {
                    StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m("Deleted old file ");
                    sbM4.append(file2.getPath());
                    Log.i("MultiDex", sbM4.toString());
                } else {
                    StringBuilder sbM5 = Insets$$ExternalSyntheticOutline0.m("Failed to delete old file ");
                    sbM5.append(file2.getPath());
                    Log.w("MultiDex", sbM5.toString());
                }
            }
            if (file.delete()) {
                StringBuilder sbM6 = Insets$$ExternalSyntheticOutline0.m("Deleted old secondary dex dir ");
                sbM6.append(file.getPath());
                Log.i("MultiDex", sbM6.toString());
            } else {
                StringBuilder sbM7 = Insets$$ExternalSyntheticOutline0.m("Failed to delete secondary dex dir ");
                sbM7.append(file.getPath());
                Log.w("MultiDex", sbM7.toString());
            }
        }
    }

    private static void doInstallation(Context context, File file, File file2, String str, String str2, boolean z) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, IOException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Set<File> set = installedApk;
        synchronized (set) {
            if (set.contains(file)) {
                return;
            }
            set.add(file);
            Log.w("MultiDex", "MultiDex is not guaranteed to work in SDK version " + Build.VERSION.SDK_INT + ": SDK version higher than 20 should be backed by runtime with built-in multidex capabilty but it's not the case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"");
            ClassLoader dexClassloader = getDexClassloader(context);
            if (dexClassloader == null) {
                return;
            }
            try {
                clearOldDexDir(context);
            } catch (Throwable th) {
                Log.w("MultiDex", "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", th);
            }
            File dexDir = getDexDir(context, file2, str);
            MultiDexExtractor multiDexExtractor = new MultiDexExtractor(file, dexDir);
            IOException e = null;
            try {
                try {
                    installSecondaryDexes(dexClassloader, dexDir, multiDexExtractor.load(context, str2, false));
                } catch (IOException e2) {
                    if (!z) {
                        throw e2;
                    }
                    Log.w("MultiDex", "Failed to install extracted secondary dex files, retrying with forced extraction", e2);
                    installSecondaryDexes(dexClassloader, dexDir, multiDexExtractor.load(context, str2, true));
                }
                try {
                    multiDexExtractor.close();
                } catch (IOException e3) {
                    e = e3;
                }
                if (e != null) {
                    throw e;
                }
            } catch (Throwable th2) {
                try {
                    multiDexExtractor.close();
                } catch (IOException unused) {
                }
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void expandFieldArray(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Field fieldFindField = findField(obj, str);
        Object[] objArr2 = (Object[]) fieldFindField.get(obj);
        Object[] objArr3 = (Object[]) Array.newInstance(objArr2.getClass().getComponentType(), objArr2.length + objArr.length);
        System.arraycopy(objArr2, 0, objArr3, 0, objArr2.length);
        System.arraycopy(objArr, 0, objArr3, objArr2.length, objArr.length);
        fieldFindField.set(obj, objArr3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Field findField(Object obj, String str) throws NoSuchFieldException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Field declaredField = superclass.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException unused) {
            }
        }
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Field ", str, " not found in ");
        sbM26m.append(obj.getClass());
        throw new NoSuchFieldException(sbM26m.toString());
    }

    private static Method findMethod(Object obj, String str, Class<?>... clsArr) throws NoSuchMethodException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Method declaredMethod = superclass.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
            }
        }
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Method ", str, " with parameters ");
        sbM26m.append(Arrays.asList(clsArr));
        sbM26m.append(" not found in ");
        sbM26m.append(obj.getClass());
        throw new NoSuchMethodException(sbM26m.toString());
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getApplicationInfo();
        } catch (RuntimeException e) {
            Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    private static ClassLoader getDexClassloader(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            if (classLoader instanceof BaseDexClassLoader) {
                return classLoader;
            }
            Log.e("MultiDex", "Context class loader is null or not dex-capable. Must be running in test mode. Skip patching.");
            return null;
        } catch (RuntimeException e) {
            Log.w("MultiDex", "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    private static File getDexDir(Context context, File file, String str) throws IOException {
        File file2 = new File(file, CODE_CACHE_NAME);
        try {
            mkdirChecked(file2);
        } catch (IOException unused) {
            file2 = new File(context.getFilesDir(), CODE_CACHE_NAME);
            mkdirChecked(file2);
        }
        File file3 = new File(file2, str);
        mkdirChecked(file3);
        return file3;
    }

    public static void install(Context context) {
        Log.i("MultiDex", "Installing application");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i("MultiDex", "VM has multidex support, MultiDex support library is disabled.");
            return;
        }
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context);
            if (applicationInfo == null) {
                Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
            } else {
                doInstallation(context, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", "", true);
                Log.i("MultiDex", "install done");
            }
        } catch (Exception e) {
            Log.e("MultiDex", "MultiDex installation failure", e);
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MultiDex installation failed (");
            sbM.append(e.getMessage());
            sbM.append(").");
            throw new RuntimeException(sbM.toString());
        }
    }

    public static void installInstrumentation(Context context, Context context2) {
        Log.i("MultiDex", "Installing instrumentation");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i("MultiDex", "VM has multidex support, MultiDex support library is disabled.");
            return;
        }
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context);
            if (applicationInfo == null) {
                Log.i("MultiDex", "No ApplicationInfo available for instrumentation, i.e. running on a test Context: MultiDex support library is disabled.");
                return;
            }
            ApplicationInfo applicationInfo2 = getApplicationInfo(context2);
            if (applicationInfo2 == null) {
                Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                return;
            }
            String str = context.getPackageName() + ".";
            File file = new File(applicationInfo2.dataDir);
            doInstallation(context2, new File(applicationInfo.sourceDir), file, str + "secondary-dexes", str, false);
            doInstallation(context2, new File(applicationInfo2.sourceDir), file, "secondary-dexes", "", false);
            Log.i("MultiDex", "Installation done");
        } catch (Exception e) {
            Log.e("MultiDex", "MultiDex installation failure", e);
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MultiDex installation failed (");
            sbM.append(e.getMessage());
            sbM.append(").");
            throw new RuntimeException(sbM.toString());
        }
    }

    private static void installSecondaryDexes(ClassLoader classLoader, File file, List<? extends File> list) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, IOException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (list.isEmpty()) {
            return;
        }
        V19.install(classLoader, list, file);
    }

    private static void mkdirChecked(File file) throws IOException {
        file.mkdir();
        if (file.isDirectory()) {
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Failed to create dir ");
            sbM.append(file.getPath());
            sbM.append(". Parent file is null.");
            Log.e("MultiDex", sbM.toString());
        } else {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Failed to create dir ");
            sbM2.append(file.getPath());
            sbM2.append(". parent file is a dir ");
            sbM2.append(parentFile.isDirectory());
            sbM2.append(", a file ");
            sbM2.append(parentFile.isFile());
            sbM2.append(", exists ");
            sbM2.append(parentFile.exists());
            sbM2.append(", readable ");
            sbM2.append(parentFile.canRead());
            sbM2.append(", writable ");
            sbM2.append(parentFile.canWrite());
            Log.e("MultiDex", sbM2.toString());
        }
        StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Failed to create directory ");
        sbM3.append(file.getPath());
        throw new IOException(sbM3.toString());
    }
}
