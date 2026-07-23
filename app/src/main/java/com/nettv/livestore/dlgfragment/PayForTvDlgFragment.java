package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;

/* JADX INFO: loaded from: classes2.dex */
public class PayForTvDlgFragment extends DialogFragment {
    public Button btn_cancel;
    public Button btn_ok;
    public EditText et_mac;
    public OnButtonClickListener listener;
    public WordModels wordModels = new WordModels();

    public interface OnButtonClickListener {
        void onCancelClick();

        void onOkClick(String str);
    }

    private void initView(View view) {
        this.et_mac = (EditText) view.findViewById(R.id.et_mac);
        this.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        this.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        if (this.et_mac.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Please put the mac address!", 0).show();
            return;
        }
        dismiss();
        Log.e("Samsung Mac", Utils.getSamsungMac(this.et_mac.getText().toString().trim().replaceAll(":", "")));
        this.listener.onOkClick(Utils.getSamsungMac(this.et_mac.getText().toString().trim().replaceAll(":", "")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final int i = 0;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_pay_tv, viewGroup, false);
        initView(viewInflate);
        WordModels wordModel = GetSharedInfo.getWordModel(getContext());
        this.wordModels = wordModel;
        this.btn_ok.setText(wordModel.getOk());
        this.btn_cancel.setText(this.wordModels.getCancel());
        final int i2 = 1;
        this.et_mac.setFilters(new InputFilter[]{new InputFilter.LengthFilter(17)});
        this.et_mac.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.dlgfragment.PayForTvDlgFragment.1
            public int count = 0;

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int length = PayForTvDlgFragment.this.et_mac.getText().toString().length();
                int i3 = this.count;
                if (i3 <= length && (length == 2 || length == 5 || length == 8 || length == 11 || length == 14)) {
                    PayForTvDlgFragment.this.et_mac.setText(PayForTvDlgFragment.this.et_mac.getText().toString() + ":");
                    PayForTvDlgFragment.this.et_mac.setSelection(PayForTvDlgFragment.this.et_mac.getText().length());
                } else if (i3 >= length && (length == 2 || length == 5 || length == 8 || length == 11 || length == 14)) {
                    EditText editText = PayForTvDlgFragment.this.et_mac;
                    editText.setText(editText.getText().toString().substring(0, PayForTvDlgFragment.this.et_mac.getText().toString().length() - 1));
                    PayForTvDlgFragment.this.et_mac.setSelection(PayForTvDlgFragment.this.et_mac.getText().length());
                }
                this.count = PayForTvDlgFragment.this.et_mac.getText().toString().length();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }
        });
        this.btn_ok.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.PayForTvDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ PayForTvDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$onCreateView$0(view);
                        break;
                    default:
                        this.f$0.lambda$onCreateView$1(view);
                        break;
                }
            }
        });
        this.btn_cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.PayForTvDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ PayForTvDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$onCreateView$0(view);
                        break;
                    default:
                        this.f$0.lambda$onCreateView$1(view);
                        break;
                }
            }
        });
        this.btn_ok.requestFocus();
        return viewInflate;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.listener = onButtonClickListener;
    }
}
