package serverside.quizkampen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    /**Constructor for the Server
     * @param serverSocket Socket which all clients will connect to
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        Questions.readQuestionsFromFile("resources/Geografi");
        Questions.readQuestionsFromFile("resources/Historia");
        Questions.readQuestionsFromFile("resources/Kultur");
    }

    /**Method used to start the server
     * Put simply, waits to accept a socket, creates ClientHandlers and assigns them to new threads
     */
    public void startServer() {
        try {
            while (!serverSocket.isClosed()){
                //socket.accept() is a blocking method, this means that the program will stop here
                //Until a connection is established
                Socket socket = serverSocket.accept();
                System.out.println("Successful connection");
                //Objects of this class will each handle communication with one client
                //The runnable interface is implemented on a class whose instances are executed by separate threads
                ClientHandler clientHandler = new ClientHandler(socket);

                //To handle multiple clients at once we need to assign every client a separate thread
                //Otherwise our program is only able to communicate with a single client at once
                Thread thread = new Thread(clientHandler);
                thread.start();

                //Add it so that multiple games can play at once
                ServerActions.startGame();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}