import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Locations {

    public ArrayList<Integer> left;
    public ArrayList<Integer> right;

    public Locations(ArrayList<Integer> l, ArrayList<Integer> r){
        left = l;
        right = r;
    }

    public void print(){
        for(int i = 0; i < left.size(); i++){
            System.out.println(left.get(i) + "\t" + right.get(i));
        }
    }

    public void sortLists(){
        Collections.sort(left);
        Collections.sort(right);
    }


    public ArrayList<Integer> calculateDifferences() {
        ArrayList<Integer> diff = new ArrayList<>();
        for(int i = 0; i < left.size(); i++) {
            diff.add(Math.abs(left.get(i) - right.get(i)));
        }
        return diff;
    }

    public int calculateSimilarityScore(){
        int score = 0;
        for(int i = 0; i < left.size(); i++) {
            score += left.get(i) * Collections.frequency(right, left.get(i));
        }
        return score;
    }
}
