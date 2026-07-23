package androidx.leanback.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
@Deprecated
public class DetailsFragment extends BaseFragment {
    public ObjectAdapter mAdapter;
    public Drawable mBackgroundDrawable;
    public View mBackgroundView;
    public int mContainerListAlignTop;
    public DetailsFragmentBackgroundController mDetailsBackgroundController;
    public DetailsParallax mDetailsParallax;
    public BaseOnItemViewSelectedListener mExternalOnItemViewSelectedListener;
    public BaseOnItemViewClickedListener mOnItemViewClickedListener;
    public BrowseFrameLayout mRootView;
    public RowsFragment mRowsFragment;
    public Object mSceneAfterEntranceTransition;
    public Fragment mVideoFragment;
    public final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State() { // from class: androidx.leanback.app.DetailsFragment.1
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsFragment.this.mRowsFragment.setEntranceTransitionState(false);
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_INIT = new StateMachine.State("STATE_ENTER_TRANSIITON_INIT");
    public final StateMachine.State STATE_SWITCH_TO_VIDEO_IN_ON_CREATE = new StateMachine.State() { // from class: androidx.leanback.app.DetailsFragment.2
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsFragment detailsFragment = DetailsFragment.this;
            DetailsFragmentBackgroundController detailsFragmentBackgroundController = detailsFragment.mDetailsBackgroundController;
            detailsFragmentBackgroundController.mVideoHelper.crossFadeBackgroundToVideo(true, true);
            detailsFragmentBackgroundController.mInitialControlVisible = true;
            detailsFragment.showTitle(false);
            detailsFragment.mPendingFocusOnVideo = true;
            detailsFragment.slideOutGridView();
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_CANCEL = new StateMachine.State() { // from class: androidx.leanback.app.DetailsFragment.3
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            Objects.requireNonNull(DetailsFragment.this);
            if (DetailsFragment.this.getActivity() != null) {
                Window window = DetailsFragment.this.getActivity().getWindow();
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
    public final StateMachine.State STATE_ENTER_TRANSITION_ADDLISTENER = new StateMachine.State() { // from class: androidx.leanback.app.DetailsFragment.4
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            TransitionHelper.addTransitionListener(TransitionHelper.getEnterTransition(DetailsFragment.this.getActivity().getWindow()), DetailsFragment.this.mEnterTransitionListener);
        }
    };
    public final StateMachine.State STATE_ENTER_TRANSITION_PENDING = new StateMachine.State() { // from class: androidx.leanback.app.DetailsFragment.5
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            Objects.requireNonNull(DetailsFragment.this);
            new WaitEnterTransitionTimeout(DetailsFragment.this);
        }
    };
    public final StateMachine.State STATE_ON_SAFE_START = new StateMachine.State() { // from class: androidx.leanback.app.DetailsFragment.6
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsFragmentBackgroundController detailsFragmentBackgroundController = DetailsFragment.this.mDetailsBackgroundController;
            if (detailsFragmentBackgroundController != null) {
                detailsFragmentBackgroundController.onStart();
            }
        }
    };
    public final StateMachine.Event EVT_ONSTART = new StateMachine.Event("onStart");
    public final StateMachine.Event EVT_NO_ENTER_TRANSITION = new StateMachine.Event("EVT_NO_ENTER_TRANSITION");
    public final StateMachine.Event EVT_DETAILS_ROW_LOADED = new StateMachine.Event("onFirstRowLoaded");
    public final StateMachine.Event EVT_ENTER_TRANSIITON_DONE = new StateMachine.Event("onEnterTransitionDone");
    public final StateMachine.Event EVT_SWITCH_TO_VIDEO = new StateMachine.Event("switchToVideo");
    public TransitionListener mEnterTransitionListener = new TransitionListener() { // from class: androidx.leanback.app.DetailsFragment.7
        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionCancel(Object obj) {
            DetailsFragment detailsFragment = DetailsFragment.this;
            detailsFragment.mStateMachine.fireEvent(detailsFragment.EVT_ENTER_TRANSIITON_DONE);
        }

        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionEnd(Object obj) {
            DetailsFragment detailsFragment = DetailsFragment.this;
            detailsFragment.mStateMachine.fireEvent(detailsFragment.EVT_ENTER_TRANSIITON_DONE);
        }

        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionStart(Object obj) {
            Objects.requireNonNull(DetailsFragment.this);
        }
    };
    public TransitionListener mReturnTransitionListener = new TransitionListener() { // from class: androidx.leanback.app.DetailsFragment.8
        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionStart(Object obj) {
            boolean zIsVideoVisible;
            DetailsFragment detailsFragment = DetailsFragment.this;
            DetailsFragmentBackgroundController detailsFragmentBackgroundController = detailsFragment.mDetailsBackgroundController;
            if (detailsFragmentBackgroundController != null) {
                DetailsBackgroundVideoHelper detailsBackgroundVideoHelper = detailsFragmentBackgroundController.mVideoHelper;
                if (detailsBackgroundVideoHelper != null) {
                    detailsBackgroundVideoHelper.stopParallax();
                    zIsVideoVisible = detailsFragmentBackgroundController.mVideoHelper.isVideoVisible();
                } else {
                    zIsVideoVisible = false;
                }
                if (zIsVideoVisible || detailsFragment.mVideoFragment == null) {
                    return;
                }
                FragmentTransaction fragmentTransactionBeginTransaction = detailsFragment.getChildFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.remove(detailsFragment.mVideoFragment);
                fragmentTransactionBeginTransaction.commit();
                detailsFragment.mVideoFragment = null;
            }
        }
    };
    public boolean mPendingFocusOnVideo = false;
    public final SetSelectionRunnable mSetSelectionRunnable = new SetSelectionRunnable();
    public final BaseOnItemViewSelectedListener<Object> mOnItemViewSelectedListener = new BaseOnItemViewSelectedListener<Object>() { // from class: androidx.leanback.app.DetailsFragment.9
        @Override // androidx.leanback.widget.BaseOnItemViewSelectedListener
        public void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, Object obj2) {
            int selectedPosition = DetailsFragment.this.mRowsFragment.getVerticalGridView().getSelectedPosition();
            int selectedSubPosition = DetailsFragment.this.mRowsFragment.getVerticalGridView().getSelectedSubPosition();
            DetailsFragment detailsFragment = DetailsFragment.this;
            ObjectAdapter adapter = detailsFragment.getAdapter();
            RowsFragment rowsFragment = detailsFragment.mRowsFragment;
            if (rowsFragment == null || rowsFragment.getView() == null || !detailsFragment.mRowsFragment.getView().hasFocus() || detailsFragment.mPendingFocusOnVideo || !(adapter == null || adapter.size() == 0 || (detailsFragment.getVerticalGridView().getSelectedPosition() == 0 && detailsFragment.getVerticalGridView().getSelectedSubPosition() == 0))) {
                detailsFragment.showTitle(false);
            } else {
                detailsFragment.showTitle(true);
            }
            if (adapter != null && adapter.size() > selectedPosition) {
                VerticalGridView verticalGridView = detailsFragment.getVerticalGridView();
                int childCount = verticalGridView.getChildCount();
                if (childCount > 0) {
                    detailsFragment.mStateMachine.fireEvent(detailsFragment.EVT_DETAILS_ROW_LOADED);
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
            BaseOnItemViewSelectedListener baseOnItemViewSelectedListener = DetailsFragment.this.mExternalOnItemViewSelectedListener;
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
            RowsFragment rowsFragment = DetailsFragment.this.mRowsFragment;
            if (rowsFragment == null) {
                return;
            }
            rowsFragment.setSelectedPosition(this.mPosition, this.mSmooth);
        }
    }

    public static class WaitEnterTransitionTimeout implements Runnable {
        public final WeakReference<DetailsFragment> mRef;

        public WaitEnterTransitionTimeout(DetailsFragment detailsFragment) {
            this.mRef = new WeakReference<>(detailsFragment);
            detailsFragment.getView().postDelayed(this, 200L);
        }

        @Override // java.lang.Runnable
        public void run() {
            DetailsFragment detailsFragment = this.mRef.get();
            if (detailsFragment != null) {
                detailsFragment.mStateMachine.fireEvent(detailsFragment.EVT_ENTER_TRANSIITON_DONE);
            }
        }
    }

    private void setupChildFragmentLayout() {
        VerticalGridView verticalGridView = this.mRowsFragment.getVerticalGridView();
        verticalGridView.setItemAlignmentOffset(-this.mContainerListAlignTop);
        verticalGridView.setItemAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignmentOffset(0);
        verticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignment(0);
    }

    @Override // androidx.leanback.app.BaseFragment
    public final Object createEntranceTransition() {
        return TransitionHelper.loadTransition(FragmentUtil.getContext(this), R.transition.lb_details_enter_transition);
    }

    @Override // androidx.leanback.app.BaseFragment
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

    @Override // androidx.leanback.app.BaseFragment
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
            RowsFragment rowsFragment = this.mRowsFragment;
            if (rowsFragment != null && rowsFragment.getView() != null) {
                this.mDetailsParallax.setRecyclerView(this.mRowsFragment.getVerticalGridView());
            }
        }
        return this.mDetailsParallax;
    }

    public RowsFragment getRowsFragment() {
        return this.mRowsFragment;
    }

    public final VerticalGridView getVerticalGridView() {
        RowsFragment rowsFragment = this.mRowsFragment;
        if (rowsFragment == null) {
            return null;
        }
        return rowsFragment.getVerticalGridView();
    }

    @Override // androidx.leanback.app.BaseFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContainerListAlignTop = getResources().getDimensionPixelSize(R.dimen.lb_details_rows_align_top);
        Activity activity = getActivity();
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

    @Override // android.app.Fragment
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
        RowsFragment rowsFragment = (RowsFragment) childFragmentManager.findFragmentById(i);
        this.mRowsFragment = rowsFragment;
        if (rowsFragment == null) {
            this.mRowsFragment = new RowsFragment();
            getChildFragmentManager().beginTransaction().replace(i, this.mRowsFragment).commit();
        }
        installTitleView(layoutInflater, this.mRootView, bundle);
        this.mRowsFragment.setAdapter(this.mAdapter);
        this.mRowsFragment.setOnItemViewSelectedListener(this.mOnItemViewSelectedListener);
        this.mRowsFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(this.mRootView, new Runnable() { // from class: androidx.leanback.app.DetailsFragment.10
            @Override // java.lang.Runnable
            public void run() {
                DetailsFragment.this.mRowsFragment.setEntranceTransitionState(true);
            }
        });
        this.mRootView.setOnChildFocusListener(new BrowseFrameLayout.OnChildFocusListener() { // from class: androidx.leanback.app.DetailsFragment.13
            @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
            public void onRequestChildFocus(View view, View view2) {
                if (view != DetailsFragment.this.mRootView.getFocusedChild()) {
                    if (view.getId() != R.id.details_fragment_root) {
                        if (view.getId() != R.id.video_surface_container) {
                            DetailsFragment.this.showTitle(true);
                            return;
                        } else {
                            DetailsFragment.this.slideOutGridView();
                            DetailsFragment.this.showTitle(false);
                            return;
                        }
                    }
                    DetailsFragment detailsFragment = DetailsFragment.this;
                    if (detailsFragment.mPendingFocusOnVideo) {
                        return;
                    }
                    if (detailsFragment.getVerticalGridView() != null) {
                        detailsFragment.getVerticalGridView().animateIn();
                    }
                    DetailsFragment.this.showTitle(true);
                }
            }

            @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
            public boolean onRequestFocusInDescendants(int i2, Rect rect) {
                return false;
            }
        });
        this.mRootView.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() { // from class: androidx.leanback.app.DetailsFragment.14
            @Override // androidx.leanback.widget.BrowseFrameLayout.OnFocusSearchListener
            public View onFocusSearch(View view, int i2) {
                Fragment fragment;
                if (DetailsFragment.this.mRowsFragment.getVerticalGridView() == null || !DetailsFragment.this.mRowsFragment.getVerticalGridView().hasFocus()) {
                    return (DetailsFragment.this.getTitleView() == null || !DetailsFragment.this.getTitleView().hasFocus() || i2 != 130 || DetailsFragment.this.mRowsFragment.getVerticalGridView() == null) ? view : DetailsFragment.this.mRowsFragment.getVerticalGridView();
                }
                if (i2 != 33) {
                    return view;
                }
                DetailsFragmentBackgroundController detailsFragmentBackgroundController = DetailsFragment.this.mDetailsBackgroundController;
                if (detailsFragmentBackgroundController == null || !detailsFragmentBackgroundController.canNavigateToVideoFragment() || (fragment = DetailsFragment.this.mVideoFragment) == null || fragment.getView() == null) {
                    return (DetailsFragment.this.getTitleView() == null || !DetailsFragment.this.getTitleView().hasFocusable()) ? view : DetailsFragment.this.getTitleView();
                }
                return DetailsFragment.this.mVideoFragment.getView();
            }
        });
        this.mRootView.setOnDispatchKeyListener(new View.OnKeyListener() { // from class: androidx.leanback.app.DetailsFragment.15
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i2, KeyEvent keyEvent) {
                Fragment fragment = DetailsFragment.this.mVideoFragment;
                if (fragment == null || fragment.getView() == null || !DetailsFragment.this.mVideoFragment.getView().hasFocus()) {
                    return false;
                }
                if ((i2 != 4 && i2 != 111) || DetailsFragment.this.getVerticalGridView().getChildCount() <= 0) {
                    return false;
                }
                DetailsFragment.this.getVerticalGridView().requestFocus();
                return true;
            }
        });
        this.mRowsFragment.mExternalAdapterListener = new ItemBridgeAdapter.AdapterListener() { // from class: androidx.leanback.app.DetailsFragment.11
            @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
            public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
                if (DetailsFragment.this.mDetailsParallax == null || !(viewHolder.getViewHolder() instanceof FullWidthDetailsOverviewRowPresenter.ViewHolder)) {
                    return;
                }
                ((FullWidthDetailsOverviewRowPresenter.ViewHolder) viewHolder.getViewHolder()).getOverviewView().setTag(R.id.lb_parallax_source, DetailsFragment.this.mDetailsParallax);
            }
        };
        return this.mRootView;
    }

    @Override // androidx.leanback.app.BaseFragment
    public final void onEntranceTransitionEnd() {
        this.mRowsFragment.onTransitionEnd();
    }

    @Override // androidx.leanback.app.BaseFragment
    public final void onEntranceTransitionPrepare() {
        this.mRowsFragment.onTransitionPrepare();
    }

    @Override // androidx.leanback.app.BaseFragment
    public final void onEntranceTransitionStart() {
        this.mRowsFragment.onTransitionStart();
    }

    @Override // androidx.leanback.app.BrandedFragment
    public View onInflateTitleView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onInflateTitleView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.leanback.app.BrandedFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
        setupChildFragmentLayout();
        this.mStateMachine.fireEvent(this.EVT_ONSTART);
        DetailsParallax detailsParallax = this.mDetailsParallax;
        if (detailsParallax != null) {
            detailsParallax.setRecyclerView(this.mRowsFragment.getVerticalGridView());
        }
        if (this.mPendingFocusOnVideo) {
            slideOutGridView();
        } else {
            if (getView().hasFocus()) {
                return;
            }
            this.mRowsFragment.getVerticalGridView().requestFocus();
        }
    }

    @Override // android.app.Fragment
    public void onStop() {
        PlaybackGlue playbackGlue;
        DetailsFragmentBackgroundController detailsFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsFragmentBackgroundController != null && (playbackGlue = detailsFragmentBackgroundController.mPlaybackGlue) != null) {
            playbackGlue.pause();
        }
        super.onStop();
    }

    @Override // androidx.leanback.app.BaseFragment
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
            Log.e("DetailsFragment", "PresenterSelector.getPresenters() not implemented");
        }
        RowsFragment rowsFragment = this.mRowsFragment;
        if (rowsFragment != null) {
            rowsFragment.setAdapter(objectAdapter);
        }
    }

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener baseOnItemViewClickedListener) {
        if (this.mOnItemViewClickedListener != baseOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = baseOnItemViewClickedListener;
            RowsFragment rowsFragment = this.mRowsFragment;
            if (rowsFragment != null) {
                rowsFragment.setOnItemViewClickedListener(baseOnItemViewClickedListener);
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
        Fragment fragment = this.mVideoFragment;
        if (fragment == null || fragment.getView() == null) {
            this.mStateMachine.fireEvent(this.EVT_SWITCH_TO_VIDEO);
        } else {
            this.mVideoFragment.getView().requestFocus();
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
