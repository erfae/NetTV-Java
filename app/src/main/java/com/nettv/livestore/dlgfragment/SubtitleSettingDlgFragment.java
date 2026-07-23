package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.MovieActivity$$ExternalSyntheticLambda2;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.WordModels;

/* JADX INFO: loaded from: classes2.dex */
public class SubtitleSettingDlgFragment extends DialogFragment implements View.OnClickListener {
    public ImageButton btn_minus;
    public ImageButton btn_plus;
    public Context context;
    public ImageView image_background_color;
    public ImageView image_subtitle_color;
    public ConstraintLayout ly_bg_color;
    public ConstraintLayout ly_color;
    public ConstraintLayout ly_enable;
    public PreferenceHelper preferenceHelper;
    public SelectColorDlgFragment selectColorDlgFragment;
    public TextView str_bg_color;
    public TextView str_enable;
    public TextView str_subtitle_size;
    public TextView str_text_color;
    public int subtitle_size;
    public SwitchCompat switch_subtitle;
    public TextView txt_header;
    public TextView txt_subtitle_size;
    public boolean is_enable = false;
    public int step = 5;
    public int min_size = 12;
    public int max_size = 60;
    public WordModels wordModels = new WordModels();

    private void initView(View view) {
        this.txt_header = (TextView) view.findViewById(R.id.txt_header);
        this.str_enable = (TextView) view.findViewById(R.id.str_enable);
        this.str_subtitle_size = (TextView) view.findViewById(R.id.str_subtitle_size);
        this.str_text_color = (TextView) view.findViewById(R.id.str_text_color);
        this.str_bg_color = (TextView) view.findViewById(R.id.str_bg_color);
        this.txt_subtitle_size = (TextView) view.findViewById(R.id.txt_subtitle_size);
        this.ly_enable = (ConstraintLayout) view.findViewById(R.id.ly_enable);
        this.ly_color = (ConstraintLayout) view.findViewById(R.id.ly_text_color);
        this.ly_bg_color = (ConstraintLayout) view.findViewById(R.id.ly_bg_color);
        this.btn_minus = (ImageButton) view.findViewById(R.id.btn_minus);
        this.btn_plus = (ImageButton) view.findViewById(R.id.btn_plus);
        this.image_subtitle_color = (ImageView) view.findViewById(R.id.image_subtitle_color);
        this.image_background_color = (ImageView) view.findViewById(R.id.image_background_color);
        this.switch_subtitle = (SwitchCompat) view.findViewById(R.id.switch_subtitle);
        this.ly_enable.setOnClickListener(this);
        this.ly_color.setOnClickListener(this);
        this.ly_bg_color.setOnClickListener(this);
        this.btn_minus.setOnClickListener(this);
        this.btn_plus.setOnClickListener(this);
        this.str_subtitle_size.setText(this.wordModels.getSubtitel_size());
        this.txt_header.setText(this.wordModels.getSubtitle_settings());
        this.str_enable.setText(this.wordModels.getEnable_subtitles());
        this.str_text_color.setText(this.wordModels.getSubtitel_color());
        this.str_bg_color.setText(this.wordModels.getSubtitel_background());
        this.txt_subtitle_size.setText(this.subtitle_size + "pt");
        this.switch_subtitle.setChecked(this.is_enable);
        this.image_subtitle_color.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()));
        this.image_background_color.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectColorDlgFragment$0() {
        this.image_subtitle_color.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleColor()));
        this.image_background_color.setBackgroundColor(Color.parseColor(this.preferenceHelper.getSharedPreferenceSubtitleBgColor()));
    }

    public static SubtitleSettingDlgFragment newInstance(Context context) {
        SubtitleSettingDlgFragment subtitleSettingDlgFragment = new SubtitleSettingDlgFragment();
        subtitleSettingDlgFragment.context = context;
        return subtitleSettingDlgFragment;
    }

    private void showSelectColorDlgFragment(boolean z) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
        Fragment fragmentFindFragmentByTag = childFragmentManager.findFragmentByTag("fragment_subtitle_color");
        if (fragmentFindFragmentByTag != null) {
            MainActivity$$ExternalSyntheticOutline0.m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        SelectColorDlgFragment selectColorDlgFragmentNewInstance = SelectColorDlgFragment.newInstance(getContext(), z);
        this.selectColorDlgFragment = selectColorDlgFragmentNewInstance;
        selectColorDlgFragmentNewInstance.setOnChangeColorListener(new MovieActivity$$ExternalSyntheticLambda2(this, 11));
        this.selectColorDlgFragment.show(childFragmentManager, "fragment_subtitle_color");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_minus /* 2131427476 */:
                int i = this.subtitle_size;
                int i2 = this.min_size;
                int i3 = this.step;
                if (i > i2 + i3) {
                    this.subtitle_size = i - i3;
                    this.txt_subtitle_size.setText(this.subtitle_size + "pt");
                    this.preferenceHelper.setSharedPreferenceSubtitleSize(this.subtitle_size);
                }
                break;
            case R.id.btn_plus /* 2131427481 */:
                int i4 = this.subtitle_size;
                int i5 = this.max_size;
                int i6 = this.step;
                if (i4 < i5 - i6) {
                    this.subtitle_size = i4 + i6;
                    this.txt_subtitle_size.setText(this.subtitle_size + "pt");
                    this.preferenceHelper.setSharedPreferenceSubtitleSize(this.subtitle_size);
                }
                break;
            case R.id.ly_bg_color /* 2131427892 */:
                showSelectColorDlgFragment(false);
                break;
            case R.id.ly_enable /* 2131427902 */:
                boolean z = !this.is_enable;
                this.is_enable = z;
                this.switch_subtitle.setChecked(z);
                this.preferenceHelper.setSharedPreferenceSubtitleEnable(this.is_enable);
                break;
            case R.id.ly_text_color /* 2131427926 */:
                showSelectColorDlgFragment(true);
                break;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_subtitle, viewGroup, false);
        this.preferenceHelper = new PreferenceHelper(this.context);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        if (GetSharedInfo.isTVDevice(this.context)) {
            this.step = 5;
            this.min_size = 12;
            this.max_size = 1000;
        } else {
            this.step = 2;
            this.min_size = 6;
            this.max_size = 48;
        }
        this.subtitle_size = this.preferenceHelper.getSharedPreferenceSubtitleSize();
        this.is_enable = this.preferenceHelper.getSharedPreferenceSubtitleEnable();
        initView(viewInflate);
        return viewInflate;
    }
}
