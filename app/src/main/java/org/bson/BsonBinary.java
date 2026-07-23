package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.base.Ascii;
import java.util.Arrays;
import java.util.UUID;
import org.bson.assertions.Assertions;
import org.bson.internal.UuidHelper;

/* JADX INFO: loaded from: classes2.dex */
public class BsonBinary extends BsonValue {
    private final byte[] data;
    private final byte type;

    public BsonBinary(byte[] bArr) {
        this(BsonBinarySubType.BINARY, bArr);
    }

    public static BsonBinary clone(BsonBinary bsonBinary) {
        return new BsonBinary(bsonBinary.type, (byte[]) bsonBinary.data.clone());
    }

    public UUID asUuid() {
        if (!BsonBinarySubType.isUuid(this.type)) {
            throw new BsonInvalidOperationException("type must be a UUID subtype.");
        }
        if (this.type == BsonBinarySubType.UUID_STANDARD.getValue()) {
            return UuidHelper.decodeBinaryToUuid((byte[]) this.data.clone(), this.type, UuidRepresentation.STANDARD);
        }
        throw new BsonInvalidOperationException("uuidRepresentation must be set to return the correct UUID.");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonBinary bsonBinary = (BsonBinary) obj;
        return Arrays.equals(this.data, bsonBinary.data) && this.type == bsonBinary.type;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.BINARY;
    }

    public byte[] getData() {
        return this.data;
    }

    public byte getType() {
        return this.type;
    }

    public int hashCode() {
        return Arrays.hashCode(this.data) + (this.type * Ascii.US);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonBinary{type=");
        sbM.append((int) this.type);
        sbM.append(", data=");
        sbM.append(Arrays.toString(this.data));
        sbM.append('}');
        return sbM.toString();
    }

    public BsonBinary(BsonBinarySubType bsonBinarySubType, byte[] bArr) {
        if (bsonBinarySubType == null) {
            throw new IllegalArgumentException("type may not be null");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("data may not be null");
        }
        this.type = bsonBinarySubType.getValue();
        this.data = bArr;
    }

    public UUID asUuid(UuidRepresentation uuidRepresentation) {
        byte value;
        Assertions.notNull("uuidRepresentation", uuidRepresentation);
        if (uuidRepresentation == UuidRepresentation.STANDARD) {
            value = BsonBinarySubType.UUID_STANDARD.getValue();
        } else {
            value = BsonBinarySubType.UUID_LEGACY.getValue();
        }
        if (this.type == value) {
            return UuidHelper.decodeBinaryToUuid((byte[]) this.data.clone(), this.type, uuidRepresentation);
        }
        throw new BsonInvalidOperationException("uuidRepresentation does not match current uuidRepresentation.");
    }

    public BsonBinary(byte b, byte[] bArr) {
        if (bArr != null) {
            this.type = b;
            this.data = bArr;
            return;
        }
        throw new IllegalArgumentException("data may not be null");
    }

    public BsonBinary(UUID uuid) {
        this(uuid, UuidRepresentation.STANDARD);
    }

    public BsonBinary(UUID uuid, UuidRepresentation uuidRepresentation) {
        byte value;
        if (uuid == null) {
            throw new IllegalArgumentException("uuid may not be null");
        }
        if (uuidRepresentation != null) {
            this.data = UuidHelper.encodeUuidToBinary(uuid, uuidRepresentation);
            if (uuidRepresentation == UuidRepresentation.STANDARD) {
                value = BsonBinarySubType.UUID_STANDARD.getValue();
            } else {
                value = BsonBinarySubType.UUID_LEGACY.getValue();
            }
            this.type = value;
            return;
        }
        throw new IllegalArgumentException("uuidRepresentation may not be null");
    }
}
