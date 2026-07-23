package com.nettv.livestore;

import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Base64;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nettv.livestore.activities.ChangePlaylistActivity;
import com.nettv.livestore.activities.HomeActivity;
import com.nettv.livestore.apps.BaseActivity;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.DescriptionDlgFragment;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetDataRequest;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes2.dex */
public class MainActivity extends BaseActivity implements GetDataRequest.OnGetResponseListener {
    public AppInfoModel appInfoModel;
    public AppInfoModel.UrlModel currentUrlModel;
    public DescriptionDlgFragment descriptionDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public GifImageView image_loader;
    public PreferenceHelper preferenceHelper;
    public String subscription = "";
    public String description = "";
    public WordModels wordModels = new WordModels();
    public int failed_count = 0;
    public int playlist_position = 0;

    @RequiresApi(api = 23)
    private void CheckSDK23Permission() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!addPermission(arrayList2, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            arrayList.add("READ / WRITE SD CARD");
        }
        if (arrayList2.size() > 0) {
            requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 124);
        } else {
            getMacAddress();
        }
    }

    @RequiresApi(api = 23)
    private boolean addPermission(List<String> list, String str) {
        if (ContextCompat.checkSelfPermission(this, str) == 0) {
            return true;
        }
        list.add(str);
        return shouldShowRequestPermissionRationale(str);
    }

    private void checkAppInfoModel(AppInfoModel appInfoModel) {
        long time;
        this.wordModels = GetSharedInfo.getWordModel(this);
        this.failed_count = 0;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd").parse(appInfoModel.getExpiredDate()).getTime();
        } catch (Exception unused) {
            time = 0;
        }
        if (time - new Date().getTime() >= 604800000 || appInfoModel.isIs_google_pay()) {
            if (appInfoModel.getResult().size() > 0) {
                loadingData();
                return;
            }
            this.subscription = "";
            String no_playlist_description = this.wordModels.getNo_playlist_description();
            this.description = no_playlist_description;
            showDescriptionDlgFragment(this.subscription, no_playlist_description, 0);
            return;
        }
        if (time - new Date().getTime() <= 0 || time - new Date().getTime() >= 604800000) {
            if (appInfoModel.getIs_trial() == 1) {
                this.subscription = this.wordModels.getTrial_ended();
            } else {
                this.subscription = this.wordModels.getTv_mac_expired();
            }
            String str = this.wordModels.getTo_continue() + " €" + appInfoModel.getPrice() + " " + this.wordModels.getVia_website();
            this.description = str;
            showDescriptionDlgFragment(this.subscription, str, -1);
            return;
        }
        String str2 = this.wordModels.getSub_remaining() + " " + ((int) ((((time - new Date().getTime()) / 1000) / 3600) / 24)) + " " + this.wordModels.getDays();
        if (appInfoModel.getResult().size() <= 0 || appInfoModel.getResult().get(0).getId().equalsIgnoreCase("0")) {
            this.description = this.wordModels.getNo_playlist_description();
        } else {
            this.description = this.wordModels.getTo_add_manage();
        }
        showDescriptionDlgFragment(str2, this.description, appInfoModel.getResult().size());
    }

    private void checkLocalStorageAccount() {
        if (Utils.ReadFile().isEmpty()) {
            AppInfoModel sharedPreferenceAppInfo = this.preferenceHelper.getSharedPreferenceAppInfo();
            this.appInfoModel = sharedPreferenceAppInfo;
            if (sharedPreferenceAppInfo != null) {
                checkAppInfoModel(sharedPreferenceAppInfo);
                return;
            }
            int i = this.failed_count + 1;
            this.failed_count = i;
            if (i < 3) {
                getUserInfoModel();
                return;
            } else {
                Toast.makeText(this, "Network Error! Please check your internet connection!", 0).show();
                return;
            }
        }
        AppInfoModel appInfoModel = (AppInfoModel) new Gson().fromJson(new String(Base64.decode(Utils.ReadFile(), 0), StandardCharsets.UTF_8).trim(), new TypeToken<AppInfoModel>() { // from class: com.nettv.livestore.MainActivity.1
        }.getType());
        this.appInfoModel = appInfoModel;
        if (appInfoModel != null) {
            this.preferenceHelper.setSharedPreferenceAppInfo(appInfoModel);
            checkAppInfoModel(this.appInfoModel);
            this.preferenceHelper.setSharedPreferenceMacAddress(this.appInfoModel.getMac_address());
        } else {
            int i2 = this.failed_count + 1;
            this.failed_count = i2;
            if (i2 < 3) {
                getUserInfoModel();
            } else {
                Toast.makeText(this, "Network Error! Please check your internet connection!", 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishApp() {
        List<ActivityManager.AppTask> appTasks;
        System.exit(0);
        Process.killProcess(Process.myPid());
        ActivityManager activityManager = (ActivityManager) getSystemService("activity");
        if (activityManager != null && (appTasks = activityManager.getAppTasks()) != null && appTasks.size() > 0) {
            appTasks.get(0).setExcludeFromRecents(true);
        }
        finishAndRemoveTask();
    }

    private void getMacAddress() {
        if (this.preferenceHelper.getSharedPreferenceLanguageCode() != null) {
            this.preferenceHelper.getSharedPreferenceLanguageCode();
        } else {
            this.preferenceHelper.setSharedPreferenceLanguageCode(Locale.getDefault().getLanguage());
        }
        if (this.preferenceHelper.getSharedPreferenceSubtitleSize() == 0) {
            if (GetSharedInfo.isTVDevice(this)) {
                this.preferenceHelper.setSharedPreferenceSubtitleSize(46);
            } else {
                this.preferenceHelper.setSharedPreferenceSubtitleSize(12);
            }
            if (Utils.IsAmazonDevice()) {
                this.preferenceHelper.setSharedPreferenceSubtitleSize(35);
            }
        }
        LTVApp.instance.versionCheck();
        LTVApp.instance.loadVersion();
        getUserInfoModel();
    }

    private void getUserInfoModel() {
        String strTrim = Security.getStringData(Utils.getDeviceId(this), LTVApp.version_name, false, this.preferenceHelper.getSharedPreferenceDeviceType()).trim();
        GetDataRequest getDataRequest = new GetDataRequest(this, 1000);
        getDataRequest.getResponse(Security.getJsonData(strTrim), Constants.second_response_url);
        getDataRequest.setOnGetResponseListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToWebsite() {
        if (GetSharedInfo.isTVDevice(this)) {
            finishApp();
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://iboapp.com/manage-playlists/login/"));
            intent.addFlags(276856832);
            startActivity(intent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadingData() {
        this.image_loader.setVisibility(0);
        this.playlist_position = GetSharedInfo.getPlaylistPosition(this);
        try {
            try {
                AppInfoModel appInfoModel = this.appInfoModel;
                if (appInfoModel == null || appInfoModel.getResult() == null || this.appInfoModel.getResult().size() <= 0 || this.appInfoModel.getResult().get(0).getId().equalsIgnoreCase("0")) {
                    startActivity(new Intent(this, (Class<?>) ChangePlaylistActivity.class));
                    finish();
                } else {
                    AppInfoModel.UrlModel urlModel = this.appInfoModel.getResult().get(this.playlist_position);
                    this.currentUrlModel = urlModel;
                    if (urlModel.getUrl().contains("username")) {
                        this.preferenceHelper.setSharedPreferenceISM3U(false);
                        goToLogin(this.currentUrlModel.getUrl(), this.wordModels);
                    } else if (GetSharedInfo.checkXUILink(this.currentUrlModel.getUrl())) {
                        this.preferenceHelper.setSharedPreferenceISM3U(false);
                        goToXUILogin(this.currentUrlModel.getUrl(), this.wordModels);
                    } else {
                        this.preferenceHelper.setSharedPreferenceISM3U(true);
                        reloadM3UData(this.currentUrlModel.getUrl(), this.wordModels);
                    }
                }
            } catch (Exception unused) {
                startActivity(new Intent(this, (Class<?>) ChangePlaylistActivity.class));
                finish();
            }
        } catch (Exception unused2) {
        }
    }

    private void showDescriptionDlgFragment(String str, String str2, final int i) {
        this.image_loader.setVisibility(8);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_description");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        DescriptionDlgFragment descriptionDlgFragmentNewInstance = DescriptionDlgFragment.newInstance(getApplicationContext(), str, str2, i);
        this.descriptionDlgFragment = descriptionDlgFragmentNewInstance;
        descriptionDlgFragmentNewInstance.setButtonClickListener(new DescriptionDlgFragment.ButtonClickListener() { // from class: com.nettv.livestore.MainActivity.2
            @Override // com.nettv.livestore.dlgfragment.DescriptionDlgFragment.ButtonClickListener
            public void onCancelClick() {
                MainActivity.this.finishApp();
            }

            @Override // com.nettv.livestore.dlgfragment.DescriptionDlgFragment.ButtonClickListener
            public void onContinueClick() {
                if (i == -1) {
                    MainActivity.this.goToWebsite();
                } else {
                    MainActivity.this.loadingData();
                }
            }
        });
        this.descriptionDlgFragment.show(supportFragmentManager, "fragment_description");
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.MainActivity.3
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                List<ActivityManager.AppTask> appTasks;
                System.exit(0);
                Process.killProcess(Process.myPid());
                ActivityManager activityManager = (ActivityManager) MainActivity.this.getSystemService("activity");
                if (activityManager != null && (appTasks = activityManager.getAppTasks()) != null && appTasks.size() > 0) {
                    appTasks.get(0).setExcludeFromRecents(true);
                }
                MainActivity.this.finishAndRemoveTask();
            }
        });
        this.exitDlgFragment.show(supportFragmentManager, "fragment_exit");
    }

    @Override // com.nettv.livestore.remote.GetDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
        if (jSONObject == null) {
            checkLocalStorageAccount();
            return;
        }
        if (i == 1000) {
            if (!jSONObject.has("data")) {
                checkLocalStorageAccount();
                return;
            }
            try {
                try {
                    AppInfoModel appInfoModel = (AppInfoModel) new Gson().fromJson(new JSONObject(Security.getDecodedString(jSONObject.getString("data"))).toString(), AppInfoModel.class);
                    this.appInfoModel = appInfoModel;
                    this.preferenceHelper.setSharedPreferenceAppInfo(appInfoModel);
                    this.preferenceHelper.setSharedPreferenceIsPlaylistChanged(false);
                    this.preferenceHelper.setSharedPreferenceFirstLunch(false);
                    this.preferenceHelper.setSharedPreferenceMacAddress(this.appInfoModel.getMac_address());
                    this.preferenceHelper.setSharedPreferenceDeviceKey(this.appInfoModel.getDevice_key());
                    if (this.appInfoModel.getParent_synced() == 1) {
                        this.preferenceHelper.setSharedPreferenceParentPassword(this.appInfoModel.getParent_control());
                    }
                    Utils.saveToFile(this.appInfoModel);
                    checkAppInfoModel(this.appInfoModel);
                } catch (Exception e) {
                    checkLocalStorageAccount();
                    e.printStackTrace();
                }
            } catch (Exception unused) {
                checkLocalStorageAccount();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (this.image_loader.getVisibility() == 0) {
            setStop(true);
            startActivity(new Intent(this, (Class<?>) ChangePlaylistActivity.class));
            finish();
        } else {
            setStop(true);
            showExitDlgFragment();
        }
        return true;
    }

    @Override // com.nettv.livestore.apps.BaseActivity
    public final void doNextTask(boolean z) {
        if (z) {
            this.preferenceHelper.setSharedPreferenceLastPlaylistDate(System.currentTimeMillis() / 1000);
            startActivity(new Intent(this, (Class<?>) HomeActivity.class));
        } else {
            this.image_loader.setVisibility(8);
            Toast.makeText(this, "" + this.wordModels.getPlaylist_is_not_working(), 0).show();
            startActivity(new Intent(this, (Class<?>) ChangePlaylistActivity.class));
        }
        finish();
    }

    @Override // com.nettv.livestore.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.image_loader = (GifImageView) findViewById(R.id.image_loader);
        ((TextView) findViewById(R.id.txt_resolution)).setText("");
        if (this.preferenceHelper.getSharedPreferenceDeviceType() == null) {
            if (Utils.checkIsTelevision(this)) {
                this.preferenceHelper.setSharedPreferenceDeviceType("tv");
            } else {
                this.preferenceHelper.setSharedPreferenceDeviceType("mobile");
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            CheckSDK23Permission();
        } else {
            getMacAddress();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        getMacAddress();
        super.onRequestPermissionsResult(i, strArr, iArr);
    }
}
