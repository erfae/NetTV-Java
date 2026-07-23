package androidx.fragment.app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.os.EnvironmentCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class FragmentStateManager {
    private static final String TAG = "FragmentManager";
    private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    private static final String TARGET_STATE_TAG = "android:target_state";
    private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    private static final String VIEW_REGISTRY_STATE_TAG = "android:view_registry_state";
    private static final String VIEW_STATE_TAG = "android:view_state";
    private final FragmentLifecycleCallbacksDispatcher mDispatcher;

    @NonNull
    private final Fragment mFragment;
    private final FragmentStore mFragmentStore;
    private boolean mMovingToState = false;
    private int mFragmentManagerState = -1;

    /* JADX INFO: renamed from: androidx.fragment.app.FragmentStateManager$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$State;

        static {
            int[] iArr = new int[Lifecycle.State.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$State = iArr;
            try {
                iArr[Lifecycle.State.RESUMED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.CREATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.INITIALIZED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, @NonNull FragmentStore fragmentStore, @NonNull Fragment fragment) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
    }

    private boolean isFragmentViewChild(@NonNull View view) {
        if (view == this.mFragment.mView) {
            return true;
        }
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == this.mFragment.mView) {
                return true;
            }
        }
        return false;
    }

    private Bundle saveBasicState() {
        Bundle bundle = new Bundle();
        Fragment fragment = this.mFragment;
        fragment.onSaveInstanceState(bundle);
        fragment.mSavedStateRegistryController.performSave(bundle);
        Parcelable parcelableSaveAllState = fragment.mChildFragmentManager.saveAllState();
        if (parcelableSaveAllState != null) {
            bundle.putParcelable("android:support:fragments", parcelableSaveAllState);
        }
        this.mDispatcher.dispatchOnFragmentSaveInstanceState(this.mFragment, bundle, false);
        if (bundle.isEmpty()) {
            bundle = null;
        }
        if (this.mFragment.mView != null) {
            saveViewState();
        }
        if (this.mFragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, this.mFragment.mSavedViewState);
        }
        if (this.mFragment.mSavedViewRegistryState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBundle(VIEW_REGISTRY_STATE_TAG, this.mFragment.mSavedViewRegistryState);
        }
        if (!this.mFragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, this.mFragment.mUserVisibleHint);
        }
        return bundle;
    }

    public final void activityCreated() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto ACTIVITY_CREATED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment fragment = this.mFragment;
        fragment.performActivityCreated(fragment.mSavedFragmentState);
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        Fragment fragment2 = this.mFragment;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated(fragment2, fragment2.mSavedFragmentState, false);
    }

    public final void addViewToContainer() {
        int iFindFragmentIndexInContainer = this.mFragmentStore.findFragmentIndexInContainer(this.mFragment);
        Fragment fragment = this.mFragment;
        fragment.mContainer.addView(fragment.mView, iFindFragmentIndexInContainer);
    }

    public final void attach() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto ATTACHED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment fragment = this.mFragment;
        Fragment fragment2 = fragment.mTarget;
        FragmentStateManager fragmentStateManager = null;
        if (fragment2 != null) {
            FragmentStateManager fragmentStateManager2 = this.mFragmentStore.getFragmentStateManager(fragment2.mWho);
            if (fragmentStateManager2 == null) {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Fragment ");
                sbM2.append(this.mFragment);
                sbM2.append(" declared target fragment ");
                sbM2.append(this.mFragment.mTarget);
                sbM2.append(" that does not belong to this FragmentManager!");
                throw new IllegalStateException(sbM2.toString());
            }
            Fragment fragment3 = this.mFragment;
            fragment3.mTargetWho = fragment3.mTarget.mWho;
            fragment3.mTarget = null;
            fragmentStateManager = fragmentStateManager2;
        } else {
            String str = fragment.mTargetWho;
            if (str != null && (fragmentStateManager = this.mFragmentStore.getFragmentStateManager(str)) == null) {
                StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Fragment ");
                sbM3.append(this.mFragment);
                sbM3.append(" declared target fragment ");
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(sbM3, this.mFragment.mTargetWho, " that does not belong to this FragmentManager!"));
            }
        }
        if (fragmentStateManager != null && (FragmentManager.USE_STATE_MANAGER || fragmentStateManager.mFragment.mState < 1)) {
            fragmentStateManager.moveToExpectedState();
        }
        Fragment fragment4 = this.mFragment;
        fragment4.mHost = fragment4.mFragmentManager.getHost();
        Fragment fragment5 = this.mFragment;
        fragment5.mParentFragment = fragment5.mFragmentManager.getParent();
        this.mDispatcher.dispatchOnFragmentPreAttached(this.mFragment, false);
        this.mFragment.performAttach();
        this.mDispatcher.dispatchOnFragmentAttached(this.mFragment, false);
    }

    public final int computeExpectedState() {
        Fragment fragment;
        ViewGroup viewGroup;
        Fragment fragment2 = this.mFragment;
        if (fragment2.mFragmentManager == null) {
            return fragment2.mState;
        }
        int iMin = this.mFragmentManagerState;
        int i = AnonymousClass2.$SwitchMap$androidx$lifecycle$Lifecycle$State[fragment2.mMaxState.ordinal()];
        if (i != 1) {
            if (i == 2) {
                iMin = Math.min(iMin, 5);
            } else if (i != 3) {
                iMin = i != 4 ? Math.min(iMin, -1) : Math.min(iMin, 0);
            } else {
                iMin = Math.min(iMin, 1);
            }
        }
        Fragment fragment3 = this.mFragment;
        if (fragment3.mFromLayout) {
            if (fragment3.mInLayout) {
                iMin = Math.max(this.mFragmentManagerState, 2);
                View view = this.mFragment.mView;
                if (view != null && view.getParent() == null) {
                    iMin = Math.min(iMin, 2);
                }
            } else {
                iMin = this.mFragmentManagerState < 4 ? Math.min(iMin, fragment3.mState) : Math.min(iMin, 1);
            }
        }
        if (!this.mFragment.mAdded) {
            iMin = Math.min(iMin, 1);
        }
        SpecialEffectsController.Operation.LifecycleImpact awaitingCompletionLifecycleImpact = null;
        if (FragmentManager.USE_STATE_MANAGER && (viewGroup = (fragment = this.mFragment).mContainer) != null) {
            awaitingCompletionLifecycleImpact = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager()).getAwaitingCompletionLifecycleImpact(this);
        }
        if (awaitingCompletionLifecycleImpact == SpecialEffectsController.Operation.LifecycleImpact.ADDING) {
            iMin = Math.min(iMin, 6);
        } else if (awaitingCompletionLifecycleImpact == SpecialEffectsController.Operation.LifecycleImpact.REMOVING) {
            iMin = Math.max(iMin, 3);
        } else {
            Fragment fragment4 = this.mFragment;
            if (fragment4.mRemoving) {
                iMin = fragment4.isInBackStack() ? Math.min(iMin, 1) : Math.min(iMin, -1);
            }
        }
        Fragment fragment5 = this.mFragment;
        if (fragment5.mDeferStart && fragment5.mState < 5) {
            iMin = Math.min(iMin, 4);
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder sbM24m = Insets$$ExternalSyntheticOutline0.m24m("computeExpectedState() of ", iMin, " for ");
            sbM24m.append(this.mFragment);
            Log.v(TAG, sbM24m.toString());
        }
        return iMin;
    }

    public final void create() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto CREATED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment fragment = this.mFragment;
        if (fragment.mIsCreated) {
            fragment.restoreChildFragmentState(fragment.mSavedFragmentState);
            this.mFragment.mState = 1;
            return;
        }
        this.mDispatcher.dispatchOnFragmentPreCreated(fragment, fragment.mSavedFragmentState, false);
        Fragment fragment2 = this.mFragment;
        fragment2.performCreate(fragment2.mSavedFragmentState);
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        Fragment fragment3 = this.mFragment;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentCreated(fragment3, fragment3.mSavedFragmentState, false);
    }

    public final void createView() {
        String resourceName;
        if (this.mFragment.mFromLayout) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto CREATE_VIEW: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment fragment = this.mFragment;
        LayoutInflater layoutInflaterPerformGetLayoutInflater = fragment.performGetLayoutInflater(fragment.mSavedFragmentState);
        ViewGroup viewGroup = null;
        Fragment fragment2 = this.mFragment;
        ViewGroup viewGroup2 = fragment2.mContainer;
        if (viewGroup2 != null) {
            viewGroup = viewGroup2;
        } else {
            int i = fragment2.mContainerId;
            if (i != 0) {
                if (i == -1) {
                    StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Cannot create fragment ");
                    sbM2.append(this.mFragment);
                    sbM2.append(" for a container view with no id");
                    throw new IllegalArgumentException(sbM2.toString());
                }
                viewGroup = (ViewGroup) fragment2.mFragmentManager.getContainer().onFindViewById(this.mFragment.mContainerId);
                if (viewGroup == null) {
                    Fragment fragment3 = this.mFragment;
                    if (!fragment3.mRestored) {
                        try {
                            resourceName = fragment3.getResources().getResourceName(this.mFragment.mContainerId);
                        } catch (Resources.NotFoundException unused) {
                            resourceName = EnvironmentCompat.MEDIA_UNKNOWN;
                        }
                        StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("No view found for id 0x");
                        sbM3.append(Integer.toHexString(this.mFragment.mContainerId));
                        sbM3.append(" (");
                        sbM3.append(resourceName);
                        sbM3.append(") for fragment ");
                        sbM3.append(this.mFragment);
                        throw new IllegalArgumentException(sbM3.toString());
                    }
                }
            }
        }
        Fragment fragment4 = this.mFragment;
        fragment4.mContainer = viewGroup;
        fragment4.performCreateView(layoutInflaterPerformGetLayoutInflater, viewGroup, fragment4.mSavedFragmentState);
        View view = this.mFragment.mView;
        if (view != null) {
            boolean z = false;
            view.setSaveFromParentEnabled(false);
            Fragment fragment5 = this.mFragment;
            fragment5.mView.setTag(R.id.fragment_container_view_tag, fragment5);
            if (viewGroup != null) {
                addViewToContainer();
            }
            Fragment fragment6 = this.mFragment;
            if (fragment6.mHidden) {
                fragment6.mView.setVisibility(8);
            }
            if (ViewCompat.isAttachedToWindow(this.mFragment.mView)) {
                ViewCompat.requestApplyInsets(this.mFragment.mView);
            } else {
                final View view2 = this.mFragment.mView;
                view2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.fragment.app.FragmentStateManager.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View view3) {
                        view2.removeOnAttachStateChangeListener(this);
                        ViewCompat.requestApplyInsets(view2);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View view3) {
                    }
                });
            }
            Fragment fragment7 = this.mFragment;
            fragment7.onViewCreated(fragment7.mView, fragment7.mSavedFragmentState);
            fragment7.mChildFragmentManager.dispatchViewCreated();
            FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
            Fragment fragment8 = this.mFragment;
            fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(fragment8, fragment8.mView, fragment8.mSavedFragmentState, false);
            int visibility = this.mFragment.mView.getVisibility();
            float alpha = this.mFragment.mView.getAlpha();
            if (FragmentManager.USE_STATE_MANAGER) {
                this.mFragment.setPostOnViewCreatedAlpha(alpha);
                Fragment fragment9 = this.mFragment;
                if (fragment9.mContainer != null && visibility == 0) {
                    View viewFindFocus = fragment9.mView.findFocus();
                    if (viewFindFocus != null) {
                        this.mFragment.setFocusedView(viewFindFocus);
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v(TAG, "requestFocus: Saved focused view " + viewFindFocus + " for Fragment " + this.mFragment);
                        }
                    }
                    this.mFragment.mView.setAlpha(0.0f);
                }
            } else {
                Fragment fragment10 = this.mFragment;
                if (visibility == 0 && fragment10.mContainer != null) {
                    z = true;
                }
                fragment10.mIsNewlyAdded = z;
            }
        }
        this.mFragment.mState = 2;
    }

    public final void destroy() {
        Fragment fragmentFindActiveFragment;
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("movefrom CREATED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment fragment = this.mFragment;
        boolean zIsChangingConfigurations = true;
        boolean z = fragment.mRemoving && !fragment.isInBackStack();
        if (!(z || this.mFragmentStore.getNonConfig().shouldDestroy(this.mFragment))) {
            String str = this.mFragment.mTargetWho;
            if (str != null && (fragmentFindActiveFragment = this.mFragmentStore.findActiveFragment(str)) != null && fragmentFindActiveFragment.mRetainInstance) {
                this.mFragment.mTarget = fragmentFindActiveFragment;
            }
            this.mFragment.mState = 0;
            return;
        }
        FragmentHostCallback<?> fragmentHostCallback = this.mFragment.mHost;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            zIsChangingConfigurations = this.mFragmentStore.getNonConfig().isCleared();
        } else if (fragmentHostCallback.getContext() instanceof Activity) {
            zIsChangingConfigurations = true ^ ((Activity) fragmentHostCallback.getContext()).isChangingConfigurations();
        }
        if (z || zIsChangingConfigurations) {
            this.mFragmentStore.getNonConfig().clearNonConfigState(this.mFragment);
        }
        this.mFragment.performDestroy();
        this.mDispatcher.dispatchOnFragmentDestroyed(this.mFragment, false);
        for (FragmentStateManager fragmentStateManager : (ArrayList) this.mFragmentStore.getActiveFragmentStateManagers()) {
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.mFragment;
                if (this.mFragment.mWho.equals(fragment2.mTargetWho)) {
                    fragment2.mTarget = this.mFragment;
                    fragment2.mTargetWho = null;
                }
            }
        }
        Fragment fragment3 = this.mFragment;
        String str2 = fragment3.mTargetWho;
        if (str2 != null) {
            fragment3.mTarget = this.mFragmentStore.findActiveFragment(str2);
        }
        this.mFragmentStore.makeInactive(this);
    }

    public final void destroyFragmentView() {
        View view;
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("movefrom CREATE_VIEW: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment fragment = this.mFragment;
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null && (view = fragment.mView) != null) {
            viewGroup.removeView(view);
        }
        this.mFragment.performDestroyView();
        this.mDispatcher.dispatchOnFragmentViewDestroyed(this.mFragment, false);
        Fragment fragment2 = this.mFragment;
        fragment2.mContainer = null;
        fragment2.mView = null;
        fragment2.mViewLifecycleOwner = null;
        fragment2.mViewLifecycleOwnerLiveData.setValue(null);
        this.mFragment.mInLayout = false;
    }

    public final void detach() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("movefrom ATTACHED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        this.mFragment.performDetach();
        boolean z = false;
        this.mDispatcher.dispatchOnFragmentDetached(this.mFragment, false);
        Fragment fragment = this.mFragment;
        fragment.mState = -1;
        fragment.mHost = null;
        fragment.mParentFragment = null;
        fragment.mFragmentManager = null;
        if (fragment.mRemoving && !fragment.isInBackStack()) {
            z = true;
        }
        if (z || this.mFragmentStore.getNonConfig().shouldDestroy(this.mFragment)) {
            if (FragmentManager.isLoggingEnabled(3)) {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("initState called for fragment: ");
                sbM2.append(this.mFragment);
                Log.d(TAG, sbM2.toString());
            }
            this.mFragment.initState();
        }
    }

    public final void ensureInflatedView() {
        Fragment fragment = this.mFragment;
        if (fragment.mFromLayout && fragment.mInLayout && !fragment.mPerformedCreateView) {
            if (FragmentManager.isLoggingEnabled(3)) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto CREATE_VIEW: ");
                sbM.append(this.mFragment);
                Log.d(TAG, sbM.toString());
            }
            Fragment fragment2 = this.mFragment;
            fragment2.performCreateView(fragment2.performGetLayoutInflater(fragment2.mSavedFragmentState), null, this.mFragment.mSavedFragmentState);
            View view = this.mFragment.mView;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                Fragment fragment3 = this.mFragment;
                fragment3.mView.setTag(R.id.fragment_container_view_tag, fragment3);
                Fragment fragment4 = this.mFragment;
                if (fragment4.mHidden) {
                    fragment4.mView.setVisibility(8);
                }
                Fragment fragment5 = this.mFragment;
                fragment5.onViewCreated(fragment5.mView, fragment5.mSavedFragmentState);
                fragment5.mChildFragmentManager.dispatchViewCreated();
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
                Fragment fragment6 = this.mFragment;
                fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(fragment6, fragment6.mView, fragment6.mSavedFragmentState, false);
                this.mFragment.mState = 2;
            }
        }
    }

    @NonNull
    public final Fragment getFragment() {
        return this.mFragment;
    }

    public final void moveToExpectedState() {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        ViewGroup viewGroup3;
        if (this.mMovingToState) {
            if (FragmentManager.isLoggingEnabled(2)) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Ignoring re-entrant call to moveToExpectedState() for ");
                sbM.append(this.mFragment);
                Log.v(TAG, sbM.toString());
                return;
            }
            return;
        }
        try {
            this.mMovingToState = true;
            while (true) {
                int iComputeExpectedState = computeExpectedState();
                Fragment fragment = this.mFragment;
                int i = fragment.mState;
                if (iComputeExpectedState == i) {
                    if (FragmentManager.USE_STATE_MANAGER && fragment.mHiddenChanged) {
                        if (fragment.mView != null && (viewGroup = fragment.mContainer) != null) {
                            SpecialEffectsController orCreateController = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager());
                            if (this.mFragment.mHidden) {
                                orCreateController.enqueueHide(this);
                            } else {
                                orCreateController.enqueueShow(this);
                            }
                        }
                        Fragment fragment2 = this.mFragment;
                        FragmentManager fragmentManager = fragment2.mFragmentManager;
                        if (fragmentManager != null) {
                            fragmentManager.invalidateMenuForFragment(fragment2);
                        }
                        Fragment fragment3 = this.mFragment;
                        fragment3.mHiddenChanged = false;
                        fragment3.onHiddenChanged(fragment3.mHidden);
                    }
                    return;
                }
                if (iComputeExpectedState <= i) {
                    switch (i - 1) {
                        case -1:
                            detach();
                            break;
                        case 0:
                            destroy();
                            break;
                        case 1:
                            destroyFragmentView();
                            this.mFragment.mState = 1;
                            break;
                        case 2:
                            fragment.mInLayout = false;
                            fragment.mState = 2;
                            break;
                        case 3:
                            if (FragmentManager.isLoggingEnabled(3)) {
                                Log.d(TAG, "movefrom ACTIVITY_CREATED: " + this.mFragment);
                            }
                            Fragment fragment4 = this.mFragment;
                            if (fragment4.mView != null && fragment4.mSavedViewState == null) {
                                saveViewState();
                            }
                            Fragment fragment5 = this.mFragment;
                            if (fragment5.mView != null && (viewGroup3 = fragment5.mContainer) != null) {
                                SpecialEffectsController.getOrCreateController(viewGroup3, fragment5.getParentFragmentManager()).enqueueRemove(this);
                            }
                            this.mFragment.mState = 3;
                            break;
                        case 4:
                            stop();
                            break;
                        case 5:
                            fragment.mState = 5;
                            break;
                        case 6:
                            pause();
                            break;
                    }
                } else {
                    switch (i + 1) {
                        case 0:
                            attach();
                            break;
                        case 1:
                            create();
                            break;
                        case 2:
                            ensureInflatedView();
                            createView();
                            break;
                        case 3:
                            activityCreated();
                            break;
                        case 4:
                            if (fragment.mView != null && (viewGroup2 = fragment.mContainer) != null) {
                                SpecialEffectsController.getOrCreateController(viewGroup2, fragment.getParentFragmentManager()).enqueueAdd(SpecialEffectsController.Operation.State.from(this.mFragment.mView.getVisibility()), this);
                            }
                            this.mFragment.mState = 4;
                            break;
                        case 5:
                            start();
                            break;
                        case 6:
                            fragment.mState = 6;
                            break;
                        case 7:
                            resume();
                            break;
                    }
                }
            }
        } finally {
            this.mMovingToState = false;
        }
    }

    public final void pause() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("movefrom RESUMED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        this.mFragment.performPause();
        this.mDispatcher.dispatchOnFragmentPaused(this.mFragment, false);
    }

    public final void restoreState(@NonNull ClassLoader classLoader) {
        Bundle bundle = this.mFragment.mSavedFragmentState;
        if (bundle == null) {
            return;
        }
        bundle.setClassLoader(classLoader);
        Fragment fragment = this.mFragment;
        fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
        Fragment fragment2 = this.mFragment;
        fragment2.mSavedViewRegistryState = fragment2.mSavedFragmentState.getBundle(VIEW_REGISTRY_STATE_TAG);
        Fragment fragment3 = this.mFragment;
        fragment3.mTargetWho = fragment3.mSavedFragmentState.getString(TARGET_STATE_TAG);
        Fragment fragment4 = this.mFragment;
        if (fragment4.mTargetWho != null) {
            fragment4.mTargetRequestCode = fragment4.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
        }
        Fragment fragment5 = this.mFragment;
        Boolean bool = fragment5.mSavedUserVisibleHint;
        if (bool != null) {
            fragment5.mUserVisibleHint = bool.booleanValue();
            this.mFragment.mSavedUserVisibleHint = null;
        } else {
            fragment5.mUserVisibleHint = fragment5.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
        }
        Fragment fragment6 = this.mFragment;
        if (fragment6.mUserVisibleHint) {
            return;
        }
        fragment6.mDeferStart = true;
    }

    public final void resume() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto RESUMED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        Fragment.AnimationInfo animationInfo = this.mFragment.mAnimationInfo;
        View view = animationInfo == null ? null : animationInfo.mFocusedView;
        if (view != null && isFragmentViewChild(view)) {
            boolean zRequestFocus = view.requestFocus();
            if (FragmentManager.isLoggingEnabled(2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("requestFocus: Restoring focused view ");
                sb.append(view);
                sb.append(" ");
                sb.append(zRequestFocus ? "succeeded" : "failed");
                sb.append(" on Fragment ");
                sb.append(this.mFragment);
                sb.append(" resulting in focused view ");
                sb.append(this.mFragment.mView.findFocus());
                Log.v(TAG, sb.toString());
            }
        }
        this.mFragment.setFocusedView(null);
        this.mFragment.performResume();
        this.mDispatcher.dispatchOnFragmentResumed(this.mFragment, false);
        Fragment fragment = this.mFragment;
        fragment.mSavedFragmentState = null;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
    }

    @Nullable
    public final Fragment.SavedState saveInstanceState() {
        Bundle bundleSaveBasicState;
        if (this.mFragment.mState <= -1 || (bundleSaveBasicState = saveBasicState()) == null) {
            return null;
        }
        return new Fragment.SavedState(bundleSaveBasicState);
    }

    @NonNull
    public final FragmentState saveState() {
        FragmentState fragmentState = new FragmentState(this.mFragment);
        Fragment fragment = this.mFragment;
        if (fragment.mState <= -1 || fragmentState.mSavedFragmentState != null) {
            fragmentState.mSavedFragmentState = fragment.mSavedFragmentState;
        } else {
            Bundle bundleSaveBasicState = saveBasicState();
            fragmentState.mSavedFragmentState = bundleSaveBasicState;
            if (this.mFragment.mTargetWho != null) {
                if (bundleSaveBasicState == null) {
                    fragmentState.mSavedFragmentState = new Bundle();
                }
                fragmentState.mSavedFragmentState.putString(TARGET_STATE_TAG, this.mFragment.mTargetWho);
                int i = this.mFragment.mTargetRequestCode;
                if (i != 0) {
                    fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, i);
                }
            }
        }
        return fragmentState;
    }

    public final void saveViewState() {
        if (this.mFragment.mView == null) {
            return;
        }
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        this.mFragment.mView.saveHierarchyState(sparseArray);
        if (sparseArray.size() > 0) {
            this.mFragment.mSavedViewState = sparseArray;
        }
        Bundle bundle = new Bundle();
        this.mFragment.mViewLifecycleOwner.performSave(bundle);
        if (bundle.isEmpty()) {
            return;
        }
        this.mFragment.mSavedViewRegistryState = bundle;
    }

    public final void setFragmentManagerState(int i) {
        this.mFragmentManagerState = i;
    }

    public final void start() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("moveto STARTED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        this.mFragment.performStart();
        this.mDispatcher.dispatchOnFragmentStarted(this.mFragment, false);
    }

    public final void stop() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("movefrom STARTED: ");
            sbM.append(this.mFragment);
            Log.d(TAG, sbM.toString());
        }
        this.mFragment.performStop();
        this.mDispatcher.dispatchOnFragmentStopped(this.mFragment, false);
    }

    public FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, @NonNull FragmentStore fragmentStore, @NonNull ClassLoader classLoader, @NonNull FragmentFactory fragmentFactory, @NonNull FragmentState fragmentState) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        Fragment fragmentInstantiate = fragmentFactory.instantiate(classLoader, fragmentState.mClassName);
        this.mFragment = fragmentInstantiate;
        Bundle bundle = fragmentState.mArguments;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
        fragmentInstantiate.setArguments(fragmentState.mArguments);
        fragmentInstantiate.mWho = fragmentState.mWho;
        fragmentInstantiate.mFromLayout = fragmentState.mFromLayout;
        fragmentInstantiate.mRestored = true;
        fragmentInstantiate.mFragmentId = fragmentState.mFragmentId;
        fragmentInstantiate.mContainerId = fragmentState.mContainerId;
        fragmentInstantiate.mTag = fragmentState.mTag;
        fragmentInstantiate.mRetainInstance = fragmentState.mRetainInstance;
        fragmentInstantiate.mRemoving = fragmentState.mRemoving;
        fragmentInstantiate.mDetached = fragmentState.mDetached;
        fragmentInstantiate.mHidden = fragmentState.mHidden;
        fragmentInstantiate.mMaxState = Lifecycle.State.values()[fragmentState.mMaxLifecycleState];
        Bundle bundle2 = fragmentState.mSavedFragmentState;
        if (bundle2 != null) {
            fragmentInstantiate.mSavedFragmentState = bundle2;
        } else {
            fragmentInstantiate.mSavedFragmentState = new Bundle();
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "Instantiated fragment " + fragmentInstantiate);
        }
    }

    public FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, @NonNull FragmentStore fragmentStore, @NonNull Fragment fragment, @NonNull FragmentState fragmentState) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
        fragment.mBackStackNesting = 0;
        fragment.mInLayout = false;
        fragment.mAdded = false;
        Fragment fragment2 = fragment.mTarget;
        fragment.mTargetWho = fragment2 != null ? fragment2.mWho : null;
        fragment.mTarget = null;
        Bundle bundle = fragmentState.mSavedFragmentState;
        if (bundle != null) {
            fragment.mSavedFragmentState = bundle;
        } else {
            fragment.mSavedFragmentState = new Bundle();
        }
    }
}
