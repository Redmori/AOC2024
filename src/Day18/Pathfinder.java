package Day18;

import java.util.ArrayList;
import java.util.List;

public class Pathfinder {

    int[][] pathMap;

    List<int[]> openList = new ArrayList<>();

    Map map;

    public Pathfinder(Map _map){
        map = _map;
        pathMap = new int[map.width][map.height];

        for (int i = 0; i < pathMap.length; i++) {
            for (int j = 0; j < pathMap[i].length; j++) {
                pathMap[i][j] = Integer.MAX_VALUE;
            }
        }

        pathMap[0][0] = 0;
        openList.add(new int[]{0,0});
    }

    public int findPath(){
        //printMap();
        while(!openList.isEmpty()){
            expandPath();
            //printMap();
        }
        //printMap();
        return pathMap[map.width-1][map.height-1];
    }

    public void expandPath() {
        int[] position = openList.getFirst();
        int x = position[0];
        int y = position[1];

        expand(x, y, 1, 0);
        expand(x, y, -1, 0);
        expand(x, y, 0, 1);
        expand(x, y, 0, -1);

        openList.removeFirst();


    }

    private void expand(int x, int y, int dx, int dy) {
        if(map.isOpen(x + dx, y + dy)){
            int newValue = pathMap[x][y] + 1;
            if(newValue < pathMap[x + dx][y + dy]) {
                pathMap[x + dx][y + dy] = newValue;
                openList.add(new int[]{x + dx, y + dy});
            }
        }
    }

    public void printMap(){
        int maxWidth = 6; // Fixed width for numbers (adjust as needed)
        String format = "%-" + maxWidth + "s"; // Left-aligned fixed width

        for (int y = 0; y < pathMap[0].length; y++) {
            for (int x = 0; x < pathMap.length; x++) {
                int value = pathMap[x][y];
                if(value == Integer.MAX_VALUE) {
                    System.out.printf(format, "X");
                }else{
                    System.out.printf(format, value);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
