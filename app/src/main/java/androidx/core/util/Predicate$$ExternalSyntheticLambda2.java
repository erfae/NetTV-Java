package androidx.core.util;

import android.content.ClipData;
import android.content.ComponentName;
import android.net.Uri;
import androidx.core.content.IntentSanitizer;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Predicate$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$1 = new Predicate$$ExternalSyntheticLambda2(1);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$2 = new Predicate$$ExternalSyntheticLambda2(2);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$3 = new Predicate$$ExternalSyntheticLambda2(3);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$4 = new Predicate$$ExternalSyntheticLambda2(4);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$5 = new Predicate$$ExternalSyntheticLambda2(5);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$6 = new Predicate$$ExternalSyntheticLambda2(6);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$7 = new Predicate$$ExternalSyntheticLambda2(7);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$8 = new Predicate$$ExternalSyntheticLambda2(8);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$9 = new Predicate$$ExternalSyntheticLambda2(9);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$10 = new Predicate$$ExternalSyntheticLambda2(10);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE$11 = new Predicate$$ExternalSyntheticLambda2(11);
    public static final /* synthetic */ Predicate$$ExternalSyntheticLambda2 INSTANCE = new Predicate$$ExternalSyntheticLambda2(0);

    public /* synthetic */ Predicate$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.$r8$classId) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
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
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
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
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
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
                return Predicate.CC.lambda$isEqual$3(obj);
            case 1:
                return IntentSanitizer.Builder.lambda$allowExtra$14(obj);
            case 2:
                return IntentSanitizer.Builder.lambda$allowExtra$12(obj);
            case 3:
                return IntentSanitizer.Builder.lambda$new$0((String) obj);
            case 4:
                return IntentSanitizer.Builder.lambda$new$1((Uri) obj);
            case 5:
                return IntentSanitizer.Builder.lambda$new$2((String) obj);
            case 6:
                return IntentSanitizer.Builder.lambda$new$3((String) obj);
            case 7:
                return IntentSanitizer.Builder.lambda$new$4((String) obj);
            case 8:
                return IntentSanitizer.Builder.lambda$new$5((ComponentName) obj);
            case 9:
                return IntentSanitizer.Builder.lambda$new$6((Uri) obj);
            case 10:
                return IntentSanitizer.Builder.lambda$new$7((ClipData) obj);
            default:
                return IntentSanitizer.Builder.lambda$allowAnyComponent$10((ComponentName) obj);
        }
    }
}
