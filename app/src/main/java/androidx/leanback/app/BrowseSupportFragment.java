package androidx.leanback.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.ColorInt;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.InvisibleRowPresenter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.PageRow;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.ScaleFrameLayout;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class BrowseSupportFragment extends BaseSupportFragment {
    private static final String CURRENT_SELECTED_POSITION = "currentSelectedPosition";
    public static final int HEADERS_DISABLED = 3;
    public static final int HEADERS_ENABLED = 1;
    public static final int HEADERS_HIDDEN = 2;
    private static final String IS_PAGE_ROW = "isPageRow";
    private static final String LB_HEADERS_BACKSTACK = "lbHeadersBackStack_";
    private ObjectAdapter mAdapter;
    private PresenterSelector mAdapterPresenter;
    public BackStackListener mBackStackChangedListener;
    private boolean mBrandColorSet;
    public BrowseFrameLayout mBrowseFrame;
    public BrowseTransitionListener mBrowseTransitionListener;
    private int mContainerListAlignTop;
    private int mContainerListMarginStart;
    public OnItemViewSelectedListener mExternalOnItemViewSelectedListener;
    private PresenterSelector mHeaderPresenterSelector;
    public HeadersSupportFragment mHeadersSupportFragment;
    public Object mHeadersTransition;
    public boolean mIsPageRow;
    public Fragment mMainFragment;
    public MainFragmentAdapter mMainFragmentAdapter;
    public ListRowDataAdapter mMainFragmentListRowDataAdapter;
    public MainFragmentRowsAdapter mMainFragmentRowsAdapter;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    public Object mPageRow;
    private float mScaleFactor;
    private ScaleFrameLayout mScaleFrameLayout;
    private Object mSceneAfterEntranceTransition;
    public Object mSceneWithHeaders;
    public Object mSceneWithoutHeaders;
    public String mWithHeadersBackStackName;
    private static final String ARG_TITLE = BrowseSupportFragment.class.getCanonicalName() + ".title";
    private static final String ARG_HEADERS_STATE = BrowseSupportFragment.class.getCanonicalName() + ".headersState";
    public final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State() { // from class: androidx.leanback.app.BrowseSupportFragment.1
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BrowseSupportFragment.this.setEntranceTransitionStartState();
        }
    };
    public final StateMachine.Event EVT_HEADER_VIEW_CREATED = new StateMachine.Event("headerFragmentViewCreated");
    public final StateMachine.Event EVT_MAIN_FRAGMENT_VIEW_CREATED = new StateMachine.Event("mainFragmentViewCreated");
    public final StateMachine.Event EVT_SCREEN_DATA_READY = new StateMachine.Event("screenDataReady");
    private MainFragmentAdapterRegistry mMainFragmentAdapterRegistry = new MainFragmentAdapterRegistry();
    private int mHeadersState = 1;
    private int mBrandColor = 0;
    public boolean mHeadersBackStackEnabled = true;
    public boolean mShowingHeaders = true;
    public boolean mCanShowHeaders = true;
    private boolean mMainFragmentScaleEnabled = true;
    private int mSelectedPosition = -1;
    public boolean mStopped = true;
    private final SetSelectionRunnable mSetSelectionRunnable = new SetSelectionRunnable();
    private final BrowseFrameLayout.OnFocusSearchListener mOnFocusSearchListener = new BrowseFrameLayout.OnFocusSearchListener() { // from class: androidx.leanback.app.BrowseSupportFragment.4
        @Override // androidx.leanback.widget.BrowseFrameLayout.OnFocusSearchListener
        public View onFocusSearch(View view, int i) {
            Fragment fragment;
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (browseSupportFragment.mCanShowHeaders && browseSupportFragment.isInHeadersTransition()) {
                return view;
            }
            if (BrowseSupportFragment.this.getTitleView() != null && view != BrowseSupportFragment.this.getTitleView() && i == 33) {
                return BrowseSupportFragment.this.getTitleView();
            }
            if (BrowseSupportFragment.this.getTitleView() != null && BrowseSupportFragment.this.getTitleView().hasFocus() && i == 130) {
                BrowseSupportFragment browseSupportFragment2 = BrowseSupportFragment.this;
                return (browseSupportFragment2.mCanShowHeaders && browseSupportFragment2.mShowingHeaders) ? browseSupportFragment2.mHeadersSupportFragment.getVerticalGridView() : browseSupportFragment2.mMainFragment.getView();
            }
            boolean z = ViewCompat.getLayoutDirection(view) == 1;
            int i2 = z ? 66 : 17;
            int i3 = z ? 17 : 66;
            BrowseSupportFragment browseSupportFragment3 = BrowseSupportFragment.this;
            if (browseSupportFragment3.mCanShowHeaders && i == i2) {
                if (browseSupportFragment3.isVerticalScrolling()) {
                    return view;
                }
                BrowseSupportFragment browseSupportFragment4 = BrowseSupportFragment.this;
                return (browseSupportFragment4.mShowingHeaders || !browseSupportFragment4.isHeadersDataReady()) ? view : BrowseSupportFragment.this.mHeadersSupportFragment.getVerticalGridView();
            }
            if (i == i3) {
                return (browseSupportFragment3.isVerticalScrolling() || (fragment = BrowseSupportFragment.this.mMainFragment) == null || fragment.getView() == null) ? view : BrowseSupportFragment.this.mMainFragment.getView();
            }
            if (i == 130 && browseSupportFragment3.mShowingHeaders) {
                return view;
            }
            return null;
        }
    };
    private final BrowseFrameLayout.OnChildFocusListener mOnChildFocusListener = new BrowseFrameLayout.OnChildFocusListener() { // from class: androidx.leanback.app.BrowseSupportFragment.5
        @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
        public void onRequestChildFocus(View view, View view2) {
            if (BrowseSupportFragment.this.getChildFragmentManager().isDestroyed()) {
                return;
            }
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (!browseSupportFragment.mCanShowHeaders || browseSupportFragment.isInHeadersTransition()) {
                return;
            }
            int id = view.getId();
            if (id == R.id.browse_container_dock) {
                BrowseSupportFragment browseSupportFragment2 = BrowseSupportFragment.this;
                if (browseSupportFragment2.mShowingHeaders) {
                    browseSupportFragment2.startHeadersTransitionInternal(false);
                    return;
                }
            }
            if (id == R.id.browse_headers_dock) {
                BrowseSupportFragment browseSupportFragment3 = BrowseSupportFragment.this;
                if (browseSupportFragment3.mShowingHeaders) {
                    return;
                }
                browseSupportFragment3.startHeadersTransitionInternal(true);
            }
        }

        @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
        public boolean onRequestFocusInDescendants(int i, Rect rect) {
            HeadersSupportFragment headersSupportFragment;
            if (BrowseSupportFragment.this.getChildFragmentManager().isDestroyed()) {
                return true;
            }
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (browseSupportFragment.mCanShowHeaders && browseSupportFragment.mShowingHeaders && (headersSupportFragment = browseSupportFragment.mHeadersSupportFragment) != null && headersSupportFragment.getView() != null && BrowseSupportFragment.this.mHeadersSupportFragment.getView().requestFocus(i, rect)) {
                return true;
            }
            Fragment fragment = BrowseSupportFragment.this.mMainFragment;
            if (fragment == null || fragment.getView() == null || !BrowseSupportFragment.this.mMainFragment.getView().requestFocus(i, rect)) {
                return BrowseSupportFragment.this.getTitleView() != null && BrowseSupportFragment.this.getTitleView().requestFocus(i, rect);
            }
            return true;
        }
    };
    private HeadersSupportFragment.OnHeaderClickedListener mHeaderClickedListener = new HeadersSupportFragment.OnHeaderClickedListener() { // from class: androidx.leanback.app.BrowseSupportFragment.10
        @Override // androidx.leanback.app.HeadersSupportFragment.OnHeaderClickedListener
        public void onHeaderClicked(RowHeaderPresenter.ViewHolder viewHolder, Row row) {
            Fragment fragment;
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (!browseSupportFragment.mCanShowHeaders || !browseSupportFragment.mShowingHeaders || browseSupportFragment.isInHeadersTransition() || (fragment = BrowseSupportFragment.this.mMainFragment) == null || fragment.getView() == null) {
                return;
            }
            BrowseSupportFragment.this.startHeadersTransitionInternal(false);
            BrowseSupportFragment.this.mMainFragment.getView().requestFocus();
        }
    };
    private HeadersSupportFragment.OnHeaderViewSelectedListener mHeaderViewSelectedListener = new HeadersSupportFragment.OnHeaderViewSelectedListener() { // from class: androidx.leanback.app.BrowseSupportFragment.11
        @Override // androidx.leanback.app.HeadersSupportFragment.OnHeaderViewSelectedListener
        public void onHeaderSelected(RowHeaderPresenter.ViewHolder viewHolder, Row row) {
            int selectedPosition = BrowseSupportFragment.this.mHeadersSupportFragment.getSelectedPosition();
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (browseSupportFragment.mShowingHeaders) {
                browseSupportFragment.onRowSelected(selectedPosition);
            }
        }
    };
    private final RecyclerView.OnScrollListener mWaitScrollFinishAndCommitMainFragment = new RecyclerView.OnScrollListener() { // from class: androidx.leanback.app.BrowseSupportFragment.12
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 0) {
                recyclerView.removeOnScrollListener(this);
                BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
                if (browseSupportFragment.mStopped) {
                    return;
                }
                browseSupportFragment.commitMainFragment();
            }
        }
    };

    public final class BackStackListener implements FragmentManager.OnBackStackChangedListener {
        public int mIndexOfHeadersBackStack = -1;
        public int mLastEntryCount;

        public BackStackListener() {
            this.mLastEntryCount = BrowseSupportFragment.this.getFragmentManager().getBackStackEntryCount();
        }

        @Override // androidx.fragment.app.FragmentManager.OnBackStackChangedListener
        public void onBackStackChanged() {
            if (BrowseSupportFragment.this.getFragmentManager() == null) {
                Log.w("BrowseSupportFragment", "getFragmentManager() is null, stack:", new Exception());
                return;
            }
            int backStackEntryCount = BrowseSupportFragment.this.getFragmentManager().getBackStackEntryCount();
            int i = this.mLastEntryCount;
            if (backStackEntryCount > i) {
                int i2 = backStackEntryCount - 1;
                if (BrowseSupportFragment.this.mWithHeadersBackStackName.equals(BrowseSupportFragment.this.getFragmentManager().getBackStackEntryAt(i2).getName())) {
                    this.mIndexOfHeadersBackStack = i2;
                }
            } else if (backStackEntryCount < i && this.mIndexOfHeadersBackStack >= backStackEntryCount) {
                if (!BrowseSupportFragment.this.isHeadersDataReady()) {
                    BrowseSupportFragment.this.getFragmentManager().beginTransaction().addToBackStack(BrowseSupportFragment.this.mWithHeadersBackStackName).commit();
                    return;
                }
                this.mIndexOfHeadersBackStack = -1;
                BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
                if (!browseSupportFragment.mShowingHeaders) {
                    browseSupportFragment.startHeadersTransitionInternal(true);
                }
            }
            this.mLastEntryCount = backStackEntryCount;
        }
    }

    public static class BrowseTransitionListener {
        public void onHeadersTransitionStart(boolean z) {
        }

        public void onHeadersTransitionStop(boolean z) {
        }
    }

    public class ExpandPreLayout implements ViewTreeObserver.OnPreDrawListener {
        private final Runnable mCallback;
        private int mState;
        private final View mView;
        private MainFragmentAdapter mainFragmentAdapter;

        public ExpandPreLayout(Runnable runnable, MainFragmentAdapter mainFragmentAdapter, View view) {
            this.mView = view;
            this.mCallback = runnable;
            this.mainFragmentAdapter = mainFragmentAdapter;
        }

        public final void execute() {
            this.mView.getViewTreeObserver().addOnPreDrawListener(this);
            this.mainFragmentAdapter.setExpand(false);
            this.mView.invalidate();
            this.mState = 0;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (BrowseSupportFragment.this.getView() == null || BrowseSupportFragment.this.getContext() == null) {
                this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
            int i = this.mState;
            if (i == 0) {
                this.mainFragmentAdapter.setExpand(true);
                this.mView.invalidate();
                this.mState = 1;
                return false;
            }
            if (i != 1) {
                return false;
            }
            this.mCallback.run();
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mState = 2;
            return false;
        }
    }

    public static abstract class FragmentFactory<T extends Fragment> {
        public abstract T createFragment(Object obj);
    }

    public interface FragmentHost {
        void notifyDataReady(MainFragmentAdapter mainFragmentAdapter);

        void notifyViewCreated(MainFragmentAdapter mainFragmentAdapter);

        void showTitleView(boolean z);
    }

    public final class FragmentHostImpl implements FragmentHost {
        public boolean mShowTitleView = true;

        public FragmentHostImpl() {
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.FragmentHost
        public void notifyDataReady(MainFragmentAdapter mainFragmentAdapter) {
            MainFragmentAdapter mainFragmentAdapter2 = BrowseSupportFragment.this.mMainFragmentAdapter;
            if (mainFragmentAdapter2 == null || mainFragmentAdapter2.getFragmentHost() != this) {
                return;
            }
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (browseSupportFragment.mIsPageRow) {
                browseSupportFragment.mStateMachine.fireEvent(browseSupportFragment.EVT_SCREEN_DATA_READY);
            }
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.FragmentHost
        public void notifyViewCreated(MainFragmentAdapter mainFragmentAdapter) {
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            browseSupportFragment.mStateMachine.fireEvent(browseSupportFragment.EVT_MAIN_FRAGMENT_VIEW_CREATED);
            BrowseSupportFragment browseSupportFragment2 = BrowseSupportFragment.this;
            if (browseSupportFragment2.mIsPageRow) {
                return;
            }
            browseSupportFragment2.mStateMachine.fireEvent(browseSupportFragment2.EVT_SCREEN_DATA_READY);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.FragmentHost
        public void showTitleView(boolean z) {
            this.mShowTitleView = z;
            MainFragmentAdapter mainFragmentAdapter = BrowseSupportFragment.this.mMainFragmentAdapter;
            if (mainFragmentAdapter == null || mainFragmentAdapter.getFragmentHost() != this) {
                return;
            }
            BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
            if (browseSupportFragment.mIsPageRow) {
                browseSupportFragment.updateTitleViewVisibility();
            }
        }
    }

    public static class ListRowFragmentFactory extends FragmentFactory<RowsSupportFragment> {
        @Override // androidx.leanback.app.BrowseSupportFragment.FragmentFactory
        public RowsSupportFragment createFragment(Object obj) {
            return new RowsSupportFragment();
        }
    }

    public static class MainFragmentAdapter<T extends Fragment> {
        private final T mFragment;
        public FragmentHostImpl mFragmentHost;
        private boolean mScalingEnabled;

        public MainFragmentAdapter(T t) {
            this.mFragment = t;
        }

        public final T getFragment() {
            return this.mFragment;
        }

        public final FragmentHost getFragmentHost() {
            return this.mFragmentHost;
        }

        public boolean isScalingEnabled() {
            return this.mScalingEnabled;
        }

        public boolean isScrolling() {
            return false;
        }

        public void onTransitionEnd() {
        }

        public boolean onTransitionPrepare() {
            return false;
        }

        public void onTransitionStart() {
        }

        public void setAlignment(int i) {
        }

        public void setEntranceTransitionState(boolean z) {
        }

        public void setExpand(boolean z) {
        }

        public void setScalingEnabled(boolean z) {
            this.mScalingEnabled = z;
        }
    }

    public interface MainFragmentAdapterProvider {
        MainFragmentAdapter getMainFragmentAdapter();
    }

    public static final class MainFragmentAdapterRegistry {
        private static final FragmentFactory sDefaultFragmentFactory = new ListRowFragmentFactory();
        private final Map<Class, FragmentFactory> mItemToFragmentFactoryMapping = new HashMap();

        public MainFragmentAdapterRegistry() {
            registerFragment(ListRow.class, sDefaultFragmentFactory);
        }

        public Fragment createFragment(Object obj) {
            FragmentFactory fragmentFactory = obj == null ? sDefaultFragmentFactory : this.mItemToFragmentFactoryMapping.get(obj.getClass());
            if (fragmentFactory == null && !(obj instanceof PageRow)) {
                fragmentFactory = sDefaultFragmentFactory;
            }
            return fragmentFactory.createFragment(obj);
        }

        public void registerFragment(Class cls, FragmentFactory fragmentFactory) {
            this.mItemToFragmentFactoryMapping.put(cls, fragmentFactory);
        }
    }

    public class MainFragmentItemViewSelectedListener implements OnItemViewSelectedListener {
        public MainFragmentRowsAdapter mMainFragmentRowsAdapter;

        public MainFragmentItemViewSelectedListener(MainFragmentRowsAdapter mainFragmentRowsAdapter) {
            this.mMainFragmentRowsAdapter = mainFragmentRowsAdapter;
        }

        @Override // androidx.leanback.widget.BaseOnItemViewSelectedListener
        public void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, Row row) {
            BrowseSupportFragment.this.onRowSelected(this.mMainFragmentRowsAdapter.getSelectedPosition());
            OnItemViewSelectedListener onItemViewSelectedListener = BrowseSupportFragment.this.mExternalOnItemViewSelectedListener;
            if (onItemViewSelectedListener != null) {
                onItemViewSelectedListener.onItemSelected(viewHolder, obj, viewHolder2, row);
            }
        }
    }

    public static class MainFragmentRowsAdapter<T extends Fragment> {
        private final T mFragment;

        public MainFragmentRowsAdapter(T t) {
            if (t == null) {
                throw new IllegalArgumentException("Fragment can't be null");
            }
            this.mFragment = t;
        }

        public RowPresenter.ViewHolder findRowViewHolderByPosition(int i) {
            return null;
        }

        public final T getFragment() {
            return this.mFragment;
        }

        public int getSelectedPosition() {
            return 0;
        }

        public void setAdapter(ObjectAdapter objectAdapter) {
        }

        public void setOnItemViewClickedListener(OnItemViewClickedListener onItemViewClickedListener) {
        }

        public void setOnItemViewSelectedListener(OnItemViewSelectedListener onItemViewSelectedListener) {
        }

        public void setSelectedPosition(int i, boolean z) {
        }

        public void setSelectedPosition(int i, boolean z, Presenter.ViewHolderTask viewHolderTask) {
        }
    }

    public interface MainFragmentRowsAdapterProvider {
        MainFragmentRowsAdapter getMainFragmentRowsAdapter();
    }

    public final class SetSelectionRunnable implements Runnable {
        private int mPosition;
        private boolean mSmooth;
        private int mType;

        public SetSelectionRunnable() {
            reset();
        }

        private void reset() {
            this.mPosition = -1;
            this.mType = -1;
            this.mSmooth = false;
        }

        public final void post(int i, int i2, boolean z) {
            if (i2 >= this.mType) {
                this.mPosition = i;
                this.mType = i2;
                this.mSmooth = z;
                BrowseSupportFragment.this.mBrowseFrame.removeCallbacks(this);
                BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
                if (browseSupportFragment.mStopped) {
                    return;
                }
                browseSupportFragment.mBrowseFrame.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            BrowseSupportFragment.this.setSelection(this.mPosition, this.mSmooth);
            reset();
        }

        public void start() {
            if (this.mType != -1) {
                BrowseSupportFragment.this.mBrowseFrame.post(this);
            }
        }

        public void stop() {
            BrowseSupportFragment.this.mBrowseFrame.removeCallbacks(this);
        }
    }

    public static Bundle createArgs(Bundle bundle, String str, int i) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(ARG_TITLE, str);
        bundle.putInt(ARG_HEADERS_STATE, i);
        return bundle;
    }

    private boolean createMainFragment(ObjectAdapter objectAdapter, int i) {
        Object obj;
        boolean z = true;
        if (!this.mCanShowHeaders) {
            obj = null;
        } else {
            if (objectAdapter == null || objectAdapter.size() == 0) {
                return false;
            }
            if (i < 0) {
                i = 0;
            } else if (i >= objectAdapter.size()) {
                throw new IllegalArgumentException(String.format("Invalid position %d requested", Integer.valueOf(i)));
            }
            obj = objectAdapter.get(i);
        }
        boolean z2 = this.mIsPageRow;
        Object obj2 = this.mPageRow;
        boolean z3 = this.mCanShowHeaders && (obj instanceof PageRow);
        this.mIsPageRow = z3;
        Object obj3 = z3 ? obj : null;
        this.mPageRow = obj3;
        if (this.mMainFragment != null) {
            if (!z2) {
                z = z3;
            } else if (z3 && (obj2 == null || obj2 == obj3)) {
                z = false;
            }
        }
        if (z) {
            Fragment fragmentCreateFragment = this.mMainFragmentAdapterRegistry.createFragment(obj);
            this.mMainFragment = fragmentCreateFragment;
            if (!(fragmentCreateFragment instanceof MainFragmentAdapterProvider)) {
                throw new IllegalArgumentException("Fragment must implement MainFragmentAdapterProvider");
            }
            setMainFragmentAdapter();
        }
        return z;
    }

    private void expandMainFragment(boolean z) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mScaleFrameLayout.getLayoutParams();
        marginLayoutParams.setMarginStart(!z ? this.mContainerListMarginStart : 0);
        this.mScaleFrameLayout.setLayoutParams(marginLayoutParams);
        this.mMainFragmentAdapter.setExpand(z);
        setMainFragmentAlignment();
        float f = (!z && this.mMainFragmentScaleEnabled && this.mMainFragmentAdapter.isScalingEnabled()) ? this.mScaleFactor : 1.0f;
        this.mScaleFrameLayout.setLayoutScaleY(f);
        this.mScaleFrameLayout.setChildScale(f);
    }

    private void onExpandTransitionStart(boolean z, Runnable runnable) {
        if (z) {
            runnable.run();
        } else {
            new ExpandPreLayout(runnable, this.mMainFragmentAdapter, getView()).execute();
        }
    }

    private void readArguments(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String str = ARG_TITLE;
        if (bundle.containsKey(str)) {
            setTitle(bundle.getString(str));
        }
        String str2 = ARG_HEADERS_STATE;
        if (bundle.containsKey(str2)) {
            setHeadersState(bundle.getInt(str2));
        }
    }

    private void replaceMainFragment(int i) {
        if (createMainFragment(this.mAdapter, i)) {
            swapToMainFragment();
            expandMainFragment((this.mCanShowHeaders && this.mShowingHeaders) ? false : true);
        }
    }

    private void setHeadersOnScreen(boolean z) {
        View view = this.mHeadersSupportFragment.getView();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMarginStart(z ? 0 : -this.mContainerListMarginStart);
        view.setLayoutParams(marginLayoutParams);
    }

    private void setMainFragmentAlignment() {
        int i = this.mContainerListAlignTop;
        if (this.mMainFragmentScaleEnabled && this.mMainFragmentAdapter.isScalingEnabled() && this.mShowingHeaders) {
            i = (int) ((i / this.mScaleFactor) + 0.5f);
        }
        this.mMainFragmentAdapter.setAlignment(i);
    }

    private void swapToMainFragment() {
        if (this.mStopped) {
            return;
        }
        VerticalGridView verticalGridView = this.mHeadersSupportFragment.getVerticalGridView();
        if (!isShowingHeaders() || verticalGridView == null || verticalGridView.getScrollState() == 0) {
            commitMainFragment();
            return;
        }
        getChildFragmentManager().beginTransaction().replace(R.id.scale_frame, new Fragment()).commit();
        verticalGridView.removeOnScrollListener(this.mWaitScrollFinishAndCommitMainFragment);
        verticalGridView.addOnScrollListener(this.mWaitScrollFinishAndCommitMainFragment);
    }

    private void updateWrapperPresenter() {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter == null) {
            this.mAdapterPresenter = null;
            return;
        }
        final PresenterSelector presenterSelector = objectAdapter.getPresenterSelector();
        if (presenterSelector == null) {
            throw new IllegalArgumentException("Adapter.getPresenterSelector() is null");
        }
        if (presenterSelector == this.mAdapterPresenter) {
            return;
        }
        this.mAdapterPresenter = presenterSelector;
        Presenter[] presenters = presenterSelector.getPresenters();
        final InvisibleRowPresenter invisibleRowPresenter = new InvisibleRowPresenter();
        int length = presenters.length + 1;
        final Presenter[] presenterArr = new Presenter[length];
        System.arraycopy(presenterArr, 0, presenters, 0, presenters.length);
        presenterArr[length - 1] = invisibleRowPresenter;
        this.mAdapter.setPresenterSelector(new PresenterSelector() { // from class: androidx.leanback.app.BrowseSupportFragment.2
            @Override // androidx.leanback.widget.PresenterSelector
            public Presenter getPresenter(Object obj) {
                return ((Row) obj).isRenderedAsRowView() ? presenterSelector.getPresenter(obj) : invisibleRowPresenter;
            }

            @Override // androidx.leanback.widget.PresenterSelector
            public Presenter[] getPresenters() {
                return presenterArr;
            }
        });
    }

    public final void commitMainFragment() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        int i = R.id.scale_frame;
        if (childFragmentManager.findFragmentById(i) != this.mMainFragment) {
            childFragmentManager.beginTransaction().replace(i, this.mMainFragment).commit();
        }
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final Object createEntranceTransition() {
        return TransitionHelper.loadTransition(getContext(), R.transition.lb_browse_entrance_transition);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void createStateMachineStates() {
        super.createStateMachineStates();
        this.mStateMachine.addState(this.STATE_SET_ENTRANCE_START_STATE);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void createStateMachineTransitions() {
        super.createStateMachineTransitions();
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_SET_ENTRANCE_START_STATE, this.EVT_HEADER_VIEW_CREATED);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW, this.EVT_MAIN_FRAGMENT_VIEW_CREATED);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_PERFORM, this.EVT_SCREEN_DATA_READY);
    }

    public void enableMainFragmentScaling(boolean z) {
        this.mMainFragmentScaleEnabled = z;
    }

    @Deprecated
    public void enableRowScaling(boolean z) {
        enableMainFragmentScaling(z);
    }

    public ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    @ColorInt
    public int getBrandColor() {
        return this.mBrandColor;
    }

    public int getHeadersState() {
        return this.mHeadersState;
    }

    public HeadersSupportFragment getHeadersSupportFragment() {
        return this.mHeadersSupportFragment;
    }

    public Fragment getMainFragment() {
        return this.mMainFragment;
    }

    public final MainFragmentAdapterRegistry getMainFragmentRegistry() {
        return this.mMainFragmentAdapterRegistry;
    }

    public OnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public OnItemViewSelectedListener getOnItemViewSelectedListener() {
        return this.mExternalOnItemViewSelectedListener;
    }

    public RowsSupportFragment getRowsSupportFragment() {
        Fragment fragment = this.mMainFragment;
        if (fragment instanceof RowsSupportFragment) {
            return (RowsSupportFragment) fragment;
        }
        return null;
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public RowPresenter.ViewHolder getSelectedRowViewHolder() {
        MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter == null) {
            return null;
        }
        return this.mMainFragmentRowsAdapter.findRowViewHolderByPosition(mainFragmentRowsAdapter.getSelectedPosition());
    }

    public final boolean isFirstRowWithContent(int i) {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter != null && objectAdapter.size() != 0) {
            int i2 = 0;
            while (i2 < this.mAdapter.size()) {
                if (((Row) this.mAdapter.get(i2)).isRenderedAsRowView()) {
                    return i == i2;
                }
                i2++;
            }
        }
        return true;
    }

    public final boolean isHeadersDataReady() {
        ObjectAdapter objectAdapter = this.mAdapter;
        return (objectAdapter == null || objectAdapter.size() == 0) ? false : true;
    }

    public final boolean isHeadersTransitionOnBackEnabled() {
        return this.mHeadersBackStackEnabled;
    }

    public boolean isInHeadersTransition() {
        return this.mHeadersTransition != null;
    }

    public boolean isShowingHeaders() {
        return this.mShowingHeaders;
    }

    public final boolean isVerticalScrolling() {
        return this.mHeadersSupportFragment.isScrolling() || this.mMainFragmentAdapter.isScrolling();
    }

    @Override // androidx.leanback.app.BaseSupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.LeanbackTheme);
        this.mContainerListMarginStart = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.LeanbackTheme_browseRowsMarginStart, context.getResources().getDimensionPixelSize(R.dimen.lb_browse_rows_margin_start));
        this.mContainerListAlignTop = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.LeanbackTheme_browseRowsMarginTop, context.getResources().getDimensionPixelSize(R.dimen.lb_browse_rows_margin_top));
        typedArrayObtainStyledAttributes.recycle();
        readArguments(getArguments());
        if (this.mCanShowHeaders) {
            if (this.mHeadersBackStackEnabled) {
                this.mWithHeadersBackStackName = LB_HEADERS_BACKSTACK + this;
                this.mBackStackChangedListener = new BackStackListener();
                getFragmentManager().addOnBackStackChangedListener(this.mBackStackChangedListener);
                BackStackListener backStackListener = this.mBackStackChangedListener;
                Objects.requireNonNull(backStackListener);
                if (bundle != null) {
                    int i = bundle.getInt("headerStackIndex", -1);
                    backStackListener.mIndexOfHeadersBackStack = i;
                    BrowseSupportFragment.this.mShowingHeaders = i == -1;
                } else {
                    BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
                    if (!browseSupportFragment.mShowingHeaders) {
                        browseSupportFragment.getFragmentManager().beginTransaction().addToBackStack(BrowseSupportFragment.this.mWithHeadersBackStackName).commit();
                    }
                }
            } else if (bundle != null) {
                this.mShowingHeaders = bundle.getBoolean("headerShow");
            }
        }
        this.mScaleFactor = getResources().getFraction(R.fraction.lb_browse_rows_scale, 1, 1);
    }

    public HeadersSupportFragment onCreateHeadersSupportFragment() {
        return new HeadersSupportFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        int i = R.id.scale_frame;
        if (childFragmentManager.findFragmentById(i) == null) {
            this.mHeadersSupportFragment = onCreateHeadersSupportFragment();
            createMainFragment(this.mAdapter, this.mSelectedPosition);
            FragmentTransaction fragmentTransactionReplace = getChildFragmentManager().beginTransaction().replace(R.id.browse_headers_dock, this.mHeadersSupportFragment);
            Fragment fragment = this.mMainFragment;
            if (fragment != null) {
                fragmentTransactionReplace.replace(i, fragment);
            } else {
                MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(null);
                this.mMainFragmentAdapter = mainFragmentAdapter;
                mainFragmentAdapter.mFragmentHost = new FragmentHostImpl();
            }
            fragmentTransactionReplace.commit();
        } else {
            this.mHeadersSupportFragment = (HeadersSupportFragment) getChildFragmentManager().findFragmentById(R.id.browse_headers_dock);
            this.mMainFragment = getChildFragmentManager().findFragmentById(i);
            this.mIsPageRow = bundle != null && bundle.getBoolean(IS_PAGE_ROW, false);
            this.mSelectedPosition = bundle != null ? bundle.getInt(CURRENT_SELECTED_POSITION, 0) : 0;
            setMainFragmentAdapter();
        }
        this.mHeadersSupportFragment.setHeadersGone(true ^ this.mCanShowHeaders);
        PresenterSelector presenterSelector = this.mHeaderPresenterSelector;
        if (presenterSelector != null) {
            this.mHeadersSupportFragment.setPresenterSelector(presenterSelector);
        }
        this.mHeadersSupportFragment.setAdapter(this.mAdapter);
        this.mHeadersSupportFragment.setOnHeaderViewSelectedListener(this.mHeaderViewSelectedListener);
        this.mHeadersSupportFragment.setOnHeaderClickedListener(this.mHeaderClickedListener);
        View viewInflate = layoutInflater.inflate(R.layout.lb_browse_fragment, viewGroup, false);
        getProgressBarManager().setRootView((ViewGroup) viewInflate);
        BrowseFrameLayout browseFrameLayout = (BrowseFrameLayout) viewInflate.findViewById(R.id.browse_frame);
        this.mBrowseFrame = browseFrameLayout;
        browseFrameLayout.setOnChildFocusListener(this.mOnChildFocusListener);
        this.mBrowseFrame.setOnFocusSearchListener(this.mOnFocusSearchListener);
        installTitleView(layoutInflater, this.mBrowseFrame, bundle);
        ScaleFrameLayout scaleFrameLayout = (ScaleFrameLayout) viewInflate.findViewById(i);
        this.mScaleFrameLayout = scaleFrameLayout;
        scaleFrameLayout.setPivotX(0.0f);
        this.mScaleFrameLayout.setPivotY(this.mContainerListAlignTop);
        if (this.mBrandColorSet) {
            this.mHeadersSupportFragment.setBackgroundColor(this.mBrandColor);
        }
        this.mSceneWithHeaders = TransitionHelper.createScene(this.mBrowseFrame, new Runnable() { // from class: androidx.leanback.app.BrowseSupportFragment.6
            @Override // java.lang.Runnable
            public void run() {
                BrowseSupportFragment.this.showHeaders(true);
            }
        });
        this.mSceneWithoutHeaders = TransitionHelper.createScene(this.mBrowseFrame, new Runnable() { // from class: androidx.leanback.app.BrowseSupportFragment.7
            @Override // java.lang.Runnable
            public void run() {
                BrowseSupportFragment.this.showHeaders(false);
            }
        });
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(this.mBrowseFrame, new Runnable() { // from class: androidx.leanback.app.BrowseSupportFragment.8
            @Override // java.lang.Runnable
            public void run() {
                BrowseSupportFragment.this.setEntranceTransitionEndState();
            }
        });
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this.mBackStackChangedListener != null) {
            getFragmentManager().removeOnBackStackChangedListener(this.mBackStackChangedListener);
        }
        super.onDestroy();
    }

    @Override // androidx.leanback.app.BrandedSupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        setMainFragmentRowsAdapter(null);
        this.mPageRow = null;
        this.mMainFragmentAdapter = null;
        this.mMainFragment = null;
        this.mHeadersSupportFragment = null;
        super.onDestroyView();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void onEntranceTransitionEnd() {
        MainFragmentAdapter mainFragmentAdapter = this.mMainFragmentAdapter;
        if (mainFragmentAdapter != null) {
            mainFragmentAdapter.onTransitionEnd();
        }
        HeadersSupportFragment headersSupportFragment = this.mHeadersSupportFragment;
        if (headersSupportFragment != null) {
            headersSupportFragment.onTransitionEnd();
        }
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void onEntranceTransitionPrepare() {
        this.mHeadersSupportFragment.onTransitionPrepare();
        this.mMainFragmentAdapter.setEntranceTransitionState(false);
        this.mMainFragmentAdapter.onTransitionPrepare();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void onEntranceTransitionStart() {
        this.mHeadersSupportFragment.onTransitionStart();
        this.mMainFragmentAdapter.onTransitionStart();
    }

    public final void onRowSelected(int i) {
        this.mSetSelectionRunnable.post(i, 0, true);
    }

    @Override // androidx.leanback.app.BrandedSupportFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(CURRENT_SELECTED_POSITION, this.mSelectedPosition);
        bundle.putBoolean(IS_PAGE_ROW, this.mIsPageRow);
        BackStackListener backStackListener = this.mBackStackChangedListener;
        if (backStackListener != null) {
            bundle.putInt("headerStackIndex", backStackListener.mIndexOfHeadersBackStack);
        } else {
            bundle.putBoolean("headerShow", this.mShowingHeaders);
        }
    }

    @Override // androidx.leanback.app.BrandedSupportFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Fragment fragment;
        HeadersSupportFragment headersSupportFragment;
        super.onStart();
        this.mHeadersSupportFragment.setAlignment(this.mContainerListAlignTop);
        setMainFragmentAlignment();
        if (this.mCanShowHeaders && this.mShowingHeaders && (headersSupportFragment = this.mHeadersSupportFragment) != null && headersSupportFragment.getView() != null) {
            this.mHeadersSupportFragment.getView().requestFocus();
        } else if ((!this.mCanShowHeaders || !this.mShowingHeaders) && (fragment = this.mMainFragment) != null && fragment.getView() != null) {
            this.mMainFragment.getView().requestFocus();
        }
        if (this.mCanShowHeaders) {
            showHeaders(this.mShowingHeaders);
        }
        this.mStateMachine.fireEvent(this.EVT_HEADER_VIEW_CREATED);
        this.mStopped = false;
        commitMainFragment();
        this.mSetSelectionRunnable.start();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        this.mStopped = true;
        this.mSetSelectionRunnable.stop();
        super.onStop();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void runEntranceTransition(Object obj) {
        TransitionHelper.runTransition(this.mSceneAfterEntranceTransition, obj);
    }

    public void setAdapter(ObjectAdapter objectAdapter) {
        this.mAdapter = objectAdapter;
        updateWrapperPresenter();
        if (getView() == null) {
            return;
        }
        updateMainFragmentRowsAdapter();
        this.mHeadersSupportFragment.setAdapter(this.mAdapter);
    }

    public void setBrandColor(@ColorInt int i) {
        this.mBrandColor = i;
        this.mBrandColorSet = true;
        HeadersSupportFragment headersSupportFragment = this.mHeadersSupportFragment;
        if (headersSupportFragment != null) {
            headersSupportFragment.setBackgroundColor(i);
        }
    }

    public void setBrowseTransitionListener(BrowseTransitionListener browseTransitionListener) {
        this.mBrowseTransitionListener = browseTransitionListener;
    }

    public final void setEntranceTransitionEndState() {
        setHeadersOnScreen(this.mShowingHeaders);
        setSearchOrbViewOnScreen(true);
        this.mMainFragmentAdapter.setEntranceTransitionState(true);
    }

    public final void setEntranceTransitionStartState() {
        setHeadersOnScreen(false);
        setSearchOrbViewOnScreen(false);
    }

    public void setHeaderPresenterSelector(PresenterSelector presenterSelector) {
        this.mHeaderPresenterSelector = presenterSelector;
        HeadersSupportFragment headersSupportFragment = this.mHeadersSupportFragment;
        if (headersSupportFragment != null) {
            headersSupportFragment.setPresenterSelector(presenterSelector);
        }
    }

    public void setHeadersState(int i) {
        if (i < 1 || i > 3) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Invalid headers state: ", i));
        }
        if (i != this.mHeadersState) {
            this.mHeadersState = i;
            if (i == 1) {
                this.mCanShowHeaders = true;
                this.mShowingHeaders = true;
            } else if (i == 2) {
                this.mCanShowHeaders = true;
                this.mShowingHeaders = false;
            } else if (i != 3) {
                Log.w("BrowseSupportFragment", "Unknown headers state: " + i);
            } else {
                this.mCanShowHeaders = false;
                this.mShowingHeaders = false;
            }
            HeadersSupportFragment headersSupportFragment = this.mHeadersSupportFragment;
            if (headersSupportFragment != null) {
                headersSupportFragment.setHeadersGone(true ^ this.mCanShowHeaders);
            }
        }
    }

    public final void setHeadersTransitionOnBackEnabled(boolean z) {
        this.mHeadersBackStackEnabled = z;
    }

    public final void setMainFragmentAdapter() {
        MainFragmentAdapter mainFragmentAdapter = ((MainFragmentAdapterProvider) this.mMainFragment).getMainFragmentAdapter();
        this.mMainFragmentAdapter = mainFragmentAdapter;
        mainFragmentAdapter.mFragmentHost = new FragmentHostImpl();
        if (this.mIsPageRow) {
            setMainFragmentRowsAdapter(null);
            return;
        }
        ActivityResultCaller activityResultCaller = this.mMainFragment;
        if (activityResultCaller instanceof MainFragmentRowsAdapterProvider) {
            setMainFragmentRowsAdapter(((MainFragmentRowsAdapterProvider) activityResultCaller).getMainFragmentRowsAdapter());
        } else {
            setMainFragmentRowsAdapter(null);
        }
        this.mIsPageRow = this.mMainFragmentRowsAdapter == null;
    }

    public final void setMainFragmentRowsAdapter(MainFragmentRowsAdapter mainFragmentRowsAdapter) {
        MainFragmentRowsAdapter mainFragmentRowsAdapter2 = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter == mainFragmentRowsAdapter2) {
            return;
        }
        if (mainFragmentRowsAdapter2 != null) {
            mainFragmentRowsAdapter2.setAdapter(null);
        }
        this.mMainFragmentRowsAdapter = mainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter != null) {
            mainFragmentRowsAdapter.setOnItemViewSelectedListener(new MainFragmentItemViewSelectedListener(mainFragmentRowsAdapter));
            this.mMainFragmentRowsAdapter.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        }
        updateMainFragmentRowsAdapter();
    }

    public void setOnItemViewClickedListener(OnItemViewClickedListener onItemViewClickedListener) {
        this.mOnItemViewClickedListener = onItemViewClickedListener;
        MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter != null) {
            mainFragmentRowsAdapter.setOnItemViewClickedListener(onItemViewClickedListener);
        }
    }

    public void setOnItemViewSelectedListener(OnItemViewSelectedListener onItemViewSelectedListener) {
        this.mExternalOnItemViewSelectedListener = onItemViewSelectedListener;
    }

    public final void setSearchOrbViewOnScreen(boolean z) {
        View searchAffordanceView = getTitleViewAdapter().getSearchAffordanceView();
        if (searchAffordanceView != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) searchAffordanceView.getLayoutParams();
            marginLayoutParams.setMarginStart(z ? 0 : -this.mContainerListMarginStart);
            searchAffordanceView.setLayoutParams(marginLayoutParams);
        }
    }

    public void setSelectedPosition(int i) {
        setSelectedPosition(i, true);
    }

    public final void setSelection(int i, boolean z) {
        if (i == -1) {
            return;
        }
        this.mSelectedPosition = i;
        HeadersSupportFragment headersSupportFragment = this.mHeadersSupportFragment;
        if (headersSupportFragment == null || this.mMainFragmentAdapter == null) {
            return;
        }
        headersSupportFragment.setSelectedPosition(i, z);
        replaceMainFragment(i);
        MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter != null) {
            mainFragmentRowsAdapter.setSelectedPosition(i, z);
        }
        updateTitleViewVisibility();
    }

    public final void showHeaders(boolean z) {
        this.mHeadersSupportFragment.setHeadersEnabled(z);
        setHeadersOnScreen(z);
        expandMainFragment(!z);
    }

    public void startHeadersTransition(boolean z) {
        if (!this.mCanShowHeaders) {
            throw new IllegalStateException("Cannot start headers transition");
        }
        if (isInHeadersTransition() || this.mShowingHeaders == z) {
            return;
        }
        startHeadersTransitionInternal(z);
    }

    public final void startHeadersTransitionInternal(final boolean z) {
        if (!getFragmentManager().isDestroyed() && isHeadersDataReady()) {
            this.mShowingHeaders = z;
            this.mMainFragmentAdapter.onTransitionPrepare();
            this.mMainFragmentAdapter.onTransitionStart();
            onExpandTransitionStart(!z, new Runnable() { // from class: androidx.leanback.app.BrowseSupportFragment.3
                @Override // java.lang.Runnable
                public void run() {
                    BrowseSupportFragment.this.mHeadersSupportFragment.onTransitionPrepare();
                    BrowseSupportFragment.this.mHeadersSupportFragment.onTransitionStart();
                    final BrowseSupportFragment browseSupportFragment = BrowseSupportFragment.this;
                    Object objLoadTransition = TransitionHelper.loadTransition(browseSupportFragment.getContext(), browseSupportFragment.mShowingHeaders ? R.transition.lb_browse_headers_in : R.transition.lb_browse_headers_out);
                    browseSupportFragment.mHeadersTransition = objLoadTransition;
                    TransitionHelper.addTransitionListener(objLoadTransition, new TransitionListener() { // from class: androidx.leanback.app.BrowseSupportFragment.9
                        @Override // androidx.leanback.transition.TransitionListener
                        public void onTransitionEnd(Object obj) {
                            VerticalGridView verticalGridView;
                            Fragment fragment;
                            View view;
                            BrowseSupportFragment browseSupportFragment2 = BrowseSupportFragment.this;
                            browseSupportFragment2.mHeadersTransition = null;
                            MainFragmentAdapter mainFragmentAdapter = browseSupportFragment2.mMainFragmentAdapter;
                            if (mainFragmentAdapter != null) {
                                mainFragmentAdapter.onTransitionEnd();
                                BrowseSupportFragment browseSupportFragment3 = BrowseSupportFragment.this;
                                if (!browseSupportFragment3.mShowingHeaders && (fragment = browseSupportFragment3.mMainFragment) != null && (view = fragment.getView()) != null && !view.hasFocus()) {
                                    view.requestFocus();
                                }
                            }
                            HeadersSupportFragment headersSupportFragment = BrowseSupportFragment.this.mHeadersSupportFragment;
                            if (headersSupportFragment != null) {
                                headersSupportFragment.onTransitionEnd();
                                BrowseSupportFragment browseSupportFragment4 = BrowseSupportFragment.this;
                                if (browseSupportFragment4.mShowingHeaders && (verticalGridView = browseSupportFragment4.mHeadersSupportFragment.getVerticalGridView()) != null && !verticalGridView.hasFocus()) {
                                    verticalGridView.requestFocus();
                                }
                            }
                            BrowseSupportFragment.this.updateTitleViewVisibility();
                            BrowseSupportFragment browseSupportFragment5 = BrowseSupportFragment.this;
                            BrowseTransitionListener browseTransitionListener = browseSupportFragment5.mBrowseTransitionListener;
                            if (browseTransitionListener != null) {
                                browseTransitionListener.onHeadersTransitionStop(browseSupportFragment5.mShowingHeaders);
                            }
                        }

                        @Override // androidx.leanback.transition.TransitionListener
                        public void onTransitionStart(Object obj) {
                        }
                    });
                    BrowseTransitionListener browseTransitionListener = BrowseSupportFragment.this.mBrowseTransitionListener;
                    if (browseTransitionListener != null) {
                        browseTransitionListener.onHeadersTransitionStart(z);
                    }
                    TransitionHelper.runTransition(z ? BrowseSupportFragment.this.mSceneWithHeaders : BrowseSupportFragment.this.mSceneWithoutHeaders, BrowseSupportFragment.this.mHeadersTransition);
                    BrowseSupportFragment browseSupportFragment2 = BrowseSupportFragment.this;
                    if (browseSupportFragment2.mHeadersBackStackEnabled) {
                        if (!z) {
                            browseSupportFragment2.getFragmentManager().beginTransaction().addToBackStack(BrowseSupportFragment.this.mWithHeadersBackStackName).commit();
                            return;
                        }
                        int i = browseSupportFragment2.mBackStackChangedListener.mIndexOfHeadersBackStack;
                        if (i >= 0) {
                            BrowseSupportFragment.this.getFragmentManager().popBackStackImmediate(browseSupportFragment2.getFragmentManager().getBackStackEntryAt(i).getId(), 1);
                        }
                    }
                }
            });
        }
    }

    public final void updateMainFragmentRowsAdapter() {
        ListRowDataAdapter listRowDataAdapter = this.mMainFragmentListRowDataAdapter;
        if (listRowDataAdapter != null) {
            listRowDataAdapter.detach();
            this.mMainFragmentListRowDataAdapter = null;
        }
        if (this.mMainFragmentRowsAdapter != null) {
            ObjectAdapter objectAdapter = this.mAdapter;
            ListRowDataAdapter listRowDataAdapter2 = objectAdapter != null ? new ListRowDataAdapter(objectAdapter) : null;
            this.mMainFragmentListRowDataAdapter = listRowDataAdapter2;
            this.mMainFragmentRowsAdapter.setAdapter(listRowDataAdapter2);
        }
    }

    public final void updateTitleViewVisibility() {
        boolean z;
        MainFragmentAdapter mainFragmentAdapter;
        MainFragmentAdapter mainFragmentAdapter2;
        if (!this.mShowingHeaders) {
            if ((!this.mIsPageRow || (mainFragmentAdapter2 = this.mMainFragmentAdapter) == null) ? isFirstRowWithContent(this.mSelectedPosition) : mainFragmentAdapter2.mFragmentHost.mShowTitleView) {
                showTitle(6);
                return;
            } else {
                showTitle(false);
                return;
            }
        }
        boolean zIsFirstRowWithContent = (!this.mIsPageRow || (mainFragmentAdapter = this.mMainFragmentAdapter) == null) ? isFirstRowWithContent(this.mSelectedPosition) : mainFragmentAdapter.mFragmentHost.mShowTitleView;
        int i = this.mSelectedPosition;
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter == null || objectAdapter.size() == 0) {
            z = true;
        } else {
            int i2 = 0;
            while (true) {
                if (i2 < this.mAdapter.size()) {
                    Row row = (Row) this.mAdapter.get(i2);
                    if (!row.isRenderedAsRowView() && !(row instanceof PageRow)) {
                        i2++;
                    } else if (i != i2) {
                        z = false;
                    }
                }
                z = true;
            }
        }
        int i3 = zIsFirstRowWithContent ? 2 : 0;
        if (z) {
            i3 |= 4;
        }
        if (i3 != 0) {
            showTitle(i3);
        } else {
            showTitle(false);
        }
    }

    public void setSelectedPosition(int i, boolean z) {
        this.mSetSelectionRunnable.post(i, 1, z);
    }

    public void setSelectedPosition(int i, boolean z, Presenter.ViewHolderTask viewHolderTask) {
        if (this.mMainFragmentAdapterRegistry == null) {
            return;
        }
        if (viewHolderTask != null) {
            startHeadersTransition(false);
        }
        MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter != null) {
            mainFragmentRowsAdapter.setSelectedPosition(i, z, viewHolderTask);
        }
    }
}
