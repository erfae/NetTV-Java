package org.bson;

/* JADX INFO: loaded from: classes2.dex */
public class BsonWriterSettings {
    private final int maxSerializationDepth;

    public BsonWriterSettings(int i) {
        this.maxSerializationDepth = i;
    }

    public int getMaxSerializationDepth() {
        return this.maxSerializationDepth;
    }

    public BsonWriterSettings() {
        this(1024);
    }
}
