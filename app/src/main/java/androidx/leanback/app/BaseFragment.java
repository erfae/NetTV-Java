package androidx.leanback.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class BaseFragment extends BrandedFragment {
    public Object mEntranceTransition;
    public final StateMachine.State STATE_START = new StateMachine.State("START", true, false);
    public final StateMachine.State STATE_ENTRANCE_INIT = new StateMachine.State("ENTRANCE_INIT");
    public final StateMachine.State STATE_ENTRANCE_ON_PREPARED = new StateMachine.State() { // from class: androidx.leanback.app.BaseFragment.1
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseFragment.this.mProgressBarManager.show();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW = new StateMachine.State() { // from class: androidx.leanback.app.BaseFragment.2
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseFragment.this.onEntranceTransitionPrepare();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_PERFORM = new StateMachine.State() { // from class: androidx.leanback.app.BaseFragment.3
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseFragment.this.mProgressBarManager.hide();
            final BaseFragment baseFragment = BaseFragment.this;
            final View view = baseFragment.getView();
            if (view == null) {
                return;
            }
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: androidx.leanback.app.BaseFragment.6
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (FragmentUtil.getContext(BaseFragment.this) == null || BaseFragment.this.getView() == null) {
                        return true;
                    }
                    final BaseFragment baseFragment2 = BaseFragment.this;
                    Object objCreateEntranceTransition = baseFragment2.createEntranceTransition();
                    baseFragment2.mEntranceTransition = objCreateEntranceTransition;
                    if (objCreateEntranceTransition != null) {
                        TransitionHelper.addTransitionListener(objCreateEntranceTransition, new TransitionListener() { // from class: androidx.leanback.app.BaseFragment.7
                            @Override // androidx.leanback.transition.TransitionListener
                            public void onTransitionEnd(Object obj) {
                                BaseFragment baseFragment3 = BaseFragment.this;
                                baseFragment3.mEntranceTransition = null;
                                baseFragment3.mStateMachine.fireEvent(baseFragment3.EVT_ENTRANCE_END);
                            }
                        });
                    }
                    BaseFragment.this.onEntranceTransitionStart();
                    BaseFragment baseFragment3 = BaseFragment.this;
                    Object obj = baseFragment3.mEntranceTransition;
                    if (obj != null) {
                        baseFragment3.runEntranceTransition(obj);
                        return false;
                    }
                    baseFragment3.mStateMachine.fireEvent(baseFragment3.EVT_ENTRANCE_END);
                    return false;
                }
            });
            view.invalidate();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_ON_ENDED = new StateMachine.State() { // from class: androidx.leanback.app.BaseFragment.4
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseFragment.this.onEntranceTransitionEnd();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_COMPLETE = new StateMachine.State("ENTRANCE_COMPLETE", true, false);
    public final StateMachine.Event EVT_ON_CREATE = new StateMachine.Event("onCreate");
    public final StateMachine.Event EVT_ON_CREATEVIEW = new StateMachine.Event("onCreateView");
    public final StateMachine.Event EVT_PREPARE_ENTRANCE = new StateMachine.Event("prepareEntranceTransition");
    public final StateMachine.Event EVT_START_ENTRANCE = new StateMachine.Event("startEntranceTransition");
    public final StateMachine.Event EVT_ENTRANCE_END = new StateMachine.Event("onEntranceTransitionEnd");
    public final StateMachine.Condition COND_TRANSITION_NOT_SUPPORTED = new StateMachine.Condition() { // from class: androidx.leanback.app.BaseFragment.5
        @Override // androidx.leanback.util.StateMachine.Condition
        public boolean canProceed() {
            return !TransitionHelper.systemSupportsEntranceTransitions();
        }
    };
    public final StateMachine mStateMachine = new StateMachine();
    public final ProgressBarManager mProgressBarManager = new ProgressBarManager();

    @SuppressLint({"ValidFragment"})
    public BaseFragment() {
    }

    public Object createEntranceTransition() {
        return null;
    }

    public void createStateMachineStates() {
        this.mStateMachine.addState(this.STATE_START);
        this.mStateMachine.addState(this.STATE_ENTRANCE_INIT);
        this.mStateMachine.addState(this.STATE_ENTRANCE_ON_PREPARED);
        this.mStateMachine.addState(this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW);
        this.mStateMachine.addState(this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addState(this.STATE_ENTRANCE_ON_ENDED);
        this.mStateMachine.addState(this.STATE_ENTRANCE_COMPLETE);
    }

    public void createStateMachineTransitions() {
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ENTRANCE_INIT, this.EVT_ON_CREATE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_ENTRANCE_COMPLETE, this.COND_TRANSITION_NOT_SUPPORTED);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_ENTRANCE_COMPLETE, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_ENTRANCE_ON_PREPARED, this.EVT_PREPARE_ENTRANCE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_PERFORM, this.EVT_START_ENTRANCE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW, this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_PERFORM, this.STATE_ENTRANCE_ON_ENDED, this.EVT_ENTRANCE_END);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_ENDED, this.STATE_ENTRANCE_COMPLETE);
    }

    public final ProgressBarManager getProgressBarManager() {
        return this.mProgressBarManager;
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        createStateMachineStates();
        createStateMachineTransitions();
        this.mStateMachine.start();
        super.onCreate(bundle);
        this.mStateMachine.fireEvent(this.EVT_ON_CREATE);
    }

    public void onEntranceTransitionEnd() {
    }

    public void onEntranceTransitionPrepare() {
    }

    public void onEntranceTransitionStart() {
    }

    @Override // androidx.leanback.app.BrandedFragment, android.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mStateMachine.fireEvent(this.EVT_ON_CREATEVIEW);
    }

    public void prepareEntranceTransition() {
        this.mStateMachine.fireEvent(this.EVT_PREPARE_ENTRANCE);
    }

    public void runEntranceTransition(Object obj) {
    }

    public void startEntranceTransition() {
        this.mStateMachine.fireEvent(this.EVT_START_ENTRANCE);
    }
}
