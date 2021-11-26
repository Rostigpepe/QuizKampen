package serverside.quizkampen;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ClientHandler implements Runnable{
    //Arraylist to keep track of all current connected clients
    protected static final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    //A socket represents the connection between a server and client
    //The socket also has an input and output stream
    private Socket socket;
    //Receive input from clients
    private BufferedReader bufferedReader;
    //Send output to clients
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private int score = 0;
    private boolean waiting = false;

    private int index;
    private final Random random = new Random();
    private int roundCategory;
    private static final int QUESTIONS_PER_ROUND = Integer.parseInt(GameSettings.getTotalQuestionsString());
    private static final int TOTAL_ROUNDS = Integer.parseInt(GameSettings.getTotalRoundsString());

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            //Character streams always ends with writer/reader, byte streams always end with stream
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            roundCategory = random.nextInt(5 - 1) + 1;

            //Adding this instance of clientHandler to the arraylist we'll use for overall communication
            clientHandlers.add(this);

        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }


    @Override
    public void run() {
        String receivedInput;

        while(socket.isConnected()){
            try {
                //This is a blocking operation, yet another reason we want to run everything in separate threads
                receivedInput = bufferedReader.readLine();
                updateScore(receivedInput);
            } catch (IOException e){
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }


    //General method used to send a packet between the server and the client
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

    //Specific method to send packets with a little of whatever we want to send
    //Header of 0 equals general text
    public void sendGeneralPacket(String stringToSend){
        String packet = "0" + stringToSend;
        sendPacket(packet);
    }

    //Specific method to send packets with a question
    //Header of 1 equals question
    public void sendQuestion(int index, int category){
        String packet = "1" + Questions.getQuestionString(index, category);
        sendPacket(packet);
    }

    //Specific method to tell a client to wait
    //Header 2 equals waiting
    public void sendWaitRequest(){
        String packet = "2";
        sendPacket(packet);
    }

    //Specific method to tell a client the game is done
    //Header of 3 equals waiting
    public void sendResults(String resultString){
        String packet = "3" + resultString;
        sendPacket(packet);
    }

    //What we are using to update the score of every client and to loop the program back around
    public void updateScore(String answer){
        this.waiting = true;
        index++;
        //Add logic to check if the currently asked question
        if(answer.equals("correct")){
            this.score += 1;
            sendGeneralPacket("You are correct, Score: " + score);
        }
        else{
            sendGeneralPacket("You are incorrect, Score: " + score);
        }

        if(index != 1 && index % QUESTIONS_PER_ROUND == 0){
            sendGeneralPacket("Round is over, Score: " + score);
            this.roundCategory = random.nextInt(4 - 1) + 1;
        }

        if(ServerActions.getWaitingFromOpponent(this).equals("waiting")){
            //Exiting the program and displaying the scores if the game is over
            if (index == QUESTIONS_PER_ROUND * TOTAL_ROUNDS) {
                ServerActions.showResults(this);
            }
            else{
                //If the other person IS waiting, that means both of us are now done, therefore the game should continue
                this.setRoundCategory(ServerActions.getRoundCategoryFromOpponent(this));
                ServerActions.sendQuestionToGame(this);
            }
        }
        else if(ServerActions.getWaitingFromOpponent(this).equals("not waiting")){
            //if the other person is NOT waiting, that means that they're still doing their round, therefore we will wait
            sendWaitRequest();
        }
        else{
            //We need to add something in case there isn't another person in the game yet, in which case we will wait
            sendWaitRequest();
        }
    }

    //Removing a ClientHandler when someone disconnects
    public void removeClientHandler(){
        clientHandlers.remove(this);
        System.out.println("Successfully removed");
    }


    public void setWaitingFalse(){
        this.waiting = false;
    }

    public void setRoundCategory(int roundCategory) { this.roundCategory = roundCategory; }


    public static int getClientHandlers(){
        return clientHandlers.size();
    }

    public String getUsername(){ return this.clientUsername;}

    public boolean getWaiting(){
        return this.waiting;
    }

    public int getScore(){
        return this.score;
    }

    public int getRoundCategory() { return this.roundCategory; }

    //We only need to close the outer wrappers of the streams, the inner parts are auto closed
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