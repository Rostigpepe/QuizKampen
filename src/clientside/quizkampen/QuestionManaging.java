package clientside.quizkampen;

    //I originally thought that more was going to be put into this class, did not end up like that
    public class QuestionManaging {

        //Utility class = Private constructor
        private QuestionManaging(){}

        /**Method used to split the question packet into its subcomponents
         * @param questionPacket Packet with all the question info bits
         * @return Returns an array of Strings, containing the question pieces in the correct order
         */
        public static String[] splitQuestion(String questionPacket){
            String[] splitQuestionInfo = questionPacket.split(",");

            for (int i = 0; i < splitQuestionInfo.length; i++) {
                splitQuestionInfo[i] = splitQuestionInfo[i].trim();
            }

            return splitQuestionInfo;
        }
    }

