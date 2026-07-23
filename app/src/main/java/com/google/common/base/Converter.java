package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public abstract class Converter<A, B> implements Function<A, B> {
    private final boolean handleNullAutomatically = true;

    @RetainedWith
    @CheckForNull
    @LazyInit
    private transient Converter<B, A> reverse;

    public static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Converter<A, B> first;
        public final Converter<B, C> second;

        public ConverterComposition(Converter<A, B> converter, Converter<B, C> converter2) {
            this.first = converter;
            this.second = converter2;
        }

        @Override // com.google.common.base.Converter
        @CheckForNull
        public final A correctedDoBackward(@CheckForNull C c) {
            return (A) this.first.correctedDoBackward(this.second.correctedDoBackward(c));
        }

        @Override // com.google.common.base.Converter
        @CheckForNull
        public final C correctedDoForward(@CheckForNull A a) {
            return (C) this.second.correctedDoForward(this.first.correctedDoForward(a));
        }

        @Override // com.google.common.base.Converter
        public final A doBackward(C c) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        public final C doForward(A a) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof ConverterComposition)) {
                return false;
            }
            ConverterComposition converterComposition = (ConverterComposition) obj;
            return this.first.equals(converterComposition.first) && this.second.equals(converterComposition.second);
        }

        public int hashCode() {
            return this.second.hashCode() + (this.first.hashCode() * 31);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.first);
            String strValueOf2 = String.valueOf(this.second);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 10, strValueOf, ".andThen(", strValueOf2, ")");
        }
    }

    public static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
        private final Function<? super B, ? extends A> backwardFunction;
        private final Function<? super A, ? extends B> forwardFunction;

        @Override // com.google.common.base.Converter
        public final A doBackward(B b) {
            return this.backwardFunction.apply(b);
        }

        @Override // com.google.common.base.Converter
        public final B doForward(A a) {
            return this.forwardFunction.apply(a);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof FunctionBasedConverter)) {
                return false;
            }
            FunctionBasedConverter functionBasedConverter = (FunctionBasedConverter) obj;
            return this.forwardFunction.equals(functionBasedConverter.forwardFunction) && this.backwardFunction.equals(functionBasedConverter.backwardFunction);
        }

        public int hashCode() {
            return this.backwardFunction.hashCode() + (this.forwardFunction.hashCode() * 31);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.forwardFunction);
            String strValueOf2 = String.valueOf(this.backwardFunction);
            StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + 18, "Converter.from(", strValueOf, ", ", strValueOf2);
            sbM209m.append(")");
            return sbM209m.toString();
        }

        private FunctionBasedConverter(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
            this.forwardFunction = (Function) Preconditions.checkNotNull(function);
            this.backwardFunction = (Function) Preconditions.checkNotNull(function2);
        }
    }

    public static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {
        public static final IdentityConverter<?> INSTANCE = new IdentityConverter<>();
        private static final long serialVersionUID = 0;

        private IdentityConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override // com.google.common.base.Converter
        public final <S> Converter<T, S> doAndThen(Converter<T, S> converter) {
            return (Converter) Preconditions.checkNotNull(converter, "otherConverter");
        }

        @Override // com.google.common.base.Converter
        public final T doBackward(T t) {
            return t;
        }

        @Override // com.google.common.base.Converter
        public final T doForward(T t) {
            return t;
        }

        @Override // com.google.common.base.Converter
        public IdentityConverter<T> reverse() {
            return this;
        }

        public String toString() {
            return "Converter.identity()";
        }
    }

    public static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Converter<A, B> original;

        public ReverseConverter(Converter<A, B> converter) {
            this.original = converter;
        }

        @Override // com.google.common.base.Converter
        @CheckForNull
        public final B correctedDoBackward(@CheckForNull A a) {
            return this.original.correctedDoForward(a);
        }

        @Override // com.google.common.base.Converter
        @CheckForNull
        public final A correctedDoForward(@CheckForNull B b) {
            return this.original.correctedDoBackward(b);
        }

        @Override // com.google.common.base.Converter
        public final B doBackward(A a) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        public final A doForward(B b) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof ReverseConverter) {
                return this.original.equals(((ReverseConverter) obj).original);
            }
            return false;
        }

        public int hashCode() {
            return ~this.original.hashCode();
        }

        @Override // com.google.common.base.Converter
        public Converter<A, B> reverse() {
            return this.original;
        }

        public String toString() {
            String strValueOf = String.valueOf(this.original);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 10, strValueOf, ".reverse()");
        }
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new FunctionBasedConverter(function, function2);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.INSTANCE;
    }

    @CheckForNull
    private A unsafeDoBackward(@CheckForNull B b) {
        return doBackward(b);
    }

    @CheckForNull
    private B unsafeDoForward(@CheckForNull A a) {
        return doForward(a);
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return doAndThen(converter);
    }

    @Override // com.google.common.base.Function
    @CanIgnoreReturnValue
    @CheckForNull
    @Deprecated
    public final B apply(@CheckForNull A a) {
        return convert(a);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public final B convert(@CheckForNull A a) {
        return correctedDoForward(a);
    }

    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>() { // from class: com.google.common.base.Converter.1
            @Override // java.lang.Iterable
            public Iterator<B> iterator() {
                return new Iterator<B>() { // from class: com.google.common.base.Converter.1.1
                    private final Iterator<? extends A> fromIterator;

                    {
                        this.fromIterator = iterable.iterator();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }

                    @Override // java.util.Iterator
                    @CheckForNull
                    public B next() {
                        return (B) Converter.this.convert(this.fromIterator.next());
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }

    @CheckForNull
    public A correctedDoBackward(@CheckForNull B b) {
        if (!this.handleNullAutomatically) {
            return unsafeDoBackward(b);
        }
        if (b == null) {
            return null;
        }
        return (A) Preconditions.checkNotNull(doBackward(b));
    }

    @CheckForNull
    public B correctedDoForward(@CheckForNull A a) {
        if (!this.handleNullAutomatically) {
            return unsafeDoForward(a);
        }
        if (a == null) {
            return null;
        }
        return (B) Preconditions.checkNotNull(doForward(a));
    }

    public <C> Converter<A, C> doAndThen(Converter<B, C> converter) {
        return new ConverterComposition(this, (Converter) Preconditions.checkNotNull(converter));
    }

    @ForOverride
    public abstract A doBackward(B b);

    @ForOverride
    public abstract B doForward(A a);

    @Override // com.google.common.base.Function
    public boolean equals(@CheckForNull Object obj) {
        return super.equals(obj);
    }

    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        Converter<B, A> converter = this.reverse;
        if (converter != null) {
            return converter;
        }
        ReverseConverter reverseConverter = new ReverseConverter(this);
        this.reverse = reverseConverter;
        return reverseConverter;
    }
}
