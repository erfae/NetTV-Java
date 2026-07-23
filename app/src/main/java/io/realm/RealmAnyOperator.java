package io.realm;

import io.realm.exceptions.RealmException;
import io.realm.internal.core.NativeRealmAny;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RealmAnyOperator {

    @Nullable
    private NativeRealmAny nativeRealmAny;
    private RealmAny.Type type;

    /* JADX INFO: renamed from: io.realm.RealmAnyOperator$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$io$realm$RealmAny$Type;

        static {
            int[] iArr = new int[RealmAny.Type.values().length];
            $SwitchMap$io$realm$RealmAny$Type = iArr;
            try {
                iArr[RealmAny.Type.INTEGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.BINARY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.DATE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.DOUBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.DECIMAL128.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.OBJECT_ID.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.UUID.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.OBJECT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$realm$RealmAny$Type[RealmAny.Type.NULL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public RealmAnyOperator(RealmAny.Type type) {
        this.type = type;
    }

    public static RealmAnyOperator fromNativeRealmAny(BaseRealm baseRealm, NativeRealmAny nativeRealmAny) {
        RealmAny.Type type = nativeRealmAny.getType();
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmAny$Type[type.ordinal()]) {
            case 1:
                return new IntegerRealmAnyOperator(nativeRealmAny);
            case 2:
                return new BooleanRealmAnyOperator(nativeRealmAny);
            case 3:
                return new StringRealmAnyOperator(nativeRealmAny);
            case 4:
                return new BinaryRealmAnyOperator(nativeRealmAny);
            case 5:
                return new DateRealmAnyOperator(nativeRealmAny);
            case 6:
                return new FloatRealmAnyOperator(nativeRealmAny);
            case 7:
                return new DoubleRealmAnyOperator(nativeRealmAny);
            case 8:
                return new Decimal128RealmAnyOperator(nativeRealmAny);
            case 9:
                return new ObjectIdRealmAnyOperator(nativeRealmAny);
            case 10:
                return new UUIDRealmAnyOperator(nativeRealmAny);
            case 11:
                if (baseRealm instanceof Realm) {
                    try {
                        return new RealmModelOperator(baseRealm, nativeRealmAny, nativeRealmAny.getModelClass(baseRealm.sharedRealm, baseRealm.configuration.getSchemaMediator()));
                    } catch (RealmException unused) {
                    }
                }
                return new DynamicRealmModelRealmAnyOperator(baseRealm, nativeRealmAny);
            case 12:
                return new NullRealmAnyOperator(nativeRealmAny);
            default:
                throw new ClassCastException("Couldn't cast to " + type);
        }
    }

    private synchronized NativeRealmAny getNativeRealmAny() {
        if (this.nativeRealmAny == null) {
            this.nativeRealmAny = createNativeRealmAny();
        }
        return this.nativeRealmAny;
    }

    public void checkValidObject(BaseRealm baseRealm) {
    }

    public final boolean coercedEquals(RealmAnyOperator realmAnyOperator) {
        return getNativeRealmAny().coercedEquals(realmAnyOperator.getNativeRealmAny());
    }

    public abstract NativeRealmAny createNativeRealmAny();

    public final long getNativePtr() {
        return getNativeRealmAny().getNativePtr();
    }

    public final RealmAny.Type getType() {
        return this.type;
    }

    public Class<?> getTypedClass() {
        return this.type.getTypedClass();
    }

    public abstract <T> T getValue(Class<T> cls);

    public RealmAnyOperator(RealmAny.Type type, NativeRealmAny nativeRealmAny) {
        this.type = type;
        this.nativeRealmAny = nativeRealmAny;
    }
}
