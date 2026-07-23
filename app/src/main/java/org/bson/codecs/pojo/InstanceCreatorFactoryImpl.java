package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes2.dex */
final class InstanceCreatorFactoryImpl<T> implements InstanceCreatorFactory<T> {
    private final CreatorExecutable<T> creatorExecutable;

    public InstanceCreatorFactoryImpl(CreatorExecutable<T> creatorExecutable) {
        this.creatorExecutable = creatorExecutable;
    }

    @Override // org.bson.codecs.pojo.InstanceCreatorFactory
    public InstanceCreator<T> create() {
        return new InstanceCreatorImpl(this.creatorExecutable);
    }
}
