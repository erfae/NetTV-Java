package io.realm.internal.android;

/* JADX INFO: loaded from: classes2.dex */
public class TypeUtils {
    public static byte[] convertNonPrimitiveBinaryToPrimitive(Byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] == null) {
                throw new IllegalArgumentException("Byte arrays cannot contain null values.");
            }
            bArr2[i] = bArr[i].byteValue();
        }
        return bArr2;
    }

    public static Byte[] convertPrimitiveBinaryToNonPrimitive(byte[] bArr) {
        Byte[] bArr2 = new Byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = Byte.valueOf(bArr[i]);
        }
        return bArr2;
    }
}
