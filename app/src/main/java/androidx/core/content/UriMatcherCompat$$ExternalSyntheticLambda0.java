package androidx.core.content;

import android.content.ComponentName;
import android.content.UriMatcher;
import android.net.Uri;
import androidx.core.util.Predicate;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class UriMatcherCompat$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UriMatcherCompat$$ExternalSyntheticLambda0(Object obj, int i) {
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
                return UriMatcherCompat.lambda$asPredicate$0((UriMatcher) this.f$0, (Uri) obj);
            default:
                return ((ComponentName) this.f$0).equals((ComponentName) obj);
        }
    }
}
