package com.google.android.exoplayer2.metadata;

import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface MetadataDecoder {
    @Nullable
    Metadata decode(MetadataInputBuffer metadataInputBuffer);
}
