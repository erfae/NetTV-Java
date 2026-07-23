package com.google.android.play.core.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Pair;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzi {
    public static X509Certificate[][] zza(String str) throws zzf, SecurityException, IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            Pair pairZzc = zzj.zzc(randomAccessFile);
            if (pairZzc == null) {
                long length = randomAccessFile.length();
                StringBuilder sb = new StringBuilder(102);
                sb.append("Not an APK file: ZIP End of Central Directory record not found in file with ");
                sb.append(length);
                sb.append(" bytes");
                throw new zzf(sb.toString());
            }
            ByteBuffer byteBuffer = (ByteBuffer) pairZzc.first;
            long jLongValue = ((Long) pairZzc.second).longValue();
            long j = (-20) + jLongValue;
            if (j >= 0) {
                randomAccessFile.seek(j);
                if (randomAccessFile.readInt() == 1347094023) {
                    throw new zzf("ZIP64 APK not supported");
                }
            }
            long jZza = zzj.zza(byteBuffer);
            if (jZza >= jLongValue) {
                StringBuilder sb2 = new StringBuilder(122);
                sb2.append("ZIP Central Directory offset out of range: ");
                sb2.append(jZza);
                sb2.append(". ZIP End of Central Directory offset: ");
                sb2.append(jLongValue);
                throw new zzf(sb2.toString());
            }
            if (zzj.zzb(byteBuffer) + jZza != jLongValue) {
                throw new zzf("ZIP Central Directory is not immediately followed by End of Central Directory");
            }
            if (jZza < 32) {
                StringBuilder sb3 = new StringBuilder(87);
                sb3.append("APK too small for APK Signing Block. ZIP Central Directory offset: ");
                sb3.append(jZza);
                throw new zzf(sb3.toString());
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(24);
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            byteBufferAllocate.order(byteOrder);
            randomAccessFile.seek(jZza - ((long) byteBufferAllocate.capacity()));
            randomAccessFile.readFully(byteBufferAllocate.array(), byteBufferAllocate.arrayOffset(), byteBufferAllocate.capacity());
            if (byteBufferAllocate.getLong(8) != 2334950737559900225L || byteBufferAllocate.getLong(16) != 3617552046287187010L) {
                throw new zzf("No APK Signing Block before ZIP Central Directory");
            }
            int i = 0;
            long j2 = byteBufferAllocate.getLong(0);
            if (j2 < byteBufferAllocate.capacity() || j2 > 2147483639) {
                StringBuilder sb4 = new StringBuilder(57);
                sb4.append("APK Signing Block size out of range: ");
                sb4.append(j2);
                throw new zzf(sb4.toString());
            }
            int i2 = (int) (8 + j2);
            long j3 = jZza - ((long) i2);
            if (j3 < 0) {
                StringBuilder sb5 = new StringBuilder(59);
                sb5.append("APK Signing Block offset out of range: ");
                sb5.append(j3);
                throw new zzf(sb5.toString());
            }
            ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(i2);
            byteBufferAllocate2.order(byteOrder);
            randomAccessFile.seek(j3);
            randomAccessFile.readFully(byteBufferAllocate2.array(), byteBufferAllocate2.arrayOffset(), byteBufferAllocate2.capacity());
            long j4 = byteBufferAllocate2.getLong(0);
            if (j4 != j2) {
                StringBuilder sb6 = new StringBuilder(103);
                sb6.append("APK Signing Block sizes in header and footer do not match: ");
                sb6.append(j4);
                sb6.append(" vs ");
                sb6.append(j2);
                throw new zzf(sb6.toString());
            }
            Pair pairCreate = Pair.create(byteBufferAllocate2, Long.valueOf(j3));
            ByteBuffer byteBuffer2 = (ByteBuffer) pairCreate.first;
            long jLongValue2 = ((Long) pairCreate.second).longValue();
            if (byteBuffer2.order() != byteOrder) {
                throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
            }
            int iCapacity = byteBuffer2.capacity() - 24;
            if (iCapacity < 8) {
                StringBuilder sb7 = new StringBuilder(38);
                sb7.append("end < start: ");
                sb7.append(iCapacity);
                sb7.append(" < ");
                sb7.append(8);
                throw new IllegalArgumentException(sb7.toString());
            }
            int iCapacity2 = byteBuffer2.capacity();
            if (iCapacity > byteBuffer2.capacity()) {
                StringBuilder sb8 = new StringBuilder(41);
                sb8.append("end > capacity: ");
                sb8.append(iCapacity);
                sb8.append(" > ");
                sb8.append(iCapacity2);
                throw new IllegalArgumentException(sb8.toString());
            }
            int iLimit = byteBuffer2.limit();
            int iPosition = byteBuffer2.position();
            try {
                byteBuffer2.position(0);
                byteBuffer2.limit(iCapacity);
                byteBuffer2.position(8);
                ByteBuffer byteBufferSlice = byteBuffer2.slice();
                byteBufferSlice.order(byteBuffer2.order());
                byteBuffer2.position(0);
                byteBuffer2.limit(iLimit);
                byteBuffer2.position(iPosition);
                while (byteBufferSlice.hasRemaining()) {
                    i++;
                    if (byteBufferSlice.remaining() < 8) {
                        StringBuilder sb9 = new StringBuilder(70);
                        sb9.append("Insufficient data to read size of APK Signing Block entry #");
                        sb9.append(i);
                        throw new zzf(sb9.toString());
                    }
                    long j5 = byteBufferSlice.getLong();
                    if (j5 < 4 || j5 > 2147483647L) {
                        StringBuilder sb10 = new StringBuilder(76);
                        sb10.append("APK Signing Block entry #");
                        sb10.append(i);
                        sb10.append(" size out of range: ");
                        sb10.append(j5);
                        throw new zzf(sb10.toString());
                    }
                    int i3 = (int) j5;
                    int iPosition2 = byteBufferSlice.position() + i3;
                    if (i3 > byteBufferSlice.remaining()) {
                        int iRemaining = byteBufferSlice.remaining();
                        StringBuilder sb11 = new StringBuilder(91);
                        sb11.append("APK Signing Block entry #");
                        sb11.append(i);
                        sb11.append(" size out of range: ");
                        sb11.append(i3);
                        sb11.append(", available: ");
                        sb11.append(iRemaining);
                        throw new zzf(sb11.toString());
                    }
                    if (byteBufferSlice.getInt() == 1896449818) {
                        X509Certificate[][] x509CertificateArrZzl = zzl(randomAccessFile.getChannel(), new zze(zze(byteBufferSlice, i3 - 4), jLongValue2, jZza, jLongValue, byteBuffer));
                        randomAccessFile.close();
                        try {
                            randomAccessFile.close();
                        } catch (IOException unused) {
                        }
                        return x509CertificateArrZzl;
                    }
                    byteBufferSlice.position(iPosition2);
                }
                throw new zzf("No APK Signature Scheme v2 block in APK Signing Block");
            } catch (Throwable th) {
                byteBuffer2.position(0);
                byteBuffer2.limit(iLimit);
                byteBuffer2.position(iPosition);
                throw th;
            }
        } catch (Throwable th2) {
            try {
                randomAccessFile.close();
            } catch (IOException unused2) {
            }
            throw th2;
        }
    }

    private static int zzb(int i) {
        if (i == 1) {
            return 32;
        }
        if (i == 2) {
            return 64;
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(44, "Unknown content digest algorthm: ", i));
    }

    private static int zzc(int i) {
        if (i == 513) {
            return 1;
        }
        if (i == 514) {
            return 2;
        }
        if (i == 769) {
            return 1;
        }
        switch (i) {
            case 257:
            case 259:
                return 1;
            case 258:
            case 260:
                return 2;
            default:
                String strValueOf = String.valueOf(Long.toHexString(i));
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unknown signature algorithm: 0x".concat(strValueOf) : new String("Unknown signature algorithm: 0x"));
        }
    }

    private static String zzd(int i) {
        if (i == 1) {
            return "SHA-256";
        }
        if (i == 2) {
            return "SHA-512";
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(44, "Unknown content digest algorthm: ", i));
    }

    private static ByteBuffer zze(ByteBuffer byteBuffer, int i) throws BufferUnderflowException {
        int iLimit = byteBuffer.limit();
        int iPosition = byteBuffer.position();
        int i2 = i + iPosition;
        if (i2 < iPosition || i2 > iLimit) {
            throw new BufferUnderflowException();
        }
        byteBuffer.limit(i2);
        try {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            byteBufferSlice.order(byteBuffer.order());
            byteBuffer.position(i2);
            return byteBufferSlice;
        } finally {
            byteBuffer.limit(iLimit);
        }
    }

    private static ByteBuffer zzf(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() < 4) {
            throw new IOException(Insets$$ExternalSyntheticOutline0.m(93, "Remaining buffer too short to contain length of length-prefixed field. Remaining: ", byteBuffer.remaining()));
        }
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        if (i <= byteBuffer.remaining()) {
            return zze(byteBuffer, i);
        }
        int iRemaining = byteBuffer.remaining();
        StringBuilder sb = new StringBuilder(101);
        sb.append("Length-prefixed field longer than remaining buffer. Field length: ");
        sb.append(i);
        sb.append(", remaining: ");
        sb.append(iRemaining);
        throw new IOException(sb.toString());
    }

    private static void zzg(int i, byte[] bArr, int i2) {
        bArr[1] = (byte) (i & 255);
        bArr[2] = (byte) ((i >>> 8) & 255);
        bArr[3] = (byte) ((i >>> 16) & 255);
        bArr[4] = (byte) (i >> 24);
    }

    private static void zzh(Map map, FileChannel fileChannel, long j, long j2, long j3, ByteBuffer byteBuffer) throws SecurityException {
        if (map.isEmpty()) {
            throw new SecurityException("No digests provided");
        }
        zzd zzdVar = new zzd(fileChannel, 0L, j);
        zzd zzdVar2 = new zzd(fileChannel, j2, j3 - j2);
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN);
        zzj.zzd(byteBufferDuplicate, j);
        zzb zzbVar = new zzb(byteBufferDuplicate);
        int size = map.size();
        int[] iArr = new int[size];
        Iterator it = map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((Integer) it.next()).intValue();
            i++;
        }
        try {
            byte[][] bArrZzk = zzk(iArr, new zzc[]{zzdVar, zzdVar2, zzbVar});
            for (int i2 = 0; i2 < size; i2++) {
                int i3 = iArr[i2];
                if (!MessageDigest.isEqual((byte[]) map.get(Integer.valueOf(i3)), bArrZzk[i2])) {
                    throw new SecurityException(zzd(i3).concat(" digest of contents did not verify"));
                }
            }
        } catch (DigestException e) {
            throw new SecurityException("Failed to compute digest(s) of contents", e);
        }
    }

    private static byte[] zzi(ByteBuffer byteBuffer) throws IOException {
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IOException("Negative length");
        }
        if (i <= byteBuffer.remaining()) {
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            return bArr;
        }
        int iRemaining = byteBuffer.remaining();
        StringBuilder sb = new StringBuilder(90);
        sb.append("Underflow while reading length-prefixed value. Length: ");
        sb.append(i);
        sb.append(", available: ");
        sb.append(iRemaining);
        throw new IOException(sb.toString());
    }

    private static X509Certificate[] zzj(ByteBuffer byteBuffer, Map map, CertificateFactory certificateFactory) throws IOException, SecurityException {
        String str;
        Pair pairCreate;
        ByteBuffer byteBufferZzf = zzf(byteBuffer);
        ByteBuffer byteBufferZzf2 = zzf(byteBuffer);
        byte[] bArrZzi = zzi(byteBuffer);
        ArrayList arrayList = new ArrayList();
        byte[] bArrZzi2 = null;
        int i = -1;
        int i2 = 0;
        byte[] bArrZzi3 = null;
        while (byteBufferZzf2.hasRemaining()) {
            i2++;
            try {
                ByteBuffer byteBufferZzf3 = zzf(byteBufferZzf2);
                if (byteBufferZzf3.remaining() < 8) {
                    throw new SecurityException("Signature record too short");
                }
                int i3 = byteBufferZzf3.getInt();
                arrayList.add(Integer.valueOf(i3));
                if (i3 != 513 && i3 != 514 && i3 != 769) {
                    switch (i3) {
                        case 257:
                        case 258:
                        case 259:
                        case 260:
                            break;
                        default:
                            continue;
                    }
                }
                if (i != -1) {
                    int iZzc = zzc(i3);
                    int iZzc2 = zzc(i);
                    if (iZzc != 1 && iZzc2 == 1) {
                    }
                }
                bArrZzi3 = zzi(byteBufferZzf3);
                i = i3;
            } catch (IOException | BufferUnderflowException e) {
                throw new SecurityException(Insets$$ExternalSyntheticOutline0.m(45, "Failed to parse signature record #", i2), e);
            }
        }
        if (i == -1) {
            if (i2 == 0) {
                throw new SecurityException("No signatures found");
            }
            throw new SecurityException("No supported signatures found");
        }
        if (i == 513 || i == 514) {
            str = "EC";
        } else if (i != 769) {
            switch (i) {
                case 257:
                case 258:
                case 259:
                case 260:
                    str = "RSA";
                    break;
                default:
                    String strValueOf = String.valueOf(Long.toHexString(i));
                    throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unknown signature algorithm: 0x".concat(strValueOf) : new String("Unknown signature algorithm: 0x"));
            }
        } else {
            str = "DSA";
        }
        if (i == 513) {
            pairCreate = Pair.create("SHA256withECDSA", null);
        } else if (i == 514) {
            pairCreate = Pair.create("SHA512withECDSA", null);
        } else if (i != 769) {
            switch (i) {
                case 257:
                    pairCreate = Pair.create("SHA256withRSA/PSS", new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                    break;
                case 258:
                    pairCreate = Pair.create("SHA512withRSA/PSS", new PSSParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, 64, 1));
                    break;
                case 259:
                    pairCreate = Pair.create("SHA256withRSA", null);
                    break;
                case 260:
                    pairCreate = Pair.create("SHA512withRSA", null);
                    break;
                default:
                    String strValueOf2 = String.valueOf(Long.toHexString(i));
                    throw new IllegalArgumentException(strValueOf2.length() != 0 ? "Unknown signature algorithm: 0x".concat(strValueOf2) : new String("Unknown signature algorithm: 0x"));
            }
        } else {
            pairCreate = Pair.create("SHA256withDSA", null);
        }
        String str2 = (String) pairCreate.first;
        AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) pairCreate.second;
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(bArrZzi));
            Signature signature = Signature.getInstance(str2);
            signature.initVerify(publicKeyGeneratePublic);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(byteBufferZzf);
            if (!signature.verify(bArrZzi3)) {
                throw new SecurityException(String.valueOf(str2).concat(" signature did not verify"));
            }
            byteBufferZzf.clear();
            ByteBuffer byteBufferZzf4 = zzf(byteBufferZzf);
            ArrayList arrayList2 = new ArrayList();
            int i4 = 0;
            while (byteBufferZzf4.hasRemaining()) {
                i4++;
                try {
                    ByteBuffer byteBufferZzf5 = zzf(byteBufferZzf4);
                    if (byteBufferZzf5.remaining() < 8) {
                        throw new IOException("Record too short");
                    }
                    int i5 = byteBufferZzf5.getInt();
                    arrayList2.add(Integer.valueOf(i5));
                    if (i5 == i) {
                        bArrZzi2 = zzi(byteBufferZzf5);
                    }
                } catch (IOException | BufferUnderflowException e2) {
                    throw new IOException(Insets$$ExternalSyntheticOutline0.m(42, "Failed to parse digest record #", i4), e2);
                }
            }
            if (!arrayList.equals(arrayList2)) {
                throw new SecurityException("Signature algorithms don't match between digests and signatures records");
            }
            int iZzc3 = zzc(i);
            byte[] bArr = (byte[]) map.put(Integer.valueOf(iZzc3), bArrZzi2);
            if (bArr != null && !MessageDigest.isEqual(bArr, bArrZzi2)) {
                throw new SecurityException(zzd(iZzc3).concat(" contents digest does not match the digest specified by a preceding signer"));
            }
            ByteBuffer byteBufferZzf6 = zzf(byteBufferZzf);
            ArrayList arrayList3 = new ArrayList();
            int i6 = 0;
            while (byteBufferZzf6.hasRemaining()) {
                i6++;
                byte[] bArrZzi4 = zzi(byteBufferZzf6);
                try {
                    arrayList3.add(new zzg((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(bArrZzi4)), bArrZzi4));
                } catch (CertificateException e3) {
                    throw new SecurityException(Insets$$ExternalSyntheticOutline0.m(41, "Failed to decode certificate #", i6), e3);
                }
            }
            if (arrayList3.isEmpty()) {
                throw new SecurityException("No certificates listed");
            }
            if (Arrays.equals(bArrZzi, ((X509Certificate) arrayList3.get(0)).getPublicKey().getEncoded())) {
                return (X509Certificate[]) arrayList3.toArray(new X509Certificate[arrayList3.size()]);
            }
            throw new SecurityException("Public key mismatch between certificate and signature record");
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e4) {
            throw new SecurityException(Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str2).length() + 27), "Failed to verify ", str2, " signature"), e4);
        }
    }

    private static byte[][] zzk(int[] iArr, zzc[] zzcVarArr) throws DigestException {
        long j;
        int i;
        int length;
        long j2 = 0;
        int i2 = 0;
        long jZza = 0;
        int i3 = 0;
        while (true) {
            j = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            if (i3 >= 3) {
                break;
            }
            jZza += (zzcVarArr[i3].zza() + 1048575) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            i3++;
        }
        if (jZza >= 2097151) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Too many chunks: ");
            sb.append(jZza);
            throw new DigestException(sb.toString());
        }
        int i4 = (int) jZza;
        byte[][] bArr = new byte[iArr.length][];
        int i5 = 0;
        while (true) {
            length = iArr.length;
            if (i5 >= length) {
                break;
            }
            byte[] bArr2 = new byte[(zzb(iArr[i5]) * i4) + 5];
            bArr2[0] = 90;
            zzg(i4, bArr2, 1);
            bArr[i5] = bArr2;
            i5++;
        }
        byte[] bArr3 = new byte[5];
        bArr3[0] = -91;
        MessageDigest[] messageDigestArr = new MessageDigest[length];
        for (int i6 = 0; i6 < iArr.length; i6++) {
            String strZzd = zzd(iArr[i6]);
            try {
                messageDigestArr[i6] = MessageDigest.getInstance(strZzd);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(strZzd.concat(" digest not supported"), e);
            }
        }
        int i7 = 0;
        int i8 = 0;
        for (i = 3; i7 < i; i = 3) {
            zzc zzcVar = zzcVarArr[i7];
            long j3 = j2;
            long jZza2 = zzcVar.zza();
            while (jZza2 > j2) {
                int iMin = (int) Math.min(jZza2, j);
                zzg(iMin, bArr3, 1);
                for (int i9 = 0; i9 < length; i9++) {
                    messageDigestArr[i9].update(bArr3);
                }
                long j4 = j3;
                try {
                    zzcVar.zzb(messageDigestArr, j4, iMin);
                    byte[] bArr4 = bArr3;
                    int i10 = 0;
                    while (i10 < iArr.length) {
                        int i11 = iArr[i10];
                        zzc zzcVar2 = zzcVar;
                        byte[] bArr5 = bArr[i10];
                        int iZzb = zzb(i11);
                        int i12 = length;
                        MessageDigest messageDigest = messageDigestArr[i10];
                        MessageDigest[] messageDigestArr2 = messageDigestArr;
                        int iDigest = messageDigest.digest(bArr5, (i8 * iZzb) + 5, iZzb);
                        if (iDigest != iZzb) {
                            String algorithm = messageDigest.getAlgorithm();
                            StringBuilder sb2 = new StringBuilder(String.valueOf(algorithm).length() + 46);
                            sb2.append("Unexpected output size of ");
                            sb2.append(algorithm);
                            sb2.append(" digest: ");
                            sb2.append(iDigest);
                            throw new RuntimeException(sb2.toString());
                        }
                        i10++;
                        zzcVar = zzcVar2;
                        length = i12;
                        messageDigestArr = messageDigestArr2;
                    }
                    MessageDigest[] messageDigestArr3 = messageDigestArr;
                    long j5 = iMin;
                    long j6 = j4 + j5;
                    jZza2 -= j5;
                    i8++;
                    bArr3 = bArr4;
                    j2 = 0;
                    j3 = j6;
                    messageDigestArr = messageDigestArr3;
                    j = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
                } catch (IOException e2) {
                    StringBuilder sb3 = new StringBuilder(59);
                    sb3.append("Failed to digest chunk #");
                    sb3.append(i8);
                    sb3.append(" of section #");
                    sb3.append(i2);
                    throw new DigestException(sb3.toString(), e2);
                }
            }
            i2++;
            i7++;
            j2 = 0;
            j = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
        }
        byte[][] bArr6 = new byte[iArr.length][];
        for (int i13 = 0; i13 < iArr.length; i13++) {
            int i14 = iArr[i13];
            byte[] bArr7 = bArr[i13];
            String strZzd2 = zzd(i14);
            try {
                bArr6[i13] = MessageDigest.getInstance(strZzd2).digest(bArr7);
            } catch (NoSuchAlgorithmException e3) {
                throw new RuntimeException(strZzd2.concat(" digest not supported"), e3);
            }
        }
        return bArr6;
    }

    private static X509Certificate[][] zzl(FileChannel fileChannel, zze zzeVar) throws SecurityException {
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try {
                ByteBuffer byteBufferZzf = zzf(zzeVar.zza);
                int i = 0;
                while (byteBufferZzf.hasRemaining()) {
                    i++;
                    try {
                        arrayList.add(zzj(zzf(byteBufferZzf), map, certificateFactory));
                    } catch (IOException | SecurityException | BufferUnderflowException e) {
                        StringBuilder sb = new StringBuilder(48);
                        sb.append("Failed to parse/verify signer #");
                        sb.append(i);
                        sb.append(" block");
                        throw new SecurityException(sb.toString(), e);
                    }
                }
                if (i <= 0) {
                    throw new SecurityException("No signers found");
                }
                if (map.isEmpty()) {
                    throw new SecurityException("No content digests found");
                }
                zzh(map, fileChannel, zzeVar.zzb, zzeVar.zzc, zzeVar.zzd, zzeVar.zze);
                return (X509Certificate[][]) arrayList.toArray(new X509Certificate[arrayList.size()][]);
            } catch (IOException e2) {
                throw new SecurityException("Failed to read list of signers", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }
}
