package com.google.android.play.core.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzcl {
    public static long zza(zzcm zzcmVar, InputStream inputStream, OutputStream outputStream, long j) throws IOException {
        byte[] bArr = new byte[16384];
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream, 4096));
        int i = dataInputStream.readInt();
        if (i != -771763713) {
            String strValueOf = String.valueOf(String.format("%x", Integer.valueOf(i)));
            throw new zzck(strValueOf.length() != 0 ? "Unexpected magic=".concat(strValueOf) : new String("Unexpected magic="));
        }
        int i2 = dataInputStream.read();
        if (i2 != 4) {
            throw new zzck(Insets$$ExternalSyntheticOutline0.m(30, "Unexpected version=", i2));
        }
        long j2 = 0;
        while (true) {
            long j3 = j - j2;
            try {
                int unsignedShort = dataInputStream.read();
                if (unsignedShort == -1) {
                    throw new IOException("Patch file overrun");
                }
                if (unsignedShort == 0) {
                    outputStream.flush();
                    return j2;
                }
                switch (unsignedShort) {
                    case 247:
                        unsignedShort = dataInputStream.readUnsignedShort();
                        zzc(bArr, dataInputStream, outputStream, unsignedShort, j3);
                        break;
                    case 248:
                        unsignedShort = dataInputStream.readInt();
                        zzc(bArr, dataInputStream, outputStream, unsignedShort, j3);
                        break;
                    case 249:
                        long unsignedShort2 = dataInputStream.readUnsignedShort();
                        unsignedShort = dataInputStream.read();
                        if (unsignedShort == -1) {
                            throw new IOException("Unexpected end of patch");
                        }
                        zzb(bArr, zzcmVar, outputStream, unsignedShort2, unsignedShort, j3);
                        break;
                        break;
                    case ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION /* 250 */:
                        long unsignedShort3 = dataInputStream.readUnsignedShort();
                        unsignedShort = dataInputStream.readUnsignedShort();
                        zzb(bArr, zzcmVar, outputStream, unsignedShort3, unsignedShort, j3);
                        break;
                    case 251:
                        long unsignedShort4 = dataInputStream.readUnsignedShort();
                        unsignedShort = dataInputStream.readInt();
                        zzb(bArr, zzcmVar, outputStream, unsignedShort4, unsignedShort, j3);
                        break;
                    case 252:
                        long j4 = dataInputStream.readInt();
                        unsignedShort = dataInputStream.read();
                        if (unsignedShort == -1) {
                            throw new IOException("Unexpected end of patch");
                        }
                        zzb(bArr, zzcmVar, outputStream, j4, unsignedShort, j3);
                        break;
                        break;
                    case 253:
                        long j5 = dataInputStream.readInt();
                        unsignedShort = dataInputStream.readUnsignedShort();
                        zzb(bArr, zzcmVar, outputStream, j5, unsignedShort, j3);
                        break;
                    case 254:
                        long j6 = dataInputStream.readInt();
                        unsignedShort = dataInputStream.readInt();
                        zzb(bArr, zzcmVar, outputStream, j6, unsignedShort, j3);
                        break;
                    case 255:
                        long j7 = dataInputStream.readLong();
                        unsignedShort = dataInputStream.readInt();
                        zzb(bArr, zzcmVar, outputStream, j7, unsignedShort, j3);
                        break;
                    default:
                        zzc(bArr, dataInputStream, outputStream, unsignedShort, j3);
                        break;
                }
                j2 += (long) unsignedShort;
            } catch (Throwable th) {
                outputStream.flush();
                throw th;
            }
        }
    }

    private static void zzb(byte[] bArr, zzcm zzcmVar, OutputStream outputStream, long j, int i, long j2) throws IOException {
        int i2 = i;
        if (i2 < 0) {
            throw new IOException("copyLength negative");
        }
        if (j < 0) {
            throw new IOException("inputOffset negative");
        }
        long j3 = i2;
        if (j3 > j2) {
            throw new IOException("Output length overrun");
        }
        try {
            InputStream inputStreamZzc = new zzcn(zzcmVar, j, j3).zzc();
            while (i2 > 0) {
                try {
                    int iMin = Math.min(i2, 16384);
                    int i3 = 0;
                    while (i3 < iMin) {
                        int i4 = inputStreamZzc.read(bArr, i3, iMin - i3);
                        if (i4 == -1) {
                            throw new IOException("truncated input stream");
                        }
                        i3 += i4;
                    }
                    outputStream.write(bArr, 0, iMin);
                    i2 -= iMin;
                } catch (Throwable th) {
                    try {
                        inputStreamZzc.close();
                    } catch (Throwable unused) {
                    }
                    throw th;
                }
            }
            inputStreamZzc.close();
        } catch (EOFException e) {
            throw new IOException("patch underrun", e);
        }
    }

    private static void zzc(byte[] bArr, DataInputStream dataInputStream, OutputStream outputStream, int i, long j) throws IOException {
        if (i < 0) {
            throw new IOException("copyLength negative");
        }
        if (i > j) {
            throw new IOException("Output length overrun");
        }
        while (i > 0) {
            try {
                int iMin = Math.min(i, 16384);
                dataInputStream.readFully(bArr, 0, iMin);
                outputStream.write(bArr, 0, iMin);
                i -= iMin;
            } catch (EOFException unused) {
                throw new IOException("patch underrun");
            }
        }
    }
}
