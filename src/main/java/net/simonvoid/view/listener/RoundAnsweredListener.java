package net.simonvoid.view.listener;

import java.util.EventListener;

/**
 * Created by stephan on 18.11.2015.
 */
public interface RoundAnsweredListener extends EventListener {
    void roundAnswered(boolean answeredCorrectly);
}
