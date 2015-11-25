package net.simonvoid.view.swing;

import net.simonvoid.data.model.xml.QuizDto;
import net.simonvoid.data.service.HighscoreService;
import net.simonvoid.data.service.QuizProviderService;
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
    private QuizProviderService quizProvider;
    @Autowired
    private HighscoreService highscoreService;

    private JFrame frame;
    private JPanel viewPanel;

    public SwingGuiWrapper() {
        viewPanel = new JPanel();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane visiblePane = new JScrollPane(viewPanel);
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
        viewPanel.removeAll();
        viewPanel.add(ComponentUtils.wrapInFlowLayoutPanel(component, FlowLayout.CENTER, 0, 0));
        repaintView();
    }

    private JPanel getStartPanel(final QuizDto quiz) {
        JPanel panel = new JPanel();
        JButton button = new JButton("Start with "+quiz.getName());
        panel.add(ComponentUtils.wrapInFlowLayoutPanel(button, FlowLayout.CENTER, 10, 50));

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
        HighscoreControler highscoreControler = new HighscoreControler(highscoreService, numberOfCorrectAnswers);
        JButton button = new JButton("Restart with "+quiz.getName());
        panel.add(ComponentUtils.wrapInFlowLayoutPanel(summary, FlowLayout.CENTER, 10, 10));
        panel.add(ComponentUtils.wrapInFlowLayoutPanel(highscoreControler.getView(), FlowLayout.CENTER, 10, 15));
        panel.add(ComponentUtils.wrapInFlowLayoutPanel(button, FlowLayout.CENTER, 10, 10));

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
        ComponentUtils.forceRepaint(viewPanel);
    }
}
