package serverside.quizkampen;

import Default.LoadQuestionsAndRounds;
import clientside.quizkampen.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;


public class GameSettings extends JFrame implements ActionListener {

    private static String totalQuestionsString;
    private static String totalRoundsString;

    public static String getTotalQuestionsString() {return totalQuestionsString;}
    public static String getTotalRoundsString() {return totalRoundsString;}

   //LoadQuestionsAndRounds loadQuestionsAndRounds = new LoadQuestionsAndRounds();
    JComboBox comboBox;
    JComboBox comboBox2;
    JButton startGameButton;


    GameSettings() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        //Combobox 1
        comboBox = new JComboBox(LoadQuestionsAndRounds.getProperty("Questions:").split(","));
        this.add(comboBox);
        //Combobox 2
        comboBox2 = new JComboBox(LoadQuestionsAndRounds.getProperty("Rounds:").split(","));
        this.add(comboBox2);
        //Adding action listener
        comboBox.addActionListener(this);
        comboBox2.addActionListener(this);

        //Button to start the server
        startGameButton = new JButton("Starta spel");
        this.add(startGameButton);
        startGameButton.addActionListener(this);

        //Standard swing procedure
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            totalQuestionsString = (String) this.comboBox.getSelectedItem();
        }
        if (e.getSource() == comboBox2) {
            totalRoundsString = (String) this.comboBox2.getSelectedItem();
        }

        if (e.getSource() == startGameButton) {

            if (comboBox.getSelectedItem().equals("FRÅGOR")) {
                JOptionPane.showMessageDialog(null, "Ange antal frågor");
            }
            else if (comboBox2.getSelectedItem().equals("RONDER")) {
                JOptionPane.showMessageDialog(null, "Ange antal ronder");
            }
            else {
                this.setVisible(false);
                try {
                    //Change to server
                    //Call client via itself lmao
                    ServerSocket serverSocket = new ServerSocket(7777);
                    Server server = new Server(serverSocket);
                    server.startServer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
}