package com.google.common.io;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class ByteSource {

    public class AsCharSource extends CharSource {
        public final Charset charset;

        public AsCharSource(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.CharSource
        public ByteSource asByteSource(Charset charset) {
            return charset.equals(this.charset) ? ByteSource.this : super.asByteSource(charset);
        }

        @Override // com.google.common.io.CharSource
        public Reader openStream() throws IOException {
            return new InputStreamReader(ByteSource.this.openStream(), this.charset);
        }

        @Override // com.google.common.io.CharSource
        public String read() throws IOException {
            return new String(ByteSource.this.read(), this.charset);
        }

        public String toString() {
            String string = ByteSource.this.toString();
            String strValueOf = String.valueOf(this.charset);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + Insets$$ExternalSyntheticOutline0.m(string, 15), string, ".asCharSource(", strValueOf, ")");
        }
    }

    public static class ByteArrayByteSource extends ByteSource {
        public final byte[] bytes;
        public final int length;
        public final int offset;

        public ByteArrayByteSource(byte[] bArr) {
            int length = bArr.length;
            this.bytes = bArr;
            this.offset = 0;
            this.length = length;
        }

        @Override // com.google.common.io.ByteSource
        public long copyTo(OutputStream outputStream) throws IOException {
            outputStream.write(this.bytes, this.offset, this.length);
            return this.length;
        }

        @Override // com.google.common.io.ByteSource
        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.bytes, this.offset, this.length);
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() {
            return this.length == 0;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() throws IOException {
            return openStream();
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() {
            return new ByteArrayInputStream(this.bytes, this.offset, this.length);
        }

        @Override // com.google.common.io.ByteSource
        public byte[] read() {
            byte[] bArr = this.bytes;
            int i = this.offset;
            return Arrays.copyOfRange(bArr, i, this.length + i);
        }

        @Override // com.google.common.io.ByteSource
        public long size() {
            return this.length;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf(this.length));
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long j, long j2) {
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            Preconditions.checkArgument(j2 >= 0, "length (%s) may not be negative", j2);
            long jMin = Math.min(j, this.length);
            return new ByteArrayByteSource(this.bytes, this.offset + ((int) jMin), (int) Math.min(j2, ((long) this.length) - jMin));
        }

        public String toString() {
            String strTruncate = Ascii.truncate(BaseEncoding.base16().encode(this.bytes, this.offset, this.length), 30, "...");
            return MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(strTruncate, 17), "ByteSource.wrap(", strTruncate, ")");
        }

        @Override // com.google.common.io.ByteSource
        @ParametricNullness
        public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
            byteProcessor.processBytes(this.bytes, this.offset, this.length);
            return byteProcessor.getResult();
        }

        public ByteArrayByteSource(byte[] bArr, int i, int i2) {
            this.bytes = bArr;
            this.offset = i;
            this.length = i2;
        }
    }

    public static final class ConcatenatedByteSource extends ByteSource {
        public final Iterable<? extends ByteSource> sources;

        public ConcatenatedByteSource(Iterable<? extends ByteSource> iterable) {
            this.sources = (Iterable) Preconditions.checkNotNull(iterable);
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            Iterator<? extends ByteSource> it = this.sources.iterator();
            while (it.hasNext()) {
                if (!it.next().isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.sources.iterator());
        }

        @Override // com.google.common.io.ByteSource
        public long size() throws IOException {
            Iterator<? extends ByteSource> it = this.sources.iterator();
            long size = 0;
            while (it.hasNext()) {
                size += it.next().size();
                if (size < 0) {
                    return Long.MAX_VALUE;
                }
            }
            return size;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Iterable<? extends ByteSource> iterable = this.sources;
            if (!(iterable instanceof Collection)) {
                return Optional.absent();
            }
            Iterator<? extends ByteSource> it = iterable.iterator();
            long jLongValue = 0;
            while (it.hasNext()) {
                Optional<Long> optionalSizeIfKnown = it.next().sizeIfKnown();
                if (!optionalSizeIfKnown.isPresent()) {
                    return Optional.absent();
                }
                jLongValue += optionalSizeIfKnown.get().longValue();
                if (jLongValue < 0) {
                    return Optional.of(Long.MAX_VALUE);
                }
            }
            return Optional.of(Long.valueOf(jLongValue));
        }

        public String toString() {
            String strValueOf = String.valueOf(this.sources);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 19, "ByteSource.concat(", strValueOf, ")");
        }
    }

    public static final class EmptyByteSource extends ByteArrayByteSource {
        public static final EmptyByteSource INSTANCE = new EmptyByteSource();

        public EmptyByteSource() {
            super(new byte[0]);
        }

        @Override // com.google.common.io.ByteSource
        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        @Override // com.google.common.io.ByteSource.ByteArrayByteSource, com.google.common.io.ByteSource
        public byte[] read() {
            return this.bytes;
        }

        @Override // com.google.common.io.ByteSource.ByteArrayByteSource
        public String toString() {
            return "ByteSource.empty()";
        }
    }

    public final class SlicedByteSource extends ByteSource {
        public final long length;
        public final long offset;

        public SlicedByteSource(long j, long j2) {
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            Preconditions.checkArgument(j2 >= 0, "length (%s) may not be negative", j2);
            this.offset = j;
            this.length = j2;
        }

        private InputStream sliceStream(InputStream inputStream) throws Throwable {
            long j = this.offset;
            if (j > 0) {
                try {
                    if (ByteStreams.skipUpTo(inputStream, j) < this.offset) {
                        inputStream.close();
                        return new ByteArrayInputStream(new byte[0]);
                    }
                } catch (Throwable th) {
                    Closer closerCreate = Closer.create();
                    closerCreate.register(inputStream);
                    try {
                        throw closerCreate.rethrow(th);
                    } catch (Throwable th2) {
                        closerCreate.close();
                        throw th2;
                    }
                }
            }
            return ByteStreams.limit(inputStream, this.length);
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            return this.length == 0 || super.isEmpty();
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() throws IOException {
            return sliceStream(ByteSource.this.openBufferedStream());
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return sliceStream(ByteSource.this.openStream());
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Optional<Long> optionalSizeIfKnown = ByteSource.this.sizeIfKnown();
            if (!optionalSizeIfKnown.isPresent()) {
                return Optional.absent();
            }
            long jLongValue = optionalSizeIfKnown.get().longValue();
            return Optional.of(Long.valueOf(Math.min(this.length, jLongValue - Math.min(this.offset, jLongValue))));
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long j, long j2) {
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            Preconditions.checkArgument(j2 >= 0, "length (%s) may not be negative", j2);
            long j3 = this.length - j;
            return j3 <= 0 ? ByteSource.empty() : ByteSource.this.slice(this.offset + j, Math.min(j2, j3));
        }

        public String toString() {
            String string = ByteSource.this.toString();
            long j = this.offset;
            long j2 = this.length;
            StringBuilder sb = new StringBuilder(Insets$$ExternalSyntheticOutline0.m(string, 50));
            sb.append(string);
            sb.append(".slice(");
            sb.append(j);
            sb.append(", ");
            sb.append(j2);
            sb.append(")");
            return sb.toString();
        }
    }

    public static ByteSource concat(Iterable<? extends ByteSource> iterable) {
        return new ConcatenatedByteSource(iterable);
    }

    private long countBySkipping(InputStream inputStream) throws IOException {
        long j = 0;
        while (true) {
            long jSkipUpTo = ByteStreams.skipUpTo(inputStream, 2147483647L);
            if (jSkipUpTo <= 0) {
                return j;
            }
            j += jSkipUpTo;
        }
    }

    public static ByteSource empty() {
        return EmptyByteSource.INSTANCE;
    }

    public static ByteSource wrap(byte[] bArr) {
        return new ByteArrayByteSource(bArr);
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(charset);
    }

    public boolean contentEquals(ByteSource byteSource) throws Throwable {
        int i;
        Preconditions.checkNotNull(byteSource);
        int i2 = ByteStreams.$r8$clinit;
        byte[] bArr = new byte[8192];
        byte[] bArr2 = new byte[8192];
        Closer closerCreate = Closer.create();
        try {
            InputStream inputStream = (InputStream) closerCreate.register(openStream());
            InputStream inputStream2 = (InputStream) closerCreate.register(byteSource.openStream());
            do {
                i = ByteStreams.read(inputStream, bArr, 0, 8192);
                if (i != ByteStreams.read(inputStream2, bArr2, 0, 8192) || !Arrays.equals(bArr, bArr2)) {
                    closerCreate.close();
                    return false;
                }
            } while (i == 8192);
            closerCreate.close();
            return true;
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
    public long copyTo(OutputStream outputStream) throws Throwable {
        Preconditions.checkNotNull(outputStream);
        Closer closerCreate = Closer.create();
        try {
            long jCopy = ByteStreams.copy((InputStream) closerCreate.register(openStream()), outputStream);
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

    public HashCode hash(HashFunction hashFunction) throws Throwable {
        Hasher hasherNewHasher = hashFunction.newHasher();
        copyTo(Funnels.asOutputStream(hasherNewHasher));
        return hasherNewHasher.hash();
    }

    public boolean isEmpty() throws Throwable {
        Optional<Long> optionalSizeIfKnown = sizeIfKnown();
        if (optionalSizeIfKnown.isPresent()) {
            return optionalSizeIfKnown.get().longValue() == 0;
        }
        Closer closerCreate = Closer.create();
        try {
            boolean z = ((InputStream) closerCreate.register(openStream())).read() == -1;
            closerCreate.close();
            return z;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public InputStream openBufferedStream() throws IOException {
        InputStream inputStreamOpenStream = openStream();
        return inputStreamOpenStream instanceof BufferedInputStream ? (BufferedInputStream) inputStreamOpenStream : new BufferedInputStream(inputStreamOpenStream);
    }

    public abstract InputStream openStream() throws IOException;

    public byte[] read() throws Throwable {
        Closer closerCreate = Closer.create();
        try {
            InputStream inputStream = (InputStream) closerCreate.register(openStream());
            Optional<Long> optionalSizeIfKnown = sizeIfKnown();
            byte[] byteArray = optionalSizeIfKnown.isPresent() ? ByteStreams.toByteArray(inputStream, optionalSizeIfKnown.get().longValue()) : ByteStreams.toByteArray(inputStream);
            closerCreate.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public long size() throws Throwable {
        Optional<Long> optionalSizeIfKnown = sizeIfKnown();
        if (optionalSizeIfKnown.isPresent()) {
            return optionalSizeIfKnown.get().longValue();
        }
        Closer closerCreate = Closer.create();
        try {
            long jCountBySkipping = countBySkipping((InputStream) closerCreate.register(openStream()));
            closerCreate.close();
            return jCountBySkipping;
        } catch (IOException unused) {
            closerCreate.close();
            Closer closerCreate2 = Closer.create();
            try {
                long jExhaust = ByteStreams.exhaust((InputStream) closerCreate2.register(openStream()));
                closerCreate2.close();
                return jExhaust;
            } catch (Throwable th) {
                try {
                    throw closerCreate2.rethrow(th);
                } catch (Throwable th2) {
                    closerCreate2.close();
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            closerCreate.close();
            throw th3;
        }
    }

    @Beta
    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    public ByteSource slice(long j, long j2) {
        return new SlicedByteSource(j, j2);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> it) {
        return concat(ImmutableList.copyOf(it));
    }

    public static ByteSource concat(ByteSource... byteSourceArr) {
        return concat(ImmutableList.copyOf(byteSourceArr));
    }

    @CanIgnoreReturnValue
    public long copyTo(ByteSink byteSink) throws Throwable {
        Preconditions.checkNotNull(byteSink);
        Closer closerCreate = Closer.create();
        try {
            long jCopy = ByteStreams.copy((InputStream) closerCreate.register(openStream()), (OutputStream) closerCreate.register(byteSink.openStream()));
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

    @CanIgnoreReturnValue
    @Beta
    public <T> T read(ByteProcessor<T> byteProcessor) throws Throwable {
        Preconditions.checkNotNull(byteProcessor);
        Closer closerCreate = Closer.create();
        try {
            T t = (T) ByteStreams.readBytes((InputStream) closerCreate.register(openStream()), byteProcessor);
            closerCreate.close();
            return t;
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
