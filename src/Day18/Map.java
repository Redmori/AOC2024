package Day18;

public class Map {
    boolean[][] blocked;
    int[][] path;

    int width;
    int height;

    public Map(int w, int h){
        width = w;
        height = h;
        blocked = new boolean[width][height];
        path = new int[width][height];
    }

    public void block(int x, int y){
        blocked[x][y] = true;
    }

    public void printLayout(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(blocked[x][y] ? "#" : ".");
            }
            System.out.println();
        }
    }

    public boolean isOpen(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height){
            return false;
        }else{
            return !blocked[x][y];
        }
    }

    public boolean isOpen(int[] position){
        return isOpen(position[0], position[1]);
    }
}
