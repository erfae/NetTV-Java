package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@ShowFirstParty
@KeepForSdk
public abstract class FastJsonResponse {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
    @ShowFirstParty
    public interface FieldConverter<I, O> {
        int zaa();

        int zab();

        @Nullable
        O zac(@NonNull I i);

        @NonNull
        I zad(@NonNull O o);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public static final <O, I> I zaD(@NonNull Field<I, O> field, @Nullable Object obj) {
        return ((Field) field).zak != null ? field.zaf(obj) : obj;
    }

    private final <I, O> void zaE(Field<I, O> field, @Nullable I i) {
        String str = field.zae;
        O oZae = field.zae(i);
        int i2 = field.zac;
        switch (i2) {
            case 0:
                if (oZae != null) {
                    setIntegerInternal(field, ((Integer) oZae).intValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 1:
                zaf(field, (BigInteger) oZae);
                return;
            case 2:
                if (oZae != null) {
                    setLongInternal(field, ((Long) oZae).longValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 3:
            default:
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(44, "Unsupported type for conversion: ", i2));
            case 4:
                if (oZae != null) {
                    zan(field, ((Double) oZae).doubleValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 5:
                zab(field, (BigDecimal) oZae);
                return;
            case 6:
                if (oZae != null) {
                    setBooleanInternal(field, ((Boolean) oZae).booleanValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 7:
                setStringInternal(field, (String) oZae);
                return;
            case 8:
            case 9:
                if (oZae != null) {
                    setDecodedBytesInternal(field, (byte[]) oZae);
                    return;
                } else {
                    zaG(str);
                    return;
                }
        }
    }

    private static final void zaF(StringBuilder sb, Field field, Object obj) {
        int i = field.zaa;
        if (i == 11) {
            Class<? extends FastJsonResponse> cls = field.zag;
            Preconditions.checkNotNull(cls);
            sb.append(cls.cast(obj).toString());
        } else {
            if (i != 7) {
                sb.append(obj);
                return;
            }
            sb.append("\"");
            sb.append(JsonUtils.escapeString((String) obj));
            sb.append("\"");
        }
    }

    private static final <O> void zaG(String str) {
        if (Log.isLoggable("FastJsonResponse", 6)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 58);
            sb.append("Output field (");
            sb.append(str);
            sb.append(") has a null value, but expected a primitive");
            Log.e("FastJsonResponse", sb.toString());
        }
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@NonNull Field field, @NonNull String str, @Nullable ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeInternal(@NonNull Field field, @NonNull String str, @NonNull T t) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    @NonNull
    @KeepForSdk
    public abstract Map<String, Field<?, ?>> getFieldMappings();

    @Nullable
    @KeepForSdk
    public final Object getFieldValue(@NonNull Field field) {
        String str = field.zae;
        if (field.zag == null) {
            return getValueObject(str);
        }
        Preconditions.checkState(getValueObject(str) == null, "Concrete field shouldn't be value object: %s", field.zae);
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String strSubstring = str.substring(1);
            StringBuilder sb = new StringBuilder(String.valueOf(strSubstring).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(strSubstring);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @KeepForSdk
    public abstract Object getValueObject(@NonNull String str);

    @KeepForSdk
    public final boolean isFieldSet(@NonNull Field field) {
        if (field.zac != 11) {
            return isPrimitiveFieldSet(field.zae);
        }
        if (field.zad) {
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    @KeepForSdk
    public abstract boolean isPrimitiveFieldSet(@NonNull String str);

    @KeepForSdk
    public void setBooleanInternal(@NonNull Field field, boolean z) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    @KeepForSdk
    public void setDecodedBytesInternal(@NonNull Field field, @Nullable byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    @KeepForSdk
    public void setIntegerInternal(@NonNull Field field, int i) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    @KeepForSdk
    public void setLongInternal(@NonNull Field field, long j) {
        throw new UnsupportedOperationException("Long not supported");
    }

    @KeepForSdk
    public void setStringInternal(@NonNull Field field, @Nullable String str) {
        throw new UnsupportedOperationException("String not supported");
    }

    @KeepForSdk
    public void setStringMapInternal(@NonNull Field field, @Nullable Map map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    @KeepForSdk
    public void setStringsInternal(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    @NonNull
    @KeepForSdk
    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String str : fieldMappings.keySet()) {
            Field<?, ?> field = fieldMappings.get(str);
            if (isFieldSet(field)) {
                Object objZaD = zaD(field, getFieldValue(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                if (objZaD != null) {
                    switch (field.zac) {
                        case 8:
                            sb.append("\"");
                            sb.append(Base64Utils.encode((byte[]) objZaD));
                            sb.append("\"");
                            break;
                        case 9:
                            sb.append("\"");
                            sb.append(Base64Utils.encodeUrlSafe((byte[]) objZaD));
                            sb.append("\"");
                            break;
                        case 10:
                            MapUtils.writeStringMapToJson(sb, (HashMap) objZaD);
                            break;
                        default:
                            if (field.zab) {
                                ArrayList arrayList = (ArrayList) objZaD;
                                sb.append("[");
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    if (i > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i);
                                    if (obj != null) {
                                        zaF(sb, field, obj);
                                    }
                                }
                                sb.append("]");
                            } else {
                                zaF(sb, field, objZaD);
                            }
                            break;
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    public final <O> void zaA(@NonNull Field<String, O> field, @Nullable String str) {
        if (((Field) field).zak != null) {
            zaE(field, str);
        } else {
            setStringInternal(field, str);
        }
    }

    public final <O> void zaB(@NonNull Field<Map<String, String>, O> field, @Nullable Map<String, String> map) {
        if (((Field) field).zak != null) {
            zaE(field, map);
        } else {
            setStringMapInternal(field, map);
        }
    }

    public final <O> void zaC(@NonNull Field<ArrayList<String>, O> field, @Nullable ArrayList<String> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            setStringsInternal(field, arrayList);
        }
    }

    public final <O> void zaa(@NonNull Field<BigDecimal, O> field, @Nullable BigDecimal bigDecimal) {
        if (((Field) field).zak != null) {
            zaE(field, bigDecimal);
        } else {
            zab(field, bigDecimal);
        }
    }

    public void zab(@NonNull Field field, @Nullable BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    public final <O> void zac(@NonNull Field<ArrayList<BigDecimal>, O> field, @Nullable ArrayList<BigDecimal> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zad(field, arrayList);
        }
    }

    public void zad(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    public final <O> void zae(@NonNull Field<BigInteger, O> field, @Nullable BigInteger bigInteger) {
        if (((Field) field).zak != null) {
            zaE(field, bigInteger);
        } else {
            zaf(field, bigInteger);
        }
    }

    public void zaf(@NonNull Field field, @Nullable BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    public final <O> void zag(@NonNull Field<ArrayList<BigInteger>, O> field, @Nullable ArrayList<BigInteger> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zah(field, arrayList);
        }
    }

    public void zah(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    public final <O> void zai(@NonNull Field<Boolean, O> field, boolean z) {
        if (((Field) field).zak != null) {
            zaE(field, Boolean.valueOf(z));
        } else {
            setBooleanInternal(field, z);
        }
    }

    public final <O> void zaj(@NonNull Field<ArrayList<Boolean>, O> field, @Nullable ArrayList<Boolean> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zak(field, arrayList);
        }
    }

    public void zak(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    public final <O> void zal(@NonNull Field<byte[], O> field, @Nullable byte[] bArr) {
        if (((Field) field).zak != null) {
            zaE(field, bArr);
        } else {
            setDecodedBytesInternal(field, bArr);
        }
    }

    public final <O> void zam(@NonNull Field<Double, O> field, double d) {
        if (((Field) field).zak != null) {
            zaE(field, Double.valueOf(d));
        } else {
            zan(field, d);
        }
    }

    public void zan(@NonNull Field field, double d) {
        throw new UnsupportedOperationException("Double not supported");
    }

    public final <O> void zao(@NonNull Field<ArrayList<Double>, O> field, @Nullable ArrayList<Double> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zap(field, arrayList);
        }
    }

    public void zap(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    public final <O> void zaq(@NonNull Field<Float, O> field, float f) {
        if (((Field) field).zak != null) {
            zaE(field, Float.valueOf(f));
        } else {
            zar(field, f);
        }
    }

    public void zar(@NonNull Field field, float f) {
        throw new UnsupportedOperationException("Float not supported");
    }

    public final <O> void zas(@NonNull Field<ArrayList<Float>, O> field, @Nullable ArrayList<Float> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zat(field, arrayList);
        }
    }

    public void zat(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    public final <O> void zau(@NonNull Field<Integer, O> field, int i) {
        if (((Field) field).zak != null) {
            zaE(field, Integer.valueOf(i));
        } else {
            setIntegerInternal(field, i);
        }
    }

    public final <O> void zav(@NonNull Field<ArrayList<Integer>, O> field, @Nullable ArrayList<Integer> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zaw(field, arrayList);
        }
    }

    public void zaw(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    public final <O> void zax(@NonNull Field<Long, O> field, long j) {
        if (((Field) field).zak != null) {
            zaE(field, Long.valueOf(j));
        } else {
            setLongInternal(field, j);
        }
    }

    public final <O> void zay(@NonNull Field<ArrayList<Long>, O> field, @Nullable ArrayList<Long> arrayList) {
        if (((Field) field).zak != null) {
            zaE(field, arrayList);
        } else {
            zaz(field, arrayList);
        }
    }

    public void zaz(@NonNull Field field, @Nullable ArrayList arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
    @VisibleForTesting
    @SafeParcelable.Class(creator = "FieldCreator")
    @ShowFirstParty
    @KeepForSdk
    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zaj CREATOR = new zaj();

        @SafeParcelable.Field(getter = "getTypeIn", id = 2)
        public final int zaa;

        @SafeParcelable.Field(getter = "isTypeInArray", id = 3)
        public final boolean zab;

        @SafeParcelable.Field(getter = "getTypeOut", id = 4)
        public final int zac;

        @SafeParcelable.Field(getter = "isTypeOutArray", id = 5)
        public final boolean zad;

        @NonNull
        @SafeParcelable.Field(getter = "getOutputFieldName", id = 6)
        public final String zae;

        @SafeParcelable.Field(getter = "getSafeParcelableFieldId", id = 7)
        public final int zaf;

        @Nullable
        public final Class<? extends FastJsonResponse> zag;

        @Nullable
        @SafeParcelable.Field(getter = "getConcreteTypeName", id = 8)
        public final String zah;

        @SafeParcelable.VersionField(getter = "getVersionCode", id = 1)
        private final int zai;
        private zan zaj;

        @Nullable
        @SafeParcelable.Field(getter = "getWrappedConverter", id = 9, type = "com.google.android.gms.common.server.converter.ConverterWrapper")
        private FieldConverter<I, O> zak;

        @SafeParcelable.Constructor
        public Field(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) boolean z, @SafeParcelable.Param(id = 4) int i3, @SafeParcelable.Param(id = 5) boolean z2, @SafeParcelable.Param(id = 6) String str, @SafeParcelable.Param(id = 7) int i4, @Nullable @SafeParcelable.Param(id = 8) String str2, @Nullable @SafeParcelable.Param(id = 9) com.google.android.gms.common.server.converter.zaa zaaVar) {
            this.zai = i;
            this.zaa = i2;
            this.zab = z;
            this.zac = i3;
            this.zad = z2;
            this.zae = str;
            this.zaf = i4;
            if (str2 == null) {
                this.zag = null;
                this.zah = null;
            } else {
                this.zag = SafeParcelResponse.class;
                this.zah = str2;
            }
            if (zaaVar == null) {
                this.zak = null;
            } else {
                this.zak = (FieldConverter<I, O>) zaaVar.zab();
            }
        }

        @NonNull
        @VisibleForTesting
        @KeepForSdk
        public static Field<byte[], byte[]> forBase64(@NonNull String str, int i) {
            return new Field<>(8, false, 8, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Boolean, Boolean> forBoolean(@NonNull String str, int i) {
            return new Field<>(6, false, 6, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(@NonNull String str, int i, @NonNull Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i, cls, null);
        }

        @NonNull
        @KeepForSdk
        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(@NonNull String str, int i, @NonNull Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i, cls, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Double, Double> forDouble(@NonNull String str, int i) {
            return new Field<>(4, false, 4, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Float, Float> forFloat(@NonNull String str, int i) {
            return new Field<>(3, false, 3, false, str, i, null, null);
        }

        @NonNull
        @VisibleForTesting
        @KeepForSdk
        public static Field<Integer, Integer> forInteger(@NonNull String str, int i) {
            return new Field<>(0, false, 0, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Long, Long> forLong(@NonNull String str, int i) {
            return new Field<>(2, false, 2, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<String, String> forString(@NonNull String str, int i) {
            return new Field<>(7, false, 7, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(@NonNull String str, int i) {
            return new Field<>(10, false, 10, false, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<ArrayList<String>, ArrayList<String>> forStrings(@NonNull String str, int i) {
            return new Field<>(7, true, 7, true, str, i, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field withConverter(@NonNull String str, int i, @NonNull FieldConverter<?, ?> fieldConverter, boolean z) {
            fieldConverter.zaa();
            fieldConverter.zab();
            return new Field(7, z, 0, false, str, i, null, fieldConverter);
        }

        @KeepForSdk
        public int getSafeParcelableFieldId() {
            return this.zaf;
        }

        @NonNull
        public final String toString() {
            Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("versionCode", Integer.valueOf(this.zai)).add("typeIn", Integer.valueOf(this.zaa)).add("typeInArray", Boolean.valueOf(this.zab)).add("typeOut", Integer.valueOf(this.zac)).add("typeOutArray", Boolean.valueOf(this.zad)).add("outputFieldName", this.zae).add("safeParcelFieldId", Integer.valueOf(this.zaf));
            String str = this.zah;
            if (str == null) {
                str = null;
            }
            Objects.ToStringHelper toStringHelperAdd2 = toStringHelperAdd.add("concreteTypeName", str);
            Class<? extends FastJsonResponse> cls = this.zag;
            if (cls != null) {
                toStringHelperAdd2.add("concreteType.class", cls.getCanonicalName());
            }
            FieldConverter<I, O> fieldConverter = this.zak;
            if (fieldConverter != null) {
                toStringHelperAdd2.add("converterName", fieldConverter.getClass().getCanonicalName());
            }
            return toStringHelperAdd2.toString();
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(@NonNull Parcel parcel, int i) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.zai);
            SafeParcelWriter.writeInt(parcel, 2, this.zaa);
            SafeParcelWriter.writeBoolean(parcel, 3, this.zab);
            SafeParcelWriter.writeInt(parcel, 4, this.zac);
            SafeParcelWriter.writeBoolean(parcel, 5, this.zad);
            SafeParcelWriter.writeString(parcel, 6, this.zae, false);
            SafeParcelWriter.writeInt(parcel, 7, getSafeParcelableFieldId());
            String str = this.zah;
            if (str == null) {
                str = null;
            }
            SafeParcelWriter.writeString(parcel, 8, str, false);
            FieldConverter<I, O> fieldConverter = this.zak;
            SafeParcelWriter.writeParcelable(parcel, 9, fieldConverter != null ? com.google.android.gms.common.server.converter.zaa.zaa(fieldConverter) : null, i, false);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        @NonNull
        public final Field<I, O> zab() {
            int i = this.zai;
            int i2 = this.zaa;
            boolean z = this.zab;
            int i3 = this.zac;
            boolean z2 = this.zad;
            String str = this.zae;
            int i4 = this.zaf;
            String str2 = this.zah;
            FieldConverter<I, O> fieldConverter = this.zak;
            return new Field<>(i, i2, z, i3, z2, str, i4, str2, fieldConverter == null ? null : com.google.android.gms.common.server.converter.zaa.zaa(fieldConverter));
        }

        @NonNull
        public final FastJsonResponse zad() throws IllegalAccessException, InstantiationException {
            Preconditions.checkNotNull(this.zag);
            Class<? extends FastJsonResponse> cls = this.zag;
            if (cls != SafeParcelResponse.class) {
                return cls.newInstance();
            }
            Preconditions.checkNotNull(this.zah);
            Preconditions.checkNotNull(this.zaj, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zaj, this.zah);
        }

        @NonNull
        public final O zae(@Nullable I i) {
            Preconditions.checkNotNull(this.zak);
            return (O) Preconditions.checkNotNull(this.zak.zac(i));
        }

        @NonNull
        public final I zaf(@NonNull O o) {
            Preconditions.checkNotNull(this.zak);
            return this.zak.zad(o);
        }

        @NonNull
        public final Map<String, Field<?, ?>> zah() {
            Preconditions.checkNotNull(this.zah);
            Preconditions.checkNotNull(this.zaj);
            return (Map) Preconditions.checkNotNull(this.zaj.zab(this.zah));
        }

        public final void zai(zan zanVar) {
            this.zaj = zanVar;
        }

        public final boolean zaj() {
            return this.zak != null;
        }

        public Field(int i, boolean z, int i2, boolean z2, @NonNull String str, int i3, @Nullable Class<? extends FastJsonResponse> cls, @Nullable FieldConverter<I, O> fieldConverter) {
            this.zai = 1;
            this.zaa = i;
            this.zab = z;
            this.zac = i2;
            this.zad = z2;
            this.zae = str;
            this.zaf = i3;
            this.zag = cls;
            if (cls == null) {
                this.zah = null;
            } else {
                this.zah = cls.getCanonicalName();
            }
            this.zak = fieldConverter;
        }
    }
}
