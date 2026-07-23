package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
abstract class LineBuffer {
    private StringBuilder line = new StringBuilder();
    private boolean sawReturn;

    @CanIgnoreReturnValue
    private boolean finishLine(boolean z) throws IOException {
        handleLine(this.line.toString());
        this.line = new StringBuilder();
        this.sawReturn = false;
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0019  */
    public final void add(char[] cArr, int i) throws IOException {
        int i2;
        if (!this.sawReturn || i <= 0) {
            i2 = 0;
        } else {
            if (finishLine(cArr[0] == '\n')) {
                i2 = 1;
            } else {
                i2 = 0;
            }
        }
        int i3 = i + 0;
        int i4 = i2;
        while (i2 < i3) {
            char c = cArr[i2];
            if (c != '\n') {
                if (c == '\r') {
                    this.line.append(cArr, i4, i2 - i4);
                    this.sawReturn = true;
                    int i5 = i2 + 1;
                    if (i5 < i3) {
                        if (finishLine(cArr[i5] == '\n')) {
                            i2 = i5;
                        }
                    }
                }
                i2++;
            } else {
                this.line.append(cArr, i4, i2 - i4);
                finishLine(true);
            }
            i4 = i2 + 1;
            i2++;
        }
        this.line.append(cArr, i4, i3 - i4);
    }

    public final void finish() throws IOException {
        if (this.sawReturn || this.line.length() > 0) {
            finishLine(false);
        }
    }

    public abstract void handleLine(String str) throws IOException;
}
