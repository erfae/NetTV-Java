package com.google.android.exoplayer2.util;

import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.media.AudioTrack;
import android.view.View;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.nettv.livestore.activities.LiveActivity;
import com.nettv.livestore.activities.LiveChannelActivity;
import com.nettv.livestore.activities.mobile.LiveChannelMobileActivity;
import com.nettv.livestore.activities.mobile.LiveMobileActivity;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Util$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ Util$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Util.lambda$transformFutureAsync$1((SettableFuture) this.f$0, (ListenableFuture) this.f$1);
                break;
            case 1:
                ((ViewTransition) this.f$0).lambda$applyTransition$0((View[]) this.f$1);
                break;
            case 2:
                ((ResourcesCompat.FontCallback) this.f$0).lambda$callbackSuccessAsync$0((Typeface) this.f$1);
                break;
            case 3:
                ((AudioRendererEventListener.EventDispatcher) this.f$0).lambda$decoderReleased$5((String) this.f$1);
                break;
            case 4:
                DefaultAudioSink.lambda$releaseAudioTrackAsync$0((AudioTrack) this.f$0, (ConditionVariable) this.f$1);
                break;
            case 5:
                ((NetworkTypeObserver) this.f$0).lambda$register$0((NetworkTypeObserver.Listener) this.f$1);
                break;
            case 6:
                ((VideoRendererEventListener.EventDispatcher) this.f$0).lambda$decoderReleased$7((String) this.f$1);
                break;
            case 7:
                ((VideoRendererEventListener.EventDispatcher) this.f$0).lambda$videoSizeChanged$5((VideoSize) this.f$1);
                break;
            case 8:
                ((VideoRendererEventListener.EventDispatcher) this.f$0).lambda$videoCodecError$9((Exception) this.f$1);
                break;
            case 9:
                ((SphericalGLSurfaceView) this.f$0).lambda$onSurfaceTextureAvailable$1((SurfaceTexture) this.f$1);
                break;
            case 10:
                ((LiveActivity) this.f$0).lambda$epgTimer$4((String) this.f$1);
                break;
            case 11:
                ((LiveChannelActivity) this.f$0).lambda$epgTimer$3((String) this.f$1);
                break;
            case 12:
                ((LiveChannelMobileActivity) this.f$0).lambda$epgTimer$3((String) this.f$1);
                break;
            default:
                ((LiveMobileActivity) this.f$0).lambda$epgTimer$4((String) this.f$1);
                break;
        }
    }
}
