package com.google.android.material.sidesheet;

import android.view.View;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
interface SheetCallback {
    void onSlide(@NonNull View view, float f);

    void onStateChanged(@NonNull View view, int i);
}
