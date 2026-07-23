package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
class Subscriber {

    @Weak
    private EventBus bus;
    private final Executor executor;
    private final Method method;

    @VisibleForTesting
    public final Object target;

    @VisibleForTesting
    public static final class SynchronizedSubscriber extends Subscriber {
        @Override // com.google.common.eventbus.Subscriber
        public final void invokeSubscriberMethod(Object obj) throws InvocationTargetException {
            synchronized (this) {
                super.invokeSubscriberMethod(obj);
            }
        }

        private SynchronizedSubscriber(EventBus eventBus, Object obj, Method method) {
            super(eventBus, obj, method);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SubscriberExceptionContext context(Object obj) {
        return new SubscriberExceptionContext(this.bus, obj, this.target, this.method);
    }

    public static Subscriber create(EventBus eventBus, Object obj, Method method) {
        return isDeclaredThreadSafe(method) ? new Subscriber(eventBus, obj, method) : new SynchronizedSubscriber(eventBus, obj, method);
    }

    private static boolean isDeclaredThreadSafe(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    public final void dispatchEvent(final Object obj) {
        this.executor.execute(new Runnable() { // from class: com.google.common.eventbus.Subscriber.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Subscriber.this.invokeSubscriberMethod(obj);
                } catch (InvocationTargetException e) {
                    Subscriber.this.bus.handleSubscriberException(e.getCause(), Subscriber.this.context(obj));
                }
            }
        });
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof Subscriber)) {
            return false;
        }
        Subscriber subscriber = (Subscriber) obj;
        return this.target == subscriber.target && this.method.equals(subscriber.method);
    }

    public final int hashCode() {
        return System.identityHashCode(this.target) + ((this.method.hashCode() + 31) * 31);
    }

    @VisibleForTesting
    public void invokeSubscriberMethod(Object obj) throws InvocationTargetException {
        try {
            this.method.invoke(this.target, Preconditions.checkNotNull(obj));
        } catch (IllegalAccessException e) {
            String strValueOf = String.valueOf(obj);
            throw new Error(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 28, "Method became inaccessible: ", strValueOf), e);
        } catch (IllegalArgumentException e2) {
            String strValueOf2 = String.valueOf(obj);
            throw new Error(MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + 33, "Method rejected target/argument: ", strValueOf2), e2);
        } catch (InvocationTargetException e3) {
            if (!(e3.getCause() instanceof Error)) {
                throw e3;
            }
            throw ((Error) e3.getCause());
        }
    }

    private Subscriber(EventBus eventBus, Object obj, Method method) {
        this.bus = eventBus;
        this.target = Preconditions.checkNotNull(obj);
        this.method = method;
        method.setAccessible(true);
        this.executor = eventBus.executor();
    }
}
