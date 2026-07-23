package androidx.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes.dex */
final class MultiDexExtractor implements Closeable {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_CRC = "dex.crc.";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_DEX_TIME = "dex.time.";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final String LOCK_FILENAME = "MultiDex.lock";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private final FileLock cacheLock;
    private final File dexDir;
    private final FileChannel lockChannel;
    private final RandomAccessFile lockRaf;
    private final File sourceApk;
    private final long sourceCrc;

    public static class ExtractedDex extends File {
        public long crc;

        public ExtractedDex(File file, String str) {
            super(file, str);
            this.crc = -1L;
        }
    }

    public MultiDexExtractor(File file, File file2) throws IOException {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MultiDexExtractor(");
        sbM.append(file.getPath());
        sbM.append(", ");
        sbM.append(file2.getPath());
        sbM.append(")");
        Log.i(TAG, sbM.toString());
        this.sourceApk = file;
        this.dexDir = file2;
        this.sourceCrc = getZipCrc(file);
        File file3 = new File(file2, LOCK_FILENAME);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file3, "rw");
        this.lockRaf = randomAccessFile;
        try {
            FileChannel channel = randomAccessFile.getChannel();
            this.lockChannel = channel;
            try {
                Log.i(TAG, "Blocking on lock " + file3.getPath());
                this.cacheLock = channel.lock();
                Log.i(TAG, file3.getPath() + " locked");
            } catch (IOException e) {
                e = e;
                closeQuietly(this.lockChannel);
                throw e;
            } catch (Error e2) {
                e = e2;
                closeQuietly(this.lockChannel);
                throw e;
            } catch (RuntimeException e3) {
                e = e3;
                closeQuietly(this.lockChannel);
                throw e;
            }
        } catch (IOException | Error | RuntimeException e4) {
            closeQuietly(this.lockRaf);
            throw e4;
        }
    }

    private void clearDexDir() {
        File[] fileArrListFiles = this.dexDir.listFiles(new FileFilter() { // from class: androidx.multidex.MultiDexExtractor.1
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                return !file.getName().equals(MultiDexExtractor.LOCK_FILENAME);
            }
        });
        if (fileArrListFiles == null) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Failed to list secondary dex dir content (");
            sbM.append(this.dexDir.getPath());
            sbM.append(").");
            Log.w(TAG, sbM.toString());
            return;
        }
        for (File file : fileArrListFiles) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Trying to delete old file ");
            sbM2.append(file.getPath());
            sbM2.append(" of size ");
            sbM2.append(file.length());
            Log.i(TAG, sbM2.toString());
            if (file.delete()) {
                StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Deleted old file ");
                sbM3.append(file.getPath());
                Log.i(TAG, sbM3.toString());
            } else {
                StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m("Failed to delete old file ");
                sbM4.append(file.getPath());
                Log.w(TAG, sbM4.toString());
            }
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e);
        }
    }

    private static void extract(ZipFile zipFile, ZipEntry zipEntry, File file, String str) throws IOException {
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        File fileCreateTempFile = File.createTempFile(Insets$$ExternalSyntheticOutline0.m("tmp-", str), ".zip", file.getParentFile());
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Extracting ");
        sbM.append(fileCreateTempFile.getPath());
        Log.i(TAG, sbM.toString());
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(fileCreateTempFile)));
            try {
                ZipEntry zipEntry2 = new ZipEntry("classes.dex");
                zipEntry2.setTime(zipEntry.getTime());
                zipOutputStream.putNextEntry(zipEntry2);
                byte[] bArr = new byte[16384];
                for (int i = inputStream.read(bArr); i != -1; i = inputStream.read(bArr)) {
                    zipOutputStream.write(bArr, 0, i);
                }
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                if (!fileCreateTempFile.setReadOnly()) {
                    throw new IOException("Failed to mark readonly \"" + fileCreateTempFile.getAbsolutePath() + "\" (tmp of \"" + file.getAbsolutePath() + "\")");
                }
                Log.i(TAG, "Renaming to " + file.getPath());
                if (fileCreateTempFile.renameTo(file)) {
                    closeQuietly(inputStream);
                    fileCreateTempFile.delete();
                    return;
                }
                throw new IOException("Failed to rename \"" + fileCreateTempFile.getAbsolutePath() + "\" to \"" + file.getAbsolutePath() + "\"");
            } catch (Throwable th) {
                zipOutputStream.close();
                throw th;
            }
        } catch (Throwable th2) {
            closeQuietly(inputStream);
            fileCreateTempFile.delete();
            throw th2;
        }
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, 4);
    }

    private static long getTimeStamp(File file) {
        long jLastModified = file.lastModified();
        return jLastModified == -1 ? jLastModified - 1 : jLastModified;
    }

    private static long getZipCrc(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            ZipUtil.CentralDirectory centralDirectoryFindCentralDirectory = ZipUtil.findCentralDirectory(randomAccessFile);
            CRC32 crc32 = new CRC32();
            long j = centralDirectoryFindCentralDirectory.size;
            randomAccessFile.seek(centralDirectoryFindCentralDirectory.offset);
            byte[] bArr = new byte[16384];
            int i = randomAccessFile.read(bArr, 0, (int) Math.min(16384L, j));
            while (i != -1) {
                crc32.update(bArr, 0, i);
                j -= (long) i;
                if (j == 0) {
                    break;
                }
                i = randomAccessFile.read(bArr, 0, (int) Math.min(16384L, j));
            }
            long value = crc32.getValue();
            randomAccessFile.close();
            return value == -1 ? value - 1 : value;
        } catch (Throwable th) {
            randomAccessFile.close();
            throw th;
        }
    }

    private static boolean isModified(Context context, File file, long j, String str) {
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        if (multiDexPreferences.getLong(str + KEY_TIME_STAMP, -1L) == getTimeStamp(file)) {
            if (multiDexPreferences.getLong(str + KEY_CRC, -1L) == j) {
                return false;
            }
        }
        return true;
    }

    private List<ExtractedDex> loadExistingExtractions(Context context, String str) throws IOException {
        Log.i(TAG, "loading existing secondary dex files");
        String str2 = this.sourceApk.getName() + EXTRACTED_NAME_EXT;
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        int i = multiDexPreferences.getInt(str + KEY_DEX_NUMBER, 1);
        ArrayList arrayList = new ArrayList(i + (-1));
        int i2 = 2;
        while (i2 <= i) {
            ExtractedDex extractedDex = new ExtractedDex(this.dexDir, Insets$$ExternalSyntheticOutline0.m(str2, i2, ".zip"));
            if (!extractedDex.isFile()) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Missing extracted secondary dex file '");
                sbM.append(extractedDex.getPath());
                sbM.append("'");
                throw new IOException(sbM.toString());
            }
            extractedDex.crc = getZipCrc(extractedDex);
            long j = multiDexPreferences.getLong(str + KEY_DEX_CRC + i2, -1L);
            long j2 = multiDexPreferences.getLong(str + KEY_DEX_TIME + i2, -1L);
            long jLastModified = extractedDex.lastModified();
            if (j2 == jLastModified) {
                String str3 = str2;
                SharedPreferences sharedPreferences = multiDexPreferences;
                if (j == extractedDex.crc) {
                    arrayList.add(extractedDex);
                    i2++;
                    multiDexPreferences = sharedPreferences;
                    str2 = str3;
                }
            }
            throw new IOException("Invalid extracted dex: " + extractedDex + " (key \"" + str + "\"), expected modification time: " + j2 + ", modification time: " + jLastModified + ", expected crc: " + j + ", file crc: " + extractedDex.crc);
        }
        return arrayList;
    }

    private List<ExtractedDex> performExtractions() throws IOException {
        String str = this.sourceApk.getName() + EXTRACTED_NAME_EXT;
        clearDexDir();
        ArrayList arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(this.sourceApk);
        int i = 2;
        try {
            ZipEntry entry = zipFile.getEntry("classes2.dex");
            while (entry != null) {
                ExtractedDex extractedDex = new ExtractedDex(this.dexDir, str + i + ".zip");
                arrayList.add(extractedDex);
                Log.i(TAG, "Extraction is needed for file " + extractedDex);
                int i2 = 0;
                boolean z = false;
                while (i2 < 3 && !z) {
                    i2++;
                    extract(zipFile, entry, extractedDex, str);
                    try {
                        extractedDex.crc = getZipCrc(extractedDex);
                        z = true;
                    } catch (IOException e) {
                        Log.w(TAG, "Failed to read crc from " + extractedDex.getAbsolutePath(), e);
                        z = false;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Extraction ");
                    sb.append(z ? "succeeded" : "failed");
                    sb.append(" '");
                    sb.append(extractedDex.getAbsolutePath());
                    sb.append("': length ");
                    sb.append(extractedDex.length());
                    sb.append(" - crc: ");
                    sb.append(extractedDex.crc);
                    Log.i(TAG, sb.toString());
                    if (!z) {
                        extractedDex.delete();
                        if (extractedDex.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + extractedDex.getPath() + "'");
                        }
                    }
                }
                if (!z) {
                    throw new IOException("Could not create zip file " + extractedDex.getAbsolutePath() + " for secondary dex (" + i + ")");
                }
                i++;
                entry = zipFile.getEntry(DEX_PREFIX + i + ".dex");
            }
            try {
                zipFile.close();
            } catch (IOException e2) {
                Log.w(TAG, "Failed to close resource", e2);
            }
            return arrayList;
        } catch (Throwable th) {
            try {
                zipFile.close();
            } catch (IOException e3) {
                Log.w(TAG, "Failed to close resource", e3);
            }
            throw th;
        }
    }

    private static void putStoredApkInfo(Context context, String str, long j, long j2, List<ExtractedDex> list) {
        SharedPreferences.Editor editorEdit = getMultiDexPreferences(context).edit();
        editorEdit.putLong(str + KEY_TIME_STAMP, j);
        editorEdit.putLong(Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), str, KEY_CRC), j2);
        editorEdit.putInt(str + KEY_DEX_NUMBER, list.size() + 1);
        int i = 2;
        for (ExtractedDex extractedDex : list) {
            editorEdit.putLong(str + KEY_DEX_CRC + i, extractedDex.crc);
            editorEdit.putLong(str + KEY_DEX_TIME + i, extractedDex.lastModified());
            i++;
        }
        editorEdit.commit();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.cacheLock.release();
        this.lockChannel.close();
        this.lockRaf.close();
    }

    public final List<? extends File> load(Context context, String str, boolean z) throws IOException {
        List<ExtractedDex> listPerformExtractions;
        List<ExtractedDex> listLoadExistingExtractions;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MultiDexExtractor.load(");
        sbM.append(this.sourceApk.getPath());
        sbM.append(", ");
        sbM.append(z);
        sbM.append(", ");
        sbM.append(str);
        sbM.append(")");
        Log.i(TAG, sbM.toString());
        if (!this.cacheLock.isValid()) {
            throw new IllegalStateException("MultiDexExtractor was closed");
        }
        if (!z && !isModified(context, this.sourceApk, this.sourceCrc, str)) {
            try {
                listLoadExistingExtractions = loadExistingExtractions(context, str);
            } catch (IOException e) {
                Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e);
                listPerformExtractions = performExtractions();
                putStoredApkInfo(context, str, getTimeStamp(this.sourceApk), this.sourceCrc, listPerformExtractions);
                listLoadExistingExtractions = listPerformExtractions;
            }
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("load found ");
            sbM2.append(listLoadExistingExtractions.size());
            sbM2.append(" secondary dex files");
            Log.i(TAG, sbM2.toString());
            return listLoadExistingExtractions;
        }
        if (z) {
            Log.i(TAG, "Forced extraction must be performed.");
        } else {
            Log.i(TAG, "Detected that extraction must be performed.");
        }
        listPerformExtractions = performExtractions();
        putStoredApkInfo(context, str, getTimeStamp(this.sourceApk), this.sourceCrc, listPerformExtractions);
        listLoadExistingExtractions = listPerformExtractions;
        StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("load found ");
        sbM3.append(listLoadExistingExtractions.size());
        sbM3.append(" secondary dex files");
        Log.i(TAG, sbM3.toString());
        return listLoadExistingExtractions;
    }
}
