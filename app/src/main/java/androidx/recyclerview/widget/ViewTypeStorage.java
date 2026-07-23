package androidx.recyclerview.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
interface ViewTypeStorage {

    public static class IsolatedViewTypeStorage implements ViewTypeStorage {
        public SparseArray<NestedAdapterWrapper> mGlobalTypeToWrapper = new SparseArray<>();
        public int mNextViewType = 0;

        public class WrapperViewTypeLookup implements ViewTypeLookup {
            public final NestedAdapterWrapper mWrapper;
            private SparseIntArray mLocalToGlobalMapping = new SparseIntArray(1);
            private SparseIntArray mGlobalToLocalMapping = new SparseIntArray(1);

            public WrapperViewTypeLookup(NestedAdapterWrapper nestedAdapterWrapper) {
                this.mWrapper = nestedAdapterWrapper;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                IsolatedViewTypeStorage isolatedViewTypeStorage = IsolatedViewTypeStorage.this;
                NestedAdapterWrapper nestedAdapterWrapper = this.mWrapper;
                int size = isolatedViewTypeStorage.mGlobalTypeToWrapper.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        return;
                    }
                    if (isolatedViewTypeStorage.mGlobalTypeToWrapper.valueAt(size) == nestedAdapterWrapper) {
                        isolatedViewTypeStorage.mGlobalTypeToWrapper.removeAt(size);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int i) {
                int iIndexOfKey = this.mGlobalToLocalMapping.indexOfKey(i);
                if (iIndexOfKey >= 0) {
                    return this.mGlobalToLocalMapping.valueAt(iIndexOfKey);
                }
                StringBuilder sbM24m = Insets$$ExternalSyntheticOutline0.m24m("requested global type ", i, " does not belong to the adapter:");
                sbM24m.append(this.mWrapper.adapter);
                throw new IllegalStateException(sbM24m.toString());
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int i) {
                int iIndexOfKey = this.mLocalToGlobalMapping.indexOfKey(i);
                if (iIndexOfKey > -1) {
                    return this.mLocalToGlobalMapping.valueAt(iIndexOfKey);
                }
                IsolatedViewTypeStorage isolatedViewTypeStorage = IsolatedViewTypeStorage.this;
                NestedAdapterWrapper nestedAdapterWrapper = this.mWrapper;
                int i2 = isolatedViewTypeStorage.mNextViewType;
                isolatedViewTypeStorage.mNextViewType = i2 + 1;
                isolatedViewTypeStorage.mGlobalTypeToWrapper.put(i2, nestedAdapterWrapper);
                this.mLocalToGlobalMapping.put(i, i2);
                this.mGlobalToLocalMapping.put(i2, i);
                return i2;
            }
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper) {
            return new WrapperViewTypeLookup(nestedAdapterWrapper);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int i) {
            NestedAdapterWrapper nestedAdapterWrapper = this.mGlobalTypeToWrapper.get(i);
            if (nestedAdapterWrapper != null) {
                return nestedAdapterWrapper;
            }
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Cannot find the wrapper for global view type ", i));
        }
    }

    public static class SharedIdRangeViewTypeStorage implements ViewTypeStorage {
        public SparseArray<List<NestedAdapterWrapper>> mGlobalTypeToWrapper = new SparseArray<>();

        public class WrapperViewTypeLookup implements ViewTypeLookup {
            public final NestedAdapterWrapper mWrapper;

            public WrapperViewTypeLookup(NestedAdapterWrapper nestedAdapterWrapper) {
                this.mWrapper = nestedAdapterWrapper;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                SharedIdRangeViewTypeStorage sharedIdRangeViewTypeStorage = SharedIdRangeViewTypeStorage.this;
                NestedAdapterWrapper nestedAdapterWrapper = this.mWrapper;
                int size = sharedIdRangeViewTypeStorage.mGlobalTypeToWrapper.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        return;
                    }
                    List<NestedAdapterWrapper> listValueAt = sharedIdRangeViewTypeStorage.mGlobalTypeToWrapper.valueAt(size);
                    if (listValueAt.remove(nestedAdapterWrapper) && listValueAt.isEmpty()) {
                        sharedIdRangeViewTypeStorage.mGlobalTypeToWrapper.removeAt(size);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int i) {
                return i;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int i) {
                List<NestedAdapterWrapper> arrayList = SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.get(i);
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                    SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.put(i, arrayList);
                }
                if (!arrayList.contains(this.mWrapper)) {
                    arrayList.add(this.mWrapper);
                }
                return i;
            }
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper) {
            return new WrapperViewTypeLookup(nestedAdapterWrapper);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int i) {
            List<NestedAdapterWrapper> list = this.mGlobalTypeToWrapper.get(i);
            if (list == null || list.isEmpty()) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Cannot find the wrapper for global view type ", i));
            }
            return list.get(0);
        }
    }

    public interface ViewTypeLookup {
        void dispose();

        int globalToLocal(int i);

        int localToGlobal(int i);
    }

    @NonNull
    ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

    @NonNull
    NestedAdapterWrapper getWrapperForGlobalType(int i);
}
