package util;

import model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    private static final String DIR = "data";
    private static final String FILE_NAME = DIR + File.separator + "user.txt";

    public static void saveFile(User user) throws IOException{
        File dir = new File(DIR);
        if (!dir.exists()){
            boolean ok = dir.mkdir();
            if (!ok){
                throw new IOException("The directory could not be created" + DIR);
            }
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))){
            bw.write(user.toString());
            bw.newLine();
        }
    }
}
