package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Main {

    public static void main(String[] args) {
        String input = readInput();
        System.out.println("Day 3 part 1: " + sumOfMultiplicationList(getMulNumbers(input)));
        String[] dos = grabDoStrings(input);
        String[] dosWithoutDont = stripDonts(dos);
        System.out.println("Day 3 part 2: " + sumOfMultiplicationList(getMulNumbersFromMultiple(dosWithoutDont)));
    }

    public static String[] grabDoStrings(String input){
        return input.split("do\\(\\)");
    }

    public static String[] stripDonts(String[] inputs){
        String[] strippedInputs = new String[inputs.length];
        for(int i = 0; i < inputs.length; i++){
            strippedInputs[i] = stripDont(inputs[i]);
        }
        return strippedInputs;
    }

    public static String stripDont(String input){
        String[] parts = input.split("don't\\(\\)");
        if (parts.length == 0)
            return "";
        return parts[0];
    }


    public static int sumOfMultiplicationList(ArrayList<int[]> list){
        int sum = 0;
        for(int[] pair : list){
            sum += pair[0] * pair[1];
        }
        return sum;
    }

    public static boolean containsMul(String input){
        return input.contains("mul(");
    }

    public static boolean containsFullMul(String input){
        return input.matches(".*mul\\([0-9]\\d{0,2},[0-9]\\d{0,2}\\).*");
    }


    public static ArrayList<int[]> getMulNumbersFromMultiple(String[] inputs){
        ArrayList<int[]> fullList = new ArrayList<>();
        for(String input : inputs){
            fullList.addAll(getMulNumbers(input));
        }
        return fullList;
    }


    public static ArrayList<int[]> getMulNumbers(String input){
        if(!containsFullMul(input))
            return null;

        Pattern pattern = Pattern.compile("(mul\\(([0-9]\\d{0,2}),([0-9]\\d{0,2})\\))");
        Matcher matcher = pattern.matcher(input);

        ArrayList<int[]> matchList= new ArrayList<>();
        while(matcher.find()) {
            matchList.add(new int[]{Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))});
        }

        //System.out.println(matchList.get(0)[0] + "," + matchList.get(0)[1]);
        return matchList;

    }




    private static String readInput() {
        String input = "";
        File inputFile = new File("src/Day3/input.txt");
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                input += line;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
}