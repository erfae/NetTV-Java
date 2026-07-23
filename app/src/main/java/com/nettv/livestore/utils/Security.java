package com.nettv.livestore.utils;

import android.text.TextUtils;
import android.util.Base64;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class Security {
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String TAG = "IABUtil/Security";

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(bArr2);
    }

    public static PublicKey generatePublicKey(String str) throws IOException {
        try {
            return KeyFactory.getInstance(KEY_FACTORY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e2) {
            throw new IOException("Invalid key specification: " + e2);
        }
    }

    public static String getAddData(String str, String str2, String str3, String str4, String str5) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mac_address", str);
            jSONObject.put("playlist_id", str2);
            jSONObject.put("playlist_name", str3);
            jSONObject.put("playlist_url", str4);
            jSONObject.put("playlist_type", str5);
            jSONObject.put("app_type", "android");
        } catch (Exception unused) {
        }
        String strTrim = new String(Base64.encode(jSONObject.toString().getBytes(StandardCharsets.UTF_8), 0)).trim();
        int iNextInt = new Random().nextInt(20);
        int iNextInt2 = new Random().nextInt(strTrim.length());
        if (iNextInt2 > 42) {
            iNextInt2 = 42;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(strTrim.substring(0, iNextInt2) + getEncryptKey(iNextInt) + strTrim.substring(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt));
        return sbM.toString();
    }

    public static String getDecodedString(String str) {
        int encryptKeyPosition = getEncryptKeyPosition(str.substring(str.length() - 2, str.length() - 1));
        int encryptKeyPosition2 = getEncryptKeyPosition(str.substring(str.length() - 1));
        String strSubstring = str.substring(0, str.length() - 2);
        return new String(Base64.decode(strSubstring.substring(0, encryptKeyPosition) + strSubstring.substring(encryptKeyPosition + encryptKeyPosition2), 0), StandardCharsets.UTF_8).trim();
    }

    public static String getDeleteData(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mac_address", str);
            jSONObject.put("playlist_id", str2);
            jSONObject.put("app_type", "android");
        } catch (Exception unused) {
        }
        String strTrim = new String(Base64.encode(jSONObject.toString().getBytes(StandardCharsets.UTF_8), 0)).trim();
        int iNextInt = new Random().nextInt(20);
        int iNextInt2 = new Random().nextInt(strTrim.length());
        if (iNextInt2 > 42) {
            iNextInt2 = 42;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(strTrim.substring(0, iNextInt2) + getEncryptKey(iNextInt) + strTrim.substring(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt));
        return sbM.toString();
    }

    private static String getEncryptKey(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(i);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(62)));
        }
        return sb.toString();
    }

    private static int getEncryptKeyPosition(String str) {
        return ALLOWED_CHARACTERS.indexOf(str);
    }

    private static String getEncryptPositionString(int i) {
        return String.valueOf(ALLOWED_CHARACTERS.charAt(i));
    }

    public static JSONObject getFileData(int i) {
        HashMap map = new HashMap();
        map.put(FontsContractCompat.Columns.FILE_ID, Integer.valueOf(i));
        return new JSONObject(map);
    }

    public static JSONObject getJsonData(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("data", str);
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public static String getParentData(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mac_address", str);
            jSONObject.put("parent_control", str2);
        } catch (Exception unused) {
        }
        String strTrim = new String(Base64.encode(jSONObject.toString().getBytes(StandardCharsets.UTF_8), 0)).trim();
        int iNextInt = new Random().nextInt(20);
        int iNextInt2 = new Random().nextInt(strTrim.length());
        if (iNextInt2 > 42) {
            iNextInt2 = 42;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(strTrim.substring(0, iNextInt2) + getEncryptKey(iNextInt) + strTrim.substring(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt));
        return sbM.toString();
    }

    public static String getStringData(String str, String str2, boolean z, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("app_device_id", str);
            jSONObject.put("app_type", str3);
            jSONObject.put("version", str2);
            jSONObject.put("is_paid", false);
        } catch (Exception unused) {
        }
        String strTrim = new String(Base64.encode(jSONObject.toString().getBytes(StandardCharsets.UTF_8), 0)).trim();
        int iNextInt = new Random().nextInt(20);
        int iNextInt2 = new Random().nextInt(strTrim.length());
        if (iNextInt2 > 42) {
            iNextInt2 = 42;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(strTrim.substring(0, iNextInt2) + getEncryptKey(iNextInt) + strTrim.substring(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt2));
        sbM.append(getEncryptPositionString(iNextInt));
        return sbM.toString();
    }

    public static JSONObject getUserObject(String str, String str2) {
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("password", str2);
        return new JSONObject(map);
    }

    public static boolean verify(PublicKey publicKey, String str, String str2) {
        try {
            byte[] bArrDecode = Base64.decode(str2, 0);
            try {
                Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
                signature.initVerify(publicKey);
                signature.update(str.getBytes());
                return signature.verify(bArrDecode);
            } catch (InvalidKeyException | SignatureException unused) {
                return false;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } catch (IllegalArgumentException unused2) {
            return false;
        }
    }

    public static boolean verifyPurchase(String str, String str2, String str3) throws IOException {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            return false;
        }
        return verify(generatePublicKey(str), str2, str3);
    }
}
