package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.android.volley.AuthFailureError;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"MissingPermission"})
public class AndroidAuthenticator implements Authenticator {
    private final Account mAccount;
    private final AccountManager mAccountManager;
    private final String mAuthTokenType;
    private final boolean mNotifyAuthFailure;

    public AndroidAuthenticator(Context context, Account account, String str) {
        this(context, account, str, false);
    }

    public Account getAccount() {
        return this.mAccount;
    }

    @Override // com.android.volley.toolbox.Authenticator
    public String getAuthToken() throws AuthFailureError {
        AccountManagerFuture<Bundle> authToken = this.mAccountManager.getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
        try {
            Bundle result = authToken.getResult();
            String string = null;
            if (authToken.isDone() && !authToken.isCancelled()) {
                if (result.containsKey("intent")) {
                    throw new AuthFailureError((Intent) result.getParcelable("intent"));
                }
                string = result.getString("authtoken");
            }
            if (string != null) {
                return string;
            }
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Got null auth token for type: ");
            sbM.append(this.mAuthTokenType);
            throw new AuthFailureError(sbM.toString());
        } catch (Exception e) {
            throw new AuthFailureError("Error while retrieving auth token", e);
        }
    }

    public String getAuthTokenType() {
        return this.mAuthTokenType;
    }

    @Override // com.android.volley.toolbox.Authenticator
    public void invalidateAuthToken(String str) {
        this.mAccountManager.invalidateAuthToken(this.mAccount.type, str);
    }

    public AndroidAuthenticator(Context context, Account account, String str, boolean z) {
        this.mAccountManager = AccountManager.get(context);
        this.mAccount = account;
        this.mAuthTokenType = str;
        this.mNotifyAuthFailure = z;
    }
}
