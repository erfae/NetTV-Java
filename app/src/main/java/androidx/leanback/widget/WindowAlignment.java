package androidx.leanback.widget;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
class WindowAlignment {
    public final Axis horizontal;
    private Axis mMainAxis;
    private int mOrientation = 0;
    private Axis mSecondAxis;
    public final Axis vertical;

    public static class Axis {
        private int mMaxScroll;
        private int mMinScroll;
        private String mName;
        private int mPaddingMax;
        private int mPaddingMin;
        private boolean mReversedFlow;
        private int mSize;
        private int mPreferredKeyLine = 2;
        private int mWindowAlignment = 3;
        private int mWindowAlignmentOffset = 0;
        private float mWindowAlignmentOffsetPercent = 50.0f;
        private int mMinEdge = Integer.MIN_VALUE;
        private int mMaxEdge = Integer.MAX_VALUE;

        public Axis(String str) {
            this.mName = str;
        }

        public final int calculateKeyline() {
            if (this.mReversedFlow) {
                int i = this.mWindowAlignmentOffset;
                int i2 = i >= 0 ? this.mSize - i : -i;
                float f = this.mWindowAlignmentOffsetPercent;
                return f != -1.0f ? i2 - ((int) ((this.mSize * f) / 100.0f)) : i2;
            }
            int i3 = this.mWindowAlignmentOffset;
            if (i3 < 0) {
                i3 += this.mSize;
            }
            float f2 = this.mWindowAlignmentOffsetPercent;
            return f2 != -1.0f ? i3 + ((int) ((this.mSize * f2) / 100.0f)) : i3;
        }

        public final int getClientSize() {
            return (this.mSize - this.mPaddingMin) - this.mPaddingMax;
        }

        public final int getMaxScroll() {
            return this.mMaxScroll;
        }

        public final int getMinScroll() {
            return this.mMinScroll;
        }

        public final int getPaddingMax() {
            return this.mPaddingMax;
        }

        public final int getPaddingMin() {
            return this.mPaddingMin;
        }

        public final int getScroll(int i) {
            int i2;
            int i3;
            int size = getSize();
            int iCalculateKeyline = calculateKeyline();
            boolean zIsMinUnknown = isMinUnknown();
            boolean zIsMaxUnknown = isMaxUnknown();
            if (!zIsMinUnknown) {
                int i4 = this.mPaddingMin;
                int i5 = iCalculateKeyline - i4;
                if (this.mReversedFlow ? (this.mWindowAlignment & 2) != 0 : (this.mWindowAlignment & 1) != 0) {
                    int i6 = this.mMinEdge;
                    if (i - i6 <= i5) {
                        int i7 = i6 - i4;
                        return (zIsMaxUnknown || i7 <= (i3 = this.mMaxScroll)) ? i7 : i3;
                    }
                }
            }
            if (!zIsMaxUnknown) {
                int i8 = this.mPaddingMax;
                int i9 = (size - iCalculateKeyline) - i8;
                if (this.mReversedFlow ? (this.mWindowAlignment & 1) != 0 : (this.mWindowAlignment & 2) != 0) {
                    int i10 = this.mMaxEdge;
                    if (i10 - i <= i9) {
                        int i11 = i10 - (size - i8);
                        return (zIsMinUnknown || i11 >= (i2 = this.mMinScroll)) ? i11 : i2;
                    }
                }
            }
            return i - iCalculateKeyline;
        }

        public final int getSize() {
            return this.mSize;
        }

        public final int getWindowAlignment() {
            return this.mWindowAlignment;
        }

        public final int getWindowAlignmentOffset() {
            return this.mWindowAlignmentOffset;
        }

        public final float getWindowAlignmentOffsetPercent() {
            return this.mWindowAlignmentOffsetPercent;
        }

        public final void invalidateScrollMax() {
            this.mMaxEdge = Integer.MAX_VALUE;
            this.mMaxScroll = Integer.MAX_VALUE;
        }

        public final void invalidateScrollMin() {
            this.mMinEdge = Integer.MIN_VALUE;
            this.mMinScroll = Integer.MIN_VALUE;
        }

        public final boolean isMaxUnknown() {
            return this.mMaxEdge == Integer.MAX_VALUE;
        }

        public final boolean isMinUnknown() {
            return this.mMinEdge == Integer.MIN_VALUE;
        }

        public final boolean isPreferKeylineOverHighEdge() {
            return (this.mPreferredKeyLine & 2) != 0;
        }

        public final boolean isPreferKeylineOverLowEdge() {
            return (this.mPreferredKeyLine & 1) != 0;
        }

        public final void reset() {
            this.mMinEdge = Integer.MIN_VALUE;
            this.mMaxEdge = Integer.MAX_VALUE;
        }

        public final void setPadding(int i, int i2) {
            this.mPaddingMin = i;
            this.mPaddingMax = i2;
        }

        public final void setPreferKeylineOverHighEdge(boolean z) {
            this.mPreferredKeyLine = z ? this.mPreferredKeyLine | 2 : this.mPreferredKeyLine & (-3);
        }

        public final void setPreferKeylineOverLowEdge(boolean z) {
            this.mPreferredKeyLine = z ? this.mPreferredKeyLine | 1 : this.mPreferredKeyLine & (-2);
        }

        public final void setReversedFlow(boolean z) {
            this.mReversedFlow = z;
        }

        public final void setSize(int i) {
            this.mSize = i;
        }

        public final void setWindowAlignment(int i) {
            this.mWindowAlignment = i;
        }

        public final void setWindowAlignmentOffset(int i) {
            this.mWindowAlignmentOffset = i;
        }

        public final void setWindowAlignmentOffsetPercent(float f) {
            if ((f < 0.0f || f > 100.0f) && f != -1.0f) {
                throw new IllegalArgumentException();
            }
            this.mWindowAlignmentOffsetPercent = f;
        }

        public String toString() {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(" min:");
            sbM.append(this.mMinEdge);
            sbM.append(" ");
            sbM.append(this.mMinScroll);
            sbM.append(" max:");
            sbM.append(this.mMaxEdge);
            sbM.append(" ");
            sbM.append(this.mMaxScroll);
            return sbM.toString();
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
        
            r4.mMinScroll = r4.mMinEdge - r4.mPaddingMin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0046, code lost:
        
            r4.mMaxScroll = (r4.mMaxEdge - r4.mPaddingMin) - r5;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateMinMax(int r5, int r6, int r7, int r8) {
            /*
                Method dump skipped, instruction units count: 215
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.WindowAlignment.Axis.updateMinMax(int, int, int, int):void");
        }
    }

    public WindowAlignment() {
        Axis axis = new Axis("vertical");
        this.vertical = axis;
        Axis axis2 = new Axis("horizontal");
        this.horizontal = axis2;
        this.mMainAxis = axis2;
        this.mSecondAxis = axis;
    }

    public final int getOrientation() {
        return this.mOrientation;
    }

    public final Axis mainAxis() {
        return this.mMainAxis;
    }

    public final void reset() {
        mainAxis().reset();
    }

    public final Axis secondAxis() {
        return this.mSecondAxis;
    }

    public final void setOrientation(int i) {
        this.mOrientation = i;
        if (i == 0) {
            this.mMainAxis = this.horizontal;
            this.mSecondAxis = this.vertical;
        } else {
            this.mMainAxis = this.vertical;
            this.mSecondAxis = this.horizontal;
        }
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("horizontal=");
        sbM.append(this.horizontal);
        sbM.append("; vertical=");
        sbM.append(this.vertical);
        return sbM.toString();
    }
}
