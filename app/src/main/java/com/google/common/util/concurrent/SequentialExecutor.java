package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.RetainedWith;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
final class SequentialExecutor implements Executor {
    private static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
    private final Executor executor;

    @GuardedBy("queue")
    private final Deque<Runnable> queue = new ArrayDeque();

    @GuardedBy("queue")
    private WorkerRunningState workerRunningState = WorkerRunningState.IDLE;

    @GuardedBy("queue")
    private long workerRunCount = 0;

    @RetainedWith
    private final QueueWorker worker = new QueueWorker();

    public final class QueueWorker implements Runnable {

        @CheckForNull
        public Runnable task;

        private QueueWorker() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:
        
            if (r1 == false) goto L49;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0045, code lost:
        
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
        
            r1 = r1 | java.lang.Thread.interrupted();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
        
            r9.task.run();
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x005c, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x005e, code lost:
        
            r3 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x005f, code lost:
        
            r4 = com.google.common.util.concurrent.SequentialExecutor.log;
            r5 = java.util.logging.Level.SEVERE;
            r6 = java.lang.String.valueOf(r9.task);
            r8 = new java.lang.StringBuilder(r6.length() + 35);
            r8.append("Exception while executing runnable ");
            r8.append(r6);
            r4.log(r5, r8.toString(), (java.lang.Throwable) r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0086, code lost:
        
            r9.task = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0088, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void workOnQueue() {
            /*
                r9 = this;
                r0 = 0
                r1 = 0
            L2:
                com.google.common.util.concurrent.SequentialExecutor r2 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L8c
                java.util.Deque r2 = com.google.common.util.concurrent.SequentialExecutor.access$100(r2)     // Catch: java.lang.Throwable -> L8c
                monitor-enter(r2)     // Catch: java.lang.Throwable -> L8c
                if (r0 != 0) goto L2b
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = com.google.common.util.concurrent.SequentialExecutor.access$200(r0)     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch: java.lang.Throwable -> L89
                if (r0 != r3) goto L20
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L89
                if (r1 == 0) goto L1f
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L1f:
                return
            L20:
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor.access$308(r0)     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor.access$202(r0, r3)     // Catch: java.lang.Throwable -> L89
                r0 = 1
            L2b:
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L89
                java.util.Deque r3 = com.google.common.util.concurrent.SequentialExecutor.access$100(r3)     // Catch: java.lang.Throwable -> L89
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L89
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch: java.lang.Throwable -> L89
                r9.task = r3     // Catch: java.lang.Throwable -> L89
                if (r3 != 0) goto L4d
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch: java.lang.Throwable -> L89
                com.google.common.util.concurrent.SequentialExecutor.access$202(r0, r3)     // Catch: java.lang.Throwable -> L89
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L89
                if (r1 == 0) goto L4c
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L4c:
                return
            L4d:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L89
                boolean r2 = java.lang.Thread.interrupted()     // Catch: java.lang.Throwable -> L8c
                r1 = r1 | r2
                r2 = 0
                java.lang.Runnable r3 = r9.task     // Catch: java.lang.Throwable -> L5c java.lang.RuntimeException -> L5e
                r3.run()     // Catch: java.lang.Throwable -> L5c java.lang.RuntimeException -> L5e
            L59:
                r9.task = r2     // Catch: java.lang.Throwable -> L8c
                goto L2
            L5c:
                r0 = move-exception
                goto L86
            L5e:
                r3 = move-exception
                java.util.logging.Logger r4 = com.google.common.util.concurrent.SequentialExecutor.access$400()     // Catch: java.lang.Throwable -> L5c
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L5c
                java.lang.Runnable r6 = r9.task     // Catch: java.lang.Throwable -> L5c
                java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L5c
                int r7 = r6.length()     // Catch: java.lang.Throwable -> L5c
                int r7 = r7 + 35
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5c
                r8.<init>(r7)     // Catch: java.lang.Throwable -> L5c
                java.lang.String r7 = "Exception while executing runnable "
                r8.append(r7)     // Catch: java.lang.Throwable -> L5c
                r8.append(r6)     // Catch: java.lang.Throwable -> L5c
                java.lang.String r6 = r8.toString()     // Catch: java.lang.Throwable -> L5c
                r4.log(r5, r6, r3)     // Catch: java.lang.Throwable -> L5c
                goto L59
            L86:
                r9.task = r2     // Catch: java.lang.Throwable -> L8c
                throw r0     // Catch: java.lang.Throwable -> L8c
            L89:
                r0 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L89
                throw r0     // Catch: java.lang.Throwable -> L8c
            L8c:
                r0 = move-exception
                if (r1 == 0) goto L96
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                r1.interrupt()
            L96:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.QueueWorker.workOnQueue():void");
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SequentialExecutor.this.queue) {
                    SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw e;
                }
            }
        }

        public String toString() {
            Runnable runnable = this.task;
            if (runnable != null) {
                String strValueOf = String.valueOf(runnable);
                return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 34, "SequentialExecutorWorker{running=", strValueOf, "}");
            }
            String strValueOf2 = String.valueOf(SequentialExecutor.this.workerRunningState);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + 32, "SequentialExecutorWorker{state=", strValueOf2, "}");
        }
    }

    public enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    public SequentialExecutor(Executor executor) {
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    public static /* synthetic */ long access$308(SequentialExecutor sequentialExecutor) {
        long j = sequentialExecutor.workerRunCount;
        sequentialExecutor.workerRunCount = 1 + j;
        return j;
    }

    @Override // java.util.concurrent.Executor
    public void execute(final Runnable runnable) {
        WorkerRunningState workerRunningState;
        Preconditions.checkNotNull(runnable);
        synchronized (this.queue) {
            WorkerRunningState workerRunningState2 = this.workerRunningState;
            if (workerRunningState2 != WorkerRunningState.RUNNING && workerRunningState2 != (workerRunningState = WorkerRunningState.QUEUED)) {
                long j = this.workerRunCount;
                Runnable runnable2 = new Runnable() { // from class: com.google.common.util.concurrent.SequentialExecutor.1
                    @Override // java.lang.Runnable
                    public void run() {
                        runnable.run();
                    }

                    public String toString() {
                        return runnable.toString();
                    }
                };
                this.queue.add(runnable2);
                WorkerRunningState workerRunningState3 = WorkerRunningState.QUEUING;
                this.workerRunningState = workerRunningState3;
                boolean z = true;
                try {
                    this.executor.execute(this.worker);
                    if (this.workerRunningState != workerRunningState3) {
                        return;
                    }
                    synchronized (this.queue) {
                        if (this.workerRunCount == j && this.workerRunningState == workerRunningState3) {
                            this.workerRunningState = workerRunningState;
                        }
                    }
                    return;
                } catch (Error | RuntimeException e) {
                    synchronized (this.queue) {
                        WorkerRunningState workerRunningState4 = this.workerRunningState;
                        if ((workerRunningState4 != WorkerRunningState.IDLE && workerRunningState4 != WorkerRunningState.QUEUING) || !this.queue.removeLastOccurrence(runnable2)) {
                            z = false;
                        }
                        if (!(e instanceof RejectedExecutionException) || z) {
                            throw e;
                        }
                    }
                    return;
                }
            }
            this.queue.add(runnable);
        }
    }

    public String toString() {
        int iIdentityHashCode = System.identityHashCode(this);
        String strValueOf = String.valueOf(this.executor);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 32);
        sb.append("SequentialExecutor@");
        sb.append(iIdentityHashCode);
        sb.append("{");
        sb.append(strValueOf);
        sb.append("}");
        return sb.toString();
    }
}
