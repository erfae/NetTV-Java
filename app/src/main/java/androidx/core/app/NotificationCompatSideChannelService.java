package androidx.core.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationCompatSideChannelService extends Service {

    public class NotificationSideChannelStub extends INotificationSideChannel.Stub {
        public final /* synthetic */ NotificationCompatSideChannelService this$0;

        @Override // android.support.v4.app.INotificationSideChannel
        public void cancel(String str, int i, String str2) throws RemoteException {
            this.this$0.checkPermission(Binder.getCallingUid(), str);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.this$0.cancel(str, i, str2);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.support.v4.app.INotificationSideChannel
        public void cancelAll(String str) {
            this.this$0.checkPermission(Binder.getCallingUid(), str);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.this$0.cancelAll(str);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.support.v4.app.INotificationSideChannel
        public void notify(String str, int i, String str2, Notification notification) throws RemoteException {
            this.this$0.checkPermission(Binder.getCallingUid(), str);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.this$0.notify(str, i, str2, notification);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    public abstract void cancel(String str, int i, String str2);

    public abstract void cancelAll(String str);

    public final void checkPermission(int i, String str) {
        for (String str2 : getPackageManager().getPackagesForUid(i)) {
            if (str2.equals(str)) {
                return;
            }
        }
        throw new SecurityException("NotificationSideChannelService: Uid " + i + " is not authorized for package " + str);
    }

    public abstract void notify(String str, int i, String str2, Notification notification);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        intent.getAction().equals(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL);
        return null;
    }
}
