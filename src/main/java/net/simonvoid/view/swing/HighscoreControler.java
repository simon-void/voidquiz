package net.simonvoid.view.swing;

import net.simonvoid.data.model.orm.HighscoreDto;
import net.simonvoid.data.service.HighscoreService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

/**
 * Created by stephan on 25.11.2015.
 */
public class HighscoreControler {
    HighscoreService highscoreService;

    private JPanel viewPanel;

    public HighscoreControler(HighscoreService highscoreService, int points) {
        this.highscoreService = highscoreService;
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout());

        createLayout(points);
    }

    private void createLayout(int points) {
        viewPanel.add(ComponentUtils.wrapInFlowLayoutPanel(new JLabel("Highscore"), FlowLayout.CENTER, 0, 0), BorderLayout.NORTH);

        //get a panel that only consists of different highscore panels
        JPanel highscoresPanel = new JPanel();
        highscoresPanel.setLayout(new BoxLayout(highscoresPanel, BoxLayout.Y_AXIS));
        //and center that on the bigger panel
        viewPanel.add(highscoresPanel, BorderLayout.CENTER);

        final List<HighscoreDto> highscores = highscoreService.getHighscoreList();
        final int newUsernameIndex = highscoreService.getIndexInHighscore(highscores, points);

        Iterator<HighscoreDto> highscoreIter = highscores.iterator();
        for(int i=0; i<HighscoreService.MAX_HIGHSCORES_IN_LIST; i++ ) {
            Component row;
            if(i==newUsernameIndex) {
                row = getNewHighscoreRow(points);
            }else{
                row = getOldHighscoreRow(highscoreIter.next());
            }
            highscoresPanel.add(row);
        }
    }

    private Component getNewHighscoreRow(int points) {
        final JPanel newUsernameField = new JPanel();
        Component row = getHighscoreRow(newUsernameField, points);

        JTextField usernameField = new JTextField(8);
        //the usernameField listens for a new username
        usernameField.addActionListener(
                (ActionEvent e)->{
                    //get username
                    String newUsername = usernameField.getText();
                    if(newUsername.length()==0) {
                        newUsername = "...";
                    }else if(newUsername.length()>HighscoreDto.MAX_NAME_LENGTH) {
                        newUsername = newUsername.substring(0, HighscoreDto.MAX_NAME_LENGTH);
                    }
                    //save the new highscore
                    highscoreService.saveNewHighscore(newUsername, points);
                    //replace the textfield with a textlabel
                    newUsernameField.removeAll();
                    newUsernameField.add(new JLabel(newUsername));
                    //repaint the field
                    ComponentUtils.forceRepaint(newUsernameField);
                }
        );
        newUsernameField.add(usernameField);

        return row;
    }

    private Component getOldHighscoreRow(HighscoreDto highscoreDto) {
        //the old row show look similar to the new row -> same panel structure
        JPanel panel = new JPanel();
        panel.add(new JLabel(highscoreDto.getName()));
        return getHighscoreRow(panel, highscoreDto.getPoints());
    }

    private Component getHighscoreRow(Component nameField, int points) {
        JPanel row = new JPanel(new BorderLayout());
        row.add(nameField, BorderLayout.CENTER);
        row.add(new JLabel(Integer.toString(points)), BorderLayout.EAST);
        return row;
    }

    public JComponent getView() {
        return viewPanel;
    }
}
