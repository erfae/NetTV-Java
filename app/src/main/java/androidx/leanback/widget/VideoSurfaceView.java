package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class VideoSurfaceView extends SurfaceView {
    public VideoSurfaceView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public void setTransitionVisibility(int i) {
    }

    public VideoSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VideoSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
