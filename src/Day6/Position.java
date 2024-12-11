package Day6;

public class Position {
    public int x;
    public int y;

    public Position(int i, int j){
        x = i;
        y = j;
    }

    public Position(int[] pos){
        x = pos[1];
        y = pos[0];
    }

    public String toString(){
        return x + "," + y;
    }

    public Position clone(){
        return new Position(x,y);
    }

    public static Position getPosition(int[] pos){
        return new Position(pos);
    }

    public static Position getPosition(int i, int j){
        return new Position(i,j);
    }

}
