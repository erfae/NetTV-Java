package androidx.emoji2.text;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FontRequestEmojiCompatConfig.FontRequestMetadataLoader f$0;

    public /* synthetic */ FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda0(FontRequestEmojiCompatConfig.FontRequestMetadataLoader fontRequestMetadataLoader, int i) {
        this.$r8$classId = i;
        this.f$0 = fontRequestMetadataLoader;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.createMetadata();
                break;
            default:
                this.f$0.loadInternal();
                break;
        }
    }
}
