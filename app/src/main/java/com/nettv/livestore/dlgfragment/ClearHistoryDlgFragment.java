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
import com.nettv.livestore.adapter.HideCategoryRecyclerViewAdapter;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.ResumeModel;
import com.nettv.livestore.models.ResumeSeriesModel;
import com.nettv.livestore.models.WordModels;
import io.realm.Realm$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: loaded from: classes2.dex */
public class ClearHistoryDlgFragment extends DialogFragment implements View.OnClickListener {
    public HideCategoryRecyclerViewAdapter adapter;
    public Button btn_all;
    public Button btn_cancel;
    public Button btn_ok;
    public boolean[] checkedItems;
    public Context context;
    public int id;
    public String[] recent_names;
    public RecyclerView recyclerGroups;
    public PreferenceHelper sharedPreferenceHelper;
    public TextView txt_header;
    public List<ResumeModel> resumeModels = new ArrayList();
    public List<ResumeSeriesModel> resumeSeriesModels = new ArrayList();
    public List<String> recentlyNames = new ArrayList();
    public WordModels wordModels = new WordModels();

    private void clearRecentMoviesFromRealm() {
        for (ResumeModel resumeModel : this.resumeModels) {
            RealmController.with().addPositionToMovies(resumeModel.getName(), resumeModel.getTmdb_id(), false, 0L, 0, Realm$$ExternalSyntheticLambda0.INSTANCE$25);
        }
    }

    private void clearRecentSeriesFromRealm() {
        Iterator<ResumeSeriesModel> it = this.resumeSeriesModels.iterator();
        while (it.hasNext()) {
            RealmController.with().addToRecentSeries(it.next().getName(), false, 0, 0, Realm$$ExternalSyntheticLambda0.INSTANCE$27);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.ArrayList, java.util.List<java.lang.String>] */
    private List<ResumeSeriesModel> getSeriesResumeModels() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.recentlyNames) {
            for (ResumeSeriesModel resumeSeriesModel : this.resumeSeriesModels) {
                if (str.equalsIgnoreCase(resumeSeriesModel.getName())) {
                    arrayList.add(resumeSeriesModel);
                    break;
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.ArrayList, java.util.List<java.lang.String>] */
    private List<ResumeModel> getVodResumeModels() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.recentlyNames) {
            for (ResumeModel resumeModel : this.resumeModels) {
                if (str.equalsIgnoreCase(resumeModel.getName())) {
                    arrayList.add(resumeModel);
                    break;
                }
            }
        }
        return arrayList;
    }

    private void initView(View view) {
        this.txt_header = (TextView) view.findViewById(R.id.txt_header);
        this.recyclerGroups = (RecyclerView) view.findViewById(R.id.recyclerGroups);
        this.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        this.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        this.btn_all = (Button) view.findViewById(R.id.btn_all);
        this.btn_ok.setOnClickListener(this);
        this.btn_cancel.setOnClickListener(this);
        this.btn_all.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearRecentMoviesFromRealm$1() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearRecentSeriesFromRealm$2() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public /* synthetic */ Unit lambda$onCreateView$0(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            if (!this.recentlyNames.contains(this.recent_names[num.intValue()])) {
                return null;
            }
            this.recentlyNames.removeAll(Collections.singletonList(this.recent_names[num.intValue()]));
            return null;
        }
        if (this.recentlyNames.contains(this.recent_names[num.intValue()])) {
            return null;
        }
        this.recentlyNames.add(this.recent_names[num.intValue()]);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setProToVod$3() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setRecentSeries$4() {
    }

    public static ClearHistoryDlgFragment newInstance(Context context, int i) {
        ClearHistoryDlgFragment clearHistoryDlgFragment = new ClearHistoryDlgFragment();
        clearHistoryDlgFragment.context = context;
        clearHistoryDlgFragment.id = i;
        return clearHistoryDlgFragment;
    }

    private void setProToVod() {
        List<ResumeModel> sharedPreferenceResumeModel = this.sharedPreferenceHelper.getSharedPreferenceResumeModel();
        if (sharedPreferenceResumeModel.size() > 0) {
            for (ResumeModel resumeModel : sharedPreferenceResumeModel) {
                RealmController.with().addPositionToMovies(resumeModel.getName(), resumeModel.getTmdb_id(), true, resumeModel.getLast_position(), resumeModel.getPro(), Realm$$ExternalSyntheticLambda0.INSTANCE$24);
            }
        }
    }

    private void setRecentSeries() {
        for (ResumeSeriesModel resumeSeriesModel : this.sharedPreferenceHelper.getSharedPreferenceRecentSeriesNames()) {
            RealmController.with().addToRecentSeries(resumeSeriesModel.getName(), true, resumeSeriesModel.getSeason_pos(), resumeSeriesModel.getEpisode_pos(), Realm$$ExternalSyntheticLambda0.INSTANCE$26);
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_all) {
            if (this.recentlyNames.size() < this.recent_names.length) {
                this.adapter.allChecked(true);
                this.recentlyNames = new ArrayList();
                return;
            } else {
                this.adapter.allChecked(false);
                this.recentlyNames.addAll(Arrays.asList(this.recent_names));
                return;
            }
        }
        if (id == R.id.btn_cancel) {
            dismiss();
            return;
        }
        if (id != R.id.btn_ok) {
            return;
        }
        dismiss();
        int i = this.id;
        if (i == 1) {
            this.sharedPreferenceHelper.setSharedPreferenceResumeModel(getVodResumeModels());
            clearRecentMoviesFromRealm();
            setProToVod();
        } else {
            if (i != 2) {
                return;
            }
            this.sharedPreferenceHelper.setSharedPreferenceRecentSeriesNames(getSeriesResumeModels());
            clearRecentSeriesFromRealm();
            setRecentSeries();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    /* JADX WARN: Type inference failed for: r7v20, types: [java.util.ArrayList, java.util.List<java.lang.String>] */
    /* JADX WARN: Type inference failed for: r7v37, types: [java.util.ArrayList, java.util.List<java.lang.String>] */
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = 0;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_hide_categories, viewGroup, false);
        initView(viewInflate);
        this.sharedPreferenceHelper = new PreferenceHelper(this.context);
        WordModels wordModel = GetSharedInfo.getWordModel(this.context);
        this.wordModels = wordModel;
        int i2 = this.id;
        int i3 = 2;
        if (i2 == 0) {
            this.txt_header.setText(wordModel.getClear_channel_header());
        } else if (i2 == 1) {
            this.txt_header.setText(wordModel.getClear_movie_header());
        } else if (i2 == 2) {
            this.txt_header.setText(wordModel.getClear_series_header());
        }
        this.btn_ok.setText(this.wordModels.getOk());
        this.btn_all.setText(this.wordModels.getSelect_all());
        this.btn_cancel.setText(this.wordModels.getCancel());
        int i4 = this.id;
        if (i4 == 1) {
            this.resumeModels = new ArrayList();
            this.recentlyNames = new ArrayList();
            List<ResumeModel> sharedPreferenceResumeModel = this.sharedPreferenceHelper.getSharedPreferenceResumeModel();
            this.resumeModels = sharedPreferenceResumeModel;
            this.recent_names = new String[sharedPreferenceResumeModel.size()];
            this.checkedItems = new boolean[this.resumeModels.size()];
            while (i < this.resumeModels.size()) {
                this.recentlyNames.add(this.resumeModels.get(i).getName());
                this.recent_names[i] = this.resumeModels.get(i).getName();
                i++;
            }
        } else if (i4 == 2) {
            this.resumeSeriesModels = new ArrayList();
            this.recentlyNames = new ArrayList();
            List<ResumeSeriesModel> sharedPreferenceRecentSeriesNames = this.sharedPreferenceHelper.getSharedPreferenceRecentSeriesNames();
            this.resumeSeriesModels = sharedPreferenceRecentSeriesNames;
            this.recent_names = new String[sharedPreferenceRecentSeriesNames.size()];
            this.checkedItems = new boolean[this.resumeSeriesModels.size()];
            while (i < this.resumeSeriesModels.size()) {
                this.recentlyNames.add(this.resumeSeriesModels.get(i).getName());
                this.recent_names[i] = this.resumeSeriesModels.get(i).getName();
                i++;
            }
        }
        this.adapter = new HideCategoryRecyclerViewAdapter(getContext(), this.recent_names, this.checkedItems, new EpisodeDlgFragment$$ExternalSyntheticLambda1(this, i3));
        this.recyclerGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerGroups.setHasFixedSize(true);
        this.recyclerGroups.setAdapter(this.adapter);
        return viewInflate;
    }
}
