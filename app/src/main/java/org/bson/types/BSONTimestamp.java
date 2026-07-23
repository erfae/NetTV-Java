package org.bson.types;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public final class BSONTimestamp implements Comparable<BSONTimestamp>, Serializable {
    private static final long serialVersionUID = -3268482672267936464L;
    private final int inc;
    private final Date time;

    public BSONTimestamp() {
        this.inc = 0;
        this.time = null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BSONTimestamp)) {
            return false;
        }
        BSONTimestamp bSONTimestamp = (BSONTimestamp) obj;
        return getTime() == bSONTimestamp.getTime() && getInc() == bSONTimestamp.getInc();
    }

    public int getInc() {
        return this.inc;
    }

    public int getTime() {
        Date date = this.time;
        if (date == null) {
            return 0;
        }
        return (int) (date.getTime() / 1000);
    }

    public int hashCode() {
        return getTime() + ((this.inc + 31) * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("TS time:");
        sbM.append(this.time);
        sbM.append(" inc:");
        sbM.append(this.inc);
        return sbM.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(BSONTimestamp bSONTimestamp) {
        int inc;
        int inc2;
        if (getTime() != bSONTimestamp.getTime()) {
            inc = getTime();
            inc2 = bSONTimestamp.getTime();
        } else {
            inc = getInc();
            inc2 = bSONTimestamp.getInc();
        }
        return inc - inc2;
    }

    public BSONTimestamp(int i, int i2) {
        this.time = new Date(((long) i) * 1000);
        this.inc = i2;
    }
}
