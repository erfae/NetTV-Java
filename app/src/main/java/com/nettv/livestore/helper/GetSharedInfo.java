package com.nettv.livestore.helper;

import android.content.Context;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.SubtitleModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class GetSharedInfo {
    public static boolean checkXUILink(String str) {
        if (!str.contains("/")) {
            return false;
        }
        try {
            return str.contains("playlist") && str.split("/")[3].equalsIgnoreCase("playlist");
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getCurrentTimeFormat(Context context) {
        return new PreferenceHelper(context).getSharedPreferenceTimeFormat() == 0 ? "hh:mm a" : "HH:mm";
    }

    public static List<CategoryModel> getDefaultLiveCategory() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CategoryModel(Constants.resume_id, "Recently Viewed"));
        arrayList.add(new CategoryModel(Constants.fav_id, "Favorite"));
        return arrayList;
    }

    public static List<CategoryModel> getDefaultVodCategory() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CategoryModel(Constants.all_id, "All"));
        arrayList.add(new CategoryModel(Constants.fav_id, "Favorite"));
        return arrayList;
    }

    public static String getDomainFromUrl(String str) {
        try {
            URL url = new URL(str.replaceAll(" ", "").trim());
            return url.getProtocol() + "://" + url.getAuthority();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getDuration(String str, String str2) {
        StringBuilder sb;
        String str3;
        if (str2.isEmpty()) {
            return str;
        }
        try {
            int i = Integer.parseInt(str2) / 60;
            int i2 = i / 60;
            int i3 = i % 60;
            if (i2 < 10) {
                sb = new StringBuilder();
                sb.append("0");
                sb.append(i2);
            } else {
                sb = new StringBuilder();
                sb.append(i2);
            }
            sb.append("h");
            String string = sb.toString();
            if (i3 < 10) {
                str3 = "0" + i3 + "min";
            } else {
                str3 = i3 + "min";
            }
            return string + " " + str3;
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getEpisodeUrl(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/series/");
        sb.append(str2);
        sb.append("/");
        sb.append(str3);
        return Insets$$ExternalSyntheticOutline0.m(sb, "/", str4, ".", str5);
    }

    public static int getLanguagePosition(Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        String sharedPreferenceLanguageCode = preferenceHelper.getSharedPreferenceLanguageCode();
        AppInfoModel sharedPreferenceAppInfo = preferenceHelper.getSharedPreferenceAppInfo();
        for (int i = 0; i < sharedPreferenceAppInfo.getLanguageModels().size(); i++) {
            if (sharedPreferenceLanguageCode.equalsIgnoreCase(sharedPreferenceAppInfo.getLanguageModels().get(i).getCode())) {
                return i;
            }
        }
        for (int i2 = 0; i2 < sharedPreferenceAppInfo.getLanguageModels().size(); i2++) {
            if ("en".equalsIgnoreCase(sharedPreferenceAppInfo.getLanguageModels().get(i2).getCode())) {
                preferenceHelper.setSharedPreferenceLanguageCode("en");
                return i2;
            }
        }
        return 0;
    }

    public static String getLiveChannelUrl(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/live/");
        sb.append(str2);
        sb.append("/");
        sb.append(str3);
        return Insets$$ExternalSyntheticOutline0.m(sb, "/", str4, ".", str5);
    }

    public static String getMovieUrl(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/movie/");
        sb.append(str2);
        sb.append("/");
        sb.append(str3);
        return Insets$$ExternalSyntheticOutline0.m(sb, "/", str4, ".", str5);
    }

    public static String getPasswordFromUrl(String str) {
        try {
            return new URL(str.replaceAll(" ", "").trim()).getQuery().split("&")[1].split("=")[1];
        } catch (Exception unused) {
            return "";
        }
    }

    public static int getPlaylistPosition(Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        AppInfoModel sharedPreferenceAppInfo = preferenceHelper.getSharedPreferenceAppInfo();
        int sharedPreferencePlaylistPosition = preferenceHelper.getSharedPreferencePlaylistPosition();
        if (sharedPreferencePlaylistPosition > sharedPreferenceAppInfo.getResult().size() - 1) {
            return 0;
        }
        return sharedPreferencePlaylistPosition;
    }

    public static String getPlaylistUrl(String str, String str2, String str3) {
        return str + "/get.php?username=" + str2 + "&password=" + str3 + "&output=ts&type=m3u_plus";
    }

    public static int getSubtitleBgColorPosition(Context context, String[] strArr) {
        String sharedPreferenceSubtitleBgColor = new PreferenceHelper(context).getSharedPreferenceSubtitleBgColor();
        for (int i = 0; i < strArr.length; i++) {
            if (sharedPreferenceSubtitleBgColor.equalsIgnoreCase(strArr[i])) {
                return i;
            }
        }
        return 0;
    }

    public static int getSubtitleColorPosition(Context context, String[] strArr) {
        String sharedPreferenceSubtitleColor = new PreferenceHelper(context).getSharedPreferenceSubtitleColor();
        for (int i = 0; i < strArr.length; i++) {
            if (sharedPreferenceSubtitleColor.equalsIgnoreCase(strArr[i])) {
                return i;
            }
        }
        return 0;
    }

    public static List<SubtitleModel.DataModel> getUniqueSubtitleModels(List<SubtitleModel.DataModel> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (SubtitleModel.DataModel dataModel : list) {
            if (linkedHashMap.get(dataModel.getAttributesModel().getLanguage()) == null) {
                linkedHashMap.put(dataModel.getAttributesModel().getLanguage(), dataModel);
            }
        }
        return new ArrayList(linkedHashMap.values());
    }

    public static String getUsernameFromUrl(String str) {
        try {
            return new URL(str.replaceAll(" ", "").trim()).getQuery().split("&")[0].split("=")[1];
        } catch (Exception unused) {
            return "";
        }
    }

    public static List<String> getVodSortLists(WordModels wordModels) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(wordModels.getOrder_by_number());
        arrayList.add(wordModels.getOrder_by_added());
        arrayList.add(wordModels.getOrder_by_rating());
        arrayList.add(wordModels.getSort_a_z());
        arrayList.add(wordModels.getSort_z_a());
        return arrayList;
    }

    public static WordModels getWordModel(Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        String sharedPreferenceLanguageCode = preferenceHelper.getSharedPreferenceLanguageCode();
        AppInfoModel sharedPreferenceAppInfo = preferenceHelper.getSharedPreferenceAppInfo();
        if (sharedPreferenceAppInfo != null) {
            for (int i = 0; i < sharedPreferenceAppInfo.getLanguageModels().size(); i++) {
                if (sharedPreferenceLanguageCode.equalsIgnoreCase(sharedPreferenceAppInfo.getLanguageModels().get(i).getCode())) {
                    return sharedPreferenceAppInfo.getLanguageModels().get(i).getWordModel();
                }
            }
            for (int i2 = 0; i2 < sharedPreferenceAppInfo.getLanguageModels().size(); i2++) {
                if ("en".equalsIgnoreCase(sharedPreferenceAppInfo.getLanguageModels().get(i2).getCode())) {
                    preferenceHelper.setSharedPreferenceLanguageCode("en");
                    return sharedPreferenceAppInfo.getLanguageModels().get(i2).getWordModel();
                }
            }
        }
        return new WordModels();
    }

    public static String getXUIPassword(String str) {
        String str2 = str.split("playlist/")[1];
        return str2.contains("/") ? str2.split("/")[1] : "";
    }

    public static String getXUIUsername(String str) {
        String str2 = str.split("playlist/")[1];
        return str2.contains("/") ? str2.split("/")[0] : "";
    }

    public static String getYear(String str) {
        try {
            return Utils.formatDateFromString("yyyy-MM-dd", "yyyy", str);
        } catch (Exception unused) {
            return str;
        }
    }

    public static boolean isTVDevice(Context context) {
        return new PreferenceHelper(context).getSharedPreferenceDeviceType().equalsIgnoreCase("tv");
    }
}
