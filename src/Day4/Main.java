package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static String word = "XMAS";


    public static void main(String[] args) {
        ArrayList<ArrayList<Character>> input = readInput();
        char[][] field = convertToArray(input);

        int resultP1  = amountOfXMAS(field);
        System.out.println("Day 4 part 1: " + resultP1);
        int resultP2 = amountOfCrossMas(field);
        System.out.println("Day 4 part 2: " + resultP2);
    }


    private static int amountOfCrossMas(char[][] input){
        int amount = 0;
        for(int i = 1; i < input.length - 1; i++){
            for(int j = 1; j < input[i].length - 1; j++){
                amount += isCrossMas(input, i, j) ? 1 : 0;
            }
        }
        return amount;
    }

    private static boolean isCrossMas(char[][] input, int i, int j) {
        if(input[i][j] == 'A'){
            if(hasMAS(input, i, j, 1) && hasMAS(input, i, j, -1)){
                return true;
            }
        }
        return false;
    }

    private static boolean hasMAS(char[][] input, int i, int j, int dir) {
        return (input[i+1][j+dir] == 'M' || input[i+1][j+dir] == 'S') && (input[i-1][j-dir] == 'M' || input[i-1][j-dir] == 'S') && input[i-1][j-dir] != input[i+1][j+dir];
    }


    private static int amountOfXMAS(char[][] input) {
        int amount = 0;
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                amount += findXMAS(input, i, j);
            }
        }
        return amount;
    }

    private static int findXMAS(char[][] input, int i, int j) {
        int amount = 0;
        if(input[i][j] == 'X'){
            amount = findMASInAllDirections(input, i, j, amount);
        }
        return amount;
    }

    private static int findMASInAllDirections(char[][] input, int i, int j, int amount) {
        for(int k = -1; k <= 1; k++){
            for(int l = -1; l <= 1; l++){
                amount += findMAS(input, i + k, j + l, k, l, 'M') ? 1 : 0;
            }
        }
        return amount;
    }

    private static boolean findMAS(char[][] input, int i, int j, int idir, int jdir, char target) {
        if(isOutOfBounds(input, i, j))
            return false;
        if(target == 'S' && input[i][j] == target) {
            return true;
        }
        else if(input[i][j] == target) {
            return findMAS(input, i + idir, j + jdir, idir, jdir, word.charAt(word.indexOf(target) + 1));
        }
        else {
            return false;
        }
    }

    private static boolean isOutOfBounds(char[][] input, int i, int j) {
        return i< 0 || j  < 0 || i  >= input.length || j  >= input[0].length;
    }


    private static ArrayList<ArrayList<Character>> readInput() {
        ArrayList<ArrayList<Character>> input = new ArrayList<>();
        File inputFile = new File("src/Day4/input.txt");
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                ArrayList<Character> newLine = new ArrayList<>();
                String line = scanner.nextLine();
                for(int i = 0; i < line.length(); i++) {
                    newLine.add(line.charAt(i));
                }
                input.add(newLine);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    private static char[][] convertToArray(ArrayList<ArrayList<Character>> input) {
        char[][] array = new char[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            ArrayList<Character> innerList = input.get(i);
            char[] innerArray = new char[innerList.size()];
            for (int j = 0; j < innerList.size(); j++) {
                innerArray[j] = innerList.get(j);
            }
            array[i] = innerArray;
        }
        return array;
    }

    private static void printInput(ArrayList<ArrayList<Character>> input){
        for(ArrayList<Character> line : input){
            for(Character c : line){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static void printInput(char[][] input){
        for(char[] line : input){
            for(Character c : line){
                System.out.print(c);
            }
            System.out.println();
        }
    }
}