package Day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Position startPosition;
    public static Map startMap;

    public static void main(String[] args) {
        char[][] input = readInput();
        tile[][] tileMap = convertMap(input);
        startMap = new Map(tileMap);
        startPosition = getGuardPosition(startMap);


        startMap.print();
        int nRepeating = findPath(Map.clone(startMap), 1);

        System.out.print("Day 6 Part 2: " + nRepeating);


    }

    private static int findPath(Map map, int numberOfPlacedObjects) {
        int repeaters = 0;
        int direction = 1;
        Position guardPosition = startPosition.clone();
        Position nextPosition = getNextPosition(guardPosition, direction);
        while(!guardOffEdge(map, nextPosition)){

            if(map.getTile(nextPosition) != tile.OBJECT){
                if(placePath(map,guardPosition, direction)){
                    //System.out.println("LOOPING FOUND");
                    //map.printWithoutPath();
                    return 1;
                }
                guardPosition = nextPosition;

                if(numberOfPlacedObjects > 0 && map.getTile(guardPosition) == tile.EMPTY) {
                    repeaters += findPath(Map.cloneWithObject(startMap, guardPosition), numberOfPlacedObjects - 1);
                }
            }
            else{
                direction = changeDirection(direction);
            }
            nextPosition = getNextPosition(guardPosition, direction);
        }
        placePath(map,guardPosition,direction);

        if (numberOfPlacedObjects == 1) {
            map.print();
            System.out.println("Day 6 Part 1: " + countVisited(map));
        }

        return repeaters;
    }

    private static boolean placePath(Map map, Position guardPosition, int direction) {
        tile newTile = switch (direction) {
            case 1 -> tile.VISITEDUP;
            case 2 -> tile.VISITEDRIGHT;
            case 3 -> tile.VISITEDDOWN;
            default -> tile.VISITEDLEFT;
        };

        if(map.getTile(guardPosition) == newTile){
            return true;
        }
        else {
            map.setTile(guardPosition, newTile);
            return false;
        }

    }

    private static boolean guardOffEdge(Map map, Position guardPosition) {
        return guardPosition.y < 0 || guardPosition.y > map.sizeY - 1 || guardPosition.x > map.sizeX - 1 || guardPosition.x < 0;
    }

    private static int countVisited(Map map){
        int visited = 0;
        for(tile[] row : map.tiles){
            for(tile t : row){
                if(hasVisited(t)){
                    visited++;
                }
            }
        }
        return visited;
    }

    private static boolean hasVisited(tile t){
        return t == tile.VISITEDLEFT || t == tile.VISITEDDOWN || t == tile.VISITEDRIGHT || t == tile.VISITEDUP;
    }

    private static int changeDirection(int dir){
        dir++;
        if(dir > 4){
            dir = 1;
        }
        return dir;
    }

    private static Position getNextPosition(Position currentPosition, int direction){
        Position nextPosition = new Position(currentPosition.x, currentPosition.y);
        switch(direction){
            case 1:
                nextPosition.y--;
                break;
            case 2:
                nextPosition.x++;
                break;
            case 3:
                nextPosition.y++;
                break;
            case 4:
                nextPosition.x--;
                break;
        }
        return nextPosition;
    }

    private static Position getGuardPosition(Map map) {
        for(int i = 0; i < map.sizeY; i++){
            for(int j = 0; j < map.sizeX; j++){
                if(map.getTile(new Position(i,j)) == tile.GUARD){
                    return Position.getPosition(i,j);
                }
            }
        }
        return null;
    }

    public enum tile{
        EMPTY,
        GUARD,
        OBJECT,
        VISITEDLEFT,
        VISITEDRIGHT,
        VISITEDUP,
        VISITEDDOWN
    }


    private static char[][] readInput() {
        File inputFile = new File("src/Day6/input.txt");
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

    private static tile[][] convertMap(char[][] map){
        tile[][] convertedMap = new tile[map.length][map[0].length];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                convertedMap[i][j] = convertChar(map[i][j]);
            }
        }
        return convertedMap;
    }

    private static tile convertChar(char c){
        return switch (c) {
            case '.' -> tile.EMPTY;
            case '#' -> tile.OBJECT;
            case '^' -> tile.GUARD;
            default -> tile.EMPTY;
        };
    }

    static char convertEnum(tile c){
        return switch (c) {
            case tile.EMPTY -> '.';
            case tile.GUARD -> '^';
            case tile.OBJECT -> '#';
            case tile.VISITEDDOWN -> 'v';
            case tile.VISITEDLEFT -> '<';
            case tile.VISITEDRIGHT -> '>';
            case tile.VISITEDUP -> '^';
            default -> 'X';
        };
    }


    static char convertEnumWithoutPath(tile c){
        return switch (c) {
            case tile.OBJECT -> '#';
            default -> '.';
        };
    }

    private static void printInput(char[][] map){
        for(char[] row : map){
            for(char cell : row){
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}