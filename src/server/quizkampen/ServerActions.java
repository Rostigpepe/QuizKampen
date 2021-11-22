package server.quizkampen;

import java.util.ArrayList;
import java.util.Random;

public class ServerActions {
    private static final Random random = new Random();
    private static final ArrayList<ClientHandler> gameOne = new ArrayList<>();
    private static final ArrayList<ClientHandler> gameTwo = new ArrayList<>();

    //Utility class
    private ServerActions(){}


    public static void startGame(){
        for (int i = 1; i < 5; i++) {
            if(i % 2 == 0){
                gameOne.add(ClientHandler.clientHandlers.get(i - 1));
            }
            else{
                gameTwo.add(ClientHandler.clientHandlers.get(i - 1));
            }
        }
        sendRandomShit("Welcome to the game");
        sendQuestion();
    }

    public static void sendRandomShit(String stringToSend){
        for (ClientHandler clientHandler : ClientHandler.clientHandlers){
            clientHandler.sendRandomShit(stringToSend);
        }
    }

    public static void sendQuestion(){
        int tempRand = random.nextInt(11 - 1 + 1);

        for (ClientHandler clientHandler : ClientHandler.clientHandlers){
            clientHandler.sendQuestion(tempRand);
        }
    }

    public static void waitForAnswers(){

    }
}