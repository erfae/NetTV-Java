package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class RealmAny {

    @Nonnull
    private final RealmAnyOperator operator;

    public enum Type {
        INTEGER(RealmFieldType.INTEGER, Long.class),
        BOOLEAN(RealmFieldType.BOOLEAN, Boolean.class),
        STRING(RealmFieldType.STRING, String.class),
        BINARY(RealmFieldType.BINARY, Byte[].class),
        DATE(RealmFieldType.DATE, Date.class),
        FLOAT(RealmFieldType.FLOAT, Float.class),
        DOUBLE(RealmFieldType.DOUBLE, Double.class),
        DECIMAL128(RealmFieldType.DECIMAL128, Decimal128.class),
        OBJECT_ID(RealmFieldType.OBJECT_ID, ObjectId.class),
        OBJECT(RealmFieldType.TYPED_LINK, RealmModel.class),
        UUID(RealmFieldType.UUID, UUID.class),
        NULL(null, null);

        private static final Type[] realmFieldToRealmAnyTypeMap = new Type[19];
        private final Class<?> clazz;
        private final RealmFieldType realmFieldType;

        static {
            for (Type type : values()) {
                if (type != NULL) {
                    realmFieldToRealmAnyTypeMap[type.realmFieldType.getNativeValue()] = type;
                }
            }
            realmFieldToRealmAnyTypeMap[RealmFieldType.OBJECT.getNativeValue()] = OBJECT;
        }

        Type(RealmFieldType realmFieldType, Class cls) {
            this.realmFieldType = realmFieldType;
            this.clazz = cls;
        }

        public static Type fromNativeValue(int i) {
            return i == -1 ? NULL : realmFieldToRealmAnyTypeMap[i];
        }

        public Class<?> getTypedClass() {
            return this.clazz;
        }
    }

    public RealmAny(@Nonnull RealmAnyOperator realmAnyOperator) {
        this.operator = realmAnyOperator;
    }

    public static RealmAny nullValue() {
        return new RealmAny(new NullRealmAnyOperator());
    }

    public static RealmAny valueOf(@Nullable Byte b) {
        return new RealmAny(b == null ? new NullRealmAnyOperator() : new IntegerRealmAnyOperator(b));
    }

    public byte[] asBinary() {
        return (byte[]) this.operator.getValue(byte[].class);
    }

    public Boolean asBoolean() {
        return (Boolean) this.operator.getValue(Boolean.class);
    }

    public Byte asByte() {
        Number number = (Number) this.operator.getValue(Number.class);
        if (number == null) {
            return null;
        }
        return Byte.valueOf(number.byteValue());
    }

    public Date asDate() {
        return (Date) this.operator.getValue(Date.class);
    }

    public Decimal128 asDecimal128() {
        return (Decimal128) this.operator.getValue(Decimal128.class);
    }

    public Double asDouble() {
        return (Double) this.operator.getValue(Double.class);
    }

    public Float asFloat() {
        return (Float) this.operator.getValue(Float.class);
    }

    public Integer asInteger() {
        Number number = (Number) this.operator.getValue(Number.class);
        if (number == null) {
            return null;
        }
        return Integer.valueOf(number.intValue());
    }

    public Long asLong() {
        Number number = (Number) this.operator.getValue(Number.class);
        if (number == null) {
            return null;
        }
        return Long.valueOf(number.longValue());
    }

    public ObjectId asObjectId() {
        return (ObjectId) this.operator.getValue(ObjectId.class);
    }

    public <T extends RealmModel> T asRealmModel(Class<T> cls) {
        return (T) this.operator.getValue(cls);
    }

    public Short asShort() {
        Number number = (Number) this.operator.getValue(Number.class);
        if (number == null) {
            return null;
        }
        return Short.valueOf(number.shortValue());
    }

    public String asString() {
        return (String) this.operator.getValue(String.class);
    }

    public UUID asUUID() {
        return (UUID) this.operator.getValue(UUID.class);
    }

    public final void checkValidObject(BaseRealm baseRealm) {
        this.operator.checkValidObject(baseRealm);
    }

    public final boolean coercedEquals(@Nullable RealmAny realmAny) {
        if (realmAny == null) {
            return false;
        }
        return this.operator.coercedEquals(realmAny.operator);
    }

    @SuppressFBWarnings({"NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION"})
    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RealmAny) {
            return this.operator.equals(((RealmAny) obj).operator);
        }
        return false;
    }

    public final long getNativePtr() {
        return this.operator.getNativePtr();
    }

    public Type getType() {
        return this.operator.getType();
    }

    @Nullable
    public Class<?> getValueClass() {
        return this.operator.getTypedClass();
    }

    public final int hashCode() {
        return this.operator.hashCode();
    }

    public boolean isNull() {
        return getType() == Type.NULL;
    }

    public String toString() {
        return this.operator.toString();
    }

    public static RealmAny valueOf(@Nullable Short sh) {
        return new RealmAny(sh == null ? new NullRealmAnyOperator() : new IntegerRealmAnyOperator(sh));
    }

    public static RealmAny valueOf(@Nullable Integer num) {
        return new RealmAny(num == null ? new NullRealmAnyOperator() : new IntegerRealmAnyOperator(num));
    }

    public static RealmAny valueOf(@Nullable Long l) {
        return new RealmAny(l == null ? new NullRealmAnyOperator() : new IntegerRealmAnyOperator(l));
    }

    public static RealmAny valueOf(@Nullable Boolean bool) {
        return new RealmAny(bool == null ? new NullRealmAnyOperator() : new BooleanRealmAnyOperator(bool));
    }

    public static RealmAny valueOf(@Nullable Float f) {
        return new RealmAny(f == null ? new NullRealmAnyOperator() : new FloatRealmAnyOperator(f));
    }

    public static RealmAny valueOf(@Nullable Double d) {
        return new RealmAny(d == null ? new NullRealmAnyOperator() : new DoubleRealmAnyOperator(d));
    }

    public static RealmAny valueOf(@Nullable String str) {
        return new RealmAny(str == null ? new NullRealmAnyOperator() : new StringRealmAnyOperator(str));
    }

    public static RealmAny valueOf(@Nullable byte[] bArr) {
        return new RealmAny(bArr == null ? new NullRealmAnyOperator() : new BinaryRealmAnyOperator(bArr));
    }

    public static RealmAny valueOf(@Nullable Date date) {
        return new RealmAny(date == null ? new NullRealmAnyOperator() : new DateRealmAnyOperator(date));
    }

    public static RealmAny valueOf(@Nullable ObjectId objectId) {
        return new RealmAny(objectId == null ? new NullRealmAnyOperator() : new ObjectIdRealmAnyOperator(objectId));
    }

    public static RealmAny valueOf(@Nullable Decimal128 decimal128) {
        return new RealmAny(decimal128 == null ? new NullRealmAnyOperator() : new Decimal128RealmAnyOperator(decimal128));
    }

    public static RealmAny valueOf(@Nullable UUID uuid) {
        return new RealmAny(uuid == null ? new NullRealmAnyOperator() : new UUIDRealmAnyOperator(uuid));
    }

    public static RealmAny valueOf(@Nullable RealmModel realmModel) {
        return new RealmAny(realmModel == null ? new NullRealmAnyOperator() : new RealmModelOperator(realmModel));
    }

    public static RealmAny valueOf(@Nullable Object obj) {
        if (obj == null) {
            return nullValue();
        }
        if (obj instanceof Boolean) {
            return valueOf((Boolean) obj);
        }
        if (obj instanceof Byte) {
            return valueOf((Byte) obj);
        }
        if (obj instanceof Short) {
            return valueOf((Short) obj);
        }
        if (obj instanceof Integer) {
            return valueOf((Integer) obj);
        }
        if (obj instanceof Long) {
            return valueOf((Long) obj);
        }
        if (obj instanceof Float) {
            return valueOf((Float) obj);
        }
        if (obj instanceof Double) {
            return valueOf((Double) obj);
        }
        if (obj instanceof Decimal128) {
            return valueOf((Decimal128) obj);
        }
        if (obj instanceof String) {
            return valueOf((String) obj);
        }
        if (obj instanceof byte[]) {
            return valueOf((byte[]) obj);
        }
        if (obj instanceof Date) {
            return valueOf((Date) obj);
        }
        if (obj instanceof ObjectId) {
            return valueOf((ObjectId) obj);
        }
        if (obj instanceof UUID) {
            return valueOf((UUID) obj);
        }
        if (obj instanceof RealmAny) {
            return (RealmAny) obj;
        }
        if (RealmModel.class.isAssignableFrom(obj.getClass())) {
            RealmModel realmModel = (RealmModel) obj;
            if (RealmObject.isValid(realmModel) && RealmObject.isManaged(realmModel)) {
                return valueOf(realmModel);
            }
            throw new IllegalArgumentException("RealmObject is not a valid managed object.");
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Type not supported on RealmAny: ");
        sbM.append(obj.getClass().getSimpleName());
        throw new IllegalArgumentException(sbM.toString());
    }
}
