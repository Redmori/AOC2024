package Day19;

import AOCutil.AOC;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day19/input.txt");

        String[] splitters = parseSplitters(input[0]);

        List<String> patterns = parsePatterns(input);
        System.out.println(patterns);
        System.out.println(Arrays.toString(splitters));

        int p1 = countOptions(patterns, splitters);

        System.out.println("Day 19 Part 1: " + p1);


        long p2 = countOptionsP2(patterns, splitters);

        System.out.println("Day 19 Part 2: " + p2);
    }


    private static boolean removeFront(String pattern, String[] splitters){
        if(pattern.isEmpty()){
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
            if (removeFront(pattern, splitters)){
                sum ++;
            }
        }
        return sum;
    }

    private static long removeFrontP2(String pattern, String[] splitters, Map<String, Long> memo) {
        if (memo.containsKey(pattern)) {
            return memo.get(pattern);
        }

        if (pattern.isEmpty()) {
            return 1;
        }

        long sum = 0;

        for (String splitter : splitters) {
            if (pattern.startsWith(splitter)) {
                String remaining = pattern.substring(splitter.length());
                sum += removeFrontP2(remaining, splitters, memo);
            }
        }

        memo.put(pattern, sum);

        return sum;
    }

    private static long countOptionsP2(List<String> patterns, String[] splitters) {
        long sum = 0;

        for (String pattern : patterns) {
            Map<String, Long> memo = new HashMap<>();
            sum += removeFrontP2(pattern, splitters, memo);
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
}
