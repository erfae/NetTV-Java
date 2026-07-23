package androidx.leanback.widget;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class InvisibleRowPresenter extends RowPresenter {
    public InvisibleRowPresenter() {
        setHeaderPresenter(null);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        RelativeLayout relativeLayout = new RelativeLayout(viewGroup.getContext());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        return new RowPresenter.ViewHolder(relativeLayout);
    }
}
