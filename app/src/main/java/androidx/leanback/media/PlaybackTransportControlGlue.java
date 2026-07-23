package androidx.leanback.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.PlaybackSeekDataProvider;
import androidx.leanback.widget.PlaybackSeekUi;
import androidx.leanback.widget.PlaybackTransportRowPresenter;
import androidx.leanback.widget.RowPresenter;
import com.google.android.exoplayer2.ExoPlayer;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class PlaybackTransportControlGlue<T extends PlayerAdapter> extends PlaybackBaseControlGlue<T> {
    public static final Handler sHandler = new UpdatePlaybackStateHandler();
    public final WeakReference<PlaybackBaseControlGlue> mGlueWeakReference;
    public final PlaybackTransportControlGlue<T>.SeekUiClient mPlaybackSeekUiClient;
    public boolean mSeekEnabled;
    public PlaybackSeekDataProvider mSeekProvider;

    public class SeekUiClient extends PlaybackSeekUi.Client {
        public boolean mIsSeek;
        public long mLastUserPosition;
        public boolean mPausedBeforeSeek;
        public long mPositionBeforeSeek;

        public SeekUiClient() {
        }

        @Override // androidx.leanback.widget.PlaybackSeekUi.Client
        public PlaybackSeekDataProvider getPlaybackSeekDataProvider() {
            return PlaybackTransportControlGlue.this.mSeekProvider;
        }

        @Override // androidx.leanback.widget.PlaybackSeekUi.Client
        public boolean isSeekEnabled() {
            PlaybackTransportControlGlue playbackTransportControlGlue = PlaybackTransportControlGlue.this;
            return playbackTransportControlGlue.mSeekProvider != null || playbackTransportControlGlue.mSeekEnabled;
        }

        @Override // androidx.leanback.widget.PlaybackSeekUi.Client
        public void onSeekFinished(boolean z) {
            if (z) {
                long j = this.mPositionBeforeSeek;
                if (j >= 0) {
                    PlaybackTransportControlGlue.this.seekTo(j);
                }
            } else {
                long j2 = this.mLastUserPosition;
                if (j2 >= 0) {
                    PlaybackTransportControlGlue.this.seekTo(j2);
                }
            }
            this.mIsSeek = false;
            if (!this.mPausedBeforeSeek) {
                PlaybackTransportControlGlue.this.play();
            } else {
                PlaybackTransportControlGlue.this.mPlayerAdapter.setProgressUpdatingEnabled(false);
                PlaybackTransportControlGlue.this.onUpdateProgress();
            }
        }

        @Override // androidx.leanback.widget.PlaybackSeekUi.Client
        public void onSeekPositionChanged(long j) {
            PlaybackTransportControlGlue playbackTransportControlGlue = PlaybackTransportControlGlue.this;
            if (playbackTransportControlGlue.mSeekProvider == null) {
                playbackTransportControlGlue.mPlayerAdapter.seekTo(j);
            } else {
                this.mLastUserPosition = j;
            }
            PlaybackControlsRow playbackControlsRow = PlaybackTransportControlGlue.this.mControlsRow;
            if (playbackControlsRow != null) {
                playbackControlsRow.setCurrentPosition(j);
            }
        }

        @Override // androidx.leanback.widget.PlaybackSeekUi.Client
        public void onSeekStarted() {
            this.mIsSeek = true;
            this.mPausedBeforeSeek = !PlaybackTransportControlGlue.this.isPlaying();
            PlaybackTransportControlGlue.this.mPlayerAdapter.setProgressUpdatingEnabled(true);
            PlaybackTransportControlGlue playbackTransportControlGlue = PlaybackTransportControlGlue.this;
            this.mPositionBeforeSeek = playbackTransportControlGlue.mSeekProvider == null ? playbackTransportControlGlue.mPlayerAdapter.getCurrentPosition() : -1L;
            this.mLastUserPosition = -1L;
            PlaybackTransportControlGlue.this.pause();
        }
    }

    public static class UpdatePlaybackStateHandler extends Handler {
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PlaybackTransportControlGlue playbackTransportControlGlue;
            if (message.what != 100 || (playbackTransportControlGlue = (PlaybackTransportControlGlue) ((WeakReference) message.obj).get()) == null) {
                return;
            }
            playbackTransportControlGlue.onUpdatePlaybackState();
        }
    }

    public PlaybackTransportControlGlue(Context context, T t) {
        super(context, t);
        this.mGlueWeakReference = new WeakReference<>(this);
        this.mPlaybackSeekUiClient = new SeekUiClient();
    }

    private void updatePlaybackState(boolean z) {
        if (this.mControlsRow == null) {
            return;
        }
        if (z) {
            this.mPlayerAdapter.setProgressUpdatingEnabled(true);
        } else {
            onUpdateProgress();
            this.mPlayerAdapter.setProgressUpdatingEnabled(this.mPlaybackSeekUiClient.mIsSeek);
        }
        if (this.mFadeWhenPlaying && getHost() != null) {
            getHost().setControlsOverlayAutoHideEnabled(z);
        }
        PlaybackControlsRow.PlayPauseAction playPauseAction = this.mPlayPauseAction;
        if (playPauseAction == null || playPauseAction.getIndex() == z) {
            return;
        }
        this.mPlayPauseAction.setIndex(z ? 1 : 0);
        PlaybackBaseControlGlue.notifyItemChanged((ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter(), this.mPlayPauseAction);
    }

    public final boolean dispatchAction(Action action, KeyEvent keyEvent) {
        if (action instanceof PlaybackControlsRow.PlayPauseAction) {
            boolean z = keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 126;
            if ((keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 127) && this.mIsPlaying) {
                this.mIsPlaying = false;
                pause();
            } else if (z && !this.mIsPlaying) {
                this.mIsPlaying = true;
                play();
            }
            updatePlaybackState(this.mIsPlaying);
            Handler handler = sHandler;
            handler.removeMessages(100, this.mGlueWeakReference);
            handler.sendMessageDelayed(handler.obtainMessage(100, this.mGlueWeakReference), ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        } else if (action instanceof PlaybackControlsRow.SkipNextAction) {
            next();
        } else {
            if (!(action instanceof PlaybackControlsRow.SkipPreviousAction)) {
                return false;
            }
            previous();
        }
        return true;
    }

    public final PlaybackSeekDataProvider getSeekProvider() {
        return this.mSeekProvider;
    }

    public final boolean isSeekEnabled() {
        return this.mSeekEnabled;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.widget.OnActionClickedListener
    public void onActionClicked(Action action) {
        dispatchAction(action, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.media.PlaybackGlue
    public final void onAttachedToHost(PlaybackGlueHost playbackGlueHost) {
        super.onAttachedToHost(playbackGlueHost);
        if (playbackGlueHost instanceof PlaybackSeekUi) {
            ((PlaybackSeekUi) playbackGlueHost).setPlaybackSeekUiClient(this.mPlaybackSeekUiClient);
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final void onCreatePrimaryActions(ArrayObjectAdapter arrayObjectAdapter) {
        PlaybackControlsRow.PlayPauseAction playPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
        this.mPlayPauseAction = playPauseAction;
        arrayObjectAdapter.add(playPauseAction);
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final PlaybackRowPresenter onCreateRowPresenter() {
        AbstractDetailsDescriptionPresenter abstractDetailsDescriptionPresenter = new AbstractDetailsDescriptionPresenter() { // from class: androidx.leanback.media.PlaybackTransportControlGlue.1
            @Override // androidx.leanback.widget.AbstractDetailsDescriptionPresenter
            public final void onBindDescription(AbstractDetailsDescriptionPresenter.ViewHolder viewHolder, Object obj) {
                PlaybackBaseControlGlue playbackBaseControlGlue = (PlaybackBaseControlGlue) obj;
                viewHolder.getTitle().setText(playbackBaseControlGlue.getTitle());
                viewHolder.getSubtitle().setText(playbackBaseControlGlue.getSubtitle());
            }
        };
        PlaybackTransportRowPresenter playbackTransportRowPresenter = new PlaybackTransportRowPresenter() { // from class: androidx.leanback.media.PlaybackTransportControlGlue.2
            @Override // androidx.leanback.widget.PlaybackTransportRowPresenter, androidx.leanback.widget.RowPresenter
            public final void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
                super.onBindRowViewHolder(viewHolder, obj);
                viewHolder.setOnKeyListener(PlaybackTransportControlGlue.this);
            }

            @Override // androidx.leanback.widget.PlaybackTransportRowPresenter, androidx.leanback.widget.RowPresenter
            public final void onUnbindRowViewHolder(RowPresenter.ViewHolder viewHolder) {
                super.onUnbindRowViewHolder(viewHolder);
                viewHolder.setOnKeyListener(null);
            }
        };
        playbackTransportRowPresenter.setDescriptionPresenter(abstractDetailsDescriptionPresenter);
        return playbackTransportRowPresenter;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.media.PlaybackGlue
    public final void onDetachedFromHost() {
        super.onDetachedFromHost();
        if (getHost() instanceof PlaybackSeekUi) {
            ((PlaybackSeekUi) getHost()).setPlaybackSeekUiClient(null);
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4 && i != 111) {
            switch (i) {
                default:
                    Action actionForKeyCode = this.mControlsRow.getActionForKeyCode(this.mControlsRow.getPrimaryActionsAdapter(), i);
                    if (actionForKeyCode == null) {
                        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
                        actionForKeyCode = playbackControlsRow.getActionForKeyCode(playbackControlsRow.getSecondaryActionsAdapter(), i);
                    }
                    if (actionForKeyCode != null) {
                        if (keyEvent.getAction() != 0) {
                            return true;
                        }
                        dispatchAction(actionForKeyCode, keyEvent);
                        return true;
                    }
                case 19:
                case 20:
                case 21:
                case 22:
                    return false;
            }
        }
        return false;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final void onPlayStateChanged() {
        Handler handler = sHandler;
        if (handler.hasMessages(100, this.mGlueWeakReference)) {
            handler.removeMessages(100, this.mGlueWeakReference);
            if (this.mPlayerAdapter.isPlaying() != this.mIsPlaying) {
                handler.sendMessageDelayed(handler.obtainMessage(100, this.mGlueWeakReference), ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } else {
                onUpdatePlaybackState();
            }
        } else {
            onUpdatePlaybackState();
        }
        super.onPlayStateChanged();
    }

    public final void onUpdatePlaybackState() {
        boolean zIsPlaying = this.mPlayerAdapter.isPlaying();
        this.mIsPlaying = zIsPlaying;
        updatePlaybackState(zIsPlaying);
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final void onUpdateProgress() {
        if (this.mPlaybackSeekUiClient.mIsSeek) {
            return;
        }
        super.onUpdateProgress();
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public void setControlsRow(PlaybackControlsRow playbackControlsRow) {
        super.setControlsRow(playbackControlsRow);
        sHandler.removeMessages(100, this.mGlueWeakReference);
        onUpdatePlaybackState();
    }

    public final void setSeekEnabled(boolean z) {
        this.mSeekEnabled = z;
    }

    public final void setSeekProvider(PlaybackSeekDataProvider playbackSeekDataProvider) {
        this.mSeekProvider = playbackSeekDataProvider;
    }
}
