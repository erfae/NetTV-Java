package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private final AsyncListDiffer.ListListener<T> mListener;

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ListAdapter$1, reason: invalid class name */
    public class AnonymousClass1 implements AsyncListDiffer.ListListener<T> {
        @Override // androidx.recyclerview.widget.AsyncListDiffer.ListListener
        public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
            throw null;
        }
    }

    @NonNull
    public List<T> getCurrentList() {
        throw null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        throw null;
    }

    public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
    }

    public void submitList(@Nullable List<T> list) {
        throw null;
    }

    public void submitList(@Nullable List<T> list, @Nullable Runnable runnable) {
        throw null;
    }
}
