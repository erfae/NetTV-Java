package com.google.android.material.textfield;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
class CustomEndIconDelegate extends EndIconDelegate {
    public CustomEndIconDelegate(@NonNull EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        this.endLayout.setEndIconOnLongClickListener(null);
    }
}
