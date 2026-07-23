package org.bson.types;

import com.google.common.base.Ascii;
import java.io.Serializable;
import java.util.Arrays;
import org.bson.BsonBinarySubType;

/* JADX INFO: loaded from: classes2.dex */
public class Binary implements Serializable {
    private static final long serialVersionUID = 7902997490338209467L;
    private final byte[] data;
    private final byte type;

    public Binary(byte[] bArr) {
        this(BsonBinarySubType.BINARY, bArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Binary binary = (Binary) obj;
        return this.type == binary.type && Arrays.equals(this.data, binary.data);
    }

    public byte[] getData() {
        return (byte[]) this.data.clone();
    }

    public byte getType() {
        return this.type;
    }

    public int hashCode() {
        return Arrays.hashCode(this.data) + (this.type * Ascii.US);
    }

    public int length() {
        return this.data.length;
    }

    public Binary(BsonBinarySubType bsonBinarySubType, byte[] bArr) {
        this(bsonBinarySubType.getValue(), bArr);
    }

    public Binary(byte b, byte[] bArr) {
        this.type = b;
        this.data = (byte[]) bArr.clone();
    }
}
