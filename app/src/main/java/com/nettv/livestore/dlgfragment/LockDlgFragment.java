package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;

/* JADX INFO: loaded from: classes2.dex */
public class LockDlgFragment extends DialogFragment {
    public Button btn_ok;
    public EditText et_pass;
    public OnPinEventListener listener;
    public String pin_code;

    public interface OnPinEventListener {
        void OnPinCorrect();

        void OnPinIncorrect();

        void OnPutPinCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        if (this.et_pass.getText().toString().isEmpty()) {
            this.listener.OnPutPinCode();
        } else if (this.pin_code.equalsIgnoreCase(this.et_pass.getText().toString())) {
            dismiss();
            this.listener.OnPinCorrect();
        } else {
            this.listener.OnPinIncorrect();
            this.et_pass.setText("");
        }
    }

    public static LockDlgFragment newInstance(String str) {
        LockDlgFragment lockDlgFragment = new LockDlgFragment();
        lockDlgFragment.pin_code = str;
        return lockDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lock, viewGroup, false);
        this.et_pass = (EditText) viewInflate.findViewById(R.id.et_pass);
        this.btn_ok = (Button) viewInflate.findViewById(R.id.btn_ok);
        this.et_pass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        this.btn_ok.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 10));
        return viewInflate;
    }

    public void setOnPinEventListener(OnPinEventListener onPinEventListener) {
        this.listener = onPinEventListener;
    }
}
