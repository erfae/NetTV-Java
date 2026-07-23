package androidx.core.util;

import android.annotation.SuppressLint;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"UnknownNullness"})
public interface Predicate<T> {

    /* JADX INFO: renamed from: androidx.core.util.Predicate$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<T> {
        @SuppressLint({"MissingNullability"})
        public static Predicate $default$and(Predicate predicate, @SuppressLint({"MissingNullability"}) Predicate predicate2) {
            Objects.requireNonNull(predicate2);
            return new Predicate$$ExternalSyntheticLambda0(predicate, predicate2, 1);
        }

        @SuppressLint({"MissingNullability"})
        public static Predicate $default$negate(Predicate predicate) {
            return new Predicate$$ExternalSyntheticLambda1(predicate, 1);
        }

        @SuppressLint({"MissingNullability"})
        public static Predicate $default$or(Predicate predicate, @SuppressLint({"MissingNullability"}) Predicate predicate2) {
            Objects.requireNonNull(predicate2);
            return new Predicate$$ExternalSyntheticLambda0(predicate, predicate2, 0);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(Predicate predicate, Predicate predicate2, Object obj) {
            return predicate.test(obj) && predicate2.test(obj);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(Predicate predicate, Object obj) {
            return !predicate.test(obj);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(Predicate predicate, Predicate predicate2, Object obj) {
            return predicate.test(obj) || predicate2.test(obj);
        }

        @SuppressLint({"MissingNullability"})
        public static <T> Predicate<T> isEqual(@SuppressLint({"MissingNullability"}) Object obj) {
            return obj == null ? Predicate$$ExternalSyntheticLambda2.INSTANCE : new Predicate$$ExternalSyntheticLambda1(obj, 0);
        }

        public static /* synthetic */ boolean lambda$isEqual$3(Object obj) {
            return obj == null;
        }

        @SuppressLint({"MissingNullability"})
        public static <T> Predicate<T> not(@SuppressLint({"MissingNullability"}) Predicate<? super T> predicate) {
            Objects.requireNonNull(predicate);
            return predicate.negate();
        }
    }

    @SuppressLint({"MissingNullability"})
    Predicate<T> and(@SuppressLint({"MissingNullability"}) Predicate<? super T> predicate);

    @SuppressLint({"MissingNullability"})
    Predicate<T> negate();

    @SuppressLint({"MissingNullability"})
    Predicate<T> or(@SuppressLint({"MissingNullability"}) Predicate<? super T> predicate);

    boolean test(T t);
}
