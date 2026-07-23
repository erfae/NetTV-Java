package androidx.leanback.widget;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public interface MultiActionsProvider {

    public static class MultiAction {
        private Drawable[] mDrawables;
        private long mId;
        private int mIndex = 0;

        public MultiAction(long j) {
            this.mId = j;
        }

        public Drawable getCurrentDrawable() {
            return this.mDrawables[this.mIndex];
        }

        public Drawable[] getDrawables() {
            return this.mDrawables;
        }

        public long getId() {
            return this.mId;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void incrementIndex() {
            int i = this.mIndex;
            setIndex(i < this.mDrawables.length + (-1) ? i + 1 : 0);
        }

        public void setDrawables(Drawable[] drawableArr) {
            this.mDrawables = drawableArr;
            if (this.mIndex > drawableArr.length - 1) {
                this.mIndex = drawableArr.length - 1;
            }
        }

        public void setIndex(int i) {
            this.mIndex = i;
        }
    }

    MultiAction[] getActions();
}
