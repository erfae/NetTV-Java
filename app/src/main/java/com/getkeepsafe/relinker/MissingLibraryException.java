package com.getkeepsafe.relinker;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MissingLibraryException extends RuntimeException {
    /* JADX WARN: Illegal instructions before constructor call */
    public MissingLibraryException(String str, String[] strArr, String[] strArr2) {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Could not find '", str, "'. Looked for: ");
        sbM26m.append(Arrays.toString(strArr));
        sbM26m.append(", but only found: ");
        super(Insets$$ExternalSyntheticOutline0.m(sbM26m, Arrays.toString(strArr2), "."));
    }
}
