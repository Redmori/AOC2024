package Day12;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day12/input.txt");
        char[][] map = AOC.convertStringToChar(input);
        AOC.printInput(map);
        Plot[][] plots = createPlots(map);
        ArrayList<ArrayList<Plot>> groups = assignGroups(plots);
        int p1 = calculateGroups(groups);
        System.out.println("Day 12 Part 1: " + p1);
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

        return size * sumBorders; //*circum
    }

    public static void assignGroup(Plot plot, Plot[][] map){
        neighbourAssigner(plot, map, 1, 0);
        neighbourAssigner(plot, map, -1, 0);
        neighbourAssigner(plot, map, 0, 1);
        neighbourAssigner(plot, map, 0, -1);



    }

    public static void neighbourAssigner(Plot plot, Plot[][] map, int dx, int dy){
        if((dy < 0 && plot.y == 0) || (dy > 0 && plot.y == map.length - 1) ||(dx < 0 && plot.x == 0) || (dx > 0 && plot.x == map[0].length - 1))
            return;
        Plot other = map[plot.y + dy][plot.x + dx];
        if(other.type == plot.type && (other.group == null)){
            other.group = plot.group;
            plot.group.add(other);
            assignGroup(other, map);
        }
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
