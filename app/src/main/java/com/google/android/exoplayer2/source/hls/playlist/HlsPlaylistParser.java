package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.hls.HlsTrackMetadataEntry;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.Iterables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* JADX INFO: loaded from: classes.dex */
public final class HlsPlaylistParser implements ParsingLoadable.Parser<HlsPlaylist> {
    private static final String ATTR_CLOSED_CAPTIONS_NONE = "CLOSED-CAPTIONS=NONE";
    private static final String BOOLEAN_FALSE = "NO";
    private static final String BOOLEAN_TRUE = "YES";
    private static final String KEYFORMAT_IDENTITY = "identity";
    private static final String KEYFORMAT_PLAYREADY = "com.microsoft.playready";
    private static final String KEYFORMAT_WIDEVINE_PSSH_BINARY = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed";
    private static final String KEYFORMAT_WIDEVINE_PSSH_JSON = "com.widevine";
    private static final String LOG_TAG = "HlsPlaylistParser";
    private static final String METHOD_AES_128 = "AES-128";
    private static final String METHOD_NONE = "NONE";
    private static final String METHOD_SAMPLE_AES = "SAMPLE-AES";
    private static final String METHOD_SAMPLE_AES_CENC = "SAMPLE-AES-CENC";
    private static final String METHOD_SAMPLE_AES_CTR = "SAMPLE-AES-CTR";
    private static final String PLAYLIST_HEADER = "#EXTM3U";
    private static final String TAG_BYTERANGE = "#EXT-X-BYTERANGE";
    private static final String TAG_DEFINE = "#EXT-X-DEFINE";
    private static final String TAG_DISCONTINUITY = "#EXT-X-DISCONTINUITY";
    private static final String TAG_DISCONTINUITY_SEQUENCE = "#EXT-X-DISCONTINUITY-SEQUENCE";
    private static final String TAG_ENDLIST = "#EXT-X-ENDLIST";
    private static final String TAG_GAP = "#EXT-X-GAP";
    private static final String TAG_IFRAME = "#EXT-X-I-FRAMES-ONLY";
    private static final String TAG_INDEPENDENT_SEGMENTS = "#EXT-X-INDEPENDENT-SEGMENTS";
    private static final String TAG_INIT_SEGMENT = "#EXT-X-MAP";
    private static final String TAG_I_FRAME_STREAM_INF = "#EXT-X-I-FRAME-STREAM-INF";
    private static final String TAG_KEY = "#EXT-X-KEY";
    private static final String TAG_MEDIA = "#EXT-X-MEDIA";
    private static final String TAG_MEDIA_DURATION = "#EXTINF";
    private static final String TAG_MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";
    private static final String TAG_PART = "#EXT-X-PART";
    private static final String TAG_PART_INF = "#EXT-X-PART-INF";
    private static final String TAG_PLAYLIST_TYPE = "#EXT-X-PLAYLIST-TYPE";
    private static final String TAG_PREFIX = "#EXT";
    private static final String TAG_PRELOAD_HINT = "#EXT-X-PRELOAD-HINT";
    private static final String TAG_PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";
    private static final String TAG_RENDITION_REPORT = "#EXT-X-RENDITION-REPORT";
    private static final String TAG_SERVER_CONTROL = "#EXT-X-SERVER-CONTROL";
    private static final String TAG_SESSION_KEY = "#EXT-X-SESSION-KEY";
    private static final String TAG_SKIP = "#EXT-X-SKIP";
    private static final String TAG_START = "#EXT-X-START";
    private static final String TAG_STREAM_INF = "#EXT-X-STREAM-INF";
    private static final String TAG_TARGET_DURATION = "#EXT-X-TARGETDURATION";
    private static final String TAG_VERSION = "#EXT-X-VERSION";
    private static final String TYPE_AUDIO = "AUDIO";
    private static final String TYPE_CLOSED_CAPTIONS = "CLOSED-CAPTIONS";
    private static final String TYPE_MAP = "MAP";
    private static final String TYPE_PART = "PART";
    private static final String TYPE_SUBTITLES = "SUBTITLES";
    private static final String TYPE_VIDEO = "VIDEO";
    private final HlsMultivariantPlaylist multivariantPlaylist;

    @Nullable
    private final HlsMediaPlaylist previousMediaPlaylist;
    private static final Pattern REGEX_AVERAGE_BANDWIDTH = Pattern.compile("AVERAGE-BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_VIDEO = Pattern.compile("VIDEO=\"(.+?)\"");
    private static final Pattern REGEX_AUDIO = Pattern.compile("AUDIO=\"(.+?)\"");
    private static final Pattern REGEX_SUBTITLES = Pattern.compile("SUBTITLES=\"(.+?)\"");
    private static final Pattern REGEX_CLOSED_CAPTIONS = Pattern.compile("CLOSED-CAPTIONS=\"(.+?)\"");
    private static final Pattern REGEX_BANDWIDTH = Pattern.compile("[^-]BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_CHANNELS = Pattern.compile("CHANNELS=\"(.+?)\"");
    private static final Pattern REGEX_CODECS = Pattern.compile("CODECS=\"(.+?)\"");
    private static final Pattern REGEX_RESOLUTION = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
    private static final Pattern REGEX_FRAME_RATE = Pattern.compile("FRAME-RATE=([\\d\\.]+)\\b");
    private static final Pattern REGEX_TARGET_DURATION = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    private static final Pattern REGEX_ATTR_DURATION = Pattern.compile("DURATION=([\\d\\.]+)\\b");
    private static final Pattern REGEX_PART_TARGET_DURATION = Pattern.compile("PART-TARGET=([\\d\\.]+)\\b");
    private static final Pattern REGEX_VERSION = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    private static final Pattern REGEX_PLAYLIST_TYPE = Pattern.compile("#EXT-X-PLAYLIST-TYPE:(.+)\\b");
    private static final Pattern REGEX_CAN_SKIP_UNTIL = Pattern.compile("CAN-SKIP-UNTIL=([\\d\\.]+)\\b");
    private static final Pattern REGEX_CAN_SKIP_DATE_RANGES = compileBooleanAttrPattern("CAN-SKIP-DATERANGES");
    private static final Pattern REGEX_SKIPPED_SEGMENTS = Pattern.compile("SKIPPED-SEGMENTS=(\\d+)\\b");
    private static final Pattern REGEX_HOLD_BACK = Pattern.compile("[:|,]HOLD-BACK=([\\d\\.]+)\\b");
    private static final Pattern REGEX_PART_HOLD_BACK = Pattern.compile("PART-HOLD-BACK=([\\d\\.]+)\\b");
    private static final Pattern REGEX_CAN_BLOCK_RELOAD = compileBooleanAttrPattern("CAN-BLOCK-RELOAD");
    private static final Pattern REGEX_MEDIA_SEQUENCE = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    private static final Pattern REGEX_MEDIA_DURATION = Pattern.compile("#EXTINF:([\\d\\.]+)\\b");
    private static final Pattern REGEX_MEDIA_TITLE = Pattern.compile("#EXTINF:[\\d\\.]+\\b,(.+)");
    private static final Pattern REGEX_LAST_MSN = Pattern.compile("LAST-MSN=(\\d+)\\b");
    private static final Pattern REGEX_LAST_PART = Pattern.compile("LAST-PART=(\\d+)\\b");
    private static final Pattern REGEX_TIME_OFFSET = Pattern.compile("TIME-OFFSET=(-?[\\d\\.]+)\\b");
    private static final Pattern REGEX_BYTERANGE = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    private static final Pattern REGEX_ATTR_BYTERANGE = Pattern.compile("BYTERANGE=\"(\\d+(?:@\\d+)?)\\b\"");
    private static final Pattern REGEX_BYTERANGE_START = Pattern.compile("BYTERANGE-START=(\\d+)\\b");
    private static final Pattern REGEX_BYTERANGE_LENGTH = Pattern.compile("BYTERANGE-LENGTH=(\\d+)\\b");
    private static final Pattern REGEX_METHOD = Pattern.compile("METHOD=(NONE|AES-128|SAMPLE-AES|SAMPLE-AES-CENC|SAMPLE-AES-CTR)\\s*(?:,|$)");
    private static final Pattern REGEX_KEYFORMAT = Pattern.compile("KEYFORMAT=\"(.+?)\"");
    private static final Pattern REGEX_KEYFORMATVERSIONS = Pattern.compile("KEYFORMATVERSIONS=\"(.+?)\"");
    private static final Pattern REGEX_URI = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern REGEX_IV = Pattern.compile("IV=([^,.*]+)");
    private static final Pattern REGEX_TYPE = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
    private static final Pattern REGEX_PRELOAD_HINT_TYPE = Pattern.compile("TYPE=(PART|MAP)");
    private static final Pattern REGEX_LANGUAGE = Pattern.compile("LANGUAGE=\"(.+?)\"");
    private static final Pattern REGEX_NAME = Pattern.compile("NAME=\"(.+?)\"");
    private static final Pattern REGEX_GROUP_ID = Pattern.compile("GROUP-ID=\"(.+?)\"");
    private static final Pattern REGEX_CHARACTERISTICS = Pattern.compile("CHARACTERISTICS=\"(.+?)\"");
    private static final Pattern REGEX_INSTREAM_ID = Pattern.compile("INSTREAM-ID=\"((?:CC|SERVICE)\\d+)\"");
    private static final Pattern REGEX_AUTOSELECT = compileBooleanAttrPattern("AUTOSELECT");
    private static final Pattern REGEX_DEFAULT = compileBooleanAttrPattern("DEFAULT");
    private static final Pattern REGEX_FORCED = compileBooleanAttrPattern("FORCED");
    private static final Pattern REGEX_INDEPENDENT = compileBooleanAttrPattern("INDEPENDENT");
    private static final Pattern REGEX_GAP = compileBooleanAttrPattern("GAP");
    private static final Pattern REGEX_PRECISE = compileBooleanAttrPattern("PRECISE");
    private static final Pattern REGEX_VALUE = Pattern.compile("VALUE=\"(.+?)\"");
    private static final Pattern REGEX_IMPORT = Pattern.compile("IMPORT=\"(.+?)\"");
    private static final Pattern REGEX_VARIABLE_REFERENCE = Pattern.compile("\\{\\$([a-zA-Z0-9\\-_]+)\\}");

    public static final class DeltaUpdateException extends IOException {
    }

    public static class LineIterator {
        private final Queue<String> extraLines;

        @Nullable
        private String next;
        private final BufferedReader reader;

        public LineIterator(Queue<String> queue, BufferedReader bufferedReader) {
            this.extraLines = queue;
            this.reader = bufferedReader;
        }

        @EnsuresNonNullIf(expression = {"next"}, result = true)
        public boolean hasNext() throws IOException {
            String strTrim;
            if (this.next != null) {
                return true;
            }
            if (!this.extraLines.isEmpty()) {
                this.next = (String) Assertions.checkNotNull(this.extraLines.poll());
                return true;
            }
            do {
                String line = this.reader.readLine();
                this.next = line;
                if (line == null) {
                    return false;
                }
                strTrim = line.trim();
                this.next = strTrim;
            } while (strTrim.isEmpty());
            return true;
        }

        public String next() throws IOException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String str = this.next;
            this.next = null;
            return str;
        }
    }

    public HlsPlaylistParser() {
        this(HlsMultivariantPlaylist.EMPTY, null);
    }

    private static boolean checkPlaylistHeader(BufferedReader bufferedReader) throws IOException {
        int i = bufferedReader.read();
        if (i == 239) {
            if (bufferedReader.read() != 187 || bufferedReader.read() != 191) {
                return false;
            }
            i = bufferedReader.read();
        }
        int iSkipIgnorableWhitespace = skipIgnorableWhitespace(bufferedReader, true, i);
        for (int i2 = 0; i2 < 7; i2++) {
            if (iSkipIgnorableWhitespace != PLAYLIST_HEADER.charAt(i2)) {
                return false;
            }
            iSkipIgnorableWhitespace = bufferedReader.read();
        }
        return Util.isLinebreak(skipIgnorableWhitespace(bufferedReader, false, iSkipIgnorableWhitespace));
    }

    private static Pattern compileBooleanAttrPattern(String str) {
        return Pattern.compile(str + "=(" + BOOLEAN_FALSE + "|" + BOOLEAN_TRUE + ")");
    }

    private static DrmInitData getPlaylistProtectionSchemes(@Nullable String str, DrmInitData.SchemeData[] schemeDataArr) {
        DrmInitData.SchemeData[] schemeDataArr2 = new DrmInitData.SchemeData[schemeDataArr.length];
        for (int i = 0; i < schemeDataArr.length; i++) {
            schemeDataArr2[i] = schemeDataArr[i].copyWithData(null);
        }
        return new DrmInitData(str, schemeDataArr2);
    }

    @Nullable
    private static String getSegmentEncryptionIV(long j, @Nullable String str, @Nullable String str2) {
        if (str == null) {
            return null;
        }
        return str2 != null ? str2 : Long.toHexString(j);
    }

    @Nullable
    private static HlsMultivariantPlaylist.Variant getVariantWithAudioGroup(ArrayList<HlsMultivariantPlaylist.Variant> arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            HlsMultivariantPlaylist.Variant variant = arrayList.get(i);
            if (str.equals(variant.audioGroupId)) {
                return variant;
            }
        }
        return null;
    }

    @Nullable
    private static HlsMultivariantPlaylist.Variant getVariantWithSubtitleGroup(ArrayList<HlsMultivariantPlaylist.Variant> arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            HlsMultivariantPlaylist.Variant variant = arrayList.get(i);
            if (str.equals(variant.subtitleGroupId)) {
                return variant;
            }
        }
        return null;
    }

    @Nullable
    private static HlsMultivariantPlaylist.Variant getVariantWithVideoGroup(ArrayList<HlsMultivariantPlaylist.Variant> arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            HlsMultivariantPlaylist.Variant variant = arrayList.get(i);
            if (str.equals(variant.videoGroupId)) {
                return variant;
            }
        }
        return null;
    }

    private static double parseDoubleAttr(String str, Pattern pattern) throws ParserException {
        return Double.parseDouble(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    @Nullable
    private static DrmInitData.SchemeData parseDrmSchemeData(String str, String str2, Map<String, String> map) throws ParserException {
        String optionalStringAttr = parseOptionalStringAttr(str, REGEX_KEYFORMATVERSIONS, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, map);
        if (KEYFORMAT_WIDEVINE_PSSH_BINARY.equals(str2)) {
            String stringAttr = parseStringAttr(str, REGEX_URI, map);
            return new DrmInitData.SchemeData(C.WIDEVINE_UUID, MimeTypes.VIDEO_MP4, Base64.decode(stringAttr.substring(stringAttr.indexOf(44)), 0));
        }
        if (KEYFORMAT_WIDEVINE_PSSH_JSON.equals(str2)) {
            return new DrmInitData.SchemeData(C.WIDEVINE_UUID, "hls", Util.getUtf8Bytes(str));
        }
        if (!KEYFORMAT_PLAYREADY.equals(str2) || !IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(optionalStringAttr)) {
            return null;
        }
        String stringAttr2 = parseStringAttr(str, REGEX_URI, map);
        byte[] bArrDecode = Base64.decode(stringAttr2.substring(stringAttr2.indexOf(44)), 0);
        UUID uuid = C.PLAYREADY_UUID;
        return new DrmInitData.SchemeData(uuid, MimeTypes.VIDEO_MP4, PsshAtomUtil.buildPsshAtom(uuid, bArrDecode));
    }

    private static String parseEncryptionScheme(String str) {
        return (METHOD_SAMPLE_AES_CENC.equals(str) || METHOD_SAMPLE_AES_CTR.equals(str)) ? C.CENC_TYPE_cenc : C.CENC_TYPE_cbcs;
    }

    private static int parseIntAttr(String str, Pattern pattern) throws ParserException {
        return Integer.parseInt(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    private static long parseLongAttr(String str, Pattern pattern) throws ParserException {
        return Long.parseLong(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    /* JADX WARN: Code duplicated, block: B:113:0x0315 A[PHI: r79
  0x0315: PHI (r79v25 java.lang.String) = (r79v23 java.lang.String), (r79v1 java.lang.String) binds: [B:119:0x032b, B:111:0x030a] A[DONT_GENERATE, DONT_INLINE]] */
    private static HlsMediaPlaylist parseMediaPlaylist(HlsMultivariantPlaylist hlsMultivariantPlaylist, @Nullable HlsMediaPlaylist hlsMediaPlaylist, LineIterator lineIterator, String str) throws IOException {
        ArrayList arrayList;
        int i;
        HlsMediaPlaylist.Part part;
        ArrayList arrayList2;
        ArrayList arrayList3;
        long j;
        long j2;
        HashMap map;
        DrmInitData drmInitData;
        long j3;
        String stringAttr;
        hlsMultivariantPlaylist = hlsMultivariantPlaylist;
        hlsMediaPlaylist = hlsMediaPlaylist;
        boolean z = hlsMultivariantPlaylist.hasIndependentSegments;
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        HlsMediaPlaylist.ServerControl serverControl = new HlsMediaPlaylist.ServerControl(C.TIME_UNSET, false, C.TIME_UNSET, C.TIME_UNSET, false);
        TreeMap treeMap = new TreeMap();
        String str2 = "";
        boolean z2 = false;
        boolean z3 = z;
        HlsMediaPlaylist.ServerControl serverControl2 = serverControl;
        String optionalStringAttr = "";
        long jMsToUs = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        long longAttr = 0;
        long timeSecondsToUs = 0;
        long j8 = 0;
        int i2 = 0;
        long j9 = C.TIME_UNSET;
        boolean optionalBooleanAttribute = false;
        boolean z4 = false;
        int i3 = 0;
        int intAttr = 1;
        long intAttr2 = C.TIME_UNSET;
        long doubleAttr = C.TIME_UNSET;
        boolean z5 = false;
        DrmInitData playlistProtectionSchemes = null;
        DrmInitData drmInitData2 = null;
        boolean z6 = false;
        String str3 = null;
        long j10 = -1;
        String str4 = null;
        String encryptionScheme = null;
        int i4 = 0;
        boolean z7 = false;
        HlsMediaPlaylist.Segment segment = null;
        ArrayList arrayList8 = arrayList5;
        HlsMediaPlaylist.Part part2 = null;
        while (lineIterator.hasNext()) {
            String next = lineIterator.next();
            if (next.startsWith(TAG_PREFIX)) {
                arrayList7.add(next);
            }
            if (next.startsWith(TAG_PLAYLIST_TYPE)) {
                String stringAttr2 = parseStringAttr(next, REGEX_PLAYLIST_TYPE, map2);
                if ("VOD".equals(stringAttr2)) {
                    i2 = 1;
                } else if ("EVENT".equals(stringAttr2)) {
                    i2 = 2;
                }
            } else if (next.equals(TAG_IFRAME)) {
                z7 = true;
            } else {
                if (next.startsWith(TAG_START)) {
                    arrayList = arrayList4;
                    long doubleAttr2 = (long) (parseDoubleAttr(next, REGEX_TIME_OFFSET) * 1000000.0d);
                    optionalBooleanAttribute = parseOptionalBooleanAttribute(next, REGEX_PRECISE, z2);
                    j9 = doubleAttr2;
                } else {
                    arrayList = arrayList4;
                    if (next.startsWith(TAG_SERVER_CONTROL)) {
                        serverControl2 = parseServerControl(next);
                    } else if (next.startsWith(TAG_PART_INF)) {
                        doubleAttr = (long) (parseDoubleAttr(next, REGEX_PART_TARGET_DURATION) * 1000000.0d);
                    } else if (next.startsWith(TAG_INIT_SEGMENT)) {
                        String stringAttr3 = parseStringAttr(next, REGEX_URI, map2);
                        String optionalStringAttr2 = parseOptionalStringAttr(next, REGEX_ATTR_BYTERANGE, map2);
                        if (optionalStringAttr2 != null) {
                            String[] strArrSplit = Util.split(optionalStringAttr2, "@");
                            j10 = Long.parseLong(strArrSplit[z2 ? 1 : 0]);
                            if (strArrSplit.length > 1) {
                                j5 = Long.parseLong(strArrSplit[1]);
                            }
                        }
                        if (j10 == -1) {
                            j5 = 0;
                        }
                        String str5 = str3;
                        String str6 = str4;
                        if (str5 != null && str6 == null) {
                            throw ParserException.createForMalformedManifest("The encryption IV attribute must be present when an initialization segment is encrypted with METHOD=AES-128.", null);
                        }
                        segment = new HlsMediaPlaylist.Segment(stringAttr3, j5, j10, str5, str6);
                        if (j10 != -1) {
                            j5 += j10;
                        }
                        str4 = str6;
                        str3 = str5;
                        arrayList4 = arrayList;
                        j10 = -1;
                    } else {
                        str3 = str3;
                        str4 = str4;
                        if (next.startsWith(TAG_TARGET_DURATION)) {
                            intAttr2 = 1000000 * ((long) parseIntAttr(next, REGEX_TARGET_DURATION));
                        } else if (next.startsWith(TAG_MEDIA_SEQUENCE)) {
                            longAttr = parseLongAttr(next, REGEX_MEDIA_SEQUENCE);
                            str4 = str4;
                            str3 = str3;
                            j4 = longAttr;
                            arrayList4 = arrayList;
                            z2 = false;
                        } else if (next.startsWith(TAG_VERSION)) {
                            intAttr = parseIntAttr(next, REGEX_VERSION);
                        } else {
                            if (next.startsWith(TAG_DEFINE)) {
                                String optionalStringAttr3 = parseOptionalStringAttr(next, REGEX_IMPORT, map2);
                                if (optionalStringAttr3 != null) {
                                    String str7 = hlsMultivariantPlaylist.variableDefinitions.get(optionalStringAttr3);
                                    if (str7 != null) {
                                        map2.put(optionalStringAttr3, str7);
                                    }
                                } else {
                                    map2.put(parseStringAttr(next, REGEX_NAME, map2), parseStringAttr(next, REGEX_VALUE, map2));
                                }
                                i = i2;
                                map2 = map2;
                                arrayList6 = arrayList6;
                                str2 = str2;
                                encryptionScheme = encryptionScheme;
                                longAttr = longAttr;
                                part = part2;
                                arrayList7 = arrayList7;
                                arrayList2 = arrayList;
                            } else if (next.startsWith(TAG_MEDIA_DURATION)) {
                                timeSecondsToUs = parseTimeSecondsToUs(next, REGEX_MEDIA_DURATION);
                                optionalStringAttr = parseOptionalStringAttr(next, REGEX_MEDIA_TITLE, str2, map2);
                            } else if (next.startsWith(TAG_SKIP)) {
                                int intAttr3 = parseIntAttr(next, REGEX_SKIPPED_SEGMENTS);
                                Assertions.checkState(hlsMediaPlaylist != null && arrayList.isEmpty());
                                int i5 = (int) (j4 - ((HlsMediaPlaylist) Util.castNonNull(hlsMediaPlaylist)).mediaSequence);
                                int i6 = intAttr3 + i5;
                                if (i5 < 0 || i6 > hlsMediaPlaylist.segments.size()) {
                                    throw new DeltaUpdateException();
                                }
                                String str8 = str2;
                                str4 = str4;
                                long j11 = j7;
                                while (i5 < i6) {
                                    HlsMediaPlaylist.Segment segmentCopyWith = hlsMediaPlaylist.segments.get(i5);
                                    int i7 = i6;
                                    String str9 = str8;
                                    if (j4 != hlsMediaPlaylist.mediaSequence) {
                                        segmentCopyWith = segmentCopyWith.copyWith(j11, (hlsMediaPlaylist.discontinuitySequence - i3) + segmentCopyWith.relativeDiscontinuitySequence);
                                    }
                                    ArrayList arrayList9 = arrayList;
                                    arrayList9.add(segmentCopyWith);
                                    long j12 = j11 + segmentCopyWith.durationUs;
                                    long j13 = segmentCopyWith.byteRangeLength;
                                    if (j13 != -1) {
                                        j5 = segmentCopyWith.byteRangeOffset + j13;
                                    }
                                    int i8 = segmentCopyWith.relativeDiscontinuitySequence;
                                    HlsMediaPlaylist.Segment segment2 = segmentCopyWith.initializationSegment;
                                    DrmInitData drmInitData3 = segmentCopyWith.drmInitData;
                                    String str10 = segmentCopyWith.fullSegmentEncryptionKeyUri;
                                    String str11 = segmentCopyWith.encryptionIV;
                                    if (str11 == null || !str11.equals(Long.toHexString(longAttr))) {
                                        str4 = segmentCopyWith.encryptionIV;
                                    }
                                    longAttr++;
                                    i5++;
                                    segment = segment2;
                                    str3 = str10;
                                    arrayList = arrayList9;
                                    i4 = i8;
                                    i6 = i7;
                                    j6 = j12;
                                    str8 = str9;
                                    hlsMediaPlaylist = hlsMediaPlaylist;
                                    drmInitData2 = drmInitData3;
                                    j11 = j6;
                                }
                                hlsMultivariantPlaylist = hlsMultivariantPlaylist;
                                hlsMediaPlaylist = hlsMediaPlaylist;
                                j7 = j11;
                                str2 = str8;
                                str3 = str3;
                                arrayList4 = arrayList;
                                z2 = false;
                            } else {
                                str2 = str2;
                                if (next.startsWith(TAG_KEY)) {
                                    String stringAttr4 = parseStringAttr(next, REGEX_METHOD, map2);
                                    String optionalStringAttr4 = parseOptionalStringAttr(next, REGEX_KEYFORMAT, KEYFORMAT_IDENTITY, map2);
                                    if (METHOD_NONE.equals(stringAttr4)) {
                                        treeMap.clear();
                                        stringAttr = null;
                                        drmInitData2 = null;
                                        str4 = null;
                                    } else {
                                        String optionalStringAttr5 = parseOptionalStringAttr(next, REGEX_IV, map2);
                                        if (!KEYFORMAT_IDENTITY.equals(optionalStringAttr4)) {
                                            String str12 = encryptionScheme;
                                            encryptionScheme = str12 == null ? parseEncryptionScheme(stringAttr4) : str12;
                                            DrmInitData.SchemeData drmSchemeData = parseDrmSchemeData(next, optionalStringAttr4, map2);
                                            if (drmSchemeData != null) {
                                                treeMap.put(optionalStringAttr4, drmSchemeData);
                                                str4 = optionalStringAttr5;
                                                stringAttr = null;
                                                drmInitData2 = null;
                                            } else {
                                                str4 = optionalStringAttr5;
                                                stringAttr = null;
                                            }
                                        } else if (METHOD_AES_128.equals(stringAttr4)) {
                                            stringAttr = parseStringAttr(next, REGEX_URI, map2);
                                            str4 = optionalStringAttr5;
                                        } else {
                                            str4 = optionalStringAttr5;
                                            stringAttr = null;
                                        }
                                    }
                                    hlsMediaPlaylist = hlsMediaPlaylist;
                                    str3 = stringAttr;
                                    arrayList4 = arrayList;
                                    str2 = str2;
                                    z2 = false;
                                    hlsMultivariantPlaylist = hlsMultivariantPlaylist;
                                } else {
                                    encryptionScheme = encryptionScheme;
                                    if (next.startsWith(TAG_BYTERANGE)) {
                                        String[] strArrSplit2 = Util.split(parseStringAttr(next, REGEX_BYTERANGE, map2), "@");
                                        j10 = Long.parseLong(strArrSplit2[0]);
                                        if (strArrSplit2.length > 1) {
                                            j5 = Long.parseLong(strArrSplit2[1]);
                                        }
                                    } else {
                                        if (next.startsWith(TAG_DISCONTINUITY_SEQUENCE)) {
                                            i3 = Integer.parseInt(next.substring(next.indexOf(58) + 1));
                                            z4 = true;
                                        } else if (next.equals(TAG_DISCONTINUITY)) {
                                            i4++;
                                        } else {
                                            if (next.startsWith(TAG_PROGRAM_DATE_TIME)) {
                                                if (jMsToUs == 0) {
                                                    jMsToUs = Util.msToUs(Util.parseXsDateTime(next.substring(next.indexOf(58) + 1))) - j7;
                                                } else {
                                                    i = i2;
                                                    arrayList2 = arrayList;
                                                }
                                            } else if (next.equals(TAG_GAP)) {
                                                z6 = true;
                                            } else if (next.equals(TAG_INDEPENDENT_SEGMENTS)) {
                                                z3 = true;
                                            } else if (next.equals(TAG_ENDLIST)) {
                                                z5 = true;
                                            } else {
                                                if (next.startsWith(TAG_RENDITION_REPORT)) {
                                                    i = i2;
                                                    arrayList3 = arrayList;
                                                    arrayList6.add(new HlsMediaPlaylist.RenditionReport(Uri.parse(UriUtil.resolve(str, parseStringAttr(next, REGEX_URI, map2))), parseOptionalLongAttr(next, REGEX_LAST_MSN, -1L), parseOptionalIntAttr(next, REGEX_LAST_PART, -1)));
                                                } else {
                                                    i = i2;
                                                    if (!next.startsWith(TAG_PRELOAD_HINT)) {
                                                        arrayList3 = arrayList;
                                                        longAttr = longAttr;
                                                        if (next.startsWith(TAG_PART)) {
                                                            String segmentEncryptionIV = getSegmentEncryptionIV(longAttr, str3, str4);
                                                            String stringAttr5 = parseStringAttr(next, REGEX_URI, map2);
                                                            HlsMediaPlaylist.Part part3 = part2;
                                                            ArrayList arrayList10 = arrayList6;
                                                            long doubleAttr3 = (long) (parseDoubleAttr(next, REGEX_ATTR_DURATION) * 1000000.0d);
                                                            ArrayList arrayList11 = arrayList7;
                                                            boolean optionalBooleanAttribute2 = parseOptionalBooleanAttribute(next, REGEX_INDEPENDENT, false) | (z3 && arrayList8.isEmpty());
                                                            boolean optionalBooleanAttribute3 = parseOptionalBooleanAttribute(next, REGEX_GAP, false);
                                                            String optionalStringAttr6 = parseOptionalStringAttr(next, REGEX_ATTR_BYTERANGE, map2);
                                                            if (optionalStringAttr6 != null) {
                                                                String[] strArrSplit3 = Util.split(optionalStringAttr6, "@");
                                                                j3 = Long.parseLong(strArrSplit3[0]);
                                                                if (strArrSplit3.length > 1) {
                                                                    j8 = Long.parseLong(strArrSplit3[1]);
                                                                }
                                                            } else {
                                                                j3 = -1;
                                                            }
                                                            if (j3 == -1) {
                                                                j8 = 0;
                                                            }
                                                            if (drmInitData2 == null && !treeMap.isEmpty()) {
                                                                DrmInitData.SchemeData[] schemeDataArr = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                                                DrmInitData drmInitData4 = new DrmInitData(encryptionScheme, schemeDataArr);
                                                                if (playlistProtectionSchemes == null) {
                                                                    playlistProtectionSchemes = getPlaylistProtectionSchemes(encryptionScheme, schemeDataArr);
                                                                }
                                                                drmInitData2 = drmInitData4;
                                                            }
                                                            arrayList8.add(new HlsMediaPlaylist.Part(stringAttr5, segment, doubleAttr3, i4, j6, drmInitData2, str3, segmentEncryptionIV, j8, j3, optionalBooleanAttribute3, optionalBooleanAttribute2, false));
                                                            j6 += doubleAttr3;
                                                            if (j3 != -1) {
                                                                j8 += j3;
                                                            }
                                                            arrayList6 = arrayList10;
                                                            str3 = str3;
                                                            i2 = i;
                                                            arrayList7 = arrayList11;
                                                            part2 = part3;
                                                            arrayList4 = arrayList3;
                                                            z2 = false;
                                                            longAttr = longAttr;
                                                            encryptionScheme = encryptionScheme;
                                                            str4 = str4;
                                                            str2 = str2;
                                                            hlsMultivariantPlaylist = hlsMultivariantPlaylist;
                                                            hlsMediaPlaylist = hlsMediaPlaylist;
                                                        } else {
                                                            part = part2;
                                                            arrayList6 = arrayList6;
                                                            arrayList7 = arrayList7;
                                                            if (next.startsWith("#")) {
                                                                map2 = map2;
                                                                arrayList2 = arrayList3;
                                                            } else {
                                                                String segmentEncryptionIV2 = getSegmentEncryptionIV(longAttr, str3, str4);
                                                                long j14 = longAttr + 1;
                                                                String strReplaceVariableReferences = replaceVariableReferences(next, map2);
                                                                HlsMediaPlaylist.Segment segment3 = (HlsMediaPlaylist.Segment) map3.get(strReplaceVariableReferences);
                                                                if (j10 == -1) {
                                                                    j = 0;
                                                                } else {
                                                                    if (z7 && segment == null && segment3 == null) {
                                                                        segment3 = new HlsMediaPlaylist.Segment(strReplaceVariableReferences, 0L, j5, null, null);
                                                                        map3.put(strReplaceVariableReferences, segment3);
                                                                    }
                                                                    j = j5;
                                                                }
                                                                if (drmInitData2 != null || treeMap.isEmpty()) {
                                                                    j2 = j14;
                                                                    map = map2;
                                                                    drmInitData = drmInitData2;
                                                                } else {
                                                                    j2 = j14;
                                                                    map = map2;
                                                                    DrmInitData.SchemeData[] schemeDataArr2 = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                                                    drmInitData = new DrmInitData(encryptionScheme, schemeDataArr2);
                                                                    if (playlistProtectionSchemes == null) {
                                                                        playlistProtectionSchemes = getPlaylistProtectionSchemes(encryptionScheme, schemeDataArr2);
                                                                    }
                                                                }
                                                                arrayList3.add(new HlsMediaPlaylist.Segment(strReplaceVariableReferences, segment != null ? segment : segment3, optionalStringAttr, timeSecondsToUs, i4, j7, drmInitData, str3, segmentEncryptionIV2, j, j10, z6, arrayList8));
                                                                j6 = j7 + timeSecondsToUs;
                                                                arrayList8 = new ArrayList();
                                                                if (j10 != -1) {
                                                                    j += j10;
                                                                }
                                                                j5 = j;
                                                                hlsMultivariantPlaylist = hlsMultivariantPlaylist;
                                                                hlsMediaPlaylist = hlsMediaPlaylist;
                                                                arrayList6 = arrayList6;
                                                                drmInitData2 = drmInitData;
                                                                str3 = str3;
                                                                timeSecondsToUs = 0;
                                                                j7 = j6;
                                                                map2 = map;
                                                                i2 = i;
                                                                part2 = part;
                                                                optionalStringAttr = str2;
                                                                z2 = false;
                                                                z6 = false;
                                                                j10 = -1;
                                                                arrayList4 = arrayList3;
                                                                str4 = str4;
                                                                longAttr = j2;
                                                                arrayList7 = arrayList7;
                                                                encryptionScheme = encryptionScheme;
                                                                str2 = optionalStringAttr;
                                                            }
                                                        }
                                                    } else if (part2 == null && TYPE_PART.equals(parseStringAttr(next, REGEX_PRELOAD_HINT_TYPE, map2))) {
                                                        arrayList3 = arrayList;
                                                        String stringAttr6 = parseStringAttr(next, REGEX_URI, map2);
                                                        long optionalLongAttr = parseOptionalLongAttr(next, REGEX_BYTERANGE_START, -1L);
                                                        long optionalLongAttr2 = parseOptionalLongAttr(next, REGEX_BYTERANGE_LENGTH, -1L);
                                                        long j15 = longAttr;
                                                        String segmentEncryptionIV3 = getSegmentEncryptionIV(j15, str3, str4);
                                                        if (drmInitData2 == null && !treeMap.isEmpty()) {
                                                            DrmInitData.SchemeData[] schemeDataArr3 = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                                            DrmInitData drmInitData5 = new DrmInitData(encryptionScheme, schemeDataArr3);
                                                            if (playlistProtectionSchemes == null) {
                                                                playlistProtectionSchemes = getPlaylistProtectionSchemes(encryptionScheme, schemeDataArr3);
                                                            }
                                                            drmInitData2 = drmInitData5;
                                                        }
                                                        if (optionalLongAttr == -1 || optionalLongAttr2 != -1) {
                                                            part2 = new HlsMediaPlaylist.Part(stringAttr6, segment, 0L, i4, j6, drmInitData2, str3, segmentEncryptionIV3, optionalLongAttr != -1 ? optionalLongAttr : 0L, optionalLongAttr2, false, false, true);
                                                        }
                                                        hlsMultivariantPlaylist = hlsMultivariantPlaylist;
                                                        hlsMediaPlaylist = hlsMediaPlaylist;
                                                        encryptionScheme = encryptionScheme;
                                                        str3 = str3;
                                                        longAttr = j15;
                                                        i2 = i;
                                                        arrayList4 = arrayList3;
                                                        str2 = str2;
                                                        z2 = false;
                                                        str4 = str4;
                                                    } else {
                                                        arrayList3 = arrayList;
                                                    }
                                                }
                                                arrayList2 = arrayList3;
                                            }
                                            part = part2;
                                        }
                                        arrayList4 = arrayList;
                                        z2 = false;
                                    }
                                    arrayList4 = arrayList;
                                    z2 = false;
                                }
                            }
                            arrayList6 = arrayList6;
                            str3 = str3;
                            map2 = map2;
                            i2 = i;
                            part2 = part;
                            z2 = false;
                            longAttr = longAttr;
                            arrayList4 = arrayList2;
                            str4 = str4;
                            arrayList7 = arrayList7;
                            hlsMultivariantPlaylist = hlsMultivariantPlaylist;
                            hlsMediaPlaylist = hlsMediaPlaylist;
                            encryptionScheme = encryptionScheme;
                            str2 = str2;
                        }
                        str4 = str4;
                        str3 = str3;
                        arrayList4 = arrayList;
                        z2 = false;
                    }
                }
                arrayList4 = arrayList;
            }
        }
        int i9 = i2;
        HlsMediaPlaylist.Part part4 = part2;
        ArrayList arrayList12 = arrayList6;
        ArrayList arrayList13 = arrayList7;
        ArrayList arrayList14 = arrayList4;
        HashMap map4 = new HashMap();
        for (int i10 = 0; i10 < arrayList12.size(); i10++) {
            HlsMediaPlaylist.RenditionReport renditionReport = (HlsMediaPlaylist.RenditionReport) arrayList12.get(i10);
            long size = renditionReport.lastMediaSequence;
            if (size == -1) {
                size = (j4 + ((long) arrayList14.size())) - (arrayList8.isEmpty() ? 1L : 0L);
            }
            int size2 = renditionReport.lastPartIndex;
            if (size2 == -1 && doubleAttr != C.TIME_UNSET) {
                size2 = (arrayList8.isEmpty() ? ((HlsMediaPlaylist.Segment) Iterables.getLast(arrayList14)).parts : arrayList8).size() - 1;
            }
            Uri uri = renditionReport.playlistUri;
            map4.put(uri, new HlsMediaPlaylist.RenditionReport(uri, size, size2));
        }
        if (part4 != null) {
            arrayList8.add(part4);
        }
        return new HlsMediaPlaylist(i9, str, arrayList13, j9, optionalBooleanAttribute, jMsToUs, z4, i3, j4, intAttr, intAttr2, doubleAttr, z3, z5, jMsToUs != 0, playlistProtectionSchemes, arrayList14, arrayList8, serverControl2, map4);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:79:0x032b  */
    private static HlsMultivariantPlaylist parseMultivariantPlaylist(LineIterator lineIterator, String str) throws IOException {
        ArrayList arrayList;
        String mediaMimeType;
        int i;
        String str2;
        String mediaMimeType2;
        int i2;
        int i3;
        Uri uriResolveToUri;
        HashMap map;
        int i4;
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = new ArrayList();
        boolean z = false;
        boolean z2 = false;
        while (true) {
            boolean zHasNext = lineIterator.hasNext();
            String str3 = MimeTypes.APPLICATION_M3U8;
            if (!zHasNext) {
                HashMap map4 = map2;
                ArrayList arrayList10 = arrayList7;
                ArrayList arrayList11 = arrayList3;
                ArrayList arrayList12 = arrayList4;
                ArrayList arrayList13 = arrayList5;
                ArrayList arrayList14 = arrayList6;
                ArrayList arrayList15 = arrayList9;
                boolean z3 = z;
                ArrayList arrayList16 = arrayList8;
                ArrayList arrayList17 = new ArrayList();
                HashSet hashSet = new HashSet();
                for (int i5 = 0; i5 < arrayList2.size(); i5++) {
                    HlsMultivariantPlaylist.Variant variant = (HlsMultivariantPlaylist.Variant) arrayList2.get(i5);
                    if (hashSet.add(variant.url)) {
                        Assertions.checkState(variant.format.metadata == null);
                        arrayList17.add(variant.copyWithFormat(variant.format.buildUpon().setMetadata(new Metadata(new HlsTrackMetadataEntry(null, null, (List) Assertions.checkNotNull((ArrayList) map4.get(variant.url))))).build()));
                    }
                }
                Uri uri = null;
                ArrayList arrayList18 = null;
                Format formatBuild = null;
                int i6 = 0;
                while (i6 < arrayList10.size()) {
                    ArrayList arrayList19 = arrayList10;
                    String str4 = (String) arrayList19.get(i6);
                    String stringAttr = parseStringAttr(str4, REGEX_GROUP_ID, map3);
                    String stringAttr2 = parseStringAttr(str4, REGEX_NAME, map3);
                    Format.Builder language = new Format.Builder().setId(stringAttr + ":" + stringAttr2).setLabel(stringAttr2).setContainerMimeType(str3).setSelectionFlags(parseSelectionFlags(str4)).setRoleFlags(parseRoleFlags(str4, map3)).setLanguage(parseOptionalStringAttr(str4, REGEX_LANGUAGE, map3));
                    String optionalStringAttr = parseOptionalStringAttr(str4, REGEX_URI, map3);
                    Uri uriResolveToUri2 = optionalStringAttr == null ? uri : UriUtil.resolveToUri(str, optionalStringAttr);
                    arrayList10 = arrayList19;
                    String str5 = str3;
                    Metadata metadata = new Metadata(new HlsTrackMetadataEntry(stringAttr, stringAttr2, Collections.emptyList()));
                    String stringAttr3 = parseStringAttr(str4, REGEX_TYPE, map3);
                    Objects.requireNonNull(stringAttr3);
                    switch (stringAttr3) {
                        case "SUBTITLES":
                            formatBuild = formatBuild;
                            arrayList12 = arrayList12;
                            arrayList = arrayList11;
                            HlsMultivariantPlaylist.Variant variantWithSubtitleGroup = getVariantWithSubtitleGroup(arrayList2, stringAttr);
                            if (variantWithSubtitleGroup != null) {
                                String codecsOfType = Util.getCodecsOfType(variantWithSubtitleGroup.format.codecs, 3);
                                language.setCodecs(codecsOfType);
                                mediaMimeType = MimeTypes.getMediaMimeType(codecsOfType);
                            } else {
                                mediaMimeType = null;
                            }
                            if (mediaMimeType == null) {
                                mediaMimeType = MimeTypes.TEXT_VTT;
                            }
                            language.setSampleMimeType(mediaMimeType).setMetadata(metadata);
                            if (uriResolveToUri2 != null) {
                                HlsMultivariantPlaylist.Rendition rendition = new HlsMultivariantPlaylist.Rendition(uriResolveToUri2, language.build(), stringAttr, stringAttr2);
                                arrayList13 = arrayList13;
                                arrayList13.add(rendition);
                                break;
                            } else {
                                arrayList13 = arrayList13;
                                Log.w(LOG_TAG, "EXT-X-MEDIA tag with missing mandatory URI attribute: skipping");
                                break;
                            }
                            break;
                        case "CLOSED-CAPTIONS":
                            formatBuild = formatBuild;
                            arrayList12 = arrayList12;
                            arrayList = arrayList11;
                            String stringAttr4 = parseStringAttr(str4, REGEX_INSTREAM_ID, map3);
                            if (stringAttr4.startsWith("CC")) {
                                i = Integer.parseInt(stringAttr4.substring(2));
                                str2 = MimeTypes.APPLICATION_CEA608;
                            } else {
                                i = Integer.parseInt(stringAttr4.substring(7));
                                str2 = MimeTypes.APPLICATION_CEA708;
                            }
                            if (arrayList18 == null) {
                                arrayList18 = new ArrayList();
                            }
                            language.setSampleMimeType(str2).setAccessibilityChannel(i);
                            arrayList18.add(language.build());
                            arrayList13 = arrayList13;
                            break;
                        case "AUDIO":
                            arrayList = arrayList11;
                            HlsMultivariantPlaylist.Variant variantWithAudioGroup = getVariantWithAudioGroup(arrayList2, stringAttr);
                            if (variantWithAudioGroup != null) {
                                String codecsOfType2 = Util.getCodecsOfType(variantWithAudioGroup.format.codecs, 1);
                                language.setCodecs(codecsOfType2);
                                mediaMimeType2 = MimeTypes.getMediaMimeType(codecsOfType2);
                            } else {
                                mediaMimeType2 = null;
                            }
                            String optionalStringAttr2 = parseOptionalStringAttr(str4, REGEX_CHANNELS, map3);
                            if (optionalStringAttr2 != null) {
                                language.setChannelCount(Integer.parseInt(Util.splitAtFirst(optionalStringAttr2, "/")[0]));
                                if (MimeTypes.AUDIO_E_AC3.equals(mediaMimeType2) && optionalStringAttr2.endsWith("/JOC")) {
                                    language.setCodecs(MimeTypes.CODEC_E_AC3_JOC);
                                    mediaMimeType2 = MimeTypes.AUDIO_E_AC3_JOC;
                                }
                            }
                            language.setSampleMimeType(mediaMimeType2);
                            if (uriResolveToUri2 != null) {
                                language.setMetadata(metadata);
                                arrayList12 = arrayList12;
                                arrayList12.add(new HlsMultivariantPlaylist.Rendition(uriResolveToUri2, language.build(), stringAttr, stringAttr2));
                            } else {
                                arrayList12 = arrayList12;
                                if (variantWithAudioGroup != null) {
                                    formatBuild = language.build();
                                }
                            }
                            arrayList13 = arrayList13;
                            break;
                        case "VIDEO":
                            HlsMultivariantPlaylist.Variant variantWithVideoGroup = getVariantWithVideoGroup(arrayList2, stringAttr);
                            if (variantWithVideoGroup != null) {
                                Format format = variantWithVideoGroup.format;
                                String codecsOfType3 = Util.getCodecsOfType(format.codecs, 2);
                                language.setCodecs(codecsOfType3).setSampleMimeType(MimeTypes.getMediaMimeType(codecsOfType3)).setWidth(format.width).setHeight(format.height).setFrameRate(format.frameRate);
                            }
                            if (uriResolveToUri2 != null) {
                                language.setMetadata(metadata);
                                arrayList = arrayList11;
                                arrayList.add(new HlsMultivariantPlaylist.Rendition(uriResolveToUri2, language.build(), stringAttr, stringAttr2));
                            }
                        default:
                            arrayList = arrayList11;
                            break;
                    }
                    i6++;
                    arrayList13 = arrayList13;
                    arrayList12 = arrayList12;
                    arrayList11 = arrayList;
                    str3 = str5;
                    formatBuild = formatBuild;
                    uri = null;
                }
                return new HlsMultivariantPlaylist(str, arrayList15, arrayList17, arrayList11, arrayList12, arrayList13, arrayList14, formatBuild, z2 ? Collections.emptyList() : arrayList18, z3, map3, arrayList16);
            }
            String next = lineIterator.next();
            if (next.startsWith(TAG_PREFIX)) {
                arrayList9.add(next);
            }
            boolean zStartsWith = next.startsWith(TAG_I_FRAME_STREAM_INF);
            boolean z4 = z;
            if (next.startsWith(TAG_DEFINE)) {
                map3.put(parseStringAttr(next, REGEX_NAME, map3), parseStringAttr(next, REGEX_VALUE, map3));
            } else {
                if (next.equals(TAG_INDEPENDENT_SEGMENTS)) {
                    map = map2;
                    arrayList3 = arrayList3;
                    arrayList5 = arrayList5;
                    z = true;
                } else if (next.startsWith(TAG_MEDIA)) {
                    arrayList7.add(next);
                } else if (next.startsWith(TAG_SESSION_KEY)) {
                    DrmInitData.SchemeData drmSchemeData = parseDrmSchemeData(next, parseOptionalStringAttr(next, REGEX_KEYFORMAT, KEYFORMAT_IDENTITY, map3), map3);
                    if (drmSchemeData != null) {
                        arrayList8.add(new DrmInitData(parseEncryptionScheme(parseStringAttr(next, REGEX_METHOD, map3)), drmSchemeData));
                    }
                } else if (next.startsWith(TAG_STREAM_INF) || zStartsWith) {
                    boolean zContains = z2 | next.contains(ATTR_CLOSED_CAPTIONS_NONE);
                    int i7 = zStartsWith ? 16384 : 0;
                    int intAttr = parseIntAttr(next, REGEX_BANDWIDTH);
                    int optionalIntAttr = parseOptionalIntAttr(next, REGEX_AVERAGE_BANDWIDTH, -1);
                    String optionalStringAttr3 = parseOptionalStringAttr(next, REGEX_CODECS, map3);
                    String optionalStringAttr4 = parseOptionalStringAttr(next, REGEX_RESOLUTION, map3);
                    if (optionalStringAttr4 != null) {
                        String[] strArrSplit = Util.split(optionalStringAttr4, "x");
                        int i8 = Integer.parseInt(strArrSplit[0]);
                        int i9 = Integer.parseInt(strArrSplit[1]);
                        if (i8 <= 0 || i9 <= 0) {
                            i9 = -1;
                            i4 = -1;
                        } else {
                            i4 = i8;
                        }
                        i3 = i9;
                        i2 = i4;
                    } else {
                        i2 = -1;
                        i3 = -1;
                    }
                    String optionalStringAttr5 = parseOptionalStringAttr(next, REGEX_FRAME_RATE, map3);
                    float f = optionalStringAttr5 != null ? Float.parseFloat(optionalStringAttr5) : -1.0f;
                    String optionalStringAttr6 = parseOptionalStringAttr(next, REGEX_VIDEO, map3);
                    String optionalStringAttr7 = parseOptionalStringAttr(next, REGEX_AUDIO, map3);
                    HashMap map5 = map2;
                    String optionalStringAttr8 = parseOptionalStringAttr(next, REGEX_SUBTITLES, map3);
                    String optionalStringAttr9 = parseOptionalStringAttr(next, REGEX_CLOSED_CAPTIONS, map3);
                    if (zStartsWith) {
                        uriResolveToUri = UriUtil.resolveToUri(str, parseStringAttr(next, REGEX_URI, map3));
                    } else {
                        if (!lineIterator.hasNext()) {
                            throw ParserException.createForMalformedManifest("#EXT-X-STREAM-INF must be followed by another line", null);
                        }
                        uriResolveToUri = UriUtil.resolveToUri(str, replaceVariableReferences(lineIterator.next(), map3));
                    }
                    arrayList2.add(new HlsMultivariantPlaylist.Variant(uriResolveToUri, new Format.Builder().setId(arrayList2.size()).setContainerMimeType(MimeTypes.APPLICATION_M3U8).setCodecs(optionalStringAttr3).setAverageBitrate(optionalIntAttr).setPeakBitrate(intAttr).setWidth(i2).setHeight(i3).setFrameRate(f).setRoleFlags(i7).build(), optionalStringAttr6, optionalStringAttr7, optionalStringAttr8, optionalStringAttr9));
                    map = map5;
                    ArrayList arrayList20 = (ArrayList) map.get(uriResolveToUri);
                    if (arrayList20 == null) {
                        arrayList20 = new ArrayList();
                        map.put(uriResolveToUri, arrayList20);
                    }
                    arrayList20.add(new HlsTrackMetadataEntry.VariantInfo(optionalIntAttr, intAttr, optionalStringAttr6, optionalStringAttr7, optionalStringAttr8, optionalStringAttr9));
                    z = z4;
                    z2 = zContains;
                }
                map2 = map;
                arrayList8 = arrayList8;
                arrayList6 = arrayList6;
                arrayList9 = arrayList9;
                arrayList5 = arrayList5;
                arrayList4 = arrayList4;
                arrayList3 = arrayList3;
                arrayList7 = arrayList7;
            }
            map = map2;
            arrayList3 = arrayList3;
            arrayList5 = arrayList5;
            z = z4;
            map2 = map;
            arrayList8 = arrayList8;
            arrayList6 = arrayList6;
            arrayList9 = arrayList9;
            arrayList5 = arrayList5;
            arrayList4 = arrayList4;
            arrayList3 = arrayList3;
            arrayList7 = arrayList7;
        }
    }

    private static boolean parseOptionalBooleanAttribute(String str, Pattern pattern, boolean z) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? BOOLEAN_TRUE.equals(matcher.group(1)) : z;
    }

    private static double parseOptionalDoubleAttr(String str, Pattern pattern, double d) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Double.parseDouble((String) Assertions.checkNotNull(matcher.group(1))) : d;
    }

    private static int parseOptionalIntAttr(String str, Pattern pattern, int i) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))) : i;
    }

    private static long parseOptionalLongAttr(String str, Pattern pattern, long j) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Long.parseLong((String) Assertions.checkNotNull(matcher.group(1))) : j;
    }

    @Nullable
    private static String parseOptionalStringAttr(String str, Pattern pattern, Map<String, String> map) {
        return parseOptionalStringAttr(str, pattern, null, map);
    }

    private static int parseRoleFlags(String str, Map<String, String> map) {
        String optionalStringAttr = parseOptionalStringAttr(str, REGEX_CHARACTERISTICS, map);
        if (TextUtils.isEmpty(optionalStringAttr)) {
            return 0;
        }
        String[] strArrSplit = Util.split(optionalStringAttr, ",");
        int i = Util.contains(strArrSplit, "public.accessibility.describes-video") ? 512 : 0;
        if (Util.contains(strArrSplit, "public.accessibility.transcribes-spoken-dialog")) {
            i |= 4096;
        }
        if (Util.contains(strArrSplit, "public.accessibility.describes-music-and-sound")) {
            i |= 1024;
        }
        return Util.contains(strArrSplit, "public.easy-to-read") ? i | 8192 : i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    private static int parseSelectionFlags(String str) {
        boolean optionalBooleanAttribute = parseOptionalBooleanAttribute(str, REGEX_DEFAULT, false);
        ?? r0 = optionalBooleanAttribute;
        if (parseOptionalBooleanAttribute(str, REGEX_FORCED, false)) {
            r0 = (optionalBooleanAttribute ? 1 : 0) | 2;
        }
        return parseOptionalBooleanAttribute(str, REGEX_AUTOSELECT, false) ? r0 | 4 : r0;
    }

    private static HlsMediaPlaylist.ServerControl parseServerControl(String str) {
        double optionalDoubleAttr = parseOptionalDoubleAttr(str, REGEX_CAN_SKIP_UNTIL, -9.223372036854776E18d);
        long j = C.TIME_UNSET;
        long j2 = optionalDoubleAttr == -9.223372036854776E18d ? -9223372036854775807L : (long) (optionalDoubleAttr * 1000000.0d);
        boolean optionalBooleanAttribute = parseOptionalBooleanAttribute(str, REGEX_CAN_SKIP_DATE_RANGES, false);
        double optionalDoubleAttr2 = parseOptionalDoubleAttr(str, REGEX_HOLD_BACK, -9.223372036854776E18d);
        long j3 = optionalDoubleAttr2 == -9.223372036854776E18d ? -9223372036854775807L : (long) (optionalDoubleAttr2 * 1000000.0d);
        double optionalDoubleAttr3 = parseOptionalDoubleAttr(str, REGEX_PART_HOLD_BACK, -9.223372036854776E18d);
        if (optionalDoubleAttr3 != -9.223372036854776E18d) {
            j = (long) (optionalDoubleAttr3 * 1000000.0d);
        }
        return new HlsMediaPlaylist.ServerControl(j2, optionalBooleanAttribute, j3, j, parseOptionalBooleanAttribute(str, REGEX_CAN_BLOCK_RELOAD, false));
    }

    private static String parseStringAttr(String str, Pattern pattern, Map<String, String> map) throws ParserException {
        String optionalStringAttr = parseOptionalStringAttr(str, pattern, map);
        if (optionalStringAttr != null) {
            return optionalStringAttr;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Couldn't match ");
        sbM.append(pattern.pattern());
        sbM.append(" in ");
        sbM.append(str);
        throw ParserException.createForMalformedManifest(sbM.toString(), null);
    }

    private static long parseTimeSecondsToUs(String str, Pattern pattern) throws ParserException {
        return new BigDecimal(parseStringAttr(str, pattern, Collections.emptyMap())).multiply(new BigDecimal(1000000L)).longValue();
    }

    private static String replaceVariableReferences(String str, Map<String, String> map) {
        Matcher matcher = REGEX_VARIABLE_REFERENCE.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String strGroup = matcher.group(1);
            if (map.containsKey(strGroup)) {
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(map.get(strGroup)));
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private static int skipIgnorableWhitespace(BufferedReader bufferedReader, boolean z, int i) throws IOException {
        while (i != -1 && Character.isWhitespace(i) && (z || !Util.isLinebreak(i))) {
            i = bufferedReader.read();
        }
        return i;
    }

    public HlsPlaylistParser(HlsMultivariantPlaylist hlsMultivariantPlaylist, @Nullable HlsMediaPlaylist hlsMediaPlaylist) {
        this.multivariantPlaylist = hlsMultivariantPlaylist;
        this.previousMediaPlaylist = hlsMediaPlaylist;
    }

    private static String parseOptionalStringAttr(String str, Pattern pattern, String str2, Map<String, String> map) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str2 = (String) Assertions.checkNotNull(matcher.group(1));
        }
        return (map.isEmpty() || str2 == null) ? str2 : replaceVariableReferences(str2, map);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public HlsPlaylist parse(Uri uri, InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayDeque arrayDeque = new ArrayDeque();
        try {
            if (!checkPlaylistHeader(bufferedReader)) {
                throw ParserException.createForMalformedManifest("Input does not start with the #EXTM3U header.", null);
            }
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    Util.closeQuietly(bufferedReader);
                    throw ParserException.createForMalformedManifest("Failed to parse the playlist, could not identify any tags.", null);
                }
                String strTrim = line.trim();
                if (!strTrim.isEmpty()) {
                    if (strTrim.startsWith(TAG_STREAM_INF)) {
                        arrayDeque.add(strTrim);
                        HlsMultivariantPlaylist multivariantPlaylist = parseMultivariantPlaylist(new LineIterator(arrayDeque, bufferedReader), uri.toString());
                        Util.closeQuietly(bufferedReader);
                        return multivariantPlaylist;
                    }
                    if (!strTrim.startsWith(TAG_TARGET_DURATION) && !strTrim.startsWith(TAG_MEDIA_SEQUENCE) && !strTrim.startsWith(TAG_MEDIA_DURATION) && !strTrim.startsWith(TAG_KEY) && !strTrim.startsWith(TAG_BYTERANGE) && !strTrim.equals(TAG_DISCONTINUITY) && !strTrim.equals(TAG_DISCONTINUITY_SEQUENCE) && !strTrim.equals(TAG_ENDLIST)) {
                        arrayDeque.add(strTrim);
                    }
                    arrayDeque.add(strTrim);
                    HlsMediaPlaylist mediaPlaylist = parseMediaPlaylist(this.multivariantPlaylist, this.previousMediaPlaylist, new LineIterator(arrayDeque, bufferedReader), uri.toString());
                    Util.closeQuietly(bufferedReader);
                    return mediaPlaylist;
                }
            }
        } catch (Throwable th) {
            Util.closeQuietly(bufferedReader);
            throw th;
        }
    }
}
