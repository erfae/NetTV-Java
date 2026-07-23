package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;

/* JADX INFO: loaded from: classes2.dex */
public class UpdateDlgFragment extends DialogFragment implements View.OnClickListener {
    public Button btn_cancel;
    public Button btn_ok;
    public UpdateAvailableListener listener;
    public TextView txt_description;
    public String description = "";
    public String str_update = "";
    public String str_cancel = "";
    public boolean is_available = false;

    public interface UpdateAvailableListener {
        void onUpdateAvailable();
    }

    private void initView(View view) {
        this.txt_description = (TextView) view.findViewById(R.id.txt_description);
        this.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        this.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        this.btn_ok.setOnClickListener(this);
        this.btn_cancel.setOnClickListener(this);
        this.txt_description.setText(this.description);
        this.btn_ok.setText(this.str_update);
        this.btn_cancel.setText(this.str_cancel);
    }

    public static UpdateDlgFragment newInstance(Context context, String str, String str2, String str3, boolean z) {
        UpdateDlgFragment updateDlgFragment = new UpdateDlgFragment();
        updateDlgFragment.description = str;
        updateDlgFragment.is_available = z;
        updateDlgFragment.str_update = str2;
        updateDlgFragment.str_cancel = str3;
        return updateDlgFragment;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else {
            if (id != R.id.btn_ok) {
                return;
            }
            if (this.is_available) {
                this.listener.onUpdateAvailable();
            } else {
                dismiss();
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
        View viewInflate = layoutInflater.inflate(R.layout.fragment_update, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    public void setOnUpdateAvailableListener(UpdateAvailableListener updateAvailableListener) {
        this.listener = updateAvailableListener;
    }
}
