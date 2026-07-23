package androidx.leanback.widget;

import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public class DetailsParallax extends RecyclerViewParallax {
    public final RecyclerViewParallax.ChildPositionProperty mFrameBottom;
    public final RecyclerViewParallax.ChildPositionProperty mFrameTop;

    public DetailsParallax() {
        RecyclerViewParallax.ChildPositionProperty childPositionPropertyAdapterPosition = addProperty("overviewRowTop").adapterPosition(0);
        int i = R.id.details_frame;
        this.mFrameTop = childPositionPropertyAdapterPosition.viewId(i);
        this.mFrameBottom = addProperty("overviewRowBottom").adapterPosition(0).viewId(i).fraction(1.0f);
    }

    public Parallax.IntProperty getOverviewRowBottom() {
        return this.mFrameBottom;
    }

    public Parallax.IntProperty getOverviewRowTop() {
        return this.mFrameTop;
    }
}
