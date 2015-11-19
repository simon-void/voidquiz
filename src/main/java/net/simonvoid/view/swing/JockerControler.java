package net.simonvoid.view.swing;

import net.simonvoid.view.listener.EventListenerOwner;
import net.simonvoid.view.listener.JokerSelectedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by stephan on 18.11.2015.
 */
public class JockerControler extends EventListenerOwner<JokerSelectedListener> {
    private int numberOfTotalJokers;
    private int numberOfJokersLeft;
    private JPanel viewPanel;
    JButton useJokerButton;

    public JockerControler(int numberOfJockers) {
        numberOfJokersLeft = numberOfJockers;
        numberOfTotalJokers = numberOfJockers;
        viewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));

        createLayout();
    }

    @Override
    protected void fireListener(JokerSelectedListener listener) {
        listener.jokerSelected();
    }

    public Component getView() {
        return viewPanel;
    }

    public void reenableIfJokersLeft() {
        if(numberOfJokersLeft>0) {
            useJokerButton.setEnabled(true);
        }
    }

    public void disable() {
        useJokerButton.setEnabled(false);
    }

    private void createLayout() {
        JLabel label = new JLabel(getJokerStatus());
        useJokerButton = new JButton("rule out two answers");
        useJokerButton.addActionListener(
                (ActionEvent e)->{
                    numberOfJokersLeft--;
                    useJokerButton.setEnabled(false);
                    label.setText(getJokerStatus());
                    fireEventListener();
                }
        );
        viewPanel.add(useJokerButton);
        viewPanel.add(label);
    }

    private String getJokerStatus() {
        StringBuilder sb = new StringBuilder();
        if(numberOfJokersLeft>0) {
            sb.append(numberOfJokersLeft).append(" of ").append(numberOfTotalJokers);
            if(numberOfJokersLeft==1) {
                sb.append(" joker is ");
            }else {
                sb.append(" jokers are ");
            }
            sb.append("still available");
        }else{
            sb.append("all ").append(numberOfTotalJokers).append(" jokers were used");
        }
        return sb.toString();
    }
}
