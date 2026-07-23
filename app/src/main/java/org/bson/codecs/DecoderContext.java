package org.bson.codecs;

import org.bson.BsonReader;

/* JADX INFO: loaded from: classes2.dex */
public final class DecoderContext {
    private static final DecoderContext DEFAULT_CONTEXT = builder().build();
    private final boolean checkedDiscriminator;

    public static final class Builder {
        private boolean checkedDiscriminator;

        public DecoderContext build() {
            return new DecoderContext(this);
        }

        public Builder checkedDiscriminator(boolean z) {
            this.checkedDiscriminator = z;
            return this;
        }

        public boolean hasCheckedDiscriminator() {
            return this.checkedDiscriminator;
        }

        private Builder() {
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T decodeWithChildContext(Decoder<T> decoder, BsonReader bsonReader) {
        return decoder.decode(bsonReader, DEFAULT_CONTEXT);
    }

    public boolean hasCheckedDiscriminator() {
        return this.checkedDiscriminator;
    }

    private DecoderContext(Builder builder) {
        this.checkedDiscriminator = builder.hasCheckedDiscriminator();
    }
}
