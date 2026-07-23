package androidx.recyclerview.widget;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class OpReorderer {
    public final Callback mCallback;

    public interface Callback {
        AdapterHelper.UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj);

        void recycleUpdateOp(AdapterHelper.UpdateOp updateOp);
    }

    public OpReorderer(Callback callback) {
        this.mCallback = callback;
    }

    private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> list) {
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).cmd != 8) {
                z = true;
            } else if (z) {
                return size;
            }
        }
        return -1;
    }

    private void swapMoveAdd(List<AdapterHelper.UpdateOp> list, int i, AdapterHelper.UpdateOp updateOp, int i2, AdapterHelper.UpdateOp updateOp2) {
        int i3 = updateOp.itemCount;
        int i4 = updateOp2.positionStart;
        int i5 = i3 < i4 ? -1 : 0;
        int i6 = updateOp.positionStart;
        if (i6 < i4) {
            i5++;
        }
        if (i4 <= i6) {
            updateOp.positionStart = i6 + updateOp2.itemCount;
        }
        int i7 = updateOp2.positionStart;
        if (i7 <= i3) {
            updateOp.itemCount = i3 + updateOp2.itemCount;
        }
        updateOp2.positionStart = i7 + i5;
        list.set(i, updateOp2);
        list.set(i2, updateOp);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0040  */
    /* JADX WARN: Code duplicated, block: B:19:0x0044  */
    /* JADX WARN: Code duplicated, block: B:21:0x0049  */
    /* JADX WARN: Code duplicated, block: B:24:0x005f  */
    /* JADX WARN: Code duplicated, block: B:25:0x0063  */
    /* JADX WARN: Code duplicated, block: B:27:0x006d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0072  */
    /* JADX WARN: Code duplicated, block: B:96:? A[RETURN, SYNTHETIC] */
    private void swapMoveOp(List<AdapterHelper.UpdateOp> list, int i, int i2) {
        boolean z;
        AdapterHelper.UpdateOp updateOpObtainUpdateOp;
        int i3;
        int i4;
        int i5;
        AdapterHelper.UpdateOp updateOp = list.get(i);
        AdapterHelper.UpdateOp updateOp2 = list.get(i2);
        int i6 = updateOp2.cmd;
        if (i6 == 1) {
            swapMoveAdd(list, i, updateOp, i2, updateOp2);
            return;
        }
        AdapterHelper.UpdateOp updateOpObtainUpdateOp2 = null;
        if (i6 != 2) {
            if (i6 != 4) {
                return;
            }
            int i7 = updateOp.itemCount;
            int i8 = updateOp2.positionStart;
            if (i7 >= i8) {
                int i9 = updateOp2.itemCount;
                if (i7 < i8 + i9) {
                    updateOp2.itemCount = i9 - 1;
                    updateOpObtainUpdateOp = this.mCallback.obtainUpdateOp(4, updateOp.positionStart, 1, updateOp2.payload);
                }
                i3 = updateOp.positionStart;
                i4 = updateOp2.positionStart;
                if (i3 <= i4) {
                    updateOp2.positionStart = i4 + 1;
                } else {
                    i5 = i4 + updateOp2.itemCount;
                    if (i3 < i5) {
                        int i10 = i5 - i3;
                        updateOpObtainUpdateOp2 = this.mCallback.obtainUpdateOp(4, i3 + 1, i10, updateOp2.payload);
                        updateOp2.itemCount -= i10;
                    }
                }
                list.set(i2, updateOp);
                if (updateOp2.itemCount > 0) {
                    list.set(i, updateOp2);
                } else {
                    list.remove(i);
                    this.mCallback.recycleUpdateOp(updateOp2);
                }
                if (updateOpObtainUpdateOp != null) {
                    list.add(i, updateOpObtainUpdateOp);
                }
                if (updateOpObtainUpdateOp2 != null) {
                    list.add(i, updateOpObtainUpdateOp2);
                    return;
                }
                return;
            }
            updateOp2.positionStart = i8 - 1;
            updateOpObtainUpdateOp = null;
            i3 = updateOp.positionStart;
            i4 = updateOp2.positionStart;
            if (i3 <= i4) {
                updateOp2.positionStart = i4 + 1;
            } else {
                i5 = i4 + updateOp2.itemCount;
                if (i3 < i5) {
                    int i11 = i5 - i3;
                    updateOpObtainUpdateOp2 = this.mCallback.obtainUpdateOp(4, i3 + 1, i11, updateOp2.payload);
                    updateOp2.itemCount -= i11;
                }
            }
            list.set(i2, updateOp);
            if (updateOp2.itemCount > 0) {
                list.set(i, updateOp2);
            } else {
                list.remove(i);
                this.mCallback.recycleUpdateOp(updateOp2);
            }
            if (updateOpObtainUpdateOp != null) {
                list.add(i, updateOpObtainUpdateOp);
            }
            if (updateOpObtainUpdateOp2 != null) {
                list.add(i, updateOpObtainUpdateOp2);
                return;
            }
            return;
        }
        int i12 = updateOp.positionStart;
        int i13 = updateOp.itemCount;
        boolean z2 = false;
        if (i12 < i13) {
            if (updateOp2.positionStart == i12 && updateOp2.itemCount == i13 - i12) {
                z = z2;
                z2 = true;
            } else {
                z = false;
            }
        } else if (updateOp2.positionStart == i13 + 1 && updateOp2.itemCount == i12 - i13) {
            z2 = true;
            z = z2;
            z2 = true;
        } else {
            z = true;
        }
        int i14 = updateOp2.positionStart;
        if (i13 < i14) {
            updateOp2.positionStart = i14 - 1;
        } else {
            int i15 = updateOp2.itemCount;
            if (i13 < i14 + i15) {
                updateOp2.itemCount = i15 - 1;
                updateOp.cmd = 2;
                updateOp.itemCount = 1;
                if (updateOp2.itemCount == 0) {
                    list.remove(i2);
                    this.mCallback.recycleUpdateOp(updateOp2);
                    return;
                }
                return;
            }
        }
        int i16 = updateOp.positionStart;
        int i17 = updateOp2.positionStart;
        if (i16 <= i17) {
            updateOp2.positionStart = i17 + 1;
        } else {
            int i18 = i17 + updateOp2.itemCount;
            if (i16 < i18) {
                updateOpObtainUpdateOp2 = this.mCallback.obtainUpdateOp(2, i16 + 1, i18 - i16, null);
                updateOp2.itemCount = updateOp.positionStart - updateOp2.positionStart;
            }
        }
        if (z2) {
            list.set(i, updateOp2);
            list.remove(i2);
            this.mCallback.recycleUpdateOp(updateOp);
            return;
        }
        if (z) {
            if (updateOpObtainUpdateOp2 != null) {
                int i19 = updateOp.positionStart;
                if (i19 > updateOpObtainUpdateOp2.positionStart) {
                    updateOp.positionStart = i19 - updateOpObtainUpdateOp2.itemCount;
                }
                int i20 = updateOp.itemCount;
                if (i20 > updateOpObtainUpdateOp2.positionStart) {
                    updateOp.itemCount = i20 - updateOpObtainUpdateOp2.itemCount;
                }
            }
            int i21 = updateOp.positionStart;
            if (i21 > updateOp2.positionStart) {
                updateOp.positionStart = i21 - updateOp2.itemCount;
            }
            int i22 = updateOp.itemCount;
            if (i22 > updateOp2.positionStart) {
                updateOp.itemCount = i22 - updateOp2.itemCount;
            }
        } else {
            if (updateOpObtainUpdateOp2 != null) {
                int i23 = updateOp.positionStart;
                if (i23 >= updateOpObtainUpdateOp2.positionStart) {
                    updateOp.positionStart = i23 - updateOpObtainUpdateOp2.itemCount;
                }
                int i24 = updateOp.itemCount;
                if (i24 >= updateOpObtainUpdateOp2.positionStart) {
                    updateOp.itemCount = i24 - updateOpObtainUpdateOp2.itemCount;
                }
            }
            int i25 = updateOp.positionStart;
            if (i25 >= updateOp2.positionStart) {
                updateOp.positionStart = i25 - updateOp2.itemCount;
            }
            int i26 = updateOp.itemCount;
            if (i26 >= updateOp2.positionStart) {
                updateOp.itemCount = i26 - updateOp2.itemCount;
            }
        }
        list.set(i, updateOp2);
        if (updateOp.positionStart != updateOp.itemCount) {
            list.set(i2, updateOp);
        } else {
            list.remove(i2);
        }
        if (updateOpObtainUpdateOp2 != null) {
            list.add(i, updateOpObtainUpdateOp2);
        }
    }

    public final void reorderOps(List<AdapterHelper.UpdateOp> list) {
        while (true) {
            int lastMoveOutOfOrder = getLastMoveOutOfOrder(list);
            if (lastMoveOutOfOrder == -1) {
                return;
            } else {
                swapMoveOp(list, lastMoveOutOfOrder, lastMoveOutOfOrder + 1);
            }
        }
    }
}
