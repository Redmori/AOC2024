package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("src/Day5/input.txt");
        Scanner scanner = new Scanner(inputFile);
        ArrayList<int[]> rules = readInputRules(scanner);
        ArrayList<ArrayList<Integer>> pages = readInputPages(scanner);

        int amount = checkPages(rules,pages, false);
        System.out.println("Day 5 Part 1: " + amount);

        int amountIncorrect = checkPages(rules, pages, true);
        System.out.println("Day 5 Part 2: " + amountIncorrect);
    }

    private static int checkPages(ArrayList<int[]> rules, ArrayList<ArrayList<Integer>> pages, boolean fixIncorrect) {
        int score = 0;
        for(ArrayList<Integer> update : pages){
            if(checkUpdate(rules, update, fixIncorrect) != fixIncorrect){
                score += updateScore(update);
            }
        }
        return score;
    }

    private static int updateScore(ArrayList<Integer> update) {
        return update.get(update.size()/2);
    }

    private static boolean checkUpdate(ArrayList<int[]> rules, ArrayList<Integer> update, boolean shouldSort) {
        boolean isCorrect = true;
        boolean adjusted;
        do{
            adjusted = false;
            for(int[] rule : rules){
                if (checkRule(update, rule)) {
                    isCorrect = false;
                    if(shouldSort) {
                        adjusted = true;
                        fixUpdateWithRule(rule, update);
                    }
                    else{
                        break;
                    }
                }
            }
        }
        while(adjusted);
        return isCorrect;
    }

    private static void fixUpdateWithRule(int[] rule, ArrayList<Integer> update) {
        int wrongNr = rule[1];
        update.remove((Integer)wrongNr);
        update.add(wrongNr);

    }

    private static boolean checkRule(ArrayList<Integer> update, int[] rule) {
        int indexLeft = update.indexOf(rule[0]);
        int indexRight = update.indexOf(rule[1]);
        return !((indexLeft != -1 && indexRight != -1 && indexLeft < indexRight) || indexLeft == -1 || indexRight == -1);
    }

    private static ArrayList<int[]> readInputRules(Scanner scanner) {
        ArrayList<int[]> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()){
                break;
            }
            String[] parts = line.split("\\|");
            input.add(new int[]{Integer.parseInt(parts[0]),Integer.parseInt(parts[1])});
        }
        return input;
    }

    private static void printRules(ArrayList<int[]> rules){
        for(int[] rule : rules){
            System.out.println(rule[0] + "," + rule[1]);
        }
    }

    private static ArrayList<ArrayList<Integer>> readInputPages(Scanner scanner) {
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            ArrayList<Integer> parsedParts = new ArrayList<Integer>();
            for(String part : parts){
                parsedParts.add(Integer.parseInt(part));
            }
            input.add(parsedParts);
        }
        return input;
    }

    private static void printPages(ArrayList<ArrayList<Integer>> pages){
        for(ArrayList<Integer> update : pages){
            for(Integer page : update){
                System.out.print(page + ",");
            }
            System.out.println();
        }
    }
}