package androidx.lifecycle;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.savedstate.SavedStateRegistry;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class SavedStateHandle$$ExternalSyntheticLambda0 implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SavedStateHandle$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
    public final Bundle saveState() {
        switch (this.$r8$classId) {
            case 0:
                return SavedStateHandle.m43savedStateProvider$lambda0((SavedStateHandle) this.f$0);
            case 1:
                return SavedStateHandle.m43savedStateProvider$lambda0((SavedStateHandle) this.f$0);
            default:
                return ((ComponentActivity) this.f$0).lambda$new$0();
        }
    }
}
