package androidx.media;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.core.app.BundleCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.util.Pair;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediaBrowserServiceCompat extends Service {
    public static final boolean DEBUG = Log.isLoggable("MBServiceCompat", 3);
    private static final float EPSILON = 1.0E-5f;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_MEDIA_ITEM = "media_item";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_SEARCH_RESULTS = "search_results";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_ERROR = -1;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_OK = 0;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_PROGRESS_UPDATE = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    public ConnectionRecord mCurConnection;
    private MediaBrowserServiceImpl mImpl;
    public MediaSessionCompat.Token mSession;
    public final ConnectionRecord mConnectionFromFwk = new ConnectionRecord(MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER, -1, -1, null, null);
    public final ArrayList<ConnectionRecord> mPendingConnections = new ArrayList<>();
    public final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap<>();
    public final ServiceHandler mHandler = new ServiceHandler(this);

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";

        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String str, @Nullable Bundle bundle) {
            if (str == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead");
            }
            this.mRootId = str;
            this.mExtras = bundle;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String getRootId() {
            return this.mRootId;
        }
    }

    public class ConnectionRecord implements IBinder.DeathRecipient {
        public final MediaSessionManager.RemoteUserInfo browserInfo;
        public final ServiceCallbacks callbacks;
        public final int pid;
        public final String pkg;
        public BrowserRoot root;
        public final Bundle rootHints;
        public final HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();
        public final int uid;

        public ConnectionRecord(String str, int i, int i2, Bundle bundle, ServiceCallbacks serviceCallbacks) {
            this.pkg = str;
            this.pid = i;
            this.uid = i2;
            this.browserInfo = new MediaSessionManager.RemoteUserInfo(str, i, i2);
            this.rootHints = bundle;
            this.callbacks = serviceCallbacks;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ConnectionRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = ConnectionRecord.this;
                    MediaBrowserServiceCompat.this.mConnections.remove(connectionRecord.callbacks.asBinder());
                }
            });
        }
    }

    public interface MediaBrowserServiceImpl {
        Bundle getBrowserRootHints();

        MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo();

        void notifyChildrenChanged(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle);

        void notifyChildrenChanged(String str, Bundle bundle);

        IBinder onBind(Intent intent);

        void onCreate();

        void setSessionToken(MediaSessionCompat.Token token);
    }

    @RequiresApi(23)
    public class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 {

        public class MediaBrowserServiceApi23 extends MediaBrowserServiceImplApi21.MediaBrowserServiceApi21 {
            public MediaBrowserServiceApi23(Context context) {
                super(context);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadItem(String str, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
                MediaBrowserServiceImplApi23.this.onLoadItem(str, new ResultWrapper<>(result));
            }
        }

        public MediaBrowserServiceImplApi23() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            MediaBrowserServiceApi23 mediaBrowserServiceApi23 = new MediaBrowserServiceApi23(MediaBrowserServiceCompat.this);
            this.mServiceFwk = mediaBrowserServiceApi23;
            mediaBrowserServiceApi23.onCreate();
        }

        public void onLoadItem(String str, final ResultWrapper<Parcel> resultWrapper) {
            Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(str) { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi23.1
                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    resultWrapper.detach();
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public final void onResultSent(@Nullable MediaBrowserCompat.MediaItem mediaItem) {
                    MediaBrowserCompat.MediaItem mediaItem2 = mediaItem;
                    if (mediaItem2 == null) {
                        resultWrapper.sendResult(null);
                        return;
                    }
                    Parcel parcelObtain = Parcel.obtain();
                    mediaItem2.writeToParcel(parcelObtain, 0);
                    resultWrapper.sendResult(parcelObtain);
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            mediaBrowserServiceCompat.onLoadItem(str, result);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }
    }

    @RequiresApi(26)
    public class MediaBrowserServiceImplApi26 extends MediaBrowserServiceImplApi23 {

        public class MediaBrowserServiceApi26 extends MediaBrowserServiceImplApi23.MediaBrowserServiceApi23 {
            public MediaBrowserServiceApi26(Context context) {
                super(context);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                MediaBrowserServiceImplApi26 mediaBrowserServiceImplApi26 = MediaBrowserServiceImplApi26.this;
                MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
                mediaBrowserServiceImplApi26.onLoadChildren(str, new ResultWrapper<>(result), bundle);
                MediaBrowserServiceCompat.this.mCurConnection = null;
            }
        }

        public MediaBrowserServiceImplApi26() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public Bundle getBrowserRootHints() {
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            ConnectionRecord connectionRecord = mediaBrowserServiceCompat.mCurConnection;
            if (connectionRecord == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            }
            if (connectionRecord == mediaBrowserServiceCompat.mConnectionFromFwk) {
                return this.mServiceFwk.getBrowserRootHints();
            }
            if (connectionRecord.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
        public final void notifyChildrenChangedForFramework(String str, Bundle bundle) {
            if (bundle != null) {
                this.mServiceFwk.notifyChildrenChanged(str, bundle);
            } else {
                this.mServiceFwk.notifyChildrenChanged(str);
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi23, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            MediaBrowserServiceApi26 mediaBrowserServiceApi26 = new MediaBrowserServiceApi26(MediaBrowserServiceCompat.this);
            this.mServiceFwk = mediaBrowserServiceApi26;
            mediaBrowserServiceApi26.onCreate();
        }

        public void onLoadChildren(String str, final ResultWrapper<List<Parcel>> resultWrapper, final Bundle bundle) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi26.1
                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    resultWrapper.detach();
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                    List<MediaBrowserCompat.MediaItem> listApplyOptions = list;
                    if (listApplyOptions == null) {
                        resultWrapper.sendResult(null);
                        return;
                    }
                    if ((getFlags() & 1) != 0) {
                        listApplyOptions = MediaBrowserServiceCompat.this.applyOptions(listApplyOptions, bundle);
                    }
                    ArrayList arrayList = new ArrayList(listApplyOptions.size());
                    for (MediaBrowserCompat.MediaItem mediaItem : listApplyOptions) {
                        Parcel parcelObtain = Parcel.obtain();
                        mediaItem.writeToParcel(parcelObtain, 0);
                        arrayList.add(parcelObtain);
                    }
                    resultWrapper.sendResult(arrayList);
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            mediaBrowserServiceCompat.onLoadChildren(str, result, bundle);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }
    }

    @RequiresApi(28)
    public class MediaBrowserServiceImplApi28 extends MediaBrowserServiceImplApi26 {
        public MediaBrowserServiceImplApi28() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            ConnectionRecord connectionRecord = mediaBrowserServiceCompat.mCurConnection;
            if (connectionRecord != null) {
                return connectionRecord == mediaBrowserServiceCompat.mConnectionFromFwk ? new MediaSessionManager.RemoteUserInfo(this.mServiceFwk.getCurrentBrowserInfo()) : connectionRecord.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }
    }

    public class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;
        public final /* synthetic */ MediaBrowserServiceCompat this$0;

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public Bundle getBrowserRootHints() {
            ConnectionRecord connectionRecord = this.this$0.mCurConnection;
            if (connectionRecord == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            }
            if (connectionRecord.rootHints == null) {
                return null;
            }
            return new Bundle(this.this$0.mCurConnection.rootHints);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            ConnectionRecord connectionRecord = this.this$0.mCurConnection;
            if (connectionRecord != null) {
                return connectionRecord.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(@NonNull final String str, final Bundle bundle) {
            this.this$0.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.2
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<IBinder> it = MediaBrowserServiceImplBase.this.this$0.mConnections.keySet().iterator();
                    while (it.hasNext()) {
                        MediaBrowserServiceImplBase.this.notifyChildrenChangedOnHandler(MediaBrowserServiceImplBase.this.this$0.mConnections.get(it.next()), str, bundle);
                    }
                }
            });
        }

        public final void notifyChildrenChangedOnHandler(ConnectionRecord connectionRecord, String str, Bundle bundle) {
            List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(str);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, pair.second)) {
                        this.this$0.performLoadChildren(str, connectionRecord, pair.second, bundle);
                    }
                }
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public IBinder onBind(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.mMessenger.getBinder();
            }
            return null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            this.mMessenger = new Messenger(this.this$0.mHandler);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void setSessionToken(final MediaSessionCompat.Token token) {
            this.this$0.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.1
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<ConnectionRecord> it = MediaBrowserServiceImplBase.this.this$0.mConnections.values().iterator();
                    while (it.hasNext()) {
                        ConnectionRecord next = it.next();
                        try {
                            next.callbacks.onConnect(next.root.getRootId(), token, next.root.getExtras());
                        } catch (RemoteException unused) {
                            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Connection for ");
                            sbM.append(next.pkg);
                            sbM.append(" is no longer valid.");
                            Log.w("MBServiceCompat", sbM.toString());
                            it.remove();
                        }
                    }
                }
            });
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(@NonNull final MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull final String str, final Bundle bundle) {
            this.this$0.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.3
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceImplBase.this.this$0.mConnections.size(); i++) {
                        ConnectionRecord connectionRecordValueAt = MediaBrowserServiceImplBase.this.this$0.mConnections.valueAt(i);
                        if (connectionRecordValueAt.browserInfo.equals(remoteUserInfo)) {
                            MediaBrowserServiceImplBase.this.notifyChildrenChangedOnHandler(connectionRecordValueAt, str, bundle);
                            return;
                        }
                    }
                }
            });
        }
    }

    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendResultCalled;

        public Result(Object obj) {
            this.mDebug = obj;
        }

        private void checkExtraFields(@Nullable Bundle bundle) {
            if (bundle != null && bundle.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float f = bundle.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (f < -1.0E-5f || f > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0]");
                }
            }
        }

        public void detach() {
            if (this.mDetachCalled) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("detach() called when detach() had already been called for: ");
                sbM.append(this.mDebug);
                throw new IllegalStateException(sbM.toString());
            }
            if (this.mSendResultCalled) {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("detach() called when sendResult() had already been called for: ");
                sbM2.append(this.mDebug);
                throw new IllegalStateException(sbM2.toString());
            }
            if (!this.mSendErrorCalled) {
                this.mDetachCalled = true;
            } else {
                StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("detach() called when sendError() had already been called for: ");
                sbM3.append(this.mDebug);
                throw new IllegalStateException(sbM3.toString());
            }
        }

        public final int getFlags() {
            return this.mFlags;
        }

        public final boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled;
        }

        public void onErrorSent(@Nullable Bundle bundle) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("It is not supported to send an error for ");
            sbM.append(this.mDebug);
            throw new UnsupportedOperationException(sbM.toString());
        }

        public void onProgressUpdateSent(@Nullable Bundle bundle) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("It is not supported to send an interim update for ");
            sbM.append(this.mDebug);
            throw new UnsupportedOperationException(sbM.toString());
        }

        public void onResultSent(@Nullable T t) {
        }

        public void sendError(@Nullable Bundle bundle) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("sendError() called when either sendResult() or sendError() had already been called for: ");
                sbM.append(this.mDebug);
                throw new IllegalStateException(sbM.toString());
            }
            this.mSendErrorCalled = true;
            onErrorSent(bundle);
        }

        public void sendProgressUpdate(@Nullable Bundle bundle) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
                sbM.append(this.mDebug);
                throw new IllegalStateException(sbM.toString());
            }
            checkExtraFields(bundle);
            onProgressUpdateSent(bundle);
        }

        public void sendResult(@Nullable T t) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("sendResult() called when either sendResult() or sendError() had already been called for: ");
                sbM.append(this.mDebug);
                throw new IllegalStateException(sbM.toString());
            }
            this.mSendResultCalled = true;
            onResultSent(t);
        }

        public final void setFlags(int i) {
            this.mFlags = i;
        }
    }

    @RequiresApi(21)
    public static class ResultWrapper<T> {
        public MediaBrowserService.Result mResultFwk;

        public ResultWrapper(MediaBrowserService.Result result) {
            this.mResultFwk = result;
        }

        public void detach() {
            this.mResultFwk.detach();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void sendResult(T t) {
            ArrayList arrayList = null;
            if (!(t instanceof List)) {
                if (!(t instanceof Parcel)) {
                    this.mResultFwk.sendResult(null);
                    return;
                }
                Parcel parcel = (Parcel) t;
                parcel.setDataPosition(0);
                this.mResultFwk.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
                return;
            }
            MediaBrowserService.Result result = this.mResultFwk;
            List<Parcel> list = (List) t;
            if (list != null) {
                arrayList = new ArrayList(list.size());
                for (Parcel parcel2 : list) {
                    parcel2.setDataPosition(0);
                    arrayList.add((MediaBrowser.MediaItem) MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel2));
                    parcel2.recycle();
                }
            }
            result.sendResult(arrayList);
        }
    }

    public class ServiceBinderImpl {
        public ServiceBinderImpl() {
        }

        public void addSubscription(final String str, final IBinder iBinder, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("addSubscription for callback that isn't registered id=");
                        sbM.append(str);
                        Log.w("MBServiceCompat", sbM.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str2 = str;
                    IBinder iBinder2 = iBinder;
                    Bundle bundle2 = bundle;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    List<Pair<IBinder, Bundle>> arrayList = connectionRecord.subscriptions.get(str2);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    for (Pair<IBinder, Bundle> pair : arrayList) {
                        if (iBinder2 == pair.first && MediaBrowserCompatUtils.areSameOptions(bundle2, pair.second)) {
                            return;
                        }
                    }
                    arrayList.add(new Pair<>(iBinder2, bundle2));
                    connectionRecord.subscriptions.put(str2, arrayList);
                    mediaBrowserServiceCompat.performLoadChildren(str2, connectionRecord, bundle2, null);
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onSubscribe(str2, bundle2);
                    mediaBrowserServiceCompat.mCurConnection = null;
                }
            });
        }

        public void connect(final String str, final int i, final int i2, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            Objects.requireNonNull(mediaBrowserServiceCompat);
            boolean z = false;
            if (str != null) {
                for (String str2 : mediaBrowserServiceCompat.getPackageManager().getPackagesForUid(i2)) {
                    if (str2.equals(str)) {
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        IBinder iBinderAsBinder = serviceCallbacks.asBinder();
                        MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                        ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.new ConnectionRecord(str, i, i2, bundle, serviceCallbacks);
                        MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
                        mediaBrowserServiceCompat2.mCurConnection = connectionRecord;
                        BrowserRoot browserRootOnGetRoot = mediaBrowserServiceCompat2.onGetRoot(str, i2, bundle);
                        connectionRecord.root = browserRootOnGetRoot;
                        MediaBrowserServiceCompat mediaBrowserServiceCompat3 = MediaBrowserServiceCompat.this;
                        mediaBrowserServiceCompat3.mCurConnection = null;
                        if (browserRootOnGetRoot == null) {
                            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("No root for client ");
                            sbM.append(str);
                            sbM.append(" from service ");
                            sbM.append(getClass().getName());
                            Log.i("MBServiceCompat", sbM.toString());
                            try {
                                serviceCallbacks.onConnectFailed();
                                return;
                            } catch (RemoteException unused) {
                                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Calling onConnectFailed() failed. Ignoring. pkg=");
                                sbM2.append(str);
                                Log.w("MBServiceCompat", sbM2.toString());
                                return;
                            }
                        }
                        try {
                            mediaBrowserServiceCompat3.mConnections.put(iBinderAsBinder, connectionRecord);
                            iBinderAsBinder.linkToDeath(connectionRecord, 0);
                            if (MediaBrowserServiceCompat.this.mSession != null) {
                                serviceCallbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras());
                            }
                        } catch (RemoteException unused2) {
                            StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Calling onConnect() failed. Dropping client. pkg=");
                            sbM3.append(str);
                            Log.w("MBServiceCompat", sbM3.toString());
                            MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + i2 + " package=" + str);
        }

        public void disconnect(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecordRemove = MediaBrowserServiceCompat.this.mConnections.remove(serviceCallbacks.asBinder());
                    if (connectionRecordRemove != null) {
                        connectionRecordRemove.callbacks.asBinder().unlinkToDeath(connectionRecordRemove, 0);
                    }
                }
            });
        }

        public void getMediaItem(final String str, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty(str) || resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.5
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("getMediaItem for callback that isn't registered id=");
                        sbM.append(str);
                        Log.w("MBServiceCompat", sbM.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str2 = str;
                    final ResultReceiver resultReceiver2 = resultReceiver;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(str2) { // from class: androidx.media.MediaBrowserServiceCompat.2
                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onResultSent(@Nullable MediaBrowserCompat.MediaItem mediaItem) {
                            MediaBrowserCompat.MediaItem mediaItem2 = mediaItem;
                            if ((getFlags() & 2) != 0) {
                                resultReceiver2.send(-1, null);
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, mediaItem2);
                            resultReceiver2.send(0, bundle);
                        }
                    };
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onLoadItem(str2, result);
                    mediaBrowserServiceCompat.mCurConnection = null;
                    if (!result.isDone()) {
                        throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("onLoadItem must call detach() or sendResult() before returning for id=", str2));
                    }
                }
            });
        }

        public void registerCallbacks(final ServiceCallbacks serviceCallbacks, final String str, final int i, final int i2, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.6
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord;
                    IBinder iBinderAsBinder = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                    Iterator<ConnectionRecord> it = MediaBrowserServiceCompat.this.mPendingConnections.iterator();
                    while (true) {
                        connectionRecord = null;
                        if (!it.hasNext()) {
                            break;
                        }
                        ConnectionRecord next = it.next();
                        if (next.uid == i2) {
                            connectionRecord = (TextUtils.isEmpty(str) || i <= 0) ? MediaBrowserServiceCompat.this.new ConnectionRecord(next.pkg, next.pid, next.uid, bundle, serviceCallbacks) : null;
                            it.remove();
                            break;
                        }
                    }
                    if (connectionRecord == null) {
                        connectionRecord = MediaBrowserServiceCompat.this.new ConnectionRecord(str, i, i2, bundle, serviceCallbacks);
                    }
                    MediaBrowserServiceCompat.this.mConnections.put(iBinderAsBinder, connectionRecord);
                    try {
                        iBinderAsBinder.linkToDeath(connectionRecord, 0);
                    } catch (RemoteException unused) {
                        Log.w("MBServiceCompat", "IBinder is already dead.");
                    }
                }
            });
        }

        public void removeSubscription(final String str, final IBinder iBinder, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.4
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("removeSubscription for callback that isn't registered id=");
                        sbM.append(str);
                        Log.w("MBServiceCompat", sbM.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str2 = str;
                    IBinder iBinder2 = iBinder;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    boolean z = false;
                    try {
                        if (iBinder2 != null) {
                            List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(str2);
                            if (list != null) {
                                Iterator<Pair<IBinder, Bundle>> it = list.iterator();
                                while (it.hasNext()) {
                                    if (iBinder2 == it.next().first) {
                                        it.remove();
                                        z = true;
                                    }
                                }
                                if (list.size() == 0) {
                                    connectionRecord.subscriptions.remove(str2);
                                }
                            }
                        } else if (connectionRecord.subscriptions.remove(str2) != null) {
                            z = true;
                        }
                        mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                        mediaBrowserServiceCompat.onUnsubscribe(str2);
                        mediaBrowserServiceCompat.mCurConnection = null;
                        if (z) {
                            return;
                        }
                        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("removeSubscription called for ");
                        sbM2.append(str);
                        sbM2.append(" which is not subscribed");
                        Log.w("MBServiceCompat", sbM2.toString());
                    } catch (Throwable th) {
                        mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                        mediaBrowserServiceCompat.onUnsubscribe(str2);
                        mediaBrowserServiceCompat.mCurConnection = null;
                        throw th;
                    }
                }
            });
        }

        public void search(final String str, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty(str) || resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.8
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("search for callback that isn't registered query=");
                        sbM.append(str);
                        Log.w("MBServiceCompat", sbM.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str2 = str;
                    Bundle bundle2 = bundle;
                    final ResultReceiver resultReceiver2 = resultReceiver;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str2) { // from class: androidx.media.MediaBrowserServiceCompat.3
                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                            List<MediaBrowserCompat.MediaItem> list2 = list;
                            if ((getFlags() & 4) != 0 || list2 == null) {
                                resultReceiver2.send(-1, null);
                                return;
                            }
                            Bundle bundle3 = new Bundle();
                            bundle3.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[]) list2.toArray(new MediaBrowserCompat.MediaItem[0]));
                            resultReceiver2.send(0, bundle3);
                        }
                    };
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onSearch(str2, bundle2, result);
                    mediaBrowserServiceCompat.mCurConnection = null;
                    if (!result.isDone()) {
                        throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("onSearch must call detach() or sendResult() before returning for query=", str2));
                    }
                }
            });
        }

        public void sendCustomAction(final String str, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty(str) || resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.9
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("sendCustomAction for callback that isn't registered action=");
                        sbM.append(str);
                        sbM.append(", extras=");
                        sbM.append(bundle);
                        Log.w("MBServiceCompat", sbM.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str2 = str;
                    Bundle bundle2 = bundle;
                    final ResultReceiver resultReceiver2 = resultReceiver;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    Result<Bundle> result = new Result<Bundle>(str2) { // from class: androidx.media.MediaBrowserServiceCompat.4
                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onErrorSent(@Nullable Bundle bundle3) {
                            resultReceiver2.send(-1, bundle3);
                        }

                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onProgressUpdateSent(@Nullable Bundle bundle3) {
                            resultReceiver2.send(1, bundle3);
                        }

                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onResultSent(@Nullable Bundle bundle3) {
                            resultReceiver2.send(0, bundle3);
                        }
                    };
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onCustomAction(str2, bundle2, result);
                    mediaBrowserServiceCompat.mCurConnection = null;
                    if (result.isDone()) {
                        return;
                    }
                    throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str2 + " extras=" + bundle2);
                }
            });
        }

        public void unregisterCallbacks(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.7
                @Override // java.lang.Runnable
                public void run() {
                    IBinder iBinderAsBinder = serviceCallbacks.asBinder();
                    ConnectionRecord connectionRecordRemove = MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                    if (connectionRecordRemove != null) {
                        iBinderAsBinder.unlinkToDeath(connectionRecordRemove, 0);
                    }
                }
            });
        }
    }

    public interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) throws RemoteException;
    }

    public static class ServiceCallbacksCompat implements ServiceCallbacks {
        public final Messenger mCallbacks;

        public ServiceCallbacksCompat(Messenger messenger) {
            this.mCallbacks = messenger;
        }

        private void sendRequest(int i, Bundle bundle) throws RemoteException {
            Message messageObtain = Message.obtain();
            messageObtain.what = i;
            messageObtain.arg1 = 2;
            messageObtain.setData(bundle);
            this.mCallbacks.send(messageObtain);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onConnect(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            bundle2.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, token);
            bundle2.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, bundle);
            sendRequest(1, bundle2);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onLoadChildren(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) throws RemoteException {
            Bundle bundle3 = new Bundle();
            bundle3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            bundle3.putBundle(MediaBrowserProtocol.DATA_OPTIONS, bundle);
            bundle3.putBundle(MediaBrowserProtocol.DATA_NOTIFY_CHILDREN_CHANGED_OPTIONS, bundle2);
            if (list != null) {
                bundle3.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, list instanceof ArrayList ? (ArrayList) list : new ArrayList<>(list));
            }
            sendRequest(3, bundle3);
        }
    }

    public final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl;

        public ServiceHandler(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            this.mServiceBinderImpl = mediaBrowserServiceCompat.new ServiceBinderImpl();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1:
                    Bundle bundle = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(bundle);
                    this.mServiceBinderImpl.connect(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), bundle, new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 2:
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 3:
                    Bundle bundle2 = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                    MediaSessionCompat.ensureClassLoader(bundle2);
                    this.mServiceBinderImpl.addSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), bundle2, new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 4:
                    this.mServiceBinderImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 5:
                    this.mServiceBinderImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 6:
                    Bundle bundle3 = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(bundle3);
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(message.replyTo), data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), bundle3);
                    break;
                case 7:
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 8:
                    Bundle bundle4 = data.getBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(bundle4);
                    this.mServiceBinderImpl.search(data.getString(MediaBrowserProtocol.DATA_SEARCH_QUERY), bundle4, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(message.replyTo));
                    break;
                case 9:
                    Bundle bundle5 = data.getBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(bundle5);
                    this.mServiceBinderImpl.sendCustomAction(data.getString(MediaBrowserProtocol.DATA_CUSTOM_ACTION), bundle5, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(message.replyTo));
                    break;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + message + "\n  Service version: 2\n  Client version: " + message.arg1);
                    break;
            }
        }

        public void postOrRun(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run();
            } else {
                post(runnable);
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message message, long j) {
            Bundle data = message.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            int callingPid = Binder.getCallingPid();
            if (callingPid > 0) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, callingPid);
            } else if (!data.containsKey(MediaBrowserProtocol.DATA_CALLING_PID)) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, -1);
            }
            return super.sendMessageAtTime(message, j);
        }
    }

    public final List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i == -1 && i2 == -1) {
            return list;
        }
        int i3 = i2 * i;
        int size = i3 + i2;
        if (i < 0 || i2 < 1 || i3 >= list.size()) {
            return Collections.emptyList();
        }
        if (size > list.size()) {
            size = list.size();
        }
        return list.subList(i3, size);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void attachToBaseContext(Context context) {
        attachBaseContext(context);
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints();
    }

    @NonNull
    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        return this.mImpl.getCurrentBrowserInfo();
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.mSession;
    }

    public void notifyChildrenChanged(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mImpl.notifyChildrenChanged(str, null);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            this.mImpl = new MediaBrowserServiceImplApi28();
        } else if (i >= 26) {
            this.mImpl = new MediaBrowserServiceImplApi26();
        } else if (i >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23();
        } else {
            this.mImpl = new MediaBrowserServiceImplApi21();
        }
        this.mImpl.onCreate();
    }

    public void onCustomAction(@NonNull String str, Bundle bundle, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result);

    public void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle bundle) {
        result.setFlags(1);
        onLoadChildren(str, result);
    }

    public void onLoadItem(String str, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String str, Bundle bundle, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.setFlags(4);
        result.sendResult(null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onSubscribe(String str, Bundle bundle) {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onUnsubscribe(String str) {
    }

    public final void performLoadChildren(final String str, final ConnectionRecord connectionRecord, final Bundle bundle, final Bundle bundle2) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.1
            @Override // androidx.media.MediaBrowserServiceCompat.Result
            public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                List<MediaBrowserCompat.MediaItem> listApplyOptions = list;
                if (MediaBrowserServiceCompat.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                    if (MediaBrowserServiceCompat.DEBUG) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
                        sbM.append(connectionRecord.pkg);
                        sbM.append(" id=");
                        sbM.append(str);
                        Log.d("MBServiceCompat", sbM.toString());
                        return;
                    }
                    return;
                }
                if ((getFlags() & 1) != 0) {
                    listApplyOptions = MediaBrowserServiceCompat.this.applyOptions(listApplyOptions, bundle);
                }
                try {
                    connectionRecord.callbacks.onLoadChildren(str, listApplyOptions, bundle, bundle2);
                } catch (RemoteException unused) {
                    StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Calling onLoadChildren() failed for id=");
                    sbM2.append(str);
                    sbM2.append(" package=");
                    sbM2.append(connectionRecord.pkg);
                    Log.w("MBServiceCompat", sbM2.toString());
                }
            }
        };
        this.mCurConnection = connectionRecord;
        if (bundle == null) {
            onLoadChildren(str, result);
        } else {
            onLoadChildren(str, result, bundle);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("onLoadChildren must call detach() or sendResult() before returning for package="), connectionRecord.pkg, " id=", str));
        }
    }

    public void setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null");
        }
        if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set");
        }
        this.mSession = token;
        this.mImpl.setSessionToken(token);
    }

    @RequiresApi(21)
    public class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl {
        public Messenger mMessenger;
        public final List<Bundle> mRootExtrasList = new ArrayList();
        public MediaBrowserService mServiceFwk;

        @RequiresApi(21)
        public class MediaBrowserServiceApi21 extends MediaBrowserService {
            public MediaBrowserServiceApi21(Context context) {
                attachBaseContext(context);
            }

            @Override // android.service.media.MediaBrowserService
            @SuppressLint({"SyntheticAccessor"})
            public MediaBrowserService.BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                BrowserRoot browserRootOnGetRoot = MediaBrowserServiceImplApi21.this.onGetRoot(str, i, bundle == null ? null : new Bundle(bundle));
                if (browserRootOnGetRoot == null) {
                    return null;
                }
                return new MediaBrowserService.BrowserRoot(browserRootOnGetRoot.mRootId, browserRootOnGetRoot.mExtras);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
                MediaBrowserServiceImplApi21.this.onLoadChildren(str, new ResultWrapper<>(result));
            }
        }

        public MediaBrowserServiceImplApi21() {
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public Bundle getBrowserRootHints() {
            if (this.mMessenger == null) {
                return null;
            }
            ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mCurConnection;
            if (connectionRecord == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            }
            if (connectionRecord.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mCurConnection;
            if (connectionRecord != null) {
                return connectionRecord.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(final String str, final Bundle bundle) {
            notifyChildrenChangedForFramework(str, bundle);
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.3
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<IBinder> it = MediaBrowserServiceCompat.this.mConnections.keySet().iterator();
                    while (it.hasNext()) {
                        MediaBrowserServiceImplApi21.this.notifyChildrenChangedForCompatOnHandler(MediaBrowserServiceCompat.this.mConnections.get(it.next()), str, bundle);
                    }
                }
            });
        }

        public final void notifyChildrenChangedForCompatOnHandler(ConnectionRecord connectionRecord, String str, Bundle bundle) {
            List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(str);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, pair.second)) {
                        MediaBrowserServiceCompat.this.performLoadChildren(str, connectionRecord, pair.second, bundle);
                    }
                }
            }
        }

        public void notifyChildrenChangedForFramework(String str, Bundle bundle) {
            this.mServiceFwk.notifyChildrenChanged(str);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public IBinder onBind(Intent intent) {
            return this.mServiceFwk.onBind(intent);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            MediaBrowserServiceApi21 mediaBrowserServiceApi21 = new MediaBrowserServiceApi21(MediaBrowserServiceCompat.this);
            this.mServiceFwk = mediaBrowserServiceApi21;
            mediaBrowserServiceApi21.onCreate();
        }

        /* JADX WARN: Type inference failed for: r3v9, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
        public BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
            Bundle extras;
            int i2;
            if (bundle == null || bundle.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 0) == 0) {
                extras = null;
                i2 = -1;
            } else {
                bundle.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION);
                this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
                extras = new Bundle();
                extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
                BundleCompat.putBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
                MediaSessionCompat.Token token = MediaBrowserServiceCompat.this.mSession;
                if (token != null) {
                    IMediaSession extraBinder = token.getExtraBinder();
                    BundleCompat.putBinder(extras, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder == null ? null : extraBinder.asBinder());
                } else {
                    this.mRootExtrasList.add(extras);
                }
                int i3 = bundle.getInt(MediaBrowserProtocol.EXTRA_CALLING_PID, -1);
                bundle.remove(MediaBrowserProtocol.EXTRA_CALLING_PID);
                i2 = i3;
            }
            ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.new ConnectionRecord(str, i2, i, bundle, null);
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = connectionRecord;
            BrowserRoot browserRootOnGetRoot = mediaBrowserServiceCompat.onGetRoot(str, i, bundle);
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat2.mCurConnection = null;
            if (browserRootOnGetRoot == null) {
                return null;
            }
            if (this.mMessenger != null) {
                mediaBrowserServiceCompat2.mPendingConnections.add(connectionRecord);
            }
            if (extras == null) {
                extras = browserRootOnGetRoot.getExtras();
            } else if (browserRootOnGetRoot.getExtras() != null) {
                extras.putAll(browserRootOnGetRoot.getExtras());
            }
            return new BrowserRoot(browserRootOnGetRoot.getRootId(), extras);
        }

        public void onLoadChildren(String str, final ResultWrapper<List<Parcel>> resultWrapper) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.2
                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    resultWrapper.detach();
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                    ArrayList arrayList;
                    List<MediaBrowserCompat.MediaItem> list2 = list;
                    if (list2 != null) {
                        arrayList = new ArrayList(list2.size());
                        for (MediaBrowserCompat.MediaItem mediaItem : list2) {
                            Parcel parcelObtain = Parcel.obtain();
                            mediaItem.writeToParcel(parcelObtain, 0);
                            arrayList.add(parcelObtain);
                        }
                    } else {
                        arrayList = null;
                    }
                    resultWrapper.sendResult(arrayList);
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            mediaBrowserServiceCompat.onLoadChildren(str, result);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.1
                /* JADX WARN: Type inference failed for: r2v0, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
                /* JADX WARN: Type inference failed for: r2v3, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
                /* JADX WARN: Type inference failed for: r3v0, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
                @Override // java.lang.Runnable
                public void run() {
                    MediaBrowserServiceImplApi21 mediaBrowserServiceImplApi21 = MediaBrowserServiceImplApi21.this;
                    MediaSessionCompat.Token token2 = token;
                    if (!mediaBrowserServiceImplApi21.mRootExtrasList.isEmpty()) {
                        IMediaSession extraBinder = token2.getExtraBinder();
                        if (extraBinder != null) {
                            Iterator it = mediaBrowserServiceImplApi21.mRootExtrasList.iterator();
                            while (it.hasNext()) {
                                BundleCompat.putBinder((Bundle) it.next(), MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder.asBinder());
                            }
                        }
                        mediaBrowserServiceImplApi21.mRootExtrasList.clear();
                    }
                    mediaBrowserServiceImplApi21.mServiceFwk.setSessionToken((MediaSession.Token) token2.getToken());
                }
            });
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(final MediaSessionManager.RemoteUserInfo remoteUserInfo, final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.4
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceCompat.this.mConnections.size(); i++) {
                        ConnectionRecord connectionRecordValueAt = MediaBrowserServiceCompat.this.mConnections.valueAt(i);
                        if (connectionRecordValueAt.browserInfo.equals(remoteUserInfo)) {
                            MediaBrowserServiceImplApi21.this.notifyChildrenChangedForCompatOnHandler(connectionRecordValueAt, str, bundle);
                        }
                    }
                }
            });
        }
    }

    public void notifyChildrenChanged(@NonNull String str, @NonNull Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        if (bundle != null) {
            this.mImpl.notifyChildrenChanged(str, bundle);
            return;
        }
        throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void notifyChildrenChanged(@NonNull MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull String str, @NonNull Bundle bundle) {
        if (remoteUserInfo == null) {
            throw new IllegalArgumentException("remoteUserInfo cannot be null in notifyChildrenChanged");
        }
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        if (bundle != null) {
            this.mImpl.notifyChildrenChanged(remoteUserInfo, str, bundle);
            return;
        }
        throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }
}
