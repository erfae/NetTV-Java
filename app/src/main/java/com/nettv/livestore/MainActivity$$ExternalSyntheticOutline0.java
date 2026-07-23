package com.nettv.livestore;

import android.content.Context;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MainActivity$$ExternalSyntheticOutline0 {
    public static int m(CharSequence charSequence, String str, Function1 function1, String str2) {
        Intrinsics.checkNotNullParameter(charSequence, str);
        Intrinsics.checkNotNullParameter(function1, str2);
        return charSequence.length();
    }

    public static int m(UInt uInt, int i) {
        return UInt.m430constructorimpl(uInt.getData() + i);
    }

    public static int m(IntRange intRange, int i) {
        return intRange.getEndInclusive().intValue() + i;
    }

    public static View m(ViewGroup viewGroup, int i, ViewGroup viewGroup2, boolean z) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup2, z);
    }

    public static IllegalArgumentException m(JsonReader jsonReader, String str) throws IOException {
        jsonReader.skipValue();
        return new IllegalArgumentException(str);
    }

    public static Object m(Realm realm, Class cls, String str, String str2) {
        return realm.where(cls).equalTo(str, str2).findFirst();
    }

    public static Object m(CharSequence charSequence, int i, Function1 function1) {
        return function1.invoke(Character.valueOf(charSequence.charAt(i)));
    }

    public static Object m(byte[] bArr, int i, Function1 function1) {
        return function1.invoke(UByte.m348boximpl(UByteArray.m411getw2LRezQ(bArr, i)));
    }

    public static Object m(int[] iArr, int i, Function1 function1) {
        return function1.invoke(UInt.m424boximpl(UIntArray.m489getpVg5ArA(iArr, i)));
    }

    public static Object m(long[] jArr, int i, Function1 function1) {
        return function1.invoke(ULong.m502boximpl(ULongArray.m567getsVKNKU(jArr, i)));
    }

    public static Object m(short[] sArr, int i, Function1 function1) {
        return function1.invoke(UShort.m608boximpl(UShortArray.m671getMh2AYeg(sArr, i)));
    }

    public static String m(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static String m(int i, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    public static String m(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static StringBuilder m209m(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb;
    }

    public static ArrayList m(LinkedHashMap linkedHashMap, Object obj) {
        ArrayList arrayList = new ArrayList();
        linkedHashMap.put(obj, arrayList);
        return arrayList;
    }

    public static ArrayList m(Map map, Object obj) {
        ArrayList arrayList = new ArrayList();
        map.put(obj, arrayList);
        return arrayList;
    }

    public static Iterator m(Iterable iterable, String str, Function1 function1, String str2) {
        Intrinsics.checkNotNullParameter(iterable, str);
        Intrinsics.checkNotNullParameter(function1, str2);
        return iterable.iterator();
    }

    public static Iterator m(Sequence sequence, String str, Function1 function1, String str2) {
        Intrinsics.checkNotNullParameter(sequence, str);
        Intrinsics.checkNotNullParameter(function1, str2);
        return sequence.iterator();
    }

    public static KotlinNothingValueException m() {
        FlowKt.noImpl();
        return new KotlinNothingValueException();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.collections.IntIterator] */
    public static IntIterator m(int i, int i2) {
        return new IntRange(i, i2).iterator();
    }

    public static void m(Context context, int i, TextView textView) {
        textView.setTextColor(context.getResources().getColor(i));
    }

    public static void m(FragmentTransaction fragmentTransaction, Fragment fragment, String str) {
        fragmentTransaction.remove(fragment);
        fragmentTransaction.addToBackStack(str);
        fragmentTransaction.commit();
    }

    public static void m(StringBuilder sb, String str, char c, String str2) {
        sb.append(str);
        sb.append(c);
        sb.append(str2);
    }
}
