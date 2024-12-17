package Day16;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main {

    static boolean[][] map;
    static int[] target;
    static int[][] optimizedMap;

    static int[][][] g;
    static int[][] h;

    static ArrayList<Location> openList = new ArrayList<>();
    static ArrayList<Location> closedList = new ArrayList<>();

    public static void main(String[] args) {
        String[] input = AOC.input("src/Day16/sampleinput.txt");

        char[][] mapChar = AOC.convertStringToChar(input);
        map = AOC.convertCharToBoolean(mapChar, '#');

        //optimizedMap = createOptimzedMap(map);

        AOC.printBoolMap(map);

        //Reindeer reindeer = findReindeer(mapChar);

        target = findExit(mapChar);
        System.out.println("target = " + target[0] + "," + target[1]);

//        ArrayList<Reindeer> reindeerList = new ArrayList<>();
//        reindeerList.add(reindeer);
//        reindeer.x -= 1;
//        moveAllReindeer(reindeerList);
//
//        int p1 = findLowestScore(reindeerList) ;
//        System.out.println("Pay 16 Part 1: " + p1);

        g = new int[map.length][map[0].length][4];
        maximizeMap(g);

        h = new int[map.length][map[0].length];

        int[] reindeerStart = findReindeerStart(mapChar);

        printDirectionMap(g , 0);


        updateG(reindeerStart, null, 0);

        printDirectionMap(g , 1);

        Location finishedLocation = processOpenList();

        printDirectionMap(g , 1);

        int score = 0;
        if(finishedLocation != null){
            score = finishedLocation.score;
        }
        System.out.println("Day 16 Part 1: " + score);




    }

    private static Location processOpenList(){
        int i = 0;
        while(i < 10000){
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
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                System.out.print(g[i][j][direction] + " ");
            }
            System.out.println();
        }
    }

    private static void rotateInPlace(int[] position, Location oldLocation){
        int currentScore = getG(position);
        updateG(new int[]{position[0], position[1], newDirection(position[2],true)}, oldLocation, 1000);
        updateG(new int[]{position[0], position[1], newDirection(position[2],false)}, oldLocation, 1000);
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
        System.out.println(location[0] + "," + location[1] + "," + location[2]);
        int score = (oldLocation == null ? 0 : oldLocation.score) + deltaScore;
        if(score < g[location[0]][location[1]][location[2]]) {
            g[location[0]][location[1]][location[2]] = score;
            Location newLocation = new Location(location,score);
            newLocation.previous = oldLocation;
            openList.add(newLocation);

            rotateInPlace(location, oldLocation);
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

    public static boolean isWall(int[] loc){
        return isWall(loc[0],loc[1]);
    }
}
