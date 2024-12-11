package AOCutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AOC {
    public static String[] input(String path){
        ArrayList<String> lines = new ArrayList<>();
        File inputFile = new File(path);
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return lines.toArray(new String[0]);
    }

    public static void printInput(String[] input){
        for(String line : input){
            System.out.println(line);
        }
    }

}
