package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DetailsOverviewRow extends Row {
    private ObjectAdapter mActionsAdapter;
    private PresenterSelector mDefaultActionPresenter;
    private Drawable mImageDrawable;
    private boolean mImageScaleUpAllowed;
    private Object mItem;
    private ArrayList<WeakReference<Listener>> mListeners;

    public static class Listener {
        public void onActionsAdapterChanged(DetailsOverviewRow detailsOverviewRow) {
        }

        public void onImageDrawableChanged(DetailsOverviewRow detailsOverviewRow) {
        }

        public void onItemChanged(DetailsOverviewRow detailsOverviewRow) {
        }
    }

    public DetailsOverviewRow(Object obj) {
        super(null);
        this.mImageScaleUpAllowed = true;
        this.mDefaultActionPresenter = new ActionPresenterSelector();
        this.mActionsAdapter = new ArrayObjectAdapter(this.mDefaultActionPresenter);
        this.mItem = obj;
        verify();
    }

    private ArrayObjectAdapter getArrayObjectAdapter() {
        return (ArrayObjectAdapter) this.mActionsAdapter;
    }

    private void verify() {
        if (this.mItem == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
    }

    @Deprecated
    public final void addAction(Action action) {
        getArrayObjectAdapter().add(action);
    }

    public final void addListener(Listener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        } else {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener listener2 = this.mListeners.get(i).get();
                if (listener2 == null) {
                    this.mListeners.remove(i);
                } else if (listener2 == listener) {
                    return;
                } else {
                    i++;
                }
            }
        }
        this.mListeners.add(new WeakReference<>(listener));
    }

    public Action getActionForKeyCode(int i) {
        ObjectAdapter actionsAdapter = getActionsAdapter();
        if (actionsAdapter == null) {
            return null;
        }
        for (int i2 = 0; i2 < actionsAdapter.size(); i2++) {
            Action action = (Action) actionsAdapter.get(i2);
            if (action.respondsToKeyCode(i)) {
                return action;
            }
        }
        return null;
    }

    @Deprecated
    public final List<Action> getActions() {
        return getArrayObjectAdapter().unmodifiableList();
    }

    public final ObjectAdapter getActionsAdapter() {
        return this.mActionsAdapter;
    }

    public final Drawable getImageDrawable() {
        return this.mImageDrawable;
    }

    public final Object getItem() {
        return this.mItem;
    }

    public boolean isImageScaleUpAllowed() {
        return this.mImageScaleUpAllowed;
    }

    public final void notifyImageDrawableChanged() {
        if (this.mListeners != null) {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener listener = this.mListeners.get(i).get();
                if (listener == null) {
                    this.mListeners.remove(i);
                } else {
                    listener.onImageDrawableChanged(this);
                    i++;
                }
            }
        }
    }

    @Deprecated
    public final boolean removeAction(Action action) {
        return getArrayObjectAdapter().remove(action);
    }

    public final void removeListener(Listener listener) {
        if (this.mListeners != null) {
            int i = 0;
            while (i < this.mListeners.size()) {
                Listener listener2 = this.mListeners.get(i).get();
                if (listener2 == null) {
                    this.mListeners.remove(i);
                } else {
                    if (listener2 == listener) {
                        this.mListeners.remove(i);
                        return;
                    }
                    i++;
                }
            }
        }
    }

    public final void setActionsAdapter(ObjectAdapter objectAdapter) {
        if (objectAdapter != this.mActionsAdapter) {
            this.mActionsAdapter = objectAdapter;
            if (objectAdapter.getPresenterSelector() == null) {
                this.mActionsAdapter.setPresenterSelector(this.mDefaultActionPresenter);
            }
            if (this.mListeners != null) {
                int i = 0;
                while (i < this.mListeners.size()) {
                    Listener listener = this.mListeners.get(i).get();
                    if (listener == null) {
                        this.mListeners.remove(i);
                    } else {
                        listener.onActionsAdapterChanged(this);
                        i++;
                    }
                }
            }
        }
    }

    public final void setImageBitmap(Context context, Bitmap bitmap) {
        this.mImageDrawable = new BitmapDrawable(context.getResources(), bitmap);
        notifyImageDrawableChanged();
    }

    public final void setImageDrawable(Drawable drawable) {
        if (this.mImageDrawable != drawable) {
            this.mImageDrawable = drawable;
            notifyImageDrawableChanged();
        }
    }

    public void setImageScaleUpAllowed(boolean z) {
        if (z != this.mImageScaleUpAllowed) {
            this.mImageScaleUpAllowed = z;
            notifyImageDrawableChanged();
        }
    }

    public final void setItem(Object obj) {
        if (obj != this.mItem) {
            this.mItem = obj;
            if (this.mListeners != null) {
                int i = 0;
                while (i < this.mListeners.size()) {
                    Listener listener = this.mListeners.get(i).get();
                    if (listener == null) {
                        this.mListeners.remove(i);
                    } else {
                        listener.onItemChanged(this);
                        i++;
                    }
                }
            }
        }
    }

    @Deprecated
    public final void addAction(int i, Action action) {
        getArrayObjectAdapter().add(i, action);
    }
}
