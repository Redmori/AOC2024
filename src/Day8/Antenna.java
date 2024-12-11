package Day8;

import java.util.ArrayList;

public class Antenna {
    char type;
    public int x;
    public int y;

    public Antenna(char symbol, int i, int j){
        type = symbol;
        x = i;
        y = j;
    }

    public String toString(){
        return "(" + x + "," + y + ") - " + type;
    }

    public int[] findAntinode(Antenna otherAntenna){
        int dx = x - otherAntenna.x;
        int dy = y - otherAntenna.y;
        return new int[]{x + dx, y + dy};
    }


    public ArrayList<int[]> findAntinodes(Antenna otherAntenna, int sizeX, int sizeY){
        ArrayList<int[]> nodes = new ArrayList<>();
        int dx = x - otherAntenna.x;
        int dy = y - otherAntenna.y;
        int currentX = x;
        int currentY = y;

        while(currentX < sizeX && currentY < sizeY && currentX >= 0 && currentY >= 0){
            int[] loc = new int[]{currentX, currentY};
            nodes.add(loc);
            currentX += dx;
            currentY += dy;
        }

        return  nodes;
    }

}
