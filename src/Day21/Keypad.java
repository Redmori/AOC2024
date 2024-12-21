package Day21;

import java.util.ArrayList;
import java.util.List;

public class Keypad {

    int pointerX;
    int pointerY;

    int startPointerX;
    int startPointerY;

    int avoidX;
    int avoidY;

    Keypad target;

    char[][] keys;

    public Keypad(){
        keys = new char[4][3];
        keys[0] = new char[]{'7', '8', '9'};
        keys[1] = new char[]{'4', '5', '6'};
        keys[2] = new char[]{'1', '2', '3'};
        keys[3] = new char[]{'X', '0', 'A'};

        startPointerX = 2;
        startPointerY = 3;

        pointerReset();

        avoidX = 0;
        avoidY = 3;

        target = null;
    }


    public Keypad(Keypad t){
        keys = new char[2][3];
        keys[0] = new char[]{'X', '^', 'A'};
        keys[1] = new char[]{'<', 'v', '>'};

        startPointerX = 2;
        startPointerY = 0;

        pointerReset();

        avoidX = 0;
        avoidY = 0;

        target = t;
    }

    public void pointerReset() {
        pointerX = startPointerX;
        pointerY = startPointerY;

        if(target != null)
            target.pointerReset();
    }

    public ArrayList<ArrayList<Character>> getChainedInput(ArrayList<Character> output) {
        if(target == null){
            ArrayList<ArrayList<Character>> input = getInput(output);
            System.out.println(input);
            return input;
        }

        ArrayList<ArrayList<Character>> chainedOutput = target.getChainedInput(output);

        ArrayList<ArrayList<Character>> combinedInput = new ArrayList<>();
        for(ArrayList<Character> singleOutput : chainedOutput){
            combinedInput.addAll(getInput(singleOutput));
        }
        return combinedInput;
    }

    public ArrayList<ArrayList<Character>> getInput(ArrayList<Character> output){
        ArrayList<ArrayList<Character>> newPaths = new ArrayList<>();

        ArrayList<ArrayList<Character>> paths = getShortestPaths(output.getFirst());
        output.removeFirst();
        if(!output.isEmpty()) {
            ArrayList<ArrayList<Character>> pathsToAdd = getInput(output);
            for (ArrayList<Character> path1 : paths) {
                for (ArrayList<Character> path2 : pathsToAdd) {
                    ArrayList newPath = (ArrayList) path1.clone();
                    newPath.addAll(path2);
                    newPaths.add(newPath);
                }
            }
        }else {
            return paths;
        }

        return newPaths;
    }

    private ArrayList<ArrayList<Character>> getShortestPaths(Character token) {
        int[] tokenLocation = getTokenLocation(token);
        int tokenY = tokenLocation[0];
        int tokenX = tokenLocation[1];

        ArrayList<Character> pathVH = new ArrayList<>();
        ArrayList<Character> pathHV = new ArrayList<>();

        int dy = tokenY - pointerY;
        int dx = tokenX - pointerX;

        addVerticalMovement(dy, pathVH);
        addHorizontalMovement(dx, pathVH);

        addHorizontalMovement(dx, pathHV);
        addVerticalMovement(dy, pathHV);

        pathVH.add('A');
        pathHV.add('A');

        ArrayList<ArrayList<Character>> paths = new ArrayList<>();

        //System.out.println(pointerX + "," + pointerY + " -> " + tokenX + "," + tokenY);
        if(!(pointerY == avoidY && tokenX == avoidX )) {
            paths.add(pathHV);
        }
        if(!pathHV.equals(pathVH)){
            if(!(pointerX == avoidX && tokenY == avoidY )) {
                paths.add(pathVH);
            }
        }

        pointerY = tokenY;
        pointerX = tokenX;

        return paths;
    }

    private static void addHorizontalMovement(int dx, List<Character> path) {
        for (int j = 0; j < Math.abs(dx); j++) {
            if(dx > 0){
                path.add('>');
            }else {
                path.add('<');
            }
        }
    }

    private static void addVerticalMovement(int dy, List<Character> path) {
        for (int i = 0; i < Math.abs(dy); i++) {
            if(dy > 0){
                path.add('v');
            }
            else {
                path.add('^');
            }
        }
    }

    private int[] getTokenLocation(Character token) {
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[0].length; j++) {
                if(keys[i][j] == token){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    public static String sequenceToString(ArrayList<Character> sequence){
        StringBuilder string = new StringBuilder();
        for(Character c : sequence){
            string.append(c);
        }
        return string.toString();
    }

    public static ArrayList<String> allSequenceToString(ArrayList<ArrayList<Character>> sequences){
        ArrayList<String> strings = new ArrayList<>();
        for(ArrayList<Character> sequence : sequences){
            strings.add(sequenceToString(sequence));
        }
        return strings;
    }
}
