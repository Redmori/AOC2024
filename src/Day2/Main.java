package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            ArrayList<ArrayList<Integer>> reports = readInput();
            int safety = determineSafety(reports, 0);
            System.out.println("Day 2 part 1: " + safety);
            int safety2 = determineSafety(reports, 1);
            System.out.println("Day 2 part 2: " + safety2);

        }

    private static int determineSafety(ArrayList<ArrayList<Integer>> reports, int tolerance) {
        int amountSafe = 0;
        for(ArrayList<Integer> report : reports){
            if(reportSafe(report, tolerance)){
                amountSafe += 1;
            }
        }
        return amountSafe;
    }

    private static boolean reportSafe(ArrayList<Integer> report, int tolerance) {
        boolean positiveDirection = report.get(0) - report.get(1) > 0;

        boolean safeInDirection = reportCorrectDifferenceInDirection(report, positiveDirection, tolerance) == 0;

        if(!safeInDirection){
            safeInDirection = reportCorrectDifferenceInDirection(report, !positiveDirection, tolerance) == 0;
        }

        return safeInDirection;

    }

    private static int reportCorrectDifferenceInDirection(ArrayList<Integer> report, boolean positiveDirection, int tolerance) {
        int errors = 0;
        int errorPosition = 0;
        for (int i = 0; i < report.size() - 1; i++) {
            boolean correctDifference = false;
            if (positiveDirection) {
                correctDifference = acceptedLevelStep(report.get(i), report.get(i + 1));
            }
            else{
                correctDifference =  acceptedLevelStep(report.get(i+1), report.get(i));
            }
            if(!correctDifference){
                errors += 1;
                errorPosition = i;
            }
        }

        if(errors != 0 && errors <= tolerance*2) {
            ArrayList<Integer> copy1 = (ArrayList<Integer>) report.clone();
            copy1.remove(errorPosition);
            ArrayList<Integer> copy2 = (ArrayList<Integer>) report.clone();
            copy2.remove(errorPosition+1);
            return Math.min(
                    reportCorrectDifferenceInDirection(copy1, positiveDirection, tolerance-1),
                    reportCorrectDifferenceInDirection(copy2, positiveDirection, tolerance-1)
            );
        }
        else {
            return errors;
        }
    }

    private static boolean acceptedLevelStep(int low, int high) {
        return low - high >= 1 && low - high <= 3;
    }



    private static ArrayList<ArrayList<Integer>> readInput() {
        ArrayList<ArrayList<Integer>> reports = new ArrayList<>();
        File inputFile = new File("src/Day2/reports.txt");
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" ");
                ArrayList<Integer> report = new ArrayList<>();
                for(String str : splitLine){
                    report.add(Integer.parseInt(str));
                }
                reports.add(report);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reports;
    }
}