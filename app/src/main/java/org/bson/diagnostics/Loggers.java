package org.bson.diagnostics;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes2.dex */
public final class Loggers {
    public static final String PREFIX = "org.bson";
    private static final boolean USE_SLF4J = shouldUseSLF4J();

    private Loggers() {
    }

    public static Logger getLogger(String str) {
        Assertions.notNull("suffix", str);
        if (str.startsWith(".") || str.endsWith(".")) {
            throw new IllegalArgumentException("The suffix can not start or end with a '.'");
        }
        String strM = Insets$$ExternalSyntheticOutline0.m("org.bson.", str);
        return USE_SLF4J ? new SLF4JLogger(strM) : new JULLogger(strM);
    }

    private static boolean shouldUseSLF4J() {
        try {
            Class.forName("org.slf4j.Logger");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }
}
