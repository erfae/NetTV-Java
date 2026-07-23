package androidx.core.util;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Predicate$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Predicate$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.$r8$classId) {
            case 0:
                break;
            default:
                break;
        }
        return Predicate.CC.$default$and(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate negate() {
        switch (this.$r8$classId) {
            case 0:
                break;
            default:
                break;
        }
        return Predicate.CC.$default$negate(this);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.$r8$classId) {
            case 0:
                break;
            default:
                break;
        }
        return Predicate.CC.$default$or(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.equals(obj);
            default:
                return Predicate.CC.$private$lambda$negate$1((Predicate) this.f$0, obj);
        }
    }
}
