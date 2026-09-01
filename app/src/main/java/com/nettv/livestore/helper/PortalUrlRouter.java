package com.nettv.livestore.helper;

import java.net.URI;

/**
 * Routes the first two configured playlists through the NetTV portal domains.
 * The playlist path and query parameters are kept so existing credentials
 * continue to work.
 */
public final class PortalUrlRouter {
    private static final String[] PORTAL_BASE_URLS = {
            "http://gadirtv.vip",
            "http://gadir.co"
    };
    private static final String[] PORTAL_NAMES = {
            "GADIRTV",
            "GADIR"
    };

    private PortalUrlRouter() {
    }

    public static String getName(int portalPosition) {
        if (portalPosition < 0 || portalPosition >= PORTAL_NAMES.length) {
            return "";
        }
        return PORTAL_NAMES[portalPosition];
    }

    public static String route(String originalUrl, int portalPosition) {
        if (portalPosition < 0 || portalPosition >= PORTAL_BASE_URLS.length) {
            return originalUrl;
        }

        String portalBaseUrl = PORTAL_BASE_URLS[portalPosition];
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            return portalBaseUrl;
        }

        try {
            URI original = URI.create(originalUrl.trim());
            StringBuilder routedUrl = new StringBuilder(portalBaseUrl);
            String path = original.getRawPath();

            if (path != null && !path.isEmpty()) {
                if (!path.startsWith("/")) {
                    routedUrl.append('/');
                }
                routedUrl.append(path);
            }
            if (original.getRawQuery() != null) {
                routedUrl.append('?').append(original.getRawQuery());
            }
            if (original.getRawFragment() != null) {
                routedUrl.append('#').append(original.getRawFragment());
            }
            return routedUrl.toString();
        } catch (IllegalArgumentException unused) {
            return portalBaseUrl;
        }
    }
}
