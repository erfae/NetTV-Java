package com.nettv.livestore.activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.CaptionStyleCompat;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.gson.Gson;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.dlgfragment.AudioTrackDlgFragment;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.MovieInfoDlgFragment;
import com.nettv.livestore.dlgfragment.PlayErrorDlgFragment;
import com.nettv.livestore.dlgfragment.SubtitleTrackDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmChangeItemListener;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.SubtitleLinkModel;
import com.nettv.livestore.models.SubtitleModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetSubtitleDataRequest;
import com.nettv.livestore.remote.GetSubtitleLinkRequest;
import com.nettv.livestore.utils.DemoUtil;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.SubtitleView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class MoviePlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, SeekBar.OnSeekBarChangeListener, GetSubtitleDataRequest.OnGetResponseListener, GetSubtitleLinkRequest.OnGetLinkModelListener {
    public ImageButton btn_audio;
    public ImageButton btn_back;
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
    public MoviePlayerActivity$$ExternalSyntheticLambda3 hideInfoTicker;
    public MoviePlayerActivity$$ExternalSyntheticLambda3 hideResolutionTicker;
    public ImageView image_forward;
    public ImageView image_rewind;
    public MovieInfoDlgFragment infoDlgFragment;
    public ConstraintLayout ly_control;
    public int maxTime;
    public MediaItem playMediaItem;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public int resolutionTime;
    public ExitDlgFragment resumeDlgFragment;
    public SeekBar seekBar;
    public SubtitleView subtitleView;
    public TrackSelectionParameters trackSelectionParameters;
    public DefaultTrackSelector trackSelector;
    public TextView txt_end_time;
    public TextView txt_name;
    public TextView txt_resolution;
    public TextView txt_start_time;
    public String cont_url = "";
    public String movie_name = "";
    public String tmdb_id = "";
    public String stream_id = "";
    public long last_position = 0;
    public int error_count = 0;
    public int duration = 0;
    public int subtitle_position = -1;
    public int audio_position = 0;
    public Handler handler = new Handler();
    public String resolution = "1920x1080";
    public WordModels wordModels = new WordModels();
    private final Runnable mUpdateTimeTask = new Runnable() { // from class: com.nettv.livestore.activities.MoviePlayerActivity.2
        @Override // java.lang.Runnable
        public void run() {
            try {
                ExoPlayer exoPlayer = MoviePlayerActivity.this.player;
                if (exoPlayer != null) {
                    long duration = exoPlayer.getDuration();
                    long currentPosition = MoviePlayerActivity.this.player.getCurrentPosition();
                    MoviePlayerActivity.this.txt_start_time.setText("" + Utils.milliSecondsToTimer(currentPosition));
                    MoviePlayerActivity.this.txt_end_time.setText("" + Utils.milliSecondsToTimer(duration));
                    int progressPercentage = Utils.getProgressPercentage(currentPosition, duration);
                    MoviePlayerActivity.this.seekBar.setProgress(progressPercentage);
                    if (progressPercentage > 98) {
                        MoviePlayerActivity moviePlayerActivity = MoviePlayerActivity.this;
                        moviePlayerActivity.handler.removeCallbacks(moviePlayerActivity.mUpdateTimeTask);
                        return;
                    }
                }
            } catch (Exception unused) {
                MoviePlayerActivity.this.seekBar.setProgress(0);
            }
            MoviePlayerActivity.this.handler.postDelayed(this, 1000L);
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
                MoviePlayerActivity.this.releaseMediaPlayer();
                MoviePlayerActivity moviePlayerActivity = MoviePlayerActivity.this;
                moviePlayerActivity.playVideo(moviePlayerActivity.cont_url, 0L);
            } else {
                if (i == 3) {
                    MoviePlayerActivity moviePlayerActivity2 = MoviePlayerActivity.this;
                    moviePlayerActivity2.error_count = 0;
                    moviePlayerActivity2.image_forward.setVisibility(8);
                    MoviePlayerActivity.this.image_rewind.setVisibility(8);
                    return;
                }
                if (i == 2 && MoviePlayerActivity.this.ly_control.getVisibility() == 0) {
                    MoviePlayerActivity moviePlayerActivity3 = MoviePlayerActivity.this;
                    moviePlayerActivity3.handler.removeCallbacks(moviePlayerActivity3.hideInfoTicker);
                    MoviePlayerActivity.this.listTimer();
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
                MoviePlayerActivity.this.image_forward.setVisibility(8);
                MoviePlayerActivity.this.image_rewind.setVisibility(8);
                MoviePlayerActivity.this.releaseMediaPlayer();
                MoviePlayerActivity moviePlayerActivity = MoviePlayerActivity.this;
                moviePlayerActivity.playVideo(moviePlayerActivity.cont_url, 0L);
                return;
            }
            MoviePlayerActivity moviePlayerActivity2 = MoviePlayerActivity.this;
            int i = moviePlayerActivity2.error_count;
            if (i > 3) {
                moviePlayerActivity2.releaseMediaPlayer();
                MoviePlayerActivity.this.showPlayErrorDlgFragment();
            } else {
                moviePlayerActivity2.error_count = i + 1;
                moviePlayerActivity2.releaseMediaPlayer();
                MoviePlayerActivity moviePlayerActivity3 = MoviePlayerActivity.this;
                moviePlayerActivity3.playVideo(moviePlayerActivity3.cont_url, 0L);
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

    private void applySubtitleSelection(TrackGroupArray trackGroupArray, int i, int i2, int i3) {
        DefaultTrackSelector.Parameters.Builder builder = new DefaultTrackSelector.Parameters.Builder();
        if (i2 == -1 || i3 == -1) {
            builder.clearSelectionOverrides(i);
        } else {
            builder.setSelectionOverride(i, trackGroupArray, new DefaultTrackSelector.SelectionOverride(i2, i3));
        }
        this.trackSelector.setParameters(builder.build());
    }

    private MediaSource.Factory createMediaSourceFactory() {
        DefaultDrmSessionManagerProvider defaultDrmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
        defaultDrmSessionManagerProvider.setDrmHttpDataSourceFactory(DemoUtil.getHttpDataSourceFactory(this));
        return new DefaultMediaSourceFactory(this).setDataSourceFactory(this.dataSourceFactory).setDrmSessionManagerProvider((DrmSessionManagerProvider) defaultDrmSessionManagerProvider);
    }

    private void downloadSubtitleFromApi(int i) {
        if (this.preferenceHelper.getSharedPreferenceSubtitleUserModel() != null) {
            String token = this.preferenceHelper.getSharedPreferenceSubtitleUserModel().getToken();
            GetSubtitleLinkRequest getSubtitleLinkRequest = new GetSubtitleLinkRequest(this, 1000);
            getSubtitleLinkRequest.getResponse(Security.getFileData(i), Constants.SUBTITLE_DOWNLOAD, Constants.API_KEY.trim(), token);
            getSubtitleLinkRequest.setOnGetLinkModelListener(this);
        }
    }

    private boolean getControlButtonFocus() {
        return this.btn_rewind.hasFocus() || this.btn_play.hasFocus() || this.btn_forward.hasFocus();
    }

    private boolean getFeatureButtonFocus() {
        return this.btn_info.hasFocus() || this.btn_audio.hasFocus() || this.btn_sub.hasFocus() || this.btn_resolution.hasFocus();
    }

    private void getOpenSubtitleFromApi() {
        GetSubtitleDataRequest getSubtitleDataRequest = new GetSubtitleDataRequest(this, 1000);
        getSubtitleDataRequest.getResponse(Constants.SUBTITLE_SEARCH + this.tmdb_id, Constants.API_KEY);
        getSubtitleDataRequest.setOnGetResponseListener(this);
    }

    private void getSubtitleTrackFromVideo() {
        DefaultTrackSelector defaultTrackSelector = this.trackSelector;
        if (defaultTrackSelector == null) {
            return;
        }
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo == null) {
            String str = this.tmdb_id;
            if (str == null || str.isEmpty()) {
                Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
                return;
            } else {
                getOpenSubtitleFromApi();
                return;
            }
        }
        TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(3);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < trackGroups.length; i++) {
            TrackGroup trackGroup = trackGroups.get(i);
            for (int i2 = 0; i2 < trackGroup.length; i2++) {
                Format format = trackGroup.getFormat(i2);
                String str2 = format.language;
                if (str2 != null && !str2.isEmpty()) {
                    arrayList.add(Utils.getLanguageNameFromCode(format.language));
                }
            }
        }
        arrayList.add("None");
        if (arrayList.size() > 1) {
            showSubTitleTrackDlgFragment(trackGroups, arrayList);
            return;
        }
        String str3 = this.tmdb_id;
        if (str3 == null || str3.isEmpty()) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
        } else {
            getOpenSubtitleFromApi();
        }
    }

    private void initView() {
        StyledPlayerView styledPlayerView = (StyledPlayerView) findViewById(R.id.player_view);
        this.playerView = styledPlayerView;
        styledPlayerView.getSubtitleView().setApplyEmbeddedStyles(false);
        SubtitleView subtitleView = (SubtitleView) findViewById(R.id.subtitle_view);
        this.subtitleView = subtitleView;
        subtitleView.setVisibility(8);
        CaptionStyleCompat captionStyleCompat = new CaptionStyleCompat(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()), Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()), 0, 0, 0, null);
        this.playerView.getSubtitleView().setFixedTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.playerView.getSubtitleView().setStyle(captionStyleCompat);
        this.subtitleView.setTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.subtitleView.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()));
        this.subtitleView.setTextColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()));
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_rewind = (ImageButton) findViewById(R.id.btn_rewind);
        this.btn_play = (ImageButton) findViewById(R.id.btn_play);
        this.btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        this.btn_info = (ImageButton) findViewById(R.id.btn_info);
        this.btn_sub = (ImageButton) findViewById(R.id.btn_sub);
        this.btn_audio = (ImageButton) findViewById(R.id.btn_audio);
        this.btn_resolution = (ImageButton) findViewById(R.id.btn_resolution);
        this.txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        this.txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        this.image_forward = (ImageView) findViewById(R.id.image_forward);
        this.image_rewind = (ImageView) findViewById(R.id.image_rewind);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.txt_resolution = (TextView) findViewById(R.id.txt_resolution);
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
        this.btn_rewind.setOnFocusChangeListener(this);
        this.btn_play.setOnFocusChangeListener(this);
        this.btn_forward.setOnFocusChangeListener(this);
        this.btn_info.setOnFocusChangeListener(this);
        this.btn_sub.setOnFocusChangeListener(this);
        this.btn_audio.setOnFocusChangeListener(this);
        this.btn_resolution.setOnFocusChangeListener(this);
        this.btn_back.setOnFocusChangeListener(this);
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
    public /* synthetic */ void lambda$releaseMediaPlayer$0() {
        this.preferenceHelper.setSharedPreferenceResumeModel(RealmController.with().getResumeMovies());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseMediaPlayer$1() {
        this.preferenceHelper.setSharedPreferenceResumeModel(RealmController.with().getResumeMovies());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resolutionTimer$3() {
        if (this.resolutionTime < 1) {
            this.txt_resolution.setVisibility(8);
        } else {
            runNextResolutionTicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAudioTrackDlgFragment$4(List list, TrackGroupArray trackGroupArray, int i, int i2) {
        int i3;
        this.audio_position = i2;
        if (i2 == list.size() - 1) {
            ExoPlayer exoPlayer = this.player;
            if (exoPlayer != null) {
                exoPlayer.setVolume(0.0f);
                return;
            }
            return;
        }
        int i4 = i2;
        int i5 = 0;
        for (int i6 = 0; i6 < trackGroupArray.length && i4 >= (i3 = trackGroupArray.get(i6).length); i6++) {
            i4 -= i3;
            i5++;
        }
        ExoPlayer exoPlayer2 = this.player;
        if (exoPlayer2 != null) {
            exoPlayer2.setVolume(1.0f);
        }
        applySubtitleSelection(trackGroupArray, i, i5, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenSubtitleDlgFragment$6(List list, List list2, int i) {
        this.subtitle_position = i;
        if (i == list.size() - 1) {
            applySubtitleSelection(null, 3, -1, -1);
        } else if (((SubtitleModel.DataModel) list2.get(this.subtitle_position)).getAttributesModel().getFileModels().size() > 0) {
            downloadSubtitleFromApi(((SubtitleModel.DataModel) list2.get(this.subtitle_position)).getAttributesModel().getFileModels().get(0).getFile_id());
        } else {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSubTitleTrackDlgFragment$5(List list, TrackGroupArray trackGroupArray, int i, int i2) {
        int i3;
        this.subtitle_position = i2;
        if (i2 == list.size() - 1) {
            applySubtitleSelection(trackGroupArray, i, -1, -1);
            return;
        }
        int i4 = i2;
        int i5 = 0;
        for (int i6 = 0; i6 < trackGroupArray.length && i4 >= (i3 = trackGroupArray.get(i6).length); i6++) {
            i4 -= i3;
            i5++;
        }
        applySubtitleSelection(trackGroupArray, i, i5, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void listTimer() {
        this.maxTime = 10;
        MoviePlayerActivity$$ExternalSyntheticLambda3 moviePlayerActivity$$ExternalSyntheticLambda3 = new MoviePlayerActivity$$ExternalSyntheticLambda3(this, 1);
        this.hideInfoTicker = moviePlayerActivity$$ExternalSyntheticLambda3;
        moviePlayerActivity$$ExternalSyntheticLambda3.run();
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
        this.playMediaItem = builder.build();
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
        this.player.addAnalyticsListener(new AnalyticsListener() { // from class: com.nettv.livestore.activities.MoviePlayerActivity.1
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
            public final /* synthetic */ void onTracksChanged(AnalyticsListener.EventTime eventTime, Tracks tracks) {
                AnalyticsListener.CC.$default$onTracksChanged(this, eventTime, tracks);
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
                MoviePlayerActivity.this.resolution = videoSize.width + "x" + videoSize.height;
                Objects.requireNonNull(MoviePlayerActivity.this);
                Objects.requireNonNull(MoviePlayerActivity.this);
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
        this.player.setMediaItem(this.playMediaItem);
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
            final int i = 1;
            RealmController.with().addPositionToMovies(this.movie_name, this.tmdb_id, false, 0L, 0, new RealmChangeItemListener(this) { // from class: com.nettv.livestore.activities.MoviePlayerActivity$$ExternalSyntheticLambda2
                public final /* synthetic */ MoviePlayerActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.nettv.livestore.helper.RealmChangeItemListener
                public final void onItemChanged() {
                    switch (i) {
                        case 0:
                            this.f$0.lambda$releaseMediaPlayer$0();
                            break;
                        default:
                            this.f$0.lambda$releaseMediaPlayer$1();
                            break;
                    }
                }
            });
        } else {
            int currentPosition = (int) ((this.player.getCurrentPosition() * 100) / this.player.getDuration());
            final int i2 = 0;
            RealmController.with().addPositionToMovies(this.movie_name, this.tmdb_id, true, this.player.getCurrentPosition(), currentPosition, new RealmChangeItemListener(this) { // from class: com.nettv.livestore.activities.MoviePlayerActivity$$ExternalSyntheticLambda2
                public final /* synthetic */ MoviePlayerActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.nettv.livestore.helper.RealmChangeItemListener
                public final void onItemChanged() {
                    switch (i2) {
                        case 0:
                            this.f$0.lambda$releaseMediaPlayer$0();
                            break;
                        default:
                            this.f$0.lambda$releaseMediaPlayer$1();
                            break;
                    }
                }
            });
        }
        this.player.stop();
        this.player.release();
        this.player = null;
        this.playerView.setPlayer(null);
    }

    private void resolutionTimer() {
        this.resolutionTime = 2;
        MoviePlayerActivity$$ExternalSyntheticLambda3 moviePlayerActivity$$ExternalSyntheticLambda3 = new MoviePlayerActivity$$ExternalSyntheticLambda3(this, 0);
        this.hideResolutionTicker = moviePlayerActivity$$ExternalSyntheticLambda3;
        moviePlayerActivity$$ExternalSyntheticLambda3.run();
    }

    private void runNextResolutionTicker() {
        this.resolutionTime--;
        this.handler.postAtTime(this.hideResolutionTicker, SystemClock.uptimeMillis() + 1000);
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
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo;
        DefaultTrackSelector defaultTrackSelector = this.trackSelector;
        if (defaultTrackSelector == null || (currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo()) == null) {
            return;
        }
        TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(1);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < trackGroups.length; i++) {
            TrackGroup trackGroup = trackGroups.get(i);
            for (int i2 = 0; i2 < trackGroup.length; i2++) {
                arrayList.add(Utils.getLanguageNameFromCode(trackGroup.getFormat(i2).language));
            }
        }
        arrayList.add("None");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_audio");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            AudioTrackDlgFragment.newInstance(this, arrayList, this.audio_position, new MoviePlayerActivity$$ExternalSyntheticLambda1(this, arrayList, trackGroups, 1, 1)).show(supportFragmentManager, "fragment_audio");
        }
    }

    private void showDefaultAudioTrackDlgFragment() {
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.MoviePlayerActivity.4
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                MoviePlayerActivity moviePlayerActivity = MoviePlayerActivity.this;
                moviePlayerActivity.handler.removeCallbacks(moviePlayerActivity.hideInfoTicker);
                MoviePlayerActivity.this.ly_control.setVisibility(0);
                MoviePlayerActivity.this.btn_play.requestFocus();
                MoviePlayerActivity.this.listTimer();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                MoviePlayerActivity.this.exitDlgFragment.dismiss();
                MoviePlayerActivity.this.releaseMediaPlayer();
                MoviePlayerActivity.this.finish();
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

    private void showOpenSubtitleDlgFragment(List<SubtitleModel.DataModel> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<SubtitleModel.DataModel> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Utils.getLanguageNameFromCode(it.next().getAttributesModel().getLanguage()));
        }
        arrayList.add("None");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_subtitle");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            SubtitleTrackDlgFragment.newInstance(this, arrayList, this.subtitle_position, new MoviePlayerActivity$$ExternalSyntheticLambda0(this, arrayList, list, 0)).show(supportFragmentManager, "fragment_subtitle");
        }
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
        playErrorDlgFragmentNewInstance.setOkButtonClickListener(new PlayErrorDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.MoviePlayerActivity.5
            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onOkClick() {
                MoviePlayerActivity.this.finish();
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.MoviePlayerActivity.3
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                MoviePlayerActivity.this.playVideo(str, 0L);
                MoviePlayerActivity.this.ly_control.setVisibility(0);
                MoviePlayerActivity.this.listTimer();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                MoviePlayerActivity.this.resumeDlgFragment.dismiss();
                MoviePlayerActivity moviePlayerActivity = MoviePlayerActivity.this;
                moviePlayerActivity.playVideo(str, moviePlayerActivity.last_position);
                MoviePlayerActivity.this.ly_control.setVisibility(0);
                MoviePlayerActivity.this.listTimer();
            }
        });
        this.resumeDlgFragment.show(supportFragmentManager, "fragment_resume");
    }

    private void showSubTitleTrackDlgFragment(TrackGroupArray trackGroupArray, List<String> list) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_subtitle");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
        } else {
            SubtitleTrackDlgFragment.newInstance(this, list, this.subtitle_position, new MoviePlayerActivity$$ExternalSyntheticLambda1(this, list, trackGroupArray, 3, 0)).show(supportFragmentManager, "fragment_subtitle");
        }
    }

    @Override // com.nettv.livestore.remote.GetSubtitleLinkRequest.OnGetLinkModelListener
    public void OnGetLinkModelResult(JSONObject jSONObject, int i) {
        if (jSONObject == null) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
            return;
        }
        SubtitleLinkModel subtitleLinkModel = (SubtitleLinkModel) new Gson().fromJson(jSONObject.toString(), SubtitleLinkModel.class);
        if (this.player == null || this.playMediaItem == null) {
            return;
        }
        this.subtitleView.setVisibility(0);
        this.subtitleView.setPlayer(this.player);
        this.subtitleView.setSubSource(subtitleLinkModel.getLink());
    }

    @Override // com.nettv.livestore.remote.GetSubtitleDataRequest.OnGetResponseListener
    public void OnGetResponseResult(JSONObject jSONObject, int i) {
        if (jSONObject == null) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
            return;
        }
        try {
            SubtitleModel subtitleModel = (SubtitleModel) new Gson().fromJson(jSONObject.toString(), SubtitleModel.class);
            if (subtitleModel.getDataModels().size() > 0) {
                showOpenSubtitleDlgFragment(GetSharedInfo.getUniqueSubtitleModels(subtitleModel.getDataModels()));
            } else {
                Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
            }
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:48:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:49:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:51:0x00c3  */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                MovieInfoDlgFragment movieInfoDlgFragment = this.infoDlgFragment;
                if (movieInfoDlgFragment != null && movieInfoDlgFragment.isAdded()) {
                    this.infoDlgFragment.dismiss();
                    return true;
                }
                if (this.ly_control.getVisibility() == 0) {
                    this.ly_control.setVisibility(8);
                    return true;
                }
                showExitDlgFragment();
                return true;
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
                }
            } else if (keyCode != 90) {
                switch (keyCode) {
                    case 19:
                        if (getFeatureButtonFocus()) {
                            this.btn_play.requestFocus();
                            return true;
                        }
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
                        if (getControlButtonFocus()) {
                            this.btn_info.requestFocus();
                            return true;
                        }
                        break;
                    case 21:
                        if (this.ly_control.getVisibility() == 8) {
                            this.image_rewind.setVisibility(0);
                            seekToRewind();
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
            case R.id.btn_audio /* 2131427463 */:
                showDefaultAudioTrackDlgFragment();
                break;
            case R.id.btn_back /* 2131427464 */:
                showExitDlgFragment();
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
                    if (this.playerView.getResizeMode() == 3) {
                        this.playerView.setResizeMode(0);
                        this.txt_resolution.setText(this.wordModels.getFit_screen());
                    } else {
                        this.playerView.setResizeMode(3);
                        this.txt_resolution.setText(this.wordModels.getFill_screen());
                    }
                    this.txt_resolution.setVisibility(0);
                    this.handler.removeCallbacks(this.hideResolutionTicker);
                    resolutionTimer();
                    break;
                }
                break;
            case R.id.btn_rewind /* 2131427486 */:
                this.handler.removeCallbacks(this.hideInfoTicker);
                listTimer();
                seekToRewind();
                break;
            case R.id.btn_sub /* 2131427489 */:
                getSubtitleTrackFromVideo();
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_player);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.description = getIntent().getStringExtra("description");
        this.movie_name = getIntent().getStringExtra("name");
        this.tmdb_id = getIntent().getStringExtra("tmdb_id");
        this.stream_id = getIntent().getStringExtra("stream_id");
        if (this.preferenceHelper.getSharedPreferenceISM3U() || this.stream_id.isEmpty()) {
            this.currentMovie = RealmController.with().getMovieByName(this.movie_name);
        } else {
            this.currentMovie = RealmController.with().getMovieById(this.stream_id);
        }
        String str = this.tmdb_id;
        if (str == null || str.isEmpty()) {
            this.tmdb_id = this.currentMovie.getTmdb_id();
        }
        this.txt_name.setText(this.movie_name);
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

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        if (z) {
            this.handler.removeCallbacks(this.hideInfoTicker);
            listTimer();
        }
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
        if (this.player == null || !z) {
            return;
        }
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

    public void updateProgressBar() {
        this.handler.postDelayed(this.mUpdateTimeTask, 100L);
    }
}
