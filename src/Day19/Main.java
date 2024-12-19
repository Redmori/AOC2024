package Day19;

import AOCutil.AOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day19/input.txt");

        String[] splitters = parseSplitters(input[0]);

        List<String> patterns = parsePatterns(input);
        System.out.println(patterns);
        System.out.println(Arrays.toString(splitters));

        int p1 = countOptions(patterns, splitters);

        System.out.println("Day 19 Part 1: " + p1);


//        String testString = "test";
//        String[]  results = testString.split("test");
//        for(String str : results){
//            System.out.println("result: " + str);
//        }

        //List<List<String>> results = splitOptions("abc||der||fgh||ijk||lmn||opq||rst||uvw||xyz", "||");
//        List<String> splitters = new ArrayList<>();
//        splitters.add("||");
//        splitters.add("##");
//        if(combineOptions("||####||####||||##||", splitters)){ //abc||der##fgh||ijk||lmn||opq##rst||uvw||xyz
//            System.out.println("emptied");
//        }
    }




    private static boolean removeFront(String pattern, String[] splitters){
        //System.out.println(pattern);
        if(pattern.isEmpty()){
            //System.out.println("emptied");
            return true;
        }
        for(String splitter : splitters){
            if(pattern.indexOf(splitter) == 0){
                String remaining = pattern.substring(splitter.length());
                if( removeFront(remaining, splitters)) {
                    return true;
                }
            }
        }
        return false;
    }


    private static int countOptions(List<String> patterns, String[] splitters) {
        int sum = 0;
        for(String pattern : patterns) {
            //System.out.println("\nnew pattern");
            if (removeFront(pattern, splitters)){
                sum ++;
            }
        }
        return sum;
    }

    private static List<String> parsePatterns(String[] input) {
        List<String> patterns = new ArrayList<>();
        for (int i = 2; i < input.length; i++) {
            patterns.add(input[i]);
        }
        return patterns;
    }

    private static String[] parseSplitters(String s) {
        return s.split(", ");
    }


    public static boolean combineOptions(String option, String[] splitters){
        if(option.matches("\\.+")){
            return true;
        }

        for(String splitter : splitters) {
            List<String> results = removeOptions(option, splitter);
            for(String result : results){
                return combineOptions(result, splitters);
            }
        }
        return false;
    }


    public static List<String> removeOptions(String string, String splitter) {
        List<String> options = new ArrayList<>();

        int index = 0;
        index = string.indexOf(splitter, index);
        while(index != -1) {
            List<String> combo = new ArrayList<>();
            String string1 = string.substring(0, index);
            String string2 = string.substring(index + splitter.length());
            String newString = string1 + "." + string2;
            options.add(newString);
            index += splitter.length();
            index = string.indexOf(splitter, index);
            System.out.println(newString);
        }

        return options;

    }






    public static boolean splitGatherer(String string, List<String> splitters){

        for(String splitter : splitters){
            List<List<String>> options = splitOptions(string, splitter);
            for(List<String> option : options){
                //TODO check if all parts resolve to empty to return true?
                for(String part : option){
                    return splitGatherer(part, splitters);
                }
            }
        }
        return false;
    }


    public static List<List<String>> splitOptions(String string, String splitter) {
        List<List<String>> options = new ArrayList<>();

        int index = 0;
        index = string.indexOf(splitter, index);
        while(index != -1) {
            List<String> combo = new ArrayList<>();
            String string1 = string.substring(0, index);
            String string2 = string.substring(index + splitter.length());
            combo.add(string1);
            combo.add(string2);
            index += splitter.length();
            index = string.indexOf(splitter, index);
            System.out.println(string1 + "<>" + string2);
            options.add(combo);
        }

        return options;

    }
}
