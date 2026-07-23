package com.nettv.livestore.apps;

import android.content.Context;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.utils.EnigmaUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import okio.Utf8;

/* JADX INFO: loaded from: classes2.dex */
public class Constants {
    public static final String API_KEY;
    public static final String APP_PNAME = "com.nettv.livestore";
    public static final int CHANNEL_TYPE = 0;
    public static final String EPISODE_SUBTITLE_SEARCH;
    public static final String IMDB_API;
    public static final String IMDB_API_SERIES;
    public static final String IMDB_IMAGE_PREF;
    public static final String IMDB_KEY = "d96abf17668601f56b3d7b8336a61933";
    public static final int NO_MEDIA_TYPE = -1;
    public static final String PASSWORD;
    public static final String PERSON_API;
    public static final int SERIES_TYPE = 2;
    public static final String SUBTITLE_DOWNLOAD;
    public static final String SUBTITLE_LOGIN;
    public static final String SUBTITLE_SEARCH;
    public static final String USERNAME;
    public static final int VIDEO_TYPE = 1;
    public static final String WEB_URL = "";
    public static final String add_group = "add_group";
    public static final String all_id = "all_id";
    public static final int create_request_code = 1000;
    public static final long date_mils = 72000;
    public static final int delete_request_code = 2000;
    public static final String fav_id = "fav_id";
    public static final String lock_id = "lock_id";
    public static final String resume_id = "resume_id";
    public static final String second_create_url;
    public static final String second_delete_url;
    public static final int second_request_code = 3000;
    public static final String second_response_url;
    public static final String second_update_control;
    public static SimpleDateFormat stampFormat;
    public static List<String> xxx_live_categories = new ArrayList();
    public static List<String> xxx_vod_categories = new ArrayList();

    static {
        EnigmaUtils.enigmatization(new byte[]{-30, 84, -24, -48, 99, 77, -24, -10, Ascii.NAK, 14, 46, -66, -95, 41, 119, 5, 87, 102, 109, -122, -58, 35, -123, -11, 94, 5, Ascii.NAK, -14, Ascii.EM, 38, 14, 110, -67, 40, SignedBytes.MAX_POWER_OF_TWO, 48, 122, Ascii.CAN, 121, -39, 0, 56, -42, 124, -4, -41, -107, -119});
        second_response_url = "https://cameleon.vip/ibonet/auth.php";
        EnigmaUtils.enigmatization(new byte[]{-30, 84, -24, -48, 99, 77, -24, -10, Ascii.NAK, 14, 46, -66, -95, 41, 119, 5, -13, -69, 47, 86, Ascii.GS, 120, 68, 38, Ascii.ESC, 95, 1, 59, 74, -8, -127, -27, -25, Ascii.US, 125, -12, 11, 39, -77, -112, 105, 6, -28, -86, 102, -61, 76, 34});
        second_create_url = "https://cameleon.vip/ibonet/playlists.php";
        second_delete_url = EnigmaUtils.enigmatization(new byte[]{-30, 84, -24, -48, 99, 77, -24, -10, Ascii.NAK, 14, 46, -66, -95, 41, 119, 5, -13, -69, 47, 86, Ascii.GS, 120, 68, 38, Ascii.ESC, 95, 1, 59, 74, -8, -127, -27, 55, -63, -60, -24, 82, -98, Ascii.EM, 56, -42, 97, 15, 56, -71, -70, -77, -4});
        EnigmaUtils.enigmatization(new byte[]{-30, 84, -24, -48, 99, 77, -24, -10, Ascii.NAK, 14, 46, -66, -95, 41, 119, 5, 54, 106, -32, -33, -127, -127, -62, 58, -40, -3, -103, -63, Ascii.SUB, Utf8.REPLACEMENT_BYTE, 15, -29, -113, -52, 32, 81, -89, 33, 72, 2, -37, 82, -87, 101, -44, 113, 43, 123});
        second_update_control = "https://cameleon.vip/ibonet/update.php";
        stampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IMDB_API = EnigmaUtils.enigmatization(new byte[]{-52, -36, -81, 7, -89, 79, -74, -125, 48, 61, -124, -11, 19, -106, -123, Ascii.ETB, 11, -46, 92, -114, 119, -10, 97, 93, Ascii.US, Ascii.ESC, 126, 69, 107, -63, -28, -102, -57, 32, -30, -54, 75, 122, Ascii.CAN, -119, 19, -28, 127, -70, 112, 80, -54, -110});
        IMDB_API_SERIES = EnigmaUtils.enigmatization(new byte[]{-52, -36, -81, 7, -89, 79, -74, -125, 48, 61, -124, -11, 19, -106, -123, Ascii.ETB, 70, 37, 119, -110, 115, -113, -23, -31, 92, 16, -98, -128, 104, -31, -68, -40, 42, -30, 111, -89, 69, 121, -99, 10, 12, -4, -53, -76, 12, -35, 104, 7});
        PERSON_API = EnigmaUtils.enigmatization(new byte[]{-52, -36, -81, 7, -89, 79, -74, -125, 48, 61, -124, -11, 19, -106, -123, Ascii.ETB, Ascii.GS, 75, 34, -85, 117, -107, -101, 7, -32, -1, 60, -72, 41, -62, -32, -10, -35, 37, 43, -38, 118, -41, 113, -44, -115, 96, -14, -108, -100, -109, -115, 5});
        IMDB_IMAGE_PREF = EnigmaUtils.enigmatization(new byte[]{-37, 48, 34, -43, -115, Ascii.US, -83, 100, -16, -63, 32, 8, -123, -98, -73, 91, -118, Ascii.ETB, -55, 61, 80, -104, -4, 45, 110, -59, 119, 99, 96, -82, 61, -83});
        SUBTITLE_LOGIN = EnigmaUtils.enigmatization(new byte[]{-24, 93, -104, 93, Ascii.SUB, -82, 18, Ascii.NAK, 11, 32, 42, 70, -68, 100, 66, -46, -93, 15, -55, -67, 126, -65, 35, 56, -79, -75, Ascii.DC4, Ascii.US, 121, 12, -76, 16, 70, -108, 61, Ascii.ETB, -84, -26, -95, -25, -86, -35, 56, 33, Ascii.NAK, -53, -83, -89});
        USERNAME = EnigmaUtils.enigmatization(new byte[]{57, -62, 122, -29, -64, -122, 0, -24, -44, 74, 4, 44, 44, 45, 96, -63});
        PASSWORD = EnigmaUtils.enigmatization(new byte[]{-117, -43, 110, -55, -14, -109, 96, -22, 8, -61, -70, Ascii.US, -18, -98, 39, 108});
        SUBTITLE_SEARCH = EnigmaUtils.enigmatization(new byte[]{-24, 93, -104, 93, Ascii.SUB, -82, 18, Ascii.NAK, 11, 32, 42, 70, -68, 100, 66, -46, -93, 15, -55, -67, 126, -65, 35, 56, -79, -75, Ascii.DC4, Ascii.US, 121, 12, -76, 16, -16, -27, -70, 69, 81, 9, 38, 44, 79, -9, -9, Ascii.RS, Ascii.ESC, 54, -58, 41, 57, -40, 101, -42, -84, -72, -22, 41, 62, 15, 65, -80, -58, -41, -52, 113});
        EPISODE_SUBTITLE_SEARCH = EnigmaUtils.enigmatization(new byte[]{-24, 93, -104, 93, Ascii.SUB, -82, 18, Ascii.NAK, 11, 32, 42, 70, -68, 100, 66, -46, -93, 15, -55, -67, 126, -65, 35, 56, -79, -75, Ascii.DC4, Ascii.US, 121, 12, -76, 16, -16, -27, -70, 69, 81, 9, 38, 44, 79, -9, -9, Ascii.RS, Ascii.ESC, 54, -58, 41, -1, 86, -121, -84, 73, 82, 66, 47, 123, -34, -76, -13, -101, 93, -104, -114, 110, -8, 32, -31, -104, -93, 40, -40, 67, Ascii.GS, -113, -123, 10, 102, -67, -122});
        SUBTITLE_DOWNLOAD = EnigmaUtils.enigmatization(new byte[]{-24, 93, -104, 93, Ascii.SUB, -82, 18, Ascii.NAK, 11, 32, 42, 70, -68, 100, 66, -46, -93, 15, -55, -67, 126, -65, 35, 56, -79, -75, Ascii.DC4, Ascii.US, 121, 12, -76, 16, -92, 12, 0, -73, -22, 60, 113, -18, 124, Ascii.SYN, -85, 73, -117, 7, -17, 38});
        API_KEY = EnigmaUtils.enigmatization(new byte[]{99, 44, -10, 107, -112, -55, 17, 1, -82, 106, 73, -30, -68, -51, -105, -24, -60, 9, 91, 82, -32, -95, -103, 59, 81, 17, 79, -105, -44, -98, -45, -98, 119, 105, 84, -54, -88, 33, -6, -40, -46, 36, -96, 50, Ascii.SUB, -101, -117, 115});
    }

    public static void getLiveGroupModels(List<String> list, Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        LTVApp.live_categories_filter = new ArrayList();
        List<CategoryModel> sharedLiveCategoryModels = preferenceHelper.getSharedLiveCategoryModels();
        LTVApp.live_categories_filter.addAll(sharedLiveCategoryModels);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < sharedLiveCategoryModels.size(); i++) {
            CategoryModel categoryModel = sharedLiveCategoryModels.get(i);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(categoryModel.getId())) {
                    LTVApp.live_categories_filter.remove(categoryModel);
                }
            }
        }
    }

    public static void getSeriesGroupModels(List<String> list, Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        LTVApp.series_categories_filter = new ArrayList();
        List<CategoryModel> sharedPreferenceSeriesCategoryModel = preferenceHelper.getSharedPreferenceSeriesCategoryModel();
        LTVApp.series_categories_filter.addAll(sharedPreferenceSeriesCategoryModel);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < sharedPreferenceSeriesCategoryModel.size(); i++) {
            CategoryModel categoryModel = sharedPreferenceSeriesCategoryModel.get(i);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(categoryModel.getId())) {
                    LTVApp.series_categories_filter.remove(categoryModel);
                }
            }
        }
    }

    public static void getVodGroupModels(List<String> list, Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        LTVApp.vod_categories_filter = new ArrayList();
        List<CategoryModel> sharedPreferenceVodCategory = preferenceHelper.getSharedPreferenceVodCategory();
        LTVApp.vod_categories_filter.addAll(sharedPreferenceVodCategory);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < sharedPreferenceVodCategory.size(); i++) {
            CategoryModel categoryModel = sharedPreferenceVodCategory.get(i);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(categoryModel.getId())) {
                    LTVApp.vod_categories_filter.remove(categoryModel);
                }
            }
        }
    }

    public static void setServerTimeOffset(long j, String str) {
        try {
            LTVApp.SEVER_OFFSET = (j * 1000) - stampFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
