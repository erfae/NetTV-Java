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
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
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
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.MovieInfoDlgFragment;
import com.nettv.livestore.dlgfragment.PlayErrorDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.utils.DemoUtil;
import com.nettv.livestore.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class MovieMobilePlayer extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    public AudioManager audioManager;
    public SeekBar brightSeekBar;
    public ImageButton btn_audio;
    public ImageButton btn_back;
    public ImageButton btn_fav;
    public ImageButton btn_forward;
    public ImageButton btn_info;
    public ImageButton btn_play;
    public ImageButton btn_resolution;
    public ImageButton btn_rewind;
    public ImageButton btn_sub;
    public MovieModel currentMovie;
    public DataSource.Factory dataSourceFactory;
    public String description;
    public PlayErrorDlgFragment errorDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public ComponentDialog$$ExternalSyntheticLambda0 hideInfoTicker;
    public MovieInfoDlgFragment infoDlgFragment;
    public ConstraintLayout ly_control;
    public int maxTime;
    public PictureInPictureParams.Builder pictureInPictureParams;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public ExitDlgFragment resumeDlgFragment;
    public SeekBar seekBar;
    public TrackSelectionParameters trackSelectionParameters;
    public DefaultTrackSelector trackSelector;
    public TextView txt_end_time;
    public TextView txt_name;
    public TextView txt_start_time;
    public View viewClick;
    public int volumeLevel;
    public SeekBar volumeSeekBar;
    public String cont_url = "";
    public String movie_name = "";
    public String category_name = "";
    public String tmdb_id = "";
    public String stream_id = "";
    public long last_position = 0;
    public int error_count = 0;
    public int duration = 0;
    public Handler handler = new Handler();
    public String resolution = "1920x1080";
    public boolean is_system_setting = false;
    public boolean is_fav = false;
    public WordModels wordModels = new WordModels();
    private final Runnable mUpdateTimeTask = new Runnable() { // from class: com.nettv.livestore.activities.mobile.MovieMobilePlayer.2
        @Override // java.lang.Runnable
        public void run() {
            try {
                ExoPlayer exoPlayer = MovieMobilePlayer.this.player;
                if (exoPlayer != null) {
                    long duration = exoPlayer.getDuration();
                    long currentPosition = MovieMobilePlayer.this.player.getCurrentPosition();
                    MovieMobilePlayer.this.txt_start_time.setText("" + Utils.milliSecondsToTimer(currentPosition));
                    MovieMobilePlayer.this.txt_end_time.setText("" + Utils.milliSecondsToTimer(duration));
                    int progressPercentage = Utils.getProgressPercentage(currentPosition, duration);
                    MovieMobilePlayer.this.seekBar.setProgress(progressPercentage);
                    if (progressPercentage > 98) {
                        MovieMobilePlayer movieMobilePlayer = MovieMobilePlayer.this;
                        movieMobilePlayer.handler.removeCallbacks(movieMobilePlayer.mUpdateTimeTask);
                        return;
                    }
                }
            } catch (Exception unused) {
                MovieMobilePlayer.this.seekBar.setProgress(0);
            }
            MovieMobilePlayer.this.handler.postDelayed(this, 1000L);
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
                MovieMobilePlayer.this.releaseMediaPlayer();
                MovieMobilePlayer movieMobilePlayer = MovieMobilePlayer.this;
                movieMobilePlayer.playVideo(movieMobilePlayer.cont_url, 0L);
            } else if (i == 3) {
                MovieMobilePlayer.this.error_count = 0;
            } else if (i == 2 && MovieMobilePlayer.this.ly_control.getVisibility() == 0) {
                MovieMobilePlayer movieMobilePlayer2 = MovieMobilePlayer.this;
                movieMobilePlayer2.handler.removeCallbacks(movieMobilePlayer2.hideInfoTicker);
                MovieMobilePlayer.this.listTimer();
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlayerError(PlaybackException playbackException) {
            if (playbackException.errorCode == 1002) {
                MovieMobilePlayer.this.releaseMediaPlayer();
                MovieMobilePlayer movieMobilePlayer = MovieMobilePlayer.this;
                movieMobilePlayer.playVideo(movieMobilePlayer.cont_url, 0L);
                return;
            }
            MovieMobilePlayer movieMobilePlayer2 = MovieMobilePlayer.this;
            int i = movieMobilePlayer2.error_count;
            if (i > 3) {
                movieMobilePlayer2.releaseMediaPlayer();
                MovieMobilePlayer.this.showPlayErrorDlgFragment();
            } else {
                movieMobilePlayer2.error_count = i + 1;
                movieMobilePlayer2.releaseMediaPlayer();
                MovieMobilePlayer movieMobilePlayer3 = MovieMobilePlayer.this;
                movieMobilePlayer3.playVideo(movieMobilePlayer3.cont_url, 0L);
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

    private boolean checkAdultMovies(String str) {
        return str.contains("xxx") || str.contains("porn") || str.contains("adult");
    }

    private MediaSource.Factory createMediaSourceFactory() {
        DefaultDrmSessionManagerProvider defaultDrmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
        defaultDrmSessionManagerProvider.setDrmHttpDataSourceFactory(DemoUtil.getHttpDataSourceFactory(this));
        return new DefaultMediaSourceFactory(this).setDataSourceFactory(this.dataSourceFactory).setDrmSessionManagerProvider((DrmSessionManagerProvider) defaultDrmSessionManagerProvider);
    }

    private void initView() {
        StyledPlayerView styledPlayerView = (StyledPlayerView) findViewById(R.id.player_view);
        this.playerView = styledPlayerView;
        styledPlayerView.getSubtitleView().setApplyEmbeddedStyles(false);
        CaptionStyleCompat captionStyleCompat = new CaptionStyleCompat(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()), Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()), 0, 0, 0, null);
        this.playerView.getSubtitleView().setFixedTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.playerView.getSubtitleView().setStyle(captionStyleCompat);
        this.viewClick = findViewById(R.id.view_click);
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_rewind = (ImageButton) findViewById(R.id.btn_rewind);
        this.btn_play = (ImageButton) findViewById(R.id.btn_play);
        this.btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        this.btn_info = (ImageButton) findViewById(R.id.btn_info);
        this.btn_sub = (ImageButton) findViewById(R.id.btn_sub);
        this.btn_audio = (ImageButton) findViewById(R.id.btn_audio);
        this.btn_resolution = (ImageButton) findViewById(R.id.btn_resolution);
        this.btn_fav = (ImageButton) findViewById(R.id.btn_fav);
        this.txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        this.txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.seekBar = seekBar;
        seekBar.setMax(100);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.btn_rewind.setOnClickListener(this);
        this.btn_play.setOnClickListener(this);
        this.btn_forward.setOnClickListener(this);
        this.btn_info.setOnClickListener(this);
        this.btn_sub.setOnClickListener(this);
        this.btn_audio.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.btn_resolution.setOnClickListener(this);
        this.btn_fav.setOnClickListener(this);
        this.viewClick.setOnClickListener(this);
        this.playerView.getVideoSurfaceView().setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 7));
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
    public /* synthetic */ void lambda$initView$3(View view) {
        if (this.ly_control.getVisibility() == 8) {
            this.ly_control.setVisibility(0);
            listTimer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$listTimer$2() {
        if (this.maxTime < 1) {
            this.ly_control.setVisibility(8);
        } else {
            runNextTicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4() {
        this.preferenceHelper.setSharedPreferenceVodFavNames(RealmController.with().getFavMovieNames());
        Toast.makeText(this, this.wordModels.getMovie_is_removed_from_fav(), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5() {
        this.preferenceHelper.setSharedPreferenceVodFavNames(RealmController.with().getFavMovieNames());
        Toast.makeText(this, this.wordModels.getMovie_is_added_to_fav(), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseMediaPlayer$0() {
        this.preferenceHelper.setSharedPreferenceResumeModel(RealmController.with().getResumeMovies());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseMediaPlayer$1() {
        this.preferenceHelper.setSharedPreferenceResumeModel(RealmController.with().getResumeMovies());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void listTimer() {
        this.maxTime = 10;
        ComponentDialog$$ExternalSyntheticLambda0 componentDialog$$ExternalSyntheticLambda0 = new ComponentDialog$$ExternalSyntheticLambda0(this, 10);
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
    public void playVideo(String str, long j) {
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
        this.player.addAnalyticsListener(new AnalyticsListener() { // from class: com.nettv.livestore.activities.mobile.MovieMobilePlayer.1
            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
                AnalyticsListener.CC.$default$onAudioAttributesChanged(this, eventTime, audioAttributes);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
                AnalyticsListener.CC.$default$onAudioCodecError(this, eventTime, exc);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j2) {
                AnalyticsListener.CC.$default$onAudioDecoderInitialized(this, eventTime, str2, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j2, long j3) {
                AnalyticsListener.CC.$default$onAudioDecoderInitialized(this, eventTime, str2, j2, j3);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String str2) {
                AnalyticsListener.CC.$default$onAudioDecoderReleased(this, eventTime, str2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
                AnalyticsListener.CC.$default$onAudioDisabled(this, eventTime, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
                AnalyticsListener.CC.$default$onAudioEnabled(this, eventTime, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
                AnalyticsListener.CC.$default$onAudioInputFormatChanged(this, eventTime, format);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
                AnalyticsListener.CC.$default$onAudioInputFormatChanged(this, eventTime, format, decoderReuseEvaluation);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioPositionAdvancing(AnalyticsListener.EventTime eventTime, long j2) {
                AnalyticsListener.CC.$default$onAudioPositionAdvancing(this, eventTime, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onAudioSessionIdChanged(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioSinkError(AnalyticsListener.EventTime eventTime, Exception exc) {
                AnalyticsListener.CC.$default$onAudioSinkError(this, eventTime, exc);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i, long j2, long j3) {
                AnalyticsListener.CC.$default$onAudioUnderrun(this, eventTime, i, j2, j3);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAvailableCommandsChanged(AnalyticsListener.EventTime eventTime, Player.Commands commands) {
                AnalyticsListener.CC.$default$onAvailableCommandsChanged(this, eventTime, commands);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i, long j2, long j3) {
                AnalyticsListener.CC.$default$onBandwidthEstimate(this, eventTime, i, j2, j3);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onCues(AnalyticsListener.EventTime eventTime, CueGroup cueGroup) {
                AnalyticsListener.CC.$default$onCues(this, eventTime, cueGroup);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onCues(AnalyticsListener.EventTime eventTime, List list) {
                AnalyticsListener.CC.$default$onCues(this, eventTime, list);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDecoderDisabled(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
                AnalyticsListener.CC.$default$onDecoderDisabled(this, eventTime, i, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDecoderEnabled(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
                AnalyticsListener.CC.$default$onDecoderEnabled(this, eventTime, i, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int i, String str2, long j2) {
                AnalyticsListener.CC.$default$onDecoderInitialized(this, eventTime, i, str2, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDecoderInputFormatChanged(AnalyticsListener.EventTime eventTime, int i, Format format) {
                AnalyticsListener.CC.$default$onDecoderInputFormatChanged(this, eventTime, i, format);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDeviceInfoChanged(AnalyticsListener.EventTime eventTime, DeviceInfo deviceInfo) {
                AnalyticsListener.CC.$default$onDeviceInfoChanged(this, eventTime, deviceInfo);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDeviceVolumeChanged(AnalyticsListener.EventTime eventTime, int i, boolean z) {
                AnalyticsListener.CC.$default$onDeviceVolumeChanged(this, eventTime, i, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
                AnalyticsListener.CC.$default$onDownstreamFormatChanged(this, eventTime, mediaLoadData);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onDrmKeysLoaded(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onDrmKeysRemoved(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onDrmKeysRestored(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onDrmSessionAcquired(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onDrmSessionAcquired(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
                AnalyticsListener.CC.$default$onDrmSessionManagerError(this, eventTime, exc);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onDrmSessionReleased(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i, long j2) {
                AnalyticsListener.CC.$default$onDroppedVideoFrames(this, eventTime, i, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onEvents(Player player, AnalyticsListener.Events events) {
                AnalyticsListener.CC.$default$onEvents(this, player, events);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
                AnalyticsListener.CC.$default$onIsLoadingChanged(this, eventTime, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
                AnalyticsListener.CC.$default$onIsPlayingChanged(this, eventTime, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                AnalyticsListener.CC.$default$onLoadCanceled(this, eventTime, loadEventInfo, mediaLoadData);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                AnalyticsListener.CC.$default$onLoadCompleted(this, eventTime, loadEventInfo, mediaLoadData);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
                AnalyticsListener.CC.$default$onLoadError(this, eventTime, loadEventInfo, mediaLoadData, iOException, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                AnalyticsListener.CC.$default$onLoadStarted(this, eventTime, loadEventInfo, mediaLoadData);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
                AnalyticsListener.CC.$default$onLoadingChanged(this, eventTime, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onMaxSeekToPreviousPositionChanged(AnalyticsListener.EventTime eventTime, long j2) {
                AnalyticsListener.CC.$default$onMaxSeekToPreviousPositionChanged(this, eventTime, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onMediaItemTransition(AnalyticsListener.EventTime eventTime, MediaItem mediaItem, int i) {
                AnalyticsListener.CC.$default$onMediaItemTransition(this, eventTime, mediaItem, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onMediaMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
                AnalyticsListener.CC.$default$onMediaMetadataChanged(this, eventTime, mediaMetadata);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
                AnalyticsListener.CC.$default$onMetadata(this, eventTime, metadata);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean z, int i) {
                AnalyticsListener.CC.$default$onPlayWhenReadyChanged(this, eventTime, z, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
                AnalyticsListener.CC.$default$onPlaybackParametersChanged(this, eventTime, playbackParameters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onPlaybackStateChanged(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onPlaybackSuppressionReasonChanged(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
                AnalyticsListener.CC.$default$onPlayerError(this, eventTime, playbackException);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlayerErrorChanged(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
                AnalyticsListener.CC.$default$onPlayerErrorChanged(this, eventTime, playbackException);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlayerReleased(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onPlayerReleased(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean z, int i) {
                AnalyticsListener.CC.$default$onPlayerStateChanged(this, eventTime, z, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPlaylistMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
                AnalyticsListener.CC.$default$onPlaylistMetadataChanged(this, eventTime, mediaMetadata);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onPositionDiscontinuity(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
                AnalyticsListener.CC.$default$onPositionDiscontinuity(this, eventTime, positionInfo, positionInfo2, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j2) {
                AnalyticsListener.CC.$default$onRenderedFirstFrame(this, eventTime, obj, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onRepeatModeChanged(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSeekBackIncrementChanged(AnalyticsListener.EventTime eventTime, long j2) {
                AnalyticsListener.CC.$default$onSeekBackIncrementChanged(this, eventTime, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSeekForwardIncrementChanged(AnalyticsListener.EventTime eventTime, long j2) {
                AnalyticsListener.CC.$default$onSeekForwardIncrementChanged(this, eventTime, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSeekProcessed(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onSeekProcessed(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
                AnalyticsListener.CC.$default$onSeekStarted(this, eventTime);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z) {
                AnalyticsListener.CC.$default$onShuffleModeChanged(this, eventTime, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean z) {
                AnalyticsListener.CC.$default$onSkipSilenceEnabledChanged(this, eventTime, z);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2) {
                AnalyticsListener.CC.$default$onSurfaceSizeChanged(this, eventTime, i, i2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onTimelineChanged(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onTrackSelectionParametersChanged(AnalyticsListener.EventTime eventTime, TrackSelectionParameters trackSelectionParameters) {
                AnalyticsListener.CC.$default$onTrackSelectionParametersChanged(this, eventTime, trackSelectionParameters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onTracksChanged(AnalyticsListener.EventTime eventTime, Tracks tracks2) {
                AnalyticsListener.CC.$default$onTracksChanged(this, eventTime, tracks2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
                AnalyticsListener.CC.$default$onUpstreamDiscarded(this, eventTime, mediaLoadData);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
                AnalyticsListener.CC.$default$onVideoCodecError(this, eventTime, exc);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j2) {
                AnalyticsListener.CC.$default$onVideoDecoderInitialized(this, eventTime, str2, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j2, long j3) {
                AnalyticsListener.CC.$default$onVideoDecoderInitialized(this, eventTime, str2, j2, j3);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String str2) {
                AnalyticsListener.CC.$default$onVideoDecoderReleased(this, eventTime, str2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
                AnalyticsListener.CC.$default$onVideoDisabled(this, eventTime, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
                AnalyticsListener.CC.$default$onVideoEnabled(this, eventTime, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoFrameProcessingOffset(AnalyticsListener.EventTime eventTime, long j2, int i) {
                AnalyticsListener.CC.$default$onVideoFrameProcessingOffset(this, eventTime, j2, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
                AnalyticsListener.CC.$default$onVideoInputFormatChanged(this, eventTime, format);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
                AnalyticsListener.CC.$default$onVideoInputFormatChanged(this, eventTime, format, decoderReuseEvaluation);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2, int i3, float f) {
                AnalyticsListener.CC.$default$onVideoSizeChanged(this, eventTime, i, i2, i3, f);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public void onVideoSizeChanged(@NonNull AnalyticsListener.EventTime eventTime, @NonNull VideoSize videoSize) {
                MovieMobilePlayer.this.resolution = videoSize.width + "x" + videoSize.height;
                Objects.requireNonNull(MovieMobilePlayer.this);
                Objects.requireNonNull(MovieMobilePlayer.this);
                AnalyticsListener.CC.$default$onVideoSizeChanged(this, eventTime, videoSize);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f) {
                AnalyticsListener.CC.$default$onVolumeChanged(this, eventTime, f);
            }
        });
        this.player.setAudioAttributes(AudioAttributes.DEFAULT, true);
        this.player.setPlayWhenReady(true);
        this.playerView.setPlayer(this.player);
        this.player.setMediaItem(mediaItemBuild);
        this.player.prepare();
        this.player.play();
        if (j > 0) {
            this.player.seekTo(j);
        }
        this.handler.removeCallbacks(this.mUpdateTimeTask);
        updateProgressBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseMediaPlayer() {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer == null) {
            return;
        }
        if (exoPlayer.getCurrentPosition() <= 120000 || this.player.getCurrentPosition() + 60000 >= this.player.getDuration()) {
            RealmController.with().addPositionToMovies(this.movie_name, this.tmdb_id, false, 0L, 0, new MovieMobilePlayer$$ExternalSyntheticLambda0(this, 3));
        } else {
            RealmController.with().addPositionToMovies(this.movie_name, this.tmdb_id, true, this.player.getCurrentPosition(), (int) ((this.player.getCurrentPosition() * 100) / this.player.getDuration()), new MovieMobilePlayer$$ExternalSyntheticLambda0(this, 2));
        }
        this.player.stop();
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

    private void showAudioTrackDlgFragment() {
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.mobile.MovieMobilePlayer.4
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                MovieMobilePlayer movieMobilePlayer = MovieMobilePlayer.this;
                movieMobilePlayer.handler.removeCallbacks(movieMobilePlayer.hideInfoTicker);
                MovieMobilePlayer.this.ly_control.setVisibility(0);
                MovieMobilePlayer.this.btn_play.requestFocus();
                MovieMobilePlayer.this.listTimer();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                MovieMobilePlayer.this.exitDlgFragment.dismiss();
                MovieMobilePlayer.this.releaseMediaPlayer();
                MovieMobilePlayer.this.finish();
            }
        });
        this.exitDlgFragment.show(supportFragmentManager, "fragment_exit");
    }

    private void showInfoDlgFragment(String str, String str2, String str3, String str4) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_info");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        MovieInfoDlgFragment movieInfoDlgFragmentNewInstance = MovieInfoDlgFragment.newInstance(str, str2, str3, str4);
        this.infoDlgFragment = movieInfoDlgFragmentNewInstance;
        movieInfoDlgFragmentNewInstance.show(supportFragmentManager, "fragment_info");
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
        PlayErrorDlgFragment playErrorDlgFragmentNewInstance = PlayErrorDlgFragment.newInstance(this.wordModels.getPlay_back_error(), this.wordModels.getPlay_back_error_description(), true);
        this.errorDlgFragment = playErrorDlgFragmentNewInstance;
        playErrorDlgFragmentNewInstance.setOkButtonClickListener(new PlayErrorDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.mobile.MovieMobilePlayer.5
            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onOkClick() {
                MovieMobilePlayer.this.finish();
            }
        });
        this.errorDlgFragment.show(supportFragmentManager, "fragment_error");
    }

    private void showResumeDlgFragment(final String str) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_resume");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        ExitDlgFragment exitDlgFragmentNewInstance = ExitDlgFragment.newInstance(this.wordModels.getResume(), this.wordModels.getResume_plyaback_from_ast_position(), this.wordModels.getStr_yes(), this.wordModels.getNo());
        this.resumeDlgFragment = exitDlgFragmentNewInstance;
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.mobile.MovieMobilePlayer.3
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                MovieMobilePlayer.this.playVideo(str, 0L);
                MovieMobilePlayer.this.ly_control.setVisibility(0);
                MovieMobilePlayer.this.listTimer();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                MovieMobilePlayer.this.resumeDlgFragment.dismiss();
                MovieMobilePlayer movieMobilePlayer = MovieMobilePlayer.this;
                movieMobilePlayer.playVideo(str, movieMobilePlayer.last_position);
                MovieMobilePlayer.this.ly_control.setVisibility(0);
                MovieMobilePlayer.this.listTimer();
            }
        });
        this.resumeDlgFragment.show(supportFragmentManager, "fragment_resume");
    }

    private void showSubTitleTrackDlgFragment() {
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
        int i = 1;
        int i2 = 0;
        switch (view.getId()) {
            case R.id.btn_audio /* 2131427463 */:
                showAudioTrackDlgFragment();
                break;
            case R.id.btn_back /* 2131427464 */:
                showExitDlgFragment();
                break;
            case R.id.btn_fav /* 2131427469 */:
                if (checkAdultMovies(this.category_name)) {
                    Toast.makeText(this, this.wordModels.getCant_add_this_movie(), 0).show();
                } else if (!this.is_fav) {
                    this.is_fav = true;
                    this.btn_fav.setColorFilter(getResources().getColor(R.color.yellow));
                    RealmController.with().addToFavMovie(this.currentMovie.getName(), true, new MovieMobilePlayer$$ExternalSyntheticLambda0(this, i));
                } else {
                    this.is_fav = false;
                    this.btn_fav.setColorFilter(getResources().getColor(R.color.gray));
                    RealmController.with().addToFavMovie(this.currentMovie.getName(), false, new MovieMobilePlayer$$ExternalSyntheticLambda0(this, i2));
                }
                break;
            case R.id.btn_forward /* 2131427470 */:
                this.handler.removeCallbacks(this.hideInfoTicker);
                listTimer();
                seekToForward();
                break;
            case R.id.btn_info /* 2131427474 */:
                MovieInfoDlgFragment movieInfoDlgFragment = this.infoDlgFragment;
                if (movieInfoDlgFragment != null && movieInfoDlgFragment.isAdded()) {
                    this.infoDlgFragment.dismiss();
                } else {
                    showInfoDlgFragment(this.movie_name, this.description, this.currentMovie.getStream_icon(), this.resolution);
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
                showSubTitleTrackDlgFragment();
                break;
            case R.id.view_click /* 2131428377 */:
                if (this.ly_control.getVisibility() == 0) {
                    this.handler.removeCallbacks(this.hideInfoTicker);
                    this.ly_control.setVisibility(8);
                }
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_mobile_player);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        if (Build.VERSION.SDK_INT >= 26) {
            this.pictureInPictureParams = new PictureInPictureParams.Builder();
        }
        this.description = getIntent().getStringExtra("description");
        this.movie_name = getIntent().getStringExtra("name");
        this.category_name = getIntent().getStringExtra("category_name");
        this.stream_id = getIntent().getStringExtra("stream_id");
        if (this.preferenceHelper.getSharedPreferenceISM3U() || this.stream_id.isEmpty()) {
            this.currentMovie = RealmController.with().getMovieByName(this.movie_name);
        } else {
            this.currentMovie = RealmController.with().getMovieById(this.stream_id);
        }
        this.is_fav = this.currentMovie.isIs_favorite();
        this.txt_name.setText(this.movie_name);
        if (this.is_fav) {
            this.btn_fav.setColorFilter(getResources().getColor(R.color.yellow));
        } else {
            this.btn_fav.setColorFilter(getResources().getColor(R.color.gray));
        }
        this.dataSourceFactory = DemoUtil.getDataSourceFactory(this);
        this.trackSelectionParameters = new TrackSelectionParameters.Builder(this).setTrackTypeDisabled(3, !this.preferenceHelper.getSharedPreferenceSubtitleEnable()).build();
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            this.cont_url = this.currentMovie.getUrl();
        } else {
            this.cont_url = GetSharedInfo.getMovieUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.currentMovie.getStream_id(), this.currentMovie.getExtension());
        }
        long time = this.currentMovie.getTime();
        this.last_position = time;
        if (time != 0) {
            showResumeDlgFragment(this.cont_url);
            return;
        }
        this.ly_control.setVisibility(0);
        playVideo(this.cont_url, 0L);
        listTimer();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            StyledPlayerView styledPlayerView = this.playerView;
            if (styledPlayerView != null) {
                styledPlayerView.onPause();
            }
            releaseMediaPlayer();
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
        if (Build.VERSION.SDK_INT > 23) {
            StyledPlayerView styledPlayerView = this.playerView;
            if (styledPlayerView != null) {
                styledPlayerView.onPause();
            }
            releaseMediaPlayer();
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
        releaseMediaPlayer();
    }

    public void updateProgressBar() {
        this.handler.postDelayed(this.mUpdateTimeTask, 100L);
    }
}
