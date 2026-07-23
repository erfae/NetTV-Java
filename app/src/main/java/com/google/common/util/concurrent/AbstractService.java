package com.google.common.util.concurrent;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class AbstractService implements Service {
    private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_RUNNING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_STARTING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_NEW_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_RUNNING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STARTING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STOPPING_EVENT;
    private static final ListenerCallQueue.Event<Service.Listener> STARTING_EVENT = new ListenerCallQueue.Event<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.1
        public String toString() {
            return "starting()";
        }

        @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
        public void call(Service.Listener listener) {
            listener.starting();
        }
    };
    private static final ListenerCallQueue.Event<Service.Listener> RUNNING_EVENT = new ListenerCallQueue.Event<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.2
        public String toString() {
            return "running()";
        }

        @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
        public void call(Service.Listener listener) {
            listener.running();
        }
    };
    private final Monitor monitor = new Monitor();
    private final Monitor.Guard isStartable = new IsStartableGuard();
    private final Monitor.Guard isStoppable = new IsStoppableGuard();
    private final Monitor.Guard hasReachedRunning = new HasReachedRunningGuard();
    private final Monitor.Guard isStopped = new IsStoppedGuard();
    private final ListenerCallQueue<Service.Listener> listeners = new ListenerCallQueue<>();
    private volatile StateSnapshot snapshot = new StateSnapshot(Service.State.NEW);

    /* JADX INFO: renamed from: com.google.common.util.concurrent.AbstractService$6, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$common$util$concurrent$Service$State;

        static {
            int[] iArr = new int[Service.State.values().length];
            $SwitchMap$com$google$common$util$concurrent$Service$State = iArr;
            try {
                iArr[Service.State.NEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[Service.State.STARTING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[Service.State.RUNNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[Service.State.STOPPING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[Service.State.TERMINATED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[Service.State.FAILED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public final class HasReachedRunningGuard extends Monitor.Guard {
        public HasReachedRunningGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0;
        }
    }

    public final class IsStartableGuard extends Monitor.Guard {
        public IsStartableGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state() == Service.State.NEW;
        }
    }

    public final class IsStoppableGuard extends Monitor.Guard {
        public IsStoppableGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0;
        }
    }

    public final class IsStoppedGuard extends Monitor.Guard {
        public IsStoppedGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.TERMINATED) >= 0;
        }
    }

    public static final class StateSnapshot {

        @CheckForNull
        public final Throwable failure;
        public final boolean shutdownWhenStartupFinishes;
        public final Service.State state;

        public StateSnapshot(Service.State state) {
            this(state, false, null);
        }

        public StateSnapshot(Service.State state, boolean z, @CheckForNull Throwable th) {
            Preconditions.checkArgument(!z || state == Service.State.STARTING, "shutdownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", state);
            Preconditions.checkArgument((th != null) == (state == Service.State.FAILED), "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", state, th);
            this.state = state;
            this.shutdownWhenStartupFinishes = z;
            this.failure = th;
        }
    }

    static {
        Service.State state = Service.State.STARTING;
        STOPPING_FROM_STARTING_EVENT = stoppingEvent(state);
        Service.State state2 = Service.State.RUNNING;
        STOPPING_FROM_RUNNING_EVENT = stoppingEvent(state2);
        TERMINATED_FROM_NEW_EVENT = terminatedEvent(Service.State.NEW);
        TERMINATED_FROM_STARTING_EVENT = terminatedEvent(state);
        TERMINATED_FROM_RUNNING_EVENT = terminatedEvent(state2);
        TERMINATED_FROM_STOPPING_EVENT = terminatedEvent(Service.State.STOPPING);
    }

    @GuardedBy("monitor")
    private void checkCurrentState(Service.State state) {
        Service.State state2 = state();
        if (state2 != state) {
            if (state2 == Service.State.FAILED) {
                String strValueOf = String.valueOf(this);
                String strValueOf2 = String.valueOf(state);
                StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + 56, "Expected the service ", strValueOf, " to be ", strValueOf2);
                sbM209m.append(", but the service has FAILED");
                throw new IllegalStateException(sbM209m.toString(), failureCause());
            }
            String strValueOf3 = String.valueOf(this);
            String strValueOf4 = String.valueOf(state);
            String strValueOf5 = String.valueOf(state2);
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf5.length() + strValueOf4.length() + strValueOf3.length() + 38, "Expected the service ", strValueOf3, " to be ", strValueOf4), ", but was ", strValueOf5));
        }
    }

    private void dispatchListenerEvents() {
        if (this.monitor.isOccupiedByCurrentThread()) {
            return;
        }
        this.listeners.dispatch();
    }

    private void enqueueFailedEvent(final Service.State state, final Throwable th) {
        this.listeners.enqueue(new ListenerCallQueue.Event<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.5
            public String toString() {
                String strValueOf = String.valueOf(state);
                String strValueOf2 = String.valueOf(th);
                StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + 27, "failed({from = ", strValueOf, ", cause = ", strValueOf2);
                sbM209m.append("})");
                return sbM209m.toString();
            }

            @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
            public void call(Service.Listener listener) {
                listener.failed(state, th);
            }
        });
    }

    private void enqueueRunningEvent() {
        this.listeners.enqueue(RUNNING_EVENT);
    }

    private void enqueueStartingEvent() {
        this.listeners.enqueue(STARTING_EVENT);
    }

    private void enqueueStoppingEvent(Service.State state) {
        if (state == Service.State.STARTING) {
            this.listeners.enqueue(STOPPING_FROM_STARTING_EVENT);
        } else {
            if (state != Service.State.RUNNING) {
                throw new AssertionError();
            }
            this.listeners.enqueue(STOPPING_FROM_RUNNING_EVENT);
        }
    }

    private void enqueueTerminatedEvent(Service.State state) {
        switch (AnonymousClass6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
            case 1:
                this.listeners.enqueue(TERMINATED_FROM_NEW_EVENT);
                return;
            case 2:
                this.listeners.enqueue(TERMINATED_FROM_STARTING_EVENT);
                return;
            case 3:
                this.listeners.enqueue(TERMINATED_FROM_RUNNING_EVENT);
                return;
            case 4:
                this.listeners.enqueue(TERMINATED_FROM_STOPPING_EVENT);
                return;
            case 5:
            case 6:
                throw new AssertionError();
            default:
                return;
        }
    }

    private static ListenerCallQueue.Event<Service.Listener> stoppingEvent(final Service.State state) {
        return new ListenerCallQueue.Event<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.4
            public String toString() {
                String strValueOf = String.valueOf(state);
                return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 19, "stopping({from = ", strValueOf, "})");
            }

            @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
            public void call(Service.Listener listener) {
                listener.stopping(state);
            }
        };
    }

    private static ListenerCallQueue.Event<Service.Listener> terminatedEvent(final Service.State state) {
        return new ListenerCallQueue.Event<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.3
            public String toString() {
                String strValueOf = String.valueOf(state);
                return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 21, "terminated({from = ", strValueOf, "})");
            }

            @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
            public void call(Service.Listener listener) {
                listener.terminated(state);
            }
        };
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.listeners.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            checkCurrentState(Service.State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            checkCurrentState(Service.State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    @ForOverride
    public abstract void doStart();

    @ForOverride
    public abstract void doStop();

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        StateSnapshot stateSnapshot = this.snapshot;
        Service.State state = stateSnapshot.state;
        Preconditions.checkState(state == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", state);
        Throwable th = stateSnapshot.failure;
        Objects.requireNonNull(th);
        return th;
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return state() == Service.State.RUNNING;
    }

    public final void notifyFailed(Throwable th) {
        Preconditions.checkNotNull(th);
        this.monitor.enter();
        try {
            Service.State state = state();
            int i = AnonymousClass6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3 || i == 4) {
                    this.snapshot = new StateSnapshot(Service.State.FAILED, false, th);
                    enqueueFailedEvent(state, th);
                } else if (i != 5) {
                }
                this.monitor.leave();
                dispatchListenerEvents();
                return;
            }
            String strValueOf = String.valueOf(state);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 22);
            sb.append("Failed while in state:");
            sb.append(strValueOf);
            throw new IllegalStateException(sb.toString(), th);
        } catch (Throwable th2) {
            this.monitor.leave();
            dispatchListenerEvents();
            throw th2;
        }
    }

    public final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state != Service.State.STARTING) {
                String strValueOf = String.valueOf(this.snapshot.state);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 43);
                sb.append("Cannot notifyStarted() when the service is ");
                sb.append(strValueOf);
                IllegalStateException illegalStateException = new IllegalStateException(sb.toString());
                notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            if (this.snapshot.shutdownWhenStartupFinishes) {
                this.snapshot = new StateSnapshot(Service.State.STOPPING);
                doStop();
            } else {
                this.snapshot = new StateSnapshot(Service.State.RUNNING);
                enqueueRunningEvent();
            }
            this.monitor.leave();
            dispatchListenerEvents();
        } catch (Throwable th) {
            this.monitor.leave();
            dispatchListenerEvents();
            throw th;
        }
    }

    public final void notifyStopped() {
        this.monitor.enter();
        try {
            Service.State state = state();
            switch (AnonymousClass6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
                case 1:
                case 5:
                case 6:
                    String strValueOf = String.valueOf(state);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 43);
                    sb.append("Cannot notifyStopped() when the service is ");
                    sb.append(strValueOf);
                    throw new IllegalStateException(sb.toString());
                case 2:
                case 3:
                case 4:
                    this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                    enqueueTerminatedEvent(state);
                    break;
            }
            this.monitor.leave();
            dispatchListenerEvents();
        } catch (Throwable th) {
            this.monitor.leave();
            dispatchListenerEvents();
            throw th;
        }
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (!this.monitor.enterIf(this.isStartable)) {
            String strValueOf = String.valueOf(this);
            throw new IllegalStateException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 33, "Service ", strValueOf, " has already been started"));
        }
        try {
            this.snapshot = new StateSnapshot(Service.State.STARTING);
            enqueueStartingEvent();
            doStart();
        } catch (Throwable th) {
            try {
                notifyFailed(th);
            } finally {
                this.monitor.leave();
                dispatchListenerEvents();
            }
        }
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        StateSnapshot stateSnapshot = this.snapshot;
        return (stateSnapshot.shutdownWhenStartupFinishes && stateSnapshot.state == Service.State.STARTING) ? Service.State.STOPPING : stateSnapshot.state;
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        if (this.monitor.enterIf(this.isStoppable)) {
            try {
                Service.State state = state();
                switch (AnonymousClass6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
                    case 1:
                        this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                        enqueueTerminatedEvent(Service.State.NEW);
                        break;
                    case 2:
                        Service.State state2 = Service.State.STARTING;
                        this.snapshot = new StateSnapshot(state2, true, null);
                        enqueueStoppingEvent(state2);
                        break;
                    case 3:
                        this.snapshot = new StateSnapshot(Service.State.STOPPING);
                        enqueueStoppingEvent(Service.State.RUNNING);
                        doStop();
                        break;
                    case 4:
                    case 5:
                    case 6:
                        String strValueOf = String.valueOf(state);
                        StringBuilder sb = new StringBuilder(strValueOf.length() + 45);
                        sb.append("isStoppable is incorrectly implemented, saw: ");
                        sb.append(strValueOf);
                        throw new AssertionError(sb.toString());
                }
            } catch (Throwable th) {
                try {
                    notifyFailed(th);
                } finally {
                    this.monitor.leave();
                    dispatchListenerEvents();
                }
            }
        }
        return this;
    }

    public String toString() {
        String simpleName = getClass().getSimpleName();
        String strValueOf = String.valueOf(state());
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + simpleName.length() + 3, simpleName, " [", strValueOf, "]");
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, j, timeUnit)) {
            try {
                checkCurrentState(Service.State.RUNNING);
                return;
            } finally {
                this.monitor.leave();
            }
        }
        String strValueOf = String.valueOf(this);
        throw new TimeoutException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 50, "Timed out waiting for ", strValueOf, " to reach the RUNNING state."));
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, j, timeUnit)) {
            try {
                checkCurrentState(Service.State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            String strValueOf = String.valueOf(this);
            String strValueOf2 = String.valueOf(state());
            throw new TimeoutException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 65, "Timed out waiting for ", strValueOf, " to reach a terminal state. Current state: ", strValueOf2));
        }
    }
}
