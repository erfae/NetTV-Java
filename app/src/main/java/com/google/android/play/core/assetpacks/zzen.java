package com.google.android.play.core.assetpacks;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Objects;
import java.util.Properties;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzen {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("SliceMetadataManager");
    private final zzbh zzc;
    private final String zzd;
    private final int zze;
    private final long zzf;
    private final String zzg;
    private final byte[] zzb = new byte[8192];
    private int zzh = -1;

    public zzen(zzbh zzbhVar, String str, int i, long j, String str2) {
        this.zzc = zzbhVar;
        this.zzd = str;
        this.zze = i;
        this.zzf = j;
        this.zzg = str2;
    }

    private final File zzn() {
        File fileZzo = this.zzc.zzo(this.zzd, this.zze, this.zzf, this.zzg);
        if (!fileZzo.exists()) {
            fileZzo.mkdirs();
        }
        return fileZzo;
    }

    private final File zzo() throws IOException {
        File fileZzn = this.zzc.zzn(this.zzd, this.zze, this.zzf, this.zzg);
        fileZzn.getParentFile().mkdirs();
        fileZzn.createNewFile();
        return fileZzn;
    }

    public final int zza() throws IOException {
        File fileZzn = this.zzc.zzn(this.zzd, this.zze, this.zzf, this.zzg);
        if (!fileZzn.exists()) {
            return 0;
        }
        FileInputStream fileInputStream = new FileInputStream(fileZzn);
        try {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            if (Integer.parseInt(properties.getProperty("fileStatus", "-1")) == 4) {
                return -1;
            }
            if (properties.getProperty("previousChunk") != null) {
                return Integer.parseInt(properties.getProperty("previousChunk")) + 1;
            }
            throw new zzck("Slice checkpoint file corrupt.");
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final zzem zzb() throws IOException {
        File fileZzn = this.zzc.zzn(this.zzd, this.zze, this.zzf, this.zzg);
        if (!fileZzn.exists()) {
            throw new zzck("Slice checkpoint file does not exist.");
        }
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(fileZzn);
        try {
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("fileStatus") == null || properties.getProperty("previousChunk") == null) {
                throw new zzck("Slice checkpoint file corrupt.");
            }
            try {
                int i = Integer.parseInt(properties.getProperty("fileStatus"));
                String property = properties.getProperty("fileName");
                long j = Long.parseLong(properties.getProperty("fileOffset", "-1"));
                long j2 = Long.parseLong(properties.getProperty("remainingBytes", "-1"));
                int i2 = Integer.parseInt(properties.getProperty("previousChunk"));
                this.zzh = Integer.parseInt(properties.getProperty("metadataFileCounter", "0"));
                return new zzbp(i, property, j, j2, i2);
            } catch (NumberFormatException e) {
                throw new zzck("Slice checkpoint file corrupt.", e);
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final File zzc() {
        return new File(zzn(), String.format("%s-NAM.dat", Integer.valueOf(this.zzh)));
    }

    public final void zzd(InputStream inputStream, long j) throws IOException {
        int i;
        RandomAccessFile randomAccessFile = new RandomAccessFile(zzc(), "rw");
        try {
            randomAccessFile.seek(j);
            do {
                i = inputStream.read(this.zzb);
                if (i > 0) {
                    randomAccessFile.write(this.zzb, 0, i);
                }
            } while (i == 8192);
            randomAccessFile.close();
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final void zze(long j, byte[] bArr, int i, int i2) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(zzc(), "rw");
        try {
            randomAccessFile.seek(j);
            randomAccessFile.write(bArr, i, i2);
            randomAccessFile.close();
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final void zzf(int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", ExifInterface.GPS_MEASUREMENT_3D);
        properties.put("fileOffset", String.valueOf(zzc().length()));
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.zzh));
        FileOutputStream fileOutputStream = new FileOutputStream(zzo());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final void zzg(String str, long j, long j2, int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        properties.put("fileName", str);
        properties.put("fileOffset", String.valueOf(j));
        properties.put("remainingBytes", String.valueOf(j2));
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.zzh));
        FileOutputStream fileOutputStream = new FileOutputStream(zzo());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final void zzh(byte[] bArr, int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", ExifInterface.GPS_MEASUREMENT_2D);
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.zzh));
        FileOutputStream fileOutputStream = new FileOutputStream(zzo());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
            zzbh zzbhVar = this.zzc;
            String str = this.zzd;
            int i2 = this.zze;
            long j = this.zzf;
            String str2 = this.zzg;
            Objects.requireNonNull(zzbhVar);
            File file = new File(zzbhVar.zzo(str, i2, j, str2), "checkpoint_ext.dat");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(bArr);
                fileOutputStream2.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream2.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            try {
                fileOutputStream.close();
            } catch (Throwable unused2) {
            }
            throw th2;
        }
    }

    public final void zzi(int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", "4");
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.zzh));
        FileOutputStream fileOutputStream = new FileOutputStream(zzo());
        try {
            properties.store(fileOutputStream, (String) null);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final void zzj(byte[] bArr) throws IOException {
        this.zzh++;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(zzn(), String.format("%s-LFH.dat", Integer.valueOf(this.zzh))));
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } catch (IOException e) {
            throw new zzck("Could not write metadata file.", e);
        }
    }

    public final void zzk(byte[] bArr, InputStream inputStream) throws IOException {
        this.zzh++;
        FileOutputStream fileOutputStream = new FileOutputStream(zzc());
        try {
            fileOutputStream.write(bArr);
            int i = inputStream.read(this.zzb);
            while (i > 0) {
                fileOutputStream.write(this.zzb, 0, i);
                i = inputStream.read(this.zzb);
            }
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final void zzl(byte[] bArr, int i) throws IOException {
        this.zzh++;
        FileOutputStream fileOutputStream = new FileOutputStream(zzc());
        try {
            fileOutputStream.write(bArr, 0, i);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final boolean zzm() {
        File fileZzn = this.zzc.zzn(this.zzd, this.zze, this.zzf, this.zzg);
        if (!fileZzn.exists()) {
            return false;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileZzn);
            try {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                fileInputStream.close();
                if (properties.getProperty("fileStatus") != null) {
                    return Integer.parseInt(properties.getProperty("fileStatus")) == 4;
                }
                zza.zzb("Slice checkpoint file corrupt while checking if extraction finished.", new Object[0]);
                return false;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } catch (IOException e) {
            zza.zzb("Could not read checkpoint while checking if extraction finished. %s", e);
            return false;
        }
    }
}
