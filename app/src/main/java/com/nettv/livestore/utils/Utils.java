package com.nettv.livestore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.C;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.AppInfoModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class Utils {
    private static final String PACKAGE_NAME = "org.videolan.vlc";
    private static final String PLAYBACK_ACTIVITY = "org.videolan.vlc.gui.video.VideoPlayerActivity";
    public static String play_list = "log.txt";
    private static SimpleDateFormat getTimeZoneFormat = new SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US);
    private static final VLCPackageInfo[] PACKAGES = {new VLCPackageInfo()};
    private static final String PACKAGE_NAME_PRO = "com.mxtech.videoplayer.pro";
    private static final String PLAYBACK_ACTIVITY_PRO = "com.mxtech.videoplayer.ActivityScreen";
    private static final String PACKAGE_NAME_AD = "com.mxtech.videoplayer.ad";
    private static final String PLAYBACK_ACTIVITY_AD = "com.mxtech.videoplayer.ad.ActivityScreen";
    public static final MXPackageInfo[] PACKAGES1 = {new MXPackageInfo(PACKAGE_NAME_PRO, PLAYBACK_ACTIVITY_PRO), new MXPackageInfo(PACKAGE_NAME_AD, PLAYBACK_ACTIVITY_AD)};
    private static final String ANDROID_TV_YOUTUBE = "com.google.android.youtube.tv";
    private static final String ANDROID_TV_BOX_YOUTUBE = "com.google.android.youtube";
    private static final String AMAZON_TV_YOUTUBE = "com.amazon.firetv.youtube";
    private static final String SMART_YOUTUBE_TV = "com.liskovsoft.videomanager";
    public static final YoutubePackageInfo[] PACKAGE_INFOS = {new YoutubePackageInfo(ANDROID_TV_YOUTUBE), new YoutubePackageInfo(ANDROID_TV_BOX_YOUTUBE), new YoutubePackageInfo(AMAZON_TV_YOUTUBE), new YoutubePackageInfo(SMART_YOUTUBE_TV)};

    public static class MXPackageInfo {
        public final String activityName;
        public final String packageName;

        public MXPackageInfo(String str, String str2) {
            this.packageName = str;
            this.activityName = str2;
        }
    }

    public static class VLCPackageInfo {
        public final String packageName = Utils.PACKAGE_NAME;
    }

    public static class YoutubePackageInfo {
        public final String packageName;

        public YoutubePackageInfo(String str) {
            this.packageName = str;
        }
    }

    public static void FullScreenCall(Activity activity) {
        activity.getWindow().setFlags(1024, 1024);
        activity.getWindow().addFlags(128);
        activity.getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        if (activity.getResources().getConfiguration().getLayoutDirection() == 1) {
            Log.e("rtl", "ltr");
            Configuration configuration = activity.getResources().getConfiguration();
            configuration.setLocale(Locale.getDefault());
            configuration.setLayoutDirection(new Locale("en"));
            activity.createConfigurationContext(configuration);
        }
    }

    public static boolean IsAmazonDevice() {
        return Build.MANUFACTURER.toLowerCase().contains("amazon") || Build.MODEL.contains("AFT");
    }

    public static String Offset(long j, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return new SimpleDateFormat(GetSharedInfo.getCurrentTimeFormat(context)).format(calendar.getTime());
    }

    public static String ReadFile() {
        String string = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(LTVApp.getInstance().getExternalFilesDir(null), play_list));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    fileInputStream.close();
                    string = sb.toString();
                    bufferedReader.close();
                    return string;
                }
                sb.append(line + System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e) {
            Log.e("com.nettv.livestore.utils.Utils", e.getMessage());
            return string;
        } catch (IOException e2) {
            Log.e("com.nettv.livestore.utils.Utils", e2.getMessage());
            return string;
        }
    }

    private static boolean canHandleCameraIntent(Context context) {
        if (context.getPackageManager().queryIntentActivities(new Intent("android.media.action.IMAGE_CAPTURE"), 0).size() > 0) {
            return (isUsbAvailable(context) && isEthernetAvailable(context) && !isTouchAvailable(context)) ? false : true;
        }
        return false;
    }

    public static boolean checkIsTelevision(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        return (uiModeManager != null && uiModeManager.getCurrentModeType() == 4) || isHdmiSwitchSet() || IsAmazonDevice() || !canHandleCameraIntent(context);
    }

    public static String decode64String(String str) {
        try {
            return new String(Base64.decode(str, 0), StandardCharsets.UTF_8).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int dp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }

    public static String formatDateFromString(String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.getDefault());
        try {
            return new SimpleDateFormat(str2, Locale.getDefault()).format(simpleDateFormat.parse(str3));
        } catch (ParseException unused) {
            return "";
        }
    }

    public static String formateDateFromstring(String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.getDefault());
        try {
            return new SimpleDateFormat(str2, Locale.getDefault()).format(simpleDateFormat.parse(str3));
        } catch (ParseException unused) {
            return "";
        }
    }

    public static String getDate(Long l) {
        if (l == null) {
            return "unlimited";
        }
        try {
            if (l.longValue() == 0) {
                return "unlimited";
            }
            try {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(l.longValue() * 1000);
                return DateFormat.format("dd/MM/yyyy", calendar).toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "unlimited";
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return "unlimited";
        }
    }

    public static String getDateFromMillisecond(String str, long j) {
        return new SimpleDateFormat(str, Locale.getDefault()).format(new Date(j));
    }

    public static Date getDateFromString(String str, String str2) {
        try {
            return new SimpleDateFormat(str, Locale.getDefault()).parse(str2);
        } catch (ParseException unused) {
            return new Date();
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String getDeviceId(Context context) {
        return new String(Base64.encode(Settings.Secure.getString(context.getContentResolver(), "android_id").getBytes(StandardCharsets.UTF_8), 0)).trim();
    }

    public static String getLanguageNameFromCode(String str) {
        if (str == null || str.isEmpty()) {
            return C.LANGUAGE_UNDETERMINED;
        }
        try {
            Locale locale = new Locale(str);
            return locale.getDisplayName(locale);
        } catch (Exception unused) {
            return str;
        }
    }

    public static MXPackageInfo getMXPackageInfo(Context context) {
        for (MXPackageInfo mXPackageInfo : PACKAGES1) {
            try {
                if (context.getPackageManager().getApplicationInfo(mXPackageInfo.packageName, 0).enabled) {
                    return mXPackageInfo;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return null;
    }

    public static int getProgressPercentage(long j, long j2) {
        return (int) ((((double) ((int) (j / 1000))) / ((double) ((int) (j2 / 1000)))) * 100.0d);
    }

    public static String getSamsungMac(String str) {
        Iterable<String> iterableSplit = Splitter.fixedLength(2).split(str);
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = iterableSplit.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(":");
        }
        return method(sb.toString());
    }

    public static long getTimeInLocalMilli(String str) {
        if (str != null) {
            try {
                return getTimeZoneFormat.parse(str).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1L;
    }

    public static String getUserId(String str) {
        try {
            return str.replaceAll(":", "").replaceAll("/", "").replaceAll("\\.", "").replaceAll("\\?", "").replaceAll("=", "").replaceAll("\\&", "");
        } catch (Exception unused) {
            return "m3u";
        }
    }

    public static VLCPackageInfo getVlcPackageInfo(Context context) {
        VLCPackageInfo[] vLCPackageInfoArr = PACKAGES;
        if (vLCPackageInfoArr.length > 0) {
            VLCPackageInfo vLCPackageInfo = vLCPackageInfoArr[0];
            try {
                if (context.getPackageManager().getApplicationInfo(vLCPackageInfo.packageName, 0).enabled) {
                    return vLCPackageInfo;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return null;
    }

    public static YoutubePackageInfo getYoutubePackageInfo(Context context) {
        for (YoutubePackageInfo youtubePackageInfo : PACKAGE_INFOS) {
            try {
                if (context.getPackageManager().getApplicationInfo(youtubePackageInfo.packageName, 0).enabled) {
                    return youtubePackageInfo;
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static boolean isEthernetAvailable(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getPackageManager().hasSystemFeature("android.hardware.ethernet");
        }
        return false;
    }

    public static boolean isHdmiSwitchSet() {
        File file = new File("/sys/devices/virtual/switch/hdmi/state");
        if (!file.exists()) {
            file = new File("/sys/class/switch/hdmi/state");
        }
        try {
            Scanner scanner = new Scanner(file);
            int iNextInt = scanner.nextInt();
            scanner.close();
            return iNextInt > 0;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean isTouchAvailable(Context context) {
        return context.getResources().getConfiguration().touchscreen != 1;
    }

    private static boolean isUsbAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.usb.host");
    }

    private static String method(String str) {
        return (str == null || str.length() <= 0 || str.charAt(str.length() + (-1)) != ':') ? "" : str.substring(0, str.length() - 1);
    }

    public static String milliSecondsToTimer(long j) {
        String strM;
        if (j < 0) {
            return "00:00";
        }
        int i = (int) (j / 3600000);
        long j2 = j % 3600000;
        int i2 = ((int) j2) / 60000;
        int i3 = (int) ((j2 % 60000) / 1000);
        if (i <= 0) {
            strM = "";
        } else if (i < 10) {
            strM = Insets$$ExternalSyntheticOutline0.m("0", i, ":");
        } else {
            strM = i + ":";
        }
        return strM + (i2 < 10 ? Insets$$ExternalSyntheticOutline0.m23m("0", i2) : Insets$$ExternalSyntheticOutline0.m23m("", i2)) + ":" + (i3 < 10 ? Insets$$ExternalSyntheticOutline0.m23m("0", i3) : Insets$$ExternalSyntheticOutline0.m23m("", i3));
    }

    public static void navToLauncherTask(Context context) {
        for (ActivityManager.AppTask appTask : ((ActivityManager) context.getSystemService("activity")).getAppTasks()) {
            Set<String> categories = appTask.getTaskInfo().baseIntent.getCategories();
            if (categories != null && categories.contains("android.intent.category.LAUNCHER")) {
                appTask.moveToFront();
                return;
            }
        }
    }

    public static int progressToTimer(int i, long j) {
        return ((int) ((((double) i) / 100.0d) * ((double) ((int) (j / 1000))))) * 1000;
    }

    public static void saveToFile(AppInfoModel appInfoModel) {
        Gson gson = new Gson();
        try {
            File file = new File(LTVApp.getInstance().getExternalFilesDir(null), play_list);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            new FileOutputStream(file, true).write((new String(Base64.encode(gson.toJson(appInfoModel).getBytes(StandardCharsets.UTF_8), 0)).trim() + System.getProperty("line.separator")).getBytes());
        } catch (FileNotFoundException e) {
            Log.e("com.nettv.livestore.utils.Utils", e.getMessage());
        } catch (IOException e2) {
            Log.e("com.nettv.livestore.utils.Utils", e2.getMessage());
        }
    }

    public static String getDate(long j) {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.setTimeInMillis(j * 1000);
        return DateFormat.format("dd/MM/yyyy", calendar).toString();
    }
}
