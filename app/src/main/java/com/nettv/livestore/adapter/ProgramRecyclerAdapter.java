package com.nettv.livestore.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.R;
import com.nettv.livestore.models.CatchUpEpg;
import com.nettv.livestore.utils.Utils;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes2.dex */
public class ProgramRecyclerAdapter extends RecyclerView.Adapter<XCProgramViewHolder> {
    public Function3<CatchUpEpg, Integer, Boolean, Unit> clickFunctionListener;
    public Context context;
    public List<CatchUpEpg> epgModels;
    public int disabled_pos = -1;
    public boolean is_disable = false;
    public int current_program_pos = -1;

    public class XCProgramViewHolder extends RecyclerView.ViewHolder {
        public ImageView catch_image;
        public TextView txt_live;
        public TextView txt_program;
        public TextView txt_program_description;
        public TextView txt_time;

        public XCProgramViewHolder(@NonNull ProgramRecyclerAdapter programRecyclerAdapter, View view) {
            super(view);
            this.txt_time = (TextView) view.findViewById(R.id.txt_time);
            this.txt_live = (TextView) view.findViewById(R.id.txt_live);
            this.txt_program = (TextView) view.findViewById(R.id.txt_program);
            this.txt_program_description = (TextView) view.findViewById(R.id.txt_program_description);
            this.catch_image = (ImageView) view.findViewById(R.id.catch_image);
        }
    }

    public ProgramRecyclerAdapter(Context context, List<CatchUpEpg> list, Function3<CatchUpEpg, Integer, Boolean, Unit> function3) {
        this.context = context;
        this.epgModels = list;
        this.clickFunctionListener = function3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(CatchUpEpg catchUpEpg, int i, XCProgramViewHolder xCProgramViewHolder, View view, boolean z) {
        if (!z) {
            xCProgramViewHolder.itemView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            xCProgramViewHolder.txt_program_description.setVisibility(8);
            return;
        }
        this.clickFunctionListener.invoke(catchUpEpg, Integer.valueOf(i), Boolean.FALSE);
        xCProgramViewHolder.itemView.setBackgroundResource(R.drawable.live_teim_focus_bg);
        xCProgramViewHolder.txt_program_description.setText(Utils.decode64String(catchUpEpg.getDescription()));
        if (catchUpEpg.getDescription().isEmpty()) {
            xCProgramViewHolder.txt_program_description.setVisibility(8);
        } else {
            xCProgramViewHolder.txt_program_description.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(CatchUpEpg catchUpEpg, int i, View view) {
        this.clickFunctionListener.invoke(catchUpEpg, Integer.valueOf(i), Boolean.TRUE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CatchUpEpg> list = this.epgModels;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setCurrentProgramPosition(int i) {
        this.current_program_pos = i;
        notifyItemChanged(i);
    }

    public void setFocusDisable(int i, boolean z) {
        this.disabled_pos = i;
        this.is_disable = z;
        notifyItemChanged(i);
    }

    public void setProgramList(List<CatchUpEpg> list) {
        this.epgModels = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull XCProgramViewHolder xCProgramViewHolder, int i) {
        CatchUpEpg catchUpEpg = this.epgModels.get(i);
        xCProgramViewHolder.txt_program.setText(Utils.decode64String(catchUpEpg.getTitle()));
        xCProgramViewHolder.txt_time.setText(Utils.Offset(catchUpEpg.getStart_timestamp() * 1000, this.context));
        if (catchUpEpg.getHas_archive() == 1) {
            xCProgramViewHolder.catch_image.setVisibility(0);
        } else {
            xCProgramViewHolder.catch_image.setVisibility(8);
        }
        if (this.current_program_pos == i) {
            xCProgramViewHolder.txt_live.setVisibility(0);
        } else {
            xCProgramViewHolder.txt_live.setVisibility(8);
        }
        xCProgramViewHolder.itemView.setOnFocusChangeListener(new CastRecyclerAdapter$$ExternalSyntheticLambda0(this, catchUpEpg, i, xCProgramViewHolder, 4));
        xCProgramViewHolder.itemView.setOnClickListener(new VodRecyclerAdapter$$ExternalSyntheticLambda0(this, catchUpEpg, i, 10));
        if (!this.is_disable) {
            xCProgramViewHolder.itemView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        } else if (i == this.disabled_pos) {
            xCProgramViewHolder.itemView.setBackgroundColor(Color.parseColor("#2e2f2f"));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public XCProgramViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XCProgramViewHolder(this, MainActivity$$ExternalSyntheticOutline0.m(viewGroup, R.layout.item_program, viewGroup, false));
    }
}
