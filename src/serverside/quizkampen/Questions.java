package serverside.quizkampen;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Questions {

    //Arraylists for all questions, and questions divided into categories
    protected static ArrayList<Questions> allQuestions = new ArrayList<>();
    protected static ArrayList<Questions> historyQuestions = new ArrayList<>();
    protected static ArrayList<Questions> geographyQuestions = new ArrayList<>();
    protected static ArrayList<Questions> cultureQuestions = new ArrayList<>();

    //Variables for every instance of the Questions class
    private final String questionTitle;
    private final String option1;
    private final String option2;
    private final String option3;
    private final String option4;
    private final int answer;

    /**Constructor for the Questions class
     * @param questionTitle Title of the question, aka the question itself
     * @param option1 Presented option one
     * @param option2 Presented option two
     * @param option3 Presented option three
     * @param option4 Presented option four
     * @param answer Correct answer in the form of a number, representing option 1 through 4
     */
    public Questions(String questionTitle,
                     String option1,
                     String option2,
                     String option3,
                     String option4,
                     int answer){
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;

        //Adds it to the arraylist for ALL the questions, no matter category
        allQuestions.add(this);
    }

    //Just converts the answer to a string from an integer
    public String getAnswerToString(){
        return Integer.toString(this.answer);
    }


    /**Method to get an instance of the Questions class, as one single string
     * @param index Which question we want, this is generated randomly
     * @param category Which category we want a question from
     * @return Returns the question as one coherent string
     */
    public static String getQuestionString(int index, int category){
        Questions question;
        int funnyIndex = index - 1;

        switch (category){
            //Category 1 == history
            //Category 2 == geography
            //Category 3 == culture
            case 1 -> question = historyQuestions.get(funnyIndex);
            case 2 -> question = geographyQuestions.get(funnyIndex);
            case 3 -> question = cultureQuestions.get(funnyIndex);
            default -> {
                question = allQuestions.get(funnyIndex);
                System.out.println("Problem in getQuestionString, fetching category went wrong");
            }
        }

        return buildQuestionString(question);
    }


    //Takes all the info from the question object and turns it into a string

    /**Method to build the string with the whole question in it
     * This is used when we're getting a question to be used in the game
     * @param question The question we are converting into a string
     * @return Returns the question in string format
     */
    public static String buildQuestionString(Questions question){
        //Assigning every bit of the question to a spot in an array
        String[] tempBuildArray = {question.questionTitle,
                question.option1,
                question.option2,
                question.option3,
                question.option4,
                question.getAnswerToString()};

        StringBuilder questionString = new StringBuilder();
        //Loops through the array and appends every bit of it to a string, separating with commas
        for (String questionBit : tempBuildArray){
            questionString.append(questionBit).append(",");
        }
        return questionString.toString();
    }


    /**Method used to read files with the purpose of creating questions
     * @param filePath Filepath of the file we want to read
     */
    public static void readQuestionsFromFile(String filePath){

        try (BufferedReader fileIn = new BufferedReader(new FileReader(filePath))){
            String line = fileIn.readLine();

            while(line != null){
                questionCreatorFromReadFile(line, filePath);
                line = fileIn.readLine();
            }


        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**Method used to create Question objects from a string input
     * Put simply, takes a string, splits it into its subcomponents, assigns component to correct Question variable
     * @param question Question read from file, this is in string format for now
     * @param filePath Filepath, used to decide what category this question is
     */
    public static void questionCreatorFromReadFile(String question, String filePath){
        //Splits the string into its subcomponents
        String[] splitQuestionInfo = question.split(",");

        //Cleans every subcomponent
        for (int i = 0; i < splitQuestionInfo.length; i++) {
            splitQuestionInfo[i] = splitQuestionInfo[i].trim();
            splitQuestionInfo[i] = splitQuestionInfo[i].replace(",", "");
        }

        //Calls the constructor and uses the subcomponent array to assign correct variables
        Questions tempQuestion = new Questions(splitQuestionInfo[0],
                splitQuestionInfo[1],
                splitQuestionInfo[2],
                splitQuestionInfo[3],
                splitQuestionInfo[4],
                Integer.parseInt(splitQuestionInfo[5]));

        //Depending on the filepath, we know which category the question was
        switch (filePath){
            case "resources/Geografi" -> geographyQuestions.add(tempQuestion);
            case "resources/Historia" -> historyQuestions.add(tempQuestion);
            case "resources/Kultur" -> cultureQuestions.add(tempQuestion);
            default -> System.out.println("Problem in questionCreatorFromReadFile, unknown filepath");
        }
    }
}