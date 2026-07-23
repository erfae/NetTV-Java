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
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.WordModels;

/* JADX INFO: loaded from: classes2.dex */
public class DescriptionDlgFragment extends DialogFragment {
    public Button btn_cancel;
    public Button btn_reload;
    public Context context;
    public String description;
    public ButtonClickListener listener;
    public PreferenceHelper preferenceHelper;
    public TextView str_device_key;
    public TextView str_mac_address;
    public String subscription;
    public TextView txt_description;
    public TextView txt_device_key;
    public TextView txt_mac_address;
    public TextView txt_subscription;
    public int playlist_size = 0;
    public WordModels wordModels = new WordModels();

    public interface ButtonClickListener {
        void onCancelClick();

        void onContinueClick();
    }

    private void initView(View view) {
        this.txt_description = (TextView) view.findViewById(R.id.txt_description);
        this.txt_subscription = (TextView) view.findViewById(R.id.txt_subscription);
        this.txt_mac_address = (TextView) view.findViewById(R.id.txt_mac_address);
        this.txt_device_key = (TextView) view.findViewById(R.id.txt_device_key);
        this.str_device_key = (TextView) view.findViewById(R.id.str_device_key);
        this.btn_reload = (Button) view.findViewById(R.id.btn_reload);
        this.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        this.str_mac_address = (TextView) view.findViewById(R.id.str_mac_address);
        this.btn_reload.setText(this.wordModels.getStr_continue());
        this.btn_cancel.setText(this.wordModels.getCancel());
        this.str_device_key.setText(this.wordModels.getDevice_key());
        if (this.playlist_size == -1) {
            if (GetSharedInfo.isTVDevice(this.context)) {
                this.btn_reload.setText(this.wordModels.getOk());
            } else {
                this.btn_reload.setText(this.wordModels.getOpen_website());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        this.listener.onContinueClick();
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        this.listener.onCancelClick();
        dismiss();
    }

    public static DescriptionDlgFragment newInstance(Context context, String str, String str2, int i) {
        DescriptionDlgFragment descriptionDlgFragment = new DescriptionDlgFragment();
        descriptionDlgFragment.context = context;
        descriptionDlgFragment.subscription = str;
        descriptionDlgFragment.description = str2;
        descriptionDlgFragment.playlist_size = i;
        return descriptionDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final int i = 0;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_description, viewGroup, false);
        this.preferenceHelper = new PreferenceHelper(this.context);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        this.preferenceHelper.getSharedPreferenceAppInfo();
        initView(viewInflate);
        this.txt_subscription.setText(this.subscription);
        this.txt_description.setText(this.description);
        this.txt_mac_address.setText(this.preferenceHelper.getSharedPreferenceMacAddress());
        this.txt_device_key.setText(this.preferenceHelper.getSharedPreferenceDeviceKey());
        this.str_mac_address.setText(this.wordModels.getMac_address());
        this.btn_reload.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.DescriptionDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ DescriptionDlgFragment f$0;

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
        final int i2 = 1;
        this.btn_cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.DescriptionDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ DescriptionDlgFragment f$0;

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
        this.btn_reload.requestFocus();
        return viewInflate;
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.listener = buttonClickListener;
    }
}
