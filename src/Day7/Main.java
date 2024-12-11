package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Equation> equations = readInput();

        ArrayList<Equation> correctEquations = filterCorrectEquations(equations, 1);

        long sum = sumOfResults(correctEquations);

        System.out.println("Day 7 Part 1: " + sum);

        ArrayList<Equation> correctEquationsD2 = filterCorrectEquations(equations, 2);

        long sumD2 = sumOfResults(correctEquationsD2);

        System.out.println("Day 7 Part 2: " + sumD2);


    }


    public static ArrayList<Equation> filterCorrectEquations(ArrayList<Equation> equations , int part){
        ArrayList<Equation> correctEquations = new ArrayList<>();
        for(Equation eq : equations){
            if(eq.validate(part)){
                correctEquations.add(eq);
            }
        }
        return correctEquations;
    }

    public static long sumOfResults(ArrayList<Equation> equations){
        long sum = 0;
        for(Equation eq : equations){
            sum += eq.result;
        }
        return sum;
    }


    private static ArrayList<Equation> readInput() {
        File inputFile = new File("src/Day7/input.txt");
        ArrayList<Equation> input = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                long result = Long.parseLong(line.split(":")[0]);
                String[] inputValues = line.split(" ");
                ArrayList<String> stringValues = new ArrayList<>(Arrays.asList(inputValues));
                stringValues.removeFirst();
                ArrayList<Integer> values = new ArrayList<>();
                for(String v : stringValues){
                    values.add(Integer.parseInt(v));
                }
                input.add(new Equation(result,values));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
}