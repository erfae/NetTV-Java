package androidx.recyclerview.widget;

import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
class ViewBoundsCheck {
    public BoundFlags mBoundFlags = new BoundFlags();
    public final Callback mCallback;

    public static class BoundFlags {
        public int mBoundFlags = 0;
        public int mChildEnd;
        public int mChildStart;
        public int mRvEnd;
        public int mRvStart;

        public final void addFlags(int i) {
            this.mBoundFlags = i | this.mBoundFlags;
        }

        public final boolean boundsMatch() {
            int i = this.mBoundFlags;
            if ((i & 7) != 0 && (i & (compare(this.mChildStart, this.mRvStart) << 0)) == 0) {
                return false;
            }
            int i2 = this.mBoundFlags;
            if ((i2 & 112) != 0 && (i2 & (compare(this.mChildStart, this.mRvEnd) << 4)) == 0) {
                return false;
            }
            int i3 = this.mBoundFlags;
            if ((i3 & 1792) != 0 && (i3 & (compare(this.mChildEnd, this.mRvStart) << 8)) == 0) {
                return false;
            }
            int i4 = this.mBoundFlags;
            return (i4 & 28672) == 0 || (i4 & (compare(this.mChildEnd, this.mRvEnd) << 12)) != 0;
        }

        public final int compare(int i, int i2) {
            if (i > i2) {
                return 1;
            }
            return i == i2 ? 2 : 4;
        }
    }

    public interface Callback {
        View getChildAt(int i);

        int getChildEnd(View view);

        int getChildStart(View view);

        int getParentEnd();

        int getParentStart();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewBounds {
    }

    public ViewBoundsCheck(Callback callback) {
        this.mCallback = callback;
    }

    public final View findOneViewWithinBoundFlags(int i, int i2, int i3, int i4) {
        int parentStart = this.mCallback.getParentStart();
        int parentEnd = this.mCallback.getParentEnd();
        int i5 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View childAt = this.mCallback.getChildAt(i);
            int childStart = this.mCallback.getChildStart(childAt);
            int childEnd = this.mCallback.getChildEnd(childAt);
            BoundFlags boundFlags = this.mBoundFlags;
            boundFlags.mRvStart = parentStart;
            boundFlags.mRvEnd = parentEnd;
            boundFlags.mChildStart = childStart;
            boundFlags.mChildEnd = childEnd;
            if (i3 != 0) {
                boundFlags.mBoundFlags = 0;
                boundFlags.addFlags(i3);
                if (this.mBoundFlags.boundsMatch()) {
                    return childAt;
                }
            }
            if (i4 != 0) {
                BoundFlags boundFlags2 = this.mBoundFlags;
                boundFlags2.mBoundFlags = 0;
                boundFlags2.addFlags(i4);
                if (this.mBoundFlags.boundsMatch()) {
                    view = childAt;
                }
            }
            i += i5;
        }
        return view;
    }

    public final boolean isViewWithinBoundFlags(View view) {
        BoundFlags boundFlags = this.mBoundFlags;
        int parentStart = this.mCallback.getParentStart();
        int parentEnd = this.mCallback.getParentEnd();
        int childStart = this.mCallback.getChildStart(view);
        int childEnd = this.mCallback.getChildEnd(view);
        boundFlags.mRvStart = parentStart;
        boundFlags.mRvEnd = parentEnd;
        boundFlags.mChildStart = childStart;
        boundFlags.mChildEnd = childEnd;
        BoundFlags boundFlags2 = this.mBoundFlags;
        boundFlags2.mBoundFlags = 0;
        boundFlags2.addFlags(24579);
        return this.mBoundFlags.boundsMatch();
    }
}
