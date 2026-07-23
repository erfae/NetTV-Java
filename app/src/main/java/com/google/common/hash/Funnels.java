package com.google.common.hash;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@ElementTypesAreNonnullByDefault
public final class Funnels {

    public enum ByteArrayFunnel implements Funnel<byte[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.byteArrayFunnel()";
        }

        @Override // com.google.common.hash.Funnel
        public void funnel(byte[] bArr, PrimitiveSink primitiveSink) {
            primitiveSink.putBytes(bArr);
        }
    }

    public enum IntegerFunnel implements Funnel<Integer> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.integerFunnel()";
        }

        @Override // com.google.common.hash.Funnel
        public void funnel(Integer num, PrimitiveSink primitiveSink) {
            primitiveSink.putInt(num.intValue());
        }
    }

    public enum LongFunnel implements Funnel<Long> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.longFunnel()";
        }

        @Override // com.google.common.hash.Funnel
        public void funnel(Long l, PrimitiveSink primitiveSink) {
            primitiveSink.putLong(l.longValue());
        }
    }

    public static class SequentialFunnel<E> implements Funnel<Iterable<? extends E>>, Serializable {
        private final Funnel<E> elementFunnel;

        public SequentialFunnel(Funnel<E> funnel) {
            this.elementFunnel = (Funnel) Preconditions.checkNotNull(funnel);
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof SequentialFunnel) {
                return this.elementFunnel.equals(((SequentialFunnel) obj).elementFunnel);
            }
            return false;
        }

        public int hashCode() {
            return SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
        }

        public String toString() {
            String strValueOf = String.valueOf(this.elementFunnel);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 26, "Funnels.sequentialFunnel(", strValueOf, ")");
        }

        @Override // com.google.common.hash.Funnel
        public void funnel(Iterable<? extends E> iterable, PrimitiveSink primitiveSink) {
            Iterator<? extends E> it = iterable.iterator();
            while (it.hasNext()) {
                this.elementFunnel.funnel(it.next(), primitiveSink);
            }
        }
    }

    public static class SinkAsStream extends OutputStream {
        public final PrimitiveSink sink;

        public SinkAsStream(PrimitiveSink primitiveSink) {
            this.sink = (PrimitiveSink) Preconditions.checkNotNull(primitiveSink);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.sink);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 24, "Funnels.asOutputStream(", strValueOf, ")");
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            this.sink.putByte((byte) i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            this.sink.putBytes(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            this.sink.putBytes(bArr, i, i2);
        }
    }

    public static class StringCharsetFunnel implements Funnel<CharSequence>, Serializable {
        private final Charset charset;

        public static class SerializedForm implements Serializable {
            private static final long serialVersionUID = 0;
            private final String charsetCanonicalName;

            private Object readResolve() {
                return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
            }
        }

        public StringCharsetFunnel(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof StringCharsetFunnel) {
                return this.charset.equals(((StringCharsetFunnel) obj).charset);
            }
            return false;
        }

        public int hashCode() {
            return StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
        }

        public String toString() {
            String strName = this.charset.name();
            return MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(strName, 22), "Funnels.stringFunnel(", strName, ")");
        }

        @Override // com.google.common.hash.Funnel
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putString(charSequence, this.charset);
        }
    }

    public enum UnencodedCharsFunnel implements Funnel<CharSequence> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.unencodedCharsFunnel()";
        }

        @Override // com.google.common.hash.Funnel
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putUnencodedChars(charSequence);
        }
    }

    private Funnels() {
    }

    public static OutputStream asOutputStream(PrimitiveSink primitiveSink) {
        return new SinkAsStream(primitiveSink);
    }

    public static Funnel<byte[]> byteArrayFunnel() {
        return ByteArrayFunnel.INSTANCE;
    }

    public static Funnel<Integer> integerFunnel() {
        return IntegerFunnel.INSTANCE;
    }

    public static Funnel<Long> longFunnel() {
        return LongFunnel.INSTANCE;
    }

    public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> funnel) {
        return new SequentialFunnel(funnel);
    }

    public static Funnel<CharSequence> stringFunnel(Charset charset) {
        return new StringCharsetFunnel(charset);
    }

    public static Funnel<CharSequence> unencodedCharsFunnel() {
        return UnencodedCharsFunnel.INSTANCE;
    }
}
