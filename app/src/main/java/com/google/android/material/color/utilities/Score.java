package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class Score {
    private static final double CUTOFF_CHROMA = 15.0d;
    private static final double CUTOFF_EXCITED_PROPORTION = 0.01d;
    private static final double CUTOFF_TONE = 10.0d;
    private static final double TARGET_CHROMA = 48.0d;
    private static final double WEIGHT_CHROMA_ABOVE = 0.3d;
    private static final double WEIGHT_CHROMA_BELOW = 0.1d;
    private static final double WEIGHT_PROPORTION = 0.7d;

    public static class ScoredComparator implements Comparator<Map.Entry<Integer, Double>> {
        @Override // java.util.Comparator
        public int compare(Map.Entry<Integer, Double> entry, Map.Entry<Integer, Double> entry2) {
            return -entry.getValue().compareTo(entry2.getValue());
        }
    }

    private Score() {
    }

    private static List<Integer> filter(Map<Integer, Double> map, Map<Integer, Cam16> map2) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Integer, Cam16> entry : map2.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            Cam16 value = entry.getValue();
            double dDoubleValue = map.get(Integer.valueOf(iIntValue)).doubleValue();
            if (value.getChroma() >= CUTOFF_CHROMA && ColorUtils.lstarFromArgb(iIntValue) >= CUTOFF_TONE && dDoubleValue >= CUTOFF_EXCITED_PROPORTION) {
                arrayList.add(Integer.valueOf(iIntValue));
            }
        }
        return arrayList;
    }

    public static List<Integer> score(Map<Integer, Integer> map) {
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        double dIntValue = 0.0d;
        while (it.hasNext()) {
            dIntValue += (double) it.next().getValue().intValue();
        }
        HashMap map2 = new HashMap();
        double[] dArr = new double[361];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            double dIntValue2 = ((double) entry.getValue().intValue()) / dIntValue;
            Cam16 cam16FromInt = Cam16.fromInt(iIntValue);
            map2.put(Integer.valueOf(iIntValue), cam16FromInt);
            int iRound = (int) Math.round(cam16FromInt.getHue());
            dArr[iRound] = dArr[iRound] + dIntValue2;
        }
        HashMap map3 = new HashMap();
        for (Map.Entry entry2 : map2.entrySet()) {
            int iIntValue2 = ((Integer) entry2.getKey()).intValue();
            int iRound2 = (int) Math.round(((Cam16) entry2.getValue()).getHue());
            double d = 0.0d;
            for (int i = iRound2 - 15; i < iRound2 + 15; i++) {
                d += dArr[MathUtils.sanitizeDegreesInt(i)];
            }
            map3.put(Integer.valueOf(iIntValue2), Double.valueOf(d));
        }
        HashMap map4 = new HashMap();
        for (Map.Entry entry3 : map2.entrySet()) {
            int iIntValue3 = ((Integer) entry3.getKey()).intValue();
            Cam16 cam16 = (Cam16) entry3.getValue();
            map4.put(Integer.valueOf(iIntValue3), Double.valueOf(((cam16.getChroma() - TARGET_CHROMA) * (cam16.getChroma() < TARGET_CHROMA ? WEIGHT_CHROMA_BELOW : WEIGHT_CHROMA_ABOVE)) + (((Double) map3.get(Integer.valueOf(iIntValue3))).doubleValue() * 100.0d * WEIGHT_PROPORTION)));
        }
        List<Integer> listFilter = filter(map3, map2);
        HashMap map5 = new HashMap();
        Iterator<Integer> it2 = listFilter.iterator();
        while (it2.hasNext()) {
            int iIntValue4 = it2.next().intValue();
            map5.put(Integer.valueOf(iIntValue4), (Double) map4.get(Integer.valueOf(iIntValue4)));
        }
        ArrayList<Map.Entry> arrayList = new ArrayList(map5.entrySet());
        Collections.sort(arrayList, new ScoredComparator());
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry4 : arrayList) {
            Cam16 cam17 = (Cam16) map2.get(Integer.valueOf(((Integer) entry4.getKey()).intValue()));
            boolean z = false;
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                if (MathUtils.differenceDegrees(cam17.getHue(), ((Cam16) map2.get((Integer) it3.next())).getHue()) < CUTOFF_CHROMA) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                arrayList2.add((Integer) entry4.getKey());
            }
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(-12417548);
        }
        return arrayList2;
    }
}
