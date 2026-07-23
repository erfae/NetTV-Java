package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.MovieActivity$$ExternalSyntheticLambda2;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.dlg.SuccessDlg;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetDataRequest;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class AddPlaylistDlgFragment extends DialogFragment implements View.OnClickListener, GetDataRequest.OnGetResponseListener {
    public AppInfoModel appInfoModel;
    public Button btn_add;
    public Button btn_add_m3u;
    public Button btn_add_xc;
    public ImageButton btn_back;
    public Context context;
    public NoConnectionDlgFragment dlgFragment;
    public EditText et_name;
    public EditText et_password;
    public EditText et_url;
    public EditText et_username;
    public SuccessAddedListener listener;
    public PreferenceHelper sharedPreferenceHelper;
    public TextView str_list_name;
    public TextView str_mac_address;
    public TextView str_password;
    public TextView str_url;
    public TextView str_username;
    public TextView txt_mac_address;
    public TextView txt_name;
    public WordModels wordModels = new WordModels();
    public List<AppInfoModel.UrlModel> urlModelList = new ArrayList();
    public int playlist_position = -1;
    public String playlistName = "";
    public String playlistType = "general";
    public String username = "";
    public String playlistID = "";
    public String playlistUrl = "";
    public boolean is_xc = false;

    public interface SuccessAddedListener {
        void onReload(int i);

        void onSkip();
    }

    private void addPlaylist(String str) {
        this.playlistType = "general";
        this.username = "";
        if (this.et_name.getText().toString().isEmpty()) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Playlist");
            sbM.append(this.urlModelList.size() + 1);
            String string = sbM.toString();
            this.playlistName = string;
            this.et_name.setText(string);
        }
        this.playlistName = this.et_name.getText().toString();
        if (this.et_url.getText().toString().isEmpty()) {
            Toast.makeText(this.context, this.wordModels.getPut_m3u_link(), 0).show();
            return;
        }
        this.playlistUrl = this.et_url.getText().toString().trim();
        if (this.is_xc) {
            this.playlistType = "general";
            if (this.et_username.getText().toString().isEmpty()) {
                Toast.makeText(this.context, this.wordModels.getPut_username(), 0).show();
                return;
            } else if (this.et_password.getText().toString().isEmpty()) {
                Toast.makeText(this.context, this.wordModels.getPut_password(), 0).show();
                return;
            } else {
                this.username = this.et_username.getText().toString().trim();
                this.playlistUrl = GetSharedInfo.getPlaylistUrl(this.playlistUrl, this.username, this.et_password.getText().toString().trim()).trim();
            }
        }
        String addData = Security.getAddData(this.sharedPreferenceHelper.getSharedPreferenceMacAddress().toLowerCase(), str, this.playlistName, this.playlistUrl, this.playlistType);
        GetDataRequest getDataRequest = new GetDataRequest(getContext(), 1000);
        getDataRequest.getResponse(Security.getJsonData(addData), Constants.second_create_url);
        getDataRequest.setOnGetResponseListener(this);
    }

    private void changeXCFormat() {
        if (!this.is_xc) {
            this.str_url.setText(this.wordModels.getEnter_m3u());
            this.str_username.setVisibility(8);
            this.str_password.setVisibility(8);
            this.et_username.setVisibility(8);
            this.et_password.setVisibility(8);
            this.et_name.setNextFocusDownId(R.id.btn_add);
            this.et_url.setNextFocusDownId(R.id.btn_add);
            this.btn_add.setNextFocusUpId(R.id.et_name);
            this.btn_add_m3u.requestFocus();
            return;
        }
        this.str_url.setText(this.wordModels.getPort());
        this.str_username.setText(this.wordModels.getUsername());
        this.str_password.setText(this.wordModels.getPassword());
        this.str_username.setVisibility(0);
        this.str_password.setVisibility(0);
        this.et_username.setVisibility(0);
        this.et_password.setVisibility(0);
        this.et_name.setNextFocusDownId(R.id.et_username);
        this.et_url.setNextFocusDownId(R.id.et_password);
        this.btn_add.setNextFocusUpId(R.id.et_username);
        this.btn_add_xc.requestFocus();
    }

    private void initView(View view) {
        this.btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        this.btn_add_xc = (Button) view.findViewById(R.id.txt_add_xc);
        this.btn_add_m3u = (Button) view.findViewById(R.id.txt_add_m3u);
        this.txt_mac_address = (TextView) view.findViewById(R.id.txt_mac_address);
        this.str_mac_address = (TextView) view.findViewById(R.id.str_mac_address);
        this.str_list_name = (TextView) view.findViewById(R.id.str_list_name);
        this.str_url = (TextView) view.findViewById(R.id.str_url);
        this.str_username = (TextView) view.findViewById(R.id.str_username);
        this.str_password = (TextView) view.findViewById(R.id.str_password);
        this.et_name = (EditText) view.findViewById(R.id.et_name);
        this.et_url = (EditText) view.findViewById(R.id.et_url);
        this.et_username = (EditText) view.findViewById(R.id.et_username);
        this.et_password = (EditText) view.findViewById(R.id.et_password);
        this.btn_add = (Button) view.findViewById(R.id.btn_add);
        this.btn_back.setOnClickListener(this);
        this.btn_add_xc.setOnClickListener(this);
        this.btn_add_m3u.setOnClickListener(this);
        this.btn_add.setOnClickListener(this);
        this.txt_name.setText(this.wordModels.getAdd_playlist());
        this.btn_add_xc.setText(this.wordModels.getXtreme_codes());
        this.btn_add_m3u.setText(this.wordModels.getAdd_m3u());
        this.btn_add.setText(this.wordModels.getAdd_playlist());
        this.txt_mac_address.setText(this.sharedPreferenceHelper.getSharedPreferenceMacAddress());
        this.str_mac_address.setText(this.wordModels.getMac_address());
        this.str_list_name.setText(this.wordModels.getPlaylist_name());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ boolean lambda$onCreateView$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 0) {
            switch (keyEvent.getKeyCode()) {
                case 19:
                    if (this.btn_add_m3u.hasFocus() || this.btn_add_xc.hasFocus()) {
                        this.btn_back.setFocusable(true);
                        this.btn_back.requestFocus();
                        return true;
                    }
                    break;
                case 20:
                    if (this.btn_back.hasFocus()) {
                        this.btn_back.setFocusable(false);
                        if (this.is_xc) {
                            this.btn_add_xc.requestFocus();
                        } else {
                            this.btn_add_m3u.requestFocus();
                        }
                        return true;
                    }
                    break;
                case 21:
                    if (this.et_name.hasFocus() || this.et_username.hasFocus() || this.btn_add.hasFocus() || this.btn_add_m3u.hasFocus()) {
                        return true;
                    }
                    break;
                case 22:
                    if (this.et_url.hasFocus() || this.et_password.hasFocus() || this.btn_add.hasFocus() || this.btn_add_xc.hasFocus()) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNoConnectionDlgFragment$1() {
        addPlaylist(this.playlistID);
    }

    public static AddPlaylistDlgFragment newInstance(Context context, int i, SuccessAddedListener successAddedListener) {
        AddPlaylistDlgFragment addPlaylistDlgFragment = new AddPlaylistDlgFragment();
        addPlaylistDlgFragment.context = context;
        addPlaylistDlgFragment.listener = successAddedListener;
        addPlaylistDlgFragment.playlist_position = i;
        return addPlaylistDlgFragment;
    }

    private void showNoConnectionDlgFragment() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = childFragmentManager.findFragmentByTag("no_connection");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        NoConnectionDlgFragment noConnectionDlgFragmentNewInstance = NoConnectionDlgFragment.newInstance(getContext(), this.wordModels.getCheck_internet());
        this.dlgFragment = noConnectionDlgFragmentNewInstance;
        noConnectionDlgFragmentNewInstance.setOnRetryClickListener(new MovieActivity$$ExternalSyntheticLambda2(this, 10));
        this.dlgFragment.show(childFragmentManager, "no_connection");
    }

    private void showSuccessDlg() {
        new SuccessDlg(getContext(), this.wordModels.getStr_playlist_uploaded_successfully(), this.wordModels.getOk(), this.wordModels.getCancel(), new SuccessDlg.OkButtonClickListener() { // from class: com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment.1
            @Override // com.nettv.livestore.dlg.SuccessDlg.OkButtonClickListener
            public void onCancelClick() {
                AddPlaylistDlgFragment.this.listener.onSkip();
            }

            @Override // com.nettv.livestore.dlg.SuccessDlg.OkButtonClickListener
            public void onOkClick() {
                AddPlaylistDlgFragment.this.listener.onSkip();
            }
        }).show();
    }

    @Override // com.nettv.livestore.remote.GetDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
        if (jSONObject == null) {
            showNoConnectionDlgFragment();
            return;
        }
        try {
            AppInfoModel.UrlModel urlModel = new AppInfoModel.UrlModel();
            urlModel.setId(jSONObject.getString(TtmlNode.ATTR_ID));
            urlModel.setName(jSONObject.getString("name").trim());
            urlModel.setUrl(jSONObject.getString(ImagesContract.URL).trim());
            int i2 = this.playlist_position;
            if (i2 != -1) {
                this.urlModelList.set(i2, urlModel);
            } else {
                this.urlModelList.add(urlModel);
            }
            this.appInfoModel.setResult(this.urlModelList);
            this.sharedPreferenceHelper.setSharedPreferenceAppInfo(this.appInfoModel);
            Utils.saveToFile(this.appInfoModel);
            showSuccessDlg();
        } catch (Exception unused) {
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add /* 2131427460 */:
                addPlaylist(this.playlistID);
                break;
            case R.id.btn_back /* 2131427464 */:
                dismiss();
                break;
            case R.id.txt_add_m3u /* 2131428280 */:
                this.is_xc = false;
                changeXCFormat();
                break;
            case R.id.txt_add_xc /* 2131428281 */:
                this.is_xc = true;
                changeXCFormat();
                break;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_add_playlist, viewGroup, false);
        this.sharedPreferenceHelper = new PreferenceHelper(getContext());
        this.wordModels = GetSharedInfo.getWordModel(getContext());
        AppInfoModel sharedPreferenceAppInfo = this.sharedPreferenceHelper.getSharedPreferenceAppInfo();
        this.appInfoModel = sharedPreferenceAppInfo;
        if (sharedPreferenceAppInfo != null) {
            this.urlModelList = sharedPreferenceAppInfo.getResult();
        }
        initView(viewInflate);
        int i = this.playlist_position;
        if (i != -1) {
            if (this.urlModelList.get(i).getUrl().contains("username") && this.urlModelList.get(this.playlist_position).getUrl().contains("password")) {
                this.is_xc = true;
                this.et_url.setText(GetSharedInfo.getDomainFromUrl(this.urlModelList.get(this.playlist_position).getUrl()));
                this.et_username.setText(GetSharedInfo.getUsernameFromUrl(this.urlModelList.get(this.playlist_position).getUrl()));
                this.et_password.setText(GetSharedInfo.getPasswordFromUrl(this.urlModelList.get(this.playlist_position).getUrl()));
            } else {
                this.is_xc = false;
                this.et_url.setText(this.urlModelList.get(this.playlist_position).getUrl());
            }
            this.et_name.setText(this.urlModelList.get(this.playlist_position).getName());
            this.playlistID = this.urlModelList.get(this.playlist_position).getId();
            this.btn_add.setText(this.wordModels.getStr_update_playlist());
            this.txt_name.setText(this.wordModels.getStr_edit_playlist());
        }
        changeXCFormat();
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 2));
        return viewInflate;
    }
}
