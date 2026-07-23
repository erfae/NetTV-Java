package com.nettv.livestore.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.activity.ComponentDialog$$ExternalSyntheticLambda0;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.PlayErrorDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.CatchUpEpg;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.DemoUtil;
import com.nettv.livestore.utils.Utils;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class CatchUpPlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, SeekBar.OnSeekBarChangeListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    public ImageButton btn_back;
    public ImageButton btn_forward;
    public ImageButton btn_next;
    public ImageButton btn_play;
    public ImageButton btn_previous;
    public ImageButton btn_rewind;
    public List<CatchUpEpg> catchUpEpgList;
    public CatchUpEpg currentEpg;
    public DataSource.Factory dataSourceFactory;
    public PlayErrorDlgFragment errorDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public ComponentDialog$$ExternalSyntheticLambda0 hideInfoTicker;
    public ImageView image_forward;
    public ImageView image_rewind;
    public ConstraintLayout ly_control;
    public int maxTime;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public SeekBar seekBar;
    public TrackSelectionParameters trackSelectionParameters;
    public TextView txt_end_time;
    public TextView txt_name;
    public TextView txt_start_time;
    public int duration = 0;
    public Handler handler = new Handler();
    public int program_position = 0;
    public int error_count = 0;
    public String content_url = "";
    public String stream_id = "";
    public WordModels wordModels = new WordModels();
    private final Runnable mUpdateTimeTask = new Runnable() { // from class: com.nettv.livestore.activities.CatchUpPlayerActivity.2
        @Override // java.lang.Runnable
        public void run() {
            try {
                ExoPlayer exoPlayer = CatchUpPlayerActivity.this.player;
                if (exoPlayer != null) {
                    long duration = exoPlayer.getDuration();
                    long currentPosition = CatchUpPlayerActivity.this.player.getCurrentPosition();
                    CatchUpPlayerActivity.this.txt_start_time.setText("" + Utils.milliSecondsToTimer(currentPosition));
                    CatchUpPlayerActivity.this.txt_end_time.setText("" + Utils.milliSecondsToTimer(duration));
                    int progressPercentage = Utils.getProgressPercentage(currentPosition, duration);
                    CatchUpPlayerActivity.this.seekBar.setProgress(progressPercentage);
                    if (progressPercentage > 98) {
                        CatchUpPlayerActivity catchUpPlayerActivity = CatchUpPlayerActivity.this;
                        catchUpPlayerActivity.handler.removeCallbacks(catchUpPlayerActivity.mUpdateTimeTask);
                        return;
                    }
                }
            } catch (Exception unused) {
                CatchUpPlayerActivity.this.seekBar.setProgress(0);
            }
            CatchUpPlayerActivity.this.handler.postDelayed(this, 1000L);
        }
    };

    public class PlayerEventListener implements Player.Listener {
        private PlayerEventListener() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            Player.Listener.CC.$default$onAudioAttributesChanged(this, audioAttributes);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onAudioSessionIdChanged(int i) {
            Player.Listener.CC.$default$onAudioSessionIdChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            Player.Listener.CC.$default$onAvailableCommandsChanged(this, commands);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onCues(CueGroup cueGroup) {
            Player.Listener.CC.$default$onCues(this, cueGroup);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onCues(List list) {
            Player.Listener.CC.$default$onCues(this, list);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            Player.Listener.CC.$default$onDeviceInfoChanged(this, deviceInfo);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onDeviceVolumeChanged(int i, boolean z) {
            Player.Listener.CC.$default$onDeviceVolumeChanged(this, i, z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onEvents(Player player, Player.Events events) {
            Player.Listener.CC.$default$onEvents(this, player, events);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onIsLoadingChanged(boolean z) {
            Player.Listener.CC.$default$onIsLoadingChanged(this, z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onIsPlayingChanged(boolean z) {
            Player.Listener.CC.$default$onIsPlayingChanged(this, z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onLoadingChanged(boolean z) {
            Player.Listener.CC.$default$onLoadingChanged(this, z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onMaxSeekToPreviousPositionChanged(long j) {
            Player.Listener.CC.$default$onMaxSeekToPreviousPositionChanged(this, j);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i) {
            Player.Listener.CC.$default$onMediaItemTransition(this, mediaItem, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            Player.Listener.CC.$default$onMediaMetadataChanged(this, mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onMetadata(Metadata metadata) {
            Player.Listener.CC.$default$onMetadata(this, metadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlayWhenReadyChanged(boolean z, int i) {
            Player.Listener.CC.$default$onPlayWhenReadyChanged(this, z, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Player.Listener.CC.$default$onPlaybackParametersChanged(this, playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlaybackStateChanged(int i) {
            if (i == 4) {
                CatchUpPlayerActivity.this.releaseMediaPlayer();
                CatchUpPlayerActivity catchUpPlayerActivity = CatchUpPlayerActivity.this;
                catchUpPlayerActivity.playVideo(catchUpPlayerActivity.content_url);
            } else {
                if (i == 3) {
                    CatchUpPlayerActivity catchUpPlayerActivity2 = CatchUpPlayerActivity.this;
                    catchUpPlayerActivity2.error_count = 0;
                    catchUpPlayerActivity2.image_forward.setVisibility(8);
                    CatchUpPlayerActivity.this.image_rewind.setVisibility(8);
                    return;
                }
                if (i == 2 && CatchUpPlayerActivity.this.ly_control.getVisibility() == 0) {
                    CatchUpPlayerActivity catchUpPlayerActivity3 = CatchUpPlayerActivity.this;
                    catchUpPlayerActivity3.handler.removeCallbacks(catchUpPlayerActivity3.hideInfoTicker);
                    CatchUpPlayerActivity.this.listTimer();
                }
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlayerError(PlaybackException playbackException) {
            if (playbackException.errorCode == 1002) {
                CatchUpPlayerActivity.this.image_forward.setVisibility(8);
                CatchUpPlayerActivity.this.image_rewind.setVisibility(8);
                CatchUpPlayerActivity.this.releaseMediaPlayer();
                CatchUpPlayerActivity catchUpPlayerActivity = CatchUpPlayerActivity.this;
                catchUpPlayerActivity.playVideo(catchUpPlayerActivity.content_url);
                return;
            }
            CatchUpPlayerActivity catchUpPlayerActivity2 = CatchUpPlayerActivity.this;
            int i = catchUpPlayerActivity2.error_count;
            if (i > 2) {
                catchUpPlayerActivity2.releaseMediaPlayer();
                CatchUpPlayerActivity.this.showPlayErrorDlgFragment();
            } else {
                catchUpPlayerActivity2.error_count = i + 1;
                catchUpPlayerActivity2.releaseMediaPlayer();
                CatchUpPlayerActivity catchUpPlayerActivity3 = CatchUpPlayerActivity.this;
                catchUpPlayerActivity3.playVideo(catchUpPlayerActivity3.content_url);
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlayerErrorChanged(PlaybackException playbackException) {
            Player.Listener.CC.$default$onPlayerErrorChanged(this, playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
            Player.Listener.CC.$default$onPlayerStateChanged(this, z, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            Player.Listener.CC.$default$onPlaylistMetadataChanged(this, mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPositionDiscontinuity(int i) {
            Player.Listener.CC.$default$onPositionDiscontinuity(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            Player.Listener.CC.$default$onPositionDiscontinuity(this, positionInfo, positionInfo2, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onRenderedFirstFrame() {
            Player.Listener.CC.$default$onRenderedFirstFrame(this);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onRepeatModeChanged(int i) {
            Player.Listener.CC.$default$onRepeatModeChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onSeekBackIncrementChanged(long j) {
            Player.Listener.CC.$default$onSeekBackIncrementChanged(this, j);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onSeekForwardIncrementChanged(long j) {
            Player.Listener.CC.$default$onSeekForwardIncrementChanged(this, j);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onSeekProcessed() {
            Player.Listener.CC.$default$onSeekProcessed(this);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            Player.Listener.CC.$default$onShuffleModeEnabledChanged(this, z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onSkipSilenceEnabledChanged(boolean z) {
            Player.Listener.CC.$default$onSkipSilenceEnabledChanged(this, z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
            Player.Listener.CC.$default$onSurfaceSizeChanged(this, i, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
            Player.Listener.CC.$default$onTimelineChanged(this, timeline, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
            Player.Listener.CC.$default$onTrackSelectionParametersChanged(this, trackSelectionParameters);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onTracksChanged(Tracks tracks) {
            Player.Listener.CC.$default$onTracksChanged(this, tracks);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
            Player.Listener.CC.$default$onVideoSizeChanged(this, videoSize);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onVolumeChanged(float f) {
            Player.Listener.CC.$default$onVolumeChanged(this, f);
        }
    }

    private MediaSource.Factory createMediaSourceFactory() {
        DefaultDrmSessionManagerProvider defaultDrmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
        defaultDrmSessionManagerProvider.setDrmHttpDataSourceFactory(DemoUtil.getHttpDataSourceFactory(this));
        return new DefaultMediaSourceFactory(this).setDataSourceFactory(this.dataSourceFactory).setDrmSessionManagerProvider((DrmSessionManagerProvider) defaultDrmSessionManagerProvider);
    }

    private boolean getControlButtonFocus() {
        return this.btn_previous.hasFocus() || this.btn_rewind.hasFocus() || this.btn_play.hasFocus() || this.btn_forward.hasFocus() || this.btn_next.hasFocus();
    }

    private void initView() {
        StyledPlayerView styledPlayerView = (StyledPlayerView) findViewById(R.id.player_view);
        this.playerView = styledPlayerView;
        styledPlayerView.setResizeMode(3);
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_previous = (ImageButton) findViewById(R.id.btn_previous);
        this.btn_rewind = (ImageButton) findViewById(R.id.btn_rewind);
        this.btn_play = (ImageButton) findViewById(R.id.btn_play);
        this.btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        this.btn_next = (ImageButton) findViewById(R.id.btn_next);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        this.txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.image_forward = (ImageView) findViewById(R.id.image_forward);
        this.image_rewind = (ImageView) findViewById(R.id.image_rewind);
        this.seekBar.setMax(100);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.btn_rewind.setOnClickListener(this);
        this.btn_play.setOnClickListener(this);
        this.btn_forward.setOnClickListener(this);
        this.btn_next.setOnClickListener(this);
        this.btn_previous.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.btn_rewind.setOnFocusChangeListener(this);
        this.btn_play.setOnFocusChangeListener(this);
        this.btn_forward.setOnFocusChangeListener(this);
        this.btn_next.setOnFocusChangeListener(this);
        this.btn_previous.setOnFocusChangeListener(this);
        this.btn_back.setOnFocusChangeListener(this);
        this.playerView.getVideoSurfaceView().setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        if (this.ly_control.getVisibility() != 8) {
            this.ly_control.setVisibility(8);
            return;
        }
        this.ly_control.setVisibility(0);
        this.handler.removeCallbacks(this.hideInfoTicker);
        listTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$listTimer$0() {
        if (this.maxTime < 1) {
            this.ly_control.setVisibility(8);
        } else {
            runNextTicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void listTimer() {
        this.maxTime = 10;
        ComponentDialog$$ExternalSyntheticLambda0 componentDialog$$ExternalSyntheticLambda0 = new ComponentDialog$$ExternalSyntheticLambda0(this, 7);
        this.hideInfoTicker = componentDialog$$ExternalSyntheticLambda0;
        componentDialog$$ExternalSyntheticLambda0.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playCatchUp(int i) {
        CatchUpEpg catchUpEpg = this.catchUpEpgList.get(i);
        this.currentEpg = catchUpEpg;
        this.txt_name.setText(Utils.decode64String(catchUpEpg.getTitle()));
        this.content_url = this.currentEpg.getUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.stream_id);
        releaseMediaPlayer();
        this.ly_control.setVisibility(0);
        playVideo(this.content_url);
        listTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playVideo(String str) {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        String adaptiveMimeTypeForContentType = Util.getAdaptiveMimeTypeForContentType(Util.inferContentType(Uri.parse(str), ""));
        MediaItem.Builder builder = new MediaItem.Builder();
        builder.setUri(Uri.parse(str)).setMediaMetadata(new MediaMetadata.Builder().setTitle("title").build()).setMimeType(adaptiveMimeTypeForContentType);
        MediaItem mediaItemBuild = builder.build();
        ExoPlayer.Builder mediaSourceFactory = new ExoPlayer.Builder(this).setMediaSourceFactory(createMediaSourceFactory());
        setRenderersFactory(mediaSourceFactory, false);
        ExoPlayer exoPlayerBuild = mediaSourceFactory.build();
        this.player = exoPlayerBuild;
        exoPlayerBuild.setTrackSelectionParameters(this.trackSelectionParameters);
        this.player.addListener(new PlayerEventListener());
        this.player.addAnalyticsListener(new EventLogger());
        this.player.setAudioAttributes(AudioAttributes.DEFAULT, true);
        this.player.setPlayWhenReady(true);
        this.playerView.setPlayer(this.player);
        this.player.setMediaItem(mediaItemBuild);
        this.player.prepare();
        this.player.play();
        this.handler.removeCallbacks(this.mUpdateTimeTask);
        updateProgressBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseMediaPlayer() {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer == null) {
            return;
        }
        exoPlayer.stop();
        this.player.release();
        this.player = null;
        this.playerView.setPlayer(null);
    }

    private void runNextTicker() {
        this.maxTime--;
        this.handler.postAtTime(this.hideInfoTicker, SystemClock.uptimeMillis() + 1000);
    }

    private void seekToForward() {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            long currentPosition = exoPlayer.getCurrentPosition();
            long duration = this.player.getDuration();
            int i = this.duration + 30;
            this.duration = i;
            if (duration < ((long) i) * 1000) {
                this.player.seekTo(duration - 30);
            } else {
                this.player.seekTo((((long) i) * 1000) + currentPosition);
            }
            this.duration = 0;
            this.handler.removeCallbacks(this.mUpdateTimeTask);
            updateProgressBar();
        }
    }

    private void seekToRewind() {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            long currentPosition = exoPlayer.getCurrentPosition();
            int i = this.duration + 30;
            this.duration = i;
            if (currentPosition < ((long) i) * 1000) {
                this.player.seekTo(1L);
            } else {
                this.player.seekTo(currentPosition - (((long) i) * 1000));
            }
            this.duration = 0;
            this.handler.removeCallbacks(this.mUpdateTimeTask);
            updateProgressBar();
        }
    }

    private void setRenderersFactory(ExoPlayer.Builder builder, boolean z) {
        builder.setRenderersFactory(DemoUtil.buildRenderersFactory(this, z));
    }

    private void showExitDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_exit");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        ExitDlgFragment exitDlgFragmentNewInstance = ExitDlgFragment.newInstance(this.wordModels.getStop_playback(), this.wordModels.getPlayback_description(), this.wordModels.getStr_yes(), this.wordModels.getNo());
        this.exitDlgFragment = exitDlgFragmentNewInstance;
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.CatchUpPlayerActivity.4
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                CatchUpPlayerActivity.this.exitDlgFragment.dismiss();
                CatchUpPlayerActivity.this.releaseMediaPlayer();
                CatchUpPlayerActivity.this.finish();
            }
        });
        this.exitDlgFragment.show(supportFragmentManager, "fragment_exit");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPlayErrorDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_error");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        PlayErrorDlgFragment playErrorDlgFragmentNewInstance = PlayErrorDlgFragment.newInstance(this.wordModels.getPlay_back_error(), this.wordModels.getPlay_back_error_description(), false);
        this.errorDlgFragment = playErrorDlgFragmentNewInstance;
        playErrorDlgFragmentNewInstance.setOkButtonClickListener(new PlayErrorDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.CatchUpPlayerActivity.3
            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                CatchUpPlayerActivity catchUpPlayerActivity = CatchUpPlayerActivity.this;
                if (catchUpPlayerActivity.program_position < catchUpPlayerActivity.catchUpEpgList.size() - 1) {
                    CatchUpPlayerActivity catchUpPlayerActivity2 = CatchUpPlayerActivity.this;
                    int i = catchUpPlayerActivity2.program_position + 1;
                    catchUpPlayerActivity2.program_position = i;
                    catchUpPlayerActivity2.playCatchUp(i);
                }
            }

            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onOkClick() {
                CatchUpPlayerActivity.this.finish();
            }
        });
        this.errorDlgFragment.show(supportFragmentManager, "fragment_error");
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0092  */
    /* JADX WARN: Code duplicated, block: B:40:0x009a  */
    /* JADX WARN: Code duplicated, block: B:41:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:43:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:44:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:46:0x00bc A[RETURN] */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                if (this.ly_control.getVisibility() == 0) {
                    this.ly_control.setVisibility(8);
                    return true;
                }
                showExitDlgFragment();
                return false;
            }
            if (keyCode == 85) {
                ExoPlayer exoPlayer = this.player;
                if (exoPlayer != null) {
                    if (exoPlayer.getPlayWhenReady()) {
                        this.player.setPlayWhenReady(false);
                        this.btn_play.setImageResource(R.drawable.ic_play);
                    } else {
                        this.player.setPlayWhenReady(true);
                        this.btn_play.setImageResource(R.drawable.ic_pause);
                    }
                }
            } else if (keyCode == 89) {
                if (this.ly_control.getVisibility() == 8) {
                    this.image_rewind.setVisibility(0);
                    seekToRewind();
                } else if (this.btn_previous.hasFocus()) {
                    return true;
                }
            } else if (keyCode != 90) {
                switch (keyCode) {
                    case 19:
                        if (getControlButtonFocus()) {
                            this.seekBar.requestFocus();
                            return true;
                        }
                        if (this.seekBar.hasFocus()) {
                            this.btn_back.requestFocus();
                            return true;
                        }
                        break;
                    case 20:
                        if (this.ly_control.getVisibility() == 8) {
                            this.ly_control.setVisibility(0);
                            this.btn_play.requestFocus();
                            this.handler.removeCallbacks(this.hideInfoTicker);
                            listTimer();
                            return true;
                        }
                        if (this.btn_back.hasFocus()) {
                            this.seekBar.requestFocus();
                            return true;
                        }
                        if (this.seekBar.hasFocus()) {
                            this.btn_play.requestFocus();
                            return true;
                        }
                        break;
                    case 21:
                        if (this.ly_control.getVisibility() == 8) {
                            this.image_rewind.setVisibility(0);
                            seekToRewind();
                        } else if (this.btn_previous.hasFocus()) {
                            return true;
                        }
                        break;
                    case 22:
                        if (this.ly_control.getVisibility() == 8) {
                            this.image_forward.setVisibility(0);
                            seekToForward();
                        }
                        break;
                    case 23:
                        if (this.ly_control.getVisibility() == 8) {
                            this.ly_control.setVisibility(0);
                            this.btn_play.requestFocus();
                            this.handler.removeCallbacks(this.hideInfoTicker);
                            listTimer();
                            return true;
                        }
                        break;
                }
            } else if (this.ly_control.getVisibility() == 8) {
                this.image_forward.setVisibility(0);
                seekToForward();
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back /* 2131427464 */:
                showExitDlgFragment();
                break;
            case R.id.btn_forward /* 2131427470 */:
                this.handler.removeCallbacks(this.hideInfoTicker);
                listTimer();
                seekToForward();
                break;
            case R.id.btn_next /* 2131427477 */:
                if (this.program_position < this.catchUpEpgList.size() - 1) {
                    int i = this.program_position + 1;
                    this.program_position = i;
                    playCatchUp(i);
                }
                break;
            case R.id.btn_play /* 2131427480 */:
                ExoPlayer exoPlayer = this.player;
                if (exoPlayer != null) {
                    if (!exoPlayer.getPlayWhenReady()) {
                        this.player.setPlayWhenReady(true);
                        this.btn_play.setImageResource(R.drawable.ic_pause);
                    } else {
                        this.player.setPlayWhenReady(false);
                        this.btn_play.setImageResource(R.drawable.ic_play);
                    }
                }
                break;
            case R.id.btn_previous /* 2131427482 */:
                int i2 = this.program_position;
                if (i2 > 0) {
                    int i3 = i2 - 1;
                    this.program_position = i3;
                    playCatchUp(i3);
                }
                break;
            case R.id.btn_rewind /* 2131427486 */:
                this.handler.removeCallbacks(this.hideInfoTicker);
                listTimer();
                seekToRewind();
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_catch_up_player);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.dataSourceFactory = DemoUtil.getDataSourceFactory(this);
        if (bundle != null) {
            this.trackSelectionParameters = TrackSelectionParameters.fromBundle(bundle.getBundle(KEY_TRACK_SELECTION_PARAMETERS));
        } else {
            this.trackSelectionParameters = new TrackSelectionParameters.Builder(this).build();
        }
        this.program_position = getIntent().getIntExtra("position", 0);
        this.stream_id = getIntent().getStringExtra("stream_id");
        this.catchUpEpgList = (List) new Gson().fromJson(getIntent().getStringExtra("epg_model"), new TypeToken<List<CatchUpEpg>>() { // from class: com.nettv.livestore.activities.CatchUpPlayerActivity.1
        }.getType());
        playCatchUp(this.program_position);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        if (z) {
            this.handler.removeCallbacks(this.hideInfoTicker);
            listTimer();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (this.player == null || !z) {
            return;
        }
        this.handler.removeCallbacks(this.mUpdateTimeTask);
        seekBar.setProgress(i);
        long duration = (int) ((this.player.getDuration() * ((long) i)) / 100);
        this.player.seekTo(duration);
        TextView textView = this.txt_start_time;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("");
        sbM.append(Utils.milliSecondsToTimer(duration));
        textView.setText(sbM.toString());
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.player == null || seekBar.getId() != R.id.seekBar) {
            return;
        }
        this.handler.removeCallbacks(this.mUpdateTimeTask);
        long jProgressToTimer = Utils.progressToTimer(seekBar.getProgress(), this.player.getDuration());
        this.player.seekTo(jProgressToTimer);
        TextView textView = this.txt_start_time;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("");
        sbM.append(Utils.milliSecondsToTimer(jProgressToTimer));
        textView.setText(sbM.toString());
    }

    public void updateProgressBar() {
        this.handler.postDelayed(this.mUpdateTimeTask, 100L);
    }
}
