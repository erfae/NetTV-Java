package androidx.leanback.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.SurfaceHolder;
import androidx.leanback.R;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MediaPlayerAdapter extends PlayerAdapter {
    public long mBufferedProgress;
    public boolean mBufferingStart;
    public Context mContext;
    public boolean mHasDisplay;
    public SurfaceHolderGlueHost mSurfaceHolderGlueHost;
    public final MediaPlayer mPlayer = new MediaPlayer();
    public final Runnable mRunnable = new Runnable() { // from class: androidx.leanback.media.MediaPlayerAdapter.1
        @Override // java.lang.Runnable
        public void run() {
            MediaPlayerAdapter.this.getCallback().onCurrentPositionChanged(MediaPlayerAdapter.this);
            MediaPlayerAdapter mediaPlayerAdapter = MediaPlayerAdapter.this;
            mediaPlayerAdapter.mHandler.postDelayed(this, mediaPlayerAdapter.getProgressUpdatingInterval());
        }
    };
    public final Handler mHandler = new Handler();
    public boolean mInitialized = false;
    public Uri mMediaSourceUri = null;
    public MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.2
        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mediaPlayer) {
            MediaPlayerAdapter mediaPlayerAdapter = MediaPlayerAdapter.this;
            mediaPlayerAdapter.mInitialized = true;
            mediaPlayerAdapter.notifyBufferingStartEnd();
            MediaPlayerAdapter mediaPlayerAdapter2 = MediaPlayerAdapter.this;
            if (mediaPlayerAdapter2.mSurfaceHolderGlueHost == null || mediaPlayerAdapter2.mHasDisplay) {
                mediaPlayerAdapter2.getCallback().onPreparedStateChanged(MediaPlayerAdapter.this);
            }
        }
    };
    public final MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.3
        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            MediaPlayerAdapter.this.getCallback().onPlayStateChanged(MediaPlayerAdapter.this);
            MediaPlayerAdapter.this.getCallback().onPlayCompleted(MediaPlayerAdapter.this);
        }
    };
    public final MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.4
        @Override // android.media.MediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            MediaPlayerAdapter mediaPlayerAdapter = MediaPlayerAdapter.this;
            mediaPlayerAdapter.mBufferedProgress = (mediaPlayerAdapter.getDuration() * ((long) i)) / 100;
            MediaPlayerAdapter.this.getCallback().onBufferedPositionChanged(MediaPlayerAdapter.this);
        }
    };
    public final MediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.5
        @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            MediaPlayerAdapter.this.getCallback().onVideoSizeChanged(MediaPlayerAdapter.this, i, i2);
        }
    };
    public final MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.6
        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            PlayerAdapter.Callback callback = MediaPlayerAdapter.this.getCallback();
            MediaPlayerAdapter mediaPlayerAdapter = MediaPlayerAdapter.this;
            callback.onError(mediaPlayerAdapter, i, mediaPlayerAdapter.mContext.getString(R.string.lb_media_player_error, Integer.valueOf(i), Integer.valueOf(i2)));
            Objects.requireNonNull(MediaPlayerAdapter.this);
            return false;
        }
    };
    public final MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.7
        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(MediaPlayer mediaPlayer) {
            Objects.requireNonNull(MediaPlayerAdapter.this);
        }
    };
    public final MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() { // from class: androidx.leanback.media.MediaPlayerAdapter.8
        /* JADX WARN: Code duplicated, block: B:13:0x0024 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Code duplicated, block: B:15:? A[RETURN, SYNTHETIC] */
        @Override // android.media.MediaPlayer.OnInfoListener
        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
            boolean z;
            if (i != 701) {
                if (i != 702) {
                    z = false;
                } else {
                    MediaPlayerAdapter mediaPlayerAdapter = MediaPlayerAdapter.this;
                    mediaPlayerAdapter.mBufferingStart = false;
                    mediaPlayerAdapter.notifyBufferingStartEnd();
                }
                Objects.requireNonNull(MediaPlayerAdapter.this);
                if (z) {
                    return true;
                }
                return false;
            }
            MediaPlayerAdapter mediaPlayerAdapter2 = MediaPlayerAdapter.this;
            mediaPlayerAdapter2.mBufferingStart = true;
            mediaPlayerAdapter2.notifyBufferingStartEnd();
            z = true;
            Objects.requireNonNull(MediaPlayerAdapter.this);
            if (z) {
                return false;
            }
            return true;
        }
    };

    public class VideoPlayerSurfaceHolderCallback implements SurfaceHolder.Callback {
        public VideoPlayerSurfaceHolderCallback() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            MediaPlayerAdapter.this.setDisplay(surfaceHolder);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            MediaPlayerAdapter.this.setDisplay(null);
        }
    }

    public MediaPlayerAdapter(Context context) {
        this.mContext = context;
    }

    private void prepareMediaForPlaying() {
        reset();
        try {
            Uri uri = this.mMediaSourceUri;
            if (uri != null) {
                this.mPlayer.setDataSource(this.mContext, uri);
                this.mPlayer.setAudioStreamType(3);
                this.mPlayer.setOnPreparedListener(this.mOnPreparedListener);
                this.mPlayer.setOnVideoSizeChangedListener(this.mOnVideoSizeChangedListener);
                this.mPlayer.setOnErrorListener(this.mOnErrorListener);
                this.mPlayer.setOnSeekCompleteListener(this.mOnSeekCompleteListener);
                this.mPlayer.setOnCompletionListener(this.mOnCompletionListener);
                this.mPlayer.setOnInfoListener(this.mOnInfoListener);
                this.mPlayer.setOnBufferingUpdateListener(this.mOnBufferingUpdateListener);
                notifyBufferingStartEnd();
                this.mPlayer.prepareAsync();
                getCallback().onPlayStateChanged(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final void changeToUnitialized() {
        if (this.mInitialized) {
            this.mInitialized = false;
            notifyBufferingStartEnd();
            if (this.mHasDisplay) {
                getCallback().onPreparedStateChanged(this);
            }
        }
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public long getBufferedPosition() {
        return this.mBufferedProgress;
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public long getCurrentPosition() {
        if (this.mInitialized) {
            return this.mPlayer.getCurrentPosition();
        }
        return -1L;
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public long getDuration() {
        if (this.mInitialized) {
            return this.mPlayer.getDuration();
        }
        return -1L;
    }

    public final MediaPlayer getMediaPlayer() {
        return this.mPlayer;
    }

    public int getProgressUpdatingInterval() {
        return 16;
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public boolean isPlaying() {
        return this.mInitialized && this.mPlayer.isPlaying();
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public boolean isPrepared() {
        return this.mInitialized && (this.mSurfaceHolderGlueHost == null || this.mHasDisplay);
    }

    public final void notifyBufferingStartEnd() {
        getCallback().onBufferingStateChanged(this, this.mBufferingStart || !this.mInitialized);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.leanback.media.PlayerAdapter
    public void onAttachedToHost(PlaybackGlueHost playbackGlueHost) {
        if (playbackGlueHost instanceof SurfaceHolderGlueHost) {
            SurfaceHolderGlueHost surfaceHolderGlueHost = (SurfaceHolderGlueHost) playbackGlueHost;
            this.mSurfaceHolderGlueHost = surfaceHolderGlueHost;
            surfaceHolderGlueHost.setSurfaceHolderCallback(new VideoPlayerSurfaceHolderCallback());
        }
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public void onDetachedFromHost() {
        SurfaceHolderGlueHost surfaceHolderGlueHost = this.mSurfaceHolderGlueHost;
        if (surfaceHolderGlueHost != null) {
            surfaceHolderGlueHost.setSurfaceHolderCallback(null);
            this.mSurfaceHolderGlueHost = null;
        }
        reset();
        release();
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public void pause() {
        if (isPlaying()) {
            this.mPlayer.pause();
            getCallback().onPlayStateChanged(this);
        }
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public void play() {
        if (!this.mInitialized || this.mPlayer.isPlaying()) {
            return;
        }
        this.mPlayer.start();
        getCallback().onPlayStateChanged(this);
        getCallback().onCurrentPositionChanged(this);
    }

    public void release() {
        changeToUnitialized();
        this.mHasDisplay = false;
        this.mPlayer.release();
    }

    public void reset() {
        changeToUnitialized();
        this.mPlayer.reset();
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public void seekTo(long j) {
        if (this.mInitialized) {
            this.mPlayer.seekTo((int) j);
        }
    }

    public boolean setDataSource(Uri uri) {
        Uri uri2 = this.mMediaSourceUri;
        if (uri2 != null) {
            if (uri2.equals(uri)) {
                return false;
            }
        } else if (uri == null) {
            return false;
        }
        this.mMediaSourceUri = uri;
        prepareMediaForPlaying();
        return true;
    }

    public final void setDisplay(SurfaceHolder surfaceHolder) {
        boolean z = this.mHasDisplay;
        boolean z2 = surfaceHolder != null;
        this.mHasDisplay = z2;
        if (z == z2) {
            return;
        }
        this.mPlayer.setDisplay(surfaceHolder);
        if (this.mHasDisplay) {
            if (this.mInitialized) {
                getCallback().onPreparedStateChanged(this);
            }
        } else if (this.mInitialized) {
            getCallback().onPreparedStateChanged(this);
        }
    }

    @Override // androidx.leanback.media.PlayerAdapter
    public void setProgressUpdatingEnabled(boolean z) {
        this.mHandler.removeCallbacks(this.mRunnable);
        if (z) {
            this.mHandler.postDelayed(this.mRunnable, getProgressUpdatingInterval());
        }
    }
}
