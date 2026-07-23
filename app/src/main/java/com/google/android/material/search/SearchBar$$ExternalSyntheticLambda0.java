package com.google.android.material.search;

import android.animation.Animator;
import android.net.Uri;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.ProgressiveDownloader;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.material.animation.AnimatableView;
import java.util.Map;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchBar$$ExternalSyntheticLambda0 implements RequirementsWatcher.Listener, CacheWriter.ProgressListener, ExtractorsFactory, ProgressiveMediaExtractor.Factory, AccessibilityManagerCompat.TouchExplorationStateChangeListener, AnimatableView.Listener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SearchBar$$ExternalSyntheticLambda0(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public Extractor[] createExtractors() {
        return DefaultMediaSourceFactory.lambda$createMediaSource$0((Format) this.f$0);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
        return createExtractors();
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
    public ProgressiveMediaExtractor createProgressiveMediaExtractor(PlayerId playerId) {
        return ProgressiveMediaSource.Factory.lambda$new$0((ExtractorsFactory) this.f$0, playerId);
    }

    @Override // com.google.android.material.animation.AnimatableView.Listener
    public void onAnimationEnd() {
        ((Animator) this.f$0).start();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.CacheWriter.ProgressListener
    public void onProgress(long j, long j2, long j3) {
        ((ProgressiveDownloader) this.f$0).onProgress(j, j2, j3);
    }

    @Override // com.google.android.exoplayer2.scheduler.RequirementsWatcher.Listener
    public void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i) {
        ((DownloadManager) this.f$0).onRequirementsStateChanged(requirementsWatcher, i);
    }

    @Override // androidx.core.view.accessibility.AccessibilityManagerCompat.TouchExplorationStateChangeListener
    public void onTouchExplorationStateChanged(boolean z) {
        ((SearchBar) this.f$0).lambda$new$0(z);
    }
}
