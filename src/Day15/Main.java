package Day15;

import AOCutil.AOC;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static int x;
    public static int y;

    public static void main(String[] args) {
        String[] input = AOC.input("src/Day15/input.txt");
        AOC.printInput(input);
        char[][] convertedInput = AOC.convertStringToChar(input);

        char[][] map = getMapFromInput(convertedInput);
        ArrayList<Character> moves = getMovesFromInput(convertedInput);
        findRobot(map);

        //moveMultiple(10000, map,moves);
        moveAll(map, moves);

        System.out.println("Final situation");
        AOC.printInput(map);

        int part1 = getSumOfGPS(map);
        System.out.println("Day 15 Part 1: " + part1);


        char[][] convertedInput2 = AOC.convertStringToChar(input);
        char[][] map2 = getMapFromInput(convertedInput2);
        char[][] wideMap = convertToWide(map2);
        //AOC.printInput(wideMap);
        ArrayList<Character> moves2 = getMovesFromInput(convertedInput2);
        findRobot(wideMap);

        moveAll2(wideMap,moves2);
        System.out.println("Final situation");
        AOC.printInput(wideMap);
        int part2 = getSumOfGPS(wideMap);
        System.out.println("Day 15 Part 2: " + part2);

    }

    private static char[][] convertToWide(char[][] map) {
        char[][] wideMap = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            wideMap[i] = new char[map[i].length * 2];
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'O') {
                    wideMap[i][2 * j] = '[';
                    wideMap[i][2 * j + 1] = ']';
                } else if (map[i][j] != '@') {
                    wideMap[i][2 * j] = map[i][j];
                    wideMap[i][2 * j + 1] = map[i][j];
                } else if (map[i][j] == '@') {
                    wideMap[i][2 * j] = '@';
                    wideMap[i][2 * j + 1] = '.';

                }
            }

        }
        return wideMap;
    }

    private static void moveAll(char[][] map, ArrayList<Character> moves) {
        while (attemptMove(map, moves)) ;
    }

    private static void moveAll2(char[][] map, ArrayList<Character> moves) {
        while (attemptMove2(map, moves)) ;
    }

    private static int getSumOfGPS(char[][] map) {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'O' || map[i][j] == '[') {
                    sum += getGPS(j, i);
                }
            }
        }
        return sum;
    }

    private static int getGPS(int x, int y) {
        return 100 * y + x;
    }

    private static void moveMultiple(int amount, char[][] map, ArrayList<Character> moves) {
        for (int i = 0; i < amount; i++) {
            //AOC.printInput(map);
            if (!attemptMove(map, moves)) {
                return;
            }
        }
    }

    private static void findRobot(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '@') {
                    x = j;
                    y = i;
                    return;
                }
            }
        }
    }

    private static boolean attemptMove(char[][] map, ArrayList<Character> moves) {
        int[] move = getMove(moves);
        //System.out.println("move direction : " + move[0] + "," + move[1]);

        if (isOpen(map, x + move[0], y + move[1])) {
            map[y][x] = '.';
            map[y + move[1]][x + move[0]] = '@';
            x += move[0];
            y += move[1];
        } else if (isMovable(map, x + move[0], y + move[1], move)) {
            map[y][x] = '.';
            map[y + move[1]][x + move[0]] = '@';
            x += move[0];
            y += move[1];
        }
        moves.removeFirst();
        return !moves.isEmpty();

    }

    private static boolean attemptMove2(char[][] map, ArrayList<Character> moves) {
        int[] move = getMove(moves);

        //AOC.printInput(map);
        //System.out.println("move direction : " + move[0] + "," + move[1]);

        if (isOpen(map, x + move[0], y + move[1])) {
            map[y][x] = '.';
            map[y + move[1]][x + move[0]] = '@';
            x += move[0];
            y += move[1];
        } else{
            ArrayList<int[]> movingObjects = isMovable2(map, x + move[0], y + move[1], move); //only [
            if(movingObjects != null) {
                //System.out.println("list size: " + movingObjects.size());
                for(int[] object : movingObjects){
                    if(map[object[1]][object[0]] == '['){
                        map[object[1]][object[0]] = '.';
                    }
                    if( map[object[1]][object[0] + 1] == ']') {
                        map[object[1]][object[0] + 1] = '.';
                    }
                    map[object[1] + move[1]][object[0] + move[0]] = '[';
                    map[object[1] + move[1]][object[0] + move[0] + 1] = ']';
                }

                map[y][x] = '.';
                map[y + move[1]][x + move[0]] = '@';
                x += move[0];
                y += move[1];
            }
        }

        moves.removeFirst();
        return !moves.isEmpty();

    }

    private static ArrayList<int[]> isMovable2(char[][] map, int xPos, int yPos, int[] move) {
        //System.out.println(xPos + "," + yPos);
        if (map[yPos][xPos] == '#')
            return null;
        int newX = xPos + move[0];
        int newY = yPos + move[1];
        if(map[yPos][xPos] == '[') {
            //System.out.println("[ found");
            //TODO horizontal movement to the right?
            if(move[0] == 1){
                //TODO check if we can move 2 steps?
                if(isOpen(map, xPos + 2 * move[0], yPos)){
                    ArrayList<int[]> freshList = new ArrayList<>();
                    freshList.add(new int[]{xPos,yPos});
                    return freshList;
                }
                ArrayList<int[]> movableToRight = isMovable2(map, xPos + 2, yPos, move);
                if(movableToRight == null){
                    return null;
                }
                else{
                    movableToRight.add(new int[]{xPos,yPos});
                    return movableToRight;
                }
            }
            ArrayList<int[]> movingList;
            if ((isOpen(map, newX, newY) && isOpen(map,newX + 1, newY)) || (isOpen(map, newX, newY) && move[0] == -1)) {
                //System.out.println("open, adding to list");
                movingList = new ArrayList<>();
                movingList.add(new int[]{xPos, yPos});
                return movingList;
            }
            else{
                //System.out.println("regular block check");
                ArrayList<int[]> movableLeft = isMovable2(map, newX, newY, move);
                ArrayList<int[]> movableRight;
                ArrayList<int[]> movables;
                if(move[0] == - 1){
                    movableRight = new ArrayList<>();
                }else {
                    movableRight = isMovable2(map, newX + 1, newY, move);
                }
                if((movableLeft == null && !isOpen(map, newX, newY)) || (movableRight == null && !isOpen(map,newX + 1, newY))){
                    //System.out.println("no path found");
                    return null;
                } else if (isOpen(map, newX, newY) && movableRight != null) {
                    movables = movableRight;
                } else if (isOpen(map,newX + 1, newY) && movableLeft != null){
                    movables = movableLeft;
                } else{
                    movables = movableLeft;
                    movables.addAll(movableRight);
                }


                //System.out.println("adding [ to list");
                movables.add(new int[]{xPos, yPos});
                return movables;
            }
        }else if(map[yPos][xPos] == ']') {
            //TODO horizontal movement to the left? return empty arraylist?
//            if(move[0] == -1){
//                return new ArrayList<>();
//            }else {
            //System.out.println("] found, looking at [");
                return isMovable2(map, xPos - 1, yPos, move);
//            }
        }
        //System.out.println("end reached");
        return null;
    }

    private static boolean isMovable(char[][] map, int xPos, int yPos, int[] move) {
        if (map[yPos][xPos] == '#')
            return false;
        int newX = xPos + move[0];
        int newY = yPos + move[1];
        if(map[yPos][xPos] == 'O') {
            if (isOpen(map, newX, newY)) {
                map[newY][newX] = 'O';
                return true;
            } else if (isMovable(map, newX, newY, move)) {
                map[newY][newX] = 'O';
                return true;
            }
        }else if(map[yPos][xPos] == '[') {
            if (isOpen(map, newX, newY) && isOpen(map,newX + 1, newY)) {
                map[newY][newX] = 'O';
                map[newY][newX + 1] = 'O';
                return true;
            } else if(isOpen(map, newX, newY) && isMovable(map, newX + 1 , newY, move)){
                map[newY][newX] = 'O';
                map[newY][newX + 1] = 'O';
                return true;
            } else if(isOpen(map,newX + 1, newY) && isMovable(map, newX , newY, move)){
                map[newY][newX] = 'O';
                map[newY][newX + 1] = 'O';
                return true;
            } else if(false){ //check if both are movable? but only move when both are.... Not possible i think, instead make an ArrayList with all objects that need to be moved, and then do that when you get the full true from movable in attemptMove

            }
        }else if(map[yPos][xPos] == ']') {
            return isMovable(map, xPos - 1, yPos , move);
        }

        return false;

    }

    private static int[] getMove(ArrayList<Character> moves) {
        int[] move = new int[2];
        switch (moves.get(0)) {
            case '<':
                move[0] = -1;
                break;
            case '>':
                move[0] = 1;
                break;
            case '^':
                move[1] = -1;
                break;
            case 'v':
                move[1] = 1;
                break;
        }
        return move;
    }

    private static boolean isOpen(char[][] map, int x, int y) {
        return map[y][x] == '.';
    }

    private static ArrayList<Character> getMovesFromInput(char[][] convertedInput) {
        int emptyLineIndex = getEmptyLineIndex(convertedInput);

        ArrayList<Character> moveList = new ArrayList<>();
        for (int i = emptyLineIndex + 1; i < convertedInput.length; i++) {
            for (char c : convertedInput[i]) {
                moveList.add(c);
            }
        }
        return moveList;

    }

    private static char[][] getMapFromInput(char[][] convertedInput) {
        int emptyLineIndex = getEmptyLineIndex(convertedInput);

        char[][] result = new char[emptyLineIndex][];
        for (int i = 0; i < emptyLineIndex; i++) {
            result[i] = convertedInput[i];
        }

        return result;
    }

    private static int getEmptyLineIndex(char[][] convertedInput) {
        int emptyLineIndex = -1;
        for (int i = 0; i < convertedInput.length; i++) {
            if (convertedInput[i].length == 0) {
                emptyLineIndex = i;
                break;
            }
        }
        return emptyLineIndex;
    }
}