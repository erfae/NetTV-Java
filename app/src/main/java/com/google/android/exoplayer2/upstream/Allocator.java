package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface Allocator {

    public interface AllocationNode {
        Allocation getAllocation();

        @Nullable
        AllocationNode next();
    }

    Allocation allocate();

    int getIndividualAllocationLength();

    int getTotalBytesAllocated();

    void release(Allocation allocation);

    void release(AllocationNode allocationNode);

    void trim();
}
