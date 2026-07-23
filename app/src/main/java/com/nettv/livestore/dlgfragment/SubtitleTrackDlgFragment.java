package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.LiveSortRecyclerAdapter;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.WordModels;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class SubtitleTrackDlgFragment extends DialogFragment {
    public LiveSortRecyclerAdapter adapter;
    public Context context;
    public List<String> formatStrings;
    public ItemPositionListener listener;
    public RecyclerView recyclerTimes;
    public TextView txt_header;
    public WordModels wordModels = new WordModels();
    public int selected_position = 0;

    public interface ItemPositionListener {
        void onItemPosition(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        dismiss();
        int iIntValue = num.intValue();
        this.selected_position = iIntValue;
        this.listener.onItemPosition(iIntValue);
        return null;
    }

    public static SubtitleTrackDlgFragment newInstance(Context context, List<String> list, int i, ItemPositionListener itemPositionListener) {
        SubtitleTrackDlgFragment subtitleTrackDlgFragment = new SubtitleTrackDlgFragment();
        subtitleTrackDlgFragment.context = context;
        subtitleTrackDlgFragment.formatStrings = list;
        subtitleTrackDlgFragment.selected_position = i;
        subtitleTrackDlgFragment.listener = itemPositionListener;
        return subtitleTrackDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_subtitle_track, viewGroup, false);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        this.txt_header = (TextView) viewInflate.findViewById(R.id.txt_header);
        this.recyclerTimes = (RecyclerView) viewInflate.findViewById(R.id.recyclerGroups);
        this.txt_header.setText(this.wordModels.getSubtitle());
        this.adapter = new LiveSortRecyclerAdapter(getContext(), this.formatStrings, this.selected_position, new EpisodeDlgFragment$$ExternalSyntheticLambda1(this, 8));
        this.recyclerTimes.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerTimes.setAdapter(this.adapter);
        this.recyclerTimes.scrollToPosition(this.selected_position);
        this.recyclerTimes.requestFocus();
        return viewInflate;
    }
}
