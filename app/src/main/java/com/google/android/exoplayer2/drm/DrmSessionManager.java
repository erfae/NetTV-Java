package com.google.android.exoplayer2.drm;

import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.state.Transition$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.analytics.PlayerId;

/* JADX INFO: loaded from: classes.dex */
public interface DrmSessionManager {
    public static final DrmSessionManager DRM_UNSUPPORTED;

    @Deprecated
    public static final DrmSessionManager DUMMY;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.drm.DrmSessionManager$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static DrmSessionReference $default$preacquireSession(DrmSessionManager drmSessionManager, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
            return DrmSessionReference.EMPTY;
        }

        public static void $default$prepare(DrmSessionManager drmSessionManager) {
        }

        public static void $default$release(DrmSessionManager drmSessionManager) {
        }

        @Deprecated
        public static DrmSessionManager getDummyDrmSessionManager() {
            return DrmSessionManager.DRM_UNSUPPORTED;
        }
    }

    public interface DrmSessionReference {
        public static final DrmSessionReference EMPTY = Transition$$ExternalSyntheticLambda0.INSTANCE$12;

        /* JADX INFO: renamed from: com.google.android.exoplayer2.drm.DrmSessionManager$DrmSessionReference$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            static {
                DrmSessionReference drmSessionReference = DrmSessionReference.EMPTY;
            }

            public static /* synthetic */ void lambda$static$0() {
            }
        }

        void release();
    }

    static {
        DrmSessionManager drmSessionManager = new DrmSessionManager() { // from class: com.google.android.exoplayer2.drm.DrmSessionManager.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            @Nullable
            public DrmSession acquireSession(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
                if (format.drmInitData == null) {
                    return null;
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(new UnsupportedDrmException(1), PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED));
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public int getCryptoType(Format format) {
                return format.drmInitData != null ? 1 : 0;
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public final /* synthetic */ DrmSessionReference preacquireSession(DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
                return CC.$default$preacquireSession(this, eventDispatcher, format);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public final /* synthetic */ void prepare() {
                CC.$default$prepare(this);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public final /* synthetic */ void release() {
                CC.$default$release(this);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public void setPlayer(Looper looper, PlayerId playerId) {
            }
        };
        DRM_UNSUPPORTED = drmSessionManager;
        DUMMY = drmSessionManager;
    }

    @Nullable
    DrmSession acquireSession(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    int getCryptoType(Format format);

    DrmSessionReference preacquireSession(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    void prepare();

    void release();

    void setPlayer(Looper looper, PlayerId playerId);
}
