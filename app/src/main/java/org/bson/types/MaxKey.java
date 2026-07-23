package org.bson.types;

import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public final class MaxKey implements Serializable {
    private static final long serialVersionUID = 5123414776151687185L;

    public boolean equals(Object obj) {
        return obj instanceof MaxKey;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "MaxKey";
    }
}
