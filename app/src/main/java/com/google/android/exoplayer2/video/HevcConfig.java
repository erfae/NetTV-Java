package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class HevcConfig {
    private static final int SPS_NAL_UNIT_TYPE = 33;

    @Nullable
    public final String codecs;
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthHeightRatio;
    public final int width;

    private HevcConfig(List<byte[]> list, int i, int i2, int i3, float f, @Nullable String str, int i4, int i5, int i6) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i;
        this.width = i2;
        this.height = i3;
        this.pixelWidthHeightRatio = f;
        this.codecs = str;
        this.colorSpace = i4;
        this.colorRange = i5;
        this.colorTransfer = i6;
    }

    public static HevcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        try {
            parsableByteArray.skipBytes(21);
            int unsignedByte = parsableByteArray.readUnsignedByte() & 3;
            int unsignedByte2 = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < unsignedByte2; i3++) {
                parsableByteArray.skipBytes(1);
                int unsignedShort = parsableByteArray.readUnsignedShort();
                for (int i4 = 0; i4 < unsignedShort; i4++) {
                    int unsignedShort2 = parsableByteArray.readUnsignedShort();
                    i2 += unsignedShort2 + 4;
                    parsableByteArray.skipBytes(unsignedShort2);
                }
            }
            parsableByteArray.setPosition(position);
            byte[] bArr = new byte[i2];
            String strBuildHevcCodecString = null;
            int i5 = 0;
            int i6 = 0;
            int i7 = -1;
            int i8 = -1;
            float f = 1.0f;
            int i9 = -1;
            int i10 = -1;
            int i11 = -1;
            while (i5 < unsignedByte2) {
                int unsignedByte3 = parsableByteArray.readUnsignedByte() & 63;
                int unsignedShort3 = parsableByteArray.readUnsignedShort();
                int i12 = 0;
                while (i12 < unsignedShort3) {
                    int unsignedShort4 = parsableByteArray.readUnsignedShort();
                    byte[] bArr2 = NalUnitUtil.NAL_START_CODE;
                    int i13 = unsignedByte2;
                    System.arraycopy(bArr2, i, bArr, i6, bArr2.length);
                    int length = i6 + bArr2.length;
                    System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), bArr, length, unsignedShort4);
                    if (unsignedByte3 == 33 && i12 == 0) {
                        NalUnitUtil.H265SpsData h265SpsNalUnit = NalUnitUtil.parseH265SpsNalUnit(bArr, length, length + unsignedShort4);
                        int i14 = h265SpsNalUnit.width;
                        i8 = h265SpsNalUnit.height;
                        int i15 = h265SpsNalUnit.colorSpace;
                        int i16 = h265SpsNalUnit.colorRange;
                        int i17 = h265SpsNalUnit.colorTransfer;
                        float f2 = h265SpsNalUnit.pixelWidthHeightRatio;
                        i7 = i14;
                        i11 = i17;
                        strBuildHevcCodecString = CodecSpecificDataUtil.buildHevcCodecString(h265SpsNalUnit.generalProfileSpace, h265SpsNalUnit.generalTierFlag, h265SpsNalUnit.generalProfileIdc, h265SpsNalUnit.generalProfileCompatibilityFlags, h265SpsNalUnit.constraintBytes, h265SpsNalUnit.generalLevelIdc);
                        i10 = i16;
                        f = f2;
                        i9 = i15;
                    }
                    i6 = length + unsignedShort4;
                    parsableByteArray.skipBytes(unsignedShort4);
                    i12++;
                    unsignedByte2 = i13;
                    unsignedByte3 = unsignedByte3;
                    unsignedShort3 = unsignedShort3;
                    i = 0;
                }
                i5++;
                i = 0;
            }
            return new HevcConfig(i2 == 0 ? Collections.emptyList() : Collections.singletonList(bArr), unsignedByte + 1, i7, i8, f, strBuildHevcCodecString, i9, i10, i11);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw ParserException.createForMalformedContainer("Error parsing HEVC config", e);
        }
    }
}
