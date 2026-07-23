package com.nettv.livestore.dlgfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.WordModels;

/* JADX INFO: loaded from: classes2.dex */
public class NoConnectionDlgFragment extends DialogFragment {
    public Button btn_retry;
    public Context context;
    public OnRetryClickListener listener;
    public TextView txt_check_network;
    public TextView txt_no_connection;
    public WordModels wordModels = new WordModels();
    public String description = "";

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    private void initView(View view) {
        this.txt_no_connection = (TextView) view.findViewById(R.id.txt_no_connection);
        this.txt_check_network = (TextView) view.findViewById(R.id.txt_check_network);
        this.txt_no_connection.setText(this.wordModels.getNo_connection());
        this.txt_check_network.setText(this.description);
        Button button = (Button) view.findViewById(R.id.btn_retry);
        this.btn_retry = button;
        button.setText(this.wordModels.getRetry());
        this.btn_retry.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 12));
        this.btn_retry.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        dismiss();
        this.listener.onRetryClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return false;
        }
        dismiss();
        return true;
    }

    public static NoConnectionDlgFragment newInstance(Context context, String str) {
        NoConnectionDlgFragment noConnectionDlgFragment = new NoConnectionDlgFragment();
        noConnectionDlgFragment.context = context;
        noConnectionDlgFragment.description = str;
        return noConnectionDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_no_connection, viewGroup, false);
        this.wordModels = GetSharedInfo.getWordModel(this.context);
        initView(viewInflate);
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 7));
        return viewInflate;
    }

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        this.listener = onRetryClickListener;
    }
}
