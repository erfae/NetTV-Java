package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
interface NetworkConnections<N, E> {
    void addInEdge(E e, N n, boolean z);

    void addOutEdge(E e, N n);

    N adjacentNode(E e);

    Set<N> adjacentNodes();

    Set<E> edgesConnecting(N n);

    Set<E> inEdges();

    Set<E> incidentEdges();

    Set<E> outEdges();

    Set<N> predecessors();

    @CanIgnoreReturnValue
    @CheckForNull
    N removeInEdge(E e, boolean z);

    @CanIgnoreReturnValue
    N removeOutEdge(E e);

    Set<N> successors();
}
