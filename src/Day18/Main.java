package Day18;

import AOCutil.AOC;

public class Main {

    public static void main(String[] args) {
        String[] input = AOC.input("src/Day18/input.txt");

        Map map = generateMap(71,71, input, 1024);

        map.printLayout();

        Pathfinder pf = new Pathfinder(map);

        int length = pf.findPath();

        System.out.println("Day 18 Part 1: " + length);

        int limit = findLimit(input);

        System.out.println("Day 18 Part 2: " + input[limit]);
    }

    private static int findLimit(String[] input) {
        int length = 0;
        int i = 1024;
        while(length != Integer.MAX_VALUE){
            Map map = generateMap(71,71, input, i);
            Pathfinder pf = new Pathfinder(map);
            length = pf.findPath();
            i ++;
            //System.out.println("amount = " + i + " path length = " + length);
        }

        return i - 2;
    }

    private static Map generateMap(int width, int height, String[] input, int amount) {
        Map map = new Map(width,height);
        int i = 0;
        for(String line : input){
            String[] coords = line.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            map.block(x,y);
            i++;
            if(i >= amount){
                break;
            }
        }
        return map;
    }
}
