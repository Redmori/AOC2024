package Day16;

import java.util.ArrayList;

public class Reindeer {

    int x;
    int y;
    int direction = 2;

    int score = 0;

    boolean stopped = false;
    boolean finished = false;

    ArrayList<int[]> visited = new ArrayList<>();

    public Reindeer(int _x, int _y){
        x = _x;
        y = _y;
    }

    public boolean move(){
        //System.out.println("moving");
        switch(direction){
            case 1:
                y += -1;
                break;
            case 2:
                x += 1;
                break;
            case 3:
                y += 1;
                break;
            case 4:
                x += -1;
                break;
        }

        if(hasVisited(x,y)){
            return true;
        }
        else {
            visited.add(new int[]{x, y});
            score++;
            return false;
        }
    }

//    public int[] getNextLocation(){
//        int newX = x + Main.directionToMove(direction)[0];
//        int newY = y + Main.directionToMove(direction)[1];
//        return new int[]{newX, newY};
//    }
//
//    public int[] getRightLocation(){
//        int newX = x + Main.directionToMove(newDirection(1))[0];
//        int newY = y + Main.directionToMove(newDirection(1))[1];
//
//        //System.out.println("right location " + newX + "," + newY + " after moving into " + Main.directionToMove(newDirection(1))[0] + "," +  Main.directionToMove(newDirection(1))[1]);
//        return new int[]{newX, newY};
//    }
//
//    public int[] getLeftLocation(){
//        int newX = x + Main.directionToMove(newDirection(-1))[0];
//        int newY = y + Main.directionToMove(newDirection(-1))[1];
//        //System.out.println("left location " + newX + "," + newY + " after moving into " + Main.directionToMove(newDirection(-1))[0] + "," +  Main.directionToMove(newDirection(-1))[1]);
//
//        return new int[]{newX, newY};
//
//    }

    public void rotate(boolean clockwise){
        direction = clockwise ? newDirection(1) : newDirection(-1);
        score += 1000;
    }

    public int newDirection(int rotation){
        int adjustedDirection = direction + rotation;
        if(adjustedDirection < 1){
            adjustedDirection += 4;
        }
        if(adjustedDirection > 4){
            adjustedDirection -= 4;
        }
        return adjustedDirection;
    }


    public boolean hasVisited(int xPos, int yPos){
        for(int[] location : visited){
            if(location[0] == xPos && location[1] == yPos){
                return true;
            }
        }
        return false;
    }
}
