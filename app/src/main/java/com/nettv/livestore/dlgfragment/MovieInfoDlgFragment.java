package com.nettv.livestore.dlgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nettv.livestore.R;
import com.nettv.livestore.activities.SearchActivity$$ExternalSyntheticLambda0;
import com.nettv.livestore.utils.ImageLoaderJava;

/* JADX INFO: loaded from: classes2.dex */
public class MovieInfoDlgFragment extends DialogFragment {
    public String description;
    public ImageView image_logo;
    public RoundedImageView image_movie;
    public String image_url;
    public String name;
    public String resolution;
    public TextView txt_description;
    public TextView txt_name;
    public TextView txt_resolution;

    private void initView(View view) {
        this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        this.txt_description = (TextView) view.findViewById(R.id.txt_description);
        this.txt_resolution = (TextView) view.findViewById(R.id.txt_resolution);
        this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
        this.image_movie = (RoundedImageView) view.findViewById(R.id.image_movie);
        view.findViewById(R.id.view_click).setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 11));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        dismiss();
    }

    public static MovieInfoDlgFragment newInstance(String str, String str2, String str3, String str4) {
        MovieInfoDlgFragment movieInfoDlgFragment = new MovieInfoDlgFragment();
        movieInfoDlgFragment.name = str;
        movieInfoDlgFragment.description = str2;
        movieInfoDlgFragment.image_url = str3;
        movieInfoDlgFragment.resolution = str4;
        return movieInfoDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.dlg_movie_info, viewGroup, false);
        initView(viewInflate);
        this.txt_name.setText(this.name);
        this.txt_description.setText(this.description);
        this.txt_resolution.setText(this.resolution);
        ImageLoaderJava.imageLoadUrlWithVodHolder(getContext(), this.image_movie, this.image_url, R.drawable.default_bg, this.image_logo);
        return viewInflate;
    }
}
