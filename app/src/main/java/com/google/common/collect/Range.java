package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public final class Range<C extends Comparable> extends RangeGwtSerializationDependencies implements Predicate<C> {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final Range<Comparable> ALL = new Range<>(Cut.BelowAll.INSTANCE, Cut.AboveAll.INSTANCE);
    private static final long serialVersionUID = 0;
    public final Cut<C> lowerBound;
    public final Cut<C> upperBound;

    /* JADX INFO: renamed from: com.google.common.collect.Range$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType;

        static {
            int[] iArr = new int[BoundType.values().length];
            $SwitchMap$com$google$common$collect$BoundType = iArr;
            try {
                iArr[BoundType.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class LowerBoundFn implements Function<Range, Cut> {
        public static final LowerBoundFn INSTANCE = new LowerBoundFn();

        @Override // com.google.common.base.Function
        public Cut apply(Range range) {
            return range.lowerBound;
        }
    }

    public static class RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
        public static final Ordering<Range<?>> INSTANCE = new RangeLexOrdering();
        private static final long serialVersionUID = 0;

        private RangeLexOrdering() {
        }

        @Override // com.google.common.collect.Ordering, java.util.Comparator
        public int compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare(range.lowerBound, range2.lowerBound).compare(range.upperBound, range2.upperBound).result();
        }
    }

    public static class UpperBoundFn implements Function<Range, Cut> {
        public static final UpperBoundFn INSTANCE = new UpperBoundFn();

        @Override // com.google.common.base.Function
        public Cut apply(Range range) {
            return range.upperBound;
        }
    }

    private Range(Cut<C> cut, Cut<C> cut2) {
        this.lowerBound = (Cut) Preconditions.checkNotNull(cut);
        this.upperBound = (Cut) Preconditions.checkNotNull(cut2);
        if (cut.compareTo((Cut) cut2) > 0 || cut == Cut.AboveAll.INSTANCE || cut2 == Cut.BelowAll.INSTANCE) {
            String strValueOf = String.valueOf(toString(cut, cut2));
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid range: ".concat(strValueOf) : new String("Invalid range: "));
        }
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return (Range<C>) ALL;
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c) {
        return new Range<>(new Cut.BelowValue(c), Cut.AboveAll.INSTANCE);
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c) {
        return new Range<>(Cut.BelowAll.INSTANCE, new Cut.AboveValue(c));
    }

    public static <C extends Comparable<?>> Range<C> closed(C c, C c2) {
        return new Range<>(new Cut.BelowValue(c), new Cut.AboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C c, C c2) {
        return new Range<>(new Cut.BelowValue(c), new Cut.BelowValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> create(Cut<C> cut, Cut<C> cut2) {
        return new Range<>(cut, cut2);
    }

    public static <C extends Comparable<?>> Range<C> downTo(C c, BoundType boundType) {
        int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
        if (i == 1) {
            return greaterThan(c);
        }
        if (i == 2) {
            return atLeast(c);
        }
        throw new AssertionError();
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) iterable;
            Comparator comparator = sortedSet.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return closed((Comparable) sortedSet.first(), (Comparable) sortedSet.last());
            }
        }
        Iterator<C> it = iterable.iterator();
        Comparable comparable = (Comparable) Preconditions.checkNotNull(it.next());
        Comparable comparable2 = comparable;
        while (it.hasNext()) {
            Comparable comparable3 = (Comparable) Preconditions.checkNotNull(it.next());
            comparable = (Comparable) Ordering.natural().min(comparable, comparable3);
            comparable2 = (Comparable) Ordering.natural().max(comparable2, comparable3);
        }
        return closed(comparable, comparable2);
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C c) {
        return new Range<>(new Cut.AboveValue(c), Cut.AboveAll.INSTANCE);
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C c) {
        return new Range<>(Cut.BelowAll.INSTANCE, new Cut.BelowValue(c));
    }

    public static <C extends Comparable<?>> Range<C> open(C c, C c2) {
        return new Range<>(new Cut.AboveValue(c), new Cut.BelowValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c, C c2) {
        return new Range<>(new Cut.AboveValue(c), new Cut.AboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> range(C c, BoundType boundType, C c2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        BoundType boundType3 = BoundType.OPEN;
        return new Range<>(boundType == boundType3 ? new Cut.AboveValue(c) : new Cut.BelowValue(c), boundType2 == boundType3 ? new Cut.BelowValue(c2) : new Cut.AboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> singleton(C c) {
        return closed(c, c);
    }

    public static <C extends Comparable<?>> Range<C> upTo(C c, BoundType boundType) {
        int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
        if (i == 1) {
            return lessThan(c);
        }
        if (i == 2) {
            return atMost(c);
        }
        throw new AssertionError();
    }

    public Range<C> canonical(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        Cut<C> cutCanonical = this.lowerBound.canonical(discreteDomain);
        Cut<C> cutCanonical2 = this.upperBound.canonical(discreteDomain);
        return (cutCanonical == this.lowerBound && cutCanonical2 == this.upperBound) ? this : new Range<>(cutCanonical, cutCanonical2);
    }

    public boolean contains(C c) {
        Preconditions.checkNotNull(c);
        return this.lowerBound.isLessThan(c) && !this.upperBound.isLessThan(c);
    }

    public boolean containsAll(Iterable<? extends C> iterable) {
        if (Iterables.isEmpty(iterable)) {
            return true;
        }
        if (iterable instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) iterable;
            Comparator comparator = sortedSet.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return contains((Comparable) sortedSet.first()) && contains((Comparable) sortedSet.last());
            }
        }
        Iterator<? extends C> it = iterable.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean encloses(Range<C> range) {
        return this.lowerBound.compareTo((Cut) range.lowerBound) <= 0 && this.upperBound.compareTo((Cut) range.upperBound) >= 0;
    }

    @Override // com.google.common.base.Predicate
    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof Range)) {
            return false;
        }
        Range range = (Range) obj;
        return this.lowerBound.equals(range.lowerBound) && this.upperBound.equals(range.upperBound);
    }

    public Range<C> gap(Range<C> range) {
        if (this.lowerBound.compareTo((Cut) range.upperBound) < 0 && range.lowerBound.compareTo((Cut) this.upperBound) < 0) {
            String strValueOf = String.valueOf(this);
            String strValueOf2 = String.valueOf(range);
            throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 39, "Ranges have a nonempty intersection: ", strValueOf, ", ", strValueOf2));
        }
        boolean z = this.lowerBound.compareTo((Cut) range.lowerBound) < 0;
        Range<C> range2 = z ? this : range;
        if (!z) {
            range = this;
        }
        return new Range<>(range2.upperBound, range.lowerBound);
    }

    public boolean hasLowerBound() {
        return this.lowerBound != Cut.BelowAll.INSTANCE;
    }

    public boolean hasUpperBound() {
        return this.upperBound != Cut.AboveAll.INSTANCE;
    }

    public int hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode();
    }

    public Range<C> intersection(Range<C> range) {
        int iCompareTo = this.lowerBound.compareTo((Cut) range.lowerBound);
        int iCompareTo2 = this.upperBound.compareTo((Cut) range.upperBound);
        if (iCompareTo >= 0 && iCompareTo2 <= 0) {
            return this;
        }
        if (iCompareTo > 0 || iCompareTo2 < 0) {
            return new Range<>(iCompareTo >= 0 ? this.lowerBound : range.lowerBound, iCompareTo2 <= 0 ? this.upperBound : range.upperBound);
        }
        return range;
    }

    public boolean isConnected(Range<C> range) {
        return this.lowerBound.compareTo((Cut) range.upperBound) <= 0 && range.lowerBound.compareTo((Cut) this.upperBound) <= 0;
    }

    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }

    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }

    public C lowerEndpoint() {
        return (C) this.lowerBound.endpoint();
    }

    public Range<C> span(Range<C> range) {
        int iCompareTo = this.lowerBound.compareTo((Cut) range.lowerBound);
        int iCompareTo2 = this.upperBound.compareTo((Cut) range.upperBound);
        if (iCompareTo <= 0 && iCompareTo2 >= 0) {
            return this;
        }
        if (iCompareTo < 0 || iCompareTo2 > 0) {
            return new Range<>(iCompareTo <= 0 ? this.lowerBound : range.lowerBound, iCompareTo2 >= 0 ? this.upperBound : range.upperBound);
        }
        return range;
    }

    public String toString() {
        return toString(this.lowerBound, this.upperBound);
    }

    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }

    public C upperEndpoint() {
        return (C) this.upperBound.endpoint();
    }

    private static String toString(Cut<?> cut, Cut<?> cut2) {
        StringBuilder sb = new StringBuilder(16);
        cut.describeAsLowerBound(sb);
        sb.append("..");
        cut2.describeAsUpperBound(sb);
        return sb.toString();
    }

    @Override // com.google.common.base.Predicate
    @Deprecated
    public boolean apply(C c) {
        return contains(c);
    }
}
