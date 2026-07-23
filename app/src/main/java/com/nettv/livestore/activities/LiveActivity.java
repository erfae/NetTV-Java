package com.nettv.livestore.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
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
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
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
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.Util$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.video.VideoSize;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.EpgRecyclerAdapter;
import com.nettv.livestore.adapter.RecyclerLiveCategoryAdapter;
import com.nettv.livestore.adapter.RecyclerLiveChannelAdapter;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.FocusStatus;
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
import com.nettv.livestore.view.LiveVerticalGridView;
import io.realm.RealmResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class LiveActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {
    public Button btn_catch_up;
    public Button btn_fav;
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
    public LiveActivity$$ExternalSyntheticLambda2 hideInfoTicker;
    public int hide_time;
    public ImageButton image_audio;
    public ImageView image_def;
    public ImageButton image_epg;
    public ImageButton image_fav;
    public ImageButton image_lock;
    public ImageButton image_search;
    public ImageButton image_series;
    public ImageButton image_subtitle;
    public ImageButton image_vod;
    public EPGChannel keySelChannel;
    public LockDlgFragment lockDlgFragment;
    public ConstraintLayout ly_actions;
    public ConstraintLayout ly_buttons;
    public ConstraintLayout ly_control;
    public ConstraintLayout ly_surface;
    public ConstraintLayout main_lay;
    public LiveActivity$$ExternalSyntheticLambda2 moveTicker;
    public int move_time;
    public ExoPlayer player;
    public StyledPlayerView playerView;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_category;
    public LiveVerticalGridView recycler_channel;
    public RecyclerView recycler_epg;
    public LiveSearchDlgFragment searchDlgFragment;
    public SeekBar seekBar;
    public EPGChannel selectedChannel;
    public TrackSelectionParameters trackSelectionParameters;
    public DefaultTrackSelector trackSelector;
    public TextView txt_audio;
    public TextView txt_bottom_series;
    public TextView txt_channel_name;
    public TextView txt_current_program;
    public TextView txt_current_time;
    public TextView txt_epg;
    public TextView txt_fav;
    public TextView txt_group;
    public TextView txt_home;
    public TextView txt_left;
    public TextView txt_live;
    public TextView txt_lock;
    public TextView txt_movie;
    public TextView txt_name;
    public TextView txt_next_program;
    public TextView txt_next_time;
    public TextView txt_num;
    public TextView txt_resolution;
    public TextView txt_right;
    public TextView txt_search;
    public TextView txt_series;
    public TextView txt_subtitle;
    public TextView txt_vod;
    public WordModels wordModels;
    public int category_pos = 0;
    public int channel_pos = 0;
    public int pre_category_pos = 0;
    public int pre_channel_pos = 0;
    public int move_pos = 0;
    public int error_count = 0;
    public String stream_id = "";
    public String key = "";
    public boolean is_full = false;
    public Handler handler = new Handler();
    public String categoryName = "";
    public FocusStatus focusStatus = FocusStatus.second;
    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new LiveActivity$$ExternalSyntheticLambda0(this));

    /* JADX INFO: renamed from: com.nettv.livestore.activities.LiveActivity$8, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass8 {
        public static final /* synthetic */ int[] $SwitchMap$com$vilhao$player$apps$FocusStatus;

        static {
            int[] iArr = new int[FocusStatus.values().length];
            $SwitchMap$com$vilhao$player$apps$FocusStatus = iArr;
            try {
                iArr[FocusStatus.first.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$vilhao$player$apps$FocusStatus[FocusStatus.second.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$vilhao$player$apps$FocusStatus[FocusStatus.third.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

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
                LiveActivity.this.releaseMediaPlayer();
                LiveActivity liveActivity = LiveActivity.this;
                liveActivity.playVideo(liveActivity.content_url);
            } else if (i == 3) {
                LiveActivity.this.error_count = 0;
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public final /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onPlayerError(PlaybackException playbackException) {
            if (playbackException.errorCode == 1002) {
                LiveActivity.this.releaseMediaPlayer();
                LiveActivity liveActivity = LiveActivity.this;
                liveActivity.playVideo(liveActivity.content_url);
                return;
            }
            LiveActivity liveActivity2 = LiveActivity.this;
            int i = liveActivity2.error_count;
            if (i > 3) {
                liveActivity2.releaseMediaPlayer();
                LiveActivity.this.image_def.setVisibility(0);
            } else {
                liveActivity2.error_count = i + 1;
                liveActivity2.releaseMediaPlayer();
                LiveActivity liveActivity3 = LiveActivity.this;
                liveActivity3.playVideo(liveActivity3.content_url);
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
        RealmController.with().addToFavChannels(ePGChannel.getName(), !ePGChannel.is_favorite(), new LiveActivity$$ExternalSyntheticLambda1(this, i, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlLock(EPGChannel ePGChannel, int i) {
        if (ePGChannel == null || Constants.xxx_live_categories.contains(this.categoryModels.get(this.category_pos).getId())) {
            return;
        }
        RealmController.with().addToLockChannels(ePGChannel.getName(), !ePGChannel.is_locked(), new LiveActivity$$ExternalSyntheticLambda1(this, i, 1));
    }

    private MediaSource.Factory createMediaSourceFactory() {
        DefaultDrmSessionManagerProvider defaultDrmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
        defaultDrmSessionManagerProvider.setDrmHttpDataSourceFactory(DemoUtil.getHttpDataSourceFactory(this));
        return new DefaultMediaSourceFactory(this).setDataSourceFactory(this.dataSourceFactory).setDrmSessionManagerProvider((DrmSessionManagerProvider) defaultDrmSessionManagerProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void epgTimer(String str) {
        this.epgTime = 1;
        Util$$ExternalSyntheticLambda0 util$$ExternalSyntheticLambda0 = new Util$$ExternalSyntheticLambda0(this, str, 10);
        this.epgTicker = util$$ExternalSyntheticLambda0;
        util$$ExternalSyntheticLambda0.run();
    }

    private void findAndShowChannel() {
        if (this.move_pos <= RealmController.with().getAllEpgChannelSize() - 1) {
            this.txt_num.setText(this.key);
            this.handler.removeCallbacks(this.moveTicker);
            moveTimer();
        } else {
            this.txt_num.setText("");
            this.key = "";
            this.move_pos = 0;
            this.handler.removeCallbacks(this.moveTicker);
        }
    }

    private void findChannelInfo() {
        for (int i = 0; i < this.epgChannels.size(); i++) {
            if (Integer.parseInt(((EPGChannel) this.epgChannels.get(i)).getNum()) == this.move_pos) {
                this.keySelChannel = (EPGChannel) this.epgChannels.get(i);
                this.channel_pos = i;
            }
        }
        if (this.keySelChannel == null) {
            this.key = "";
            this.txt_num.setText("");
            this.txt_num.setVisibility(8);
            Toast.makeText(this, this.wordModels.getNo_channels(), 0).show();
            return;
        }
        this.key = "";
        this.txt_num.setText("");
        this.txt_num.setVisibility(8);
        if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.all_id) && isAdultChannel(this.keySelChannel.getCategory_id(), this.keySelChannel.getCategory_name())) {
            showChannelLockDlgFragment(this.keySelChannel, this.channel_pos, 2);
            return;
        }
        playSelectedChannel(this.keySelChannel);
        this.recycler_channel.setSelectedPosition(this.channel_pos);
        this.handler.removeCallbacks(this.epgTicker);
        getShortEpg(this.stream_id);
        changeChannelInfo(this.channel_pos);
        if (this.ly_control.getVisibility() == 8 && this.is_full) {
            this.ly_control.setVisibility(0);
        }
        this.handler.removeCallbacks(this.hideInfoTicker);
        mInfoHideTimer();
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
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_short_epg(this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), str).enqueue(new Callback<CatchUpEpgResponse>() { // from class: com.nettv.livestore.activities.LiveActivity.4
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<CatchUpEpgResponse> call, @NonNull Throwable th) {
                    LiveActivity.this.showEpgInfo(null);
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<CatchUpEpgResponse> call, @NonNull Response<CatchUpEpgResponse> response) {
                    if (response.body() == null || response.body().getEpg_listings() == null || response.body().getEpg_listings().size() <= 0) {
                        LiveActivity.this.showEpgInfo(null);
                        return;
                    }
                    LiveActivity.this.showEpgInfo(response.body().getEpg_listings());
                    LiveActivity.this.epgEventList = response.body().getEpg_listings();
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

    private void goToSettingActivity() {
        releaseMediaPlayer();
        this.someActivityResultLauncher.launch(new Intent(this, (Class<?>) SettingActivity.class));
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
        this.txt_num = (TextView) findViewById(R.id.txt_num);
        this.txt_home = (TextView) findViewById(R.id.txt_home);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.txt_name = (TextView) findViewById(R.id.txt_name);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.recycler_category = (LiveVerticalGridView) findViewById(R.id.recycler_category);
        this.recycler_channel = (LiveVerticalGridView) findViewById(R.id.recycler_channel);
        this.ly_surface = (ConstraintLayout) findViewById(R.id.ly_surface);
        this.recycler_epg = (RecyclerView) findViewById(R.id.recycler_epg);
        this.btn_fav = (Button) findViewById(R.id.btn_fav);
        this.btn_catch_up = (Button) findViewById(R.id.btn_catch_up);
        this.btn_search = (Button) findViewById(R.id.btn_search);
        this.txt_left = (TextView) findViewById(R.id.txt_left);
        this.txt_right = (TextView) findViewById(R.id.txt_right);
        this.txt_audio = (TextView) findViewById(R.id.txt_audio);
        this.txt_epg = (TextView) findViewById(R.id.txt_epg);
        this.txt_vod = (TextView) findViewById(R.id.txt_vod);
        this.txt_search = (TextView) findViewById(R.id.txt_search);
        this.txt_bottom_series = (TextView) findViewById(R.id.txt_bottom_series);
        this.txt_fav = (TextView) findViewById(R.id.txt_fav);
        this.txt_subtitle = (TextView) findViewById(R.id.txt_subtitle);
        this.txt_lock = (TextView) findViewById(R.id.txt_lock);
        this.txt_left.setText(this.wordModels.getPrevious_channel());
        this.txt_right.setText(this.wordModels.getNext_channel());
        this.txt_home.setText(this.wordModels.getHome());
        this.txt_live.setText(this.wordModels.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.txt_audio.setText(this.wordModels.getAudio_track());
        this.btn_catch_up.setText(this.wordModels.getCatch_up());
        this.btn_search.setText(this.wordModels.getSearch());
        this.btn_fav.setText(this.wordModels.getAdd_to_favorite());
        this.txt_epg.setText(this.wordModels.getEpg());
        this.txt_vod.setText(this.wordModels.getMovies());
        this.txt_search.setText(this.wordModels.getSearch());
        this.txt_bottom_series.setText(this.wordModels.getSeries());
        this.txt_fav.setText(this.wordModels.getFavorite());
        this.txt_subtitle.setText(this.wordModels.getSubtitle());
        this.txt_lock.setText(this.wordModels.getLock());
        this.recycler_category.setNumColumns(1);
        this.recycler_category.setLoop(false);
        this.recycler_category.setPreserveFocusAfterLayout(true);
        final View[] viewArr = {null};
        this.recycler_category.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.LiveActivity.5
            @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
            public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                View[] viewArr2 = viewArr;
                if (viewArr2[0] != null) {
                    viewArr2[0].setSelected(false);
                    View[] viewArr3 = viewArr;
                    viewArr3[0] = viewHolder.itemView;
                    viewArr3[0].setSelected(true);
                }
            }
        });
        this.recycler_channel.setNumColumns(1);
        this.recycler_channel.setLoop(false);
        this.recycler_channel.setPreserveFocusAfterLayout(true);
        final View[] viewArr2 = {null};
        this.recycler_channel.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.LiveActivity.6
            @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
            public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                View[] viewArr3 = viewArr2;
                if (viewArr3[0] != null) {
                    viewArr3[0].setSelected(false);
                    View[] viewArr4 = viewArr2;
                    viewArr4[0] = viewHolder.itemView;
                    viewArr4[0].setSelected(true);
                }
            }
        });
        this.ly_control = (ConstraintLayout) findViewById(R.id.ly_control);
        this.ly_buttons = (ConstraintLayout) findViewById(R.id.ly_buttons);
        this.ly_actions = (ConstraintLayout) findViewById(R.id.ly_actions);
        this.image_epg = (ImageButton) findViewById(R.id.image_epg);
        this.image_lock = (ImageButton) findViewById(R.id.image_lock);
        this.image_vod = (ImageButton) findViewById(R.id.image_vod);
        this.image_series = (ImageButton) findViewById(R.id.image_series);
        this.image_subtitle = (ImageButton) findViewById(R.id.image_subtitle);
        this.image_audio = (ImageButton) findViewById(R.id.image_audio);
        this.image_fav = (ImageButton) findViewById(R.id.image_fav);
        this.image_search = (ImageButton) findViewById(R.id.image_search);
        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.txt_current_time = (TextView) findViewById(R.id.txt_current_time);
        this.txt_current_program = (TextView) findViewById(R.id.txt_current_program);
        this.txt_next_time = (TextView) findViewById(R.id.txt_next_time);
        this.txt_next_program = (TextView) findViewById(R.id.txt_next_program);
        this.txt_group = (TextView) findViewById(R.id.txt_group);
        this.txt_channel_name = (TextView) findViewById(R.id.txt_channel_name);
        this.txt_resolution = (TextView) findViewById(R.id.txt_resolution);
        this.channel_image = (ImageView) findViewById(R.id.channel_image);
        this.image_def = (ImageView) findViewById(R.id.image_def);
        this.image_epg.setOnFocusChangeListener(this);
        this.image_vod.setOnFocusChangeListener(this);
        this.image_series.setOnFocusChangeListener(this);
        this.image_fav.setOnFocusChangeListener(this);
        this.image_search.setOnFocusChangeListener(this);
        this.image_subtitle.setOnFocusChangeListener(this);
        this.image_audio.setOnFocusChangeListener(this);
        this.image_lock.setOnFocusChangeListener(this);
        this.image_epg.setOnClickListener(this);
        this.image_vod.setOnClickListener(this);
        this.image_series.setOnClickListener(this);
        this.image_fav.setOnClickListener(this);
        this.image_search.setOnClickListener(this);
        this.image_subtitle.setOnClickListener(this);
        this.image_audio.setOnClickListener(this);
        this.image_lock.setOnClickListener(this);
        this.txt_live.setOnClickListener(this);
        this.txt_home.setOnClickListener(this);
        this.txt_series.setOnClickListener(this);
        this.txt_movie.setOnClickListener(this);
        this.btn_fav.setOnClickListener(this);
        this.btn_catch_up.setOnClickListener(this);
        this.btn_search.setOnClickListener(this);
        this.et_search.addTextChangedListener(new TextWatcher() { // from class: com.nettv.livestore.activities.LiveActivity.7
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                LiveActivity.this.searchChannelsInCategory(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
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
            this.channelAdapter.updateData(liveChannelsByCategory, 0);
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
    public /* synthetic */ void lambda$mInfoHideTimer$7() {
        if (this.hide_time == 0 && this.ly_control.getVisibility() == 0) {
            this.ly_control.setVisibility(8);
            this.focusStatus = FocusStatus.second;
            showAndHideActionsButtons(false);
        }
        moveNexHideTicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveTimer$8() {
        this.handler.removeCallbacks(this.moveTicker);
        if (this.move_time == 0) {
            findChannelInfo();
        }
        moveNextTicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(ActivityResult activityResult) {
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
            this.recycler_category.setSelectedPosition(this.category_pos);
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
        setFocusButtons(false);
        this.recycler_channel.requestFocus();
        this.recycler_channel.setSelectedPosition(this.channel_pos);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(CategoryModel categoryModel, Integer num, Boolean bool) {
        this.pre_category_pos = num.intValue();
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
            this.recycler_channel.setSelectedPosition(0);
            return null;
        }
        if (this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() <= 0) {
            return null;
        }
        RealmResults<EPGChannel> epgChannelsByNameArray = RealmController.with().getEpgChannelsByNameArray(this.preferenceHelper.getSharedPreferenceMyGroupChannels(this.preferenceHelper.getSharedPreferenceMyGroupCategory().get(this.category_pos - 5)), "");
        this.epgChannels = epgChannelsByNameArray;
        this.channelAdapter.updateData(epgChannelsByNameArray, -1);
        this.recycler_channel.setSelectedPosition(0);
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
            String name = ePGChannel.getName();
            this.channel_name = name;
            this.txt_name.setText(name);
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
        this.recycler_channel.setSelectedPosition(0);
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
        this.recycler_channel.setSelectedPosition(realChannelPosition);
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
        LiveActivity$$ExternalSyntheticLambda2 liveActivity$$ExternalSyntheticLambda2 = new LiveActivity$$ExternalSyntheticLambda2(this, 1);
        this.hideInfoTicker = liveActivity$$ExternalSyntheticLambda2;
        liveActivity$$ExternalSyntheticLambda2.run();
    }

    private void moveNexHideTicker() {
        this.hide_time--;
        this.handler.postAtTime(this.hideInfoTicker, SystemClock.uptimeMillis() + 1000);
    }

    private void moveNextTicker() {
        this.move_time--;
        this.handler.postAtTime(this.moveTicker, SystemClock.uptimeMillis() + 1000);
    }

    private void moveTimer() {
        this.move_time = 2;
        LiveActivity$$ExternalSyntheticLambda2 liveActivity$$ExternalSyntheticLambda2 = new LiveActivity$$ExternalSyntheticLambda2(this, 0);
        this.moveTicker = liveActivity$$ExternalSyntheticLambda2;
        liveActivity$$ExternalSyntheticLambda2.run();
    }

    private void playNextChannel() {
        if (this.channel_pos < this.epgChannels.size() - 1) {
            this.channel_pos++;
        } else {
            this.channel_pos = 0;
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
        this.recycler_channel.setSelectedPosition(this.channel_pos);
    }

    private void playPreviousChannel() {
        int i = this.channel_pos;
        if (i > 0) {
            this.channel_pos = i - 1;
        } else {
            this.channel_pos = this.epgChannels.size() - 1;
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
        this.recycler_channel.setSelectedPosition(this.channel_pos);
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
                RealmController.with().addToRecentChannels(this.selectedChannel.getName(), true, new LiveActivity$$ExternalSyntheticLambda0(this));
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
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(this);
        this.trackSelector = defaultTrackSelector;
        defaultTrackSelector.setParameters(this.trackSelectionParameters);
        ExoPlayer.Builder trackSelector = new ExoPlayer.Builder(this).setMediaSourceFactory(createMediaSourceFactory()).setTrackSelector(this.trackSelector);
        setRenderersFactory(trackSelector, true);
        ExoPlayer exoPlayerBuild = trackSelector.build();
        this.player = exoPlayerBuild;
        exoPlayerBuild.setTrackSelectionParameters(this.trackSelectionParameters);
        this.player.addAnalyticsListener(new AnalyticsListener() { // from class: com.nettv.livestore.activities.LiveActivity.3
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
                LiveActivity.this.txt_resolution.setText(videoSize.width + "x" + videoSize.height);
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
        this.recycler_channel.setSelectedPosition(0);
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

    private void setFocusButtons(boolean z) {
        this.btn_catch_up.setFocusable(z);
        this.btn_fav.setFocusable(z);
        this.btn_search.setFocusable(z);
    }

    private void setFocusTopView(boolean z) {
        this.txt_home.setFocusable(z);
        this.txt_live.setFocusable(z);
        this.txt_movie.setFocusable(z);
        this.txt_series.setFocusable(z);
        this.et_search.setFocusable(z);
    }

    private void setFull() {
        if (this.is_full) {
            this.btn_catch_up.setVisibility(8);
            this.btn_fav.setVisibility(8);
            this.btn_search.setVisibility(8);
            this.recycler_channel.setVisibility(8);
            this.recycler_category.setVisibility(8);
            setMargins(this.ly_surface, 0, 0, 0, 0);
            if (this.ly_control.getVisibility() == 8) {
                this.ly_control.setVisibility(0);
            }
            this.handler.removeCallbacks(this.hideInfoTicker);
            mInfoHideTimer();
        } else {
            setMargins(this.ly_surface, 0, 0, 0, 0);
            this.btn_catch_up.setVisibility(0);
            this.btn_fav.setVisibility(0);
            this.btn_search.setVisibility(0);
            this.handler.removeCallbacks(this.hideInfoTicker);
            this.ly_control.setVisibility(8);
            showAndHideActionsButtons(false);
            this.focusStatus = FocusStatus.second;
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

    private void setMargins(View view, int i, int i2, int i3, int i4) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(i, i2, i3, i4);
            view.requestLayout();
        }
    }

    private void setRenderersFactory(ExoPlayer.Builder builder, boolean z) {
        builder.setRenderersFactory(DemoUtil.buildRenderersFactory(this, z));
    }

    private void showAndHideActionsButtons(boolean z) {
        if (!z) {
            this.ly_buttons.setVisibility(8);
            this.ly_actions.setVisibility(0);
        } else {
            this.ly_buttons.setVisibility(0);
            this.ly_actions.setVisibility(8);
            this.image_epg.requestFocus();
        }
    }

    private void showAudioTrack() {
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
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.activities.LiveActivity.2
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                int i3 = i2;
                if (i3 == 0) {
                    LiveActivity liveActivity = LiveActivity.this;
                    liveActivity.channel_pos = i;
                    liveActivity.playSelectedChannel(ePGChannel);
                    return;
                }
                if (i3 == 1) {
                    LiveActivity.this.playSelectedChannel(ePGChannel);
                    if (LiveActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                        LiveActivity.this.showEpgInfo(null);
                    } else {
                        LiveActivity liveActivity2 = LiveActivity.this;
                        liveActivity2.handler.removeCallbacks(liveActivity2.epgTicker);
                        LiveActivity liveActivity3 = LiveActivity.this;
                        liveActivity3.epgTimer(liveActivity3.stream_id);
                    }
                    LiveActivity liveActivity4 = LiveActivity.this;
                    liveActivity4.changeChannelInfo(liveActivity4.channel_pos);
                    if (LiveActivity.this.ly_control.getVisibility() == 8) {
                        LiveActivity.this.ly_control.setVisibility(0);
                    }
                    LiveActivity liveActivity5 = LiveActivity.this;
                    liveActivity5.handler.removeCallbacks(liveActivity5.hideInfoTicker);
                    LiveActivity.this.mInfoHideTimer();
                    LiveActivity liveActivity6 = LiveActivity.this;
                    liveActivity6.txt_name.setText(liveActivity6.channel_name);
                    LiveActivity liveActivity7 = LiveActivity.this;
                    liveActivity7.recycler_channel.setSelectedPosition(liveActivity7.channel_pos);
                    return;
                }
                if (i3 == 2) {
                    LiveActivity.this.playSelectedChannel(ePGChannel);
                    LiveActivity liveActivity8 = LiveActivity.this;
                    liveActivity8.recycler_channel.setSelectedPosition(liveActivity8.channel_pos);
                    LiveActivity liveActivity9 = LiveActivity.this;
                    liveActivity9.handler.removeCallbacks(liveActivity9.epgTicker);
                    LiveActivity liveActivity10 = LiveActivity.this;
                    liveActivity10.getShortEpg(liveActivity10.stream_id);
                    LiveActivity liveActivity11 = LiveActivity.this;
                    liveActivity11.changeChannelInfo(liveActivity11.channel_pos);
                    if (LiveActivity.this.ly_control.getVisibility() == 8) {
                        LiveActivity liveActivity12 = LiveActivity.this;
                        if (liveActivity12.is_full) {
                            liveActivity12.ly_control.setVisibility(0);
                        }
                    }
                    LiveActivity liveActivity13 = LiveActivity.this;
                    liveActivity13.handler.removeCallbacks(liveActivity13.hideInfoTicker);
                    LiveActivity.this.mInfoHideTimer();
                    return;
                }
                if (i3 != 3) {
                    if (i3 != 4) {
                        return;
                    }
                    LiveActivity.this.controlLock(ePGChannel, i);
                    LiveActivity.this.showLockImageIcon(!ePGChannel.is_locked());
                    if (ePGChannel.is_locked()) {
                        LiveActivity liveActivity14 = LiveActivity.this;
                        Toast.makeText(liveActivity14, liveActivity14.wordModels.getChannel_unlocked(), 0).show();
                        return;
                    } else {
                        LiveActivity liveActivity15 = LiveActivity.this;
                        Toast.makeText(liveActivity15, liveActivity15.wordModels.getChannel_locked(), 0).show();
                        return;
                    }
                }
                LiveActivity liveActivity16 = LiveActivity.this;
                liveActivity16.playSelectedChannel((EPGChannel) liveActivity16.epgChannels.get(liveActivity16.channel_pos));
                LiveActivity liveActivity17 = LiveActivity.this;
                liveActivity17.stream_id = ((EPGChannel) liveActivity17.epgChannels.get(liveActivity17.channel_pos)).getStream_id();
                if (LiveActivity.this.preferenceHelper.getSharedPreferenceISM3U()) {
                    LiveActivity.this.showEpgInfo(null);
                } else {
                    LiveActivity liveActivity18 = LiveActivity.this;
                    liveActivity18.handler.removeCallbacks(liveActivity18.epgTicker);
                    LiveActivity liveActivity19 = LiveActivity.this;
                    liveActivity19.epgTimer(liveActivity19.stream_id);
                }
                LiveActivity liveActivity20 = LiveActivity.this;
                liveActivity20.txt_name.setText(liveActivity20.channel_name);
                LiveActivity liveActivity21 = LiveActivity.this;
                liveActivity21.changeChannelInfo(liveActivity21.channel_pos);
                LiveActivity.this.recycler_channel.requestFocus();
                LiveActivity liveActivity22 = LiveActivity.this;
                liveActivity22.recycler_channel.setSelectedPosition(liveActivity22.channel_pos);
                LiveActivity liveActivity23 = LiveActivity.this;
                liveActivity23.recycler_channel.scrollToPosition(liveActivity23.channel_pos);
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                LiveActivity liveActivity = LiveActivity.this;
                Toast.makeText(liveActivity, liveActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                LiveActivity liveActivity = LiveActivity.this;
                Toast.makeText(liveActivity, liveActivity.wordModels.getPut_pin_code(), 0).show();
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

    private void showFavImageIcon(boolean z) {
        if (z) {
            this.btn_fav.setText(this.wordModels.getRemove_favorites());
        } else {
            this.btn_fav.setText(this.wordModels.getAdd_to_favorite());
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
        lockDlgFragmentNewInstance.setOnPinEventListener(new LockDlgFragment.OnPinEventListener() { // from class: com.nettv.livestore.activities.LiveActivity.1
            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinCorrect() {
                LiveActivity liveActivity = LiveActivity.this;
                liveActivity.category_pos = i;
                StringBuilder sb = new StringBuilder();
                sb.append(LiveActivity.this.category_pos + 1);
                sb.append(" • Group : ");
                LiveActivity liveActivity2 = LiveActivity.this;
                sb.append(liveActivity2.categoryModels.get(liveActivity2.category_pos).getName());
                liveActivity.categoryName = sb.toString();
                LiveActivity.this.epgChannels = RealmController.with().getLiveChannelsByCategory(LiveActivity.this.categoryModels.get(i), "", LiveActivity.this.preferenceHelper.getSharedPreferenceISM3U(), LiveActivity.this.preferenceHelper.getSharedPreferenceLiveOrder());
                LiveActivity liveActivity3 = LiveActivity.this;
                liveActivity3.channelAdapter.updateData(liveActivity3.epgChannels, -1);
                LiveActivity.this.recycler_channel.setSelectedPosition(0);
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPinIncorrect() {
                LiveActivity liveActivity = LiveActivity.this;
                Toast.makeText(liveActivity, liveActivity.wordModels.getPin_incorrect(), 0).show();
            }

            @Override // com.nettv.livestore.dlgfragment.LockDlgFragment.OnPinEventListener
            public void OnPutPinCode() {
                LiveActivity liveActivity = LiveActivity.this;
                Toast.makeText(liveActivity, liveActivity.wordModels.getPut_pin_code(), 0).show();
            }
        });
        this.lockDlgFragment.show(supportFragmentManager, "fragment_lock");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLockImageIcon(boolean z) {
        if (z) {
            this.image_lock.setImageResource(R.drawable.ic_locked);
        } else {
            this.image_lock.setImageResource(R.drawable.ic_lock);
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
        liveSearchDlgFragmentNewInstance.setSelectCurrentChannelListener(new LiveActivity$$ExternalSyntheticLambda0(this));
        this.searchDlgFragment.show(supportFragmentManager, "fragment_search");
    }

    private void showSubtitleTrack() {
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

    /* JADX WARN: Code duplicated, block: B:162:0x0326  */
    /* JADX WARN: Code duplicated, block: B:163:0x032b  */
    /* JADX WARN: Code duplicated, block: B:165:0x032f  */
    /* JADX WARN: Code duplicated, block: B:167:0x0333  */
    /* JADX WARN: Code duplicated, block: B:169:0x034a  */
    /* JADX WARN: Code duplicated, block: B:170:0x0359  */
    /* JADX WARN: Code duplicated, block: B:171:0x0368  */
    /* JADX WARN: Code duplicated, block: B:173:0x0374  */
    /* JADX WARN: Code duplicated, block: B:175:0x0387  */
    /* JADX WARN: Code duplicated, block: B:176:0x0396  */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int i;
        EPGChannel ePGChannel;
        EPGChannel ePGChannel2;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                if (this.ly_control.getVisibility() == 0) {
                    this.ly_control.setVisibility(8);
                    showAndHideActionsButtons(false);
                    this.focusStatus = FocusStatus.second;
                    return true;
                }
                if (this.is_full) {
                    this.is_full = false;
                    setFull();
                    return true;
                }
                releaseMediaPlayer();
                if (this.selectedChannel != null) {
                    saveCategoryAndChannelPosition();
                }
                finish();
            } else if (keyCode != 89) {
                if (keyCode != 90) {
                    if (keyCode == 135) {
                        if (!this.is_full) {
                            ePGChannel2 = this.selectedChannel;
                            if (ePGChannel2 != null) {
                                controlFav(ePGChannel2, this.channel_pos);
                                showFavImageIcon(!this.selectedChannel.is_favorite());
                                if (!this.selectedChannel.is_favorite()) {
                                    Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                                } else {
                                    Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                                }
                            }
                        } else {
                            ePGChannel = (EPGChannel) this.epgChannels.get(this.pre_channel_pos);
                            if (ePGChannel != null) {
                                controlFav(ePGChannel, this.channel_pos);
                                showFavImageIcon(!ePGChannel.is_favorite());
                                if (!ePGChannel.is_favorite()) {
                                    Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                                } else {
                                    Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                                }
                            }
                        }
                    } else if (keyCode == 136) {
                        showSearchDlgFragment();
                    } else if (keyCode != 166) {
                        if (keyCode != 167) {
                            switch (keyCode) {
                                case 7:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    if (!this.key.isEmpty()) {
                                        String strM = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "0");
                                        this.key = strM;
                                        this.move_pos = Integer.parseInt(strM);
                                        findAndShowChannel();
                                    }
                                    break;
                                case 8:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM2 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                                    this.key = strM2;
                                    this.move_pos = Integer.parseInt(strM2);
                                    findAndShowChannel();
                                    break;
                                case 9:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM3 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, ExifInterface.GPS_MEASUREMENT_2D);
                                    this.key = strM3;
                                    this.move_pos = Integer.parseInt(strM3);
                                    findAndShowChannel();
                                    break;
                                case 10:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM4 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, ExifInterface.GPS_MEASUREMENT_3D);
                                    this.key = strM4;
                                    this.move_pos = Integer.parseInt(strM4);
                                    findAndShowChannel();
                                    break;
                                case 11:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM5 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "4");
                                    this.key = strM5;
                                    this.move_pos = Integer.parseInt(strM5);
                                    findAndShowChannel();
                                    break;
                                case 12:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM6 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "5");
                                    this.key = strM6;
                                    this.move_pos = Integer.parseInt(strM6);
                                    findAndShowChannel();
                                    break;
                                case 13:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM7 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "6");
                                    this.key = strM7;
                                    this.move_pos = Integer.parseInt(strM7);
                                    findAndShowChannel();
                                    break;
                                case 14:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM8 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "7");
                                    this.key = strM8;
                                    this.move_pos = Integer.parseInt(strM8);
                                    findAndShowChannel();
                                    break;
                                case 15:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM9 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "8");
                                    this.key = strM9;
                                    this.move_pos = Integer.parseInt(strM9);
                                    findAndShowChannel();
                                    break;
                                case 16:
                                    if (this.txt_num.getVisibility() == 8) {
                                        this.txt_num.setVisibility(0);
                                    }
                                    String strM10 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), this.key, "9");
                                    this.key = strM10;
                                    this.move_pos = Integer.parseInt(strM10);
                                    findAndShowChannel();
                                    break;
                                default:
                                    switch (keyCode) {
                                        case 19:
                                            if (this.is_full) {
                                                int i2 = AnonymousClass8.$SwitchMap$com$vilhao$player$apps$FocusStatus[this.focusStatus.ordinal()];
                                                if (i2 == 1 || i2 == 2) {
                                                    this.ly_control.setVisibility(8);
                                                    return true;
                                                }
                                                if (i2 == 3) {
                                                    this.focusStatus = FocusStatus.second;
                                                    showAndHideActionsButtons(false);
                                                }
                                            } else {
                                                if (this.recycler_category.hasFocus() && this.pre_category_pos == 0) {
                                                    setFocusTopView(true);
                                                    this.txt_home.requestFocus();
                                                    return true;
                                                }
                                                if (this.recycler_channel.hasFocus() && this.pre_channel_pos == 0) {
                                                    setFocusTopView(true);
                                                    this.txt_series.requestFocus();
                                                    return true;
                                                }
                                                if (this.btn_catch_up.hasFocus() || this.btn_fav.hasFocus() || this.btn_search.hasFocus()) {
                                                    return true;
                                                }
                                            }
                                            break;
                                        case 20:
                                            if (!this.is_full) {
                                                if (this.txt_home.hasFocus() || this.txt_live.hasFocus() || this.txt_movie.hasFocus()) {
                                                    setFocusTopView(false);
                                                    this.recycler_category.requestFocus();
                                                    return true;
                                                }
                                                if (this.txt_series.hasFocus() || this.et_search.hasFocus()) {
                                                    setFocusTopView(false);
                                                    this.recycler_channel.requestFocus();
                                                    return true;
                                                }
                                            } else if (this.ly_control.getVisibility() == 8) {
                                                this.ly_control.setVisibility(0);
                                                this.handler.removeCallbacks(this.hideInfoTicker);
                                                mInfoHideTimer();
                                            } else {
                                                int i3 = AnonymousClass8.$SwitchMap$com$vilhao$player$apps$FocusStatus[this.focusStatus.ordinal()];
                                                if (i3 == 1) {
                                                    this.focusStatus = FocusStatus.second;
                                                    return true;
                                                }
                                                if (i3 == 2) {
                                                    this.focusStatus = FocusStatus.third;
                                                    showAndHideActionsButtons(true);
                                                    return true;
                                                }
                                                if (i3 == 3) {
                                                    return true;
                                                }
                                            }
                                            break;
                                        case 21:
                                            if (this.is_full) {
                                                if (this.focusStatus == FocusStatus.second) {
                                                    playPreviousChannel();
                                                } else if (this.image_epg.hasFocus()) {
                                                    return true;
                                                }
                                            } else if (this.btn_catch_up.hasFocus()) {
                                                setFocusButtons(false);
                                                this.recycler_channel.requestFocus();
                                                return true;
                                            }
                                            break;
                                        case 22:
                                            if (this.is_full) {
                                                if (this.focusStatus == FocusStatus.second) {
                                                    playNextChannel();
                                                } else if (this.image_audio.hasFocus()) {
                                                    return true;
                                                }
                                            } else if (this.recycler_channel.hasFocus()) {
                                                setFocusButtons(true);
                                                this.btn_catch_up.requestFocus();
                                                return true;
                                            }
                                            break;
                                        case 23:
                                            if (this.is_full && this.ly_control.getVisibility() == 8) {
                                                this.is_full = false;
                                                setFull();
                                                return true;
                                            }
                                            break;
                                        default:
                                            switch (keyCode) {
                                                case 183:
                                                    goToVodActivity();
                                                    break;
                                                case 184:
                                                    goToSeriesActivity();
                                                    break;
                                                case 185:
                                                    if (!this.is_full) {
                                                        ePGChannel = (EPGChannel) this.epgChannels.get(this.pre_channel_pos);
                                                        if (ePGChannel != null) {
                                                            controlFav(ePGChannel, this.channel_pos);
                                                            showFavImageIcon(!ePGChannel.is_favorite());
                                                            if (!ePGChannel.is_favorite()) {
                                                                Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                                                            } else {
                                                                Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                                                            }
                                                        }
                                                    } else {
                                                        ePGChannel2 = this.selectedChannel;
                                                        if (ePGChannel2 != null) {
                                                            controlFav(ePGChannel2, this.channel_pos);
                                                            showFavImageIcon(!this.selectedChannel.is_favorite());
                                                            if (!this.selectedChannel.is_favorite()) {
                                                                Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                                                            } else {
                                                                Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 186:
                                                    showSearchDlgFragment();
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                        } else if (this.is_full) {
                            playPreviousChannel();
                        }
                    } else if (this.is_full) {
                        playNextChannel();
                    }
                } else if (!this.is_full) {
                    if (this.recycler_category.hasFocus()) {
                        if (this.pre_category_pos < this.categoryModels.size() - 11) {
                            int i4 = this.pre_category_pos + 10;
                            this.pre_category_pos = i4;
                            this.recycler_category.setSelectedPosition(i4);
                        }
                    } else if (this.recycler_channel.hasFocus() && this.pre_channel_pos < this.epgChannels.size() - 11) {
                        int i5 = this.pre_channel_pos + 10;
                        this.pre_channel_pos = i5;
                        this.recycler_channel.setSelectedPosition(i5);
                    }
                }
            } else if (!this.is_full) {
                if (this.recycler_category.hasFocus()) {
                    int i6 = this.pre_category_pos;
                    if (i6 > 10) {
                        int i7 = i6 - 10;
                        this.pre_category_pos = i7;
                        this.recycler_category.setSelectedPosition(i7);
                    }
                } else if (this.recycler_channel.hasFocus() && (i = this.pre_channel_pos) > 10) {
                    int i8 = i - 10;
                    this.pre_channel_pos = i8;
                    this.recycler_channel.setSelectedPosition(i8);
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        EPGChannel ePGChannel;
        switch (view.getId()) {
            case R.id.btn_catch_up /* 2131427466 */:
            case R.id.image_epg /* 2131427795 */:
                goToCatchupActivity();
                break;
            case R.id.btn_fav /* 2131427469 */:
                if (this.epgChannels.size() > 0 && (ePGChannel = (EPGChannel) this.epgChannels.get(this.pre_channel_pos)) != null) {
                    controlFav(ePGChannel, this.pre_channel_pos);
                    showFavImageIcon(!ePGChannel.is_favorite());
                    if (!ePGChannel.is_favorite()) {
                        Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                    } else {
                        Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                    }
                    break;
                }
                break;
            case R.id.btn_search /* 2131427487 */:
                goToSearchActivity();
                break;
            case R.id.image_audio /* 2131427780 */:
                showAudioTrack();
                break;
            case R.id.image_fav /* 2131427798 */:
                EPGChannel ePGChannel2 = this.selectedChannel;
                if (ePGChannel2 != null) {
                    controlFav(ePGChannel2, this.channel_pos);
                    showFavImageIcon(!this.selectedChannel.is_favorite());
                    if (!this.selectedChannel.is_favorite()) {
                        Toast.makeText(this, this.wordModels.getChannel_added_to_fav(), 0).show();
                    } else {
                        Toast.makeText(this, this.wordModels.getChannel_removed_from_fav(), 0).show();
                    }
                }
                break;
            case R.id.image_lock /* 2131427804 */:
                EPGChannel ePGChannel3 = this.selectedChannel;
                if (ePGChannel3 != null) {
                    if (isAdultChannel(ePGChannel3.getCategory_id(), this.selectedChannel.getCategory_name())) {
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
            case R.id.image_search /* 2131427815 */:
                this.ly_control.setVisibility(8);
                this.focusStatus = FocusStatus.second;
                showAndHideActionsButtons(false);
                showSearchDlgFragment();
                break;
            case R.id.image_series /* 2131427816 */:
            case R.id.txt_series /* 2131428343 */:
                goToSeriesActivity();
                break;
            case R.id.image_subtitle /* 2131427818 */:
                showSubtitleTrack();
                break;
            case R.id.image_vod /* 2131427824 */:
            case R.id.txt_movie /* 2131428322 */:
                goToVodActivity();
                break;
            case R.id.txt_home /* 2131428313 */:
                releaseMediaPlayer();
                if (this.categoryModels.get(this.category_pos).getId().equalsIgnoreCase(Constants.resume_id) && this.selectedChannel != null) {
                    saveCategoryAndChannelPosition();
                }
                finish();
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_live);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.dataSourceFactory = DemoUtil.getDataSourceFactory(this);
        this.trackSelectionParameters = new TrackSelectionParameters.Builder(this).setTrackTypeDisabled(3, !this.preferenceHelper.getSharedPreferenceSubtitleEnable()).build();
        int i = 0;
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
        RecyclerLiveCategoryAdapter recyclerLiveCategoryAdapter = new RecyclerLiveCategoryAdapter(this, this.categoryModels, this.preferenceHelper.getSharedPreferenceISM3U(), false, this.category_pos, new LiveActivity$$ExternalSyntheticLambda3(this, i));
        this.categoryAdapter = recyclerLiveCategoryAdapter;
        this.recycler_category.setAdapter(recyclerLiveCategoryAdapter);
        this.recycler_category.setSelectedPosition(this.category_pos);
        int i2 = this.category_pos;
        if (i2 <= 4 || i2 >= this.preferenceHelper.getSharedPreferenceMyGroupCategory().size() + 5) {
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
        RecyclerLiveChannelAdapter recyclerLiveChannelAdapter = new RecyclerLiveChannelAdapter(this, this.epgChannels, this.channel_pos, new LiveActivity$$ExternalSyntheticLambda4(this, i));
        this.channelAdapter = recyclerLiveChannelAdapter;
        this.recycler_channel.setAdapter(recyclerLiveChannelAdapter);
        setFocusTopView(false);
        setFocusButtons(false);
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
            this.txt_name.setText(this.channel_name);
            changeChannelInfo(this.channel_pos);
            this.recycler_channel.requestFocus();
            this.recycler_channel.setSelectedPosition(this.channel_pos);
            this.recycler_channel.scrollToPosition(this.channel_pos);
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
        this.recycler_channel.setSelectedPosition(this.channel_pos);
        this.recycler_channel.scrollToPosition(this.channel_pos);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        if (z) {
            this.handler.removeCallbacks(this.hideInfoTicker);
            mInfoHideTimer();
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
            releaseMediaPlayer();
        }
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
}
