package com.nettv.livestore.activities.mobile;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Rational;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentDialog$$ExternalSyntheticLambda0;
import androidx.annotation.NonNull;
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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.CaptionStyleCompat;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.dlgfragment.EpisodeDlgFragment;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.PlayErrorDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.ResumeModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.DemoUtil;
import com.nettv.livestore.utils.Utils;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SeriesMobilePlayer extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    public AudioManager audioManager;
    public SeekBar brightSeekBar;
    public ImageButton btn_audio;
    public ImageButton btn_back;
    public ImageButton btn_down;
    public ImageButton btn_forward;
    public ImageButton btn_play;
    public ImageButton btn_resolution;
    public ImageButton btn_rewind;
    public ImageButton btn_sub;
    public String contentUrl;
    public DataSource.Factory dataSourceFactory;
    public EpisodeDlgFragment episodeDlgFragment;
    public List<EpisodeModel> episodeModels;
    public int episode_position;
    public PlayErrorDlgFragment errorDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public ComponentDialog$$ExternalSyntheticLambda0 hideInfoTicker;
    public ConstraintLayout ly_control;
    public int maxTime;
    public String name;
    public PictureInPictureParams.Builder pictureInPictureParams;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public ExitDlgFragment resumeDlgFragment;
    public String resume_key;
    public String season_name;
    public int season_pos;
    public SeekBar seekBar;
    public String series_name;
    public TrackSelectionParameters trackSelectionParameters;
    public DefaultTrackSelector trackSelector;
    public TextView txt_end_time;
    public TextView txt_name;
    public TextView txt_start_time;
    public int volumeLevel;
    public SeekBar volumeSeekBar;
    public WordModels wordModels = new WordModels();
    public long last_position = 0;
    public int error_count = 0;
    public int duration = 0;
    public boolean is_system_setting = false;
    public Handler handler = new Handler();
    private final Runnable mUpdateTimeTask = new Runnable() { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer.4
        @Override // java.lang.Runnable
        public void run() {
            try {
                ExoPlayer exoPlayer = SeriesMobilePlayer.this.player;
                if (exoPlayer != null) {
                    long duration = exoPlayer.getDuration();
                    long currentPosition = SeriesMobilePlayer.this.player.getCurrentPosition();
                    SeriesMobilePlayer.this.txt_start_time.setText(Utils.milliSecondsToTimer(currentPosition));
                    SeriesMobilePlayer.this.txt_end_time.setText(Utils.milliSecondsToTimer(duration));
                    int progressPercentage = Utils.getProgressPercentage(currentPosition, duration);
                    SeriesMobilePlayer.this.seekBar.setProgress(progressPercentage);
                    if (progressPercentage > 98) {
                        SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                        seriesMobilePlayer.handler.removeCallbacks(seriesMobilePlayer.mUpdateTimeTask);
                        return;
                    }
                }
            } catch (Exception unused) {
                SeriesMobilePlayer.this.seekBar.setProgress(0);
            }
            SeriesMobilePlayer.this.handler.postDelayed(this, 1000L);
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
                SeriesMobilePlayer.this.releasePlayer();
                SeriesMobilePlayer.this.playNextEpisode();
            } else if (i == 3) {
                SeriesMobilePlayer.this.error_count = 0;
            } else if (i == 2 && SeriesMobilePlayer.this.ly_control.getVisibility() == 0) {
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                seriesMobilePlayer.handler.removeCallbacks(seriesMobilePlayer.hideInfoTicker);
                SeriesMobilePlayer.this.listTimer();
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlayerError(@NonNull PlaybackException playbackException) {
            if (playbackException.errorCode == 1002) {
                SeriesMobilePlayer.this.releasePlayer();
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                seriesMobilePlayer.last_position = seriesMobilePlayer.getLastPosition(seriesMobilePlayer.resume_key);
                SeriesMobilePlayer seriesMobilePlayer2 = SeriesMobilePlayer.this;
                seriesMobilePlayer2.playVideo(seriesMobilePlayer2.contentUrl, seriesMobilePlayer2.last_position);
                return;
            }
            SeriesMobilePlayer seriesMobilePlayer3 = SeriesMobilePlayer.this;
            int i = seriesMobilePlayer3.error_count;
            if (i > 3) {
                seriesMobilePlayer3.releasePlayer();
                SeriesMobilePlayer.this.showPlayErrorDlgFragment();
            } else {
                seriesMobilePlayer3.error_count = i + 1;
                seriesMobilePlayer3.releasePlayer();
                SeriesMobilePlayer seriesMobilePlayer4 = SeriesMobilePlayer.this;
                seriesMobilePlayer4.playVideo(seriesMobilePlayer4.contentUrl, 0L);
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

    private void checkAddedRecent(String str) {
        Iterator<ResumeModel> it = this.preferenceHelper.getSharedPreferenceSeriesResumeModel().iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(str)) {
                it.remove();
            }
        }
    }

    private MediaSource.Factory createMediaSourceFactory() {
        DefaultDrmSessionManagerProvider defaultDrmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
        defaultDrmSessionManagerProvider.setDrmHttpDataSourceFactory(DemoUtil.getHttpDataSourceFactory(this));
        return new DefaultMediaSourceFactory(this).setDataSourceFactory(this.dataSourceFactory).setDrmSessionManagerProvider((DrmSessionManagerProvider) defaultDrmSessionManagerProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getLastPosition(String str) {
        List<ResumeModel> sharedPreferenceSeriesResumeModel = this.preferenceHelper.getSharedPreferenceSeriesResumeModel();
        for (int i = 0; i < sharedPreferenceSeriesResumeModel.size(); i++) {
            if (sharedPreferenceSeriesResumeModel.get(i).getName().equalsIgnoreCase(str)) {
                return sharedPreferenceSeriesResumeModel.get(i).getLast_position();
            }
        }
        return 0L;
    }

    private void initView() {
        StyledPlayerView styledPlayerView = (StyledPlayerView) findViewById(R.id.player_view);
        this.playerView = styledPlayerView;
        final int i = 0;
        styledPlayerView.getSubtitleView().setApplyEmbeddedStyles(false);
        this.playerView.getSubtitleView().setStyle(new CaptionStyleCompat(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()), Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()), 0, 0, 0, null));
        this.playerView.getSubtitleView().setFixedTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_rewind = (ImageButton) findViewById(R.id.btn_rewind);
        this.btn_play = (ImageButton) findViewById(R.id.btn_play);
        this.btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        this.btn_sub = (ImageButton) findViewById(R.id.btn_sub);
        this.btn_audio = (ImageButton) findViewById(R.id.btn_audio);
        this.btn_resolution = (ImageButton) findViewById(R.id.btn_resolution);
        this.btn_down = (ImageButton) findViewById(R.id.btn_down);
        this.txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        this.txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.seekBar = seekBar;
        seekBar.setMax(100);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.btn_back.setOnClickListener(this);
        this.btn_rewind.setOnClickListener(this);
        this.btn_play.setOnClickListener(this);
        this.btn_forward.setOnClickListener(this);
        this.btn_sub.setOnClickListener(this);
        this.btn_audio.setOnClickListener(this);
        this.btn_down.setOnClickListener(this);
        this.btn_resolution.setOnClickListener(this);
        findViewById(R.id.view_click).setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer$$ExternalSyntheticLambda0
            public final /* synthetic */ SeriesMobilePlayer f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$initView$1(view);
                        break;
                    default:
                        this.f$0.lambda$initView$2(view);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.playerView.getVideoSurfaceView().setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer$$ExternalSyntheticLambda0
            public final /* synthetic */ SeriesMobilePlayer f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$initView$1(view);
                        break;
                    default:
                        this.f$0.lambda$initView$2(view);
                        break;
                }
            }
        });
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.volume_seekbar);
        this.volumeSeekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.volumeSeekBar.setMax(100);
        this.volumeLevel = this.audioManager.getStreamVolume(3);
        this.volumeSeekBar.setProgress((int) ((this.volumeLevel / this.audioManager.getStreamMaxVolume(3)) * 100.0f));
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.bright_seekbar);
        this.brightSeekBar = seekBar3;
        seekBar3.setMax(255);
        this.brightSeekBar.setProgress(Settings.System.getInt(getContentResolver(), "screen_brightness", 0));
        this.brightSeekBar.setOnSeekBarChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        if (this.ly_control.getVisibility() == 0) {
            this.handler.removeCallbacks(this.hideInfoTicker);
            this.ly_control.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        if (this.ly_control.getVisibility() == 8) {
            this.ly_control.setVisibility(0);
            listTimer();
        }
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
        ComponentDialog$$ExternalSyntheticLambda0 componentDialog$$ExternalSyntheticLambda0 = new ComponentDialog$$ExternalSyntheticLambda0(this, 11);
        this.hideInfoTicker = componentDialog$$ExternalSyntheticLambda0;
        componentDialog$$ExternalSyntheticLambda0.run();
    }

    private void pictureInPictureMode() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.pictureInPictureParams.setAspectRatio(new Rational(16, 9));
            enterPictureInPictureMode(this.pictureInPictureParams.build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playEpisode(int i) {
        this.seekBar.setProgress(0);
        this.name = this.episodeModels.get(i).getTitle();
        this.resume_key = this.series_name + this.season_name + this.name;
        this.txt_name.setText(this.name);
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            this.contentUrl = this.episodeModels.get(i).getUrl();
        } else {
            this.contentUrl = GetSharedInfo.getEpisodeUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.episodeModels.get(i).getId(), this.episodeModels.get(i).getContainer_extension());
        }
        long lastPosition = getLastPosition(this.resume_key);
        this.last_position = lastPosition;
        if (lastPosition != 0) {
            this.ly_control.setVisibility(8);
            showResumeDlgFragment();
            return;
        }
        this.ly_control.setVisibility(0);
        this.btn_play.requestFocus();
        playVideo(this.contentUrl, 0L);
        this.handler.removeCallbacks(this.hideInfoTicker);
        listTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNextEpisode() {
        if (this.episode_position < this.episodeModels.size() - 1) {
            this.episode_position++;
            releasePlayer();
            playEpisode(this.episode_position);
        }
    }

    private void playPreviousEpisode() {
        int i = this.episode_position;
        if (i > 0) {
            this.episode_position = i - 1;
            releasePlayer();
            playEpisode(this.episode_position);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playVideo(String str, long j) {
        try {
            ExoPlayer exoPlayer = this.player;
            if (exoPlayer != null) {
                exoPlayer.release();
            }
            String adaptiveMimeTypeForContentType = Util.getAdaptiveMimeTypeForContentType(Util.inferContentType(Uri.parse(str), ""));
            MediaItem.Builder builder = new MediaItem.Builder();
            builder.setUri(Uri.parse(str)).setMediaMetadata(new MediaMetadata.Builder().setTitle("title").build()).setMimeType(adaptiveMimeTypeForContentType);
            MediaItem mediaItemBuild = builder.build();
            Tracks tracks = Tracks.EMPTY;
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(this);
            this.trackSelector = defaultTrackSelector;
            defaultTrackSelector.setParameters(this.trackSelectionParameters);
            ExoPlayer.Builder trackSelector = new ExoPlayer.Builder(this).setMediaSourceFactory(createMediaSourceFactory()).setTrackSelector(this.trackSelector);
            setRenderersFactory(trackSelector, true);
            ExoPlayer exoPlayerBuild = trackSelector.build();
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
            if (j != 0) {
                this.player.seekTo(j);
            }
            this.handler.removeCallbacks(this.mUpdateTimeTask);
            updateProgressBar();
        } catch (Exception unused) {
        }
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
            int i = this.duration + 10;
            this.duration = i;
            if (duration < ((long) i) * 1000) {
                this.player.seekTo(duration - 10);
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
            int i = this.duration + 10;
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

    private void showAudioTracksList() {
        DefaultTrackSelector defaultTrackSelector = this.trackSelector;
        if (defaultTrackSelector == null) {
            return;
        }
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo == null) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_audio(), 1).show();
            return;
        }
        String audio_track = this.wordModels.getAudio_track();
        int rendererType = currentMappedTrackInfo.getRendererType(1);
        boolean z = rendererType == 2 || (rendererType == 1 && currentMappedTrackInfo.getTypeSupport(2) == 0);
        TrackSelectionDialogBuilder trackSelectionDialogBuilder = new TrackSelectionDialogBuilder(this, audio_track, this.player, 1);
        trackSelectionDialogBuilder.setAllowAdaptiveSelections(z);
        trackSelectionDialogBuilder.setShowDisableOption(false);
        trackSelectionDialogBuilder.build().show();
    }

    private void showEpisodesDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("episode_group");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        EpisodeDlgFragment episodeDlgFragmentNewInstance = EpisodeDlgFragment.newInstance(this.episodeModels, this.season_pos, this.episode_position);
        this.episodeDlgFragment = episodeDlgFragmentNewInstance;
        episodeDlgFragmentNewInstance.setOnEpisodeItemListener(new EpisodeDlgFragment.OnEpisodeItemListener() { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer.5
            @Override // com.nettv.livestore.dlgfragment.EpisodeDlgFragment.OnEpisodeItemListener
            public void OnEpisodeSelected(EpisodeModel episodeModel, int i) {
                SeriesMobilePlayer.this.episodeDlgFragment.dismiss();
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                if (seriesMobilePlayer.episode_position != i) {
                    seriesMobilePlayer.episode_position = i;
                    seriesMobilePlayer.releasePlayer();
                    SeriesMobilePlayer seriesMobilePlayer2 = SeriesMobilePlayer.this;
                    seriesMobilePlayer2.playEpisode(seriesMobilePlayer2.episode_position);
                }
            }

            @Override // com.nettv.livestore.dlgfragment.EpisodeDlgFragment.OnEpisodeItemListener
            public void OnSeeAllEpisodes() {
                SeriesMobilePlayer.this.releasePlayer();
                SeriesMobilePlayer.this.finish();
            }
        });
        this.episodeDlgFragment.show(supportFragmentManager, "episode_group");
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer.2
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                seriesMobilePlayer.handler.removeCallbacks(seriesMobilePlayer.hideInfoTicker);
                SeriesMobilePlayer.this.ly_control.setVisibility(0);
                SeriesMobilePlayer.this.listTimer();
                SeriesMobilePlayer.this.btn_play.requestFocus();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                SeriesMobilePlayer.this.exitDlgFragment.dismiss();
                SeriesMobilePlayer.this.releasePlayer();
                SeriesMobilePlayer.this.finish();
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
        playErrorDlgFragmentNewInstance.setOkButtonClickListener(new PlayErrorDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer.3
            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                if (seriesMobilePlayer.episode_position < seriesMobilePlayer.episodeModels.size() - 1) {
                    SeriesMobilePlayer seriesMobilePlayer2 = SeriesMobilePlayer.this;
                    seriesMobilePlayer2.episode_position++;
                    seriesMobilePlayer2.releasePlayer();
                    SeriesMobilePlayer seriesMobilePlayer3 = SeriesMobilePlayer.this;
                    seriesMobilePlayer3.playEpisode(seriesMobilePlayer3.episode_position);
                }
            }

            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onOkClick() {
                SeriesMobilePlayer.this.finish();
            }
        });
        this.errorDlgFragment.show(supportFragmentManager, "fragment_error");
    }

    private void showResumeDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_resume");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        ExitDlgFragment exitDlgFragmentNewInstance = ExitDlgFragment.newInstance(this.wordModels.getResume(), this.wordModels.getResume_plyaback_from_ast_position(), this.wordModels.getStr_yes(), this.wordModels.getNo());
        this.resumeDlgFragment = exitDlgFragmentNewInstance;
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.mobile.SeriesMobilePlayer.1
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                SeriesMobilePlayer.this.ly_control.setVisibility(0);
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                seriesMobilePlayer.playVideo(seriesMobilePlayer.contentUrl, 0L);
                SeriesMobilePlayer seriesMobilePlayer2 = SeriesMobilePlayer.this;
                seriesMobilePlayer2.handler.removeCallbacks(seriesMobilePlayer2.hideInfoTicker);
                SeriesMobilePlayer.this.listTimer();
                SeriesMobilePlayer.this.btn_play.requestFocus();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                SeriesMobilePlayer.this.resumeDlgFragment.dismiss();
                SeriesMobilePlayer.this.ly_control.setVisibility(0);
                SeriesMobilePlayer.this.btn_play.requestFocus();
                SeriesMobilePlayer seriesMobilePlayer = SeriesMobilePlayer.this;
                seriesMobilePlayer.playVideo(seriesMobilePlayer.contentUrl, seriesMobilePlayer.last_position);
                SeriesMobilePlayer seriesMobilePlayer2 = SeriesMobilePlayer.this;
                seriesMobilePlayer2.handler.removeCallbacks(seriesMobilePlayer2.hideInfoTicker);
                SeriesMobilePlayer.this.listTimer();
            }
        });
        this.resumeDlgFragment.show(supportFragmentManager, "fragment_resume");
    }

    private void showSubTracksList() {
        DefaultTrackSelector defaultTrackSelector = this.trackSelector;
        if (defaultTrackSelector == null) {
            return;
        }
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo();
        boolean z = true;
        if (currentMappedTrackInfo == null) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
            return;
        }
        String subtitle = this.wordModels.getSubtitle();
        int rendererType = currentMappedTrackInfo.getRendererType(3);
        if (rendererType != 2 && (rendererType != 3 || currentMappedTrackInfo.getTypeSupport(2) != 0)) {
            z = false;
        }
        TrackSelectionDialogBuilder trackSelectionDialogBuilder = new TrackSelectionDialogBuilder(this, subtitle, this.player, 3);
        trackSelectionDialogBuilder.setAllowAdaptiveSelections(z);
        trackSelectionDialogBuilder.build().show();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 4) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_audio /* 2131427463 */:
                showAudioTracksList();
                break;
            case R.id.btn_back /* 2131427464 */:
                showExitDlgFragment();
                break;
            case R.id.btn_down /* 2131427468 */:
                showEpisodesDlgFragment();
                break;
            case R.id.btn_forward /* 2131427470 */:
                this.handler.removeCallbacks(this.hideInfoTicker);
                listTimer();
                seekToForward();
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
            case R.id.btn_resolution /* 2131427484 */:
                ExoPlayer exoPlayer2 = this.player;
                if (exoPlayer2 != null && exoPlayer2.getPlayWhenReady()) {
                    if (this.playerView.getResizeMode() != 3) {
                        this.playerView.setResizeMode(3);
                    } else {
                        this.playerView.setResizeMode(0);
                    }
                    break;
                }
                break;
            case R.id.btn_rewind /* 2131427486 */:
                this.handler.removeCallbacks(this.hideInfoTicker);
                listTimer();
                seekToRewind();
                break;
            case R.id.btn_sub /* 2131427489 */:
                showSubTracksList();
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_series_mobile_player);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        if (Build.VERSION.SDK_INT >= 26) {
            this.pictureInPictureParams = new PictureInPictureParams.Builder();
        }
        initView();
        this.episode_position = getIntent().getIntExtra("position", 0);
        this.season_pos = getIntent().getIntExtra("season_pos", 0);
        this.series_name = getIntent().getStringExtra("series_name");
        this.season_name = getIntent().getStringExtra("season_name");
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            this.episodeModels = RealmController.with().getEpisodesBySeason(this.series_name, this.season_name);
        } else {
            this.episodeModels = this.preferenceHelper.getSharedPreferenceEpisodeModels();
        }
        this.dataSourceFactory = DemoUtil.getDataSourceFactory(this);
        this.trackSelectionParameters = new TrackSelectionParameters.Builder(this).setTrackTypeDisabled(3, !this.preferenceHelper.getSharedPreferenceSubtitleEnable()).build();
        playEpisode(this.episode_position);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            StyledPlayerView styledPlayerView = this.playerView;
            if (styledPlayerView != null) {
                styledPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int id = seekBar.getId();
        if (id != R.id.bright_seekbar) {
            if (id != R.id.seekBar) {
                if (id != R.id.volume_seekbar) {
                    return;
                }
                this.audioManager.setStreamVolume(3, (this.audioManager.getStreamMaxVolume(3) * i) / 100, 0);
                return;
            }
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
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.System.canWrite(this)) {
                Settings.System.putInt(getContentResolver(), "screen_brightness_mode", 0);
                Settings.System.putInt(getContentResolver(), "screen_brightness", i);
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.screenBrightness = i / 255.0f;
                window.setAttributes(attributes);
                return;
            }
            if (this.is_system_setting) {
                return;
            }
            this.is_system_setting = true;
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("package:");
            sbM2.append(getPackageName());
            intent.setData(Uri.parse(sbM2.toString()));
            startActivity(intent);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            StyledPlayerView styledPlayerView = this.playerView;
            if (styledPlayerView != null) {
                styledPlayerView.onPause();
            }
            releasePlayer();
        }
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

    @Override // android.app.Activity
    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
        releasePlayer();
    }

    public void releasePlayer() {
        if (this.player != null) {
            checkAddedRecent(this.resume_key);
            if (this.player.getCurrentPosition() > 120000 && this.player.getCurrentPosition() + 10000 < this.player.getDuration()) {
                ResumeModel resumeModel = new ResumeModel();
                resumeModel.setName(this.resume_key);
                resumeModel.setLast_position(this.player.getCurrentPosition());
                resumeModel.setPro((int) ((this.player.getCurrentPosition() * 100) / this.player.getDuration()));
                List<ResumeModel> sharedPreferenceSeriesResumeModel = this.preferenceHelper.getSharedPreferenceSeriesResumeModel();
                sharedPreferenceSeriesResumeModel.add(0, resumeModel);
                this.preferenceHelper.setSharedPreferenceSeriesResumeModel(sharedPreferenceSeriesResumeModel);
            }
            this.player.stop();
            this.player.release();
            this.player = null;
        }
    }

    public void updateProgressBar() {
        this.handler.postDelayed(this.mUpdateTimeTask, 100L);
    }
}
