package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.core.NativeRealmAny;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class UncheckedRow implements NativeObject, Row {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    public final NativeContext context;
    private final long nativePtr;
    public final Table parent;

    /* JADX INFO: renamed from: io.realm.internal.UncheckedRow$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$io$realm$RealmFieldType;

        static {
            int[] iArr = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr;
            try {
                iArr[RealmFieldType.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public UncheckedRow(NativeContext nativeContext, Table table, long j) {
        this.context = nativeContext;
        this.parent = table;
        this.nativePtr = j;
        nativeContext.addReference(this);
    }

    private static native long nativeGetFinalizerPtr();

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        if (!isValid()) {
            throw new IllegalStateException("Object is no longer managed by Realm. Has it been deleted?");
        }
    }

    public CheckedRow convertToChecked() {
        return CheckedRow.getFromRow(this);
    }

    @Override // io.realm.internal.Row
    public long createEmbeddedObject(long j, RealmFieldType realmFieldType) {
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()];
        if (i == 1) {
            this.parent.checkImmutable();
            return nativeCreateEmbeddedObject(this.nativePtr, j);
        }
        if (i == 2) {
            return getModelList(j).createAndAddEmbeddedObject();
        }
        throw new IllegalArgumentException("Wrong parentPropertyType, expected OBJECT or LIST but received " + realmFieldType);
    }

    public Row freeze(OsSharedRealm osSharedRealm) {
        return !isValid() ? InvalidRow.INSTANCE : new UncheckedRow(this.context, this.parent.freeze(osSharedRealm), nativeFreeze(this.nativePtr, osSharedRealm.getNativePtr()));
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        return nativeGetByteArray(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        return nativeGetBoolean(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        return nativeGetColumnCount(this.nativePtr);
    }

    @Override // io.realm.internal.Row
    public long getColumnKey(String str) {
        if (str != null) {
            return nativeGetColumnKey(this.nativePtr, str);
        }
        throw new IllegalArgumentException("Column name can not be null.");
    }

    @Override // io.realm.internal.Row
    public String[] getColumnNames() {
        return nativeGetColumnNames(this.nativePtr);
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        return new Date(nativeGetTimestamp(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public Decimal128 getDecimal128(long j) {
        long[] jArrNativeGetDecimal128 = nativeGetDecimal128(this.nativePtr, j);
        if (jArrNativeGetDecimal128 != null) {
            return Decimal128.fromIEEE754BIDEncoding(jArrNativeGetDecimal128[1], jArrNativeGetDecimal128[0]);
        }
        return null;
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        return nativeGetDouble(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        return nativeGetFloat(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        return nativeGetLink(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        return nativeGetLong(this.nativePtr, j);
    }

    public OsList getModelList(long j) {
        return new OsList(this, j);
    }

    public OsMap getModelMap(long j) {
        return new OsMap(this, j);
    }

    public OsSet getModelSet(long j) {
        return new OsSet(this, j);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.Row
    public NativeRealmAny getNativeRealmAny(long j) {
        return new NativeRealmAny(nativeGetRealmAny(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public ObjectId getObjectId(long j) {
        return new ObjectId(nativeGetObjectId(this.nativePtr, j));
    }

    @Override // io.realm.internal.Row
    public long getObjectKey() {
        return nativeGetObjectKey(this.nativePtr);
    }

    public OsMap getRealmAnyMap(long j) {
        return new OsMap(this, j);
    }

    public OsSet getRealmAnySet(long j) {
        return new OsSet(this, j);
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        return nativeGetString(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        return this.parent;
    }

    @Override // io.realm.internal.Row
    public UUID getUUID(long j) {
        return UUID.fromString(nativeGetUUID(this.nativePtr, j));
    }

    public OsList getValueList(long j, RealmFieldType realmFieldType) {
        return new OsList(this, j);
    }

    public OsMap getValueMap(long j, RealmFieldType realmFieldType) {
        return new OsMap(this, j);
    }

    public OsSet getValueSet(long j, RealmFieldType realmFieldType) {
        return new OsSet(this, j);
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        return nativeHasColumn(this.nativePtr, str);
    }

    @Override // io.realm.internal.Row
    public boolean isLoaded() {
        return true;
    }

    public boolean isNull(long j) {
        return nativeIsNull(this.nativePtr, j);
    }

    public boolean isNullLink(long j) {
        return nativeIsNullLink(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public boolean isValid() {
        long j = this.nativePtr;
        return j != 0 && nativeIsValid(j);
    }

    public native long nativeCreateEmbeddedObject(long j, long j2);

    public native long nativeFreeze(long j, long j2);

    public native boolean nativeGetBoolean(long j, long j2);

    public native byte[] nativeGetByteArray(long j, long j2);

    public native long nativeGetColumnCount(long j);

    public native long nativeGetColumnKey(long j, String str);

    public native String[] nativeGetColumnNames(long j);

    public native int nativeGetColumnType(long j, long j2);

    public native long[] nativeGetDecimal128(long j, long j2);

    public native double nativeGetDouble(long j, long j2);

    public native float nativeGetFloat(long j, long j2);

    public native long nativeGetLink(long j, long j2);

    public native long nativeGetLong(long j, long j2);

    public native String nativeGetObjectId(long j, long j2);

    public native long nativeGetObjectKey(long j);

    public native long nativeGetRealmAny(long j, long j2);

    public native String nativeGetString(long j, long j2);

    public native long nativeGetTimestamp(long j, long j2);

    public native String nativeGetUUID(long j, long j2);

    public native boolean nativeHasColumn(long j, String str);

    public native boolean nativeIsNull(long j, long j2);

    public native boolean nativeIsNullLink(long j, long j2);

    public native boolean nativeIsValid(long j);

    public native void nativeNullifyLink(long j, long j2);

    public native void nativeSetBoolean(long j, long j2, boolean z);

    public native void nativeSetByteArray(long j, long j2, @Nullable byte[] bArr);

    public native void nativeSetDecimal128(long j, long j2, long j3, long j4);

    public native void nativeSetDouble(long j, long j2, double d);

    public native void nativeSetFloat(long j, long j2, float f);

    public native void nativeSetLink(long j, long j2, long j3);

    public native void nativeSetLong(long j, long j2, long j3);

    public native void nativeSetNull(long j, long j2);

    public native void nativeSetObjectId(long j, long j2, String str);

    public native void nativeSetRealmAny(long j, long j2, long j3);

    public native void nativeSetString(long j, long j2, String str);

    public native void nativeSetTimestamp(long j, long j2, long j3);

    public native void nativeSetUUID(long j, long j2, String str);

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        this.parent.checkImmutable();
        nativeNullifyLink(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, @Nullable byte[] bArr) {
        this.parent.checkImmutable();
        nativeSetByteArray(this.nativePtr, j, bArr);
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        this.parent.checkImmutable();
        nativeSetBoolean(this.nativePtr, j, z);
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        this.parent.checkImmutable();
        if (date == null) {
            throw new IllegalArgumentException("Null Date is not allowed.");
        }
        nativeSetTimestamp(this.nativePtr, j, date.getTime());
    }

    @Override // io.realm.internal.Row
    public void setDecimal128(long j, @Nullable Decimal128 decimal128) {
        this.parent.checkImmutable();
        if (decimal128 == null) {
            nativeSetNull(this.nativePtr, j);
        } else {
            nativeSetDecimal128(this.nativePtr, j, decimal128.getLow(), decimal128.getHigh());
        }
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        this.parent.checkImmutable();
        nativeSetDouble(this.nativePtr, j, d);
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        this.parent.checkImmutable();
        nativeSetFloat(this.nativePtr, j, f);
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        this.parent.checkImmutable();
        nativeSetLink(this.nativePtr, j, j2);
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        this.parent.checkImmutable();
        nativeSetLong(this.nativePtr, j, j2);
    }

    public void setNull(long j) {
        this.parent.checkImmutable();
        nativeSetNull(this.nativePtr, j);
    }

    @Override // io.realm.internal.Row
    public void setObjectId(long j, @Nullable ObjectId objectId) {
        this.parent.checkImmutable();
        if (objectId == null) {
            nativeSetNull(this.nativePtr, j);
        } else {
            nativeSetObjectId(this.nativePtr, j, objectId.toString());
        }
    }

    @Override // io.realm.internal.Row
    public void setRealmAny(long j, long j2) {
        this.parent.checkImmutable();
        nativeSetRealmAny(this.nativePtr, j, j2);
    }

    @Override // io.realm.internal.Row
    public void setString(long j, @Nullable String str) {
        this.parent.checkImmutable();
        if (str == null) {
            nativeSetNull(this.nativePtr, j);
        } else {
            nativeSetString(this.nativePtr, j, str);
        }
    }

    @Override // io.realm.internal.Row
    public void setUUID(long j, @Nullable UUID uuid) {
        this.parent.checkImmutable();
        if (uuid == null) {
            nativeSetNull(this.nativePtr, j);
        } else {
            nativeSetUUID(this.nativePtr, j, uuid.toString());
        }
    }

    public UncheckedRow(UncheckedRow uncheckedRow) {
        this.context = uncheckedRow.context;
        this.parent = uncheckedRow.parent;
        this.nativePtr = uncheckedRow.nativePtr;
    }
}
