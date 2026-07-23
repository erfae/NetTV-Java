package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
@ElementTypesAreNonnullByDefault
final class ChecksumHashFunction extends AbstractHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int bits;
    private final ImmutableSupplier<? extends Checksum> checksumSupplier;
    private final String toString;

    public final class ChecksumHasher extends AbstractByteHasher {
        private final Checksum checksum;

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            long value = this.checksum.getValue();
            return ChecksumHashFunction.this.bits == 32 ? HashCode.fromInt((int) value) : HashCode.fromLong(value);
        }

        @Override // com.google.common.hash.AbstractByteHasher
        public final void update(byte b) {
            this.checksum.update(b);
        }

        private ChecksumHasher(Checksum checksum) {
            this.checksum = (Checksum) Preconditions.checkNotNull(checksum);
        }

        @Override // com.google.common.hash.AbstractByteHasher
        public final void update(byte[] bArr, int i, int i2) {
            this.checksum.update(bArr, i, i2);
        }
    }

    public ChecksumHashFunction(ImmutableSupplier immutableSupplier, String str) {
        this.checksumSupplier = (ImmutableSupplier) Preconditions.checkNotNull(immutableSupplier);
        Preconditions.checkArgument(true, "bits (%s) must be either 32 or 64", 32);
        this.bits = 32;
        this.toString = (String) Preconditions.checkNotNull(str);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return this.bits;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new ChecksumHasher(this.checksumSupplier.get());
    }

    public String toString() {
        return this.toString;
    }
}
