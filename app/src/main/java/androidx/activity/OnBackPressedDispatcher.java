package androidx.activity;

import android.annotation.SuppressLint;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.annotation.DoNotInline;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.core.os.BuildCompat;
import androidx.core.util.Consumer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    private boolean mBackInvokedCallbackRegistered;
    private Consumer<Boolean> mEnabledConsumer;

    @Nullable
    private final Runnable mFallbackOnBackPressed;
    private OnBackInvokedDispatcher mInvokedDispatcher;
    private OnBackInvokedCallback mOnBackInvokedCallback;
    public final ArrayDeque<OnBackPressedCallback> mOnBackPressedCallbacks;

    @RequiresApi(33)
    public static class Api33Impl {
        private Api33Impl() {
        }

        @DoNotInline
        public static OnBackInvokedCallback createOnBackInvokedCallback(Runnable runnable) {
            Objects.requireNonNull(runnable);
            return new OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0(runnable);
        }

        @DoNotInline
        public static void registerOnBackInvokedCallback(Object obj, int i, Object obj2) {
            ((OnBackInvokedDispatcher) obj).registerOnBackInvokedCallback(i, (OnBackInvokedCallback) obj2);
        }

        @DoNotInline
        public static void unregisterOnBackInvokedCallback(Object obj, Object obj2) {
            ((OnBackInvokedDispatcher) obj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) obj2);
        }
    }

    public class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {

        @Nullable
        private Cancellable mCurrentCancellable;
        private final Lifecycle mLifecycle;
        private final OnBackPressedCallback mOnBackPressedCallback;

        public LifecycleOnBackPressedCancellable(@NonNull Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            this.mLifecycle = lifecycle;
            this.mOnBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.activity.Cancellable
        public void cancel() {
            this.mLifecycle.removeObserver(this);
            this.mOnBackPressedCallback.removeCancellable(this);
            Cancellable cancellable = this.mCurrentCancellable;
            if (cancellable != null) {
                cancellable.cancel();
                this.mCurrentCancellable = null;
            }
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.mCurrentCancellable = OnBackPressedDispatcher.this.addCancellableCallback(this.mOnBackPressedCallback);
                return;
            }
            if (event != Lifecycle.Event.ON_STOP) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    cancel();
                }
            } else {
                Cancellable cancellable = this.mCurrentCancellable;
                if (cancellable != null) {
                    cancellable.cancel();
                }
            }
        }
    }

    public class OnBackPressedCancellable implements Cancellable {
        private final OnBackPressedCallback mOnBackPressedCallback;

        public OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.mOnBackPressedCallback = onBackPressedCallback;
        }

        @Override // androidx.activity.Cancellable
        @OptIn(markerClass = {BuildCompat.PrereleaseSdkCheck.class})
        public void cancel() {
            OnBackPressedDispatcher.this.mOnBackPressedCallbacks.remove(this.mOnBackPressedCallback);
            this.mOnBackPressedCallback.removeCancellable(this);
            if (BuildCompat.isAtLeastT()) {
                this.mOnBackPressedCallback.setIsEnabledConsumer(null);
                OnBackPressedDispatcher.this.updateBackInvokedCallbackState();
            }
        }
    }

    public OnBackPressedDispatcher() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Boolean bool) {
        if (BuildCompat.isAtLeastT()) {
            updateBackInvokedCallbackState();
        }
    }

    @MainThread
    public void addCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        addCancellableCallback(onBackPressedCallback);
    }

    @NonNull
    @OptIn(markerClass = {BuildCompat.PrereleaseSdkCheck.class})
    @MainThread
    public final Cancellable addCancellableCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        this.mOnBackPressedCallbacks.add(onBackPressedCallback);
        OnBackPressedCancellable onBackPressedCancellable = new OnBackPressedCancellable(onBackPressedCallback);
        onBackPressedCallback.addCancellable(onBackPressedCancellable);
        if (BuildCompat.isAtLeastT()) {
            updateBackInvokedCallbackState();
            onBackPressedCallback.setIsEnabledConsumer(this.mEnabledConsumer);
        }
        return onBackPressedCancellable;
    }

    @MainThread
    public boolean hasEnabledCallbacks() {
        Iterator<OnBackPressedCallback> itDescendingIterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next().isEnabled()) {
                return true;
            }
        }
        return false;
    }

    @MainThread
    public void onBackPressed() {
        Iterator<OnBackPressedCallback> itDescendingIterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            OnBackPressedCallback next = itDescendingIterator.next();
            if (next.isEnabled()) {
                next.handleOnBackPressed();
                return;
            }
        }
        Runnable runnable = this.mFallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    @RequiresApi(33)
    public void setOnBackInvokedDispatcher(@NonNull OnBackInvokedDispatcher onBackInvokedDispatcher) {
        this.mInvokedDispatcher = onBackInvokedDispatcher;
        updateBackInvokedCallbackState();
    }

    @RequiresApi(33)
    public final void updateBackInvokedCallbackState() {
        boolean zHasEnabledCallbacks = hasEnabledCallbacks();
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mInvokedDispatcher;
        if (onBackInvokedDispatcher != null) {
            if (zHasEnabledCallbacks && !this.mBackInvokedCallbackRegistered) {
                Api33Impl.registerOnBackInvokedCallback(onBackInvokedDispatcher, 0, this.mOnBackInvokedCallback);
                this.mBackInvokedCallbackRegistered = true;
            } else {
                if (zHasEnabledCallbacks || !this.mBackInvokedCallbackRegistered) {
                    return;
                }
                Api33Impl.unregisterOnBackInvokedCallback(onBackInvokedDispatcher, this.mOnBackInvokedCallback);
                this.mBackInvokedCallbackRegistered = false;
            }
        }
    }

    @OptIn(markerClass = {BuildCompat.PrereleaseSdkCheck.class})
    public OnBackPressedDispatcher(@Nullable Runnable runnable) {
        this.mOnBackPressedCallbacks = new ArrayDeque<>();
        this.mBackInvokedCallbackRegistered = false;
        this.mFallbackOnBackPressed = runnable;
        if (BuildCompat.isAtLeastT()) {
            this.mEnabledConsumer = new Consumer() { // from class: androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$new$0((Boolean) obj);
                }
            };
            this.mOnBackInvokedCallback = Api33Impl.createOnBackInvokedCallback(new ComponentDialog$$ExternalSyntheticLambda0(this, 2));
        }
    }

    @OptIn(markerClass = {BuildCompat.PrereleaseSdkCheck.class})
    @SuppressLint({"LambdaLast"})
    @MainThread
    public void addCallback(@NonNull LifecycleOwner lifecycleOwner, @NonNull OnBackPressedCallback onBackPressedCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        onBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(lifecycle, onBackPressedCallback));
        if (BuildCompat.isAtLeastT()) {
            updateBackInvokedCallbackState();
            onBackPressedCallback.setIsEnabledConsumer(this.mEnabledConsumer);
        }
    }
}
