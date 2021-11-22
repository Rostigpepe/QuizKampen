package gameControllers;

public class CatQuesAnsw {


    public String[] getQuestions() {
        return questions;
    }

    public String[][] getAnswerOptions() {
        return answerOptions;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public CatQuesAnsw() {

    }

    public String[] getGenre() {
        return genre;
    }


   private final String[] questions = {"Vad heter huvudstaden i Brasilien? ",
           "Vem är president i USA?", "Vad är den kemiska beteckningen för vatten ?"};

   private final String[] genre = {"Geografi", "Samhällskunskap", "Naturvetenskap"};

  private final String[][] answerOptions = {
            {"Stockholm", "Buenos Aires", "Sao Paul", "Brasilia"},
            {"Donald Trump", "Kamala Harris", "Joe Biden", "John F.Kennedy"},
            {"G20", "H2O", "F2O", "S2O"}
  };
   private final String[] correctAnswers = {
            "D",
            "C",
            "B"
    };

}
