package com.nettv.livestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nettv.livestore.R;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SortSpinnerAdapter extends BaseAdapter {
    public Context context;
    public List<String> names;

    public SortSpinnerAdapter(Context context, List<String> list) {
        this.context = context;
        this.names = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<String> list = this.names;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.names.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_sort, viewGroup, false);
        ((TextView) viewInflate.findViewById(R.id.txt_name)).setText(this.names.get(i));
        return viewInflate;
    }
}
