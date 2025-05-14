package example.weather.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class ApiKeyService {
    public String getAPIkey(String pathToFile) {
        String APIkey = null;
        try {
            File APIfile = new File(pathToFile);
            Scanner myReader = new Scanner(APIfile);
            APIkey = myReader.nextLine();
            System.out.println(APIkey);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return APIkey;
    }
}


