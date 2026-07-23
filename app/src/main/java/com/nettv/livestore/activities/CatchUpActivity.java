package com.nettv.livestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nettv.livestore.R;
import com.nettv.livestore.adapter.DateRecyclerAdapter;
import com.nettv.livestore.adapter.ProgramRecyclerAdapter;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.helper.GetSharedInfo;
import com.nettv.livestore.helper.PreferenceHelper;
import com.nettv.livestore.helper.RealmController;
import com.nettv.livestore.models.CatchUpEpg;
import com.nettv.livestore.models.CatchUpEpgResponse;
import com.nettv.livestore.models.CatchupModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.WordModels;
import com.nettv.livestore.remote.RetroClass;
import com.nettv.livestore.utils.Utils;
import com.nettv.livestore.view.LiveVerticalGridView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* JADX INFO: loaded from: classes2.dex */
public class CatchUpActivity extends AppCompatActivity {
    public List<CatchUpEpg> catchUpEpgList;
    public List<CatchupModel> catchupModels;
    public ImageView channel_image;
    public TextView channel_name;
    public List<CatchUpEpg> currentEventList;
    public DateRecyclerAdapter dateAdapter;
    public LiveVerticalGridView date_list;
    public LiveVerticalGridView epg_list;
    public ImageButton image_back;
    public PreferenceHelper preferenceHelper;
    public ProgramRecyclerAdapter programAdapter;
    public ProgressBar progressBar;
    public EPGChannel selectedChannel;
    public TextView txt_catch;
    public int date_pos = -1;
    public int program_pos = -1;
    public int epg_focus_pos = -1;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d");
    public SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
    public WordModels wordModels = new WordModels();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v15, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CatchupModel>] */
    /* JADX WARN: Type inference failed for: r13v2, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CatchupModel>] */
    /* JADX WARN: Type inference failed for: r13v9, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CatchupModel>] */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.util.ArrayList, java.util.List<com.nettv.livestore.models.CatchupModel>] */
    public void getCatchupModels(List<CatchUpEpg> list) {
        this.catchupModels = new ArrayList();
        ArrayList arrayList = new ArrayList();
        String str = null;
        String str2 = null;
        int i = 0;
        for (CatchUpEpg catchUpEpg : list) {
            i++;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(catchUpEpg.getStart_timestamp() * 1000);
            String str3 = this.simpleDateFormat.format(calendar.getTime());
            if (str == null) {
                str2 = this.weekFormat.format(calendar.getTime());
                arrayList = new ArrayList();
                str = str3;
            }
            if (!str3.equals(str)) {
                CatchupModel catchupModel = new CatchupModel();
                catchupModel.setName(str);
                catchupModel.setDayofweek(str2);
                catchupModel.setEpgEvents(arrayList);
                this.catchupModels.add(catchupModel);
                str2 = this.weekFormat.format(calendar.getTime());
                arrayList = new ArrayList();
                str = str3;
            }
            arrayList.add(catchUpEpg);
            if (i == list.size()) {
                CatchupModel catchupModel2 = new CatchupModel();
                catchupModel2.setName(str);
                String str4 = this.weekFormat.format(calendar.getTime());
                catchupModel2.setDayofweek(str4);
                catchupModel2.setEpgEvents(arrayList);
                this.catchupModels.add(catchupModel2);
                str2 = str4;
            }
        }
        this.progressBar.setVisibility(8);
        if (this.catchupModels.size() <= 0) {
            Toast.makeText(this, this.wordModels.getNo_epg_avaliable(), 0).show();
            return;
        }
        int currentDatePosition = getCurrentDatePosition(this.catchupModels);
        this.date_pos = currentDatePosition;
        this.dateAdapter.setCatchupModels(this.catchupModels, currentDatePosition);
        this.currentEventList = ((CatchupModel) this.catchupModels.get(this.date_pos)).getEpgEvents();
        int i2 = -1;
        for (int i3 = 0; i3 < this.currentEventList.size(); i3++) {
            if (this.currentEventList.get(i3).getNow_playing() == 1) {
                i2 = i3;
            }
        }
        if (i2 == -1) {
            this.program_pos = 0;
        } else {
            this.program_pos = i2;
        }
        this.programAdapter.setProgramList(this.currentEventList);
        this.epg_list.setSelectedPosition(this.program_pos);
        this.programAdapter.setFocusDisable(this.program_pos, false);
        this.programAdapter.setCurrentProgramPosition(i2);
        this.date_list.requestFocus();
        this.date_list.setSelectedPosition(this.date_pos);
    }

    private int getCurrentDatePosition(List<CatchupModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (this.simpleDateFormat.format(new Date()).equalsIgnoreCase(list.get(i).getName())) {
                return i;
            }
        }
        return 0;
    }

    private void getEpg() {
        this.progressBar.setVisibility(0);
        try {
            RetroClass.getAPIService(this.preferenceHelper.getSharedPreferenceServerUrl()).get_full_epg(this.preferenceHelper.getSharedPreferenceUsername(), this.preferenceHelper.getSharedPreferencePassword(), this.selectedChannel.getStream_id()).enqueue(new Callback<CatchUpEpgResponse>() { // from class: com.nettv.livestore.activities.CatchUpActivity.1
                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<CatchUpEpgResponse> call, @NonNull Throwable th) {
                    CatchUpActivity.this.progressBar.setVisibility(8);
                    CatchUpActivity catchUpActivity = CatchUpActivity.this;
                    Toast.makeText(catchUpActivity, catchUpActivity.wordModels.getNo_epg_avaliable(), 0).show();
                    CatchUpActivity.this.catchUpEpgList = new ArrayList();
                    CatchUpActivity.this.image_back.setFocusable(true);
                    CatchUpActivity.this.image_back.requestFocus();
                }

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<CatchUpEpgResponse> call, @NonNull Response<CatchUpEpgResponse> response) {
                    if (response.body() != null && response.body().getEpg_listings().size() != 0) {
                        CatchUpActivity.this.catchUpEpgList = response.body().getEpg_listings();
                        CatchUpActivity catchUpActivity = CatchUpActivity.this;
                        catchUpActivity.getCatchupModels(catchUpActivity.catchUpEpgList);
                        return;
                    }
                    CatchUpActivity.this.catchUpEpgList = new ArrayList();
                    CatchUpActivity.this.progressBar.setVisibility(8);
                    CatchUpActivity catchUpActivity2 = CatchUpActivity.this;
                    Toast.makeText(catchUpActivity2, catchUpActivity2.wordModels.getNo_epg_avaliable(), 0).show();
                }
            });
        } catch (Exception unused) {
            Toast.makeText(this, this.wordModels.getNo_epg_avaliable(), 0).show();
            this.progressBar.setVisibility(8);
            this.catchUpEpgList = new ArrayList();
            this.image_back.setFocusable(true);
            this.image_back.requestFocus();
        }
    }

    private void goToLivePage() {
        Intent intent = new Intent();
        intent.putExtra("is_changed", "");
        setResult(-1, intent);
        finish();
    }

    private void initView() {
        this.channel_image = (ImageView) findViewById(R.id.channel_image);
        this.channel_name = (TextView) findViewById(R.id.channel_name);
        this.date_list = (LiveVerticalGridView) findViewById(R.id.date_list);
        this.epg_list = (LiveVerticalGridView) findViewById(R.id.epg_list);
        this.txt_catch = (TextView) findViewById(R.id.txt_catch);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.progressBar = progressBar;
        progressBar.setVisibility(8);
        ImageButton imageButton = (ImageButton) findViewById(R.id.image_back);
        this.image_back = imageButton;
        imageButton.setFocusable(false);
        this.image_back.setOnClickListener(new SearchActivity$$ExternalSyntheticLambda0(this, 1));
        this.date_list.setNumColumns(1);
        this.date_list.setLoop(false);
        this.date_list.setPreserveFocusAfterLayout(true);
        final View[] viewArr = {null};
        this.date_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.CatchUpActivity.2
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
        this.epg_list.setNumColumns(1);
        this.epg_list.setPreserveFocusAfterLayout(true);
        this.epg_list.setLoop(false);
        final View[] viewArr2 = {null};
        this.epg_list.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: com.nettv.livestore.activities.CatchUpActivity.3
            @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
            public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
                super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
                View[] viewArr3 = viewArr2;
                if (viewArr3[0] != null) {
                    viewArr3[0].setSelected(false);
                    View[] viewArr4 = viewArr2;
                    viewArr4[0] = viewHolder.itemView;
                    viewArr4[0].setSelected(true);
                }
            }
        });
        this.txt_catch.setText(this.wordModels.getCatch_up());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        goToLivePage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(CatchupModel catchupModel, Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            this.currentEventList = catchupModel.getEpgEvents();
            int i = -1;
            for (int i2 = 0; i2 < this.currentEventList.size(); i2++) {
                if (this.currentEventList.get(i2).getNow_playing() == 1) {
                    i = i2;
                }
            }
            if (i == -1) {
                this.program_pos = 0;
            } else {
                this.program_pos = i;
            }
            this.programAdapter.setProgramList(this.currentEventList);
            this.epg_list.setSelectedPosition(this.program_pos);
            this.programAdapter.setFocusDisable(this.program_pos, false);
            this.programAdapter.setCurrentProgramPosition(i);
        }
        this.date_pos = num.intValue();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$1(CatchUpEpg catchUpEpg, Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            if (catchUpEpg.getHas_archive() == 1) {
                Intent intent = new Intent(this, (Class<?>) CatchUpPlayerActivity.class);
                intent.putExtra("position", num);
                intent.putExtra("stream_id", this.selectedChannel.getStream_id());
                intent.putExtra("epg_model", new Gson().toJson(this.currentEventList));
                startActivity(intent);
            } else {
                Toast.makeText(this, "This program can not be play", 0).show();
            }
        }
        this.epg_focus_pos = num.intValue();
        return null;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        List<CatchUpEpg> list;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4) {
                goToLivePage();
                return true;
            }
            if (keyCode != 22) {
                if (keyCode != 19) {
                    if (keyCode == 20 && this.image_back.hasFocus()) {
                        this.image_back.setFocusable(false);
                        this.date_list.requestFocus();
                        return true;
                    }
                } else {
                    if (this.date_list.hasFocus() && this.date_pos == 0) {
                        this.image_back.setFocusable(true);
                        this.image_back.requestFocus();
                        return true;
                    }
                    if (this.epg_list.hasFocus() && this.epg_focus_pos == 0) {
                        this.image_back.setFocusable(true);
                        this.image_back.requestFocus();
                        return true;
                    }
                }
            } else if (this.image_back.hasFocus() && (list = this.currentEventList) != null && list.size() > 0) {
                this.image_back.setFocusable(false);
                this.epg_list.requestFocus();
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_catch_up);
        Utils.FullScreenCall(this);
        this.preferenceHelper = new PreferenceHelper(this);
        this.wordModels = GetSharedInfo.getWordModel(this);
        initView();
        this.selectedChannel = RealmController.with().getEpgChannelByName(LTVApp.channelName);
        TextView textView = this.channel_name;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("");
        sbM.append(this.selectedChannel.getNum());
        sbM.append(" ");
        sbM.append(this.selectedChannel.getName());
        textView.setText(sbM.toString());
        try {
            Glide.with((FragmentActivity) this).load(this.selectedChannel.getStream_icon()).error(R.drawable.home_logo).into(this.channel_image);
        } catch (Exception unused) {
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.home_logo)).error(R.drawable.home_logo).into(this.channel_image);
        }
        final int i = 0;
        DateRecyclerAdapter dateRecyclerAdapter = new DateRecyclerAdapter(this, new ArrayList(), new Function3(this) { // from class: com.nettv.livestore.activities.CatchUpActivity$$ExternalSyntheticLambda0
            public final /* synthetic */ CatchUpActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i) {
                    case 0:
                        return this.f$0.lambda$onCreate$0((CatchupModel) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$onCreate$1((CatchUpEpg) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        });
        this.dateAdapter = dateRecyclerAdapter;
        this.date_list.setAdapter(dateRecyclerAdapter);
        final int i2 = 1;
        ProgramRecyclerAdapter programRecyclerAdapter = new ProgramRecyclerAdapter(this, new ArrayList(), new Function3(this) { // from class: com.nettv.livestore.activities.CatchUpActivity$$ExternalSyntheticLambda0
            public final /* synthetic */ CatchUpActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i2) {
                    case 0:
                        return this.f$0.lambda$onCreate$0((CatchupModel) obj, (Integer) obj2, (Boolean) obj3);
                    default:
                        return this.f$0.lambda$onCreate$1((CatchUpEpg) obj, (Integer) obj2, (Boolean) obj3);
                }
            }
        });
        this.programAdapter = programRecyclerAdapter;
        this.epg_list.setAdapter(programRecyclerAdapter);
        getEpg();
    }
}
