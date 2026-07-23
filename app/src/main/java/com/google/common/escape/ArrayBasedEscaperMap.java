package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@GwtCompatible
@ElementTypesAreNonnullByDefault
public final class ArrayBasedEscaperMap {
    private static final char[][] EMPTY_REPLACEMENT_ARRAY = (char[][]) Array.newInstance((Class<?>) char.class, 0, 0);
    private final char[][] replacementArray;

    private ArrayBasedEscaperMap(char[][] cArr) {
        this.replacementArray = cArr;
    }

    public static ArrayBasedEscaperMap create(Map<Character, String> map) {
        char[][] cArr;
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            cArr = EMPTY_REPLACEMENT_ARRAY;
        } else {
            char[][] cArr2 = new char[((Character) Collections.max(map.keySet())).charValue() + 1][];
            for (Character ch : map.keySet()) {
                cArr2[ch.charValue()] = map.get(ch).toCharArray();
            }
            cArr = cArr2;
        }
        return new ArrayBasedEscaperMap(cArr);
    }

    public final char[][] getReplacementArray() {
        return this.replacementArray;
    }
}
