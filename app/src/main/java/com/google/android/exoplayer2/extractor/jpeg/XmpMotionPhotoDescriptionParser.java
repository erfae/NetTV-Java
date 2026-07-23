package com.google.android.exoplayer2.extractor.jpeg;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: loaded from: classes.dex */
final class XmpMotionPhotoDescriptionParser {
    private static final String TAG = "MotionPhotoXmpParser";
    private static final String[] MOTION_PHOTO_ATTRIBUTE_NAMES = {"Camera:MotionPhoto", "GCamera:MotionPhoto", "Camera:MicroVideo", "GCamera:MicroVideo"};
    private static final String[] DESCRIPTION_MOTION_PHOTO_PRESENTATION_TIMESTAMP_ATTRIBUTE_NAMES = {"Camera:MotionPhotoPresentationTimestampUs", "GCamera:MotionPhotoPresentationTimestampUs", "Camera:MicroVideoPresentationTimestampUs", "GCamera:MicroVideoPresentationTimestampUs"};
    private static final String[] DESCRIPTION_MICRO_VIDEO_OFFSET_ATTRIBUTE_NAMES = {"Camera:MicroVideoOffset", "GCamera:MicroVideoOffset"};

    private XmpMotionPhotoDescriptionParser() {
    }

    @Nullable
    public static MotionPhotoDescription parse(String str) throws IOException {
        try {
            return parseInternal(str);
        } catch (ParserException | NumberFormatException | XmlPullParserException unused) {
            Log.w(TAG, "Ignoring unexpected XMP metadata");
            return null;
        }
    }

    @Nullable
    private static MotionPhotoDescription parseInternal(String str) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
        xmlPullParserNewPullParser.setInput(new StringReader(str));
        xmlPullParserNewPullParser.next();
        if (!XmlPullParserUtil.isStartTag(xmlPullParserNewPullParser, "x:xmpmeta")) {
            throw ParserException.createForMalformedContainer("Couldn't find xmp metadata", null);
        }
        long motionPhotoPresentationTimestampUsFromDescription = C.TIME_UNSET;
        ImmutableList<MotionPhotoDescription.ContainerItem> immutableListOf = ImmutableList.of();
        do {
            xmlPullParserNewPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParserNewPullParser, "rdf:Description")) {
                if (!parseMotionPhotoFlagFromDescription(xmlPullParserNewPullParser)) {
                    return null;
                }
                motionPhotoPresentationTimestampUsFromDescription = parseMotionPhotoPresentationTimestampUsFromDescription(xmlPullParserNewPullParser);
                immutableListOf = parseMicroVideoOffsetFromDescription(xmlPullParserNewPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParserNewPullParser, "Container:Directory")) {
                immutableListOf = parseMotionPhotoV1Directory(xmlPullParserNewPullParser, "Container", "Item");
            } else if (XmlPullParserUtil.isStartTag(xmlPullParserNewPullParser, "GContainer:Directory")) {
                immutableListOf = parseMotionPhotoV1Directory(xmlPullParserNewPullParser, "GContainer", "GContainerItem");
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParserNewPullParser, "x:xmpmeta"));
        if (immutableListOf.isEmpty()) {
            return null;
        }
        return new MotionPhotoDescription(motionPhotoPresentationTimestampUsFromDescription, immutableListOf);
    }

    private static ImmutableList<MotionPhotoDescription.ContainerItem> parseMicroVideoOffsetFromDescription(XmlPullParser xmlPullParser) {
        for (String str : DESCRIPTION_MICRO_VIDEO_OFFSET_ATTRIBUTE_NAMES) {
            String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, str);
            if (attributeValue != null) {
                return ImmutableList.of(new MotionPhotoDescription.ContainerItem("image/jpeg", "Primary", 0L, 0L), new MotionPhotoDescription.ContainerItem(MimeTypes.VIDEO_MP4, "MotionPhoto", Long.parseLong(attributeValue), 0L));
            }
        }
        return ImmutableList.of();
    }

    private static boolean parseMotionPhotoFlagFromDescription(XmlPullParser xmlPullParser) {
        for (String str : MOTION_PHOTO_ATTRIBUTE_NAMES) {
            String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, str);
            if (attributeValue != null) {
                return Integer.parseInt(attributeValue) == 1;
            }
        }
        return false;
    }

    private static long parseMotionPhotoPresentationTimestampUsFromDescription(XmlPullParser xmlPullParser) {
        for (String str : DESCRIPTION_MOTION_PHOTO_PRESENTATION_TIMESTAMP_ATTRIBUTE_NAMES) {
            String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, str);
            if (attributeValue != null) {
                long j = Long.parseLong(attributeValue);
                return j == -1 ? C.TIME_UNSET : j;
            }
        }
        return C.TIME_UNSET;
    }

    private static ImmutableList<MotionPhotoDescription.ContainerItem> parseMotionPhotoV1Directory(XmlPullParser xmlPullParser, String str, String str2) throws XmlPullParserException, IOException {
        ImmutableList.Builder builder = ImmutableList.builder();
        String strM = Insets$$ExternalSyntheticOutline0.m(str, ":Item");
        String strM2 = Insets$$ExternalSyntheticOutline0.m(str, ":Directory");
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, strM)) {
                String strM3 = Insets$$ExternalSyntheticOutline0.m(str2, ":Mime");
                String strM4 = Insets$$ExternalSyntheticOutline0.m(str2, ":Semantic");
                String strM5 = Insets$$ExternalSyntheticOutline0.m(str2, ":Length");
                String strM6 = Insets$$ExternalSyntheticOutline0.m(str2, ":Padding");
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, strM3);
                String attributeValue2 = XmlPullParserUtil.getAttributeValue(xmlPullParser, strM4);
                String attributeValue3 = XmlPullParserUtil.getAttributeValue(xmlPullParser, strM5);
                String attributeValue4 = XmlPullParserUtil.getAttributeValue(xmlPullParser, strM6);
                if (attributeValue == null || attributeValue2 == null) {
                    return ImmutableList.of();
                }
                builder.add(new MotionPhotoDescription.ContainerItem(attributeValue, attributeValue2, attributeValue3 != null ? Long.parseLong(attributeValue3) : 0L, attributeValue4 != null ? Long.parseLong(attributeValue4) : 0L));
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, strM2));
        return builder.build();
    }
}
