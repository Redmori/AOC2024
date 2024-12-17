package Day16;

import AOCutil.AOC;

import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

    static boolean[][] map;
    static int[] target;

    static int[][][] g;
    static int[][] h;

    static TreeSet<Location> openList = new TreeSet<>();


    static Location[][][] locationMap;

    public static void main(String[] args) {
        String[] input = AOC.input("src/Day16/input.txt");

        char[][] mapChar = AOC.convertStringToChar(input);
        map = AOC.convertCharToBoolean(mapChar, '#');


        AOC.printBoolMap(map);


        target = findExit(mapChar);
        System.out.println("target = " + target[0] + "," + target[1]);



        g = new int[map.length][map[0].length][4];
        maximizeMap(g);

        h = new int[map.length][map[0].length];

        locationMap = new Location[map.length][map[0].length][4];

        int[] reindeerStart = findReindeerStart(mapChar);

        printDirectionMap(g , 0);


        updateG(reindeerStart, null, 0);

        printDirectionMap(g , 1);

        Location finishedLocation = processOpenList();

        printOptimalMap(g);

        int score = 0;
        if(finishedLocation != null){
            score = finishedLocation.score + 1;
        }
        System.out.println("Day 16 Part 1: " + score);


        int sum = calculatePaths(finishedLocation) + 1;

        System.out.println("Day 16 Part 2: " + sum);



    }

    private static int calculatePaths(Location finishLocation) {
        boolean[][][] visited = new boolean[g.length][g[0].length][g[0][0].length];

        updateVisited(visited,finishLocation);

        int sum = AOC.countBoolean(visited);


        AOC.printBoolMap(visited);

        return sum;
    }

    private static void updateVisited(boolean[][][] visited, Location location){
        visited[location.y][location.x][location.direction] = true;
        if(location.previous != null) {
            updateVisited(visited, location.previous);
        }
        if(location.alternative != null){
            updateVisited(visited, location.alternative);
        }
    }

    private static Location processOpenList(){
        int i = 0;
        while(openList.size() > 0){
            //System.out.println("openlist size: " + openList.size());
            if(move(openList.getFirst())){
                System.out.println("TARGET FOUND");
                return openList.getFirst();
            }
            openList.remove(openList.getFirst());
            i++;
        }
        return null;
    }

    private static boolean move(Location location){
        int[] nextLocation = location.getNextPosition();
        if(!isWall(nextLocation[0],nextLocation[1])){
            if(isTarget(nextLocation[0], nextLocation[1])){
                return true;
            }
            updateG(nextLocation, location , 1);
        }
        return false;
    }

    private static boolean isTarget(int y, int x) {
        return (y == target[0] && x == target[1]);
    }

    private static void printDirectionMap(int[][][] g, int direction) {
        int maxWidth = 6; // Fixed width for numbers (adjust as needed)
        String format = "%-" + maxWidth + "s"; // Left-aligned fixed width

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                int value = g[i][j][direction];
                if(value == Integer.MAX_VALUE) {
                    System.out.printf(format, "X");
                }else{
                    System.out.printf(format, value);
                }
            }
            System.out.println();
        }
    }

    private static void printOptimalMap(int[][][] g) {
        int maxWidth = 6; // Fixed width for numbers (adjust as needed)
        String format = "%-" + maxWidth + "s"; // Left-aligned fixed width

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                int value = Integer.MAX_VALUE;
                for (int k = 0; k < g[0][0].length; k++) {
                    if (g[i][j][k] < value){
                        value = g[i][j][k];
                    }
                }
                if(value == Integer.MAX_VALUE) {
                    System.out.printf(format, "X");
                }else{
                    System.out.printf(format, value);
                }
            }
            System.out.println();
        }
    }

    private static void rotateInPlace(int[] position, Location oldLocation, int score){
        int currentScore = getG(position);
        updateG(new int[]{position[0], position[1], newDirection(position[2],true)}, oldLocation, score - (oldLocation == null ? 0 : oldLocation.score)  + 1000);
        updateG(new int[]{position[0], position[1], newDirection(position[2],false)}, oldLocation, score - (oldLocation == null ? 0 : oldLocation.score)  + 1000);
    }

    public static int getG(int[] position){
        return g[position[0]][position[1]][position[2]];
    }

    public static int newDirection(int direction, boolean clockwise){
        direction += clockwise ? 1 : -1;
        if(direction < 0){
            direction += 4;
        }
        if(direction > 3){
            direction -= 4;
        }
        return direction;
    }

    private static void maximizeMap(int[][][] g) {
        for (int i = 0; i < g.length; i++) {            // Loop through 3D array
            for (int j = 0; j < g[i].length; j++) {     // Loop through 2D array
                for (int k = 0; k < g[i][j].length; k++) { // Loop through 1D array
                    g[i][j][k] = Integer.MAX_VALUE;     // Modify the actual array element
                }
            }
        }
    }

    private static void updateG(int[] location, Location oldLocation, int deltaScore) {
        //System.out.println(location[0] + "," + location[1] + "," + location[2]);
        int score = (oldLocation == null ? 0 : oldLocation.score) + deltaScore;
        if(score < g[location[0]][location[1]][location[2]]) {
            g[location[0]][location[1]][location[2]] = score;
            Location newLocation = new Location(location, score);
            newLocation.previous = oldLocation;
            openList.add(newLocation);
            locationMap[location[0]][location[1]][location[2]] = newLocation;

            rotateInPlace(location, oldLocation, score);

        } else if (score == g[location[0]][location[1]][location[2]]) {
            Location newLocation = new Location(location, score);
            newLocation.previous = oldLocation;
            locationMap[location[0]][location[1]][location[2]].alternative = newLocation;
        }
    }

    private static int[] findReindeerStart(char[][] mapChar) {
        for (int i = 0; i < mapChar.length; i++) {
            for (int j = 0; j < mapChar[0].length; j++) {
                if(mapChar[i][j] == 'S'){
                    return new int[]{i,j,1};
                }
            }
        }
        return null;
    }

    private static int[] findExit(char[][] mapChar) {
        for (int i = 0; i < mapChar.length; i++) {
            for (int j = 0; j < mapChar[0].length; j++) {
                if(mapChar[i][j] == 'E'){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public static boolean isWall(int y, int x){
        return map[y][x];
    }
}
