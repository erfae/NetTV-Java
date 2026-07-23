package org.androidannotations.api.builder;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import java.io.Serializable;
import java.util.ArrayList;
import org.androidannotations.api.builder.FragmentBuilder;

/* JADX INFO: loaded from: classes2.dex */
public abstract class FragmentBuilder<I extends FragmentBuilder<I, F>, F> extends Builder {
    public Bundle args = new Bundle();

    public I arg(Bundle bundle) {
        this.args.putAll(bundle);
        return this;
    }

    public Bundle args() {
        return this.args;
    }

    public abstract F build();

    public I integerArrayListArg(String str, ArrayList<Integer> arrayList) {
        this.args.putIntegerArrayList(str, arrayList);
        return this;
    }

    public I parcelableArrayListArg(String str, ArrayList<? extends Parcelable> arrayList) {
        this.args.putParcelableArrayList(str, arrayList);
        return this;
    }

    public I stringArrayListArg(String str, ArrayList<String> arrayList) {
        this.args.putStringArrayList(str, arrayList);
        return this;
    }

    public I arg(String str, boolean z) {
        this.args.putBoolean(str, z);
        return this;
    }

    public I arg(String str, byte b) {
        this.args.putByte(str, b);
        return this;
    }

    public I arg(String str, char c) {
        this.args.putChar(str, c);
        return this;
    }

    public I arg(String str, short s) {
        this.args.putShort(str, s);
        return this;
    }

    public I arg(String str, int i) {
        this.args.putInt(str, i);
        return this;
    }

    public I arg(String str, long j) {
        this.args.putLong(str, j);
        return this;
    }

    public I arg(String str, float f) {
        this.args.putFloat(str, f);
        return this;
    }

    public I arg(String str, double d) {
        this.args.putDouble(str, d);
        return this;
    }

    public I arg(String str, String str2) {
        this.args.putString(str, str2);
        return this;
    }

    public I arg(String str, CharSequence charSequence) {
        this.args.putCharSequence(str, charSequence);
        return this;
    }

    public I arg(String str, Parcelable parcelable) {
        this.args.putParcelable(str, parcelable);
        return this;
    }

    public I arg(String str, Parcelable[] parcelableArr) {
        this.args.putParcelableArray(str, parcelableArr);
        return this;
    }

    public I arg(String str, SparseArray<? extends Parcelable> sparseArray) {
        this.args.putSparseParcelableArray(str, sparseArray);
        return this;
    }

    public I arg(String str, Serializable serializable) {
        this.args.putSerializable(str, serializable);
        return this;
    }

    public I arg(String str, boolean[] zArr) {
        this.args.putBooleanArray(str, zArr);
        return this;
    }

    public I arg(String str, byte[] bArr) {
        this.args.putByteArray(str, bArr);
        return this;
    }

    public I arg(String str, short[] sArr) {
        this.args.putShortArray(str, sArr);
        return this;
    }

    public I arg(String str, char[] cArr) {
        this.args.putCharArray(str, cArr);
        return this;
    }

    public I arg(String str, int[] iArr) {
        this.args.putIntArray(str, iArr);
        return this;
    }

    public I arg(String str, long[] jArr) {
        this.args.putLongArray(str, jArr);
        return this;
    }

    public I arg(String str, float[] fArr) {
        this.args.putFloatArray(str, fArr);
        return this;
    }

    public I arg(String str, double[] dArr) {
        this.args.putDoubleArray(str, dArr);
        return this;
    }

    public I arg(String str, String[] strArr) {
        this.args.putStringArray(str, strArr);
        return this;
    }

    public I arg(String str, Bundle bundle) {
        this.args.putBundle(str, bundle);
        return this;
    }
}
