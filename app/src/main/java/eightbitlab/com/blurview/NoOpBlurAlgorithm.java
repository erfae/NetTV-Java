package eightbitlab.com.blurview;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
class NoOpBlurAlgorithm implements BlurAlgorithm {
    @Override // eightbitlab.com.blurview.BlurAlgorithm
    public Bitmap blur(Bitmap bitmap, float f) {
        return bitmap;
    }

    @Override // eightbitlab.com.blurview.BlurAlgorithm
    public boolean canModifyBitmap() {
        return true;
    }

    @Override // eightbitlab.com.blurview.BlurAlgorithm
    public void destroy() {
    }

    @Override // eightbitlab.com.blurview.BlurAlgorithm
    @NonNull
    public Bitmap.Config getSupportedBitmapConfig() {
        return Bitmap.Config.ARGB_8888;
    }
}
