package androidx.leanback.media;

import android.content.Context;
import androidx.annotation.CallSuper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class PlaybackGlue {
    private final Context mContext;
    private PlaybackGlueHost mPlaybackGlueHost;
    public ArrayList<PlayerCallback> mPlayerCallbacks;

    public static abstract class PlayerCallback {
        public void onPlayCompleted(PlaybackGlue playbackGlue) {
        }

        public void onPlayStateChanged(PlaybackGlue playbackGlue) {
        }

        public void onPreparedStateChanged(PlaybackGlue playbackGlue) {
        }
    }

    public PlaybackGlue(Context context) {
        this.mContext = context;
    }

    public void addPlayerCallback(PlayerCallback playerCallback) {
        if (this.mPlayerCallbacks == null) {
            this.mPlayerCallbacks = new ArrayList<>();
        }
        this.mPlayerCallbacks.add(playerCallback);
    }

    public Context getContext() {
        return this.mContext;
    }

    public PlaybackGlueHost getHost() {
        return this.mPlaybackGlueHost;
    }

    public final List<PlayerCallback> getPlayerCallbacks() {
        if (this.mPlayerCallbacks == null) {
            return null;
        }
        return new ArrayList(this.mPlayerCallbacks);
    }

    public boolean isPlaying() {
        return false;
    }

    public boolean isPrepared() {
        return true;
    }

    public void next() {
    }

    @CallSuper
    public void onAttachedToHost(PlaybackGlueHost playbackGlueHost) {
        this.mPlaybackGlueHost = playbackGlueHost;
        playbackGlueHost.setHostCallback(new PlaybackGlueHost.HostCallback() { // from class: androidx.leanback.media.PlaybackGlue.2
            @Override // androidx.leanback.media.PlaybackGlueHost.HostCallback
            public void onHostDestroy() {
                PlaybackGlue.this.setHost(null);
            }

            @Override // androidx.leanback.media.PlaybackGlueHost.HostCallback
            public void onHostPause() {
                Objects.requireNonNull(PlaybackGlue.this);
            }

            @Override // androidx.leanback.media.PlaybackGlueHost.HostCallback
            public void onHostResume() {
                Objects.requireNonNull(PlaybackGlue.this);
            }

            @Override // androidx.leanback.media.PlaybackGlueHost.HostCallback
            public void onHostStart() {
                PlaybackGlue.this.onHostStart();
            }

            @Override // androidx.leanback.media.PlaybackGlueHost.HostCallback
            public void onHostStop() {
                PlaybackGlue.this.onHostStop();
            }
        });
    }

    @CallSuper
    public void onDetachedFromHost() {
        PlaybackGlueHost playbackGlueHost = this.mPlaybackGlueHost;
        if (playbackGlueHost != null) {
            playbackGlueHost.setHostCallback(null);
            this.mPlaybackGlueHost = null;
        }
    }

    public void onHostStart() {
    }

    public void onHostStop() {
    }

    public void pause() {
    }

    public void play() {
    }

    public void playWhenPrepared() {
        if (isPrepared()) {
            play();
        } else {
            addPlayerCallback(new PlayerCallback() { // from class: androidx.leanback.media.PlaybackGlue.1
                @Override // androidx.leanback.media.PlaybackGlue.PlayerCallback
                public void onPreparedStateChanged(PlaybackGlue playbackGlue) {
                    if (playbackGlue.isPrepared()) {
                        PlaybackGlue.this.removePlayerCallback(this);
                        PlaybackGlue.this.play();
                    }
                }
            });
        }
    }

    public void previous() {
    }

    public void removePlayerCallback(PlayerCallback playerCallback) {
        ArrayList<PlayerCallback> arrayList = this.mPlayerCallbacks;
        if (arrayList != null) {
            arrayList.remove(playerCallback);
        }
    }

    public final void setHost(PlaybackGlueHost playbackGlueHost) {
        PlaybackGlueHost playbackGlueHost2 = this.mPlaybackGlueHost;
        if (playbackGlueHost2 == playbackGlueHost) {
            return;
        }
        if (playbackGlueHost2 != null) {
            PlaybackGlue playbackGlue = playbackGlueHost2.mGlue;
            if (playbackGlue != null) {
                playbackGlue.onDetachedFromHost();
            }
            playbackGlueHost2.mGlue = null;
        }
        this.mPlaybackGlueHost = playbackGlueHost;
        if (playbackGlueHost != null) {
            PlaybackGlue playbackGlue2 = playbackGlueHost.mGlue;
            if (playbackGlue2 != null) {
                playbackGlue2.onDetachedFromHost();
            }
            playbackGlueHost.mGlue = this;
            onAttachedToHost(playbackGlueHost);
        }
    }
}
