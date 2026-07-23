package org.bson.json;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes2.dex */
class ShellRegularExpressionConverter implements Converter<BsonRegularExpression> {
    @Override // org.bson.json.Converter
    public void convert(BsonRegularExpression bsonRegularExpression, StrictJsonWriter strictJsonWriter) {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("/", bsonRegularExpression.getPattern().equals("") ? "(?:)" : bsonRegularExpression.getPattern().replace("/", "\\/"), "/");
        sbM26m.append(bsonRegularExpression.getOptions());
        strictJsonWriter.writeRaw(sbM26m.toString());
    }
}
