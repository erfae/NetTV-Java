package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    private static final long serialVersionUID = 0;
    public final C endpoint;

    /* JADX INFO: renamed from: com.google.common.collect.Cut$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType;

        static {
            int[] iArr = new int[BoundType.values().length];
            $SwitchMap$com$google$common$collect$BoundType = iArr;
            try {
                iArr[BoundType.CLOSED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static final class AboveAll extends Cut<Comparable<?>> {
        private static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0;

        private AboveAll() {
            super("");
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : 1;
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsLowerBound(StringBuilder sb) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsUpperBound(StringBuilder sb) {
            sb.append("+∞)");
        }

        @Override // com.google.common.collect.Cut
        public final Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override // com.google.common.collect.Cut
        public final Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.maxValue();
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return System.identityHashCode(this);
        }

        @Override // com.google.common.collect.Cut
        public final boolean isLessThan(Comparable<?> comparable) {
            return false;
        }

        @Override // com.google.common.collect.Cut
        public final Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        public String toString() {
            return "+∞";
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsLowerBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        public final Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        public final Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }
    }

    public static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public AboveValue(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        @Override // com.google.common.collect.Cut
        public final Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
            Comparable next = discreteDomain.next(this.endpoint);
            return next != null ? new BelowValue(next) : AboveAll.INSTANCE;
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return super.compareTo((Cut) obj);
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsLowerBound(StringBuilder sb) {
            sb.append('(');
            sb.append(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(']');
        }

        @Override // com.google.common.collect.Cut
        public final C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return this.endpoint;
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return ~this.endpoint.hashCode();
        }

        @Override // com.google.common.collect.Cut
        public final boolean isLessThan(C c) {
            C c2 = this.endpoint;
            int i = Range.$r8$clinit;
            return c2.compareTo(c) < 0;
        }

        @Override // com.google.common.collect.Cut
        @CheckForNull
        public final C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return (C) discreteDomain.next(this.endpoint);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.endpoint);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 2, "/", strValueOf, "\\");
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }

        @Override // com.google.common.collect.Cut
        public final Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                Comparable next = discreteDomain.next(this.endpoint);
                return next == null ? BelowAll.INSTANCE : new BelowValue(next);
            }
            if (i == 2) {
                return this;
            }
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        public final Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                return this;
            }
            if (i != 2) {
                throw new AssertionError();
            }
            Comparable next = discreteDomain.next(this.endpoint);
            return next == null ? AboveAll.INSTANCE : new BelowValue(next);
        }
    }

    public static final class BelowAll extends Cut<Comparable<?>> {
        private static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0;

        private BelowAll() {
            super("");
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override // com.google.common.collect.Cut
        public final Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> discreteDomain) {
            try {
                return new BelowValue(discreteDomain.minValue());
            } catch (NoSuchElementException unused) {
                return this;
            }
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : -1;
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsLowerBound(StringBuilder sb) {
            sb.append("(-∞");
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsUpperBound(StringBuilder sb) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        public final Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override // com.google.common.collect.Cut
        public final Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return System.identityHashCode(this);
        }

        @Override // com.google.common.collect.Cut
        public final boolean isLessThan(Comparable<?> comparable) {
            return true;
        }

        @Override // com.google.common.collect.Cut
        public final Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.minValue();
        }

        public String toString() {
            return "-∞";
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsUpperBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        public final Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        public final Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }
    }

    public static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public BelowValue(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return super.compareTo((Cut) obj);
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsLowerBound(StringBuilder sb) {
            sb.append('[');
            sb.append(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        public final void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(')');
        }

        @Override // com.google.common.collect.Cut
        @CheckForNull
        public final C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return (C) discreteDomain.previous(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return this.endpoint.hashCode();
        }

        @Override // com.google.common.collect.Cut
        public final boolean isLessThan(C c) {
            C c2 = this.endpoint;
            int i = Range.$r8$clinit;
            return c2.compareTo(c) <= 0;
        }

        @Override // com.google.common.collect.Cut
        public final C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return this.endpoint;
        }

        public String toString() {
            String strValueOf = String.valueOf(this.endpoint);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 2, "\\", strValueOf, "/");
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }

        @Override // com.google.common.collect.Cut
        public final BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }

        @Override // com.google.common.collect.Cut
        public final Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                return this;
            }
            if (i != 2) {
                throw new AssertionError();
            }
            Comparable comparablePrevious = discreteDomain.previous(this.endpoint);
            return comparablePrevious == null ? BelowAll.INSTANCE : new AboveValue(comparablePrevious);
        }

        @Override // com.google.common.collect.Cut
        public final Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                Comparable comparablePrevious = discreteDomain.previous(this.endpoint);
                return comparablePrevious == null ? AboveAll.INSTANCE : new AboveValue(comparablePrevious);
            }
            if (i == 2) {
                return this;
            }
            throw new AssertionError();
        }
    }

    public Cut(C c) {
        this.endpoint = c;
    }

    public Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    public abstract void describeAsLowerBound(StringBuilder sb);

    public abstract void describeAsUpperBound(StringBuilder sb);

    public C endpoint() {
        return this.endpoint;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof Cut)) {
            return false;
        }
        try {
            return compareTo((Cut) obj) == 0;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    @CheckForNull
    public abstract C greatestValueBelow(DiscreteDomain<C> discreteDomain);

    public abstract int hashCode();

    public abstract boolean isLessThan(C c);

    @CheckForNull
    public abstract C leastValueAbove(DiscreteDomain<C> discreteDomain);

    public abstract BoundType typeAsLowerBound();

    public abstract BoundType typeAsUpperBound();

    public abstract Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain);

    public abstract Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain);

    @Override // java.lang.Comparable
    public int compareTo(Cut<C> cut) {
        if (cut == BelowAll.INSTANCE) {
            return 1;
        }
        if (cut == AboveAll.INSTANCE) {
            return -1;
        }
        C c = this.endpoint;
        C c2 = cut.endpoint;
        int i = Range.$r8$clinit;
        int iCompareTo = c.compareTo(c2);
        return iCompareTo != 0 ? iCompareTo : Booleans.compare(this instanceof AboveValue, cut instanceof AboveValue);
    }
}
