package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
class TileList<T> {
    public Tile<T> mLastAccessedTile;
    public final int mTileSize;
    private final SparseArray<Tile<T>> mTiles = new SparseArray<>(10);

    public static class Tile<T> {
        public int mItemCount;
        public final T[] mItems;
        public Tile<T> mNext;
        public int mStartPosition;

        public Tile(Class<T> cls, int i) {
            this.mItems = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        }
    }

    public TileList(int i) {
        this.mTileSize = i;
    }

    public Tile<T> addOrReplace(Tile<T> tile) {
        int iIndexOfKey = this.mTiles.indexOfKey(tile.mStartPosition);
        if (iIndexOfKey < 0) {
            this.mTiles.put(tile.mStartPosition, tile);
            return null;
        }
        Tile<T> tileValueAt = this.mTiles.valueAt(iIndexOfKey);
        this.mTiles.setValueAt(iIndexOfKey, tile);
        if (this.mLastAccessedTile == tileValueAt) {
            this.mLastAccessedTile = tile;
        }
        return tileValueAt;
    }

    public void clear() {
        this.mTiles.clear();
    }

    public Tile<T> getAtIndex(int i) {
        if (i < 0 || i >= this.mTiles.size()) {
            return null;
        }
        return this.mTiles.valueAt(i);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0012  */
    /* JADX WARN: Code duplicated, block: B:13:0x0020 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:15:0x0022  */
    public T getItemAt(int i) {
        int iIndexOfKey;
        Tile<T> tile = this.mLastAccessedTile;
        if (tile == null) {
            iIndexOfKey = this.mTiles.indexOfKey(i - (i % this.mTileSize));
            if (iIndexOfKey < 0) {
                return null;
            }
            this.mLastAccessedTile = this.mTiles.valueAt(iIndexOfKey);
        } else {
            int i2 = tile.mStartPosition;
            if (!(i2 <= i && i < i2 + tile.mItemCount)) {
                iIndexOfKey = this.mTiles.indexOfKey(i - (i % this.mTileSize));
                if (iIndexOfKey < 0) {
                    return null;
                }
                this.mLastAccessedTile = this.mTiles.valueAt(iIndexOfKey);
            }
        }
        Tile<T> tile2 = this.mLastAccessedTile;
        return tile2.mItems[i - tile2.mStartPosition];
    }

    public Tile<T> removeAtPos(int i) {
        Tile<T> tile = this.mTiles.get(i);
        if (this.mLastAccessedTile == tile) {
            this.mLastAccessedTile = null;
        }
        this.mTiles.delete(i);
        return tile;
    }

    public int size() {
        return this.mTiles.size();
    }
}
