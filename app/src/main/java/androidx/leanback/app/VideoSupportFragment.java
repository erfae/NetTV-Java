package androidx.leanback.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public class VideoSupportFragment extends PlaybackSupportFragment {
    public SurfaceHolder.Callback mMediaPlaybackCallback;
    public int mState = 0;
    public SurfaceView mVideoSurface;

    public SurfaceView getSurfaceView() {
        return this.mVideoSurface;
    }

    @Override // androidx.leanback.app.PlaybackSupportFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        SurfaceView surfaceView = (SurfaceView) LayoutInflater.from(getContext()).inflate(R.layout.lb_video_surface, viewGroup2, false);
        this.mVideoSurface = surfaceView;
        viewGroup2.addView(surfaceView, 0);
        this.mVideoSurface.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: androidx.leanback.app.VideoSupportFragment.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                SurfaceHolder.Callback callback = VideoSupportFragment.this.mMediaPlaybackCallback;
                if (callback != null) {
                    callback.surfaceChanged(surfaceHolder, i, i2, i3);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                SurfaceHolder.Callback callback = VideoSupportFragment.this.mMediaPlaybackCallback;
                if (callback != null) {
                    callback.surfaceCreated(surfaceHolder);
                }
                VideoSupportFragment.this.mState = 1;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                SurfaceHolder.Callback callback = VideoSupportFragment.this.mMediaPlaybackCallback;
                if (callback != null) {
                    callback.surfaceDestroyed(surfaceHolder);
                }
                VideoSupportFragment.this.mState = 0;
            }
        });
        setBackgroundType(2);
        return viewGroup2;
    }

    @Override // androidx.leanback.app.PlaybackSupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mVideoSurface = null;
        this.mState = 0;
        super.onDestroyView();
    }

    @Override // androidx.leanback.app.PlaybackSupportFragment
    public final void onVideoSizeChanged(int i, int i2) {
        int width = getView().getWidth();
        int height = getView().getHeight();
        ViewGroup.LayoutParams layoutParams = this.mVideoSurface.getLayoutParams();
        int i3 = width * i2;
        int i4 = i * height;
        if (i3 > i4) {
            layoutParams.height = height;
            layoutParams.width = i4 / i2;
        } else {
            layoutParams.width = width;
            layoutParams.height = i3 / i;
        }
        this.mVideoSurface.setLayoutParams(layoutParams);
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback) {
        this.mMediaPlaybackCallback = callback;
        if (callback == null || this.mState != 1) {
            return;
        }
        callback.surfaceCreated(this.mVideoSurface.getHolder());
    }
}
