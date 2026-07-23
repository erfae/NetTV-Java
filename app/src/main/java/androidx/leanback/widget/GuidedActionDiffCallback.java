package androidx.leanback.widget;

import android.text.TextUtils;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
public class GuidedActionDiffCallback extends DiffCallback<GuidedAction> {
    public static final GuidedActionDiffCallback sInstance = new GuidedActionDiffCallback();

    public static GuidedActionDiffCallback getInstance() {
        return sInstance;
    }

    @Override // androidx.leanback.widget.DiffCallback
    public boolean areContentsTheSame(@NonNull GuidedAction guidedAction, @NonNull GuidedAction guidedAction2) {
        if (guidedAction == null) {
            return guidedAction2 == null;
        }
        return guidedAction2 != null && guidedAction.getCheckSetId() == guidedAction2.getCheckSetId() && guidedAction.mActionFlags == guidedAction2.mActionFlags && TextUtils.equals(guidedAction.getTitle(), guidedAction2.getTitle()) && TextUtils.equals(guidedAction.getDescription(), guidedAction2.getDescription()) && guidedAction.getInputType() == guidedAction2.getInputType() && TextUtils.equals(guidedAction.getEditTitle(), guidedAction2.getEditTitle()) && TextUtils.equals(guidedAction.getEditDescription(), guidedAction2.getEditDescription()) && guidedAction.getEditInputType() == guidedAction2.getEditInputType() && guidedAction.getDescriptionEditInputType() == guidedAction2.getDescriptionEditInputType();
    }

    @Override // androidx.leanback.widget.DiffCallback
    public boolean areItemsTheSame(@NonNull GuidedAction guidedAction, @NonNull GuidedAction guidedAction2) {
        if (guidedAction == null) {
            return guidedAction2 == null;
        }
        return guidedAction2 != null && guidedAction.getId() == guidedAction2.getId();
    }
}
