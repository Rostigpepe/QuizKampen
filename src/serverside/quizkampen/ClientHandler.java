package serverside.quizkampen;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ClientHandler implements Runnable{
    //Any constants
    private static final int QUESTIONS_PER_ROUND = Integer.parseInt(GameSettings.getTotalQuestionsString());
    private static final int TOTAL_ROUNDS = Integer.parseInt(GameSettings.getTotalRoundsString());

    //Arraylist to keep track of all current connected clients
    protected static final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    //All bits and pieces for the connection and communication
    //A socket represents the connection between a server and client
    //The socket also has an input and output stream
    private Socket socket;
    //Receive input from clients
    private BufferedReader bufferedReader;
    //Send output to clients
    private BufferedWriter bufferedWriter;

    private String clientUsername;

    //Variables to keep track of score and round status
    private int score = 0;
    private boolean waiting = false;
    private int currentGameQuestion;
    private int roundCategory;

    //It's, it's literally just a random seed
    private final Random random = new Random();

    /**Constructor for the ClientHandler class
     * @param socket What to connect to
     */
    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            //Character streams always ends with writer/reader, byte streams always end with stream
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            //This is assigned to have a starting round category, if you join a game this gets changed to match
            roundCategory = random.nextInt(5 - 1) + 1;

            //Adding this instance of clientHandler to the arraylist we'll use for overall communication
            clientHandlers.add(this);

        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }


    /**Overridden runnable method
     * Simply put, this ensures that anything within this method is running on a separate thread
     */
    @Override
    public void run() {
        String receivedInput;

        while(socket.isConnected()){
            try {
                //This is a blocking operation, which is the reason this needs to run in a separate thread
                receivedInput = bufferedReader.readLine();
                updateScore(receivedInput);
            } catch (IOException e){
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }


    /**General method to send packet between server and client
     * @param packet Information which is to be sent
     */
    public void sendPacket(String packet){
        try {
            this.bufferedWriter.write(packet);
            //We are listening for a new line, so we're sending one
            this.bufferedWriter.newLine();
            //We need to flush since it only auto flushes once the buffer is full, and we do NOT fill it
            this.bufferedWriter.flush();
        } catch(IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    /**Specific method to send packets of misc stuff, this opens a text window on the client side
     * Header of 0 equals general Text
     * @param stringToSend What we're sending to the client
     */
    public void sendGeneralPacket(String stringToSend){
        //Attaches the correct header
        String packet = "0" + stringToSend;
        sendPacket(packet);
    }

    /**Specific method to send a question
     * Header of 1 equals question
     * @param index Which question to get and send, this is random
     * @param category Which category is currently in play
     */
    public void sendQuestion(int index, int category){
        //Attaches the correct header
        String packet = "1" + Questions.getQuestionString(index, category);
        sendPacket(packet);
    }

    /**Specific method to tell a client to wait
     * Header 2 equals waiting
     */
    public void sendWaitRequest(){
        //Attaches the correct header
        String packet = "2";
        sendPacket(packet);
    }

    /**Specific method to tell a client the game is done
     * Header of 3 equals game is over
     * Sends a packet containing the results of the game
     * @param resultString String with results of the game, this is split up client side
     */
    public void sendResults(String resultString){
        //Attaches the correct header
        String packet = "3" + resultString;
        sendPacket(packet);
    }


    /**Method to update the score of clients, and to loop the program back around
     * It updates scores, checks if the round is over, and it checks if we should wait for the other client or not
     * @param answer The answer received from client
     */
    public void updateScore(String answer){
        //Auto assigns this client handler to be waiting, just in case this player is first to finish
        this.waiting = true;
        //Increases the overall question we are at
        currentGameQuestion++;

        //Depending on your result, updates score or not
        if(answer.equals("correct")){
            this.score += 1;
            sendGeneralPacket("You are correct, Score: " + score);
        }
        else{
            sendGeneralPacket("You are incorrect, Score: " + score);
        }

        //Changes the round if it is over
        //This depends on the settings for the game, and how many questions have been answered so far
        if(QUESTIONS_PER_ROUND == 1){
            sendGeneralPacket("Round is over, Score: " + score);
            this.roundCategory = random.nextInt(4 - 1) + 1;
        }
        else{
            if(currentGameQuestion != 1 && currentGameQuestion % QUESTIONS_PER_ROUND == 0){
                sendGeneralPacket("Round is over, Score: " + score);
                this.roundCategory = random.nextInt(4 - 1) + 1;
            }
        }

        //Decides if we should wait for the opponent or not
        //If the opponent is already waiting we continue and tell them to do so, otherwise we wait
        if(ServerActions.getWaitingFromOpponent(this).equals("waiting")){
            //Exiting the program and displaying the scores if the game is over
            if (currentGameQuestion == QUESTIONS_PER_ROUND * TOTAL_ROUNDS) {
                ServerActions.showResults(this);
            }
            else{
                //If the opponent IS waiting, that means both of us are now done, therefore the game should continue
                this.setRoundCategory(ServerActions.getRoundCategoryFromOpponent(this));
                ServerActions.sendQuestionToGame(this);
            }
        }
        else if(ServerActions.getWaitingFromOpponent(this).equals("not waiting")){
            //if the opponent is NOT waiting, that means that they're still doing their round, therefore we will wait
            sendWaitRequest();
        }
        else{
            //We need to add something in case there isn't another person in the game yet, in which case we will wait
            sendWaitRequest();
        }
    }


    /**Method to remove ClientHandler from the game when the player disconnects
     */
    public void removeClientHandler(){
        clientHandlers.remove(this);
        //This is printed in the server console
        System.out.println("Successfully removed");
    }


    //Some setters
    public void setWaitingFalse(){ this.waiting = false; }

    public void setRoundCategory(int roundCategory) { this.roundCategory = roundCategory; }


    //Some getters
    public static int getClientHandlers(){ return clientHandlers.size(); }

    public String getUsername(){ return this.clientUsername;}

    public boolean getWaiting(){ return this.waiting; }

    public int getScore(){ return this.score; }

    public int getRoundCategory() { return this.roundCategory; }


    /**Method used to close everything and essentially turn off the client in case of a fatal error
     * Only need to close the outer wrappers of IOStreams, rest is auto closed
     * @param socket Connection to terminate
     * @param bufferedReader Reader to close
     * @param bufferedWriter Write to close
     */
    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
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
}