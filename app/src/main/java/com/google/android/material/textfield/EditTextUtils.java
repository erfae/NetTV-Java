package com.google.android.material.textfield;

import android.widget.EditText;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
class EditTextUtils {
    private EditTextUtils() {
    }

    public static boolean isEditable(@NonNull EditText editText) {
        return editText.getInputType() != 0;
    }
}
