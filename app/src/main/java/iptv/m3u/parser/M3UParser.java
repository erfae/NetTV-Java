package iptv.m3u.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class M3UParser {
    private static final String ATTR_CHANNEL_ID = "id";
    private static final String ATTR_CHANNEL_NAME = "channel_name";
    private static final String ATTR_DLNA_EXTRAS = "dlna_extras";
    private static final String ATTR_DURATION = "duration";
    private static final String ATTR_GROUP_TITLE = "group-title";
    private static final String ATTR_LOGO = "logo";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PLUGIN = "plugin";
    private static final String ATTR_TVG_PREFIX = "tvg-";
    private static final String ATTR_TVG_SUFFIX = "-tvg";
    private static final String ATTR_TYPE = "type";
    private static final String EMPTY_STRING = "";
    private static final String INVALID_STREAM_URL = "http://0.0.0.0:1234";
    private static final String PREFIX_COMMENT = "#";
    private static final String PREFIX_EXTINF = "#EXTINF:";
    private static final String PREFIX_EXTM3U = "#EXTM3U";
    private static M3UParser mInstance;
    private M3UHandler mHandler = null;
    private M3UItem mTempItem = null;

    /* JADX INFO: renamed from: iptv.m3u.parser.M3UParser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$iptv$m3u$parser$M3UParser$Status;

        static {
            int[] iArr = new int[Status.values().length];
            $SwitchMap$iptv$m3u$parser$M3UParser$Status = iArr;
            try {
                iArr[Status.READY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$iptv$m3u$parser$M3UParser$Status[Status.READING_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$iptv$m3u$parser$M3UParser$Status[Status.KEY_READY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$iptv$m3u$parser$M3UParser$Status[Status.READING_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum Status {
        READY,
        READING_KEY,
        KEY_READY,
        READING_VALUE
    }

    private M3UParser() {
    }

    private int convert2int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return -1;
        }
    }

    private void flush(M3UHandler m3UHandler) {
        M3UItem m3UItem = this.mTempItem;
        if (m3UItem != null) {
            if (m3UItem.getStreamURL() != null) {
                m3UHandler.onReadEXTINF(this.mTempItem);
            }
            this.mTempItem = null;
        }
    }

    private String getAttr(Map<String, String> map, String str) {
        String str2 = map.get(str);
        if (str2 != null) {
            return str2;
        }
        String str3 = map.get(ATTR_TVG_PREFIX + str);
        if (str3 != null) {
            return str3;
        }
        return map.get(str + ATTR_TVG_SUFFIX);
    }

    public static final M3UParser getInstance() {
        if (mInstance == null) {
            mInstance = new M3UParser();
        }
        return mInstance;
    }

    private Map<String, String> parseAttributes(String str) {
        String str2;
        int i;
        char cCharAt;
        HashMap map = new HashMap();
        if (str != null && !str.equals("")) {
            Status status = Status.READY;
            StringBuffer stringBuffer = new StringBuffer();
            char cCharAt2 = str.charAt(0);
            if (cCharAt2 == '-' || Character.isDigit(cCharAt2)) {
                stringBuffer.append(cCharAt2);
                int i2 = 0;
                while (true) {
                    i2++;
                    if (i2 >= str.length()) {
                        break;
                    }
                    char cCharAt3 = str.charAt(i2);
                    if (!Character.isDigit(cCharAt3)) {
                        break;
                    }
                    stringBuffer.append(cCharAt3);
                }
                putAttr(map, "duration", stringBuffer.toString());
                str = shrink(str.replaceFirst(stringBuffer.toString(), ""));
                reset(stringBuffer);
            }
            int length = 0;
            loop0: while (true) {
                boolean z = false;
                str2 = "";
                while (true) {
                    if (length >= str.length()) {
                        break loop0;
                    }
                    i = length + 1;
                    cCharAt = str.charAt(length);
                    int i3 = AnonymousClass1.$SwitchMap$iptv$m3u$parser$M3UParser$Status[status.ordinal()];
                    if (i3 != 1) {
                        if (i3 != 2) {
                            if (i3 != 3) {
                                if (i3 == 4) {
                                    if (z) {
                                        break;
                                    }
                                    if (Character.isWhitespace(cCharAt)) {
                                        if (stringBuffer.length() > 0) {
                                            putAttr(map, str2, stringBuffer.toString());
                                            reset(stringBuffer);
                                        }
                                        status = Status.READY;
                                        str2 = "";
                                    } else {
                                        stringBuffer.append(cCharAt);
                                    }
                                }
                            } else if (!Character.isWhitespace(cCharAt)) {
                                if (cCharAt == '\"') {
                                    z = true;
                                } else {
                                    stringBuffer.append(cCharAt);
                                }
                                status = Status.READING_VALUE;
                            }
                        } else if (cCharAt == '=') {
                            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(str2);
                            sbM.append(stringBuffer.toString());
                            String strShrink = shrink(sbM.toString());
                            reset(stringBuffer);
                            str2 = strShrink;
                            status = Status.KEY_READY;
                        } else {
                            stringBuffer.append(cCharAt);
                        }
                    } else if (!Character.isWhitespace(cCharAt)) {
                        if (cCharAt == ',') {
                            putAttr(map, ATTR_CHANNEL_NAME, str.substring(i));
                            length = str.length();
                        } else {
                            stringBuffer.append(cCharAt);
                            status = Status.READING_KEY;
                        }
                    }
                    length = i;
                }
                while (cCharAt != '\"' && i < str.length()) {
                    stringBuffer.append(cCharAt);
                    cCharAt = str.charAt(i);
                    i++;
                }
                if (stringBuffer.length() > 0) {
                    putAttr(map, str2, stringBuffer.toString());
                    reset(stringBuffer);
                }
                status = Status.READY;
                length = i;
            }
            if (!str2.equals("") && stringBuffer.length() > 0) {
                putAttr(map, str2, stringBuffer.toString());
                reset(stringBuffer);
            }
        }
        return map;
    }

    private M3UHead parseHead(String str) {
        Map<String, String> attributes = parseAttributes(str);
        M3UHead m3UHead = new M3UHead();
        m3UHead.setName(getAttr(attributes, ATTR_NAME));
        m3UHead.setType(getAttr(attributes, ATTR_TYPE));
        m3UHead.setDLNAExtras(getAttr(attributes, ATTR_DLNA_EXTRAS));
        m3UHead.setPlugin(getAttr(attributes, ATTR_PLUGIN));
        return m3UHead;
    }

    private M3UItem parseItem(String str) {
        Map<String, String> attributes = parseAttributes(str);
        M3UItem m3UItem = new M3UItem();
        m3UItem.setChannelID(getAttr(attributes, "id"));
        m3UItem.setChannelName(getAttr(attributes, ATTR_CHANNEL_NAME));
        m3UItem.setDuration(convert2int(getAttr(attributes, "duration")));
        m3UItem.setLogoURL(getAttr(attributes, ATTR_LOGO));
        m3UItem.setGroupTitle(getAttr(attributes, ATTR_GROUP_TITLE));
        m3UItem.setType(getAttr(attributes, ATTR_TYPE));
        m3UItem.setDLNAExtras(getAttr(attributes, ATTR_DLNA_EXTRAS));
        m3UItem.setPlugin(getAttr(attributes, ATTR_PLUGIN));
        return m3UItem;
    }

    private void putAttr(Map<String, String> map, String str, String str2) {
        map.put(str, str2);
    }

    private void reset(StringBuffer stringBuffer) {
        stringBuffer.delete(0, stringBuffer.length());
    }

    private String shrink(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    private void updateURL(String str) {
        if (this.mTempItem == null || INVALID_STREAM_URL.equals(str)) {
            return;
        }
        this.mTempItem.setStreamURL(str.trim());
    }

    public void parse(String str) {
        parse(str, this.mHandler);
    }

    public void setHandler(M3UHandler m3UHandler) {
        this.mHandler = m3UHandler;
    }

    public void parse(String str, M3UHandler m3UHandler) {
        if (m3UHandler == null) {
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), StandardCharsets.UTF_8));
            while (true) {
                String strShrink = shrink(bufferedReader.readLine());
                if (strShrink == null) {
                    flush(m3UHandler);
                    bufferedReader.close();
                    return;
                } else if (strShrink.startsWith(PREFIX_EXTM3U)) {
                    m3UHandler.onReadEXTM3U(parseHead(shrink(strShrink.replaceFirst(PREFIX_EXTM3U, ""))));
                } else if (strShrink.startsWith(PREFIX_EXTINF)) {
                    flush(m3UHandler);
                    this.mTempItem = parseItem(shrink(strShrink.replaceFirst(PREFIX_EXTINF, "")));
                } else if (!strShrink.startsWith(PREFIX_COMMENT) && !strShrink.equals("")) {
                    updateURL(strShrink);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
