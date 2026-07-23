package com.getkeepsafe.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    public static class ZipFileInZipEntry {
        public ZipEntry zipEntry;
        public ZipFile zipFile;

        public ZipFileInZipEntry(ZipFile zipFile, ZipEntry zipEntry) {
            this.zipFile = zipFile;
            this.zipEntry = zipEntry;
        }
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    private long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                outputStream.flush();
                return j;
            }
            outputStream.write(bArr, 0, i);
            j += (long) i;
        }
    }

    private ZipFileInZipEntry findAPKWithLibrary(Context context, String[] strArr, String str, ReLinkerInstance reLinkerInstance) {
        String[] strArrSourceDirectories = sourceDirectories(context);
        int length = strArrSourceDirectories.length;
        char c = 0;
        int i = 0;
        while (true) {
            ZipFile zipFile = null;
            if (i >= length) {
                return null;
            }
            String str2 = strArrSourceDirectories[i];
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(str2), 1);
                    break;
                } catch (IOException unused) {
                    i2 = i3;
                }
            }
            if (zipFile != null) {
                int i4 = 0;
                while (true) {
                    int i5 = i4 + 1;
                    if (i4 >= 5) {
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException unused2) {
                            break;
                        }
                    }
                    int length2 = strArr.length;
                    int i6 = 0;
                    while (i6 < length2) {
                        String str3 = strArr[i6];
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("lib");
                        sbM.append(File.separatorChar);
                        sbM.append(str3);
                        sbM.append(File.separatorChar);
                        sbM.append(str);
                        String string = sbM.toString();
                        Object[] objArr = new Object[2];
                        objArr[c] = string;
                        objArr[1] = str2;
                        reLinkerInstance.log("Looking for %s in APK %s...", objArr);
                        ZipEntry entry = zipFile.getEntry(string);
                        if (entry != null) {
                            return new ZipFileInZipEntry(zipFile, entry);
                        }
                        i6++;
                        c = 0;
                    }
                    c = 0;
                    i4 = i5;
                }
            }
            i++;
            c = 0;
        }
    }

    private String[] getSupportedABIs(Context context, String str) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("lib");
        sbM.append(File.separatorChar);
        sbM.append("([^\\");
        sbM.append(File.separatorChar);
        sbM.append("]*)");
        sbM.append(File.separatorChar);
        sbM.append(str);
        Pattern patternCompile = Pattern.compile(sbM.toString());
        HashSet hashSet = new HashSet();
        for (String str2 : sourceDirectories(context)) {
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(new File(str2), 1).entries();
                while (enumerationEntries.hasMoreElements()) {
                    Matcher matcher = patternCompile.matcher(enumerationEntries.nextElement().getName());
                    if (matcher.matches()) {
                        hashSet.add(matcher.group(1));
                    }
                }
            } catch (IOException unused) {
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    private String[] sourceDirectories(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String[] strArr = applicationInfo.splitSourceDirs;
        if (strArr == null || strArr.length == 0) {
            return new String[]{applicationInfo.sourceDir};
        }
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = applicationInfo.sourceDir;
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        return strArr2;
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    public void installLibrary(Context context, String[] strArr, String str, File file, ReLinkerInstance reLinkerInstance) throws Throwable {
        String[] supportedABIs;
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        ZipFileInZipEntry zipFileInZipEntry = null;
        Closeable closeable = null;
        try {
            ZipFileInZipEntry zipFileInZipEntryFindAPKWithLibrary = findAPKWithLibrary(context, strArr, str, reLinkerInstance);
            try {
                if (zipFileInZipEntryFindAPKWithLibrary == null) {
                    try {
                        supportedABIs = getSupportedABIs(context, str);
                    } catch (Exception e) {
                        supportedABIs = new String[]{e.toString()};
                    }
                    throw new MissingLibraryException(str, strArr, supportedABIs);
                }
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    if (i >= 5) {
                        reLinkerInstance.log("FATAL! Couldn't extract the library from the APK!");
                        try {
                            ZipFile zipFile = zipFileInZipEntryFindAPKWithLibrary.zipFile;
                            if (zipFile != null) {
                                zipFile.close();
                                return;
                            }
                            return;
                        } catch (IOException unused) {
                            return;
                        }
                    }
                    reLinkerInstance.log("Found %s! Extracting...", str);
                    try {
                        if (file.exists() || file.createNewFile()) {
                            try {
                                inputStream = zipFileInZipEntryFindAPKWithLibrary.zipFile.getInputStream(zipFileInZipEntryFindAPKWithLibrary.zipEntry);
                                try {
                                    fileOutputStream = new FileOutputStream(file);
                                    try {
                                        long jCopy = copy(inputStream, fileOutputStream);
                                        fileOutputStream.getFD().sync();
                                        if (jCopy == file.length()) {
                                            closeSilently(inputStream);
                                            closeSilently(fileOutputStream);
                                            file.setReadable(true, false);
                                            file.setExecutable(true, false);
                                            file.setWritable(true);
                                            try {
                                                ZipFile zipFile2 = zipFileInZipEntryFindAPKWithLibrary.zipFile;
                                                if (zipFile2 != null) {
                                                    zipFile2.close();
                                                    return;
                                                }
                                                return;
                                            } catch (IOException unused2) {
                                                return;
                                            }
                                        }
                                        closeSilently(inputStream);
                                        closeSilently(fileOutputStream);
                                    } catch (FileNotFoundException unused3) {
                                        closeSilently(inputStream);
                                    } catch (IOException unused4) {
                                        closeSilently(inputStream);
                                    } catch (Throwable th) {
                                        th = th;
                                        closeable = inputStream;
                                        closeSilently(closeable);
                                        closeSilently(fileOutputStream);
                                        throw th;
                                    }
                                } catch (FileNotFoundException unused5) {
                                    fileOutputStream = null;
                                } catch (IOException unused6) {
                                    fileOutputStream = null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileOutputStream = null;
                                }
                            } catch (FileNotFoundException unused7) {
                                inputStream = null;
                                fileOutputStream = null;
                            } catch (IOException unused8) {
                                inputStream = null;
                                fileOutputStream = null;
                            } catch (Throwable th3) {
                                th = th3;
                                fileOutputStream = null;
                            }
                        }
                    } catch (IOException unused9) {
                    }
                    i = i2;
                }
            } catch (Throwable th4) {
                th = th4;
                zipFileInZipEntry = zipFileInZipEntryFindAPKWithLibrary;
                if (zipFileInZipEntry != null) {
                    try {
                        ZipFile zipFile3 = zipFileInZipEntry.zipFile;
                        if (zipFile3 != null) {
                            zipFile3.close();
                        }
                    } catch (IOException unused10) {
                    }
                }
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }
}
