package com.google.android.exoplayer2.extractor.avi;

import com.google.android.exoplayer2.util.ParsableByteArray;

/* JADX INFO: loaded from: classes.dex */
final class StreamNameChunk implements AviChunk {
    public final String name;

    private StreamNameChunk(String str) {
        this.name = str;
    }

    public static StreamNameChunk parseFrom(ParsableByteArray parsableByteArray) {
        return new StreamNameChunk(parsableByteArray.readString(parsableByteArray.bytesLeft()));
    }

    @Override // com.google.android.exoplayer2.extractor.avi.AviChunk
    public int getType() {
        return AviExtractor.FOURCC_strn;
    }
}
