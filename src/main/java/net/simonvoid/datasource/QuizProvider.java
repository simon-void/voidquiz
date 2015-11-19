package net.simonvoid.datasource;

import net.simonvoid.dto.QuizDto;

/**
 * Created by stephan on 18.11.2015.
 */
public interface QuizProvider {
    QuizDto getQuiz();
}
