package net.simonvoid.view.listener;

import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stephan on 19.11.2015.
 */
abstract public class EventListenerOwner <T extends EventListener> {
    private T listener;

    public void setListener(T listener) {
        this.listener = listener;
    }

    protected void fireEventListener() {
        fireListener(listener);
    }

    abstract protected void fireListener(T listener);
}
