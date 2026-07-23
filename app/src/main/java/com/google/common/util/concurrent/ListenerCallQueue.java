package com.google.common.util.concurrent;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
final class ListenerCallQueue<L> {
    private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final List<PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

    public interface Event<L> {
        void call(L l);
    }

    public static final class PerListenerQueue<L> implements Runnable {
        public final Executor executor;

        @GuardedBy("this")
        public boolean isThreadScheduled;
        public final L listener;

        @GuardedBy("this")
        public final Queue<Event<L>> waitQueue = Queues.newArrayDeque();

        @GuardedBy("this")
        public final Queue<Object> labelQueue = Queues.newArrayDeque();

        public PerListenerQueue(L l, Executor executor) {
            this.listener = (L) Preconditions.checkNotNull(l);
            this.executor = (Executor) Preconditions.checkNotNull(executor);
        }

        /* JADX WARN: Bottom block not found for handler: all -> 0x0069 */
        /* JADX WARN: Code duplicated, block: B:27:0x006e  */
        /* JADX WARN: Code duplicated, block: B:37:0x006f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
        
            r2.call(r10.listener);
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
        
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0027, code lost:
        
            r4 = com.google.common.util.concurrent.ListenerCallQueue.logger;
            r5 = java.util.logging.Level.SEVERE;
            r6 = java.lang.String.valueOf(r10.listener);
            r3 = java.lang.String.valueOf(r3);
            r8 = new java.lang.StringBuilder((r6.length() + 37) + r3.length());
            r8.append("Exception while executing callback: ");
            r8.append(r6);
            r8.append(" ");
            r8.append(r3);
            r4.log(r5, r8.toString(), (java.lang.Throwable) r2);
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() throws java.lang.Throwable {
            /*
                r10 = this;
            L0:
                r0 = 1
                r1 = 0
                monitor-enter(r10)     // Catch: java.lang.Throwable -> L6b
                boolean r2 = r10.isThreadScheduled     // Catch: java.lang.Throwable -> L5f
                com.google.common.base.Preconditions.checkState(r2)     // Catch: java.lang.Throwable -> L5f
                java.util.Queue<com.google.common.util.concurrent.ListenerCallQueue$Event<L>> r2 = r10.waitQueue     // Catch: java.lang.Throwable -> L5f
                java.lang.Object r2 = r2.poll()     // Catch: java.lang.Throwable -> L5f
                com.google.common.util.concurrent.ListenerCallQueue$Event r2 = (com.google.common.util.concurrent.ListenerCallQueue.Event) r2     // Catch: java.lang.Throwable -> L5f
                java.util.Queue<java.lang.Object> r3 = r10.labelQueue     // Catch: java.lang.Throwable -> L5f
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L5f
                if (r2 != 0) goto L1f
                r10.isThreadScheduled = r1     // Catch: java.lang.Throwable -> L5f
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L1c
                return
            L1c:
                r0 = move-exception
                r2 = 0
                goto L62
            L1f:
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L5f
                L r4 = r10.listener     // Catch: java.lang.RuntimeException -> L26 java.lang.Throwable -> L6b
                r2.call(r4)     // Catch: java.lang.RuntimeException -> L26 java.lang.Throwable -> L6b
                goto L0
            L26:
                r2 = move-exception
                java.util.logging.Logger r4 = com.google.common.util.concurrent.ListenerCallQueue.access$000()     // Catch: java.lang.Throwable -> L6b
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L6b
                L r6 = r10.listener     // Catch: java.lang.Throwable -> L6b
                java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L6b
                java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch: java.lang.Throwable -> L6b
                int r7 = r6.length()     // Catch: java.lang.Throwable -> L6b
                int r7 = r7 + 37
                int r8 = r3.length()     // Catch: java.lang.Throwable -> L6b
                int r7 = r7 + r8
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6b
                r8.<init>(r7)     // Catch: java.lang.Throwable -> L6b
                java.lang.String r7 = "Exception while executing callback: "
                r8.append(r7)     // Catch: java.lang.Throwable -> L6b
                r8.append(r6)     // Catch: java.lang.Throwable -> L6b
                java.lang.String r6 = " "
                r8.append(r6)     // Catch: java.lang.Throwable -> L6b
                r8.append(r3)     // Catch: java.lang.Throwable -> L6b
                java.lang.String r3 = r8.toString()     // Catch: java.lang.Throwable -> L6b
                r4.log(r5, r3, r2)     // Catch: java.lang.Throwable -> L6b
                goto L0
            L5f:
                r2 = move-exception
                r0 = r2
                r2 = 1
            L62:
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L69
                throw r0     // Catch: java.lang.Throwable -> L64
            L64:
                r0 = move-exception
                r9 = r2
                r2 = r0
                r0 = r9
                goto L6c
            L69:
                r0 = move-exception
                goto L62
            L6b:
                r2 = move-exception
            L6c:
                if (r0 == 0) goto L76
                monitor-enter(r10)
                r10.isThreadScheduled = r1     // Catch: java.lang.Throwable -> L73
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L73
                goto L76
            L73:
                r0 = move-exception
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L73
                throw r0
            L76:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.PerListenerQueue.run():void");
        }
    }

    private void enqueueHelper(Event<L> event, Object obj) {
        Preconditions.checkNotNull(event, NotificationCompat.CATEGORY_EVENT);
        Preconditions.checkNotNull(obj, "label");
        synchronized (this.listeners) {
            for (PerListenerQueue<L> perListenerQueue : this.listeners) {
                synchronized (perListenerQueue) {
                    perListenerQueue.waitQueue.add(event);
                    perListenerQueue.labelQueue.add(obj);
                }
            }
        }
    }

    public void addListener(L l, Executor executor) {
        Preconditions.checkNotNull(l, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        Preconditions.checkNotNull(executor, "executor");
        this.listeners.add(new PerListenerQueue<>(l, executor));
    }

    public void dispatch() {
        boolean z;
        for (int i = 0; i < this.listeners.size(); i++) {
            PerListenerQueue<L> perListenerQueue = this.listeners.get(i);
            synchronized (perListenerQueue) {
                z = true;
                if (perListenerQueue.isThreadScheduled) {
                    z = false;
                } else {
                    perListenerQueue.isThreadScheduled = true;
                }
            }
            if (z) {
                try {
                    perListenerQueue.executor.execute(perListenerQueue);
                } catch (RuntimeException e) {
                    synchronized (perListenerQueue) {
                        perListenerQueue.isThreadScheduled = false;
                        Logger logger2 = logger;
                        Level level = Level.SEVERE;
                        String strValueOf = String.valueOf(perListenerQueue.listener);
                        String strValueOf2 = String.valueOf(perListenerQueue.executor);
                        logger2.log(level, MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 42, "Exception while running callbacks for ", strValueOf, " on ", strValueOf2), (Throwable) e);
                        throw e;
                    }
                }
            }
        }
    }

    public void enqueue(Event<L> event) {
        enqueueHelper(event, event);
    }

    public void enqueue(Event<L> event, String str) {
        enqueueHelper(event, str);
    }
}
