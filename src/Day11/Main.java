package Day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input = readInput();
        int[] values = seperateInput(input);
        ArrayList<Stone> stones = generateStones(values);
        //System.out.println(stones);
        blinkMultiple(stones, 25);

        System.out.println("Day 11 Part 1: " + stones.size());

        HashMap<Long, Long> stonesMap = generateMap(values);
        //System.out.println(stonesMap);
        stonesMap =  blinkMultiple(stonesMap, 75);
        //System.out.println(stonesMap);

        long sum = mapSum(stonesMap);
//
//        HashMap<Long, Integer> stonesMap2 = generateMapI(values);
//        System.out.println(stonesMap2);
//        stonesMap2 =  blinkMultipleI(stonesMap2, 75);
//        System.out.println(stonesMap2);
//
//        long sum2 = mapSumI(stonesMap2);
        System.out.println("Day 11 Part 2: " + sum);
        //System.out.println("Day 11 Part 2: " + sum2);

        //compareMaps(stonesMap, stonesMap2);


    }

    private static void compareMaps(HashMap<Long, Long> stonesMap, HashMap<Long, Integer> stonesMap2) {
        System.out.println("Comparing Maps:");
        for (Long key : stonesMap.keySet()) {
            if (!stonesMap2.containsKey(key)) {
                System.out.println("Key " + key + " is missing in stonesMap2.");
            } else {
                Long value1 = stonesMap.get(key);
                Integer value2 = stonesMap2.get(key);
                if (!value1.equals(value2.longValue())) {
                    System.out.println("Key " + key + " has different values: stonesMap=" + value1 + ", stonesMap2=" + value2);
                }
            }
        }

        for (Long key : stonesMap2.keySet()) {
            if (!stonesMap.containsKey(key)) {
                System.out.println("Key " + key + " is missing in stonesMap.");
            }
        }
    }

    private static long mapSum(HashMap<Long, Long> map) {
        long amount = 0L;
        for(long v : map.values()){
            amount += v;
        }
        return amount;
    }

    private static HashMap<Long, Long> blinkMultiple(HashMap<Long, Long> stones, int n){
        for(int i = 0; i < n; i++){
            stones = blink(stones);
            //System.out.println("Blink " + (i + 1));
            //System.out.println(stones);
        }
        return stones;
    }

    private static HashMap<Long, Long> blink(HashMap<Long, Long> stones){
        HashMap<Long, Long> changes = new HashMap<>();

        for(Long key : stones.keySet()){
            if(key == 0){
                //changes.put(0L, stones.getOrDefault(0,0) - 1);
                changes.put(1L, changes.getOrDefault(1L, 0L) + stones.get(0L));
            }
            else if(Long.toString(key).length() % 2L == 0L){
                String digits = Long.toString(key);
                String first = digits.substring(0,digits.length()/2);
                Long number1 = Long.parseLong(first);
                String second = digits.substring(digits.length()/2);
                Long number2 = Long.parseLong(second);
                //changes.put(key, changes.getOrDefault(key, 0 ) - 1);
                changes.put(number1,changes.getOrDefault(number1,0L) + stones.get(key));
                changes.put(number2,changes.getOrDefault(number2,0L) + stones.get(key));
            }
            else{
                //changes.put(key,changes.getOrDefault(key, 0 ) - 1);
                changes.put(key * 2024L, changes.getOrDefault(key * 2024L, 0L) + stones.get(key));
            }
        }

        return changes;

//        for(Long key : changes.keySet()){
//            int amount = changes.get(key);
//            if (amount != 0) {
//                stones.put(key, stones.getOrDefault(key, 0) + amount);
//            }
//        }
//        changes.clear();
    }

    private static long mapSumI(HashMap<Long, Integer> map) {
        long amount = 0;
        for(int v : map.values()){
            amount += v;
        }
        return amount;
    }

    private static HashMap<Long, Integer> blinkMultipleI(HashMap<Long, Integer> stones, int n){
        for(int i = 0; i < n; i++){
            stones = blinkI(stones);
            System.out.println("Blink " + (i + 1));
            System.out.println(stones);
        }
        return stones;
    }


    private static HashMap<Long, Integer> blinkI(HashMap<Long, Integer> stones){
        HashMap<Long, Integer> changes = new HashMap<>();

        for(Long key : stones.keySet()){
            if(key == 0){
                changes.put(1L, stones.getOrDefault(1, 0) + stones.get(key));
            }
            else if(("" + key).length() % 2 == 0){
                String digits = "" + key;
                String first = digits.substring(0,digits.length()/2);
                Long number1 = Long.parseLong(first);
                String second = digits.substring(digits.length()/2);
                Long number2 = Long.parseLong(second);
                changes.put(number1,changes.getOrDefault(number1,0) + stones.get(key));
                changes.put(number2,changes.getOrDefault(number2,0) + stones.get(key));
            }
            else{
                changes.put(key * 2024, changes.getOrDefault(key * 2024, 0) + stones.get(key));
            }
        }

        return changes;
    }

    private static HashMap<Long, Integer> generateMapI(int[] values) {
        HashMap<Long, Integer> stonesMap = new HashMap<>();
        for(int value : values) {
            stonesMap.put((long) value,1);
        }
        return stonesMap;
    }





    private static HashMap<Long, Long> generateMap(int[] values) {
        HashMap<Long, Long> stonesMap = new HashMap<>();
        for(int value : values) {
            stonesMap.put((long) value,1L);
        }
        return stonesMap;
    }

    private static void blinkMultiple(ArrayList<Stone> stones, int n){
        for(int i = 0; i < n; i++){
            blink(stones);
            //System.out.println("Blink " + i);
            //System.out.println(stones);
        }
    }

    private static void blink(ArrayList<Stone> stones){
        ListIterator<Stone> iterator = stones.listIterator();
        while(iterator.hasNext()){
            Stone stone = iterator.next();
            Stone newStone = stone.blink();
            if(newStone != null){
                iterator.add(newStone);
                //stones.add(stones.indexOf(stone) +  1, newStone);
            }
        }
    }

    private static ArrayList<Stone> generateStones(int[] values){
        ArrayList<Stone> stones = new ArrayList<>();
        for(int value : values){
            stones.add(new Stone(value));
        }
        return stones;
    }

    private static int[] seperateInput(String input){
        String[] splitInput = input.split(" ");
        int[] values = new int[splitInput.length];
        for(int i = 0; i < splitInput.length; i++){
            values[i] = Integer.parseInt(splitInput[i]);
        }
        return values;
    }

    private static String readInput() {
        String input = "";
        File inputFile = new File("src/Day11/input.txt");
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