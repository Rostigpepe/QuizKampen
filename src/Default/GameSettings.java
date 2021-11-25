package Default;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



public class GameSettings extends JFrame implements ActionListener {

    public static String totalQuestionsString;
    public static String getTotalQuestionsString() {return totalQuestionsString;}
    public static String totalRoundsString;
    public static String getTotalRoundsString() {return totalRoundsString;}

    LoadQuestionsAndRounds loadQuestionsAndRounds = new LoadQuestionsAndRounds();
    JComboBox comboBox;
    JComboBox comboBox2;
    JButton startGameButton;


    GameSettings() throws IOException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        comboBox = new JComboBox(LoadQuestionsAndRounds.getProperty("Questions:").split(","));
        this.add(comboBox);
        comboBox2 = new JComboBox(LoadQuestionsAndRounds.getProperty("Rounds:").split(","));
        this.add(comboBox2);
        comboBox.addActionListener(this);
        comboBox2.addActionListener(this);
        startGameButton = new JButton("Starta spel");
        this.add(startGameButton);
        startGameButton.addActionListener(this);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == comboBox)
        {
            totalQuestionsString = (String) this.comboBox.getSelectedItem();
        }

        if (e.getSource() == comboBox2)
        {
            totalRoundsString = (String) this.comboBox2.getSelectedItem();
        }

        if (e.getSource() == startGameButton) {

            if (comboBox.getSelectedItem().equals("FRÅGOR"))  {
                JOptionPane.showMessageDialog(null, "Ange antal frågor");
            }
            else if ( comboBox2.getSelectedItem().equals("RONDER"))
            {
                JOptionPane.showMessageDialog(null, "Ange antal ronder");
            }
            else {
                this.setVisible(false);
                try {
                    new Quiz();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
}