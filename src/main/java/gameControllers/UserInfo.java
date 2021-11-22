package gameControllers;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

    public class UserInfo {


        Properties p = new Properties();
        OutputStream os = new FileOutputStream("konton.properties");



        public UserInfo() throws IOException {
            p.setProperty("Ronder:" , "1,2,3,4,5,6,7,8,9");
            p.setProperty("Fragor:" , "1,2,3,4,5,6,7,8,9");
            p.store(os, null);

        }

    }

