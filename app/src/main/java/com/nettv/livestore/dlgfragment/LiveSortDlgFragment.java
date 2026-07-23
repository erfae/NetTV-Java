package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class LiveSortDlgFragment extends DialogFragment {
    public LiveSortRecyclerAdapter adapter;
    public Button btn_cancel;
    public Button btn_ok;
    public Context context;
    public List<String> formatStrings;
    public ItemPositionListener listener;
    public RecyclerView recyclerTimes;
    public TextView txt_header;
    public WordModels wordModels = new WordModels();
    public int selected_position = 0;
    public String header = "";

    public interface ItemPositionListener {
        void onItemPosition(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        this.selected_position = num.intValue();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        this.listener.onItemPosition(this.selected_position);
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$2(View view) {
        dismiss();
    }

    public static LiveSortDlgFragment newInstance(Context context, List<String> list, int i, String str, ItemPositionListener itemPositionListener) {
        LiveSortDlgFragment liveSortDlgFragment = new LiveSortDlgFragment();
        liveSortDlgFragment.context = context;
        liveSortDlgFragment.formatStrings = list;
        liveSortDlgFragment.selected_position = i;
        liveSortDlgFragment.listener = itemPositionListener;
        liveSortDlgFragment.header = str;
        return liveSortDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final int i = 0;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_live_sort, viewGroup, false);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        this.btn_ok = (Button) viewInflate.findViewById(R.id.btn_ok);
        this.btn_cancel = (Button) viewInflate.findViewById(R.id.btn_cancel);
        this.txt_header = (TextView) viewInflate.findViewById(R.id.txt_header);
        this.recyclerTimes = (RecyclerView) viewInflate.findViewById(R.id.recyclerGroups);
        this.btn_ok.setText(this.wordModels.getOk());
        this.btn_cancel.setText(this.wordModels.getCancel());
        this.txt_header.setText(this.header);
        this.adapter = new LiveSortRecyclerAdapter(getContext(), this.formatStrings, this.selected_position, new EpisodeDlgFragment$$ExternalSyntheticLambda1(this, 6));
        this.recyclerTimes.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerTimes.setAdapter(this.adapter);
        this.recyclerTimes.smoothScrollToPosition(this.selected_position);
        this.recyclerTimes.requestFocus();
        this.btn_ok.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.LiveSortDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ LiveSortDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$onCreateView$1(view);
                        break;
                    default:
                        this.f$0.lambda$onCreateView$2(view);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.btn_cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.LiveSortDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ LiveSortDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$onCreateView$1(view);
                        break;
                    default:
                        this.f$0.lambda$onCreateView$2(view);
                        break;
                }
            }
        });
        return viewInflate;
    }
}
