package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Locations input = ReadInput();
        input.sortLists();
        ArrayList<Integer> differences = input.calculateDifferences();
        int sum = sumArray(differences);
        System.out.println("Day 1 first puzzle: " + sum);
        int similarity = input.calculateSimilarityScore();
        System.out.println("Day 1 second puzzle: " + similarity);

    }


    private static Locations ReadInput() {
        String locations = "";
        ArrayList<Integer> locationsLeft = new ArrayList<Integer>();
        ArrayList<Integer> locationsRight = new ArrayList<Integer>();

        File inputFile = new File("src/Day1/locations.txt");
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" ");
                locationsLeft.add(Integer.valueOf(splitLine[0]));
                locationsRight.add(Integer.valueOf(splitLine[splitLine.length-1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Locations(locationsLeft, locationsRight);
    }


    public static int sumArray(ArrayList<Integer> list){
        int sum = 0;
        for(Integer value : list){
            sum += value;
        }
        return sum;
    }
}