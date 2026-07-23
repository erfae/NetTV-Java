package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public final class ServiceManager implements ServiceManagerBridge {
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;
    private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
    private static final ListenerCallQueue.Event<Listener> HEALTHY_EVENT = new ListenerCallQueue.Event<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.1
        public String toString() {
            return "healthy()";
        }

        @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
        public void call(Listener listener) {
            listener.healthy();
        }
    };
    private static final ListenerCallQueue.Event<Listener> STOPPED_EVENT = new ListenerCallQueue.Event<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.2
        public String toString() {
            return "stopped()";
        }

        @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
        public void call(Listener listener) {
            listener.stopped();
        }
    };

    public static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    public static abstract class Listener {
        public void failure(Service service) {
        }

        public void healthy() {
        }

        public void stopped() {
        }
    }

    public static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStart() {
            notifyStarted();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStop() {
            notifyStopped();
        }
    }

    public static final class ServiceListener extends Service.Listener {
        public final Service service;
        public final WeakReference<ServiceManagerState> state;

        public ServiceListener(Service service, WeakReference<ServiceManagerState> weakReference) {
            this.service = service;
            this.state = weakReference;
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void failed(Service.State state, Throwable th) {
            ServiceManagerState serviceManagerState = this.state.get();
            if (serviceManagerState != null) {
                if (!(this.service instanceof NoOpService)) {
                    Logger logger = ServiceManager.logger;
                    Level level = Level.SEVERE;
                    String strValueOf = String.valueOf(this.service);
                    String strValueOf2 = String.valueOf(state);
                    StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + 34, "Service ", strValueOf, " has failed in the ", strValueOf2);
                    sbM209m.append(" state.");
                    logger.log(level, sbM209m.toString(), th);
                }
                serviceManagerState.transitionService(this.service, state, Service.State.FAILED);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void running() {
            ServiceManagerState serviceManagerState = this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void starting() {
            ServiceManagerState serviceManagerState = this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
                if (this.service instanceof NoOpService) {
                    return;
                }
                ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void stopping(Service.State state) {
            ServiceManagerState serviceManagerState = this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, state, Service.State.STOPPING);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void terminated(Service.State state) {
            ServiceManagerState serviceManagerState = this.state.get();
            if (serviceManagerState != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, state});
                }
                serviceManagerState.transitionService(this.service, state, Service.State.TERMINATED);
            }
        }
    }

    public static final class ServiceManagerState {
        public final Monitor.Guard awaitHealthGuard;
        public final ListenerCallQueue<Listener> listeners;
        public final Monitor monitor = new Monitor();
        public final int numberOfServices;

        @GuardedBy("monitor")
        public boolean ready;

        @GuardedBy("monitor")
        public final SetMultimap<Service.State, Service> servicesByState;

        @GuardedBy("monitor")
        public final Map<Service, Stopwatch> startupTimers;

        @GuardedBy("monitor")
        public final Multiset<Service.State> states;
        public final Monitor.Guard stoppedGuard;

        @GuardedBy("monitor")
        public boolean transitioned;

        public final class AwaitHealthGuard extends Monitor.Guard {
            public AwaitHealthGuard() {
                super(ServiceManagerState.this.monitor);
            }

            @Override // com.google.common.util.concurrent.Monitor.Guard
            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                int iCount = ServiceManagerState.this.states.count(Service.State.RUNNING);
                ServiceManagerState serviceManagerState = ServiceManagerState.this;
                return iCount == serviceManagerState.numberOfServices || serviceManagerState.states.contains(Service.State.STOPPING) || ServiceManagerState.this.states.contains(Service.State.TERMINATED) || ServiceManagerState.this.states.contains(Service.State.FAILED);
            }
        }

        public final class StoppedGuard extends Monitor.Guard {
            public StoppedGuard() {
                super(ServiceManagerState.this.monitor);
            }

            @Override // com.google.common.util.concurrent.Monitor.Guard
            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(Service.State.FAILED) + ServiceManagerState.this.states.count(Service.State.TERMINATED) == ServiceManagerState.this.numberOfServices;
            }
        }

        public ServiceManagerState(ImmutableCollection<Service> immutableCollection) {
            SetMultimap<Service.State, Service> setMultimapBuild = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
            this.servicesByState = setMultimapBuild;
            this.states = setMultimapBuild.keys();
            this.startupTimers = Maps.newIdentityHashMap();
            this.awaitHealthGuard = new AwaitHealthGuard();
            this.stoppedGuard = new StoppedGuard();
            this.listeners = new ListenerCallQueue<>();
            this.numberOfServices = immutableCollection.size();
            setMultimapBuild.putAll(Service.State.NEW, immutableCollection);
        }

        @GuardedBy("monitor")
        public final void checkHealthy() {
            Multiset<Service.State> multiset = this.states;
            Service.State state = Service.State.RUNNING;
            if (multiset.count(state) == this.numberOfServices) {
                return;
            }
            String strValueOf = String.valueOf(Multimaps.filterKeys((SetMultimap) this.servicesByState, Predicates.not(Predicates.equalTo(state))));
            throw new IllegalStateException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 79, "Expected to be healthy after starting. The following services are not running: ", strValueOf));
        }

        public final void dispatchListenerEvents() {
            Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            this.listeners.dispatch();
        }

        public final ImmutableSetMultimap<Service.State, Service> servicesByState() {
            ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                for (Map.Entry<Service.State, Service> entry : this.servicesByState.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put((Map.Entry) entry);
                    }
                }
                this.monitor.leave();
                return builder.build();
            } catch (Throwable th) {
                this.monitor.leave();
                throw th;
            }
        }

        public final void transitionService(final Service service, Service.State state, Service.State state2) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(state != state2);
            this.monitor.enter();
            try {
                this.transitioned = true;
                if (this.ready) {
                    Preconditions.checkState(this.servicesByState.remove(state, service), "Service %s not at the expected location in the state map %s", service, state);
                    Preconditions.checkState(this.servicesByState.put(state2, service), "Service %s in the state map unexpectedly at %s", service, state2);
                    Stopwatch stopwatchCreateStarted = this.startupTimers.get(service);
                    if (stopwatchCreateStarted == null) {
                        stopwatchCreateStarted = Stopwatch.createStarted();
                        this.startupTimers.put(service, stopwatchCreateStarted);
                    }
                    Service.State state3 = Service.State.RUNNING;
                    if (state2.compareTo(state3) >= 0 && stopwatchCreateStarted.isRunning()) {
                        stopwatchCreateStarted.stop();
                        if (!(service instanceof NoOpService)) {
                            ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatchCreateStarted});
                        }
                    }
                    Service.State state4 = Service.State.FAILED;
                    if (state2 == state4) {
                        this.listeners.enqueue(new ListenerCallQueue.Event<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.2
                            public String toString() {
                                String strValueOf = String.valueOf(service);
                                return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 18, "failed({service=", strValueOf, "})");
                            }

                            @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
                            public void call(Listener listener) {
                                listener.failure(service);
                            }
                        });
                    }
                    if (this.states.count(state3) == this.numberOfServices) {
                        this.listeners.enqueue(ServiceManager.HEALTHY_EVENT);
                    } else if (this.states.count(Service.State.TERMINATED) + this.states.count(state4) == this.numberOfServices) {
                        this.listeners.enqueue(ServiceManager.STOPPED_EVENT);
                    }
                }
            } finally {
                this.monitor.leave();
                dispatchListenerEvents();
            }
        }
    }

    public ServiceManager(Iterable<? extends Service> iterable) {
        ImmutableList<Service> immutableListCopyOf = ImmutableList.copyOf(iterable);
        if (immutableListCopyOf.isEmpty()) {
            logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", (Throwable) new EmptyServiceManagerWarning());
            immutableListCopyOf = ImmutableList.of(new NoOpService());
        }
        ServiceManagerState serviceManagerState = new ServiceManagerState(immutableListCopyOf);
        this.state = serviceManagerState;
        this.services = immutableListCopyOf;
        WeakReference weakReference = new WeakReference(serviceManagerState);
        UnmodifiableIterator<Service> it = immutableListCopyOf.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Service next = it.next();
            next.addListener(new ServiceListener(next, weakReference), MoreExecutors.directExecutor());
            if (next.state() != Service.State.NEW) {
                z = false;
            }
            Preconditions.checkArgument(z, "Can only manage NEW services, %s", next);
        }
        ServiceManagerState serviceManagerState2 = this.state;
        serviceManagerState2.monitor.enter();
        try {
            if (!serviceManagerState2.transitioned) {
                serviceManagerState2.ready = true;
                serviceManagerState2.monitor.leave();
                return;
            }
            ArrayList arrayListNewArrayList = Lists.newArrayList();
            UnmodifiableIterator<Service> it2 = serviceManagerState2.servicesByState().values().iterator();
            while (it2.hasNext()) {
                Service next2 = it2.next();
                if (next2.state() != Service.State.NEW) {
                    arrayListNewArrayList.add(next2);
                }
            }
            String strValueOf = String.valueOf(arrayListNewArrayList);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 89);
            sb.append("Services started transitioning asynchronously before the ServiceManager was constructed: ");
            sb.append(strValueOf);
            throw new IllegalArgumentException(sb.toString());
        } catch (Throwable th) {
            serviceManagerState2.monitor.leave();
            throw th;
        }
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.listeners.addListener(listener, executor);
    }

    public void awaitHealthy() {
        ServiceManagerState serviceManagerState = this.state;
        serviceManagerState.monitor.enterWhenUninterruptibly(serviceManagerState.awaitHealthGuard);
        try {
            serviceManagerState.checkHealthy();
        } finally {
            serviceManagerState.monitor.leave();
        }
    }

    public void awaitStopped() {
        ServiceManagerState serviceManagerState = this.state;
        serviceManagerState.monitor.enterWhenUninterruptibly(serviceManagerState.stoppedGuard);
        serviceManagerState.monitor.leave();
    }

    public boolean isHealthy() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            if (!it.next().isRunning()) {
                return false;
            }
        }
        return true;
    }

    @CanIgnoreReturnValue
    public ServiceManager startAsync() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            Service next = it.next();
            Service.State state = next.state();
            Preconditions.checkState(state == Service.State.NEW, "Service %s is %s, cannot start it.", next, state);
        }
        UnmodifiableIterator<Service> it2 = this.services.iterator();
        while (it2.hasNext()) {
            Service next2 = it2.next();
            try {
                ServiceManagerState serviceManagerState = this.state;
                serviceManagerState.monitor.enter();
                try {
                    if (serviceManagerState.startupTimers.get(next2) == null) {
                        serviceManagerState.startupTimers.put(next2, Stopwatch.createStarted());
                    }
                    serviceManagerState.monitor.leave();
                    next2.startAsync();
                } catch (Throwable th) {
                    serviceManagerState.monitor.leave();
                    throw th;
                }
            } catch (IllegalStateException e) {
                Logger logger2 = logger;
                Level level = Level.WARNING;
                String strValueOf = String.valueOf(next2);
                logger2.log(level, MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 24, "Unable to start Service ", strValueOf), (Throwable) e);
            }
        }
        return this;
    }

    public ImmutableMap<Service, Long> startupTimes() {
        ServiceManagerState serviceManagerState = this.state;
        serviceManagerState.monitor.enter();
        try {
            ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(serviceManagerState.startupTimers.size());
            for (Map.Entry<Service, Stopwatch> entry : serviceManagerState.startupTimers.entrySet()) {
                Service key = entry.getKey();
                Stopwatch value = entry.getValue();
                if (!value.isRunning() && !(key instanceof NoOpService)) {
                    arrayListNewArrayListWithCapacity.add(Maps.immutableEntry(key, Long.valueOf(value.elapsed(TimeUnit.MILLISECONDS))));
                }
            }
            serviceManagerState.monitor.leave();
            Collections.sort(arrayListNewArrayListWithCapacity, Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>() { // from class: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.1
                @Override // com.google.common.base.Function
                public Long apply(Map.Entry<Service, Long> entry2) {
                    return entry2.getValue();
                }
            }));
            return ImmutableMap.copyOf(arrayListNewArrayListWithCapacity);
        } catch (Throwable th) {
            serviceManagerState.monitor.leave();
            throw th;
        }
    }

    @CanIgnoreReturnValue
    public ServiceManager stopAsync() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            it.next().stopAsync();
        }
        return this;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Class<?>) ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }

    @Override // com.google.common.util.concurrent.ServiceManagerBridge
    public ImmutableSetMultimap<Service.State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public void awaitStopped(long j, TimeUnit timeUnit) throws TimeoutException {
        ServiceManagerState serviceManagerState = this.state;
        serviceManagerState.monitor.enter();
        try {
            if (serviceManagerState.monitor.waitForUninterruptibly(serviceManagerState.stoppedGuard, j, timeUnit)) {
                serviceManagerState.monitor.leave();
                return;
            }
            String strValueOf = String.valueOf(Multimaps.filterKeys((SetMultimap) serviceManagerState.servicesByState, Predicates.not(Predicates.in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
            StringBuilder sb = new StringBuilder(strValueOf.length() + 83);
            sb.append("Timeout waiting for the services to stop. The following services have not stopped: ");
            sb.append(strValueOf);
            throw new TimeoutException(sb.toString());
        } catch (Throwable th) {
            serviceManagerState.monitor.leave();
            throw th;
        }
    }

    public void awaitHealthy(long j, TimeUnit timeUnit) throws TimeoutException {
        ServiceManagerState serviceManagerState = this.state;
        serviceManagerState.monitor.enter();
        try {
            if (serviceManagerState.monitor.waitForUninterruptibly(serviceManagerState.awaitHealthGuard, j, timeUnit)) {
                serviceManagerState.checkHealthy();
                serviceManagerState.monitor.leave();
            } else {
                String strValueOf = String.valueOf(Multimaps.filterKeys((SetMultimap) serviceManagerState.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
                StringBuilder sb = new StringBuilder(strValueOf.length() + 93);
                sb.append("Timeout waiting for the services to become healthy. The following services have not started: ");
                sb.append(strValueOf);
                throw new TimeoutException(sb.toString());
            }
        } catch (Throwable th) {
            serviceManagerState.monitor.leave();
            throw th;
        }
    }
}
