package androidx.constraintlayout.core.state;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.amr.AmrExtractor;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.extractor.flv.FlvExtractor;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.util.Consumer;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.ListenerSet;
import java.lang.reflect.Constructor;
import java.util.Map;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Transition$$ExternalSyntheticLambda0 implements Interpolator, ListenerSet.IterationFinishedEvent, Bundleable.Creator, Consumer, DrmSessionManager.DrmSessionReference, DefaultExtractorsFactory.ExtensionLoader.ConstructorSupplier, ExtractorsFactory, Id3Decoder.FramePredicate {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE = new Transition$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$1 = new Transition$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$2 = new Transition$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$3 = new Transition$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$4 = new Transition$$ExternalSyntheticLambda0(4);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$5 = new Transition$$ExternalSyntheticLambda0(5);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$6 = new Transition$$ExternalSyntheticLambda0(6);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$7 = new Transition$$ExternalSyntheticLambda0(7);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$8 = new Transition$$ExternalSyntheticLambda0(8);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$9 = new Transition$$ExternalSyntheticLambda0(9);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$10 = new Transition$$ExternalSyntheticLambda0(10);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$11 = new Transition$$ExternalSyntheticLambda0(11);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$12 = new Transition$$ExternalSyntheticLambda0(12);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$13 = new Transition$$ExternalSyntheticLambda0(13);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$14 = new Transition$$ExternalSyntheticLambda0(14);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$15 = new Transition$$ExternalSyntheticLambda0(15);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$16 = new Transition$$ExternalSyntheticLambda0(16);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$17 = new Transition$$ExternalSyntheticLambda0(17);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$18 = new Transition$$ExternalSyntheticLambda0(18);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$19 = new Transition$$ExternalSyntheticLambda0(19);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$20 = new Transition$$ExternalSyntheticLambda0(20);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$21 = new Transition$$ExternalSyntheticLambda0(21);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$22 = new Transition$$ExternalSyntheticLambda0(22);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$23 = new Transition$$ExternalSyntheticLambda0(23);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$24 = new Transition$$ExternalSyntheticLambda0(24);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$25 = new Transition$$ExternalSyntheticLambda0(25);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$26 = new Transition$$ExternalSyntheticLambda0(26);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$27 = new Transition$$ExternalSyntheticLambda0(27);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$28 = new Transition$$ExternalSyntheticLambda0(28);
    public static final /* synthetic */ Transition$$ExternalSyntheticLambda0 INSTANCE$29 = new Transition$$ExternalSyntheticLambda0(29);

    public /* synthetic */ Transition$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.exoplayer2.util.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 9:
                ((DrmSessionEventListener.EventDispatcher) obj).drmKeysRemoved();
                break;
            case 10:
                ((DrmSessionEventListener.EventDispatcher) obj).drmKeysLoaded();
                break;
            default:
                ((DrmSessionEventListener.EventDispatcher) obj).drmKeysRestored();
                break;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        switch (this.$r8$classId) {
            case 15:
                return ExtractorsFactory.CC.lambda$static$0();
            case 16:
                return AmrExtractor.lambda$static$0();
            case 17:
                return FlacExtractor.lambda$static$0();
            case 18:
                return FlvExtractor.lambda$static$0();
            case 19:
                return MatroskaExtractor.lambda$static$0();
            case 20:
                return Mp3Extractor.lambda$static$0();
            case 21:
            default:
                return TsExtractor.lambda$static$0();
            case 22:
                return FragmentedMp4Extractor.lambda$static$0();
            case 23:
                return Mp4Extractor.lambda$static$0();
            case 24:
                return OggExtractor.lambda$static$0();
            case 25:
                return Ac3Extractor.lambda$static$0();
            case 26:
                return Ac4Extractor.lambda$static$0();
            case 27:
                return AdtsExtractor.lambda$static$0();
            case 28:
                return PsExtractor.lambda$static$0();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
        switch (this.$r8$classId) {
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;
            case 18:
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
            default:
                break;
            case 22:
                break;
            case 23:
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                break;
            case 28:
                break;
        }
        return createExtractors();
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        return Mp3Extractor.lambda$static$1(i, i2, i3, i4, i5);
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        return AudioAttributes.lambda$static$0(bundle);
    }

    @Override // com.google.android.exoplayer2.extractor.DefaultExtractorsFactory.ExtensionLoader.ConstructorSupplier
    public final Constructor getConstructor() {
        switch (this.$r8$classId) {
            case 13:
                return DefaultExtractorsFactory.getFlacExtractorConstructor();
            default:
                return DefaultExtractorsFactory.getMidiExtractorConstructor();
        }
    }

    @Override // androidx.constraintlayout.core.state.Interpolator
    public final float getInterpolation(float f) {
        switch (this.$r8$classId) {
            case 0:
                return Transition.lambda$getInterpolator$1(f);
            case 1:
                return Transition.lambda$getInterpolator$2(f);
            case 2:
                return Transition.lambda$getInterpolator$3(f);
            case 3:
                return Transition.lambda$getInterpolator$4(f);
            case 4:
                return Transition.lambda$getInterpolator$5(f);
            case 5:
                return Transition.lambda$getInterpolator$6(f);
            default:
                return Transition.lambda$getInterpolator$7(f);
        }
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
    public final void invoke(Object obj, FlagSet flagSet) {
        DefaultAnalyticsCollector.lambda$new$0((AnalyticsListener) obj, flagSet);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager.DrmSessionReference
    public final void release() {
        DrmSessionManager.DrmSessionReference.CC.lambda$static$0();
    }
}
