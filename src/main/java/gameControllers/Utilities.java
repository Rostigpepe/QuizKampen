package gameControllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utilities {

    public static String getProperty(String propertyName)
    {
        return properties.getProperty(propertyName);
    }
    private static Properties properties = null;


    static {
        FileInputStream fileInputStream = null;
        try {
            properties = new Properties();
            fileInputStream = new FileInputStream("konton.properties");
            properties.load(fileInputStream);

        } catch (Exception e){

        }
        finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


