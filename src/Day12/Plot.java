package Day12;

import java.util.ArrayList;

public class Plot {
    int x;
    int y;
    char type;

    ArrayList<Plot> group;
    Plot North;
    Plot East;
    Plot South;
    Plot West;


    public Plot(int i, int j , char t){
        x = j;
        y = i;
        type = t;
    }
}
