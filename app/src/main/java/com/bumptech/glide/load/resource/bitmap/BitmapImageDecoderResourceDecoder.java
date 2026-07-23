package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.resource.ImageDecoderResourceDecoder;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(api = 28)
public final class BitmapImageDecoderResourceDecoder extends ImageDecoderResourceDecoder<Bitmap> {
    private static final String TAG = "BitmapImageDecoder";
    private final BitmapPool bitmapPool = new BitmapPoolAdapter();

    @Override // com.bumptech.glide.load.resource.ImageDecoderResourceDecoder
    public final Resource<Bitmap> decode(ImageDecoder.Source source, int i, int i2, ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        Bitmap bitmapDecodeBitmap = ImageDecoder.decodeBitmap(source, onHeaderDecodedListener);
        if (Log.isLoggable(TAG, 2)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Decoded [");
            sbM.append(bitmapDecodeBitmap.getWidth());
            sbM.append("x");
            sbM.append(bitmapDecodeBitmap.getHeight());
            sbM.append("] for [");
            sbM.append(i);
            sbM.append("x");
            sbM.append(i2);
            sbM.append("]");
            Log.v(TAG, sbM.toString());
        }
        return new BitmapResource(bitmapDecodeBitmap, this.bitmapPool);
    }
}
