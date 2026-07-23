package com.google.common.io;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
public abstract class BaseEncoding {
    private static final BaseEncoding BASE64 = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", '=');
    private static final BaseEncoding BASE64_URL = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", '=');
    private static final BaseEncoding BASE32 = new StandardBaseEncoding(new Alphabet("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray()), '=');
    private static final BaseEncoding BASE32_HEX = new StandardBaseEncoding(new Alphabet("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray()), '=');
    private static final BaseEncoding BASE16 = new Base16Encoding();

    public static final class Alphabet {
        public final int bitsPerChar;
        public final int bytesPerChunk;
        private final char[] chars;
        public final int charsPerChunk;
        private final byte[] decodabet;
        public final int mask;
        private final String name;
        private final boolean[] validPadding;

        public Alphabet(String str, char[] cArr) {
            this.name = (String) Preconditions.checkNotNull(str);
            this.chars = (char[]) Preconditions.checkNotNull(cArr);
            try {
                int iLog2 = IntMath.log2(cArr.length, RoundingMode.UNNECESSARY);
                this.bitsPerChar = iLog2;
                int iMin = Math.min(8, Integer.lowestOneBit(iLog2));
                try {
                    this.charsPerChunk = 8 / iMin;
                    this.bytesPerChunk = iLog2 / iMin;
                    this.mask = cArr.length - 1;
                    byte[] bArr = new byte[128];
                    Arrays.fill(bArr, (byte) -1);
                    for (int i = 0; i < cArr.length; i++) {
                        char c = cArr[i];
                        Preconditions.checkArgument(c < 128, "Non-ASCII character: %s", c);
                        Preconditions.checkArgument(bArr[c] == -1, "Duplicate character: %s", c);
                        bArr[c] = (byte) i;
                    }
                    this.decodabet = bArr;
                    boolean[] zArr = new boolean[this.charsPerChunk];
                    for (int i2 = 0; i2 < this.bytesPerChunk; i2++) {
                        zArr[IntMath.divide(i2 * 8, this.bitsPerChar, RoundingMode.CEILING)] = true;
                    }
                    this.validPadding = zArr;
                } catch (ArithmeticException e) {
                    String str2 = new String(cArr);
                    throw new IllegalArgumentException(str2.length() != 0 ? "Illegal alphabet ".concat(str2) : new String("Illegal alphabet "), e);
                }
            } catch (ArithmeticException e2) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(35, "Illegal alphabet length ", cArr.length), e2);
            }
        }

        private boolean hasLowerCase() {
            for (char c : this.chars) {
                if (Ascii.isLowerCase(c)) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasUpperCase() {
            for (char c : this.chars) {
                if (Ascii.isUpperCase(c)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean canDecode(char c) {
            return c <= 127 && this.decodabet[c] != -1;
        }

        public final int decode(char c) throws DecodingException {
            if (c > 127) {
                String strValueOf = String.valueOf(Integer.toHexString(c));
                throw new DecodingException(strValueOf.length() != 0 ? "Unrecognized character: 0x".concat(strValueOf) : new String("Unrecognized character: 0x"));
            }
            byte b = this.decodabet[c];
            if (b != -1) {
                return b;
            }
            if (c <= ' ' || c == 127) {
                String strValueOf2 = String.valueOf(Integer.toHexString(c));
                throw new DecodingException(strValueOf2.length() != 0 ? "Unrecognized character: 0x".concat(strValueOf2) : new String("Unrecognized character: 0x"));
            }
            StringBuilder sb = new StringBuilder(25);
            sb.append("Unrecognized character: ");
            sb.append(c);
            throw new DecodingException(sb.toString());
        }

        public final char encode(int i) {
            return this.chars[i];
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof Alphabet) {
                return Arrays.equals(this.chars, ((Alphabet) obj).chars);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars);
        }

        public final boolean isValidPaddingStartPosition(int i) {
            return this.validPadding[i % this.charsPerChunk];
        }

        public final Alphabet lowerCase() {
            if (!hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(!hasLowerCase(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i >= cArr2.length) {
                    return new Alphabet(String.valueOf(this.name).concat(".lowerCase()"), cArr);
                }
                cArr[i] = Ascii.toLowerCase(cArr2[i]);
                i++;
            }
        }

        public boolean matches(char c) {
            byte[] bArr = this.decodabet;
            return c < bArr.length && bArr[c] != -1;
        }

        public String toString() {
            return this.name;
        }

        public final Alphabet upperCase() {
            if (!hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(!hasUpperCase(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i >= cArr2.length) {
                    return new Alphabet(String.valueOf(this.name).concat(".upperCase()"), cArr);
                }
                cArr[i] = Ascii.toUpperCase(cArr2[i]);
                i++;
            }
        }
    }

    public static final class Base16Encoding extends StandardBaseEncoding {
        public final char[] encoding;

        public Base16Encoding() {
            this(new Alphabet("base16()", "0123456789ABCDEF".toCharArray()));
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public final int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            if (charSequence.length() % 2 == 1) {
                throw new DecodingException(Insets$$ExternalSyntheticOutline0.m(32, "Invalid input length ", charSequence.length()));
            }
            int i = 0;
            int i2 = 0;
            while (i < charSequence.length()) {
                bArr[i2] = (byte) ((this.alphabet.decode(charSequence.charAt(i)) << 4) | this.alphabet.decode(charSequence.charAt(i + 1)));
                i += 2;
                i2++;
            }
            return i2;
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public final void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = bArr[i + i3] & 255;
                appendable.append(this.encoding[i4]);
                appendable.append(this.encoding[i4 | 256]);
            }
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding
        public final BaseEncoding newInstance(Alphabet alphabet, @CheckForNull Character ch) {
            return new Base16Encoding(alphabet);
        }

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            this.encoding = new char[512];
            Preconditions.checkArgument(alphabet.chars.length == 16);
            for (int i = 0; i < 256; i++) {
                this.encoding[i] = alphabet.encode(i >>> 4);
                this.encoding[i | 256] = alphabet.encode(i & 15);
            }
        }
    }

    public static final class Base64Encoding extends StandardBaseEncoding {
        public Base64Encoding(String str, String str2, @CheckForNull Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public final int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequenceTrimTrailingPadding.length())) {
                throw new DecodingException(Insets$$ExternalSyntheticOutline0.m(32, "Invalid input length ", charSequenceTrimTrailingPadding.length()));
            }
            int i = 0;
            int i2 = 0;
            while (i < charSequenceTrimTrailingPadding.length()) {
                int i3 = i + 1;
                int i4 = i3 + 1;
                int iDecode = (this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i)) << 18) | (this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i3)) << 12);
                int i5 = i2 + 1;
                bArr[i2] = (byte) (iDecode >>> 16);
                if (i4 < charSequenceTrimTrailingPadding.length()) {
                    int i6 = i4 + 1;
                    int iDecode2 = iDecode | (this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i4)) << 6);
                    i2 = i5 + 1;
                    bArr[i5] = (byte) ((iDecode2 >>> 8) & 255);
                    if (i6 < charSequenceTrimTrailingPadding.length()) {
                        i4 = i6 + 1;
                        i5 = i2 + 1;
                        bArr[i2] = (byte) ((iDecode2 | this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i6))) & 255);
                    } else {
                        i = i6;
                    }
                }
                i2 = i5;
                i = i4;
            }
            return i2;
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public final void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            int i3 = i + i2;
            Preconditions.checkPositionIndexes(i, i3, bArr.length);
            while (i2 >= 3) {
                int i4 = i + 1;
                int i5 = i4 + 1;
                int i6 = ((bArr[i] & 255) << 16) | ((bArr[i4] & 255) << 8) | (bArr[i5] & 255);
                appendable.append(this.alphabet.encode(i6 >>> 18));
                appendable.append(this.alphabet.encode((i6 >>> 12) & 63));
                appendable.append(this.alphabet.encode((i6 >>> 6) & 63));
                appendable.append(this.alphabet.encode(i6 & 63));
                i2 -= 3;
                i = i5 + 1;
            }
            if (i < i3) {
                encodeChunkTo(appendable, bArr, i, i3 - i);
            }
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding
        public final BaseEncoding newInstance(Alphabet alphabet, @CheckForNull Character ch) {
            return new Base64Encoding(alphabet, ch);
        }

        private Base64Encoding(Alphabet alphabet, @CheckForNull Character ch) {
            super(alphabet, ch);
            Preconditions.checkArgument(alphabet.chars.length == 64);
        }
    }

    public static final class DecodingException extends IOException {
        public DecodingException(String str) {
            super(str);
        }
    }

    public static final class SeparatedBaseEncoding extends BaseEncoding {
        private final int afterEveryChars;
        private final BaseEncoding delegate;
        private final String separator;

        public SeparatedBaseEncoding(BaseEncoding baseEncoding, String str, int i) {
            this.delegate = (BaseEncoding) Preconditions.checkNotNull(baseEncoding);
            this.separator = (String) Preconditions.checkNotNull(str);
            this.afterEveryChars = i;
            Preconditions.checkArgument(i > 0, "Cannot add a separator after every %s chars", i);
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < charSequence.length(); i++) {
                char cCharAt = charSequence.charAt(i);
                if (this.separator.indexOf(cCharAt) < 0) {
                    sb.append(cCharAt);
                }
            }
            return this.delegate.canDecode(sb);
        }

        @Override // com.google.common.io.BaseEncoding
        public final int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            StringBuilder sb = new StringBuilder(charSequence.length());
            for (int i = 0; i < charSequence.length(); i++) {
                char cCharAt = charSequence.charAt(i);
                if (this.separator.indexOf(cCharAt) < 0) {
                    sb.append(cCharAt);
                }
            }
            return this.delegate.decodeTo(bArr, sb);
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            BaseEncoding baseEncoding = this.delegate;
            final String str = this.separator;
            Preconditions.checkNotNull(reader);
            Preconditions.checkNotNull(str);
            return baseEncoding.decodingStream(new Reader() { // from class: com.google.common.io.BaseEncoding.3
                @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    reader.close();
                }

                @Override // java.io.Reader
                public int read() throws IOException {
                    int i;
                    do {
                        i = reader.read();
                        if (i == -1) {
                            break;
                        }
                    } while (str.indexOf((char) i) >= 0);
                    return i;
                }

                @Override // java.io.Reader
                public int read(char[] cArr, int i, int i2) throws IOException {
                    throw new UnsupportedOperationException();
                }
            });
        }

        @Override // com.google.common.io.BaseEncoding
        public final void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            BaseEncoding baseEncoding = this.delegate;
            String str = this.separator;
            int i3 = this.afterEveryChars;
            Preconditions.checkNotNull(appendable);
            Preconditions.checkNotNull(str);
            Preconditions.checkArgument(i3 > 0);
            baseEncoding.encodeTo(new AnonymousClass4(i3, appendable, str), bArr, i, i2);
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            BaseEncoding baseEncoding = this.delegate;
            String str = this.separator;
            int i = this.afterEveryChars;
            Preconditions.checkNotNull(writer);
            Preconditions.checkNotNull(str);
            Preconditions.checkArgument(i > 0);
            final AnonymousClass4 anonymousClass4 = new AnonymousClass4(i, writer, str);
            return baseEncoding.encodingStream(new Writer() { // from class: com.google.common.io.BaseEncoding.5
                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    writer.close();
                }

                @Override // java.io.Writer, java.io.Flushable
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override // java.io.Writer
                public void write(int i2) throws IOException {
                    anonymousClass4.append((char) i2);
                }

                @Override // java.io.Writer
                public void write(char[] cArr, int i2, int i3) throws IOException {
                    throw new UnsupportedOperationException();
                }
            });
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public final int maxDecodedSize(int i) {
            return this.delegate.maxDecodedSize(i);
        }

        @Override // com.google.common.io.BaseEncoding
        public final int maxEncodedSize(int i) {
            int iMaxEncodedSize = this.delegate.maxEncodedSize(i);
            return (IntMath.divide(Math.max(0, iMaxEncodedSize - 1), this.afterEveryChars, RoundingMode.FLOOR) * this.separator.length()) + iMaxEncodedSize;
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.delegate);
            String str = this.separator;
            return Insets$$ExternalSyntheticOutline0.m(MainActivity$$ExternalSyntheticOutline0.m209m(Insets$$ExternalSyntheticOutline0.m(str, strValueOf.length() + 31), strValueOf, ".withSeparator(\"", str, "\", "), this.afterEveryChars, ")");
        }

        @Override // com.google.common.io.BaseEncoding
        public final CharSequence trimTrailingPadding(CharSequence charSequence) {
            return this.delegate.trimTrailingPadding(charSequence);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c) {
            return this.delegate.withPadChar(c).withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i) {
            throw new UnsupportedOperationException("Already have a separator");
        }
    }

    public static class StandardBaseEncoding extends BaseEncoding {
        public final Alphabet alphabet;

        @CheckForNull
        @LazyInit
        private transient BaseEncoding lowerCase;

        @CheckForNull
        public final Character paddingChar;

        @CheckForNull
        @LazyInit
        private transient BaseEncoding upperCase;

        public StandardBaseEncoding(Alphabet alphabet, @CheckForNull Character ch) {
            this.alphabet = (Alphabet) Preconditions.checkNotNull(alphabet);
            Preconditions.checkArgument(ch == null || !alphabet.matches(ch.charValue()), "Padding character %s was already in alphabet", ch);
            this.paddingChar = ch;
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequenceTrimTrailingPadding.length())) {
                return false;
            }
            for (int i = 0; i < charSequenceTrimTrailingPadding.length(); i++) {
                if (!this.alphabet.canDecode(charSequenceTrimTrailingPadding.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.BaseEncoding
        public int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Alphabet alphabet;
            Preconditions.checkNotNull(bArr);
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequenceTrimTrailingPadding.length())) {
                throw new DecodingException(Insets$$ExternalSyntheticOutline0.m(32, "Invalid input length ", charSequenceTrimTrailingPadding.length()));
            }
            int i = 0;
            int i2 = 0;
            while (i < charSequenceTrimTrailingPadding.length()) {
                long jDecode = 0;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    alphabet = this.alphabet;
                    if (i3 >= alphabet.charsPerChunk) {
                        break;
                    }
                    jDecode <<= alphabet.bitsPerChar;
                    if (i + i3 < charSequenceTrimTrailingPadding.length()) {
                        jDecode |= (long) this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i4 + i));
                        i4++;
                    }
                    i3++;
                }
                int i5 = alphabet.bytesPerChunk;
                int i6 = (i5 * 8) - (i4 * alphabet.bitsPerChar);
                int i7 = (i5 - 1) * 8;
                while (i7 >= i6) {
                    bArr[i2] = (byte) ((jDecode >>> i7) & 255);
                    i7 -= 8;
                    i2++;
                }
                i += this.alphabet.charsPerChunk;
            }
            return i2;
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream() { // from class: com.google.common.io.BaseEncoding.StandardBaseEncoding.2
                public int bitBuffer = 0;
                public int bitBufferLength = 0;
                public int readChars = 0;
                public boolean hitPadding = false;

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    reader.close();
                }

                @Override // java.io.InputStream
                public int read() throws IOException {
                    int i;
                    while (true) {
                        int i2 = reader.read();
                        if (i2 == -1) {
                            if (this.hitPadding || StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars)) {
                                return -1;
                            }
                            throw new DecodingException(Insets$$ExternalSyntheticOutline0.m(32, "Invalid input length ", this.readChars));
                        }
                        this.readChars++;
                        char c = (char) i2;
                        Character ch = StandardBaseEncoding.this.paddingChar;
                        if (ch == null || ch.charValue() != c) {
                            if (this.hitPadding) {
                                int i3 = this.readChars;
                                StringBuilder sb = new StringBuilder(61);
                                sb.append("Expected padding character but found '");
                                sb.append(c);
                                sb.append("' at index ");
                                sb.append(i3);
                                throw new DecodingException(sb.toString());
                            }
                            int i4 = this.bitBuffer;
                            Alphabet alphabet = StandardBaseEncoding.this.alphabet;
                            int i5 = i4 << alphabet.bitsPerChar;
                            this.bitBuffer = i5;
                            int iDecode = alphabet.decode(c) | i5;
                            this.bitBuffer = iDecode;
                            int i6 = this.bitBufferLength + StandardBaseEncoding.this.alphabet.bitsPerChar;
                            this.bitBufferLength = i6;
                            if (i6 >= 8) {
                                int i7 = i6 - 8;
                                this.bitBufferLength = i7;
                                return (iDecode >> i7) & 255;
                            }
                        } else {
                            if (!this.hitPadding && ((i = this.readChars) == 1 || !StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(i - 1))) {
                                throw new DecodingException(Insets$$ExternalSyntheticOutline0.m(41, "Padding cannot start at index ", this.readChars));
                            }
                            this.hitPadding = true;
                        }
                    }
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr, int i, int i2) throws IOException {
                    int i3 = i2 + i;
                    Preconditions.checkPositionIndexes(i, i3, bArr.length);
                    int i4 = i;
                    while (i4 < i3) {
                        int i5 = read();
                        if (i5 == -1) {
                            int i6 = i4 - i;
                            if (i6 == 0) {
                                return -1;
                            }
                            return i6;
                        }
                        bArr[i4] = (byte) i5;
                        i4++;
                    }
                    return i4 - i;
                }
            };
        }

        public final void encodeChunkTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            Preconditions.checkArgument(i2 <= this.alphabet.bytesPerChunk);
            long j = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                j = (j | ((long) (bArr[i + i4] & 255))) << 8;
            }
            int i5 = ((i2 + 1) * 8) - this.alphabet.bitsPerChar;
            while (i3 < i2 * 8) {
                Alphabet alphabet = this.alphabet;
                appendable.append(alphabet.encode(((int) (j >>> (i5 - i3))) & alphabet.mask));
                i3 += this.alphabet.bitsPerChar;
            }
            if (this.paddingChar != null) {
                while (i3 < this.alphabet.bytesPerChunk * 8) {
                    appendable.append(this.paddingChar.charValue());
                    i3 += this.alphabet.bitsPerChar;
                }
            }
        }

        @Override // com.google.common.io.BaseEncoding
        public void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            while (i3 < i2) {
                encodeChunkTo(appendable, bArr, i + i3, Math.min(this.alphabet.bytesPerChunk, i2 - i3));
                i3 += this.alphabet.bytesPerChunk;
            }
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream() { // from class: com.google.common.io.BaseEncoding.StandardBaseEncoding.1
                public int bitBuffer = 0;
                public int bitBufferLength = 0;
                public int writtenChars = 0;

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    int i = this.bitBufferLength;
                    if (i > 0) {
                        int i2 = this.bitBuffer;
                        Alphabet alphabet = StandardBaseEncoding.this.alphabet;
                        writer.write(alphabet.encode((i2 << (alphabet.bitsPerChar - i)) & alphabet.mask));
                        this.writtenChars++;
                        if (StandardBaseEncoding.this.paddingChar != null) {
                            while (true) {
                                int i3 = this.writtenChars;
                                StandardBaseEncoding standardBaseEncoding = StandardBaseEncoding.this;
                                if (i3 % standardBaseEncoding.alphabet.charsPerChunk == 0) {
                                    break;
                                }
                                writer.write(standardBaseEncoding.paddingChar.charValue());
                                this.writtenChars++;
                            }
                        }
                    }
                    writer.close();
                }

                @Override // java.io.OutputStream, java.io.Flushable
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override // java.io.OutputStream
                public void write(int i) throws IOException {
                    this.bitBuffer = (i & 255) | (this.bitBuffer << 8);
                    this.bitBufferLength += 8;
                    while (true) {
                        int i2 = this.bitBufferLength;
                        Alphabet alphabet = StandardBaseEncoding.this.alphabet;
                        int i3 = alphabet.bitsPerChar;
                        if (i2 < i3) {
                            return;
                        }
                        writer.write(alphabet.encode((this.bitBuffer >> (i2 - i3)) & alphabet.mask));
                        this.writtenChars++;
                        this.bitBufferLength -= StandardBaseEncoding.this.alphabet.bitsPerChar;
                    }
                }
            };
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof StandardBaseEncoding)) {
                return false;
            }
            StandardBaseEncoding standardBaseEncoding = (StandardBaseEncoding) obj;
            return this.alphabet.equals(standardBaseEncoding.alphabet) && Objects.equal(this.paddingChar, standardBaseEncoding.paddingChar);
        }

        public int hashCode() {
            return this.alphabet.hashCode() ^ Objects.hashCode(this.paddingChar);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            BaseEncoding baseEncodingNewInstance = this.lowerCase;
            if (baseEncodingNewInstance == null) {
                Alphabet alphabetLowerCase = this.alphabet.lowerCase();
                baseEncodingNewInstance = alphabetLowerCase == this.alphabet ? this : newInstance(alphabetLowerCase, this.paddingChar);
                this.lowerCase = baseEncodingNewInstance;
            }
            return baseEncodingNewInstance;
        }

        @Override // com.google.common.io.BaseEncoding
        public final int maxDecodedSize(int i) {
            return (int) (((((long) this.alphabet.bitsPerChar) * ((long) i)) + 7) / 8);
        }

        @Override // com.google.common.io.BaseEncoding
        public final int maxEncodedSize(int i) {
            Alphabet alphabet = this.alphabet;
            return IntMath.divide(i, alphabet.bytesPerChunk, RoundingMode.CEILING) * alphabet.charsPerChunk;
        }

        public BaseEncoding newInstance(Alphabet alphabet, @CheckForNull Character ch) {
            return new StandardBaseEncoding(alphabet, ch);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.paddingChar == null ? this : newInstance(this.alphabet, null);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("BaseEncoding.");
            sb.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    sb.append(".omitPadding()");
                } else {
                    sb.append(".withPadChar('");
                    sb.append(this.paddingChar);
                    sb.append("')");
                }
            }
            return sb.toString();
        }

        @Override // com.google.common.io.BaseEncoding
        public final CharSequence trimTrailingPadding(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            Character ch = this.paddingChar;
            if (ch == null) {
                return charSequence;
            }
            char cCharValue = ch.charValue();
            int length = charSequence.length() - 1;
            while (length >= 0 && charSequence.charAt(length) == cCharValue) {
                length--;
            }
            return charSequence.subSequence(0, length + 1);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            BaseEncoding baseEncodingNewInstance = this.upperCase;
            if (baseEncodingNewInstance == null) {
                Alphabet alphabetUpperCase = this.alphabet.upperCase();
                baseEncodingNewInstance = alphabetUpperCase == this.alphabet ? this : newInstance(alphabetUpperCase, this.paddingChar);
                this.upperCase = baseEncodingNewInstance;
            }
            return baseEncodingNewInstance;
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c) {
            Character ch;
            return (8 % this.alphabet.bitsPerChar == 0 || ((ch = this.paddingChar) != null && ch.charValue() == c)) ? this : newInstance(this.alphabet, Character.valueOf(c));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i) {
            for (int i2 = 0; i2 < str.length(); i2++) {
                Preconditions.checkArgument(!this.alphabet.matches(str.charAt(i2)), "Separator (%s) cannot contain alphabet characters", str);
            }
            Character ch = this.paddingChar;
            if (ch != null) {
                Preconditions.checkArgument(str.indexOf(ch.charValue()) < 0, "Separator (%s) cannot contain padding character", str);
            }
            return new SeparatedBaseEncoding(this, str, i);
        }
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    private static byte[] extract(byte[] bArr, int i) {
        if (i == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public abstract boolean canDecode(CharSequence charSequence);

    public final byte[] decode(CharSequence charSequence) {
        try {
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            byte[] bArr = new byte[maxDecodedSize(charSequenceTrimTrailingPadding.length())];
            return extract(bArr, decodeTo(bArr, charSequenceTrimTrailingPadding));
        } catch (DecodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException;

    @GwtIncompatible
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource() { // from class: com.google.common.io.BaseEncoding.2
            @Override // com.google.common.io.ByteSource
            public InputStream openStream() throws IOException {
                return BaseEncoding.this.decodingStream(charSource.openStream());
            }
        };
    }

    @GwtIncompatible
    public abstract InputStream decodingStream(Reader reader);

    public String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public abstract void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException;

    @GwtIncompatible
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink() { // from class: com.google.common.io.BaseEncoding.1
            @Override // com.google.common.io.ByteSink
            public OutputStream openStream() throws IOException {
                return BaseEncoding.this.encodingStream(charSink.openStream());
            }
        };
    }

    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer writer);

    public abstract BaseEncoding lowerCase();

    public abstract int maxDecodedSize(int i);

    public abstract int maxEncodedSize(int i);

    public abstract BaseEncoding omitPadding();

    public CharSequence trimTrailingPadding(CharSequence charSequence) {
        return (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char c);

    public abstract BaseEncoding withSeparator(String str, int i);

    public final String encode(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        StringBuilder sb = new StringBuilder(maxEncodedSize(i2));
        try {
            encodeTo(sb, bArr, i, i2);
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /* JADX INFO: renamed from: com.google.common.io.BaseEncoding$4, reason: invalid class name */
    class AnonymousClass4 implements Appendable {
        public int charsUntilSeparator;
        public final /* synthetic */ int val$afterEveryChars;
        public final /* synthetic */ Appendable val$delegate;
        public final /* synthetic */ String val$separator;

        public AnonymousClass4(int i, Appendable appendable, String str) {
            this.val$afterEveryChars = i;
            this.val$delegate = appendable;
            this.val$separator = str;
            this.charsUntilSeparator = i;
        }

        @Override // java.lang.Appendable
        public Appendable append(char c) throws IOException {
            if (this.charsUntilSeparator == 0) {
                this.val$delegate.append(this.val$separator);
                this.charsUntilSeparator = this.val$afterEveryChars;
            }
            this.val$delegate.append(c);
            this.charsUntilSeparator--;
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(@CheckForNull CharSequence charSequence, int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.lang.Appendable
        public Appendable append(@CheckForNull CharSequence charSequence) {
            throw new UnsupportedOperationException();
        }
    }
}
