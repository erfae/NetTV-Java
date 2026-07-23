package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.models.WordModels;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AddGroupDlgFragment extends DialogFragment {
    public ImageButton btn_back;
    public Button btn_ok;
    public OnAddedGroupListener listener;
    public String originalName;
    public PreferenceHelper preferenceHelper;
    public TextView txt_add;
    public EditText txt_name;
    public TextView txt_pick_name;
    public WordModels wordModels;
    public List<String> myGroupNames = new ArrayList();
    public boolean is_rename = false;

    public interface OnAddedGroupListener {
        void onAddedGroup(String str);
    }

    private void addGroupToModel() {
        if (this.txt_name.getText().toString().isEmpty()) {
            return;
        }
        String strTrim = this.txt_name.getText().toString().trim();
        if (this.myGroupNames.contains(strTrim)) {
            Toast.makeText(getContext(), this.wordModels.getStr_contain_this_name(), 0).show();
            return;
        }
        this.myGroupNames.add(strTrim);
        this.preferenceHelper.setSharedPreferenceMyGroupCategory(this.myGroupNames);
        this.listener.onAddedGroup(strTrim);
    }

    private void initView(View view) {
        this.btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        this.txt_add = (TextView) view.findViewById(R.id.txt_add);
        this.txt_pick_name = (TextView) view.findViewById(R.id.txt_pick_name);
        this.txt_name = (EditText) view.findViewById(R.id.txt_name);
        this.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        this.txt_add.setText(this.wordModels.getStr_my_group());
        this.txt_pick_name.setText(this.wordModels.getStr_group_name());
        this.btn_ok.setText(this.wordModels.getOk());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        addGroupToModel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        dismiss();
    }

    public static AddGroupDlgFragment newInstance(boolean z, String str) {
        AddGroupDlgFragment addGroupDlgFragment = new AddGroupDlgFragment();
        addGroupDlgFragment.is_rename = z;
        addGroupDlgFragment.originalName = str;
        return addGroupDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_add_group, viewGroup);
        this.preferenceHelper = new PreferenceHelper(getContext());
        this.wordModels = GetSharedInfo.getWordModel(getContext());
        initView(viewInflate);
        if (this.is_rename) {
            this.txt_name.setText(this.originalName);
        }
        this.myGroupNames = this.preferenceHelper.getSharedPreferenceMyGroupCategory();
        final int i = 0;
        this.btn_ok.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.AddGroupDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ AddGroupDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$onCreateView$0(view);
                        break;
                    default:
                        this.f$0.lambda$onCreateView$1(view);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.btn_back.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.AddGroupDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ AddGroupDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$onCreateView$0(view);
                        break;
                    default:
                        this.f$0.lambda$onCreateView$1(view);
                        break;
                }
            }
        });
        this.txt_name.requestFocus();
        return viewInflate;
    }

    public void setOnAddedGroupListener(OnAddedGroupListener onAddedGroupListener) {
        this.listener = onAddedGroupListener;
    }
}
