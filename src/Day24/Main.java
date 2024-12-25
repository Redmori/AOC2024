package Day24;

import AOCutil.AOC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day24/input.txt");


        HashMap<String, Boolean> wires = getWires(input);

        System.out.println(wires);

        connectWires(wires, input);

        System.out.println(wires);

        ArrayList<Boolean> binaryArray = getBinary(wires);

        String binary = createBinaryString(binaryArray);

        long p1 = convertBinary(binary);

        System.out.println("Day 24 Part 1: " + p1);
    }

    private static String createBinaryString(ArrayList<Boolean> binaryArray) {
        StringBuilder sb = new StringBuilder();

        for(int i = binaryArray.size() - 1; i >= 0; i--){
            sb.append(binaryArray.get(i) ? "1" : "0");
        }
        return sb.toString();
    }

    private static long convertBinary(String binary) {
        System.out.println(binary);
        return Long.parseLong(binary, 2);
    }

    private static ArrayList<Boolean> getBinary(HashMap<String, Boolean> wires) {
        ArrayList<Boolean> z = new ArrayList<>();

        wires.forEach((key, value) -> {
            if(key.charAt(0) == 'z'){
                int index = Integer.parseInt(key.substring(1));
                while (z.size() <= index) {
                    z.add(false);
                }
                z.set(index, value);
            }

        });
        return z;
    }

    private static HashMap<String, Boolean> getWires(String[] input) {
        HashMap<String, Boolean> wires = new HashMap<>();

        for (String line : input) {
            if (Objects.equals(line, "")) {
                break;
            }
            String[] wireInfo = line.split(": ");

            wires.put(wireInfo[0], Objects.equals(wireInfo[1], "1"));
        }



        return wires;
    }


    public static void connectWires(HashMap<String, Boolean> wires, String[] connections){
        int i = 0;
        while(!Objects.equals(connections[i], "")){
            i++;
        }
        i++;

        ArrayList<String> todo = new ArrayList<>();

        for(; i < connections.length; i++) {
            todo.add(connections[i]);
        }

        while(!todo.isEmpty()) {
            Iterator<String> it = todo.iterator();
            while (it.hasNext()) {
                String[] split = it.next().split(" ");
                if (!wires.containsKey(split[0]) || !wires.containsKey(split[2])) {
                    continue;
                }
                boolean value1 = wires.get(split[0]);
                boolean value2 = wires.get(split[2]);

                boolean answer = false;
                switch (split[1]) {
                    case "AND":
                        answer = value1 && value2;
                        break;
                    case "OR":
                        answer = value1 || value2;
                        break;
                    case "XOR":
                        answer = value1 ^ value2;
                        break;
                }

                wires.put(split[4], answer);
                it.remove();
            }
        }
    }
}
