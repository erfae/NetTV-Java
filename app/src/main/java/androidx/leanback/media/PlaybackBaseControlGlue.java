package androidx.leanback.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ControlButtonPresenterSelector;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class PlaybackBaseControlGlue<T extends PlayerAdapter> extends PlaybackGlue implements OnActionClickedListener, View.OnKeyListener {
    public static final int ACTION_CUSTOM_LEFT_FIRST = 1;
    public static final int ACTION_CUSTOM_RIGHT_FIRST = 4096;
    public static final int ACTION_FAST_FORWARD = 128;
    public static final int ACTION_PLAY_PAUSE = 64;
    public static final int ACTION_REPEAT = 512;
    public static final int ACTION_REWIND = 32;
    public static final int ACTION_SHUFFLE = 1024;
    public static final int ACTION_SKIP_TO_NEXT = 256;
    public static final int ACTION_SKIP_TO_PREVIOUS = 16;
    public boolean mBuffering;
    public PlaybackControlsRow mControlsRow;
    public PlaybackRowPresenter mControlsRowPresenter;
    public Drawable mCover;
    public int mErrorCode;
    public String mErrorMessage;
    public boolean mErrorSet;
    public boolean mFadeWhenPlaying;
    public boolean mIsPlaying;
    public PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    public final T mPlayerAdapter;
    public PlaybackGlueHost.PlayerCallback mPlayerCallback;
    public CharSequence mSubtitle;
    public CharSequence mTitle;
    public int mVideoHeight;
    public int mVideoWidth;

    public PlaybackBaseControlGlue(Context context, T t) {
        super(context);
        this.mIsPlaying = false;
        this.mFadeWhenPlaying = true;
        this.mBuffering = false;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mErrorSet = false;
        PlayerAdapter.Callback callback = new PlayerAdapter.Callback() { // from class: androidx.leanback.media.PlaybackBaseControlGlue.1
            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onBufferedPositionChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
                PlaybackControlsRow playbackControlsRow = playbackBaseControlGlue.mControlsRow;
                if (playbackControlsRow != null) {
                    playbackControlsRow.setBufferedPosition(playbackBaseControlGlue.mPlayerAdapter.getBufferedPosition());
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onBufferingStateChanged(PlayerAdapter playerAdapter, boolean z) {
                PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
                playbackBaseControlGlue.mBuffering = z;
                PlaybackGlueHost.PlayerCallback playerCallback = playbackBaseControlGlue.mPlayerCallback;
                if (playerCallback != null) {
                    playerCallback.onBufferingStateChanged(z);
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onCurrentPositionChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onUpdateProgress();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onDurationChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onUpdateDuration();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onError(PlayerAdapter playerAdapter, int i, String str) {
                PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
                playbackBaseControlGlue.mErrorSet = true;
                playbackBaseControlGlue.mErrorCode = i;
                playbackBaseControlGlue.mErrorMessage = str;
                PlaybackGlueHost.PlayerCallback playerCallback = playbackBaseControlGlue.mPlayerCallback;
                if (playerCallback != null) {
                    playerCallback.onError(i, str);
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onMetadataChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onMetadataChanged();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onPlayCompleted(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onPlayCompleted();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onPlayStateChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onPlayStateChanged();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onPreparedStateChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
                playbackBaseControlGlue.onUpdateDuration();
                List<PlaybackGlue.PlayerCallback> playerCallbacks = playbackBaseControlGlue.getPlayerCallbacks();
                if (playerCallbacks != null) {
                    ArrayList arrayList = (ArrayList) playerCallbacks;
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        ((PlaybackGlue.PlayerCallback) arrayList.get(i)).onPreparedStateChanged(playbackBaseControlGlue);
                    }
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onVideoSizeChanged(PlayerAdapter playerAdapter, int i, int i2) {
                PlaybackBaseControlGlue playbackBaseControlGlue = PlaybackBaseControlGlue.this;
                playbackBaseControlGlue.mVideoWidth = i;
                playbackBaseControlGlue.mVideoHeight = i2;
                PlaybackGlueHost.PlayerCallback playerCallback = playbackBaseControlGlue.mPlayerCallback;
                if (playerCallback != null) {
                    playerCallback.onVideoSizeChanged(i, i2);
                }
            }
        };
        this.mPlayerAdapter = t;
        t.setCallback(callback);
    }

    public static void notifyItemChanged(ArrayObjectAdapter arrayObjectAdapter, Object obj) {
        int iIndexOf = arrayObjectAdapter.indexOf(obj);
        if (iIndexOf >= 0) {
            arrayObjectAdapter.notifyArrayItemRangeChanged(iIndexOf, 1);
        }
    }

    private void updateControlsRow() {
        onMetadataChanged();
    }

    public Drawable getArt() {
        return this.mCover;
    }

    public final long getBufferedPosition() {
        return this.mPlayerAdapter.getBufferedPosition();
    }

    public PlaybackControlsRow getControlsRow() {
        return this.mControlsRow;
    }

    public long getCurrentPosition() {
        return this.mPlayerAdapter.getCurrentPosition();
    }

    public final long getDuration() {
        return this.mPlayerAdapter.getDuration();
    }

    public PlaybackRowPresenter getPlaybackRowPresenter() {
        return this.mControlsRowPresenter;
    }

    public final T getPlayerAdapter() {
        return this.mPlayerAdapter;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public long getSupportedActions() {
        return this.mPlayerAdapter.getSupportedActions();
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean isControlsOverlayAutoHideEnabled() {
        return this.mFadeWhenPlaying;
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public final boolean isPlaying() {
        return this.mPlayerAdapter.isPlaying();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public final boolean isPrepared() {
        return this.mPlayerAdapter.isPrepared();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void next() {
        this.mPlayerAdapter.next();
    }

    public abstract void onActionClicked(Action action);

    @Override // androidx.leanback.media.PlaybackGlue
    public void onAttachedToHost(PlaybackGlueHost playbackGlueHost) {
        int i;
        super.onAttachedToHost(playbackGlueHost);
        playbackGlueHost.setOnKeyInterceptListener(this);
        playbackGlueHost.setOnActionClickedListener(this);
        if (this.mControlsRow == null) {
            setControlsRow(new PlaybackControlsRow(this));
        }
        if (this.mControlsRowPresenter == null) {
            setPlaybackRowPresenter(onCreateRowPresenter());
        }
        playbackGlueHost.setPlaybackRowPresenter(getPlaybackRowPresenter());
        playbackGlueHost.setPlaybackRow(getControlsRow());
        PlaybackGlueHost.PlayerCallback playerCallback = playbackGlueHost.getPlayerCallback();
        this.mPlayerCallback = playerCallback;
        if (playerCallback != null) {
            int i2 = this.mVideoWidth;
            if (i2 != 0 && (i = this.mVideoHeight) != 0) {
                playerCallback.onVideoSizeChanged(i2, i);
            }
            if (this.mErrorSet) {
                this.mPlayerCallback.onError(this.mErrorCode, this.mErrorMessage);
            }
            this.mPlayerCallback.onBufferingStateChanged(this.mBuffering);
        }
        this.mPlayerAdapter.onAttachedToHost(playbackGlueHost);
    }

    public void onCreatePrimaryActions(ArrayObjectAdapter arrayObjectAdapter) {
    }

    public abstract PlaybackRowPresenter onCreateRowPresenter();

    @Override // androidx.leanback.media.PlaybackGlue
    public void onDetachedFromHost() {
        this.mErrorSet = false;
        this.mErrorCode = 0;
        this.mErrorMessage = null;
        PlaybackGlueHost.PlayerCallback playerCallback = this.mPlayerCallback;
        if (playerCallback != null) {
            playerCallback.onBufferingStateChanged(false);
        }
        this.mPlayerCallback = null;
        this.mPlayerAdapter.onDetachedFromHost();
        this.mPlayerAdapter.setProgressUpdatingEnabled(false);
        super.onDetachedFromHost();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public final void onHostStart() {
        this.mPlayerAdapter.setProgressUpdatingEnabled(true);
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public final void onHostStop() {
        this.mPlayerAdapter.setProgressUpdatingEnabled(false);
    }

    public abstract boolean onKey(View view, int i, KeyEvent keyEvent);

    public final void onMetadataChanged() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow == null) {
            return;
        }
        playbackControlsRow.setImageDrawable(getArt());
        this.mControlsRow.setDuration(getDuration());
        this.mControlsRow.setCurrentPosition(getCurrentPosition());
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    @CallSuper
    public void onPlayCompleted() {
        List<PlaybackGlue.PlayerCallback> playerCallbacks = getPlayerCallbacks();
        if (playerCallbacks != null) {
            ArrayList arrayList = (ArrayList) playerCallbacks;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((PlaybackGlue.PlayerCallback) arrayList.get(i)).onPlayCompleted(this);
            }
        }
    }

    @CallSuper
    public void onPlayStateChanged() {
        List<PlaybackGlue.PlayerCallback> playerCallbacks = getPlayerCallbacks();
        if (playerCallbacks != null) {
            ArrayList arrayList = (ArrayList) playerCallbacks;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((PlaybackGlue.PlayerCallback) arrayList.get(i)).onPlayStateChanged(this);
            }
        }
    }

    @CallSuper
    public final void onUpdateDuration() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setDuration(this.mPlayerAdapter.isPrepared() ? this.mPlayerAdapter.getDuration() : -1L);
        }
    }

    @CallSuper
    public void onUpdateProgress() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setCurrentPosition(this.mPlayerAdapter.isPrepared() ? getCurrentPosition() : -1L);
        }
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void pause() {
        this.mPlayerAdapter.pause();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void play() {
        this.mPlayerAdapter.play();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void previous() {
        this.mPlayerAdapter.previous();
    }

    public final void seekTo(long j) {
        this.mPlayerAdapter.seekTo(j);
    }

    public void setArt(Drawable drawable) {
        if (this.mCover == drawable) {
            return;
        }
        this.mCover = drawable;
        this.mControlsRow.setImageDrawable(drawable);
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    public void setControlsOverlayAutoHideEnabled(boolean z) {
        this.mFadeWhenPlaying = z;
        if (z || getHost() == null) {
            return;
        }
        getHost().setControlsOverlayAutoHideEnabled(false);
    }

    public void setControlsRow(PlaybackControlsRow playbackControlsRow) {
        this.mControlsRow = playbackControlsRow;
        playbackControlsRow.setCurrentPosition(-1L);
        this.mControlsRow.setDuration(-1L);
        this.mControlsRow.setBufferedPosition(-1L);
        if (this.mControlsRow.getPrimaryActionsAdapter() == null) {
            ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
            onCreatePrimaryActions(arrayObjectAdapter);
            this.mControlsRow.setPrimaryActionsAdapter(arrayObjectAdapter);
        }
        if (this.mControlsRow.getSecondaryActionsAdapter() == null) {
            getControlsRow().setSecondaryActionsAdapter(new ArrayObjectAdapter(new ControlButtonPresenterSelector()));
        }
        updateControlsRow();
    }

    public void setPlaybackRowPresenter(PlaybackRowPresenter playbackRowPresenter) {
        this.mControlsRowPresenter = playbackRowPresenter;
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mSubtitle)) {
            return;
        }
        this.mSubtitle = charSequence;
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mTitle)) {
            return;
        }
        this.mTitle = charSequence;
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }
}
