package androidx.leanback.app;

import android.R;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.GravityCompat;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.widget.DiffCallback;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;
import androidx.leanback.widget.GuidedActionAdapter;
import androidx.leanback.widget.GuidedActionAdapterGroup;
import androidx.leanback.widget.GuidedActionsStylist;
import androidx.leanback.widget.NonOverlappingLinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class GuidedStepFragment extends Fragment implements GuidedActionAdapter.FocusListener {
    private static final boolean DEBUG = false;
    private static final String ENTRY_NAME_ENTRANCE = "GuidedStepEntrance";
    private static final String ENTRY_NAME_REPLACE = "GuidedStepDefault";
    private static final String EXTRA_ACTION_PREFIX = "action_";
    private static final String EXTRA_BUTTON_ACTION_PREFIX = "buttonaction_";
    public static final String EXTRA_UI_STYLE = "uiStyle";
    private static final boolean IS_FRAMEWORK_FRAGMENT = true;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int SLIDE_FROM_BOTTOM = 1;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int SLIDE_FROM_SIDE = 0;
    private static final String TAG = "GuidedStepF";
    private static final String TAG_LEAN_BACK_ACTIONS_FRAGMENT = "leanBackGuidedStepFragment";
    public static final int UI_STYLE_ACTIVITY_ROOT = 2;

    @Deprecated
    public static final int UI_STYLE_DEFAULT = 0;
    public static final int UI_STYLE_ENTRANCE = 1;
    public static final int UI_STYLE_REPLACE = 0;
    private GuidedActionAdapter mAdapter;
    private GuidedActionAdapterGroup mAdapterGroup;
    private GuidedActionAdapter mButtonAdapter;
    private GuidedActionAdapter mSubAdapter;
    private ContextThemeWrapper mThemeWrapper;
    private List<GuidedAction> mActions = new ArrayList();
    private List<GuidedAction> mButtonActions = new ArrayList();
    private int entranceTransitionType = 0;
    private GuidanceStylist mGuidanceStylist = onCreateGuidanceStylist();
    public GuidedActionsStylist mActionsStylist = onCreateActionsStylist();
    private GuidedActionsStylist mButtonActionsStylist = onCreateButtonActionsStylist();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class DummyFragment extends Fragment {
        @Override // android.app.Fragment
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View view = new View(layoutInflater.getContext());
            view.setVisibility(8);
            return view;
        }
    }

    public GuidedStepFragment() {
        onProvideFragmentTransitions();
    }

    public static int add(FragmentManager fragmentManager, GuidedStepFragment guidedStepFragment) {
        return add(fragmentManager, guidedStepFragment, R.id.content);
    }

    public static int addAsRoot(Activity activity, GuidedStepFragment guidedStepFragment, int i) {
        activity.getWindow().getDecorView();
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag(TAG_LEAN_BACK_ACTIONS_FRAGMENT) != null) {
            Log.w(TAG, "Fragment is already exists, likely calling addAsRoot() when savedInstanceState is not null in Activity.onCreate().");
            return -1;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        guidedStepFragment.setUiStyle(2);
        return fragmentTransactionBeginTransaction.replace(i, guidedStepFragment, TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
    }

    private static void addNonNullSharedElementTransition(FragmentTransaction fragmentTransaction, View view, String str) {
        if (view != null) {
            fragmentTransaction.addSharedElement(view, str);
        }
    }

    public static GuidedStepFragment getCurrentGuidedStepFragment(FragmentManager fragmentManager) {
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(TAG_LEAN_BACK_ACTIONS_FRAGMENT);
        if (fragmentFindFragmentByTag instanceof GuidedStepFragment) {
            return (GuidedStepFragment) fragmentFindFragmentByTag;
        }
        return null;
    }

    private int getFirstCheckedAction() {
        int size = this.mActions.size();
        for (int i = 0; i < size; i++) {
            if (this.mActions.get(i).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    private LayoutInflater getThemeInflater(LayoutInflater layoutInflater) {
        ContextThemeWrapper contextThemeWrapper = this.mThemeWrapper;
        return contextThemeWrapper == null ? layoutInflater : layoutInflater.cloneInContext(contextThemeWrapper);
    }

    private static boolean isGuidedStepTheme(Context context) {
        int i = androidx.leanback.R.attr.guidedStepThemeFlag;
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(i, typedValue, true) && typedValue.type == 18 && typedValue.data != 0;
    }

    public static boolean isSaveEnabled(GuidedAction guidedAction) {
        return guidedAction.isAutoSaveRestoreEnabled() && guidedAction.getId() != -1;
    }

    private void resolveTheme() {
        Context context = FragmentUtil.getContext(this);
        int iOnProvideTheme = onProvideTheme();
        if (iOnProvideTheme != -1 || isGuidedStepTheme(context)) {
            if (iOnProvideTheme != -1) {
                this.mThemeWrapper = new ContextThemeWrapper(context, iOnProvideTheme);
                return;
            }
            return;
        }
        int i = androidx.leanback.R.attr.guidedStepTheme;
        TypedValue typedValue = new TypedValue();
        boolean zResolveAttribute = context.getTheme().resolveAttribute(i, typedValue, true);
        if (zResolveAttribute) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, typedValue.resourceId);
            if (isGuidedStepTheme(contextThemeWrapper)) {
                this.mThemeWrapper = contextThemeWrapper;
            } else {
                zResolveAttribute = false;
                this.mThemeWrapper = null;
            }
        }
        if (zResolveAttribute) {
            return;
        }
        Log.e(TAG, "GuidedStepFragment does not have an appropriate theme set.");
    }

    public void collapseAction(boolean z) {
        GuidedActionsStylist guidedActionsStylist = this.mActionsStylist;
        if (guidedActionsStylist == null || guidedActionsStylist.getActionsGridView() == null) {
            return;
        }
        this.mActionsStylist.collapseAction(z);
    }

    public void collapseSubActions() {
        collapseAction(true);
    }

    public void expandAction(GuidedAction guidedAction, boolean z) {
        this.mActionsStylist.expandAction(guidedAction, z);
    }

    public void expandSubActions(GuidedAction guidedAction) {
        if (guidedAction.hasSubActions()) {
            expandAction(guidedAction, true);
        }
    }

    public GuidedAction findActionById(long j) {
        int iFindActionPositionById = findActionPositionById(j);
        if (iFindActionPositionById >= 0) {
            return this.mActions.get(iFindActionPositionById);
        }
        return null;
    }

    public int findActionPositionById(long j) {
        if (this.mActions == null) {
            return -1;
        }
        for (int i = 0; i < this.mActions.size(); i++) {
            this.mActions.get(i);
            if (this.mActions.get(i).getId() == j) {
                return i;
            }
        }
        return -1;
    }

    public GuidedAction findButtonActionById(long j) {
        int iFindButtonActionPositionById = findButtonActionPositionById(j);
        if (iFindButtonActionPositionById >= 0) {
            return this.mButtonActions.get(iFindButtonActionPositionById);
        }
        return null;
    }

    public int findButtonActionPositionById(long j) {
        if (this.mButtonActions == null) {
            return -1;
        }
        for (int i = 0; i < this.mButtonActions.size(); i++) {
            this.mButtonActions.get(i);
            if (this.mButtonActions.get(i).getId() == j) {
                return i;
            }
        }
        return -1;
    }

    public void finishGuidedStepFragments() {
        FragmentManager fragmentManager = getFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            for (int i = backStackEntryCount - 1; i >= 0; i--) {
                FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(i);
                String name = backStackEntryAt.getName();
                if (name != null && name.startsWith(ENTRY_NAME_ENTRANCE)) {
                    GuidedStepFragment currentGuidedStepFragment = getCurrentGuidedStepFragment(fragmentManager);
                    if (currentGuidedStepFragment != null) {
                        currentGuidedStepFragment.setUiStyle(1);
                    }
                    fragmentManager.popBackStackImmediate(backStackEntryAt.getId(), 1);
                    return;
                }
            }
        }
        ActivityCompat.finishAfterTransition(getActivity());
    }

    public View getActionItemView(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = this.mActionsStylist.getActionsGridView().findViewHolderForPosition(i);
        if (viewHolderFindViewHolderForPosition == null) {
            return null;
        }
        return viewHolderFindViewHolderForPosition.itemView;
    }

    public List<GuidedAction> getActions() {
        return this.mActions;
    }

    public final String getAutoRestoreKey(GuidedAction guidedAction) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(EXTRA_ACTION_PREFIX);
        sbM.append(guidedAction.getId());
        return sbM.toString();
    }

    public View getButtonActionItemView(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = this.mButtonActionsStylist.getActionsGridView().findViewHolderForPosition(i);
        if (viewHolderFindViewHolderForPosition == null) {
            return null;
        }
        return viewHolderFindViewHolderForPosition.itemView;
    }

    public List<GuidedAction> getButtonActions() {
        return this.mButtonActions;
    }

    public final String getButtonAutoRestoreKey(GuidedAction guidedAction) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(EXTRA_BUTTON_ACTION_PREFIX);
        sbM.append(guidedAction.getId());
        return sbM.toString();
    }

    public GuidanceStylist getGuidanceStylist() {
        return this.mGuidanceStylist;
    }

    public GuidedActionsStylist getGuidedActionsStylist() {
        return this.mActionsStylist;
    }

    public GuidedActionsStylist getGuidedButtonActionsStylist() {
        return this.mButtonActionsStylist;
    }

    public int getSelectedActionPosition() {
        return this.mActionsStylist.getActionsGridView().getSelectedPosition();
    }

    public int getSelectedButtonActionPosition() {
        return this.mButtonActionsStylist.getActionsGridView().getSelectedPosition();
    }

    public int getUiStyle() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return 1;
        }
        return arguments.getInt("uiStyle", 1);
    }

    public boolean isExpanded() {
        return this.mActionsStylist.isExpanded();
    }

    public boolean isFocusOutEndAllowed() {
        return false;
    }

    public boolean isFocusOutStartAllowed() {
        return false;
    }

    public boolean isSubActionsExpanded() {
        return this.mActionsStylist.isSubActionsExpanded();
    }

    public void notifyActionChanged(int i) {
        GuidedActionAdapter guidedActionAdapter = this.mAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.notifyItemChanged(i);
        }
    }

    public void notifyButtonActionChanged(int i) {
        GuidedActionAdapter guidedActionAdapter = this.mButtonAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.notifyItemChanged(i);
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        onProvideFragmentTransitions();
        ArrayList arrayList = new ArrayList();
        onCreateActions(arrayList, bundle);
        if (bundle != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                GuidedAction guidedAction = (GuidedAction) arrayList.get(i);
                if (isSaveEnabled(guidedAction)) {
                    guidedAction.onRestoreInstanceState(bundle, getAutoRestoreKey(guidedAction));
                }
            }
        }
        setActions(arrayList);
        ArrayList arrayList2 = new ArrayList();
        onCreateButtonActions(arrayList2, bundle);
        if (bundle != null) {
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                GuidedAction guidedAction2 = (GuidedAction) arrayList2.get(i2);
                if (isSaveEnabled(guidedAction2)) {
                    guidedAction2.onRestoreInstanceState(bundle, getButtonAutoRestoreKey(guidedAction2));
                }
            }
        }
        setButtonActions(arrayList2);
    }

    public void onCreateActions(@NonNull List<GuidedAction> list, Bundle bundle) {
    }

    public GuidedActionsStylist onCreateActionsStylist() {
        return new GuidedActionsStylist();
    }

    public View onCreateBackgroundView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(androidx.leanback.R.layout.lb_guidedstep_background, viewGroup, false);
    }

    public void onCreateButtonActions(@NonNull List<GuidedAction> list, Bundle bundle) {
    }

    public GuidedActionsStylist onCreateButtonActionsStylist() {
        GuidedActionsStylist guidedActionsStylist = new GuidedActionsStylist();
        guidedActionsStylist.setAsButtonActions();
        return guidedActionsStylist;
    }

    @NonNull
    public GuidanceStylist.Guidance onCreateGuidance(Bundle bundle) {
        return new GuidanceStylist.Guidance("", "", "", null);
    }

    public GuidanceStylist onCreateGuidanceStylist() {
        return new GuidanceStylist();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        resolveTheme();
        LayoutInflater themeInflater = getThemeInflater(layoutInflater);
        GuidedStepRootLayout guidedStepRootLayout = (GuidedStepRootLayout) themeInflater.inflate(androidx.leanback.R.layout.lb_guidedstep_fragment, viewGroup, false);
        guidedStepRootLayout.setFocusOutStart(isFocusOutStartAllowed());
        guidedStepRootLayout.setFocusOutEnd(isFocusOutEndAllowed());
        ViewGroup viewGroup2 = (ViewGroup) guidedStepRootLayout.findViewById(androidx.leanback.R.id.content_fragment);
        ViewGroup viewGroup3 = (ViewGroup) guidedStepRootLayout.findViewById(androidx.leanback.R.id.action_fragment);
        ((NonOverlappingLinearLayout) viewGroup3).setFocusableViewAvailableFixEnabled(true);
        viewGroup2.addView(this.mGuidanceStylist.onCreateView(themeInflater, viewGroup2, onCreateGuidance(bundle)));
        viewGroup3.addView(this.mActionsStylist.onCreateView(themeInflater, viewGroup3));
        View viewOnCreateView = this.mButtonActionsStylist.onCreateView(themeInflater, viewGroup3);
        viewGroup3.addView(viewOnCreateView);
        GuidedActionAdapter.EditListener editListener = new GuidedActionAdapter.EditListener() { // from class: androidx.leanback.app.GuidedStepFragment.1
            @Override // androidx.leanback.widget.GuidedActionAdapter.EditListener
            public void onGuidedActionEditCanceled(GuidedAction guidedAction) {
                GuidedStepFragment.this.onGuidedActionEditCanceled(guidedAction);
            }

            @Override // androidx.leanback.widget.GuidedActionAdapter.EditListener
            public long onGuidedActionEditedAndProceed(GuidedAction guidedAction) {
                return GuidedStepFragment.this.onGuidedActionEditedAndProceed(guidedAction);
            }

            @Override // androidx.leanback.widget.GuidedActionAdapter.EditListener
            public void onImeClose() {
                GuidedStepFragment.this.runImeAnimations(false);
            }

            @Override // androidx.leanback.widget.GuidedActionAdapter.EditListener
            public void onImeOpen() {
                GuidedStepFragment.this.runImeAnimations(true);
            }
        };
        this.mAdapter = new GuidedActionAdapter(this.mActions, new GuidedActionAdapter.ClickListener() { // from class: androidx.leanback.app.GuidedStepFragment.2
            @Override // androidx.leanback.widget.GuidedActionAdapter.ClickListener
            public void onGuidedActionClicked(GuidedAction guidedAction) {
                GuidedStepFragment.this.onGuidedActionClicked(guidedAction);
                if (GuidedStepFragment.this.isExpanded()) {
                    GuidedStepFragment.this.collapseAction(true);
                } else if (guidedAction.hasSubActions() || guidedAction.hasEditableActivatorView()) {
                    GuidedStepFragment.this.expandAction(guidedAction, true);
                }
            }
        }, this, this.mActionsStylist, false);
        this.mButtonAdapter = new GuidedActionAdapter(this.mButtonActions, new GuidedActionAdapter.ClickListener() { // from class: androidx.leanback.app.GuidedStepFragment.3
            @Override // androidx.leanback.widget.GuidedActionAdapter.ClickListener
            public void onGuidedActionClicked(GuidedAction guidedAction) {
                GuidedStepFragment.this.onGuidedActionClicked(guidedAction);
            }
        }, this, this.mButtonActionsStylist, false);
        this.mSubAdapter = new GuidedActionAdapter(null, new GuidedActionAdapter.ClickListener() { // from class: androidx.leanback.app.GuidedStepFragment.4
            @Override // androidx.leanback.widget.GuidedActionAdapter.ClickListener
            public void onGuidedActionClicked(GuidedAction guidedAction) {
                if (!GuidedStepFragment.this.mActionsStylist.isInExpandTransition() && GuidedStepFragment.this.onSubGuidedActionClicked(guidedAction)) {
                    GuidedStepFragment.this.collapseSubActions();
                }
            }
        }, this, this.mActionsStylist, true);
        GuidedActionAdapterGroup guidedActionAdapterGroup = new GuidedActionAdapterGroup();
        this.mAdapterGroup = guidedActionAdapterGroup;
        guidedActionAdapterGroup.addAdpter(this.mAdapter, this.mButtonAdapter);
        this.mAdapterGroup.addAdpter(this.mSubAdapter, null);
        this.mAdapterGroup.setEditListener(editListener);
        this.mActionsStylist.setEditListener(editListener);
        this.mActionsStylist.getActionsGridView().setAdapter(this.mAdapter);
        if (this.mActionsStylist.getSubActionsGridView() != null) {
            this.mActionsStylist.getSubActionsGridView().setAdapter(this.mSubAdapter);
        }
        this.mButtonActionsStylist.getActionsGridView().setAdapter(this.mButtonAdapter);
        if (this.mButtonActions.size() == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewOnCreateView.getLayoutParams();
            layoutParams.weight = 0.0f;
            viewOnCreateView.setLayoutParams(layoutParams);
        } else {
            Context context = this.mThemeWrapper;
            if (context == null) {
                context = FragmentUtil.getContext(this);
            }
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(androidx.leanback.R.attr.guidedActionContentWidthWeightTwoPanels, typedValue, true)) {
                View viewFindViewById = guidedStepRootLayout.findViewById(androidx.leanback.R.id.action_fragment_root);
                float f = typedValue.getFloat();
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
                layoutParams2.weight = f;
                viewFindViewById.setLayoutParams(layoutParams2);
            }
        }
        View viewOnCreateBackgroundView = onCreateBackgroundView(themeInflater, guidedStepRootLayout, bundle);
        if (viewOnCreateBackgroundView != null) {
            ((FrameLayout) guidedStepRootLayout.findViewById(androidx.leanback.R.id.guidedstep_background_view_root)).addView(viewOnCreateBackgroundView, 0);
        }
        return guidedStepRootLayout;
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        this.mGuidanceStylist.onDestroyView();
        this.mActionsStylist.onDestroyView();
        this.mButtonActionsStylist.onDestroyView();
        this.mAdapter = null;
        this.mSubAdapter = null;
        this.mButtonAdapter = null;
        this.mAdapterGroup = null;
        super.onDestroyView();
    }

    public void onGuidedActionClicked(GuidedAction guidedAction) {
    }

    public void onGuidedActionEditCanceled(GuidedAction guidedAction) {
        onGuidedActionEdited(guidedAction);
    }

    @Deprecated
    public void onGuidedActionEdited(GuidedAction guidedAction) {
    }

    public long onGuidedActionEditedAndProceed(GuidedAction guidedAction) {
        onGuidedActionEdited(guidedAction);
        return -2L;
    }

    @Override // androidx.leanback.widget.GuidedActionAdapter.FocusListener
    public void onGuidedActionFocused(GuidedAction guidedAction) {
    }

    public final void onProvideFragmentTransitions() {
        int uiStyle = getUiStyle();
        if (uiStyle == 0) {
            Object objCreateFadeAndShortSlide = TransitionHelper.createFadeAndShortSlide(GravityCompat.END);
            TransitionHelper.exclude(objCreateFadeAndShortSlide, androidx.leanback.R.id.guidedstep_background, true);
            int i = androidx.leanback.R.id.guidedactions_sub_list_background;
            TransitionHelper.exclude(objCreateFadeAndShortSlide, i, true);
            setEnterTransition((Transition) objCreateFadeAndShortSlide);
            Object objCreateFadeTransition = TransitionHelper.createFadeTransition(3);
            TransitionHelper.include(objCreateFadeTransition, i);
            Object objCreateChangeBounds = TransitionHelper.createChangeBounds(false);
            Object objCreateTransitionSet = TransitionHelper.createTransitionSet(false);
            TransitionHelper.addTransition(objCreateTransitionSet, objCreateFadeTransition);
            TransitionHelper.addTransition(objCreateTransitionSet, objCreateChangeBounds);
            setSharedElementEnterTransition((Transition) objCreateTransitionSet);
        } else if (uiStyle == 1) {
            if (this.entranceTransitionType == 0) {
                Object objCreateFadeTransition2 = TransitionHelper.createFadeTransition(3);
                TransitionHelper.include(objCreateFadeTransition2, androidx.leanback.R.id.guidedstep_background);
                Object objCreateFadeAndShortSlide2 = TransitionHelper.createFadeAndShortSlide(GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
                TransitionHelper.include(objCreateFadeAndShortSlide2, androidx.leanback.R.id.content_fragment);
                TransitionHelper.include(objCreateFadeAndShortSlide2, androidx.leanback.R.id.action_fragment_root);
                Object objCreateTransitionSet2 = TransitionHelper.createTransitionSet(false);
                TransitionHelper.addTransition(objCreateTransitionSet2, objCreateFadeTransition2);
                TransitionHelper.addTransition(objCreateTransitionSet2, objCreateFadeAndShortSlide2);
                setEnterTransition((Transition) objCreateTransitionSet2);
            } else {
                Object objCreateFadeAndShortSlide3 = TransitionHelper.createFadeAndShortSlide(80);
                TransitionHelper.include(objCreateFadeAndShortSlide3, androidx.leanback.R.id.guidedstep_background_view_root);
                Object objCreateTransitionSet3 = TransitionHelper.createTransitionSet(false);
                TransitionHelper.addTransition(objCreateTransitionSet3, objCreateFadeAndShortSlide3);
                setEnterTransition((Transition) objCreateTransitionSet3);
            }
            setSharedElementEnterTransition(null);
        } else if (uiStyle == 2) {
            setEnterTransition(null);
            setSharedElementEnterTransition(null);
        }
        Object objCreateFadeAndShortSlide4 = TransitionHelper.createFadeAndShortSlide(GravityCompat.START);
        TransitionHelper.exclude(objCreateFadeAndShortSlide4, androidx.leanback.R.id.guidedstep_background, true);
        TransitionHelper.exclude(objCreateFadeAndShortSlide4, androidx.leanback.R.id.guidedactions_sub_list_background, true);
        setExitTransition((Transition) objCreateFadeAndShortSlide4);
    }

    public int onProvideTheme() {
        return -1;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        getView().findViewById(androidx.leanback.R.id.action_fragment).requestFocus();
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        List<GuidedAction> list = this.mActions;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            GuidedAction guidedAction = list.get(i);
            if (isSaveEnabled(guidedAction)) {
                guidedAction.onSaveInstanceState(bundle, getAutoRestoreKey(guidedAction));
            }
        }
        List<GuidedAction> list2 = this.mButtonActions;
        int size2 = list2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            GuidedAction guidedAction2 = list2.get(i2);
            if (isSaveEnabled(guidedAction2)) {
                guidedAction2.onSaveInstanceState(bundle, getButtonAutoRestoreKey(guidedAction2));
            }
        }
    }

    public boolean onSubGuidedActionClicked(GuidedAction guidedAction) {
        return true;
    }

    public void openInEditMode(GuidedAction guidedAction) {
        this.mActionsStylist.openInEditMode(guidedAction);
    }

    public void popBackStackToGuidedStepFragment(Class cls, int i) {
        if (GuidedStepFragment.class.isAssignableFrom(cls)) {
            FragmentManager fragmentManager = getFragmentManager();
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
            String name = cls.getName();
            if (backStackEntryCount > 0) {
                for (int i2 = backStackEntryCount - 1; i2 >= 0; i2--) {
                    FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(i2);
                    String name2 = backStackEntryAt.getName();
                    if (name.equals(name2.startsWith(ENTRY_NAME_REPLACE) ? name2.substring(17) : name2.startsWith(ENTRY_NAME_ENTRANCE) ? name2.substring(18) : "")) {
                        fragmentManager.popBackStackImmediate(backStackEntryAt.getId(), i);
                        return;
                    }
                }
            }
        }
    }

    public final void runImeAnimations(boolean z) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            this.mGuidanceStylist.onImeAppearing(arrayList);
            this.mActionsStylist.onImeAppearing(arrayList);
            this.mButtonActionsStylist.onImeAppearing(arrayList);
        } else {
            this.mGuidanceStylist.onImeDisappearing(arrayList);
            this.mActionsStylist.onImeDisappearing(arrayList);
            this.mButtonActionsStylist.onImeDisappearing(arrayList);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        animatorSet.start();
    }

    public void setActions(List<GuidedAction> list) {
        this.mActions = list;
        GuidedActionAdapter guidedActionAdapter = this.mAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.setActions(list);
        }
    }

    public void setActionsDiffCallback(DiffCallback<GuidedAction> diffCallback) {
        this.mAdapter.setDiffCallback(diffCallback);
    }

    public void setButtonActions(List<GuidedAction> list) {
        this.mButtonActions = list;
        GuidedActionAdapter guidedActionAdapter = this.mButtonAdapter;
        if (guidedActionAdapter != null) {
            guidedActionAdapter.setActions(list);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setEntranceTransitionType(int i) {
        this.entranceTransitionType = i;
    }

    public void setSelectedActionPosition(int i) {
        this.mActionsStylist.getActionsGridView().setSelectedPosition(i);
    }

    public void setSelectedButtonActionPosition(int i) {
        this.mButtonActionsStylist.getActionsGridView().setSelectedPosition(i);
    }

    public void setUiStyle(int i) {
        boolean z;
        int uiStyle = getUiStyle();
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            z = true;
        } else {
            z = false;
        }
        arguments.putInt("uiStyle", i);
        if (z) {
            setArguments(arguments);
        }
        if (i != uiStyle) {
            onProvideFragmentTransitions();
        }
    }

    public static int add(FragmentManager fragmentManager, GuidedStepFragment guidedStepFragment, int i) {
        String string;
        GuidedStepFragment currentGuidedStepFragment = getCurrentGuidedStepFragment(fragmentManager);
        int i2 = currentGuidedStepFragment != null ? 1 : 0;
        if (Build.VERSION.SDK_INT < 23 && i2 == 0) {
            fragmentManager.beginTransaction().replace(i, new DummyFragment(), TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
        }
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        guidedStepFragment.setUiStyle(i2 ^ 1);
        int uiStyle = guidedStepFragment.getUiStyle();
        Class<?> cls = guidedStepFragment.getClass();
        if (uiStyle == 0) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(ENTRY_NAME_REPLACE);
            sbM.append(cls.getName());
            string = sbM.toString();
        } else if (uiStyle != 1) {
            string = "";
        } else {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m(ENTRY_NAME_ENTRANCE);
            sbM2.append(cls.getName());
            string = sbM2.toString();
        }
        fragmentTransactionBeginTransaction.addToBackStack(string);
        if (currentGuidedStepFragment != null) {
            View view = currentGuidedStepFragment.getView();
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.action_fragment_root), "action_fragment_root");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.action_fragment_background), "action_fragment_background");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.action_fragment), "action_fragment");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.guidedactions_root), "guidedactions_root");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.guidedactions_content), "guidedactions_content");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.guidedactions_list_background), "guidedactions_list_background");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.guidedactions_root2), "guidedactions_root2");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.guidedactions_content2), "guidedactions_content2");
            addNonNullSharedElementTransition(fragmentTransactionBeginTransaction, view.findViewById(androidx.leanback.R.id.guidedactions_list_background2), "guidedactions_list_background2");
        }
        return fragmentTransactionBeginTransaction.replace(i, guidedStepFragment, TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
    }
}
