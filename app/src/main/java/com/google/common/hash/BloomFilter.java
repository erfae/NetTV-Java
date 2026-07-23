package com.google.common.hash;

import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.math.DoubleMath;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.RoundingMode;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@ElementTypesAreNonnullByDefault
public final class BloomFilter<T> implements Predicate<T>, Serializable {
    private final BloomFilterStrategies.LockFreeBitArray bits;
    private final Funnel<? super T> funnel;
    private final int numHashFunctions;
    private final Strategy strategy;

    public static class SerialForm<T> implements Serializable {
        private static final long serialVersionUID = 1;
        public final Funnel<? super T> funnel;
        public final Strategy strategy;

        public SerialForm(BloomFilter<T> bloomFilter) {
            BloomFilterStrategies.LockFreeBitArray.toPlainArray(((BloomFilter) bloomFilter).bits.data);
            int unused = ((BloomFilter) bloomFilter).numHashFunctions;
            this.funnel = ((BloomFilter) bloomFilter).funnel;
            this.strategy = ((BloomFilter) bloomFilter).strategy;
        }
    }

    public interface Strategy extends Serializable {
        <T> boolean mightContain(@ParametricNullness T t, Funnel<? super T> funnel, int i, BloomFilterStrategies.LockFreeBitArray lockFreeBitArray);

        int ordinal();

        <T> boolean put(@ParametricNullness T t, Funnel<? super T> funnel, int i, BloomFilterStrategies.LockFreeBitArray lockFreeBitArray);
    }

    private BloomFilter(BloomFilterStrategies.LockFreeBitArray lockFreeBitArray, int i, Funnel<? super T> funnel, Strategy strategy) {
        Preconditions.checkArgument(i > 0, "numHashFunctions (%s) must be > 0", i);
        Preconditions.checkArgument(i <= 255, "numHashFunctions (%s) must be <= 255", i);
        this.bits = (BloomFilterStrategies.LockFreeBitArray) Preconditions.checkNotNull(lockFreeBitArray);
        this.numHashFunctions = i;
        this.funnel = (Funnel) Preconditions.checkNotNull(funnel);
        this.strategy = (Strategy) Preconditions.checkNotNull(strategy);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i, double d) {
        return create(funnel, i, d);
    }

    public static <T> BloomFilter<T> readFrom(InputStream inputStream, Funnel<? super T> funnel) throws IOException {
        int i;
        int i2;
        Preconditions.checkNotNull(inputStream, "InputStream");
        Preconditions.checkNotNull(funnel, "Funnel");
        byte b = -1;
        try {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            byte b2 = dataInputStream.readByte();
            try {
                i2 = UnsignedBytes.toInt(dataInputStream.readByte());
                try {
                    int i3 = dataInputStream.readInt();
                    try {
                        BloomFilterStrategies bloomFilterStrategies = BloomFilterStrategies.values()[b2];
                        long[] jArr = new long[i3];
                        for (int i4 = 0; i4 < i3; i4++) {
                            jArr[i4] = dataInputStream.readLong();
                        }
                        return new BloomFilter<>(new BloomFilterStrategies.LockFreeBitArray(jArr), i2, funnel, bloomFilterStrategies);
                    } catch (RuntimeException e) {
                        e = e;
                        b = b2;
                        i = i3;
                        StringBuilder sb = new StringBuilder(TsExtractor.TS_STREAM_TYPE_SPLICE_INFO);
                        sb.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
                        sb.append((int) b);
                        sb.append(" numHashFunctions: ");
                        sb.append(i2);
                        sb.append(" dataLength: ");
                        sb.append(i);
                        throw new IOException(sb.toString(), e);
                    }
                } catch (RuntimeException e2) {
                    e = e2;
                    b = b2;
                    i = -1;
                    StringBuilder sb2 = new StringBuilder(TsExtractor.TS_STREAM_TYPE_SPLICE_INFO);
                    sb2.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
                    sb2.append((int) b);
                    sb2.append(" numHashFunctions: ");
                    sb2.append(i2);
                    sb2.append(" dataLength: ");
                    sb2.append(i);
                    throw new IOException(sb2.toString(), e);
                }
            } catch (RuntimeException e3) {
                e = e3;
                i2 = -1;
            }
        } catch (RuntimeException e4) {
            e = e4;
            i = -1;
            i2 = -1;
        }
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }

    @Override // com.google.common.base.Predicate
    @Deprecated
    public boolean apply(@ParametricNullness T t) {
        return mightContain(t);
    }

    public long approximateElementCount() {
        double dBitSize = this.bits.bitSize();
        return DoubleMath.roundToLong(((-Math.log1p(-(this.bits.bitCount() / dBitSize))) * dBitSize) / ((double) this.numHashFunctions), RoundingMode.HALF_UP);
    }

    @VisibleForTesting
    public final long bitSize() {
        return this.bits.bitSize();
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<>(new BloomFilterStrategies.LockFreeBitArray(BloomFilterStrategies.LockFreeBitArray.toPlainArray(this.bits.data)), this.numHashFunctions, this.funnel, this.strategy);
    }

    @Override // com.google.common.base.Predicate
    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BloomFilter)) {
            return false;
        }
        BloomFilter bloomFilter = (BloomFilter) obj;
        return this.numHashFunctions == bloomFilter.numHashFunctions && this.funnel.equals(bloomFilter.funnel) && this.bits.equals(bloomFilter.bits) && this.strategy.equals(bloomFilter.strategy);
    }

    public double expectedFpp() {
        return Math.pow(this.bits.bitCount() / bitSize(), this.numHashFunctions);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.numHashFunctions), this.funnel, this.strategy, this.bits);
    }

    public boolean isCompatible(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        return this != bloomFilter && this.numHashFunctions == bloomFilter.numHashFunctions && bitSize() == bloomFilter.bitSize() && this.strategy.equals(bloomFilter.strategy) && this.funnel.equals(bloomFilter.funnel);
    }

    public boolean mightContain(@ParametricNullness T t) {
        return this.strategy.mightContain(t, this.funnel, this.numHashFunctions, this.bits);
    }

    @CanIgnoreReturnValue
    public boolean put(@ParametricNullness T t) {
        return this.strategy.put(t, this.funnel, this.numHashFunctions, this.bits);
    }

    public void putAll(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        Preconditions.checkArgument(this != bloomFilter, "Cannot combine a BloomFilter with itself.");
        int i = this.numHashFunctions;
        int i2 = bloomFilter.numHashFunctions;
        Preconditions.checkArgument(i == i2, "BloomFilters must have the same number of hash functions (%s != %s)", i, i2);
        Preconditions.checkArgument(bitSize() == bloomFilter.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", bitSize(), bloomFilter.bitSize());
        Preconditions.checkArgument(this.strategy.equals(bloomFilter.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, bloomFilter.strategy);
        Preconditions.checkArgument(this.funnel.equals(bloomFilter.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, bloomFilter.funnel);
        this.bits.putAll(bloomFilter.bits);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(SignedBytes.checkedCast(this.strategy.ordinal()));
        dataOutputStream.writeByte(UnsignedBytes.checkedCast(this.numHashFunctions));
        dataOutputStream.writeInt(this.bits.data.length());
        for (int i = 0; i < this.bits.data.length(); i++) {
            dataOutputStream.writeLong(this.bits.data.get(i));
        }
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j, double d) {
        BloomFilterStrategies bloomFilterStrategies = BloomFilterStrategies.MURMUR128_MITZ_64;
        Preconditions.checkNotNull(funnel);
        Preconditions.checkArgument(j >= 0, "Expected insertions (%s) must be >= 0", j);
        Preconditions.checkArgument(d > 0.0d, "False positive probability (%s) must be > 0.0", Double.valueOf(d));
        Preconditions.checkArgument(d < 1.0d, "False positive probability (%s) must be < 1.0", Double.valueOf(d));
        Preconditions.checkNotNull(bloomFilterStrategies);
        if (j == 0) {
            j = 1;
        }
        if (d == 0.0d) {
            d = Double.MIN_VALUE;
        }
        long jLog = (long) ((Math.log(d) * (-j)) / (Math.log(2.0d) * Math.log(2.0d)));
        try {
            return new BloomFilter<>(new BloomFilterStrategies.LockFreeBitArray(jLog), Math.max(1, (int) Math.round(Math.log(2.0d) * (jLog / j))), funnel, bloomFilterStrategies);
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder(57);
            sb.append("Could not create BloomFilter of ");
            sb.append(jLog);
            sb.append(" bits");
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i) {
        return create(funnel, i);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j) {
        return create(funnel, j, 0.03d);
    }
}
