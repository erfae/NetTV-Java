package com.nettv.livestore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.leanback.widget.HorizontalGridView;

/* JADX INFO: loaded from: classes2.dex */
public class LiveHorizontalGridView extends HorizontalGridView {
    private int K;
    private boolean L;

    public LiveHorizontalGridView(Context context) {
        super(context);
        this.K = 0;
        this.L = true;
    }

    @Override // androidx.leanback.widget.BaseGridView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int selectedPosition = getSelectedPosition();
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode == 22 && selectedPosition != -1 && getAdapter() != null && selectedPosition == getAdapter().getItemCount() - 1 && this.L) {
                    setSelectedPosition(0);
                    return true;
                }
            } else if (selectedPosition != -1 && getAdapter() != null) {
                int itemCount = getAdapter().getItemCount();
                if (selectedPosition != 0 || !this.L) {
                    return false;
                }
                setSelectedPosition(itemCount - 1);
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public int getPage() {
        return this.K;
    }

    public void setLoop(boolean z) {
        this.L = z;
    }

    public void setPage(int i) {
        this.K = i;
    }

    public LiveHorizontalGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.K = 0;
        this.L = true;
    }

    public LiveHorizontalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.K = 0;
        this.L = true;
    }
}
