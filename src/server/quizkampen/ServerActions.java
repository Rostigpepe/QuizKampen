package server.quizkampen;

import java.util.ArrayList;
import java.util.Random;

public class ServerActions {
    private static final Random random = new Random();
    private static final ArrayList<ArrayList<ClientHandler>> currentGames = new ArrayList<>();

    //Utility class
    private ServerActions(){}


    public static void startGame(){
        if(currentGames.isEmpty()){
            currentGames.add(addNewArrayList());
        }
        else if(currentGames.get(currentGames.size() - 1).size() < 2){
            currentGames.get(currentGames.size() - 1).add(addNewClientHandler());
        }
        else{
            currentGames.add(addNewArrayList());
        }

        sendRandomShit("Welcome to the game");
        sendQuestion();
    }


    public static ArrayList<ClientHandler> addNewArrayList(){
        ArrayList<ClientHandler> list = new ArrayList<>();
        int clientHandlerSize = ClientHandler.getClientHandlers();
        ClientHandler tempClientHandler = ClientHandler.clientHandlers.get(clientHandlerSize - 1);
        list.add(tempClientHandler);

        return list;
    }

    public static ClientHandler addNewClientHandler(){
        int clientHandlerSize = ClientHandler.getClientHandlers();

        return ClientHandler.clientHandlers.get(clientHandlerSize - 1);
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


    public static void waitForAnswers(int gameId){
        ArrayList<ClientHandler> activeGame = currentGames.get(gameId);
        boolean clientOneAnswered = false;
        boolean clientTwoAnswered = false;

        while(clientOneAnswered == false && clientTwoAnswered == false){

        }


    }
}