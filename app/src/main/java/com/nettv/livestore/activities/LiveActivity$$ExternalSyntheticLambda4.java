package com.nettv.livestore.activities;

import com.nettv.livestore.activities.mobile.LiveChannelMobileActivity;
import com.nettv.livestore.activities.mobile.LiveMobileActivity;
import com.nettv.livestore.dlgfragment.LiveSearchDlgFragment;
import com.nettv.livestore.models.EPGChannel;
import kotlin.jvm.functions.Function4;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveActivity$$ExternalSyntheticLambda4 implements Function4 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LiveActivity$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        switch (this.$r8$classId) {
            case 0:
                return ((LiveActivity) this.f$0).lambda$onCreate$1((EPGChannel) obj, (Integer) obj2, (Boolean) obj3, (Boolean) obj4);
            case 1:
                return ((AddGroupActivity) this.f$0).lambda$onCreate$0((String) obj, (Integer) obj2, (Boolean) obj3, (Boolean) obj4);
            case 2:
                return ((LiveChannelActivity) this.f$0).lambda$onCreate$0((EPGChannel) obj, (Integer) obj2, (Boolean) obj3, (Boolean) obj4);
            case 3:
                return ((LiveChannelMobileActivity) this.f$0).lambda$onCreate$0((EPGChannel) obj, (Integer) obj2, (Boolean) obj3, (Boolean) obj4);
            case 4:
                return ((LiveMobileActivity) this.f$0).lambda$onCreate$1((EPGChannel) obj, (Integer) obj2, (Boolean) obj3, (Boolean) obj4);
            default:
                return ((LiveSearchDlgFragment) this.f$0).lambda$searchLiveChannels$1((EPGChannel) obj, (Integer) obj2, (Boolean) obj3, (Boolean) obj4);
        }
    }
}
