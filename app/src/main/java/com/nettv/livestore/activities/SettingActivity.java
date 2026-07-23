package com.nettv.livestore.activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.SettingRecyclerAdapter;
import com.nettv.livestore.apps.BaseActivity;
import com.nettv.livestore.apps.BaseActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.apps.SideMenu;
import com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment;
import com.nettv.livestore.dlgfragment.ClearHistoryDlgFragment;
import com.nettv.livestore.dlgfragment.ExternalPlayerDlgFragment;
import com.nettv.livestore.dlgfragment.HideCategoryDlgFragment;
import com.nettv.livestore.dlgfragment.LanguageDlgFragment;
import com.nettv.livestore.dlgfragment.LiveSortDlgFragment;
import com.nettv.livestore.dlgfragment.NoConnectionDlgFragment;
import com.nettv.livestore.dlgfragment.ParentControlDlgFragment;
import com.nettv.livestore.dlgfragment.PayForTvDlgFragment;
import com.nettv.livestore.dlgfragment.SubtitleSettingDlgFragment;
import com.nettv.livestore.dlgfragment.UpdateDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.LanguageModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetDataRequest;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.Realm$$ExternalSyntheticLambda0;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes2.dex */
public class SettingActivity extends BaseActivity implements View.OnClickListener, GetDataRequest.OnGetResponseListener {
    public SettingRecyclerAdapter adapter;
    public AddPlaylistDlgFragment addPlaylistDlgFragment;
    public double api_version;
    public AppInfoModel appInfoModel;
    public ImageButton btn_back;
    public Button btn_pay;
    public Button btn_tv_pay;
    public double current_version;
    public HideCategoryDlgFragment hideCategoryDlgFragment;
    public NoConnectionDlgFragment noConnectionDlgFragment;
    public PreferenceHelper preferenceHelper;
    public GifImageView progress_bar;
    public LiveVerticalGridView recycler_setting;
    public ReviewInfo reviewInfo;
    public ReviewManager reviewManager;
    public List<SideMenu> settingLists;
    public TextView txt_mac_address;
    public TextView txt_setting;
    public UpdateDlgFragment updateDlgFragment;
    public WordModels wordModels = new WordModels();
    public int setting_pos = 0;
    private String tv_mac_address = "";
    public SimpleDateFormat expire_format = new SimpleDateFormat("yyyy-MM-dd");
    public long expired_mils = 0;

    public class versionUpdate extends AsyncTask<String, Integer, String> {
        public File file;
        public ProgressDialog mProgressDialog;

        public versionUpdate() {
        }

        /* JADX WARN: Code duplicated, block: B:54:0x00e2 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:55:0x00e4 A[Catch: IOException -> 0x00e0, TRY_LEAVE, TryCatch #8 {IOException -> 0x00e0, blocks: (B:52:0x00dc, B:55:0x00e4), top: B:74:0x00dc }] */
        /* JADX WARN: Code duplicated, block: B:57:0x00e9 A[PHI: r0 r15
  0x00e9: PHI (r0v7 java.lang.String) = (r0v5 java.lang.String), (r0v13 java.lang.String), (r0v14 java.lang.String) binds: [B:56:0x00e7, B:69:0x00e9, B:22:0x0092] A[DONT_GENERATE, DONT_INLINE]
  0x00e9: PHI (r15v9 java.net.HttpURLConnection) = (r15v8 java.net.HttpURLConnection), (r15v12 java.net.HttpURLConnection), (r15v12 java.net.HttpURLConnection) binds: [B:56:0x00e7, B:69:0x00e9, B:22:0x0092] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:64:0x00f7 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:65:0x00f9 A[Catch: IOException -> 0x00f5, TRY_LEAVE, TryCatch #4 {IOException -> 0x00f5, blocks: (B:62:0x00f1, B:65:0x00f9), top: B:72:0x00f1 }] */
        /* JADX WARN: Code duplicated, block: B:67:0x00fe  */
        /* JADX WARN: Code duplicated, block: B:72:0x00f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:74:0x00dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:90:? A[SYNTHETIC] */
        @Override // android.os.AsyncTask
        public final String doInBackground(String[] strArr) throws Throwable {
            Throwable th;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            FileOutputStream fileOutputStream;
            Exception e;
            FileOutputStream fileOutputStream2 = null;
            string = null;
            String string = null;
            fileOutputStream2 = null;
            fileOutputStream2 = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                try {
                    httpURLConnection.setConnectTimeout(60000);
                    httpURLConnection.connect();
                    int contentLength = httpURLConnection.getContentLength();
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        String str = (Environment.getExternalStorageDirectory() + "/") + "com.nettv.livestore.apk";
                        if (Build.VERSION.SDK_INT >= 30) {
                            this.file = new File(LTVApp.getInstance().getExternalFilesDir(null), "com.nettv.livestore.apk");
                        } else {
                            this.file = new File(str);
                        }
                        if (this.file.exists()) {
                            this.file.delete();
                        }
                        fileOutputStream = new FileOutputStream(this.file, false);
                        try {
                            byte[] bArr = new byte[4096];
                            long j = 0;
                            while (true) {
                                int i = inputStream.read(bArr);
                                try {
                                    if (i == -1) {
                                        fileOutputStream.close();
                                        break;
                                    }
                                    if (isCancelled()) {
                                        inputStream.close();
                                        fileOutputStream.close();
                                        break;
                                    }
                                    j += (long) i;
                                    if (contentLength > 0) {
                                        publishProgress(Integer.valueOf((int) ((100 * j) / ((long) contentLength))));
                                    }
                                    fileOutputStream.write(bArr, 0, i);
                                } catch (IOException unused) {
                                }
                            }
                            inputStream.close();
                        } catch (Exception e2) {
                            e = e2;
                            try {
                                e.printStackTrace();
                                string = e.toString();
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } catch (IOException unused2) {
                                        if (httpURLConnection != null) {
                                            httpURLConnection.disconnect();
                                        }
                                        return string;
                                    }
                                } else if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (httpURLConnection != null) {
                                }
                                return string;
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream2 = fileOutputStream;
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } catch (IOException unused3) {
                                        if (httpURLConnection != null) {
                                            throw th;
                                        }
                                        httpURLConnection.disconnect();
                                        throw th;
                                    }
                                } else if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (httpURLConnection != null) {
                                    throw th;
                                }
                                httpURLConnection.disconnect();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            } else if (inputStream != null) {
                                inputStream.close();
                            }
                            if (httpURLConnection != null) {
                                throw th;
                            }
                            httpURLConnection.disconnect();
                            throw th;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        fileOutputStream = null;
                        e = e;
                        e.printStackTrace();
                        string = e.toString();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } else if (inputStream != null) {
                            inputStream.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return string;
                    } catch (Throwable th4) {
                        th = th4;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } else if (inputStream != null) {
                            inputStream.close();
                        }
                        if (httpURLConnection != null) {
                            throw th;
                        }
                        httpURLConnection.disconnect();
                        throw th;
                    }
                } catch (Exception e4) {
                    e = e4;
                    inputStream = null;
                    fileOutputStream = null;
                } catch (Throwable th5) {
                    th = th5;
                    inputStream = null;
                }
            } catch (Exception e5) {
                inputStream = null;
                fileOutputStream = null;
                e = e5;
                httpURLConnection = null;
            } catch (Throwable th6) {
                th = th6;
                httpURLConnection = null;
                inputStream = null;
            }
            httpURLConnection.disconnect();
            return string;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(String str) {
            String str2 = str;
            this.mProgressDialog.dismiss();
            if (str2 != null) {
                Toast.makeText(SettingActivity.this.getApplicationContext(), SettingActivity.this.wordModels.getUpdate_failed(), 0).show();
            } else {
                SettingActivity.this.startInstall(this.file);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            ProgressDialog progressDialog = new ProgressDialog(SettingActivity.this);
            this.mProgressDialog = progressDialog;
            progressDialog.setMessage(SettingActivity.this.wordModels.getRequest_download());
            this.mProgressDialog.setIndeterminate(true);
            this.mProgressDialog.setProgressStyle(1);
            this.mProgressDialog.setCancelable(false);
            this.mProgressDialog.show();
        }

        @Override // android.os.AsyncTask
        public final void onProgressUpdate(Integer[] numArr) {
            this.mProgressDialog.setIndeterminate(false);
            this.mProgressDialog.setMax(100);
            this.mProgressDialog.setProgress(numArr[0].intValue());
        }
    }

    private void ClearHistoryChannels() {
        Iterator<String> it = RealmController.with().getRecentChannelNames().iterator();
        while (it.hasNext()) {
            RealmController.with().addToRecentChannels(it.next(), false, Realm$$ExternalSyntheticLambda0.INSTANCE$20);
        }
        Toast.makeText(this, this.wordModels.getRecent_channels_removed(), 0).show();
    }

    private void activateReviewInfo() {
        ReviewManager reviewManagerCreate = ReviewManagerFactory.create(this);
        this.reviewManager = reviewManagerCreate;
        reviewManagerCreate.requestReviewFlow().addOnCompleteListener(new SettingActivity$$ExternalSyntheticLambda0(this, 10));
    }

    private List<SideMenu> getSettingLists(WordModels wordModels) {
        this.txt_setting.setText(wordModels.getSettings());
        this.btn_pay.setText(wordModels.getPay_with_google_pay() + "(€" + this.appInfoModel.getPrice() + ")");
        ArrayList arrayList = new ArrayList();
        new SideMenu(wordModels.getAdd_playlist(), 0, R.drawable.image_playlist);
        arrayList.add(new SideMenu(wordModels.getParent_control(), 1, R.drawable.ic_lock));
        arrayList.add(new SideMenu(wordModels.getChange_playlist(), 2, R.drawable.image_playlist));
        arrayList.add(new SideMenu(wordModels.getChange_language(), 3, R.drawable.image_languange));
        arrayList.add(new SideMenu(wordModels.getChangeLayout(), 4, R.drawable.ic_widget));
        arrayList.add(new SideMenu(wordModels.getHide_live_category(), 5, R.drawable.image_hiding));
        arrayList.add(new SideMenu(wordModels.getHide_vod_category(), 6, R.drawable.image_hiding));
        arrayList.add(new SideMenu(wordModels.getHide_series_category(), 7, R.drawable.image_hiding));
        arrayList.add(new SideMenu(wordModels.getClear_history_channels(), 8, R.drawable.ic_delete));
        arrayList.add(new SideMenu(wordModels.getClear_history_movies(), 9, R.drawable.ic_delete));
        arrayList.add(new SideMenu(wordModels.getClear_history_series(), 10, R.drawable.ic_delete));
        arrayList.add(new SideMenu(wordModels.getLive_sort(), 11, R.drawable.image_sort));
        arrayList.add(new SideMenu(wordModels.getLive_stream_format(), 12, R.drawable.icon_live));
        arrayList.add(new SideMenu(wordModels.getExternal_player(), 13, R.drawable.ic_external_player));
        arrayList.add(new SideMenu(wordModels.getAutomatic(), 14, R.drawable.ic_auto));
        arrayList.add(new SideMenu(wordModels.getTime_format(), 15, R.drawable.ic_timmer));
        arrayList.add(new SideMenu(wordModels.getSubtitle_settings(), 16, R.drawable.image_subtitle));
        arrayList.add(new SideMenu(wordModels.getSelect_device_type(), 17, R.drawable.ic_devices));
        arrayList.add(new SideMenu(wordModels.getUpdate_now(), 18, R.drawable.ic_update));
        return arrayList;
    }

    private void goToChangePlaylist() {
        Intent intent = new Intent(this, (Class<?>) ChangePlaylistActivity.class);
        intent.addFlags(67108864);
        intent.putExtra("is_home", true);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToUpdate() {
        if (Utils.IsAmazonDevice()) {
            new versionUpdate().execute(this.appInfoModel.getApk_link());
            return;
        }
        String packageName = getPackageName();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void initView() {
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.txt_setting = (TextView) findViewById(R.id.txt_setting);
        this.txt_mac_address = (TextView) findViewById(R.id.txt_mac_address);
        this.btn_pay = (Button) findViewById(R.id.btn_pay);
        this.btn_tv_pay = (Button) findViewById(R.id.btn_tv_pay);
        this.progress_bar = (GifImageView) findViewById(R.id.progress_bar);
        this.btn_pay.setOnClickListener(this);
        this.btn_tv_pay.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.recycler_setting = (LiveVerticalGridView) findViewById(R.id.recycler_setting);
        if (!GetSharedInfo.isTVDevice(this)) {
            this.recycler_setting.setLayoutManager(new GridLayoutManager(this, 4));
            this.recycler_setting.setHasFixedSize(true);
            return;
        }
        this.btn_tv_pay.setVisibility(8);
        this.recycler_setting.setNumColumns(4);
        this.recycler_setting.setPreserveFocusAfterLayout(true);
        final View[] viewArr = {null};
        this.recycler_setting.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.SettingActivity.2
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ClearHistoryChannels$5() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$activateReviewInfo$1(Task task) {
        if (task.isSuccessful()) {
            this.reviewInfo = (ReviewInfo) task.getResult();
        } else {
            Toast.makeText(this, "Review failed to start!", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(SideMenu sideMenu, Integer num, Boolean bool) {
        this.setting_pos = num.intValue();
        if (!bool.booleanValue()) {
            return null;
        }
        switch (sideMenu.getPosition()) {
            case 0:
                showAddPlaylistDlgFragment();
                break;
            case 1:
                showParentalControlDlgFragment();
                break;
            case 2:
                goToChangePlaylist();
                break;
            case 3:
                showChangeLangDlgFragment();
                break;
            case 4:
                showChangeLayoutDlgFragment();
                break;
            case 5:
                showHideCategoryDlgFragment(0);
                break;
            case 6:
                showHideCategoryDlgFragment(1);
                break;
            case 7:
                showHideCategoryDlgFragment(2);
                break;
            case 8:
                if (RealmController.with().getRecentChannelNames().size() <= 0) {
                    Toast.makeText(this, this.wordModels.getNo_recently_channels(), 0).show();
                } else {
                    ClearHistoryChannels();
                }
                break;
            case 9:
                if (this.preferenceHelper.getSharedPreferenceResumeModel().size() <= 0) {
                    Toast.makeText(this, this.wordModels.getNo_recently_movies(), 0).show();
                } else {
                    showClearHistoryDlgFragment(1);
                }
                break;
            case 10:
                if (this.preferenceHelper.getSharedPreferenceRecentSeriesNames().size() <= 0) {
                    Toast.makeText(this, this.wordModels.getNo_recently_series(), 0).show();
                } else {
                    showClearHistoryDlgFragment(2);
                }
                break;
            case 11:
                showLiveChannelSortDlgFragment();
                break;
            case 12:
                showLiveStreamFormatDlgFragment();
                break;
            case 13:
                showExternalDlgFragment();
                break;
            case 14:
                showAutomationDlgFragment();
                break;
            case 15:
                showChangeTimeFormatDlgFragment();
                break;
            case 16:
                showSubTitleSettingDlgFragment();
                break;
            case 17:
                showDeviceTypeDlgFragment();
                break;
            case 18:
                showUpdateDlgFragment();
                break;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAutomationDlgFragment$11(int i) {
        this.preferenceHelper.setSharedPreferenceUpdatePeriod(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChangeLangDlgFragment$2(List list, int i) {
        this.preferenceHelper.setSharedPreferenceLanguageCode(((LanguageModel) list.get(i)).getCode());
        WordModels wordModel = GetSharedInfo.getWordModel(this);
        this.wordModels = wordModel;
        List<SideMenu> settingLists = getSettingLists(wordModel);
        this.settingLists = settingLists;
        this.adapter.setSettingData(settingLists);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChangeLayoutDlgFragment$3(int i) {
        this.preferenceHelper.setSharedPreferenceIsGrid(i != 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChangeTimeFormatDlgFragment$6(int i) {
        this.preferenceHelper.setSharedPreferenceTimeFormat(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeviceTypeDlgFragment$9(int i) {
        if (i == 0) {
            this.preferenceHelper.setSharedPreferenceDeviceType("tv");
        } else {
            this.preferenceHelper.setSharedPreferenceDeviceType("mobile");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExternalDlgFragment$10(int i) {
        this.preferenceHelper.setSharedPreferenceExternalPlayer(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showHideCategoryDlgFragment$4() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLiveChannelSortDlgFragment$7(int i) {
        this.preferenceHelper.setSharedPreferenceLiveOrder(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLiveStreamFormatDlgFragment$8(int i) {
        this.preferenceHelper.setSharedPreferenceLiveStreamFormat(i == 0 ? "ts" : "m3u8");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNoConnectionDlgFragment$13() {
        Intent intent = new Intent(this, (Class<?>) ChangePlaylistActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startReviewFlow$12(Task task) {
        Toast.makeText(this, "Rating is completed.", 0).show();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.nettv.livestore")));
    }

    private void showAddPlaylistDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_add");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        AddPlaylistDlgFragment addPlaylistDlgFragmentNewInstance = AddPlaylistDlgFragment.newInstance(this, -1, new AddPlaylistDlgFragment.SuccessAddedListener() { // from class: com.nettv.livestore.activities.SettingActivity.1
            @Override // com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment.SuccessAddedListener
            public void onReload(int i) {
                SettingActivity.this.addPlaylistDlgFragment.dismiss();
            }

            @Override // com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment.SuccessAddedListener
            public void onSkip() {
                SettingActivity.this.addPlaylistDlgFragment.dismiss();
            }
        });
        this.addPlaylistDlgFragment = addPlaylistDlgFragmentNewInstance;
        addPlaylistDlgFragmentNewInstance.show(supportFragmentManager, "fragment_add");
    }

    private void showAutomationDlgFragment() {
        int sharedPreferenceUpdatePeriod = this.preferenceHelper.getSharedPreferenceUpdatePeriod();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.wordModels.getAuto_update_everytime());
        arrayList.add(this.wordModels.getAuto_update_everyday());
        arrayList.add(this.wordModels.getAuto_update_2day());
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_automation");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            ExternalPlayerDlgFragment.newInstance(this, arrayList, this.wordModels.getAutomatic(), sharedPreferenceUpdatePeriod, new SettingActivity$$ExternalSyntheticLambda0(this, 8)).show(supportFragmentManager, "fragment_automation");
        }
    }

    private void showChangeLangDlgFragment() {
        int languagePosition = GetSharedInfo.getLanguagePosition(this);
        List<LanguageModel> languageModels = this.appInfoModel.getLanguageModels();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_language");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            LanguageDlgFragment.newInstance(this, languageModels, languagePosition, new BaseActivity$$ExternalSyntheticLambda0(this, languageModels, 14)).show(supportFragmentManager, "fragment_language");
        }
    }

    private void showChangeLayoutDlgFragment() {
        boolean sharedPreferenceIsGrid = this.preferenceHelper.getSharedPreferenceIsGrid();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.wordModels.getList_layout());
        arrayList.add(this.wordModels.getGrid_layout());
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_layout");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LiveSortDlgFragment.newInstance(this, arrayList, sharedPreferenceIsGrid ? 1 : 0, this.wordModels.getChangeLayout(), new SettingActivity$$ExternalSyntheticLambda0(this, 6)).show(supportFragmentManager, "fragment_layout");
    }

    private void showChangeTimeFormatDlgFragment() {
        int sharedPreferenceTimeFormat = this.preferenceHelper.getSharedPreferenceTimeFormat();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.wordModels.get_12_hour_format());
        arrayList.add(this.wordModels.get_24_hour_format());
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_live_sort");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            LiveSortDlgFragment.newInstance(this, arrayList, sharedPreferenceTimeFormat, this.wordModels.getTime_format(), new SettingActivity$$ExternalSyntheticLambda0(this, 2)).show(supportFragmentManager, "fragment_live_sort");
        }
    }

    private void showClearHistoryDlgFragment(int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_clear_history");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            ClearHistoryDlgFragment.newInstance(this, i).show(supportFragmentManager, "fragment_clear_history");
        }
    }

    private void showDeviceTypeDlgFragment() {
        int i = !GetSharedInfo.isTVDevice(this) ? 1 : 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add("TV");
        arrayList.add("Mobile");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_device_type");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            LiveSortDlgFragment.newInstance(this, arrayList, i, "Select Device Type", new SettingActivity$$ExternalSyntheticLambda0(this, 7)).show(supportFragmentManager, "fragment_device_type");
        }
    }

    private void showExternalDlgFragment() {
        int sharedPreferenceExternalPlayer = this.preferenceHelper.getSharedPreferenceExternalPlayer();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.wordModels.getString_default());
        arrayList.add(this.wordModels.getVlc_player());
        arrayList.add(this.wordModels.getMx_player());
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_external_player");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            ExternalPlayerDlgFragment.newInstance(this, arrayList, this.wordModels.getExternal_player(), sharedPreferenceExternalPlayer, new SettingActivity$$ExternalSyntheticLambda0(this, 1)).show(supportFragmentManager, "fragment_external_player");
        }
    }

    private void showHideCategoryDlgFragment(int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_hide_category");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        HideCategoryDlgFragment hideCategoryDlgFragmentNewInstance = HideCategoryDlgFragment.newInstance(this, i);
        this.hideCategoryDlgFragment = hideCategoryDlgFragmentNewInstance;
        hideCategoryDlgFragmentNewInstance.setOnCategoryChangedListener(Realm$$ExternalSyntheticLambda0.INSTANCE$19);
        this.hideCategoryDlgFragment.show(supportFragmentManager, "fragment_hide_category");
    }

    private void showLiveChannelSortDlgFragment() {
        int sharedPreferenceLiveOrder = this.preferenceHelper.getSharedPreferenceLiveOrder();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.wordModels.getString_default());
        arrayList.add(this.wordModels.getSort_a_z());
        arrayList.add(this.wordModels.getSort_z_a());
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_live_sort");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            LiveSortDlgFragment.newInstance(this, arrayList, sharedPreferenceLiveOrder, this.wordModels.getLive_sort(), new SettingActivity$$ExternalSyntheticLambda0(this, 9)).show(supportFragmentManager, "fragment_live_sort");
        }
    }

    private void showLiveStreamFormatDlgFragment() {
        int i = !this.preferenceHelper.getSharedPreferenceLiveStreamFormat().equalsIgnoreCase("ts") ? 1 : 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add("MPEG(ts)");
        arrayList.add("HLS(m3u8)");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_live_format");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            LiveSortDlgFragment.newInstance(this, arrayList, i, this.wordModels.getLive_stream_format(), new SettingActivity$$ExternalSyntheticLambda0(this, 5)).show(supportFragmentManager, "fragment_live_format");
        }
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
        noConnectionDlgFragmentNewInstance.setOnRetryClickListener(new SettingActivity$$ExternalSyntheticLambda0(this, 0));
        this.noConnectionDlgFragment.show(supportFragmentManager, "fragment_no_connection");
    }

    private void showParentalControlDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_parent");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            new ParentControlDlgFragment().show(supportFragmentManager, "fragment_parent");
        }
    }

    private void showSubTitleSettingDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_subtitle_setting");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            SubtitleSettingDlgFragment.newInstance(this).show(supportFragmentManager, "fragment_subtitle_setting");
        }
    }

    private void showTVDeviceDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_pay_tv");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        PayForTvDlgFragment payForTvDlgFragment = new PayForTvDlgFragment();
        payForTvDlgFragment.setOnButtonClickListener(new PayForTvDlgFragment.OnButtonClickListener() { // from class: com.nettv.livestore.activities.SettingActivity.3
            @Override // com.nettv.livestore.dlgfragment.PayForTvDlgFragment.OnButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.PayForTvDlgFragment.OnButtonClickListener
            public void onOkClick(String str) {
                SettingActivity.this.tv_mac_address = str;
            }
        });
        payForTvDlgFragment.show(supportFragmentManager, "fragment_pay_tv");
    }

    private void showUpdateDlgFragment() {
        String app_is_up_to_date;
        String ok;
        String cancel;
        boolean z;
        LTVApp.instance.versionCheck();
        LTVApp.instance.loadVersion();
        if (this.api_version > this.current_version) {
            z = true;
            app_is_up_to_date = this.wordModels.getNew_software_update_available();
            ok = this.wordModels.getUpdate_now();
            cancel = this.wordModels.getSkip();
        } else {
            app_is_up_to_date = this.wordModels.getApp_is_up_to_date();
            ok = this.wordModels.getOk();
            cancel = this.wordModels.getCancel();
            z = false;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_update");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        UpdateDlgFragment updateDlgFragmentNewInstance = UpdateDlgFragment.newInstance(this, app_is_up_to_date, ok, cancel, z);
        this.updateDlgFragment = updateDlgFragmentNewInstance;
        updateDlgFragmentNewInstance.setOnUpdateAvailableListener(new SettingActivity$$ExternalSyntheticLambda0(this, 3));
        this.updateDlgFragment.show(supportFragmentManager, "fragment_update");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInstall(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT > 24) {
            intent.setDataAndType(FileProvider.getUriForFile(this, "com.nettv.livestore.provider", file), "application/vnd.android.package-archive");
            intent.setFlags(268435456);
            intent.addFlags(1);
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(268435456);
        }
        startActivity(intent);
    }

    private void startReviewFlow() {
        ReviewInfo reviewInfo = this.reviewInfo;
        if (reviewInfo != null) {
            this.reviewManager.launchReviewFlow(this, reviewInfo).addOnCompleteListener(new SettingActivity$$ExternalSyntheticLambda0(this, 4));
        }
    }

    @Override // com.nettv.livestore.remote.GetDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
        if (jSONObject != null) {
            if (i != 2000) {
                if (jSONObject.has("data")) {
                    try {
                        new JSONObject(Security.getDecodedString(jSONObject.getString("data")));
                        jSONObject.getBoolean(NotificationCompat.CATEGORY_STATUS);
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                }
                return;
            }
            try {
                if (jSONObject.getBoolean(NotificationCompat.CATEGORY_STATUS)) {
                    Toast.makeText(this, this.wordModels.getActivate_success(), 0).show();
                    this.appInfoModel.setIs_trial(2);
                    this.appInfoModel.setIs_google_pay(true);
                    this.preferenceHelper.setSharedPreferenceIsPlaylistChanged(true);
                    this.preferenceHelper.setSharedPreferenceAppInfo(this.appInfoModel);
                    this.btn_pay.setVisibility(8);
                    if (GetSharedInfo.isTVDevice(this)) {
                        this.btn_tv_pay.setVisibility(8);
                    }
                }
            } catch (Exception unused2) {
                this.appInfoModel.setIs_trial(2);
                this.appInfoModel.setIs_google_pay(true);
                this.preferenceHelper.setSharedPreferenceIsPlaylistChanged(true);
                this.preferenceHelper.setSharedPreferenceAppInfo(this.appInfoModel);
                this.btn_pay.setVisibility(8);
                if (GetSharedInfo.isTVDevice(this)) {
                    this.btn_tv_pay.setVisibility(8);
                }
            }
            Utils.saveToFile(this.appInfoModel);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 4) {
                switch (keyCode) {
                    case 19:
                        if (this.setting_pos < 3) {
                            this.btn_back.setFocusable(true);
                            this.btn_back.requestFocus();
                            return true;
                        }
                        break;
                    case 20:
                        if (this.btn_back.hasFocus()) {
                            this.btn_back.setFocusable(false);
                            this.recycler_setting.requestFocus();
                            return true;
                        }
                        if (this.setting_pos > this.settingLists.size() - 4) {
                            this.btn_tv_pay.setFocusable(true);
                            this.btn_pay.setFocusable(true);
                            this.btn_pay.requestFocus();
                            return true;
                        }
                        break;
                    case 21:
                        if (this.recycler_setting.hasFocus() && this.setting_pos % 4 == 0) {
                            return true;
                        }
                        break;
                    case 22:
                        if (this.btn_back.hasFocus()) {
                            return true;
                        }
                        break;
                }
            } else if (this.progress_bar.getVisibility() == 0) {
                Toast.makeText(this, this.wordModels.getPlaylist_is_loading(), 0).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("is_changed", "");
                setResult(-1, intent);
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
        } else {
            this.progress_bar.setVisibility(8);
            this.preferenceHelper.setSharedPreferencePlaylistPosition(0);
            startActivity(new Intent(this, (Class<?>) HomeActivity.class));
            finish();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btn_back) {
            if (id != R.id.btn_tv_pay) {
                return;
            }
            showTVDeviceDlgFragment();
        } else {
            if (this.progress_bar.getVisibility() == 0) {
                Toast.makeText(this, this.wordModels.getPlaylist_is_loading(), 0).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("is_changed", "");
            setResult(-1, intent);
            finish();
        }
    }

    @Override // com.nettv.livestore.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting);
        Utils.FullScreenCall(this);
        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        this.preferenceHelper = preferenceHelper;
        this.appInfoModel = preferenceHelper.getSharedPreferenceAppInfo();
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        TextView textView = this.txt_mac_address;
        String str = this.wordModels.getMac_address() + ": " + this.preferenceHelper.getSharedPreferenceMacAddress() + "\n" + this.wordModels.getDevice_key() + ": " + this.preferenceHelper.getSharedPreferenceDeviceKey();
        this.settingLists = getSettingLists(this.wordModels);
        this.current_version = Double.parseDouble(LTVApp.version_name);
        double d = Double.parseDouble(this.appInfoModel.getApp_version());
        this.api_version = d;
        SettingRecyclerAdapter settingRecyclerAdapter = new SettingRecyclerAdapter(this, this.settingLists, d > this.current_version ? 16 : -1, new LiveActivity$$ExternalSyntheticLambda3(this, 7));
        this.adapter = settingRecyclerAdapter;
        this.recycler_setting.setAdapter(settingRecyclerAdapter);
        this.recycler_setting.setSelectedPosition(0);
        this.btn_back.setFocusable(false);
        this.btn_pay.setFocusable(false);
        this.btn_tv_pay.setFocusable(false);
        this.btn_tv_pay.setVisibility(8);
        this.btn_pay.setVisibility(8);
        try {
            this.expired_mils = this.expire_format.parse(this.appInfoModel.getExpiredDate()).getTime();
        } catch (Exception unused) {
            this.expired_mils = 0L;
        }
        if (this.appInfoModel.getIs_trial() == 2 || this.appInfoModel.isIs_google_pay() || this.expired_mils - new Date().getTime() > 604800000) {
            this.btn_pay.setVisibility(8);
        }
    }
}
