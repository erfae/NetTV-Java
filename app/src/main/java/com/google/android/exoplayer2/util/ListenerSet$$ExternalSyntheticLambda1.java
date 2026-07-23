package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ListenerSet$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ListenerSet$$ExternalSyntheticLambda1(DrmSessionEventListener.EventDispatcher eventDispatcher, DrmSessionEventListener drmSessionEventListener, int i) {
        this.f$0 = eventDispatcher;
        this.f$2 = drmSessionEventListener;
        this.f$1 = i;
    }

    public /* synthetic */ ListenerSet$$ExternalSyntheticLambda1(CopyOnWriteArraySet copyOnWriteArraySet, int i, ListenerSet.Event event) {
        this.f$0 = copyOnWriteArraySet;
        this.f$1 = i;
        this.f$2 = event;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ListenerSet.lambda$queueEvent$0((CopyOnWriteArraySet) this.f$0, this.f$1, (ListenerSet.Event) this.f$2);
                break;
            default:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmSessionAcquired$0((DrmSessionEventListener) this.f$2, this.f$1);
                break;
        }
    }
}
