package com.google.common.io;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class ByteSink {

    public final class AsCharSink extends CharSink {
        private final Charset charset;

        @Override // com.google.common.io.CharSink
        public Writer openStream() throws IOException {
            return new OutputStreamWriter(ByteSink.this.openStream(), this.charset);
        }

        public String toString() {
            String string = ByteSink.this.toString();
            String strValueOf = String.valueOf(this.charset);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + Insets$$ExternalSyntheticOutline0.m(string, 13), string, ".asCharSink(", strValueOf, ")");
        }

        private AsCharSink(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }
    }

    public CharSink asCharSink(Charset charset) {
        return new AsCharSink(charset);
    }

    public OutputStream openBufferedStream() throws IOException {
        OutputStream outputStreamOpenStream = openStream();
        return outputStreamOpenStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStreamOpenStream : new BufferedOutputStream(outputStreamOpenStream);
    }

    public abstract OutputStream openStream() throws IOException;

    public void write(byte[] bArr) throws Throwable {
        Preconditions.checkNotNull(bArr);
        Closer closerCreate = Closer.create();
        try {
            OutputStream outputStream = (OutputStream) closerCreate.register(openStream());
            outputStream.write(bArr);
            outputStream.flush();
            closerCreate.close();
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    @CanIgnoreReturnValue
    public long writeFrom(InputStream inputStream) throws Throwable {
        Preconditions.checkNotNull(inputStream);
        Closer closerCreate = Closer.create();
        try {
            OutputStream outputStream = (OutputStream) closerCreate.register(openStream());
            long jCopy = ByteStreams.copy(inputStream, outputStream);
            outputStream.flush();
            closerCreate.close();
            return jCopy;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }
}
