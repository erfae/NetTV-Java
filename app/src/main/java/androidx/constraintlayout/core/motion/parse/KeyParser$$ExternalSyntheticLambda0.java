package androidx.constraintlayout.core.motion.parse;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class KeyParser$$ExternalSyntheticLambda0 implements KeyParser.Ids, KeyParser.DataType {
    public static final /* synthetic */ KeyParser$$ExternalSyntheticLambda0 INSTANCE = new KeyParser$$ExternalSyntheticLambda0();
    public static final /* synthetic */ KeyParser$$ExternalSyntheticLambda0 INSTANCE$1 = new KeyParser$$ExternalSyntheticLambda0();

    @Override // androidx.constraintlayout.core.motion.parse.KeyParser.DataType
    public final int get(int i) {
        return TypedValues.AttributesType.CC.getType(i);
    }

    @Override // androidx.constraintlayout.core.motion.parse.KeyParser.Ids
    public final int get(String str) {
        return TypedValues.AttributesType.CC.getId(str);
    }
}
