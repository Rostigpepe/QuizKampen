package Default;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz implements ActionListener {

    String[] questions = {
            "Vad heter huvudstaden i Brasilien? ", "Vem är president i USA?"


    };

    String [] genre = {"Geografi", "Samhällskunskap"};



    String[][] answerOptions = {
            {"Stockholm", "Buenos Aires", "Sao Paul", "Brasilia"},
            {"Donald Trump", "Kamala Harris", "Joe Biden", "John F.Kennedy"}

    };
    String[] correctAnswers = {
            "D",
            "C",

    };

    String answer;
    int index;
    int correctGuesses = 0;
    int totalQuestions = questions.length;
    int seconds = 10;

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel genreField = new JLabel();
    JTextField questionField = new JTextField();
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();
    JLabel timeLabel = new JLabel();
    JLabel timeLeft = new JLabel();
    JLabel scoreofRound = new JLabel();
    JLabel congratulations = new JLabel();



    Timer timer = new Timer(1000, e -> {

        seconds--;
        timeLeft.setText(String.valueOf(seconds));
        if (seconds <= 0) {
            displayAnswer();
        }
    });

    public Quiz() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(440, 650);
        frame.getContentPane().setBackground(new Color(53, 140, 223));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel.setSize(440,650);
        panel.setBackground(new Color(53, 140, 223));
        panel.setVisible(true);
        panel.setLayout(new BorderLayout());


        genreField.setBounds(90, 60, 250, 30);
        genreField.setBackground(new Color(53, 140, 223));
        genreField.setForeground(new Color(255, 255, 255));
        genreField.setFont(new Font("Arial", Font.PLAIN, 15));
        genreField.setHorizontalAlignment(JTextField.CENTER);
        genreField.setOpaque(true);


        questionField.setBounds(10, 60, 420, 200);
        questionField.setBackground(new Color(255, 255, 255));
        questionField.setForeground(new Color(25, 25, 25));
        questionField.setFont(new Font("Arial", Font.BOLD, 20));
        questionField.setHorizontalAlignment(JTextField.CENTER);
        questionField.setBorder(BorderFactory.createBevelBorder(1));
        questionField.setEditable(false);

        buttonA.setBounds(10, 280, 200, 100);
        buttonA.setFont(new Font("Arial", Font.BOLD, 15));
        buttonA.setForeground(new Color(255, 255, 255));
        buttonA.setBackground(new Color(41, 66, 123));
        buttonA.setBorderPainted(false);
        buttonA.setOpaque(true);
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);

        buttonB.setBounds(230, 280, 200, 100);
        buttonB.setFont(new Font("Arial", Font.BOLD, 15));
        buttonB.setForeground(new Color(255, 255, 255));
        buttonB.setBackground(new Color(41, 66, 123));
        buttonB.setBorderPainted(false);
        buttonB.setOpaque(true);
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);

        buttonC.setBounds(10, 400, 200, 100);
        buttonC.setFont(new Font("Arial", Font.BOLD, 15));
        buttonC.setForeground(new Color(255, 255, 255));
        buttonC.setBackground(new Color(41, 66, 123));
        buttonC.setBorderPainted(false);
        buttonC.setOpaque(true);
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);

        buttonD.setBounds(230, 400, 200, 100);
        buttonD.setFont(new Font("Arial", Font.BOLD, 15));
        buttonD.setForeground(new Color(255, 255, 255));
        buttonD.setBackground(new Color(41, 66, 123));
        buttonD.setBorder(BorderFactory.createBevelBorder(1));
        buttonD.setBorderPainted(false);
        buttonD.setOpaque(true);
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setVisible(true);

        timeLeft.setBounds(190, 530, 60, 60);
        timeLeft.setForeground(new Color(25, 255, 0));
        timeLeft.setFont(new Font("Times New Roman", Font.BOLD, 40));
        timeLeft.setOpaque(false);
        timeLeft.setHorizontalAlignment(JTextField.CENTER);
        timeLeft.setText(String.valueOf(seconds));

        scoreofRound.setBounds(25, 225, 400, 80);
        scoreofRound.setForeground(new Color(0, 244, 0));
        scoreofRound.setFont(new Font("Arial", Font.BOLD, 40));
        scoreofRound.setHorizontalAlignment(JTextField.CENTER);

        congratulations.setBounds(25, 55, 400, 60);
        congratulations.setForeground(new Color(25, 255, 0));
        congratulations.setFont(new Font("Arial", Font.BOLD, 45));
        congratulations.setHorizontalAlignment(JTextField.CENTER);

        frame.add(panel);
        panel.add(genreField);
         panel.add(questionField);
         panel.add(buttonA);
         panel.add(buttonB);
         panel.add(buttonC);
         panel.add(buttonD);
         panel.add(timeLeft);
         panel.add(timeLabel);


        nextQuestion();
    }

    public void nextQuestion() {
        if (index >= totalQuestions) {
            results();
        } else {

            questionField.setText(questions[index]);
            genreField.setText(genre[index]);
            buttonA.setText(answerOptions[index][0]);
            buttonB.setText(answerOptions[index][1]);
            buttonC.setText(answerOptions[index][2]);
            buttonD.setText(answerOptions[index][3]);
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        if (e.getSource() == buttonA) {
            answer = "A";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonB) {
            answer = "B";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonC) {
            answer = "C";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonD) {
            answer = "D";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        displayAnswer();

    }

    public void displayAnswer() {
        timer.stop();
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);

        if (!correctAnswers[index].equals("A")) {
            buttonA.setBackground(new Color(255, 0, 0));
        } else {
            buttonA.setBackground(new Color(0, 244, 0));
        }
        if (!correctAnswers[index].equals("B")) {
            buttonB.setBackground(new Color(255, 0, 0));
        } else {
            buttonB.setBackground(new Color(0, 244, 0));
        }
        if (!correctAnswers[index].equals("C")) {
            buttonC.setBackground(new Color(255, 0, 0));
        } else {
            buttonC.setBackground(new Color(0, 244, 0));
        }
        if (!correctAnswers[index].equals("D")) {
            buttonD.setBackground(new Color(255, 0, 0));
        } else {
            buttonD.setBackground(new Color(0, 244, 0));
        }

        Timer time = new Timer(2000, (ActionEvent e) -> {
            buttonA.setBackground(new Color(41, 66, 123));
            buttonB.setBackground(new Color(41, 66, 123));
            buttonC.setBackground(new Color(41, 66, 123));
            buttonD.setBackground(new Color(41, 66, 123));

            answer = " ";
            seconds = 10;
            timeLeft.setText(String.valueOf(seconds));
            buttonA.setEnabled(true);
            buttonB.setEnabled(true);
            buttonC.setEnabled(true);
            buttonD.setEnabled(true);
            index++;

            nextQuestion();
        });
        time.setRepeats(false);
        time.start();
    }

    public void results() {
        timer.stop();
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);


        genreField.setVisible(false);
        questionField.setVisible(false);
        buttonA.setVisible(false);
        buttonB.setVisible(false);
        buttonC.setVisible(false);
        buttonD.setVisible(false);
        timeLeft.setVisible(false);
        timeLabel.setVisible(false);

        scoreofRound.setText(correctGuesses + " rätt av " + totalQuestions + " möjliga");
        if((double)correctGuesses/(double)totalQuestions>0.5) {
            congratulations.setText("BRA JOBBAT!!");
        }
        else {
            congratulations.setText("BRA FÖRSÖK!");
        }
        panel.add(congratulations);
        panel.add(scoreofRound);

    }
}
