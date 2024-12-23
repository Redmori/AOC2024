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

    public static int[]  convertStringToInt(String[] input){
        int[] converted = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            converted[i] = Integer.parseInt(input[i]);
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

    public static void printIntMapFormatted(int[][] g) {
        int maxWidth = 6; // Fixed width for numbers (adjust as needed)
        String format = "%-" + maxWidth + "s"; // Left-aligned fixed width

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                int value = Integer.MAX_VALUE;
                if (g[i][j] < value){
                    value = g[i][j];
                }
                if(value == Integer.MAX_VALUE) {
                    System.out.printf(format, "X");
                }else{
                    System.out.printf(format, value);
                }
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

    public static void printBoolMap(boolean[][][] map){
        for(boolean[][] row : map){
            for(boolean[] cell : row){
                boolean found = false;
                for(boolean direction : cell){
                    if(direction){
                        found = true;
                    }
                }
                if(found)
                    System.out.print("X");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }

    public static int countBoolean(boolean[][][] map) {
        int sum = 0;
        for(boolean[][] row : map){
            for(boolean cell[] : row){
                boolean found = false;
                for(boolean direction : cell){
                    if(direction){
                        found = true;
                    }
                }
                if(found){
                    sum++;
                }
            }
        }
        return sum;
    }

    public static int[]  coordsOfChar(char[][] map, char token){
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if(map[x][y] == token){
                    return new int[]{y,x};
                }
            }
        }
        return new int[]{-1,-1};
    }
}
