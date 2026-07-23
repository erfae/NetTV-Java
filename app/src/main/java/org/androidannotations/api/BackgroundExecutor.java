package org.androidannotations.api;

import android.os.Looper;
import android.util.Log;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public class BackgroundExecutor {
    public static Executor DEFAULT_EXECUTOR = null;
    public static final WrongThreadListener DEFAULT_WRONG_THREAD_LISTENER;
    private static final String TAG = "BackgroundExecutor";
    private static final ThreadLocal<String> currentSerial;
    private static Executor executor;
    private static final List<Task> tasks;
    private static WrongThreadListener wrongThreadListener;

    public static abstract class Task implements Runnable {
        private boolean executionAsked;
        private Future<?> future;
        private String id;
        private AtomicBoolean managed = new AtomicBoolean();
        private int remainingDelay;
        private String serial;
        private long targetTimeMillis;

        public Task(String str, int i, String str2) {
            if (!"".equals(str)) {
                this.id = str;
            }
            if (i > 0) {
                this.remainingDelay = i;
                this.targetTimeMillis = System.currentTimeMillis() + ((long) i);
            }
            if ("".equals(str2)) {
                return;
            }
            this.serial = str2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postExecute() {
            Task taskTake;
            if (this.id == null && this.serial == null) {
                return;
            }
            BackgroundExecutor.currentSerial.set(null);
            synchronized (BackgroundExecutor.class) {
                BackgroundExecutor.tasks.remove(this);
                String str = this.serial;
                if (str != null && (taskTake = BackgroundExecutor.take(str)) != null) {
                    if (taskTake.remainingDelay != 0) {
                        taskTake.remainingDelay = Math.max(0, (int) (this.targetTimeMillis - System.currentTimeMillis()));
                    }
                    BackgroundExecutor.execute(taskTake);
                }
            }
        }

        public abstract void execute();

        @Override // java.lang.Runnable
        public void run() {
            if (this.managed.getAndSet(true)) {
                return;
            }
            try {
                BackgroundExecutor.currentSerial.set(this.serial);
                execute();
            } finally {
                postExecute();
            }
        }
    }

    public interface WrongThreadListener {
        void onBgExpected(String... strArr);

        void onUiExpected();

        void onWrongBgSerial(String str, String... strArr);
    }

    static {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        DEFAULT_EXECUTOR = scheduledExecutorServiceNewScheduledThreadPool;
        executor = scheduledExecutorServiceNewScheduledThreadPool;
        WrongThreadListener wrongThreadListener2 = new WrongThreadListener() { // from class: org.androidannotations.api.BackgroundExecutor.1
            @Override // org.androidannotations.api.BackgroundExecutor.WrongThreadListener
            public void onBgExpected(String... strArr) {
                if (strArr.length != 0) {
                    throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("Method invocation is expected from one of serials "), Arrays.toString(strArr), ", but it was called from the UI thread"));
                }
                throw new IllegalStateException("Method invocation is expected from a background thread, but it was called from the UI thread");
            }

            @Override // org.androidannotations.api.BackgroundExecutor.WrongThreadListener
            public void onUiExpected() {
                throw new IllegalStateException("Method invocation is expected from the UI thread");
            }

            @Override // org.androidannotations.api.BackgroundExecutor.WrongThreadListener
            public void onWrongBgSerial(String str, String... strArr) {
                if (str == null) {
                    str = "anonymous";
                }
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("Method invocation is expected from one of serials "), Arrays.toString(strArr), ", but it was called from ", str, " serial"));
            }
        };
        DEFAULT_WRONG_THREAD_LISTENER = wrongThreadListener2;
        wrongThreadListener = wrongThreadListener2;
        tasks = new ArrayList();
        currentSerial = new ThreadLocal<>();
    }

    public static synchronized void cancelAll(String str, boolean z) {
        for (int size = tasks.size() - 1; size >= 0; size--) {
            List<Task> list = tasks;
            Task task = list.get(size);
            if (str.equals(task.id)) {
                if (task.future != null) {
                    task.future.cancel(z);
                    if (!task.managed.getAndSet(true)) {
                        task.postExecute();
                    }
                } else if (task.executionAsked) {
                    Log.w(TAG, "A task with id " + task.id + " cannot be cancelled (the executor set does not support it)");
                } else {
                    list.remove(size);
                }
            }
        }
    }

    public static void checkBgThread(String... strArr) {
        if (strArr.length == 0) {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                wrongThreadListener.onBgExpected(strArr);
                return;
            }
            return;
        }
        String str = currentSerial.get();
        if (str == null) {
            wrongThreadListener.onWrongBgSerial(null, strArr);
            return;
        }
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return;
            }
        }
        wrongThreadListener.onWrongBgSerial(str, strArr);
    }

    public static void checkUiThread() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            wrongThreadListener.onUiExpected();
        }
    }

    private static Future<?> directExecute(Runnable runnable, int i) {
        if (i > 0) {
            Executor executor2 = executor;
            if (executor2 instanceof ScheduledExecutorService) {
                return ((ScheduledExecutorService) executor2).schedule(runnable, i, TimeUnit.MILLISECONDS);
            }
            throw new IllegalArgumentException("The executor set does not support scheduling");
        }
        Executor executor3 = executor;
        if (executor3 instanceof ExecutorService) {
            return ((ExecutorService) executor3).submit(runnable);
        }
        executor3.execute(runnable);
        return null;
    }

    public static synchronized void execute(Task task) {
        Future<?> futureDirectExecute;
        if (task.serial == null || !hasSerialRunning(task.serial)) {
            task.executionAsked = true;
            futureDirectExecute = directExecute(task, task.remainingDelay);
        } else {
            futureDirectExecute = null;
        }
        if (task.id != null || task.serial != null) {
            task.future = futureDirectExecute;
            tasks.add(task);
        }
    }

    private static boolean hasSerialRunning(String str) {
        for (Task task : tasks) {
            if (task.executionAsked && str.equals(task.serial)) {
                return true;
            }
        }
        return false;
    }

    public static void setExecutor(Executor executor2) {
        executor = executor2;
    }

    public static void setWrongThreadListener(WrongThreadListener wrongThreadListener2) {
        wrongThreadListener = wrongThreadListener2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Task take(String str) {
        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            List<Task> list = tasks;
            if (str.equals(list.get(i).serial)) {
                return list.remove(i);
            }
        }
        return null;
    }

    public static void execute(final Runnable runnable, String str, int i, String str2) {
        execute(new Task(str, i, str2) { // from class: org.androidannotations.api.BackgroundExecutor.2
            @Override // org.androidannotations.api.BackgroundExecutor.Task
            public void execute() {
                runnable.run();
            }
        });
    }

    public static void execute(Runnable runnable, int i) {
        directExecute(runnable, i);
    }

    public static void execute(Runnable runnable) {
        directExecute(runnable, 0);
    }

    public static void execute(Runnable runnable, String str, String str2) {
        execute(runnable, str, 0, str2);
    }
}
