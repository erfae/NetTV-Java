package androidx.leanback.widget;

import android.database.Cursor;
import android.util.LruCache;
import androidx.leanback.database.CursorMapper;

/* JADX INFO: loaded from: classes.dex */
public class CursorObjectAdapter extends ObjectAdapter {
    private static final int CACHE_SIZE = 100;
    private Cursor mCursor;
    private final LruCache<Integer, Object> mItemCache;
    private CursorMapper mMapper;

    public CursorObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
        this.mItemCache = new LruCache<>(100);
    }

    public void changeCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return;
        }
        if (cursor2 != null) {
            cursor2.close();
        }
        this.mCursor = cursor;
        this.mItemCache.trimToSize(0);
        notifyChanged();
    }

    public void close() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            cursor.close();
            this.mCursor = null;
        }
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public Object get(int i) {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return null;
        }
        if (!cursor.moveToPosition(i)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Object obj = this.mItemCache.get(Integer.valueOf(i));
        if (obj != null) {
            return obj;
        }
        Object objConvert = this.mMapper.convert(this.mCursor);
        this.mItemCache.put(Integer.valueOf(i), objConvert);
        return objConvert;
    }

    public final Cursor getCursor() {
        return this.mCursor;
    }

    public final CursorMapper getMapper() {
        return this.mMapper;
    }

    public boolean isClosed() {
        Cursor cursor = this.mCursor;
        return cursor == null || cursor.isClosed();
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public boolean isImmediateNotifySupported() {
        return true;
    }

    public final void setMapper(CursorMapper cursorMapper) {
        this.mMapper = cursorMapper;
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public int size() {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return cursor2;
        }
        this.mCursor = cursor;
        this.mItemCache.trimToSize(0);
        notifyChanged();
        return cursor2;
    }

    public CursorObjectAdapter(Presenter presenter) {
        super(presenter);
        this.mItemCache = new LruCache<>(100);
    }

    public CursorObjectAdapter() {
        this.mItemCache = new LruCache<>(100);
    }
}
