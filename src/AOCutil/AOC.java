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

    public static char[][] convertStringToChar(String[] input){
        char[][] converted = new char[input.length][input[0].length()];
        for(int i = 0; i < input.length; i++){
            converted[i] = input[i].toCharArray();
        }
        return converted;
    }

    public static void printInput(char[][] input){
        for(char[] row : input){
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void printIntMap(int[][] input){
        for(int[] row : input){
            for(int c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static boolean[][] convertCharToBoolean(char[][] chars, char trueToken) {
        boolean[][] bool = new boolean[chars.length][chars[0].length];
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                bool[i][j] = chars[i][j] == trueToken;
            }
        }
        return bool;
    }

    public static void printBoolMap(boolean[][] map){
        for(boolean[] row : map){
            for(boolean cell : row){
                if(cell)
                    System.out.print("X");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }
}
