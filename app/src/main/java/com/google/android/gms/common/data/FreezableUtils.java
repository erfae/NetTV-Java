package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.types.BasicBSONList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public final class FreezableUtils {
    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@NonNull ArrayList<E> arrayList) {
        BasicBSONList basicBSONList = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            basicBSONList.add(arrayList.get(i).freeze());
        }
        return basicBSONList;
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(@NonNull Iterable<E> iterable) {
        BasicBSONList basicBSONList = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            basicBSONList.add(it.next().freeze());
        }
        return basicBSONList;
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@NonNull E[] eArr) {
        BasicBSONList basicBSONList = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            basicBSONList.add(e.freeze());
        }
        return basicBSONList;
    }
}
