package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import org.androidannotations.api.rest.MediaType;

/* JADX INFO: loaded from: classes.dex */
final class MetadataUtil {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int SHORT_TYPE_ALBUM = 6384738;
    private static final int SHORT_TYPE_ARTIST = 4280916;
    private static final int SHORT_TYPE_COMMENT = 6516084;
    private static final int SHORT_TYPE_COMPOSER_1 = 6516589;
    private static final int SHORT_TYPE_COMPOSER_2 = 7828084;
    private static final int SHORT_TYPE_ENCODER = 7630703;
    private static final int SHORT_TYPE_GENRE = 6776174;
    private static final int SHORT_TYPE_LYRICS = 7108978;
    private static final int SHORT_TYPE_NAME_1 = 7233901;
    private static final int SHORT_TYPE_NAME_2 = 7631467;
    private static final int SHORT_TYPE_YEAR = 6578553;

    @VisibleForTesting
    public static final String[] STANDARD_GENRES = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", HttpHeaders.TRAILER, "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Afro-Punk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop", "Abstract", "Art Rock", "Baroque", "Bhangra", "Big beat", "Breakbeat", "Chillout", "Downtempo", "Dub", "EBM", "Eclectic", "Electro", "Electroclash", "Emo", "Experimental", "Garage", "Global", "IDM", "Illbient", "Industro-Goth", "Jam Band", "Krautrock", "Leftfield", "Lounge", "Math Rock", "New Romantic", "Nu-Breakz", "Post-Punk", "Post-Rock", "Psytrance", "Shoegaze", "Space Rock", "Trop Rock", "World Music", "Neoclassical", "Audiobook", "Audio theatre", "Neue Deutsche Welle", "Podcast", "Indie-Rock", "G-Funk", "Dubstep", "Garage Rock", "Psybient"};
    private static final String TAG = "MetadataUtil";
    private static final int TYPE_ALBUM_ARTIST = 1631670868;
    private static final int TYPE_COMPILATION = 1668311404;
    private static final int TYPE_COVER_ART = 1668249202;
    private static final int TYPE_DISK_NUMBER = 1684632427;
    private static final int TYPE_GAPLESS_ALBUM = 1885823344;
    private static final int TYPE_GENRE = 1735291493;
    private static final int TYPE_GROUPING = 6779504;
    private static final int TYPE_INTERNAL = 757935405;
    private static final int TYPE_RATING = 1920233063;
    private static final int TYPE_SORT_ALBUM = 1936679276;
    private static final int TYPE_SORT_ALBUM_ARTIST = 1936679265;
    private static final int TYPE_SORT_ARTIST = 1936679282;
    private static final int TYPE_SORT_COMPOSER = 1936679791;
    private static final int TYPE_SORT_TRACK_NAME = 1936682605;
    private static final int TYPE_TEMPO = 1953329263;
    private static final int TYPE_TOP_BYTE_COPYRIGHT = 169;
    private static final int TYPE_TOP_BYTE_REPLACEMENT = 253;
    private static final int TYPE_TRACK_NUMBER = 1953655662;
    private static final int TYPE_TV_SHOW = 1953919848;
    private static final int TYPE_TV_SORT_SHOW = 1936683886;

    private MetadataUtil() {
    }

    @Nullable
    private static CommentFrame parseCommentAttribute(int i, ParsableByteArray parsableByteArray) {
        int i2 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            String nullTerminatedString = parsableByteArray.readNullTerminatedString(i2 - 16);
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, nullTerminatedString, nullTerminatedString);
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Failed to parse comment attribute: ");
        sbM.append(Atom.getAtomTypeString(i));
        Log.w(TAG, sbM.toString());
        return null;
    }

    @Nullable
    private static ApicFrame parseCoverArt(ParsableByteArray parsableByteArray) {
        String str;
        int i = parsableByteArray.readInt();
        if (parsableByteArray.readInt() != 1684108385) {
            Log.w(TAG, "Failed to parse cover art attribute");
            return null;
        }
        int fullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        if (fullAtomFlags == 13) {
            str = "image/jpeg";
        } else {
            str = fullAtomFlags == 14 ? MediaType.IMAGE_PNG : null;
        }
        if (str == null) {
            Insets$$ExternalSyntheticOutline0.m27m("Unrecognized cover art flags: ", fullAtomFlags, TAG);
            return null;
        }
        parsableByteArray.skipBytes(4);
        int i2 = i - 16;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        return new ApicFrame(str, null, 3, bArr);
    }

    @Nullable
    public static Metadata.Entry parseIlstElement(ParsableByteArray parsableByteArray) {
        int i = parsableByteArray.readInt() + parsableByteArray.getPosition();
        int i2 = parsableByteArray.readInt();
        int i3 = (i2 >> 24) & 255;
        try {
            if (i3 == TYPE_TOP_BYTE_COPYRIGHT || i3 == TYPE_TOP_BYTE_REPLACEMENT) {
                int i4 = 16777215 & i2;
                if (i4 == SHORT_TYPE_COMMENT) {
                    CommentFrame commentAttribute = parseCommentAttribute(i2, parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return commentAttribute;
                }
                if (i4 == SHORT_TYPE_NAME_1 || i4 == SHORT_TYPE_NAME_2) {
                    TextInformationFrame textAttribute = parseTextAttribute(i2, "TIT2", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute;
                }
                if (i4 == SHORT_TYPE_COMPOSER_1 || i4 == SHORT_TYPE_COMPOSER_2) {
                    TextInformationFrame textAttribute2 = parseTextAttribute(i2, "TCOM", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute2;
                }
                if (i4 == SHORT_TYPE_YEAR) {
                    TextInformationFrame textAttribute3 = parseTextAttribute(i2, "TDRC", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute3;
                }
                if (i4 == SHORT_TYPE_ARTIST) {
                    TextInformationFrame textAttribute4 = parseTextAttribute(i2, "TPE1", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute4;
                }
                if (i4 == SHORT_TYPE_ENCODER) {
                    TextInformationFrame textAttribute5 = parseTextAttribute(i2, "TSSE", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute5;
                }
                if (i4 == SHORT_TYPE_ALBUM) {
                    TextInformationFrame textAttribute6 = parseTextAttribute(i2, "TALB", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute6;
                }
                if (i4 == SHORT_TYPE_LYRICS) {
                    TextInformationFrame textAttribute7 = parseTextAttribute(i2, "USLT", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute7;
                }
                if (i4 == SHORT_TYPE_GENRE) {
                    TextInformationFrame textAttribute8 = parseTextAttribute(i2, "TCON", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute8;
                }
                if (i4 == TYPE_GROUPING) {
                    TextInformationFrame textAttribute9 = parseTextAttribute(i2, "TIT1", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute9;
                }
            } else {
                if (i2 == TYPE_GENRE) {
                    TextInformationFrame standardGenreAttribute = parseStandardGenreAttribute(parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return standardGenreAttribute;
                }
                if (i2 == TYPE_DISK_NUMBER) {
                    TextInformationFrame indexAndCountAttribute = parseIndexAndCountAttribute(i2, "TPOS", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return indexAndCountAttribute;
                }
                if (i2 == TYPE_TRACK_NUMBER) {
                    TextInformationFrame indexAndCountAttribute2 = parseIndexAndCountAttribute(i2, "TRCK", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return indexAndCountAttribute2;
                }
                if (i2 == TYPE_TEMPO) {
                    Id3Frame uint8Attribute = parseUint8Attribute(i2, "TBPM", parsableByteArray, true, false);
                    parsableByteArray.setPosition(i);
                    return uint8Attribute;
                }
                if (i2 == TYPE_COMPILATION) {
                    Id3Frame uint8Attribute2 = parseUint8Attribute(i2, "TCMP", parsableByteArray, true, true);
                    parsableByteArray.setPosition(i);
                    return uint8Attribute2;
                }
                if (i2 == TYPE_COVER_ART) {
                    ApicFrame coverArt = parseCoverArt(parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return coverArt;
                }
                if (i2 == TYPE_ALBUM_ARTIST) {
                    TextInformationFrame textAttribute10 = parseTextAttribute(i2, "TPE2", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute10;
                }
                if (i2 == TYPE_SORT_TRACK_NAME) {
                    TextInformationFrame textAttribute11 = parseTextAttribute(i2, "TSOT", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute11;
                }
                if (i2 == TYPE_SORT_ALBUM) {
                    TextInformationFrame textAttribute12 = parseTextAttribute(i2, "TSO2", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute12;
                }
                if (i2 == TYPE_SORT_ARTIST) {
                    TextInformationFrame textAttribute13 = parseTextAttribute(i2, "TSOA", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute13;
                }
                if (i2 == TYPE_SORT_ALBUM_ARTIST) {
                    TextInformationFrame textAttribute14 = parseTextAttribute(i2, "TSOP", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute14;
                }
                if (i2 == TYPE_SORT_COMPOSER) {
                    TextInformationFrame textAttribute15 = parseTextAttribute(i2, "TSOC", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute15;
                }
                if (i2 == TYPE_RATING) {
                    Id3Frame uint8Attribute3 = parseUint8Attribute(i2, "ITUNESADVISORY", parsableByteArray, false, false);
                    parsableByteArray.setPosition(i);
                    return uint8Attribute3;
                }
                if (i2 == TYPE_GAPLESS_ALBUM) {
                    Id3Frame uint8Attribute4 = parseUint8Attribute(i2, "ITUNESGAPLESS", parsableByteArray, false, true);
                    parsableByteArray.setPosition(i);
                    return uint8Attribute4;
                }
                if (i2 == TYPE_TV_SORT_SHOW) {
                    TextInformationFrame textAttribute16 = parseTextAttribute(i2, "TVSHOWSORT", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute16;
                }
                if (i2 == TYPE_TV_SHOW) {
                    TextInformationFrame textAttribute17 = parseTextAttribute(i2, "TVSHOW", parsableByteArray);
                    parsableByteArray.setPosition(i);
                    return textAttribute17;
                }
                if (i2 == TYPE_INTERNAL) {
                    Id3Frame internalAttribute = parseInternalAttribute(parsableByteArray, i);
                    parsableByteArray.setPosition(i);
                    return internalAttribute;
                }
            }
            Log.d(TAG, "Skipped unknown metadata entry: " + Atom.getAtomTypeString(i2));
            parsableByteArray.setPosition(i);
            return null;
        } catch (Throwable th) {
            parsableByteArray.setPosition(i);
            throw th;
        }
    }

    @Nullable
    private static TextInformationFrame parseIndexAndCountAttribute(int i, String str, ParsableByteArray parsableByteArray) {
        int i2 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385 && i2 >= 22) {
            parsableByteArray.skipBytes(10);
            int unsignedShort = parsableByteArray.readUnsignedShort();
            if (unsignedShort > 0) {
                String strM23m = Insets$$ExternalSyntheticOutline0.m23m("", unsignedShort);
                int unsignedShort2 = parsableByteArray.readUnsignedShort();
                if (unsignedShort2 > 0) {
                    strM23m = strM23m + "/" + unsignedShort2;
                }
                return new TextInformationFrame(str, (String) null, ImmutableList.of(strM23m));
            }
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Failed to parse index/count attribute: ");
        sbM.append(Atom.getAtomTypeString(i));
        Log.w(TAG, sbM.toString());
        return null;
    }

    @Nullable
    private static Id3Frame parseInternalAttribute(ParsableByteArray parsableByteArray, int i) {
        String nullTerminatedString = null;
        String nullTerminatedString2 = null;
        int i2 = -1;
        int i3 = -1;
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int i4 = parsableByteArray.readInt();
            int i5 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            if (i5 == 1835360622) {
                nullTerminatedString = parsableByteArray.readNullTerminatedString(i4 - 12);
            } else if (i5 == 1851878757) {
                nullTerminatedString2 = parsableByteArray.readNullTerminatedString(i4 - 12);
            } else {
                if (i5 == 1684108385) {
                    i2 = position;
                    i3 = i4;
                }
                parsableByteArray.skipBytes(i4 - 12);
            }
        }
        if (nullTerminatedString == null || nullTerminatedString2 == null || i2 == -1) {
            return null;
        }
        parsableByteArray.setPosition(i2);
        parsableByteArray.skipBytes(16);
        return new InternalFrame(nullTerminatedString, nullTerminatedString2, parsableByteArray.readNullTerminatedString(i3 - 16));
    }

    @Nullable
    public static MdtaMetadataEntry parseMdtaMetadataEntryFromIlst(ParsableByteArray parsableByteArray, int i, String str) {
        while (true) {
            int position = parsableByteArray.getPosition();
            if (position >= i) {
                return null;
            }
            int i2 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1684108385) {
                int i3 = parsableByteArray.readInt();
                int i4 = parsableByteArray.readInt();
                int i5 = i2 - 16;
                byte[] bArr = new byte[i5];
                parsableByteArray.readBytes(bArr, 0, i5);
                return new MdtaMetadataEntry(str, bArr, i4, i3);
            }
            parsableByteArray.setPosition(position + i2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0012  */
    @Nullable
    private static TextInformationFrame parseStandardGenreAttribute(ParsableByteArray parsableByteArray) {
        String str;
        int uint8AttributeValue = parseUint8AttributeValue(parsableByteArray);
        if (uint8AttributeValue > 0) {
            String[] strArr = STANDARD_GENRES;
            if (uint8AttributeValue <= 192) {
                str = strArr[uint8AttributeValue - 1];
            } else {
                str = null;
            }
        } else {
            str = null;
        }
        if (str != null) {
            return new TextInformationFrame("TCON", (String) null, ImmutableList.of(str));
        }
        Log.w(TAG, "Failed to parse standard genre code");
        return null;
    }

    @Nullable
    private static TextInformationFrame parseTextAttribute(int i, String str, ParsableByteArray parsableByteArray) {
        int i2 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return new TextInformationFrame(str, (String) null, ImmutableList.of(parsableByteArray.readNullTerminatedString(i2 - 16)));
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Failed to parse text attribute: ");
        sbM.append(Atom.getAtomTypeString(i));
        Log.w(TAG, sbM.toString());
        return null;
    }

    @Nullable
    private static Id3Frame parseUint8Attribute(int i, String str, ParsableByteArray parsableByteArray, boolean z, boolean z2) {
        int uint8AttributeValue = parseUint8AttributeValue(parsableByteArray);
        if (z2) {
            uint8AttributeValue = Math.min(1, uint8AttributeValue);
        }
        if (uint8AttributeValue >= 0) {
            return z ? new TextInformationFrame(str, (String) null, ImmutableList.of(Integer.toString(uint8AttributeValue))) : new CommentFrame(C.LANGUAGE_UNDETERMINED, str, Integer.toString(uint8AttributeValue));
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Failed to parse uint8 attribute: ");
        sbM.append(Atom.getAtomTypeString(i));
        Log.w(TAG, sbM.toString());
        return null;
    }

    private static int parseUint8AttributeValue(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return parsableByteArray.readUnsignedByte();
        }
        Log.w(TAG, "Failed to parse uint8 attribute value");
        return -1;
    }

    public static void setFormatGaplessInfo(int i, GaplessInfoHolder gaplessInfoHolder, Format.Builder builder) {
        if (i == 1 && gaplessInfoHolder.hasGaplessInfo()) {
            builder.setEncoderDelay(gaplessInfoHolder.encoderDelay).setEncoderPadding(gaplessInfoHolder.encoderPadding);
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003c  */
    public static void setFormatMetadata(int i, @Nullable Metadata metadata, @Nullable Metadata metadata2, Format.Builder builder, Metadata... metadataArr) {
        Metadata metadata3 = new Metadata(new Metadata.Entry[0]);
        if (i == 1) {
            if (metadata == null) {
                metadata = metadata3;
                break;
            }
        } else {
            if (i != 2 || metadata2 == null) {
                metadata = metadata3;
                break;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= metadata2.length()) {
                    metadata = metadata3;
                    break;
                }
                Metadata.Entry entry = metadata2.get(i2);
                if (entry instanceof MdtaMetadataEntry) {
                    MdtaMetadataEntry mdtaMetadataEntry = (MdtaMetadataEntry) entry;
                    if (MdtaMetadataEntry.KEY_ANDROID_CAPTURE_FPS.equals(mdtaMetadataEntry.key)) {
                        metadata = new Metadata(mdtaMetadataEntry);
                        break;
                    }
                }
                i2++;
            }
        }
        for (Metadata metadata4 : metadataArr) {
            metadata = metadata.copyWithAppendedEntriesFrom(metadata4);
        }
        if (metadata.length() > 0) {
            builder.setMetadata(metadata);
        }
    }
}
