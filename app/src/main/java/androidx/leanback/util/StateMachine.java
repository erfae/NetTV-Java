package androidx.leanback.util;

import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class StateMachine {
    public static final int STATUS_INVOKED = 1;
    public static final int STATUS_ZERO = 0;
    public final ArrayList<State> mStates = new ArrayList<>();
    public final ArrayList<State> mFinishedStates = new ArrayList<>();
    public final ArrayList<State> mUnfinishedStates = new ArrayList<>();

    public static class Condition {
        public final String mName;

        public Condition(String str) {
            this.mName = str;
        }

        public boolean canProceed() {
            return true;
        }
    }

    public static class Event {
        public final String mName;

        public Event(String str) {
            this.mName = str;
        }
    }

    public static class State {
        public final boolean mBranchEnd;
        public final boolean mBranchStart;
        public ArrayList<Transition> mIncomings;
        public int mInvokedOutTransitions;
        public final String mName;
        public ArrayList<Transition> mOutgoings;
        public int mStatus;

        public State(String str) {
            this(str, false, true);
        }

        public final void addIncoming(Transition transition) {
            if (this.mIncomings == null) {
                this.mIncomings = new ArrayList<>();
            }
            this.mIncomings.add(transition);
        }

        public final void addOutgoing(Transition transition) {
            if (this.mOutgoings == null) {
                this.mOutgoings = new ArrayList<>();
            }
            this.mOutgoings.add(transition);
        }

        public final int getStatus() {
            return this.mStatus;
        }

        public void run() {
        }

        public String toString() {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("[");
            sbM.append(this.mName);
            sbM.append(" ");
            return Insets$$ExternalSyntheticOutline0.m(sbM, this.mStatus, "]");
        }

        public State(String str, boolean z, boolean z2) {
            this.mStatus = 0;
            this.mInvokedOutTransitions = 0;
            this.mName = str;
            this.mBranchStart = z;
            this.mBranchEnd = z2;
        }
    }

    public void addState(State state) {
        if (this.mStates.contains(state)) {
            return;
        }
        this.mStates.add(state);
    }

    public void addTransition(State state, State state2, Event event) {
        Transition transition = new Transition(state, state2, event);
        state2.addIncoming(transition);
        state.addOutgoing(transition);
    }

    public void fireEvent(Event event) {
        for (int i = 0; i < this.mFinishedStates.size(); i++) {
            State state = this.mFinishedStates.get(i);
            ArrayList<Transition> arrayList = state.mOutgoings;
            if (arrayList != null && (state.mBranchStart || state.mInvokedOutTransitions <= 0)) {
                for (Transition transition : arrayList) {
                    if (transition.mState != 1 && transition.mEvent == event) {
                        transition.mState = 1;
                        state.mInvokedOutTransitions++;
                        if (!state.mBranchStart) {
                            break;
                        }
                    }
                }
            }
        }
        runUnfinishedStates();
    }

    public void reset() {
        this.mUnfinishedStates.clear();
        this.mFinishedStates.clear();
        for (State state : this.mStates) {
            state.mStatus = 0;
            state.mInvokedOutTransitions = 0;
            ArrayList<Transition> arrayList = state.mOutgoings;
            if (arrayList != null) {
                Iterator<Transition> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().mState = 0;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0083  */
    public final void runUnfinishedStates() {
        boolean z;
        boolean z2;
        boolean z3;
        Condition condition;
        do {
            z = false;
            for (int size = this.mUnfinishedStates.size() - 1; size >= 0; size--) {
                State state = this.mUnfinishedStates.get(size);
                if (state.mStatus == 1) {
                    z2 = false;
                } else {
                    ArrayList<Transition> arrayList = state.mIncomings;
                    if (arrayList == null) {
                        z3 = true;
                    } else if (state.mBranchEnd) {
                        Iterator<Transition> it = arrayList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z3 = true;
                            } else if (it.next().mState != 1) {
                                z3 = false;
                            }
                        }
                    } else {
                        Iterator<Transition> it2 = arrayList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z3 = false;
                            } else if (it2.next().mState == 1) {
                                z3 = true;
                            }
                        }
                    }
                    if (z3) {
                        state.mStatus = 1;
                        state.run();
                        ArrayList<Transition> arrayList2 = state.mOutgoings;
                        if (arrayList2 != null) {
                            for (Transition transition : arrayList2) {
                                if (transition.mEvent == null && ((condition = transition.mCondition) == null || condition.canProceed())) {
                                    state.mInvokedOutTransitions++;
                                    transition.mState = 1;
                                    if (!state.mBranchStart) {
                                        break;
                                    }
                                }
                            }
                        }
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                }
                if (z2) {
                    this.mUnfinishedStates.remove(size);
                    this.mFinishedStates.add(state);
                    z = true;
                }
            }
        } while (z);
    }

    public void start() {
        this.mUnfinishedStates.addAll(this.mStates);
        runUnfinishedStates();
    }

    public void addTransition(State state, State state2, Condition condition) {
        Transition transition = new Transition(state, state2, condition);
        state2.addIncoming(transition);
        state.addOutgoing(transition);
    }

    public static class Transition {
        public final Condition mCondition;
        public final Event mEvent;
        public final State mFromState;
        public int mState;
        public final State mToState;

        public Transition(State state, State state2, Event event) {
            this.mState = 0;
            if (event == null) {
                throw new IllegalArgumentException();
            }
            this.mFromState = state;
            this.mToState = state2;
            this.mEvent = event;
            this.mCondition = null;
        }

        public String toString() {
            String str;
            Event event = this.mEvent;
            if (event != null) {
                str = event.mName;
            } else {
                Condition condition = this.mCondition;
                str = condition != null ? condition.mName : "auto";
            }
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("[");
            sbM.append(this.mFromState.mName);
            sbM.append(" -> ");
            return Insets$$ExternalSyntheticOutline0.m(sbM, this.mToState.mName, " <", str, ">]");
        }

        public Transition(State state, State state2) {
            this.mState = 0;
            this.mFromState = state;
            this.mToState = state2;
            this.mEvent = null;
            this.mCondition = null;
        }

        public Transition(State state, State state2, Condition condition) {
            this.mState = 0;
            if (condition != null) {
                this.mFromState = state;
                this.mToState = state2;
                this.mEvent = null;
                this.mCondition = condition;
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    public void addTransition(State state, State state2) {
        Transition transition = new Transition(state, state2);
        state2.addIncoming(transition);
        state.addOutgoing(transition);
    }
}
