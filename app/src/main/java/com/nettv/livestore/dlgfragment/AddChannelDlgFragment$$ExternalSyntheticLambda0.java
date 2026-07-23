package com.nettv.livestore.dlgfragment;

import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class AddChannelDlgFragment$$ExternalSyntheticLambda0 implements GroupDlgFragment.onSelectCategoryListener, SearchChannelDlgFragment.onSearchChannelListener {
    public final /* synthetic */ AddChannelDlgFragment f$0;

    public /* synthetic */ AddChannelDlgFragment$$ExternalSyntheticLambda0(AddChannelDlgFragment addChannelDlgFragment) {
        this.f$0 = addChannelDlgFragment;
    }

    @Override // com.nettv.livestore.dlgfragment.SearchChannelDlgFragment.onSearchChannelListener
    public final void onSearchChannelNames(List list) {
        this.f$0.lambda$showSearchDlgFragment$4(list);
    }

    @Override // com.nettv.livestore.dlgfragment.GroupDlgFragment.onSelectCategoryListener
    public final void onSelectCategory(int i) {
        this.f$0.lambda$showGroupDlgFragment$3(i);
    }
}
