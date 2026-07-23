package com.nettv.livestore.activities;

import com.nettv.livestore.activities.mobile.LiveMobileActivity;
import com.nettv.livestore.apps.SideMenu;
import com.nettv.livestore.dlgfragment.ConnectDlgFragment;
import com.nettv.livestore.dlgfragment.GroupDlgFragment;
import com.nettv.livestore.dlgfragment.RenameGroupDlgFragment;
import com.nettv.livestore.dlgfragment.SearchChannelDlgFragment;
import com.nettv.livestore.models.AppInfoModel;
import com.nettv.livestore.models.CastModel;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.EpisodeModel;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveActivity$$ExternalSyntheticLambda3 implements Function3 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LiveActivity$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                return ((LiveActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 1:
                return ((AddGroupActivity) this.f$0).lambda$onCreate$1((String) obj, (Integer) obj2, (Boolean) obj3);
            case 2:
                return ((ChangePlaylistActivity) this.f$0).lambda$onCreate$0((AppInfoModel.UrlModel) obj, (Integer) obj2, (Boolean) obj3);
            case 3:
                return ((MovieActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 4:
                return ((MovieInfoActivity) this.f$0).lambda$initView$0((CastModel) obj, (Integer) obj2, (Boolean) obj3);
            case 5:
                return ((SeriesActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 6:
                return ((SeriesPlayerActivity) this.f$0).lambda$onCreate$0((EpisodeModel) obj, (Integer) obj2, (Boolean) obj3);
            case 7:
                return ((SettingActivity) this.f$0).lambda$onCreate$0((SideMenu) obj, (Integer) obj2, (Boolean) obj3);
            case 8:
                return ((LiveMobileActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 9:
                return ((ConnectDlgFragment) this.f$0).lambda$onCreateView$0((SideMenu) obj, (Integer) obj2, (Boolean) obj3);
            case 10:
                return ((GroupDlgFragment) this.f$0).lambda$onCreateView$1((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 11:
                return ((RenameGroupDlgFragment) this.f$0).lambda$onCreateView$0((SideMenu) obj, (Integer) obj2, (Boolean) obj3);
            default:
                return ((SearchChannelDlgFragment) this.f$0).lambda$onCreateView$0((EPGChannel) obj, (Integer) obj2, (Boolean) obj3);
        }
    }
}
