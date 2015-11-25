package net.simonvoid.data.dao;

import net.simonvoid.data.model.orm.HighscoreDto;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by stephan on 25.11.2015.
 */
@Repository("highscoreDao")
public class HighscoreDaoImpl extends AbstractDao implements HighscoreDao {
    @Override
    public List<HighscoreDto> getHighscoreList() {
        Criteria getAllCriteria = getSession().createCriteria(HighscoreDto.class);
        return (List<HighscoreDto>)getAllCriteria.list();
    }

    @Override
    public void saveHighscore(HighscoreDto newHighscore) {
        persist(newHighscore);
    }

    @Override
    public void deleteHighscore(HighscoreDto oldHighscore) {
        //delete a highscore only if it exists
        if(oldHighscore.getId()!=null) {
            delete(oldHighscore);
        }
    }
}
