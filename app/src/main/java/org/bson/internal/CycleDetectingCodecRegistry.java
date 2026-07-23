package org.bson.internal;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes2.dex */
interface CycleDetectingCodecRegistry extends CodecRegistry {
    <T> Codec<T> get(ChildCodecRegistry<T> childCodecRegistry);
}
