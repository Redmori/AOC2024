package Day25;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day25/input.txt");
        ArrayList<String[]> schematicLines = findSchematics(input);

        ArrayList<char[][]> schematics = convertToChar(schematicLines);

        printSchematics(schematics);

        ArrayList<int[]> locks = getShape(schematics, '#');
        ArrayList<int[]> keys = getShape(schematics, '.');



        printShapes(locks);
        System.out.println();
        printShapes(keys);

        int p1 = numberOfUniqueSets(locks,keys);

        System.out.println("Day 25 Part 1: " + p1);



    }

    private static int numberOfUniqueSets(ArrayList<int[]> locks, ArrayList<int[]> keys) {
        int amount = 0;
        for(int[] lock : locks){
            for(int[] key : keys){
                if(fits(lock,key)){
                    amount ++;
                }
            }
        }
        return amount;
    }

    private static boolean fits(int[] lock, int[] key){
        for (int i = 0; i < lock.length; i++) {
            if(key[i] < lock[i]){
                return false;
            }
        }
        return true;
    }

    private static void printShapes(ArrayList<int[]> locks) {
        for(int[] lock : locks){
            for(int i = 0; i < lock.length; i++){
                System.out.print(lock[i] + " ");
            }
            System.out.println();
        }
    }

    private static ArrayList<int[]> getShape(ArrayList<char[][]> schematics, char token) {
        ArrayList<int[]> locks = new ArrayList<>();

        for(char[][] schematic : schematics){
            if(schematic[0][0] != token){
                continue;
            }
            int[] shape = new int[5];
            for(int x = 0; x < 5; x++){
                int y = 0;
                while(schematic[y][x] == token){
                    y ++;
                }
                shape[x] = y;
            }
            locks.add(shape);
        }
        return locks;
    }

    private static ArrayList<char[][]> convertToChar(ArrayList<String[]> schematicLines) {
        ArrayList<char[][]> schematics = new ArrayList<>();
        for(String[] schematic : schematicLines){
            schematics.add(AOC.convertStringToChar(schematic));
        }
        return schematics;
    }


    private static void printSchematics(ArrayList<char[][]> schematics){
        for(char[][] schematic : schematics){
            AOC.printInput(schematic);
            System.out.println();
        }
    }

    private static ArrayList<String[]> findSchematics(String[] input) {
        ArrayList<String[]> schematics = new ArrayList<>();

        int j = 0;
        while(j < input.length - 1) {
            String[] newSchematic = new String[7];
            for (int i = 0; i < 7; i++) {
                newSchematic[i] = input[i + j];
            }
            schematics.add(newSchematic);
            j += 8;
        }

        return schematics;
    }


}
