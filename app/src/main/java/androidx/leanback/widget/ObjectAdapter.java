package androidx.leanback.widget;

import android.database.Observable;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
public abstract class ObjectAdapter {
    public static final int NO_ID = -1;
    private boolean mHasStableIds;
    private final DataObservable mObservable = new DataObservable();
    private PresenterSelector mPresenterSelector;

    public static abstract class DataObserver {
        public void onChanged() {
        }

        public void onItemMoved(int i, int i2) {
            onChanged();
        }

        public void onItemRangeChanged(int i, int i2) {
            onChanged();
        }

        public void onItemRangeInserted(int i, int i2) {
            onChanged();
        }

        public void onItemRangeRemoved(int i, int i2) {
            onChanged();
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            onChanged();
        }
    }

    public ObjectAdapter(PresenterSelector presenterSelector) {
        setPresenterSelector(presenterSelector);
    }

    public abstract Object get(int i);

    public long getId(int i) {
        return -1L;
    }

    public final Presenter getPresenter(Object obj) {
        PresenterSelector presenterSelector = this.mPresenterSelector;
        if (presenterSelector != null) {
            return presenterSelector.getPresenter(obj);
        }
        throw new IllegalStateException("Presenter selector must not be null");
    }

    public final PresenterSelector getPresenterSelector() {
        return this.mPresenterSelector;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final boolean hasObserver() {
        return this.mObservable.hasObserver();
    }

    public final boolean hasStableIds() {
        return this.mHasStableIds;
    }

    public boolean isImmediateNotifySupported() {
        return false;
    }

    public final void notifyChanged() {
        this.mObservable.notifyChanged();
    }

    public final void notifyItemMoved(int i, int i2) {
        this.mObservable.notifyItemMoved(i, i2);
    }

    public final void notifyItemRangeChanged(int i, int i2) {
        this.mObservable.notifyItemRangeChanged(i, i2);
    }

    public final void notifyItemRangeInserted(int i, int i2) {
        this.mObservable.notifyItemRangeInserted(i, i2);
    }

    public final void notifyItemRangeRemoved(int i, int i2) {
        this.mObservable.notifyItemRangeRemoved(i, i2);
    }

    public final void registerObserver(DataObserver dataObserver) {
        this.mObservable.registerObserver(dataObserver);
    }

    public final void setHasStableIds(boolean z) {
        this.mHasStableIds = z;
    }

    public final void setPresenterSelector(PresenterSelector presenterSelector) {
        if (presenterSelector == null) {
            throw new IllegalArgumentException("Presenter selector must not be null");
        }
        boolean z = this.mPresenterSelector != null;
        this.mPresenterSelector = presenterSelector;
        if (z) {
            notifyChanged();
        }
    }

    public abstract int size();

    public final void unregisterAllObservers() {
        this.mObservable.unregisterAll();
    }

    public final void unregisterObserver(DataObserver dataObserver) {
        this.mObservable.unregisterObserver(dataObserver);
    }

    public static final class DataObservable extends Observable<DataObserver> {
        public final boolean hasObserver() {
            return ((Observable) this).mObservers.size() > 0;
        }

        public void notifyChanged() {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((DataObserver) ((Observable) this).mObservers.get(size)).onChanged();
            }
        }

        public void notifyItemMoved(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((DataObserver) ((Observable) this).mObservers.get(size)).onItemMoved(i, i2);
            }
        }

        public void notifyItemRangeChanged(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((DataObserver) ((Observable) this).mObservers.get(size)).onItemRangeChanged(i, i2);
            }
        }

        public void notifyItemRangeInserted(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((DataObserver) ((Observable) this).mObservers.get(size)).onItemRangeInserted(i, i2);
            }
        }

        public void notifyItemRangeRemoved(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((DataObserver) ((Observable) this).mObservers.get(size)).onItemRangeRemoved(i, i2);
            }
        }

        public void notifyItemRangeChanged(int i, int i2, Object obj) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((DataObserver) ((Observable) this).mObservers.get(size)).onItemRangeChanged(i, i2, obj);
            }
        }
    }

    public final void notifyItemRangeChanged(int i, int i2, Object obj) {
        this.mObservable.notifyItemRangeChanged(i, i2, obj);
    }

    public ObjectAdapter(Presenter presenter) {
        setPresenterSelector(new SinglePresenterSelector(presenter));
    }

    public ObjectAdapter() {
    }
}
