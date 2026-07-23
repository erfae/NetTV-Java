package com.google.android.exoplayer2.extractor.ogg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.VorbisUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* JADX INFO: loaded from: classes.dex */
final class VorbisReader extends StreamReader {

    @Nullable
    private VorbisUtil.CommentHeader commentHeader;
    private int previousPacketBlockSize;
    private boolean seenFirstAudioPacket;

    @Nullable
    private VorbisUtil.VorbisIdHeader vorbisIdHeader;

    @Nullable
    private VorbisSetup vorbisSetup;

    public static final class VorbisSetup {
        public final VorbisUtil.CommentHeader commentHeader;
        public final int iLogModes;
        public final VorbisUtil.VorbisIdHeader idHeader;
        public final VorbisUtil.Mode[] modes;
        public final byte[] setupHeaderData;

        public VorbisSetup(VorbisUtil.VorbisIdHeader vorbisIdHeader, VorbisUtil.CommentHeader commentHeader, byte[] bArr, VorbisUtil.Mode[] modeArr, int i) {
            this.idHeader = vorbisIdHeader;
            this.commentHeader = commentHeader;
            this.setupHeaderData = bArr;
            this.modes = modeArr;
            this.iLogModes = i;
        }
    }

    private static int decodeBlockSize(byte b, VorbisSetup vorbisSetup) {
        return !vorbisSetup.modes[(b >> 1) & (255 >>> (8 - vorbisSetup.iLogModes))].blockFlag ? vorbisSetup.idHeader.blockSize0 : vorbisSetup.idHeader.blockSize1;
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, parsableByteArray, true);
        } catch (ParserException unused) {
            return false;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public final void onSeekEnd(long j) {
        super.onSeekEnd(j);
        this.seenFirstAudioPacket = j != 0;
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.vorbisIdHeader;
        this.previousPacketBlockSize = vorbisIdHeader != null ? vorbisIdHeader.blockSize0 : 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public final long preparePayload(ParsableByteArray parsableByteArray) {
        if ((parsableByteArray.getData()[0] & 1) == 1) {
            return -1L;
        }
        int iDecodeBlockSize = decodeBlockSize(parsableByteArray.getData()[0], (VorbisSetup) Assertions.checkStateNotNull(this.vorbisSetup));
        long j = this.seenFirstAudioPacket ? (this.previousPacketBlockSize + iDecodeBlockSize) / 4 : 0;
        if (parsableByteArray.capacity() < parsableByteArray.limit() + 4) {
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit() + 4));
        } else {
            parsableByteArray.setLimit(parsableByteArray.limit() + 4);
        }
        byte[] data = parsableByteArray.getData();
        data[parsableByteArray.limit() - 4] = (byte) (j & 255);
        data[parsableByteArray.limit() - 3] = (byte) ((j >>> 8) & 255);
        data[parsableByteArray.limit() - 2] = (byte) ((j >>> 16) & 255);
        data[parsableByteArray.limit() - 1] = (byte) ((j >>> 24) & 255);
        this.seenFirstAudioPacket = true;
        this.previousPacketBlockSize = iDecodeBlockSize;
        return j;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public final boolean readHeaders(ParsableByteArray parsableByteArray, long j, StreamReader.SetupData setupData) throws IOException {
        if (this.vorbisSetup != null) {
            Assertions.checkNotNull(setupData.format);
            return false;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.vorbisIdHeader;
        VorbisSetup vorbisSetup = null;
        if (vorbisIdHeader == null) {
            this.vorbisIdHeader = VorbisUtil.readVorbisIdentificationHeader(parsableByteArray);
        } else {
            VorbisUtil.CommentHeader commentHeader = this.commentHeader;
            if (commentHeader == null) {
                this.commentHeader = VorbisUtil.readVorbisCommentHeader(parsableByteArray);
            } else {
                byte[] bArr = new byte[parsableByteArray.limit()];
                System.arraycopy(parsableByteArray.getData(), 0, bArr, 0, parsableByteArray.limit());
                VorbisUtil.Mode[] vorbisModes = VorbisUtil.readVorbisModes(parsableByteArray, vorbisIdHeader.channels);
                vorbisSetup = new VorbisSetup(vorbisIdHeader, commentHeader, bArr, vorbisModes, VorbisUtil.iLog(vorbisModes.length - 1));
            }
        }
        this.vorbisSetup = vorbisSetup;
        if (vorbisSetup == null) {
            return true;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader2 = vorbisSetup.idHeader;
        ArrayList arrayList = new ArrayList();
        arrayList.add(vorbisIdHeader2.data);
        arrayList.add(vorbisSetup.setupHeaderData);
        setupData.format = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_VORBIS).setAverageBitrate(vorbisIdHeader2.bitrateNominal).setPeakBitrate(vorbisIdHeader2.bitrateMaximum).setChannelCount(vorbisIdHeader2.channels).setSampleRate(vorbisIdHeader2.sampleRate).setInitializationData(arrayList).setMetadata(VorbisUtil.parseVorbisComments(ImmutableList.copyOf(vorbisSetup.commentHeader.comments))).build();
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public final void reset(boolean z) {
        super.reset(z);
        if (z) {
            this.vorbisSetup = null;
            this.vorbisIdHeader = null;
            this.commentHeader = null;
        }
        this.previousPacketBlockSize = 0;
        this.seenFirstAudioPacket = false;
    }
}
