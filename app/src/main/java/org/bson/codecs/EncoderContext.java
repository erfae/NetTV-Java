package org.bson.codecs;

import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes2.dex */
public final class EncoderContext {
    private static final EncoderContext DEFAULT_CONTEXT = builder().build();
    private final boolean encodingCollectibleDocument;

    public static final class Builder {
        private boolean encodingCollectibleDocument;

        public EncoderContext build() {
            return new EncoderContext(this);
        }

        public Builder isEncodingCollectibleDocument(boolean z) {
            this.encodingCollectibleDocument = z;
            return this;
        }

        private Builder() {
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> void encodeWithChildContext(Encoder<T> encoder, BsonWriter bsonWriter, T t) {
        encoder.encode(bsonWriter, t, DEFAULT_CONTEXT);
    }

    public EncoderContext getChildContext() {
        return DEFAULT_CONTEXT;
    }

    public boolean isEncodingCollectibleDocument() {
        return this.encodingCollectibleDocument;
    }

    private EncoderContext(Builder builder) {
        this.encodingCollectibleDocument = builder.encodingCollectibleDocument;
    }
}
