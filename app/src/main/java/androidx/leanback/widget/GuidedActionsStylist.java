package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.leanback.R;
import androidx.leanback.transition.TransitionEpicenterCallback;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.widget.picker.DatePicker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GuidedActionsStylist implements FragmentAnimationProvider {
    private static final String TAG = "GuidedActionsStylist";
    public static final int VIEW_TYPE_DATE_PICKER = 1;
    public static final int VIEW_TYPE_DEFAULT = 0;
    public static final ItemAlignmentFacet sGuidedActionItemAlignFacet;
    private VerticalGridView mActionsGridView;
    private View mBgView;
    private boolean mButtonActions;
    private View mContentView;
    private int mDescriptionMinLines;
    private float mDisabledChevronAlpha;
    private float mDisabledDescriptionAlpha;
    private float mDisabledTextAlpha;
    private int mDisplayHeight;
    private GuidedActionAdapter.EditListener mEditListener;
    private float mEnabledChevronAlpha;
    private float mEnabledDescriptionAlpha;
    private float mEnabledTextAlpha;
    public Object mExpandTransition;
    private float mKeyLinePercent;
    public ViewGroup mMainView;
    private View mSubActionsBackground;
    public VerticalGridView mSubActionsGridView;
    private int mTitleMaxLines;
    private int mTitleMinLines;
    private int mVerticalPadding;
    public GuidedAction mExpandedAction = null;
    private boolean mBackToCollapseSubActions = true;
    private boolean mBackToCollapseActivatorView = true;

    public static class ViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        public GuidedAction mAction;
        public View mActivatorView;
        public ImageView mCheckmarkView;
        public ImageView mChevronView;
        private View mContentView;
        public TextView mDescriptionView;
        public int mEditingMode;
        public ImageView mIconView;
        private final boolean mIsSubAction;
        public Animator mPressAnimator;
        public TextView mTitleView;

        public ViewHolder(View view) {
            this(view, false);
        }

        public GuidedAction getAction() {
            return this.mAction;
        }

        public ImageView getCheckmarkView() {
            return this.mCheckmarkView;
        }

        public ImageView getChevronView() {
            return this.mChevronView;
        }

        public View getContentView() {
            return this.mContentView;
        }

        public TextView getDescriptionView() {
            return this.mDescriptionView;
        }

        public EditText getEditableDescriptionView() {
            TextView textView = this.mDescriptionView;
            if (textView instanceof EditText) {
                return (EditText) textView;
            }
            return null;
        }

        public EditText getEditableTitleView() {
            TextView textView = this.mTitleView;
            if (textView instanceof EditText) {
                return (EditText) textView;
            }
            return null;
        }

        public View getEditingView() {
            int i = this.mEditingMode;
            if (i == 1) {
                return this.mTitleView;
            }
            if (i == 2) {
                return this.mDescriptionView;
            }
            if (i != 3) {
                return null;
            }
            return this.mActivatorView;
        }

        @Override // androidx.leanback.widget.FacetProvider
        public Object getFacet(Class<?> cls) {
            if (cls == ItemAlignmentFacet.class) {
                return GuidedActionsStylist.sGuidedActionItemAlignFacet;
            }
            return null;
        }

        public ImageView getIconView() {
            return this.mIconView;
        }

        public TextView getTitleView() {
            return this.mTitleView;
        }

        public boolean isInEditing() {
            return this.mEditingMode != 0;
        }

        public boolean isInEditingActivatorView() {
            return this.mEditingMode == 3;
        }

        public boolean isInEditingDescription() {
            return this.mEditingMode == 2;
        }

        public boolean isInEditingText() {
            int i = this.mEditingMode;
            return i == 1 || i == 2;
        }

        public boolean isInEditingTitle() {
            return this.mEditingMode == 1;
        }

        public boolean isSubAction() {
            return this.mIsSubAction;
        }

        public final void press(boolean z) {
            Animator animator = this.mPressAnimator;
            if (animator != null) {
                animator.cancel();
                this.mPressAnimator = null;
            }
            int i = z ? R.attr.guidedActionPressedAnimation : R.attr.guidedActionUnpressedAnimation;
            Context context = this.itemView.getContext();
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(i, typedValue, true)) {
                Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, typedValue.resourceId);
                this.mPressAnimator = animatorLoadAnimator;
                animatorLoadAnimator.setTarget(this.itemView);
                this.mPressAnimator.addListener(new AnimatorListenerAdapter() { // from class: androidx.leanback.widget.GuidedActionsStylist.ViewHolder.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        ViewHolder.this.mPressAnimator = null;
                    }
                });
                this.mPressAnimator.start();
            }
        }

        public ViewHolder(View view, boolean z) {
            super(view);
            this.mEditingMode = 0;
            View.AccessibilityDelegate accessibilityDelegate = new View.AccessibilityDelegate() { // from class: androidx.leanback.widget.GuidedActionsStylist.ViewHolder.1
                @Override // android.view.View.AccessibilityDelegate
                public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
                    super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
                    GuidedAction guidedAction = ViewHolder.this.mAction;
                    accessibilityEvent.setChecked(guidedAction != null && guidedAction.isChecked());
                }

                @Override // android.view.View.AccessibilityDelegate
                public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                    GuidedAction guidedAction = ViewHolder.this.mAction;
                    accessibilityNodeInfo.setCheckable((guidedAction == null || guidedAction.getCheckSetId() == 0) ? false : true);
                    GuidedAction guidedAction2 = ViewHolder.this.mAction;
                    accessibilityNodeInfo.setChecked(guidedAction2 != null && guidedAction2.isChecked());
                }
            };
            this.mContentView = view.findViewById(R.id.guidedactions_item_content);
            this.mTitleView = (TextView) view.findViewById(R.id.guidedactions_item_title);
            this.mActivatorView = view.findViewById(R.id.guidedactions_activator_item);
            this.mDescriptionView = (TextView) view.findViewById(R.id.guidedactions_item_description);
            this.mIconView = (ImageView) view.findViewById(R.id.guidedactions_item_icon);
            this.mCheckmarkView = (ImageView) view.findViewById(R.id.guidedactions_item_checkmark);
            this.mChevronView = (ImageView) view.findViewById(R.id.guidedactions_item_chevron);
            this.mIsSubAction = z;
            view.setAccessibilityDelegate(accessibilityDelegate);
        }
    }

    static {
        ItemAlignmentFacet itemAlignmentFacet = new ItemAlignmentFacet();
        sGuidedActionItemAlignFacet = itemAlignmentFacet;
        ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef = new ItemAlignmentFacet.ItemAlignmentDef();
        itemAlignmentDef.setItemAlignmentViewId(R.id.guidedactions_item_title);
        itemAlignmentDef.setAlignedToTextViewBaseline(true);
        itemAlignmentDef.setItemAlignmentOffset(0);
        itemAlignmentDef.setItemAlignmentOffsetWithPadding(true);
        itemAlignmentDef.setItemAlignmentOffsetPercent(0.0f);
        itemAlignmentFacet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{itemAlignmentDef});
    }

    private int getDescriptionMaxHeight(Context context, TextView textView) {
        return (this.mDisplayHeight - (this.mVerticalPadding * 2)) - (textView.getLineHeight() * (this.mTitleMaxLines * 2));
    }

    private static int getDimension(Context context, TypedValue typedValue, int i) {
        context.getTheme().resolveAttribute(i, typedValue, true);
        return context.getResources().getDimensionPixelSize(typedValue.resourceId);
    }

    private static float getFloat(Context context, TypedValue typedValue, int i) {
        context.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.getFloat();
    }

    private static float getFloatValue(Resources resources, TypedValue typedValue, int i) {
        resources.getValue(i, typedValue, true);
        return typedValue.getFloat();
    }

    private static int getInteger(Context context, TypedValue typedValue, int i) {
        context.getTheme().resolveAttribute(i, typedValue, true);
        return context.getResources().getInteger(typedValue.resourceId);
    }

    private boolean setIcon(ImageView imageView, GuidedAction guidedAction) {
        Drawable icon;
        if (imageView != null) {
            icon = guidedAction.getIcon();
            if (icon != null) {
                imageView.setImageLevel(icon.getLevel());
                imageView.setImageDrawable(icon);
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        } else {
            icon = null;
        }
        return icon != null;
    }

    private static void setMaxLines(TextView textView, int i) {
        if (i == 1) {
            textView.setSingleLine(true);
        } else {
            textView.setSingleLine(false);
            textView.setMaxLines(i);
        }
    }

    private void setupNextImeOptions(EditText editText) {
        if (editText != null) {
            editText.setImeOptions(5);
        }
    }

    private void updateChevronAndVisibility(ViewHolder viewHolder) {
        if (!viewHolder.isSubAction()) {
            if (this.mExpandedAction == null) {
                viewHolder.itemView.setVisibility(0);
                viewHolder.itemView.setTranslationY(0.0f);
                View view = viewHolder.mActivatorView;
                if (view != null) {
                    view.setActivated(false);
                    View view2 = viewHolder.itemView;
                    if (view2 instanceof GuidedActionItemContainer) {
                        ((GuidedActionItemContainer) view2).setFocusOutAllowed(true);
                    }
                }
            } else if (viewHolder.getAction() == this.mExpandedAction) {
                viewHolder.itemView.setVisibility(0);
                if (viewHolder.getAction().hasSubActions()) {
                    viewHolder.itemView.setTranslationY(((int) ((this.mKeyLinePercent * this.mActionsGridView.getHeight()) / 100.0f)) - viewHolder.itemView.getBottom());
                } else if (viewHolder.mActivatorView != null) {
                    viewHolder.itemView.setTranslationY(0.0f);
                    viewHolder.mActivatorView.setActivated(true);
                    View view3 = viewHolder.itemView;
                    if (view3 instanceof GuidedActionItemContainer) {
                        ((GuidedActionItemContainer) view3).setFocusOutAllowed(false);
                    }
                }
            } else {
                viewHolder.itemView.setVisibility(4);
                viewHolder.itemView.setTranslationY(0.0f);
            }
        }
        if (viewHolder.mChevronView != null) {
            onBindChevronView(viewHolder, viewHolder.getAction());
        }
    }

    public void collapseAction(boolean z) {
        if (isInExpandTransition() || this.mExpandedAction == null) {
            return;
        }
        boolean z2 = isExpandTransitionSupported() && z;
        int iIndexOf = ((GuidedActionAdapter) getActionsGridView().getAdapter()).indexOf(this.mExpandedAction);
        if (iIndexOf < 0) {
            return;
        }
        if (this.mExpandedAction.hasEditableActivatorView()) {
            setEditingMode((ViewHolder) getActionsGridView().findViewHolderForPosition(iIndexOf), false, z2);
        } else {
            startExpanded(null, z2);
        }
    }

    public void expandAction(GuidedAction guidedAction, boolean z) {
        int iIndexOf;
        if (isInExpandTransition() || this.mExpandedAction != null || (iIndexOf = ((GuidedActionAdapter) getActionsGridView().getAdapter()).indexOf(guidedAction)) < 0) {
            return;
        }
        if (isExpandTransitionSupported() && z) {
            getActionsGridView().setSelectedPosition(iIndexOf, new ViewHolderTask() { // from class: androidx.leanback.widget.GuidedActionsStylist.5
                @Override // androidx.leanback.widget.ViewHolderTask
                public void run(RecyclerView.ViewHolder viewHolder) {
                    ViewHolder viewHolder2 = (ViewHolder) viewHolder;
                    if (viewHolder2.getAction().hasEditableActivatorView()) {
                        GuidedActionsStylist.this.setEditingMode(viewHolder2, true, true);
                    } else {
                        GuidedActionsStylist.this.startExpanded(viewHolder2, true);
                    }
                }
            });
            return;
        }
        getActionsGridView().setSelectedPosition(iIndexOf, new ViewHolderTask() { // from class: androidx.leanback.widget.GuidedActionsStylist.4
            @Override // androidx.leanback.widget.ViewHolderTask
            public void run(RecyclerView.ViewHolder viewHolder) {
                ViewHolder viewHolder2 = (ViewHolder) viewHolder;
                if (viewHolder2.getAction().hasEditableActivatorView()) {
                    GuidedActionsStylist.this.setEditingMode(viewHolder2, true, false);
                } else {
                    GuidedActionsStylist.this.onUpdateExpandedViewHolder(viewHolder2);
                }
            }
        });
        if (guidedAction.hasSubActions()) {
            onUpdateSubActionsGridView(guidedAction, true);
        }
    }

    public VerticalGridView getActionsGridView() {
        return this.mActionsGridView;
    }

    public GuidedAction getExpandedAction() {
        return this.mExpandedAction;
    }

    public int getItemViewType(GuidedAction guidedAction) {
        return guidedAction instanceof GuidedDatePickerAction ? 1 : 0;
    }

    public final int getKeyLine() {
        return (int) ((this.mKeyLinePercent * this.mActionsGridView.getHeight()) / 100.0f);
    }

    public VerticalGridView getSubActionsGridView() {
        return this.mSubActionsGridView;
    }

    public final boolean isBackKeyToCollapseActivatorView() {
        return this.mBackToCollapseActivatorView;
    }

    public final boolean isBackKeyToCollapseSubActions() {
        return this.mBackToCollapseSubActions;
    }

    public boolean isButtonActions() {
        return this.mButtonActions;
    }

    public boolean isExpandTransitionSupported() {
        return true;
    }

    public boolean isExpanded() {
        return this.mExpandedAction != null;
    }

    public boolean isInExpandTransition() {
        return this.mExpandTransition != null;
    }

    public boolean isSubActionsExpanded() {
        GuidedAction guidedAction = this.mExpandedAction;
        return guidedAction != null && guidedAction.hasSubActions();
    }

    public void onAnimateItemChecked(ViewHolder viewHolder, boolean z) {
        KeyEvent.Callback callback = viewHolder.mCheckmarkView;
        if (callback instanceof Checkable) {
            ((Checkable) callback).setChecked(z);
        }
    }

    public void onAnimateItemFocused(ViewHolder viewHolder, boolean z) {
    }

    public void onAnimateItemPressed(ViewHolder viewHolder, boolean z) {
        viewHolder.press(z);
    }

    public void onAnimateItemPressedCancelled(ViewHolder viewHolder) {
        viewHolder.press(false);
    }

    public void onBindActivatorView(ViewHolder viewHolder, GuidedAction guidedAction) {
        if (guidedAction instanceof GuidedDatePickerAction) {
            GuidedDatePickerAction guidedDatePickerAction = (GuidedDatePickerAction) guidedAction;
            DatePicker datePicker = (DatePicker) viewHolder.mActivatorView;
            datePicker.setDatePickerFormat(guidedDatePickerAction.getDatePickerFormat());
            if (guidedDatePickerAction.getMinDate() != Long.MIN_VALUE) {
                datePicker.setMinDate(guidedDatePickerAction.getMinDate());
            }
            if (guidedDatePickerAction.getMaxDate() != Long.MAX_VALUE) {
                datePicker.setMaxDate(guidedDatePickerAction.getMaxDate());
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(guidedDatePickerAction.getDate());
            datePicker.updateDate(calendar.get(1), calendar.get(2), calendar.get(5), false);
        }
    }

    public void onBindCheckMarkView(ViewHolder viewHolder, GuidedAction guidedAction) {
        if (guidedAction.getCheckSetId() == 0) {
            viewHolder.mCheckmarkView.setVisibility(8);
            return;
        }
        viewHolder.mCheckmarkView.setVisibility(0);
        int i = guidedAction.getCheckSetId() == -1 ? android.R.attr.listChoiceIndicatorMultiple : android.R.attr.listChoiceIndicatorSingle;
        Context context = viewHolder.mCheckmarkView.getContext();
        TypedValue typedValue = new TypedValue();
        viewHolder.mCheckmarkView.setImageDrawable(context.getTheme().resolveAttribute(i, typedValue, true) ? ContextCompat.getDrawable(context, typedValue.resourceId) : null);
        KeyEvent.Callback callback = viewHolder.mCheckmarkView;
        if (callback instanceof Checkable) {
            ((Checkable) callback).setChecked(guidedAction.isChecked());
        }
    }

    public void onBindChevronView(ViewHolder viewHolder, GuidedAction guidedAction) {
        boolean zHasNext = guidedAction.hasNext();
        boolean zHasSubActions = guidedAction.hasSubActions();
        if (!zHasNext && !zHasSubActions) {
            viewHolder.mChevronView.setVisibility(8);
            return;
        }
        viewHolder.mChevronView.setVisibility(0);
        viewHolder.mChevronView.setAlpha(guidedAction.isEnabled() ? this.mEnabledChevronAlpha : this.mDisabledChevronAlpha);
        if (zHasNext) {
            ViewGroup viewGroup = this.mMainView;
            viewHolder.mChevronView.setRotation((viewGroup == null || viewGroup.getLayoutDirection() != 1) ? 0.0f : 180.0f);
        } else if (guidedAction == this.mExpandedAction) {
            viewHolder.mChevronView.setRotation(270.0f);
        } else {
            viewHolder.mChevronView.setRotation(90.0f);
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, GuidedAction guidedAction) {
        viewHolder.mAction = guidedAction;
        TextView textView = viewHolder.mTitleView;
        if (textView != null) {
            textView.setInputType(guidedAction.getInputType());
            viewHolder.mTitleView.setText(guidedAction.getTitle());
            viewHolder.mTitleView.setAlpha(guidedAction.isEnabled() ? this.mEnabledTextAlpha : this.mDisabledTextAlpha);
            viewHolder.mTitleView.setFocusable(false);
            viewHolder.mTitleView.setClickable(false);
            viewHolder.mTitleView.setLongClickable(false);
            int i = Build.VERSION.SDK_INT;
            if (i >= 28) {
                if (guidedAction.isEditable()) {
                    viewHolder.mTitleView.setAutofillHints(guidedAction.getAutofillHints());
                } else {
                    viewHolder.mTitleView.setAutofillHints(null);
                }
            } else if (i >= 26) {
                viewHolder.mTitleView.setImportantForAutofill(2);
            }
        }
        TextView textView2 = viewHolder.mDescriptionView;
        if (textView2 != null) {
            textView2.setInputType(guidedAction.getDescriptionInputType());
            viewHolder.mDescriptionView.setText(guidedAction.getDescription());
            viewHolder.mDescriptionView.setVisibility(TextUtils.isEmpty(guidedAction.getDescription()) ? 8 : 0);
            viewHolder.mDescriptionView.setAlpha(guidedAction.isEnabled() ? this.mEnabledDescriptionAlpha : this.mDisabledDescriptionAlpha);
            viewHolder.mDescriptionView.setFocusable(false);
            viewHolder.mDescriptionView.setClickable(false);
            viewHolder.mDescriptionView.setLongClickable(false);
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 28) {
                if (guidedAction.isDescriptionEditable()) {
                    viewHolder.mDescriptionView.setAutofillHints(guidedAction.getAutofillHints());
                } else {
                    viewHolder.mDescriptionView.setAutofillHints(null);
                }
            } else if (i2 >= 26) {
                viewHolder.mTitleView.setImportantForAutofill(2);
            }
        }
        if (viewHolder.mCheckmarkView != null) {
            onBindCheckMarkView(viewHolder, guidedAction);
        }
        setIcon(viewHolder.mIconView, guidedAction);
        if (guidedAction.hasMultilineDescription()) {
            TextView textView3 = viewHolder.mTitleView;
            if (textView3 != null) {
                setMaxLines(textView3, this.mTitleMaxLines);
                TextView textView4 = viewHolder.mTitleView;
                textView4.setInputType(textView4.getInputType() | 131072);
                TextView textView5 = viewHolder.mDescriptionView;
                if (textView5 != null) {
                    textView5.setInputType(textView5.getInputType() | 131072);
                    viewHolder.mDescriptionView.setMaxHeight(getDescriptionMaxHeight(viewHolder.itemView.getContext(), viewHolder.mTitleView));
                }
            }
        } else {
            TextView textView6 = viewHolder.mTitleView;
            if (textView6 != null) {
                setMaxLines(textView6, this.mTitleMinLines);
            }
            TextView textView7 = viewHolder.mDescriptionView;
            if (textView7 != null) {
                setMaxLines(textView7, this.mDescriptionMinLines);
            }
        }
        if (viewHolder.mActivatorView != null) {
            onBindActivatorView(viewHolder, guidedAction);
        }
        setEditingMode(viewHolder, false, false);
        if (guidedAction.isFocusable()) {
            viewHolder.itemView.setFocusable(true);
            ((ViewGroup) viewHolder.itemView).setDescendantFocusability(131072);
        } else {
            viewHolder.itemView.setFocusable(false);
            ((ViewGroup) viewHolder.itemView).setDescendantFocusability(393216);
        }
        setupNextImeOptions(viewHolder.getEditableTitleView());
        setupNextImeOptions(viewHolder.getEditableDescriptionView());
        updateChevronAndVisibility(viewHolder);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        float f = layoutInflater.getContext().getTheme().obtainStyledAttributes(R.styleable.LeanbackGuidedStepTheme).getFloat(R.styleable.LeanbackGuidedStepTheme_guidedStepKeyline, 40.0f);
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(onProvideLayoutId(), viewGroup, false);
        this.mMainView = viewGroup2;
        this.mContentView = viewGroup2.findViewById(this.mButtonActions ? R.id.guidedactions_content2 : R.id.guidedactions_content);
        this.mBgView = this.mMainView.findViewById(this.mButtonActions ? R.id.guidedactions_list_background2 : R.id.guidedactions_list_background);
        ViewGroup viewGroup3 = this.mMainView;
        if (viewGroup3 instanceof VerticalGridView) {
            this.mActionsGridView = (VerticalGridView) viewGroup3;
        } else {
            VerticalGridView verticalGridView = (VerticalGridView) viewGroup3.findViewById(this.mButtonActions ? R.id.guidedactions_list2 : R.id.guidedactions_list);
            this.mActionsGridView = verticalGridView;
            if (verticalGridView == null) {
                throw new IllegalStateException("No ListView exists.");
            }
            verticalGridView.setWindowAlignmentOffsetPercent(f);
            this.mActionsGridView.setWindowAlignment(0);
            if (!this.mButtonActions) {
                this.mSubActionsGridView = (VerticalGridView) this.mMainView.findViewById(R.id.guidedactions_sub_list);
                this.mSubActionsBackground = this.mMainView.findViewById(R.id.guidedactions_sub_list_background);
            }
        }
        this.mActionsGridView.setFocusable(false);
        this.mActionsGridView.setFocusableInTouchMode(false);
        Context context = this.mMainView.getContext();
        TypedValue typedValue = new TypedValue();
        this.mEnabledChevronAlpha = getFloat(context, typedValue, R.attr.guidedActionEnabledChevronAlpha);
        this.mDisabledChevronAlpha = getFloat(context, typedValue, R.attr.guidedActionDisabledChevronAlpha);
        this.mTitleMinLines = getInteger(context, typedValue, R.attr.guidedActionTitleMinLines);
        this.mTitleMaxLines = getInteger(context, typedValue, R.attr.guidedActionTitleMaxLines);
        this.mDescriptionMinLines = getInteger(context, typedValue, R.attr.guidedActionDescriptionMinLines);
        this.mVerticalPadding = getDimension(context, typedValue, R.attr.guidedActionVerticalPadding);
        this.mDisplayHeight = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
        this.mEnabledTextAlpha = getFloatValue(context.getResources(), typedValue, R.dimen.lb_guidedactions_item_unselected_text_alpha);
        this.mDisabledTextAlpha = getFloatValue(context.getResources(), typedValue, R.dimen.lb_guidedactions_item_disabled_text_alpha);
        this.mEnabledDescriptionAlpha = getFloatValue(context.getResources(), typedValue, R.dimen.lb_guidedactions_item_unselected_description_text_alpha);
        this.mDisabledDescriptionAlpha = getFloatValue(context.getResources(), typedValue, R.dimen.lb_guidedactions_item_disabled_description_text_alpha);
        this.mKeyLinePercent = GuidanceStylingRelativeLayout.getKeyLinePercent(context);
        View view = this.mContentView;
        if (view instanceof GuidedActionsRelativeLayout) {
            ((GuidedActionsRelativeLayout) view).setInterceptKeyEventListener(new GuidedActionsRelativeLayout.InterceptKeyEventListener() { // from class: androidx.leanback.widget.GuidedActionsStylist.1
                @Override // androidx.leanback.widget.GuidedActionsRelativeLayout.InterceptKeyEventListener
                public boolean onInterceptKeyEvent(KeyEvent keyEvent) {
                    GuidedAction guidedAction;
                    if (keyEvent.getKeyCode() != 4 || keyEvent.getAction() != 1 || (guidedAction = GuidedActionsStylist.this.mExpandedAction) == null) {
                        return false;
                    }
                    if ((!guidedAction.hasSubActions() || !GuidedActionsStylist.this.isBackKeyToCollapseSubActions()) && (!GuidedActionsStylist.this.mExpandedAction.hasEditableActivatorView() || !GuidedActionsStylist.this.isBackKeyToCollapseActivatorView())) {
                        return false;
                    }
                    GuidedActionsStylist.this.collapseAction(true);
                    return true;
                }
            });
        }
        return this.mMainView;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(onProvideItemLayoutId(), viewGroup, false), viewGroup == this.mSubActionsGridView);
    }

    public void onDestroyView() {
        this.mExpandedAction = null;
        this.mExpandTransition = null;
        this.mActionsGridView = null;
        this.mSubActionsGridView = null;
        this.mSubActionsBackground = null;
        this.mContentView = null;
        this.mBgView = null;
        this.mMainView = null;
    }

    public final void onEditActivatorView(final ViewHolder viewHolder, boolean z, boolean z2) {
        GuidedActionAdapter.EditListener editListener;
        if (z) {
            startExpanded(viewHolder, z2);
            viewHolder.itemView.setFocusable(false);
            viewHolder.mActivatorView.requestFocus();
            viewHolder.mActivatorView.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.GuidedActionsStylist.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (GuidedActionsStylist.this.isInExpandTransition()) {
                        return;
                    }
                    ((GuidedActionAdapter) GuidedActionsStylist.this.getActionsGridView().getAdapter()).performOnActionClick(viewHolder);
                }
            });
            return;
        }
        if (onUpdateActivatorView(viewHolder, viewHolder.getAction()) && (editListener = this.mEditListener) != null) {
            editListener.onGuidedActionEditedAndProceed(viewHolder.getAction());
        }
        viewHolder.itemView.setFocusable(true);
        viewHolder.itemView.requestFocus();
        startExpanded(null, z2);
        viewHolder.mActivatorView.setOnClickListener(null);
        viewHolder.mActivatorView.setClickable(false);
    }

    @Override // androidx.leanback.widget.FragmentAnimationProvider
    public void onImeAppearing(@NonNull List<Animator> list) {
    }

    @Override // androidx.leanback.widget.FragmentAnimationProvider
    public void onImeDisappearing(@NonNull List<Animator> list) {
    }

    public int onProvideItemLayoutId() {
        return R.layout.lb_guidedactions_item;
    }

    public int onProvideLayoutId() {
        return this.mButtonActions ? R.layout.lb_guidedbuttonactions : R.layout.lb_guidedactions;
    }

    public boolean onUpdateActivatorView(ViewHolder viewHolder, GuidedAction guidedAction) {
        if (!(guidedAction instanceof GuidedDatePickerAction)) {
            return false;
        }
        GuidedDatePickerAction guidedDatePickerAction = (GuidedDatePickerAction) guidedAction;
        DatePicker datePicker = (DatePicker) viewHolder.mActivatorView;
        if (guidedDatePickerAction.getDate() == datePicker.getDate()) {
            return false;
        }
        guidedDatePickerAction.setDate(datePicker.getDate());
        return true;
    }

    public void onUpdateExpandedViewHolder(ViewHolder viewHolder) {
        if (viewHolder == null) {
            this.mExpandedAction = null;
            this.mActionsGridView.setPruneChild(true);
        } else if (viewHolder.getAction() != this.mExpandedAction) {
            this.mExpandedAction = viewHolder.getAction();
            this.mActionsGridView.setPruneChild(false);
        }
        this.mActionsGridView.setAnimateChildLayout(false);
        int childCount = this.mActionsGridView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            VerticalGridView verticalGridView = this.mActionsGridView;
            updateChevronAndVisibility((ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i)));
        }
    }

    public final void onUpdateSubActionsGridView(GuidedAction guidedAction, boolean z) {
        VerticalGridView verticalGridView = this.mSubActionsGridView;
        if (verticalGridView != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) verticalGridView.getLayoutParams();
            GuidedActionAdapter guidedActionAdapter = (GuidedActionAdapter) this.mSubActionsGridView.getAdapter();
            if (z) {
                marginLayoutParams.topMargin = -2;
                marginLayoutParams.height = -1;
                this.mSubActionsGridView.setLayoutParams(marginLayoutParams);
                this.mSubActionsGridView.setVisibility(0);
                this.mSubActionsBackground.setVisibility(0);
                this.mSubActionsGridView.requestFocus();
                guidedActionAdapter.setActions(guidedAction.getSubActions());
                return;
            }
            marginLayoutParams.topMargin = this.mActionsGridView.getLayoutManager().findViewByPosition(((GuidedActionAdapter) this.mActionsGridView.getAdapter()).indexOf(guidedAction)).getBottom();
            marginLayoutParams.height = 0;
            this.mSubActionsGridView.setVisibility(4);
            this.mSubActionsBackground.setVisibility(4);
            this.mSubActionsGridView.setLayoutParams(marginLayoutParams);
            guidedActionAdapter.setActions(Collections.emptyList());
            this.mActionsGridView.requestFocus();
        }
    }

    public void openInEditMode(GuidedAction guidedAction) {
        final GuidedActionAdapter guidedActionAdapter = (GuidedActionAdapter) getActionsGridView().getAdapter();
        int iIndexOf = guidedActionAdapter.getActions().indexOf(guidedAction);
        if (iIndexOf < 0 || !guidedAction.isEditable()) {
            return;
        }
        getActionsGridView().setSelectedPosition(iIndexOf, new ViewHolderTask() { // from class: androidx.leanback.widget.GuidedActionsStylist.2
            @Override // androidx.leanback.widget.ViewHolderTask
            public void run(RecyclerView.ViewHolder viewHolder) {
                GuidedActionAdapter guidedActionAdapter2 = guidedActionAdapter;
                guidedActionAdapter2.mGroup.openIme(guidedActionAdapter2, (ViewHolder) viewHolder);
            }
        });
    }

    public void setAsButtonActions() {
        if (this.mMainView != null) {
            throw new IllegalStateException("setAsButtonActions() must be called before creating views");
        }
        this.mButtonActions = true;
    }

    public final void setBackKeyToCollapseActivatorView(boolean z) {
        this.mBackToCollapseActivatorView = z;
    }

    public final void setBackKeyToCollapseSubActions(boolean z) {
        this.mBackToCollapseSubActions = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setEditListener(GuidedActionAdapter.EditListener editListener) {
        this.mEditListener = editListener;
    }

    @Deprecated
    public void setEditingMode(ViewHolder viewHolder, GuidedAction guidedAction, boolean z) {
        if (z != viewHolder.isInEditing()) {
            isInExpandTransition();
        }
    }

    @Deprecated
    public void setExpandedViewHolder(ViewHolder viewHolder) {
        expandAction(viewHolder == null ? null : viewHolder.getAction(), isExpandTransitionSupported());
    }

    public final void startExpanded(ViewHolder viewHolder, boolean z) {
        ViewHolder viewHolder2;
        int childCount = this.mActionsGridView.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                viewHolder2 = null;
                break;
            }
            VerticalGridView verticalGridView = this.mActionsGridView;
            viewHolder2 = (ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i));
            if ((viewHolder == null && viewHolder2.itemView.getVisibility() == 0) || (viewHolder != null && viewHolder2.getAction() == viewHolder.getAction())) {
                break;
            } else {
                i++;
            }
        }
        if (viewHolder2 == null) {
            return;
        }
        boolean z2 = viewHolder != null;
        boolean zHasSubActions = viewHolder2.getAction().hasSubActions();
        if (z) {
            Object objCreateTransitionSet = TransitionHelper.createTransitionSet(false);
            View view = viewHolder2.itemView;
            Object objCreateFadeAndShortSlide = TransitionHelper.createFadeAndShortSlide(112, zHasSubActions ? view.getHeight() : view.getHeight() * 0.5f);
            TransitionHelper.setEpicenterCallback(objCreateFadeAndShortSlide, new TransitionEpicenterCallback() { // from class: androidx.leanback.widget.GuidedActionsStylist.6
                public Rect mRect = new Rect();

                @Override // androidx.leanback.transition.TransitionEpicenterCallback
                public Rect onGetEpicenter(Object obj) {
                    int keyLine = GuidedActionsStylist.this.getKeyLine();
                    this.mRect.set(0, keyLine, 0, keyLine);
                    return this.mRect;
                }
            });
            Object objCreateChangeTransform = TransitionHelper.createChangeTransform();
            Object objCreateChangeBounds = TransitionHelper.createChangeBounds(false);
            Object objCreateFadeTransition = TransitionHelper.createFadeTransition(3);
            Object objCreateChangeBounds2 = TransitionHelper.createChangeBounds(false);
            if (viewHolder == null) {
                TransitionHelper.setStartDelay(objCreateFadeAndShortSlide, 150L);
                TransitionHelper.setStartDelay(objCreateChangeTransform, 100L);
                TransitionHelper.setStartDelay(objCreateChangeBounds, 100L);
                TransitionHelper.setStartDelay(objCreateChangeBounds2, 100L);
            } else {
                TransitionHelper.setStartDelay(objCreateFadeTransition, 100L);
                TransitionHelper.setStartDelay(objCreateChangeBounds2, 50L);
                TransitionHelper.setStartDelay(objCreateChangeTransform, 50L);
                TransitionHelper.setStartDelay(objCreateChangeBounds, 50L);
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                VerticalGridView verticalGridView2 = this.mActionsGridView;
                ViewHolder viewHolder3 = (ViewHolder) verticalGridView2.getChildViewHolder(verticalGridView2.getChildAt(i2));
                if (viewHolder3 != viewHolder2) {
                    TransitionHelper.include(objCreateFadeAndShortSlide, viewHolder3.itemView);
                    TransitionHelper.exclude(objCreateFadeTransition, viewHolder3.itemView, true);
                } else if (zHasSubActions) {
                    TransitionHelper.include(objCreateChangeTransform, viewHolder3.itemView);
                    TransitionHelper.include(objCreateChangeBounds, viewHolder3.itemView);
                }
            }
            TransitionHelper.include(objCreateChangeBounds2, this.mSubActionsGridView);
            TransitionHelper.include(objCreateChangeBounds2, this.mSubActionsBackground);
            TransitionHelper.addTransition(objCreateTransitionSet, objCreateFadeAndShortSlide);
            if (zHasSubActions) {
                TransitionHelper.addTransition(objCreateTransitionSet, objCreateChangeTransform);
                TransitionHelper.addTransition(objCreateTransitionSet, objCreateChangeBounds);
            }
            TransitionHelper.addTransition(objCreateTransitionSet, objCreateFadeTransition);
            TransitionHelper.addTransition(objCreateTransitionSet, objCreateChangeBounds2);
            this.mExpandTransition = objCreateTransitionSet;
            TransitionHelper.addTransitionListener(objCreateTransitionSet, new TransitionListener() { // from class: androidx.leanback.widget.GuidedActionsStylist.7
                @Override // androidx.leanback.transition.TransitionListener
                public void onTransitionEnd(Object obj) {
                    GuidedActionsStylist.this.mExpandTransition = null;
                }
            });
            if (z2 && zHasSubActions) {
                int bottom = viewHolder.itemView.getBottom();
                VerticalGridView verticalGridView3 = this.mSubActionsGridView;
                verticalGridView3.offsetTopAndBottom(bottom - verticalGridView3.getTop());
                View view2 = this.mSubActionsBackground;
                view2.offsetTopAndBottom(bottom - view2.getTop());
            }
            TransitionHelper.beginDelayedTransition(this.mMainView, this.mExpandTransition);
        }
        onUpdateExpandedViewHolder(viewHolder);
        if (zHasSubActions) {
            onUpdateSubActionsGridView(viewHolder2.getAction(), z2);
        }
    }

    @Deprecated
    public void startExpandedTransition(ViewHolder viewHolder) {
        expandAction(viewHolder == null ? null : viewHolder.getAction(), isExpandTransitionSupported());
    }

    public int onProvideItemLayoutId(int i) {
        if (i == 0) {
            return onProvideItemLayoutId();
        }
        if (i == 1) {
            return R.layout.lb_guidedactions_datepicker_item;
        }
        throw new RuntimeException(Insets$$ExternalSyntheticOutline0.m("ViewType ", i, " not supported in GuidedActionsStylist"));
    }

    public final void setEditingMode(ViewHolder viewHolder, boolean z, boolean z2) {
        if (z == viewHolder.isInEditing() || isInExpandTransition()) {
            return;
        }
        GuidedAction action = viewHolder.getAction();
        TextView titleView = viewHolder.getTitleView();
        TextView descriptionView = viewHolder.getDescriptionView();
        if (!z) {
            if (titleView != null) {
                titleView.setText(action.getTitle());
            }
            if (descriptionView != null) {
                descriptionView.setText(action.getDescription());
            }
            int i = viewHolder.mEditingMode;
            if (i == 2) {
                if (descriptionView != null) {
                    descriptionView.setVisibility(TextUtils.isEmpty(action.getDescription()) ? 8 : 0);
                    descriptionView.setInputType(action.getDescriptionInputType());
                }
            } else if (i == 1) {
                if (titleView != null) {
                    titleView.setInputType(action.getInputType());
                }
            } else if (i == 3 && viewHolder.mActivatorView != null) {
                onEditActivatorView(viewHolder, z, z2);
            }
            viewHolder.mEditingMode = 0;
            return;
        }
        CharSequence editTitle = action.getEditTitle();
        if (titleView != null && editTitle != null) {
            titleView.setText(editTitle);
        }
        CharSequence editDescription = action.getEditDescription();
        if (descriptionView != null && editDescription != null) {
            descriptionView.setText(editDescription);
        }
        if (action.isDescriptionEditable()) {
            if (descriptionView != null) {
                descriptionView.setVisibility(0);
                descriptionView.setInputType(action.getDescriptionEditInputType());
            }
            viewHolder.mEditingMode = 2;
            return;
        }
        if (action.isEditable()) {
            if (titleView != null) {
                titleView.setInputType(action.getEditInputType());
            }
            viewHolder.mEditingMode = 1;
        } else if (viewHolder.mActivatorView != null) {
            onEditActivatorView(viewHolder, z, z2);
            viewHolder.mEditingMode = 3;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return onCreateViewHolder(viewGroup);
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(onProvideItemLayoutId(i), viewGroup, false), viewGroup == this.mSubActionsGridView);
    }
}
