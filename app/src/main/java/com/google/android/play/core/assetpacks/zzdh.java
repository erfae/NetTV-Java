package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdh {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("ExtractorTaskFinder");
    private final zzde zzb;
    private final zzbh zzc;
    private final zzbu zzd;
    private final com.google.android.play.core.common.zza zze;

    public zzdh(zzde zzdeVar, zzbh zzbhVar, zzbu zzbuVar, com.google.android.play.core.common.zza zzaVar) {
        this.zzb = zzdeVar;
        this.zzc = zzbhVar;
        this.zzd = zzbuVar;
        this.zze = zzaVar;
    }

    private final boolean zzb(zzdb zzdbVar, zzdc zzdcVar) {
        zzbh zzbhVar = this.zzc;
        zzda zzdaVar = zzdbVar.zzc;
        return new zzen(zzbhVar, zzdaVar.zza, zzdbVar.zzb, zzdaVar.zzb, zzdcVar.zza).zzm();
    }

    private static boolean zzc(zzdc zzdcVar) {
        int i = zzdcVar.zzf;
        return i == 1 || i == 2;
    }

    /* JADX WARN: Code duplicated, block: B:109:0x03e8  */
    /* JADX WARN: Code duplicated, block: B:110:0x03eb  */
    /* JADX WARN: Code duplicated, block: B:112:0x03f2  */
    /* JADX WARN: Code duplicated, block: B:115:0x03f9 A[PHI: r0
  0x03f9: PHI (r0v47 com.google.android.play.core.assetpacks.zzdg) = 
  (r0v11 com.google.android.play.core.assetpacks.zzdg)
  (r0v14 com.google.android.play.core.assetpacks.zzdg)
  (r0v17 com.google.android.play.core.assetpacks.zzdg)
  (r0v51 com.google.android.play.core.assetpacks.zzdg)
 binds: [B:35:0x0125, B:49:0x01ae, B:64:0x0238, B:23:0x00af] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:137:0x0124 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:0x00d9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:139:? A[LOOP:2: B:25:0x00b5->B:139:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:140:0x01ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:141:0x0142 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:142:0x016f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:144:0x012b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:146:0x0237 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x01ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:148:0x01f8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:150:0x01b4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x01e2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:155:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:156:0x032e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:157:0x0256 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:160:0x023e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:162:0x026f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:164:0x025c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:167:0x03e5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:168:0x03e2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:24:0x00b1 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x00bb A[Catch: all -> 0x03ff, TRY_LEAVE, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0127 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0132 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x014e A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x01aa A[LOOP:4: B:42:0x0148->B:47:0x01aa, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:50:0x01b0 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x01ba A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:58:0x01d6 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x023a A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x0245 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x0262 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:89:0x0331 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:92:0x033b A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:94:0x034b A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x0357 A[Catch: all -> 0x03ff, TryCatch #2 {all -> 0x03ff, blocks: (B:3:0x0002, B:4:0x001a, B:6:0x0020, B:8:0x0030, B:9:0x0034, B:12:0x003f, B:14:0x004c, B:15:0x0056, B:17:0x005c, B:19:0x0071, B:21:0x007d, B:24:0x00b1, B:25:0x00b5, B:27:0x00bb, B:28:0x00c1, B:30:0x00d9, B:36:0x0127, B:37:0x012b, B:39:0x0132, B:41:0x0142, B:42:0x0148, B:44:0x014e, B:46:0x016f, B:50:0x01b0, B:51:0x01b4, B:53:0x01ba, B:55:0x01ca, B:56:0x01d0, B:58:0x01d6, B:60:0x01e2, B:62:0x01f8, B:65:0x023a, B:66:0x023e, B:68:0x0245, B:70:0x0256, B:71:0x025c, B:73:0x0262, B:75:0x026f, B:76:0x028e, B:83:0x02a5, B:85:0x02b1, B:89:0x0331, B:90:0x0335, B:92:0x033b, B:94:0x034b, B:95:0x0351, B:97:0x0357, B:99:0x0363, B:101:0x0370, B:103:0x0376, B:80:0x0295, B:32:0x0106, B:33:0x0123), top: B:125:0x0002, inners: #0, #1 }] */
    @Nullable
    public final zzdg zza() {
        Iterator it;
        zzdg zzefVar;
        Iterator it2;
        Iterator it3;
        Iterator it4;
        zzce zzceVar;
        zzde zzdeVar;
        zzce zzceVar2;
        Iterator it5;
        zzde zzdeVar2;
        zzda zzdaVar;
        zzdb zzdbVar;
        zzda zzdaVar2;
        Iterator it6;
        zzdc zzdcVar;
        int iZza;
        zzdb zzdbVar2;
        zzda zzdaVar3;
        zzbh zzbhVar;
        zzda zzdaVar4;
        zzdb zzdbVar3;
        zzda zzdaVar5;
        zzbh zzbhVar2;
        zzda zzdaVar6;
        zzdb zzdbVar4;
        zzbh zzbhVar3;
        zzda zzdaVar7;
        try {
            this.zzb.zzj();
            ArrayList arrayList = new ArrayList();
            for (zzdb zzdbVar5 : this.zzb.zzg().values()) {
                if (zzbg.zzb(zzdbVar5.zzc.zzd)) {
                    arrayList.add(zzdbVar5);
                }
            }
            if (!arrayList.isEmpty()) {
                char c = 0;
                if (this.zze.zza("assetOnlyUpdates")) {
                    Map mapZzt = this.zzc.zzt();
                    Iterator it7 = arrayList.iterator();
                    while (true) {
                        if (!it7.hasNext()) {
                            zzefVar = null;
                            break;
                        }
                        zzdb zzdbVar6 = (zzdb) it7.next();
                        Long l = (Long) ((HashMap) mapZzt).get(zzdbVar6.zzc.zza);
                        if (l != null && zzdbVar6.zzc.zzb == l.longValue()) {
                            zza.zza("Found promote pack task for session %s with pack %s.", Integer.valueOf(zzdbVar6.zza), zzdbVar6.zzc.zza);
                            int i = zzdbVar6.zza;
                            String str = zzdbVar6.zzc.zza;
                            zzefVar = new zzei(i, str, this.zzc.zza(str), zzdbVar6.zzb, zzdbVar6.zzc.zzb);
                            break;
                        }
                    }
                    if (zzefVar == null) {
                        it = arrayList.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                zzefVar = null;
                                break;
                            }
                            zzdbVar4 = (zzdb) it.next();
                            try {
                                zzbhVar3 = this.zzc;
                                zzdaVar7 = zzdbVar4.zzc;
                                if (zzbhVar3.zzb(zzdaVar7.zza, zzdbVar4.zzb, zzdaVar7.zzb) == zzdbVar4.zzc.zzf.size()) {
                                    zza.zza("Found final move task for session %s with pack %s.", Integer.valueOf(zzdbVar4.zza), zzdbVar4.zzc.zza);
                                    int i2 = zzdbVar4.zza;
                                    zzda zzdaVar8 = zzdbVar4.zzc;
                                    zzefVar = new zzdw(i2, zzdaVar8.zza, zzdbVar4.zzb, zzdaVar8.zzb, zzdaVar8.zzc);
                                    break;
                                }
                            } catch (IOException e) {
                                throw new zzck(String.format("Failed to check number of completed merges for session %s, pack %s", Integer.valueOf(zzdbVar4.zza), zzdbVar4.zzc.zza), e, zzdbVar4.zza);
                            }
                        }
                        if (zzefVar == null) {
                            it2 = arrayList.iterator();
                            loop3: while (true) {
                                if (it2.hasNext()) {
                                    zzefVar = null;
                                    break;
                                }
                                zzdbVar3 = (zzdb) it2.next();
                                zzdaVar5 = zzdbVar3.zzc;
                                if (zzbg.zzb(zzdaVar5.zzd)) {
                                    for (zzdc zzdcVar2 : zzdaVar5.zzf) {
                                        zzbhVar2 = this.zzc;
                                        zzdaVar6 = zzdbVar3.zzc;
                                        if (zzbhVar2.zzq(zzdaVar6.zza, zzdbVar3.zzb, zzdaVar6.zzb, zzdcVar2.zza).exists()) {
                                            zza.zza("Found merge task for session %s with pack %s and slice %s.", Integer.valueOf(zzdbVar3.zza), zzdbVar3.zzc.zza, zzdcVar2.zza);
                                            int i3 = zzdbVar3.zza;
                                            zzda zzdaVar9 = zzdbVar3.zzc;
                                            zzefVar = new zzdt(i3, zzdaVar9.zza, zzdbVar3.zzb, zzdaVar9.zzb, zzdcVar2.zza);
                                            break loop3;
                                        }
                                    }
                                }
                            }
                            if (zzefVar == null) {
                                it3 = arrayList.iterator();
                                loop5: while (true) {
                                    if (it3.hasNext()) {
                                        zzefVar = null;
                                        break;
                                    }
                                    zzdbVar2 = (zzdb) it3.next();
                                    zzdaVar3 = zzdbVar2.zzc;
                                    if (zzbg.zzb(zzdaVar3.zzd)) {
                                        for (zzdc zzdcVar3 : zzdaVar3.zzf) {
                                            if (zzb(zzdbVar2, zzdcVar3)) {
                                                zzbhVar = this.zzc;
                                                zzdaVar4 = zzdbVar2.zzc;
                                                if (zzbhVar.zzp(zzdaVar4.zza, zzdbVar2.zzb, zzdaVar4.zzb, zzdcVar3.zza).exists()) {
                                                    zza.zza("Found verify task for session %s with pack %s and slice %s.", Integer.valueOf(zzdbVar2.zza), zzdbVar2.zzc.zza, zzdcVar3.zza);
                                                    int i4 = zzdbVar2.zza;
                                                    zzda zzdaVar10 = zzdbVar2.zzc;
                                                    zzefVar = new zzeq(i4, zzdaVar10.zza, zzdbVar2.zzb, zzdaVar10.zzb, zzdcVar3.zza, zzdcVar3.zzb);
                                                    break loop5;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (zzefVar == null) {
                                    it4 = arrayList.iterator();
                                    loop7: while (true) {
                                        if (it4.hasNext()) {
                                            zzceVar = null;
                                            break;
                                        }
                                        zzdbVar = (zzdb) it4.next();
                                        zzdaVar2 = zzdbVar.zzc;
                                        if (zzbg.zzb(zzdaVar2.zzd)) {
                                            it6 = zzdaVar2.zzf.iterator();
                                            while (it6.hasNext()) {
                                                zzdcVar = (zzdc) it6.next();
                                                if (!zzc(zzdcVar)) {
                                                    zzbh zzbhVar4 = this.zzc;
                                                    zzda zzdaVar11 = zzdbVar.zzc;
                                                    Iterator it8 = it6;
                                                    try {
                                                        iZza = new zzen(zzbhVar4, zzdaVar11.zza, zzdbVar.zzb, zzdaVar11.zzb, zzdcVar.zza).zza();
                                                    } catch (IOException e2) {
                                                        com.google.android.play.core.internal.zzag zzagVar = zza;
                                                        Object[] objArr = new Object[1];
                                                        objArr[c] = e2;
                                                        zzagVar.zzb("Slice checkpoint corrupt, restarting extraction. %s", objArr);
                                                        iZza = 0;
                                                    }
                                                    if (iZza == -1 && ((zzcz) zzdcVar.zzd.get(iZza)).zza) {
                                                        com.google.android.play.core.internal.zzag zzagVar2 = zza;
                                                        Object[] objArr2 = new Object[5];
                                                        objArr2[c] = Integer.valueOf(zzdcVar.zze);
                                                        objArr2[1] = Integer.valueOf(zzdbVar.zza);
                                                        objArr2[2] = zzdbVar.zzc.zza;
                                                        objArr2[3] = zzdcVar.zza;
                                                        objArr2[4] = Integer.valueOf(iZza);
                                                        zzagVar2.zza("Found extraction task using compression format %s for session %s, pack %s, slice %s, chunk %s.", objArr2);
                                                        InputStream inputStreamZza = this.zzd.zza(zzdbVar.zza, zzdbVar.zzc.zza, zzdcVar.zza, iZza);
                                                        int i5 = zzdbVar.zza;
                                                        zzda zzdaVar12 = zzdbVar.zzc;
                                                        String str2 = zzdaVar12.zza;
                                                        int i6 = zzdbVar.zzb;
                                                        long j = zzdaVar12.zzb;
                                                        String str3 = zzdaVar12.zzc;
                                                        String str4 = zzdcVar.zza;
                                                        int i7 = zzdcVar.zze;
                                                        int size = zzdcVar.zzd.size();
                                                        zzda zzdaVar13 = zzdbVar.zzc;
                                                        zzceVar = new zzce(i5, str2, i6, j, str3, str4, i7, iZza, size, zzdaVar13.zze, zzdaVar13.zzd, inputStreamZza);
                                                        break loop7;
                                                    }
                                                    it6 = it8;
                                                    c = 0;
                                                }
                                            }
                                        }
                                    }
                                    if (zzceVar == null) {
                                        it5 = arrayList.iterator();
                                        loop9: while (true) {
                                            if (it5.hasNext()) {
                                                zzefVar = null;
                                                break;
                                            }
                                            zzdb zzdbVar7 = (zzdb) it5.next();
                                            zzdaVar = zzdbVar7.zzc;
                                            if (zzbg.zzb(zzdaVar.zzd)) {
                                                for (zzdc zzdcVar4 : zzdaVar.zzf) {
                                                    if (!zzc(zzdcVar4) && ((zzcz) zzdcVar4.zzd.get(0)).zza && !zzb(zzdbVar7, zzdcVar4)) {
                                                        zza.zza("Found patch slice task using patch format %s for session %s, pack %s, slice %s.", Integer.valueOf(zzdcVar4.zzf), Integer.valueOf(zzdbVar7.zza), zzdbVar7.zzc.zza, zzdcVar4.zza);
                                                        InputStream inputStreamZza2 = this.zzd.zza(zzdbVar7.zza, zzdbVar7.zzc.zza, zzdcVar4.zza, 0);
                                                        int i8 = zzdbVar7.zza;
                                                        String str5 = zzdbVar7.zzc.zza;
                                                        zzefVar = new zzef(i8, str5, this.zzc.zza(str5), this.zzc.zzc(zzdbVar7.zzc.zza), zzdbVar7.zzb, zzdbVar7.zzc.zzb, zzdcVar4.zzf, zzdcVar4.zza, zzdcVar4.zzc, inputStreamZza2);
                                                        break loop9;
                                                    }
                                                }
                                            }
                                        }
                                        if (zzefVar != null) {
                                            this.zzb.zzl();
                                            return null;
                                        }
                                        zzdeVar2 = this.zzb;
                                    } else {
                                        zzdeVar = this.zzb;
                                        zzceVar2 = zzceVar;
                                    }
                                } else {
                                    zzdeVar2 = this.zzb;
                                }
                            } else {
                                zzdeVar2 = this.zzb;
                            }
                        } else {
                            zzdeVar2 = this.zzb;
                        }
                    } else {
                        zzdeVar2 = this.zzb;
                    }
                } else {
                    it = arrayList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            zzefVar = null;
                            break;
                        }
                        zzdbVar4 = (zzdb) it.next();
                        zzbhVar3 = this.zzc;
                        zzdaVar7 = zzdbVar4.zzc;
                        if (zzbhVar3.zzb(zzdaVar7.zza, zzdbVar4.zzb, zzdaVar7.zzb) == zzdbVar4.zzc.zzf.size()) {
                            zza.zza("Found final move task for session %s with pack %s.", Integer.valueOf(zzdbVar4.zza), zzdbVar4.zzc.zza);
                            int i9 = zzdbVar4.zza;
                            zzda zzdaVar14 = zzdbVar4.zzc;
                            zzefVar = new zzdw(i9, zzdaVar14.zza, zzdbVar4.zzb, zzdaVar14.zzb, zzdaVar14.zzc);
                            break;
                        }
                    }
                    if (zzefVar == null) {
                        it2 = arrayList.iterator();
                        loop3: while (true) {
                            if (it2.hasNext()) {
                                zzefVar = null;
                                break;
                            }
                            zzdbVar3 = (zzdb) it2.next();
                            zzdaVar5 = zzdbVar3.zzc;
                            if (zzbg.zzb(zzdaVar5.zzd)) {
                                while (r9.hasNext()) {
                                    zzbhVar2 = this.zzc;
                                    zzdaVar6 = zzdbVar3.zzc;
                                    if (zzbhVar2.zzq(zzdaVar6.zza, zzdbVar3.zzb, zzdaVar6.zzb, zzdcVar2.zza).exists()) {
                                        zza.zza("Found merge task for session %s with pack %s and slice %s.", Integer.valueOf(zzdbVar3.zza), zzdbVar3.zzc.zza, zzdcVar2.zza);
                                        int i10 = zzdbVar3.zza;
                                        zzda zzdaVar15 = zzdbVar3.zzc;
                                        zzefVar = new zzdt(i10, zzdaVar15.zza, zzdbVar3.zzb, zzdaVar15.zzb, zzdcVar2.zza);
                                        break loop3;
                                    }
                                }
                            }
                        }
                        if (zzefVar == null) {
                            it3 = arrayList.iterator();
                            loop5: while (true) {
                                if (it3.hasNext()) {
                                    zzefVar = null;
                                    break;
                                }
                                zzdbVar2 = (zzdb) it3.next();
                                zzdaVar3 = zzdbVar2.zzc;
                                if (zzbg.zzb(zzdaVar3.zzd)) {
                                    while (r6.hasNext()) {
                                        if (zzb(zzdbVar2, zzdcVar3)) {
                                            zzbhVar = this.zzc;
                                            zzdaVar4 = zzdbVar2.zzc;
                                            if (zzbhVar.zzp(zzdaVar4.zza, zzdbVar2.zzb, zzdaVar4.zzb, zzdcVar3.zza).exists()) {
                                                zza.zza("Found verify task for session %s with pack %s and slice %s.", Integer.valueOf(zzdbVar2.zza), zzdbVar2.zzc.zza, zzdcVar3.zza);
                                                int i11 = zzdbVar2.zza;
                                                zzda zzdaVar16 = zzdbVar2.zzc;
                                                zzefVar = new zzeq(i11, zzdaVar16.zza, zzdbVar2.zzb, zzdaVar16.zzb, zzdcVar3.zza, zzdcVar3.zzb);
                                                break loop5;
                                            }
                                        }
                                    }
                                }
                            }
                            if (zzefVar == null) {
                                it4 = arrayList.iterator();
                                loop7: while (true) {
                                    if (it4.hasNext()) {
                                        zzceVar = null;
                                        break;
                                    }
                                    zzdbVar = (zzdb) it4.next();
                                    zzdaVar2 = zzdbVar.zzc;
                                    if (zzbg.zzb(zzdaVar2.zzd)) {
                                        it6 = zzdaVar2.zzf.iterator();
                                        while (it6.hasNext()) {
                                            zzdcVar = (zzdc) it6.next();
                                            if (!zzc(zzdcVar)) {
                                                zzbh zzbhVar5 = this.zzc;
                                                zzda zzdaVar17 = zzdbVar.zzc;
                                                Iterator it9 = it6;
                                                iZza = new zzen(zzbhVar5, zzdaVar17.zza, zzdbVar.zzb, zzdaVar17.zzb, zzdcVar.zza).zza();
                                                if (iZza == -1) {
                                                }
                                                it6 = it9;
                                                c = 0;
                                            }
                                        }
                                    }
                                }
                                if (zzceVar == null) {
                                    it5 = arrayList.iterator();
                                    loop9: while (true) {
                                        if (it5.hasNext()) {
                                            zzefVar = null;
                                            break;
                                        }
                                        zzdb zzdbVar8 = (zzdb) it5.next();
                                        zzdaVar = zzdbVar8.zzc;
                                        if (zzbg.zzb(zzdaVar.zzd)) {
                                            while (r3.hasNext()) {
                                                if (!zzc(zzdcVar4)) {
                                                }
                                            }
                                        }
                                    }
                                    if (zzefVar != null) {
                                        this.zzb.zzl();
                                        return null;
                                    }
                                    zzdeVar2 = this.zzb;
                                } else {
                                    zzdeVar = this.zzb;
                                    zzceVar2 = zzceVar;
                                }
                            } else {
                                zzdeVar2 = this.zzb;
                            }
                        } else {
                            zzdeVar2 = this.zzb;
                        }
                    } else {
                        zzdeVar2 = this.zzb;
                    }
                }
                zzdeVar2.zzl();
                return zzefVar;
            }
            zzdeVar = this.zzb;
            zzceVar2 = null;
            zzdeVar.zzl();
            return zzceVar2;
        } catch (Throwable th) {
            this.zzb.zzl();
            throw th;
        }
    }
}
