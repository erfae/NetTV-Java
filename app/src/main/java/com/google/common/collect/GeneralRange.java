package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(serializable = true)
@ElementTypesAreNonnullByDefault
final class GeneralRange<T> implements Serializable {
    private final Comparator<? super T> comparator;
    private final boolean hasLowerBound;
    private final boolean hasUpperBound;
    private final BoundType lowerBoundType;

    @CheckForNull
    private final T lowerEndpoint;

    @CheckForNull
    private transient GeneralRange<T> reverse;
    private final BoundType upperBoundType;

    @CheckForNull
    private final T upperEndpoint;

    /* JADX WARN: Multi-variable type inference failed */
    private GeneralRange(Comparator<? super T> comparator, boolean z, @CheckForNull T t, BoundType boundType, boolean z2, @CheckForNull T t2, BoundType boundType2) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
        this.hasLowerBound = z;
        this.hasUpperBound = z2;
        this.lowerEndpoint = t;
        this.lowerBoundType = (BoundType) Preconditions.checkNotNull(boundType);
        this.upperEndpoint = t2;
        this.upperBoundType = (BoundType) Preconditions.checkNotNull(boundType2);
        if (z) {
            comparator.compare(t, t);
        }
        if (z2) {
            comparator.compare(t2, t2);
        }
        if (z && z2) {
            int iCompare = comparator.compare(t, t2);
            Preconditions.checkArgument(iCompare <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", t, t2);
            if (iCompare == 0) {
                BoundType boundType3 = BoundType.OPEN;
                Preconditions.checkArgument((boundType != boundType3) | (boundType2 != boundType3));
            }
        }
    }

    public static <T> GeneralRange<T> all(Comparator<? super T> comparator) {
        BoundType boundType = BoundType.OPEN;
        return new GeneralRange<>(comparator, false, null, boundType, false, null, boundType);
    }

    public static <T> GeneralRange<T> downTo(Comparator<? super T> comparator, @ParametricNullness T t, BoundType boundType) {
        return new GeneralRange<>(comparator, true, t, boundType, false, null, BoundType.OPEN);
    }

    public static <T> GeneralRange<T> upTo(Comparator<? super T> comparator, @ParametricNullness T t, BoundType boundType) {
        return new GeneralRange<>(comparator, false, null, BoundType.OPEN, true, t, boundType);
    }

    public final Comparator<? super T> comparator() {
        return this.comparator;
    }

    public final boolean contains(@ParametricNullness T t) {
        return (tooLow(t) || tooHigh(t)) ? false : true;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof GeneralRange)) {
            return false;
        }
        GeneralRange generalRange = (GeneralRange) obj;
        return this.comparator.equals(generalRange.comparator) && this.hasLowerBound == generalRange.hasLowerBound && this.hasUpperBound == generalRange.hasUpperBound && this.lowerBoundType.equals(generalRange.lowerBoundType) && this.upperBoundType.equals(generalRange.upperBoundType) && Objects.equal(this.lowerEndpoint, generalRange.lowerEndpoint) && Objects.equal(this.upperEndpoint, generalRange.upperEndpoint);
    }

    public final BoundType getLowerBoundType() {
        return this.lowerBoundType;
    }

    @CheckForNull
    public final T getLowerEndpoint() {
        return this.lowerEndpoint;
    }

    public final BoundType getUpperBoundType() {
        return this.upperBoundType;
    }

    @CheckForNull
    public final T getUpperEndpoint() {
        return this.upperEndpoint;
    }

    public final boolean hasLowerBound() {
        return this.hasLowerBound;
    }

    public final boolean hasUpperBound() {
        return this.hasUpperBound;
    }

    public int hashCode() {
        return Objects.hashCode(this.comparator, this.lowerEndpoint, this.lowerBoundType, this.upperEndpoint, this.upperBoundType);
    }

    public final GeneralRange<T> intersect(GeneralRange<T> generalRange) {
        int iCompare;
        int iCompare2;
        T t;
        BoundType boundType;
        BoundType boundType2;
        int iCompare3;
        BoundType boundType3;
        Preconditions.checkNotNull(generalRange);
        Preconditions.checkArgument(this.comparator.equals(generalRange.comparator));
        boolean z = this.hasLowerBound;
        T t2 = this.lowerEndpoint;
        BoundType boundType4 = this.lowerBoundType;
        if (!z) {
            z = generalRange.hasLowerBound;
            t2 = generalRange.lowerEndpoint;
            boundType4 = generalRange.lowerBoundType;
        } else if (generalRange.hasLowerBound && ((iCompare = this.comparator.compare(t2, generalRange.lowerEndpoint)) < 0 || (iCompare == 0 && generalRange.lowerBoundType == BoundType.OPEN))) {
            t2 = generalRange.lowerEndpoint;
            boundType4 = generalRange.lowerBoundType;
        }
        boolean z2 = z;
        boolean z3 = this.hasUpperBound;
        T t3 = this.upperEndpoint;
        BoundType boundType5 = this.upperBoundType;
        if (!z3) {
            z3 = generalRange.hasUpperBound;
            t3 = generalRange.upperEndpoint;
            boundType5 = generalRange.upperBoundType;
        } else if (generalRange.hasUpperBound && ((iCompare2 = this.comparator.compare(t3, generalRange.upperEndpoint)) > 0 || (iCompare2 == 0 && generalRange.upperBoundType == BoundType.OPEN))) {
            t3 = generalRange.upperEndpoint;
            boundType5 = generalRange.upperBoundType;
        }
        boolean z4 = z3;
        T t4 = t3;
        if (z2 && z4 && ((iCompare3 = this.comparator.compare(t2, t4)) > 0 || (iCompare3 == 0 && boundType4 == (boundType3 = BoundType.OPEN) && boundType5 == boundType3))) {
            boundType = BoundType.OPEN;
            boundType2 = BoundType.CLOSED;
            t = t4;
        } else {
            t = t2;
            boundType = boundType4;
            boundType2 = boundType5;
        }
        return new GeneralRange<>(this.comparator, z2, t, boundType, z4, t4, boundType2);
    }

    public String toString() {
        String strValueOf = String.valueOf(this.comparator);
        BoundType boundType = this.lowerBoundType;
        BoundType boundType2 = BoundType.CLOSED;
        char c = boundType == boundType2 ? '[' : '(';
        String strValueOf2 = String.valueOf(this.hasLowerBound ? this.lowerEndpoint : "-∞");
        String strValueOf3 = String.valueOf(this.hasUpperBound ? this.upperEndpoint : "∞");
        char c2 = this.upperBoundType == boundType2 ? ']' : ')';
        StringBuilder sb = new StringBuilder(strValueOf3.length() + strValueOf2.length() + strValueOf.length() + 4);
        sb.append(strValueOf);
        sb.append(":");
        sb.append(c);
        sb.append(strValueOf2);
        sb.append(',');
        sb.append(strValueOf3);
        sb.append(c2);
        return sb.toString();
    }

    public final boolean tooHigh(@ParametricNullness T t) {
        if (!this.hasUpperBound) {
            return false;
        }
        int iCompare = this.comparator.compare(t, this.upperEndpoint);
        return ((iCompare == 0) & (this.upperBoundType == BoundType.OPEN)) | (iCompare > 0);
    }

    public final boolean tooLow(@ParametricNullness T t) {
        if (!this.hasLowerBound) {
            return false;
        }
        int iCompare = this.comparator.compare(t, this.lowerEndpoint);
        return ((iCompare == 0) & (this.lowerBoundType == BoundType.OPEN)) | (iCompare < 0);
    }
}
