package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public abstract class BaseGmsClient<T extends IInterface> {

    @KeepForSdk
    public static final int CONNECT_STATE_CONNECTED = 4;

    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTED = 1;

    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTING = 5;

    @NonNull
    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";

    @NonNull
    @KeepForSdk
    public static final String KEY_PENDING_INTENT = "pendingIntent";

    @Nullable
    private volatile String zzA;

    @VisibleForTesting
    public zzu zza;
    public final Handler zzb;

    @NonNull
    @VisibleForTesting
    public ConnectionProgressReportCallbacks zzc;
    private int zzf;
    private long zzg;
    private long zzh;
    private int zzi;
    private long zzj;
    private final Context zzl;
    private final Looper zzm;
    private final GmsClientSupervisor zzn;
    private final GoogleApiAvailabilityLight zzo;

    @Nullable
    @GuardedBy("mServiceBrokerLock")
    private IGmsServiceBroker zzr;

    @Nullable
    @GuardedBy("mLock")
    private T zzs;

    @Nullable
    @GuardedBy("mLock")
    private zze zzu;

    @Nullable
    private final BaseConnectionCallbacks zzw;

    @Nullable
    private final BaseOnConnectionFailedListener zzx;
    private final int zzy;

    @Nullable
    private final String zzz;
    private static final Feature[] zze = new Feature[0];

    @NonNull
    @KeepForSdk
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", "service_googleme"};

    @Nullable
    private volatile String zzk = null;
    private final Object zzp = new Object();
    private final Object zzq = new Object();
    private final ArrayList<zzc<?>> zzt = new ArrayList<>();

    @GuardedBy("mLock")
    private int zzv = 1;

    @Nullable
    private ConnectionResult zzB = null;
    private boolean zzC = false;

    @Nullable
    private volatile zzj zzD = null;

    @NonNull
    @VisibleForTesting
    public AtomicInteger zzd = new AtomicInteger(0);

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface BaseConnectionCallbacks {

        @KeepForSdk
        public static final int CAUSE_DEAD_OBJECT_EXCEPTION = 3;

        @KeepForSdk
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        @KeepForSdk
        void onConnected(@Nullable Bundle bundle);

        @KeepForSdk
        void onConnectionSuspended(int i);
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface BaseOnConnectionFailedListener {
        @KeepForSdk
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface ConnectionProgressReportCallbacks {
        @KeepForSdk
        void onReportServiceBinding(@NonNull ConnectionResult connectionResult);
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        @KeepForSdk
        public LegacyClientCallbackAdapter() {
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzx != null) {
                BaseGmsClient.this.zzx.onConnectionFailed(connectionResult);
            }
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface SignOutCallbacks {
        @KeepForSdk
        void onSignOutComplete();
    }

    @VisibleForTesting
    @KeepForSdk
    public BaseGmsClient(@NonNull Context context, @NonNull Looper looper, @NonNull GmsClientSupervisor gmsClientSupervisor, @NonNull GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, @Nullable BaseConnectionCallbacks baseConnectionCallbacks, @Nullable BaseOnConnectionFailedListener baseOnConnectionFailedListener, @Nullable String str) {
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzm = looper;
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzo = googleApiAvailabilityLight;
        this.zzb = new zzb(this, looper);
        this.zzy = i;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = str;
    }

    public static /* bridge */ /* synthetic */ void zzj(BaseGmsClient baseGmsClient, zzj zzjVar) {
        baseGmsClient.zzD = zzjVar;
        if (baseGmsClient.usesClientTelemetry()) {
            ConnectionTelemetryConfiguration connectionTelemetryConfiguration = zzjVar.zzd;
            RootTelemetryConfigManager.getInstance().zza(connectionTelemetryConfiguration == null ? null : connectionTelemetryConfiguration.zza());
        }
    }

    public static /* bridge */ /* synthetic */ void zzk(BaseGmsClient baseGmsClient) {
        int i;
        int i2;
        synchronized (baseGmsClient.zzp) {
            i = baseGmsClient.zzv;
        }
        if (i == 3) {
            baseGmsClient.zzC = true;
            i2 = 5;
        } else {
            i2 = 4;
        }
        Handler handler = baseGmsClient.zzb;
        handler.sendMessage(handler.obtainMessage(i2, baseGmsClient.zzd.get(), 16));
    }

    public static /* bridge */ /* synthetic */ boolean zzn(BaseGmsClient baseGmsClient, int i, int i2, IInterface iInterface) {
        synchronized (baseGmsClient.zzp) {
            if (baseGmsClient.zzv != i) {
                return false;
            }
            baseGmsClient.zzp(i2, iInterface);
            return true;
        }
    }

    public static /* bridge */ /* synthetic */ boolean zzo(BaseGmsClient baseGmsClient) {
        if (baseGmsClient.zzC || TextUtils.isEmpty(baseGmsClient.getServiceDescriptor()) || TextUtils.isEmpty(null)) {
            return false;
        }
        try {
            Class.forName(baseGmsClient.getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzp(int i, @Nullable T t) {
        zzu zzuVar;
        Preconditions.checkArgument((i == 4) == (t != null));
        synchronized (this.zzp) {
            this.zzv = i;
            this.zzs = t;
            if (i == 1) {
                zze zzeVar = this.zzu;
                if (zzeVar != null) {
                    GmsClientSupervisor gmsClientSupervisor = this.zzn;
                    String strZzc = this.zza.zzc();
                    Preconditions.checkNotNull(strZzc);
                    gmsClientSupervisor.zzb(strZzc, this.zza.zzb(), this.zza.zza(), zzeVar, zze(), this.zza.zzd());
                    this.zzu = null;
                }
            } else if (i == 2 || i == 3) {
                zze zzeVar2 = this.zzu;
                if (zzeVar2 != null && (zzuVar = this.zza) != null) {
                    String strZzc2 = zzuVar.zzc();
                    String strZzb = zzuVar.zzb();
                    StringBuilder sb = new StringBuilder(String.valueOf(strZzc2).length() + 70 + String.valueOf(strZzb).length());
                    sb.append("Calling connect() while still connected, missing disconnect() for ");
                    sb.append(strZzc2);
                    sb.append(" on ");
                    sb.append(strZzb);
                    Log.e("GmsClient", sb.toString());
                    GmsClientSupervisor gmsClientSupervisor2 = this.zzn;
                    String strZzc3 = this.zza.zzc();
                    Preconditions.checkNotNull(strZzc3);
                    gmsClientSupervisor2.zzb(strZzc3, this.zza.zzb(), this.zza.zza(), zzeVar2, zze(), this.zza.zzd());
                    this.zzd.incrementAndGet();
                }
                zze zzeVar3 = new zze(this, this.zzd.get());
                this.zzu = zzeVar3;
                zzu zzuVar2 = new zzu("com.google.android.gms", getStartServiceAction(), false, GmsClientSupervisor.getDefaultBindFlags(), getUseDynamicLookup());
                this.zza = zzuVar2;
                if (zzuVar2.zzd() && getMinApkVersion() < 17895000) {
                    String strValueOf = String.valueOf(this.zza.zzc());
                    throw new IllegalStateException(strValueOf.length() != 0 ? "Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat(strValueOf) : new String("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: "));
                }
                GmsClientSupervisor gmsClientSupervisor3 = this.zzn;
                String strZzc4 = this.zza.zzc();
                Preconditions.checkNotNull(strZzc4);
                String strZzb2 = this.zza.zzb();
                int iZza = this.zza.zza();
                String strZze = zze();
                boolean zZzd = this.zza.zzd();
                getBindServiceExecutor();
                if (!gmsClientSupervisor3.zzc(new zzn(strZzc4, strZzb2, iZza, zZzd), zzeVar3, strZze, null)) {
                    String strZzc5 = this.zza.zzc();
                    String strZzb3 = this.zza.zzb();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strZzc5).length() + 34 + String.valueOf(strZzb3).length());
                    sb2.append("unable to connect to service: ");
                    sb2.append(strZzc5);
                    sb2.append(" on ");
                    sb2.append(strZzb3);
                    Log.w("GmsClient", sb2.toString());
                    int i2 = this.zzd.get();
                    Handler handler = this.zzb;
                    handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzg(this, 16, null)));
                }
            } else if (i == 4) {
                Preconditions.checkNotNull(t);
                this.zzh = System.currentTimeMillis();
            }
        }
    }

    @KeepForSdk
    public void checkAvailabilityAndConnect() {
        int iIsGooglePlayServicesAvailable = this.zzo.isGooglePlayServicesAvailable(this.zzl, getMinApkVersion());
        if (iIsGooglePlayServicesAvailable == 0) {
            connect(new LegacyClientCallbackAdapter());
            return;
        }
        zzp(1, null);
        LegacyClientCallbackAdapter legacyClientCallbackAdapter = new LegacyClientCallbackAdapter();
        Preconditions.checkNotNull(legacyClientCallbackAdapter, "Connection progress callbacks cannot be null.");
        this.zzc = legacyClientCallbackAdapter;
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(3, this.zzd.get(), iIsGooglePlayServicesAvailable, null));
    }

    @KeepForSdk
    public void connect(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.zzc = connectionProgressReportCallbacks;
        zzp(2, null);
    }

    @Nullable
    @KeepForSdk
    public abstract T createServiceInterface(@NonNull IBinder iBinder);

    @KeepForSdk
    public void disconnect() {
        this.zzd.incrementAndGet();
        synchronized (this.zzt) {
            int size = this.zzt.size();
            for (int i = 0; i < size; i++) {
                this.zzt.get(i).zzf();
            }
            this.zzt.clear();
        }
        synchronized (this.zzq) {
            this.zzr = null;
        }
        zzp(1, null);
    }

    @KeepForSdk
    public void dump(@NonNull String str, @NonNull FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @NonNull String[] strArr) {
        int i;
        T t;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzp) {
            i = this.zzv;
            t = this.zzs;
        }
        synchronized (this.zzq) {
            iGmsServiceBroker = this.zzr;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        if (i == 1) {
            printWriter.print("DISCONNECTED");
        } else if (i == 2) {
            printWriter.print("REMOTE_CONNECTING");
        } else if (i == 3) {
            printWriter.print("LOCAL_CONNECTING");
        } else if (i == 4) {
            printWriter.print("CONNECTED");
        } else if (i != 5) {
            printWriter.print("UNKNOWN");
        } else {
            printWriter.print("DISCONNECTING");
        }
        printWriter.append(" mService=");
        if (t == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzh > 0) {
            PrintWriter printWriterAppend = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j = this.zzh;
            String str2 = simpleDateFormat.format(new Date(j));
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 21);
            sb.append(j);
            sb.append(" ");
            sb.append(str2);
            printWriterAppend.println(sb.toString());
        }
        if (this.zzg > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            int i2 = this.zzf;
            if (i2 == 1) {
                printWriter.append("CAUSE_SERVICE_DISCONNECTED");
            } else if (i2 == 2) {
                printWriter.append("CAUSE_NETWORK_LOST");
            } else if (i2 != 3) {
                printWriter.append((CharSequence) String.valueOf(i2));
            } else {
                printWriter.append("CAUSE_DEAD_OBJECT_EXCEPTION");
            }
            PrintWriter printWriterAppend2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzg;
            String str3 = simpleDateFormat.format(new Date(j2));
            StringBuilder sb2 = new StringBuilder(String.valueOf(str3).length() + 21);
            sb2.append(j2);
            sb2.append(" ");
            sb2.append(str3);
            printWriterAppend2.println(sb2.toString());
        }
        if (this.zzj > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzi));
            PrintWriter printWriterAppend3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzj;
            String str4 = simpleDateFormat.format(new Date(j3));
            StringBuilder sb3 = new StringBuilder(String.valueOf(str4).length() + 21);
            sb3.append(j3);
            sb3.append(" ");
            sb3.append(str4);
            printWriterAppend3.println(sb3.toString());
        }
    }

    @Nullable
    @KeepForSdk
    public Account getAccount() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public Feature[] getApiFeatures() {
        return zze;
    }

    @Nullable
    @KeepForSdk
    public final Feature[] getAvailableFeatures() {
        zzj zzjVar = this.zzD;
        if (zzjVar == null) {
            return null;
        }
        return zzjVar.zzb;
    }

    @Nullable
    @KeepForSdk
    public void getBindServiceExecutor() {
    }

    @Nullable
    @KeepForSdk
    public Bundle getConnectionHint() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public final Context getContext() {
        return this.zzl;
    }

    @NonNull
    @KeepForSdk
    public String getEndpointPackageName() {
        zzu zzuVar;
        if (!isConnected() || (zzuVar = this.zza) == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return zzuVar.zzb();
    }

    @KeepForSdk
    public int getGCoreServiceId() {
        return this.zzy;
    }

    @NonNull
    @KeepForSdk
    public Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    @Nullable
    @KeepForSdk
    public String getLastDisconnectMessage() {
        return this.zzk;
    }

    @NonNull
    @KeepForSdk
    public final Looper getLooper() {
        return this.zzm;
    }

    @KeepForSdk
    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    @KeepForSdk
    @WorkerThread
    public void getRemoteService(@Nullable IAccountAccessor iAccountAccessor, @NonNull Set<Scope> set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzy, this.zzA);
        getServiceRequest.zzd = this.zzl.getPackageName();
        getServiceRequest.zzg = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzf = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (requiresSignIn()) {
            Account account = getAccount();
            if (account == null) {
                account = new Account("<<default account>>", AccountType.GOOGLE);
            }
            getServiceRequest.zzh = account;
            if (iAccountAccessor != null) {
                getServiceRequest.zze = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest.zzh = getAccount();
        }
        getServiceRequest.zzi = zze;
        getServiceRequest.zzj = getApiFeatures();
        if (usesClientTelemetry()) {
            getServiceRequest.zzm = true;
        }
        try {
            synchronized (this.zzq) {
                IGmsServiceBroker iGmsServiceBroker = this.zzr;
                if (iGmsServiceBroker != null) {
                    iGmsServiceBroker.getService(new zzd(this, this.zzd.get()), getServiceRequest);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            triggerConnectionSuspended(3);
        } catch (RemoteException e2) {
            e = e2;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            int i = this.zzd.get();
            Handler handler = this.zzb;
            handler.sendMessage(handler.obtainMessage(1, i, -1, new zzf(this, 8, null, null)));
        } catch (SecurityException e3) {
            throw e3;
        } catch (RuntimeException e4) {
            e = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            int i2 = this.zzd.get();
            Handler handler2 = this.zzb;
            handler2.sendMessage(handler2.obtainMessage(1, i2, -1, new zzf(this, 8, null, null)));
        }
    }

    @NonNull
    @KeepForSdk
    public Set<Scope> getScopes() {
        return Collections.emptySet();
    }

    @NonNull
    @KeepForSdk
    public final T getService() throws DeadObjectException {
        T t;
        synchronized (this.zzp) {
            try {
                if (this.zzv == 5) {
                    throw new DeadObjectException();
                }
                if (!isConnected()) {
                    throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
                }
                t = this.zzs;
                Preconditions.checkNotNull(t, "Client is connected but service is null");
            } catch (Throwable th) {
                throw th;
            }
        }
        return t;
    }

    @Nullable
    @KeepForSdk
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzq) {
            IGmsServiceBroker iGmsServiceBroker = this.zzr;
            if (iGmsServiceBroker == null) {
                return null;
            }
            return iGmsServiceBroker.asBinder();
        }
    }

    @NonNull
    @KeepForSdk
    public abstract String getServiceDescriptor();

    @NonNull
    @KeepForSdk
    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    @NonNull
    @KeepForSdk
    public abstract String getStartServiceAction();

    @Nullable
    @KeepForSdk
    public ConnectionTelemetryConfiguration getTelemetryConfiguration() {
        zzj zzjVar = this.zzD;
        if (zzjVar == null) {
            return null;
        }
        return zzjVar.zzd;
    }

    @KeepForSdk
    public boolean getUseDynamicLookup() {
        return getMinApkVersion() >= 211700000;
    }

    @KeepForSdk
    public boolean hasConnectionInfo() {
        return this.zzD != null;
    }

    @KeepForSdk
    public boolean isConnected() {
        boolean z;
        synchronized (this.zzp) {
            z = this.zzv == 4;
        }
        return z;
    }

    @KeepForSdk
    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzp) {
            int i = this.zzv;
            z = true;
            if (i != 2 && i != 3) {
                z = false;
            }
        }
        return z;
    }

    @KeepForSdk
    @CallSuper
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzi = connectionResult.getErrorCode();
        this.zzj = System.currentTimeMillis();
    }

    @KeepForSdk
    @CallSuper
    public final void onConnectionSuspended(int i) {
        this.zzf = i;
        this.zzg = System.currentTimeMillis();
    }

    @KeepForSdk
    public void onUserSignOut(@NonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    @KeepForSdk
    public boolean providesSignIn() {
        return false;
    }

    @KeepForSdk
    public boolean requiresAccount() {
        return false;
    }

    @KeepForSdk
    public boolean requiresGooglePlayServices() {
        return true;
    }

    @KeepForSdk
    public boolean requiresSignIn() {
        return false;
    }

    @KeepForSdk
    public void setAttributionTag(@NonNull String str) {
        this.zzA = str;
    }

    @KeepForSdk
    public void triggerConnectionSuspended(int i) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzd.get(), i));
    }

    @KeepForSdk
    public boolean usesClientTelemetry() {
        return false;
    }

    @NonNull
    public final String zze() {
        String str = this.zzz;
        return str == null ? this.zzl.getClass().getName() : str;
    }

    @KeepForSdk
    public void disconnect(@NonNull String str) {
        this.zzk = str;
        disconnect();
    }
}
