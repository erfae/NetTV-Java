package androidx.core.graphics;

import android.graphics.Canvas;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.SimpleBasePlayer;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.play.core.internal.zzag;
import com.google.android.play.core.tasks.zzi;
import java.util.HashMap;
import kotlin.jvm.internal.InlineMarker;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Insets$$ExternalSyntheticOutline0 implements TrackSelector.InvalidationListener {
    public static final /* synthetic */ Insets$$ExternalSyntheticOutline0 INSTANCE = new Insets$$ExternalSyntheticOutline0();

    public static float m(float f, float f2, float f3, float f4) {
        return ((f - f2) * f3) + f4;
    }

    public static int m(String str, int i) {
        return String.valueOf(str).length() + i;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
    public void onTrackSelectionsInvalidated() {
        DownloadHelper.lambda$new$2();
    }

    public static int m(String str, int i, int i2) {
        return (str.hashCode() + i) * i2;
    }

    public static zzi m(zzag zzagVar, String str, Object[] objArr) {
        zzagVar.zzd(str, objArr);
        return new zzi();
    }

    public static Integer m(HashMap map, Integer num, String str, int i, String str2) {
        map.put(num, str);
        Integer numValueOf = Integer.valueOf(i);
        map.put(numValueOf, str2);
        return numValueOf;
    }

    public static String m(int i, String str, int i2) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(i2);
        return sb.toString();
    }

    public static String m(RecyclerView recyclerView, StringBuilder sb) {
        sb.append(recyclerView.exceptionLabel());
        return sb.toString();
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static String m23m(String str, int i) {
        return str + i;
    }

    public static String m(String str, int i, String str2) {
        return str + i + str2;
    }

    public static String m(String str, long j) {
        return str + j;
    }

    public static String m(String str, Fragment fragment, String str2) {
        return str + fragment + str2;
    }

    public static String m(String str, String str2) {
        return str + str2;
    }

    public static String m(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String m(StringBuilder sb, int i, char c) {
        sb.append(i);
        sb.append(c);
        return sb.toString();
    }

    public static String m(StringBuilder sb, int i, String str) {
        sb.append(i);
        sb.append(str);
        return sb.toString();
    }

    public static String m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static String m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    public static String m(StringBuilder sb, String str, String str2, String str3, String str4) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb.toString();
    }

    public static StringBuilder m(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static StringBuilder m24m(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(i);
        sb.append(str2);
        return sb;
    }

    public static StringBuilder m(String str, int i, String str2, int i2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(i);
        sb.append(str2);
        sb.append(i2);
        sb.append(str3);
        return sb;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static StringBuilder m25m(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static StringBuilder m26m(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb;
    }

    public static void m(int i, Canvas canvas, int i2, int i3) {
        InlineMarker.finallyStart(i);
        canvas.restoreToCount(i2);
        InlineMarker.finallyEnd(i3);
    }

    public static void m(int i, HashMap map, String str, int i2, String str2, int i3, String str3, int i4, String str4) {
        map.put(str, Integer.valueOf(i));
        map.put(str2, Integer.valueOf(i2));
        map.put(str3, Integer.valueOf(i3));
        map.put(str4, Integer.valueOf(i4));
    }

    public static void m(final SimpleBasePlayer.State state, final int i, ListenerSet listenerSet, int i2) {
        listenerSet.queueEvent(i2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.SimpleBasePlayer$$ExternalSyntheticLambda1
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                switch (i) {
                    case 0:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$28(state, (Player.Listener) obj);
                        break;
                    case 1:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$35(state, (Player.Listener) obj);
                        break;
                    case 2:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$36(state, (Player.Listener) obj);
                        break;
                    case 3:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$37(state, (Player.Listener) obj);
                        break;
                    case 4:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$38(state, (Player.Listener) obj);
                        break;
                    case 5:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$39(state, (Player.Listener) obj);
                        break;
                    case 6:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$40(state, (Player.Listener) obj);
                        break;
                    case 7:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$41(state, (Player.Listener) obj);
                        break;
                    case 8:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$42(state, (Player.Listener) obj);
                        break;
                    case 9:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$43(state, (Player.Listener) obj);
                        break;
                    case 10:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$44(state, (Player.Listener) obj);
                        break;
                    case 11:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$45(state, (Player.Listener) obj);
                        break;
                    case 12:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$46(state, (Player.Listener) obj);
                        break;
                    case 13:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$47(state, (Player.Listener) obj);
                        break;
                    case 14:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$48(state, (Player.Listener) obj);
                        break;
                    case 15:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$49(state, (Player.Listener) obj);
                        break;
                    case 16:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$50(state, (Player.Listener) obj);
                        break;
                    case 17:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$51(state, (Player.Listener) obj);
                        break;
                    case 18:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$52(state, (Player.Listener) obj);
                        break;
                    case 19:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$53(state, (Player.Listener) obj);
                        break;
                    case 20:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$54(state, (Player.Listener) obj);
                        break;
                    case 21:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$29(state, (Player.Listener) obj);
                        break;
                    case 22:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$30(state, (Player.Listener) obj);
                        break;
                    case 23:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$33(state, (Player.Listener) obj);
                        break;
                    default:
                        SimpleBasePlayer.lambda$updateStateAndInformListeners$34(state, (Player.Listener) obj);
                        break;
                }
            }
        });
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static void m27m(String str, int i, String str2) {
        Log.w(str2, str + i);
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static void m28m(String str, String str2, String str3) {
        Log.w(str3, str + str2);
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static void m29m(StringBuilder sb, String str, String str2, String str3, String str4) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
    }
}
