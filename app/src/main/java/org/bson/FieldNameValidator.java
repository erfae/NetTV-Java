package org.bson;

/* JADX INFO: loaded from: classes2.dex */
public interface FieldNameValidator {
    FieldNameValidator getValidatorForField(String str);

    boolean validate(String str);
}
