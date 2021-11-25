package Default;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    //The clientHandler will await an assigned ID, so we'll send that right away
    public void sendAnswer(String correctAnswer){
        //Replacement for future implementation of a GUI and buttons
        Scanner uInput = new Scanner(System.in);

        String packet = uInput.nextLine();
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
        //new Thread(() -> {
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

        //}).start();
    }


    public void questionHandling(String questionPacket){
        //Index 0 == tietl
        //Index 1-4 == Svarsalternativ
        //Index 5 == korrekta svaret
        String[] splitQuestion = QuestionManaging.splitQuestion(questionPacket);

        for (int i = 0; i < splitQuestion.length; i++) {
            switch (i){
                case 0 -> System.out.println(splitQuestion[i]);
                case 1,2,3,4 -> System.out.println("Option " + i + ": " + splitQuestion[i]);
                case 5 -> System.out.print("Please enter your answer: ");
                default -> System.out.println("Pain i dont know whats happening");
            }
        }
        sendAnswer(splitQuestion[5]);
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