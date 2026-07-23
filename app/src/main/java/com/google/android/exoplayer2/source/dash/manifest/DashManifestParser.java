package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: loaded from: classes.dex */
public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final String TAG = "MpdParser";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final int[] MPEG_CHANNEL_CONFIGURATION_MAPPING = {-1, 1, 2, 3, 4, 5, 6, 8, 2, 3, 4, 7, 8, 24, 8, 12, 10, 12, 14, 12, 14};

    public static final class RepresentationInfo {
        public final ImmutableList<BaseUrl> baseUrls;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;

        @Nullable
        public final String drmSchemeType;
        public final List<Descriptor> essentialProperties;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;
        public final List<Descriptor> supplementalProperties;

        public RepresentationInfo(Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable String str, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2, List<Descriptor> list2, List<Descriptor> list3, long j) {
            this.format = format;
            this.baseUrls = ImmutableList.copyOf((Collection) list);
            this.segmentBase = segmentBase;
            this.drmSchemeType = str;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.essentialProperties = list2;
            this.supplementalProperties = list3;
            this.revisionId = j;
        }
    }

    public DashManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    private long addSegmentTimelineElementsToList(List<SegmentBase.SegmentTimelineElement> list, long j, long j2, int i, long j3) {
        int iCeilDivide = i >= 0 ? i + 1 : (int) Util.ceilDivide(j3 - j, j2);
        for (int i2 = 0; i2 < iCeilDivide; i2++) {
            list.add(new SegmentBase.SegmentTimelineElement(j, j2));
            j += j2;
        }
        return j;
    }

    private static int checkContentTypeConsistency(int i, int i2) {
        if (i == -1) {
            return i2;
        }
        if (i2 == -1) {
            return i;
        }
        Assertions.checkState(i == i2);
        return i;
    }

    @Nullable
    private static String checkLanguageConsistency(@Nullable String str, @Nullable String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static void fillInClearKeyInformation(ArrayList<DrmInitData.SchemeData> arrayList) {
        String str;
        int i = 0;
        while (true) {
            if (i >= arrayList.size()) {
                str = null;
                break;
            }
            DrmInitData.SchemeData schemeData = arrayList.get(i);
            if (C.CLEARKEY_UUID.equals(schemeData.uuid) && (str = schemeData.licenseServerUrl) != null) {
                arrayList.remove(i);
                break;
            }
            i++;
        }
        if (str == null) {
            return;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            DrmInitData.SchemeData schemeData2 = arrayList.get(i2);
            if (C.COMMON_PSSH_UUID.equals(schemeData2.uuid) && schemeData2.licenseServerUrl == null) {
                arrayList.set(i2, new DrmInitData.SchemeData(C.CLEARKEY_UUID, str, schemeData2.mimeType, schemeData2.data));
            }
        }
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<DrmInitData.SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DrmInitData.SchemeData schemeData = arrayList.get(size);
            if (!schemeData.hasData()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    }
                }
            }
        }
    }

    private static long getFinalAvailabilityTimeOffset(long j, long j2) {
        if (j2 != C.TIME_UNSET) {
            j = j2;
        }
        return j == Long.MAX_VALUE ? C.TIME_UNSET : j;
    }

    @Nullable
    private static String getSampleMimeType(@Nullable String str, @Nullable String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (MimeTypes.isText(str) || MimeTypes.isImage(str)) {
            return str;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(str)) {
            return null;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(str2);
        return MimeTypes.TEXT_VTT.equals(mediaMimeType) ? MimeTypes.APPLICATION_MP4VTT : mediaMimeType;
    }

    private boolean isDvbProfileDeclared(String[] strArr) {
        for (String str : strArr) {
            if (str.startsWith("urn:dvb:dash:profile:dvb-dash:")) {
                return true;
            }
        }
        return false;
    }

    public static void maybeSkipTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
            int i = 1;
            while (i != 0) {
                xmlPullParser.next();
                if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                    i++;
                } else if (XmlPullParserUtil.isEndTag(xmlPullParser)) {
                    i--;
                }
            }
        }
    }

    public static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "schemeIdUri");
        if (attributeValue == null) {
            attributeValue = "";
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "value");
        if (attributeValue2 == null) {
            attributeValue2 = null;
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, TtmlNode.ATTR_ID);
        String str2 = attributeValue3 != null ? attributeValue3 : null;
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(attributeValue, attributeValue2, str2);
    }

    public static long parseDuration(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Util.parseXsDuration(attributeValue);
    }

    public static float parseFrameRate(XmlPullParser xmlPullParser, float f) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return f;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return f;
        }
        int i = Integer.parseInt(matcher.group(1));
        String strGroup = matcher.group(2);
        return !TextUtils.isEmpty(strGroup) ? i / Integer.parseInt(strGroup) : i;
    }

    public static int parseInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i : Integer.parseInt(attributeValue);
    }

    public static long parseLong(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Long.parseLong(attributeValue);
    }

    public static String parseText(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String text = "";
        do {
            xmlPullParser.next();
            if (xmlPullParser.getEventType() == 4) {
                text = xmlPullParser.getText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return text;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:36:0x0070  */
    /* JADX WARN: Code duplicated, block: B:46:0x008f A[PHI: r0
  0x008f: PHI (r0v11 int) = (r0v3 int), (r0v14 int) binds: [B:86:0x0100, B:44:0x008b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:72:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:77:0x00e9  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final int parseAudioChannelConfiguration(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int iBitCount;
        String attributeValue = xmlPullParser.getAttributeValue(null, "schemeIdUri");
        if (attributeValue == null) {
            attributeValue = null;
        }
        Objects.requireNonNull(attributeValue);
        byte b = 0;
        int i = 6;
        int i2 = -1;
        switch (attributeValue) {
            case "urn:dts:dash:audio_channel_configuration:2012":
            case "tag:dts.com,2014:dash:audio_channel_configuration:2012":
                iBitCount = parseInt(xmlPullParser, "value", -1);
                if (iBitCount > 0 && iBitCount < 33) {
                    i2 = iBitCount;
                    break;
                }
                break;
            case "urn:mpeg:dash:23003:3:audio_channel_configuration:2011":
                i2 = parseInt(xmlPullParser, "value", -1);
                break;
            case "tag:dolby.com,2014:dash:audio_channel_configuration:2011":
            case "urn:dolby:dash:audio_channel_configuration:2011":
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "value");
                if (attributeValue2 == null) {
                    i = -1;
                } else {
                    String lowerCase = Ascii.toLowerCase(attributeValue2);
                    Objects.requireNonNull(lowerCase);
                    switch (lowerCase.hashCode()) {
                        case 1596796:
                            if (!lowerCase.equals("4000")) {
                                b = -1;
                            }
                            break;
                        case 2937391:
                            if (!lowerCase.equals("a000")) {
                                b = -1;
                            } else {
                                b = 1;
                            }
                            break;
                        case 3094035:
                            if (!lowerCase.equals("f801")) {
                                b = -1;
                            } else {
                                b = 2;
                            }
                            break;
                        case 3133436:
                            if (!lowerCase.equals("fa01")) {
                                b = -1;
                            } else {
                                b = 3;
                            }
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    if (b == 0) {
                        i = 1;
                    } else if (b == 1) {
                        i = 2;
                    } else if (b != 2) {
                        if (b != 3) {
                            i = -1;
                        } else {
                            i = 8;
                        }
                    }
                }
                i2 = i;
                break;
            case "urn:mpeg:mpegB:cicp:ChannelConfiguration":
                int i3 = parseInt(xmlPullParser, "value", -1);
                if (i3 >= 0) {
                    int[] iArr = MPEG_CHANNEL_CONFIGURATION_MAPPING;
                    if (i3 < iArr.length) {
                        i2 = iArr[i3];
                    }
                    break;
                }
                break;
            case "tag:dts.com,2018:uhd:audio_channel_configuration":
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "value");
                if (attributeValue3 != null && (iBitCount = Integer.bitCount(Integer.parseInt(attributeValue3, 16))) != 0) {
                    i2 = iBitCount;
                    break;
                }
                break;
        }
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "AudioChannelConfiguration"));
        return i2;
    }

    public final long parseAvailabilityTimeOffsetUs(XmlPullParser xmlPullParser, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "availabilityTimeOffset");
        if (attributeValue == null) {
            return j;
        }
        if ("INF".equals(attributeValue)) {
            return Long.MAX_VALUE;
        }
        return (long) (Float.parseFloat(attributeValue) * 1000000.0f);
    }

    public final List<BaseUrl> parseBaseUrl(XmlPullParser xmlPullParser, List<BaseUrl> list, boolean z) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "dvb:priority");
        int i = attributeValue != null ? Integer.parseInt(attributeValue) : z ? 1 : Integer.MIN_VALUE;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "dvb:weight");
        int i2 = attributeValue2 != null ? Integer.parseInt(attributeValue2) : 1;
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "serviceLocation");
        String text = parseText(xmlPullParser, "BaseURL");
        if (UriUtil.isAbsolute(text)) {
            if (attributeValue3 == null) {
                attributeValue3 = text;
            }
            return Lists.newArrayList(new BaseUrl(text, attributeValue3, i, i2));
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            BaseUrl baseUrl = list.get(i3);
            String strResolve = UriUtil.resolve(baseUrl.url, text);
            String str = attributeValue3 == null ? strResolve : attributeValue3;
            if (z) {
                i = baseUrl.priority;
                i2 = baseUrl.weight;
                str = baseUrl.serviceLocation;
            }
            arrayList.add(new BaseUrl(strResolve, str, i, i2));
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0094  */
    /* JADX WARN: Code duplicated, block: B:66:0x0110  */
    public final Pair<String, DrmInitData.SchemeData> parseContentProtection(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String attributeValue;
        UUID uuid;
        byte[] bArrBuildPsshAtom;
        String attributeValue2;
        UUID uuid2;
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "schemeIdUri");
        if (attributeValue3 != null) {
            String lowerCase = Ascii.toLowerCase(attributeValue3);
            Objects.requireNonNull(lowerCase);
            switch (lowerCase) {
                case "urn:uuid:e2719d58-a985-b3c9-781a-b030af78d30e":
                    uuid2 = C.CLEARKEY_UUID;
                    uuid = uuid2;
                    attributeValue = null;
                    bArrBuildPsshAtom = null;
                    attributeValue2 = null;
                    break;
                case "urn:uuid:9a04f079-9840-4286-ab92-e65be0885f95":
                    uuid2 = C.PLAYREADY_UUID;
                    uuid = uuid2;
                    attributeValue = null;
                    bArrBuildPsshAtom = null;
                    attributeValue2 = null;
                    break;
                case "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed":
                    uuid2 = C.WIDEVINE_UUID;
                    uuid = uuid2;
                    attributeValue = null;
                    bArrBuildPsshAtom = null;
                    attributeValue2 = null;
                    break;
                case "urn:mpeg:dash:mp4protection:2011":
                    attributeValue = xmlPullParser.getAttributeValue(null, "value");
                    String attributeValueIgnorePrefix = XmlPullParserUtil.getAttributeValueIgnorePrefix(xmlPullParser, "default_KID");
                    if (!TextUtils.isEmpty(attributeValueIgnorePrefix) && !"00000000-0000-0000-0000-000000000000".equals(attributeValueIgnorePrefix)) {
                        String[] strArrSplit = attributeValueIgnorePrefix.split("\\s+");
                        UUID[] uuidArr = new UUID[strArrSplit.length];
                        for (int i = 0; i < strArrSplit.length; i++) {
                            uuidArr[i] = UUID.fromString(strArrSplit[i]);
                        }
                        uuid = C.COMMON_PSSH_UUID;
                        bArrBuildPsshAtom = PsshAtomUtil.buildPsshAtom(uuid, uuidArr, null);
                        attributeValue2 = null;
                        break;
                    } else {
                        uuid = null;
                        bArrBuildPsshAtom = null;
                        attributeValue2 = null;
                        break;
                    }
                    break;
                default:
                    attributeValue = null;
                    uuid = null;
                    bArrBuildPsshAtom = null;
                    attributeValue2 = null;
                    break;
            }
        } else {
            attributeValue = null;
            uuid = null;
            bArrBuildPsshAtom = null;
            attributeValue2 = null;
        }
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "clearkey:Laurl") && xmlPullParser.next() == 4) {
                attributeValue2 = xmlPullParser.getText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "ms:laurl")) {
                attributeValue2 = xmlPullParser.getAttributeValue(null, "licenseUrl");
            } else if (bArrBuildPsshAtom == null && XmlPullParserUtil.isStartTagIgnorePrefix(xmlPullParser, "pssh") && xmlPullParser.next() == 4) {
                byte[] bArrDecode = Base64.decode(xmlPullParser.getText(), 0);
                UUID uuid3 = PsshAtomUtil.parseUuid(bArrDecode);
                if (uuid3 == null) {
                    Log.w(TAG, "Skipping malformed cenc:pssh data");
                    uuid = uuid3;
                    bArrBuildPsshAtom = null;
                } else {
                    bArrBuildPsshAtom = bArrDecode;
                    uuid = uuid3;
                }
            } else if (bArrBuildPsshAtom == null) {
                UUID uuid4 = C.PLAYREADY_UUID;
                if (uuid4.equals(uuid) && XmlPullParserUtil.isStartTag(xmlPullParser, "mspr:pro") && xmlPullParser.next() == 4) {
                    bArrBuildPsshAtom = PsshAtomUtil.buildPsshAtom(uuid4, Base64.decode(xmlPullParser.getText(), 0));
                } else {
                    maybeSkipTag(xmlPullParser);
                }
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "ContentProtection"));
        return Pair.create(attributeValue, uuid != null ? new DrmInitData.SchemeData(uuid, attributeValue2, MimeTypes.VIDEO_MP4, bArrBuildPsshAtom) : null);
    }

    public final int parseContentType(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "contentType");
        if (!TextUtils.isEmpty(attributeValue)) {
            if (MimeTypes.BASE_TYPE_AUDIO.equals(attributeValue)) {
                return 1;
            }
            if (MimeTypes.BASE_TYPE_VIDEO.equals(attributeValue)) {
                return 2;
            }
            if ("text".equals(attributeValue)) {
                return 3;
            }
            if ("image".equals(attributeValue)) {
                return 4;
            }
        }
        return -1;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 45401. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    public final com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r145, android.net.Uri r146) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instruction units count: 4540
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, android.net.Uri):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    public final RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) {
        long j;
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, str2);
        if (attributeValue2 != null) {
            String[] strArrSplit = attributeValue2.split("-");
            j = Long.parseLong(strArrSplit[0]);
            long j2 = strArrSplit.length == 2 ? (Long.parseLong(strArrSplit[1]) - j) + 1 : -1L;
            return new RangedUri(attributeValue, j, j2);
        }
        j = 0;
        return new RangedUri(attributeValue, j, j2);
    }

    public final int parseRoleFlagsFromDashRoleScheme(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
            case "subtitle":
            case "forced_subtitle":
            case "forced-subtitle":
                return 128;
            case "description":
                return 512;
            case "enhanced-audio-intelligibility":
                return 2048;
            case "alternate":
                return 2;
            case "dub":
                return 16;
            case "main":
                return 1;
            case "sign":
                return 256;
            case "caption":
                return 64;
            case "commentary":
                return 8;
            case "emergency":
                return 32;
            case "supplementary":
                return 4;
            default:
                return 0;
        }
    }

    public final int parseRoleFlagsFromProperties(List<Descriptor> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/trickmode", list.get(i2).schemeIdUri)) {
                i |= 16384;
            }
        }
        return i;
    }

    public final SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, @Nullable SegmentBase.SingleSegmentBase singleSegmentBase) throws XmlPullParserException, IOException {
        long j;
        long j2;
        long j3 = parseLong(xmlPullParser, "timescale", singleSegmentBase != null ? singleSegmentBase.timescale : 1L);
        long j4 = parseLong(xmlPullParser, "presentationTimeOffset", singleSegmentBase != null ? singleSegmentBase.presentationTimeOffset : 0L);
        long j5 = singleSegmentBase != null ? singleSegmentBase.indexStart : 0L;
        long j6 = singleSegmentBase != null ? singleSegmentBase.indexLength : 0L;
        String attributeValue = xmlPullParser.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] strArrSplit = attributeValue.split("-");
            long j7 = Long.parseLong(strArrSplit[0]);
            j = (Long.parseLong(strArrSplit[1]) - j7) + 1;
            j2 = j7;
        } else {
            j = j6;
            j2 = j5;
        }
        RangedUri rangedUrl = singleSegmentBase != null ? singleSegmentBase.initialization : null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUrl = parseRangedUrl(xmlPullParser, "sourceURL", "range");
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentBase"));
        return new SegmentBase.SingleSegmentBase(rangedUrl, j3, j4, j2, j);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00b5 A[PHI: r0 r20
  0x00b5: PHI (r0v7 java.util.List<com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTimelineElement>) = 
  (r0v6 java.util.List<com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTimelineElement>)
  (r0v9 java.util.List<com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTimelineElement>)
 binds: [B:32:0x009b, B:39:0x00a9] A[DONT_GENERATE, DONT_INLINE]
  0x00b5: PHI (r20v3 com.google.android.exoplayer2.source.dash.manifest.RangedUri) = 
  (r20v2 com.google.android.exoplayer2.source.dash.manifest.RangedUri)
  (r20v6 com.google.android.exoplayer2.source.dash.manifest.RangedUri)
 binds: [B:32:0x009b, B:39:0x00a9] A[DONT_GENERATE, DONT_INLINE]] */
    public final SegmentBase.SegmentList parseSegmentList(XmlPullParser xmlPullParser, @Nullable SegmentBase.SegmentList segmentList, long j, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException {
        RangedUri rangedUri;
        List<RangedUri> list;
        List<SegmentBase.SegmentTimelineElement> list2;
        long j6 = parseLong(xmlPullParser, "timescale", segmentList != null ? segmentList.timescale : 1L);
        long j7 = parseLong(xmlPullParser, "presentationTimeOffset", segmentList != null ? segmentList.presentationTimeOffset : 0L);
        long j8 = parseLong(xmlPullParser, TypedValues.TransitionType.S_DURATION, segmentList != null ? segmentList.duration : C.TIME_UNSET);
        long j9 = parseLong(xmlPullParser, "startNumber", segmentList != null ? segmentList.startNumber : 1L);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(j3, j4);
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = null;
        ArrayList arrayList = null;
        RangedUri rangedUrl = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUrl = parseRangedUrl(xmlPullParser, "sourceURL", "range");
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                segmentTimeline = parseSegmentTimeline(xmlPullParser, j6, j2);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentURL")) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                ArrayList arrayList2 = arrayList;
                arrayList2.add(parseRangedUrl(xmlPullParser, "media", "mediaRange"));
                arrayList = arrayList2;
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentList"));
        if (segmentList == null) {
            rangedUri = rangedUrl;
            list = arrayList;
            list2 = segmentTimeline;
        } else {
            if (rangedUrl == null) {
                rangedUrl = segmentList.initialization;
            }
            if (segmentTimeline == null) {
                segmentTimeline = segmentList.segmentTimeline;
            }
            if (arrayList != null) {
                rangedUri = rangedUrl;
                list = arrayList;
                list2 = segmentTimeline;
            } else {
                list2 = segmentTimeline;
                rangedUri = rangedUrl;
                list = segmentList.mediaSegments;
            }
        }
        return new SegmentBase.SegmentList(rangedUri, j6, j7, j9, j8, list2, finalAvailabilityTimeOffset, list, Util.msToUs(j5), Util.msToUs(j));
    }

    public final SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, @Nullable SegmentBase.SegmentTemplate segmentTemplate, List<Descriptor> list, long j, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException {
        long j6;
        long j7 = parseLong(xmlPullParser, "timescale", segmentTemplate != null ? segmentTemplate.timescale : 1L);
        long j8 = parseLong(xmlPullParser, "presentationTimeOffset", segmentTemplate != null ? segmentTemplate.presentationTimeOffset : 0L);
        long j9 = parseLong(xmlPullParser, TypedValues.TransitionType.S_DURATION, segmentTemplate != null ? segmentTemplate.duration : C.TIME_UNSET);
        long j10 = parseLong(xmlPullParser, "startNumber", segmentTemplate != null ? segmentTemplate.startNumber : 1L);
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                j6 = -1;
                break;
            }
            Descriptor descriptor = list.get(i);
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/last-segment-number", descriptor.schemeIdUri)) {
                j6 = Long.parseLong(descriptor.value);
                break;
            }
            i++;
        }
        long j11 = j6;
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(j3, j4);
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = null;
        UrlTemplate urlTemplateCompile = segmentTemplate != null ? segmentTemplate.mediaTemplate : null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "media");
        if (attributeValue != null) {
            urlTemplateCompile = UrlTemplate.compile(attributeValue);
        }
        UrlTemplate urlTemplate = urlTemplateCompile;
        UrlTemplate urlTemplateCompile2 = segmentTemplate != null ? segmentTemplate.initializationTemplate : null;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "initialization");
        if (attributeValue2 != null) {
            urlTemplateCompile2 = UrlTemplate.compile(attributeValue2);
        }
        UrlTemplate urlTemplate2 = urlTemplateCompile2;
        RangedUri rangedUrl = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUrl = parseRangedUrl(xmlPullParser, "sourceURL", "range");
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                segmentTimeline = parseSegmentTimeline(xmlPullParser, j7, j2);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTemplate"));
        if (segmentTemplate != null) {
            if (rangedUrl == null) {
                rangedUrl = segmentTemplate.initialization;
            }
            if (segmentTimeline == null) {
                segmentTimeline = segmentTemplate.segmentTimeline;
            }
        }
        return new SegmentBase.SegmentTemplate(rangedUrl, j7, j8, j10, j11, j9, segmentTimeline, finalAvailabilityTimeOffset, urlTemplate2, urlTemplate, Util.msToUs(j5), Util.msToUs(j));
    }

    public final List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser, long j, long j2) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        long jAddSegmentTimelineElementsToList = 0;
        long j3 = -9223372036854775807L;
        boolean z = false;
        int i = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.LATITUDE_SOUTH)) {
                long j4 = parseLong(xmlPullParser, "t", C.TIME_UNSET);
                if (z) {
                    jAddSegmentTimelineElementsToList = addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, j3, i, j4);
                }
                if (j4 == C.TIME_UNSET) {
                    j4 = jAddSegmentTimelineElementsToList;
                }
                j3 = parseLong(xmlPullParser, "d", C.TIME_UNSET);
                i = parseInt(xmlPullParser, "r", 0);
                jAddSegmentTimelineElementsToList = j4;
                z = true;
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        if (z) {
            addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, j3, i, Util.scaleLargeTimestamp(j2, j, 1000L));
        }
        return arrayList;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public DashManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            if (xmlPullParserNewPullParser.next() == 2 && "MPD".equals(xmlPullParserNewPullParser.getName())) {
                return parseMediaPresentationDescription(xmlPullParserNewPullParser, uri);
            }
            throw ParserException.createForMalformedManifest("inputStream does not contain a valid media presentation description", null);
        } catch (XmlPullParserException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }
}
