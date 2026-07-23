package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nettv.livestore.R;

/* JADX INFO: loaded from: classes2.dex */
public class PlayErrorDlgFragment extends DialogFragment {
    public Button btn_cancel;
    public Button btn_ok;
    public String description;
    public String header;
    public boolean is_vod = false;
    public OkButtonClickListener listener;
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
        this.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        this.btn_ok.setText("Exit");
        this.btn_cancel.setText("Play Next");
        final int i = 1;
        this.txt_quit.setAllCaps(true);
        if (this.is_vod) {
            this.btn_cancel.setVisibility(8);
            this.btn_ok.requestFocus();
        } else {
            this.btn_cancel.requestFocus();
        }
        final int i2 = 0;
        this.btn_cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.PlayErrorDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ PlayErrorDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                    case 0:
                        this.f$0.lambda$initView$0(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$1(view2);
                        break;
                }
            }
        });
        this.btn_ok.setOnClickListener(new View.OnClickListener(this) { // from class: com.nettv.livestore.dlgfragment.PlayErrorDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ PlayErrorDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                    case 0:
                        this.f$0.lambda$initView$0(view2);
                        break;
                    default:
                        this.f$0.lambda$initView$1(view2);
                        break;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        this.listener.onCancelClick();
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        this.listener.onOkClick();
    }

    public static PlayErrorDlgFragment newInstance(String str, String str2, boolean z) {
        PlayErrorDlgFragment playErrorDlgFragment = new PlayErrorDlgFragment();
        playErrorDlgFragment.header = str;
        playErrorDlgFragment.description = str2;
        playErrorDlgFragment.is_vod = z;
        return playErrorDlgFragment;
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
        return viewInflate;
    }

    public void setOkButtonClickListener(OkButtonClickListener okButtonClickListener) {
        this.listener = okButtonClickListener;
    }
}
