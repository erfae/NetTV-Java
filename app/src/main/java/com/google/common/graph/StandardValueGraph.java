package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
class StandardValueGraph<N, V> extends AbstractValueGraph<N, V> {
    private final boolean allowsSelfLoops;
    public long edgeCount;
    private final boolean isDirected;
    public final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    public StandardValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder, Map<N, GraphConnections<N, V>> map, long j) {
        this.isDirected = abstractGraphBuilder.directed;
        this.allowsSelfLoops = abstractGraphBuilder.allowsSelfLoops;
        ElementOrder<? super N> elementOrder = abstractGraphBuilder.nodeOrder;
        Objects.requireNonNull(elementOrder);
        this.nodeOrder = elementOrder;
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        Graphs.checkNonNegative(j);
        this.edgeCount = j;
    }

    private final GraphConnections<N, V> checkedConnections(N n) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n);
        if (graphConnections != null) {
            return graphConnections;
        }
        Preconditions.checkNotNull(n);
        String strValueOf = String.valueOf(n);
        throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 38, "Node ", strValueOf, " is not an element of this graph."));
    }

    @CheckForNull
    private final V edgeValueOrDefaultInternal(N n, N n2, @CheckForNull V v) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n);
        V vValue = graphConnections == null ? null : graphConnections.value(n2);
        return vValue == null ? v : vValue;
    }

    private final boolean hasEdgeConnectingInternal(N n, N n2) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n);
        return graphConnections != null && graphConnections.successors().contains(n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n) {
        return checkedConnections(n).adjacentNodes();
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    @Override // com.google.common.graph.AbstractBaseGraph
    public final long edgeCount() {
        return this.edgeCount;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckForNull
    public V edgeValueOrDefault(N n, N n2, @CheckForNull V v) {
        return (V) edgeValueOrDefaultInternal(Preconditions.checkNotNull(n), Preconditions.checkNotNull(n2), v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(N n, N n2) {
        return hasEdgeConnectingInternal(Preconditions.checkNotNull(n), Preconditions.checkNotNull(n2));
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public Set<EndpointPair<N>> incidentEdges(N n) {
        final GraphConnections<N, V> graphConnectionsCheckedConnections = checkedConnections(n);
        return new IncidentEdgeSet<N>(this, n) { // from class: com.google.common.graph.StandardValueGraph.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<EndpointPair<N>> iterator() {
                return graphConnectionsCheckedConnections.incidentEdgeIterator(this.node);
            }
        };
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> nodes() {
        MapIteratorCache<N, GraphConnections<N, V>> mapIteratorCache = this.nodeConnections;
        Objects.requireNonNull(mapIteratorCache);
        return new MapIteratorCache.AnonymousClass1();
    }

    @CheckForNull
    public V edgeValueOrDefault(EndpointPair<N> endpointPair, @CheckForNull V v) {
        validateEndpoints(endpointPair);
        return edgeValueOrDefaultInternal(endpointPair.nodeU(), endpointPair.nodeV(), v);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        return isOrderingCompatible(endpointPair) && hasEdgeConnectingInternal(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n) {
        return checkedConnections(n).predecessors();
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n) {
        return checkedConnections(n).successors();
    }
}
