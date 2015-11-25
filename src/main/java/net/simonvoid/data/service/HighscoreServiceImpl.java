package net.simonvoid.data.service;

import net.simonvoid.data.dao.HighscoreDao;
import net.simonvoid.data.model.orm.HighscoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by stephan on 25.11.2015.
 */
@Service("highscoreService")
@Transactional
public class HighscoreServiceImpl implements HighscoreService {
    @Autowired
    private HighscoreDao highscoreDao;

    @Override
    public List<HighscoreDto> getHighscoreList() {
        List<HighscoreDto> highscoreDtos = highscoreDao.getHighscoreList();
        sortHighscore(highscoreDtos);
        //fill the list up with dummys if it's not long enough
        while (highscoreDtos.size()<MAX_HIGHSCORES_IN_LIST) {
            highscoreDtos.add(createDummyHighscore());
        }
        return highscoreDtos;
    }

    private void sortHighscore(List<HighscoreDto> highscores) {
        Collections.sort(highscores,
                //highscores with higher points com first in list
                (HighscoreDto h1, HighscoreDto h2)-> {
                    final int h1Points = h1.getPoints();
                    final int h2Points = h2.getPoints();
                    if(h1Points<h2Points) {
                        return 1;
                    }
                    if(h1Points>h2Points) {
                        return -1;
                    }
                    //else new entries should come first
                    return h2.getCreatedAt().compareTo(h1.getCreatedAt());
                });
    }

    private HighscoreDto createDummyHighscore() {
        HighscoreDto highscore = new HighscoreDto();
        highscore.setName("...");
        highscore.setPoints(0);

        return highscore;
    }

    @Override
    public void saveNewHighscore(final String username, final int points) {
        HighscoreDto highscore = new HighscoreDto();
        highscore.setName(username);
        highscore.setPoints(points);

        List<HighscoreDto> highscores = getHighscoreList();
        HighscoreDto worstOldHighscore = highscores.get(highscores.size()-1);

        if(points>=worstOldHighscore.getPoints()) {
            //remove an old highscore (with lowers points) from list and from db
            highscores.remove(highscores.size()-1);
            highscoreDao.deleteHighscore(worstOldHighscore);
            //insert the new highscore so early as possible (according to points)
            int index = getIndexInHighscore(highscores, points);
            highscores.add(index, highscore);
            //add the new Highscore to the db
            highscoreDao.saveHighscore(highscore);
        }
    }

    @Override
    public int getIndexInHighscore(List<HighscoreDto> currentHighscores, int newPoints) {
        int index = 0;
        while (index<currentHighscores.size() && newPoints<currentHighscores.get(index).getPoints()) {
            index++;
        }
        return index;
    }
}
