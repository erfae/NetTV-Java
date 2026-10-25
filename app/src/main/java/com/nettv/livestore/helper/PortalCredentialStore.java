package com.nettv.livestore.helper;

import android.content.SharedPreferences;
import com.nettv.livestore.models.AppInfoModel;
import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.util.List;

/** Device-local overrides for the three fixed portals, separate from server data. */
public final class PortalCredentialStore {
    private static final String PREFIX = "fixed_portal_v1_";

    private PortalCredentialStore() { }

    public static String xtreamUrl(int position, String username, String password) {
        try {
            return PortalUrlRouter.route("/get.php?username="
                    + URLEncoder.encode(username, "UTF-8") + "&password="
                    + URLEncoder.encode(password, "UTF-8")
                    + "&output=ts&type=m3u_plus", position);
        } catch (UnsupportedEncodingException impossible) {
            throw new IllegalStateException(impossible);
        }
    }

    public static String queryValue(String url, String key) {
        try {
            String query = URI.create(url).getRawQuery();
            if (query != null) {
                for (String pair : query.split("&")) {
                    String[] parts = pair.split("=", 2);
                    if (parts.length == 2 && key.equals(URLDecoder.decode(parts[0], "UTF-8"))) {
                        return URLDecoder.decode(parts[1], "UTF-8");
                    }
                }
            }
        } catch (Exception invalid) {
            // A malformed URL has no usable credential value.
        }
        return "";
    }

    public static boolean save(SharedPreferences preferences, AppInfoModel model,
            int position, String url, String type) {
        if (position < 0 || position >= 3 || model == null) {
            return false;
        }
        boolean saved = preferences.edit()
                .putString(PREFIX + position, PortalUrlRouter.route(url, position))
                .putString(PREFIX + position + "_type", type == null ? "general" : type)
                .commit();
        if (saved) {
            restore(preferences, model);
        }
        return saved;
    }

    /** Migrate cached credentials once; server refreshes never replace overrides. */
    public static void captureMissing(SharedPreferences preferences, AppInfoModel cached) {
        if (cached == null) {
            return;
        }
        List<AppInfoModel.UrlModel> portals = cached.getResult();
        SharedPreferences.Editor editor = preferences.edit();
        boolean changed = false;
        for (int i = 0; i < 3; i++) {
            AppInfoModel.UrlModel portal = portals.get(i);
            if (!preferences.contains(PREFIX + i) && configured(portal.getUrl())) {
                editor.putString(PREFIX + i, portal.getUrl());
                editor.putString(PREFIX + i + "_type", portal.getType());
                changed = true;
            }
        }
        if (changed) {
            editor.commit();
        }
    }

    public static AppInfoModel restore(SharedPreferences preferences, AppInfoModel model) {
        if (model == null) {
            return null;
        }
        List<AppInfoModel.UrlModel> portals = model.getResult();
        for (int i = 0; i < 3; i++) {
            if (preferences.contains(PREFIX + i)) {
                AppInfoModel.UrlModel portal = portals.get(i);
                portal.setUrl(PortalUrlRouter.route(preferences.getString(PREFIX + i, ""), i));
                portal.setType(preferences.getString(PREFIX + i + "_type", "general"));
            }
        }
        model.setResult(portals);
        return model;
    }

    private static boolean configured(String url) {
        try {
            URI value = URI.create(url);
            String path = value.getRawPath();
            return path != null && !path.isEmpty() && !"/".equals(path);
        } catch (IllegalArgumentException invalid) {
            return false;
        }
    }
}
