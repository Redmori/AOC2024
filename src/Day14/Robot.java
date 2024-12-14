package Day14;

public class Robot {
    int x;
    int y;
    int vx;
    int vy;

    public Robot(int _x, int _y, int _vx, int _vy){
        x = _x;
        y = _y;
        vx = _vx;
        vy = _vy;
    }

    public String toString(){
        return "Robot (" + x + "," + y + ") + (" + vx + "," + vy + ")";
    }

    public void move(int seconds){
        for(int i = 0; i < seconds; i++){
            x += vx;
            y += vy;
        }
    }

    public void wrap(int sizeX, int sizeY){
        if(x > sizeX - 1){
            x -= sizeX;
        } else if (x < 0) {
            x += sizeX;
        }
        if(y > sizeY - 1){
            y -= sizeY;
        } else if (y < 0) {
            y += sizeY;
        }
    }
}
