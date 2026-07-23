package org.bson.codecs.pojo;

import com.google.android.exoplayer2.text.ttml.TtmlNode;

/* JADX INFO: loaded from: classes2.dex */
final class ConventionDefaultsImpl implements Convention {
    @Override // org.bson.codecs.pojo.Convention
    public void apply(ClassModelBuilder<?> classModelBuilder) {
        if (classModelBuilder.getDiscriminatorKey() == null) {
            classModelBuilder.discriminatorKey("_t");
        }
        if (classModelBuilder.getDiscriminator() == null && classModelBuilder.getType() != null) {
            classModelBuilder.discriminator(classModelBuilder.getType().getName());
        }
        for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
            if (classModelBuilder.getIdPropertyName() == null) {
                String name = propertyModelBuilder.getName();
                if (name.equals("_id") || name.equals(TtmlNode.ATTR_ID)) {
                    classModelBuilder.idPropertyName(name);
                }
            }
        }
    }
}
