package com.google.android.exoplayer2.source;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.IdentityHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class ConcatenatingMediaSource2 extends CompositeMediaSource<Integer> {
    private static final int MSG_UPDATE_TIMELINE = 0;
    private final MediaItem mediaItem;
    private final IdentityHashMap<MediaPeriod, MediaSourceHolder> mediaSourceByMediaPeriod;
    private final ImmutableList<MediaSourceHolder> mediaSourceHolders;

    @Nullable
    private Handler playbackThreadHandler;
    private boolean timelineUpdateScheduled;

    public static final class Builder {
        private int index;

        @Nullable
        private MediaItem mediaItem;

        @Nullable
        private MediaSource.Factory mediaSourceFactory;
        private final ImmutableList.Builder<MediaSourceHolder> mediaSourceHoldersBuilder = ImmutableList.builder();

        @CanIgnoreReturnValue
        public Builder add(MediaItem mediaItem) {
            return add(mediaItem, C.TIME_UNSET);
        }

        public ConcatenatingMediaSource2 build() {
            Assertions.checkArgument(this.index > 0, "Must add at least one source to the concatenation.");
            if (this.mediaItem == null) {
                this.mediaItem = MediaItem.fromUri(Uri.EMPTY);
            }
            return new ConcatenatingMediaSource2(this.mediaItem, this.mediaSourceHoldersBuilder.build());
        }

        @CanIgnoreReturnValue
        public Builder setMediaItem(MediaItem mediaItem) {
            this.mediaItem = mediaItem;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMediaSourceFactory(MediaSource.Factory factory) {
            this.mediaSourceFactory = (MediaSource.Factory) Assertions.checkNotNull(factory);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder useDefaultMediaSourceFactory(Context context) {
            return setMediaSourceFactory(new DefaultMediaSourceFactory(context));
        }

        @CanIgnoreReturnValue
        public Builder add(MediaItem mediaItem, long j) {
            Assertions.checkNotNull(mediaItem);
            Assertions.checkStateNotNull(this.mediaSourceFactory, "Must use useDefaultMediaSourceFactory or setMediaSourceFactory first.");
            return add(this.mediaSourceFactory.createMediaSource(mediaItem), j);
        }

        @CanIgnoreReturnValue
        public Builder add(MediaSource mediaSource) {
            return add(mediaSource, C.TIME_UNSET);
        }

        @CanIgnoreReturnValue
        public Builder add(MediaSource mediaSource, long j) {
            Assertions.checkNotNull(mediaSource);
            Assertions.checkState(((mediaSource instanceof ProgressiveMediaSource) && j == C.TIME_UNSET) ? false : true, "Progressive media source must define an initial placeholder duration.");
            ImmutableList.Builder<MediaSourceHolder> builder = this.mediaSourceHoldersBuilder;
            int i = this.index;
            this.index = i + 1;
            builder.add(new MediaSourceHolder(mediaSource, i, Util.msToUs(j)));
            return this;
        }
    }

    public static final class ConcatenatedTimeline extends Timeline {
        private final long defaultPositionUs;
        private final long durationUs;
        private final ImmutableList<Integer> firstPeriodIndices;
        private final boolean isDynamic;
        private final boolean isSeekable;

        @Nullable
        private final Object manifest;
        private final MediaItem mediaItem;
        private final ImmutableList<Long> periodOffsetsInWindowUs;
        private final ImmutableList<Timeline> timelines;

        public ConcatenatedTimeline(MediaItem mediaItem, ImmutableList<Timeline> immutableList, ImmutableList<Integer> immutableList2, ImmutableList<Long> immutableList3, boolean z, boolean z2, long j, long j2, @Nullable Object obj) {
            this.mediaItem = mediaItem;
            this.timelines = immutableList;
            this.firstPeriodIndices = immutableList2;
            this.periodOffsetsInWindowUs = immutableList3;
            this.isSeekable = z;
            this.isDynamic = z2;
            this.durationUs = j;
            this.defaultPositionUs = j2;
            this.manifest = obj;
        }

        private int getChildIndexByPeriodIndex(int i) {
            return Util.binarySearchFloor((List<? extends Comparable<? super Integer>>) this.firstPeriodIndices, Integer.valueOf(i + 1), false, false);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public final int getIndexOfPeriod(Object obj) {
            if (!(obj instanceof Pair) || !(((Pair) obj).first instanceof Integer)) {
                return -1;
            }
            int childIndex = ConcatenatingMediaSource2.getChildIndex(obj);
            int indexOfPeriod = this.timelines.get(childIndex).getIndexOfPeriod(ConcatenatingMediaSource2.getChildPeriodUid(obj));
            if (indexOfPeriod == -1) {
                return -1;
            }
            return this.firstPeriodIndices.get(childIndex).intValue() + indexOfPeriod;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public final Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i);
            this.timelines.get(childIndexByPeriodIndex).getPeriod(i - this.firstPeriodIndices.get(childIndexByPeriodIndex).intValue(), period, z);
            period.windowIndex = 0;
            period.positionInWindowUs = this.periodOffsetsInWindowUs.get(i).longValue();
            if (z) {
                period.uid = ConcatenatingMediaSource2.getPeriodUid(childIndexByPeriodIndex, Assertions.checkNotNull(period.uid));
            }
            return period;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public final Timeline.Period getPeriodByUid(Object obj, Timeline.Period period) {
            int childIndex = ConcatenatingMediaSource2.getChildIndex(obj);
            Object childPeriodUid = ConcatenatingMediaSource2.getChildPeriodUid(obj);
            Timeline timeline = this.timelines.get(childIndex);
            int indexOfPeriod = timeline.getIndexOfPeriod(childPeriodUid) + this.firstPeriodIndices.get(childIndex).intValue();
            timeline.getPeriodByUid(childPeriodUid, period);
            period.windowIndex = 0;
            period.positionInWindowUs = this.periodOffsetsInWindowUs.get(indexOfPeriod).longValue();
            period.uid = obj;
            return period;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.periodOffsetsInWindowUs.size();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public final Object getUidOfPeriod(int i) {
            int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i);
            return ConcatenatingMediaSource2.getPeriodUid(childIndexByPeriodIndex, this.timelines.get(childIndexByPeriodIndex).getUidOfPeriod(i - this.firstPeriodIndices.get(childIndexByPeriodIndex).intValue()));
        }

        @Override // com.google.android.exoplayer2.Timeline
        public final Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            return window.set(Timeline.Window.SINGLE_WINDOW_UID, this.mediaItem, this.manifest, C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, this.isSeekable, this.isDynamic, null, this.defaultPositionUs, this.durationUs, 0, getPeriodCount() - 1, -this.periodOffsetsInWindowUs.get(0).longValue());
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 1;
        }
    }

    public static final class MediaSourceHolder {
        public int activeMediaPeriods;
        public final int index;
        public final long initialPlaceholderDurationUs;
        public final MaskingMediaSource mediaSource;

        public MediaSourceHolder(MediaSource mediaSource, int i, long j) {
            this.mediaSource = new MaskingMediaSource(mediaSource, false);
            this.index = i;
            this.initialPlaceholderDurationUs = j;
        }
    }

    private void disableUnusedMediaSources() {
        for (int i = 0; i < this.mediaSourceHolders.size(); i++) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(i);
            if (mediaSourceHolder.activeMediaPeriods == 0) {
                disableChildSource(Integer.valueOf(mediaSourceHolder.index));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getChildIndex(Object obj) {
        return ((Integer) ((Pair) obj).first).intValue();
    }

    private static int getChildIndexFromChildWindowSequenceNumber(long j, int i) {
        return (int) (j % ((long) i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object getChildPeriodUid(Object obj) {
        return ((Pair) obj).second;
    }

    private static long getChildWindowSequenceNumber(long j, int i, int i2) {
        return (j * ((long) i)) + ((long) i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object getPeriodUid(int i, Object obj) {
        return Pair.create(Integer.valueOf(i), obj);
    }

    private static long getWindowSequenceNumberFromChildWindowSequenceNumber(long j, int i) {
        return j / ((long) i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            return true;
        }
        updateTimeline();
        return true;
    }

    @Nullable
    private ConcatenatedTimeline maybeCreateConcatenatedTimeline() {
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        ImmutableList.Builder builder = ImmutableList.builder();
        ImmutableList.Builder builder2 = ImmutableList.builder();
        ImmutableList.Builder builder3 = ImmutableList.builder();
        boolean z = true;
        int i = 0;
        boolean z2 = true;
        Object obj = null;
        int periodCount = 0;
        long j = 0;
        boolean z3 = true;
        boolean z4 = false;
        long j2 = 0;
        long j3 = 0;
        boolean z5 = false;
        while (i < this.mediaSourceHolders.size()) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(i);
            Timeline timeline = mediaSourceHolder.mediaSource.getTimeline();
            Assertions.checkArgument(timeline.isEmpty() ^ z, "Can't concatenate empty child Timeline.");
            builder.add(timeline);
            builder2.add(Integer.valueOf(periodCount));
            periodCount += timeline.getPeriodCount();
            int i2 = 0;
            while (i2 < timeline.getWindowCount()) {
                timeline.getWindow(i2, window);
                if (!z5) {
                    obj = window.manifest;
                    z5 = true;
                }
                z2 = z2 && Util.areEqual(obj, window.manifest);
                long j4 = window.durationUs;
                if (j4 == C.TIME_UNSET) {
                    j4 = mediaSourceHolder.initialPlaceholderDurationUs;
                    if (j4 == C.TIME_UNSET) {
                        return null;
                    }
                }
                j2 += j4;
                if (mediaSourceHolder.index == 0 && i2 == 0) {
                    j3 = window.defaultPositionUs;
                    j = -window.positionInFirstPeriodUs;
                } else {
                    Assertions.checkArgument(window.positionInFirstPeriodUs == 0, "Can't concatenate windows. A window has a non-zero offset in a period.");
                }
                z3 &= window.isSeekable || window.isPlaceholder;
                z4 |= window.isDynamic;
                i2++;
                timeline = timeline;
                i = i;
            }
            Timeline timeline2 = timeline;
            int i3 = i;
            int periodCount2 = timeline2.getPeriodCount();
            int i4 = 0;
            while (i4 < periodCount2) {
                builder3.add(Long.valueOf(j));
                Timeline timeline3 = timeline2;
                timeline3.getPeriod(i4, period);
                long j5 = period.durationUs;
                if (j5 == C.TIME_UNSET) {
                    Assertions.checkArgument(periodCount2 == 1, "Can't concatenate multiple periods with unknown duration in one window.");
                    long j6 = window.durationUs;
                    if (j6 == C.TIME_UNSET) {
                        j6 = mediaSourceHolder.initialPlaceholderDurationUs;
                    }
                    j5 = j6 + window.positionInFirstPeriodUs;
                }
                j += j5;
                i4++;
                builder = builder;
                period = period;
                timeline2 = timeline3;
            }
            i = i3 + 1;
            z = true;
        }
        return new ConcatenatedTimeline(this.mediaItem, builder.build(), builder2.build(), builder3.build(), z3, z4, j2, j3, z2 ? obj : null);
    }

    private void scheduleTimelineUpdate() {
        if (this.timelineUpdateScheduled) {
            return;
        }
        ((Handler) Assertions.checkNotNull(this.playbackThreadHandler)).obtainMessage(0).sendToTarget();
        this.timelineUpdateScheduled = true;
    }

    private void updateTimeline() {
        this.timelineUpdateScheduled = false;
        ConcatenatedTimeline concatenatedTimelineMaybeCreateConcatenatedTimeline = maybeCreateConcatenatedTimeline();
        if (concatenatedTimelineMaybeCreateConcatenatedTimeline != null) {
            refreshSourceInfo(concatenatedTimelineMaybeCreateConcatenatedTimeline);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(getChildIndex(mediaPeriodId.periodUid));
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithWindowSequenceNumber = mediaPeriodId.copyWithPeriodUid(getChildPeriodUid(mediaPeriodId.periodUid)).copyWithWindowSequenceNumber(getChildWindowSequenceNumber(mediaPeriodId.windowSequenceNumber, this.mediaSourceHolders.size(), mediaSourceHolder.index));
        enableChildSource(Integer.valueOf(mediaSourceHolder.index));
        mediaSourceHolder.activeMediaPeriods++;
        MaskingMediaPeriod maskingMediaPeriodCreatePeriod = mediaSourceHolder.mediaSource.createPeriod(mediaPeriodIdCopyWithWindowSequenceNumber, allocator, j);
        this.mediaSourceByMediaPeriod.put(maskingMediaPeriodCreatePeriod, mediaSourceHolder);
        disableUnusedMediaSources();
        return maskingMediaPeriodCreatePeriod;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public final void enableInternal() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource, com.google.android.exoplayer2.source.MediaSource
    @Nullable
    public Timeline getInitialTimeline() {
        return maybeCreateConcatenatedTimeline();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    @Nullable
    public final MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Integer num, MediaSource.MediaPeriodId mediaPeriodId) {
        Integer num2 = num;
        if (num2.intValue() != getChildIndexFromChildWindowSequenceNumber(mediaPeriodId.windowSequenceNumber, this.mediaSourceHolders.size())) {
            return null;
        }
        return mediaPeriodId.copyWithPeriodUid(getPeriodUid(num2.intValue(), mediaPeriodId.periodUid)).copyWithWindowSequenceNumber(getWindowSequenceNumberFromChildWindowSequenceNumber(mediaPeriodId.windowSequenceNumber, this.mediaSourceHolders.size()));
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public final /* bridge */ /* synthetic */ int getWindowIndexForChildWindowIndex(Integer num, int i) {
        return 0;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    /* JADX INFO: renamed from: onChildSourceInfoRefreshed */
    public final void lambda$prepareChildSource$0(Integer num, MediaSource mediaSource, Timeline timeline) {
        scheduleTimelineUpdate();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        this.playbackThreadHandler = new Handler(new ConcatenatingMediaSource$$ExternalSyntheticLambda0(this, 1));
        for (int i = 0; i < this.mediaSourceHolders.size(); i++) {
            prepareChildSource(Integer.valueOf(i), this.mediaSourceHolders.get(i).mediaSource);
        }
        scheduleTimelineUpdate();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) Assertions.checkNotNull(this.mediaSourceByMediaPeriod.remove(mediaPeriod));
        mediaSourceHolder.mediaSource.releasePeriod(mediaPeriod);
        mediaSourceHolder.activeMediaPeriods--;
        if (this.mediaSourceByMediaPeriod.isEmpty()) {
            return;
        }
        disableUnusedMediaSources();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public final void releaseSourceInternal() {
        super.releaseSourceInternal();
        Handler handler = this.playbackThreadHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.playbackThreadHandler = null;
        }
        this.timelineUpdateScheduled = false;
    }

    private ConcatenatingMediaSource2(MediaItem mediaItem, ImmutableList<MediaSourceHolder> immutableList) {
        this.mediaItem = mediaItem;
        this.mediaSourceHolders = immutableList;
        this.mediaSourceByMediaPeriod = new IdentityHashMap<>();
    }
}
