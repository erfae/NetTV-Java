package com.nettv.livestore.dlgfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetDataRequest;
import com.nettv.livestore.utils.Security;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class ParentControlDlgFragment extends DialogFragment implements GetDataRequest.OnGetResponseListener {
    public Button btn_cancel;
    public Button btn_ok;
    public EditText et_confirm_password;
    public EditText et_new_password;
    public EditText et_password;
    public PreferenceHelper sharedPreferenceHelper;
    public TextView str_confirm_password;
    public TextView str_new_password;
    public TextView str_password;
    public TextView txt_name;
    public String pin_code = "";
    public WordModels wordModels = new WordModels();

    private void initView(View view) {
        this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        this.str_password = (TextView) view.findViewById(R.id.str_password);
        this.str_new_password = (TextView) view.findViewById(R.id.str_new_password);
        this.str_confirm_password = (TextView) view.findViewById(R.id.str_confirm_password);
        this.et_password = (EditText) view.findViewById(R.id.et_password);
        this.et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        this.et_confirm_password = (EditText) view.findViewById(R.id.et_confirm_password);
        this.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        this.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        final int i = 0;
        this.btn_ok.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.ParentControlDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ ParentControlDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$initView$1(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$2(view2);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.btn_cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.ParentControlDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ ParentControlDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$initView$1(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$2(view2);
                        break;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        if (this.et_password.getText().toString().isEmpty()) {
            this.et_password.setError(this.wordModels.getParent_pass_cant_empty());
            return;
        }
        if (this.et_new_password.getText().toString().isEmpty()) {
            this.et_new_password.setError(this.wordModels.getNew_pass_cant_empty());
            return;
        }
        if (this.et_new_password.getText().toString().length() != 4) {
            this.et_new_password.setError(this.wordModels.getNew_pass_length());
            return;
        }
        if (this.et_confirm_password.getText().toString().isEmpty()) {
            this.et_confirm_password.setError(this.wordModels.getConfirm_pass_cant_empty());
            return;
        }
        if (!this.pin_code.equalsIgnoreCase(this.et_password.getText().toString())) {
            this.et_password.setError(this.wordModels.getPass_incorrect());
        } else if (this.et_new_password.getText().toString().equalsIgnoreCase(this.et_confirm_password.getText().toString())) {
            updatePinCode();
        } else {
            this.et_confirm_password.setError(this.wordModels.getConfirm_pass_not_matched());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return false;
        }
        dismiss();
        return true;
    }

    private void updatePinCode() {
        this.pin_code = this.et_new_password.getText().toString();
        String parentData = Security.getParentData(this.sharedPreferenceHelper.getSharedPreferenceMacAddress(), this.pin_code);
        GetDataRequest getDataRequest = new GetDataRequest(getContext(), 3000);
        getDataRequest.getResponse(Security.getJsonData(parentData), Constants.second_update_control);
        getDataRequest.setOnGetResponseListener(this);
    }

    @Override // com.nettv.livestore.remote.GetDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
        if (jSONObject != null) {
            try {
                if (jSONObject.getBoolean(NotificationCompat.CATEGORY_STATUS)) {
                    this.sharedPreferenceHelper.setSharedPreferenceParentPassword(this.pin_code);
                    Toast.makeText(getContext(), this.wordModels.getParent_pass_changed(), 0).show();
                    this.et_password.setText("");
                    this.et_new_password.setText("");
                    this.et_confirm_password.setText("");
                    dismiss();
                }
            } catch (Exception unused) {
            }
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_parent, viewGroup, false);
        initView(viewInflate);
        PreferenceHelper preferenceHelper = new PreferenceHelper(getContext());
        this.sharedPreferenceHelper = preferenceHelper;
        preferenceHelper.getSharedPreferenceAppInfo();
        this.pin_code = this.sharedPreferenceHelper.getSharedPreferenceParentPassword();
        WordModels wordModel = GetSharedInfo.getWordModel(getContext());
        this.wordModels = wordModel;
        this.txt_name.setText(wordModel.getParent_control());
        this.str_password.setText(this.wordModels.getPassword());
        this.str_new_password.setText(this.wordModels.getNet_pass());
        this.str_confirm_password.setText(this.wordModels.getConfirm_password());
        this.btn_ok.setText(this.wordModels.getOk());
        this.btn_cancel.setText(this.wordModels.getCancel());
        this.et_password.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.dlgfragment.ParentControlDlgFragment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 4) {
                    if (editable.toString().equalsIgnoreCase(ParentControlDlgFragment.this.pin_code)) {
                        return;
                    }
                    ParentControlDlgFragment parentControlDlgFragment = ParentControlDlgFragment.this;
                    parentControlDlgFragment.et_password.setError(parentControlDlgFragment.wordModels.getPass_incorrect());
                    return;
                }
                if (editable.toString().length() > 4) {
                    ParentControlDlgFragment parentControlDlgFragment2 = ParentControlDlgFragment.this;
                    parentControlDlgFragment2.et_password.setError(parentControlDlgFragment2.wordModels.getPass_incorrect());
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.et_confirm_password.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.dlgfragment.ParentControlDlgFragment.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 4 || editable.toString().equalsIgnoreCase(ParentControlDlgFragment.this.et_new_password.getText().toString())) {
                    return;
                }
                ParentControlDlgFragment parentControlDlgFragment = ParentControlDlgFragment.this;
                parentControlDlgFragment.et_confirm_password.setError(parentControlDlgFragment.wordModels.getConfirm_pass_not_matched());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 8));
        return viewInflate;
    }
}
