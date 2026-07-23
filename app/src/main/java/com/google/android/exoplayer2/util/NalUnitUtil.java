package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class NalUnitUtil {
    public static final int EXTENDED_SAR = 255;
    private static final int H264_NAL_UNIT_TYPE_SEI = 6;
    private static final int H264_NAL_UNIT_TYPE_SPS = 7;
    private static final int H265_NAL_UNIT_TYPE_PREFIX_SEI = 39;
    public static final int NAL_UNIT_TYPE_AUD = 9;
    public static final int NAL_UNIT_TYPE_IDR = 5;
    public static final int NAL_UNIT_TYPE_NON_IDR = 1;
    public static final int NAL_UNIT_TYPE_PARTITION_A = 2;
    public static final int NAL_UNIT_TYPE_PPS = 8;
    public static final int NAL_UNIT_TYPE_SEI = 6;
    public static final int NAL_UNIT_TYPE_SPS = 7;
    private static final String TAG = "NalUnitUtil";
    public static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    public static final float[] ASPECT_RATIO_IDC_VALUES = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, 2.0f};
    private static final Object scratchEscapePositionsLock = new Object();
    private static int[] scratchEscapePositions = new int[10];

    public static final class H265SpsData {
        public final int colorRange;
        public final int colorSpace;
        public final int colorTransfer;
        public final int[] constraintBytes;
        public final int generalLevelIdc;
        public final int generalProfileCompatibilityFlags;
        public final int generalProfileIdc;
        public final int generalProfileSpace;
        public final boolean generalTierFlag;
        public final int height;
        public final float pixelWidthHeightRatio;
        public final int seqParameterSetId;
        public final int width;

        public H265SpsData(int i, boolean z, int i2, int i3, int[] iArr, int i4, int i5, int i6, int i7, float f, int i8, int i9, int i10) {
            this.generalProfileSpace = i;
            this.generalTierFlag = z;
            this.generalProfileIdc = i2;
            this.generalProfileCompatibilityFlags = i3;
            this.constraintBytes = iArr;
            this.generalLevelIdc = i4;
            this.seqParameterSetId = i5;
            this.width = i6;
            this.height = i7;
            this.pixelWidthHeightRatio = f;
            this.colorSpace = i8;
            this.colorRange = i9;
            this.colorTransfer = i10;
        }
    }

    public static final class PpsData {
        public final boolean bottomFieldPicOrderInFramePresentFlag;
        public final int picParameterSetId;
        public final int seqParameterSetId;

        public PpsData(int i, int i2, boolean z) {
            this.picParameterSetId = i;
            this.seqParameterSetId = i2;
            this.bottomFieldPicOrderInFramePresentFlag = z;
        }
    }

    public static final class SpsData {
        public final int constraintsFlagsAndReservedZero2Bits;
        public final boolean deltaPicOrderAlwaysZeroFlag;
        public final boolean frameMbsOnlyFlag;
        public final int frameNumLength;
        public final int height;
        public final int levelIdc;
        public final int maxNumRefFrames;
        public final int picOrderCntLsbLength;
        public final int picOrderCountType;
        public final float pixelWidthHeightRatio;
        public final int profileIdc;
        public final boolean separateColorPlaneFlag;
        public final int seqParameterSetId;
        public final int width;

        public SpsData(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f, boolean z, boolean z2, int i8, int i9, int i10, boolean z3) {
            this.profileIdc = i;
            this.constraintsFlagsAndReservedZero2Bits = i2;
            this.levelIdc = i3;
            this.seqParameterSetId = i4;
            this.maxNumRefFrames = i5;
            this.width = i6;
            this.height = i7;
            this.pixelWidthHeightRatio = f;
            this.separateColorPlaneFlag = z;
            this.frameMbsOnlyFlag = z2;
            this.frameNumLength = i8;
            this.picOrderCountType = i9;
            this.picOrderCntLsbLength = i10;
            this.deltaPicOrderAlwaysZeroFlag = z3;
        }
    }

    private NalUnitUtil() {
    }

    public static void clearPrefixFlags(boolean[] zArr) {
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
    }

    public static void discardToSps(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i + 1;
            if (i3 >= iPosition) {
                byteBuffer.clear();
                return;
            }
            int i4 = byteBuffer.get(i) & 255;
            if (i2 == 3) {
                if (i4 == 1 && (byteBuffer.get(i3) & Ascii.US) == 7) {
                    ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
                    byteBufferDuplicate.position(i - 3);
                    byteBufferDuplicate.limit(iPosition);
                    byteBuffer.position(0);
                    byteBuffer.put(byteBufferDuplicate);
                    return;
                }
            } else if (i4 == 0) {
                i2++;
            }
            if (i4 != 0) {
                i2 = 0;
            }
            i = i3;
        }
    }

    public static int findNalUnit(byte[] bArr, int i, int i2, boolean[] zArr) {
        int i3 = i2 - i;
        Assertions.checkState(i3 >= 0);
        if (i3 == 0) {
            return i2;
        }
        if (zArr[0]) {
            clearPrefixFlags(zArr);
            return i - 3;
        }
        if (i3 > 1 && zArr[1] && bArr[i] == 1) {
            clearPrefixFlags(zArr);
            return i - 2;
        }
        if (i3 > 2 && zArr[2] && bArr[i] == 0 && bArr[i + 1] == 1) {
            clearPrefixFlags(zArr);
            return i - 1;
        }
        int i4 = i2 - 1;
        int i5 = i + 2;
        while (i5 < i4) {
            if ((bArr[i5] & 254) == 0) {
                int i6 = i5 - 2;
                if (bArr[i6] == 0 && bArr[i5 - 1] == 0 && bArr[i5] == 1) {
                    clearPrefixFlags(zArr);
                    return i6;
                }
                i5 -= 2;
            }
            i5 += 3;
        }
        zArr[0] = i3 <= 2 ? !(i3 != 2 ? !(zArr[1] && bArr[i4] == 1) : !(zArr[2] && bArr[i2 + (-2)] == 0 && bArr[i4] == 1)) : bArr[i2 + (-3)] == 0 && bArr[i2 + (-2)] == 0 && bArr[i4] == 1;
        zArr[1] = i3 <= 1 ? zArr[2] && bArr[i4] == 0 : bArr[i2 + (-2)] == 0 && bArr[i4] == 0;
        zArr[2] = bArr[i4] == 0;
        return i2;
    }

    private static int findNextUnescapeIndex(byte[] bArr, int i, int i2) {
        while (i < i2 - 2) {
            if (bArr[i] == 0 && bArr[i + 1] == 0 && bArr[i + 2] == 3) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static int getH265NalUnitType(byte[] bArr, int i) {
        return (bArr[i + 3] & 126) >> 1;
    }

    public static int getNalUnitType(byte[] bArr, int i) {
        return bArr[i + 3] & Ascii.US;
    }

    public static boolean isNalUnitSei(@Nullable String str, byte b) {
        if (MimeTypes.VIDEO_H264.equals(str) && (b & Ascii.US) == 6) {
            return true;
        }
        return MimeTypes.VIDEO_H265.equals(str) && ((b & 126) >> 1) == 39;
    }

    public static H265SpsData parseH265SpsNalUnit(byte[] bArr, int i, int i2) {
        return parseH265SpsNalUnitPayload(bArr, i + 2, i2);
    }

    /* JADX WARN: Code duplicated, block: B:75:0x0166  */
    /* JADX WARN: Code duplicated, block: B:87:0x019c  */
    public static H265SpsData parseH265SpsNalUnitPayload(byte[] bArr, int i, int i2) {
        float f;
        int i3;
        int i4;
        int i5;
        float f2;
        int iIsoTransferCharacteristicsToColorTransfer;
        int i6;
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        parsableNalUnitBitArray.skipBits(4);
        int bits = parsableNalUnitBitArray.readBits(3);
        parsableNalUnitBitArray.skipBit();
        int bits2 = parsableNalUnitBitArray.readBits(2);
        boolean bit = parsableNalUnitBitArray.readBit();
        int bits3 = parsableNalUnitBitArray.readBits(5);
        int i7 = 0;
        for (int i8 = 0; i8 < 32; i8++) {
            if (parsableNalUnitBitArray.readBit()) {
                i7 |= 1 << i8;
            }
        }
        int[] iArr = new int[6];
        for (int i9 = 0; i9 < 6; i9++) {
            iArr[i9] = parsableNalUnitBitArray.readBits(8);
        }
        int bits4 = parsableNalUnitBitArray.readBits(8);
        int i10 = 0;
        for (int i11 = 0; i11 < bits; i11++) {
            if (parsableNalUnitBitArray.readBit()) {
                i10 += 89;
            }
            if (parsableNalUnitBitArray.readBit()) {
                i10 += 8;
            }
        }
        parsableNalUnitBitArray.skipBits(i10);
        if (bits > 0) {
            parsableNalUnitBitArray.skipBits((8 - bits) * 2);
        }
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (unsignedExpGolombCodedInt2 == 3) {
            parsableNalUnitBitArray.skipBit();
        }
        int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit()) {
            int unsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            unsignedExpGolombCodedInt3 -= (unsignedExpGolombCodedInt5 + unsignedExpGolombCodedInt6) * ((unsignedExpGolombCodedInt2 == 1 || unsignedExpGolombCodedInt2 == 2) ? 2 : 1);
            unsignedExpGolombCodedInt4 -= (unsignedExpGolombCodedInt7 + unsignedExpGolombCodedInt8) * (unsignedExpGolombCodedInt2 == 1 ? 2 : 1);
        }
        int i12 = unsignedExpGolombCodedInt4;
        int i13 = unsignedExpGolombCodedInt3;
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        for (int i14 = parsableNalUnitBitArray.readBit() ? 0 : bits; i14 <= bits; i14++) {
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            skipH265ScalingList(parsableNalUnitBitArray);
        }
        parsableNalUnitBitArray.skipBits(2);
        if (parsableNalUnitBitArray.readBit()) {
            parsableNalUnitBitArray.skipBits(8);
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
        }
        skipShortTermReferencePictureSets(parsableNalUnitBitArray);
        if (parsableNalUnitBitArray.readBit()) {
            for (int i15 = 0; i15 < parsableNalUnitBitArray.readUnsignedExpGolombCodedInt(); i15++) {
                parsableNalUnitBitArray.skipBits(unsignedExpGolombCodedInt9 + 4 + 1);
            }
        }
        parsableNalUnitBitArray.skipBits(2);
        int iIsoColorPrimariesToColorSpace = -1;
        if (parsableNalUnitBitArray.readBit()) {
            if (parsableNalUnitBitArray.readBit()) {
                int bits5 = parsableNalUnitBitArray.readBits(8);
                if (bits5 == 255) {
                    int bits6 = parsableNalUnitBitArray.readBits(16);
                    int bits7 = parsableNalUnitBitArray.readBits(16);
                    if (bits6 == 0 || bits7 == 0) {
                        f2 = 1.0f;
                    } else {
                        f2 = bits6 / bits7;
                    }
                } else {
                    float[] fArr = ASPECT_RATIO_IDC_VALUES;
                    if (bits5 < fArr.length) {
                        f2 = fArr[bits5];
                    } else {
                        Insets$$ExternalSyntheticOutline0.m27m("Unexpected aspect_ratio_idc value: ", bits5, TAG);
                        f2 = 1.0f;
                    }
                }
            } else {
                f2 = 1.0f;
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBit();
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBits(3);
                boolean bit2 = parsableNalUnitBitArray.readBit();
                if (parsableNalUnitBitArray.readBit()) {
                    int bits8 = parsableNalUnitBitArray.readBits(8);
                    int bits9 = parsableNalUnitBitArray.readBits(8);
                    parsableNalUnitBitArray.skipBits(8);
                    iIsoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace(bits8);
                    i6 = bit2 ? 1 : 2;
                    iIsoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer(bits9);
                } else {
                    iIsoTransferCharacteristicsToColorTransfer = -1;
                    i6 = -1;
                }
            } else {
                iIsoTransferCharacteristicsToColorTransfer = -1;
                i6 = -1;
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                i12 *= 2;
            }
            i5 = iIsoTransferCharacteristicsToColorTransfer;
            i4 = i6;
            i3 = i12;
            f = f2;
        } else {
            f = 1.0f;
            i3 = i12;
            iIsoColorPrimariesToColorSpace = -1;
            i4 = -1;
            i5 = -1;
        }
        return new H265SpsData(bits2, bit, bits3, i7, iArr, bits4, unsignedExpGolombCodedInt, i13, i3, f, iIsoColorPrimariesToColorSpace, i4, i5);
    }

    public static PpsData parsePpsNalUnit(byte[] bArr, int i, int i2) {
        return parsePpsNalUnitPayload(bArr, i + 1, i2);
    }

    public static PpsData parsePpsNalUnitPayload(byte[] bArr, int i, int i2) {
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        return new PpsData(unsignedExpGolombCodedInt, unsignedExpGolombCodedInt2, parsableNalUnitBitArray.readBit());
    }

    public static SpsData parseSpsNalUnit(byte[] bArr, int i, int i2) {
        return parseSpsNalUnitPayload(bArr, i + 1, i2);
    }

    /* JADX WARN: Code duplicated, block: B:57:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:60:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:62:0x0105  */
    /* JADX WARN: Code duplicated, block: B:63:0x0108  */
    /* JADX WARN: Code duplicated, block: B:65:0x010b  */
    /* JADX WARN: Code duplicated, block: B:66:0x010f  */
    /* JADX WARN: Code duplicated, block: B:68:0x0114  */
    /* JADX WARN: Code duplicated, block: B:86:0x0161  */
    public static SpsData parseSpsNalUnitPayload(byte[] bArr, int i, int i2) {
        int i3;
        boolean z;
        int unsignedExpGolombCodedInt;
        boolean z2;
        int i4;
        int i5;
        boolean bit;
        int i6;
        int i7;
        float f;
        int i8;
        int i9;
        int i10;
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        int bits = parsableNalUnitBitArray.readBits(8);
        int bits2 = parsableNalUnitBitArray.readBits(8);
        int bits3 = parsableNalUnitBitArray.readBits(8);
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (bits == 100 || bits == 110 || bits == 122 || bits == 244 || bits == 44 || bits == 83 || bits == 86 || bits == 118 || bits == 128 || bits == 138) {
            int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            boolean bit2 = unsignedExpGolombCodedInt3 == 3 ? parsableNalUnitBitArray.readBit() : false;
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                int i11 = unsignedExpGolombCodedInt3 != 3 ? 8 : 12;
                int i12 = 0;
                while (i12 < i11) {
                    if (parsableNalUnitBitArray.readBit()) {
                        skipScalingList(parsableNalUnitBitArray, i12 < 6 ? 16 : 64);
                    }
                    i12++;
                }
            }
            i3 = unsignedExpGolombCodedInt3;
            z = bit2;
        } else {
            i3 = 1;
            z = false;
        }
        int unsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        int unsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (unsignedExpGolombCodedInt5 != 0) {
            if (unsignedExpGolombCodedInt5 == 1) {
                boolean bit3 = parsableNalUnitBitArray.readBit();
                parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                long unsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                bits = bits;
                for (int i13 = 0; i13 < unsignedExpGolombCodedInt6; i13++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                }
                z2 = bit3;
                i4 = 0;
            } else {
                unsignedExpGolombCodedInt = 0;
            }
            int unsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
            i5 = 1;
            int unsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
            int unsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
            bit = parsableNalUnitBitArray.readBit();
            int i14 = (2 - (bit ? 1 : 0)) * unsignedExpGolombCodedInt9;
            if (!bit) {
                parsableNalUnitBitArray.skipBit();
            }
            parsableNalUnitBitArray.skipBit();
            i6 = unsignedExpGolombCodedInt8 * 16;
            i7 = i14 * 16;
            if (parsableNalUnitBitArray.readBit()) {
                int unsignedExpGolombCodedInt10 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt11 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt12 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt13 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                if (i3 == 0) {
                    i10 = 2 - (bit ? 1 : 0);
                } else {
                    if (i3 == 3) {
                        i8 = 1;
                        i9 = 1;
                    } else {
                        i8 = 1;
                        i9 = 2;
                    }
                    if (i3 == i8) {
                        i8 = 2;
                    }
                    i10 = (2 - (bit ? 1 : 0)) * i8;
                    i5 = i9;
                }
                i6 -= (unsignedExpGolombCodedInt10 + unsignedExpGolombCodedInt11) * i5;
                i7 -= (unsignedExpGolombCodedInt12 + unsignedExpGolombCodedInt13) * i10;
            }
            int i15 = i6;
            float f2 = 1.0f;
            if (parsableNalUnitBitArray.readBit() || !parsableNalUnitBitArray.readBit()) {
                f = 1.0f;
            } else {
                int bits4 = parsableNalUnitBitArray.readBits(8);
                if (bits4 == 255) {
                    int bits5 = parsableNalUnitBitArray.readBits(16);
                    int bits6 = parsableNalUnitBitArray.readBits(16);
                    if (bits5 != 0 && bits6 != 0) {
                        f2 = bits5 / bits6;
                    }
                    f = f2;
                } else {
                    float[] fArr = ASPECT_RATIO_IDC_VALUES;
                    if (bits4 < fArr.length) {
                        f = fArr[bits4];
                    } else {
                        Insets$$ExternalSyntheticOutline0.m27m("Unexpected aspect_ratio_idc value: ", bits4, TAG);
                        f = 1.0f;
                    }
                }
            }
            return new SpsData(bits, bits2, bits3, unsignedExpGolombCodedInt2, unsignedExpGolombCodedInt7, i15, i7, f, z, bit, unsignedExpGolombCodedInt4, unsignedExpGolombCodedInt5, i4, z2);
        }
        unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        i4 = unsignedExpGolombCodedInt;
        z2 = false;
        int unsignedExpGolombCodedInt14 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        i5 = 1;
        int unsignedExpGolombCodedInt15 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        int unsignedExpGolombCodedInt16 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        bit = parsableNalUnitBitArray.readBit();
        int i16 = (2 - (bit ? 1 : 0)) * unsignedExpGolombCodedInt16;
        if (!bit) {
            parsableNalUnitBitArray.skipBit();
        }
        parsableNalUnitBitArray.skipBit();
        i6 = unsignedExpGolombCodedInt15 * 16;
        i7 = i16 * 16;
        if (parsableNalUnitBitArray.readBit()) {
            int unsignedExpGolombCodedInt17 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt18 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt19 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt110 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            if (i3 == 0) {
                i10 = 2 - (bit ? 1 : 0);
            } else {
                if (i3 == 3) {
                    i8 = 1;
                    i9 = 1;
                } else {
                    i8 = 1;
                    i9 = 2;
                }
                if (i3 == i8) {
                    i8 = 2;
                }
                i10 = (2 - (bit ? 1 : 0)) * i8;
                i5 = i9;
            }
            i6 -= (unsignedExpGolombCodedInt17 + unsignedExpGolombCodedInt18) * i5;
            i7 -= (unsignedExpGolombCodedInt19 + unsignedExpGolombCodedInt110) * i10;
        }
        int i17 = i6;
        float f3 = 1.0f;
        if (parsableNalUnitBitArray.readBit()) {
            f = 1.0f;
        } else {
            f = 1.0f;
        }
        return new SpsData(bits, bits2, bits3, unsignedExpGolombCodedInt2, unsignedExpGolombCodedInt14, i17, i7, f, z, bit, unsignedExpGolombCodedInt4, unsignedExpGolombCodedInt5, i4, z2);
    }

    private static void skipH265ScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        for (int i = 0; i < 4; i++) {
            int i2 = 0;
            while (i2 < 6) {
                int i3 = 1;
                if (parsableNalUnitBitArray.readBit()) {
                    int iMin = Math.min(64, 1 << ((i << 1) + 4));
                    if (i > 1) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                    for (int i4 = 0; i4 < iMin; i4++) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                } else {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                }
                if (i == 3) {
                    i3 = 3;
                }
                i2 += i3;
            }
        }
    }

    private static void skipScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray, int i) {
        int signedExpGolombCodedInt = 8;
        int i2 = 8;
        for (int i3 = 0; i3 < i; i3++) {
            if (signedExpGolombCodedInt != 0) {
                signedExpGolombCodedInt = ((parsableNalUnitBitArray.readSignedExpGolombCodedInt() + i2) + 256) % 256;
            }
            if (signedExpGolombCodedInt != 0) {
                i2 = signedExpGolombCodedInt;
            }
        }
    }

    private static void skipShortTermReferencePictureSets(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int[] iArr = new int[0];
        int[] iArrCopyOf = new int[0];
        int i = -1;
        int i2 = -1;
        int i3 = 0;
        while (i3 < unsignedExpGolombCodedInt) {
            if (i3 != 0 && parsableNalUnitBitArray.readBit()) {
                int i4 = i + i2;
                int unsignedExpGolombCodedInt2 = (1 - ((parsableNalUnitBitArray.readBit() ? 1 : 0) * 2)) * (parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1);
                int i5 = i4 + 1;
                boolean[] zArr = new boolean[i5];
                for (int i6 = 0; i6 <= i4; i6++) {
                    if (parsableNalUnitBitArray.readBit()) {
                        zArr[i6] = true;
                    } else {
                        zArr[i6] = parsableNalUnitBitArray.readBit();
                    }
                }
                int[] iArr2 = new int[i5];
                int[] iArr3 = new int[i5];
                int i7 = 0;
                for (int i8 = i2 - 1; i8 >= 0; i8--) {
                    int i9 = iArrCopyOf[i8] + unsignedExpGolombCodedInt2;
                    if (i9 < 0 && zArr[i + i8]) {
                        iArr2[i7] = i9;
                        i7++;
                    }
                }
                if (unsignedExpGolombCodedInt2 < 0 && zArr[i4]) {
                    iArr2[i7] = unsignedExpGolombCodedInt2;
                    i7++;
                }
                for (int i10 = 0; i10 < i; i10++) {
                    int i11 = iArr[i10] + unsignedExpGolombCodedInt2;
                    if (i11 < 0 && zArr[i10]) {
                        iArr2[i7] = i11;
                        i7++;
                    }
                }
                int[] iArrCopyOf2 = Arrays.copyOf(iArr2, i7);
                int i12 = 0;
                for (int i13 = i - 1; i13 >= 0; i13--) {
                    int i14 = iArr[i13] + unsignedExpGolombCodedInt2;
                    if (i14 > 0 && zArr[i13]) {
                        iArr3[i12] = i14;
                        i12++;
                    }
                }
                if (unsignedExpGolombCodedInt2 > 0 && zArr[i4]) {
                    iArr3[i12] = unsignedExpGolombCodedInt2;
                    i12++;
                }
                for (int i15 = 0; i15 < i2; i15++) {
                    int i16 = iArrCopyOf[i15] + unsignedExpGolombCodedInt2;
                    if (i16 > 0 && zArr[i + i15]) {
                        iArr3[i12] = i16;
                        i12++;
                    }
                }
                iArrCopyOf = Arrays.copyOf(iArr3, i12);
                iArr = iArrCopyOf2;
                i = i7;
                i2 = i12;
            } else {
                int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int[] iArr4 = new int[unsignedExpGolombCodedInt3];
                for (int i17 = 0; i17 < unsignedExpGolombCodedInt3; i17++) {
                    iArr4[i17] = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
                    parsableNalUnitBitArray.skipBit();
                }
                int[] iArr5 = new int[unsignedExpGolombCodedInt4];
                for (int i18 = 0; i18 < unsignedExpGolombCodedInt4; i18++) {
                    iArr5[i18] = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
                    parsableNalUnitBitArray.skipBit();
                }
                i = unsignedExpGolombCodedInt3;
                iArr = iArr4;
                i2 = unsignedExpGolombCodedInt4;
                iArrCopyOf = iArr5;
            }
            i3++;
        }
    }

    public static int unescapeStream(byte[] bArr, int i) {
        int i2;
        synchronized (scratchEscapePositionsLock) {
            int iFindNextUnescapeIndex = 0;
            int i3 = 0;
            while (iFindNextUnescapeIndex < i) {
                try {
                    iFindNextUnescapeIndex = findNextUnescapeIndex(bArr, iFindNextUnescapeIndex, i);
                    if (iFindNextUnescapeIndex < i) {
                        int[] iArr = scratchEscapePositions;
                        if (iArr.length <= i3) {
                            scratchEscapePositions = Arrays.copyOf(iArr, iArr.length * 2);
                        }
                        scratchEscapePositions[i3] = iFindNextUnescapeIndex;
                        iFindNextUnescapeIndex += 3;
                        i3++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            i2 = i - i3;
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < i3; i6++) {
                int i7 = scratchEscapePositions[i6] - i5;
                System.arraycopy(bArr, i5, bArr, i4, i7);
                int i8 = i4 + i7;
                int i9 = i8 + 1;
                bArr[i8] = 0;
                i4 = i9 + 1;
                bArr[i9] = 0;
                i5 += i7 + 3;
            }
            System.arraycopy(bArr, i5, bArr, i4, i2 - i4);
        }
        return i2;
    }
}
