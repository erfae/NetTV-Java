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
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.LoginModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class AccountDlgFragment extends DialogFragment {
    public AppInfoModel appInfoModel;
    public Button btn_pay;
    public Context context;
    public PayButtonClickListener listener;
    public LoginModel loginModel;
    public PreferenceHelper sharedPreferenceHelper;
    public TextView str_account;
    public TextView str_device_key;
    public TextView str_expire;
    public TextView str_mac;
    public TextView str_playlist_expire;
    public TextView str_state;
    public TextView str_username;
    public TextView txt_active_con;
    public TextView txt_device_key;
    public TextView txt_expire;
    public TextView txt_mac;
    public TextView txt_max_con;
    public TextView txt_playlist_expire;
    public TextView txt_state;
    public TextView txt_username;
    public View view_click;
    public WordModels wordModels = new WordModels();
    public SimpleDateFormat expire_format = new SimpleDateFormat("yyyy-MM-dd");
    public long expired_mils = 0;

    public interface PayButtonClickListener {
        void onPayButtonClicked();
    }

    private void initView(View view) {
        this.str_account = (TextView) view.findViewById(R.id.str_account);
        this.str_username = (TextView) view.findViewById(R.id.str_username);
        this.str_mac = (TextView) view.findViewById(R.id.str_mac);
        this.str_state = (TextView) view.findViewById(R.id.str_state);
        this.str_expire = (TextView) view.findViewById(R.id.str_expire);
        this.str_device_key = (TextView) view.findViewById(R.id.str_device_key);
        this.str_playlist_expire = (TextView) view.findViewById(R.id.str_playlist_expire);
        this.view_click = view.findViewById(R.id.view_click);
        this.txt_username = (TextView) view.findViewById(R.id.txt_username);
        this.txt_mac = (TextView) view.findViewById(R.id.txt_mac);
        this.txt_state = (TextView) view.findViewById(R.id.txt_state);
        this.txt_expire = (TextView) view.findViewById(R.id.txt_expire);
        this.txt_device_key = (TextView) view.findViewById(R.id.txt_device_key);
        this.txt_active_con = (TextView) view.findViewById(R.id.txt_connections);
        this.txt_max_con = (TextView) view.findViewById(R.id.txt_max_connections);
        this.txt_playlist_expire = (TextView) view.findViewById(R.id.txt_playlist_expire);
        final int i = 0;
        this.view_click.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.AccountDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ AccountDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$initView$0(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$1(view2);
                        break;
                }
            }
        });
        Button button = (Button) view.findViewById(R.id.btn_pay);
        this.btn_pay = button;
        final int i2 = 1;
        button.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.AccountDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ AccountDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$initView$0(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$1(view2);
                        break;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        this.listener.onPayButtonClicked();
    }

    public static AccountDlgFragment newInstance(Context context) {
        AccountDlgFragment accountDlgFragment = new AccountDlgFragment();
        accountDlgFragment.context = context;
        return accountDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_account, viewGroup, false);
        initView(viewInflate);
        PreferenceHelper preferenceHelper = new PreferenceHelper(this.context);
        this.sharedPreferenceHelper = preferenceHelper;
        this.appInfoModel = preferenceHelper.getSharedPreferenceAppInfo();
        this.loginModel = this.sharedPreferenceHelper.getSharedPreferenceLoginModel();
        if (this.sharedPreferenceHelper.getSharedPreferenceISM3U()) {
            this.txt_username.setText("");
            this.txt_active_con.setText("");
            this.txt_max_con.setText("");
            this.txt_playlist_expire.setText("");
        } else {
            LoginModel loginModel = this.loginModel;
            if (loginModel != null) {
                this.txt_username.setText(loginModel.getUsername());
                this.txt_active_con.setText(this.loginModel.getActive_cons());
                this.txt_max_con.setText(this.loginModel.getMax_connections());
                this.txt_playlist_expire.setText(Utils.getDate(this.loginModel.getExp_date()));
            }
        }
        try {
            this.expired_mils = this.expire_format.parse(this.appInfoModel.getExpiredDate()).getTime();
        } catch (Exception unused) {
            this.expired_mils = 0L;
        }
        this.txt_mac.setText(this.sharedPreferenceHelper.getSharedPreferenceMacAddress());
        if (this.appInfoModel.getIs_trial() == 2 || this.appInfoModel.isIs_google_pay() || this.expired_mils - new Date().getTime() > 604800000) {
            this.txt_state.setText("Activate");
            this.btn_pay.setVisibility(8);
        } else {
            this.btn_pay.setVisibility(8);
            this.txt_state.setText("Free Trial");
        }
        if (Utils.IsAmazonDevice()) {
            this.btn_pay.setVisibility(8);
        }
        try {
            this.txt_expire.setText(this.appInfoModel.getExpiredDate());
        } catch (Exception unused2) {
            this.txt_expire.setText("unlimited");
        }
        this.txt_device_key.setText(this.appInfoModel.getDevice_key());
        WordModels wordModel = GetSharedInfo.getWordModel(this.context);
        this.wordModels = wordModel;
        this.str_account.setText(wordModel.getAccount());
        this.str_username.setText(this.wordModels.getUsername());
        this.str_mac.setText(this.wordModels.getMac_address());
        this.str_expire.setText(this.wordModels.getExpire_date());
        this.str_state.setText(this.wordModels.getApp_status());
        this.str_device_key.setText(this.wordModels.getDevice_key());
        this.str_playlist_expire.setText(this.wordModels.getPlaylist_expire_date());
        this.btn_pay.setText(this.wordModels.getPay_with_google_pay() + "(€" + this.appInfoModel.getPrice() + ")");
        return viewInflate;
    }

    public void setOnPayButtonClickListener(PayButtonClickListener payButtonClickListener) {
        this.listener = payButtonClickListener;
    }
}
