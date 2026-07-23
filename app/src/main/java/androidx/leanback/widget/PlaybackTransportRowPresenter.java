package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.leanback.R;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PlaybackTransportRowPresenter extends PlaybackRowPresenter {
    public Presenter mDescriptionPresenter;
    public OnActionClickedListener mOnActionClickedListener;
    private final ControlBarPresenter.OnControlClickedListener mOnControlClickedListener;
    private final ControlBarPresenter.OnControlSelectedListener mOnControlSelectedListener;
    public ControlBarPresenter mPlaybackControlsPresenter;
    public boolean mProgressColorSet;
    public ControlBarPresenter mSecondaryControlsPresenter;
    public boolean mSecondaryProgressColorSet;
    public float mDefaultSeekIncrement = 0.01f;
    public int mProgressColor = 0;
    public int mSecondaryProgressColor = 0;

    public static class BoundData extends PlaybackControlsPresenter.BoundData {
        public ViewHolder mRowViewHolder;
    }

    public class ViewHolder extends PlaybackRowPresenter.ViewHolder implements PlaybackSeekUi {
        public BoundData mControlsBoundData;
        public final ViewGroup mControlsDock;
        public ControlBarPresenter.ViewHolder mControlsVh;
        public final TextView mCurrentTime;
        public long mCurrentTimeInMs;
        public final ViewGroup mDescriptionDock;
        public final Presenter.ViewHolder mDescriptionViewHolder;
        public final ImageView mImageView;
        public boolean mInSeek;
        public final PlaybackControlsRow.OnPlaybackProgressCallback mListener;
        public PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
        public long[] mPositions;
        public int mPositionsLength;
        public final SeekBar mProgressBar;
        public BoundData mSecondaryBoundData;
        public final ViewGroup mSecondaryControlsDock;
        public ControlBarPresenter.ViewHolder mSecondaryControlsVh;
        public PlaybackSeekUi.Client mSeekClient;
        public PlaybackSeekDataProvider mSeekDataProvider;
        public Object mSelectedItem;
        public Presenter.ViewHolder mSelectedViewHolder;
        public final StringBuilder mTempBuilder;
        public int mThumbHeroIndex;
        public PlaybackSeekDataProvider.ResultCallback mThumbResult;
        public final ThumbsBar mThumbsBar;
        public final TextView mTotalTime;
        public long mTotalTimeInMs;

        public ViewHolder(View view, Presenter presenter) {
            super(view);
            this.mTotalTimeInMs = Long.MIN_VALUE;
            this.mCurrentTimeInMs = Long.MIN_VALUE;
            this.mTempBuilder = new StringBuilder();
            this.mControlsBoundData = new BoundData();
            this.mSecondaryBoundData = new BoundData();
            this.mThumbHeroIndex = -1;
            this.mListener = new PlaybackControlsRow.OnPlaybackProgressCallback() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.ViewHolder.1
                @Override // androidx.leanback.widget.PlaybackControlsRow.OnPlaybackProgressCallback
                public void onBufferedPositionChanged(PlaybackControlsRow playbackControlsRow, long j) {
                    ViewHolder viewHolder = ViewHolder.this;
                    viewHolder.mProgressBar.setSecondaryProgress((int) ((j / viewHolder.mTotalTimeInMs) * 2.147483647E9d));
                }

                @Override // androidx.leanback.widget.PlaybackControlsRow.OnPlaybackProgressCallback
                public void onCurrentPositionChanged(PlaybackControlsRow playbackControlsRow, long j) {
                    ViewHolder.this.setCurrentPosition(j);
                }

                @Override // androidx.leanback.widget.PlaybackControlsRow.OnPlaybackProgressCallback
                public void onDurationChanged(PlaybackControlsRow playbackControlsRow, long j) {
                    ViewHolder.this.setTotalTime(j);
                }
            };
            this.mThumbResult = new PlaybackSeekDataProvider.ResultCallback() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.ViewHolder.2
                @Override // androidx.leanback.widget.PlaybackSeekDataProvider.ResultCallback
                public void onThumbnailLoaded(Bitmap bitmap, int i) {
                    ViewHolder viewHolder = ViewHolder.this;
                    int childCount = i - (viewHolder.mThumbHeroIndex - (viewHolder.mThumbsBar.getChildCount() / 2));
                    if (childCount < 0 || childCount >= ViewHolder.this.mThumbsBar.getChildCount()) {
                        return;
                    }
                    ViewHolder.this.mThumbsBar.setThumbBitmap(childCount, bitmap);
                }
            };
            this.mImageView = (ImageView) view.findViewById(R.id.image);
            ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.description_dock);
            this.mDescriptionDock = viewGroup;
            this.mCurrentTime = (TextView) view.findViewById(R.id.current_time);
            this.mTotalTime = (TextView) view.findViewById(R.id.total_time);
            SeekBar seekBar = (SeekBar) view.findViewById(R.id.playback_progress);
            this.mProgressBar = seekBar;
            seekBar.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.ViewHolder.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ViewHolder viewHolder = ViewHolder.this;
                    PlaybackTransportRowPresenter playbackTransportRowPresenter = PlaybackTransportRowPresenter.this;
                    Objects.requireNonNull(playbackTransportRowPresenter);
                    if (viewHolder != null) {
                        if (viewHolder.mPlayPauseAction == null) {
                            viewHolder.mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(viewHolder.view.getContext());
                        }
                        if (viewHolder.getOnItemViewClickedListener() != null) {
                            viewHolder.getOnItemViewClickedListener().onItemClicked(viewHolder, viewHolder.mPlayPauseAction, viewHolder, viewHolder.getRow());
                        }
                        OnActionClickedListener onActionClickedListener = playbackTransportRowPresenter.mOnActionClickedListener;
                        if (onActionClickedListener != null) {
                            onActionClickedListener.onActionClicked(viewHolder.mPlayPauseAction);
                        }
                    }
                }
            });
            seekBar.setOnKeyListener(new View.OnKeyListener() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.ViewHolder.4
                @Override // android.view.View.OnKeyListener
                public boolean onKey(View view2, int i, KeyEvent keyEvent) {
                    if (i != 4) {
                        if (i != 66) {
                            if (i != 69) {
                                if (i != 81) {
                                    if (i != 111) {
                                        if (i != 89) {
                                            if (i != 90) {
                                                switch (i) {
                                                    case 19:
                                                    case 20:
                                                        return ViewHolder.this.mInSeek;
                                                    case 21:
                                                        break;
                                                    case 22:
                                                        break;
                                                    case 23:
                                                        break;
                                                    default:
                                                        return false;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (keyEvent.getAction() == 0) {
                                    ViewHolder viewHolder = ViewHolder.this;
                                    if (viewHolder.startSeek()) {
                                        viewHolder.updateProgressInSeek(true);
                                    }
                                }
                                return true;
                            }
                            if (keyEvent.getAction() == 0) {
                                ViewHolder viewHolder2 = ViewHolder.this;
                                if (viewHolder2.startSeek()) {
                                    viewHolder2.updateProgressInSeek(false);
                                }
                            }
                            return true;
                        }
                        if (!ViewHolder.this.mInSeek) {
                            return false;
                        }
                        if (keyEvent.getAction() == 1) {
                            ViewHolder.this.stopSeek(false);
                        }
                        return true;
                    }
                    if (!ViewHolder.this.mInSeek) {
                        return false;
                    }
                    if (keyEvent.getAction() == 1) {
                        ViewHolder viewHolder3 = ViewHolder.this;
                        viewHolder3.stopSeek(!viewHolder3.mProgressBar.isAccessibilityFocused());
                    }
                    return true;
                }
            });
            seekBar.setAccessibilitySeekListener(new SeekBar.AccessibilitySeekListener() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.ViewHolder.5
                @Override // androidx.leanback.widget.SeekBar.AccessibilitySeekListener
                public boolean onAccessibilitySeekBackward() {
                    ViewHolder viewHolder = ViewHolder.this;
                    if (!viewHolder.startSeek()) {
                        return false;
                    }
                    viewHolder.updateProgressInSeek(false);
                    return true;
                }

                @Override // androidx.leanback.widget.SeekBar.AccessibilitySeekListener
                public boolean onAccessibilitySeekForward() {
                    ViewHolder viewHolder = ViewHolder.this;
                    if (!viewHolder.startSeek()) {
                        return false;
                    }
                    viewHolder.updateProgressInSeek(true);
                    return true;
                }
            });
            seekBar.setMax(Integer.MAX_VALUE);
            this.mControlsDock = (ViewGroup) view.findViewById(R.id.controls_dock);
            this.mSecondaryControlsDock = (ViewGroup) view.findViewById(R.id.secondary_controls_dock);
            Presenter.ViewHolder viewHolderOnCreateViewHolder = presenter == null ? null : presenter.onCreateViewHolder(viewGroup);
            this.mDescriptionViewHolder = viewHolderOnCreateViewHolder;
            if (viewHolderOnCreateViewHolder != null) {
                viewGroup.addView(viewHolderOnCreateViewHolder.view);
            }
            this.mThumbsBar = (ThumbsBar) view.findViewById(R.id.thumbs_row);
        }

        public final void dispatchItemSelection() {
            if (isSelected()) {
                if (this.mSelectedViewHolder == null) {
                    if (getOnItemViewSelectedListener() != null) {
                        getOnItemViewSelectedListener().onItemSelected(null, null, this, getRow());
                    }
                } else if (getOnItemViewSelectedListener() != null) {
                    getOnItemViewSelectedListener().onItemSelected(this.mSelectedViewHolder, this.mSelectedItem, this, getRow());
                }
            }
        }

        public final TextView getCurrentPositionView() {
            return this.mCurrentTime;
        }

        public final Presenter.ViewHolder getDescriptionViewHolder() {
            return this.mDescriptionViewHolder;
        }

        public final TextView getDurationView() {
            return this.mTotalTime;
        }

        public final Presenter getPresenter(boolean z) {
            ObjectAdapter primaryActionsAdapter = z ? ((PlaybackControlsRow) getRow()).getPrimaryActionsAdapter() : ((PlaybackControlsRow) getRow()).getSecondaryActionsAdapter();
            if (primaryActionsAdapter == null) {
                return null;
            }
            if (primaryActionsAdapter.getPresenterSelector() instanceof ControlButtonPresenterSelector) {
                return ((ControlButtonPresenterSelector) primaryActionsAdapter.getPresenterSelector()).getSecondaryPresenter();
            }
            return primaryActionsAdapter.getPresenter(primaryActionsAdapter.size() > 0 ? primaryActionsAdapter.get(0) : null);
        }

        public final void setCurrentPosition(long j) {
            if (j != this.mCurrentTimeInMs) {
                this.mCurrentTimeInMs = j;
                if (this.mCurrentTime != null) {
                    PlaybackTransportRowPresenter.formatTime(j, this.mTempBuilder);
                    this.mCurrentTime.setText(this.mTempBuilder.toString());
                }
            }
            if (this.mInSeek) {
                return;
            }
            long j2 = this.mTotalTimeInMs;
            this.mProgressBar.setProgress(j2 > 0 ? (int) ((this.mCurrentTimeInMs / j2) * 2.147483647E9d) : 0);
        }

        @Override // androidx.leanback.widget.PlaybackSeekUi
        public void setPlaybackSeekUiClient(PlaybackSeekUi.Client client) {
            this.mSeekClient = client;
        }

        public final void setTotalTime(long j) {
            if (this.mTotalTimeInMs != j) {
                this.mTotalTimeInMs = j;
                if (this.mTotalTime != null) {
                    PlaybackTransportRowPresenter.formatTime(j, this.mTempBuilder);
                    this.mTotalTime.setText(this.mTempBuilder.toString());
                }
            }
        }

        public final boolean startSeek() {
            if (this.mInSeek) {
                return true;
            }
            PlaybackSeekUi.Client client = this.mSeekClient;
            if (client == null || !client.isSeekEnabled() || this.mTotalTimeInMs <= 0) {
                return false;
            }
            this.mInSeek = true;
            this.mSeekClient.onSeekStarted();
            PlaybackSeekDataProvider playbackSeekDataProvider = this.mSeekClient.getPlaybackSeekDataProvider();
            this.mSeekDataProvider = playbackSeekDataProvider;
            long[] seekPositions = playbackSeekDataProvider != null ? playbackSeekDataProvider.getSeekPositions() : null;
            this.mPositions = seekPositions;
            if (seekPositions != null) {
                int iBinarySearch = Arrays.binarySearch(seekPositions, this.mTotalTimeInMs);
                if (iBinarySearch >= 0) {
                    this.mPositionsLength = iBinarySearch + 1;
                } else {
                    this.mPositionsLength = (-1) - iBinarySearch;
                }
            } else {
                this.mPositionsLength = 0;
            }
            this.mControlsVh.view.setVisibility(8);
            this.mSecondaryControlsVh.view.setVisibility(4);
            this.mDescriptionViewHolder.view.setVisibility(4);
            this.mThumbsBar.setVisibility(0);
            return true;
        }

        public final void stopSeek(boolean z) {
            if (this.mInSeek) {
                this.mInSeek = false;
                this.mSeekClient.onSeekFinished(z);
                PlaybackSeekDataProvider playbackSeekDataProvider = this.mSeekDataProvider;
                if (playbackSeekDataProvider != null) {
                    playbackSeekDataProvider.reset();
                }
                this.mThumbHeroIndex = -1;
                this.mThumbsBar.clearThumbBitmaps();
                this.mSeekDataProvider = null;
                this.mPositions = null;
                this.mPositionsLength = 0;
                this.mControlsVh.view.setVisibility(0);
                this.mSecondaryControlsVh.view.setVisibility(0);
                this.mDescriptionViewHolder.view.setVisibility(0);
                this.mThumbsBar.setVisibility(4);
            }
        }

        /* JADX WARN: Code duplicated, block: B:25:0x004e  */
        /* JADX WARN: Code duplicated, block: B:53:0x00d2 A[ADDED_TO_REGION, LOOP:0: B:53:0x00d2->B:54:0x00d4, LOOP_START, PHI: r10
  0x00d2: PHI (r10v12 int) = (r10v11 int), (r10v13 int) binds: [B:52:0x00d0, B:54:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:54:0x00d4 A[LOOP:0: B:53:0x00d2->B:54:0x00d4, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:55:0x00de A[ADDED_TO_REGION, LOOP:3: B:55:0x00de->B:56:0x00e0, LOOP_START, PHI: r9
  0x00de: PHI (r9v9 int) = (r9v8 int), (r9v10 int) binds: [B:52:0x00d0, B:56:0x00e0] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:56:0x00e0 A[LOOP:3: B:55:0x00de->B:56:0x00e0, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:59:0x00f2 A[LOOP:1: B:57:0x00ea->B:59:0x00f2, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:62:0x00ff A[LOOP:2: B:61:0x00fd->B:62:0x00ff, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:79:0x00fa A[EDGE_INSN: B:79:0x00fa->B:60:0x00fa BREAK  A[LOOP:1: B:57:0x00ea->B:59:0x00f2], SYNTHETIC] */
        public final void updateProgressInSeek(boolean z) {
            int iMin;
            int i;
            int iMax;
            int i2;
            int i3;
            long j = this.mCurrentTimeInMs;
            int i4 = this.mPositionsLength;
            long j2 = 0;
            if (i4 > 0) {
                int i5 = 0;
                int iBinarySearch = Arrays.binarySearch(this.mPositions, 0, i4, j);
                if (z) {
                    if (iBinarySearch < 0) {
                        iBinarySearch = (-1) - iBinarySearch;
                        if (iBinarySearch <= this.mPositionsLength - 1) {
                            j2 = this.mPositions[iBinarySearch];
                        } else {
                            long j3 = this.mTotalTimeInMs;
                            iBinarySearch = iBinarySearch > 0 ? iBinarySearch - 1 : 0;
                            j2 = j3;
                        }
                    } else if (iBinarySearch < this.mPositionsLength - 1) {
                        iBinarySearch++;
                        j2 = this.mPositions[iBinarySearch];
                    } else {
                        j2 = this.mTotalTimeInMs;
                    }
                } else if (iBinarySearch < 0) {
                    int i6 = (-1) - iBinarySearch;
                    if (i6 > 0) {
                        iBinarySearch = i6 - 1;
                        j2 = this.mPositions[iBinarySearch];
                    } else {
                        iBinarySearch = 0;
                    }
                } else if (iBinarySearch > 0) {
                    iBinarySearch--;
                    j2 = this.mPositions[iBinarySearch];
                } else {
                    iBinarySearch = 0;
                }
                if (this.mThumbHeroIndex != iBinarySearch) {
                    int childCount = this.mThumbsBar.getChildCount();
                    if (childCount < 0 || (childCount & 1) == 0) {
                        throw new RuntimeException();
                    }
                    int i7 = childCount / 2;
                    int iMax2 = Math.max(iBinarySearch - i7, 0);
                    int iMin2 = Math.min(iBinarySearch + i7, this.mPositionsLength - 1);
                    int i8 = this.mThumbHeroIndex;
                    if (i8 < 0) {
                        iMax = iMax2;
                    } else {
                        z = iBinarySearch > i8;
                        int iMax3 = Math.max(i8 - i7, 0);
                        int iMin3 = Math.min(this.mThumbHeroIndex + i7, this.mPositionsLength - 1);
                        if (z) {
                            iMax = Math.max(iMin3 + 1, iMax2);
                            for (int i9 = iMax2; i9 <= iMax - 1; i9++) {
                                ThumbsBar thumbsBar = this.mThumbsBar;
                                thumbsBar.setThumbBitmap((i9 - iBinarySearch) + i7, thumbsBar.getThumbBitmap((i9 - this.mThumbHeroIndex) + i7));
                            }
                        } else {
                            iMin = Math.min(iMax3 - 1, iMin2);
                            for (int i10 = iMin2; i10 >= iMin + 1; i10--) {
                                ThumbsBar thumbsBar2 = this.mThumbsBar;
                                thumbsBar2.setThumbBitmap((i10 - iBinarySearch) + i7, thumbsBar2.getThumbBitmap((i10 - this.mThumbHeroIndex) + i7));
                            }
                            i = iMax2;
                        }
                        this.mThumbHeroIndex = iBinarySearch;
                        if (z) {
                            while (i <= iMin) {
                                this.mSeekDataProvider.getThumbnail(i, this.mThumbResult);
                                i++;
                            }
                        } else {
                            while (iMin >= i) {
                                this.mSeekDataProvider.getThumbnail(iMin, this.mThumbResult);
                                iMin--;
                            }
                        }
                        while (true) {
                            i2 = this.mThumbHeroIndex;
                            if (i5 < (i7 - i2) + iMax2) {
                                break;
                            }
                            this.mThumbsBar.setThumbBitmap(i5, null);
                            i5++;
                        }
                        for (i3 = ((i7 + iMin2) - i2) + 1; i3 < childCount; i3++) {
                            this.mThumbsBar.setThumbBitmap(i3, null);
                        }
                    }
                    i = iMax;
                    iMin = iMin2;
                    this.mThumbHeroIndex = iBinarySearch;
                    if (z) {
                        while (i <= iMin) {
                            this.mSeekDataProvider.getThumbnail(i, this.mThumbResult);
                            i++;
                        }
                    } else {
                        while (iMin >= i) {
                            this.mSeekDataProvider.getThumbnail(iMin, this.mThumbResult);
                            iMin--;
                        }
                    }
                    while (true) {
                        i2 = this.mThumbHeroIndex;
                        if (i5 < (i7 - i2) + iMax2) {
                            break;
                            break;
                        } else {
                            this.mThumbsBar.setThumbBitmap(i5, null);
                            i5++;
                        }
                    }
                    while (i3 < childCount) {
                        this.mThumbsBar.setThumbBitmap(i3, null);
                    }
                }
            } else {
                long defaultSeekIncrement = (long) (PlaybackTransportRowPresenter.this.getDefaultSeekIncrement() * this.mTotalTimeInMs);
                if (!z) {
                    defaultSeekIncrement = -defaultSeekIncrement;
                }
                long j4 = j + defaultSeekIncrement;
                long j5 = this.mTotalTimeInMs;
                if (j4 > j5) {
                    j2 = j5;
                } else if (j4 >= 0) {
                    j2 = j4;
                }
            }
            this.mProgressBar.setProgress((int) ((j2 / this.mTotalTimeInMs) * 2.147483647E9d));
            this.mSeekClient.onSeekPositionChanged(j2);
        }
    }

    public PlaybackTransportRowPresenter() {
        ControlBarPresenter.OnControlSelectedListener onControlSelectedListener = new ControlBarPresenter.OnControlSelectedListener() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.1
            @Override // androidx.leanback.widget.ControlBarPresenter.OnControlSelectedListener
            public void onControlSelected(Presenter.ViewHolder viewHolder, Object obj, ControlBarPresenter.BoundData boundData) {
                ViewHolder viewHolder2 = ((BoundData) boundData).mRowViewHolder;
                if (viewHolder2.mSelectedViewHolder == viewHolder && viewHolder2.mSelectedItem == obj) {
                    return;
                }
                viewHolder2.mSelectedViewHolder = viewHolder;
                viewHolder2.mSelectedItem = obj;
                viewHolder2.dispatchItemSelection();
            }
        };
        this.mOnControlSelectedListener = onControlSelectedListener;
        ControlBarPresenter.OnControlClickedListener onControlClickedListener = new ControlBarPresenter.OnControlClickedListener() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.2
            @Override // androidx.leanback.widget.ControlBarPresenter.OnControlClickedListener
            public void onControlClicked(Presenter.ViewHolder viewHolder, Object obj, ControlBarPresenter.BoundData boundData) {
                RowPresenter.ViewHolder viewHolder2 = ((BoundData) boundData).mRowViewHolder;
                if (viewHolder2.getOnItemViewClickedListener() != null) {
                    viewHolder2.getOnItemViewClickedListener().onItemClicked(viewHolder, obj, viewHolder2, viewHolder2.getRow());
                }
                OnActionClickedListener onActionClickedListener = PlaybackTransportRowPresenter.this.mOnActionClickedListener;
                if (onActionClickedListener == null || !(obj instanceof Action)) {
                    return;
                }
                onActionClickedListener.onActionClicked((Action) obj);
            }
        };
        this.mOnControlClickedListener = onControlClickedListener;
        setHeaderPresenter(null);
        setSelectEffectEnabled(false);
        int i = R.layout.lb_control_bar;
        ControlBarPresenter controlBarPresenter = new ControlBarPresenter(i);
        this.mPlaybackControlsPresenter = controlBarPresenter;
        controlBarPresenter.mDefaultFocusToMiddle = false;
        ControlBarPresenter controlBarPresenter2 = new ControlBarPresenter(i);
        this.mSecondaryControlsPresenter = controlBarPresenter2;
        controlBarPresenter2.mDefaultFocusToMiddle = false;
        this.mPlaybackControlsPresenter.setOnControlSelectedListener(onControlSelectedListener);
        this.mSecondaryControlsPresenter.setOnControlSelectedListener(onControlSelectedListener);
        this.mPlaybackControlsPresenter.setOnControlClickedListener(onControlClickedListener);
        this.mSecondaryControlsPresenter.setOnControlClickedListener(onControlClickedListener);
    }

    public static void formatTime(long j, StringBuilder sb) {
        sb.setLength(0);
        if (j < 0) {
            sb.append("--");
            return;
        }
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j3 / 60;
        long j5 = j2 - (j3 * 60);
        long j6 = j3 - (60 * j4);
        if (j4 > 0) {
            sb.append(j4);
            sb.append(':');
            if (j6 < 10) {
                sb.append('0');
            }
        }
        sb.append(j6);
        sb.append(':');
        if (j5 < 10) {
            sb.append('0');
        }
        sb.append(j5);
    }

    private static int getDefaultProgressColor(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(R.attr.playbackProgressPrimaryColor, typedValue, true) ? context.getResources().getColor(typedValue.resourceId) : context.getResources().getColor(R.color.lb_playback_progress_color_no_theme);
    }

    private static int getDefaultSecondaryProgressColor(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(R.attr.playbackProgressSecondaryColor, typedValue, true) ? context.getResources().getColor(typedValue.resourceId) : context.getResources().getColor(R.color.lb_playback_progress_secondary_color_no_theme);
    }

    private void initRow(final ViewHolder viewHolder) {
        viewHolder.mControlsVh = (ControlBarPresenter.ViewHolder) this.mPlaybackControlsPresenter.onCreateViewHolder(viewHolder.mControlsDock);
        viewHolder.mProgressBar.setProgressColor(this.mProgressColorSet ? this.mProgressColor : getDefaultProgressColor(viewHolder.mControlsDock.getContext()));
        viewHolder.mProgressBar.setSecondaryProgressColor(this.mSecondaryProgressColorSet ? this.mSecondaryProgressColor : getDefaultSecondaryProgressColor(viewHolder.mControlsDock.getContext()));
        viewHolder.mControlsDock.addView(viewHolder.mControlsVh.view);
        ControlBarPresenter.ViewHolder viewHolder2 = (ControlBarPresenter.ViewHolder) this.mSecondaryControlsPresenter.onCreateViewHolder(viewHolder.mSecondaryControlsDock);
        viewHolder.mSecondaryControlsVh = viewHolder2;
        viewHolder.mSecondaryControlsDock.addView(viewHolder2.view);
        ((PlaybackTransportRowView) viewHolder.view.findViewById(R.id.transport_row)).setOnUnhandledKeyListener(new PlaybackTransportRowView.OnUnhandledKeyListener() { // from class: androidx.leanback.widget.PlaybackTransportRowPresenter.3
            @Override // androidx.leanback.widget.PlaybackTransportRowView.OnUnhandledKeyListener
            public boolean onUnhandledKey(KeyEvent keyEvent) {
                return viewHolder.getOnKeyListener() != null && viewHolder.getOnKeyListener().onKey(viewHolder.view, keyEvent.getKeyCode(), keyEvent);
            }
        });
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_playback_transport_controls_row, viewGroup, false), this.mDescriptionPresenter);
        initRow(viewHolder);
        return viewHolder;
    }

    public float getDefaultSeekIncrement() {
        return this.mDefaultSeekIncrement;
    }

    public OnActionClickedListener getOnActionClickedListener() {
        return this.mOnActionClickedListener;
    }

    @ColorInt
    public int getProgressColor() {
        return this.mProgressColor;
    }

    @ColorInt
    public int getSecondaryProgressColor() {
        return this.mSecondaryProgressColor;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        PlaybackControlsRow playbackControlsRow = (PlaybackControlsRow) viewHolder2.getRow();
        if (playbackControlsRow.getItem() == null) {
            viewHolder2.mDescriptionDock.setVisibility(8);
        } else {
            viewHolder2.mDescriptionDock.setVisibility(0);
            Presenter.ViewHolder viewHolder3 = viewHolder2.mDescriptionViewHolder;
            if (viewHolder3 != null) {
                this.mDescriptionPresenter.onBindViewHolder(viewHolder3, playbackControlsRow.getItem());
            }
        }
        if (playbackControlsRow.getImageDrawable() == null) {
            viewHolder2.mImageView.setVisibility(8);
        } else {
            viewHolder2.mImageView.setVisibility(0);
        }
        viewHolder2.mImageView.setImageDrawable(playbackControlsRow.getImageDrawable());
        viewHolder2.mControlsBoundData.adapter = playbackControlsRow.getPrimaryActionsAdapter();
        viewHolder2.mControlsBoundData.presenter = viewHolder2.getPresenter(true);
        BoundData boundData = viewHolder2.mControlsBoundData;
        boundData.mRowViewHolder = viewHolder2;
        this.mPlaybackControlsPresenter.onBindViewHolder(viewHolder2.mControlsVh, boundData);
        viewHolder2.mSecondaryBoundData.adapter = playbackControlsRow.getSecondaryActionsAdapter();
        viewHolder2.mSecondaryBoundData.presenter = viewHolder2.getPresenter(false);
        BoundData boundData2 = viewHolder2.mSecondaryBoundData;
        boundData2.mRowViewHolder = viewHolder2;
        this.mSecondaryControlsPresenter.onBindViewHolder(viewHolder2.mSecondaryControlsVh, boundData2);
        viewHolder2.setTotalTime(playbackControlsRow.getDuration());
        viewHolder2.setCurrentPosition(playbackControlsRow.getCurrentPosition());
        viewHolder2.mProgressBar.setSecondaryProgress((int) ((playbackControlsRow.getBufferedPosition() / viewHolder2.mTotalTimeInMs) * 2.147483647E9d));
        playbackControlsRow.setOnPlaybackProgressChangedListener(viewHolder2.mListener);
    }

    @Override // androidx.leanback.widget.PlaybackRowPresenter
    public void onReappear(RowPresenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (viewHolder2.view.hasFocus()) {
            viewHolder2.mProgressBar.requestFocus();
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewAttachedToWindow(RowPresenter.ViewHolder viewHolder) {
        super.onRowViewAttachedToWindow(viewHolder);
        Presenter presenter = this.mDescriptionPresenter;
        if (presenter != null) {
            presenter.onViewAttachedToWindow(((ViewHolder) viewHolder).mDescriptionViewHolder);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewDetachedFromWindow(RowPresenter.ViewHolder viewHolder) {
        super.onRowViewDetachedFromWindow(viewHolder);
        Presenter presenter = this.mDescriptionPresenter;
        if (presenter != null) {
            presenter.onViewDetachedFromWindow(((ViewHolder) viewHolder).mDescriptionViewHolder);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewSelected(RowPresenter.ViewHolder viewHolder, boolean z) {
        super.onRowViewSelected(viewHolder, z);
        if (z) {
            ((ViewHolder) viewHolder).dispatchItemSelection();
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public void onUnbindRowViewHolder(RowPresenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        PlaybackControlsRow playbackControlsRow = (PlaybackControlsRow) viewHolder2.getRow();
        Presenter.ViewHolder viewHolder3 = viewHolder2.mDescriptionViewHolder;
        if (viewHolder3 != null) {
            this.mDescriptionPresenter.onUnbindViewHolder(viewHolder3);
        }
        this.mPlaybackControlsPresenter.onUnbindViewHolder(viewHolder2.mControlsVh);
        this.mSecondaryControlsPresenter.onUnbindViewHolder(viewHolder2.mSecondaryControlsVh);
        playbackControlsRow.setOnPlaybackProgressChangedListener(null);
        super.onUnbindRowViewHolder(viewHolder);
    }

    public void setDefaultSeekIncrement(float f) {
        this.mDefaultSeekIncrement = f;
    }

    public void setDescriptionPresenter(Presenter presenter) {
        this.mDescriptionPresenter = presenter;
    }

    public void setOnActionClickedListener(OnActionClickedListener onActionClickedListener) {
        this.mOnActionClickedListener = onActionClickedListener;
    }

    public void setProgressColor(@ColorInt int i) {
        this.mProgressColor = i;
        this.mProgressColorSet = true;
    }

    public void setSecondaryProgressColor(@ColorInt int i) {
        this.mSecondaryProgressColor = i;
        this.mSecondaryProgressColorSet = true;
    }
}
