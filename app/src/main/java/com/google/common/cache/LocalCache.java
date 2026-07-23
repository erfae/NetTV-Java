package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    public final int concurrencyLevel;

    @NullableDecl
    public final CacheLoader<? super K, V> defaultLoader;
    public final EntryFactory entryFactory;

    @RetainedWith
    @NullableDecl
    public Set<Map.Entry<K, V>> entrySet;
    public final long expireAfterAccessNanos;
    public final long expireAfterWriteNanos;
    public final AbstractCache.StatsCounter globalStatsCounter;
    public final Equivalence<Object> keyEquivalence;

    @RetainedWith
    @NullableDecl
    public Set<K> keySet;
    public final Strength keyStrength;
    public final long maxWeight;
    public final long refreshNanos;
    public final RemovalListener<K, V> removalListener;
    public final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    public final int segmentMask;
    public final int segmentShift;
    public final Segment<K, V>[] segments;
    public final Ticker ticker;
    public final Equivalence<Object> valueEquivalence;
    public final Strength valueStrength;

    @RetainedWith
    @NullableDecl
    public Collection<V> values;
    public final Weigher<K, V> weigher;
    public static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    public static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>() { // from class: com.google.common.cache.LocalCache.1
        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @NullableDecl Object obj, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public Object get() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 0;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(Object obj) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public Object waitForValue() {
            return null;
        }
    };
    public static final Queue<?> DISCARDING_QUEUE = new AbstractQueue<Object>() { // from class: com.google.common.cache.LocalCache.2
        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }

        @Override // java.util.Queue
        public boolean offer(Object obj) {
            return true;
        }

        @Override // java.util.Queue
        public Object peek() {
            return null;
        }

        @Override // java.util.Queue
        public Object poll() {
            return null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return 0;
        }
    };

    public abstract class AbstractCacheSet<T> extends AbstractSet<T> {
        public AbstractCacheSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LocalCache.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LocalCache.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <E> E[] toArray(E[] eArr) {
            return (E[]) LocalCache.toArrayList(this).toArray(eArr);
        }
    }

    public static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        @Override // com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        public final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>() { // from class: com.google.common.cache.LocalCache.AccessQueue.1

            @Weak
            public ReferenceEntry<K, V> nextAccess = this;

            @Weak
            public ReferenceEntry<K, V> previousAccess = this;

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setAccessTime(long j) {
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextAccess = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousAccess = referenceEntry;
            }
        };

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.head;
                if (nextInAccessQueue == referenceEntry) {
                    referenceEntry.setNextInAccessQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.head;
                    referenceEntry2.setPreviousInAccessQueue(referenceEntry2);
                    return;
                } else {
                    ReferenceEntry<K, V> nextInAccessQueue2 = nextInAccessQueue.getNextInAccessQueue();
                    LocalCache.nullifyAccessOrder(nextInAccessQueue);
                    nextInAccessQueue = nextInAccessQueue2;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) { // from class: com.google.common.cache.LocalCache.AccessQueue.2
                @Override // com.google.common.collect.AbstractSequentialIterator
                public final Object computeNext(Object obj) {
                    ReferenceEntry<K, V> nextInAccessQueue = ((ReferenceEntry) obj).getNextInAccessQueue();
                    if (nextInAccessQueue == AccessQueue.this.head) {
                        return null;
                    }
                    return nextInAccessQueue;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry<K, V> previousInAccessQueue = referenceEntry.getPreviousInAccessQueue();
            ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previousInAccessQueue, nextInAccessQueue);
            LocalCache.nullifyAccessOrder(referenceEntry);
            return nextInAccessQueue != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i = 0;
            for (ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue(); nextInAccessQueue != this.head; nextInAccessQueue = nextInAccessQueue.getNextInAccessQueue()) {
                i++;
            }
            return i;
        }

        @Override // java.util.Queue
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), referenceEntry);
            ReferenceEntry<K, V> referenceEntry2 = this.head;
            referenceEntry.setNextInAccessQueue(referenceEntry2);
            referenceEntry2.setPreviousInAccessQueue(referenceEntry);
            return true;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            return nextInAccessQueue;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            remove(nextInAccessQueue);
            return nextInAccessQueue;
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 com.google.common.cache.LocalCache$EntryFactory, still in use, count: 1, list:
  (r0v0 com.google.common.cache.LocalCache$EntryFactory) from 0x0040: FILLED_NEW_ARRAY 
  (r0v0 com.google.common.cache.LocalCache$EntryFactory)
  (r1v0 com.google.common.cache.LocalCache$EntryFactory)
  (r2v0 com.google.common.cache.LocalCache$EntryFactory)
  (r3v0 com.google.common.cache.LocalCache$EntryFactory)
  (r4v0 com.google.common.cache.LocalCache$EntryFactory)
  (r5v0 com.google.common.cache.LocalCache$EntryFactory)
  (r6v0 com.google.common.cache.LocalCache$EntryFactory)
  (r7v0 com.google.common.cache.LocalCache$EntryFactory)
 A[WRAPPED] elemType: com.google.common.cache.LocalCache$EntryFactory
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
    	at java.base/java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static abstract class EntryFactory {
        STRONG { // from class: com.google.common.cache.LocalCache.EntryFactory.1
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k, i, referenceEntry);
            }
        },
        STRONG_ACCESS { // from class: com.google.common.cache.LocalCache.EntryFactory.2
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k, i, referenceEntry);
            }
        },
        STRONG_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.3
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k, i, referenceEntry);
            }
        },
        STRONG_ACCESS_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.4
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k, i, referenceEntry);
            }
        },
        WEAK { // from class: com.google.common.cache.LocalCache.EntryFactory.5
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        },
        WEAK_ACCESS { // from class: com.google.common.cache.LocalCache.EntryFactory.6
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        },
        WEAK_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.7
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        },
        WEAK_ACCESS_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.8
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        };

        public static final EntryFactory[] factories = {new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.1
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.2
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.3
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.4
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.5
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.6
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.7
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.8
            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            public final <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        }};

        static {
        }

        private EntryFactory(String str, int i) {
            super(str, i);
        }

        public static EntryFactory valueOf(String str) {
            return (EntryFactory) Enum.valueOf(EntryFactory.class, str);
        }

        public static EntryFactory[] values() {
            return (EntryFactory[]) $VALUES.clone();
        }

        public final <K, V> void copyAccessEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
            referenceEntry2.setNextInAccessQueue(nextInAccessQueue);
            nextInAccessQueue.setPreviousInAccessQueue(referenceEntry2);
            LocalCache.nullifyAccessOrder(referenceEntry);
        }

        public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            return newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }

        public final <K, V> void copyWriteEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
            referenceEntry2.setNextInWriteQueue(nextInWriteQueue);
            nextInWriteQueue.setPreviousInWriteQueue(referenceEntry2);
            LocalCache.nullifyWriteOrder(referenceEntry);
        }

        public abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry);
    }

    public final class EntryIterator extends LocalCache<K, V>.HashIterator<Map.Entry<K, V>> {
        public EntryIterator(LocalCache localCache) {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    public final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Map.Entry<K, V>> {
        public EntrySet() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (obj2 = LocalCache.this.get(key)) != null && LocalCache.this.valueEquivalence.equivalent(entry.getValue(), obj2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(LocalCache.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && LocalCache.this.remove(key, entry.getValue());
        }
    }

    public abstract class HashIterator<T> implements Iterator<T> {

        @NullableDecl
        public Segment<K, V> currentSegment;

        @NullableDecl
        public AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;

        @NullableDecl
        public LocalCache<K, V>.WriteThroughEntry lastReturned;

        @NullableDecl
        public ReferenceEntry<K, V> nextEntry;

        @NullableDecl
        public LocalCache<K, V>.WriteThroughEntry nextExternal;
        public int nextSegmentIndex;
        public int nextTableIndex = -1;

        public HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
            advance();
        }

        public final void advance() {
            this.nextExternal = null;
            if (nextInChain() || nextInTable()) {
                return;
            }
            while (true) {
                int i = this.nextSegmentIndex;
                if (i < 0) {
                    return;
                }
                Segment<K, V>[] segmentArr = LocalCache.this.segments;
                this.nextSegmentIndex = i - 1;
                Segment<K, V> segment = segmentArr[i];
                this.currentSegment = segment;
                if (segment.count != 0) {
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentSegment.table;
                    this.currentTable = atomicReferenceArray;
                    this.nextTableIndex = atomicReferenceArray.length() - 1;
                    if (nextInTable()) {
                        return;
                    }
                }
            }
        }

        public final boolean advanceTo(ReferenceEntry<K, V> referenceEntry) {
            V v;
            boolean z;
            try {
                long j = LocalCache.this.ticker.read();
                K key = referenceEntry.getKey();
                LocalCache localCache = LocalCache.this;
                Objects.requireNonNull(localCache);
                V v2 = null;
                if (referenceEntry.getKey() != null && (v = referenceEntry.getValueReference().get()) != null && !localCache.isExpired(referenceEntry, j)) {
                    v2 = v;
                }
                if (v2 != null) {
                    this.nextExternal = new WriteThroughEntry(key, v2);
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } finally {
                this.currentSegment.postReadCleanup();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.nextExternal != null;
        }

        @Override // java.util.Iterator
        public abstract T next();

        public final LocalCache<K, V>.WriteThroughEntry nextEntry() {
            LocalCache<K, V>.WriteThroughEntry writeThroughEntry = this.nextExternal;
            if (writeThroughEntry == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = writeThroughEntry;
            advance();
            return this.lastReturned;
        }

        public final boolean nextInChain() {
            ReferenceEntry<K, V> referenceEntry = this.nextEntry;
            if (referenceEntry == null) {
                return false;
            }
            while (true) {
                this.nextEntry = referenceEntry.getNext();
                ReferenceEntry<K, V> referenceEntry2 = this.nextEntry;
                if (referenceEntry2 == null) {
                    return false;
                }
                if (advanceTo(referenceEntry2)) {
                    return true;
                }
                referenceEntry = this.nextEntry;
            }
        }

        public final boolean nextInTable() {
            while (true) {
                int i = this.nextTableIndex;
                if (i < 0) {
                    return false;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentTable;
                this.nextTableIndex = i - 1;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(i);
                this.nextEntry = referenceEntry;
                if (referenceEntry != null && (advanceTo(referenceEntry) || nextInChain())) {
                    return true;
                }
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            LocalCache.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    public final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
        public KeyIterator(LocalCache localCache) {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public K next() {
            return nextEntry().getKey();
        }
    }

    public final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
        public KeySet() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return LocalCache.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new KeyIterator(LocalCache.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return LocalCache.this.remove(obj) != null;
        }
    }

    public static final class LoadingSerializationProxy<K, V> extends ManualSerializationProxy<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        @NullableDecl
        public transient LoadingCache<K, V> autoDelegate;

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.autoDelegate = (LoadingCache<K, V>) recreateCacheBuilder().build(this.loader);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k) {
            return this.autoDelegate.apply(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k) throws ExecutionException {
            return this.autoDelegate.get(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.autoDelegate.getAll(iterable);
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k) {
            return this.autoDelegate.getUnchecked(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k) {
            this.autoDelegate.refresh(k);
        }
    }

    public static class LocalLoadingCache<K, V> extends LocalManualCache<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        public LocalLoadingCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super();
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k) {
            return getUnchecked(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k) throws ExecutionException {
            LocalCache<K, V> localCache = this.localCache;
            return localCache.get(k, localCache.defaultLoader);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            LocalCache<K, V> localCache = this.localCache;
            Objects.requireNonNull(localCache);
            LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
            LinkedHashSet linkedHashSetNewLinkedHashSet = Sets.newLinkedHashSet();
            int i = 0;
            int i2 = 0;
            for (K k : iterable) {
                V v = localCache.get(k);
                if (!linkedHashMapNewLinkedHashMap.containsKey(k)) {
                    linkedHashMapNewLinkedHashMap.put(k, v);
                    if (v == null) {
                        i2++;
                        linkedHashSetNewLinkedHashSet.add(k);
                    } else {
                        i++;
                    }
                }
            }
            try {
                if (!linkedHashSetNewLinkedHashSet.isEmpty()) {
                    try {
                        Map<K, V> mapLoadAll = localCache.loadAll(linkedHashSetNewLinkedHashSet, localCache.defaultLoader);
                        for (K k2 : linkedHashSetNewLinkedHashSet) {
                            V v2 = mapLoadAll.get(k2);
                            if (v2 == null) {
                                String strValueOf = String.valueOf(k2);
                                StringBuilder sb = new StringBuilder(strValueOf.length() + 37);
                                sb.append("loadAll failed to return a value for ");
                                sb.append(strValueOf);
                                throw new CacheLoader.InvalidCacheLoadException(sb.toString());
                            }
                            linkedHashMapNewLinkedHashMap.put(k2, v2);
                        }
                    } catch (CacheLoader.UnsupportedLoadingOperationException unused) {
                        for (K k3 : linkedHashSetNewLinkedHashSet) {
                            i2--;
                            linkedHashMapNewLinkedHashMap.put(k3, localCache.get(k3, localCache.defaultLoader));
                        }
                    }
                }
                ImmutableMap<K, V> immutableMapCopyOf = ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
                localCache.globalStatsCounter.recordHits(i);
                localCache.globalStatsCounter.recordMisses(i2);
                return immutableMapCopyOf;
            } catch (Throwable th) {
                localCache.globalStatsCounter.recordHits(i);
                localCache.globalStatsCounter.recordMisses(i2);
                throw th;
            }
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k) {
            try {
                return get(k);
            } catch (ExecutionException e) {
                throw new UncheckedExecutionException(e.getCause());
            }
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k) {
            LocalCache<K, V> localCache = this.localCache;
            Objects.requireNonNull(localCache);
            int iHash = localCache.hash(Preconditions.checkNotNull(k));
            localCache.segmentFor(iHash).refresh(k, iHash, localCache.defaultLoader, false);
        }
    }

    public static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        public final LocalCache<K, V> localCache;

        @Override // com.google.common.cache.Cache
        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        @Override // com.google.common.cache.Cache
        public void cleanUp() {
            this.localCache.cleanUp();
        }

        @Override // com.google.common.cache.Cache
        public V get(K k, final Callable<? extends V> callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return this.localCache.get(k, new CacheLoader<Object, V>() { // from class: com.google.common.cache.LocalCache.LocalManualCache.1
                @Override // com.google.common.cache.CacheLoader
                public V load(Object obj) throws Exception {
                    return (V) callable.call();
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.cache.Cache
        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            LocalCache<K, V> localCache = this.localCache;
            Objects.requireNonNull(localCache);
            LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
            int i = 0;
            int i2 = 0;
            for (Object obj : iterable) {
                V v = localCache.get(obj);
                if (v == null) {
                    i2++;
                } else {
                    linkedHashMapNewLinkedHashMap.put(obj, v);
                    i++;
                }
            }
            localCache.globalStatsCounter.recordHits(i);
            localCache.globalStatsCounter.recordMisses(i2);
            return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
        }

        @Override // com.google.common.cache.Cache
        @NullableDecl
        public V getIfPresent(Object obj) {
            return this.localCache.getIfPresent(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidate(Object obj) {
            Preconditions.checkNotNull(obj);
            this.localCache.remove(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll(Iterable<?> iterable) {
            LocalCache<K, V> localCache = this.localCache;
            Objects.requireNonNull(localCache);
            Iterator<?> it = iterable.iterator();
            while (it.hasNext()) {
                localCache.remove(it.next());
            }
        }

        @Override // com.google.common.cache.Cache
        public void put(K k, V v) {
            this.localCache.put(k, v);
        }

        @Override // com.google.common.cache.Cache
        public void putAll(Map<? extends K, ? extends V> map) {
            this.localCache.putAll(map);
        }

        @Override // com.google.common.cache.Cache
        public long size() {
            return this.localCache.longSize();
        }

        @Override // com.google.common.cache.Cache
        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.globalStatsCounter);
            for (Segment<K, V> segment : this.localCache.segments) {
                simpleStatsCounter.incrementBy(segment.statsCounter);
            }
            return simpleStatsCounter.snapshot();
        }

        public LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache(cacheBuilder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll() {
            this.localCache.clear();
        }
    }

    public static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        public final int concurrencyLevel;

        @NullableDecl
        public transient Cache<K, V> delegate;
        public final long expireAfterAccessNanos;
        public final long expireAfterWriteNanos;
        public final Equivalence<Object> keyEquivalence;
        public final Strength keyStrength;
        public final CacheLoader<? super K, V> loader;
        public final long maxWeight;
        public final RemovalListener<? super K, ? super V> removalListener;

        @NullableDecl
        public final Ticker ticker;
        public final Equivalence<Object> valueEquivalence;
        public final Strength valueStrength;
        public final Weigher<K, V> weigher;

        private ManualSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long j, long j2, long j3, Weigher<K, V> weigher, int i, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.expireAfterWriteNanos = j;
            this.expireAfterAccessNanos = j2;
            this.maxWeight = j3;
            this.weigher = weigher;
            this.concurrencyLevel = i;
            this.removalListener = removalListener;
            this.ticker = (ticker == Ticker.systemTicker() || ticker == CacheBuilder.NULL_TICKER) ? null : ticker;
            this.loader = cacheLoader;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.delegate = (Cache<K, V>) recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public final Cache<K, V> delegate() {
            return this.delegate;
        }

        public final CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<Object, Object> cacheBuilderNewBuilder = CacheBuilder.newBuilder();
            cacheBuilderNewBuilder.setKeyStrength(this.keyStrength);
            cacheBuilderNewBuilder.setValueStrength(this.valueStrength);
            Equivalence<Object> equivalence = this.keyEquivalence;
            Equivalence<Object> equivalence2 = cacheBuilderNewBuilder.keyEquivalence;
            Preconditions.checkState(equivalence2 == null, "key equivalence was already set to %s", equivalence2);
            cacheBuilderNewBuilder.keyEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
            Equivalence<Object> equivalence3 = this.valueEquivalence;
            Equivalence<Object> equivalence4 = cacheBuilderNewBuilder.valueEquivalence;
            Preconditions.checkState(equivalence4 == null, "value equivalence was already set to %s", equivalence4);
            cacheBuilderNewBuilder.valueEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence3);
            CacheBuilder<K, V> cacheBuilder = (CacheBuilder<K, V>) cacheBuilderNewBuilder.concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            cacheBuilder.strictParsing = false;
            long j = this.expireAfterWriteNanos;
            if (j > 0) {
                cacheBuilder.expireAfterWrite(j, TimeUnit.NANOSECONDS);
            }
            long j2 = this.expireAfterAccessNanos;
            if (j2 > 0) {
                cacheBuilder.expireAfterAccess(j2, TimeUnit.NANOSECONDS);
            }
            Weigher weigher = this.weigher;
            if (weigher != CacheBuilder.OneWeigher.INSTANCE) {
                cacheBuilder.weigher(weigher);
                long j3 = this.maxWeight;
                if (j3 != -1) {
                    cacheBuilder.maximumWeight(j3);
                }
            } else {
                long j4 = this.maxWeight;
                if (j4 != -1) {
                    cacheBuilder.maximumSize(j4);
                }
            }
            Ticker ticker = this.ticker;
            if (ticker != null) {
                cacheBuilder.ticker(ticker);
            }
            return cacheBuilder;
        }

        @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public final Object delegate() {
            return this.delegate;
        }
    }

    public enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return 0L;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            return 0;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public Object getKey() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return 0L;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j) {
        }
    }

    public static class Segment<K, V> extends ReentrantLock {

        @GuardedBy("this")
        public final Queue<ReferenceEntry<K, V>> accessQueue;
        public volatile int count;

        @NullableDecl
        public final ReferenceQueue<K> keyReferenceQueue;

        @Weak
        public final LocalCache<K, V> map;
        public final long maxSegmentWeight;
        public int modCount;
        public final AtomicInteger readCount = new AtomicInteger();
        public final Queue<ReferenceEntry<K, V>> recencyQueue;
        public final AbstractCache.StatsCounter statsCounter;

        @NullableDecl
        public volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        public int threshold;

        @GuardedBy("this")
        public long totalWeight;

        @NullableDecl
        public final ReferenceQueue<V> valueReferenceQueue;

        @GuardedBy("this")
        public final Queue<ReferenceEntry<K, V>> writeQueue;

        public Segment(LocalCache<K, V> localCache, int i, long j, AbstractCache.StatsCounter statsCounter) {
            this.map = localCache;
            this.maxSegmentWeight = j;
            this.statsCounter = (AbstractCache.StatsCounter) Preconditions.checkNotNull(statsCounter);
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = new AtomicReferenceArray<>(i);
            int length = (atomicReferenceArray.length() * 3) / 4;
            this.threshold = length;
            if (!(localCache.weigher != CacheBuilder.OneWeigher.INSTANCE) && length == j) {
                this.threshold = length + 1;
            }
            this.table = atomicReferenceArray;
            this.keyReferenceQueue = localCache.usesKeyReferences() ? new ReferenceQueue<>() : null;
            this.valueReferenceQueue = localCache.usesValueReferences() ? new ReferenceQueue<>() : null;
            this.recencyQueue = (AbstractQueue) (localCache.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.DISCARDING_QUEUE);
            this.writeQueue = (AbstractQueue) (localCache.expiresAfterWrite() ? new WriteQueue() : LocalCache.DISCARDING_QUEUE);
            this.accessQueue = (AbstractQueue) (localCache.usesAccessQueue() ? new AccessQueue() : LocalCache.DISCARDING_QUEUE);
        }

        @GuardedBy("this")
        public final ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            V v = valueReference.get();
            if (v == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> referenceEntryCopyEntry = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            referenceEntryCopyEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, v, referenceEntryCopyEntry));
            return referenceEntryCopyEntry;
        }

        @GuardedBy("this")
        public final void drainRecencyQueue() {
            while (true) {
                ReferenceEntry<K, V> referenceEntryPoll = this.recencyQueue.poll();
                if (referenceEntryPoll == null) {
                    return;
                }
                if (this.accessQueue.contains(referenceEntryPoll)) {
                    this.accessQueue.add(referenceEntryPoll);
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:45:0x0111 A[EDGE_INSN: B:45:0x0111->B:46:0x0114 BREAK  A[LOOP:3: B:26:0x00b7->B:41:0x0103]] */
        @GuardedBy("this")
        public final void drainReferenceQueues() {
            int i = 0;
            if (this.map.usesKeyReferences()) {
                int i2 = 0;
                do {
                    Reference<? extends K> referencePoll = this.keyReferenceQueue.poll();
                    if (referencePoll == null) {
                        break;
                    }
                    ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) referencePoll;
                    LocalCache<K, V> localCache = this.map;
                    Objects.requireNonNull(localCache);
                    int hash = referenceEntry.getHash();
                    Segment<K, V> segmentSegmentFor = localCache.segmentFor(hash);
                    segmentSegmentFor.lock();
                    try {
                        AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segmentSegmentFor.table;
                        int length = (atomicReferenceArray.length() - 1) & hash;
                        ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
                        for (ReferenceEntry<K, V> next = referenceEntry2; next != null; next = next.getNext()) {
                            if (next == referenceEntry) {
                                segmentSegmentFor.modCount++;
                                ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = segmentSegmentFor.removeValueFromChain(referenceEntry2, next, next.getKey(), hash, next.getValueReference().get(), next.getValueReference(), RemovalCause.COLLECTED);
                                int i3 = segmentSegmentFor.count - 1;
                                atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                                segmentSegmentFor.count = i3;
                                break;
                            }
                        }
                        segmentSegmentFor.unlock();
                        segmentSegmentFor.runUnlockedCleanup();
                        i2++;
                    } catch (Throwable th) {
                        segmentSegmentFor.unlock();
                        segmentSegmentFor.runUnlockedCleanup();
                        throw th;
                    }
                } while (i2 != 16);
            }
            if (this.map.usesValueReferences()) {
                do {
                    Reference<? extends V> referencePoll2 = this.valueReferenceQueue.poll();
                    if (referencePoll2 == null) {
                        return;
                    }
                    ValueReference<K, V> valueReference = (ValueReference) referencePoll2;
                    LocalCache<K, V> localCache2 = this.map;
                    Objects.requireNonNull(localCache2);
                    ReferenceEntry<K, V> entry = valueReference.getEntry();
                    int hash2 = entry.getHash();
                    Segment<K, V> segmentSegmentFor2 = localCache2.segmentFor(hash2);
                    K key = entry.getKey();
                    segmentSegmentFor2.lock();
                    try {
                        AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray2 = segmentSegmentFor2.table;
                        int length2 = (atomicReferenceArray2.length() - 1) & hash2;
                        ReferenceEntry<K, V> referenceEntry3 = atomicReferenceArray2.get(length2);
                        ReferenceEntry<K, V> next2 = referenceEntry3;
                        while (true) {
                            if (next2 == null) {
                                segmentSegmentFor2.unlock();
                                if (!segmentSegmentFor2.isHeldByCurrentThread()) {
                                    segmentSegmentFor2.runUnlockedCleanup();
                                    break;
                                }
                                break;
                            }
                            K key2 = next2.getKey();
                            if (next2.getHash() == hash2 && key2 != null && segmentSegmentFor2.map.keyEquivalence.equivalent(key, key2)) {
                                if (next2.getValueReference() != valueReference) {
                                    segmentSegmentFor2.unlock();
                                    if (!segmentSegmentFor2.isHeldByCurrentThread()) {
                                        segmentSegmentFor2.runUnlockedCleanup();
                                        break;
                                    }
                                    break;
                                }
                                segmentSegmentFor2.modCount++;
                                ReferenceEntry<K, V> referenceEntryRemoveValueFromChain2 = segmentSegmentFor2.removeValueFromChain(referenceEntry3, next2, key2, hash2, valueReference.get(), valueReference, RemovalCause.COLLECTED);
                                int i4 = segmentSegmentFor2.count - 1;
                                atomicReferenceArray2.set(length2, referenceEntryRemoveValueFromChain2);
                                segmentSegmentFor2.count = i4;
                                segmentSegmentFor2.unlock();
                                if (!segmentSegmentFor2.isHeldByCurrentThread()) {
                                    segmentSegmentFor2.runUnlockedCleanup();
                                    break;
                                }
                                break;
                            }
                            next2 = next2.getNext();
                        }
                        i++;
                    } catch (Throwable th2) {
                        segmentSegmentFor2.unlock();
                        if (!segmentSegmentFor2.isHeldByCurrentThread()) {
                            segmentSegmentFor2.runUnlockedCleanup();
                        }
                        throw th2;
                    }
                } while (i != 16);
            }
        }

        @GuardedBy("this")
        public final void enqueueNotification(@NullableDecl Object obj, @NullableDecl Object obj2, int i, RemovalCause removalCause) {
            this.totalWeight -= (long) i;
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(RemovalNotification.create(obj, obj2, removalCause));
            }
        }

        @GuardedBy("this")
        public final void evictEntries(ReferenceEntry<K, V> referenceEntry) {
            ReferenceEntry<K, V> next;
            if (this.map.evictsBySize()) {
                drainRecencyQueue();
                if (referenceEntry.getValueReference().getWeight() > this.maxSegmentWeight && !removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) {
                    throw new AssertionError();
                }
                while (this.totalWeight > this.maxSegmentWeight) {
                    Iterator<ReferenceEntry<K, V>> it = this.accessQueue.iterator();
                    do {
                        if (!it.hasNext()) {
                            throw new AssertionError();
                        }
                        next = it.next();
                    } while (next.getValueReference().getWeight() <= 0);
                    if (!removeEntry(next, next.getHash(), RemovalCause.SIZE)) {
                        throw new AssertionError();
                    }
                }
            }
        }

        @GuardedBy("this")
        public final void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length >= 1073741824) {
                return;
            }
            int i = this.count;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray2 = new AtomicReferenceArray<>(length << 1);
            this.threshold = (atomicReferenceArray2.length() * 3) / 4;
            int length2 = atomicReferenceArray2.length() - 1;
            for (int i2 = 0; i2 < length; i2++) {
                ReferenceEntry<K, V> next = atomicReferenceArray.get(i2);
                if (next != null) {
                    ReferenceEntry<K, V> next2 = next.getNext();
                    int hash = next.getHash() & length2;
                    if (next2 == null) {
                        atomicReferenceArray2.set(hash, next);
                    } else {
                        ReferenceEntry<K, V> referenceEntry = next;
                        while (next2 != null) {
                            int hash2 = next2.getHash() & length2;
                            if (hash2 != hash) {
                                referenceEntry = next2;
                                hash = hash2;
                            }
                            next2 = next2.getNext();
                        }
                        atomicReferenceArray2.set(hash, referenceEntry);
                        while (next != referenceEntry) {
                            int hash3 = next.getHash() & length2;
                            ReferenceEntry<K, V> referenceEntryCopyEntry = copyEntry(next, atomicReferenceArray2.get(hash3));
                            if (referenceEntryCopyEntry != null) {
                                atomicReferenceArray2.set(hash3, referenceEntryCopyEntry);
                            } else {
                                removeCollectedEntry(next);
                                i--;
                            }
                            next = next.getNext();
                        }
                    }
                }
            }
            this.table = atomicReferenceArray2;
            this.count = i;
        }

        @GuardedBy("this")
        public final void expireEntries(long j) {
            ReferenceEntry<K, V> referenceEntryPeek;
            ReferenceEntry<K, V> referenceEntryPeek2;
            drainRecencyQueue();
            do {
                referenceEntryPeek = this.writeQueue.peek();
                if (referenceEntryPeek == null || !this.map.isExpired(referenceEntryPeek, j)) {
                    do {
                        referenceEntryPeek2 = this.accessQueue.peek();
                        if (referenceEntryPeek2 == null || !this.map.isExpired(referenceEntryPeek2, j)) {
                            return;
                        }
                    } while (removeEntry(referenceEntryPeek2, referenceEntryPeek2.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(referenceEntryPeek, referenceEntryPeek.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        @NullableDecl
        public final V get(Object obj, int i) {
            try {
                if (this.count != 0) {
                    long j = this.map.ticker.read();
                    ReferenceEntry<K, V> liveEntry = getLiveEntry(obj, i, j);
                    if (liveEntry == null) {
                        return null;
                    }
                    V v = liveEntry.getValueReference().get();
                    if (v != null) {
                        recordRead(liveEntry, j);
                        return scheduleRefresh(liveEntry, liveEntry.getKey(), i, v, j, this.map.defaultLoader);
                    }
                    tryDrainReferenceQueues();
                }
                return null;
            } finally {
                postReadCleanup();
            }
        }

        public final V getAndRecordStats(K k, int i, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> listenableFuture) throws Throwable {
            V v;
            try {
                v = (V) Uninterruptibles.getUninterruptibly(listenableFuture);
                try {
                    if (v != null) {
                        this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                        storeLoadedValue(k, i, loadingValueReference, v);
                        return v;
                    }
                    String strValueOf = String.valueOf(k);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 35);
                    sb.append("CacheLoader returned null for key ");
                    sb.append(strValueOf);
                    sb.append(".");
                    throw new CacheLoader.InvalidCacheLoadException(sb.toString());
                } catch (Throwable th) {
                    th = th;
                    if (v == null) {
                        this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                        removeLoadingValue(k, i, loadingValueReference);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                v = null;
            }
        }

        @NullableDecl
        public final ReferenceEntry<K, V> getEntry(Object obj, int i) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            for (ReferenceEntry<K, V> next = atomicReferenceArray.get((atomicReferenceArray.length() - 1) & i); next != null; next = next.getNext()) {
                if (next.getHash() == i) {
                    K key = next.getKey();
                    if (key == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(obj, key)) {
                        return next;
                    }
                }
            }
            return null;
        }

        @NullableDecl
        public final ReferenceEntry<K, V> getLiveEntry(Object obj, int i, long j) {
            ReferenceEntry<K, V> entry = getEntry(obj, i);
            if (entry == null) {
                return null;
            }
            if (!this.map.isExpired(entry, j)) {
                return entry;
            }
            if (tryLock()) {
                try {
                    expireEntries(j);
                } finally {
                    unlock();
                }
            }
            return null;
        }

        public final V getLiveValue(ReferenceEntry<K, V> referenceEntry, long j) {
            if (referenceEntry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V v = referenceEntry.getValueReference().get();
            if (v == null) {
                tryDrainReferenceQueues();
                return null;
            }
            if (!this.map.isExpired(referenceEntry, j)) {
                return v;
            }
            if (tryLock()) {
                try {
                    expireEntries(j);
                } finally {
                    unlock();
                }
            }
            return null;
        }

        public final V lockedGetOrLoad(K k, int i, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            LoadingValueReference<K, V> loadingValueReference;
            ValueReference<K, V> valueReference;
            boolean z;
            V andRecordStats;
            lock();
            try {
                long j = this.map.ticker.read();
                runLockedCleanup(j);
                int i2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry<K, V> referenceEntryNewEntry = referenceEntry;
                while (true) {
                    loadingValueReference = null;
                    if (referenceEntryNewEntry != null) {
                        K key = referenceEntryNewEntry.getKey();
                        if (referenceEntryNewEntry.getHash() == i && key != null && this.map.keyEquivalence.equivalent(k, key)) {
                            valueReference = referenceEntryNewEntry.getValueReference();
                            if (valueReference.isLoading()) {
                                z = false;
                                break;
                            }
                            V v = valueReference.get();
                            if (v == null) {
                                enqueueNotification(key, v, valueReference.getWeight(), RemovalCause.COLLECTED);
                            } else {
                                if (!this.map.isExpired(referenceEntryNewEntry, j)) {
                                    recordLockedRead(referenceEntryNewEntry, j);
                                    this.statsCounter.recordHits(1);
                                    unlock();
                                    runUnlockedCleanup();
                                    return v;
                                }
                                enqueueNotification(key, v, valueReference.getWeight(), RemovalCause.EXPIRED);
                            }
                            this.writeQueue.remove(referenceEntryNewEntry);
                            this.accessQueue.remove(referenceEntryNewEntry);
                            this.count = i2;
                        } else {
                            referenceEntryNewEntry = referenceEntryNewEntry.getNext();
                        }
                    } else {
                        valueReference = null;
                    }
                    z = true;
                    break;
                }
                if (z) {
                    loadingValueReference = new LoadingValueReference<>();
                    if (referenceEntryNewEntry == null) {
                        referenceEntryNewEntry = newEntry(k, i, referenceEntry);
                        referenceEntryNewEntry.setValueReference(loadingValueReference);
                        atomicReferenceArray.set(length, referenceEntryNewEntry);
                    } else {
                        referenceEntryNewEntry.setValueReference(loadingValueReference);
                    }
                }
                unlock();
                runUnlockedCleanup();
                if (!z) {
                    return waitForLoadingValue(referenceEntryNewEntry, k, valueReference);
                }
                try {
                    synchronized (referenceEntryNewEntry) {
                        andRecordStats = getAndRecordStats(k, i, loadingValueReference, loadingValueReference.loadFuture(k, cacheLoader));
                    }
                    this.statsCounter.recordMisses(1);
                    return andRecordStats;
                } catch (Throwable th) {
                    this.statsCounter.recordMisses(1);
                    throw th;
                }
            } catch (Throwable th2) {
                unlock();
                runUnlockedCleanup();
                throw th2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @GuardedBy("this")
        public final ReferenceEntry<K, V> newEntry(K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(k), i, referenceEntry);
        }

        public final void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                runLockedCleanup(this.map.ticker.read());
                runUnlockedCleanup();
            }
        }

        @NullableDecl
        public final V put(K k, int i, V v, boolean z) {
            int i2;
            lock();
            try {
                long j = this.map.ticker.read();
                runLockedCleanup(j);
                if (this.count + 1 > this.threshold) {
                    expand();
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                    K key = next.getKey();
                    if (next.getHash() == i && key != null && this.map.keyEquivalence.equivalent(k, key)) {
                        ValueReference<K, V> valueReference = next.getValueReference();
                        V v2 = valueReference.get();
                        if (v2 != null) {
                            if (z) {
                                recordLockedRead(next, j);
                            } else {
                                this.modCount++;
                                enqueueNotification(k, v2, valueReference.getWeight(), RemovalCause.REPLACED);
                                setValue(next, k, v, j);
                                evictEntries(next);
                            }
                            return v2;
                        }
                        this.modCount++;
                        if (valueReference.isActive()) {
                            enqueueNotification(k, v2, valueReference.getWeight(), RemovalCause.COLLECTED);
                            setValue(next, k, v, j);
                            i2 = this.count;
                        } else {
                            setValue(next, k, v, j);
                            i2 = this.count + 1;
                        }
                        this.count = i2;
                        evictEntries(next);
                        return null;
                    }
                }
                this.modCount++;
                ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k, i, referenceEntry);
                setValue(referenceEntryNewEntry, k, v, j);
                atomicReferenceArray.set(length, referenceEntryNewEntry);
                this.count++;
                evictEntries(referenceEntryNewEntry);
                return null;
            } finally {
                unlock();
                runUnlockedCleanup();
            }
        }

        @GuardedBy("this")
        public final void recordLockedRead(ReferenceEntry<K, V> referenceEntry, long j) {
            if (this.map.expiresAfterAccess()) {
                referenceEntry.setAccessTime(j);
            }
            this.accessQueue.add(referenceEntry);
        }

        public final void recordRead(ReferenceEntry<K, V> referenceEntry, long j) {
            if (this.map.expiresAfterAccess()) {
                referenceEntry.setAccessTime(j);
            }
            this.recencyQueue.add(referenceEntry);
        }

        @NullableDecl
        public final V refresh(final K k, final int i, CacheLoader<? super K, V> cacheLoader, boolean z) {
            LoadingValueReference loadingValueReference;
            final LoadingValueReference loadingValueReference2;
            lock();
            try {
                long j = this.map.ticker.read();
                runLockedCleanup(j);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry<K, V> next = referenceEntry;
                while (true) {
                    if (next != null) {
                        K key = next.getKey();
                        if (next.getHash() == i && key != null && this.map.keyEquivalence.equivalent(k, key)) {
                            ValueReference<K, V> valueReference = next.getValueReference();
                            if (valueReference.isLoading() || (z && j - next.getWriteTime() < this.map.refreshNanos)) {
                                unlock();
                                runUnlockedCleanup();
                                loadingValueReference2 = null;
                                break;
                            }
                            this.modCount++;
                            loadingValueReference = new LoadingValueReference(valueReference);
                            next.setValueReference(loadingValueReference);
                        } else {
                            next = next.getNext();
                        }
                    } else {
                        this.modCount++;
                        loadingValueReference = new LoadingValueReference();
                        ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k, i, referenceEntry);
                        referenceEntryNewEntry.setValueReference(loadingValueReference);
                        atomicReferenceArray.set(length, referenceEntryNewEntry);
                    }
                    unlock();
                    runUnlockedCleanup();
                    loadingValueReference2 = loadingValueReference;
                    break;
                }
                if (loadingValueReference2 == null) {
                    return null;
                }
                final ListenableFuture<V> listenableFutureLoadFuture = loadingValueReference2.loadFuture(k, cacheLoader);
                listenableFutureLoadFuture.addListener(new Runnable() { // from class: com.google.common.cache.LocalCache.Segment.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference fix 'apply assigned field type' failed
                    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
                    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                     */
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Segment.this.getAndRecordStats(k, i, loadingValueReference2, listenableFutureLoadFuture);
                        } catch (Throwable th) {
                            LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", th);
                            loadingValueReference2.setException(th);
                        }
                    }
                }, MoreExecutors.directExecutor());
                if (listenableFutureLoadFuture.isDone()) {
                    try {
                        return (V) Uninterruptibles.getUninterruptibly(listenableFutureLoadFuture);
                    } catch (Throwable unused) {
                    }
                }
                return null;
            } catch (Throwable th) {
                unlock();
                runUnlockedCleanup();
                throw th;
            }
        }

        @GuardedBy("this")
        public final void removeCollectedEntry(ReferenceEntry<K, V> referenceEntry) {
            K key = referenceEntry.getKey();
            referenceEntry.getHash();
            enqueueNotification(key, referenceEntry.getValueReference().get(), referenceEntry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
        }

        @VisibleForTesting
        @GuardedBy("this")
        public final boolean removeEntry(ReferenceEntry<K, V> referenceEntry, int i, RemovalCause removalCause) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = (atomicReferenceArray.length() - 1) & i;
            ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry2; next != null; next = next.getNext()) {
                if (next == referenceEntry) {
                    this.modCount++;
                    ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry2, next, next.getKey(), i, next.getValueReference().get(), next.getValueReference(), removalCause);
                    int i2 = this.count - 1;
                    atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                    this.count = i2;
                    return true;
                }
            }
            return false;
        }

        @NullableDecl
        @GuardedBy("this")
        public final ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int i = this.count;
            ReferenceEntry<K, V> next = referenceEntry2.getNext();
            while (referenceEntry != referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = copyEntry(referenceEntry, next);
                if (referenceEntryCopyEntry != null) {
                    next = referenceEntryCopyEntry;
                } else {
                    removeCollectedEntry(referenceEntry);
                    i--;
                }
                referenceEntry = referenceEntry.getNext();
            }
            this.count = i;
            return next;
        }

        public final boolean removeLoadingValue(K k, int i, LoadingValueReference<K, V> loadingValueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                    K key = next.getKey();
                    if (next.getHash() == i && key != null && this.map.keyEquivalence.equivalent(k, key)) {
                        if (next.getValueReference() != loadingValueReference) {
                            return false;
                        }
                        if (loadingValueReference.isActive()) {
                            next.setValueReference(loadingValueReference.getOldValue());
                        } else {
                            atomicReferenceArray.set(length, removeEntryFromChain(referenceEntry, next));
                        }
                        return true;
                    }
                }
                return false;
            } finally {
                unlock();
                runUnlockedCleanup();
            }
        }

        @NullableDecl
        @GuardedBy("this")
        public final ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, @NullableDecl K k, int i, V v, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            enqueueNotification(k, v, valueReference.getWeight(), removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(referenceEntry, referenceEntry2);
            }
            valueReference.notifyNewValue(null);
            return referenceEntry;
        }

        public final void runLockedCleanup(long j) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(j);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        public final void runUnlockedCleanup() {
            if (isHeldByCurrentThread()) {
                return;
            }
            LocalCache<K, V> localCache = this.map;
            while (true) {
                RemovalNotification<K, V> removalNotificationPoll = localCache.removalNotificationQueue.poll();
                if (removalNotificationPoll == null) {
                    return;
                }
                try {
                    localCache.removalListener.onRemoval(removalNotificationPoll);
                } catch (Throwable th) {
                    LocalCache.logger.log(Level.WARNING, "Exception thrown by removal listener", th);
                }
            }
        }

        public final V scheduleRefresh(ReferenceEntry<K, V> referenceEntry, K k, int i, V v, long j, CacheLoader<? super K, V> cacheLoader) {
            V vRefresh;
            return (!((this.map.refreshNanos > 0L ? 1 : (this.map.refreshNanos == 0L ? 0 : -1)) > 0) || j - referenceEntry.getWriteTime() <= this.map.refreshNanos || referenceEntry.getValueReference().isLoading() || (vRefresh = refresh(k, i, cacheLoader, true)) == null) ? v : vRefresh;
        }

        @GuardedBy("this")
        public final void setValue(ReferenceEntry<K, V> referenceEntry, K k, V v, long j) {
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            int iWeigh = this.map.weigher.weigh(k, v);
            Preconditions.checkState(iWeigh >= 0, "Weights must be non-negative");
            referenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, referenceEntry, v, iWeigh));
            drainRecencyQueue();
            this.totalWeight += (long) iWeigh;
            if (this.map.expiresAfterAccess()) {
                referenceEntry.setAccessTime(j);
            }
            if (this.map.recordsWrite()) {
                referenceEntry.setWriteTime(j);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
            valueReference.notifyNewValue(v);
        }

        public final boolean storeLoadedValue(K k, int i, LoadingValueReference<K, V> loadingValueReference, V v) {
            lock();
            try {
                long j = this.map.ticker.read();
                runLockedCleanup(j);
                int i2 = this.count + 1;
                if (i2 > this.threshold) {
                    expand();
                    i2 = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                    K key = next.getKey();
                    if (next.getHash() == i && key != null && this.map.keyEquivalence.equivalent(k, key)) {
                        ValueReference<K, V> valueReference = next.getValueReference();
                        V v2 = valueReference.get();
                        if (loadingValueReference != valueReference && (v2 != null || valueReference == LocalCache.UNSET)) {
                            enqueueNotification(k, v, 0, RemovalCause.REPLACED);
                            return false;
                        }
                        this.modCount++;
                        if (loadingValueReference.isActive()) {
                            enqueueNotification(k, v2, loadingValueReference.getWeight(), v2 == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                            i2--;
                        }
                        setValue(next, k, v, j);
                        this.count = i2;
                        evictEntries(next);
                        return true;
                    }
                }
                this.modCount++;
                ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k, i, referenceEntry);
                setValue(referenceEntryNewEntry, k, v, j);
                atomicReferenceArray.set(length, referenceEntryNewEntry);
                this.count = i2;
                evictEntries(referenceEntryNewEntry);
                return true;
            } finally {
                unlock();
                runUnlockedCleanup();
            }
        }

        public final void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        public final V waitForLoadingValue(ReferenceEntry<K, V> referenceEntry, K k, ValueReference<K, V> valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(referenceEntry), "Recursive load of: %s", k);
            try {
                V vWaitForValue = valueReference.waitForValue();
                if (vWaitForValue != null) {
                    recordRead(referenceEntry, this.map.ticker.read());
                    this.statsCounter.recordMisses(1);
                    return vWaitForValue;
                }
                String strValueOf = String.valueOf(k);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 35);
                sb.append("CacheLoader returned null for key ");
                sb.append(strValueOf);
                sb.append(".");
                throw new CacheLoader.InvalidCacheLoadException(sb.toString());
            } catch (Throwable th) {
                this.statsCounter.recordMisses(1);
                throw th;
            }
        }
    }

    public static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        public final ReferenceEntry<K, V> entry;

        public SoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference(referenceQueue, v, referenceEntry);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    public enum Strength {
        STRONG { // from class: com.google.common.cache.LocalCache.Strength.1
            @Override // com.google.common.cache.LocalCache.Strength
            public final Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            public final <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                return i == 1 ? new StrongValueReference(v) : new WeightedStrongValueReference(v, i);
            }
        },
        SOFT { // from class: com.google.common.cache.LocalCache.Strength.2
            @Override // com.google.common.cache.LocalCache.Strength
            public final Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            public final <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                return i == 1 ? new SoftValueReference(segment.valueReferenceQueue, v, referenceEntry) : new WeightedSoftValueReference(segment.valueReferenceQueue, v, referenceEntry, i);
            }
        },
        WEAK { // from class: com.google.common.cache.LocalCache.Strength.3
            @Override // com.google.common.cache.LocalCache.Strength
            public final Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            public final <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                return i == 1 ? new WeakValueReference(segment.valueReferenceQueue, v, referenceEntry) : new WeightedWeakValueReference(segment.valueReferenceQueue, v, referenceEntry, i);
            }
        };

        public abstract Equivalence<Object> defaultEquivalence();

        public abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i);
    }

    public static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {
        public volatile long accessTime;

        @Weak
        public ReferenceEntry<K, V> nextAccess;

        @Weak
        public ReferenceEntry<K, V> previousAccess;

        public StrongAccessEntry(K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            NullEntry nullEntry = NullEntry.INSTANCE;
            this.nextAccess = nullEntry;
            this.previousAccess = nullEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    public static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {
        public volatile long accessTime;

        @Weak
        public ReferenceEntry<K, V> nextAccess;

        @Weak
        public ReferenceEntry<K, V> nextWrite;

        @Weak
        public ReferenceEntry<K, V> previousAccess;

        @Weak
        public ReferenceEntry<K, V> previousWrite;
        public volatile long writeTime;

        public StrongAccessWriteEntry(K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            NullEntry nullEntry = NullEntry.INSTANCE;
            this.nextAccess = nullEntry;
            this.previousAccess = nullEntry;
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = nullEntry;
            this.previousWrite = nullEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    public static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {
        public final int hash;
        public final K key;

        @NullableDecl
        public final ReferenceEntry<K, V> next;
        public volatile ValueReference<K, V> valueReference = (ValueReference<K, V>) LocalCache.UNSET;

        public StrongEntry(K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            this.key = k;
            this.hash = i;
            this.next = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public int getHash() {
            return this.hash;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }
    }

    public static class StrongValueReference<K, V> implements ValueReference<K, V> {
        public final V referent;

        public StrongValueReference(V v) {
            this.referent = v;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V get() {
            return this.referent;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    public static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {

        @Weak
        public ReferenceEntry<K, V> nextWrite;

        @Weak
        public ReferenceEntry<K, V> previousWrite;
        public volatile long writeTime;

        public StrongWriteEntry(K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
            this.writeTime = Long.MAX_VALUE;
            NullEntry nullEntry = NullEntry.INSTANCE;
            this.nextWrite = nullEntry;
            this.previousWrite = nullEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    public final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
        public ValueIterator(LocalCache localCache) {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public V next() {
            return nextEntry().getValue();
        }
    }

    public interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @NullableDecl V v, ReferenceEntry<K, V> referenceEntry);

        @NullableDecl
        V get();

        @NullableDecl
        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(@NullableDecl V v);

        V waitForValue() throws ExecutionException;
    }

    public final class Values extends AbstractCollection<V> {
        public Values() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            LocalCache.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return LocalCache.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValueIterator(LocalCache.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return LocalCache.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <E> E[] toArray(E[] eArr) {
            return (E[]) LocalCache.toArrayList(this).toArray(eArr);
        }
    }

    public static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {
        public volatile long accessTime;

        @Weak
        public ReferenceEntry<K, V> nextAccess;

        @Weak
        public ReferenceEntry<K, V> previousAccess;

        public WeakAccessEntry(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            NullEntry nullEntry = NullEntry.INSTANCE;
            this.nextAccess = nullEntry;
            this.previousAccess = nullEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    public static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {
        public volatile long accessTime;

        @Weak
        public ReferenceEntry<K, V> nextAccess;

        @Weak
        public ReferenceEntry<K, V> nextWrite;

        @Weak
        public ReferenceEntry<K, V> previousAccess;

        @Weak
        public ReferenceEntry<K, V> previousWrite;
        public volatile long writeTime;

        public WeakAccessWriteEntry(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            NullEntry nullEntry = NullEntry.INSTANCE;
            this.nextAccess = nullEntry;
            this.previousAccess = nullEntry;
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = nullEntry;
            this.previousWrite = nullEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    public static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
        public final int hash;

        @NullableDecl
        public final ReferenceEntry<K, V> next;
        public volatile ValueReference<K, V> valueReference;

        public WeakEntry(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, referenceQueue);
            this.valueReference = (ValueReference<K, V>) LocalCache.UNSET;
            this.hash = i;
            this.next = referenceEntry;
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            return this.hash;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public K getKey() {
            return get();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long j) {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        public void setWriteTime(long j) {
            throw new UnsupportedOperationException();
        }
    }

    public static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {
        public final ReferenceEntry<K, V> entry;

        public WeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference(referenceQueue, v, referenceEntry);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    public static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {

        @Weak
        public ReferenceEntry<K, V> nextWrite;

        @Weak
        public ReferenceEntry<K, V> previousWrite;
        public volatile long writeTime;

        public WeakWriteEntry(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
            this.writeTime = Long.MAX_VALUE;
            NullEntry nullEntry = NullEntry.INSTANCE;
            this.nextWrite = nullEntry;
            this.previousWrite = nullEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    public static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {
        public final int weight;

        public WeightedSoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int i) {
            super(referenceQueue, v, referenceEntry);
            this.weight = i;
        }

        @Override // com.google.common.cache.LocalCache.SoftValueReference, com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference(referenceQueue, v, referenceEntry, this.weight);
        }

        @Override // com.google.common.cache.LocalCache.SoftValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.weight;
        }
    }

    public static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {
        public final int weight;

        public WeightedStrongValueReference(V v, int i) {
            super(v);
            this.weight = i;
        }

        @Override // com.google.common.cache.LocalCache.StrongValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.weight;
        }
    }

    public static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {
        public final int weight;

        public WeightedWeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int i) {
            super(referenceQueue, v, referenceEntry);
            this.weight = i;
        }

        @Override // com.google.common.cache.LocalCache.WeakValueReference, com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference(referenceQueue, v, referenceEntry, this.weight);
        }

        @Override // com.google.common.cache.LocalCache.WeakValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.weight;
        }
    }

    public static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        public final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>() { // from class: com.google.common.cache.LocalCache.WriteQueue.1

            @Weak
            public ReferenceEntry<K, V> nextWrite = this;

            @Weak
            public ReferenceEntry<K, V> previousWrite = this;

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextWrite = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousWrite = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setWriteTime(long j) {
            }
        };

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.head;
                if (nextInWriteQueue == referenceEntry) {
                    referenceEntry.setNextInWriteQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.head;
                    referenceEntry2.setPreviousInWriteQueue(referenceEntry2);
                    return;
                } else {
                    ReferenceEntry<K, V> nextInWriteQueue2 = nextInWriteQueue.getNextInWriteQueue();
                    LocalCache.nullifyWriteOrder(nextInWriteQueue);
                    nextInWriteQueue = nextInWriteQueue2;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) { // from class: com.google.common.cache.LocalCache.WriteQueue.2
                @Override // com.google.common.collect.AbstractSequentialIterator
                public final Object computeNext(Object obj) {
                    ReferenceEntry<K, V> nextInWriteQueue = ((ReferenceEntry) obj).getNextInWriteQueue();
                    if (nextInWriteQueue == WriteQueue.this.head) {
                        return null;
                    }
                    return nextInWriteQueue;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry<K, V> previousInWriteQueue = referenceEntry.getPreviousInWriteQueue();
            ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previousInWriteQueue, nextInWriteQueue);
            LocalCache.nullifyWriteOrder(referenceEntry);
            return nextInWriteQueue != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i = 0;
            for (ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue(); nextInWriteQueue != this.head; nextInWriteQueue = nextInWriteQueue.getNextInWriteQueue()) {
                i++;
            }
            return i;
        }

        @Override // java.util.Queue
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), referenceEntry);
            ReferenceEntry<K, V> referenceEntry2 = this.head;
            referenceEntry.setNextInWriteQueue(referenceEntry2);
            referenceEntry2.setPreviousInWriteQueue(referenceEntry);
            return true;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            return nextInWriteQueue;
        }

        @Override // java.util.Queue
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            remove(nextInWriteQueue);
            return nextInWriteQueue;
        }
    }

    public final class WriteThroughEntry implements Map.Entry<K, V> {
        public final K key;
        public V value;

        public WriteThroughEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override // java.util.Map.Entry
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = (V) LocalCache.this.put(this.key, v);
            this.value = v;
            return v2;
        }

        public String toString() {
            String strValueOf = String.valueOf(getKey());
            String strValueOf2 = String.valueOf(getValue());
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 1, strValueOf, "=", strValueOf2);
        }
    }

    public LocalCache(CacheBuilder<? super K, ? super V> cacheBuilder, @NullableDecl CacheLoader<? super K, V> cacheLoader) {
        long j;
        int i = cacheBuilder.concurrencyLevel;
        this.concurrencyLevel = Math.min(i == -1 ? 4 : i, 65536);
        Strength strength = cacheBuilder.keyStrength;
        Strength strength2 = Strength.STRONG;
        Strength strength3 = (Strength) MoreObjects.firstNonNull(strength, strength2);
        this.keyStrength = strength3;
        this.valueStrength = (Strength) MoreObjects.firstNonNull(cacheBuilder.valueStrength, strength2);
        this.keyEquivalence = (Equivalence) MoreObjects.firstNonNull(cacheBuilder.keyEquivalence, ((Strength) MoreObjects.firstNonNull(cacheBuilder.keyStrength, strength2)).defaultEquivalence());
        this.valueEquivalence = (Equivalence) MoreObjects.firstNonNull(cacheBuilder.valueEquivalence, ((Strength) MoreObjects.firstNonNull(cacheBuilder.valueStrength, strength2)).defaultEquivalence());
        if (cacheBuilder.expireAfterWriteNanos == 0 || cacheBuilder.expireAfterAccessNanos == 0) {
            j = 0;
        } else {
            j = cacheBuilder.weigher == null ? cacheBuilder.maximumSize : cacheBuilder.maximumWeight;
        }
        this.maxWeight = j;
        Weigher<? super Object, ? super Object> weigher = cacheBuilder.weigher;
        CacheBuilder.OneWeigher oneWeigher = CacheBuilder.OneWeigher.INSTANCE;
        Weigher<K, V> weigher2 = (Weigher) MoreObjects.firstNonNull(weigher, oneWeigher);
        this.weigher = weigher2;
        long j2 = cacheBuilder.expireAfterAccessNanos;
        this.expireAfterAccessNanos = j2 == -1 ? 0L : j2;
        long j3 = cacheBuilder.expireAfterWriteNanos;
        this.expireAfterWriteNanos = j3 == -1 ? 0L : j3;
        long j4 = cacheBuilder.refreshNanos;
        this.refreshNanos = j4 != -1 ? j4 : 0L;
        RemovalListener<? super Object, ? super Object> removalListener = cacheBuilder.removalListener;
        CacheBuilder.NullListener nullListener = CacheBuilder.NullListener.INSTANCE;
        RemovalListener<K, V> removalListener2 = (RemovalListener) MoreObjects.firstNonNull(removalListener, nullListener);
        this.removalListener = removalListener2;
        this.removalNotificationQueue = (AbstractQueue) (removalListener2 == nullListener ? DISCARDING_QUEUE : new ConcurrentLinkedQueue());
        int i2 = 0;
        int i3 = 1;
        boolean z = recordsWrite() || expiresAfterAccess();
        Ticker tickerSystemTicker = cacheBuilder.ticker;
        if (tickerSystemTicker == null) {
            tickerSystemTicker = z ? Ticker.systemTicker() : CacheBuilder.NULL_TICKER;
        }
        this.ticker = tickerSystemTicker;
        char c = (usesAccessQueue() || expiresAfterAccess()) ? (char) 1 : (char) 0;
        boolean z2 = expiresAfterWrite() || recordsWrite();
        EntryFactory entryFactory = EntryFactory.STRONG;
        this.entryFactory = EntryFactory.factories[(strength3 != Strength.WEAK ? (char) 0 : (char) 4) | c | (z2 ? 2 : 0)];
        this.globalStatsCounter = cacheBuilder.statsCounterSupplier.get();
        this.defaultLoader = cacheLoader;
        int i4 = cacheBuilder.initialCapacity;
        int iMin = Math.min(i4 == -1 ? 16 : i4, 1073741824);
        if (evictsBySize()) {
            if (!(weigher2 != oneWeigher)) {
                iMin = (int) Math.min(iMin, j);
            }
        }
        int i5 = 1;
        int i6 = 0;
        while (i5 < this.concurrencyLevel && (!evictsBySize() || i5 * 20 <= this.maxWeight)) {
            i6++;
            i5 <<= 1;
        }
        this.segmentShift = 32 - i6;
        this.segmentMask = i5 - 1;
        this.segments = new Segment[i5];
        int i7 = iMin / i5;
        while (i3 < (i7 * i5 < iMin ? i7 + 1 : i7)) {
            i3 <<= 1;
        }
        if (evictsBySize()) {
            long j5 = this.maxWeight;
            long j6 = i5;
            long j7 = (j5 / j6) + 1;
            long j8 = j5 % j6;
            while (true) {
                Segment<K, V>[] segmentArr = this.segments;
                if (i2 >= segmentArr.length) {
                    return;
                }
                if (i2 == j8) {
                    j7--;
                }
                long j9 = j7;
                segmentArr[i2] = new Segment<>(this, i3, j9, cacheBuilder.statsCounterSupplier.get());
                i2++;
                j7 = j9;
            }
        } else {
            while (true) {
                Segment<K, V>[] segmentArr2 = this.segments;
                if (i2 >= segmentArr2.length) {
                    return;
                }
                segmentArr2[i2] = new Segment<>(this, i3, -1L, cacheBuilder.statsCounterSupplier.get());
                i2++;
            }
        }
    }

    public static <K, V> void connectAccessOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry2.setPreviousInAccessQueue(referenceEntry);
    }

    public static <K, V> void connectWriteOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry2.setPreviousInWriteQueue(referenceEntry);
    }

    public static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> referenceEntry) {
        NullEntry nullEntry = NullEntry.INSTANCE;
        referenceEntry.setNextInAccessQueue(nullEntry);
        referenceEntry.setPreviousInAccessQueue(nullEntry);
    }

    public static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> referenceEntry) {
        NullEntry nullEntry = NullEntry.INSTANCE;
        referenceEntry.setNextInWriteQueue(nullEntry);
        referenceEntry.setPreviousInWriteQueue(nullEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    public void cleanUp() {
        for (Segment<K, V> segment : this.segments) {
            segment.runLockedCleanup(segment.map.ticker.read());
            segment.runUnlockedCleanup();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            if (segment.count != 0) {
                segment.lock();
                try {
                    segment.runLockedCleanup(segment.map.ticker.read());
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segment.table;
                    for (int i = 0; i < atomicReferenceArray.length(); i++) {
                        for (ReferenceEntry<K, V> next = atomicReferenceArray.get(i); next != null; next = next.getNext()) {
                            if (next.getValueReference().isActive()) {
                                K key = next.getKey();
                                V v = next.getValueReference().get();
                                RemovalCause removalCause = (key == null || v == null) ? RemovalCause.COLLECTED : RemovalCause.EXPLICIT;
                                next.getHash();
                                segment.enqueueNotification(key, v, next.getValueReference().getWeight(), removalCause);
                            }
                        }
                    }
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        atomicReferenceArray.set(i2, null);
                    }
                    if (segment.map.usesKeyReferences()) {
                        while (segment.keyReferenceQueue.poll() != null) {
                        }
                    }
                    if (segment.map.usesValueReferences()) {
                        while (segment.valueReferenceQueue.poll() != null) {
                        }
                    }
                    segment.writeQueue.clear();
                    segment.accessQueue.clear();
                    segment.readCount.set(0);
                    segment.modCount++;
                    segment.count = 0;
                    segment.unlock();
                    segment.runUnlockedCleanup();
                } catch (Throwable th) {
                    segment.unlock();
                    segment.runUnlockedCleanup();
                    throw th;
                }
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        ReferenceEntry<K, V> liveEntry;
        boolean z = false;
        if (obj == null) {
            return false;
        }
        int iHash = hash(obj);
        Segment<K, V> segmentSegmentFor = segmentFor(iHash);
        Objects.requireNonNull(segmentSegmentFor);
        try {
            if (segmentSegmentFor.count != 0 && (liveEntry = segmentSegmentFor.getLiveEntry(obj, iHash, segmentSegmentFor.map.ticker.read())) != null && liveEntry.getValueReference().get() != null) {
                z = true;
            }
            return z;
        } finally {
            segmentSegmentFor.postReadCleanup();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        long j = this.ticker.read();
        Segment<K, V>[] segmentArr = this.segments;
        long j2 = -1;
        int i = 0;
        while (i < 3) {
            long j3 = 0;
            int length = segmentArr.length;
            int i2 = 0;
            while (i2 < length) {
                Segment<K, V> segment = segmentArr[i2];
                int i3 = segment.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segment.table;
                for (int i4 = 0; i4 < atomicReferenceArray.length(); i4++) {
                    ReferenceEntry<K, V> next = atomicReferenceArray.get(i4);
                    while (next != null) {
                        Segment<K, V>[] segmentArr2 = segmentArr;
                        V liveValue = segment.getLiveValue(next, j);
                        long j4 = j;
                        if (liveValue != null && this.valueEquivalence.equivalent(obj, liveValue)) {
                            return true;
                        }
                        next = next.getNext();
                        segmentArr = segmentArr2;
                        j = j4;
                    }
                }
                j3 += (long) segment.modCount;
                i2++;
                j = j;
            }
            long j5 = j;
            Segment<K, V>[] segmentArr3 = segmentArr;
            if (j3 == j2) {
                return false;
            }
            i++;
            j2 = j3;
            segmentArr = segmentArr3;
            j = j5;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @GwtIncompatible
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    public final boolean evictsBySize() {
        return this.maxWeight >= 0;
    }

    public final boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0;
    }

    public final boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).get(obj, iHash);
    }

    @NullableDecl
    public V getIfPresent(Object obj) {
        int iHash = hash(Preconditions.checkNotNull(obj));
        V v = segmentFor(iHash).get(obj, iHash);
        if (v == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return v;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @NullableDecl
    public V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    public final int hash(@NullableDecl Object obj) {
        int iHash = this.keyEquivalence.hash(obj);
        int i = iHash + ((iHash << 15) ^ (-12931));
        int i2 = i ^ (i >>> 10);
        int i3 = i2 + (i2 << 3);
        int i4 = i3 ^ (i3 >>> 6);
        int i5 = (i4 << 2) + (i4 << 14) + i4;
        return (i5 >>> 16) ^ i5;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        Segment<K, V>[] segmentArr = this.segments;
        long j = 0;
        for (int i = 0; i < segmentArr.length; i++) {
            if (segmentArr[i].count != 0) {
                return false;
            }
            j += (long) segmentArr[i].modCount;
        }
        if (j == 0) {
            return true;
        }
        for (int i2 = 0; i2 < segmentArr.length; i2++) {
            if (segmentArr[i2].count != 0) {
                return false;
            }
            j -= (long) segmentArr[i2].modCount;
        }
        return j == 0;
    }

    public final boolean isExpired(ReferenceEntry<K, V> referenceEntry, long j) {
        Preconditions.checkNotNull(referenceEntry);
        if (!expiresAfterAccess() || j - referenceEntry.getAccessTime() < this.expireAfterAccessNanos) {
            return expiresAfterWrite() && j - referenceEntry.getWriteTime() >= this.expireAfterWriteNanos;
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.keySet = keySet;
        return keySet;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00b6  */
    @NullableDecl
    public final Map<K, V> loadAll(Set<? extends K> set, CacheLoader<? super K, V> cacheLoader) throws Throwable {
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(set);
        Stopwatch stopwatchCreateStarted = Stopwatch.createStarted();
        boolean z = false;
        try {
            try {
                try {
                    try {
                        Map<? super K, V> mapLoadAll = cacheLoader.loadAll(set);
                        if (mapLoadAll == null) {
                            this.globalStatsCounter.recordLoadException(stopwatchCreateStarted.elapsed(TimeUnit.NANOSECONDS));
                            String strValueOf = String.valueOf(cacheLoader);
                            throw new CacheLoader.InvalidCacheLoadException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 31, strValueOf, " returned null map from loadAll"));
                        }
                        stopwatchCreateStarted.stop();
                        for (Map.Entry<K, V> entry : mapLoadAll.entrySet()) {
                            K key = entry.getKey();
                            V value = entry.getValue();
                            if (key == null || value == null) {
                                z = true;
                            } else {
                                put(key, value);
                            }
                        }
                        if (!z) {
                            this.globalStatsCounter.recordLoadSuccess(stopwatchCreateStarted.elapsed(TimeUnit.NANOSECONDS));
                            return mapLoadAll;
                        }
                        this.globalStatsCounter.recordLoadException(stopwatchCreateStarted.elapsed(TimeUnit.NANOSECONDS));
                        String strValueOf2 = String.valueOf(cacheLoader);
                        throw new CacheLoader.InvalidCacheLoadException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + 42, strValueOf2, " returned null keys or values from loadAll"));
                    } catch (CacheLoader.UnsupportedLoadingOperationException e) {
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            z = true;
                            if (!z) {
                                this.globalStatsCounter.recordLoadException(stopwatchCreateStarted.elapsed(TimeUnit.NANOSECONDS));
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        throw new ExecutionException(e2);
                    }
                } catch (Error e3) {
                    throw new ExecutionError(e3);
                } catch (InterruptedException e4) {
                    Thread.currentThread().interrupt();
                    throw new ExecutionException(e4);
                }
            } catch (RuntimeException e5) {
                throw new UncheckedExecutionException(e5);
            }
        } catch (Throwable th2) {
            th = th2;
            if (!z) {
                this.globalStatsCounter.recordLoadException(stopwatchCreateStarted.elapsed(TimeUnit.NANOSECONDS));
            }
            throw th;
        }
    }

    public final long longSize() {
        long jMax = 0;
        for (Segment<K, V> segment : this.segments) {
            jMax += (long) Math.max(0, segment.count);
        }
        return jMax;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int iHash = hash(k);
        return segmentFor(iHash).put(k, iHash, v, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V putIfAbsent(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int iHash = hash(k);
        return segmentFor(iHash).put(k, iHash, v, true);
    }

    public final boolean recordsWrite() {
        if (!expiresAfterWrite()) {
            if (!(this.refreshNanos > 0)) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(@NullableDecl Object obj) {
        RemovalCause removalCause;
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        Segment<K, V> segmentSegmentFor = segmentFor(iHash);
        segmentSegmentFor.lock();
        try {
            segmentSegmentFor.runLockedCleanup(segmentSegmentFor.map.ticker.read());
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segmentSegmentFor.table;
            int length = (atomicReferenceArray.length() - 1) & iHash;
            ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                K key = next.getKey();
                if (next.getHash() == iHash && key != null && segmentSegmentFor.map.keyEquivalence.equivalent(obj, key)) {
                    ValueReference<K, V> valueReference = next.getValueReference();
                    V v = valueReference.get();
                    if (v == null) {
                        if (!valueReference.isActive()) {
                            break;
                        }
                        removalCause = RemovalCause.COLLECTED;
                    } else {
                        removalCause = RemovalCause.EXPLICIT;
                    }
                    RemovalCause removalCause2 = removalCause;
                    segmentSegmentFor.modCount++;
                    ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = segmentSegmentFor.removeValueFromChain(referenceEntry, next, key, iHash, v, valueReference, removalCause2);
                    int i = segmentSegmentFor.count - 1;
                    atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                    segmentSegmentFor.count = i;
                    return v;
                }
            }
            return null;
        } finally {
            segmentSegmentFor.unlock();
            segmentSegmentFor.runUnlockedCleanup();
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(K k, @NullableDecl V v, V v2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v2);
        if (v == null) {
            return false;
        }
        int iHash = hash(k);
        Segment<K, V> segmentSegmentFor = segmentFor(iHash);
        segmentSegmentFor.lock();
        try {
            long j = segmentSegmentFor.map.ticker.read();
            segmentSegmentFor.runLockedCleanup(j);
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segmentSegmentFor.table;
            int length = iHash & (atomicReferenceArray.length() - 1);
            ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                K key = next.getKey();
                if (next.getHash() == iHash && key != null && segmentSegmentFor.map.keyEquivalence.equivalent(k, key)) {
                    ValueReference<K, V> valueReference = next.getValueReference();
                    V v3 = valueReference.get();
                    if (v3 != null) {
                        if (!segmentSegmentFor.map.valueEquivalence.equivalent(v, v3)) {
                            segmentSegmentFor.recordLockedRead(next, j);
                            break;
                        }
                        segmentSegmentFor.modCount++;
                        segmentSegmentFor.enqueueNotification(k, v3, valueReference.getWeight(), RemovalCause.REPLACED);
                        segmentSegmentFor.setValue(next, k, v2, j);
                        segmentSegmentFor.evictEntries(next);
                        return true;
                    }
                    if (!valueReference.isActive()) {
                        break;
                    }
                    segmentSegmentFor.modCount++;
                    ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = segmentSegmentFor.removeValueFromChain(referenceEntry, next, key, iHash, v3, valueReference, RemovalCause.COLLECTED);
                    int i = segmentSegmentFor.count - 1;
                    atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                    segmentSegmentFor.count = i;
                    break;
                }
            }
            return false;
        } finally {
            segmentSegmentFor.unlock();
            segmentSegmentFor.runUnlockedCleanup();
        }
    }

    public final Segment<K, V> segmentFor(int i) {
        return this.segments[(i >>> this.segmentShift) & this.segmentMask];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return Ints.saturatedCast(longSize());
    }

    public final boolean usesAccessQueue() {
        return expiresAfterAccess() || evictsBySize();
    }

    public final boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    public final boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.values;
        if (collection != null) {
            return collection;
        }
        Values values = new Values();
        this.values = values;
        return values;
    }

    public static class LoadingValueReference<K, V> implements ValueReference<K, V> {
        public final SettableFuture<V> futureValue;
        public volatile ValueReference<K, V> oldValue;
        public final Stopwatch stopwatch;

        public LoadingValueReference() {
            this(LocalCache.UNSET);
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable th) {
            return Futures.immediateFailedFuture(th);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @NullableDecl V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V get() {
            return this.oldValue.get();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.oldValue.getWeight();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return this.oldValue.isActive();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return true;
        }

        public ListenableFuture<V> loadFuture(K k, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.stopwatch.start();
                V v = this.oldValue.get();
                if (v == null) {
                    V vLoad = cacheLoader.load(k);
                    return set(vLoad) ? this.futureValue : Futures.immediateFuture(vLoad);
                }
                ListenableFuture<V> listenableFutureReload = cacheLoader.reload(k, v);
                return listenableFutureReload == null ? Futures.immediateFuture(null) : Futures.transform(listenableFutureReload, new Function<V, V>() { // from class: com.google.common.cache.LocalCache.LoadingValueReference.1
                    @Override // com.google.common.base.Function
                    public V apply(V v2) {
                        LoadingValueReference.this.set(v2);
                        return v2;
                    }
                }, MoreExecutors.directExecutor());
            } catch (Throwable th) {
                ListenableFuture<V> listenableFutureFullyFailedFuture = setException(th) ? this.futureValue : fullyFailedFuture(th);
                if (th instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return listenableFutureFullyFailedFuture;
            }
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(@NullableDecl V v) {
            if (v != null) {
                set(v);
            } else {
                this.oldValue = (ValueReference<K, V>) LocalCache.UNSET;
            }
        }

        public boolean set(@NullableDecl V v) {
            return this.futureValue.set(v);
        }

        public boolean setException(Throwable th) {
            return this.futureValue.setException(th);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() throws ExecutionException {
            return (V) Uninterruptibles.getUninterruptibly(this.futureValue);
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.futureValue = SettableFuture.create();
            this.stopwatch = Stopwatch.createUnstarted();
            this.oldValue = valueReference;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x004d A[Catch: all -> 0x0055, ExecutionException -> 0x0057, TRY_LEAVE, TryCatch #0 {ExecutionException -> 0x0057, blocks: (B:3:0x0015, B:5:0x0019, B:7:0x001f, B:9:0x002d, B:10:0x003e, B:12:0x0048, B:13:0x004d), top: B:30:0x0015, outer: #1 }] */
    public final V get(K k, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        V vLockedGetOrLoad;
        ReferenceEntry<K, V> entry;
        int iHash = hash(Preconditions.checkNotNull(k));
        Segment<K, V> segmentSegmentFor = segmentFor(iHash);
        Objects.requireNonNull(segmentSegmentFor);
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(cacheLoader);
        try {
            try {
                if (segmentSegmentFor.count != 0 && (entry = segmentSegmentFor.getEntry(k, iHash)) != null) {
                    long j = segmentSegmentFor.map.ticker.read();
                    V liveValue = segmentSegmentFor.getLiveValue(entry, j);
                    if (liveValue != null) {
                        segmentSegmentFor.recordRead(entry, j);
                        segmentSegmentFor.statsCounter.recordHits(1);
                        vLockedGetOrLoad = segmentSegmentFor.scheduleRefresh(entry, k, iHash, liveValue, j, cacheLoader);
                    } else {
                        ValueReference<K, V> valueReference = entry.getValueReference();
                        if (valueReference.isLoading()) {
                            vLockedGetOrLoad = segmentSegmentFor.waitForLoadingValue(entry, k, valueReference);
                        } else {
                            vLockedGetOrLoad = segmentSegmentFor.lockedGetOrLoad(k, iHash, cacheLoader);
                        }
                    }
                } else {
                    vLockedGetOrLoad = segmentSegmentFor.lockedGetOrLoad(k, iHash, cacheLoader);
                }
                segmentSegmentFor.postReadCleanup();
                return vLockedGetOrLoad;
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof Error)) {
                    if (cause instanceof RuntimeException) {
                        throw new UncheckedExecutionException(cause);
                    }
                    throw e;
                }
                throw new ExecutionError((Error) cause);
            }
        } catch (Throwable th) {
            segmentSegmentFor.postReadCleanup();
            throw th;
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        RemovalCause removalCause;
        boolean z = false;
        if (obj == null || obj2 == null) {
            return false;
        }
        int iHash = hash(obj);
        Segment<K, V> segmentSegmentFor = segmentFor(iHash);
        segmentSegmentFor.lock();
        try {
            segmentSegmentFor.runLockedCleanup(segmentSegmentFor.map.ticker.read());
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segmentSegmentFor.table;
            int length = (atomicReferenceArray.length() - 1) & iHash;
            ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                K key = next.getKey();
                if (next.getHash() == iHash && key != null && segmentSegmentFor.map.keyEquivalence.equivalent(obj, key)) {
                    ValueReference<K, V> valueReference = next.getValueReference();
                    V v = valueReference.get();
                    if (segmentSegmentFor.map.valueEquivalence.equivalent(obj2, v)) {
                        removalCause = RemovalCause.EXPLICIT;
                    } else {
                        if (v != null || !valueReference.isActive()) {
                            break;
                            break;
                        }
                        removalCause = RemovalCause.COLLECTED;
                    }
                    segmentSegmentFor.modCount++;
                    ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = segmentSegmentFor.removeValueFromChain(referenceEntry, next, key, iHash, v, valueReference, removalCause);
                    int i = segmentSegmentFor.count - 1;
                    atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                    segmentSegmentFor.count = i;
                    if (removalCause != RemovalCause.EXPLICIT) {
                        break;
                    }
                    z = true;
                    break;
                }
            }
            return z;
        } finally {
            segmentSegmentFor.unlock();
            segmentSegmentFor.runUnlockedCleanup();
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V replace(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int iHash = hash(k);
        Segment<K, V> segmentSegmentFor = segmentFor(iHash);
        segmentSegmentFor.lock();
        try {
            long j = segmentSegmentFor.map.ticker.read();
            segmentSegmentFor.runLockedCleanup(j);
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segmentSegmentFor.table;
            int length = iHash & (atomicReferenceArray.length() - 1);
            ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                K key = next.getKey();
                if (next.getHash() == iHash && key != null && segmentSegmentFor.map.keyEquivalence.equivalent(k, key)) {
                    ValueReference<K, V> valueReference = next.getValueReference();
                    V v2 = valueReference.get();
                    if (v2 == null) {
                        if (!valueReference.isActive()) {
                            break;
                        }
                        segmentSegmentFor.modCount++;
                        ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = segmentSegmentFor.removeValueFromChain(referenceEntry, next, key, iHash, v2, valueReference, RemovalCause.COLLECTED);
                        int i = segmentSegmentFor.count - 1;
                        atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                        segmentSegmentFor.count = i;
                        break;
                    }
                    segmentSegmentFor.modCount++;
                    segmentSegmentFor.enqueueNotification(k, v2, valueReference.getWeight(), RemovalCause.REPLACED);
                    segmentSegmentFor.setValue(next, k, v, j);
                    segmentSegmentFor.evictEntries(next);
                    return v2;
                }
            }
            return null;
        } finally {
            segmentSegmentFor.unlock();
            segmentSegmentFor.runUnlockedCleanup();
        }
    }
}
