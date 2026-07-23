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
public class BaseSupportFragment extends BrandedSupportFragment {
    public Object mEntranceTransition;
    public final StateMachine.State STATE_START = new StateMachine.State("START", true, false);
    public final StateMachine.State STATE_ENTRANCE_INIT = new StateMachine.State("ENTRANCE_INIT");
    public final StateMachine.State STATE_ENTRANCE_ON_PREPARED = new StateMachine.State() { // from class: androidx.leanback.app.BaseSupportFragment.1
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseSupportFragment.this.mProgressBarManager.show();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW = new StateMachine.State() { // from class: androidx.leanback.app.BaseSupportFragment.2
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseSupportFragment.this.onEntranceTransitionPrepare();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_PERFORM = new StateMachine.State() { // from class: androidx.leanback.app.BaseSupportFragment.3
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseSupportFragment.this.mProgressBarManager.hide();
            final BaseSupportFragment baseSupportFragment = BaseSupportFragment.this;
            final View view = baseSupportFragment.getView();
            if (view == null) {
                return;
            }
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: androidx.leanback.app.BaseSupportFragment.6
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (BaseSupportFragment.this.getContext() == null || BaseSupportFragment.this.getView() == null) {
                        return true;
                    }
                    final BaseSupportFragment baseSupportFragment2 = BaseSupportFragment.this;
                    Object objCreateEntranceTransition = baseSupportFragment2.createEntranceTransition();
                    baseSupportFragment2.mEntranceTransition = objCreateEntranceTransition;
                    if (objCreateEntranceTransition != null) {
                        TransitionHelper.addTransitionListener(objCreateEntranceTransition, new TransitionListener() { // from class: androidx.leanback.app.BaseSupportFragment.7
                            @Override // androidx.leanback.transition.TransitionListener
                            public void onTransitionEnd(Object obj) {
                                BaseSupportFragment baseSupportFragment3 = BaseSupportFragment.this;
                                baseSupportFragment3.mEntranceTransition = null;
                                baseSupportFragment3.mStateMachine.fireEvent(baseSupportFragment3.EVT_ENTRANCE_END);
                            }
                        });
                    }
                    BaseSupportFragment.this.onEntranceTransitionStart();
                    BaseSupportFragment baseSupportFragment3 = BaseSupportFragment.this;
                    Object obj = baseSupportFragment3.mEntranceTransition;
                    if (obj != null) {
                        baseSupportFragment3.runEntranceTransition(obj);
                        return false;
                    }
                    baseSupportFragment3.mStateMachine.fireEvent(baseSupportFragment3.EVT_ENTRANCE_END);
                    return false;
                }
            });
            view.invalidate();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_ON_ENDED = new StateMachine.State() { // from class: androidx.leanback.app.BaseSupportFragment.4
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            BaseSupportFragment.this.onEntranceTransitionEnd();
        }
    };
    public final StateMachine.State STATE_ENTRANCE_COMPLETE = new StateMachine.State("ENTRANCE_COMPLETE", true, false);
    public final StateMachine.Event EVT_ON_CREATE = new StateMachine.Event("onCreate");
    public final StateMachine.Event EVT_ON_CREATEVIEW = new StateMachine.Event("onCreateView");
    public final StateMachine.Event EVT_PREPARE_ENTRANCE = new StateMachine.Event("prepareEntranceTransition");
    public final StateMachine.Event EVT_START_ENTRANCE = new StateMachine.Event("startEntranceTransition");
    public final StateMachine.Event EVT_ENTRANCE_END = new StateMachine.Event("onEntranceTransitionEnd");
    public final StateMachine.Condition COND_TRANSITION_NOT_SUPPORTED = new StateMachine.Condition() { // from class: androidx.leanback.app.BaseSupportFragment.5
        @Override // androidx.leanback.util.StateMachine.Condition
        public boolean canProceed() {
            return !TransitionHelper.systemSupportsEntranceTransitions();
        }
    };
    public final StateMachine mStateMachine = new StateMachine();
    public final ProgressBarManager mProgressBarManager = new ProgressBarManager();

    @SuppressLint({"ValidFragment"})
    public BaseSupportFragment() {
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

    @Override // androidx.fragment.app.Fragment
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

    @Override // androidx.leanback.app.BrandedSupportFragment, androidx.fragment.app.Fragment
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
