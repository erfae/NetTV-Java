package androidx.leanback.widget;

import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.CircularIntArray;
import androidx.recyclerview.widget.RecyclerView;
import java.io.PrintWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
abstract class Grid {
    public static final int START_DEFAULT = -1;
    public int mNumRows;
    public Provider mProvider;
    public boolean mReversedFlow;
    public int mSpacing;
    public CircularIntArray[] mTmpItemPositionsInRows;
    public Object[] mTmpItem = new Object[1];
    public int mFirstVisibleIndex = -1;
    public int mLastVisibleIndex = -1;
    public int mStartIndex = -1;

    public static class Location {
        public int row;

        public Location(int i) {
            this.row = i;
        }
    }

    public interface Provider {
        void addItem(Object obj, int i, int i2, int i3, int i4);

        int createItem(int i, boolean z, Object[] objArr, boolean z2);

        int getCount();

        int getEdge(int i);

        int getMinIndex();

        int getSize(int i);

        void removeItem(int i);
    }

    public static Grid createGrid(int i) {
        if (i == 1) {
            return new SingleRow();
        }
        StaggeredGridDefault staggeredGridDefault = new StaggeredGridDefault();
        staggeredGridDefault.setNumRows(i);
        return staggeredGridDefault;
    }

    private void resetVisibleIndexIfEmpty() {
        if (this.mLastVisibleIndex < this.mFirstVisibleIndex) {
            resetVisibleIndex();
        }
    }

    public boolean appendOneColumnVisibleItems() {
        return appendVisibleItems(this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE, true);
    }

    public final void appendVisibleItems(int i) {
        appendVisibleItems(i, false);
    }

    public abstract boolean appendVisibleItems(int i, boolean z);

    public final boolean checkAppendOverLimit(int i) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMin(true, null) > i + this.mSpacing) {
                return false;
            }
        } else if (findRowMax(false, null) < i - this.mSpacing) {
            return false;
        }
        return true;
    }

    public final boolean checkPrependOverLimit(int i) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMax(false, null) < i - this.mSpacing) {
                return false;
            }
        } else if (findRowMin(true, null) > i + this.mSpacing) {
            return false;
        }
        return true;
    }

    public void collectAdjacentPrefetchPositions(int i, int i2, @NonNull RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
    }

    public abstract void debugPrint(PrintWriter printWriter);

    public void fillDisappearingItems(int[] iArr, int i, SparseIntArray sparseIntArray) {
        int lastVisibleIndex = getLastVisibleIndex();
        int iBinarySearch = lastVisibleIndex >= 0 ? Arrays.binarySearch(iArr, 0, i, lastVisibleIndex) : 0;
        if (iBinarySearch < 0) {
            int edge = this.mReversedFlow ? (this.mProvider.getEdge(lastVisibleIndex) - this.mProvider.getSize(lastVisibleIndex)) - this.mSpacing : this.mSpacing + this.mProvider.getSize(lastVisibleIndex) + this.mProvider.getEdge(lastVisibleIndex);
            for (int i2 = (-iBinarySearch) - 1; i2 < i; i2++) {
                int i3 = iArr[i2];
                int i4 = sparseIntArray.get(i3);
                int i5 = i4 < 0 ? 0 : i4;
                int iCreateItem = this.mProvider.createItem(i3, true, this.mTmpItem, true);
                this.mProvider.addItem(this.mTmpItem[0], i3, iCreateItem, i5, edge);
                edge = this.mReversedFlow ? (edge - iCreateItem) - this.mSpacing : edge + iCreateItem + this.mSpacing;
            }
        }
        int firstVisibleIndex = getFirstVisibleIndex();
        int iBinarySearch2 = firstVisibleIndex >= 0 ? Arrays.binarySearch(iArr, 0, i, firstVisibleIndex) : 0;
        if (iBinarySearch2 < 0) {
            int edge2 = this.mReversedFlow ? this.mProvider.getEdge(firstVisibleIndex) : this.mProvider.getEdge(firstVisibleIndex);
            for (int i6 = (-iBinarySearch2) - 2; i6 >= 0; i6--) {
                int i7 = iArr[i6];
                int i8 = sparseIntArray.get(i7);
                int i9 = i8 < 0 ? 0 : i8;
                int iCreateItem2 = this.mProvider.createItem(i7, false, this.mTmpItem, true);
                edge2 = this.mReversedFlow ? edge2 + this.mSpacing + iCreateItem2 : (edge2 - this.mSpacing) - iCreateItem2;
                this.mProvider.addItem(this.mTmpItem[0], i7, iCreateItem2, i9, edge2);
            }
        }
    }

    public abstract int findRowMax(boolean z, int i, int[] iArr);

    public final int findRowMax(boolean z, @Nullable int[] iArr) {
        return findRowMax(z, this.mReversedFlow ? this.mFirstVisibleIndex : this.mLastVisibleIndex, iArr);
    }

    public abstract int findRowMin(boolean z, int i, int[] iArr);

    public final int findRowMin(boolean z, @Nullable int[] iArr) {
        return findRowMin(z, this.mReversedFlow ? this.mLastVisibleIndex : this.mFirstVisibleIndex, iArr);
    }

    public final int getFirstVisibleIndex() {
        return this.mFirstVisibleIndex;
    }

    public final CircularIntArray[] getItemPositionsInRows() {
        return getItemPositionsInRows(getFirstVisibleIndex(), getLastVisibleIndex());
    }

    public abstract CircularIntArray[] getItemPositionsInRows(int i, int i2);

    public final int getLastVisibleIndex() {
        return this.mLastVisibleIndex;
    }

    public abstract Location getLocation(int i);

    public int getNumRows() {
        return this.mNumRows;
    }

    public final int getRowIndex(int i) {
        Location location = getLocation(i);
        if (location == null) {
            return -1;
        }
        return location.row;
    }

    public void invalidateItemsAfter(int i) {
        int i2;
        if (i >= 0 && (i2 = this.mLastVisibleIndex) >= 0) {
            if (i2 >= i) {
                this.mLastVisibleIndex = i - 1;
            }
            resetVisibleIndexIfEmpty();
            if (getFirstVisibleIndex() < 0) {
                setStart(i);
            }
        }
    }

    public boolean isReversedFlow() {
        return this.mReversedFlow;
    }

    public final boolean prependOneColumnVisibleItems() {
        return prependVisibleItems(this.mReversedFlow ? Integer.MIN_VALUE : Integer.MAX_VALUE, true);
    }

    public final void prependVisibleItems(int i) {
        prependVisibleItems(i, false);
    }

    public abstract boolean prependVisibleItems(int i, boolean z);

    public void removeInvisibleItemsAtEnd(int i, int i2) {
        while (true) {
            int i3 = this.mLastVisibleIndex;
            if (i3 >= this.mFirstVisibleIndex && i3 > i) {
                boolean z = false;
                if (this.mReversedFlow ? this.mProvider.getEdge(i3) <= i2 : this.mProvider.getEdge(i3) >= i2) {
                    z = true;
                }
                if (!z) {
                    break;
                }
                this.mProvider.removeItem(this.mLastVisibleIndex);
                this.mLastVisibleIndex--;
            } else {
                break;
            }
        }
        resetVisibleIndexIfEmpty();
    }

    public void removeInvisibleItemsAtFront(int i, int i2) {
        while (true) {
            int i3 = this.mLastVisibleIndex;
            int i4 = this.mFirstVisibleIndex;
            if (i3 >= i4 && i4 < i) {
                int size = this.mProvider.getSize(i4);
                boolean z = false;
                if (this.mReversedFlow ? this.mProvider.getEdge(this.mFirstVisibleIndex) - size >= i2 : this.mProvider.getEdge(this.mFirstVisibleIndex) + size <= i2) {
                    z = true;
                }
                if (!z) {
                    break;
                }
                this.mProvider.removeItem(this.mFirstVisibleIndex);
                this.mFirstVisibleIndex++;
            } else {
                break;
            }
        }
        resetVisibleIndexIfEmpty();
    }

    public void resetVisibleIndex() {
        this.mLastVisibleIndex = -1;
        this.mFirstVisibleIndex = -1;
    }

    public final void setNumRows(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.mNumRows == i) {
            return;
        }
        this.mNumRows = i;
        this.mTmpItemPositionsInRows = new CircularIntArray[i];
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            this.mTmpItemPositionsInRows[i2] = new CircularIntArray();
        }
    }

    public void setProvider(Provider provider) {
        this.mProvider = provider;
    }

    public final void setReversedFlow(boolean z) {
        this.mReversedFlow = z;
    }

    public final void setSpacing(int i) {
        this.mSpacing = i;
    }

    public void setStart(int i) {
        this.mStartIndex = i;
    }
}
