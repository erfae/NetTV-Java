package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Queue;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
final class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    private final Queue<T> queue;

    public ConsumingQueueIterator(Queue<T> queue) {
        this.queue = (Queue) Preconditions.checkNotNull(queue);
    }

    @Override // com.google.common.collect.AbstractIterator
    @CheckForNull
    public T computeNext() {
        if (!this.queue.isEmpty()) {
            return this.queue.remove();
        }
        endOfData();
        return null;
    }
}
