package com.google.android.exoplayer2.util;

import android.graphics.Color;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.google.common.base.Ascii;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class ColorParser {
    private static final Map<String, Integer> COLOR_MAP;
    private static final String RGB = "rgb";
    private static final String RGBA = "rgba";
    private static final Pattern RGB_PATTERN = Pattern.compile("^rgb\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)$");
    private static final Pattern RGBA_PATTERN_INT_ALPHA = Pattern.compile("^rgba\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)$");
    private static final Pattern RGBA_PATTERN_FLOAT_ALPHA = Pattern.compile("^rgba\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3}),(\\d*\\.?\\d*?)\\)$");

    static {
        HashMap map = new HashMap();
        COLOR_MAP = map;
        map.put("aliceblue", -984833);
        map.put("antiquewhite", -332841);
        map.put("aqua", -16711681);
        Insets$$ExternalSyntheticOutline0.m(-8388652, map, "aquamarine", -983041, "azure", -657956, "beige", -6972, "bisque");
        Insets$$ExternalSyntheticOutline0.m(-16777216, map, "black", -5171, "blanchedalmond", -16776961, "blue", -7722014, "blueviolet");
        Insets$$ExternalSyntheticOutline0.m(-5952982, map, "brown", -2180985, "burlywood", -10510688, "cadetblue", -8388864, "chartreuse");
        Insets$$ExternalSyntheticOutline0.m(-2987746, map, "chocolate", -32944, "coral", -10185235, "cornflowerblue", -1828, "cornsilk");
        map.put("crimson", -2354116);
        map.put("cyan", -16711681);
        map.put("darkblue", -16777077);
        map.put("darkcyan", -16741493);
        map.put("darkgoldenrod", -4684277);
        map.put("darkgray", -5658199);
        map.put("darkgreen", -16751616);
        map.put("darkgrey", -5658199);
        map.put("darkkhaki", -4343957);
        Insets$$ExternalSyntheticOutline0.m(-7667573, map, "darkmagenta", -11179217, "darkolivegreen", -29696, "darkorange", -6737204, "darkorchid");
        Insets$$ExternalSyntheticOutline0.m(-7667712, map, "darkred", -1468806, "darksalmon", -7357297, "darkseagreen", -12042869, "darkslateblue");
        map.put("darkslategray", -13676721);
        map.put("darkslategrey", -13676721);
        map.put("darkturquoise", -16724271);
        map.put("darkviolet", -7077677);
        map.put("deeppink", -60269);
        map.put("deepskyblue", -16728065);
        map.put("dimgray", -9868951);
        map.put("dimgrey", -9868951);
        Insets$$ExternalSyntheticOutline0.m(-14774017, map, "dodgerblue", -5103070, "firebrick", -1296, "floralwhite", -14513374, "forestgreen");
        map.put("fuchsia", -65281);
        map.put("gainsboro", -2302756);
        map.put("ghostwhite", -460545);
        map.put("gold", -10496);
        map.put("goldenrod", -2448096);
        map.put("gray", -8355712);
        map.put("green", -16744448);
        map.put("greenyellow", -5374161);
        map.put("grey", -8355712);
        Insets$$ExternalSyntheticOutline0.m(-983056, map, "honeydew", -38476, "hotpink", -3318692, "indianred", -11861886, "indigo");
        Insets$$ExternalSyntheticOutline0.m(-16, map, "ivory", -989556, "khaki", -1644806, "lavender", -3851, "lavenderblush");
        Insets$$ExternalSyntheticOutline0.m(-8586240, map, "lawngreen", -1331, "lemonchiffon", -5383962, "lightblue", -1015680, "lightcoral");
        map.put("lightcyan", -2031617);
        map.put("lightgoldenrodyellow", -329006);
        map.put("lightgray", -2894893);
        map.put("lightgreen", -7278960);
        map.put("lightgrey", -2894893);
        map.put("lightpink", -18751);
        map.put("lightsalmon", -24454);
        map.put("lightseagreen", -14634326);
        map.put("lightskyblue", -7876870);
        map.put("lightslategray", -8943463);
        map.put("lightslategrey", -8943463);
        map.put("lightsteelblue", -5192482);
        map.put("lightyellow", -32);
        map.put("lime", -16711936);
        map.put("limegreen", -13447886);
        map.put("linen", -331546);
        map.put("magenta", -65281);
        map.put("maroon", -8388608);
        map.put("mediumaquamarine", -10039894);
        Insets$$ExternalSyntheticOutline0.m(-16777011, map, "mediumblue", -4565549, "mediumorchid", -7114533, "mediumpurple", -12799119, "mediumseagreen");
        Insets$$ExternalSyntheticOutline0.m(-8689426, map, "mediumslateblue", -16713062, "mediumspringgreen", -12004916, "mediumturquoise", -3730043, "mediumvioletred");
        Insets$$ExternalSyntheticOutline0.m(-15132304, map, "midnightblue", -655366, "mintcream", -6943, "mistyrose", -6987, "moccasin");
        Insets$$ExternalSyntheticOutline0.m(-8531, map, "navajowhite", -16777088, "navy", -133658, "oldlace", -8355840, "olive");
        Insets$$ExternalSyntheticOutline0.m(-9728477, map, "olivedrab", -23296, "orange", -47872, "orangered", -2461482, "orchid");
        Insets$$ExternalSyntheticOutline0.m(-1120086, map, "palegoldenrod", -6751336, "palegreen", -5247250, "paleturquoise", -2396013, "palevioletred");
        Insets$$ExternalSyntheticOutline0.m(-4139, map, "papayawhip", -9543, "peachpuff", -3308225, "peru", -16181, "pink");
        Insets$$ExternalSyntheticOutline0.m(-2252579, map, "plum", -5185306, "powderblue", -8388480, "purple", -10079335, "rebeccapurple");
        Insets$$ExternalSyntheticOutline0.m(SupportMenu.CATEGORY_MASK, map, "red", -4419697, "rosybrown", -12490271, "royalblue", -7650029, "saddlebrown");
        Insets$$ExternalSyntheticOutline0.m(-360334, map, "salmon", -744352, "sandybrown", -13726889, "seagreen", -2578, "seashell");
        Insets$$ExternalSyntheticOutline0.m(-6270419, map, "sienna", -4144960, "silver", -7876885, "skyblue", -9807155, "slateblue");
        map.put("slategray", -9404272);
        map.put("slategrey", -9404272);
        map.put("snow", -1286);
        map.put("springgreen", -16711809);
        Insets$$ExternalSyntheticOutline0.m(-12156236, map, "steelblue", -2968436, "tan", -16744320, "teal", -2572328, "thistle");
        Insets$$ExternalSyntheticOutline0.m(-40121, map, "tomato", 0, "transparent", -12525360, "turquoise", -1146130, "violet");
        Insets$$ExternalSyntheticOutline0.m(-663885, map, "wheat", -1, "white", -657931, "whitesmoke", InputDeviceCompat.SOURCE_ANY, "yellow");
        map.put("yellowgreen", -6632142);
    }

    private ColorParser() {
    }

    @ColorInt
    private static int parseColorInternal(String str, boolean z) {
        Assertions.checkArgument(!TextUtils.isEmpty(str));
        String strReplace = str.replace(" ", "");
        if (strReplace.charAt(0) == '#') {
            int i = (int) Long.parseLong(strReplace.substring(1), 16);
            if (strReplace.length() == 7) {
                return (-16777216) | i;
            }
            if (strReplace.length() == 9) {
                return ((i & 255) << 24) | (i >>> 8);
            }
            throw new IllegalArgumentException();
        }
        if (strReplace.startsWith(RGBA)) {
            Matcher matcher = (z ? RGBA_PATTERN_FLOAT_ALPHA : RGBA_PATTERN_INT_ALPHA).matcher(strReplace);
            if (matcher.matches()) {
                return Color.argb(z ? (int) (Float.parseFloat((String) Assertions.checkNotNull(matcher.group(4))) * 255.0f) : Integer.parseInt((String) Assertions.checkNotNull(matcher.group(4)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(3)), 10));
            }
        } else if (strReplace.startsWith(RGB)) {
            Matcher matcher2 = RGB_PATTERN.matcher(strReplace);
            if (matcher2.matches()) {
                return Color.rgb(Integer.parseInt((String) Assertions.checkNotNull(matcher2.group(1)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher2.group(2)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher2.group(3)), 10));
            }
        } else {
            Integer num = COLOR_MAP.get(Ascii.toLowerCase(strReplace));
            if (num != null) {
                return num.intValue();
            }
        }
        throw new IllegalArgumentException();
    }

    @ColorInt
    public static int parseCssColor(String str) {
        return parseColorInternal(str, true);
    }

    @ColorInt
    public static int parseTtmlColor(String str) {
        return parseColorInternal(str, false);
    }
}
