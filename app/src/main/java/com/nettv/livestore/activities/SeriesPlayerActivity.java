package com.nettv.livestore.activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.TransitionManager;
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
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.nettv.livestore.adapter.EpisodeHorizontalRecyclerAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.dlgfragment.AudioTrackDlgFragment;
import com.nettv.livestore.dlgfragment.ExitDlgFragment;
import com.nettv.livestore.dlgfragment.PlayErrorDlgFragment;
import com.nettv.livestore.dlgfragment.SubtitleTrackDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.ResumeModel;
import com.nettv.livestore.models.SubtitleLinkModel;
import com.nettv.livestore.models.SubtitleModel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.GetSubtitleDataRequest;
import com.nettv.livestore.remote.GetSubtitleLinkRequest;
import com.nettv.livestore.utils.DemoUtil;
import com.nettv.livestore.utils.Security;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.SubtitleView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class SeriesPlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, SeekBar.OnSeekBarChangeListener, GetSubtitleDataRequest.OnGetResponseListener, GetSubtitleLinkRequest.OnGetLinkModelListener {
    public EpisodeHorizontalRecyclerAdapter adapter;
    public ImageButton btn_audio;
    public ImageButton btn_back;
    public ImageButton btn_forward;
    public ImageButton btn_next;
    public ImageButton btn_play;
    public ImageButton btn_previous;
    public ImageButton btn_resolution;
    public ImageButton btn_rewind;
    public ImageButton btn_sub;
    public ImageButton btn_title_setting;
    public DataSource.Factory dataSourceFactory;
    public List<EpisodeModel> episodeModels;
    public int episode_position;
    public PlayErrorDlgFragment errorDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public SeriesPlayerActivity$$ExternalSyntheticLambda1 hideInfoTicker;
    public SeriesPlayerActivity$$ExternalSyntheticLambda1 hideResolutionTicker;
    public ImageView image_forward;
    public ImageView image_rewind;
    public ConstraintLayout ly_control;
    public int maxTime;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public RecyclerView recyclerEpisodes;
    public int resolutionTime;
    public ExitDlgFragment resumeDlgFragment;
    public String resume_key;
    public int season_pos;
    public SeekBar seekBar;
    public SubtitleView subtitleView;
    public TrackSelectionParameters trackSelectionParameters;
    public DefaultTrackSelector trackSelector;
    public TextView txt_end_time;
    public TextView txt_name;
    public TextView txt_resolution;
    public TextView txt_season;
    public TextView txt_start_time;
    public WordModels wordModels = new WordModels();
    public String name = "";
    public String contentUrl = "";
    public String series_name = "";
    public String season_name = "";
    public String tmdb_id = "";
    public long last_position = 0;
    public int error_count = 0;
    public int duration = 0;
    public int episode_focused_position = 0;
    public int subtitle_position = -1;
    public int audio_position = 0;
    public Handler handler = new Handler();
    private final Runnable mUpdateTimeTask = new Runnable() { // from class: com.nettv.livestore.activities.SeriesPlayerActivity.4
        @Override // java.lang.Runnable
        public void run() {
            try {
                ExoPlayer exoPlayer = SeriesPlayerActivity.this.player;
                if (exoPlayer != null) {
                    long duration = exoPlayer.getDuration();
                    long currentPosition = SeriesPlayerActivity.this.player.getCurrentPosition();
                    SeriesPlayerActivity.this.txt_start_time.setText(Utils.milliSecondsToTimer(currentPosition));
                    SeriesPlayerActivity.this.txt_end_time.setText(Utils.milliSecondsToTimer(duration));
                    int progressPercentage = Utils.getProgressPercentage(currentPosition, duration);
                    SeriesPlayerActivity.this.seekBar.setProgress(progressPercentage);
                    if (progressPercentage > 98) {
                        SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                        seriesPlayerActivity.handler.removeCallbacks(seriesPlayerActivity.mUpdateTimeTask);
                        return;
                    }
                }
            } catch (Exception unused) {
                SeriesPlayerActivity.this.seekBar.setProgress(0);
            }
            SeriesPlayerActivity.this.handler.postDelayed(this, 1000L);
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
                SeriesPlayerActivity.this.releasePlayer();
                SeriesPlayerActivity.this.playNextEpisode();
                return;
            }
            if (i == 3) {
                SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                seriesPlayerActivity.error_count = 0;
                seriesPlayerActivity.image_rewind.setVisibility(8);
                SeriesPlayerActivity.this.image_forward.setVisibility(8);
                return;
            }
            if (i == 2 && SeriesPlayerActivity.this.ly_control.getVisibility() == 0) {
                SeriesPlayerActivity seriesPlayerActivity2 = SeriesPlayerActivity.this;
                seriesPlayerActivity2.handler.removeCallbacks(seriesPlayerActivity2.hideInfoTicker);
                SeriesPlayerActivity.this.listTimer();
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlayerError(@NonNull PlaybackException playbackException) {
            if (playbackException.errorCode == 1002) {
                SeriesPlayerActivity.this.image_forward.setVisibility(8);
                SeriesPlayerActivity.this.image_rewind.setVisibility(8);
                SeriesPlayerActivity.this.releasePlayer();
                SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                seriesPlayerActivity.last_position = seriesPlayerActivity.getLastPosition(seriesPlayerActivity.resume_key);
                SeriesPlayerActivity seriesPlayerActivity2 = SeriesPlayerActivity.this;
                seriesPlayerActivity2.playVideo(seriesPlayerActivity2.contentUrl, seriesPlayerActivity2.last_position);
                return;
            }
            SeriesPlayerActivity seriesPlayerActivity3 = SeriesPlayerActivity.this;
            int i = seriesPlayerActivity3.error_count;
            if (i > 3) {
                seriesPlayerActivity3.releasePlayer();
                SeriesPlayerActivity.this.showPlayErrorDlgFragment();
            } else {
                seriesPlayerActivity3.error_count = i + 1;
                seriesPlayerActivity3.releasePlayer();
                SeriesPlayerActivity seriesPlayerActivity4 = SeriesPlayerActivity.this;
                seriesPlayerActivity4.playVideo(seriesPlayerActivity4.contentUrl, 0L);
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

    private void downloadSubtitleFromApi(int i) {
        if (this.preferenceHelper.getSharedPreferenceSubtitleUserModel() != null) {
            String token = this.preferenceHelper.getSharedPreferenceSubtitleUserModel().getToken();
            GetSubtitleLinkRequest getSubtitleLinkRequest = new GetSubtitleLinkRequest(this, 1000);
            getSubtitleLinkRequest.getResponse(Security.getFileData(i), Constants.SUBTITLE_DOWNLOAD, Constants.API_KEY.trim(), token);
            getSubtitleLinkRequest.setOnGetLinkModelListener(this);
        }
    }

    private boolean getControlButtonFocus() {
        return this.btn_previous.hasFocus() || this.btn_rewind.hasFocus() || this.btn_play.hasFocus() || this.btn_forward.hasFocus() || this.btn_next.hasFocus();
    }

    private boolean getFeatureButtonsFocus() {
        return this.btn_audio.hasFocus() || this.btn_sub.hasFocus() || this.btn_resolution.hasFocus() || this.btn_title_setting.hasFocus();
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

    private void getOpenSubtitleFromApi() {
        GetSubtitleDataRequest getSubtitleDataRequest = new GetSubtitleDataRequest(this, 1000);
        getSubtitleDataRequest.getResponse(Constants.EPISODE_SUBTITLE_SEARCH + this.tmdb_id + "&season_number=" + this.episodeModels.get(this.episode_position).getSeason() + "&episode_number=" + this.episodeModels.get(this.episode_position).getEpisode_num(), Constants.API_KEY);
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
                arrayList.add(Utils.getLanguageNameFromCode(trackGroup.getFormat(i2).language));
            }
        }
        arrayList.add("None");
        if (arrayList.size() > 1) {
            showSubTitleTrackDlgFragment(trackGroups, arrayList);
            return;
        }
        String str2 = this.tmdb_id;
        if (str2 == null || str2.isEmpty()) {
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
        this.playerView.getSubtitleView().setStyle(new CaptionStyleCompat(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()), Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()), 0, 0, 0, null));
        this.playerView.getSubtitleView().setFixedTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.subtitleView.setTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.subtitleView.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()));
        this.subtitleView.setTextColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()));
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_previous = (ImageButton) findViewById(R.id.btn_previous);
        this.btn_rewind = (ImageButton) findViewById(R.id.btn_rewind);
        this.btn_play = (ImageButton) findViewById(R.id.btn_play);
        this.btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        this.btn_next = (ImageButton) findViewById(R.id.btn_next);
        this.btn_sub = (ImageButton) findViewById(R.id.btn_sub);
        this.btn_audio = (ImageButton) findViewById(R.id.btn_audio);
        this.btn_resolution = (ImageButton) findViewById(R.id.btn_resolution);
        this.btn_title_setting = (ImageButton) findViewById(R.id.btn_title_setting);
        this.txt_resolution = (TextView) findViewById(R.id.txt_resolution);
        this.txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        this.txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.txt_season = (TextView) findViewById(R.id.txt_season);
        this.image_rewind = (ImageView) findViewById(R.id.image_rewind);
        this.image_forward = (ImageView) findViewById(R.id.image_forward);
        this.recyclerEpisodes = (RecyclerView) findViewById(R.id.recycler_episodes);
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
        this.btn_title_setting.setOnClickListener(this);
        this.btn_resolution.setOnClickListener(this);
        this.btn_back.setOnFocusChangeListener(this);
        this.btn_rewind.setOnFocusChangeListener(this);
        this.btn_play.setOnFocusChangeListener(this);
        this.btn_forward.setOnFocusChangeListener(this);
        this.btn_sub.setOnFocusChangeListener(this);
        this.btn_audio.setOnFocusChangeListener(this);
        this.btn_resolution.setOnFocusChangeListener(this);
        this.btn_title_setting.setOnFocusChangeListener(this);
        this.btn_next.setOnFocusChangeListener(this);
        this.btn_previous.setOnFocusChangeListener(this);
        this.btn_next.setOnClickListener(this);
        this.btn_previous.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$listTimer$1() {
        if (this.maxTime >= 1) {
            runNextTicker();
        } else {
            showEpisodes(false);
            this.ly_control.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(EpisodeModel episodeModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            this.episode_focused_position = num.intValue();
            this.handler.removeCallbacks(this.hideInfoTicker);
            listTimer();
            return null;
        }
        if (this.episode_position == num.intValue()) {
            return null;
        }
        releasePlayer();
        int iIntValue = num.intValue();
        this.episode_position = iIntValue;
        playEpisode(iIntValue);
        showEpisodes(false);
        this.ly_control.setVisibility(8);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resolutionTimer$2() {
        if (this.resolutionTime < 1) {
            this.txt_resolution.setVisibility(8);
        } else {
            runNextResolutionTicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAudioTrackDlgFragment$3(List list, TrackGroupArray trackGroupArray, int i, int i2) {
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
    public /* synthetic */ void lambda$showOpenSubtitleDlgFragment$5(List list, List list2, int i) {
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
    public /* synthetic */ void lambda$showSubTitleTrackDlgFragment$4(List list, TrackGroupArray trackGroupArray, int i, int i2) {
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
        SeriesPlayerActivity$$ExternalSyntheticLambda1 seriesPlayerActivity$$ExternalSyntheticLambda1 = new SeriesPlayerActivity$$ExternalSyntheticLambda1(this, 0);
        this.hideInfoTicker = seriesPlayerActivity$$ExternalSyntheticLambda1;
        seriesPlayerActivity$$ExternalSyntheticLambda1.run();
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
            showEpisodes(false);
            showResumeDlgFragment();
        } else {
            this.ly_control.setVisibility(0);
            this.recyclerEpisodes.setFocusable(false);
            this.btn_play.requestFocus();
            playVideo(this.contentUrl, 0L);
            this.handler.removeCallbacks(this.hideInfoTicker);
            listTimer();
        }
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

    private void resolutionTimer() {
        this.resolutionTime = 2;
        SeriesPlayerActivity$$ExternalSyntheticLambda1 seriesPlayerActivity$$ExternalSyntheticLambda1 = new SeriesPlayerActivity$$ExternalSyntheticLambda1(this, 1);
        this.hideResolutionTicker = seriesPlayerActivity$$ExternalSyntheticLambda1;
        seriesPlayerActivity$$ExternalSyntheticLambda1.run();
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
            AudioTrackDlgFragment.newInstance(this, arrayList, this.audio_position, new SeriesPlayerActivity$$ExternalSyntheticLambda0(this, arrayList, trackGroups, 1, 1)).show(supportFragmentManager, "fragment_audio");
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

    private void showEpisodes(boolean z) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.ly_control);
        if (z) {
            constraintSet.setGuidelinePercent(R.id.horizontal_line1, 0.75f);
            constraintSet.setGuidelinePercent(R.id.horizontal_line2, 1.0f);
        } else {
            constraintSet.setGuidelinePercent(R.id.horizontal_line1, 0.9f);
            constraintSet.setGuidelinePercent(R.id.horizontal_line2, 1.15f);
        }
        TransitionManager.beginDelayedTransition(this.ly_control);
        constraintSet.applyTo(this.ly_control);
        if (!z) {
            this.recyclerEpisodes.setFocusable(false);
            this.btn_play.requestFocus();
        } else {
            this.recyclerEpisodes.setFocusable(true);
            this.adapter.setSelectedPosition(this.episode_position);
            this.recyclerEpisodes.requestFocus();
            this.recyclerEpisodes.scrollToPosition(this.episode_position);
        }
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.SeriesPlayerActivity.2
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                seriesPlayerActivity.handler.removeCallbacks(seriesPlayerActivity.hideInfoTicker);
                SeriesPlayerActivity.this.ly_control.setVisibility(0);
                SeriesPlayerActivity.this.listTimer();
                SeriesPlayerActivity.this.btn_play.requestFocus();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                SeriesPlayerActivity.this.exitDlgFragment.dismiss();
                SeriesPlayerActivity.this.releasePlayer();
                SeriesPlayerActivity.this.finish();
            }
        });
        this.exitDlgFragment.show(supportFragmentManager, "fragment_exit");
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
            SubtitleTrackDlgFragment.newInstance(this, arrayList, this.subtitle_position, new MoviePlayerActivity$$ExternalSyntheticLambda0(this, arrayList, list, 1)).show(supportFragmentManager, "fragment_subtitle");
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
        PlayErrorDlgFragment playErrorDlgFragmentNewInstance = PlayErrorDlgFragment.newInstance(this.wordModels.getPlay_back_error(), this.wordModels.getPlay_back_error_description(), false);
        this.errorDlgFragment = playErrorDlgFragmentNewInstance;
        playErrorDlgFragmentNewInstance.setOkButtonClickListener(new PlayErrorDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.SeriesPlayerActivity.3
            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                if (seriesPlayerActivity.episode_position < seriesPlayerActivity.episodeModels.size() - 1) {
                    SeriesPlayerActivity seriesPlayerActivity2 = SeriesPlayerActivity.this;
                    seriesPlayerActivity2.episode_position++;
                    seriesPlayerActivity2.releasePlayer();
                    SeriesPlayerActivity seriesPlayerActivity3 = SeriesPlayerActivity.this;
                    seriesPlayerActivity3.playEpisode(seriesPlayerActivity3.episode_position);
                }
            }

            @Override // com.nettv.livestore.dlgfragment.PlayErrorDlgFragment.OkButtonClickListener
            public void onOkClick() {
                SeriesPlayerActivity.this.finish();
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
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.nettv.livestore.activities.SeriesPlayerActivity.1
            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
                SeriesPlayerActivity.this.ly_control.setVisibility(0);
                SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                seriesPlayerActivity.playVideo(seriesPlayerActivity.contentUrl, 0L);
                SeriesPlayerActivity seriesPlayerActivity2 = SeriesPlayerActivity.this;
                seriesPlayerActivity2.handler.removeCallbacks(seriesPlayerActivity2.hideInfoTicker);
                SeriesPlayerActivity.this.listTimer();
                SeriesPlayerActivity.this.recyclerEpisodes.setFocusable(false);
                SeriesPlayerActivity.this.btn_play.requestFocus();
            }

            @Override // com.nettv.livestore.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                SeriesPlayerActivity.this.resumeDlgFragment.dismiss();
                SeriesPlayerActivity.this.ly_control.setVisibility(0);
                SeriesPlayerActivity.this.recyclerEpisodes.setFocusable(false);
                SeriesPlayerActivity.this.btn_play.requestFocus();
                SeriesPlayerActivity seriesPlayerActivity = SeriesPlayerActivity.this;
                seriesPlayerActivity.playVideo(seriesPlayerActivity.contentUrl, seriesPlayerActivity.last_position);
                SeriesPlayerActivity seriesPlayerActivity2 = SeriesPlayerActivity.this;
                seriesPlayerActivity2.handler.removeCallbacks(seriesPlayerActivity2.hideInfoTicker);
                SeriesPlayerActivity.this.listTimer();
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
            SubtitleTrackDlgFragment.newInstance(this, list, this.subtitle_position, new SeriesPlayerActivity$$ExternalSyntheticLambda0(this, list, trackGroupArray, 3, 0)).show(supportFragmentManager, "fragment_subtitle");
        }
    }

    @Override // com.nettv.livestore.remote.GetSubtitleLinkRequest.OnGetLinkModelListener
    public void OnGetLinkModelResult(JSONObject jSONObject, int i) {
        if (jSONObject == null) {
            Toast.makeText(getApplicationContext(), this.wordModels.getNo_subtitle(), 1).show();
            return;
        }
        SubtitleLinkModel subtitleLinkModel = (SubtitleLinkModel) new Gson().fromJson(jSONObject.toString(), SubtitleLinkModel.class);
        if (this.player != null) {
            this.subtitleView.setVisibility(0);
            this.subtitleView.setPlayer(this.player);
            this.subtitleView.setSubSource(subtitleLinkModel.getLink());
        }
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

    /* JADX WARN: Code duplicated, block: B:61:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:75:0x011b  */
    /* JADX WARN: Code duplicated, block: B:76:0x0124  */
    /* JADX WARN: Code duplicated, block: B:78:0x012c  */
    /* JADX WARN: Code duplicated, block: B:79:0x0135  */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                if (this.ly_control.getVisibility() != 0) {
                    showExitDlgFragment();
                    return true;
                }
                this.ly_control.setVisibility(8);
                showEpisodes(false);
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
                if (this.ly_control.getVisibility() != 8) {
                    seekToRewind();
                    this.image_rewind.setVisibility(0);
                } else if ((this.recyclerEpisodes.hasFocus() && this.episode_focused_position == 0) || this.btn_previous.hasFocus()) {
                    return true;
                }
            } else if (keyCode != 90) {
                switch (keyCode) {
                    case 19:
                        if (this.ly_control.getVisibility() == 0 && this.seekBar.hasFocus()) {
                            this.btn_back.requestFocus();
                            return true;
                        }
                        if (this.ly_control.getVisibility() == 0 && getControlButtonFocus()) {
                            this.seekBar.requestFocus();
                            return true;
                        }
                        if (this.ly_control.getVisibility() == 0 && getFeatureButtonsFocus()) {
                            this.btn_play.requestFocus();
                            return true;
                        }
                        if (this.ly_control.getVisibility() == 0 && this.recyclerEpisodes.hasFocus()) {
                            showEpisodes(false);
                            this.btn_sub.requestFocus();
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
                            this.btn_sub.requestFocus();
                            return true;
                        }
                        if (getFeatureButtonsFocus()) {
                            showEpisodes(true);
                        }
                        break;
                    case 21:
                        if (this.ly_control.getVisibility() != 8) {
                            if (this.recyclerEpisodes.hasFocus()) {
                                return true;
                            }
                            return true;
                        }
                        seekToRewind();
                        this.image_rewind.setVisibility(0);
                        break;
                        break;
                    case 22:
                        if (this.ly_control.getVisibility() != 0 && this.btn_next.hasFocus()) {
                            return true;
                        }
                        if (this.ly_control.getVisibility() != 0 && this.recyclerEpisodes.hasFocus() && this.episode_focused_position == this.episodeModels.size() - 1) {
                            return true;
                        }
                        if (this.ly_control.getVisibility() == 8) {
                            seekToForward();
                            this.image_forward.setVisibility(0);
                        }
                        break;
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
            } else {
                if (this.ly_control.getVisibility() != 0) {
                }
                if (this.ly_control.getVisibility() != 0) {
                }
                if (this.ly_control.getVisibility() == 8) {
                    seekToForward();
                    this.image_forward.setVisibility(0);
                }
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
            case R.id.btn_next /* 2131427477 */:
                playNextEpisode();
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
                playPreviousEpisode();
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
        setContentView(R.layout.activity_series_player);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.episode_position = getIntent().getIntExtra("position", 0);
        this.season_pos = getIntent().getIntExtra("season_pos", 0);
        this.series_name = getIntent().getStringExtra("series_name");
        this.season_name = getIntent().getStringExtra("season_name");
        this.tmdb_id = getIntent().getStringExtra("tmdb_id");
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            this.episodeModels = RealmController.with().getEpisodesBySeason(this.series_name, this.season_name);
        } else {
            this.episodeModels = this.preferenceHelper.getSharedPreferenceEpisodeModels();
        }
        this.txt_season.setText(this.season_name);
        EpisodeHorizontalRecyclerAdapter episodeHorizontalRecyclerAdapter = new EpisodeHorizontalRecyclerAdapter(this, new ArrayList(), -1, new LiveActivity$$ExternalSyntheticLambda3(this, 6));
        this.adapter = episodeHorizontalRecyclerAdapter;
        episodeHorizontalRecyclerAdapter.setEpisodes(this.episodeModels, this.season_pos);
        this.recyclerEpisodes.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.recyclerEpisodes.setHasFixedSize(true);
        this.recyclerEpisodes.setAdapter(this.adapter);
        this.dataSourceFactory = DemoUtil.getDataSourceFactory(this);
        this.trackSelectionParameters = new TrackSelectionParameters.Builder(this).setTrackTypeDisabled(3, true ^ this.preferenceHelper.getSharedPreferenceSubtitleEnable()).build();
        playEpisode(this.episode_position);
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
