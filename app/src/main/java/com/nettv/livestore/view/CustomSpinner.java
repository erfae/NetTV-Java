package com.nettv.livestore.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

/* JADX INFO: loaded from: classes2.dex */
public class CustomSpinner extends AppCompatSpinner {
    private OnSpinnerEventsListener listener;
    private boolean mOpenInitiated;

    public interface OnSpinnerEventsListener {
        void onPopupWindowClosed(Spinner spinner);

        void onPopupWindowOpened(Spinner spinner);
    }

    public CustomSpinner(@NonNull Context context) {
        super(context);
        this.mOpenInitiated = false;
    }

    public boolean hasBeenFocused() {
        return this.mOpenInitiated;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        if (hasBeenFocused() && z) {
            performCloseEvent();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatSpinner, android.widget.Spinner, android.view.View
    public boolean performClick() {
        this.mOpenInitiated = false;
        OnSpinnerEventsListener onSpinnerEventsListener = this.listener;
        if (onSpinnerEventsListener != null) {
            onSpinnerEventsListener.onPopupWindowOpened(this);
        }
        return super.performClick();
    }

    public void performCloseEvent() {
        this.mOpenInitiated = false;
        OnSpinnerEventsListener onSpinnerEventsListener = this.listener;
        if (onSpinnerEventsListener != null) {
            onSpinnerEventsListener.onPopupWindowClosed(this);
        }
    }

    public void setSpinnerEventsListener(OnSpinnerEventsListener onSpinnerEventsListener) {
        this.listener = onSpinnerEventsListener;
    }

    public CustomSpinner(@NonNull Context context, int i) {
        super(context, i);
        this.mOpenInitiated = false;
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOpenInitiated = false;
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOpenInitiated = false;
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOpenInitiated = false;
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2, Resources.Theme theme) {
        super(context, attributeSet, i, i2, theme);
        this.mOpenInitiated = false;
    }
}
