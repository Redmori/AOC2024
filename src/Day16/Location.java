package Day16;

public class Location implements Comparable<Location> {
    int x;
    int y;
    int direction;
    int score;

    Location previous;

    Location alternative;

    public Location(int[] loc, int _score){
        y = loc[0];
        x = loc[1];
        direction = loc[2];
        score = _score;
    }

    public Location(int _x, int _y, int _direction, int _score){
        x = _x;
        y = _y;
        direction = _direction;
        score = _score;
    }

    public int[] getNextPosition(){
        int[] delta = getMoveDelta();
        return new int[]{y + delta[0], x + delta[1], direction};
    }

    public int[] getMoveDelta(){
        return switch (direction) {
            case 0 -> new int[]{-1, 0};
            case 1 -> new int[]{0, 1};
            case 2 -> new int[]{1, 0};
            default -> new int[]{0, -1};
        };
    }

    @Override
    public int compareTo(Location o) {
        int scoreComparison = Integer.compare(this.score, o.score);

        if (scoreComparison == 0) {
            scoreComparison =  Integer.compare(this.x, o.x);
        }
        if (scoreComparison == 0) {
            scoreComparison =  Integer.compare(this.y, o.y);
        }
        if (scoreComparison == 0) {
            scoreComparison =  Integer.compare(this.direction, o.direction);
        }

        return scoreComparison;
    }
}
