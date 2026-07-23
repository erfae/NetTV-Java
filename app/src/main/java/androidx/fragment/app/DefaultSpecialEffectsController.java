package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* JADX INFO: renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class AnimationInfo extends SpecialEffectsInfo {

        @Nullable
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        public AnimationInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z;
        }

        @Nullable
        public final FragmentAnim.AnimationOrAnimator getAnimation(@NonNull Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator animationOrAnimatorLoadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = animationOrAnimatorLoadAnimation;
            this.mLoadedAnim = true;
            return animationOrAnimatorLoadAnimation;
        }
    }

    public static class SpecialEffectsInfo {

        @NonNull
        private final SpecialEffectsController.Operation mOperation;

        @NonNull
        private final CancellationSignal mSignal;

        public SpecialEffectsInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        public final void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }

        @NonNull
        public final SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        @NonNull
        public final CancellationSignal getSignal() {
            return this.mSignal;
        }

        public final boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State state;
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return stateFrom == finalState || !(stateFrom == (state = SpecialEffectsController.Operation.State.VISIBLE) || finalState == state);
        }
    }

    public DefaultSpecialEffectsController(@NonNull ViewGroup viewGroup) {
        super(viewGroup);
    }

    private void startAnimations(@NonNull List<AnimationInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, boolean z, @NonNull Map<SpecialEffectsController.Operation, Boolean> map) {
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> arrayList = new ArrayList();
        boolean z2 = false;
        for (final AnimationInfo animationInfo : list) {
            if (animationInfo.isVisibilityUnchanged()) {
                animationInfo.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
                if (animation == null) {
                    animationInfo.completeSpecialEffect();
                } else {
                    final Animator animator = animation.animator;
                    if (animator == null) {
                        arrayList.add(animationInfo);
                    } else {
                        final SpecialEffectsController.Operation operation = animationInfo.getOperation();
                        Fragment fragment = operation.getFragment();
                        if (Boolean.TRUE.equals(map.get(operation))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo.completeSpecialEffect();
                        } else {
                            final boolean z3 = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (z3) {
                                list2.remove(operation);
                            }
                            final View view = fragment.mView;
                            container.startViewTransition(view);
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    container.endViewTransition(view);
                                    if (z3) {
                                        operation.getFinalState().applyState(view);
                                    }
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            animator.setTarget(view);
                            animator.start();
                            animationInfo.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                }
                            });
                            z2 = true;
                        }
                    }
                }
            }
        }
        for (final AnimationInfo animationInfo2 : arrayList) {
            SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
            Fragment fragment2 = operation2.getFragment();
            if (z) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo2.completeSpecialEffect();
            } else if (z2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo2.completeSpecialEffect();
            } else {
                final View view2 = fragment2.mView;
                Animation animation2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo2.getAnimation(context))).animation);
                if (operation2.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view2.startAnimation(animation2);
                    animationInfo2.completeSpecialEffect();
                } else {
                    container.startViewTransition(view2);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, container, view2);
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation3) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                    container.endViewTransition(view2);
                                    animationInfo2.completeSpecialEffect();
                                }
                            });
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation3) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation3) {
                        }
                    });
                    view2.startAnimation(endViewTransitionAnimation);
                }
                animationInfo2.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view2.clearAnimation();
                        container.endViewTransition(view2);
                        animationInfo2.completeSpecialEffect();
                    }
                });
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:164:0x04b8  */
    /* JADX WARN: Code duplicated, block: B:166:0x04c2  */
    /* JADX WARN: Code duplicated, block: B:168:0x04c9  */
    /* JADX WARN: Code duplicated, block: B:170:0x04eb  */
    @NonNull
    private Map<SpecialEffectsController.Operation, Boolean> startTransitions(@NonNull List<TransitionInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, final boolean z, @Nullable final SpecialEffectsController.Operation operation, @Nullable final SpecialEffectsController.Operation operation2) {
        SpecialEffectsController.Operation operation3;
        SpecialEffectsController.Operation operation4;
        Iterator<TransitionInfo> it;
        ArrayList<View> arrayList;
        DefaultSpecialEffectsController defaultSpecialEffectsController;
        SpecialEffectsController.Operation operation5;
        SpecialEffectsController.Operation operation6;
        View view;
        Object obj;
        Object obj2;
        View view2;
        ArrayMap arrayMap;
        final ArrayList<View> arrayList2;
        HashMap map;
        SpecialEffectsController.Operation operation7;
        View view3;
        HashMap map2;
        View view4;
        final FragmentTransitionImpl fragmentTransitionImpl;
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayList<String> arrayList5;
        ArrayList<String> arrayList6;
        SharedElementCallback enterTransitionCallback;
        SharedElementCallback exitTransitionCallback;
        FragmentTransitionImpl fragmentTransitionImpl2;
        int i;
        final View view5;
        String strFindKeyForValue;
        FragmentTransitionImpl fragmentTransitionImpl3;
        HashMap map3 = new HashMap();
        FragmentTransitionImpl fragmentTransitionImpl4 = null;
        for (TransitionInfo transitionInfo : list) {
            if (!transitionInfo.isVisibilityUnchanged()) {
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                if (fragmentTransitionImpl4 == null) {
                    fragmentTransitionImpl4 = handlingImpl;
                } else if (handlingImpl != null && fragmentTransitionImpl4 != handlingImpl) {
                    StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Mixing framework transitions and AndroidX transitions is not allowed. Fragment ");
                    sbM.append(transitionInfo.getOperation().getFragment());
                    sbM.append(" returned Transition ");
                    sbM.append(transitionInfo.getTransition());
                    sbM.append(" which uses a different Transition  type than other Fragments.");
                    throw new IllegalArgumentException(sbM.toString());
                }
            }
        }
        if (fragmentTransitionImpl4 == null) {
            for (TransitionInfo transitionInfo2 : list) {
                map3.put(transitionInfo2.getOperation(), Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
            return map3;
        }
        View view6 = new View(getContainer().getContext());
        final Rect rect = new Rect();
        ArrayList<View> arrayList7 = new ArrayList<>();
        ArrayList<View> arrayList8 = new ArrayList<>();
        ArrayMap arrayMap2 = new ArrayMap();
        SpecialEffectsController.Operation operation8 = operation;
        SpecialEffectsController.Operation operation9 = operation2;
        Object obj3 = null;
        View view7 = null;
        boolean z2 = false;
        DefaultSpecialEffectsController defaultSpecialEffectsController2 = this;
        for (TransitionInfo transitionInfo3 : list) {
            if (!transitionInfo3.hasSharedElementTransition() || operation8 == null || operation9 == null) {
                map2 = map3;
                view4 = view6;
                fragmentTransitionImpl = fragmentTransitionImpl4;
                view7 = view7;
            } else {
                Object objWrapTransitionInSet = fragmentTransitionImpl4.wrapTransitionInSet(fragmentTransitionImpl4.cloneTransition(transitionInfo3.getSharedElementTransition()));
                Fragment.AnimationInfo animationInfo = operation2.getFragment().mAnimationInfo;
                if (animationInfo == null || (arrayList3 = animationInfo.mSharedElementSourceNames) == null) {
                    arrayList3 = new ArrayList<>();
                }
                Fragment.AnimationInfo animationInfo2 = operation.getFragment().mAnimationInfo;
                if (animationInfo2 == null || (arrayList4 = animationInfo2.mSharedElementSourceNames) == null) {
                    arrayList4 = new ArrayList<>();
                }
                Fragment.AnimationInfo animationInfo3 = operation.getFragment().mAnimationInfo;
                if (animationInfo3 == null || (arrayList5 = animationInfo3.mSharedElementTargetNames) == null) {
                    arrayList5 = new ArrayList<>();
                }
                HashMap map4 = map3;
                View view8 = view7;
                int i2 = 0;
                while (i2 < arrayList5.size()) {
                    int iIndexOf = arrayList3.indexOf(arrayList5.get(i2));
                    ArrayList<String> arrayList9 = arrayList5;
                    if (iIndexOf != -1) {
                        arrayList3.set(iIndexOf, arrayList4.get(i2));
                    }
                    i2++;
                    arrayList5 = arrayList9;
                }
                Fragment.AnimationInfo animationInfo4 = operation2.getFragment().mAnimationInfo;
                if (animationInfo4 == null || (arrayList6 = animationInfo4.mSharedElementTargetNames) == null) {
                    arrayList6 = new ArrayList<>();
                }
                if (z) {
                    enterTransitionCallback = operation.getFragment().getEnterTransitionCallback();
                    exitTransitionCallback = operation2.getFragment().getExitTransitionCallback();
                } else {
                    enterTransitionCallback = operation.getFragment().getExitTransitionCallback();
                    exitTransitionCallback = operation2.getFragment().getEnterTransitionCallback();
                }
                int size = arrayList3.size();
                View view9 = view6;
                int i3 = 0;
                while (i3 < size) {
                    arrayMap2.put(arrayList3.get(i3), arrayList6.get(i3));
                    i3++;
                    size = size;
                    rect = rect;
                }
                Rect rect2 = rect;
                ArrayMap<String, View> arrayMap3 = new ArrayMap<>();
                defaultSpecialEffectsController2.findNamedViews(arrayMap3, operation.getFragment().mView);
                arrayMap3.retainAll(arrayList3);
                if (enterTransitionCallback != null) {
                    enterTransitionCallback.onMapSharedElements(arrayList3, arrayMap3);
                    int size2 = arrayList3.size() - 1;
                    while (size2 >= 0) {
                        String str = arrayList3.get(size2);
                        View view10 = arrayMap3.get(str);
                        if (view10 == null) {
                            arrayMap2.remove(str);
                            fragmentTransitionImpl3 = fragmentTransitionImpl4;
                        } else {
                            fragmentTransitionImpl3 = fragmentTransitionImpl4;
                            if (!str.equals(ViewCompat.getTransitionName(view10))) {
                                arrayMap2.put(ViewCompat.getTransitionName(view10), (String) arrayMap2.remove(str));
                            }
                        }
                        size2--;
                        fragmentTransitionImpl4 = fragmentTransitionImpl3;
                    }
                    fragmentTransitionImpl2 = fragmentTransitionImpl4;
                } else {
                    fragmentTransitionImpl2 = fragmentTransitionImpl4;
                    arrayMap2.retainAll(arrayMap3.keySet());
                }
                final ArrayMap<String, View> arrayMap4 = new ArrayMap<>();
                defaultSpecialEffectsController2.findNamedViews(arrayMap4, operation2.getFragment().mView);
                arrayMap4.retainAll(arrayList6);
                arrayMap4.retainAll(arrayMap2.values());
                if (exitTransitionCallback != null) {
                    exitTransitionCallback.onMapSharedElements(arrayList6, arrayMap4);
                    for (int size3 = arrayList6.size() - 1; size3 >= 0; size3--) {
                        String str2 = arrayList6.get(size3);
                        View view11 = arrayMap4.get(str2);
                        if (view11 == null) {
                            String strFindKeyForValue2 = FragmentTransition.findKeyForValue(arrayMap2, str2);
                            if (strFindKeyForValue2 != null) {
                                arrayMap2.remove(strFindKeyForValue2);
                            }
                        } else if (!str2.equals(ViewCompat.getTransitionName(view11)) && (strFindKeyForValue = FragmentTransition.findKeyForValue(arrayMap2, str2)) != null) {
                            arrayMap2.put(strFindKeyForValue, ViewCompat.getTransitionName(view11));
                        }
                    }
                } else {
                    FragmentTransition.retainValues(arrayMap2, arrayMap4);
                }
                defaultSpecialEffectsController2.retainMatchingViews(arrayMap3, arrayMap2.keySet());
                defaultSpecialEffectsController2.retainMatchingViews(arrayMap4, arrayMap2.values());
                if (arrayMap2.isEmpty()) {
                    arrayList7.clear();
                    arrayList8.clear();
                    operation8 = operation;
                    operation9 = operation2;
                    obj3 = null;
                    view7 = view8;
                    fragmentTransitionImpl = fragmentTransitionImpl2;
                    view4 = view9;
                    rect = rect2;
                    map2 = map4;
                } else {
                    FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z, arrayMap3, true);
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                        @Override // java.lang.Runnable
                        public void run() {
                            FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z, arrayMap4, false);
                        }
                    });
                    arrayList7.addAll(arrayMap3.values());
                    if (arrayList3.isEmpty()) {
                        fragmentTransitionImpl = fragmentTransitionImpl2;
                        i = 0;
                    } else {
                        i = 0;
                        View view12 = arrayMap3.get(arrayList3.get(0));
                        fragmentTransitionImpl = fragmentTransitionImpl2;
                        fragmentTransitionImpl.setEpicenter(objWrapTransitionInSet, view12);
                        view8 = view12;
                    }
                    arrayList8.addAll(arrayMap4.values());
                    if (arrayList6.isEmpty() || (view5 = arrayMap4.get(arrayList6.get(i))) == null) {
                        rect = rect2;
                    } else {
                        rect = rect2;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                            @Override // java.lang.Runnable
                            public void run() {
                                fragmentTransitionImpl.getBoundsOnScreen(view5, rect);
                            }
                        });
                        z2 = true;
                    }
                    view4 = view9;
                    fragmentTransitionImpl.setSharedElementTargets(objWrapTransitionInSet, view4, arrayList7);
                    fragmentTransitionImpl.scheduleRemoveTargets(objWrapTransitionInSet, null, null, null, null, objWrapTransitionInSet, arrayList8);
                    Boolean bool = Boolean.TRUE;
                    map2 = map4;
                    map2.put(operation, bool);
                    map2.put(operation2, bool);
                    defaultSpecialEffectsController2 = this;
                    operation8 = operation;
                    operation9 = operation2;
                    view7 = view8;
                    obj3 = objWrapTransitionInSet;
                }
            }
            arrayMap2 = arrayMap2;
            fragmentTransitionImpl4 = fragmentTransitionImpl;
            view6 = view4;
            map3 = map2;
            arrayList8 = arrayList8;
        }
        View view13 = view7;
        ArrayList<View> arrayList10 = arrayList8;
        HashMap map5 = map3;
        View view14 = view6;
        FragmentTransitionImpl fragmentTransitionImpl5 = fragmentTransitionImpl4;
        ArrayMap arrayMap5 = arrayMap2;
        ArrayList arrayList11 = new ArrayList();
        Iterator<TransitionInfo> it2 = list.iterator();
        Object objMergeTransitionsTogether = null;
        Object objMergeTransitionsTogether2 = null;
        while (it2.hasNext()) {
            TransitionInfo next = it2.next();
            if (next.isVisibilityUnchanged()) {
                map5.put(next.getOperation(), Boolean.FALSE);
                next.completeSpecialEffect();
            } else {
                Object objCloneTransition = fragmentTransitionImpl5.cloneTransition(next.getTransition());
                SpecialEffectsController.Operation operation10 = next.getOperation();
                boolean z3 = obj3 != null && (operation10 == operation8 || operation10 == operation9);
                if (objCloneTransition == null) {
                    if (!z3) {
                        map5.put(operation10, Boolean.FALSE);
                        next.completeSpecialEffect();
                    }
                    it = it2;
                    arrayMap = arrayMap5;
                    defaultSpecialEffectsController = defaultSpecialEffectsController2;
                    operation5 = operation9;
                    operation6 = operation8;
                    obj = obj3;
                    view3 = view13;
                    arrayList = arrayList10;
                    view2 = view14;
                    map = map5;
                } else {
                    HashMap map6 = map5;
                    ArrayList<View> arrayList12 = new ArrayList<>();
                    it = it2;
                    defaultSpecialEffectsController2.captureTransitioningViews(arrayList12, operation10.getFragment().mView);
                    if (!z3) {
                        arrayList = arrayList10;
                    } else if (operation10 == operation8) {
                        arrayList12.removeAll(arrayList7);
                        arrayList = arrayList10;
                    } else {
                        arrayList = arrayList10;
                        arrayList12.removeAll(arrayList);
                    }
                    if (arrayList12.isEmpty()) {
                        fragmentTransitionImpl5.addTarget(objCloneTransition, view14);
                        view2 = view14;
                        arrayMap = arrayMap5;
                        defaultSpecialEffectsController = defaultSpecialEffectsController2;
                        operation5 = operation9;
                        operation6 = operation8;
                        obj = obj3;
                        obj2 = objMergeTransitionsTogether2;
                        arrayList2 = arrayList12;
                        view = view13;
                        map = map6;
                        operation7 = operation10;
                    } else {
                        fragmentTransitionImpl5.addTargets(objCloneTransition, arrayList12);
                        defaultSpecialEffectsController = defaultSpecialEffectsController2;
                        operation5 = operation9;
                        operation6 = operation8;
                        view = view13;
                        obj = obj3;
                        obj2 = objMergeTransitionsTogether2;
                        view2 = view14;
                        arrayMap = arrayMap5;
                        arrayList2 = arrayList12;
                        map = map6;
                        fragmentTransitionImpl5.scheduleRemoveTargets(objCloneTransition, objCloneTransition, arrayList12, null, null, null, null);
                        if (operation10.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation7 = operation10;
                            list2.remove(operation7);
                            ArrayList<View> arrayList13 = new ArrayList<>(arrayList2);
                            arrayList13.remove(operation7.getFragment().mView);
                            objCloneTransition = objCloneTransition;
                            fragmentTransitionImpl5.scheduleHideFragmentView(objCloneTransition, operation7.getFragment().mView, arrayList13);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    FragmentTransition.setViewVisibility(arrayList2, 4);
                                }
                            });
                        } else {
                            operation7 = operation10;
                            objCloneTransition = objCloneTransition;
                        }
                    }
                    if (operation7.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList11.addAll(arrayList2);
                        if (z2) {
                            fragmentTransitionImpl5.setEpicenter(objCloneTransition, rect);
                        }
                        view3 = view;
                    } else {
                        view3 = view;
                        fragmentTransitionImpl5.setEpicenter(objCloneTransition, view3);
                    }
                    map.put(operation7, Boolean.TRUE);
                    if (next.isOverlapAllowed()) {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl5.mergeTransitionsTogether(obj2, objCloneTransition, null);
                    } else {
                        objMergeTransitionsTogether2 = obj2;
                        objMergeTransitionsTogether = fragmentTransitionImpl5.mergeTransitionsTogether(objMergeTransitionsTogether, objCloneTransition, null);
                    }
                }
                map5 = map;
                view13 = view3;
                arrayMap5 = arrayMap;
                view14 = view2;
                defaultSpecialEffectsController2 = defaultSpecialEffectsController;
                operation9 = operation5;
                operation8 = operation6;
                obj3 = obj;
                arrayList10 = arrayList;
                it2 = it;
            }
        }
        ArrayMap arrayMap6 = arrayMap5;
        SpecialEffectsController.Operation operation11 = operation9;
        SpecialEffectsController.Operation operation12 = operation8;
        HashMap map7 = map5;
        ArrayList<View> arrayList14 = arrayList10;
        Object obj4 = obj3;
        Object objMergeTransitionsInSequence = fragmentTransitionImpl5.mergeTransitionsInSequence(objMergeTransitionsTogether2, objMergeTransitionsTogether, obj4);
        for (final TransitionInfo transitionInfo4 : list) {
            if (!transitionInfo4.isVisibilityUnchanged()) {
                Object transition = transitionInfo4.getTransition();
                SpecialEffectsController.Operation operation13 = transitionInfo4.getOperation();
                if (obj4 != null) {
                    operation4 = operation12;
                    operation3 = operation11;
                    boolean z4 = operation13 == operation4 || operation13 == operation3;
                    if (transition == null || z4) {
                        if (ViewCompat.isLaidOut(getContainer())) {
                            fragmentTransitionImpl5.setListenerForTransitionEnd(transitionInfo4.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo4.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                                @Override // java.lang.Runnable
                                public void run() {
                                    transitionInfo4.completeSpecialEffect();
                                }
                            });
                        } else {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("SpecialEffectsController: Container ");
                                sbM2.append(getContainer());
                                sbM2.append(" has not been laid out. Completing operation ");
                                sbM2.append(operation13);
                                Log.v("FragmentManager", sbM2.toString());
                            }
                            transitionInfo4.completeSpecialEffect();
                        }
                    }
                    operation12 = operation4;
                    operation11 = operation3;
                } else {
                    operation3 = operation11;
                    operation4 = operation12;
                }
                if (transition == null) {
                    if (ViewCompat.isLaidOut(getContainer())) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("SpecialEffectsController: Container ");
                            sbM3.append(getContainer());
                            sbM3.append(" has not been laid out. Completing operation ");
                            sbM3.append(operation13);
                            Log.v("FragmentManager", sbM3.toString());
                        }
                        transitionInfo4.completeSpecialEffect();
                    } else {
                        fragmentTransitionImpl5.setListenerForTransitionEnd(transitionInfo4.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo4.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                            @Override // java.lang.Runnable
                            public void run() {
                                transitionInfo4.completeSpecialEffect();
                            }
                        });
                    }
                } else if (ViewCompat.isLaidOut(getContainer())) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m("SpecialEffectsController: Container ");
                        sbM4.append(getContainer());
                        sbM4.append(" has not been laid out. Completing operation ");
                        sbM4.append(operation13);
                        Log.v("FragmentManager", sbM4.toString());
                    }
                    transitionInfo4.completeSpecialEffect();
                } else {
                    fragmentTransitionImpl5.setListenerForTransitionEnd(transitionInfo4.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo4.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                        @Override // java.lang.Runnable
                        public void run() {
                            transitionInfo4.completeSpecialEffect();
                        }
                    });
                }
                operation12 = operation4;
                operation11 = operation3;
            }
        }
        if (!ViewCompat.isLaidOut(getContainer())) {
            return map7;
        }
        FragmentTransition.setViewVisibility(arrayList11, 4);
        ArrayList<String> arrayListPrepareSetNameOverridesReordered = fragmentTransitionImpl5.prepareSetNameOverridesReordered(arrayList14);
        fragmentTransitionImpl5.beginDelayedTransition(getContainer(), objMergeTransitionsInSequence);
        fragmentTransitionImpl5.setNameOverridesReordered(getContainer(), arrayList7, arrayList14, arrayListPrepareSetNameOverridesReordered, arrayMap6);
        FragmentTransition.setViewVisibility(arrayList11, 0);
        fragmentTransitionImpl5.swapSharedElementTargets(obj4, arrayList7, arrayList14);
        return map7;
    }

    public final void captureTransitioningViews(ArrayList<View> arrayList, View view) {
        if (!(view instanceof ViewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(view);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(viewGroup);
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                captureTransitioningViews(arrayList, childAt);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0088  */
    @Override // androidx.fragment.app.SpecialEffectsController
    public final void executeOperations(@NonNull List<SpecialEffectsController.Operation> list, boolean z) {
        ArrayList<SpecialEffectsController.Operation> arrayList = (ArrayList) list;
        SpecialEffectsController.Operation operation = null;
        SpecialEffectsController.Operation operation2 = null;
        for (SpecialEffectsController.Operation operation3 : arrayList) {
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(operation3.getFragment().mView);
            int i = AnonymousClass10.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[operation3.getFinalState().ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                if (stateFrom == SpecialEffectsController.Operation.State.VISIBLE && operation == null) {
                    operation = operation3;
                }
            } else if (i == 4 && stateFrom != SpecialEffectsController.Operation.State.VISIBLE) {
                operation2 = operation3;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        final ArrayList<SpecialEffectsController.Operation> arrayList4 = new ArrayList(list);
        for (final SpecialEffectsController.Operation operation4 : arrayList) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal);
            arrayList2.add(new AnimationInfo(operation4, cancellationSignal, z));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal2);
            boolean z2 = false;
            if (z) {
                if (operation4 == operation) {
                    z2 = true;
                }
            } else if (operation4 == operation2) {
                z2 = true;
            }
            arrayList3.add(new TransitionInfo(operation4, cancellationSignal2, z, z2));
            operation4.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                @Override // java.lang.Runnable
                public void run() {
                    if (arrayList4.contains(operation4)) {
                        arrayList4.remove(operation4);
                        DefaultSpecialEffectsController defaultSpecialEffectsController = DefaultSpecialEffectsController.this;
                        SpecialEffectsController.Operation operation5 = operation4;
                        Objects.requireNonNull(defaultSpecialEffectsController);
                        operation5.getFinalState().applyState(operation5.getFragment().mView);
                    }
                }
            });
        }
        Map<SpecialEffectsController.Operation, Boolean> mapStartTransitions = startTransitions(arrayList3, arrayList4, z, operation, operation2);
        startAnimations(arrayList2, arrayList4, mapStartTransitions.containsValue(Boolean.TRUE), mapStartTransitions);
        for (SpecialEffectsController.Operation operation5 : arrayList4) {
            operation5.getFinalState().applyState(operation5.getFragment().mView);
        }
        arrayList4.clear();
    }

    public final void findNamedViews(Map<String, View> map, @NonNull View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(map, childAt);
                }
            }
        }
    }

    public final void retainMatchingViews(@NonNull ArrayMap<String, View> arrayMap, @NonNull Collection<String> collection) {
        Iterator<Map.Entry<String, View>> it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName(it.next().getValue()))) {
                it.remove();
            }
        }
    }

    public static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;

        @Nullable
        private final Object mSharedElementTransition;

        @Nullable
        private final Object mTransition;

        public TransitionInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z, boolean z2) {
            super(operation, cancellationSignal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                this.mTransition = z ? operation.getFragment().getReenterTransition() : operation.getFragment().getEnterTransition();
                this.mOverlapAllowed = z ? operation.getFragment().getAllowReturnTransitionOverlap() : operation.getFragment().getAllowEnterTransitionOverlap();
            } else {
                this.mTransition = z ? operation.getFragment().getReturnTransition() : operation.getFragment().getExitTransition();
                this.mOverlapAllowed = true;
            }
            if (!z2) {
                this.mSharedElementTransition = null;
            } else if (z) {
                this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
            } else {
                this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
            }
        }

        @Nullable
        public final FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl handlingImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.mSharedElementTransition);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl != null ? handlingImpl : handlingImpl2;
            }
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Mixing framework transitions and AndroidX transitions is not allowed. Fragment ");
            sbM.append(getOperation().getFragment());
            sbM.append(" returned Transition ");
            sbM.append(this.mTransition);
            sbM.append(" which uses a different Transition  type than its shared element transition ");
            sbM.append(this.mSharedElementTransition);
            throw new IllegalArgumentException(sbM.toString());
        }

        @Nullable
        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        @Nullable
        public final Object getTransition() {
            return this.mTransition;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public final boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        @Nullable
        private FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            FragmentTransitionImpl fragmentTransitionImpl2 = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                return fragmentTransitionImpl2;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
