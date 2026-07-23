package androidx.leanback.app;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Row;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class ListRowDataAdapter extends ObjectAdapter {
    public static final int ON_CHANGED = 16;
    public static final int ON_ITEM_RANGE_CHANGED = 2;
    public static final int ON_ITEM_RANGE_INSERTED = 4;
    public static final int ON_ITEM_RANGE_REMOVED = 8;
    private final ObjectAdapter mAdapter;
    public final ObjectAdapter.DataObserver mDataObserver;
    public int mLastVisibleRowIndex;

    public class QueueBasedDataObserver extends ObjectAdapter.DataObserver {
        public QueueBasedDataObserver() {
        }

        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onChanged() {
            ListRowDataAdapter.this.initialize();
            ListRowDataAdapter.this.notifyChanged();
        }
    }

    public class SimpleDataObserver extends ObjectAdapter.DataObserver {
        public SimpleDataObserver() {
        }

        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onChanged() {
            ListRowDataAdapter.this.initialize();
            onEventFired(16, -1, -1);
        }

        public final void onEventFired(int i, int i2, int i3) {
            ListRowDataAdapter listRowDataAdapter = ListRowDataAdapter.this;
            Objects.requireNonNull(listRowDataAdapter);
            if (i == 2) {
                listRowDataAdapter.notifyItemRangeChanged(i2, i3);
                return;
            }
            if (i == 4) {
                listRowDataAdapter.notifyItemRangeInserted(i2, i3);
            } else if (i == 8) {
                listRowDataAdapter.notifyItemRangeRemoved(i2, i3);
            } else {
                if (i != 16) {
                    throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Invalid event type ", i));
                }
                listRowDataAdapter.notifyChanged();
            }
        }

        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onItemRangeChanged(int i, int i2) {
            int i3 = ListRowDataAdapter.this.mLastVisibleRowIndex;
            if (i <= i3) {
                onEventFired(2, i, Math.min(i2, (i3 - i) + 1));
            }
        }

        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onItemRangeInserted(int i, int i2) {
            ListRowDataAdapter listRowDataAdapter = ListRowDataAdapter.this;
            int i3 = listRowDataAdapter.mLastVisibleRowIndex;
            if (i <= i3) {
                listRowDataAdapter.mLastVisibleRowIndex = i3 + i2;
                onEventFired(4, i, i2);
                return;
            }
            listRowDataAdapter.initialize();
            int i4 = ListRowDataAdapter.this.mLastVisibleRowIndex;
            if (i4 > i3) {
                onEventFired(4, i3 + 1, i4 - i3);
            }
        }

        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onItemRangeRemoved(int i, int i2) {
            int i3 = (i + i2) - 1;
            ListRowDataAdapter listRowDataAdapter = ListRowDataAdapter.this;
            int i4 = listRowDataAdapter.mLastVisibleRowIndex;
            if (i3 < i4) {
                listRowDataAdapter.mLastVisibleRowIndex = i4 - i2;
                onEventFired(8, i, i2);
                return;
            }
            listRowDataAdapter.initialize();
            int i5 = ListRowDataAdapter.this.mLastVisibleRowIndex;
            int i6 = i4 - i5;
            if (i6 > 0) {
                onEventFired(8, Math.min(i5 + 1, i), i6);
            }
        }
    }

    public ListRowDataAdapter(ObjectAdapter objectAdapter) {
        super(objectAdapter.getPresenterSelector());
        this.mAdapter = objectAdapter;
        initialize();
        if (objectAdapter.isImmediateNotifySupported()) {
            this.mDataObserver = new SimpleDataObserver();
        } else {
            this.mDataObserver = new QueueBasedDataObserver();
        }
        initialize();
        objectAdapter.registerObserver(this.mDataObserver);
    }

    public final void detach() {
        this.mAdapter.unregisterObserver(this.mDataObserver);
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public Object get(int i) {
        return this.mAdapter.get(i);
    }

    public final void initialize() {
        this.mLastVisibleRowIndex = -1;
        for (int size = this.mAdapter.size() - 1; size >= 0; size--) {
            if (((Row) this.mAdapter.get(size)).isRenderedAsRowView()) {
                this.mLastVisibleRowIndex = size;
                return;
            }
        }
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public int size() {
        return this.mLastVisibleRowIndex + 1;
    }
}
