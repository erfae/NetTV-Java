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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentDialog$$ExternalSyntheticLambda0;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
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
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.CaptionStyleCompat;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.Util$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.video.VideoSize;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.AddGroupActivity;
import com.nettv.livestore.activities.CatchUpActivity;
import com.nettv.livestore.activities.LiveActivity$$ExternalSyntheticLambda3;
import com.nettv.livestore.activities.LiveActivity$$ExternalSyntheticLambda4;
import com.nettv.livestore.activities.MovieActivity;
import com.nettv.livestore.activities.SearchActivity;
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.activities.SeriesActivity;
import com.nettv.livestore.adapter.EpgRecyclerAdapter;
import com.nettv.livestore.adapter.RecyclerLiveCategoryAdapter;
import com.nettv.livestore.adapter.RecyclerLiveChannelAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.dlgfragment.LiveSearchDlgFragment;
import com.nettv.livestore.dlgfragment.LockDlgFragment;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CatchUpEpg;
import com.nettv.livestore.models.CatchUpEpgResponse;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.RetroClass;
import com.nettv.livestore.utils.DemoUtil;
import com.nettv.livestore.utils.Utils;
import io.realm.RealmResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class LiveMobileActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    public AudioManager audioManager;
    public SeekBar bright_seekbar;
    public ImageButton btn_back;
    public Button btn_catch_up;
    public Button btn_fav;
    public ImageButton btn_full_epg;
    public ImageButton btn_full_fav;
    public ImageButton btn_full_search;
    public ImageButton btn_lock;
    public ImageButton btn_next;
    public ImageButton btn_play;
    public ImageButton btn_previous;
    public ImageButton btn_resolution;
    public Button btn_search;
    public RecyclerLiveCategoryAdapter categoryAdapter;
    public List<CategoryModel> categoryModels;
    public RecyclerLiveChannelAdapter channelAdapter;
    public ImageView channel_image;
    public String channel_name;
    public String content_url;
    public DataSource.Factory dataSourceFactory;
    public EpgRecyclerAdapter epgAdapter;
    public RealmResults<EPGChannel> epgChannels;
    public List<CatchUpEpg> epgEventList;
    public Util$$ExternalSyntheticLambda0 epgTicker;
    public int epgTime;
    public EditText et_search;
    public ComponentDialog$$ExternalSyntheticLambda0 hideInfoTicker;
    public int hide_time;
    public ImageView image_def;
    public LockDlgFragment lockDlgFragment;
    public ConstraintLayout ly_control;
    public ConstraintLayout ly_surface;
    public ConstraintLayout main_lay;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public RecyclerView recycler_category;
    public RecyclerView recycler_channel;
    public RecyclerView recycler_epg;
    public LiveSearchDlgFragment searchDlgFragment;
    public SeekBar seekBar;
    public EPGChannel selectedChannel;
    public TrackSelectionParameters trackSelectionParameters;
    public TextView txt_channel_name;
    public TextView txt_current_program;
    public TextView txt_current_time;
    public TextView txt_group;
    public TextView txt_home;
    public TextView txt_live;
    public TextView txt_movie;
    public TextView txt_name;
    public TextView txt_next_program;
    public TextView txt_next_time;
    public TextView txt_resolution;
    public TextView txt_series;
    public View view_click;
    public int volumeLevel;
    public SeekBar volume_seekbar;
    public WordModels wordModels;
    public int category_pos = 0;
    public int channel_pos = 0;
    public int pre_channel_pos = 0;
    public int error_count = 0;
    public String stream_id = "";
    public boolean is_full = false;
    public Handler handler = new Handler();
    public String categoryName = "";
    public boolean is_system_setting = false;
    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new LiveMobileActivity$$ExternalSyntheticLambda0(this));

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
                LiveMobileActivity.this.releaseMediaPlayer();
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                liveMobileActivity.playVideo(liveMobileActivity.content_url);
            } else if (i == 3) {
                LiveMobileActivity.this.error_count = 0;
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlayerError(PlaybackException playbackException) {
            if (playbackException.errorCode == 1002) {
                LiveMobileActivity.this.releaseMediaPlayer();
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                liveMobileActivity.playVideo(liveMobileActivity.content_url);
                return;
            }
            LiveMobileActivity liveMobileActivity2 = LiveMobileActivity.this;
            int i = liveMobileActivity2.error_count;
            if (i > 3) {
                liveMobileActivity2.releaseMediaPlayer();
                LiveMobileActivity.this.image_def.setVisibility(0);
            } else {
                liveMobileActivity2.error_count = i + 1;
                liveMobileActivity2.releaseMediaPlayer();
                LiveMobileActivity liveMobileActivity3 = LiveMobileActivity.this;
                liveMobileActivity3.playVideo(liveMobileActivity3.content_url);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void changeChannelInfo(int i) {
        this.txt_group.setText(this.categoryName);
        this.txt_channel_name.setText(((EPGChannel) this.epgChannels.get(i)).getNum() + " " + ((EPGChannel) this.epgChannels.get(i)).getName());
        String stream_icon = ((EPGChannel) this.epgChannels.get(i)).getStream_icon();
        if (stream_icon == null || stream_icon.isEmpty()) {
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.home_logo)).error(R.drawable.home_logo).into(this.channel_image);
        } else {
            Glide.with((FragmentActivity) this).load(stream_icon).error(R.drawable.home_logo).into(this.channel_image);
        }
    }

    private void controlFav(EPGChannel ePGChannel, int i) {
        if (ePGChannel == null || Constants.xxx_live_categories.contains(this.categoryModels.get(this.category_pos).getId())) {
            return;
        }
        RealmController.with().addToFavChannels(ePGChannel.getName(), !ePGChannel.is_favorite(), new LiveMobileActivity$$ExternalSyntheticLambda1(this, i, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlLock(EPGChannel ePGChannel, int i) {
        if (ePGChannel == null || Constants.xxx_live_categories.contains(this.categoryModels.get(this.category_pos).getId())) {
            return;
        }
        RealmController.with().addToLockChannels(ePGChannel.getName(), !ePGChannel.is_locked(), new LiveMobileActivity$$ExternalSyntheticLambda1(this, i, 1));
    }

    private MediaSource.Factory createMediaSourceFactory() {
        DefaultDrmSessionManagerProvider defaultDrmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
        defaultDrmSessionManagerProvider.setDrmHttpDataSourceFactory(DemoUtil.getHttpDataSourceFactory(this));
        return new DefaultMediaSourceFactory(this).setDataSourceFactory(this.dataSourceFactory).setDrmSessionManagerProvider((DrmSessionManagerProvider) defaultDrmSessionManagerProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void epgTimer(String str) {
        this.epgTime = 1;
        Util$$ExternalSyntheticLambda0 util$$ExternalSyntheticLambda0 = new Util$$ExternalSyntheticLambda0(this, str, 13);
        this.epgTicker = util$$ExternalSyntheticLambda0;
        util$$ExternalSyntheticLambda0.run();
    }

    private int getRealChannelPosition(EPGChannel ePGChannel) {
        ArrayList arrayList = new ArrayList(this.epgChannels);
        for (int i = 0; i < arrayList.size(); i++) {
            if (((EPGChannel) arrayList.get(i)).getName().equalsIgnoreCase(ePGChannel.getName())) {
                return i;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getShortEpg(String str) {
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_short_epg(this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), str).enqueue(new Callback<CatchUpEpgResponse>() { // from class: com.nettv.livestore.activities.mobile.LiveMobileActivity.4
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<CatchUpEpgResponse> call, @NonNull Throwable th) {
                    LiveMobileActivity.this.showEpgInfo(null);
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<CatchUpEpgResponse> call, @NonNull Response<CatchUpEpgResponse> response) {
                    if (response.body() == null || response.body().getEpg_listings() == null || response.body().getEpg_listings().size() <= 0) {
                        LiveMobileActivity.this.showEpgInfo(null);
                        return;
                    }
                    LiveMobileActivity.this.showEpgInfo(response.body().getEpg_listings());
                    LiveMobileActivity.this.epgEventList = response.body().getEpg_listings();
                }
            });
        } catch (Exception unused) {
            showEpgInfo(null);
        }
    }

    private void goToAddGroupActivity() {
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id) && this.selectedChannel != null) {
            saveCategoryAndChannelPosition();
        }
        releaseMediaPlayer();
        this.someActivityResultLauncher.launch(new Intent(this, (Class<?>) AddGroupActivity.class));
    }

    private void goToCatchupActivity() {
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            Toast.makeText(this, this.wordModels.getNo_epg_avaliable(), 0).show();
        } else if (this.selectedChannel != null) {
            releaseMediaPlayer();
            LTVApp.channelName = this.selectedChannel.getName();
            this.someActivityResultLauncher.launch(new Intent(this, (Class<?>) CatchUpActivity.class));
        }
    }

    private void goToSearchActivity() {
        releaseMediaPlayer();
        Intent intent = new Intent(this, (Class<?>) SearchActivity.class);
        intent.putExtra("is_live", true);
        this.someActivityResultLauncher.launch(intent);
    }

    private void goToSeriesActivity() {
        releaseMediaPlayer();
        startActivity(new Intent(this, (Class<?>) SeriesActivity.class));
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id) && this.selectedChannel != null) {
            saveCategoryAndChannelPosition();
        }
        finish();
    }

    private void goToVodActivity() {
        releaseMediaPlayer();
        startActivity(new Intent(this, (Class<?>) MovieActivity.class));
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id) && this.selectedChannel != null) {
            saveCategoryAndChannelPosition();
        }
        finish();
    }

    private void initView() {
        this.main_lay = (ConstraintLayout) findViewById(R.id.fullContainer);
        StyledPlayerView styledPlayerView = (StyledPlayerView) findViewById(R.id.player_view);
        this.playerView = styledPlayerView;
        styledPlayerView.setResizeMode(3);
        this.playerView.getSubtitleView().setApplyEmbeddedStyles(false);
        CaptionStyleCompat captionStyleCompat = new CaptionStyleCompat(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()), Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()), 0, 0, 0, null);
        this.playerView.getSubtitleView().setFixedTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.playerView.getSubtitleView().setStyle(captionStyleCompat);
        this.txt_home = (TextView) findViewById(R.id.txt_home);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.recycler_category = (RecyclerView) findViewById(R.id.recycler_category);
        this.recycler_channel = (RecyclerView) findViewById(R.id.recycler_channel);
        this.ly_surface = (ConstraintLayout) findViewById(R.id.ly_surface);
        this.recycler_epg = (RecyclerView) findViewById(R.id.recycler_epg);
        this.btn_fav = (Button) findViewById(R.id.btn_fav);
        this.btn_catch_up = (Button) findViewById(R.id.btn_catch_up);
        this.btn_search = (Button) findViewById(R.id.btn_search);
        this.btn_lock = (ImageButton) findViewById(R.id.btn_lock);
        this.txt_home.setText(this.wordModels.getHome());
        this.txt_live.setText(this.wordModels.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.btn_catch_up.setText(this.wordModels.getCatch_up());
        this.btn_search.setText(this.wordModels.getSearch());
        this.btn_fav.setText(this.wordModels.getAdd_to_favorite());
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.seekBar = seekBar;
        seekBar.setMax(100);
        this.txt_current_time = (TextView) findViewById(R.id.txt_current_time);
        this.txt_current_program = (TextView) findViewById(R.id.txt_current_program);
        this.txt_next_time = (TextView) findViewById(R.id.txt_next_time);
        this.txt_next_program = (TextView) findViewById(R.id.txt_next_program);
        this.txt_group = (TextView) findViewById(R.id.txt_group);
        this.txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);
        this.channel_image = (ImageView) findViewById(R.id.channel_image);
        this.image_def = (ImageView) findViewById(R.id.image_def);
        this.btn_full_epg = (ImageButton) findViewById(R.id.btn_full_catch);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_full_fav = (ImageButton) findViewById(R.id.btn_full_fav);
        this.btn_full_search = (ImageButton) findViewById(R.id.btn_full_search);
        this.btn_resolution = (ImageButton) findViewById(R.id.btn_resolution);
        this.txt_resolution = (TextView) findViewById(R.id.txt_resolution);
        this.view_click = findViewById(R.id.view_click);
        this.btn_next = (ImageButton) findViewById(R.id.btn_next);
        this.btn_play = (ImageButton) findViewById(R.id.btn_play);
        this.btn_previous = (ImageButton) findViewById(R.id.btn_previous);
        this.volume_seekbar = (SeekBar) findViewById(R.id.volume_seekbar);
        this.bright_seekbar = (SeekBar) findViewById(R.id.bright_seekbar);
        this.volume_seekbar.setOnSeekBarChangeListener(this);
        this.audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.volume_seekbar.setMax(100);
        this.volumeLevel = this.audioManager.getStreamVolume(3);
        this.volume_seekbar.setProgress((int) ((this.volumeLevel / this.audioManager.getStreamMaxVolume(3)) * 100.0f));
        this.bright_seekbar.setOnSeekBarChangeListener(this);
        this.bright_seekbar.setMax(255);
        this.bright_seekbar.setProgress(Settings.System.getInt(getContentResolver(), "screen_brightness", 0));
        this.ly_surface.setOnClickListener(this);
        this.txt_live.setOnClickListener(this);
        this.txt_home.setOnClickListener(this);
        this.txt_series.setOnClickListener(this);
        this.txt_movie.setOnClickListener(this);
        this.btn_fav.setOnClickListener(this);
        this.btn_catch_up.setOnClickListener(this);
        this.btn_search.setOnClickListener(this);
        this.btn_lock.setOnClickListener(this);
        this.btn_full_epg.setOnClickListener(this);
        this.btn_full_search.setOnClickListener(this);
        this.btn_full_fav.setOnClickListener(this);
        this.btn_resolution.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.btn_next.setOnClickListener(this);
        this.btn_play.setOnClickListener(this);
        this.btn_previous.setOnClickListener(this);
        this.view_click.setOnClickListener(this);
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.activities.mobile.LiveMobileActivity.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                LiveMobileActivity.this.searchChannelsInCategory(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.playerView.getVideoSurfaceView().setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 6));
    }

    private boolean isAdultChannel(String str, String str2) {
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            return str2.contains("adult") || str2.contains("xxx") || str2.contains("porn");
        }
        return Constants.xxx_live_categories.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlFav$5(int i) {
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.fav_id)) {
            RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
            this.epgChannels = liveChannelsByCategory;
            this.channelAdapter.updateData(liveChannelsByCategory, -1);
        } else {
            this.channelAdapter.notifyItemChanged(i);
        }
        this.preferenceHelper.setSharedPreferenceLiveFavChannels(RealmController.with().getFavChannelNames());
        this.categoryAdapter.notifyItemChanged(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlLock$6(int i) {
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.lock_id)) {
            RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
            this.epgChannels = liveChannelsByCategory;
            this.channelAdapter.updateData(liveChannelsByCategory, 0);
        } else {
            this.channelAdapter.notifyItemChanged(i);
        }
        this.preferenceHelper.setSharedPreferenceLiveLockChannels(RealmController.with().getLockChannelNames());
        this.categoryAdapter.notifyItemChanged(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$epgTimer$4(String str) {
        if (this.epgTime == 0) {
            getShortEpg(str);
        } else {
            runNextEpgTicker();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9(View view) {
        if (!this.is_full) {
            this.is_full = true;
            setFull();
        } else if (this.ly_control.getVisibility() == 8) {
            this.ly_control.setVisibility(0);
            this.handler.removeCallbacks(this.hideInfoTicker);
            mInfoHideTimer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mInfoHideTimer$7() {
        if (this.hide_time == 0 && this.ly_control.getVisibility() == 0) {
            this.ly_control.setVisibility(8);
        }
        moveNexHideTicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(ActivityResult activityResult) {
        Intent data;
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null) {
            return;
        }
        if (data.getStringExtra("is_changed").equalsIgnoreCase("from_search")) {
            this.is_full = true;
            setFull();
            this.category_pos = this.preferenceHelper.getSharedPreferenceCategoryPos();
            this.channel_pos = this.preferenceHelper.getSharedPreferenceChannelPos();
            RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
            this.epgChannels = liveChannelsByCategory;
            if (this.channel_pos > liveChannelsByCategory.size() - 1) {
                this.channel_pos = 0;
            }
            this.channelAdapter.updateData(this.epgChannels, this.channel_pos);
            this.selectedChannel = (EPGChannel) this.epgChannels.get(this.channel_pos);
            this.recycler_category.scrollToPosition(this.category_pos);
            this.categoryAdapter.setCategoryPosition(this.category_pos);
            if (this.preferenceHelper.getSharedPreferenceISM3U()) {
                showEpgInfo(null);
            } else {
                this.handler.removeCallbacks(this.epgTicker);
                epgTimer(this.selectedChannel.getStream_id());
            }
            String name = this.selectedChannel.getName();
            this.channel_name = name;
            this.txt_name.setText(name);
            showFavImageIcon(this.selectedChannel.is_favorite());
            changeChannelInfo(this.channel_pos);
        } else if (data.getStringExtra("is_changed").equalsIgnoreCase(Constants.add_group)) {
            List<CategoryModel> sharedLiveCategoryModels = this.preferenceHelper.getSharedLiveCategoryModels();
            this.categoryModels = sharedLiveCategoryModels;
            this.categoryAdapter.setCategoryModels(sharedLiveCategoryModels);
            this.recycler_category.requestFocus();
        }
        playSelectedChannel(this.selectedChannel);
        this.recycler_channel.requestFocus();
        this.recycler_channel.scrollToPosition(this.channel_pos);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(CategoryModel categoryModel, Integer num, Boolean bool) {
        num.intValue();
        if (!bool.booleanValue()) {
            return null;
        }
        if (num.intValue() == 0) {
            goToAddGroupActivity();
            return null;
        }
        if (this.category_pos == num.intValue()) {
            return null;
        }
        this.et_search.setText("");
        if (Constants.xxx_live_categories.contains(categoryModel.getId()) || categoryModel.getId().equalsIgnoreCase(Constants.lock_id)) {
            if (!categoryModel.getId().equalsIgnoreCase(Constants.lock_id)) {
                showLockDlgFragment(num.intValue());
                return null;
            }
            if (this.preferenceHelper.getSharedPreferenceLiveLockChannels().size() > 0) {
                showLockDlgFragment(num.intValue());
                return null;
            }
            this.category_pos = num.intValue();
            Toast.makeText(this, this.wordModels.getStr_no_lock_channels(), 0).show();
            return null;
        }
        this.category_pos = num.intValue();
        this.categoryName = (this.category_pos + 1) + " • Group : " + this.categoryModels.get(this.category_pos).getName();
        int i = this.category_pos;
        if (i <= 4 || i >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
            RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(categoryModel, "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
            this.epgChannels = liveChannelsByCategory;
            this.channelAdapter.updateData(liveChannelsByCategory, -1);
            this.recycler_channel.scrollToPosition(0);
            return null;
        }
        if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() <= 0) {
            return null;
        }
        RealmResults<EPGChannel> epgChannelsByNameArray = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(this.category_pos - 5)), "");
        this.epgChannels = epgChannelsByNameArray;
        this.channelAdapter.updateData(epgChannelsByNameArray, -1);
        this.recycler_channel.scrollToPosition(0);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$1(EPGChannel ePGChannel, Integer num, Boolean bool, Boolean bool2) {
        this.pre_channel_pos = num.intValue();
        if (bool.booleanValue()) {
            if (!this.is_full) {
                if (this.stream_id.equalsIgnoreCase(ePGChannel.getStream_id())) {
                    if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id)) {
                        this.channel_pos = 0;
                    }
                    this.is_full = true;
                    setFull();
                } else if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.all_id) && isAdultChannel(ePGChannel.getCategory_id(), ePGChannel.getCategory_name())) {
                    showChannelLockDlgFragment(ePGChannel, num.intValue(), 0);
                } else if (isAdultChannel(ePGChannel.getCategory_id(), ePGChannel.getCategory_name()) || this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.lock_id) || !ePGChannel.is_locked()) {
                    this.channel_pos = num.intValue();
                    playSelectedChannel(ePGChannel);
                    if (this.preferenceHelper.getSharedPreferenceISM3U()) {
                        showEpgInfo(null);
                    } else {
                        this.handler.removeCallbacks(this.epgTicker);
                        epgTimer(ePGChannel.getStream_id());
                    }
                    String name = ePGChannel.getName();
                    this.channel_name = name;
                    this.txt_name.setText(name);
                    showFavImageIcon(ePGChannel.is_favorite());
                    changeChannelInfo(this.channel_pos);
                } else {
                    showChannelLockDlgFragment(ePGChannel, num.intValue(), 0);
                }
            }
        } else if (bool2.booleanValue()) {
            controlFav(ePGChannel, num.intValue());
            showFavImageIcon(ePGChannel.is_favorite());
        } else if (!this.is_full) {
            if (this.preferenceHelper.getSharedPreferenceISM3U()) {
                showEpgInfo(null);
            } else {
                this.handler.removeCallbacks(this.epgTicker);
                epgTimer(ePGChannel.getStream_id());
            }
            String name2 = ePGChannel.getName();
            this.channel_name = name2;
            this.txt_name.setText(name2);
            showFavImageIcon(ePGChannel.is_favorite());
            changeChannelInfo(this.pre_channel_pos);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playSelectedChannel$3() {
        if (!this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id)) {
            this.categoryAdapter.notifyItemChanged(0);
            return;
        }
        this.channelAdapter.notifyDataSetChanged();
        this.channelAdapter.setSelectedPosition(0);
        this.recycler_channel.scrollToPosition(0);
        this.channel_pos = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearchDlgFragment$2(EPGChannel ePGChannel) {
        releaseMediaPlayer();
        if (!this.et_search.getText().toString().isEmpty()) {
            int i = this.category_pos;
            if (i <= 4 || i >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
                this.epgChannels = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), 0);
            } else if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() > 0) {
                this.epgChannels = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(this.category_pos - 5)), "");
            }
            this.et_search.setText("");
        }
        int realChannelPosition = getRealChannelPosition(ePGChannel);
        this.channel_pos = realChannelPosition;
        this.recycler_channel.scrollToPosition(realChannelPosition);
        playSelectedChannel(ePGChannel);
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            showEpgInfo(null);
        } else {
            getShortEpg(this.stream_id);
        }
        changeChannelInfo(this.channel_pos);
        showFavImageIcon(this.selectedChannel.is_favorite());
        this.ly_control.setVisibility(0);
        this.handler.removeCallbacks(this.hideInfoTicker);
        mInfoHideTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mInfoHideTimer() {
        this.hide_time = 10;
        ComponentDialog$$ExternalSyntheticLambda0 componentDialog$$ExternalSyntheticLambda0 = new ComponentDialog$$ExternalSyntheticLambda0(this, 9);
        this.hideInfoTicker = componentDialog$$ExternalSyntheticLambda0;
        componentDialog$$ExternalSyntheticLambda0.run();
    }

    private void moveNexHideTicker() {
        this.hide_time--;
        this.handler.postAtTime(this.hideInfoTicker, SystemClock.uptimeMillis() + 1000);
    }

    private void playNextChannel() {
        if (this.channel_pos < this.epgChannels.size() - 1) {
            this.channel_pos++;
        } else {
            this.channel_pos = 0;
        }
        if (this.epgChannels.size() <= 0 || this.channel_pos >= this.epgChannels.size()) {
            return;
        }
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.all_id) && isAdultChannel(((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_id(), ((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_name())) {
            showChannelLockDlgFragment((EPGChannel) this.epgChannels.get(this.channel_pos), this.channel_pos, 1);
            return;
        }
        if (!Constants.xxx_live_categories.contains(this.categoryModels.get(this.category_pos).getId()) && !this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.lock_id) && ((EPGChannel) this.epgChannels.get(this.channel_pos)).is_locked()) {
            showChannelLockDlgFragment((EPGChannel) this.epgChannels.get(this.channel_pos), this.channel_pos, 1);
            return;
        }
        playSelectedChannel((EPGChannel) this.epgChannels.get(this.channel_pos));
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            showEpgInfo(null);
        } else {
            this.handler.removeCallbacks(this.epgTicker);
            epgTimer(this.stream_id);
        }
        changeChannelInfo(this.channel_pos);
        if (this.ly_control.getVisibility() == 8) {
            this.ly_control.setVisibility(0);
        }
        this.handler.removeCallbacks(this.hideInfoTicker);
        mInfoHideTimer();
        this.txt_name.setText(this.channel_name);
        this.recycler_channel.scrollToPosition(this.channel_pos);
    }

    private void playPreviousChannel() {
        int i = this.channel_pos;
        if (i > 0) {
            this.channel_pos = i - 1;
        } else {
            this.channel_pos = this.epgChannels.size() - 1;
        }
        if (this.epgChannels.size() <= 0 || this.channel_pos >= this.epgChannels.size()) {
            return;
        }
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.all_id) && isAdultChannel(((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_id(), ((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_name())) {
            showChannelLockDlgFragment((EPGChannel) this.epgChannels.get(this.channel_pos), this.channel_pos, 1);
            return;
        }
        if (!Constants.xxx_live_categories.contains(this.categoryModels.get(this.category_pos).getId()) && !this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.lock_id) && ((EPGChannel) this.epgChannels.get(this.channel_pos)).is_locked()) {
            showChannelLockDlgFragment((EPGChannel) this.epgChannels.get(this.channel_pos), this.channel_pos, 1);
            return;
        }
        playSelectedChannel((EPGChannel) this.epgChannels.get(this.channel_pos));
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            showEpgInfo(null);
        } else {
            this.handler.removeCallbacks(this.epgTicker);
            epgTimer(this.stream_id);
        }
        changeChannelInfo(this.channel_pos);
        this.txt_name.setText(this.channel_name);
        if (this.ly_control.getVisibility() == 8) {
            this.ly_control.setVisibility(0);
        }
        this.handler.removeCallbacks(this.hideInfoTicker);
        mInfoHideTimer();
        this.recycler_channel.scrollToPosition(this.channel_pos);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playSelectedChannel(EPGChannel ePGChannel) {
        if (ePGChannel != null) {
            this.preferenceHelper.setSharedPreferenceCategoryPos(this.category_pos);
            this.preferenceHelper.setSharedPreferenceChannelPos(this.channel_pos);
            this.selectedChannel = ePGChannel;
            this.stream_id = ePGChannel.getStream_id();
            this.channel_name = this.selectedChannel.getName();
            showFavImageIcon(this.selectedChannel.is_favorite());
            showLockImageIcon(ePGChannel.is_locked());
            if (this.preferenceHelper.getSharedPreferenceISM3U()) {
                this.content_url = this.selectedChannel.getUrl();
            } else {
                this.content_url = GetSharedInfo.getLiveChannelUrl(this.preferenceHelper.getSharedPreferenceServerUrl(), this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.stream_id, this.preferenceHelper.getSharedPreferenceLiveStreamFormat());
            }
            if (Constants.xxx_live_categories.contains(this.categoryModels.get(this.category_pos).getId()) || isAdultChannel(this.selectedChannel.getCategory_id(), this.selectedChannel.getCategory_name())) {
                showLockImageIcon(true);
            } else {
                RealmController.with().addToRecentChannels(this.selectedChannel.getName(), true, new LiveMobileActivity$$ExternalSyntheticLambda0(this));
            }
            this.categoryAdapter.notifyItemChanged(1);
            releaseMediaPlayer();
            playVideo(this.content_url);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playVideo(String str) {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        if (this.image_def.getVisibility() == 0) {
            this.image_def.setVisibility(8);
        }
        String adaptiveMimeTypeForContentType = Util.getAdaptiveMimeTypeForContentType(Util.inferContentType(Uri.parse(str), ""));
        MediaItem.Builder builder = new MediaItem.Builder();
        builder.setUri(Uri.parse(str)).setMediaMetadata(new MediaMetadata.Builder().setTitle("title").build()).setMimeType(adaptiveMimeTypeForContentType);
        MediaItem mediaItemBuild = builder.build();
        ExoPlayer.Builder mediaSourceFactory = new ExoPlayer.Builder(this).setMediaSourceFactory(createMediaSourceFactory());
        setRenderersFactory(mediaSourceFactory, true);
        ExoPlayer exoPlayerBuild = mediaSourceFactory.build();
        this.player = exoPlayerBuild;
        exoPlayerBuild.setTrackSelectionParameters(this.trackSelectionParameters);
        this.player.addAnalyticsListener(new AnalyticsListener() { // from class: com.nettv.livestore.activities.mobile.LiveMobileActivity.3
            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
                AnalyticsListener.CC.$default$onAudioAttributesChanged(this, eventTime, audioAttributes);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
                AnalyticsListener.CC.$default$onAudioCodecError(this, eventTime, exc);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j) {
                AnalyticsListener.CC.$default$onAudioDecoderInitialized(this, eventTime, str2, j);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j, long j2) {
                AnalyticsListener.CC.$default$onAudioDecoderInitialized(this, eventTime, str2, j, j2);
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
            public final /* synthetic */ void onAudioPositionAdvancing(AnalyticsListener.EventTime eventTime, long j) {
                AnalyticsListener.CC.$default$onAudioPositionAdvancing(this, eventTime, j);
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
            public final /* synthetic */ void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
                AnalyticsListener.CC.$default$onAudioUnderrun(this, eventTime, i, j, j2);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onAvailableCommandsChanged(AnalyticsListener.EventTime eventTime, Player.Commands commands) {
                AnalyticsListener.CC.$default$onAvailableCommandsChanged(this, eventTime, commands);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
                AnalyticsListener.CC.$default$onBandwidthEstimate(this, eventTime, i, j, j2);
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
            public final /* synthetic */ void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int i, String str2, long j) {
                AnalyticsListener.CC.$default$onDecoderInitialized(this, eventTime, i, str2, j);
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
            public final /* synthetic */ void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i, long j) {
                AnalyticsListener.CC.$default$onDroppedVideoFrames(this, eventTime, i, j);
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
            public final /* synthetic */ void onMaxSeekToPreviousPositionChanged(AnalyticsListener.EventTime eventTime, long j) {
                AnalyticsListener.CC.$default$onMaxSeekToPreviousPositionChanged(this, eventTime, j);
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
            public final /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j) {
                AnalyticsListener.CC.$default$onRenderedFirstFrame(this, eventTime, obj, j);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i) {
                AnalyticsListener.CC.$default$onRepeatModeChanged(this, eventTime, i);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSeekBackIncrementChanged(AnalyticsListener.EventTime eventTime, long j) {
                AnalyticsListener.CC.$default$onSeekBackIncrementChanged(this, eventTime, j);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onSeekForwardIncrementChanged(AnalyticsListener.EventTime eventTime, long j) {
                AnalyticsListener.CC.$default$onSeekForwardIncrementChanged(this, eventTime, j);
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
            public final /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j) {
                AnalyticsListener.CC.$default$onVideoDecoderInitialized(this, eventTime, str2, j);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str2, long j, long j2) {
                AnalyticsListener.CC.$default$onVideoDecoderInitialized(this, eventTime, str2, j, j2);
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
            public final /* synthetic */ void onVideoFrameProcessingOffset(AnalyticsListener.EventTime eventTime, long j, int i) {
                AnalyticsListener.CC.$default$onVideoFrameProcessingOffset(this, eventTime, j, i);
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
                LiveMobileActivity.this.txt_resolution.setText(videoSize.width + "x" + videoSize.height);
                AnalyticsListener.CC.$default$onVideoSizeChanged(this, eventTime, videoSize);
            }

            @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
            public final /* synthetic */ void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f) {
                AnalyticsListener.CC.$default$onVolumeChanged(this, eventTime, f);
            }
        });
        this.player.addListener(new PlayerEventListener());
        this.player.addAnalyticsListener(new EventLogger());
        this.player.setAudioAttributes(AudioAttributes.DEFAULT, true);
        this.player.setPlayWhenReady(true);
        this.playerView.setPlayer(this.player);
        this.player.setMediaItem(mediaItemBuild);
        this.player.prepare();
        this.player.play();
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

    private void runNextEpgTicker() {
        this.epgTime--;
        this.handler.postAtTime(this.epgTicker, SystemClock.uptimeMillis() + 500);
    }

    private void saveCategoryAndChannelPosition() {
        int i;
        int i2 = 0;
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            String category_name = this.selectedChannel.getCategory_name();
            i = 0;
            while (true) {
                if (i >= this.categoryModels.size()) {
                    i = 0;
                    break;
                } else if (this.categoryModels.get(i).getName().equalsIgnoreCase(category_name)) {
                    break;
                } else {
                    i++;
                }
            }
        } else {
            String category_id = this.selectedChannel.getCategory_id();
            i = 0;
            for (int i3 = 0; i3 < this.categoryModels.size(); i3++) {
                if (this.categoryModels.get(i3).getId().equalsIgnoreCase(category_id)) {
                    i = i3;
                }
            }
        }
        RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(i), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
        for (int i4 = 0; i4 < liveChannelsByCategory.size(); i4++) {
            if (this.selectedChannel.getName().equalsIgnoreCase(((EPGChannel) liveChannelsByCategory.get(i4)).getName())) {
                i2 = i4;
                break;
            }
        }
        this.preferenceHelper.setSharedPreferenceCategoryPos(i);
        this.preferenceHelper.setSharedPreferenceChannelPos(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchChannelsInCategory(String str) {
        int i = this.category_pos;
        if (i <= 4 || i >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
            this.epgChannels = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), str, this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
        } else if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() > 0) {
            this.epgChannels = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(this.category_pos - 5)), str);
        }
        this.channelAdapter.updateData(this.epgChannels, -1);
        this.recycler_channel.scrollToPosition(0);
    }

    private void setCurrentEpgEvent(List<CatchUpEpg> list) {
        if (list == null || list.size() <= 0) {
            this.txt_current_time.setText(this.wordModels.getNo_information());
            this.txt_current_program.setText("");
            this.seekBar.setProgress(0);
            this.txt_next_time.setText("");
            this.txt_next_program.setText(this.wordModels.getNo_information());
            return;
        }
        this.txt_current_program.setText(Utils.decode64String(list.get(0).getTitle()));
        this.txt_current_time.setText(Utils.getDateFromMillisecond(GetSharedInfo.getCurrentTimeFormat(this), Utils.getDateFromString("yyyy-MM-dd HH:mm:ss", list.get(0).getStart()).getTime() + LTVApp.SEVER_OFFSET));
        this.seekBar.setProgress(list.get(0).getProgress());
        if (list.size() > 1) {
            this.txt_next_program.setText(Utils.decode64String(list.get(1).getTitle()));
            this.txt_next_time.setText(Utils.getDateFromMillisecond(GetSharedInfo.getCurrentTimeFormat(this), Utils.getDateFromString("yyyy-MM-dd HH:mm:ss", list.get(1).getStart()).getTime() + LTVApp.SEVER_OFFSET));
        } else {
            this.txt_next_time.setText("");
            this.txt_next_program.setText(this.wordModels.getNo_information());
        }
    }

    private void setFull() {
        if (this.is_full) {
            this.btn_catch_up.setVisibility(8);
            this.btn_fav.setVisibility(8);
            this.btn_search.setVisibility(8);
            this.recycler_channel.setVisibility(8);
            this.recycler_category.setVisibility(8);
            if (this.ly_control.getVisibility() == 8) {
                this.ly_control.setVisibility(0);
            }
            this.handler.removeCallbacks(this.hideInfoTicker);
            mInfoHideTimer();
        } else {
            this.btn_catch_up.setVisibility(0);
            this.btn_fav.setVisibility(0);
            this.btn_search.setVisibility(0);
            this.handler.removeCallbacks(this.hideInfoTicker);
            this.ly_control.setVisibility(8);
            this.recycler_channel.setVisibility(0);
            this.recycler_category.setVisibility(0);
            this.channelAdapter.setSelectedPosition(this.channel_pos);
            this.recycler_channel.requestFocus();
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.main_lay);
        if (this.is_full) {
            constraintSet.setGuidelinePercent(R.id.vertical_line1, -0.23f);
            constraintSet.setGuidelinePercent(R.id.vertical_line2, 0.0f);
            constraintSet.setGuidelinePercent(R.id.horizontal_line1, 0.0f);
            constraintSet.setGuidelinePercent(R.id.horizontal_line2, 1.0f);
        } else {
            constraintSet.setGuidelinePercent(R.id.vertical_line1, 0.23f);
            constraintSet.setGuidelinePercent(R.id.vertical_line2, 0.5f);
            constraintSet.setGuidelinePercent(R.id.horizontal_line1, 0.1f);
            constraintSet.setGuidelinePercent(R.id.horizontal_line2, 0.6f);
        }
        constraintSet.applyTo(this.main_lay);
    }

    private void setRenderersFactory(ExoPlayer.Builder builder, boolean z) {
        builder.setRenderersFactory(DemoUtil.buildRenderersFactory(this, z));
    }

    private void showChannelLockDlgFragment(final EPGChannel ePGChannel, final int i, final int i2) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_channel_lock");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LockDlgFragment lockDlgFragmentNewInstance = LockDlgFragment.newInstance(this.preferenceHelper.getSharedPreferenceParentPassword());
        this.lockDlgFragment = lockDlgFragmentNewInstance;
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.activities.mobile.LiveMobileActivity.2
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                int i3 = i2;
                if (i3 == 0) {
                    LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                    liveMobileActivity.channel_pos = i;
                    liveMobileActivity.playSelectedChannel(ePGChannel);
                    if (LiveMobileActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                        LiveMobileActivity.this.showEpgInfo(null);
                    } else {
                        LiveMobileActivity liveMobileActivity2 = LiveMobileActivity.this;
                        liveMobileActivity2.handler.removeCallbacks(liveMobileActivity2.epgTicker);
                        LiveMobileActivity.this.epgTimer(ePGChannel.getStream_id());
                    }
                    LiveMobileActivity.this.channel_name = ePGChannel.getName();
                    LiveMobileActivity liveMobileActivity3 = LiveMobileActivity.this;
                    liveMobileActivity3.txt_name.setText(liveMobileActivity3.channel_name);
                    LiveMobileActivity.this.showFavImageIcon(ePGChannel.is_favorite());
                    LiveMobileActivity liveMobileActivity4 = LiveMobileActivity.this;
                    liveMobileActivity4.changeChannelInfo(liveMobileActivity4.channel_pos);
                    return;
                }
                if (i3 == 1) {
                    LiveMobileActivity liveMobileActivity5 = LiveMobileActivity.this;
                    liveMobileActivity5.playSelectedChannel((EPGChannel) liveMobileActivity5.epgChannels.get(liveMobileActivity5.channel_pos));
                    if (LiveMobileActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                        LiveMobileActivity.this.showEpgInfo(null);
                    } else {
                        LiveMobileActivity liveMobileActivity6 = LiveMobileActivity.this;
                        liveMobileActivity6.handler.removeCallbacks(liveMobileActivity6.epgTicker);
                        LiveMobileActivity liveMobileActivity7 = LiveMobileActivity.this;
                        liveMobileActivity7.epgTimer(liveMobileActivity7.stream_id);
                    }
                    LiveMobileActivity liveMobileActivity8 = LiveMobileActivity.this;
                    liveMobileActivity8.changeChannelInfo(liveMobileActivity8.channel_pos);
                    if (LiveMobileActivity.this.ly_control.getVisibility() == 8) {
                        LiveMobileActivity.this.ly_control.setVisibility(0);
                    }
                    LiveMobileActivity liveMobileActivity9 = LiveMobileActivity.this;
                    liveMobileActivity9.handler.removeCallbacks(liveMobileActivity9.hideInfoTicker);
                    LiveMobileActivity.this.mInfoHideTimer();
                    LiveMobileActivity liveMobileActivity10 = LiveMobileActivity.this;
                    liveMobileActivity10.txt_name.setText(liveMobileActivity10.channel_name);
                    LiveMobileActivity liveMobileActivity11 = LiveMobileActivity.this;
                    liveMobileActivity11.recycler_channel.scrollToPosition(liveMobileActivity11.channel_pos);
                    return;
                }
                if (i3 == 2) {
                    LiveMobileActivity.this.playSelectedChannel(ePGChannel);
                    LiveMobileActivity liveMobileActivity12 = LiveMobileActivity.this;
                    liveMobileActivity12.recycler_channel.scrollToPosition(liveMobileActivity12.channel_pos);
                    LiveMobileActivity liveMobileActivity13 = LiveMobileActivity.this;
                    liveMobileActivity13.handler.removeCallbacks(liveMobileActivity13.epgTicker);
                    LiveMobileActivity liveMobileActivity14 = LiveMobileActivity.this;
                    liveMobileActivity14.getShortEpg(liveMobileActivity14.stream_id);
                    LiveMobileActivity liveMobileActivity15 = LiveMobileActivity.this;
                    liveMobileActivity15.changeChannelInfo(liveMobileActivity15.channel_pos);
                    if (LiveMobileActivity.this.ly_control.getVisibility() == 8) {
                        LiveMobileActivity liveMobileActivity16 = LiveMobileActivity.this;
                        if (liveMobileActivity16.is_full) {
                            liveMobileActivity16.ly_control.setVisibility(0);
                        }
                    }
                    LiveMobileActivity liveMobileActivity17 = LiveMobileActivity.this;
                    liveMobileActivity17.handler.removeCallbacks(liveMobileActivity17.hideInfoTicker);
                    LiveMobileActivity.this.mInfoHideTimer();
                    return;
                }
                if (i3 != 3) {
                    if (i3 != 4) {
                        return;
                    }
                    LiveMobileActivity.this.controlLock(ePGChannel, i);
                    LiveMobileActivity.this.showLockImageIcon(!ePGChannel.is_locked());
                    if (ePGChannel.is_locked()) {
                        LiveMobileActivity liveMobileActivity18 = LiveMobileActivity.this;
                        Toast.makeText(liveMobileActivity18, liveMobileActivity18.wordModels.getChannel_unlocked(), 0).show();
                        return;
                    } else {
                        LiveMobileActivity liveMobileActivity19 = LiveMobileActivity.this;
                        Toast.makeText(liveMobileActivity19, liveMobileActivity19.wordModels.getChannel_locked(), 0).show();
                        return;
                    }
                }
                LiveMobileActivity liveMobileActivity20 = LiveMobileActivity.this;
                liveMobileActivity20.playSelectedChannel((EPGChannel) liveMobileActivity20.epgChannels.get(liveMobileActivity20.channel_pos));
                LiveMobileActivity liveMobileActivity21 = LiveMobileActivity.this;
                liveMobileActivity21.stream_id = ((EPGChannel) liveMobileActivity21.epgChannels.get(liveMobileActivity21.channel_pos)).getStream_id();
                if (LiveMobileActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                    LiveMobileActivity.this.showEpgInfo(null);
                } else {
                    LiveMobileActivity liveMobileActivity22 = LiveMobileActivity.this;
                    liveMobileActivity22.handler.removeCallbacks(liveMobileActivity22.epgTicker);
                    LiveMobileActivity liveMobileActivity23 = LiveMobileActivity.this;
                    liveMobileActivity23.epgTimer(liveMobileActivity23.stream_id);
                }
                LiveMobileActivity liveMobileActivity24 = LiveMobileActivity.this;
                liveMobileActivity24.txt_name.setText(liveMobileActivity24.channel_name);
                LiveMobileActivity liveMobileActivity25 = LiveMobileActivity.this;
                liveMobileActivity25.changeChannelInfo(liveMobileActivity25.channel_pos);
                LiveMobileActivity.this.recycler_channel.requestFocus();
                LiveMobileActivity liveMobileActivity26 = LiveMobileActivity.this;
                liveMobileActivity26.recycler_channel.scrollToPosition(liveMobileActivity26.channel_pos);
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                Toast.makeText(liveMobileActivity, liveMobileActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                Toast.makeText(liveMobileActivity, liveMobileActivity.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(supportFragmentManager, "fragment_channel_lock");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEpgInfo(List<CatchUpEpg> list) {
        if (list == null || list.size() == 0) {
            this.epgAdapter.setEpgList(new ArrayList());
            setCurrentEpgEvent(new ArrayList());
        } else {
            this.epgAdapter.setEpgList(list);
            setCurrentEpgEvent(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFavImageIcon(boolean z) {
        if (z) {
            this.btn_fav.setText(this.wordModels.getRemove_favorites());
            this.btn_full_fav.setColorFilter(getResources().getColor(R.color.yellow));
        } else {
            this.btn_fav.setText(this.wordModels.getAdd_to_favorite());
            this.btn_full_fav.setColorFilter(getResources().getColor(R.color.white));
        }
    }

    private void showLockDlgFragment(final int i) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_lock");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LockDlgFragment lockDlgFragmentNewInstance = LockDlgFragment.newInstance(this.preferenceHelper.getSharedPreferenceParentPassword());
        this.lockDlgFragment = lockDlgFragmentNewInstance;
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.activities.mobile.LiveMobileActivity.1
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                liveMobileActivity.category_pos = i;
                StringBuilder sb = new StringBuilder();
                sb.append(LiveMobileActivity.this.category_pos + 1);
                sb.append(" • Group : ");
                LiveMobileActivity liveMobileActivity2 = LiveMobileActivity.this;
                sb.append(liveMobileActivity2.categoryModels.get(liveMobileActivity2.category_pos).getName());
                liveMobileActivity.categoryName = sb.toString();
                LiveMobileActivity.this.epgChannels = RealmController.with().getLiveChannelsByCategory(LiveMobileActivity.this.categoryModels.get(i), "", LiveMobileActivity.this.preferenceHelper.getSharedPreferenceISM3U(), LiveMobileActivity.this.preferenceHelper.getSharedPreferenceLiveOrder());
                LiveMobileActivity liveMobileActivity3 = LiveMobileActivity.this;
                liveMobileActivity3.channelAdapter.updateData(liveMobileActivity3.epgChannels, -1);
                LiveMobileActivity.this.recycler_channel.scrollToPosition(0);
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                Toast.makeText(liveMobileActivity, liveMobileActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                LiveMobileActivity liveMobileActivity = LiveMobileActivity.this;
                Toast.makeText(liveMobileActivity, liveMobileActivity.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(supportFragmentManager, "fragment_lock");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLockImageIcon(boolean z) {
        if (z) {
            this.btn_lock.setImageResource(R.drawable.ic_locked);
        } else {
            this.btn_lock.setImageResource(R.drawable.ic_lock);
        }
    }

    private void showSearchDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag("fragment_search");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        LiveSearchDlgFragment liveSearchDlgFragmentNewInstance = LiveSearchDlgFragment.newInstance(this, this.categoryModels.get(this.category_pos), this.category_pos, this.preferenceHelper.getSharedPreferenceISM3U());
        this.searchDlgFragment = liveSearchDlgFragmentNewInstance;
        liveSearchDlgFragmentNewInstance.setSelectCurrentChannelListener(new LiveMobileActivity$$ExternalSyntheticLambda0(this));
        this.searchDlgFragment.show(supportFragmentManager, "fragment_search");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back /* 2131427464 */:
                this.is_full = false;
                setFull();
                break;
            case R.id.btn_catch_up /* 2131427466 */:
            case R.id.btn_full_catch /* 2131427471 */:
                goToCatchupActivity();
                break;
            case R.id.btn_fav /* 2131427469 */:
            case R.id.btn_full_fav /* 2131427472 */:
                EPGChannel ePGChannel = this.selectedChannel;
                if (ePGChannel != null) {
                    controlFav(ePGChannel, this.channel_pos);
                    showFavImageIcon(!this.selectedChannel.is_favorite());
                    if (!this.selectedChannel.is_favorite()) {
                        Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                    } else {
                        Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                    }
                }
                break;
            case R.id.btn_full_search /* 2131427473 */:
                this.ly_control.setVisibility(8);
                showSearchDlgFragment();
                break;
            case R.id.btn_lock /* 2131427475 */:
                EPGChannel ePGChannel2 = this.selectedChannel;
                if (ePGChannel2 != null) {
                    if (isAdultChannel(ePGChannel2.getCategory_id(), this.selectedChannel.getCategory_name())) {
                        Toast.makeText(this, this.wordModels.getChannel_locked_default(), 0).show();
                    } else if (!this.selectedChannel.is_locked()) {
                        controlLock(this.selectedChannel, this.channel_pos);
                        showLockImageIcon(!this.selectedChannel.is_locked());
                        if (!this.selectedChannel.is_locked()) {
                            Toast.makeText(this, this.wordModels.getChannel_locked(), 0).show();
                        } else {
                            Toast.makeText(this, this.wordModels.getChannel_unlocked(), 0).show();
                        }
                    } else {
                        showChannelLockDlgFragment(this.selectedChannel, this.channel_pos, 4);
                    }
                }
                break;
            case R.id.btn_next /* 2131427477 */:
                playNextChannel();
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
                playPreviousChannel();
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
            case R.id.btn_search /* 2131427487 */:
                goToSearchActivity();
                break;
            case R.id.txt_home /* 2131428313 */:
                releaseMediaPlayer();
                if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id) && this.selectedChannel != null) {
                    saveCategoryAndChannelPosition();
                }
                finish();
                break;
            case R.id.txt_movie /* 2131428322 */:
                goToVodActivity();
                break;
            case R.id.txt_series /* 2131428343 */:
                goToSeriesActivity();
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
        setContentView(R.layout.activity_live_mobile);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        if (Build.VERSION.SDK_INT >= 26) {
            new PictureInPictureParams.Builder();
        }
        this.dataSourceFactory = DemoUtil.getDataSourceFactory(this);
        this.trackSelectionParameters = new TrackSelectionParameters.Builder(this).setTrackTypeDisabled(3, !this.preferenceHelper.getSharedPreferenceSubtitleEnable()).build();
        this.is_full = getIntent().getBooleanExtra("is_full", false);
        Constants.getLiveGroupModels(this.preferenceHelper.getSharedPreferenceInvisibleLiveCategories(), this);
        this.categoryModels = LTVApp.live_categories_filter;
        int sharedPreferenceCategoryPos = this.preferenceHelper.getSharedPreferenceCategoryPos();
        this.category_pos = sharedPreferenceCategoryPos;
        if (sharedPreferenceCategoryPos > this.categoryModels.size() - 1) {
            this.category_pos = 1;
        }
        if (isAdultChannel(this.categoryModels.get(this.category_pos).getId(), this.categoryModels.get(this.category_pos).getId())) {
            this.category_pos = 1;
        }
        this.categoryAdapter = new RecyclerLiveCategoryAdapter(this, this.categoryModels, this.preferenceHelper.getSharedPreferenceISM3U(), false, this.category_pos, new LiveActivity$$ExternalSyntheticLambda3(this, 8));
        this.recycler_category.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_category.setAdapter(this.categoryAdapter);
        this.recycler_category.scrollToPosition(this.category_pos);
        int i = this.category_pos;
        int i2 = 4;
        if (i <= 4 || i >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
            this.epgChannels = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
        } else if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() > 0) {
            this.epgChannels = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(this.category_pos - 5)), "");
        }
        this.channel_pos = this.preferenceHelper.getSharedPreferenceChannelPos();
        if (this.epgChannels.size() <= 0) {
            this.channel_pos = -1;
        } else if (this.channel_pos > this.epgChannels.size() - 1) {
            this.channel_pos = 0;
        }
        this.channelAdapter = new RecyclerLiveChannelAdapter(this, this.epgChannels, this.channel_pos, new LiveActivity$$ExternalSyntheticLambda4(this, i2));
        this.recycler_channel.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_channel.setAdapter(this.channelAdapter);
        this.recycler_channel.requestFocus();
        this.recycler_channel.scrollToPosition(this.channel_pos);
        this.epgAdapter = new EpgRecyclerAdapter(this, new ArrayList());
        this.recycler_epg.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_epg.setAdapter(this.epgAdapter);
        this.recycler_epg.setFocusable(false);
        if (this.epgChannels.size() > 0) {
            setFull();
            if (isAdultChannel(((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_id(), ((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_name())) {
                this.channel_pos = 0;
            }
            if (((EPGChannel) this.epgChannels.get(this.channel_pos)).is_locked()) {
                showChannelLockDlgFragment((EPGChannel) this.epgChannels.get(this.channel_pos), this.channel_pos, 3);
                return;
            }
            playSelectedChannel((EPGChannel) this.epgChannels.get(this.channel_pos));
            this.stream_id = ((EPGChannel) this.epgChannels.get(this.channel_pos)).getStream_id();
            if (this.preferenceHelper.getSharedPreferenceISM3U()) {
                showEpgInfo(null);
            } else {
                this.handler.removeCallbacks(this.epgTicker);
                epgTimer(this.stream_id);
            }
            String name = ((EPGChannel) this.epgChannels.get(this.channel_pos)).getName();
            this.channel_name = name;
            this.txt_name.setText(name);
            showFavImageIcon(((EPGChannel) this.epgChannels.get(this.channel_pos)).is_favorite());
            changeChannelInfo(this.channel_pos);
            return;
        }
        this.category_pos = 2;
        this.channel_pos = 0;
        setFull();
        RealmResults<EPGChannel> liveChannelsByCategory = RealmController.with().getLiveChannelsByCategory(this.categoryModels.get(this.category_pos), "", this.preferenceHelper.getSharedPreferenceISM3U(), this.preferenceHelper.getSharedPreferenceLiveOrder());
        this.epgChannels = liveChannelsByCategory;
        this.channelAdapter.updateData(liveChannelsByCategory, 0);
        if (isAdultChannel(((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_id(), ((EPGChannel) this.epgChannels.get(this.channel_pos)).getCategory_name())) {
            this.recycler_category.requestFocus();
            return;
        }
        playSelectedChannel((EPGChannel) this.epgChannels.get(this.channel_pos));
        this.stream_id = ((EPGChannel) this.epgChannels.get(this.channel_pos)).getStream_id();
        if (this.preferenceHelper.getSharedPreferenceISM3U()) {
            showEpgInfo(null);
        } else {
            this.handler.removeCallbacks(this.epgTicker);
            epgTimer(this.stream_id);
        }
        this.txt_name.setText(this.channel_name);
        changeChannelInfo(this.channel_pos);
        this.recycler_channel.requestFocus();
        this.recycler_channel.scrollToPosition(this.channel_pos);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            StyledPlayerView styledPlayerView = this.playerView;
            if (styledPlayerView != null) {
                styledPlayerView.onPause();
            }
            releaseMediaPlayer();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            int id = seekBar.getId();
            if (id != R.id.bright_seekbar) {
                if (id != R.id.volume_seekbar) {
                    return;
                }
                this.audioManager.setStreamVolume(3, (this.audioManager.getStreamMaxVolume(3) * i) / 100, 0);
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
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("package:");
                sbM.append(getPackageName());
                intent.setData(Uri.parse(sbM.toString()));
                startActivity(intent);
            }
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
            releaseMediaPlayer();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.app.Activity
    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
        releaseMediaPlayer();
    }
}
