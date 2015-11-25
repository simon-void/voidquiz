package net.simonvoid.data.service;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import net.simonvoid.data.dao.HighscoreDao;
import net.simonvoid.data.model.orm.HighscoreDto;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by stephan on 25.11.2015.
 */
public class HighscoreServiceImplTest {
    private HighscoreServiceImpl highscoreService;
    private HighscoreDao highscoreDaoMock;

    @BeforeMethod
    public void setup() {
        highscoreService = new HighscoreServiceImpl();
        highscoreDaoMock = mock(HighscoreDao.class);
        // manually insert the dependencies of the class under test with mocks
        ReflectionTestUtils.setField(highscoreService, "highscoreDao", highscoreDaoMock);
    }

    @Test(dataProvider = "getIndexInHighscoreDataProvider")
    public void testGetIndexInHighscore(List<HighscoreDto> providedHighscores, int providedPoints, int expectedIndex) {
        //*nothing to setup
        //*execute
        int actualIndex = highscoreService.getIndexInHighscore(providedHighscores, providedPoints);
        //*assert
        assertEquals(actualIndex, expectedIndex, "index");
    }

    @DataProvider(name = "getIndexInHighscoreDataProvider")
    public Object[][] getIndexInHighscoreDataProvider() {
        return new Object[][]{
                new Object[]{getHighscoresForPoints(new int[]{3,2,1}), 5, 0},
                new Object[]{getHighscoresForPoints(new int[]{5,2,1}), 5, 0},
                new Object[]{getHighscoresForPoints(new int[]{9,2,1}), 5, 1},
                new Object[]{getHighscoresForPoints(new int[]{9,5,1}), 5, 1},
                new Object[]{getHighscoresForPoints(new int[]{9,9,1}), 5, 2},
                new Object[]{getHighscoresForPoints(new int[]{9,9,5}), 5, 2},
                new Object[]{getHighscoresForPoints(new int[]{9,9,9}), 5, 3},
        };
    }

    private List<HighscoreDto> getHighscoresForPoints(int[] points) {
        List<HighscoreDto> result = new ArrayList<>(points.length);
        for(int point: points) {
            HighscoreDto highscore = new HighscoreDto();
            highscore.setName("testuser");
            highscore.setPoints(point);
            highscore.setCreatedAt(new Date());
            result.add(highscore );
        }

        return result;
    }

    @Test(dataProvider = "getHighscoreListProvider")
    //test the sorting
    public void testGetHighscoreList(List<HighscoreDto> providedUnordered, List<HighscoreDto> expectedOrder) {
        //*setup
        when(highscoreDaoMock.getHighscoreList()).thenReturn(providedUnordered);
        //*execute
        List<HighscoreDto> actualOrder = highscoreService.getHighscoreList();
        //*assert
        assertEquals(actualOrder, expectedOrder, "order");

    }

    @DataProvider(name = "getHighscoreListProvider")
    public Object[][] getHighscoreListProvider() {
        HighscoreDto highestscoreDto = getHighscore(6, 20, 1);
        HighscoreDto mediumscoreDto = getHighscore(6, 10, 1);
        HighscoreDto lowestscoreDto = getHighscore(5, 21, 1);
        // the order is primary higher points first, then later time (=bigger day) first
        List<HighscoreDto> orderedHighscores = Arrays.asList(highestscoreDto, mediumscoreDto, lowestscoreDto);


        return new Object[][]{
                new Object[]{Arrays.asList(highestscoreDto, mediumscoreDto, lowestscoreDto), orderedHighscores},
                new Object[]{Arrays.asList(lowestscoreDto, mediumscoreDto, highestscoreDto), orderedHighscores},
                new Object[]{Arrays.asList(mediumscoreDto, highestscoreDto, lowestscoreDto), orderedHighscores},
                new Object[]{Arrays.asList(mediumscoreDto, lowestscoreDto, highestscoreDto), orderedHighscores},
        };
    }

    private HighscoreDto getHighscore(int points, int dayInMonth, long id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 11, dayInMonth);

        HighscoreDto highscore = new HighscoreDto();
        highscore.setCreatedAt(calendar.getTime());
        highscore.setId(id);
        highscore.setPoints(points);
        highscore.setName("testuser");

        return highscore;
    }
}
