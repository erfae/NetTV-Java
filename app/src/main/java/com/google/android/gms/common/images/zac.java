package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Asserts;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zac implements Runnable {
    public final /* synthetic */ ImageManager zaa;
    private final Uri zab;

    @Nullable
    private final Bitmap zac;
    private final CountDownLatch zad;

    public zac(ImageManager imageManager, @Nullable Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
        this.zaa = imageManager;
        this.zab = uri;
        this.zac = bitmap;
        this.zad = countDownLatch;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Asserts.checkMainThread("OnBitmapLoadedRunnable must be executed in the main thread");
        Bitmap bitmap = this.zac;
        ImageManager.ImageReceiver imageReceiver = (ImageManager.ImageReceiver) this.zaa.zai.remove(this.zab);
        if (imageReceiver != null) {
            ArrayList arrayList = imageReceiver.zac;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zag zagVar = (zag) arrayList.get(i);
                Bitmap bitmap2 = this.zac;
                if (bitmap2 == null || bitmap == null) {
                    this.zaa.zaj.put(this.zab, Long.valueOf(SystemClock.elapsedRealtime()));
                    Context context = this.zaa.zad;
                    int i2 = ImageManager.$r8$clinit;
                    zagVar.zab(context, false);
                } else {
                    Context context2 = this.zaa.zad;
                    Objects.requireNonNull(zagVar);
                    Asserts.checkNotNull(bitmap2);
                    zagVar.zaa(new BitmapDrawable(context2.getResources(), bitmap2), false, false, true);
                }
                if (!(zagVar instanceof zaf)) {
                    this.zaa.zah.remove(zagVar);
                }
            }
        }
        this.zad.countDown();
        synchronized (ImageManager.zaa) {
            ImageManager.zab.remove(this.zab);
        }
    }
}
