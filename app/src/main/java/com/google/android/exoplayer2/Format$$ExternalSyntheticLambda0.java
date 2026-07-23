package com.google.android.exoplayer2;

import android.os.Bundle;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Format$$ExternalSyntheticLambda0 implements ListenerSet.Event, Bundleable.Creator {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$1 = new Format$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$2 = new Format$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$3 = new Format$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$4 = new Format$$ExternalSyntheticLambda0(4);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$5 = new Format$$ExternalSyntheticLambda0(5);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE = new Format$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$6 = new Format$$ExternalSyntheticLambda0(6);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$7 = new Format$$ExternalSyntheticLambda0(7);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$8 = new Format$$ExternalSyntheticLambda0(8);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$9 = new Format$$ExternalSyntheticLambda0(9);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$10 = new Format$$ExternalSyntheticLambda0(10);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$11 = new Format$$ExternalSyntheticLambda0(11);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$12 = new Format$$ExternalSyntheticLambda0(12);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$13 = new Format$$ExternalSyntheticLambda0(13);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$14 = new Format$$ExternalSyntheticLambda0(14);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$15 = new Format$$ExternalSyntheticLambda0(15);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$16 = new Format$$ExternalSyntheticLambda0(16);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$17 = new Format$$ExternalSyntheticLambda0(17);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$18 = new Format$$ExternalSyntheticLambda0(18);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$19 = new Format$$ExternalSyntheticLambda0(19);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$20 = new Format$$ExternalSyntheticLambda0(20);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$21 = new Format$$ExternalSyntheticLambda0(21);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$22 = new Format$$ExternalSyntheticLambda0(22);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$23 = new Format$$ExternalSyntheticLambda0(23);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$24 = new Format$$ExternalSyntheticLambda0(24);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$25 = new Format$$ExternalSyntheticLambda0(25);
    public static final /* synthetic */ Format$$ExternalSyntheticLambda0 INSTANCE$26 = new Format$$ExternalSyntheticLambda0(26);

    public /* synthetic */ Format$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        switch (this.$r8$classId) {
            case 0:
                return Format.fromBundle(bundle);
            case 1:
            case 4:
            case 5:
            case 18:
            case 19:
            default:
                return Tracks.Group.lambda$static$0(bundle);
            case 2:
                return DeviceInfo.lambda$static$0(bundle);
            case 3:
                return ExoPlaybackException.$r8$lambda$mXbXdGG_PHMarv0ObcHmIhB4uIw(bundle);
            case 6:
                return HeartRating.fromBundle(bundle);
            case 7:
                return MediaItem.fromBundle(bundle);
            case 8:
                return MediaItem.ClippingConfiguration.lambda$static$0(bundle);
            case 9:
                return MediaItem.LiveConfiguration.lambda$static$0(bundle);
            case 10:
                return MediaItem.RequestMetadata.lambda$static$0(bundle);
            case 11:
                return MediaMetadata.fromBundle(bundle);
            case 12:
                return PercentageRating.fromBundle(bundle);
            case 13:
                return new PlaybackException(bundle);
            case 14:
                return PlaybackParameters.lambda$static$0(bundle);
            case 15:
                return Player.Commands.fromBundle(bundle);
            case 16:
                return Player.PositionInfo.fromBundle(bundle);
            case 17:
                return Rating.fromBundle(bundle);
            case 20:
                return StarRating.fromBundle(bundle);
            case 21:
                return ThumbRating.fromBundle(bundle);
            case 22:
                return Timeline.fromBundle(bundle);
            case 23:
                return Timeline.Period.fromBundle(bundle);
            case 24:
                return Timeline.Window.fromBundle(bundle);
            case 25:
                return Tracks.lambda$static$0(bundle);
        }
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 1:
                ExoPlayerImpl.lambda$release$5((Player.Listener) obj);
                break;
            case 4:
                ((Player.Listener) obj).onSeekProcessed();
                break;
            case 5:
                ((Player.Listener) obj).onRenderedFirstFrame();
                break;
            case 18:
                ((Player.Listener) obj).onRenderedFirstFrame();
                break;
            default:
                ((Player.Listener) obj).onSeekProcessed();
                break;
        }
    }
}
