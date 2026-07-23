package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
abstract class SheetDelegate {
    public abstract float calculateSlideOffsetBasedOnOutwardEdge(int i);

    public abstract int calculateTargetStateOnViewReleased(@NonNull View view, float f, float f2);

    public abstract int getExpandedOffset();

    public abstract int getHiddenOffset();

    public abstract <V extends View> int getOutwardEdge(@NonNull V v);

    public abstract void getSheetEdge();

    public abstract boolean isSettling(View view, int i, boolean z);

    public abstract void updateCoplanarSiblingLayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams, int i);
}
