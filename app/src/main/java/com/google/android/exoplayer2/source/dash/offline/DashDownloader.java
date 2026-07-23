package com.google.android.exoplayer2.source.dash.offline;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.offline.DownloadException;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.source.dash.BaseUrlExclusionList;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.DashUtil;
import com.google.android.exoplayer2.source.dash.DashWrappingSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.source.hls.offline.HlsDownloader$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class DashDownloader extends SegmentDownloader<DashManifest> {
    private final BaseUrlExclusionList baseUrlExclusionList;

    public DashDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, HlsDownloader$$ExternalSyntheticLambda0.INSTANCE$1);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00bb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x00b7 A[SYNTHETIC] */
    private void addSegmentsForAdaptationSet(DataSource dataSource, AdaptationSet adaptationSet, long j, long j2, boolean z, ArrayList<SegmentDownloader.Segment> arrayList) throws InterruptedException, IOException {
        for (int i = 0; i < adaptationSet.representations.size(); i++) {
            Representation representation = adaptationSet.representations.get(i);
            try {
                try {
                    DashSegmentIndex segmentIndex = getSegmentIndex(dataSource, adaptationSet.type, representation, z);
                    if (segmentIndex != null) {
                        long segmentCount = segmentIndex.getSegmentCount(j2);
                        if (segmentCount == -1) {
                            throw new DownloadException("Unbounded segment index");
                        }
                        String str = ((BaseUrl) Util.castNonNull(this.baseUrlExclusionList.selectBaseUrl(representation.baseUrls))).url;
                        RangedUri initializationUri = representation.getInitializationUri();
                        if (initializationUri != null) {
                            arrayList.add(createSegment(representation, str, j, initializationUri));
                        }
                        RangedUri indexUri = representation.getIndexUri();
                        if (indexUri != null) {
                            arrayList.add(createSegment(representation, str, j, indexUri));
                        }
                        long firstSegmentNum = segmentIndex.getFirstSegmentNum();
                        long j3 = (firstSegmentNum + segmentCount) - 1;
                        for (long j4 = firstSegmentNum; j4 <= j3; j4++) {
                            arrayList.add(createSegment(representation, str, segmentIndex.getTimeUs(j4) + j, segmentIndex.getSegmentUrl(j4)));
                        }
                    } else {
                        try {
                            throw new DownloadException("Missing segment index");
                        } catch (IOException e) {
                            e = e;
                            if (z) {
                                throw e;
                            }
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    if (z) {
                        throw e;
                    }
                }
            } catch (IOException e3) {
                e = e3;
            }
        }
    }

    private SegmentDownloader.Segment createSegment(Representation representation, String str, long j, RangedUri rangedUri) {
        return new SegmentDownloader.Segment(j, DashUtil.buildDataSpec(representation, str, rangedUri, 0));
    }

    @Nullable
    private DashSegmentIndex getSegmentIndex(final DataSource dataSource, final int i, final Representation representation, boolean z) throws InterruptedException, IOException {
        DashSegmentIndex index = representation.getIndex();
        if (index != null) {
            return index;
        }
        ChunkIndex chunkIndex = (ChunkIndex) execute(new RunnableFutureTask<ChunkIndex, IOException>() { // from class: com.google.android.exoplayer2.source.dash.offline.DashDownloader.1
            @Override // com.google.android.exoplayer2.util.RunnableFutureTask
            public final ChunkIndex doWork() throws Exception {
                return DashUtil.loadChunkIndex(dataSource, i, representation);
            }
        }, z);
        if (chunkIndex == null) {
            return null;
        }
        return new DashWrappingSegmentIndex(chunkIndex, representation.presentationTimeOffsetUs);
    }

    @Override // com.google.android.exoplayer2.offline.SegmentDownloader
    public final List getSegments(DataSource dataSource, FilterableManifest filterableManifest, boolean z) throws InterruptedException, IOException {
        DashManifest dashManifest = (DashManifest) filterableManifest;
        ArrayList<SegmentDownloader.Segment> arrayList = new ArrayList<>();
        for (int i = 0; i < dashManifest.getPeriodCount(); i++) {
            Period period = dashManifest.getPeriod(i);
            long jMsToUs = Util.msToUs(period.startMs);
            long periodDurationUs = dashManifest.getPeriodDurationUs(i);
            int i2 = 0;
            for (List<AdaptationSet> list = period.adaptationSets; i2 < list.size(); list = list) {
                addSegmentsForAdaptationSet(dataSource, list.get(i2), jMsToUs, periodDurationUs, z, arrayList);
                i2++;
            }
        }
        return arrayList;
    }

    public DashDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this(mediaItem, new DashManifestParser(), factory, executor, 20000L);
    }

    @Deprecated
    public DashDownloader(MediaItem mediaItem, ParsingLoadable.Parser<DashManifest> parser, CacheDataSource.Factory factory, Executor executor) {
        this(mediaItem, parser, factory, executor, 20000L);
    }

    public DashDownloader(MediaItem mediaItem, ParsingLoadable.Parser<DashManifest> parser, CacheDataSource.Factory factory, Executor executor, long j) {
        super(mediaItem, parser, factory, executor, j);
        this.baseUrlExclusionList = new BaseUrlExclusionList();
    }
}
