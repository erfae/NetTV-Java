package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@GwtCompatible
@Deprecated
@ElementTypesAreNonnullByDefault
public abstract class TreeTraverser<T> {

    public final class BreadthFirstIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
        private final Queue<T> queue;

        public BreadthFirstIterator(T t) {
            ArrayDeque arrayDeque = new ArrayDeque();
            this.queue = arrayDeque;
            arrayDeque.add(t);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override // java.util.Iterator, com.google.common.collect.PeekingIterator
        public T next() {
            T tRemove = this.queue.remove();
            Iterables.addAll(this.queue, TreeTraverser.this.children(tRemove));
            return tRemove;
        }

        @Override // com.google.common.collect.PeekingIterator
        public T peek() {
            return this.queue.element();
        }
    }

    public final class PostOrderIterator extends AbstractIterator<T> {
        private final ArrayDeque<PostOrderNode<T>> stack;

        public PostOrderIterator(T t) {
            ArrayDeque<PostOrderNode<T>> arrayDeque = new ArrayDeque<>();
            this.stack = arrayDeque;
            arrayDeque.addLast(expand(t));
        }

        private PostOrderNode<T> expand(T t) {
            return new PostOrderNode<>(t, TreeTraverser.this.children(t).iterator());
        }

        @Override // com.google.common.collect.AbstractIterator
        @CheckForNull
        public final T computeNext() {
            while (!this.stack.isEmpty()) {
                PostOrderNode<T> last = this.stack.getLast();
                if (!last.childIterator.hasNext()) {
                    this.stack.removeLast();
                    return last.root;
                }
                this.stack.addLast(expand(last.childIterator.next()));
            }
            endOfData();
            return null;
        }
    }

    public static final class PostOrderNode<T> {
        public final Iterator<T> childIterator;
        public final T root;

        public PostOrderNode(T t, Iterator<T> it) {
            this.root = (T) Preconditions.checkNotNull(t);
            this.childIterator = (Iterator) Preconditions.checkNotNull(it);
        }
    }

    public final class PreOrderIterator extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> stack;

        public PreOrderIterator(T t) {
            ArrayDeque arrayDeque = new ArrayDeque();
            this.stack = arrayDeque;
            arrayDeque.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t)));
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

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
        @Override // java.util.Iterator
        public T next() {
            Iterator<T> last = this.stack.getLast();
            T t = (T) Preconditions.checkNotNull(last.next());
            if (!last.hasNext()) {
                this.stack.removeLast();
            }
            Iterator<T> it = TreeTraverser.this.children(t).iterator();
            if (it.hasNext()) {
                this.stack.addLast(it);
            }
            return t;
        }
    }

    @Deprecated
    public static <T> TreeTraverser<T> using(final Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>() { // from class: com.google.common.collect.TreeTraverser.1
            @Override // com.google.common.collect.TreeTraverser
            public Iterable<T> children(T t) {
                return (Iterable) function.apply(t);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> breadthFirstTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.4
            @Override // java.lang.Iterable
            public UnmodifiableIterator<T> iterator() {
                return new BreadthFirstIterator(t);
            }
        };
    }

    public abstract Iterable<T> children(T t);

    @Deprecated
    public final FluentIterable<T> postOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.3
            @Override // java.lang.Iterable
            public UnmodifiableIterator<T> iterator() {
                TreeTraverser treeTraverser = TreeTraverser.this;
                Object obj = t;
                Objects.requireNonNull(treeTraverser);
                return new PostOrderIterator(obj);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> preOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.2
            @Override // java.lang.Iterable
            public UnmodifiableIterator<T> iterator() {
                TreeTraverser treeTraverser = TreeTraverser.this;
                Object obj = t;
                Objects.requireNonNull(treeTraverser);
                return new PreOrderIterator(obj);
            }
        };
    }
}
