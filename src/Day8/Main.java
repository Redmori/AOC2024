package Day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[][] input = readInput();

        ArrayList<Antenna> antennas = locateAntennas(input);
        printAntennas(antennas);

        ArrayList<AntennaGroup> groups = sortAntennas(antennas);
        printGroups(groups);

        boolean[][] anitnodeMap = generateNodeMap(input);
        addAntinodesToMap(anitnodeMap, groups);
        int amount = amountOfNodesInMap(anitnodeMap);

        printAntinodeMap(anitnodeMap);
        System.out.println("Day 8 Part 1: " + amount);


        boolean[][] antinodeMapP2 = generateNodeMap(input);
        addAntinodesToMapP2(antinodeMapP2, groups);
        int amountP2 = amountOfNodesInMap(antinodeMapP2);


        printAntinodeMap(antinodeMapP2);
        System.out.println("Day 8 Part 2: " + amountP2);


    }

    private static void addAntinodesToMap(boolean[][] map, ArrayList<AntennaGroup> groups){
        for(AntennaGroup group : groups){
            group.addAntinodes(map);
        }
    }

    private static void addAntinodesToMapP2(boolean[][] map, ArrayList<AntennaGroup> groups){
        for(AntennaGroup group : groups){
            group.addAntinodesP2(map);
        }
    }

    private static int amountOfNodesInMap(boolean[][] map){
        int amount = 0;
        for(boolean[] row : map){
            for(boolean cell: row){
                amount += cell ? 1 : 0;
            }
        }
        return amount;
    }

    private static void printAntinodeMap(boolean[][] map){
        for(boolean[] row : map){
            for(boolean cell: row){
                if(cell)
                    System.out.print("#");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }

    private static boolean[][] generateNodeMap(char[][] input) {
        boolean[][] map = new boolean[input.length][input[0].length];
        for(boolean[] row : map){
            for(boolean cell : row){
                cell = false;
            }
        }
        return map;
    }

    private static ArrayList<AntennaGroup> sortAntennas(ArrayList<Antenna> antennas){
        ArrayList<AntennaGroup> groups = new ArrayList<>();
        for(Antenna antenna : antennas){
            AntennaGroup foundGroup = null;
            for(AntennaGroup group : groups) {
                if (antenna.type == group.type) {
                    foundGroup = group;
                    break;
                }
            }
            if(foundGroup != null){
                foundGroup.add(antenna);
            }
            else{
                AntennaGroup newGroup = new AntennaGroup(antenna.type);
                groups.add(newGroup);
                newGroup.add(antenna);
            }
        }


        return groups;
    }

    private static ArrayList<Antenna> locateAntennas(char[][] input){
        ArrayList<Antenna> antennas = new ArrayList<>();
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                if(input[i][j] != '.'){
                    antennas.add(new Antenna(input[i][j], j, i));
                }
            }
        }

        return antennas;
    }

    private static void printGroups(ArrayList<AntennaGroup> groups){
        System.out.println("Antenna Groups:");
        for(AntennaGroup group : groups){
            System.out.println(group);
        }
    }

    private static void printAntennas(ArrayList<Antenna> antennas){
        System.out.println("Individual Antenna:");
        for(Antenna antenna : antennas){
            System.out.println(antenna);
        }
    }

    private static char[][] readInput() {
        File inputFile = new File("src/Day8/input.txt");
        ArrayList<char[]> input = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                input.add(line.toCharArray());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input.toArray(new char[input.size()][]);
    }
}