package Day12;

import java.util.ArrayList;

public class Plot {
    int x;
    int y;
    char type;

    ArrayList<Plot> group;
    boolean[] borders = new boolean[]{true,true,true,true};


    public Plot(int i, int j , char t){
        x = j;
        y = i;
        type = t;
    }


    enum border{
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
