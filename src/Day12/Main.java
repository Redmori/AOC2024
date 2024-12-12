package Day12;

import AOCutil.AOC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static int mapSizeX;
    public static int mapSizeY;

    public static void main(String[] args) {
        String[] input = AOC.input("src/Day12/input.txt");
        char[][] map = AOC.convertStringToChar(input);
        AOC.printInput(map);
        Plot[][] plots = createPlots(map);
        ArrayList<ArrayList<Plot>> groups = assignGroups(plots);
        int p1 = calculateGroups(groups);
        System.out.println("Day 12 Part 1: " + p1);
        //int p2 = countEdgesOfGroups(groups, plots);
        assignBorders(groups , plots);
        //plotNumberOfBorders(plots);

        mapSizeX = plots[0].length;
        mapSizeY = plots.length;

        int p2 = countLongEdges(groups);
        System.out.println("Day 12 Part 2: " + p2);

    }

    private static int countLongEdges(ArrayList<ArrayList<Plot>> groups) {
        int sum = 0;
        for(ArrayList<Plot> group : groups){
            sum += countLongEdgesOfGroup(group) * group.size();
        }
        return sum;
    }

    private static int countLongEdgesOfGroup(ArrayList<Plot> group) {
        int sum = 0;
        ArrayList<Plot> up = new ArrayList<>();
        ArrayList<Plot> right = new ArrayList<>();
        ArrayList<Plot> down = new ArrayList<>();
        ArrayList<Plot> left = new ArrayList<>();

        for(Plot plot : group){
            if (plot.borders[Plot.border.NORTH.ordinal()])
                up.add(plot);
            if (plot.borders[Plot.border.EAST.ordinal()])
                right.add(plot);
            if (plot.borders[Plot.border.SOUTH.ordinal()])
                down.add(plot);
            if (plot.borders[Plot.border.WEST.ordinal()])
                left.add(plot);
        }

        sum += groupHorizontal(up);
        sum += groupHorizontal(down);

        sum+= groupVertical(right);
        sum+= groupVertical(left);

        return sum;
    }

    private static int groupHorizontal(ArrayList<Plot> plots) {
        int sum = 0;
        int[][] bin = new int[mapSizeY][mapSizeX];
        for(Plot plot : plots){
            bin[plot.y][plot.x] = 1;
        }
        //System.out.println("bin map: " + plots.get(0).type);
        //AOC.printIntMap(bin);

        boolean found = false;
        for(int[] row : bin){
            for(int cell : row){
                if(cell == 1 && !found){
                    found = true;
                    sum ++;
                }
                else if(cell == 0){
                    found = false;
                }
            }
            found = false;
        }

        return sum;
    }

    private static int groupVertical(ArrayList<Plot> plots) {
        int sum = 0;
        int[][] bin = new int[mapSizeY][mapSizeX];

        for (Plot plot : plots) {
            bin[plot.y][plot.x] = 1;
        }

        for (int col = 0; col < mapSizeX; col++) {
            boolean found = false;
            for (int row = 0; row < mapSizeY; row++) {
                if (bin[row][col] == 1 && !found) {
                    found = true;
                    sum++;
                }
                if (bin[row][col] == 0) {
                    found = false;
                }
            }
        }

        return sum;
    }


    private static void assignBorders(ArrayList<ArrayList<Plot>> groups, Plot[][] map) {
        for(ArrayList<Plot> group : groups){
            for(Plot plot : group){
                if(samePlotType(map, plot, 0,1)){
                    plot.borders[Plot.border.SOUTH.ordinal()] = false;
                }
                if(samePlotType(map, plot, 0,-1)){
                    plot.borders[Plot.border.NORTH.ordinal()] = false;
                }
                if(samePlotType(map, plot, 1,0)){
                    plot.borders[Plot.border.EAST.ordinal()] = false;
                }
                if(samePlotType(map, plot, -1,0)){
                    plot.borders[Plot.border.WEST.ordinal()] = false;
                }
            }
        }

    }

    private static boolean samePlotType(Plot[][] map, Plot plot, int dx, int dy) {
        if((dy < 0 && plot.y == 0) || (dy > 0 && plot.y == map.length - 1) ||(dx < 0 && plot.x == 0) || (dx > 0 && plot.x == map[0].length - 1))
            return false;
        return map[plot.y + dy][plot.x + dx].type == plot.type;
    }

    public static ArrayList<ArrayList<Plot>> assignGroups(Plot[][] map){
        ArrayList<ArrayList<Plot>> groups = new ArrayList<>();
        for(Plot[] row : map){
            for(Plot plot : row){
                if(plot.group == null){
                    plot.group = new ArrayList<>();
                    groups.add(plot.group);
                    plot.group.add(plot);
                    assignGroup(plot,map);
                }
            }
        }
        return groups;
    }

    public static int calculateGroups(ArrayList<ArrayList<Plot>> groups){
        int sum = 0;
        for(ArrayList<Plot> group : groups){
            sum += calculateGroup(group);
        }
        return sum;
    }

    public static int calculateGroup(ArrayList<Plot> group){
        int size = group.size();

        int sumBorders = 0;
        for(Plot plot1 : group){
            int borders = 4;
            for(Plot plot2 : group){
                if((Math.abs(plot1.x - plot2.x) == 1 && plot1.y == plot2.y) || ((Math.abs(plot1.y - plot2.y) == 1 && plot1.x == plot2.x))){
                    borders--;
                }
            }
            sumBorders += borders;
        }

        return size * sumBorders;
    }

    public static void assignGroup(Plot plot, Plot[][] map){
        plot.borders[Plot.border.NORTH.ordinal()] = neighbourAssigner(plot, map, 0, -1); //NORTH
        plot.borders[Plot.border.EAST.ordinal()] = neighbourAssigner(plot, map, 1, 0); //EAST
        plot.borders[Plot.border.SOUTH.ordinal()] = neighbourAssigner(plot, map, 0, 1); //SOUTH
        plot.borders[Plot.border.WEST.ordinal()] = neighbourAssigner(plot, map, -1, 0); //WEST
    }

    public static boolean neighbourAssigner(Plot plot, Plot[][] map, int dx, int dy){
        if((dy < 0 && plot.y == 0) || (dy > 0 && plot.y == map.length - 1) ||(dx < 0 && plot.x == 0) || (dx > 0 && plot.x == map[0].length - 1))
            return true;
        Plot other = map[plot.y + dy][plot.x + dx];
        if(other.type == plot.type && (other.group == null)){
            other.group = plot.group;
            plot.group.add(other);
            assignGroup(other, map);
            return false;
        }
        return true;
    }

    public static Plot[][] createPlots(char[][] map){
        Plot[][] plots = new Plot[map.length][map[0].length];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                plots[i][j] = new Plot(i,j, map[i][j]);
            }
        }

        return plots;
    }

    public static void printGroups(Plot[][] plots){
        for(Plot[] row : plots){
            for(Plot plot : row) {
                System.out.print(plot.group);
            }
            System.out.println();
        }
    }

}
