package androidx.leanback.app;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.R;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;
import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.BaseOnItemViewSelectedListener;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.DetailsParallax;
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import androidx.leanback.widget.ItemAlignmentFacet;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class DetailsSupportFragment extends BaseSupportFragment {
    public ObjectAdapter mAdapter;
    public Drawable mBackgroundDrawable;
    public View mBackgroundView;
    public int mContainerListAlignTop;
    public DetailsSupportFragmentBackgroundController mDetailsBackgroundController;
    public DetailsParallax mDetailsParallax;
    public BaseOnItemViewSelectedListener mExternalOnItemViewSelectedListener;
    public BaseOnItemViewClickedListener mOnItemViewClickedListener;
    public BrowseFrameLayout mRootView;
    public RowsSupportFragment mRowsSupportFragment;
    public Object mSceneAfterEntranceTransition;
    public Fragment mVideoSupportFragment;
    public final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State() { // from class: androidx.leanback.app.DetailsSupportFragment.1
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsSupportFragment.this.mRowsSupportFragment.setEntranceTransitionState(false);
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_INIT = new StateMachine.State("STATE_ENTER_TRANSIITON_INIT");
    public final StateMachine.State STATE_SWITCH_TO_VIDEO_IN_ON_CREATE = new StateMachine.State() { // from class: androidx.leanback.app.DetailsSupportFragment.2
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsSupportFragment detailsSupportFragment = DetailsSupportFragment.this;
            DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = detailsSupportFragment.mDetailsBackgroundController;
            detailsSupportFragmentBackgroundController.mVideoHelper.crossFadeBackgroundToVideo(true, true);
            detailsSupportFragmentBackgroundController.mInitialControlVisible = true;
            detailsSupportFragment.showTitle(false);
            detailsSupportFragment.mPendingFocusOnVideo = true;
            detailsSupportFragment.slideOutGridView();
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_CANCEL = new StateMachine.State() { // from class: androidx.leanback.app.DetailsSupportFragment.3
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            Objects.requireNonNull(DetailsSupportFragment.this);
            if (DetailsSupportFragment.this.getActivity() != null) {
                Window window = DetailsSupportFragment.this.getActivity().getWindow();
                Object returnTransition = TransitionHelper.getReturnTransition(window);
                Object sharedElementReturnTransition = TransitionHelper.getSharedElementReturnTransition(window);
                TransitionHelper.setEnterTransition(window, (Object) null);
                TransitionHelper.setSharedElementEnterTransition(window, (Object) null);
                TransitionHelper.setReturnTransition(window, returnTransition);
                TransitionHelper.setSharedElementReturnTransition(window, sharedElementReturnTransition);
            }
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_COMPLETE = new StateMachine.State("STATE_ENTER_TRANSIITON_COMPLETE", true, false);
    public final StateMachine.State STATE_ENTER_TRANSITION_ADDLISTENER = new StateMachine.State() { // from class: androidx.leanback.app.DetailsSupportFragment.4
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            TransitionHelper.addTransitionListener(TransitionHelper.getEnterTransition(DetailsSupportFragment.this.getActivity().getWindow()), DetailsSupportFragment.this.mEnterTransitionListener);
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_PENDING = new StateMachine.State() { // from class: androidx.leanback.app.DetailsSupportFragment.5
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            Objects.requireNonNull(DetailsSupportFragment.this);
            new WaitEnterTransitionTimeout(DetailsSupportFragment.this);
        }
    };
    public final StateMachine.State STATE_ON_SAFE_START = new StateMachine.State() { // from class: androidx.leanback.app.DetailsSupportFragment.6
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = DetailsSupportFragment.this.mDetailsBackgroundController;
            if (detailsSupportFragmentBackgroundController != null) {
                detailsSupportFragmentBackgroundController.onStart();
            }
        }
    };
    public final StateMachine.Event EVT_ONSTART = new StateMachine.Event("onStart");
    public final StateMachine.Event EVT_NO_ENTER_TRANSITION = new StateMachine.Event("EVT_NO_ENTER_TRANSITION");
    public final StateMachine.Event EVT_DETAILS_ROW_LOADED = new StateMachine.Event("onFirstRowLoaded");
    public final StateMachine.Event EVT_ENTER_TRANSIITON_DONE = new StateMachine.Event("onEnterTransitionDone");
    public final StateMachine.Event EVT_SWITCH_TO_VIDEO = new StateMachine.Event("switchToVideo");
    public TransitionListener mEnterTransitionListener = new TransitionListener() { // from class: androidx.leanback.app.DetailsSupportFragment.7
        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionCancel(Object obj) {
            DetailsSupportFragment detailsSupportFragment = DetailsSupportFragment.this;
            detailsSupportFragment.mStateMachine.fireEvent(detailsSupportFragment.EVT_ENTER_TRANSIITON_DONE);
        }

        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionEnd(Object obj) {
            DetailsSupportFragment detailsSupportFragment = DetailsSupportFragment.this;
            detailsSupportFragment.mStateMachine.fireEvent(detailsSupportFragment.EVT_ENTER_TRANSIITON_DONE);
        }

        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionStart(Object obj) {
            Objects.requireNonNull(DetailsSupportFragment.this);
        }
    };
    public TransitionListener mReturnTransitionListener = new TransitionListener() { // from class: androidx.leanback.app.DetailsSupportFragment.8
        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionStart(Object obj) {
            boolean zIsVideoVisible;
            DetailsSupportFragment detailsSupportFragment = DetailsSupportFragment.this;
            DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = detailsSupportFragment.mDetailsBackgroundController;
            if (detailsSupportFragmentBackgroundController != null) {
                DetailsBackgroundVideoHelper detailsBackgroundVideoHelper = detailsSupportFragmentBackgroundController.mVideoHelper;
                if (detailsBackgroundVideoHelper != null) {
                    detailsBackgroundVideoHelper.stopParallax();
                    zIsVideoVisible = detailsSupportFragmentBackgroundController.mVideoHelper.isVideoVisible();
                } else {
                    zIsVideoVisible = false;
                }
                if (zIsVideoVisible || detailsSupportFragment.mVideoSupportFragment == null) {
                    return;
                }
                FragmentTransaction fragmentTransactionBeginTransaction = detailsSupportFragment.getChildFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.remove(detailsSupportFragment.mVideoSupportFragment);
                fragmentTransactionBeginTransaction.commit();
                detailsSupportFragment.mVideoSupportFragment = null;
            }
        }
    };
    public boolean mPendingFocusOnVideo = false;
    public final SetSelectionRunnable mSetSelectionRunnable = new SetSelectionRunnable();
    public final BaseOnItemViewSelectedListener<Object> mOnItemViewSelectedListener = new BaseOnItemViewSelectedListener<Object>() { // from class: androidx.leanback.app.DetailsSupportFragment.9
        @Override // androidx.leanback.widget.BaseOnItemViewSelectedListener
        public void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, Object obj2) {
            int selectedPosition = DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().getSelectedPosition();
            int selectedSubPosition = DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().getSelectedSubPosition();
            DetailsSupportFragment detailsSupportFragment = DetailsSupportFragment.this;
            ObjectAdapter adapter = detailsSupportFragment.getAdapter();
            RowsSupportFragment rowsSupportFragment = detailsSupportFragment.mRowsSupportFragment;
            if (rowsSupportFragment == null || rowsSupportFragment.getView() == null || !detailsSupportFragment.mRowsSupportFragment.getView().hasFocus() || detailsSupportFragment.mPendingFocusOnVideo || !(adapter == null || adapter.size() == 0 || (detailsSupportFragment.getVerticalGridView().getSelectedPosition() == 0 && detailsSupportFragment.getVerticalGridView().getSelectedSubPosition() == 0))) {
                detailsSupportFragment.showTitle(false);
            } else {
                detailsSupportFragment.showTitle(true);
            }
            if (adapter != null && adapter.size() > selectedPosition) {
                VerticalGridView verticalGridView = detailsSupportFragment.getVerticalGridView();
                int childCount = verticalGridView.getChildCount();
                if (childCount > 0) {
                    detailsSupportFragment.mStateMachine.fireEvent(detailsSupportFragment.EVT_DETAILS_ROW_LOADED);
                }
                for (int i = 0; i < childCount; i++) {
                    ItemBridgeAdapter.ViewHolder viewHolder3 = (ItemBridgeAdapter.ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i));
                    RowPresenter rowPresenter = (RowPresenter) viewHolder3.getPresenter();
                    RowPresenter.ViewHolder rowViewHolder = rowPresenter.getRowViewHolder(viewHolder3.getViewHolder());
                    int adapterPosition = viewHolder3.getAdapterPosition();
                    if (rowPresenter instanceof FullWidthDetailsOverviewRowPresenter) {
                        FullWidthDetailsOverviewRowPresenter fullWidthDetailsOverviewRowPresenter = (FullWidthDetailsOverviewRowPresenter) rowPresenter;
                        FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder4 = (FullWidthDetailsOverviewRowPresenter.ViewHolder) rowViewHolder;
                        if (selectedPosition > adapterPosition) {
                            fullWidthDetailsOverviewRowPresenter.setState(viewHolder4, 0);
                        } else if (selectedPosition == adapterPosition && selectedSubPosition == 1) {
                            fullWidthDetailsOverviewRowPresenter.setState(viewHolder4, 0);
                        } else if (selectedPosition == adapterPosition && selectedSubPosition == 0) {
                            fullWidthDetailsOverviewRowPresenter.setState(viewHolder4, 1);
                        } else {
                            fullWidthDetailsOverviewRowPresenter.setState(viewHolder4, 2);
                        }
                    }
                }
            }
            BaseOnItemViewSelectedListener baseOnItemViewSelectedListener = DetailsSupportFragment.this.mExternalOnItemViewSelectedListener;
            if (baseOnItemViewSelectedListener != null) {
                baseOnItemViewSelectedListener.onItemSelected(viewHolder, obj, viewHolder2, obj2);
            }
        }
    };

    public class SetSelectionRunnable implements Runnable {
        public int mPosition;
        public boolean mSmooth = true;

        public SetSelectionRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RowsSupportFragment rowsSupportFragment = DetailsSupportFragment.this.mRowsSupportFragment;
            if (rowsSupportFragment == null) {
                return;
            }
            rowsSupportFragment.setSelectedPosition(this.mPosition, this.mSmooth);
        }
    }

    public static class WaitEnterTransitionTimeout implements Runnable {
        public final WeakReference<DetailsSupportFragment> mRef;

        public WaitEnterTransitionTimeout(DetailsSupportFragment detailsSupportFragment) {
            this.mRef = new WeakReference<>(detailsSupportFragment);
            detailsSupportFragment.getView().postDelayed(this, 200L);
        }

        @Override // java.lang.Runnable
        public void run() {
            DetailsSupportFragment detailsSupportFragment = this.mRef.get();
            if (detailsSupportFragment != null) {
                detailsSupportFragment.mStateMachine.fireEvent(detailsSupportFragment.EVT_ENTER_TRANSIITON_DONE);
            }
        }
    }

    private void setupChildFragmentLayout() {
        VerticalGridView verticalGridView = this.mRowsSupportFragment.getVerticalGridView();
        verticalGridView.setItemAlignmentOffset(-this.mContainerListAlignTop);
        verticalGridView.setItemAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignmentOffset(0);
        verticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignment(0);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final Object createEntranceTransition() {
        return TransitionHelper.loadTransition(getContext(), R.transition.lb_details_enter_transition);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void createStateMachineStates() {
        super.createStateMachineStates();
        this.mStateMachine.addState(this.STATE_SET_ENTRANCE_START_STATE);
        this.mStateMachine.addState(this.STATE_ON_SAFE_START);
        this.mStateMachine.addState(this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_INIT);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_ADDLISTENER);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_CANCEL);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_PENDING);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_COMPLETE);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void createStateMachineTransitions() {
        super.createStateMachineTransitions();
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ENTER_TRANSITION_INIT, this.EVT_ON_CREATE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_COMPLETE, this.COND_TRANSITION_NOT_SUPPORTED);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_NO_ENTER_TRANSITION);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_CANCEL, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_CANCEL, this.STATE_ENTER_TRANSITION_COMPLETE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_ADDLISTENER, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_ADDLISTENER, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_ENTER_TRANSIITON_DONE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_ADDLISTENER, this.STATE_ENTER_TRANSITION_PENDING, this.EVT_DETAILS_ROW_LOADED);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_PENDING, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_ENTER_TRANSIITON_DONE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_COMPLETE, this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.STATE_ENTRANCE_COMPLETE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_COMPLETE, this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_SET_ENTRANCE_START_STATE, this.EVT_ONSTART);
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ON_SAFE_START, this.EVT_ONSTART);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_COMPLETE, this.STATE_ON_SAFE_START);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_COMPLETE, this.STATE_ON_SAFE_START);
    }

    public ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public BaseOnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public DetailsParallax getParallax() {
        if (this.mDetailsParallax == null) {
            this.mDetailsParallax = new DetailsParallax();
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (rowsSupportFragment != null && rowsSupportFragment.getView() != null) {
                this.mDetailsParallax.setRecyclerView(this.mRowsSupportFragment.getVerticalGridView());
            }
        }
        return this.mDetailsParallax;
    }

    public RowsSupportFragment getRowsSupportFragment() {
        return this.mRowsSupportFragment;
    }

    public final VerticalGridView getVerticalGridView() {
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment == null) {
            return null;
        }
        return rowsSupportFragment.getVerticalGridView();
    }

    @Override // androidx.leanback.app.BaseSupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContainerListAlignTop = getResources().getDimensionPixelSize(R.dimen.lb_details_rows_align_top);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            this.mStateMachine.fireEvent(this.EVT_NO_ENTER_TRANSITION);
            return;
        }
        if (TransitionHelper.getEnterTransition(activity.getWindow()) == null) {
            this.mStateMachine.fireEvent(this.EVT_NO_ENTER_TRANSITION);
        }
        Object returnTransition = TransitionHelper.getReturnTransition(activity.getWindow());
        if (returnTransition != null) {
            TransitionHelper.addTransitionListener(returnTransition, this.mReturnTransitionListener);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BrowseFrameLayout browseFrameLayout = (BrowseFrameLayout) layoutInflater.inflate(R.layout.lb_details_fragment, viewGroup, false);
        this.mRootView = browseFrameLayout;
        View viewFindViewById = browseFrameLayout.findViewById(R.id.details_background_view);
        this.mBackgroundView = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setBackground(this.mBackgroundDrawable);
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        int i = R.id.details_rows_dock;
        RowsSupportFragment rowsSupportFragment = (RowsSupportFragment) childFragmentManager.findFragmentById(i);
        this.mRowsSupportFragment = rowsSupportFragment;
        if (rowsSupportFragment == null) {
            this.mRowsSupportFragment = new RowsSupportFragment();
            getChildFragmentManager().beginTransaction().replace(i, this.mRowsSupportFragment).commit();
        }
        installTitleView(layoutInflater, this.mRootView, bundle);
        this.mRowsSupportFragment.setAdapter(this.mAdapter);
        this.mRowsSupportFragment.setOnItemViewSelectedListener(this.mOnItemViewSelectedListener);
        this.mRowsSupportFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(this.mRootView, new Runnable() { // from class: androidx.leanback.app.DetailsSupportFragment.10
            @Override // java.lang.Runnable
            public void run() {
                DetailsSupportFragment.this.mRowsSupportFragment.setEntranceTransitionState(true);
            }
        });
        this.mRootView.setOnChildFocusListener(new BrowseFrameLayout.OnChildFocusListener() { // from class: androidx.leanback.app.DetailsSupportFragment.13
            @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
            public void onRequestChildFocus(View view, View view2) {
                if (view != DetailsSupportFragment.this.mRootView.getFocusedChild()) {
                    if (view.getId() != R.id.details_fragment_root) {
                        if (view.getId() != R.id.video_surface_container) {
                            DetailsSupportFragment.this.showTitle(true);
                            return;
                        } else {
                            DetailsSupportFragment.this.slideOutGridView();
                            DetailsSupportFragment.this.showTitle(false);
                            return;
                        }
                    }
                    DetailsSupportFragment detailsSupportFragment = DetailsSupportFragment.this;
                    if (detailsSupportFragment.mPendingFocusOnVideo) {
                        return;
                    }
                    if (detailsSupportFragment.getVerticalGridView() != null) {
                        detailsSupportFragment.getVerticalGridView().animateIn();
                    }
                    DetailsSupportFragment.this.showTitle(true);
                }
            }

            @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
            public boolean onRequestFocusInDescendants(int i2, Rect rect) {
                return false;
            }
        });
        this.mRootView.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() { // from class: androidx.leanback.app.DetailsSupportFragment.14
            @Override // androidx.leanback.widget.BrowseFrameLayout.OnFocusSearchListener
            public View onFocusSearch(View view, int i2) {
                Fragment fragment;
                if (DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView() == null || !DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().hasFocus()) {
                    return (DetailsSupportFragment.this.getTitleView() == null || !DetailsSupportFragment.this.getTitleView().hasFocus() || i2 != 130 || DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView() == null) ? view : DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView();
                }
                if (i2 != 33) {
                    return view;
                }
                DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = DetailsSupportFragment.this.mDetailsBackgroundController;
                if (detailsSupportFragmentBackgroundController == null || !detailsSupportFragmentBackgroundController.canNavigateToVideoSupportFragment() || (fragment = DetailsSupportFragment.this.mVideoSupportFragment) == null || fragment.getView() == null) {
                    return (DetailsSupportFragment.this.getTitleView() == null || !DetailsSupportFragment.this.getTitleView().hasFocusable()) ? view : DetailsSupportFragment.this.getTitleView();
                }
                return DetailsSupportFragment.this.mVideoSupportFragment.getView();
            }
        });
        this.mRootView.setOnDispatchKeyListener(new View.OnKeyListener() { // from class: androidx.leanback.app.DetailsSupportFragment.15
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i2, KeyEvent keyEvent) {
                Fragment fragment = DetailsSupportFragment.this.mVideoSupportFragment;
                if (fragment == null || fragment.getView() == null || !DetailsSupportFragment.this.mVideoSupportFragment.getView().hasFocus()) {
                    return false;
                }
                if ((i2 != 4 && i2 != 111) || DetailsSupportFragment.this.getVerticalGridView().getChildCount() <= 0) {
                    return false;
                }
                DetailsSupportFragment.this.getVerticalGridView().requestFocus();
                return true;
            }
        });
        this.mRowsSupportFragment.mExternalAdapterListener = new ItemBridgeAdapter.AdapterListener() { // from class: androidx.leanback.app.DetailsSupportFragment.11
            @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
            public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
                if (DetailsSupportFragment.this.mDetailsParallax == null || !(viewHolder.getViewHolder() instanceof FullWidthDetailsOverviewRowPresenter.ViewHolder)) {
                    return;
                }
                ((FullWidthDetailsOverviewRowPresenter.ViewHolder) viewHolder.getViewHolder()).getOverviewView().setTag(R.id.lb_parallax_source, DetailsSupportFragment.this.mDetailsParallax);
            }
        };
        return this.mRootView;
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void onEntranceTransitionEnd() {
        this.mRowsSupportFragment.onTransitionEnd();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void onEntranceTransitionPrepare() {
        this.mRowsSupportFragment.onTransitionPrepare();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void onEntranceTransitionStart() {
        this.mRowsSupportFragment.onTransitionStart();
    }

    @Override // androidx.leanback.app.BrandedSupportFragment
    public View onInflateTitleView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onInflateTitleView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.leanback.app.BrandedSupportFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        setupChildFragmentLayout();
        this.mStateMachine.fireEvent(this.EVT_ONSTART);
        DetailsParallax detailsParallax = this.mDetailsParallax;
        if (detailsParallax != null) {
            detailsParallax.setRecyclerView(this.mRowsSupportFragment.getVerticalGridView());
        }
        if (this.mPendingFocusOnVideo) {
            slideOutGridView();
        } else {
            if (getView().hasFocus()) {
                return;
            }
            this.mRowsSupportFragment.getVerticalGridView().requestFocus();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        PlaybackGlue playbackGlue;
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController != null && (playbackGlue = detailsSupportFragmentBackgroundController.mPlaybackGlue) != null) {
            playbackGlue.pause();
        }
        super.onStop();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    public final void runEntranceTransition(Object obj) {
        TransitionHelper.runTransition(this.mSceneAfterEntranceTransition, obj);
    }

    public void setAdapter(ObjectAdapter objectAdapter) {
        this.mAdapter = objectAdapter;
        Presenter[] presenters = objectAdapter.getPresenterSelector().getPresenters();
        if (presenters != null) {
            for (Presenter presenter : presenters) {
                if (presenter instanceof FullWidthDetailsOverviewRowPresenter) {
                    ItemAlignmentFacet itemAlignmentFacet = new ItemAlignmentFacet();
                    ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef = new ItemAlignmentFacet.ItemAlignmentDef();
                    int i = R.id.details_frame;
                    itemAlignmentDef.setItemAlignmentViewId(i);
                    itemAlignmentDef.setItemAlignmentOffset(-getResources().getDimensionPixelSize(R.dimen.lb_details_v2_align_pos_for_actions));
                    itemAlignmentDef.setItemAlignmentOffsetPercent(0.0f);
                    ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef2 = new ItemAlignmentFacet.ItemAlignmentDef();
                    itemAlignmentDef2.setItemAlignmentViewId(i);
                    itemAlignmentDef2.setItemAlignmentFocusViewId(R.id.details_overview_description);
                    itemAlignmentDef2.setItemAlignmentOffset(-getResources().getDimensionPixelSize(R.dimen.lb_details_v2_align_pos_for_description));
                    itemAlignmentDef2.setItemAlignmentOffsetPercent(0.0f);
                    itemAlignmentFacet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{itemAlignmentDef, itemAlignmentDef2});
                    ((FullWidthDetailsOverviewRowPresenter) presenter).setFacet(ItemAlignmentFacet.class, itemAlignmentFacet);
                }
            }
        } else {
            Log.e("DetailsSupportFragment", "PresenterSelector.getPresenters() not implemented");
        }
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment != null) {
            rowsSupportFragment.setAdapter(objectAdapter);
        }
    }

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener baseOnItemViewClickedListener) {
        if (this.mOnItemViewClickedListener != baseOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = baseOnItemViewClickedListener;
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (rowsSupportFragment != null) {
                rowsSupportFragment.setOnItemViewClickedListener(baseOnItemViewClickedListener);
            }
        }
    }

    public void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener baseOnItemViewSelectedListener) {
        this.mExternalOnItemViewSelectedListener = baseOnItemViewSelectedListener;
    }

    public void setSelectedPosition(int i) {
        setSelectedPosition(i, true);
    }

    public final void slideOutGridView() {
        if (getVerticalGridView() != null) {
            getVerticalGridView().animateOut();
        }
    }

    public final void switchToVideo() {
        Fragment fragment = this.mVideoSupportFragment;
        if (fragment == null || fragment.getView() == null) {
            this.mStateMachine.fireEvent(this.EVT_SWITCH_TO_VIDEO);
        } else {
            this.mVideoSupportFragment.getView().requestFocus();
        }
    }

    public void setSelectedPosition(int i, boolean z) {
        SetSelectionRunnable setSelectionRunnable = this.mSetSelectionRunnable;
        setSelectionRunnable.mPosition = i;
        setSelectionRunnable.mSmooth = z;
        if (getView() == null || getView().getHandler() == null) {
            return;
        }
        getView().getHandler().post(this.mSetSelectionRunnable);
    }
}
