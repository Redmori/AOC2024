package Day20;

import AOCutil.AOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day20/sampleinput.txt");

        char[][] charMap = AOC.convertStringToChar(input);
        boolean[][] walls = AOC.convertCharToBoolean(charMap, '#');

        AOC.printBoolMap(walls);

        int[] start = AOC.coordsOfChar(charMap, 'S');
        int[] end = AOC.coordsOfChar(charMap, 'E');

        System.out.println("Pathing from " + start[0] + "," + start[1] + " to " + end[0] + "," + end[1]);

        int[][] pathMap = generatePath(walls, start, end);

        AOC.printIntMapFormatted(pathMap);

        System.out.println("Base path length: " + pathMap[end[1]][end[0]]);

        int p1 = countShortcuts(pathMap, walls, 100);

        System.out.println("Day 20 Part 1 " + p1 );

        List<int[]> path = getPath(walls, start, end);

        long p2 = loopPath(path, walls, pathMap);

        System.out.println("Day 20 Part 2: " + p2);
    }

    private static long loopPath(List<int[]> path, boolean[][] walls, int[][] pathMap){
        long sum = 0;
        int i = 0;
        System.out.println("path length = " + path.size());
        for(int[] tile : path){
            sum += getPaths(pathMap, walls, tile);
            //System.out.println(i + "/" + path.size());
            i++;
        }

        return sum;
    }

    private static int getPaths(int[][] pathMap, boolean[][] walls, int[] position){
        int x = position[0];
        int y = position[1];
        int radius = 20;
        int sum = 0;
        int maxScore = pathMap[y][x] - 50;
        //System.out.println("maxScore = " + maxScore);;
        for(int i = -radius; i < radius; i++){
            for (int j = -radius + Math.abs(i); j < radius - Math.abs(i); j++) {
                if(isValid(walls, y + i, x + j) && !walls[y + i][x + j]){
                    if(pathMap[y + i][x + j] <= (maxScore - Math.abs(i) - Math.abs(j))){
                        sum++;
                        //System.out.println("Cheating from " + y + "," + x + " to " + (y + i) + "," + (x + j));
                    }
                }
            }
        }
        return sum;
    }

    private static int countShortcuts(int[][] pathMap, boolean[][] walls, int minSkip) {
        int sum = 0;

        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[0].length; j++) {
                if(walls[i][j]){
                    int shortcutLength = calculateShortcut(pathMap, walls, i, j);
                    if( shortcutLength >= minSkip) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    private static boolean isValid(boolean[][] map, int i, int j){
        if(i < 0 || j < 0 || i >= map.length || j >= map[0].length){
            return false;
        }else {
            return true;
        }
    }

    private static int calculateShortcut(int[][] pathMap, boolean[][] walls, int i, int j) {
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        int shortcutLength = 0;

        for(int[] dir : directions){
            if(!isValid(walls,i + dir[0],j + dir[1]) || walls[i + dir[0]][j + dir[1]]){
                continue;
            }
            for(int[] dir2 : directions){
                if(!isValid(walls,i + dir2[0],j + dir2[1]) || walls[i + dir2[0]][j + dir2[1]]){
                    continue;
                }
                if(Arrays.equals(dir, dir2)){
                    continue;
                }
                int newShortcut = Math.abs(pathMap[i + dir[0]][j + dir[1]] - pathMap[i + dir2[0]][j + dir2[1]] );
                shortcutLength = Math.max(shortcutLength, newShortcut);
            }
        }

        return shortcutLength - 2;
    }

    private static List<int[]> getPath(boolean[][] walls, int[] start, int[] end) {
        List<int[]> path = new ArrayList<>();

        int[] current = start.clone();
        int[] previous = new int[2];

        path.add(start);

        while (!(current[0] == end[0] && current[1] == end[1])) {
            int[] next = null;

            int[][] directions = {
                    {1, 0},
                    {-1, 0},
                    {0, 1},
                    {0, -1}
            };

            for (int[] dir : directions) {
                int[] neighbor = new int[]{current[0] + dir[0], current[1] + dir[1]};
                if (!walls[neighbor[1]][neighbor[0]] && !Arrays.equals(previous, neighbor)) {
                    next = neighbor;
                }
            }

            previous = current;
            current = next;
            path.add(current);
            //System.out.println(Arrays.toString(current));
        }
        return path;
    }

    private static int[][] generatePath(boolean[][] walls, int[] start, int[] end) {
        int[][] pathMap = new int[walls.length][walls[0].length];

        for (int i = 0; i < pathMap.length; i++) {
            for (int j = 0; j < pathMap[i].length; j++) {
                pathMap[i][j] = Integer.MAX_VALUE;
            }
        }

        int[] current = start.clone();
        int[] previous = new int[2];
        int score = 0;

        pathMap[start[1]][start[0]] = score;

        while(!(current[0] == end[0] && current[1] == end[1])){
            int[] next = null;

            int[][] directions = {
                    {1, 0},
                    {-1, 0},
                    {0, 1},
                    {0, -1}
            };

            for (int[] dir : directions) {
                int[] neighbor = new int[]{current[0] + dir[0], current[1] + dir[1]};

                if (score + 1 < pathMap[neighbor[1]][neighbor[0]]) {
                    pathMap[neighbor[1]][neighbor[0]] = score + 1;
                    if (!walls[neighbor[1]][neighbor[0]] && !Arrays.equals(previous, neighbor)) {
                        next = neighbor;
                    }
                }
            }

            score++;
            previous = current;
            current = next;
        }

        return pathMap;
    }
}
