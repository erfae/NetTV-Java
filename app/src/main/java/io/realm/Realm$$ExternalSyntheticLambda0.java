package io.realm;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.SurfaceView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.wav.WavExtractor;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.MediaParserChunkExtractor;
import com.google.android.exoplayer2.source.hls.HlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor;
import com.google.android.exoplayer2.source.hls.MediaParserHlsMediaChunkExtractor;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.util.DebugViewProvider;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.material.textfield.TextInputLayout;
import com.nettv.livestore.activities.AddGroupActivity;
import com.nettv.livestore.activities.SearchActivity;
import com.nettv.livestore.activities.SeriesInfoActivity;
import com.nettv.livestore.activities.SettingActivity;
import com.nettv.livestore.dlgfragment.ClearHistoryDlgFragment;
import com.nettv.livestore.dlgfragment.HideCategoryDlgFragment;
import com.nettv.livestore.helper.RealmChangeItemListener;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsSharedRealm;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class Realm$$ExternalSyntheticLambda0 implements ExtractorsFactory, Id3Decoder.FramePredicate, Bundleable.Creator, ChunkExtractor.Factory, HlsExtractorFactory, CacheKeyFactory, DebugViewProvider, TextInputLayout.LengthCounter, RealmChangeItemListener, Response.ErrorListener, HideCategoryDlgFragment.OnCategoryChanged, Realm.Transaction, ObjectServerFacade.RealmCacheAccessor, ObjectServerFacade.RealmInstanceFactory {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$1 = new Realm$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$2 = new Realm$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$3 = new Realm$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$4 = new Realm$$ExternalSyntheticLambda0(4);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$5 = new Realm$$ExternalSyntheticLambda0(5);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$6 = new Realm$$ExternalSyntheticLambda0(6);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$7 = new Realm$$ExternalSyntheticLambda0(7);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$8 = new Realm$$ExternalSyntheticLambda0(8);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$9 = new Realm$$ExternalSyntheticLambda0(9);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$10 = new Realm$$ExternalSyntheticLambda0(10);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$11 = new Realm$$ExternalSyntheticLambda0(11);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$12 = new Realm$$ExternalSyntheticLambda0(12);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$13 = new Realm$$ExternalSyntheticLambda0(13);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$14 = new Realm$$ExternalSyntheticLambda0(14);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$15 = new Realm$$ExternalSyntheticLambda0(15);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$16 = new Realm$$ExternalSyntheticLambda0(16);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$17 = new Realm$$ExternalSyntheticLambda0(17);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$18 = new Realm$$ExternalSyntheticLambda0(18);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$19 = new Realm$$ExternalSyntheticLambda0(19);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$20 = new Realm$$ExternalSyntheticLambda0(20);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$21 = new Realm$$ExternalSyntheticLambda0(21);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$22 = new Realm$$ExternalSyntheticLambda0(22);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$23 = new Realm$$ExternalSyntheticLambda0(23);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$24 = new Realm$$ExternalSyntheticLambda0(24);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$25 = new Realm$$ExternalSyntheticLambda0(25);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$26 = new Realm$$ExternalSyntheticLambda0(26);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$27 = new Realm$$ExternalSyntheticLambda0(27);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE = new Realm$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$28 = new Realm$$ExternalSyntheticLambda0(28);

    public /* synthetic */ Realm$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.nettv.livestore.dlgfragment.HideCategoryDlgFragment.OnCategoryChanged
    public final void CategoryChanged() {
        SettingActivity.lambda$showHideCategoryDlgFragment$4();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.CacheKeyFactory
    public final String buildCacheKey(DataSpec dataSpec) {
        return CacheKeyFactory.CC.lambda$static$0(dataSpec);
    }

    @Override // com.google.android.material.textfield.TextInputLayout.LengthCounter
    public final int countLength(Editable editable) {
        return TextInputLayout.lambda$new$0(editable);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public final HlsMediaChunkExtractor createExtractor(Uri uri, Format format, List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput, PlayerId playerId) {
        return MediaParserHlsMediaChunkExtractor.lambda$static$0(uri, format, list, timestampAdjuster, map, extractorInput, playerId);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        return WavExtractor.lambda$static$0();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
        return createExtractors();
    }

    @Override // io.realm.internal.ObjectServerFacade.RealmInstanceFactory
    public final Realm createInstance(OsSharedRealm osSharedRealm) {
        return Realm.createInstance(osSharedRealm);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
    public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput, PlayerId playerId) {
        switch (this.$r8$classId) {
            case 5:
                return BundledChunkExtractor.lambda$static$0(i, format, z, list, trackOutput, playerId);
            default:
                return MediaParserChunkExtractor.lambda$static$0(i, format, z, list, trackOutput, playerId);
        }
    }

    @Override // io.realm.internal.ObjectServerFacade.RealmCacheAccessor
    public final Realm createRealmOrGetFromCache(RealmConfiguration realmConfiguration, OsSharedRealm.VersionID versionID) {
        return Realm.lambda$initializeRealm$0(realmConfiguration, versionID);
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        return Id3Decoder.lambda$static$0(i, i2, i3, i4, i5);
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 21:
                realm.deleteAll();
                break;
            case 22:
                realm.deleteAll();
                break;
            default:
                realm.deleteAll();
                break;
        }
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        switch (this.$r8$classId) {
            case 3:
                return AdPlaybackState.fromBundle(bundle);
            case 4:
                return AdPlaybackState.AdGroup.fromBundle(bundle);
            case 8:
                return Cue.fromBundle(bundle);
            case 9:
                return CueGroup.fromBundle(bundle);
            case 12:
                return ColorInfo.lambda$static$0(bundle);
            default:
                return VideoSize.lambda$static$0(bundle);
        }
    }

    @Override // com.google.android.exoplayer2.util.DebugViewProvider
    public final SurfaceView getDebugPreviewSurfaceView(int i, int i2) {
        return DebugViewProvider.CC.lambda$static$0(i, i2);
    }

    @Override // com.android.volley.Response.ErrorListener
    public final void onErrorResponse(VolleyError volleyError) {
        SeriesInfoActivity.lambda$getSeriesInfo$1(volleyError);
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        switch (this.$r8$classId) {
            case 15:
                AddGroupActivity.lambda$showAddChannelDlgFragment$5();
                break;
            case 16:
                AddGroupActivity.lambda$showAddChannelDlgFragment$6();
                break;
            case 17:
                SearchActivity.lambda$searchModels$0();
                break;
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            default:
                ClearHistoryDlgFragment.lambda$clearRecentSeriesFromRealm$2();
                break;
            case 20:
                SettingActivity.lambda$ClearHistoryChannels$5();
                break;
            case 24:
                ClearHistoryDlgFragment.lambda$setProToVod$3();
                break;
            case 25:
                ClearHistoryDlgFragment.lambda$clearRecentMoviesFromRealm$1();
                break;
            case 26:
                ClearHistoryDlgFragment.lambda$setRecentSeries$4();
                break;
        }
    }
}
