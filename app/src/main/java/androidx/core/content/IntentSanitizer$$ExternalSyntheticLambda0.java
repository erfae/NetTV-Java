package androidx.core.content;

import androidx.core.util.Consumer;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class IntentSanitizer$$ExternalSyntheticLambda0 implements Consumer {
    public static final /* synthetic */ IntentSanitizer$$ExternalSyntheticLambda0 INSTANCE = new IntentSanitizer$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ IntentSanitizer$$ExternalSyntheticLambda0 INSTANCE$1 = new IntentSanitizer$$ExternalSyntheticLambda0(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ IntentSanitizer$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // androidx.core.util.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                IntentSanitizer.lambda$sanitizeByFiltering$0((String) obj);
                break;
            default:
                IntentSanitizer.lambda$sanitizeByThrowing$1((String) obj);
                break;
        }
    }
}
