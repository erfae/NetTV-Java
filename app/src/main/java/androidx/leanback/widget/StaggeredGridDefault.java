package androidx.leanback.widget;

/* JADX INFO: loaded from: classes.dex */
final class StaggeredGridDefault extends StaggeredGrid {
    private int findRowEdgeLimitSearchIndex(boolean z) {
        boolean z2 = false;
        if (z) {
            for (int i = this.mLastVisibleIndex; i >= this.mFirstVisibleIndex; i--) {
                int i2 = getLocation(i).row;
                if (i2 == 0) {
                    z2 = true;
                } else if (z2 && i2 == this.mNumRows - 1) {
                    return i;
                }
            }
            return -1;
        }
        for (int i3 = this.mFirstVisibleIndex; i3 <= this.mLastVisibleIndex; i3++) {
            int i4 = getLocation(i3).row;
            if (i4 == this.mNumRows - 1) {
                z2 = true;
            } else if (z2 && i4 == 0) {
                return i3;
            }
        }
        return -1;
    }

    @Override // androidx.leanback.widget.StaggeredGrid
    public final boolean appendVisibleItemsWithoutCache(int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int rowMin;
        int i4;
        int i5;
        int count = this.mProvider.getCount();
        int i6 = this.mLastVisibleIndex;
        if (i6 < 0) {
            int i7 = this.mStartIndex;
            i2 = i7 != -1 ? i7 : 0;
            i3 = (this.mLocations.size() > 0 ? getLocation(getLastIndex()).row + 1 : i2) % this.mNumRows;
            z2 = false;
            rowMin = 0;
        } else {
            if (i6 < getLastIndex()) {
                return false;
            }
            int i8 = this.mLastVisibleIndex;
            i2 = i8 + 1;
            i3 = getLocation(i8).row;
            int iFindRowEdgeLimitSearchIndex = findRowEdgeLimitSearchIndex(true);
            if (iFindRowEdgeLimitSearchIndex < 0) {
                rowMin = Integer.MIN_VALUE;
                for (int i9 = 0; i9 < this.mNumRows; i9++) {
                    rowMin = this.mReversedFlow ? getRowMin(i9) : getRowMax(i9);
                    if (rowMin != Integer.MIN_VALUE) {
                        break;
                    }
                }
            } else {
                rowMin = this.mReversedFlow ? findRowMin(false, iFindRowEdgeLimitSearchIndex, null) : findRowMax(true, iFindRowEdgeLimitSearchIndex, null);
            }
            if (!this.mReversedFlow ? getRowMax(i3) >= rowMin : getRowMin(i3) <= rowMin) {
                i3++;
                if (i3 == this.mNumRows) {
                    rowMin = this.mReversedFlow ? findRowMin(false, null) : findRowMax(true, null);
                    i3 = 0;
                }
            }
            z2 = true;
        }
        boolean z3 = false;
        while (true) {
            if (i3 < this.mNumRows) {
                if (i2 == count || (!z && checkAppendOverLimit(i))) {
                    break;
                }
                int rowMin2 = this.mReversedFlow ? getRowMin(i3) : getRowMax(i3);
                if (rowMin2 != Integer.MAX_VALUE && rowMin2 != Integer.MIN_VALUE) {
                    if (this.mReversedFlow) {
                        i5 = this.mSpacing;
                        i4 = -i5;
                    } else {
                        i4 = this.mSpacing;
                    }
                    rowMin2 += i4;
                } else if (i3 == 0) {
                    rowMin2 = this.mReversedFlow ? getRowMin(this.mNumRows - 1) : getRowMax(this.mNumRows - 1);
                    if (rowMin2 != Integer.MAX_VALUE && rowMin2 != Integer.MIN_VALUE) {
                        if (this.mReversedFlow) {
                            i5 = this.mSpacing;
                            i4 = -i5;
                        } else {
                            i4 = this.mSpacing;
                        }
                        rowMin2 += i4;
                    }
                } else {
                    rowMin2 = this.mReversedFlow ? getRowMax(i3 - 1) : getRowMin(i3 - 1);
                }
                int i10 = i2 + 1;
                int iAppendVisibleItemToRow = appendVisibleItemToRow(i2, i3, rowMin2);
                if (z2) {
                    while (true) {
                        if (!this.mReversedFlow) {
                            if (rowMin2 + iAppendVisibleItemToRow >= rowMin) {
                                break;
                            }
                            if (i10 != count) {
                            }
                            return true;
                        }
                        if (rowMin2 - iAppendVisibleItemToRow <= rowMin) {
                            break;
                        }
                        if (i10 != count || (!z && checkAppendOverLimit(i))) {
                            return true;
                        }
                        rowMin2 += this.mReversedFlow ? (-iAppendVisibleItemToRow) - this.mSpacing : iAppendVisibleItemToRow + this.mSpacing;
                        int i11 = i10 + 1;
                        int iAppendVisibleItemToRow2 = appendVisibleItemToRow(i10, i3, rowMin2);
                        i10 = i11;
                        iAppendVisibleItemToRow = iAppendVisibleItemToRow2;
                    }
                    i2 = i10;
                } else {
                    rowMin = this.mReversedFlow ? getRowMin(i3) : getRowMax(i3);
                    i2 = i10;
                    z2 = true;
                }
                i3++;
                z3 = true;
            } else {
                if (z) {
                    return z3;
                }
                rowMin = this.mReversedFlow ? findRowMin(false, null) : findRowMax(true, null);
                i3 = 0;
            }
        }
        return z3;
    }

    @Override // androidx.leanback.widget.Grid
    public int findRowMax(boolean z, int i, int[] iArr) {
        int i2;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.row;
        if (this.mReversedFlow) {
            i2 = i3;
            int i4 = i2;
            int i5 = 1;
            int i6 = edge;
            for (int i7 = i + 1; i5 < this.mNumRows && i7 <= this.mLastVisibleIndex; i7++) {
                StaggeredGrid.Location location2 = getLocation(i7);
                i6 += location2.offset;
                int i8 = location2.row;
                if (i8 != i4) {
                    i5++;
                    if (!z ? i6 >= edge : i6 <= edge) {
                        i4 = i8;
                    } else {
                        edge = i6;
                        i = i7;
                        i2 = i8;
                        i4 = i2;
                    }
                }
            }
        } else {
            int i9 = 1;
            int i10 = i3;
            StaggeredGrid.Location location3 = location;
            int i11 = edge;
            edge = this.mProvider.getSize(i) + edge;
            i2 = i10;
            for (int i12 = i - 1; i9 < this.mNumRows && i12 >= this.mFirstVisibleIndex; i12--) {
                i11 -= location3.offset;
                location3 = getLocation(i12);
                int i13 = location3.row;
                if (i13 != i10) {
                    i9++;
                    int size = this.mProvider.getSize(i12) + i11;
                    if (!z ? size >= edge : size <= edge) {
                        i10 = i13;
                    } else {
                        edge = size;
                        i = i12;
                        i2 = i13;
                        i10 = i2;
                    }
                }
            }
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return edge;
    }

    @Override // androidx.leanback.widget.Grid
    public int findRowMin(boolean z, int i, int[] iArr) {
        int size;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i2 = location.row;
        if (this.mReversedFlow) {
            int i3 = 1;
            size = edge - this.mProvider.getSize(i);
            int i4 = i2;
            for (int i5 = i - 1; i3 < this.mNumRows && i5 >= this.mFirstVisibleIndex; i5--) {
                edge -= location.offset;
                location = getLocation(i5);
                int i6 = location.row;
                if (i6 != i4) {
                    i3++;
                    int size2 = edge - this.mProvider.getSize(i5);
                    if (!z ? size2 >= size : size2 <= size) {
                        i4 = i6;
                    } else {
                        size = size2;
                        i = i5;
                        i2 = i6;
                        i4 = i2;
                    }
                }
            }
        } else {
            int i7 = i2;
            int i8 = i7;
            int i9 = 1;
            int i10 = edge;
            for (int i11 = i + 1; i9 < this.mNumRows && i11 <= this.mLastVisibleIndex; i11++) {
                StaggeredGrid.Location location2 = getLocation(i11);
                i10 += location2.offset;
                int i12 = location2.row;
                if (i12 != i8) {
                    i9++;
                    if (!z ? i10 >= edge : i10 <= edge) {
                        i8 = i12;
                    } else {
                        edge = i10;
                        i = i11;
                        i7 = i12;
                        i8 = i7;
                    }
                }
            }
            size = edge;
            i2 = i7;
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return size;
    }

    public final int getRowMax(int i) {
        int i2;
        StaggeredGrid.Location location;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MIN_VALUE;
        }
        if (this.mReversedFlow) {
            int edge = this.mProvider.getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).row == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.offset;
                }
            } while (location.row != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.row == i) {
            i2 = location2.size;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= getFirstIndex()) {
                    edge2 -= location2.offset;
                    location2 = getLocation(i5);
                }
            } while (location2.row != i);
            i2 = location2.size;
        }
        return edge2 + i2;
        return Integer.MIN_VALUE;
    }

    public final int getRowMin(int i) {
        StaggeredGrid.Location location;
        int i2;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MAX_VALUE;
        }
        if (!this.mReversedFlow) {
            int edge = this.mProvider.getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).row == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.offset;
                }
            } while (location.row != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.row == i) {
            i2 = location2.size;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= getFirstIndex()) {
                    edge2 -= location2.offset;
                    location2 = getLocation(i5);
                }
            } while (location2.row != i);
            i2 = location2.size;
        }
        return edge2 - i2;
        return Integer.MAX_VALUE;
    }

    @Override // androidx.leanback.widget.StaggeredGrid
    public final boolean prependVisibleItemsWithoutCache(int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int rowMax;
        int i4;
        int i5;
        int i6 = this.mFirstVisibleIndex;
        if (i6 < 0) {
            int i7 = this.mStartIndex;
            i2 = i7 != -1 ? i7 : 0;
            i3 = (this.mLocations.size() > 0 ? (getLocation(getFirstIndex()).row + this.mNumRows) - 1 : i2) % this.mNumRows;
            z2 = false;
            rowMax = 0;
        } else {
            if (i6 > getFirstIndex()) {
                return false;
            }
            int i8 = this.mFirstVisibleIndex;
            i2 = i8 - 1;
            i3 = getLocation(i8).row;
            int iFindRowEdgeLimitSearchIndex = findRowEdgeLimitSearchIndex(false);
            if (iFindRowEdgeLimitSearchIndex < 0) {
                i3--;
                rowMax = Integer.MAX_VALUE;
                for (int i9 = this.mNumRows - 1; i9 >= 0; i9--) {
                    rowMax = this.mReversedFlow ? getRowMax(i9) : getRowMin(i9);
                    if (rowMax != Integer.MAX_VALUE) {
                        break;
                    }
                }
            } else {
                rowMax = this.mReversedFlow ? findRowMax(true, iFindRowEdgeLimitSearchIndex, null) : findRowMin(false, iFindRowEdgeLimitSearchIndex, null);
            }
            if (!this.mReversedFlow ? getRowMin(i3) <= rowMax : getRowMax(i3) >= rowMax) {
                i3--;
                if (i3 < 0) {
                    i3 = this.mNumRows - 1;
                    rowMax = this.mReversedFlow ? findRowMax(true, null) : findRowMin(false, null);
                }
            }
            z2 = true;
        }
        boolean z3 = false;
        while (true) {
            if (i3 >= 0) {
                if (i2 < 0 || (!z && checkPrependOverLimit(i))) {
                    break;
                }
                int rowMax2 = this.mReversedFlow ? getRowMax(i3) : getRowMin(i3);
                if (rowMax2 != Integer.MAX_VALUE && rowMax2 != Integer.MIN_VALUE) {
                    if (this.mReversedFlow) {
                        i5 = this.mSpacing;
                    } else {
                        i4 = this.mSpacing;
                        i5 = -i4;
                    }
                    rowMax2 += i5;
                } else if (i3 == this.mNumRows - 1) {
                    rowMax2 = this.mReversedFlow ? getRowMax(0) : getRowMin(0);
                    if (rowMax2 != Integer.MAX_VALUE && rowMax2 != Integer.MIN_VALUE) {
                        if (this.mReversedFlow) {
                            i5 = this.mSpacing;
                        } else {
                            i4 = this.mSpacing;
                            i5 = -i4;
                        }
                        rowMax2 += i5;
                    }
                } else {
                    rowMax2 = this.mReversedFlow ? getRowMin(i3 + 1) : getRowMax(i3 + 1);
                }
                int i10 = i2 - 1;
                int iPrependVisibleItemToRow = prependVisibleItemToRow(i2, i3, rowMax2);
                if (z2) {
                    while (true) {
                        if (!this.mReversedFlow) {
                            if (rowMax2 - iPrependVisibleItemToRow <= rowMax) {
                                break;
                            }
                            if (i10 >= 0) {
                            }
                            return true;
                        }
                        if (rowMax2 + iPrependVisibleItemToRow >= rowMax) {
                            break;
                        }
                        if (i10 >= 0 || (!z && checkPrependOverLimit(i))) {
                            return true;
                        }
                        rowMax2 += this.mReversedFlow ? iPrependVisibleItemToRow + this.mSpacing : (-iPrependVisibleItemToRow) - this.mSpacing;
                        int i11 = i10 - 1;
                        int iPrependVisibleItemToRow2 = prependVisibleItemToRow(i10, i3, rowMax2);
                        i10 = i11;
                        iPrependVisibleItemToRow = iPrependVisibleItemToRow2;
                    }
                    i2 = i10;
                } else {
                    rowMax = this.mReversedFlow ? getRowMax(i3) : getRowMin(i3);
                    i2 = i10;
                    z2 = true;
                }
                i3--;
                z3 = true;
            } else {
                if (z) {
                    return z3;
                }
                rowMax = this.mReversedFlow ? findRowMax(true, null) : findRowMin(false, null);
                i3 = this.mNumRows - 1;
            }
        }
        return z3;
    }
}
