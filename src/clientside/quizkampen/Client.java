package clientside.quizkampen;

import serverside.quizkampen.GameSettings;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Client implements ActionListener{
    //List for me to minimize code because im lazy
    private ArrayList<JButton> buttonList = new ArrayList<>();

    //Variables for online interfacing
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    //TEMPORARY STORAGE
    //MIGHT BE MOVED TO CLIENTHANDLER
    //Stuff for handling score
    //Index is which question we're currently on
    String correctAnswer;
    String answer;
    int index;
    int correctGuesses = 0;
    int roundCounter = 0;

    //Seconds is for the timer
    //The rest is probably not going to be here
    int seconds = 20;
    //private final int totalQuestions = Integer.parseInt(GameSettings.getTotalQuestionsString());
    //private final int totalRounds = Integer.parseInt(GameSettings.getTotalRoundsString());

    //A lot of variables for the GUI
    JFrame frame = new JFrame();
    JPanel panel = new ImageBackground("quizkampenbakgrund.jpg");
    JLabel genreField = new JLabel();
    ImageIcon icon = new ImageIcon("nya_quizkampen.png");

    JLabel imageHolder = new JLabel(icon, SwingConstants.CENTER);
    JTextField questionField = new JTextField();
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();
    JLabel timeLabel = new JLabel();
    JLabel timeLeft = new JLabel();
    JLabel scoreOfRound = new JLabel();
    JLabel congratulations = new JLabel();


    //Timer to be fancy
    Timer timer = new Timer(1000, e -> {
        seconds--;
        timeLeft.setText(String.valueOf(seconds));
        if (seconds <= 0) {
            timeLeft.setText("");
            displayAnswer();
        }
    });

    public Client(Socket socket, String username) throws IOException {
        //Fixing all the stuff needed for it to work between multiple clients
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }

        //All of this is for the GUI
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        genreField.setBackground(new Color(66, 104, 230));
        genreField.setOpaque(true);
        genreField.setVisible(true);

        genreField.setForeground(new Color(255, 255, 255));
        genreField.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        genreField.setHorizontalAlignment(SwingConstants.CENTER);




        questionField.setBounds(20, 60, 290, 200);
        questionField.setBackground(new Color(255, 255, 255));
        questionField.setForeground(new Color(25, 25, 25));
        questionField.setFont(new Font("Avenir Next", Font.BOLD, 15));
        questionField.setHorizontalAlignment(SwingConstants.CENTER);
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
        buttonList.add(buttonA);

        buttonB.setBounds(170, 290, 140, 100);
        buttonB.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        buttonB.setForeground(new Color(255, 255, 255));
        buttonB.setBackground(new Color(41, 66, 123));
        buttonB.setBorderPainted(false);
        buttonB.setOpaque(true);
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonList.add(buttonB);

        buttonC.setBounds(20, 400, 140, 100);
        buttonC.setFont(new Font("Avenir Next Condensed", Font.BOLD, 15));
        buttonC.setForeground(new Color(255, 255, 255));
        buttonC.setBackground(new Color(41, 66, 123));
        buttonC.setBorderPainted(false);
        buttonC.setOpaque(true);
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonList.add(buttonC);

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
        buttonList.add(buttonD);

        timeLeft.setBounds(135, 510, 60, 60);
        timeLeft.setForeground(new Color(25, 255, 0));
        timeLeft.setFont(new Font("Avenir Next", Font.BOLD, 40));
        timeLeft.setOpaque(false);
        timeLeft.setHorizontalAlignment(SwingConstants.CENTER);
        timeLeft.setText(String.valueOf(seconds));

        scoreOfRound.setBounds(25, 225, 100, 80);
        scoreOfRound.setForeground(new Color(0, 244, 0));
        scoreOfRound.setFont(new Font("Avenir Next", Font.BOLD, 30));
        scoreOfRound.setHorizontalAlignment(SwingConstants.CENTER);

        congratulations.setBounds(25, 55, 100, 60);
        congratulations.setForeground(new Color(25, 255, 0));
        congratulations.setFont(new Font("Avenir next", Font.BOLD, 15));
        congratulations.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(panel);
        panel.add(genreField);
        panel.add(questionField);
        panel.add(buttonA);
        panel.add(buttonB);
        panel.add(buttonC);
        panel.add(buttonD);
        panel.add(timeLeft);
        panel.add(timeLabel);
    }

    //The clientHandler will await an assigned ID, so we'll send that right away
    public void sendAnswer(String correctAnswer){
        String packet = answer;
        if(packet.equals(correctAnswer)){
            sendPacket("correct");
        }
        else{
            sendPacket("false");
        }

    }


    //Lil method to not have to write all the below memes over and over again
    public void sendPacket(String inputToSend){
        try {
            bufferedWriter.write(inputToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    //Header of 0 equals random bs
    //Header of 1 equals question
    //Header of 2 equals other client isn't done yet
    //This whole method will be a blocking one, therefore we'll run it in a difference thread
    public void listenForPacket(){
        String receivedPacket;
        int header;

        while(socket.isConnected()){
            try{
                receivedPacket = bufferedReader.readLine();

                //Separating the header and the rest of the string
                header = Integer.parseInt(String.valueOf(receivedPacket.charAt(0)));
                receivedPacket = receivedPacket.substring(1);

                switch (header){
                    case 0 -> System.out.println(receivedPacket);
                    case 1 -> questionHandling(receivedPacket);
                    case 2 -> waitForOtherClient();
                    default -> System.out.println("Problem with the header");
                }
            } catch (IOException e){
                closeAll(socket, bufferedReader, bufferedWriter);
            }
        }
    }


    //Whole ass method to deal with questions
    public void questionHandling(String questionPacket){
        //Enabling the buttons since we disable them as soon as an answer is put in
        for (JButton button : buttonList){
            button.setEnabled(true);
        }

        //Index 0 == Titel
        //Index 1-4 == Svarsalternativ
        //Index 5 == korrekta svaret
        String[] splitQuestion = QuestionManaging.splitQuestion(questionPacket);
        correctAnswer = splitQuestion[5];

        for (int i = 0; i < splitQuestion.length; i++) {
            switch (i){
                case 0 -> questionField.setText(splitQuestion[i]);
                case 1 -> buttonA.setText(splitQuestion[i]);
                case 2 -> buttonB.setText(splitQuestion[i]);
                case 3 -> buttonC.setText(splitQuestion[i]);
                case 4 -> buttonD.setText(splitQuestion[i]);
                case 5 -> genreField.setText("WIP, fix genre");
                default -> System.out.println("Pain i dont know whats happening");
            }
        }
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();
        for (JButton button : buttonList){
            button.setEnabled(false);
        }

        if (e.getSource() == buttonA) {
            answer = "1";
        }
        if (e.getSource() == buttonB) {
            answer = "2";
        }
        if (e.getSource() == buttonC) {
            answer = "3";
        }
        if (e.getSource() == buttonD) {
            answer = "4";
        }
        System.out.println(answer);
        displayAnswer();
    }


    public void displayAnswer() {
        //Loops through the button list, gets button A through D
        for (int i = 0; i < 4; i++) {
            if(!correctAnswer.equals(Integer.toString(i))){
                buttonList.get(i).setBackground(new Color(255, 0, 0));
            } else {
                buttonList.get(i).setBackground(new Color(0, 244, 0));
            }
        }

        Timer time = new Timer(2000, (ActionEvent e) -> {
            for (JButton button : buttonList){
                button.setBackground(new Color(41, 66, 123));
            }

            seconds = 20;
            timeLeft.setText(String.valueOf(seconds));
            for (JButton button : buttonList){
                button.setEnabled(true);
            }
            index++;

            sendAnswer(correctAnswer);
        });
        time.setRepeats(false);
        time.start();
    }


    public void results() {
        timer.stop();
        for (JButton button : buttonList){
            button.setEnabled(false);
        }

        genreField.setVisible(false);
        questionField.setVisible(false);
        for (JButton button : buttonList){
            button.setVisible(false);
        }
        timeLeft.setVisible(false);
        timeLabel.setVisible(false);

        scoreOfRound.setText("Totala poäng: "+ correctGuesses);
        panel.add(congratulations);
        panel.add(scoreOfRound);
    }


    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void waitForOtherClient() {
        System.out.println("Awaiting other client to finish their question...");
        listenForPacket();
    }


    public static void runLoop() throws IOException {
        Scanner uInput = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String name = uInput.nextLine();

        Socket socket = new Socket("localhost", 7777);
        Client client = new Client(socket, name);
        client.sendPacket(client.username);
        client.listenForPacket();
    }


    public static void main(String[] args) throws IOException {
        runLoop();
    }
}