package com.google.android.gms.common.images;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import com.google.android.gms.common.images.ImageManager.ImageReceiver;
import com.google.android.gms.common.internal.Asserts;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zab implements Runnable {
    public final /* synthetic */ ImageManager zaa;
    private final zag zab;

    public zab(ImageManager imageManager, zag zagVar) {
        this.zaa = imageManager;
        this.zab = zagVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
        ImageManager.ImageReceiver imageReceiver = (ImageManager.ImageReceiver) this.zaa.zah.get(this.zab);
        if (imageReceiver != null) {
            this.zaa.zah.remove(this.zab);
            imageReceiver.zac(this.zab);
        }
        zag zagVar = this.zab;
        zad zadVar = zagVar.zaa;
        Uri uri = zadVar.zaa;
        if (uri == null) {
            Context context = this.zaa.zad;
            int i = ImageManager.$r8$clinit;
            zagVar.zab(context, true);
            return;
        }
        Long l = (Long) this.zaa.zaj.get(uri);
        if (l != null) {
            if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                zag zagVar2 = this.zab;
                Context context2 = this.zaa.zad;
                int i2 = ImageManager.$r8$clinit;
                zagVar2.zab(context2, true);
                return;
            }
            this.zaa.zaj.remove(zadVar.zaa);
        }
        this.zab.zaa(null, false, true, false);
        ImageManager.ImageReceiver imageReceiver2 = (ImageManager.ImageReceiver) this.zaa.zai.get(zadVar.zaa);
        if (imageReceiver2 == null) {
            imageReceiver2 = this.zaa.new ImageReceiver(zadVar.zaa);
            this.zaa.zai.put(zadVar.zaa, imageReceiver2);
        }
        imageReceiver2.zab(this.zab);
        zag zagVar3 = this.zab;
        if (!(zagVar3 instanceof zaf)) {
            this.zaa.zah.put(zagVar3, imageReceiver2);
        }
        synchronized (ImageManager.zaa) {
            if (!ImageManager.zab.contains(zadVar.zaa)) {
                ImageManager.zab.add(zadVar.zaa);
                imageReceiver2.zad();
            }
        }
    }
}
