package quiz.quizkampen;

import java.io.*;
import java.util.*;

import java.util.ArrayList;


/* Exempel på kultur frågor
Vilket år föddes Michael Jackson?,1958,1968,1961,1972,1
Vem var första James Bond?,Sean Connery,Roger Moore,George Lazenby,Timothy Dolton,1
När dog Ingmar Bergman?,2007,2000,1997,2002,1
Vad heter Alfons Åbergs pappa i böckerna?,Bertil,Bert,Bosse,Bengt,1*/

public class Questions {

    public static void main(String[] args) throws Exception {

        ArrayList<ArrayList<String> > kultur =  new ArrayList<ArrayList<String> >();


/* beror på vart txt filen finns*/
        FileReader fr = new FileReader("/Users/mahadmohamud/Desktop/Quizkampen/Kultur.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();

        while(line != null) {
            ArrayList<String> eachQuestion = new ArrayList<String>();
            String question = line.substring(0 , line.indexOf("?") + 1);
            String[] parts = line.split(",");

            String option1 = parts[1] ;
            String option2 = parts[2];
            String option3 = parts[3];
            String option4 = parts[4] ;

            String answer = parts[5];

            eachQuestion.add(question);
            eachQuestion.add(option1);
            eachQuestion.add(option2);
            eachQuestion.add(option3);
            eachQuestion.add(option4);

            eachQuestion.add(answer);

            kultur.add(eachQuestion);




            //System.out.println("Question : " + question + " option1 : " +option1 + " option2 : "+ option2 + " option3 : " + option3 + " option4 : "+ option4 + " answer : "+ answer );

            line = br.readLine();
        }
        for(ArrayList eachQues : kultur) {
            System.out.println(eachQues);
        }

        br.close();
    }


}