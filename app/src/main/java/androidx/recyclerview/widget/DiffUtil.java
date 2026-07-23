package androidx.recyclerview.widget;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DiffUtil {
    private static final Comparator<Diagonal> DIAGONAL_COMPARATOR = new Comparator<Diagonal>() { // from class: androidx.recyclerview.widget.DiffUtil.1
        @Override // java.util.Comparator
        public int compare(Diagonal diagonal, Diagonal diagonal2) {
            return diagonal.x - diagonal2.x;
        }
    };

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        @Nullable
        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class CenteredArray {
        private final int[] mData;
        private final int mMid;

        public CenteredArray(int i) {
            int[] iArr = new int[i];
            this.mData = iArr;
            this.mMid = iArr.length / 2;
        }

        public final int[] backingData() {
            return this.mData;
        }

        public void fill(int i) {
            Arrays.fill(this.mData, i);
        }

        public final int get(int i) {
            return this.mData[i + this.mMid];
        }

        public final void set(int i, int i2) {
            this.mData[i + this.mMid] = i2;
        }
    }

    public static class Diagonal {
        public final int size;
        public final int x;
        public final int y;

        public Diagonal(int i, int i2, int i3) {
            this.x = i;
            this.y = i2;
            this.size = i3;
        }
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_MASK = 15;
        private static final int FLAG_MOVED = 12;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 4;
        public static final int NO_POSITION = -1;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final List<Diagonal> mDiagonals;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;

        public DiffResult(Callback callback, List<Diagonal> list, int[] iArr, int[] iArr2, boolean z) {
            this.mDiagonals = list;
            this.mOldItemStatuses = iArr;
            this.mNewItemStatuses = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = z;
            addEdgeDiagonals();
            findMatchingItems();
        }

        private void addEdgeDiagonals() {
            Diagonal diagonal = this.mDiagonals.isEmpty() ? null : this.mDiagonals.get(0);
            if (diagonal == null || diagonal.x != 0 || diagonal.y != 0) {
                this.mDiagonals.add(0, new Diagonal(0, 0, 0));
            }
            this.mDiagonals.add(new Diagonal(this.mOldListSize, this.mNewListSize, 0));
        }

        private void findMatchingAddition(int i) {
            int i2;
            int size = this.mDiagonals.size();
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                Diagonal diagonal = this.mDiagonals.get(i4);
                while (true) {
                    i2 = diagonal.y;
                    if (i3 < i2) {
                        if (this.mNewItemStatuses[i3] == 0 && this.mCallback.areItemsTheSame(i, i3)) {
                            int i5 = this.mCallback.areContentsTheSame(i, i3) ? 8 : 4;
                            this.mOldItemStatuses[i] = (i3 << 4) | i5;
                            this.mNewItemStatuses[i3] = (i << 4) | i5;
                            return;
                        }
                        i3++;
                    }
                }
                i3 = diagonal.size + i2;
            }
        }

        private void findMatchingItems() {
            for (Diagonal diagonal : this.mDiagonals) {
                for (int i = 0; i < diagonal.size; i++) {
                    int i2 = diagonal.x + i;
                    int i3 = diagonal.y + i;
                    int i4 = this.mCallback.areContentsTheSame(i2, i3) ? 1 : 2;
                    this.mOldItemStatuses[i2] = (i3 << 4) | i4;
                    this.mNewItemStatuses[i3] = (i2 << 4) | i4;
                }
            }
            if (this.mDetectMoves) {
                findMoveMatches();
            }
        }

        private void findMoveMatches() {
            int i;
            int i2 = 0;
            for (Diagonal diagonal : this.mDiagonals) {
                while (true) {
                    i = diagonal.x;
                    if (i2 < i) {
                        if (this.mOldItemStatuses[i2] == 0) {
                            findMatchingAddition(i2);
                        }
                        i2++;
                    }
                }
                i2 = diagonal.size + i;
            }
        }

        @Nullable
        private static PostponedUpdate getPostponedUpdate(Collection<PostponedUpdate> collection, int i, boolean z) {
            PostponedUpdate next;
            Iterator<PostponedUpdate> it = collection.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (next.posInOwnerList == i && next.removal == z) {
                    it.remove();
                    break;
                }
            }
            while (it.hasNext()) {
                PostponedUpdate next2 = it.next();
                if (z) {
                    next2.currentPos--;
                } else {
                    next2.currentPos++;
                }
            }
            return next;
        }

        public int convertNewPositionToOld(@IntRange(from = 0) int i) {
            if (i < 0 || i >= this.mNewListSize) {
                StringBuilder sbM24m = Insets$$ExternalSyntheticOutline0.m24m("Index out of bounds - passed position = ", i, ", new list size = ");
                sbM24m.append(this.mNewListSize);
                throw new IndexOutOfBoundsException(sbM24m.toString());
            }
            int i2 = this.mNewItemStatuses[i];
            if ((i2 & 15) == 0) {
                return -1;
            }
            return i2 >> 4;
        }

        public int convertOldPositionToNew(@IntRange(from = 0) int i) {
            if (i < 0 || i >= this.mOldListSize) {
                StringBuilder sbM24m = Insets$$ExternalSyntheticOutline0.m24m("Index out of bounds - passed position = ", i, ", old list size = ");
                sbM24m.append(this.mOldListSize);
                throw new IndexOutOfBoundsException(sbM24m.toString());
            }
            int i2 = this.mOldItemStatuses[i];
            if ((i2 & 15) == 0) {
                return -1;
            }
            return i2 >> 4;
        }

        public void dispatchUpdatesTo(@NonNull RecyclerView.Adapter adapter) {
            dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }

        public void dispatchUpdatesTo(@NonNull ListUpdateCallback listUpdateCallback) {
            int i;
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            int i2 = this.mOldListSize;
            ArrayDeque arrayDeque = new ArrayDeque();
            int i3 = this.mOldListSize;
            int i4 = this.mNewListSize;
            for (int size = this.mDiagonals.size() - 1; size >= 0; size--) {
                Diagonal diagonal = this.mDiagonals.get(size);
                int i5 = diagonal.x;
                int i6 = diagonal.size;
                int i7 = i5 + i6;
                int i8 = diagonal.y + i6;
                while (true) {
                    if (i3 <= i7) {
                        break;
                    }
                    i3--;
                    int i9 = this.mOldItemStatuses[i3];
                    if ((i9 & 12) != 0) {
                        int i10 = i9 >> 4;
                        PostponedUpdate postponedUpdate = getPostponedUpdate(arrayDeque, i10, false);
                        if (postponedUpdate != null) {
                            int i11 = (i2 - postponedUpdate.currentPos) - 1;
                            batchingListUpdateCallback.onMoved(i3, i11);
                            if ((i9 & 4) != 0) {
                                batchingListUpdateCallback.onChanged(i11, 1, this.mCallback.getChangePayload(i3, i10));
                            }
                        } else {
                            arrayDeque.add(new PostponedUpdate(i3, (i2 - i3) - 1, true));
                        }
                    } else {
                        batchingListUpdateCallback.onRemoved(i3, 1);
                        i2--;
                    }
                }
                while (i4 > i8) {
                    i4--;
                    int i12 = this.mNewItemStatuses[i4];
                    if ((i12 & 12) != 0) {
                        int i13 = i12 >> 4;
                        PostponedUpdate postponedUpdate2 = getPostponedUpdate(arrayDeque, i13, true);
                        if (postponedUpdate2 == null) {
                            arrayDeque.add(new PostponedUpdate(i4, i2 - i3, false));
                        } else {
                            batchingListUpdateCallback.onMoved((i2 - postponedUpdate2.currentPos) - 1, i3);
                            if ((i12 & 4) != 0) {
                                batchingListUpdateCallback.onChanged(i3, 1, this.mCallback.getChangePayload(i13, i4));
                            }
                        }
                    } else {
                        batchingListUpdateCallback.onInserted(i3, 1);
                        i2++;
                    }
                }
                int i14 = diagonal.x;
                int i15 = diagonal.y;
                for (i = 0; i < diagonal.size; i++) {
                    if ((this.mOldItemStatuses[i14] & 15) == 2) {
                        batchingListUpdateCallback.onChanged(i14, 1, this.mCallback.getChangePayload(i14, i15));
                    }
                    i14++;
                    i15++;
                }
                i3 = diagonal.x;
                i4 = diagonal.y;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(@NonNull T t, @NonNull T t2);

        public abstract boolean areItemsTheSame(@NonNull T t, @NonNull T t2);

        @Nullable
        public Object getChangePayload(@NonNull T t, @NonNull T t2) {
            return null;
        }
    }

    public static class PostponedUpdate {
        public int currentPos;
        public int posInOwnerList;
        public boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.posInOwnerList = i;
            this.currentPos = i2;
            this.removal = z;
        }
    }

    public static class Range {
        public int newListEnd;
        public int newListStart;
        public int oldListEnd;
        public int oldListStart;

        public Range() {
        }

        public Range(int i, int i2, int i3, int i4) {
            this.oldListStart = i;
            this.oldListEnd = i2;
            this.newListStart = i3;
            this.newListEnd = i4;
        }
    }

    public static class Snake {
        public int endX;
        public int endY;
        public boolean reverse;
        public int startX;
        public int startY;

        public final int diagonalSize() {
            return Math.min(this.endX - this.startX, this.endY - this.startY);
        }
    }

    private DiffUtil() {
    }

    @Nullable
    private static Snake backward(Range range, Callback callback, CenteredArray centeredArray, CenteredArray centeredArray2, int i) {
        int i2;
        int i3;
        int i4;
        int i5 = range.oldListEnd;
        int i6 = range.oldListStart;
        int i7 = range.newListEnd;
        int i8 = range.newListStart;
        boolean z = ((i5 - i6) - (i7 - i8)) % 2 == 0;
        int i9 = (i5 - i6) - (i7 - i8);
        int i10 = -i;
        for (int i11 = i10; i11 <= i; i11 += 2) {
            if (i11 == i10 || (i11 != i && centeredArray2.get(i11 + 1) < centeredArray2.get(i11 - 1))) {
                i2 = centeredArray2.get(i11 + 1);
                i3 = i2;
            } else {
                i2 = centeredArray2.get(i11 - 1);
                i3 = i2 - 1;
            }
            int i12 = range.newListEnd - ((range.oldListEnd - i3) - i11);
            int i13 = (i == 0 || i3 != i2) ? i12 : i12 + 1;
            while (i3 > range.oldListStart && i12 > range.newListStart && callback.areItemsTheSame(i3 - 1, i12 - 1)) {
                i3--;
                i12--;
            }
            centeredArray2.set(i11, i3);
            if (z && (i4 = i9 - i11) >= i10 && i4 <= i && centeredArray.get(i4) >= i3) {
                Snake snake = new Snake();
                snake.startX = i3;
                snake.startY = i12;
                snake.endX = i2;
                snake.endY = i13;
                snake.reverse = true;
                return snake;
            }
        }
        return null;
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback) {
        return calculateDiff(callback, true);
    }

    @Nullable
    private static Snake forward(Range range, Callback callback, CenteredArray centeredArray, CenteredArray centeredArray2, int i) {
        int i2;
        int i3;
        int i4;
        boolean z = Math.abs((range.oldListEnd - range.oldListStart) - (range.newListEnd - range.newListStart)) % 2 == 1;
        int i5 = (range.oldListEnd - range.oldListStart) - (range.newListEnd - range.newListStart);
        int i6 = -i;
        for (int i7 = i6; i7 <= i; i7 += 2) {
            if (i7 == i6 || (i7 != i && centeredArray.get(i7 + 1) > centeredArray.get(i7 - 1))) {
                i2 = centeredArray.get(i7 + 1);
                i3 = i2;
            } else {
                i2 = centeredArray.get(i7 - 1);
                i3 = i2 + 1;
            }
            int i8 = ((i3 - range.oldListStart) + range.newListStart) - i7;
            int i9 = (i == 0 || i3 != i2) ? i8 : i8 - 1;
            while (i3 < range.oldListEnd && i8 < range.newListEnd && callback.areItemsTheSame(i3, i8)) {
                i3++;
                i8++;
            }
            centeredArray.set(i7, i3);
            if (z && (i4 = i5 - i7) >= i6 + 1 && i4 <= i - 1 && centeredArray2.get(i4) <= i3) {
                Snake snake = new Snake();
                snake.startX = i2;
                snake.startY = i9;
                snake.endX = i3;
                snake.endY = i8;
                snake.reverse = false;
                return snake;
            }
        }
        return null;
    }

    @Nullable
    private static Snake midPoint(Range range, Callback callback, CenteredArray centeredArray, CenteredArray centeredArray2) {
        int i = range.oldListEnd;
        int i2 = range.oldListStart;
        if (i - i2 >= 1) {
            int i3 = range.newListEnd;
            int i4 = range.newListStart;
            if (i3 - i4 >= 1) {
                int i5 = (((i3 - i4) + (i - i2)) + 1) / 2;
                centeredArray.set(1, i2);
                centeredArray2.set(1, range.oldListEnd);
                for (int i6 = 0; i6 < i5; i6++) {
                    Snake snakeForward = forward(range, callback, centeredArray, centeredArray2, i6);
                    if (snakeForward != null) {
                        return snakeForward;
                    }
                    Snake snakeBackward = backward(range, callback, centeredArray, centeredArray2, i6);
                    if (snakeBackward != null) {
                        return snakeBackward;
                    }
                }
            }
        }
        return null;
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback, boolean z) {
        Diagonal diagonal;
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int i = ((((oldListSize + newListSize) + 1) / 2) * 2) + 1;
        CenteredArray centeredArray = new CenteredArray(i);
        CenteredArray centeredArray2 = new CenteredArray(i);
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            Range range = (Range) arrayList2.remove(arrayList2.size() - 1);
            Snake snakeMidPoint = midPoint(range, callback, centeredArray, centeredArray2);
            if (snakeMidPoint != null) {
                if (snakeMidPoint.diagonalSize() > 0) {
                    int i2 = snakeMidPoint.endY;
                    int i3 = snakeMidPoint.startY;
                    int i4 = i2 - i3;
                    int i5 = snakeMidPoint.endX;
                    int i6 = snakeMidPoint.startX;
                    int i7 = i5 - i6;
                    if (!(i4 != i7)) {
                        diagonal = new Diagonal(i6, i3, i7);
                    } else if (snakeMidPoint.reverse) {
                        diagonal = new Diagonal(i6, i3, snakeMidPoint.diagonalSize());
                    } else {
                        diagonal = i4 > i7 ? new Diagonal(i6, i3 + 1, snakeMidPoint.diagonalSize()) : new Diagonal(i6 + 1, i3, snakeMidPoint.diagonalSize());
                    }
                    arrayList.add(diagonal);
                }
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range) arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                range2.oldListEnd = snakeMidPoint.startX;
                range2.newListEnd = snakeMidPoint.startY;
                arrayList2.add(range2);
                range.oldListEnd = range.oldListEnd;
                range.newListEnd = range.newListEnd;
                range.oldListStart = snakeMidPoint.endX;
                range.newListStart = snakeMidPoint.endY;
                arrayList2.add(range);
            } else {
                arrayList3.add(range);
            }
        }
        Collections.sort(arrayList, DIAGONAL_COMPARATOR);
        return new DiffResult(callback, arrayList, centeredArray.backingData(), centeredArray2.backingData(), z);
    }
}
