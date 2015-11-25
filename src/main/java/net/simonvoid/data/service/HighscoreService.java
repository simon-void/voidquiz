package net.simonvoid.data.service;

import net.simonvoid.data.model.orm.HighscoreDto;

import java.util.List;

/**
 * Created by stephan on 25.11.2015.
 */
public interface HighscoreService {
    final public static int MAX_HIGHSCORES_IN_LIST = 3;

    List<HighscoreDto> getHighscoreList();
    void saveNewHighscore(String username, int points);
    int getIndexInHighscore(List<HighscoreDto> currentHighscores, int newPoints);
}
