package Day22;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day22/input.txt");
        int[] secrets = AOC.convertStringToInt(input);

        long p1 = sumSteps(secrets, 2000);

        System.out.println("Day 22 Part 1 " + p1);

        //String diffs = getDifferences(2024, 2000);
        //System.out.println(diffs);

        //int index = findChanges(diffs, "-2+1-1+3");
        //System.out.println(index);
        //int price = getPrice(prices, index);
        //System.out.println(price);


        int p2 = getMostBananas(secrets, 2000);

        System.out.println("Day 22 Part 2 " + p2);
    }

    public static int getMostBananas(int[] secrets, int evolutions){
        String[] differences = new String[secrets.length];
        ArrayList[] prices = new ArrayList[secrets.length];
        for (int i = 0; i < secrets.length; i++) {
            differences[i] = getDifferences(secrets[i], evolutions);
            prices[i] = (ArrayList) newPrices.clone();
        }

        int mostBananas = 0;


        for (int i = -9; i < 9; i++) {
            for (int j = -9; j < 9; j++) {
                for (int k = -9; k < 9; k++) {
                    for (int l = -9; l < 9; l++) {
                        String seq = (i >= 0 ? "+" + i : String.valueOf(i)) +
                                (j >= 0 ? "+" + j : String.valueOf(j)) +
                                (k >= 0 ? "+" + k : String.valueOf(k)) +
                                (l >= 0 ? "+" + l : String.valueOf(l));
                        mostBananas = Math.max(mostBananas, determineBananas(differences, prices, seq));
                    }
                }
            }
        }



        return mostBananas;

    }

    private static int determineBananas(String[] differences, ArrayList[] prices, String searchSequence) {
        int sum = 0;
        for (int i = 0; i < differences.length; i++) {
            int index = findChanges(differences[i], searchSequence);
            int price = getPrice(prices[i], index);
            sum += price;
        }
        return sum;
    }

    private static long sumSteps(int[] secrets, int evolutions) {
        long sum = 0;
        for (int secret : secrets){
            sum += manySteps(secret, evolutions);
        }
        return sum;
    }

    public static ArrayList<Integer> newPrices = new ArrayList<>();

    public static int getPrice(ArrayList<Integer> prices, int index){
        if(index == -1){
            return 0;
        }else {
            return prices.get(index + 3);
        }
    }

    public static int findChanges(String differences, String changes){
        int index = differences.indexOf(changes);
        if(index == -1){
            return -1;
        }else {
            return index/2;
        }
    }

    public static String getDifferences(long number, int evolutions){
        newPrices.clear();
        StringBuilder diffs = new StringBuilder();
        //System.out.println();
        for (int i = 0; i < evolutions; i++) {
            long newNumber = steps(number);
            int digitNew = (int)newNumber%10;
            newPrices.add(digitNew);
            int digitOld = (int)number%10;
            int digit = digitNew - digitOld;
            String digitStr = String.valueOf(digit);
            if(digit >= 0){
                digitStr = "+" + digitStr;
            }
            //System.out.print(number + " diff: " + digit + " ");
            diffs.append(digitStr);
            number = newNumber;
        }
        //System.out.println();
        return diffs.toString();
    }

    public static long manySteps(long number, int evolutions){
        for (int i = 0; i < evolutions; i++) {
            number = steps(number);
        }
        return number;
    }

    public static long steps(long number){
        return step3(step2(step1(number)));
    }

    public static long step1(long number){
        long step = number * 64L;
        long mix = number^step;
        long prune = mix%16777216L;
        return prune;
    }

    public static long step2(long number){
        long step = number/32;
        long mix = number^step;
        long prune = mix%16777216L;
        return prune;
    }

    public static long step3(long number){
        long step = number*2048L;
        long mix = number^step;
        long prune = mix%16777216L;
        return prune;
    }
}
