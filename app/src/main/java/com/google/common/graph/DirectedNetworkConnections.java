package com.google.common.graph;

import com.google.common.collect.BiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class DirectedNetworkConnections<N, E> extends AbstractDirectedNetworkConnections<N, E> {
    public DirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int i) {
        super(map, map2, i);
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> edgesConnecting(N n) {
        return new EdgesConnecting(((BiMap) this.outEdgeMap).inverse(), n);
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> predecessors() {
        return Collections.unmodifiableSet(((BiMap) this.inEdgeMap).values());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> successors() {
        return Collections.unmodifiableSet(((BiMap) this.outEdgeMap).values());
    }
}
