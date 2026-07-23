package eightbitlab.com.blurview;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
public interface BlurAlgorithm {
    Bitmap blur(Bitmap bitmap, float f);

    boolean canModifyBitmap();

    void destroy();

    @NonNull
    Bitmap.Config getSupportedBitmapConfig();
}
