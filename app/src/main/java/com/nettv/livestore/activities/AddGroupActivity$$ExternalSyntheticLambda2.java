package com.nettv.livestore.activities;

import com.nettv.livestore.dlgfragment.AddChannelDlgFragment;
import com.nettv.livestore.dlgfragment.RenameGroupDlgFragment;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class AddGroupActivity$$ExternalSyntheticLambda2 implements RenameGroupDlgFragment.SelectList, AddChannelDlgFragment.onSendMyGroupNamesListener {
    public final /* synthetic */ AddGroupActivity f$0;

    public /* synthetic */ AddGroupActivity$$ExternalSyntheticLambda2(AddGroupActivity addGroupActivity) {
        this.f$0 = addGroupActivity;
    }

    @Override // com.nettv.livestore.dlgfragment.RenameGroupDlgFragment.SelectList
    public final void onSelect(int i) {
        this.f$0.lambda$showRenameDlgFragment$3(i);
    }

    @Override // com.nettv.livestore.dlgfragment.AddChannelDlgFragment.onSendMyGroupNamesListener
    public final void onSendMyGroup(List list) {
        this.f$0.lambda$showAddChannelDlgFragment$7(list);
    }
}
