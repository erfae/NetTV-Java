package com.google.android.exoplayer2.metadata.icy;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class IcyDecoder extends SimpleMetadataDecoder {
    private static final Pattern METADATA_ELEMENT = Pattern.compile("(.+?)='(.*?)';", 32);
    private static final String STREAM_KEY_NAME = "streamtitle";
    private static final String STREAM_KEY_URL = "streamurl";
    private final CharsetDecoder utf8Decoder = Charsets.UTF_8.newDecoder();
    private final CharsetDecoder iso88591Decoder = Charsets.ISO_8859_1.newDecoder();

    @Nullable
    private String decodeToString(ByteBuffer byteBuffer) {
        try {
            String string = this.utf8Decoder.decode(byteBuffer).toString();
            this.utf8Decoder.reset();
            byteBuffer.rewind();
            return string;
        } catch (CharacterCodingException unused) {
            this.utf8Decoder.reset();
            byteBuffer.rewind();
            try {
                return this.iso88591Decoder.decode(byteBuffer).toString();
            } catch (CharacterCodingException unused2) {
                return null;
            } finally {
                this.iso88591Decoder.reset();
                byteBuffer.rewind();
            }
        } catch (Throwable th) {
            this.utf8Decoder.reset();
            byteBuffer.rewind();
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    public final Metadata decode(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        String strDecodeToString = decodeToString(byteBuffer);
        byte[] bArr = new byte[byteBuffer.limit()];
        byteBuffer.get(bArr);
        String str = null;
        if (strDecodeToString == null) {
            return new Metadata(new IcyInfo(bArr, null, null));
        }
        Matcher matcher = METADATA_ELEMENT.matcher(strDecodeToString);
        String str2 = null;
        for (int iEnd = 0; matcher.find(iEnd); iEnd = matcher.end()) {
            String strGroup = matcher.group(1);
            String strGroup2 = matcher.group(2);
            if (strGroup != null) {
                String lowerCase = Ascii.toLowerCase(strGroup);
                Objects.requireNonNull(lowerCase);
                if (lowerCase.equals(STREAM_KEY_URL)) {
                    str2 = strGroup2;
                } else if (lowerCase.equals(STREAM_KEY_NAME)) {
                    str = strGroup2;
                }
            }
        }
        return new Metadata(new IcyInfo(bArr, str, str2));
    }
}
