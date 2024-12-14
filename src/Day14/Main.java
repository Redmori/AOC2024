package Day14;

import AOCutil.AOC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day14/input.txt");
        AOC.printInput(input);

        ArrayList<Robot> robots = generateRobots(input);
        int seconds = 100;
        int sizeX = 101;
        int sizeY = 103;
        moveRobots(robots, seconds, sizeX, sizeY);



        int safety = calculateSafety(robots, sizeX, sizeY);
        System.out.println("Day 14 Part 1: " + safety);

        char[][] map = generateMap(robots,sizeX,sizeY);
        //AOC.printInput(map);

        ArrayList<Robot> robotsP2 = generateRobots(input);

        ArrayList<char[][]> images = findPossibleTrees(robotsP2, sizeX, sizeY);
    }

    private static ArrayList<char[][]> findPossibleTrees(ArrayList<Robot> robotsP2, int sizeX, int sizeY) {
        ArrayList<char[][]> possibilities = new ArrayList<>();
        for(int s = 1; s < 58660; s++){
            moveRobots(robotsP2, 1, sizeX, sizeY);
            int safety = calculateSafety(robotsP2, sizeX, sizeY);
            if (safety < 126445970){
                char[][] map = generateMap(robotsP2, sizeX, sizeY);
                possibilities.add(map);
                AOC.printInput(map);
                System.out.println("Day 14 Part 2: " + s + " with safety " + safety);
                break;
            }
        }
        return possibilities;
    }

    private static char[][] generateMap(ArrayList<Robot> robots, int sizeX, int sizeY){
        char[][] mapC = new char[sizeY][sizeX];
        int[][] map = new int[sizeY][sizeX];

        //System.out.println("map");
        for(Robot robot : robots){
            //System.out.println(robot);
            map[robot.y][robot.x] += 1;
        }

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == 0){
                    mapC[i][j] = '.';
                }else{
                    mapC[i][j] = (char) map[i][j];
                }

            }
        }


        return mapC;
    }

    private static int calculateSafety(ArrayList<Robot> robots, int sizeX, int sizeY) {
        int[] quadrants = new int[4];
        for(Robot robot : robots){
            int quadrant;
            //System.out.println(sizeX/2);
            //System.out.println(sizeY/2);
            if(robot.x == sizeX/2 || robot.y == sizeY/2){
                continue;
            }
            if(robot.y <= sizeY/2){
                quadrant = 0;
            }
            else{
                quadrant = 2;
            }
            if(robot.x >= sizeX/2){
                quadrant += 1;
            }
            quadrants[quadrant] += 1;
        }
        //System.out.println(quadrants[0] + " * " + quadrants[1] + " * " + quadrants[2] + " * " + quadrants[3]);
        return quadrants[0]*quadrants[1]*quadrants[2]*quadrants[3];

    }

    private static void moveRobots(ArrayList<Robot> robots, int seconds, int sizeX, int sizeY) {
        for(Robot robot : robots){
            for(int i = 0; i < seconds; i++) {
                robot.move(1);
                robot.wrap(sizeX, sizeY);
            }
            //System.out.println(robot);
        }
    }

    private static ArrayList<Robot> generateRobots(String[] input) {
        ArrayList<Robot> robots = new ArrayList<>();
        for(String line : input){
            String[] split1 = line.split("=");
            String[] split2 = split1[1].split(",");
            String[] split3 = split1[2].split(",");
            String[] split4 = split2[1].split(" ");
            String[] split5 = split3[1].split(" ");

            Robot newRobot = new Robot(Integer.parseInt(split2[0]), Integer.parseInt(split4[0]), Integer.parseInt(split3[0]), Integer.parseInt(split5[0]));
            robots.add(newRobot);
            //System.out.println(newRobot);
        }
        return robots;
    }
}
