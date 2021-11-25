package Default;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Quiz implements ActionListener {

    String[] questions = {
            "Vad heter huvudstaden i Brasilien? ", "Vem är president i USA?","Vad heter vår fantastiska lärare?",
            "Vilket är det största däggdjuret?"


    };

    String [] genre = {"Geografi", "Samhällskunskap", "NO","Matte"};



    String[][] answerOptions = {
            {"Stockholm", "Buenos Aires", "Sao Paul", "Brasilia"},
            {"Donald Trump", "Kamala Harris", "Joe Biden", "John F.Kennedy"},
            {"Sigrun", "Sigrud", "Sigrub", "Sigrul"},
            {"Häst", "Ko","Älg","Blåval"}

    };
    String[] correctAnswers = {
            "4",
            "3",
            "1",
            "4"

    };

    String answer;
    int index;
    int correctGuesses = 0;

    int seconds = 10;
    private final int totalQuestions = Integer.parseInt(GameSettings.getTotalQuestionsString());
    private final int totalRounds = Integer.parseInt(GameSettings.getTotalRoundsString());


    JFrame frame = new JFrame();
    JPanel panel = new ImageBackground("quizkampenbakgrund.jpg");
    JLabel genreField = new JLabel();
    ImageIcon icon = new ImageIcon("nya_quizkampen.png");
    //ImageIcon iconGenre = new ImageIcon("quizkampenbakgrund.jpg");

    JLabel imageHolder = new JLabel(icon,JLabel.CENTER);
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
            timeLeft.setText("");
            displayAnswer();
        }
    });

    public Quiz() throws IOException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(335, 600);
        frame.setLayout(new BorderLayout());
        frame.setTitle("QUIZKAMPEN");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel.setSize(335,600);
        panel.setVisible(true);
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        imageHolder.setIcon(icon);
        panel.add(imageHolder,BorderLayout.NORTH);


        genreField.setBounds(90, 60, 150, 30);
        //genreField.setIcon(iconGenre);
        genreField.setBackground(new Color(66, 104, 230));
        genreField.setOpaque(true);
        genreField.setVisible(true);

        genreField.setForeground(new Color(255, 255, 255));
        genreField.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        genreField.setHorizontalAlignment(JTextField.CENTER);

        


        questionField.setBounds(20, 60, 290, 200);
        questionField.setBackground(new Color(255, 255, 255));
        questionField.setForeground(new Color(25, 25, 25));
        questionField.setFont(new Font("Avenir Next", Font.BOLD, 15));
        questionField.setHorizontalAlignment(JTextField.CENTER);
        questionField.setBorder(BorderFactory.createBevelBorder(1));
        questionField.setEditable(false);

        buttonA.setBounds(20, 290, 140, 100);
        buttonA.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        buttonA.setForeground(new Color(255, 255, 255));
        buttonA.setBackground(new Color(41, 66, 123));
        buttonA.setBorderPainted(false);
        buttonA.setOpaque(true);
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);

        buttonB.setBounds(170, 290, 140, 100);
        buttonB.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        buttonB.setForeground(new Color(255, 255, 255));
        buttonB.setBackground(new Color(41, 66, 123));
        buttonB.setBorderPainted(false);
        buttonB.setOpaque(true);
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);

        buttonC.setBounds(20, 400, 140, 100);
        buttonC.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        buttonC.setForeground(new Color(255, 255, 255));
        buttonC.setBackground(new Color(41, 66, 123));
        buttonC.setBorderPainted(false);
        buttonC.setOpaque(true);
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);

        buttonD.setBounds(170, 400, 140, 100);
        buttonD.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        buttonD.setForeground(new Color(255, 255, 255));
        buttonD.setBackground(new Color(41, 66, 123));
        buttonD.setBorder(BorderFactory.createBevelBorder(1));
        buttonD.setBorderPainted(false);
        buttonD.setOpaque(true);
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setVisible(true);

        timeLeft.setBounds(135, 510, 60, 60);
        timeLeft.setForeground(new Color(25, 255, 0));
        timeLeft.setFont(new Font("Avenir Next", Font.BOLD, 40));
        timeLeft.setOpaque(false);
        timeLeft.setHorizontalAlignment(JTextField.CENTER);
        timeLeft.setText(String.valueOf(seconds));

        scoreofRound.setBounds(25, 225, 100, 80);
        scoreofRound.setForeground(new Color(0, 244, 0));
        scoreofRound.setFont(new Font("Avenir Next", Font.BOLD, 40));
        scoreofRound.setHorizontalAlignment(JTextField.CENTER);

        congratulations.setBounds(25, 55, 100, 60);
        congratulations.setForeground(new Color(25, 255, 0));
        congratulations.setFont(new Font("Avenir next", Font.BOLD, 45));
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

    int roundCounter = 0;


    public void nextQuestion() {
        if (index >= totalQuestions*totalRounds) {
            results();

        } else {


            if (index == totalQuestions || totalQuestions == roundCounter)
            {
                JOptionPane.showMessageDialog(null, "Ronden  är slut, din poäng är: " + correctGuesses);
                roundCounter = roundCounter - totalQuestions;
            }
            roundCounter = roundCounter + 1;

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
            answer = "1";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonB) {
            answer = "2";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonC) {
            answer = "3";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonD) {
            answer = "4";
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

        if (!correctAnswers[index].equals("1")) {
            buttonA.setBackground(new Color(255, 0, 0));
        } else {
            buttonA.setBackground(new Color(0, 244, 0));
        }
        if (!correctAnswers[index].equals("2")) {
            buttonB.setBackground(new Color(255, 0, 0));
        } else {
            buttonB.setBackground(new Color(0, 244, 0));
        }
        if (!correctAnswers[index].equals("3")) {
            buttonC.setBackground(new Color(255, 0, 0));
        } else {
            buttonC.setBackground(new Color(0, 244, 0));
        }
        if (!correctAnswers[index].equals("4")) {
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

        scoreofRound.setText(correctGuesses + " rätt av " + totalQuestions*totalRounds + " möjliga");
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
