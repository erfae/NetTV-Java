package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.SignedBytes;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class Ac4Util {
    public static final int AC40_SYNCWORD = 44096;
    public static final int AC41_SYNCWORD = 44097;
    private static final int CHANNEL_COUNT_2 = 2;
    public static final int HEADER_SIZE_FOR_PARSER = 16;
    public static final int MAX_RATE_BYTES_PER_SECOND = 336000;
    private static final int[] SAMPLE_COUNT = {PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT, 2000, 1920, 1601, 1600, 1001, 1000, 960, 800, 800, 480, 400, 400, 2048};
    public static final int SAMPLE_HEADER_SIZE = 7;

    public static final class SyncFrameInfo {
        public final int bitstreamVersion;
        public final int channelCount;
        public final int frameSize;
        public final int sampleCount;
        public final int sampleRate;

        public /* synthetic */ SyncFrameInfo(int i, int i2, int i3, int i4) {
            this(i, 2, i2, i3, i4);
        }

        private SyncFrameInfo(int i, int i2, int i3, int i4, int i5) {
            this.bitstreamVersion = i;
            this.channelCount = i2;
            this.sampleRate = i3;
            this.frameSize = i4;
            this.sampleCount = i5;
        }
    }

    private Ac4Util() {
    }

    public static void getAc4SampleHeader(int i, ParsableByteArray parsableByteArray) {
        parsableByteArray.reset(7);
        byte[] data = parsableByteArray.getData();
        data[0] = -84;
        data[1] = SignedBytes.MAX_POWER_OF_TWO;
        data[2] = -1;
        data[3] = -1;
        data[4] = (byte) ((i >> 16) & 255);
        data[5] = (byte) ((i >> 8) & 255);
        data[6] = (byte) (i & 255);
    }

    public static Format parseAc4AnnexEFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        parsableByteArray.skipBytes(1);
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.AUDIO_AC4).setChannelCount(2).setSampleRate(((parsableByteArray.readUnsignedByte() & 32) >> 5) == 1 ? OpusUtil.SAMPLE_RATE : 44100).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseAc4SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[16];
        int iPosition = byteBuffer.position();
        byteBuffer.get(bArr);
        byteBuffer.position(iPosition);
        return parseAc4SyncframeInfo(new ParsableBitArray(bArr)).sampleCount;
    }

    /* JADX WARN: Code duplicated, block: B:43:0x008a  */
    /* JADX WARN: Code duplicated, block: B:45:0x008e  */
    /* JADX WARN: Code duplicated, block: B:47:0x0092  */
    public static SyncFrameInfo parseAc4SyncframeInfo(ParsableBitArray parsableBitArray) {
        int i;
        int i2;
        int bits = parsableBitArray.readBits(16);
        int bits2 = parsableBitArray.readBits(16);
        if (bits2 == 65535) {
            bits2 = parsableBitArray.readBits(24);
            i = 7;
        } else {
            i = 4;
        }
        int i3 = bits2 + i;
        if (bits == 44097) {
            i3 += 2;
        }
        int bits3 = parsableBitArray.readBits(2);
        if (bits3 == 3) {
            bits3 += readVariableBits(parsableBitArray, 2);
        }
        int bits4 = parsableBitArray.readBits(10);
        if (parsableBitArray.readBit() && parsableBitArray.readBits(3) > 0) {
            parsableBitArray.skipBits(2);
        }
        int i4 = parsableBitArray.readBit() ? OpusUtil.SAMPLE_RATE : 44100;
        int bits5 = parsableBitArray.readBits(4);
        if (i4 == 44100 && bits5 == 13) {
            i2 = SAMPLE_COUNT[bits5];
        } else if (i4 == 48000) {
            int[] iArr = SAMPLE_COUNT;
            if (bits5 < iArr.length) {
                int i5 = iArr[bits5];
                int i6 = bits4 % 5;
                if (i6 == 1) {
                    if (bits5 != 3 || bits5 == 8) {
                        i5++;
                    }
                } else if (i6 != 2) {
                    if (i6 != 3) {
                        if (i6 == 4 && (bits5 == 3 || bits5 == 8 || bits5 == 11)) {
                            i5++;
                        }
                    } else if (bits5 != 3) {
                        i5++;
                    } else {
                        i5++;
                    }
                } else if (bits5 == 8 || bits5 == 11) {
                    i5++;
                }
                i2 = i5;
            } else {
                i2 = 0;
            }
        } else {
            i2 = 0;
        }
        return new SyncFrameInfo(bits3, i4, i3, i2);
    }

    public static int parseAc4SyncframeSize(byte[] bArr, int i) {
        int i2 = 7;
        if (bArr.length < 7) {
            return -1;
        }
        int i3 = ((bArr[2] & 255) << 8) | (bArr[3] & 255);
        if (i3 == 65535) {
            i3 = ((bArr[4] & 255) << 16) | ((bArr[5] & 255) << 8) | (bArr[6] & 255);
        } else {
            i2 = 4;
        }
        if (i == 44097) {
            i2 += 2;
        }
        return i3 + i2;
    }

    private static int readVariableBits(ParsableBitArray parsableBitArray, int i) {
        int i2 = 0;
        while (true) {
            int bits = parsableBitArray.readBits(i) + i2;
            if (!parsableBitArray.readBit()) {
                return bits;
            }
            i2 = (bits + 1) << i;
        }
    }
}
