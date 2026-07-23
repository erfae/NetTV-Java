package androidx.leanback.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
abstract class BaseRowFragment extends Fragment {
    private static final String CURRENT_SELECTED_POSITION = "currentSelectedPosition";
    private ObjectAdapter mAdapter;
    private boolean mPendingTransitionPrepare;
    private PresenterSelector mPresenterSelector;
    public VerticalGridView mVerticalGridView;
    public final ItemBridgeAdapter mBridgeAdapter = new ItemBridgeAdapter();
    public int mSelectedPosition = -1;
    public LateSelectionObserver mLateSelectionObserver = new LateSelectionObserver();
    private final OnChildViewHolderSelectedListener mRowSelectedListener = new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.app.BaseRowFragment.1
        @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
        public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
            BaseRowFragment baseRowFragment = BaseRowFragment.this;
            if (baseRowFragment.mLateSelectionObserver.mIsLateSelection) {
                return;
            }
            baseRowFragment.mSelectedPosition = i;
            baseRowFragment.onRowSelected(viewHolder, i, i2);
        }
    };

    public class LateSelectionObserver extends RecyclerView.AdapterDataObserver {
        public boolean mIsLateSelection = false;

        public LateSelectionObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            performLateSelection();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            performLateSelection();
        }

        public final void performLateSelection() {
            if (this.mIsLateSelection) {
                this.mIsLateSelection = false;
                BaseRowFragment.this.mBridgeAdapter.unregisterAdapterDataObserver(this);
            }
            BaseRowFragment baseRowFragment = BaseRowFragment.this;
            VerticalGridView verticalGridView = baseRowFragment.mVerticalGridView;
            if (verticalGridView != null) {
                verticalGridView.setSelectedPosition(baseRowFragment.mSelectedPosition);
            }
        }
    }

    public VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view;
    }

    public final ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public final ItemBridgeAdapter getBridgeAdapter() {
        return this.mBridgeAdapter;
    }

    public abstract int getLayoutResourceId();

    public final PresenterSelector getPresenterSelector() {
        return this.mPresenterSelector;
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public final VerticalGridView getVerticalGridView() {
        return this.mVerticalGridView;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(getLayoutResourceId(), viewGroup, false);
        this.mVerticalGridView = findGridViewFromRoot(viewInflate);
        if (this.mPendingTransitionPrepare) {
            this.mPendingTransitionPrepare = false;
            onTransitionPrepare();
        }
        return viewInflate;
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        LateSelectionObserver lateSelectionObserver = this.mLateSelectionObserver;
        if (lateSelectionObserver.mIsLateSelection) {
            lateSelectionObserver.mIsLateSelection = false;
            BaseRowFragment.this.mBridgeAdapter.unregisterAdapterDataObserver(lateSelectionObserver);
        }
        this.mVerticalGridView = null;
    }

    public void onRowSelected(RecyclerView.ViewHolder viewHolder, int i, int i2) {
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(CURRENT_SELECTED_POSITION, this.mSelectedPosition);
    }

    public void onTransitionEnd() {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setLayoutFrozen(false);
            this.mVerticalGridView.setAnimateChildLayout(true);
            this.mVerticalGridView.setPruneChild(true);
            this.mVerticalGridView.setFocusSearchDisabled(false);
            this.mVerticalGridView.setScrollEnabled(true);
        }
    }

    public boolean onTransitionPrepare() {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView == null) {
            this.mPendingTransitionPrepare = true;
            return false;
        }
        verticalGridView.setAnimateChildLayout(false);
        this.mVerticalGridView.setScrollEnabled(false);
        return true;
    }

    public void onTransitionStart() {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setPruneChild(false);
            this.mVerticalGridView.setLayoutFrozen(true);
            this.mVerticalGridView.setFocusSearchDisabled(true);
        }
    }

    @Override // android.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        if (bundle != null) {
            this.mSelectedPosition = bundle.getInt(CURRENT_SELECTED_POSITION, -1);
        }
        setAdapterAndSelection();
        this.mVerticalGridView.setOnChildViewHolderSelectedListener(this.mRowSelectedListener);
    }

    public final void setAdapter(ObjectAdapter objectAdapter) {
        if (this.mAdapter != objectAdapter) {
            this.mAdapter = objectAdapter;
            updateAdapter();
        }
    }

    public final void setAdapterAndSelection() {
        if (this.mAdapter == null) {
            return;
        }
        RecyclerView.Adapter adapter = this.mVerticalGridView.getAdapter();
        ItemBridgeAdapter itemBridgeAdapter = this.mBridgeAdapter;
        if (adapter != itemBridgeAdapter) {
            this.mVerticalGridView.setAdapter(itemBridgeAdapter);
        }
        if (this.mBridgeAdapter.getItemCount() == 0 && this.mSelectedPosition >= 0) {
            LateSelectionObserver lateSelectionObserver = this.mLateSelectionObserver;
            lateSelectionObserver.mIsLateSelection = true;
            BaseRowFragment.this.mBridgeAdapter.registerAdapterDataObserver(lateSelectionObserver);
        } else {
            int i = this.mSelectedPosition;
            if (i >= 0) {
                this.mVerticalGridView.setSelectedPosition(i);
            }
        }
    }

    public void setAlignment(int i) {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setItemAlignmentOffset(0);
            this.mVerticalGridView.setItemAlignmentOffsetPercent(-1.0f);
            this.mVerticalGridView.setWindowAlignmentOffset(i);
            this.mVerticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
            this.mVerticalGridView.setWindowAlignment(0);
        }
    }

    public final void setPresenterSelector(PresenterSelector presenterSelector) {
        if (this.mPresenterSelector != presenterSelector) {
            this.mPresenterSelector = presenterSelector;
            updateAdapter();
        }
    }

    public void setSelectedPosition(int i) {
        setSelectedPosition(i, true);
    }

    public void updateAdapter() {
        this.mBridgeAdapter.setAdapter(this.mAdapter);
        this.mBridgeAdapter.setPresenter(this.mPresenterSelector);
        if (this.mVerticalGridView != null) {
            setAdapterAndSelection();
        }
    }

    public void setSelectedPosition(int i, boolean z) {
        if (this.mSelectedPosition == i) {
            return;
        }
        this.mSelectedPosition = i;
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView == null || this.mLateSelectionObserver.mIsLateSelection) {
            return;
        }
        if (z) {
            verticalGridView.setSelectedPositionSmooth(i);
        } else {
            verticalGridView.setSelectedPosition(i);
        }
    }
}
