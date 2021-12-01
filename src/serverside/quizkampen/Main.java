package serverside.quizkampen;

import Default.QuestionsAndRounds;

import java.io.IOException;

public class Main {

    /**Main method for server side
     * Gives you several options for the amount of questions per round, and the amount of rounds
     * Starts the server inside GameSettings
     * @param args Still unsure what this does
     * @throws IOException Its necessary and should not happen
     */
    public static void main(String[] args) throws IOException {
        new QuestionsAndRounds();
        new GameSettings();
    }
}