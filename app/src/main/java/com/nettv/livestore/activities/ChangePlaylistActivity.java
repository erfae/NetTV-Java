package com.nettv.livestore.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.PortalRecyclerAdapter;
import com.nettv.livestore.apps.BaseActivity;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment;
import com.nettv.livestore.dlgfragment.ConnectDlgFragment;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetDataRequest;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveVerticalGridView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kotlin.Unit;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes2.dex */
public class ChangePlaylistActivity extends BaseActivity implements GetDataRequest.OnGetResponseListener {
    public AddPlaylistDlgFragment addPlaylistDlgFragment;
    public AppInfoModel appInfoModel;
    public Button btn_pay;
    public Button btn_web_site;
    public ConnectDlgFragment connectDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public ImageView image_qr;
    public NoConnectionDlgFragment noConnectionDlgFragment;
    public TextView notiContent;
    public TextView notiTitle;
    public PortalRecyclerAdapter portalAdapter;
    public PreferenceHelper preferenceHelper;
    public GifImageView progress_bar;
    public LiveVerticalGridView recycler_playlist;
    public TextView str_device_key;
    public TextView str_mac_address;
    public TextView str_mac_trial;
    public TextView str_scan_code;
    public TextView str_trial;
    public TextView str_upload;
    public TextView txt_description;
    public TextView txt_device_key;
    public TextView txt_mac_address;
    public TextView txt_version;
    public SimpleDateFormat expire_format = new SimpleDateFormat("yyyy-MM-dd");
    public long expired_mils = 0;
    public List<AppInfoModel.UrlModel> urlModelList = new ArrayList();
    public WordModels wordModels = new WordModels();
    public int playlist_position = 0;
    public int focused_position = 0;
    public AppInfoModel.UrlModel selectedModel = null;
    public boolean is_home = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void changePlaylistView() {
        AppInfoModel sharedPreferenceAppInfo = this.preferenceHelper.getSharedPreferenceAppInfo();
        this.appInfoModel = sharedPreferenceAppInfo;
        List<AppInfoModel.UrlModel> result = sharedPreferenceAppInfo.getResult();
        this.urlModelList = result;
        this.portalAdapter.setData(result);
    }

    private void checkDescription() {
        try {
            this.expired_mils = this.expire_format.parse(this.appInfoModel.getExpiredDate()).getTime();
        } catch (Exception unused) {
            this.expired_mils = 0L;
        }
        AppInfoModel appInfoModel = this.appInfoModel;
        if (appInfoModel != null) {
            if (appInfoModel.getIs_trial() == 2 || this.appInfoModel.isIs_google_pay() || this.expired_mils - new Date().getTime() > 604800000) {
                this.str_mac_trial.setText(this.wordModels.getMac_activated());
                this.btn_pay.setVisibility(4);
                this.txt_description.setText(this.wordModels.getEnjoy_tv());
                this.str_trial.setVisibility(8);
            } else if (this.expired_mils - new Date().getTime() > 0) {
                this.str_mac_trial.setText(this.wordModels.getTv_is_trial());
                this.str_trial.setText(this.wordModels.getStr_trial_description());
                this.str_trial.setVisibility(0);
            } else {
                this.str_mac_trial.setText(this.wordModels.getTv_mac_expired());
                this.str_trial.setVisibility(8);
                this.btn_pay.setVisibility(4);
            }
        }
        if (Utils.IsAmazonDevice()) {
            this.btn_pay.setVisibility(4);
        }
    }

    private void deletePlaylist(final AppInfoModel.UrlModel urlModel) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_delete");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        final ExitDlgFragment exitDlgFragmentNewInstance = ExitDlgFragment.newInstance(this.wordModels.getDelete_playlist() + "?", this.wordModels.getWant_delete_playlist(), this.wordModels.getStr_yes(), this.wordModels.getNo());
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.ChangePlaylistActivity.1
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                exitDlgFragmentNewInstance.dismiss();
                String deleteData = Security.getDeleteData(ChangePlaylistActivity.this.preferenceHelper.getSharedPreferenceMacAddress().toLowerCase(), urlModel.getId());
                GetDataRequest getDataRequest = new GetDataRequest(ChangePlaylistActivity.this, 2000);
                getDataRequest.getResponse(Security.getJsonData(deleteData), Constants.second_delete_url);
                getDataRequest.setOnGetResponseListener(ChangePlaylistActivity.this);
            }
        });
        exitDlgFragmentNewInstance.show(supportFragmentManager, "fragment_delete");
    }

    private void initView() {
        this.recycler_playlist = (LiveVerticalGridView) findViewById(R.id.recycler_playlist);
        this.txt_mac_address = (TextView) findViewById(R.id.txt_mac_address);
        this.str_mac_trial = (TextView) findViewById(R.id.str_mac_trial);
        this.str_trial = (TextView) findViewById(R.id.str_trial);
        this.str_scan_code = (TextView) findViewById(R.id.str_scan_code);
        this.str_device_key = (TextView) findViewById(R.id.str_device_key);
        this.txt_description = (TextView) findViewById(R.id.txt_description);
        this.str_mac_address = (TextView) findViewById(R.id.str_mac_address);
        this.txt_device_key = (TextView) findViewById(R.id.txt_device_key);
        this.txt_version = (TextView) findViewById(R.id.txt_version);
        this.notiTitle = (TextView) findViewById(R.id.notiTitle);
        this.notiContent = (TextView) findViewById(R.id.notiContent);
        this.str_upload = (TextView) findViewById(R.id.str_upload);
        this.btn_web_site = (Button) findViewById(R.id.btn_web_site);
        this.image_qr = (ImageView) findViewById(R.id.image_logo);
        this.progress_bar = (GifImageView) findViewById(R.id.progress_bar);
        this.btn_pay = (Button) findViewById(R.id.btn_pay);
        this.btn_web_site.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 3));
        if (GetSharedInfo.isTVDevice(this)) {
            this.recycler_playlist.setNumColumns(3);
            this.recycler_playlist.setLoop(false);
            this.recycler_playlist.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_playlist.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.ChangePlaylistActivity.3
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                    View[] viewArr2 = viewArr;
                    if (viewArr2[0] != null) {
                        viewArr2[0].setSelected(false);
                        View[] viewArr3 = viewArr;
                        viewArr3[0] = viewHolder.itemView;
                        viewArr3[0].setSelected(true);
                    }
                }
            });
            this.image_qr.setVisibility(0);
            Button button = this.btn_web_site;
            this.str_scan_code.setVisibility(0);
        } else {
            this.recycler_playlist.setLayoutManager(new GridLayoutManager(this, 3));
            this.recycler_playlist.setHasFixedSize(true);
            this.image_qr.setVisibility(8);
            Button button2 = this.btn_web_site;
            this.str_scan_code.setVisibility(8);
        }
        this.btn_web_site.setText(this.wordModels.getOpen_website());
        this.notiTitle.setText(this.appInfoModel.getNoteTitle());
        this.notiContent.setText(this.appInfoModel.getNoteContent());
        this.str_upload.setText(this.wordModels.getTo_add_manage());
        this.str_mac_address.setText(this.wordModels.getMac_address());
        this.txt_mac_address.setText(this.preferenceHelper.getSharedPreferenceMacAddress());
        this.txt_device_key.setText(this.preferenceHelper.getSharedPreferenceDeviceKey());
        this.str_scan_code.setText(this.wordModels.getScan_code());
        this.str_device_key.setText(this.wordModels.getDevice_key());
        LTVApp.instance.versionCheck();
        LTVApp.instance.loadVersion();
        TextView textView = this.txt_version;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("v");
        sbM.append(LTVApp.version_name);
        textView.setText(sbM.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(""));
            intent.addFlags(276856832);
            startActivity(intent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(AppInfoModel.UrlModel urlModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            this.focused_position = num.intValue();
            return null;
        }
        if (num.intValue() == this.urlModelList.size()) {
            showAddPlaylistDlgFragment(-1);
            return null;
        }
        this.selectedModel = urlModel;
        if (urlModel.getId().equalsIgnoreCase("0")) {
            loadingData(this.selectedModel);
            return null;
        }
        showConnectDlgFragment(this.selectedModel, num.intValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConnectDlgFragment$1(int i, AppInfoModel.UrlModel urlModel, int i2) {
        if (i2 == 0) {
            if (this.is_home && this.playlist_position == i) {
                startActivity(new Intent(this, (Class<?>) HomeActivity.class));
                finish();
                return;
            } else {
                this.playlist_position = i;
                loadingData(urlModel);
                return;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            deletePlaylist(urlModel);
        } else if (urlModel.getIs_protected().equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
            Toast.makeText(this, this.wordModels.getPlaylist_protected(), 0).show();
        } else {
            showAddPlaylistDlgFragment(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNoConnectionDlgFragment$2() {
        this.playlist_position = -1;
    }

    private void loadingData(AppInfoModel.UrlModel urlModel) {
        this.progress_bar.setVisibility(0);
        if (urlModel.getUrl().contains("username")) {
            this.preferenceHelper.setSharedPreferenceISM3U(false);
            goToLogin(urlModel.getUrl(), this.wordModels);
        } else if (GetSharedInfo.checkXUILink(urlModel.getUrl())) {
            this.preferenceHelper.setSharedPreferenceISM3U(false);
            goToXUILogin(urlModel.getUrl(), this.wordModels);
        } else {
            this.preferenceHelper.setSharedPreferenceISM3U(true);
            reloadM3UData(urlModel.getUrl(), this.wordModels);
        }
    }

    private void showAddPlaylistDlgFragment(int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_add");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        AddPlaylistDlgFragment addPlaylistDlgFragmentNewInstance = AddPlaylistDlgFragment.newInstance(this, i, new AddPlaylistDlgFragment.SuccessAddedListener() { // from class: com.nettv.livestore.activities.ChangePlaylistActivity.2
            @Override // com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment.SuccessAddedListener
            public void onReload(int i2) {
                ChangePlaylistActivity changePlaylistActivity = ChangePlaylistActivity.this;
                changePlaylistActivity.is_home = false;
                changePlaylistActivity.addPlaylistDlgFragment.dismiss();
                ChangePlaylistActivity.this.changePlaylistView();
            }

            @Override // com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment.SuccessAddedListener
            public void onSkip() {
                ChangePlaylistActivity changePlaylistActivity = ChangePlaylistActivity.this;
                changePlaylistActivity.is_home = false;
                changePlaylistActivity.addPlaylistDlgFragment.dismiss();
                ChangePlaylistActivity.this.changePlaylistView();
            }
        });
        this.addPlaylistDlgFragment = addPlaylistDlgFragmentNewInstance;
        addPlaylistDlgFragmentNewInstance.show(supportFragmentManager, "fragment_add");
    }

    private void showConnectDlgFragment(AppInfoModel.UrlModel urlModel, int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_connect");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        ConnectDlgFragment connectDlgFragmentNewInstance = ConnectDlgFragment.newInstance(this, urlModel.getName());
        this.connectDlgFragment = connectDlgFragmentNewInstance;
        connectDlgFragmentNewInstance.setSelectListener(new ChangePlaylistActivity$$ExternalSyntheticLambda0(this, i, urlModel));
        this.connectDlgFragment.show(supportFragmentManager, "fragment_connect");
    }

    private void showExitDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_exit");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        ExitDlgFragment exitDlgFragmentNewInstance = ExitDlgFragment.newInstance(this.wordModels.getExit(), this.wordModels.getExit_description(), this.wordModels.getStr_yes(), this.wordModels.getNo());
        this.exitDlgFragment = exitDlgFragmentNewInstance;
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.ChangePlaylistActivity.4
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                ChangePlaylistActivity.this.finishAffinity();
                System.exit(0);
            }
        });
        this.exitDlgFragment.show(supportFragmentManager, "fragment_exit");
    }

    private void showNoConnectionDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_no_connection");
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            fragmentTransactionBeginTransaction.addToBackStack(null);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        } else {
            NoConnectionDlgFragment noConnectionDlgFragmentNewInstance = NoConnectionDlgFragment.newInstance(this, this.wordModels.getPlaylist_is_not_working());
            this.noConnectionDlgFragment = noConnectionDlgFragmentNewInstance;
            noConnectionDlgFragmentNewInstance.setOnRetryClickListener(new MovieActivity$$ExternalSyntheticLambda2(this, 8));
            this.noConnectionDlgFragment.show(supportFragmentManager, "fragment_no_connection");
        }
    }

    @Override // com.nettv.livestore.remote.GetDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
        if (jSONObject != null) {
            if (i == 2000) {
                try {
                    AppInfoModel.UrlModel urlModel = this.selectedModel;
                    if (urlModel != null) {
                        this.urlModelList.remove(urlModel);
                        this.portalAdapter.setData(this.urlModelList);
                        this.appInfoModel.setResult(this.urlModelList);
                        this.preferenceHelper.setSharedPreferenceAppInfo(this.appInfoModel);
                        Utils.saveToFile(this.appInfoModel);
                        return;
                    }
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            try {
                if (jSONObject.getBoolean(NotificationCompat.CATEGORY_STATUS)) {
                    Toast.makeText(this, this.wordModels.getActivate_success(), 0).show();
                    this.appInfoModel.setIs_trial(2);
                    this.appInfoModel.setIs_google_pay(true);
                    this.preferenceHelper.setSharedPreferenceIsPlaylistChanged(true);
                    this.preferenceHelper.setSharedPreferenceAppInfo(this.appInfoModel);
                    this.btn_pay.setVisibility(4);
                }
            } catch (Exception unused2) {
                this.appInfoModel.setIs_trial(2);
                this.appInfoModel.setIs_google_pay(true);
                this.preferenceHelper.setSharedPreferenceIsPlaylistChanged(true);
                this.preferenceHelper.setSharedPreferenceAppInfo(this.appInfoModel);
                this.btn_pay.setVisibility(4);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 4) {
                switch (keyCode) {
                    case 19:
                    case 21:
                        if (this.btn_pay.hasFocus()) {
                            this.btn_pay.setFocusable(false);
                            this.recycler_playlist.requestFocus();
                            return true;
                        }
                        break;
                    case 20:
                        if (this.focused_position > this.urlModelList.size() - 3) {
                            this.btn_pay.setFocusable(true);
                            this.btn_pay.requestFocus();
                            return true;
                        }
                        break;
                    case 22:
                        int i = this.focused_position;
                        if (i % 3 == 2 || i == this.urlModelList.size()) {
                            this.btn_pay.setFocusable(true);
                            this.btn_pay.requestFocus();
                            return true;
                        }
                        break;
                }
            } else {
                if (!this.is_home) {
                    showExitDlgFragment();
                    return true;
                }
                startActivity(new Intent(this, (Class<?>) HomeActivity.class));
                finish();
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.nettv.livestore.apps.BaseActivity
    public final void doNextTask(boolean z) {
        if (!z) {
            this.progress_bar.setVisibility(8);
            showNoConnectionDlgFragment();
            return;
        }
        this.progress_bar.setVisibility(8);
        this.preferenceHelper.setSharedPreferenceLastPlaylistDate(System.currentTimeMillis() / 1000);
        this.preferenceHelper.setSharedPreferencePlaylistPosition(this.playlist_position);
        startActivity(new Intent(this, (Class<?>) HomeActivity.class));
        finish();
    }

    @Override // com.nettv.livestore.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.FullScreenCall(this);
        if (GetSharedInfo.isTVDevice(this)) {
            setContentView(R.layout.activity_change_playlist);
        } else {
            setContentView(R.layout.activity_change_playlist_mobile);
        }
        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        this.preferenceHelper = preferenceHelper;
        AppInfoModel sharedPreferenceAppInfo = preferenceHelper.getSharedPreferenceAppInfo();
        this.appInfoModel = sharedPreferenceAppInfo;
        if (sharedPreferenceAppInfo != null) {
            this.urlModelList = sharedPreferenceAppInfo.getResult();
            int playlistPosition = GetSharedInfo.getPlaylistPosition(this);
            this.playlist_position = playlistPosition;
            this.focused_position = playlistPosition;
        }
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.is_home = getIntent().getBooleanExtra("is_home", false);
        setStop(false);
        BaseActivity.setBusy(false);
        if (this.urlModelList == null) {
            this.urlModelList = new ArrayList();
        }
        checkDescription();
        this.portalAdapter = new PortalRecyclerAdapter(this.urlModelList, this, this.playlist_position, new LiveActivity$$ExternalSyntheticLambda3(this, 2));
        this.btn_pay.setFocusable(false);
        this.recycler_playlist.setAdapter(this.portalAdapter);
        this.recycler_playlist.requestFocus();
        this.recycler_playlist.setSelectedPosition(this.playlist_position);
    }
}
