package com.getkeepsafe.relinker;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
final class SystemLibraryLoader implements ReLinker.LibraryLoader {
    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public void loadLibrary(String str) {
        System.loadLibrary(str);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public void loadPath(String str) {
        System.load(str);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String mapLibraryName(String str) {
        return (str.startsWith("lib") && str.endsWith(".so")) ? str : System.mapLibraryName(str);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String[] supportedAbis() {
        String[] strArr = Build.SUPPORTED_ABIS;
        if (strArr.length > 0) {
            return strArr;
        }
        String str = Build.CPU_ABI2;
        return !TextUtils.isEmpty(str) ? new String[]{Build.CPU_ABI, str} : new String[]{Build.CPU_ABI};
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String unmapLibraryName(String str) {
        return str.substring(3, str.length() - 3);
    }
}
