package serverside.quizkampen;


import java.util.ArrayList;
import java.util.Random;

public class ServerActions {
    private static final Random random = new Random();
    private static final ArrayList<ArrayList<ClientHandler>> currentGames = new ArrayList<>();

    //Utility class
    private ServerActions(){}

    //Method used to start up a new game for a client
    public static void startGame(){
        //If there are currently ZERO games, we'll create a new game
        if(currentGames.isEmpty()){
            currentGames.add(addNewArrayList());
        }
        //If there is currently a game with an empty spot, we'll add the client to that game
        else if(currentGames.get(currentGames.size() - 1).size() < 2){
            currentGames.get(currentGames.size() - 1).add(addNewClientHandler());
        }
        //If there are games, but no game with an empty spot, we'll create a new game
        else{
            currentGames.add(addNewArrayList());
        }

        //Welcoming them to the game
        sendGeneralPacket("Welcome to the game");

        //Gets the newest client handler and sends only them a question
        //We do not send a question to everyone in the game since we don't want the first potential player to get a duplicate question
        int clientHandlerSize = ClientHandler.getClientHandlers();
        ClientHandler tempClientHandler = ClientHandler.clientHandlers.get(clientHandlerSize - 1);
        sendQuestion(tempClientHandler);
    }


    //Adds a new game to the arraylist of all ongoing games
    //Automatically assigns the most recent client handler so that we do not return an empty list
    public static ArrayList<ClientHandler> addNewArrayList(){
        //Creates the new game
        ArrayList<ClientHandler> list = new ArrayList<>();

        //Gets the latest client handler to automatically add it to the new game
        int clientHandlerSize = ClientHandler.getClientHandlers();
        ClientHandler tempClientHandler = ClientHandler.clientHandlers.get(clientHandlerSize - 1);

        //Adds the client handler to the new game
        list.add(tempClientHandler);

        return list;
    }

    //This method returns the most recent client, so that we can add them to the most recent game
    public static ClientHandler addNewClientHandler(){
        int clientHandlerSize = ClientHandler.getClientHandlers();

        return ClientHandler.clientHandlers.get(clientHandlerSize - 1);
    }


    public static void sendGeneralPacket(String stringToSend){
        for (ClientHandler clientHandler : ClientHandler.clientHandlers){
            clientHandler.sendGeneralPacket(stringToSend);
        }
    }

    //Method to only send a question to one person
    public static void sendQuestion(ClientHandler clientHandler){
        int tempRand = random.nextInt(11 - 1 + 1);

        clientHandler.sendQuestion(tempRand);
    }

    //Method to send a question to both people in a game
    public static void sendQuestionToGame(ClientHandler clientHandler){
        int tempRand = random.nextInt(11 - 1 + 1);

        //We look for the game our specific client is assigned to
        for (ArrayList<ClientHandler> currentGame : currentGames){
            if (currentGame.contains(clientHandler)){
                //If a game contains our client, we'll send the question to every client in that specific game
                for (ClientHandler tempClientHandler : currentGame) {
                    tempClientHandler.setWaitingFalse();
                    tempClientHandler.sendQuestion(tempRand);
                }
            }
        }
    }

    //Method to get the waiting boolean from your opponent in the game
    public static String getWaitingFromOpponent(ClientHandler clientHandler){
        boolean opponentWaiting;

        //Checking through every game in the game list
        for (ArrayList<ClientHandler> currentGame : currentGames) {
            //If this game contains the specific user whose opponent we're looking for
            if (currentGame.contains(clientHandler) && currentGame.size() == 2) {
                //If index 0 equals the client whose opponent we're looking for, we know that index 1 is the opponent
                if(currentGame.get(0).equals(clientHandler)){
                    opponentWaiting = currentGame.get(1).getWaiting();
                }
                //If index 0 does NOT equal the client whose opponent we're looking for, we know that index 0 is the opponent
                else{
                    opponentWaiting = currentGame.get(0).getWaiting();
                }

                //if the opponent is waiting, we return waiting
                if(opponentWaiting){
                    return "waiting";
                }
                else{
                    return "not waiting";
                }
            }
            else if(currentGame.contains(clientHandler) && currentGame.size() < 2){
                return "no opponent yet";
            }
        }
        System.out.println("Something went very wrong");
        return "something went very wrong";
    }
}