package com.nettv.livestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.mobile.LiveMobileActivity;
import com.nettv.livestore.apps.BaseActivity;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.HomeType;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.AccountDlgFragment;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.LoginModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetDataRequest;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes2.dex */
public class HomeActivity extends BaseActivity implements View.OnClickListener, GetDataRequest.OnGetResponseListener {
    public AccountDlgFragment accountDlgFragment;
    public AppInfoModel appInfoModel;
    public ExitDlgFragment exitDlgFragment;
    public ConstraintLayout ly_account;
    public ConstraintLayout ly_ads;
    public ConstraintLayout ly_change;
    public ConstraintLayout ly_exit;
    public ConstraintLayout ly_live;
    public ConstraintLayout ly_movie;
    public ConstraintLayout ly_reload;
    public ConstraintLayout ly_series;
    public ConstraintLayout ly_setting;
    public NoConnectionDlgFragment noConnectionDlgFragment;
    public PreferenceHelper preferenceHelper;
    public GifImageView progressBar;
    public TextView txt_account;
    public TextView txt_change;
    public TextView txt_exit;
    public TextView txt_live;
    public TextView txt_movie;
    public TextView txt_reload;
    public TextView txt_series;
    public TextView txt_setting;
    public TextView txt_time;
    public TextView txt_version;
    public WordModels wordModels = new WordModels();
    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new HomeActivity$$ExternalSyntheticLambda0(this));

    private void changeStringsInApp() {
        WordModels wordModel = GetSharedInfo.getWordModel(this);
        this.wordModels = wordModel;
        this.txt_live.setText(wordModel.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.txt_account.setText(this.wordModels.getAccount());
        this.txt_change.setText(this.wordModels.getChange_playlist());
        this.txt_setting.setText(this.wordModels.getSettings());
        this.txt_reload.setText(this.wordModels.getReload_portal());
        this.txt_exit.setText(this.wordModels.getExit());
    }

    private String getCurrentPlaylistExpiredDate() {
        LoginModel sharedPreferenceLoginModel;
        return (this.preferenceHelper.getSharedPreferenceISM3U() || (sharedPreferenceLoginModel = this.preferenceHelper.getSharedPreferenceLoginModel()) == null) ? "Undefined." : Utils.getDate(sharedPreferenceLoginModel.getExp_date());
    }

    private void initView() {
        this.txt_time = (TextView) findViewById(R.id.txt_time);
        this.ly_live = (ConstraintLayout) findViewById(R.id.ly_live);
        this.ly_movie = (ConstraintLayout) findViewById(R.id.ly_movie);
        this.ly_series = (ConstraintLayout) findViewById(R.id.ly_series);
        this.ly_account = (ConstraintLayout) findViewById(R.id.ly_account);
        this.ly_change = (ConstraintLayout) findViewById(R.id.ly_change);
        this.ly_setting = (ConstraintLayout) findViewById(R.id.ly_setting);
        this.ly_ads = (ConstraintLayout) findViewById(R.id.ly_reload);
        this.ly_reload = (ConstraintLayout) findViewById(R.id.ly_ads);
        this.ly_exit = (ConstraintLayout) findViewById(R.id.ly_exit);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.txt_account = (TextView) findViewById(R.id.txt_account);
        this.txt_change = (TextView) findViewById(R.id.txt_change);
        this.txt_setting = (TextView) findViewById(R.id.txt_setting);
        this.txt_reload = (TextView) findViewById(R.id.txt_reload);
        this.txt_exit = (TextView) findViewById(R.id.txt_exit);
        this.txt_version = (TextView) findViewById(R.id.txt_version);
        this.progressBar = (GifImageView) findViewById(R.id.progress_bar);
        this.ly_live.setOnClickListener(this);
        this.ly_movie.setOnClickListener(this);
        this.ly_series.setOnClickListener(this);
        this.ly_account.setOnClickListener(this);
        this.ly_change.setOnClickListener(this);
        this.ly_setting.setOnClickListener(this);
        this.ly_reload.setOnClickListener(this);
        this.ly_exit.setOnClickListener(this);
        this.ly_ads.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ActivityResult activityResult) {
        if (activityResult.getResultCode() != -1 || activityResult.getData() == null) {
            return;
        }
        changeStringsInApp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAccountDlgFragment$1() {
        this.accountDlgFragment.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNoConnectionDlgFragment$2() {
        Intent intent = new Intent(this, (Class<?>) ChangePlaylistActivity.class);
        intent.putExtra("is_home", false);
        startActivity(intent);
        finish();
    }

    private void reloadPortal() {
        this.progressBar.setVisibility(0);
        int playlistPosition = GetSharedInfo.getPlaylistPosition(this);
        AppInfoModel sharedPreferenceAppInfo = this.preferenceHelper.getSharedPreferenceAppInfo();
        if (sharedPreferenceAppInfo.getResult().size() > 0) {
            AppInfoModel.UrlModel urlModel = sharedPreferenceAppInfo.getResult().get(playlistPosition);
            this.preferenceHelper.setSharedPreferenceLastPlaylistDate(0L);
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
    }

    private void showAccountDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_account");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        AccountDlgFragment accountDlgFragmentNewInstance = AccountDlgFragment.newInstance(this);
        this.accountDlgFragment = accountDlgFragmentNewInstance;
        accountDlgFragmentNewInstance.setOnPayButtonClickListener(new HomeActivity$$ExternalSyntheticLambda0(this));
        this.accountDlgFragment.show(supportFragmentManager, "fragment_account");
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.HomeActivity.1
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                HomeActivity.this.finishAffinity();
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
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        NoConnectionDlgFragment noConnectionDlgFragmentNewInstance = NoConnectionDlgFragment.newInstance(this, this.wordModels.getPlaylist_is_not_working());
        this.noConnectionDlgFragment = noConnectionDlgFragmentNewInstance;
        noConnectionDlgFragmentNewInstance.setOnRetryClickListener(new HomeActivity$$ExternalSyntheticLambda0(this));
        this.noConnectionDlgFragment.show(supportFragmentManager, "fragment_no_connection");
    }

    private void showWaitToast() {
        Toast.makeText(this, this.wordModels.getPlaylist_is_loading(), 0).show();
    }

    private void syncParentControl() {
        String parentData = Security.getParentData(this.preferenceHelper.getSharedPreferenceMacAddress(), this.preferenceHelper.getSharedPreferenceParentPassword());
        GetDataRequest getDataRequest = new GetDataRequest(this, 3000);
        getDataRequest.getResponse(Security.getJsonData(parentData), Constants.second_update_control);
        getDataRequest.setOnGetResponseListener(this);
    }

    @Override // com.nettv.livestore.remote.GetDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        showExitDlgFragment();
        return true;
    }

    @Override // com.nettv.livestore.apps.BaseActivity
    public final void doNextTask(boolean z) {
        if (!z) {
            this.progressBar.setVisibility(8);
            showNoConnectionDlgFragment();
        } else {
            this.preferenceHelper.setSharedPreferenceLastPlaylistDate(System.currentTimeMillis() / 1000);
            this.progressBar.setVisibility(8);
            Toast.makeText(this, this.wordModels.getPortal_loaded_successfully(), 0).show();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_account /* 2131427887 */:
                startActivity(new Intent(this, (Class<?>) SportActivity.class));
                break;
            case R.id.ly_change /* 2131427897 */:
                setStop(true);
                Intent intent = new Intent(this, (Class<?>) ChangePlaylistActivity.class);
                intent.addFlags(67108864);
                intent.putExtra("is_home", true);
                startActivity(intent);
                finish();
                break;
            case R.id.ly_exit /* 2131427904 */:
                showExitDlgFragment();
                break;
            case R.id.ly_live /* 2131427909 */:
                if (this.progressBar.getVisibility() == 0) {
                    showWaitToast();
                } else if (this.preferenceHelper.getSharedPreferenceIsGrid()) {
                    LTVApp.homeType = HomeType.live;
                    startActivity(new Intent(this, (Class<?>) CategoryActivity.class));
                } else if (!GetSharedInfo.isTVDevice(this)) {
                    startActivity(new Intent(this, (Class<?>) LiveMobileActivity.class));
                } else {
                    startActivity(new Intent(this, (Class<?>) LiveActivity.class));
                }
                break;
            case R.id.ly_movie /* 2131427913 */:
                if (this.progressBar.getVisibility() == 0) {
                    showWaitToast();
                } else if (!this.preferenceHelper.getSharedPreferenceIsGrid()) {
                    startActivity(new Intent(this, (Class<?>) MovieActivity.class));
                } else {
                    LTVApp.homeType = HomeType.movies;
                    startActivity(new Intent(this, (Class<?>) CategoryActivity.class));
                }
                break;
            case R.id.ly_reload /* 2131427916 */:
                if (this.progressBar.getVisibility() != 0) {
                    reloadPortal();
                } else {
                    showWaitToast();
                }
                break;
            case R.id.ly_series /* 2131427920 */:
                if (this.progressBar.getVisibility() == 0) {
                    showWaitToast();
                } else if (!this.preferenceHelper.getSharedPreferenceIsGrid()) {
                    startActivity(new Intent(this, (Class<?>) SeriesActivity.class));
                } else {
                    LTVApp.homeType = HomeType.series;
                    startActivity(new Intent(this, (Class<?>) CategoryActivity.class));
                }
                break;
            case R.id.ly_setting /* 2131427921 */:
                if (this.progressBar.getVisibility() != 0) {
                    this.someActivityResultLauncher.launch(new Intent(this, (Class<?>) SettingActivity.class));
                } else {
                    showWaitToast();
                }
                break;
        }
    }

    @Override // com.nettv.livestore.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);
        Utils.FullScreenCall(this);
        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        this.preferenceHelper = preferenceHelper;
        this.appInfoModel = preferenceHelper.getSharedPreferenceAppInfo();
        initView();
        changeStringsInApp();
        this.txt_time.setText(this.wordModels.getCurrent_expired() + " " + getCurrentPlaylistExpiredDate());
        LTVApp.instance.versionCheck();
        LTVApp.instance.loadVersion();
        TextView textView = this.txt_version;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("v");
        sbM.append(LTVApp.version_name);
        textView.setText(sbM.toString());
        if (this.appInfoModel.getParent_synced() == 0) {
            syncParentControl();
        }
        this.ly_live.requestFocus();
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Bet3AdsActivity());
        webView.loadUrl("http://theths.co.uk/latestapps/ibo/api/main_movies.php");
        webView.setBackgroundColor(2130706432);
    }
}
