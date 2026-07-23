package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public final class Suppliers {

    @VisibleForTesting
    public static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> delegate;
        public final long durationNanos;
        public volatile transient long expirationNanos;

        @CheckForNull
        public volatile transient T value;

        public ExpiringMemoizingSupplier(Supplier<T> supplier, long j, TimeUnit timeUnit) {
            this.delegate = (Supplier) Preconditions.checkNotNull(supplier);
            this.durationNanos = timeUnit.toNanos(j);
            Preconditions.checkArgument(j > 0, "duration (%s %s) must be > 0", j, timeUnit);
        }

        @Override // com.google.common.base.Supplier
        @ParametricNullness
        public T get() {
            long j = this.expirationNanos;
            int i = Platform.$r8$clinit;
            long jNanoTime = System.nanoTime();
            if (j == 0 || jNanoTime - j >= 0) {
                synchronized (this) {
                    if (j == this.expirationNanos) {
                        T t = this.delegate.get();
                        this.value = t;
                        long j2 = jNanoTime + this.durationNanos;
                        if (j2 == 0) {
                            j2 = 1;
                        }
                        this.expirationNanos = j2;
                        return t;
                    }
                }
            }
            return this.value;
        }

        public String toString() {
            String strValueOf = String.valueOf(this.delegate);
            long j = this.durationNanos;
            StringBuilder sb = new StringBuilder(strValueOf.length() + 62);
            sb.append("Suppliers.memoizeWithExpiration(");
            sb.append(strValueOf);
            sb.append(", ");
            sb.append(j);
            sb.append(", NANOS)");
            return sb.toString();
        }
    }

    @VisibleForTesting
    public static class MemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> delegate;
        public volatile transient boolean initialized;

        @CheckForNull
        public transient T value;

        public MemoizingSupplier(Supplier<T> supplier) {
            this.delegate = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.base.Supplier
        @ParametricNullness
        public T get() {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        T t = this.delegate.get();
                        this.value = t;
                        this.initialized = true;
                        return t;
                    }
                }
            }
            return this.value;
        }

        public String toString() {
            Object objM;
            if (this.initialized) {
                String strValueOf = String.valueOf(this.value);
                objM = MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 25, "<supplier that returned ", strValueOf, ">");
            } else {
                objM = this.delegate;
            }
            String strValueOf2 = String.valueOf(objM);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + 19, "Suppliers.memoize(", strValueOf2, ")");
        }
    }

    @VisibleForTesting
    public static class NonSerializableMemoizingSupplier<T> implements Supplier<T> {

        @CheckForNull
        public volatile Supplier<T> delegate;
        public volatile boolean initialized;

        @CheckForNull
        public T value;

        public NonSerializableMemoizingSupplier(Supplier<T> supplier) {
            this.delegate = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.base.Supplier
        @ParametricNullness
        public T get() {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        Supplier<T> supplier = this.delegate;
                        java.util.Objects.requireNonNull(supplier);
                        T t = supplier.get();
                        this.value = t;
                        this.initialized = true;
                        this.delegate = null;
                        return t;
                    }
                }
            }
            return this.value;
        }

        public String toString() {
            Object objM = this.delegate;
            if (objM == null) {
                String strValueOf = String.valueOf(this.value);
                objM = MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 25, "<supplier that returned ", strValueOf, ">");
            }
            String strValueOf2 = String.valueOf(objM);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + 19, "Suppliers.memoize(", strValueOf2, ")");
        }
    }

    public static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Function<? super F, T> function;
        public final Supplier<F> supplier;

        public SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
            this.function = (Function) Preconditions.checkNotNull(function);
            this.supplier = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof SupplierComposition)) {
                return false;
            }
            SupplierComposition supplierComposition = (SupplierComposition) obj;
            return this.function.equals(supplierComposition.function) && this.supplier.equals(supplierComposition.supplier);
        }

        @Override // com.google.common.base.Supplier
        @ParametricNullness
        public T get() {
            return this.function.apply(this.supplier.get());
        }

        public int hashCode() {
            return Objects.hashCode(this.function, this.supplier);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.function);
            String strValueOf2 = String.valueOf(this.supplier);
            StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + 21, "Suppliers.compose(", strValueOf, ", ", strValueOf2);
            sbM209m.append(")");
            return sbM209m.toString();
        }
    }

    public interface SupplierFunction<T> extends Function<Supplier<T>, T> {
    }

    public enum SupplierFunctionImpl implements SupplierFunction<Object> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Suppliers.supplierFunction()";
        }

        @Override // com.google.common.base.Function
        @CheckForNull
        public Object apply(Supplier<Object> supplier) {
            return supplier.get();
        }
    }

    public static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;

        @ParametricNullness
        public final T instance;

        public SupplierOfInstance(@ParametricNullness T t) {
            this.instance = t;
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof SupplierOfInstance) {
                return Objects.equal(this.instance, ((SupplierOfInstance) obj).instance);
            }
            return false;
        }

        @Override // com.google.common.base.Supplier
        @ParametricNullness
        public T get() {
            return this.instance;
        }

        public int hashCode() {
            return Objects.hashCode(this.instance);
        }

        public String toString() {
            String strValueOf = String.valueOf(this.instance);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 22, "Suppliers.ofInstance(", strValueOf, ")");
        }
    }

    public static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> delegate;

        public ThreadSafeSupplier(Supplier<T> supplier) {
            this.delegate = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.base.Supplier
        @ParametricNullness
        public T get() {
            T t;
            synchronized (this.delegate) {
                t = this.delegate.get();
            }
            return t;
        }

        public String toString() {
            String strValueOf = String.valueOf(this.delegate);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 32, "Suppliers.synchronizedSupplier(", strValueOf, ")");
        }
    }

    private Suppliers() {
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        return new SupplierComposition(function, supplier);
    }

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        if ((supplier instanceof NonSerializableMemoizingSupplier) || (supplier instanceof MemoizingSupplier)) {
            return supplier;
        }
        return supplier instanceof Serializable ? new MemoizingSupplier(supplier) : new NonSerializableMemoizingSupplier(supplier);
    }

    public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> supplier, long j, TimeUnit timeUnit) {
        return new ExpiringMemoizingSupplier(supplier, j, timeUnit);
    }

    public static <T> Supplier<T> ofInstance(@ParametricNullness T t) {
        return new SupplierOfInstance(t);
    }

    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return SupplierFunctionImpl.INSTANCE;
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> supplier) {
        return new ThreadSafeSupplier(supplier);
    }
}
