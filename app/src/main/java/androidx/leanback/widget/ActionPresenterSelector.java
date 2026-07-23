package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.leanback.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class ActionPresenterSelector extends PresenterSelector {
    private final Presenter mOneLineActionPresenter;
    private final Presenter[] mPresenters;
    private final Presenter mTwoLineActionPresenter;

    public static abstract class ActionPresenter extends Presenter {
        @Override // androidx.leanback.widget.Presenter
        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
            ActionViewHolder actionViewHolder = (ActionViewHolder) viewHolder;
            Objects.requireNonNull(actionViewHolder);
            Drawable icon = ((Action) obj).getIcon();
            if (icon != null) {
                actionViewHolder.view.setPaddingRelative(actionViewHolder.view.getResources().getDimensionPixelSize(R.dimen.lb_action_with_icon_padding_start), 0, actionViewHolder.view.getResources().getDimensionPixelSize(R.dimen.lb_action_with_icon_padding_end), 0);
            } else {
                int dimensionPixelSize = actionViewHolder.view.getResources().getDimensionPixelSize(R.dimen.lb_action_padding_horizontal);
                actionViewHolder.view.setPaddingRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
            }
            if (actionViewHolder.mLayoutDirection == 1) {
                actionViewHolder.mButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, icon, (Drawable) null);
            } else {
                actionViewHolder.mButton.setCompoundDrawablesWithIntrinsicBounds(icon, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }

        @Override // androidx.leanback.widget.Presenter
        public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
            ActionViewHolder actionViewHolder = (ActionViewHolder) viewHolder;
            actionViewHolder.mButton.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            actionViewHolder.view.setPadding(0, 0, 0, 0);
        }
    }

    public static class ActionViewHolder extends Presenter.ViewHolder {
        public Button mButton;
        public int mLayoutDirection;

        public ActionViewHolder(View view, int i) {
            super(view);
            this.mButton = (Button) view.findViewById(R.id.lb_action_button);
            this.mLayoutDirection = i;
        }
    }

    public static class OneLineActionPresenter extends ActionPresenter {
        @Override // androidx.leanback.widget.ActionPresenterSelector.ActionPresenter, androidx.leanback.widget.Presenter
        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
            super.onBindViewHolder(viewHolder, obj);
            ((ActionViewHolder) viewHolder).mButton.setText(((Action) obj).getLabel1());
        }

        @Override // androidx.leanback.widget.Presenter
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
            return new ActionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_action_1_line, viewGroup, false), viewGroup.getLayoutDirection());
        }
    }

    public static class TwoLineActionPresenter extends ActionPresenter {
        @Override // androidx.leanback.widget.ActionPresenterSelector.ActionPresenter, androidx.leanback.widget.Presenter
        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
            super.onBindViewHolder(viewHolder, obj);
            Action action = (Action) obj;
            ActionViewHolder actionViewHolder = (ActionViewHolder) viewHolder;
            CharSequence label1 = action.getLabel1();
            CharSequence label2 = action.getLabel2();
            if (TextUtils.isEmpty(label1)) {
                actionViewHolder.mButton.setText(label2);
                return;
            }
            if (TextUtils.isEmpty(label2)) {
                actionViewHolder.mButton.setText(label1);
                return;
            }
            actionViewHolder.mButton.setText(((Object) label1) + "\n" + ((Object) label2));
        }

        @Override // androidx.leanback.widget.Presenter
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
            return new ActionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_action_2_lines, viewGroup, false), viewGroup.getLayoutDirection());
        }
    }

    public ActionPresenterSelector() {
        OneLineActionPresenter oneLineActionPresenter = new OneLineActionPresenter();
        this.mOneLineActionPresenter = oneLineActionPresenter;
        TwoLineActionPresenter twoLineActionPresenter = new TwoLineActionPresenter();
        this.mTwoLineActionPresenter = twoLineActionPresenter;
        this.mPresenters = new Presenter[]{oneLineActionPresenter, twoLineActionPresenter};
    }

    @Override // androidx.leanback.widget.PresenterSelector
    public Presenter getPresenter(Object obj) {
        return TextUtils.isEmpty(((Action) obj).getLabel2()) ? this.mOneLineActionPresenter : this.mTwoLineActionPresenter;
    }

    @Override // androidx.leanback.widget.PresenterSelector
    public Presenter[] getPresenters() {
        return this.mPresenters;
    }
}
