package net.simonvoid.data.dao;

import net.simonvoid.data.model.orm.HighscoreDto;

import java.util.List;

/**
 * Created by stephan on 25.11.2015.
 */
public interface HighscoreDao {
    List<HighscoreDto> getHighscoreList();
    void saveHighscore(HighscoreDto newHighscore);
    void deleteHighscore(HighscoreDto newHighscore);
}
