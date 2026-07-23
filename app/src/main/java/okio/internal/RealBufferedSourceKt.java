package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.EOFException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.RealBufferedSource;
import okio.Sink;
import okio.Timeout;
import okio.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: RealBufferedSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a%\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\r\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a-\u0010\u0011\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010\u001a\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001bH\u0080\b\u001a\r\u0010\u001c\u001a\u00020\b*\u00020\u0002H\u0080\b\u001a\r\u0010\u001d\u001a\u00020\u0018*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001e\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\u0015\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\u0080\b\u001a\u001d\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010!\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010\"\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010#\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010$\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010%\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010&\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010(\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010)\u001a\u00020**\u00020\u0002H\u0080\b\u001a\u0015\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010+\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\u000f\u0010,\u001a\u0004\u0018\u00010**\u00020\u0002H\u0080\b\u001a\u0015\u0010-\u001a\u00020**\u00020\u00022\u0006\u0010.\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010/\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0014*\u00020\u00022\u0006\u00102\u001a\u000203H\u0080\b\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0002H\u0080\b\u001a\r\u00107\u001a\u00020**\u00020\u0002H\u0080\b¨\u00068"}, d2 = {"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", TypedValues.CycleType.S_WAVE_OFFSET, "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k = 2, mv = {1, 4, 1})
public final class RealBufferedSourceKt {
    public static final void commonClose(@NotNull RealBufferedSource commonClose) throws IOException {
        Intrinsics.checkNotNullParameter(commonClose, "$this$commonClose");
        if (commonClose.closed) {
            return;
        }
        commonClose.closed = true;
        commonClose.source.close();
        commonClose.bufferField.clear();
    }

    public static final boolean commonExhausted(@NotNull RealBufferedSource commonExhausted) {
        Intrinsics.checkNotNullParameter(commonExhausted, "$this$commonExhausted");
        if (!commonExhausted.closed) {
            return commonExhausted.bufferField.exhausted() && commonExhausted.source.read(commonExhausted.bufferField, (long) 8192) == -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final long commonIndexOf(@NotNull RealBufferedSource commonIndexOf, byte b, long j, long j2) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        if (!(!commonIndexOf.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (!(0 <= j && j2 >= j)) {
            throw new IllegalArgumentException(("fromIndex=" + j + " toIndex=" + j2).toString());
        }
        while (j < j2) {
            long jIndexOf = commonIndexOf.bufferField.indexOf(b, j, j2);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long size = commonIndexOf.bufferField.size();
            if (size >= j2 || commonIndexOf.source.read(commonIndexOf.bufferField, 8192) == -1) {
                break;
            }
            j = Math.max(j, size);
        }
        return -1L;
    }

    public static final long commonIndexOfElement(@NotNull RealBufferedSource commonIndexOfElement, @NotNull ByteString targetBytes, long j) {
        Intrinsics.checkNotNullParameter(commonIndexOfElement, "$this$commonIndexOfElement");
        Intrinsics.checkNotNullParameter(targetBytes, "targetBytes");
        if (!(!commonIndexOfElement.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (true) {
            long jIndexOfElement = commonIndexOfElement.bufferField.indexOfElement(targetBytes, j);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            long size = commonIndexOfElement.bufferField.size();
            if (commonIndexOfElement.source.read(commonIndexOfElement.bufferField, 8192) == -1) {
                return -1L;
            }
            j = Math.max(j, size);
        }
    }

    @NotNull
    public static final BufferedSource commonPeek(@NotNull RealBufferedSource commonPeek) {
        Intrinsics.checkNotNullParameter(commonPeek, "$this$commonPeek");
        return Okio.buffer(new PeekSource(commonPeek));
    }

    public static final boolean commonRangeEquals(@NotNull RealBufferedSource commonRangeEquals, long j, @NotNull ByteString bytes, int i, int i2) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (!(!commonRangeEquals.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (j < 0 || i < 0 || i2 < 0 || bytes.size() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            long j2 = ((long) i3) + j;
            if (!commonRangeEquals.request(1 + j2) || commonRangeEquals.bufferField.getByte(j2) != bytes.getByte(i + i3)) {
                return false;
            }
        }
        return true;
    }

    public static final long commonRead(@NotNull RealBufferedSource commonRead, @NotNull Buffer sink, long j) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        if (!(!commonRead.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (commonRead.bufferField.size() == 0 && commonRead.source.read(commonRead.bufferField, 8192) == -1) {
            return -1L;
        }
        return commonRead.bufferField.read(sink, Math.min(j, commonRead.bufferField.size()));
    }

    public static final long commonReadAll(@NotNull RealBufferedSource commonReadAll, @NotNull Sink sink) throws IOException {
        Intrinsics.checkNotNullParameter(commonReadAll, "$this$commonReadAll");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = 0;
        while (commonReadAll.source.read(commonReadAll.bufferField, 8192) != -1) {
            long jCompleteSegmentByteCount = commonReadAll.bufferField.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j += jCompleteSegmentByteCount;
                sink.write(commonReadAll.bufferField, jCompleteSegmentByteCount);
            }
        }
        if (commonReadAll.bufferField.size() <= 0) {
            return j;
        }
        long size = j + commonReadAll.bufferField.size();
        Buffer buffer = commonReadAll.bufferField;
        sink.write(buffer, buffer.size());
        return size;
    }

    public static final byte commonReadByte(@NotNull RealBufferedSource commonReadByte) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadByte, "$this$commonReadByte");
        commonReadByte.require(1L);
        return commonReadByte.bufferField.readByte();
    }

    @NotNull
    public static final byte[] commonReadByteArray(@NotNull RealBufferedSource commonReadByteArray, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadByteArray, "$this$commonReadByteArray");
        commonReadByteArray.require(j);
        return commonReadByteArray.bufferField.readByteArray(j);
    }

    @NotNull
    public static final ByteString commonReadByteString(@NotNull RealBufferedSource commonReadByteString, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadByteString, "$this$commonReadByteString");
        commonReadByteString.require(j);
        return commonReadByteString.bufferField.readByteString(j);
    }

    public static final long commonReadDecimalLong(@NotNull RealBufferedSource commonReadDecimalLong) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadDecimalLong, "$this$commonReadDecimalLong");
        commonReadDecimalLong.require(1L);
        long j = 0;
        while (true) {
            long j2 = j + 1;
            if (!commonReadDecimalLong.request(j2)) {
                break;
            }
            byte b = commonReadDecimalLong.bufferField.getByte(j);
            if ((b < ((byte) 48) || b > ((byte) 57)) && !(j == 0 && b == ((byte) 45))) {
                if (j != 0) {
                    break;
                }
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Expected leading [0-9] or '-' character but was 0x");
                String string = Integer.toString(b, CharsKt.checkRadix(CharsKt.checkRadix(16)));
                Intrinsics.checkNotNullExpressionValue(string, "java.lang.Integer.toStri…(this, checkRadix(radix))");
                sbM.append(string);
                throw new NumberFormatException(sbM.toString());
            }
            j = j2;
        }
        return commonReadDecimalLong.bufferField.readDecimalLong();
    }

    public static final void commonReadFully(@NotNull RealBufferedSource commonReadFully, @NotNull byte[] sink) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            commonReadFully.require(sink.length);
            commonReadFully.bufferField.readFully(sink);
        } catch (EOFException e) {
            int i = 0;
            while (commonReadFully.bufferField.size() > 0) {
                Buffer buffer = commonReadFully.bufferField;
                int i2 = buffer.read(sink, i, (int) buffer.size());
                if (i2 == -1) {
                    throw new AssertionError();
                }
                i += i2;
            }
            throw e;
        }
    }

    public static final long commonReadHexadecimalUnsignedLong(@NotNull RealBufferedSource commonReadHexadecimalUnsignedLong) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadHexadecimalUnsignedLong, "$this$commonReadHexadecimalUnsignedLong");
        commonReadHexadecimalUnsignedLong.require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!commonReadHexadecimalUnsignedLong.request(i2)) {
                break;
            }
            byte b = commonReadHexadecimalUnsignedLong.bufferField.getByte(i);
            if ((b < ((byte) 48) || b > ((byte) 57)) && ((b < ((byte) 97) || b > ((byte) 102)) && (b < ((byte) 65) || b > ((byte) 70)))) {
                if (i != 0) {
                    break;
                }
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Expected leading [0-9a-fA-F] character but was 0x");
                String string = Integer.toString(b, CharsKt.checkRadix(CharsKt.checkRadix(16)));
                Intrinsics.checkNotNullExpressionValue(string, "java.lang.Integer.toStri…(this, checkRadix(radix))");
                sbM.append(string);
                throw new NumberFormatException(sbM.toString());
            }
            i = i2;
        }
        return commonReadHexadecimalUnsignedLong.bufferField.readHexadecimalUnsignedLong();
    }

    public static final int commonReadInt(@NotNull RealBufferedSource commonReadInt) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadInt, "$this$commonReadInt");
        commonReadInt.require(4L);
        return commonReadInt.bufferField.readInt();
    }

    public static final int commonReadIntLe(@NotNull RealBufferedSource commonReadIntLe) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadIntLe, "$this$commonReadIntLe");
        commonReadIntLe.require(4L);
        return commonReadIntLe.bufferField.readIntLe();
    }

    public static final long commonReadLong(@NotNull RealBufferedSource commonReadLong) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadLong, "$this$commonReadLong");
        commonReadLong.require(8L);
        return commonReadLong.bufferField.readLong();
    }

    public static final long commonReadLongLe(@NotNull RealBufferedSource commonReadLongLe) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadLongLe, "$this$commonReadLongLe");
        commonReadLongLe.require(8L);
        return commonReadLongLe.bufferField.readLongLe();
    }

    public static final short commonReadShort(@NotNull RealBufferedSource commonReadShort) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadShort, "$this$commonReadShort");
        commonReadShort.require(2L);
        return commonReadShort.bufferField.readShort();
    }

    public static final short commonReadShortLe(@NotNull RealBufferedSource commonReadShortLe) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadShortLe, "$this$commonReadShortLe");
        commonReadShortLe.require(2L);
        return commonReadShortLe.bufferField.readShortLe();
    }

    @NotNull
    public static final String commonReadUtf8(@NotNull RealBufferedSource commonReadUtf8, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadUtf8, "$this$commonReadUtf8");
        commonReadUtf8.require(j);
        return commonReadUtf8.bufferField.readUtf8(j);
    }

    public static final int commonReadUtf8CodePoint(@NotNull RealBufferedSource commonReadUtf8CodePoint) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadUtf8CodePoint, "$this$commonReadUtf8CodePoint");
        commonReadUtf8CodePoint.require(1L);
        byte b = commonReadUtf8CodePoint.bufferField.getByte(0L);
        if ((b & 224) == 192) {
            commonReadUtf8CodePoint.require(2L);
        } else if ((b & 240) == 224) {
            commonReadUtf8CodePoint.require(3L);
        } else if ((b & 248) == 240) {
            commonReadUtf8CodePoint.require(4L);
        }
        return commonReadUtf8CodePoint.bufferField.readUtf8CodePoint();
    }

    @Nullable
    public static final String commonReadUtf8Line(@NotNull RealBufferedSource commonReadUtf8Line) {
        Intrinsics.checkNotNullParameter(commonReadUtf8Line, "$this$commonReadUtf8Line");
        long jIndexOf = commonReadUtf8Line.indexOf((byte) 10);
        if (jIndexOf != -1) {
            return BufferKt.readUtf8Line(commonReadUtf8Line.bufferField, jIndexOf);
        }
        if (commonReadUtf8Line.bufferField.size() != 0) {
            return commonReadUtf8Line.readUtf8(commonReadUtf8Line.bufferField.size());
        }
        return null;
    }

    @NotNull
    public static final String commonReadUtf8LineStrict(@NotNull RealBufferedSource commonReadUtf8LineStrict, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadUtf8LineStrict, "$this$commonReadUtf8LineStrict");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("limit < 0: ", j).toString());
        }
        long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
        byte b = (byte) 10;
        long jIndexOf = commonReadUtf8LineStrict.indexOf(b, 0L, j2);
        if (jIndexOf != -1) {
            return BufferKt.readUtf8Line(commonReadUtf8LineStrict.bufferField, jIndexOf);
        }
        if (j2 < Long.MAX_VALUE && commonReadUtf8LineStrict.request(j2) && commonReadUtf8LineStrict.bufferField.getByte(j2 - 1) == ((byte) 13) && commonReadUtf8LineStrict.request(1 + j2) && commonReadUtf8LineStrict.bufferField.getByte(j2) == b) {
            return BufferKt.readUtf8Line(commonReadUtf8LineStrict.bufferField, j2);
        }
        Buffer buffer = new Buffer();
        Buffer buffer2 = commonReadUtf8LineStrict.bufferField;
        buffer2.copyTo(buffer, 0L, Math.min(32, buffer2.size()));
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("\\n not found: limit=");
        sbM.append(Math.min(commonReadUtf8LineStrict.bufferField.size(), j));
        sbM.append(" content=");
        sbM.append(buffer.readByteString().hex());
        sbM.append("…");
        throw new EOFException(sbM.toString());
    }

    public static final boolean commonRequest(@NotNull RealBufferedSource commonRequest, long j) {
        Intrinsics.checkNotNullParameter(commonRequest, "$this$commonRequest");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        if (!(!commonRequest.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (commonRequest.bufferField.size() < j) {
            if (commonRequest.source.read(commonRequest.bufferField, 8192) == -1) {
                return false;
            }
        }
        return true;
    }

    public static final void commonRequire(@NotNull RealBufferedSource commonRequire, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(commonRequire, "$this$commonRequire");
        if (!commonRequire.request(j)) {
            throw new EOFException();
        }
    }

    public static final int commonSelect(@NotNull RealBufferedSource commonSelect, @NotNull Options options) throws EOFException {
        Intrinsics.checkNotNullParameter(commonSelect, "$this$commonSelect");
        Intrinsics.checkNotNullParameter(options, "options");
        if (!(!commonSelect.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        do {
            int iSelectPrefix = BufferKt.selectPrefix(commonSelect.bufferField, options, true);
            if (iSelectPrefix != -2) {
                if (iSelectPrefix == -1) {
                    return -1;
                }
                commonSelect.bufferField.skip(options.getByteStrings()[iSelectPrefix].size());
                return iSelectPrefix;
            }
        } while (commonSelect.source.read(commonSelect.bufferField, 8192) != -1);
        return -1;
    }

    public static final void commonSkip(@NotNull RealBufferedSource commonSkip, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(commonSkip, "$this$commonSkip");
        if (!(!commonSkip.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (j > 0) {
            if (commonSkip.bufferField.size() == 0 && commonSkip.source.read(commonSkip.bufferField, 8192) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j, commonSkip.bufferField.size());
            commonSkip.bufferField.skip(jMin);
            j -= jMin;
        }
    }

    @NotNull
    public static final Timeout commonTimeout(@NotNull RealBufferedSource commonTimeout) {
        Intrinsics.checkNotNullParameter(commonTimeout, "$this$commonTimeout");
        return commonTimeout.source.getTimeout();
    }

    @NotNull
    public static final String commonToString(@NotNull RealBufferedSource commonToString) {
        Intrinsics.checkNotNullParameter(commonToString, "$this$commonToString");
        return "buffer(" + commonToString.source + ')';
    }

    @NotNull
    public static final byte[] commonReadByteArray(@NotNull RealBufferedSource commonReadByteArray) throws IOException {
        Intrinsics.checkNotNullParameter(commonReadByteArray, "$this$commonReadByteArray");
        commonReadByteArray.bufferField.writeAll(commonReadByteArray.source);
        return commonReadByteArray.bufferField.readByteArray();
    }

    @NotNull
    public static final ByteString commonReadByteString(@NotNull RealBufferedSource commonReadByteString) throws IOException {
        Intrinsics.checkNotNullParameter(commonReadByteString, "$this$commonReadByteString");
        commonReadByteString.bufferField.writeAll(commonReadByteString.source);
        return commonReadByteString.bufferField.readByteString();
    }

    @NotNull
    public static final String commonReadUtf8(@NotNull RealBufferedSource commonReadUtf8) throws IOException {
        Intrinsics.checkNotNullParameter(commonReadUtf8, "$this$commonReadUtf8");
        commonReadUtf8.bufferField.writeAll(commonReadUtf8.source);
        return commonReadUtf8.bufferField.readUtf8();
    }

    public static final void commonReadFully(@NotNull RealBufferedSource commonReadFully, @NotNull Buffer sink, long j) throws IOException {
        Intrinsics.checkNotNullParameter(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            commonReadFully.require(j);
            commonReadFully.bufferField.readFully(sink, j);
        } catch (EOFException e) {
            sink.writeAll(commonReadFully.bufferField);
            throw e;
        }
    }

    public static final long commonIndexOf(@NotNull RealBufferedSource commonIndexOf, @NotNull ByteString bytes, long j) throws IOException {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (!(!commonIndexOf.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (true) {
            long jIndexOf = commonIndexOf.bufferField.indexOf(bytes, j);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long size = commonIndexOf.bufferField.size();
            if (commonIndexOf.source.read(commonIndexOf.bufferField, 8192) == -1) {
                return -1L;
            }
            j = Math.max(j, (size - ((long) bytes.size())) + 1);
        }
    }

    public static final int commonRead(@NotNull RealBufferedSource commonRead, @NotNull byte[] sink, int i, int i2) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = i2;
        Util.checkOffsetAndCount(sink.length, i, j);
        if (commonRead.bufferField.size() == 0 && commonRead.source.read(commonRead.bufferField, 8192) == -1) {
            return -1;
        }
        return commonRead.bufferField.read(sink, i, (int) Math.min(j, commonRead.bufferField.size()));
    }
}
