package org.bson;

/* JADX INFO: loaded from: classes2.dex */
public class BsonBinaryWriterSettings {
    private final int maxDocumentSize;

    public BsonBinaryWriterSettings(int i) {
        this.maxDocumentSize = i;
    }

    public int getMaxDocumentSize() {
        return this.maxDocumentSize;
    }

    public BsonBinaryWriterSettings() {
        this(Integer.MAX_VALUE);
    }
}
