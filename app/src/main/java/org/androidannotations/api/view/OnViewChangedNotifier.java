package org.androidannotations.api.view;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class OnViewChangedNotifier {
    private static OnViewChangedNotifier currentNotifier;
    private final List<OnViewChangedListener> listeners = new LinkedList();

    public static void registerOnViewChangedListener(OnViewChangedListener onViewChangedListener) {
        OnViewChangedNotifier onViewChangedNotifier = currentNotifier;
        if (onViewChangedNotifier != null) {
            onViewChangedNotifier.listeners.add(onViewChangedListener);
        }
    }

    public static OnViewChangedNotifier replaceNotifier(OnViewChangedNotifier onViewChangedNotifier) {
        OnViewChangedNotifier onViewChangedNotifier2 = currentNotifier;
        currentNotifier = onViewChangedNotifier;
        return onViewChangedNotifier2;
    }

    public void notifyViewChanged(HasViews hasViews) {
        Iterator<OnViewChangedListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onViewChanged(hasViews);
        }
    }
}
