package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import io.realm.internal.Keep;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public enum RealmFieldType {
    INTEGER(0),
    BOOLEAN(1),
    STRING(2),
    BINARY(4),
    DATE(8),
    FLOAT(9),
    DOUBLE(10),
    OBJECT(12),
    DECIMAL128(11),
    OBJECT_ID(15),
    UUID(17),
    MIXED(6),
    TYPED_LINK(16),
    LIST(13),
    LINKING_OBJECTS(14),
    INTEGER_LIST(128),
    BOOLEAN_LIST(TsExtractor.TS_STREAM_TYPE_AC3),
    STRING_LIST(TsExtractor.TS_STREAM_TYPE_HDMV_DTS),
    BINARY_LIST(132),
    DATE_LIST(136),
    FLOAT_LIST(137),
    DOUBLE_LIST(TsExtractor.TS_STREAM_TYPE_DTS),
    DECIMAL128_LIST(139),
    OBJECT_ID_LIST(143),
    UUID_LIST(145),
    MIXED_LIST(TsExtractor.TS_STREAM_TYPE_SPLICE_INFO),
    STRING_TO_INTEGER_MAP(512),
    STRING_TO_BOOLEAN_MAP(InputDeviceCompat.SOURCE_DPAD),
    STRING_TO_STRING_MAP(514),
    STRING_TO_BINARY_MAP(516),
    STRING_TO_DATE_MAP(520),
    STRING_TO_FLOAT_MAP(521),
    STRING_TO_DOUBLE_MAP(522),
    STRING_TO_DECIMAL128_MAP(523),
    STRING_TO_OBJECT_ID_MAP(527),
    STRING_TO_UUID_MAP(529),
    STRING_TO_MIXED_MAP(518),
    STRING_TO_LINK_MAP(524),
    INTEGER_SET(256),
    BOOLEAN_SET(257),
    STRING_SET(258),
    BINARY_SET(260),
    DATE_SET(264),
    FLOAT_SET(265),
    DOUBLE_SET(266),
    DECIMAL128_SET(267),
    OBJECT_ID_SET(271),
    UUID_SET(273),
    LINK_SET(268),
    MIXED_SET(262);

    private static final RealmFieldType[] basicTypes = new RealmFieldType[18];
    private static final RealmFieldType[] listTypes = new RealmFieldType[18];
    private static final RealmFieldType[] mapTypes = new RealmFieldType[18];
    private static final RealmFieldType[] setTypes = new RealmFieldType[18];
    private final int nativeValue;

    static {
        for (RealmFieldType realmFieldType : values()) {
            int i = realmFieldType.nativeValue;
            if (i < 128) {
                basicTypes[i] = realmFieldType;
            } else {
                if (i < 256) {
                    listTypes[i - 128] = realmFieldType;
                } else if (i < 512) {
                    setTypes[i + InputDeviceCompat.SOURCE_ANY] = realmFieldType;
                } else {
                    mapTypes[i - 512] = realmFieldType;
                }
            }
        }
    }

    RealmFieldType(int i) {
        this.nativeValue = i;
    }

    public static RealmFieldType fromNativeValue(int i) {
        RealmFieldType realmFieldType;
        RealmFieldType realmFieldType2;
        RealmFieldType realmFieldType3;
        RealmFieldType realmFieldType4;
        if (i >= 0) {
            RealmFieldType[] realmFieldTypeArr = basicTypes;
            if (i < realmFieldTypeArr.length && (realmFieldType4 = realmFieldTypeArr[i]) != null) {
                return realmFieldType4;
            }
        }
        if (128 <= i && i < 256) {
            int i2 = i - 128;
            RealmFieldType[] realmFieldTypeArr2 = listTypes;
            if (i2 < realmFieldTypeArr2.length && (realmFieldType3 = realmFieldTypeArr2[i2]) != null) {
                return realmFieldType3;
            }
        }
        if (256 <= i && i < 512) {
            int i3 = i + InputDeviceCompat.SOURCE_ANY;
            RealmFieldType[] realmFieldTypeArr3 = setTypes;
            if (i3 < realmFieldTypeArr3.length && (realmFieldType2 = realmFieldTypeArr3[i3]) != null) {
                return realmFieldType2;
            }
        }
        if (512 <= i) {
            int i4 = i - 512;
            RealmFieldType[] realmFieldTypeArr4 = mapTypes;
            if (i4 < realmFieldTypeArr4.length && (realmFieldType = realmFieldTypeArr4[i4]) != null) {
                return realmFieldType;
            }
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Invalid native Realm type: ", i));
    }

    public int getNativeValue() {
        return this.nativeValue;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:31:0x004f A[FALL_THROUGH, RETURN] */
    public boolean isValid(Object obj) {
        int i = this.nativeValue;
        if (i == 0) {
            return (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte);
        }
        if (i == 1) {
            return obj instanceof Boolean;
        }
        if (i == 2) {
            return obj instanceof String;
        }
        if (i == 4) {
            return (obj instanceof byte[]) || (obj instanceof ByteBuffer);
        }
        if (i != 6) {
            switch (i) {
                case 6:
                    break;
                case 17:
                    return obj instanceof UUID;
                default:
                    switch (i) {
                        case 8:
                            return obj instanceof Date;
                        case 9:
                            return obj instanceof Float;
                        case 10:
                            return obj instanceof Double;
                        case 11:
                            return obj instanceof Decimal128;
                        case 12:
                        case 13:
                        case 14:
                            break;
                        case 15:
                            return obj instanceof ObjectId;
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        default:
                                            switch (i) {
                                                default:
                                                    switch (i) {
                                                        default:
                                                            switch (i) {
                                                                default:
                                                                    switch (i) {
                                                                        case 520:
                                                                        case 521:
                                                                        case 522:
                                                                        case 523:
                                                                        case 524:
                                                                            break;
                                                                        default:
                                                                            throw new RuntimeException("Unsupported Realm type:  " + this);
                                                                    }
                                                                case 512:
                                                                case InputDeviceCompat.SOURCE_DPAD /* 513 */:
                                                                case 514:
                                                                    return false;
                                                            }
                                                        case 264:
                                                        case 265:
                                                        case 266:
                                                        case 267:
                                                        case 268:
                                                            return false;
                                                    }
                                                case 256:
                                                case 257:
                                                case 258:
                                                    return false;
                                            }
                                        case 136:
                                        case 137:
                                        case TsExtractor.TS_STREAM_TYPE_DTS /* 138 */:
                                        case 139:
                                            return false;
                                    }
                                case 128:
                                case TsExtractor.TS_STREAM_TYPE_AC3 /* 129 */:
                                case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /* 130 */:
                                    return false;
                            }
                            break;
                    }
                case 132:
                case TsExtractor.TS_STREAM_TYPE_SPLICE_INFO /* 134 */:
                case 143:
                case 145:
                case 260:
                case 262:
                case 271:
                case 273:
                case 516:
                case 518:
                case 527:
                case 529:
                    return false;
            }
        }
        return obj instanceof RealmAny;
    }
}
