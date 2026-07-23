package androidx.recyclerview.widget;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
class LayoutState {
    public int mAvailable;
    public int mCurrentPosition;
    public boolean mInfinite;
    public int mItemDirection;
    public int mLayoutDirection;
    public boolean mStopInFocusable;
    public boolean mRecycle = true;
    public int mStartLine = 0;
    public int mEndLine = 0;

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("LayoutState{mAvailable=");
        sbM.append(this.mAvailable);
        sbM.append(", mCurrentPosition=");
        sbM.append(this.mCurrentPosition);
        sbM.append(", mItemDirection=");
        sbM.append(this.mItemDirection);
        sbM.append(", mLayoutDirection=");
        sbM.append(this.mLayoutDirection);
        sbM.append(", mStartLine=");
        sbM.append(this.mStartLine);
        sbM.append(", mEndLine=");
        return Insets$$ExternalSyntheticOutline0.m(sbM, this.mEndLine, '}');
    }
}
