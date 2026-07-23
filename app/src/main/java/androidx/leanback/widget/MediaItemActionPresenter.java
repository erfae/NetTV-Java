package androidx.leanback.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
class MediaItemActionPresenter extends Presenter {

    public static class ViewHolder extends Presenter.ViewHolder {
        public final ImageView mIcon;

        public ViewHolder(View view) {
            super(view);
            this.mIcon = (ImageView) view.findViewById(R.id.actionIcon);
        }

        public ImageView getIcon() {
            return this.mIcon;
        }
    }

    @Override // androidx.leanback.widget.Presenter
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        ((ViewHolder) viewHolder).getIcon().setImageDrawable(((MultiActionsProvider.MultiAction) obj).getCurrentDrawable());
    }

    @Override // androidx.leanback.widget.Presenter
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_row_media_item_action, viewGroup, false));
    }

    @Override // androidx.leanback.widget.Presenter
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }
}
