package androidx.media;

import android.content.Context;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(28)
class MediaSessionManagerImplApi28 extends MediaSessionManagerImplApi21 {
    public MediaSessionManagerImplApi28(Context context) {
        super(context);
    }

    @Override // androidx.media.MediaSessionManagerImplApi21, androidx.media.MediaSessionManagerImplBase, androidx.media.MediaSessionManager.MediaSessionManagerImpl
    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        return super.isTrustedForMediaControl(remoteUserInfoImpl);
    }

    @RequiresApi(28)
    public static final class RemoteUserInfoImplApi28 extends MediaSessionManagerImplBase.RemoteUserInfoImplBase {
        public RemoteUserInfoImplApi28(String str, int i, int i2) {
            super(str, i, i2);
            new android.media.session.MediaSessionManager.RemoteUserInfo(str, i, i2);
        }

        public static String getPackageName(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            return remoteUserInfo.getPackageName();
        }

        public RemoteUserInfoImplApi28(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            super(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
        }
    }
}
