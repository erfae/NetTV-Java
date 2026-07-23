package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public final class AccountPicker {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public static class AccountChooserOptions {

        @Nullable
        private Account zza;
        private boolean zzb;

        @Nullable
        private ArrayList<Account> zzc;

        @Nullable
        private ArrayList<String> zzd;
        private boolean zze;

        @Nullable
        private String zzf;

        @Nullable
        private Bundle zzg;
        private boolean zzh;
        private int zzi;

        @Nullable
        private String zzj;
        private boolean zzk;

        @Nullable
        private zza zzl;

        @Nullable
        private String zzm;
        private boolean zzn;
        private boolean zzo;

        /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
        public static class Builder {

            @Nullable
            private Account zza;

            @Nullable
            private ArrayList<Account> zzb;

            @Nullable
            private ArrayList<String> zzc;
            private boolean zzd = false;

            @Nullable
            private String zze;

            @Nullable
            private Bundle zzf;

            @NonNull
            public AccountChooserOptions build() {
                Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
                Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
                AccountChooserOptions accountChooserOptions = new AccountChooserOptions();
                accountChooserOptions.zzd = this.zzc;
                accountChooserOptions.zzc = this.zzb;
                accountChooserOptions.zze = this.zzd;
                accountChooserOptions.zzl = null;
                accountChooserOptions.zzj = null;
                accountChooserOptions.zzg = this.zzf;
                accountChooserOptions.zza = this.zza;
                accountChooserOptions.zzb = false;
                accountChooserOptions.zzh = false;
                accountChooserOptions.zzm = null;
                accountChooserOptions.zzi = 0;
                accountChooserOptions.zzf = this.zze;
                accountChooserOptions.zzk = false;
                accountChooserOptions.zzn = false;
                accountChooserOptions.zzo = false;
                return accountChooserOptions;
            }

            @NonNull
            public Builder setAllowableAccounts(@Nullable List<Account> list) {
                this.zzb = list == null ? null : new ArrayList<>(list);
                return this;
            }

            @NonNull
            public Builder setAllowableAccountsTypes(@Nullable List<String> list) {
                this.zzc = list == null ? null : new ArrayList<>(list);
                return this;
            }

            @NonNull
            public Builder setAlwaysShowAccountPicker(boolean z) {
                this.zzd = z;
                return this;
            }

            @NonNull
            public Builder setOptionsForAddingAccount(@Nullable Bundle bundle) {
                this.zzf = bundle;
                return this;
            }

            @NonNull
            public Builder setSelectedAccount(@Nullable Account account) {
                this.zza = account;
                return this;
            }

            @NonNull
            public Builder setTitleOverrideText(@Nullable String str) {
                this.zze = str;
                return this;
            }
        }
    }

    private AccountPicker() {
    }

    @NonNull
    @Deprecated
    public static Intent newChooseAccountIntent(@Nullable Account account, @Nullable ArrayList<Account> arrayList, @Nullable String[] strArr, boolean z, @Nullable String str, @Nullable String str2, @Nullable String[] strArr2, @Nullable Bundle bundle) {
        Intent intent = new Intent();
        Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", arrayList);
        intent.putExtra("allowableAccountTypes", strArr);
        intent.putExtra("addAccountOptions", bundle);
        intent.putExtra("selectedAccount", account);
        intent.putExtra("alwaysPromptForAccount", z);
        intent.putExtra("descriptionTextOverride", str);
        intent.putExtra("authTokenType", str2);
        intent.putExtra("addAccountRequiredFeatures", strArr2);
        intent.putExtra("setGmsCoreAccount", false);
        intent.putExtra("overrideTheme", 0);
        intent.putExtra("overrideCustomTheme", 0);
        intent.putExtra("hostedDomainFilter", (String) null);
        return intent;
    }

    @NonNull
    public static Intent newChooseAccountIntent(@NonNull AccountChooserOptions accountChooserOptions) {
        Intent intent = new Intent();
        accountChooserOptions.zzk;
        accountChooserOptions.zzj;
        Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
        accountChooserOptions.zzl;
        Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
        accountChooserOptions.zzb;
        Preconditions.checkArgument(true, "Making the selected account non-clickable is only supported for the theme THEME_DAY_NIGHT_GOOGLE_MATERIAL2");
        accountChooserOptions.zzk;
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", accountChooserOptions.zzc);
        if (accountChooserOptions.zzd != null) {
            intent.putExtra("allowableAccountTypes", (String[]) accountChooserOptions.zzd.toArray(new String[0]));
        }
        intent.putExtra("addAccountOptions", accountChooserOptions.zzg);
        intent.putExtra("selectedAccount", accountChooserOptions.zza);
        accountChooserOptions.zzb;
        intent.putExtra("selectedAccountIsNotClickable", false);
        intent.putExtra("alwaysPromptForAccount", accountChooserOptions.zze);
        intent.putExtra("descriptionTextOverride", accountChooserOptions.zzf);
        accountChooserOptions.zzh;
        intent.putExtra("setGmsCoreAccount", false);
        accountChooserOptions.zzm;
        intent.putExtra("realClientPackage", (String) null);
        accountChooserOptions.zzi;
        intent.putExtra("overrideTheme", 0);
        accountChooserOptions.zzk;
        intent.putExtra("overrideCustomTheme", 0);
        accountChooserOptions.zzj;
        intent.putExtra("hostedDomainFilter", (String) null);
        Bundle bundle = new Bundle();
        accountChooserOptions.zzk;
        accountChooserOptions.zzl;
        accountChooserOptions.zzn;
        accountChooserOptions.zzo;
        if (!bundle.isEmpty()) {
            intent.putExtra("first_party_options_bundle", bundle);
        }
        return intent;
    }
}
