package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.SubtitleColorRecyclerAdapter;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.view.LiveVerticalGridView;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class SelectColorDlgFragment extends DialogFragment {
    public String[] backgroundColors;
    public Context context;
    public ChangeColorListener listener;
    public PreferenceHelper preferenceHelper;
    public LiveVerticalGridView recycler_colors;
    public String[] subtitleColors;
    public TextView txt_header;
    public TextView txt_subtitle;
    public int selected_position = 0;
    public boolean is_subtitle_color = true;
    public WordModels wordModels = new WordModels();

    public interface ChangeColorListener {
        void onChangeColor();
    }

    private void initView(View view) {
        this.txt_header = (TextView) view.findViewById(R.id.txt_header);
        this.txt_subtitle = (TextView) view.findViewById(R.id.txt_subtitle);
        this.recycler_colors = (LiveVerticalGridView) view.findViewById(R.id.recycler_colors);
        if (GetSharedInfo.isTVDevice(this.context)) {
            this.recycler_colors.setNumColumns(5);
            this.recycler_colors.setLoop(false);
            this.recycler_colors.setPreserveFocusAfterLayout(true);
            final View[] viewArr = {null};
            this.recycler_colors.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.dlgfragment.SelectColorDlgFragment.1
                @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                    super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                    View[] viewArr2 = viewArr;
                    if (viewArr2[0] != null) {
                        viewArr2[0].setSelected(false);
                        View[] viewArr3 = viewArr;
                        viewArr3[0] = viewHolder.itemView;
                        viewArr3[0].setSelected(true);
                    }
                }
            });
        } else {
            this.recycler_colors.setLayoutManager(new GridLayoutManager(this.context, 5));
            this.recycler_colors.setHasFixedSize(true);
        }
        if (this.is_subtitle_color) {
            this.txt_header.setText(this.wordModels.getSubtitel_color());
        } else {
            this.txt_header.setText(this.wordModels.getSubtitel_background());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        this.selected_position = num.intValue();
        if (this.is_subtitle_color) {
            this.txt_subtitle.setTextColor(Color.parseColor(this.subtitleColors[num.intValue()]));
            this.preferenceHelper.setSharedPreferenceSubtitleColor(this.subtitleColors[num.intValue()]);
            return null;
        }
        this.txt_subtitle.setBackgroundColor(Color.parseColor(this.backgroundColors[num.intValue()]));
        this.preferenceHelper.setSharedPreferenceSubtitleBgColor(this.backgroundColors[num.intValue()]);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$1(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return false;
        }
        this.listener.onChangeColor();
        return false;
    }

    public static SelectColorDlgFragment newInstance(Context context, boolean z) {
        SelectColorDlgFragment selectColorDlgFragment = new SelectColorDlgFragment();
        selectColorDlgFragment.context = context;
        selectColorDlgFragment.is_subtitle_color = z;
        return selectColorDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_select_color, viewGroup, false);
        this.preferenceHelper = new PreferenceHelper(this.context);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        initView(viewInflate);
        this.subtitleColors = getResources().getStringArray(R.array.subtitle_colors);
        String[] stringArray = getResources().getStringArray(R.array.subtitle_background_colors);
        this.backgroundColors = stringArray;
        if (this.is_subtitle_color) {
            this.selected_position = GetSharedInfo.getSubtitleColorPosition(this.context, this.subtitleColors);
        } else {
            this.selected_position = GetSharedInfo.getSubtitleBgColorPosition(this.context, stringArray);
        }
        this.txt_subtitle.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()));
        this.txt_subtitle.setTextColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()));
        this.txt_subtitle.setTextSize(3, this.preferenceHelper.getSharedPreferenceSubtitleSize());
        this.recycler_colors.setAdapter(new SubtitleColorRecyclerAdapter(this.is_subtitle_color ? this.subtitleColors : this.backgroundColors, this.selected_position, new EpisodeDlgFragment$$ExternalSyntheticLambda1(this, 7)));
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 10));
        return viewInflate;
    }

    public void setOnChangeColorListener(ChangeColorListener changeColorListener) {
        this.listener = changeColorListener;
    }
}
