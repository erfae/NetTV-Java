package com.nettv.livestore.dlgfragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.PlayEpisodeRecyclerAdapter;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.WordModels;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class EpisodeDlgFragment extends DialogFragment {
    public PlayEpisodeRecyclerAdapter adapter;
    public ImageButton btn_close;
    public List<EpisodeModel> episodeModels;
    public int episode_pos;
    public OnEpisodeItemListener listener;
    public RecyclerView recycler_episodes;
    public int season_pos;
    public TextView str_episodes;
    public TextView txt_see_all;
    public WordModels wordModels = new WordModels();

    public interface OnEpisodeItemListener {
        void OnEpisodeSelected(EpisodeModel episodeModel, int i);

        void OnSeeAllEpisodes();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void initView(View view) {
        this.str_episodes = (TextView) view.findViewById(R.id.str_episodes);
        this.txt_see_all = (TextView) view.findViewById(R.id.txt_see_all);
        this.btn_close = (ImageButton) view.findViewById(R.id.btn_close);
        this.recycler_episodes = (RecyclerView) view.findViewById(R.id.recycler_episodes);
        this.str_episodes.setText(this.wordModels.getStr_more_episodes());
        this.txt_see_all.setText(this.wordModels.getSee_all());
        final int i = 0;
        this.btn_close.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.nettv.livestore.dlgfragment.EpisodeDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ EpisodeDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                switch (i) {
                    case 0:
                        return this.f$0.lambda$initView$2(view2, motionEvent);
                    default:
                        return this.f$0.lambda$initView$3(view2, motionEvent);
                }
            }
        });
        final int i2 = 1;
        this.txt_see_all.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.nettv.livestore.dlgfragment.EpisodeDlgFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ EpisodeDlgFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                switch (i2) {
                    case 0:
                        return this.f$0.lambda$initView$2(view2, motionEvent);
                    default:
                        return this.f$0.lambda$initView$3(view2, motionEvent);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$2(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3) {
            this.btn_close.setColorFilter(getResources().getColor(R.color.trans_parent));
        }
        if (motionEvent.getAction() == 0) {
            this.btn_close.setColorFilter(getResources().getColor(R.color.hover_color));
            return true;
        }
        if (motionEvent.getAction() != 1) {
            return false;
        }
        this.btn_close.setColorFilter(getResources().getColor(R.color.trans_parent));
        dismiss();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$3(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3) {
            this.txt_see_all.setTextColor(getResources().getColor(R.color.gray));
        }
        if (motionEvent.getAction() == 0) {
            this.txt_see_all.setTextColor(getResources().getColor(R.color.hover_color));
            return true;
        }
        if (motionEvent.getAction() != 1) {
            return false;
        }
        this.txt_see_all.setTextColor(getResources().getColor(R.color.gray));
        this.listener.OnSeeAllEpisodes();
        dismiss();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreateView$0(EpisodeModel episodeModel, Integer num) {
        this.listener.OnEpisodeSelected(episodeModel, num.intValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreateView$1(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return false;
        }
        dismiss();
        return true;
    }

    public static EpisodeDlgFragment newInstance(List<EpisodeModel> list, int i, int i2) {
        EpisodeDlgFragment episodeDlgFragment = new EpisodeDlgFragment();
        episodeDlgFragment.episodeModels = list;
        episodeDlgFragment.season_pos = i;
        episodeDlgFragment.episode_pos = i2;
        return episodeDlgFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_episode, viewGroup, false);
        this.wordModels = GetSharedInfo.getWordModel(getContext());
        initView(viewInflate);
        this.adapter = new PlayEpisodeRecyclerAdapter(getContext(), this.episodeModels, this.season_pos, this.episode_pos, new EpisodeDlgFragment$$ExternalSyntheticLambda1(this, 0));
        this.recycler_episodes.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recycler_episodes.setHasFixedSize(true);
        this.recycler_episodes.setAdapter(this.adapter);
        this.recycler_episodes.scrollToPosition(this.episode_pos);
        this.recycler_episodes.requestFocus();
        getDialog().setOnKeyListener(new ExitDlgFragment$$ExternalSyntheticLambda0(this, 3));
        return viewInflate;
    }

    public void setOnEpisodeItemListener(OnEpisodeItemListener onEpisodeItemListener) {
        this.listener = onEpisodeItemListener;
    }
}
