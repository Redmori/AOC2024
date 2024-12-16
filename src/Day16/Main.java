package Day16;

import AOCutil.AOC;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {

    static boolean[][] map;
    static int[] target;
    static int[][] optimizedMap;

    public static void main(String[] args) {
        String[] input = AOC.input("src/Day16/sampleinput.txt");

        char[][] mapChar = AOC.convertStringToChar(input);
        map = AOC.convertCharToBoolean(mapChar, '#');

        optimizedMap = createOptimzedMap(map);

        AOC.printBoolMap(map);

        Reindeer reindeer = findReindeer(mapChar);

        target = findExit(mapChar);
        System.out.println("target = " + target[0] + "," + target[1]);

        ArrayList<Reindeer> reindeerList = new ArrayList<>();
        reindeerList.add(reindeer);
        reindeer.x -= 1;
        moveAllReindeer(reindeerList);

        int p1 = findLowestScore(reindeerList) ;
        System.out.println("Pay 16 Part 1: " + p1);



    }

    private static int[][] createOptimzedMap(boolean[][] map) {
        int[][] optimizedMap = new int[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                optimizedMap[i][j] = Integer.MAX_VALUE - 1000;
            }
        }
        
        return optimizedMap;
    }

    private static int findLowestScore(ArrayList<Reindeer> reindeerList) {
        int lowest = Integer.MAX_VALUE;
        for (Reindeer reindeer : reindeerList) {
            if(reindeer.finished && reindeer.score < lowest){
                lowest = reindeer.score;
            }
        }
        return lowest - 1;
    }

    private static void moveAllReindeer(ArrayList<Reindeer> reindeerList) {
        //System.out.println("move all");
        int i = 0;
        while (!areAllReindeerStopped(reindeerList) && i < 75) {
            System.out.println(i);
            //System.out.println("not stopped");
            //printCurrentMap(reindeerList);
            ArrayList<Reindeer> newReindeer = new ArrayList<>();
            for (Reindeer reindeer : reindeerList) {
                //System.out.println("loop move");
                if(!reindeer.stopped) {
                    //System.out.println(reindeer.x + "," + reindeer.y + " -> " + reindeer.direction);
                    newReindeer.addAll(moveReindeer(reindeer));
                }
            }
            reindeerList.addAll(newReindeer);
            newReindeer.clear();
            i++;
        }
    }

    private static boolean areAllReindeerStopped(ArrayList<Reindeer> reindeerList){
        //System.out.println("reindeerlist size: " + reindeerList.size());
        for(Reindeer reindeer : reindeerList){
            //System.out.println("stopped? " + reindeer.stopped);
            if(!reindeer.stopped ){
                return false;
            }
        }
        return true;
    }

    private static void printCurrentMap(ArrayList<Reindeer> reindeerList) {
        char[][] currentMap = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j]){
                    currentMap[i][j] = 'X';
                }
                else{
                    currentMap[i][j] = '.';
                }
            }
        }
        for(Reindeer reindeer : reindeerList){
            if(!reindeer.stopped){
                currentMap[reindeer.y][reindeer.x] = 'S';
            }
        }

        for(char[] row : currentMap){
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static ArrayList<Reindeer> moveReindeer(Reindeer reindeer){
        //System.out.println("moveReindeer");
        ArrayList<Reindeer> newReindeer = new ArrayList<>();


        if(isWall(reindeer.getNextLocation())){
            reindeer.stopped = true;
        }else{
            if(reindeer.move()){
                reindeer.stopped = true;
            }
            if(reindeer.score >= Main.optimizedMap[reindeer.y][reindeer.x] + 1000){
                //System.out.println("stopping");
                reindeer.stopped = true;
            }
            else{
                //System.out.println("changing score");
                Main.optimizedMap[reindeer.y][reindeer.x] = reindeer.score + 1000;
            }
        }

        if(target[0] == reindeer.x && target[1] == reindeer.y){
            reindeer.stopped = true;
            reindeer.finished = true;
        }

        if(!isWall(reindeer.getRightLocation())){
            //System.out.println("adding reindeer right");
            Reindeer newReindeerRight = new Reindeer(reindeer.x, reindeer.y);
            newReindeerRight.direction = reindeer.direction;
            newReindeerRight.visited = new ArrayList<>(reindeer.visited);
            newReindeerRight.score = reindeer.score;
            newReindeerRight.rotate(true);
            newReindeer.add(newReindeerRight);
            //System.out.println("create clone right");
        }
        if(!isWall(reindeer.getLeftLocation())){
            //System.out.println("adding reindeer left");
            Reindeer newReindeerLeft = new Reindeer(reindeer.x, reindeer.y);
            newReindeerLeft.direction = reindeer.direction;
            newReindeerLeft.visited = new ArrayList<>(reindeer.visited);
            newReindeerLeft.score = reindeer.score;
            newReindeerLeft.rotate(false);
            newReindeer.add(newReindeerLeft);
            //System.out.println("create clone left");
        }

        return newReindeer;
    }

    public static int[] directionToMove(int direction){
        int[] dPos;
        //System.out.println(direction);
        switch (direction) {
            case 1:
                dPos = new int[]{0, -1};
                break;
            case 2:
                dPos = new int[]{1, 0};
                break;
            case 3:
                dPos = new int[]{0, 1};
                break;
            default:
                dPos = new int[]{-1, 0};
                break;
        };
        return dPos;
    }

    private static Reindeer findReindeer(char[][] mapChar) {
        for (int i = 0; i < mapChar.length; i++) {
            for (int j = 0; j < mapChar[0].length; j++) {
                if(mapChar[i][j] == 'S'){
                    return new Reindeer(j,i);
                }
            }
        }
        return null;
    }

    private static int[] findExit(char[][] mapChar) {
        for (int i = 0; i < mapChar.length; i++) {
            for (int j = 0; j < mapChar[0].length; j++) {
                if(mapChar[i][j] == 'E'){
                    return new int[]{j,i};
                }
            }
        }
        return null;
    }

    public static boolean isWall(int x, int y){
        return map[y][x];
    }

    public static boolean isWall(int[] loc){
        return isWall(loc[0],loc[1]);
    }
}
