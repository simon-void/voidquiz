package net.simonvoid.view.swing;

import net.simonvoid.dto.QuizDto;
import net.simonvoid.datasource.QuizProvider;
import net.simonvoid.view.GuiWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by stephan on 18.11.2015.
 */
public class SwingGuiWrapper implements GuiWrapper {
    @Autowired
    private QuizProvider quizProvider;
    private JFrame frame;
    private JPanel activePanel;

    public SwingGuiWrapper() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        activePanel = new JPanel();
        JScrollPane visiblePane = new JScrollPane(activePanel);
        frame.setContentPane(visiblePane);
        frame.setSize(new Dimension(550, 270));
    }

    @Override
    public void startupGUI() {
        QuizDto quizDto = quizProvider.getQuiz();

        frame.setTitle(quizDto.getName());
        setActiveComponent(getStartPanel(quizDto));

        //move frame to middle of screen
        moveToScreenCenter(frame);
        frame.setVisible(true);
    }

    private void moveToScreenCenter(JFrame frame) {
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        Dimension frameSize = frame.getSize();
        Point centeredFramePos = new Point(
                (int)Math.round((displayMode.getWidth()-frameSize.getWidth())/2),
                (int)Math.round((displayMode.getHeight()-frameSize.getHeight())/2));
        frame.setLocation(centeredFramePos);
    }

    private void setActiveComponent(JComponent component) {
        activePanel.removeAll();
        activePanel.add(JComponentUtils.wrapInFlowLayoutPanel(component, FlowLayout.CENTER, 0, 0));
        repaintView();
    }

    private JPanel getStartPanel(final QuizDto quiz) {
        JPanel panel = new JPanel();
        JButton button = new JButton("Start with "+quiz.getName());
        panel.add(JComponentUtils.wrapInFlowLayoutPanel(button, FlowLayout.CENTER, 10, 50));

        button.addActionListener(
                (ActionEvent e)->{
                    RoundsControler roundsControler = new RoundsControler(quiz);
                    roundsControler.setListener(
                            (int numberOfCorrectAnswers, int totalNumberOfAnswers)->{
                                setActiveComponent(getSummaryPanel(quiz, numberOfCorrectAnswers, totalNumberOfAnswers));
                            }
                    );
                    setActiveComponent(roundsControler.getPanel());
                }
        );

        return panel;
    }

    private JPanel getSummaryPanel(final QuizDto quiz, int numberOfCorrectAnswers, int totalNumberOfAnswers) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel summary = new JLabel(numberOfCorrectAnswers+" answer(s) out of "+totalNumberOfAnswers+" were correct.");
        JButton button = new JButton("Restart with "+quiz.getName());
        panel.add(JComponentUtils.wrapInFlowLayoutPanel(summary, FlowLayout.CENTER, 10, 50));
        panel.add(JComponentUtils.wrapInFlowLayoutPanel(button, FlowLayout.CENTER, 10, 0));

        button.addActionListener(
                (ActionEvent e)->{
                    RoundsControler roundsControler = new RoundsControler(quiz);
                    roundsControler.setListener(
                            (int newNumberOfCorrectAnswers, int newTotalNumberOfAnswers)->{
                                setActiveComponent(getSummaryPanel(quiz, newNumberOfCorrectAnswers, newTotalNumberOfAnswers));
                            }
                    );
                    setActiveComponent(roundsControler.getPanel());
                }
        );

        return panel;
    }

    private void repaintView() {
        activePanel.validate();
        activePanel.repaint();
    }
}
