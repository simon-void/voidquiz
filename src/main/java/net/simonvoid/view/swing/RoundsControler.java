package net.simonvoid.view.swing;

import net.simonvoid.dto.xml.QuizDto;
import net.simonvoid.dto.xml.RoundDto;
import net.simonvoid.view.listener.EventListenerOwner;
import net.simonvoid.view.listener.QuizResultListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by stephan on 18.11.2015.
 */
public class RoundsControler extends EventListenerOwner<QuizResultListener> {
    private List<RoundDto> rounds;
    private int numberOfCorrectlyAnsweredRounds;
    private int numberOfAnsweredRounds;
    private JPanel viewPanel;

    public RoundsControler(QuizDto quiz) {
        rounds = quiz.getRounds();
        numberOfCorrectlyAnsweredRounds = 0;
        numberOfAnsweredRounds = 0;
        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

        createLayout(quiz.getNumberOfJokers());
    }

    private void createLayout(int numberOfJokers) {
        final JockerControler jokerControler = new JockerControler(numberOfJokers);

        JPanel nextRoundPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        JButton nextRound = new JButton("next question");
        nextRoundPanel.add(nextRound);
        nextRound.setEnabled(false);
        JPanel roundsPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(jokerControler.getView(), BorderLayout.NORTH);
        viewPanel.add(roundsPanel, BorderLayout.CENTER);
        viewPanel.add(nextRoundPanel, BorderLayout.SOUTH);

        showCurrentRoundPanel(roundsPanel, nextRound, jokerControler);

        //wire the nextRoundButton to show the next round
        nextRound.addActionListener(
                (ActionEvent e) ->{
                    //was that the last question?
                    if(numberOfAnsweredRounds==rounds.size()) {
                        fireEventListener();
                    }else{
                        //disable the nextRound button again
                        nextRound.setEnabled(false);
                        //and update the round Panel
                        showCurrentRoundPanel(roundsPanel, nextRound, jokerControler);
                        //reenable the jocker button
                        jokerControler.reenableIfJokersLeft();
                        //repaint the view
                        repaintView();
                    }
                }
        );
    }

    private void showCurrentRoundPanel(final JPanel roundPanel, final JButton nextRound, final JockerControler jokerControler) {
        RoundDto currentRound = rounds.get(numberOfAnsweredRounds);
        final RoundControler roundControler = new RoundControler(currentRound, numberOfAnsweredRounds+1);
        roundControler.setListener(
                (boolean answeredCorrectly)->{
                    //disable the jocker button
                    jokerControler.disable();
                    //increase the current indexes
                    numberOfAnsweredRounds++;
                    if(answeredCorrectly) {
                        numberOfCorrectlyAnsweredRounds++;
                    }
                    nextRound.setEnabled(true);
                    if(numberOfAnsweredRounds==rounds.size()) {
                        nextRound.setText("see summary");
                    }

                    //repaint the view
                    repaintView();
                }
        );

        //
        jokerControler.setListener(
                ()->{
                    roundControler.ruleOutTwoWrongAnswers();
                    //repaint the view
                    repaintView();
                }
        );

        roundPanel.removeAll();
        roundPanel.add(roundControler.viewPanel);
        //repaint the view
        repaintView();
    }

    @Override
    protected void fireListener(QuizResultListener listener) {
        listener.quizFinished(numberOfCorrectlyAnsweredRounds, rounds.size());
    }

    public JPanel getPanel() {
        return viewPanel;
    }

    private void repaintView() {
        JComponentUtils.forceRepaint(viewPanel);
    }
}