package androidx.core.util;

import androidx.core.content.IntentSanitizer;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Predicate$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Predicate f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ Predicate$$ExternalSyntheticLambda0(Predicate predicate, Predicate predicate2, int i) {
        this.$r8$classId = i;
        this.f$0 = predicate;
        this.f$1 = predicate2;
    }

    public /* synthetic */ Predicate$$ExternalSyntheticLambda0(Class cls, Predicate predicate) {
        this.$r8$classId = 2;
        this.f$1 = cls;
        this.f$0 = predicate;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.$r8$classId) {
            case 0:
                break;
            case 1:
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
            case 1:
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
            case 1:
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
                return Predicate.CC.$private$lambda$or$2(this.f$0, (Predicate) this.f$1, obj);
            case 1:
                return Predicate.CC.$private$lambda$and$0(this.f$0, (Predicate) this.f$1, obj);
            default:
                return IntentSanitizer.Builder.lambda$allowExtra$13((Class) this.f$1, this.f$0, obj);
        }
    }
}
