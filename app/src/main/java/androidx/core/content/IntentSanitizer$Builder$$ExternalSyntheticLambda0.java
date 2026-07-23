package androidx.core.content;

import android.content.ComponentName;
import android.net.Uri;
import androidx.core.util.Predicate;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class IntentSanitizer$Builder$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ IntentSanitizer$Builder$$ExternalSyntheticLambda0(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
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
            default:
                break;
        }
        return Predicate.CC.$default$or(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return IntentSanitizer.Builder.lambda$allowClipDataUriWithAuthority$11(this.f$0, (Uri) obj);
            case 1:
                return this.f$0.equals((String) obj);
            case 2:
                return this.f$0.equals((String) obj);
            case 3:
                return IntentSanitizer.Builder.lambda$allowExtraStreamUriWithAuthority$15(this.f$0, (Uri) obj);
            case 4:
                return this.f$0.equals((String) obj);
            case 5:
                return this.f$0.equals((String) obj);
            case 6:
                return IntentSanitizer.Builder.lambda$allowComponentWithPackage$9(this.f$0, (ComponentName) obj);
            case 7:
                return IntentSanitizer.Builder.lambda$allowDataWithAuthority$8(this.f$0, (Uri) obj);
            default:
                return IntentSanitizer.Builder.lambda$allowExtraOutput$16(this.f$0, (Uri) obj);
        }
    }
}
