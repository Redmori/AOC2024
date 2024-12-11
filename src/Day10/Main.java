package Day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[][] input = readInput();
        printMap(input);

        Cell[][] map = convertToMap(input);
        setNeighbours(map);

        int paths = determinePeaks(map);
        System.out.println("Day 10 Part 1: " + paths);

        int pathsP2 = determineAllPeaks(map);
        System.out.println("Day 10 Part 2: " + pathsP2);

    }

    private static int determinePeaks(Cell[][] map) {
        int amount = 0;
        for(Cell[] row : map){
            for(Cell cell: row){
                if(cell.height == 0){
                    cell.getPeaks();
                    amount += cell.savedPeaks.size();
                }
            }
        }
        return amount;
    }

    private static int determineAllPeaks(Cell[][] map) {
        int amount = 0;
        for(Cell[] row : map){
            for(Cell cell: row){
                if(cell.height == 0){
                    cell.getAllPeaks();
                    amount += cell.allSavedPeaks.size();
                }
            }
        }
        return amount;
    }

    private static void setNeighbours(Cell[][] map) {
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++) {
                if(i > 0) {
                    map[i][j].neighbourN = map[i - 1][j];
                    map[i - 1][j].neighbourS = map[i][j];
                }

                if(j > 0) {
                    map[i][j].neighbourW = map[i][j - 1];
                    map[i][j - 1].neighbourE = map[i][j];
                }
            }
        }
    }

    private static Cell[][] convertToMap(int[][] input){
        Cell[][] map = new Cell[input.length][input[0].length];
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                map[i][j] = new Cell(input[i][j],j,i);
            }
        }
        return map;
    }

    private static void printMap(int[][] input){
        for(int[] row : input){
            for(int cell : row){
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    private static int[][] readInput() {
        File inputFile = new File("src/Day10/input.txt");
        ArrayList<int[]> input = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int[] intRow = line.chars().map(Character::getNumericValue).toArray();
                input.add(intRow);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input.toArray(new int[input.size()][]);
    }
}