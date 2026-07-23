package com.google.common.reflect;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@ElementTypesAreNonnullByDefault
public final class ClassPath {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private final ImmutableSet<ResourceInfo> resources;
    private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();

    @Beta
    public static final class ClassInfo extends ResourceInfo {
        private final String className;

        public ClassInfo(File file, String str, ClassLoader classLoader) {
            super(file, str, classLoader);
            int i = ClassPath.$r8$clinit;
            this.className = str.substring(0, str.length() - 6).replace('/', '.');
        }

        public String getName() {
            return this.className;
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }

        public String getSimpleName() {
            int iLastIndexOf = this.className.lastIndexOf(36);
            if (iLastIndexOf != -1) {
                return CharMatcher.inRange('0', '9').trimLeadingFrom(this.className.substring(iLastIndexOf + 1));
            }
            String packageName = getPackageName();
            return packageName.isEmpty() ? this.className : this.className.substring(packageName.length() + 1);
        }

        public boolean isTopLevel() {
            return this.className.indexOf(36) == -1;
        }

        public Class<?> load() {
            try {
                return this.loader.loadClass(this.className);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.reflect.ClassPath.ResourceInfo
        public String toString() {
            return this.className;
        }
    }

    public static final class LocationInfo {
        private final ClassLoader classloader;
        public final File home;

        public LocationInfo(File file, ClassLoader classLoader) {
            this.home = (File) Preconditions.checkNotNull(file);
            this.classloader = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        private void scan(File file, Set<File> set, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            try {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        scanDirectory(file, builder);
                    } else {
                        scanJar(file, set, builder);
                    }
                }
            } catch (SecurityException e) {
                Logger logger = ClassPath.logger;
                String strValueOf = String.valueOf(file);
                String strValueOf2 = String.valueOf(e);
                logger.warning(MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 16, "Cannot access ", strValueOf, ": ", strValueOf2));
            }
        }

        private void scanDirectory(File file, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            HashSet hashSet = new HashSet();
            hashSet.add(file.getCanonicalFile());
            scanDirectory(file, "", hashSet, builder);
        }

        private void scanJar(File file, Set<File> set, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    UnmodifiableIterator<File> it = ClassPath.getClassPathFromManifest(file, jarFile.getManifest()).iterator();
                    while (it.hasNext()) {
                        File next = it.next();
                        if (set.add(next.getCanonicalFile())) {
                            scan(next, set, builder);
                        }
                    }
                    scanJarFile(jarFile, builder);
                } finally {
                    try {
                        jarFile.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (IOException unused2) {
            }
        }

        private void scanJarFile(JarFile jarFile, ImmutableSet.Builder<ResourceInfo> builder) {
            Enumeration<JarEntry> enumerationEntries = jarFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                JarEntry jarEntryNextElement = enumerationEntries.nextElement();
                if (!jarEntryNextElement.isDirectory() && !jarEntryNextElement.getName().equals("META-INF/MANIFEST.MF")) {
                    File file = new File(jarFile.getName());
                    String name = jarEntryNextElement.getName();
                    ClassLoader classLoader = this.classloader;
                    builder.add(name.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION) ? new ClassInfo(file, name, classLoader) : new ResourceInfo(file, name, classLoader));
                }
            }
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof LocationInfo)) {
                return false;
            }
            LocationInfo locationInfo = (LocationInfo) obj;
            return this.home.equals(locationInfo.home) && this.classloader.equals(locationInfo.classloader);
        }

        public final File file() {
            return this.home;
        }

        public int hashCode() {
            return this.home.hashCode();
        }

        public ImmutableSet<ResourceInfo> scanResources() throws IOException {
            return scanResources(new HashSet());
        }

        public String toString() {
            return this.home.toString();
        }

        public ImmutableSet<ResourceInfo> scanResources(Set<File> set) throws IOException {
            ImmutableSet.Builder<ResourceInfo> builder = ImmutableSet.builder();
            set.add(this.home);
            scan(this.home, set, builder);
            return builder.build();
        }

        private void scanDirectory(File file, String str, Set<File> set, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            ResourceInfo resourceInfo;
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                Logger logger = ClassPath.logger;
                String strValueOf = String.valueOf(file);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 22);
                sb.append("Cannot read directory ");
                sb.append(strValueOf);
                logger.warning(sb.toString());
                return;
            }
            for (File file2 : fileArrListFiles) {
                String name = file2.getName();
                if (file2.isDirectory()) {
                    File canonicalFile = file2.getCanonicalFile();
                    if (set.add(canonicalFile)) {
                        scanDirectory(canonicalFile, MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(name, Insets$$ExternalSyntheticOutline0.m(str, 1)), str, name, "/"), set, builder);
                        set.remove(canonicalFile);
                    }
                } else {
                    String strValueOf2 = String.valueOf(str);
                    String strValueOf3 = String.valueOf(name);
                    String strConcat = strValueOf3.length() != 0 ? strValueOf2.concat(strValueOf3) : new String(strValueOf2);
                    if (!strConcat.equals("META-INF/MANIFEST.MF")) {
                        ClassLoader classLoader = this.classloader;
                        if (strConcat.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION)) {
                            resourceInfo = new ClassInfo(file2, strConcat, classLoader);
                        } else {
                            resourceInfo = new ResourceInfo(file2, strConcat, classLoader);
                        }
                        builder.add(resourceInfo);
                    }
                }
            }
        }
    }

    @Beta
    public static class ResourceInfo {
        private final File file;
        public final ClassLoader loader;
        private final String resourceName;

        public ResourceInfo(File file, String str, ClassLoader classLoader) {
            this.file = (File) Preconditions.checkNotNull(file);
            this.resourceName = (String) Preconditions.checkNotNull(str);
            this.loader = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(url(), charset);
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof ResourceInfo)) {
                return false;
            }
            ResourceInfo resourceInfo = (ResourceInfo) obj;
            return this.resourceName.equals(resourceInfo.resourceName) && this.loader == resourceInfo.loader;
        }

        public final String getResourceName() {
            return this.resourceName;
        }

        public int hashCode() {
            return this.resourceName.hashCode();
        }

        public String toString() {
            return this.resourceName;
        }

        public final URL url() {
            URL resource = this.loader.getResource(this.resourceName);
            if (resource != null) {
                return resource;
            }
            throw new NoSuchElementException(this.resourceName);
        }
    }

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.resources = immutableSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ClassPath from(ClassLoader classLoader) throws IOException {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<Map.Entry<File, ClassLoader>> it = getClassPathEntries(classLoader).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<File, ClassLoader> next = it.next();
            builder.add(new LocationInfo(next.getKey(), next.getValue()));
        }
        ImmutableSet immutableSetBuild = builder.build();
        HashSet hashSet = new HashSet();
        UnmodifiableIterator it2 = immutableSetBuild.iterator();
        while (it2.hasNext()) {
            hashSet.add(((LocationInfo) it2.next()).file());
        }
        ImmutableSet.Builder builder2 = ImmutableSet.builder();
        UnmodifiableIterator it3 = immutableSetBuild.iterator();
        while (it3.hasNext()) {
            builder2.addAll((Iterable) ((LocationInfo) it3.next()).scanResources(hashSet));
        }
        return new ClassPath(builder2.build());
    }

    private static ImmutableList<URL> getClassLoaderUrls(ClassLoader classLoader) {
        if (classLoader instanceof URLClassLoader) {
            return ImmutableList.copyOf(((URLClassLoader) classLoader).getURLs());
        }
        if (!classLoader.equals(ClassLoader.getSystemClassLoader())) {
            return ImmutableList.of();
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (String str : Splitter.on(StandardSystemProperty.PATH_SEPARATOR.value()).split(StandardSystemProperty.JAVA_CLASS_PATH.value())) {
            try {
                try {
                    builder.add(new File(str).toURI().toURL());
                } catch (SecurityException unused) {
                    builder.add(new URL("file", (String) null, new File(str).getAbsolutePath()));
                }
            } catch (MalformedURLException e) {
                Logger logger2 = logger;
                Level level = Level.WARNING;
                String strValueOf = String.valueOf(str);
                logger2.log(level, strValueOf.length() != 0 ? "malformed classpath entry: ".concat(strValueOf) : new String("malformed classpath entry: "), (Throwable) e);
            }
        }
        return builder.build();
    }

    @VisibleForTesting
    public static ImmutableMap<File, ClassLoader> getClassPathEntries(ClassLoader classLoader) {
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        ClassLoader parent = classLoader.getParent();
        if (parent != null) {
            linkedHashMapNewLinkedHashMap.putAll(getClassPathEntries(parent));
        }
        UnmodifiableIterator<URL> it = getClassLoaderUrls(classLoader).iterator();
        while (it.hasNext()) {
            URL next = it.next();
            if (next.getProtocol().equals("file")) {
                File file = toFile(next);
                if (!linkedHashMapNewLinkedHashMap.containsKey(file)) {
                    linkedHashMapNewLinkedHashMap.put(file, classLoader);
                }
            }
        }
        return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
    }

    @VisibleForTesting
    public static ImmutableSet<File> getClassPathFromManifest(File file, @CheckForNull Manifest manifest) {
        if (manifest == null) {
            return ImmutableSet.of();
        }
        ImmutableSet.Builder builder = ImmutableSet.builder();
        String value = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
        if (value != null) {
            for (String str : CLASS_PATH_ATTRIBUTE_SEPARATOR.split(value)) {
                try {
                    URL url = new URL(file.toURI().toURL(), str);
                    if (url.getProtocol().equals("file")) {
                        builder.add(toFile(url));
                    }
                } catch (MalformedURLException unused) {
                    Logger logger2 = logger;
                    String strValueOf = String.valueOf(str);
                    logger2.warning(strValueOf.length() != 0 ? "Invalid Class-Path entry: ".concat(strValueOf) : new String("Invalid Class-Path entry: "));
                }
            }
        }
        return builder.build();
    }

    @VisibleForTesting
    public static File toFile(URL url) {
        Preconditions.checkArgument(url.getProtocol().equals("file"));
        try {
            return new File(url.toURI());
        } catch (URISyntaxException unused) {
            return new File(url.getPath());
        }
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.resources;
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(new Predicate<ClassInfo>() { // from class: com.google.common.reflect.ClassPath.1
            @Override // com.google.common.base.Predicate
            public boolean apply(ClassInfo classInfo) {
                return classInfo.isTopLevel();
            }
        }).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String str) {
        Preconditions.checkNotNull(str);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1);
        sb.append(str);
        sb.append('.');
        String string = sb.toString();
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<ClassInfo> it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo next = it.next();
            if (next.getName().startsWith(string)) {
                builder.add(next);
            }
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String str) {
        Preconditions.checkNotNull(str);
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<ClassInfo> it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo next = it.next();
            if (next.getPackageName().equals(str)) {
                builder.add(next);
            }
        }
        return builder.build();
    }
}
