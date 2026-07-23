package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
class StandardNetwork<N, E> extends AbstractNetwork<N, E> {
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;
    private final ElementOrder<E> edgeOrder;
    public final MapIteratorCache<E, N> edgeToReferenceNode;
    private final boolean isDirected;
    public final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    public StandardNetwork(NetworkBuilder<? super N, ? super E> networkBuilder, Map<N, NetworkConnections<N, E>> map, Map<E, N> map2) {
        this.isDirected = networkBuilder.directed;
        this.allowsParallelEdges = networkBuilder.allowsParallelEdges;
        this.allowsSelfLoops = networkBuilder.allowsSelfLoops;
        ElementOrder<? super N> elementOrder = networkBuilder.nodeOrder;
        Objects.requireNonNull(elementOrder);
        this.nodeOrder = elementOrder;
        ElementOrder<? super Object> elementOrder2 = networkBuilder.edgeOrder;
        Objects.requireNonNull(elementOrder2);
        this.edgeOrder = elementOrder2;
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.edgeToReferenceNode = new MapIteratorCache<>(map2);
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N n) {
        return checkedConnections(n).adjacentNodes();
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    public final NetworkConnections<N, E> checkedConnections(N n) {
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(n);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", n));
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        MapIteratorCache<E, N> mapIteratorCache = this.edgeToReferenceNode;
        Objects.requireNonNull(mapIteratorCache);
        return new MapIteratorCache.AnonymousClass1();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N n, N n2) {
        NetworkConnections<N, E> networkConnectionsCheckedConnections = checkedConnections(n);
        if (!this.allowsSelfLoops && n == n2) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(this.nodeConnections.containsKey(n2), "Node %s is not an element of this graph.", n2);
        return networkConnectionsCheckedConnections.edgesConnecting(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N n) {
        return checkedConnections(n).inEdges();
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N n) {
        return checkedConnections(n).incidentEdges();
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E e) {
        N n = this.edgeToReferenceNode.get(e);
        if (n == null) {
            Preconditions.checkNotNull(e);
            throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", e));
        }
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n);
        Objects.requireNonNull(networkConnections);
        return EndpointPair.of(this, n, networkConnections.adjacentNode(e));
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        MapIteratorCache<N, NetworkConnections<N, E>> mapIteratorCache = this.nodeConnections;
        Objects.requireNonNull(mapIteratorCache);
        return new MapIteratorCache.AnonymousClass1();
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N n) {
        return checkedConnections(n).outEdges();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n) {
        return checkedConnections(n).predecessors();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n) {
        return checkedConnections(n).successors();
    }
}
