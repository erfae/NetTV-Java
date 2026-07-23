package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@ElementTypesAreNonnullByDefault
@DoNotMock("Call forGraph or forTree, passing a lambda or a Graph with the desired edges (built with GraphBuilder)")
public abstract class Traverser<N> {
    private final SuccessorsFunction<N> successorFunction;

    public enum InsertionOrder {
        FRONT { // from class: com.google.common.graph.Traverser.InsertionOrder.1
            @Override // com.google.common.graph.Traverser.InsertionOrder
            public final <T> void insertInto(Deque<T> deque, T t) {
                deque.addFirst(t);
            }
        },
        BACK { // from class: com.google.common.graph.Traverser.InsertionOrder.2
            @Override // com.google.common.graph.Traverser.InsertionOrder
            public final <T> void insertInto(Deque<T> deque, T t) {
                deque.addLast(t);
            }
        };

        public abstract <T> void insertInto(Deque<T> deque, T t);
    }

    public static abstract class Traversal<N> {
        public final SuccessorsFunction<N> successorFunction;

        public Traversal(SuccessorsFunction<N> successorsFunction) {
            this.successorFunction = successorsFunction;
        }

        private Iterator<N> topDown(Iterator<? extends N> it, final InsertionOrder insertionOrder) {
            final ArrayDeque arrayDeque = new ArrayDeque();
            arrayDeque.add(it);
            return new AbstractIterator<N>() { // from class: com.google.common.graph.Traverser.Traversal.3
                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // com.google.common.collect.AbstractIterator
                @CheckForNull
                public final N computeNext() {
                    do {
                        N n = (N) Traversal.this.visitNext(arrayDeque);
                        if (n != null) {
                            Iterator<? extends N> it2 = Traversal.this.successorFunction.successors(n).iterator();
                            if (it2.hasNext()) {
                                insertionOrder.insertInto(arrayDeque, it2);
                            }
                            return n;
                        }
                    } while (!arrayDeque.isEmpty());
                    endOfData();
                    return null;
                }
            };
        }

        public final Iterator<N> breadthFirst(Iterator<? extends N> it) {
            return topDown(it, InsertionOrder.BACK);
        }

        public final Iterator<N> preOrder(Iterator<? extends N> it) {
            return topDown(it, InsertionOrder.FRONT);
        }

        @CheckForNull
        public abstract N visitNext(Deque<Iterator<? extends N>> deque);
    }

    public static <N> Traverser<N> forGraph(final SuccessorsFunction<N> successorsFunction) {
        return new Traverser<N>(successorsFunction) { // from class: com.google.common.graph.Traverser.1
            @Override // com.google.common.graph.Traverser
            public final Traversal<N> newTraversal() {
                SuccessorsFunction successorsFunction2 = successorsFunction;
                final HashSet hashSet = new HashSet();
                return (Traversal<N>) new Traversal<Object>(successorsFunction2) { // from class: com.google.common.graph.Traverser.Traversal.1
                    @Override // com.google.common.graph.Traverser.Traversal
                    @CheckForNull
                    public final Object visitNext(Deque<Iterator<? extends Object>> deque) {
                        Iterator<? extends Object> first = deque.getFirst();
                        while (first.hasNext()) {
                            Object next = first.next();
                            Objects.requireNonNull(next);
                            if (hashSet.add(next)) {
                                return next;
                            }
                        }
                        deque.removeFirst();
                        return null;
                    }
                };
            }
        };
    }

    public static <N> Traverser<N> forTree(final SuccessorsFunction<N> successorsFunction) {
        if (successorsFunction instanceof BaseGraph) {
            Preconditions.checkArgument(((BaseGraph) successorsFunction).isDirected(), "Undirected graphs can never be trees.");
        }
        if (successorsFunction instanceof Network) {
            Preconditions.checkArgument(((Network) successorsFunction).isDirected(), "Undirected networks can never be trees.");
        }
        return new Traverser<N>(successorsFunction) { // from class: com.google.common.graph.Traverser.2
            @Override // com.google.common.graph.Traverser
            public final Traversal<N> newTraversal() {
                return (Traversal<N>) new Traversal<Object>(successorsFunction) { // from class: com.google.common.graph.Traverser.Traversal.2
                    @Override // com.google.common.graph.Traverser.Traversal
                    @CheckForNull
                    public final Object visitNext(Deque<Iterator<? extends Object>> deque) {
                        Iterator<? extends Object> first = deque.getFirst();
                        if (first.hasNext()) {
                            return Preconditions.checkNotNull(first.next());
                        }
                        deque.removeFirst();
                        return null;
                    }
                };
            }
        };
    }

    private ImmutableSet<N> validate(Iterable<? extends N> iterable) {
        ImmutableSet<N> immutableSetCopyOf = ImmutableSet.copyOf(iterable);
        UnmodifiableIterator<N> it = immutableSetCopyOf.iterator();
        while (it.hasNext()) {
            this.successorFunction.successors(it.next());
        }
        return immutableSetCopyOf;
    }

    public final Iterable<N> breadthFirst(N n) {
        return breadthFirst((Iterable) ImmutableSet.of(n));
    }

    public final Iterable<N> depthFirstPostOrder(N n) {
        return depthFirstPostOrder((Iterable) ImmutableSet.of(n));
    }

    public final Iterable<N> depthFirstPreOrder(N n) {
        return depthFirstPreOrder((Iterable) ImmutableSet.of(n));
    }

    public abstract Traversal<N> newTraversal();

    private Traverser(SuccessorsFunction<N> successorsFunction) {
        this.successorFunction = (SuccessorsFunction) Preconditions.checkNotNull(successorsFunction);
    }

    public final Iterable<N> breadthFirst(Iterable<? extends N> iterable) {
        final ImmutableSet<N> immutableSetValidate = validate(iterable);
        return new Iterable<N>() { // from class: com.google.common.graph.Traverser.3
            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return Traverser.this.newTraversal().breadthFirst(immutableSetValidate.iterator());
            }
        };
    }

    public final Iterable<N> depthFirstPostOrder(Iterable<? extends N> iterable) {
        final ImmutableSet<N> immutableSetValidate = validate(iterable);
        return new Iterable<N>() { // from class: com.google.common.graph.Traverser.5
            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                final Traversal<N> traversalNewTraversal = Traverser.this.newTraversal();
                UnmodifiableIterator it = immutableSetValidate.iterator();
                Objects.requireNonNull(traversalNewTraversal);
                final ArrayDeque arrayDeque = new ArrayDeque();
                final ArrayDeque arrayDeque2 = new ArrayDeque();
                arrayDeque2.add(it);
                return new AbstractIterator<Object>() { // from class: com.google.common.graph.Traverser.Traversal.4
                    @Override // com.google.common.collect.AbstractIterator
                    @CheckForNull
                    public final Object computeNext() {
                        while (true) {
                            Object objVisitNext = Traversal.this.visitNext(arrayDeque2);
                            if (objVisitNext == null) {
                                if (!arrayDeque.isEmpty()) {
                                    return arrayDeque.pop();
                                }
                                endOfData();
                                return null;
                            }
                            Iterator<? extends N> it2 = Traversal.this.successorFunction.successors((N) objVisitNext).iterator();
                            if (!it2.hasNext()) {
                                return objVisitNext;
                            }
                            arrayDeque2.addFirst(it2);
                            arrayDeque.push(objVisitNext);
                        }
                    }
                };
            }
        };
    }

    public final Iterable<N> depthFirstPreOrder(Iterable<? extends N> iterable) {
        final ImmutableSet<N> immutableSetValidate = validate(iterable);
        return new Iterable<N>() { // from class: com.google.common.graph.Traverser.4
            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return Traverser.this.newTraversal().preOrder(immutableSetValidate.iterator());
            }
        };
    }
}
