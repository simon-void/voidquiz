package net.simonvoid.view.swing;

import net.simonvoid.dto.xml.AnswerDto;
import net.simonvoid.dto.xml.RoundDto;
import net.simonvoid.view.listener.EventListenerOwner;
import net.simonvoid.view.listener.RoundAnsweredListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stephan on 19.11.2015.
 */
public class RoundControler  extends EventListenerOwner<RoundAnsweredListener> {
    JPanel viewPanel;
    Boolean roundAnsweredCorrectly;
    List<JButton> twoWrongAnswerButtons;

    public RoundControler(RoundDto round, int questionNumber) {
        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

        createLayout(round, questionNumber);
    }

    private void createLayout(RoundDto round, int questionNumber) {
        //instanciate colors for later use
        final Color red = new Color(200, 100, 100);
        final Color green = new Color(120, 200, 120);
        //save instances of the buttons and panels to make them accesable by the action listeners
        final List<JButton> selectButtons = new ArrayList<>(round.getAnswers().size());
        final List<JPanel> answerPanels  = new ArrayList<>(round.getAnswers().size());
        final int correctAnswerIndex = round.getCorrectAnswerIndex();
        //add the question
        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,10));
        questionPanel.add(new JLabel(questionNumber+") "+round.getQuestion().getText()));
        viewPanel.add(questionPanel);
        //and the answers
        for(int i=0; i<round.getAnswers().size();i++) {
            AnswerDto answerDto = round.getAnswers().get(i);
            JButton answerButton = new JButton("Select");
            JLabel answerLabel = new JLabel(answerDto.getText());
            JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,1));
            answerPanel.add(answerButton);
            answerPanel.add(answerLabel);
            viewPanel.add(answerPanel);

            selectButtons.add(answerButton);
            answerPanels.add(answerPanel);
            final int currentAnswerIndex = i;
            answerButton.addActionListener(
                    (ActionEvent e)->{
                        roundAnsweredCorrectly = currentAnswerIndex==correctAnswerIndex;
                        //color the right answer green
                        answerPanels.get(correctAnswerIndex).setBackground(green);
                        if(!roundAnsweredCorrectly) {
                            //color the wrong answer red
                            answerPanels.get(currentAnswerIndex).setBackground(red);
                        }
                        //deactivate all the buttons
                        for(JButton button: selectButtons) {
                            button.setEnabled(false);
                        }
                        //inform the listener
                        fireEventListener();
                    }
            );
        }
        //extract two buttons that might be disabled if joker is used
        //i need to work on a copy because i remove the other buttons
        extractTwoWrongAnswerButtons(new LinkedList<>(selectButtons), correctAnswerIndex);
    }

    private void extractTwoWrongAnswerButtons(List<JButton> selectButtons, int correctButtonIndex) {
        //remove the correct answer
        selectButtons.remove(correctButtonIndex);
        //remove random wrong answers until only two remain
        while(selectButtons.size()>2) {
            int randomIndex = (int)(Math.round(Math.random())%selectButtons.size());
            selectButtons.remove(randomIndex);
        }
        //remove
        twoWrongAnswerButtons = selectButtons;
    }

    public void ruleOutTwoWrongAnswers() {
        if(twoWrongAnswerButtons==null) {
            throw new IllegalStateException("twoWrongAnswerButtons shouldn't be null but is");
        }
        //disable those two wrong answer buttons
        for(JButton wrongAnswer: twoWrongAnswerButtons) {
            wrongAnswer.setEnabled(false);
        }
    }

    @Override
    protected void fireListener(RoundAnsweredListener listener) {
        if(roundAnsweredCorrectly!=null) {
            listener.roundAnswered(roundAnsweredCorrectly);
        }else{
            throw new IllegalStateException("roundAnsweredCorrectly shouldn't be null at this point");
        }
    }
}
