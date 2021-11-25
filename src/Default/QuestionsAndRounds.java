package Default;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Niklas Sahlberg
 * Date: 2021-11-24
 * Time: 21:23
 * Project: QuizKampenSwing
 * Copyright: MIT
 */

public class QuestionsAndRounds {
    Properties p = new Properties();
    OutputStream os = new FileOutputStream("questionsAndRounds.properties");

    public QuestionsAndRounds() throws IOException {
        p.setProperty("Questions:", "FRÅGOR,1,2,3,4,5,6,7,8,9");
        p.setProperty("Rounds:", "RONDER,1,2,3,4,5,6,7,8,9");
        p.store(os, null);

    }

}