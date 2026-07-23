package com.nettv.livestore.apps;

import android.accounts.NetworkErrorException;
import com.nettv.livestore.net.NetworkTask;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda2 implements NetworkTask.OnCompleteListener, NetworkTask.OnExceptionListener, NetworkTask.OnNetworkUnavailableListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseActivity f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda2(BaseActivity baseActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = baseActivity;
    }

    @Override // com.nettv.livestore.net.NetworkTask.OnCompleteListener
    public final void onComplete(Object obj) {
        this.f$0.lambda$fetchM3UItems$3((List) obj);
    }

    @Override // com.nettv.livestore.net.NetworkTask.OnExceptionListener
    public final void onException(Exception exc) {
        switch (this.$r8$classId) {
            case 1:
                this.f$0.lambda$fetchM3UItems$4(exc);
                break;
            case 2:
            default:
                this.f$0.lambda$getChannelModels$10(exc);
                break;
            case 3:
                this.f$0.lambda$getMovieModels$15(exc);
                break;
            case 4:
                this.f$0.lambda$getEpisodeModels$18(exc);
                break;
        }
    }

    @Override // com.nettv.livestore.net.NetworkTask.OnNetworkUnavailableListener
    public final void onNetworkException(NetworkErrorException networkErrorException) {
        this.f$0.lambda$fetchM3UItems$5(networkErrorException);
    }
}
