package kotlin.internal;

import androidx.exifinterface.media.ExifInterface;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.internal.jdk8.JDK8PlatformImplementations;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PlatformImplementations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", ExifInterface.GPS_DIRECTION_TRUE, "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
public final class PlatformImplementationsKt {

    @JvmField
    @NotNull
    public static final PlatformImplementations IMPLEMENTATIONS;

    /* JADX WARN: Code duplicated, block: B:35:0x00b8 A[Catch: ClassCastException -> 0x00bd, ClassNotFoundException -> 0x00f3, TRY_ENTER, TryCatch #4 {ClassCastException -> 0x00bd, blocks: (B:35:0x00b8, B:38:0x00bf, B:39:0x00c4), top: B:64:0x00b6, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00bf A[Catch: ClassCastException -> 0x00bd, ClassNotFoundException -> 0x00f3, TryCatch #4 {ClassCastException -> 0x00bd, blocks: (B:35:0x00b8, B:38:0x00bf, B:39:0x00c4), top: B:64:0x00b6, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x00ad A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static {
        PlatformImplementations platformImplementations;
        Object objNewInstance;
        int javaVersion = getJavaVersion();
        if (javaVersion >= 65544 || javaVersion < 65536) {
            try {
                Object objNewInstance2 = JDK8PlatformImplementations.class.newInstance();
                Intrinsics.checkNotNullExpressionValue(objNewInstance2, "forName(\"kotlin.internal…entations\").newInstance()");
                try {
                    try {
                        if (objNewInstance2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                        }
                        platformImplementations = (PlatformImplementations) objNewInstance2;
                    } catch (ClassNotFoundException unused) {
                        if (javaVersion < 65543) {
                            try {
                                try {
                                    objNewInstance = JDK7PlatformImplementations.class.newInstance();
                                    Intrinsics.checkNotNullExpressionValue(objNewInstance, "forName(\"kotlin.internal…entations\").newInstance()");
                                    try {
                                        if (objNewInstance == null) {
                                            throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                                        }
                                        platformImplementations = (PlatformImplementations) objNewInstance;
                                    } catch (ClassCastException e) {
                                        ClassLoader classLoader = objNewInstance.getClass().getClassLoader();
                                        ClassLoader classLoader2 = PlatformImplementations.class.getClassLoader();
                                        if (Intrinsics.areEqual(classLoader, classLoader2)) {
                                            throw e;
                                        }
                                        throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader + ", base type classloader: " + classLoader2, e);
                                    }
                                } catch (ClassNotFoundException unused2) {
                                    platformImplementations = new PlatformImplementations();
                                }
                            } catch (ClassNotFoundException unused3) {
                                Object objNewInstance3 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                                Intrinsics.checkNotNullExpressionValue(objNewInstance3, "forName(\"kotlin.internal…entations\").newInstance()");
                                try {
                                    if (objNewInstance3 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                                    }
                                    platformImplementations = (PlatformImplementations) objNewInstance3;
                                } catch (ClassCastException e2) {
                                    ClassLoader classLoader3 = objNewInstance3.getClass().getClassLoader();
                                    ClassLoader classLoader4 = PlatformImplementations.class.getClassLoader();
                                    if (Intrinsics.areEqual(classLoader3, classLoader4)) {
                                        throw e2;
                                    }
                                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader3 + ", base type classloader: " + classLoader4, e2);
                                }
                            }
                        } else {
                            objNewInstance = JDK7PlatformImplementations.class.newInstance();
                            Intrinsics.checkNotNullExpressionValue(objNewInstance, "forName(\"kotlin.internal…entations\").newInstance()");
                            if (objNewInstance == null) {
                                throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                            }
                            platformImplementations = (PlatformImplementations) objNewInstance;
                        }
                    }
                } catch (ClassCastException e3) {
                    ClassLoader classLoader5 = objNewInstance2.getClass().getClassLoader();
                    ClassLoader classLoader6 = PlatformImplementations.class.getClassLoader();
                    if (Intrinsics.areEqual(classLoader5, classLoader6)) {
                        throw e3;
                    }
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader5 + ", base type classloader: " + classLoader6, e3);
                }
            } catch (ClassNotFoundException unused4) {
                Object objNewInstance4 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                Intrinsics.checkNotNullExpressionValue(objNewInstance4, "forName(\"kotlin.internal…entations\").newInstance()");
                try {
                    if (objNewInstance4 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                    platformImplementations = (PlatformImplementations) objNewInstance4;
                } catch (ClassCastException e4) {
                    ClassLoader classLoader7 = objNewInstance4.getClass().getClassLoader();
                    ClassLoader classLoader8 = PlatformImplementations.class.getClassLoader();
                    if (Intrinsics.areEqual(classLoader7, classLoader8)) {
                        throw e4;
                    }
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader7 + ", base type classloader: " + classLoader8, e4);
                }
            }
        } else if (javaVersion < 65543 || javaVersion < 65536) {
            objNewInstance = JDK7PlatformImplementations.class.newInstance();
            Intrinsics.checkNotNullExpressionValue(objNewInstance, "forName(\"kotlin.internal…entations\").newInstance()");
            if (objNewInstance == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            }
            platformImplementations = (PlatformImplementations) objNewInstance;
        } else {
            platformImplementations = new PlatformImplementations();
        }
        IMPLEMENTATIONS = platformImplementations;
    }

    @SinceKotlin(version = "1.2")
    @PublishedApi
    public static final boolean apiVersionIsAtLeast(int i, int i2, int i3) {
        return KotlinVersion.CURRENT.isAtLeast(i, i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @InlineOnly
    private static final /* synthetic */ <T> T castToBaseType(Object obj) throws ClassNotFoundException {
        try {
            Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
            return obj;
        } catch (ClassCastException e) {
            ClassLoader classLoader = obj.getClass().getClassLoader();
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            ClassLoader classLoader2 = Object.class.getClassLoader();
            if (Intrinsics.areEqual(classLoader, classLoader2)) {
                throw e;
            }
            throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader + ", base type classloader: " + classLoader2, e);
        }
    }

    private static final int getJavaVersion() {
        String property = System.getProperty("java.specification.version");
        if (property == null) {
            return 65542;
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) property, '.', 0, 6);
        if (iIndexOf$default < 0) {
            try {
                return Integer.parseInt(property) * 65536;
            } catch (NumberFormatException unused) {
                return 65542;
            }
        }
        int i = iIndexOf$default + 1;
        int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) property, '.', i, 4);
        if (iIndexOf$default2 < 0) {
            iIndexOf$default2 = property.length();
        }
        String strSubstring = property.substring(0, iIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        String strSubstring2 = property.substring(i, iIndexOf$default2);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
        try {
            return (Integer.parseInt(strSubstring) * 65536) + Integer.parseInt(strSubstring2);
        } catch (NumberFormatException unused2) {
            return 65542;
        }
    }
}
