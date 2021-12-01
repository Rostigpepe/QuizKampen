package clientside.quizkampen;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Client implements ActionListener{
    //Any possible constants
    private static final String FONT_BIG = "Avenir Next";
    private static final String FONT_SMALL = "Avenir Next Condensed";


    //List for me to minimize code because im lazy
    private final ArrayList<JButton> buttonList = new ArrayList<>();

    //Variables for online interfacing
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    //Variables to keep track of the correct answer and what the user themselves answered
    String correctAnswer;
    String answer;
    //Stuff for handling score to properly display final round scores
    int correctGuesses = 0;
    int roundCounter = 0;

    //Seconds is for the timer
    //The rest is probably not going to be here
    int seconds = 20;
    private final ArrayList<Integer> allRoundCorrectGuesses = new ArrayList<>();
    private final ArrayList<JLabel> scoreOfEveryRound = new ArrayList<>();

    //Creating several objects for the GUI, these are later manipulated to look and work the way we want
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


    /**Constructor for Client class, this is what every participant uses to play
     * @param socket What to connect to
     * @param username Name of the player
     * @throws IOException Throwing IOException because it absolutely should not happen
     */
    public Client(Socket socket, String username) throws IOException {
        //Setting up connection to server and IOStreams for communication
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }

        //Just everything to set the GUI into motion, creating frames, panels, buttons etc
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
        genreField.setFont(new Font(FONT_SMALL, Font.BOLD, 15));
        genreField.setHorizontalAlignment(SwingConstants.CENTER);

        questionField.setBounds(20, 60, 290, 200);
        questionField.setBackground(new Color(255, 255, 255));
        questionField.setForeground(new Color(25, 25, 25));
        questionField.setFont(new Font(FONT_BIG, Font.BOLD, 15));
        questionField.setHorizontalAlignment(SwingConstants.CENTER);
        questionField.setBorder(BorderFactory.createBevelBorder(1));
        questionField.setEditable(false);


        createButton(buttonA, 20, 290, 140, 100);
        buttonA.addActionListener(this);
        buttonList.add(buttonA);

        createButton(buttonB, 170, 290, 140, 100);
        buttonB.addActionListener(this);
        buttonList.add(buttonB);

        createButton(buttonC, 20, 400, 140, 100);
        buttonC.addActionListener(this);
        buttonList.add(buttonC);

        createButton(buttonD, 170, 400, 140, 100);
        buttonD.addActionListener(this);
        buttonList.add(buttonD);


        timeLeft.setBounds(135, 510, 60, 60);
        timeLeft.setForeground(new Color(25, 255, 0));
        timeLeft.setFont(new Font(FONT_BIG, Font.BOLD, 40));
        timeLeft.setOpaque(false);
        timeLeft.setHorizontalAlignment(SwingConstants.CENTER);
        timeLeft.setText(String.valueOf(seconds));

        scoreOfRound.setBounds(25, 225, 300, 80);
        scoreOfRound.setForeground(new Color(0, 244, 0));
        scoreOfRound.setFont(new Font(FONT_BIG, Font.BOLD, 30));
        scoreOfRound.setHorizontalAlignment(SwingConstants.CENTER);

        congratulations.setBounds(25, 55, 100, 60);
        congratulations.setForeground(new Color(25, 255, 0));
        congratulations.setFont(new Font(FONT_BIG, Font.BOLD, 15));
        congratulations.setHorizontalAlignment(SwingConstants.CENTER);

        //Adding all the components the frame and its content panel
        frame.add(panel);
        panel.add(genreField);
        panel.add(questionField);
        panel.add(buttonA);
        panel.add(buttonB);
        panel.add(buttonC);
        panel.add(buttonD);
        panel.add(timeLeft);
        panel.add(timeLabel);

        //Adds initial round to round score tracking list, we reference this without adding a new one so its needed
        allRoundCorrectGuesses.add(0);
    }


    /**Method to minimize the amount of code when repeatedly making buttons
     * Standard template for our buttons
     * @param button Button to modify
     * @param x X coordinate
     * @param y Y coordinate
     * @param w Button width
     * @param h Button height
     */
    public void createButton(JButton button, int x, int y, int w, int h){
        button.setBounds(x, y, w, h);
        button.setFont(new Font(FONT_SMALL, Font.BOLD, 15));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(41, 66, 123));
        button.setBorder(BorderFactory.createBevelBorder(1));
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFocusable(false);
    }


    /**Method that displays a window with text in it
     * The window runs in a new thread
     * @param windowText Text to be displayed
     */
    public void displayTextWindow(String windowText){
        if(windowText.contains("Round is over")){
            //Resets this rounds correct guesses, changes the round and adds new bit to round score tracking list
            correctGuesses = 0;
            roundCounter++;
            allRoundCorrectGuesses.add(0);

            //Creates a label for the last rounds score, this is later used for score display
            JLabel newScoreOfRound = new JLabel();
            newScoreOfRound.setBounds(25, 225, 300, 80);
            newScoreOfRound.setForeground(new Color(0, 244, 0));
            newScoreOfRound.setFont(new Font(FONT_BIG, Font.BOLD, 30));
            newScoreOfRound.setHorizontalAlignment(SwingConstants.CENTER);
            //Stores all labels in a list for easy loop use
            scoreOfEveryRound.add(newScoreOfRound);
        }

        //The window gets its own thread so the game can keep running in the meantime
        Thread windowThread = new Thread(() -> {
            JFrame ioWindowFrame = new JFrame();
            JOptionPane.showMessageDialog(ioWindowFrame, windowText);
        }); windowThread.start();
    }

    /**Method to display a window for user input
     * Does not need a separate thread since we want to wait for input either way
     * @param windowText Text to be displayed
     * @return User input as a string
     */
    public static String takeInputWindow(String windowText){
        JFrame ioWindowFrame = new JFrame();
        return JOptionPane.showInputDialog(ioWindowFrame, windowText);
    }


    /**Sends our answer to the client handler
     * Checks right away in the client if we're right
     * The client handler simply needs to know that we've answered a question
     */
    public void sendAnswer(){
        String packet = answer;
        if(packet.equals(correctAnswer)){
            //If we're correct the correct guesses naturally goes up
            correctGuesses++;
            //Setting the current rounds score to equal our newly updated round score
            allRoundCorrectGuesses.set(roundCounter, correctGuesses);
            sendPacket("correct");
        }
        else{
            //If we're incorrect nothing really needs to happen
            sendPacket("false");
        }

    }


    /**General method to send something to the server
     * @param inputToSend What needs to be sent to the server
     */
    public void sendPacket(String inputToSend){
        try {
            bufferedWriter.write(inputToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }


    /**This is the method used to listen for, and do the right thing with a packet
     * The packet arrives with a header, a switch statement executes the correct thing based on the header
     */
    //This method used to run in a separate thread, this is not needed since when we listen, nothing else happens
    //It was originally based on a multiple client chat program, hence we needed to be able to send and receive
    //So that we could send messages while also receiving other peoples messages
    public void listenForPacket(){
        String receivedPacket;
        int header;

        while(socket.isConnected()){
            try{
                receivedPacket = bufferedReader.readLine();

                //Separating the header and the rest of the packet from each other
                header = Integer.parseInt(String.valueOf(receivedPacket.charAt(0)));
                receivedPacket = receivedPacket.substring(1);

                //Header of 0 equals show a text window with random text
                //Header of 1 equals display a new question to the player
                //Header of 2 equals that the other client isn't done yet
                //Header of 3 equals that the game is over, and that results needs to be shown
                switch (header){
                    case 0 -> displayTextWindow(receivedPacket);
                    case 1 -> questionHandling(receivedPacket);
                    case 2 -> waitForOtherClient();
                    case 3 -> showResults(receivedPacket);
                    default -> System.out.println("Problem with header in listenForPacket");
                }
            } catch (IOException e){
                closeAll(socket, bufferedReader, bufferedWriter);
            }
        }
    }



    /**Method that is called when a question is received
     * Put simply, takes the question info, splits it up and displays it
     * @param questionPacket String with all the information regarding the question
     */
    public void questionHandling(String questionPacket){
        //Enabling the buttons since we disable them as soon as an answer is put in
        for (JButton button : buttonList){
            button.setEnabled(true);
        }

        //Index 0 == Title
        //Index 1-4 == Answer alternatives
        //Index 5 == Correct answer
        String[] splitQuestion = QuestionManaging.splitQuestion(questionPacket);
        correctAnswer = splitQuestion[5];

        //Based on which bit in the splitQuestion array, we set text of the correct GUI component
        for (int i = 0; i < splitQuestion.length; i++) {
            switch (i){
                case 0 -> questionField.setText(splitQuestion[i]);
                case 1 -> buttonA.setText(splitQuestion[i]);
                case 2 -> buttonB.setText(splitQuestion[i]);
                case 3 -> buttonC.setText(splitQuestion[i]);
                case 4 -> buttonD.setText(splitQuestion[i]);
                //Did not have time to implement displaying which genre the question is
                case 5 -> genreField.setText("Question");
                default -> System.out.println("Issue in questionHandling switch statement");
            }
        }
        timer.start();
    }

    /**Overridden action listener method
     * Put simply, when you press a button, it registers which one and assigns the answer variable to that button
     * @param e Clicking a button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Stopping the timer as soon as you answer the question
        timer.stop();
        //Looping through all buttons and disabling them to avoid errors and weird interactions
        for (JButton button : buttonList){
            button.setEnabled(false);
        }

        //Depending on what button was pressed, the answer variable is changed to represent that button
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
        displayAnswer();
    }

    /**Method used for changing button colour to display the correct and incorrect answers
     */
    public void displayAnswer() {
        //Loops through the button list
        //If the correct answer equals the buttons number, it turns green
        //If the correct answer does not equal the buttons number, it turns red
        int i = 1;
        for (JButton button : buttonList){
            if(!correctAnswer.equals(Integer.toString(i))){
                button.setBackground(new Color(255, 0, 0));
            } else {
                button.setBackground(new Color(0, 244, 0));
            }
            i++;
        }

        //New timer to display the answer for 2 seconds
        Timer time = new Timer(2000, (ActionEvent e) -> {
            for (JButton button : buttonList){
                button.setBackground(new Color(41, 66, 123));
            }

            //Resets the game timer to have 20 seconds left
            seconds = 20;
            timeLeft.setText(String.valueOf(seconds));

            sendAnswer();
        });
        time.setRepeats(false);
        time.start();
    }

    /**Method to display the results at the end of a game
     * Put simply, displays every individual rounds amount of correct answers
     * @param result String to display score of both players
     * @throws IOException Should absolutely not get any exceptions, so might as well throw
     */
    public void showResults(String result) throws IOException {
        //The game is over, so we stop the timer from ticking
        timer.stop();
        //Setting the original content panel to invisible, and hence you can't interact with it
        panel.setVisible(false);

        //Creating a new content panel which contains all the scores
        JPanel scorePanel = new ImageBackground("quizkampenbakgrund.jpg");
        scorePanel.setSize(335, 600);
        scorePanel.setVisible(true);
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new GridLayout(scoreOfEveryRound.size(), 1));
        //Adding the new content panel to the frame itself
        frame.add(scorePanel);

        //For loop that sets the text of every JLabel to represent the round and the score of that round
        int i = 0;
        for (JLabel tempScoreOfRound : scoreOfEveryRound){
            tempScoreOfRound.setText("Round " + (i + 1) + ", " + allRoundCorrectGuesses.get(i) + " Points");
            i++;
            scorePanel.add(tempScoreOfRound);
        }
        scorePanel.revalidate();
        displayTextWindow(result);
    }

    /**Method used to close everything and essentially turn off the client in case of a fatal error
     * Only need to close the outer wrappers of IOStreams, rest is auto closed
     * @param socket Connection to terminate
     * @param bufferedReader Reader to close
     * @param bufferedWriter Write to close
     */
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


    /**Method used when we are waiting for the opponent to finish their question, or to connect
     */
    public void waitForOtherClient() {
        questionField.setText("Awaiting other client...");
        //Will just display text until our client receives something new
        listenForPacket();
    }

    /**Main method for client
     * Takes username input, creates a socket to connect to, starts the program
     * @param args I'm honestly still not sure what this means
     * @throws IOException Should not get an error, this is easier
     */
    public static void main(String[] args) throws IOException {
        String name = takeInputWindow("Please enter your name");

        //INSERT SERVER IP HERE, REPLACE LOCALHOST
        Socket socket = new Socket("localhost", 7777);
        Client client = new Client(socket, name);
        client.sendPacket(client.username);
        client.listenForPacket();
    }
}