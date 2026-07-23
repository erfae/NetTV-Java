package androidx.leanback.widget;

import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class GuidedActionAdapterGroup {
    private static final boolean DEBUG_EDIT = false;
    private static final String TAG_EDIT = "EditableAction";
    public ArrayList<Pair<GuidedActionAdapter, GuidedActionAdapter>> mAdapters = new ArrayList<>();
    private GuidedActionAdapter.EditListener mEditListener;
    private boolean mImeOpened;

    private void updateTextIntoAction(GuidedActionsStylist.ViewHolder viewHolder, TextView textView) {
        GuidedAction action = viewHolder.getAction();
        if (textView == viewHolder.getDescriptionView()) {
            if (action.getEditDescription() != null) {
                action.setEditDescription(textView.getText());
                return;
            } else {
                action.setDescription(textView.getText());
                return;
            }
        }
        if (textView == viewHolder.getTitleView()) {
            if (action.getEditTitle() != null) {
                action.setEditTitle(textView.getText());
            } else {
                action.setTitle(textView.getText());
            }
        }
    }

    public void addAdpter(GuidedActionAdapter guidedActionAdapter, GuidedActionAdapter guidedActionAdapter2) {
        this.mAdapters.add(new Pair<>(guidedActionAdapter, guidedActionAdapter2));
        if (guidedActionAdapter != null) {
            guidedActionAdapter.mGroup = this;
        }
        if (guidedActionAdapter2 != null) {
            guidedActionAdapter2.mGroup = this;
        }
    }

    public void closeIme(View view) {
        if (this.mImeOpened) {
            this.mImeOpened = false;
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            this.mEditListener.onImeClose();
        }
    }

    public void fillAndGoNext(GuidedActionAdapter guidedActionAdapter, TextView textView) {
        GuidedActionAdapterGroup guidedActionAdapterGroup;
        int i;
        GuidedActionsStylist.ViewHolder viewHolderFindSubChildViewHolder = guidedActionAdapter.findSubChildViewHolder(textView);
        updateTextIntoAction(viewHolderFindSubChildViewHolder, textView);
        guidedActionAdapter.performOnActionClick(viewHolderFindSubChildViewHolder);
        long jOnGuidedActionEditedAndProceed = this.mEditListener.onGuidedActionEditedAndProceed(viewHolderFindSubChildViewHolder.getAction());
        boolean z = false;
        guidedActionAdapter.getGuidedActionsStylist().setEditingMode(viewHolderFindSubChildViewHolder, false, true);
        if (jOnGuidedActionEditedAndProceed != -3 && jOnGuidedActionEditedAndProceed != viewHolderFindSubChildViewHolder.getAction().getId()) {
            GuidedAction action = viewHolderFindSubChildViewHolder.getAction();
            if (jOnGuidedActionEditedAndProceed == -2) {
                int iIndexOf = guidedActionAdapter.indexOf(action);
                if (iIndexOf >= 0) {
                    i = iIndexOf + 1;
                    guidedActionAdapterGroup = this;
                }
            } else {
                guidedActionAdapterGroup = this;
                i = 0;
            }
            while (true) {
                int count = guidedActionAdapter.getCount();
                if (jOnGuidedActionEditedAndProceed == -2) {
                    while (i < count && !guidedActionAdapter.getItem(i).isFocusable()) {
                        i++;
                    }
                } else {
                    while (i < count && guidedActionAdapter.getItem(i).getId() != jOnGuidedActionEditedAndProceed) {
                        i++;
                    }
                }
                if (i < count) {
                    GuidedActionsStylist.ViewHolder viewHolder = (GuidedActionsStylist.ViewHolder) guidedActionAdapter.getGuidedActionsStylist().getActionsGridView().findViewHolderForPosition(i);
                    if (viewHolder == null) {
                        break;
                    }
                    if (viewHolder.getAction().hasTextEditable()) {
                        guidedActionAdapterGroup.openIme(guidedActionAdapter, viewHolder);
                    } else {
                        guidedActionAdapterGroup.closeIme(viewHolder.itemView);
                        viewHolder.itemView.requestFocus();
                    }
                    z = true;
                    break;
                }
                guidedActionAdapter = guidedActionAdapterGroup.getNextAdapter(guidedActionAdapter);
                if (guidedActionAdapter == null) {
                    break;
                }
                guidedActionAdapterGroup = guidedActionAdapterGroup;
                i = 0;
            }
        }
        if (z) {
            return;
        }
        closeIme(textView);
        viewHolderFindSubChildViewHolder.itemView.requestFocus();
    }

    public void fillAndStay(GuidedActionAdapter guidedActionAdapter, TextView textView) {
        GuidedActionsStylist.ViewHolder viewHolderFindSubChildViewHolder = guidedActionAdapter.findSubChildViewHolder(textView);
        updateTextIntoAction(viewHolderFindSubChildViewHolder, textView);
        this.mEditListener.onGuidedActionEditCanceled(viewHolderFindSubChildViewHolder.getAction());
        guidedActionAdapter.getGuidedActionsStylist().setEditingMode(viewHolderFindSubChildViewHolder, false, true);
        closeIme(textView);
        viewHolderFindSubChildViewHolder.itemView.requestFocus();
    }

    public GuidedActionAdapter getNextAdapter(GuidedActionAdapter guidedActionAdapter) {
        for (int i = 0; i < this.mAdapters.size(); i++) {
            Pair<GuidedActionAdapter, GuidedActionAdapter> pair = this.mAdapters.get(i);
            if (pair.first == guidedActionAdapter) {
                return (GuidedActionAdapter) pair.second;
            }
        }
        return null;
    }

    public void openIme(GuidedActionAdapter guidedActionAdapter, GuidedActionsStylist.ViewHolder viewHolder) {
        guidedActionAdapter.getGuidedActionsStylist().setEditingMode(viewHolder, true, true);
        View editingView = viewHolder.getEditingView();
        if (editingView == null || !viewHolder.isInEditingText()) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) editingView.getContext().getSystemService("input_method");
        editingView.setFocusable(true);
        editingView.requestFocus();
        inputMethodManager.showSoftInput(editingView, 0);
        if (this.mImeOpened) {
            return;
        }
        this.mImeOpened = true;
        this.mEditListener.onImeOpen();
    }

    public void setEditListener(GuidedActionAdapter.EditListener editListener) {
        this.mEditListener = editListener;
    }
}
