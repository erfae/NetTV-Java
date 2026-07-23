package com.nettv.livestore.net;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import com.nettv.livestore.apps.LTVApp;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public abstract class NetworkTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private ClientProtocolException clientProtocolException;
    private OnClientProtocolExceptionListener clientProtocolExceptionListener;
    private OnCompleteListener<Result> completeListener;
    private Exception exception;
    private OnExceptionListener exceptionListener;
    private OnExceptionListener genericExceptionListener;
    private IOException ioException;
    private OnIOExceptionListener ioExceptionListener;
    private JSONException jsonException;
    private OnJSONExceptionListener jsonExceptionListener;
    private OnNetworkUnavailableListener networkUnavailableListener;
    private OnProgressListener progressListener;
    private boolean isComplete = false;
    private boolean isAborted = false;

    public interface OnClientProtocolExceptionListener {
        void onClientProtocolException(ClientProtocolException clientProtocolException);
    }

    public interface OnCompleteListener<Result> {
        void onComplete(Result result);
    }

    public interface OnExceptionListener {
        void onException(Exception exc);
    }

    public interface OnIOExceptionListener {
        void onIOException(IOException iOException);
    }

    public interface OnJSONExceptionListener {
        void onJSONException(JSONException jSONException);
    }

    public interface OnNetworkUnavailableListener {
        void onNetworkException(NetworkErrorException networkErrorException);
    }

    public interface OnProgressListener {
        void onProgressListener(String str);
    }

    public void abort() {
        this.isAborted = true;
        cancel(true);
    }

    @Override // android.os.AsyncTask
    public final Result doInBackground(Params... paramsArr) {
        if (isCancelled()) {
            return null;
        }
        try {
            return doNetworkAction();
        } catch (ClientProtocolException e) {
            this.clientProtocolException = e;
            return null;
        } catch (IOException e2) {
            this.ioException = e2;
            return null;
        } catch (JSONException e3) {
            this.jsonException = e3;
            return null;
        } catch (Exception e4) {
            this.exception = e4;
            return null;
        }
    }

    public abstract Result doNetworkAction() throws JSONException, IOException;

    /* JADX WARN: Multi-variable type inference failed */
    public void execute() {
        execute(null, null);
    }

    public boolean isAborted() {
        return this.isAborted;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    @Override // android.os.AsyncTask
    public final void onPostExecute(Result result) {
        super.onPostExecute(result);
        this.isComplete = true;
        if (isCancelled() || isAborted()) {
            return;
        }
        ClientProtocolException clientProtocolException = this.clientProtocolException;
        if (clientProtocolException != null) {
            OnClientProtocolExceptionListener onClientProtocolExceptionListener = this.clientProtocolExceptionListener;
            if (onClientProtocolExceptionListener != null) {
                onClientProtocolExceptionListener.onClientProtocolException(clientProtocolException);
            }
            OnExceptionListener onExceptionListener = this.genericExceptionListener;
            if (onExceptionListener != null) {
                onExceptionListener.onException(this.clientProtocolException);
                return;
            }
            return;
        }
        if (this.jsonException != null) {
            OnIOExceptionListener onIOExceptionListener = this.ioExceptionListener;
            if (onIOExceptionListener != null) {
                onIOExceptionListener.onIOException(this.ioException);
            }
            OnExceptionListener onExceptionListener2 = this.genericExceptionListener;
            if (onExceptionListener2 != null) {
                onExceptionListener2.onException(this.ioException);
                return;
            }
            return;
        }
        IOException iOException = this.ioException;
        if (iOException != null) {
            OnIOExceptionListener onIOExceptionListener2 = this.ioExceptionListener;
            if (onIOExceptionListener2 != null) {
                onIOExceptionListener2.onIOException(iOException);
            }
            OnExceptionListener onExceptionListener3 = this.genericExceptionListener;
            if (onExceptionListener3 != null) {
                onExceptionListener3.onException(this.ioException);
                return;
            }
            return;
        }
        Exception exc = this.exception;
        if (exc == null) {
            OnCompleteListener<Result> onCompleteListener = this.completeListener;
            if (onCompleteListener != null) {
                onCompleteListener.onComplete(result);
                return;
            }
            return;
        }
        OnExceptionListener onExceptionListener4 = this.exceptionListener;
        if (onExceptionListener4 != null) {
            onExceptionListener4.onException(exc);
        }
        OnExceptionListener onExceptionListener5 = this.genericExceptionListener;
        if (onExceptionListener5 != null) {
            onExceptionListener5.onException(this.exception);
        }
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        super.onPreExecute();
        this.isComplete = false;
        this.isAborted = false;
        if (NetworkUtil.hasInternetAccess(LTVApp.getInstance())) {
            return;
        }
        OnNetworkUnavailableListener onNetworkUnavailableListener = this.networkUnavailableListener;
        if (onNetworkUnavailableListener != null) {
            onNetworkUnavailableListener.onNetworkException(new NetworkErrorException("Not aviable connection"));
        }
        abort();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.AsyncTask
    public final void onProgressUpdate(Progress... progressArr) {
        super.onProgressUpdate(progressArr);
        OnProgressListener onProgressListener = this.progressListener;
        if (onProgressListener != null) {
            onProgressListener.onProgressListener((String) progressArr[0]);
        }
    }

    public void setOnClientProtocolExceptionListener(OnClientProtocolExceptionListener onClientProtocolExceptionListener) {
        this.clientProtocolExceptionListener = onClientProtocolExceptionListener;
    }

    public void setOnCompleteListener(OnCompleteListener<Result> onCompleteListener) {
        this.completeListener = onCompleteListener;
    }

    public void setOnExceptionListener(OnExceptionListener onExceptionListener) {
        this.exceptionListener = onExceptionListener;
    }

    public void setOnGenericExceptionListener(OnExceptionListener onExceptionListener) {
        this.genericExceptionListener = onExceptionListener;
    }

    public void setOnIOExceptionListener(OnIOExceptionListener onIOExceptionListener) {
        this.ioExceptionListener = onIOExceptionListener;
    }

    public void setOnJSONExceptionListener(OnJSONExceptionListener onJSONExceptionListener) {
        this.jsonExceptionListener = onJSONExceptionListener;
    }

    public void setOnNetworkUnavailableListener(OnNetworkUnavailableListener onNetworkUnavailableListener) {
        this.networkUnavailableListener = onNetworkUnavailableListener;
    }

    public void setProgressListener(OnProgressListener onProgressListener) {
        this.progressListener = onProgressListener;
    }
}
