package com.nettv.livestore.dlgfragment;

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

/* JADX INFO: loaded from: classes2.dex */
public class ExitDlgFragment extends DialogFragment {
    public Button btn_cancel;
    public Button btn_ok;
    public String description;
    public String header;
    public OkButtonClickListener listener;
    public String str_no;
    public String str_yes;
    public TextView txt_description;
    public TextView txt_quit;

    public interface OkButtonClickListener {
        void onCancelClick();

        void onOkClick();
    }

    private void initView(View view) {
        this.txt_quit = (TextView) view.findViewById(R.id.txt_quit);
        this.txt_description = (TextView) view.findViewById(R.id.txt_description);
        this.txt_quit.setText(this.header);
        this.txt_description.setText(this.description);
        this.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button button = (Button) view.findViewById(R.id.btn_cancel);
        this.btn_cancel = button;
        final int i = 0;
        button.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.ExitDlgFragment$$ExternalSyntheticLambda1
            public final /* synthetic */ ExitDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$initView$1(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$2(view2);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.btn_ok.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.ExitDlgFragment$$ExternalSyntheticLambda1
            public final /* synthetic */ ExitDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$initView$1(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$2(view2);
                        break;
                }
            }
        });
        this.btn_ok.setText(this.str_yes);
        this.btn_cancel.setText(this.str_no);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        this.listener.onCancelClick();
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        this.listener.onOkClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return false;
        }
        dismiss();
        return true;
    }

    public static ExitDlgFragment newInstance(String str, String str2, String str3, String str4) {
        ExitDlgFragment exitDlgFragment = new ExitDlgFragment();
        exitDlgFragment.header = str;
        exitDlgFragment.description = str2;
        exitDlgFragment.str_yes = str3;
        exitDlgFragment.str_no = str4;
        return exitDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_exit, viewGroup, false);
        initView(viewInflate);
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 0));
        this.btn_cancel.requestFocus();
        return viewInflate;
    }

    public void setOkButtonClickListener(OkButtonClickListener okButtonClickListener) {
        this.listener = okButtonClickListener;
    }
}
