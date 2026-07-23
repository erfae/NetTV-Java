package androidx.leanback.media;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackControlsRowPresenter;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.RowPresenter;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class PlaybackBannerControlGlue<T extends PlayerAdapter> extends PlaybackBaseControlGlue<T> {
    public static final int ACTION_CUSTOM_LEFT_FIRST = 1;
    public static final int ACTION_CUSTOM_RIGHT_FIRST = 4096;
    public static final int ACTION_FAST_FORWARD = 128;
    public static final int ACTION_PLAY_PAUSE = 64;
    public static final int ACTION_REWIND = 32;
    public static final int ACTION_SKIP_TO_NEXT = 256;
    public static final int ACTION_SKIP_TO_PREVIOUS = 16;
    private static final int NUMBER_OF_SEEK_SPEEDS = 5;
    public static final int PLAYBACK_SPEED_FAST_L0 = 10;
    public static final int PLAYBACK_SPEED_FAST_L1 = 11;
    public static final int PLAYBACK_SPEED_FAST_L2 = 12;
    public static final int PLAYBACK_SPEED_FAST_L3 = 13;
    public static final int PLAYBACK_SPEED_FAST_L4 = 14;
    public static final int PLAYBACK_SPEED_INVALID = -1;
    public static final int PLAYBACK_SPEED_NORMAL = 1;
    public static final int PLAYBACK_SPEED_PAUSED = 0;
    private static final String TAG = "PlaybackBannerControlGlue";
    private PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private final int[] mFastForwardSpeeds;
    private boolean mIsCustomizedFastForwardSupported;
    private boolean mIsCustomizedRewindSupported;
    private int mPlaybackSpeed;
    private PlaybackControlsRow.RewindAction mRewindAction;
    private final int[] mRewindSpeeds;
    private PlaybackControlsRow.SkipNextAction mSkipNextAction;
    private PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private long mStartPosition;
    private long mStartTime;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ACTION_ {
    }

    public PlaybackBannerControlGlue(Context context, int[] iArr, T t) {
        this(context, iArr, iArr, t);
    }

    private void decrementRewindPlaybackSpeed() {
        int i = this.mPlaybackSpeed;
        switch (i) {
            case -13:
            case SplitInstallErrorCode.SPLITCOMPAT_EMULATION_ERROR /* -12 */:
            case SplitInstallErrorCode.SPLITCOMPAT_VERIFICATION_ERROR /* -11 */:
            case -10:
                this.mPlaybackSpeed = i - 1;
                break;
            default:
                this.mPlaybackSpeed = -10;
                break;
        }
    }

    private void fakePause() {
        this.mIsPlaying = true;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        super.pause();
        onUpdatePlaybackState();
    }

    private int getMaxForwardSpeedId() {
        return (this.mFastForwardSpeeds.length - 1) + 10;
    }

    private int getMaxRewindSpeedId() {
        return (this.mRewindSpeeds.length - 1) + 10;
    }

    private void incrementFastForwardPlaybackSpeed() {
        int i = this.mPlaybackSpeed;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                this.mPlaybackSpeed = i + 1;
                break;
            default:
                this.mPlaybackSpeed = 10;
                break;
        }
    }

    private void updatePlaybackState(boolean z) {
        if (this.mControlsRow == null) {
            return;
        }
        if (z) {
            this.mPlayerAdapter.setProgressUpdatingEnabled(true);
        } else {
            onUpdateProgress();
            this.mPlayerAdapter.setProgressUpdatingEnabled(false);
        }
        if (this.mFadeWhenPlaying && getHost() != null) {
            getHost().setControlsOverlayAutoHideEnabled(z);
        }
        ArrayObjectAdapter arrayObjectAdapter = (ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter();
        PlaybackControlsRow.PlayPauseAction playPauseAction = this.mPlayPauseAction;
        if (playPauseAction != null && playPauseAction.getIndex() != z) {
            this.mPlayPauseAction.setIndex(z ? 1 : 0);
            PlaybackBaseControlGlue.notifyItemChanged(arrayObjectAdapter, this.mPlayPauseAction);
        }
        PlaybackControlsRow.FastForwardAction fastForwardAction = this.mFastForwardAction;
        if (fastForwardAction != null) {
            int i = this.mPlaybackSpeed;
            int i2 = i >= 10 ? (i - 10) + 1 : 0;
            if (fastForwardAction.getIndex() != i2) {
                this.mFastForwardAction.setIndex(i2);
                PlaybackBaseControlGlue.notifyItemChanged(arrayObjectAdapter, this.mFastForwardAction);
            }
        }
        PlaybackControlsRow.RewindAction rewindAction = this.mRewindAction;
        if (rewindAction != null) {
            int i3 = this.mPlaybackSpeed;
            int i4 = i3 <= -10 ? ((-i3) - 10) + 1 : 0;
            if (rewindAction.getIndex() != i4) {
                this.mRewindAction.setIndex(i4);
                PlaybackBaseControlGlue.notifyItemChanged(arrayObjectAdapter, this.mRewindAction);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x003c  */
    public final boolean dispatchAction(Action action, KeyEvent keyEvent) {
        if (action == this.mPlayPauseAction) {
            boolean z = keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 126;
            if (keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 127) {
                int i = this.mPlaybackSpeed;
                if (!z ? i == 0 : i != 1) {
                    pause();
                } else if (z && this.mPlaybackSpeed != 1) {
                    play();
                }
            } else if (z) {
                play();
            }
            onUpdatePlaybackStatusAfterUserAction();
        } else if (action == this.mSkipNextAction) {
            next();
        } else if (action == this.mSkipPreviousAction) {
            previous();
        } else if (action == this.mFastForwardAction) {
            if (this.mPlayerAdapter.isPrepared() && this.mPlaybackSpeed < getMaxForwardSpeedId()) {
                if (this.mIsCustomizedFastForwardSupported) {
                    this.mIsPlaying = true;
                    this.mPlayerAdapter.fastForward();
                } else {
                    fakePause();
                }
                incrementFastForwardPlaybackSpeed();
                onUpdatePlaybackStatusAfterUserAction();
            }
        } else {
            if (action != this.mRewindAction) {
                return false;
            }
            if (this.mPlayerAdapter.isPrepared() && this.mPlaybackSpeed > (-getMaxRewindSpeedId())) {
                if (this.mIsCustomizedFastForwardSupported) {
                    this.mIsPlaying = true;
                    this.mPlayerAdapter.rewind();
                } else {
                    fakePause();
                }
                decrementRewindPlaybackSpeed();
                onUpdatePlaybackStatusAfterUserAction();
            }
        }
        return true;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public long getCurrentPosition() {
        int i;
        int i2 = this.mPlaybackSpeed;
        if (i2 == 0 || i2 == 1) {
            return this.mPlayerAdapter.getCurrentPosition();
        }
        if (i2 >= 10) {
            if (this.mIsCustomizedFastForwardSupported) {
                return this.mPlayerAdapter.getCurrentPosition();
            }
            i = getFastForwardSpeeds()[i2 - 10];
        } else {
            if (i2 > -10) {
                return -1L;
            }
            if (this.mIsCustomizedRewindSupported) {
                return this.mPlayerAdapter.getCurrentPosition();
            }
            i = -getRewindSpeeds()[(-i2) - 10];
        }
        long jCurrentTimeMillis = ((System.currentTimeMillis() - this.mStartTime) * ((long) i)) + this.mStartPosition;
        if (jCurrentTimeMillis > getDuration()) {
            this.mPlaybackSpeed = 0;
            long duration = getDuration();
            this.mPlayerAdapter.seekTo(duration);
            this.mStartPosition = 0L;
            pause();
            return duration;
        }
        if (jCurrentTimeMillis >= 0) {
            return jCurrentTimeMillis;
        }
        this.mPlaybackSpeed = 0;
        this.mPlayerAdapter.seekTo(0L);
        this.mStartPosition = 0L;
        pause();
        return 0L;
    }

    @NonNull
    public int[] getFastForwardSpeeds() {
        return this.mFastForwardSpeeds;
    }

    @NonNull
    public int[] getRewindSpeeds() {
        return this.mRewindSpeeds;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.widget.OnActionClickedListener
    public void onActionClicked(Action action) {
        dispatchAction(action, null);
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final void onCreatePrimaryActions(ArrayObjectAdapter arrayObjectAdapter) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        long supportedActions = getSupportedActions();
        long j = 16 & supportedActions;
        if (j != 0 && this.mSkipPreviousAction == null) {
            PlaybackControlsRow.SkipPreviousAction skipPreviousAction = new PlaybackControlsRow.SkipPreviousAction(getContext());
            this.mSkipPreviousAction = skipPreviousAction;
            arrayObjectAdapter.add(skipPreviousAction);
        } else if (j == 0 && (obj = this.mSkipPreviousAction) != null) {
            arrayObjectAdapter.remove(obj);
            this.mSkipPreviousAction = null;
        }
        long j2 = 32 & supportedActions;
        if (j2 != 0 && this.mRewindAction == null) {
            PlaybackControlsRow.RewindAction rewindAction = new PlaybackControlsRow.RewindAction(getContext(), this.mRewindSpeeds.length);
            this.mRewindAction = rewindAction;
            arrayObjectAdapter.add(rewindAction);
        } else if (j2 == 0 && (obj2 = this.mRewindAction) != null) {
            arrayObjectAdapter.remove(obj2);
            this.mRewindAction = null;
        }
        long j3 = 64 & supportedActions;
        if (j3 != 0 && this.mPlayPauseAction == null) {
            this.mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
            PlaybackControlsRow.PlayPauseAction playPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
            this.mPlayPauseAction = playPauseAction;
            arrayObjectAdapter.add(playPauseAction);
        } else if (j3 == 0 && (obj3 = this.mPlayPauseAction) != null) {
            arrayObjectAdapter.remove(obj3);
            this.mPlayPauseAction = null;
        }
        long j4 = 128 & supportedActions;
        if (j4 != 0 && this.mFastForwardAction == null) {
            this.mFastForwardAction = new PlaybackControlsRow.FastForwardAction(getContext(), this.mFastForwardSpeeds.length);
            PlaybackControlsRow.FastForwardAction fastForwardAction = new PlaybackControlsRow.FastForwardAction(getContext(), this.mFastForwardSpeeds.length);
            this.mFastForwardAction = fastForwardAction;
            arrayObjectAdapter.add(fastForwardAction);
        } else if (j4 == 0 && (obj4 = this.mFastForwardAction) != null) {
            arrayObjectAdapter.remove(obj4);
            this.mFastForwardAction = null;
        }
        long j5 = supportedActions & 256;
        if (j5 != 0 && this.mSkipNextAction == null) {
            PlaybackControlsRow.SkipNextAction skipNextAction = new PlaybackControlsRow.SkipNextAction(getContext());
            this.mSkipNextAction = skipNextAction;
            arrayObjectAdapter.add(skipNextAction);
        } else {
            if (j5 != 0 || (obj5 = this.mSkipNextAction) == null) {
                return;
            }
            arrayObjectAdapter.remove(obj5);
            this.mSkipNextAction = null;
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final PlaybackRowPresenter onCreateRowPresenter() {
        return new PlaybackControlsRowPresenter(new AbstractDetailsDescriptionPresenter() { // from class: androidx.leanback.media.PlaybackBannerControlGlue.1
            @Override // androidx.leanback.widget.AbstractDetailsDescriptionPresenter
            public final void onBindDescription(AbstractDetailsDescriptionPresenter.ViewHolder viewHolder, Object obj) {
                PlaybackBannerControlGlue playbackBannerControlGlue = (PlaybackBannerControlGlue) obj;
                viewHolder.getTitle().setText(playbackBannerControlGlue.getTitle());
                viewHolder.getSubtitle().setText(playbackBannerControlGlue.getSubtitle());
            }
        }) { // from class: androidx.leanback.media.PlaybackBannerControlGlue.2
            @Override // androidx.leanback.widget.PlaybackControlsRowPresenter, androidx.leanback.widget.RowPresenter
            public final void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
                super.onBindRowViewHolder(viewHolder, obj);
                viewHolder.setOnKeyListener(PlaybackBannerControlGlue.this);
            }

            @Override // androidx.leanback.widget.PlaybackControlsRowPresenter, androidx.leanback.widget.RowPresenter
            public final void onUnbindRowViewHolder(RowPresenter.ViewHolder viewHolder) {
                super.onUnbindRowViewHolder(viewHolder);
                viewHolder.setOnKeyListener(null);
            }
        };
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4 && i != 111) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                    break;
                default:
                    Action actionForKeyCode = this.mControlsRow.getActionForKeyCode(this.mControlsRow.getPrimaryActionsAdapter(), i);
                    if (actionForKeyCode == null) {
                        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
                        actionForKeyCode = playbackControlsRow.getActionForKeyCode(playbackControlsRow.getSecondaryActionsAdapter(), i);
                    }
                    if (actionForKeyCode == null) {
                        return false;
                    }
                    if (keyEvent.getAction() == 0) {
                        dispatchAction(actionForKeyCode, keyEvent);
                    }
                    return true;
            }
        }
        int i2 = this.mPlaybackSpeed;
        if (!(i2 >= 10 || i2 <= -10)) {
            return false;
        }
        play();
        onUpdatePlaybackStatusAfterUserAction();
        return i == 4 || i == 111;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final void onPlayCompleted() {
        super.onPlayCompleted();
        this.mIsPlaying = false;
        this.mPlaybackSpeed = 0;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        onUpdatePlaybackState();
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public final void onPlayStateChanged() {
        onUpdatePlaybackState();
        super.onPlayStateChanged();
    }

    public final void onUpdatePlaybackState() {
        updatePlaybackState(this.mIsPlaying);
    }

    public final void onUpdatePlaybackStatusAfterUserAction() {
        updatePlaybackState(this.mIsPlaying);
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.media.PlaybackGlue
    public void pause() {
        this.mIsPlaying = false;
        this.mPlaybackSpeed = 0;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        super.pause();
        onUpdatePlaybackState();
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.media.PlaybackGlue
    public void play() {
        if (this.mPlayerAdapter.isPrepared()) {
            if (this.mPlaybackSpeed != 0 || this.mPlayerAdapter.getCurrentPosition() < this.mPlayerAdapter.getDuration()) {
                this.mStartPosition = getCurrentPosition();
            } else {
                this.mStartPosition = 0L;
            }
            this.mStartTime = System.currentTimeMillis();
            this.mIsPlaying = true;
            this.mPlaybackSpeed = 1;
            this.mPlayerAdapter.seekTo(this.mStartPosition);
            super.play();
            onUpdatePlaybackState();
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public void setControlsRow(PlaybackControlsRow playbackControlsRow) {
        super.setControlsRow(playbackControlsRow);
        onUpdatePlaybackState();
    }

    public PlaybackBannerControlGlue(Context context, int[] iArr, int[] iArr2, T t) {
        super(context, t);
        this.mPlaybackSpeed = 0;
        this.mStartPosition = 0L;
        if (iArr.length == 0 || iArr.length > 5) {
            throw new IllegalArgumentException("invalid fastForwardSpeeds array size");
        }
        this.mFastForwardSpeeds = iArr;
        if (iArr2.length == 0 || iArr2.length > 5) {
            throw new IllegalArgumentException("invalid rewindSpeeds array size");
        }
        this.mRewindSpeeds = iArr2;
        if ((this.mPlayerAdapter.getSupportedActions() & 128) != 0) {
            this.mIsCustomizedFastForwardSupported = true;
        }
        if ((this.mPlayerAdapter.getSupportedActions() & 32) != 0) {
            this.mIsCustomizedRewindSupported = true;
        }
    }
}
