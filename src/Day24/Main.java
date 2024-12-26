package Day24;

import AOCutil.AOC;

import java.util.*;

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

        ArrayList<Wire> xy = generateXY(input);

        ArrayList<Wire> connections = generateConnections(input, xy);

        ArrayList<Wire> z = getZ(connections, 'z');

//        System.out.println("xy:");
//        System.out.println(xy);
//        System.out.println("connections:");
//        System.out.println(connections);
//        System.out.println("z:");
//        System.out.println(z);

        calculateZ(z);

        String zBinary = createBinary(z);

        long p2 = convertBinary(zBinary);

        System.out.println("part 2 base: " + p2);


        setDefaults(connections);

        assertInfluence(z);

        for(Wire wire : connections){
            System.out.println(wire + " " + wire.influence);
        }

        ArrayList<Wire> x = getZ(xy,'x');
        ArrayList<Wire> y = getZ(xy,'y');
        long xValue = convertBinary(createBinary(x));
        long yValue = convertBinary(createBinary(y));
        long sum = xValue + yValue;
        System.out.println(xValue + " + " + yValue + " = " + sum);

        checkXORS(x,y,z, connections);

        countOperations(z);

        displayZ(z, 10);

        //swap("jnt", "vkd", connections);
        //swap("tgc", "vkd", connections);
        ArrayList<Wire> adjusted = new ArrayList<>();

        adjusted.addAll(swap("fhc", "z06", connections));

        displayZ(z,15);

        adjusted.addAll(swap("z11", "qhj", connections));

        displayZ(z,25);

        adjusted.addAll(swap("mwh", "ggt", connections));

        displayZ(z,30);

        adjusted.addAll(swap("hqk", "z35", connections));

        displayZ(z,z.size());


        countOperations(z);

        calculateZ(adjusted);
        long xValueAfter = convertBinary(createBinary(x));
        long yValueAfter = convertBinary(createBinary(y));
        long sumAfter = xValueAfter + yValueAfter;
        System.out.println(xValueAfter + " + " + yValueAfter + " = " + sumAfter);

        System.out.println();
        System.out.println("Day 24 Part 1: " + p1);
        System.out.println("Day 24 Part 2: fhc,ggt,hqk,mwh,qhj,z06,z11,z35");

    }

    private static void displayZ(ArrayList<Wire> z, int amount){
        System.out.println("==================");
        System.out.println(z.get(0));

        System.out.println();
        System.out.println(z.get(1));
        System.out.println(z.get(1).wire1 + "      " + z.get(1).wire2);

        for (int i = 2; i < amount; i++) {
            displaySingleZ(z.get(i));
        }

        System.out.println("==================");
    }

    private static void displaySingleZ(Wire z) {
        System.out.println();
        System.out.println(z);
        Wire carryOverWire;
        if(z.wire1.operation == Wire.Operation.XOR){
            System.out.println(z.wire1 + "     " + z.wire2);
            carryOverWire = z.wire2;
        }else {
            System.out.println(z.wire2 + "     " + z.wire1);
            carryOverWire = z.wire1;
        }
        if(carryOverWire.wire1.isNearEnd()){
            System.out.println(carryOverWire.wire1 + "     " + carryOverWire.wire2);

        }else {
            System.out.println(carryOverWire.wire2 + "     " + carryOverWire.wire1);

        }
    }

    private static void countOperations(ArrayList<Wire> z){
        int amount =0;
        for (int i = 0; i < z.size(); i++) {
            Wire zWire = z.get(i);
            int totalOps = zWire.countOperations();
            int newOps = totalOps - amount;
            //System.out.println("amount of operations for " + zWire + " is " + newOps);
            if(!((i == 0 && newOps == 1) || (i == 1 && newOps == 2) || (i == 45 && newOps == 2) || (i > 1 && newOps == 4))){
                System.out.println("Possible mistake at " + zWire + " because " + newOps + " new operations over expected " );
            }
            amount = totalOps;
        }
    }


    private static void checkXORS(ArrayList<Wire> x, ArrayList<Wire> y, ArrayList<Wire> z, ArrayList<Wire> connections) {
        System.out.println("Checking XOR gates for the A-wire");
        for (int i = 1; i < z.size(); i++) {
            Wire zWire = z.get(i);
            Wire wire1 = zWire.wire1;
            Wire wire2 = zWire.wire2;

            Wire aWire;
            if(wire1.isA(zWire)){
                aWire = wire1;
            } else if (wire2.isA(zWire)) {
                aWire = wire2;
            } else {
                System.out.println("ERROR FOUND AT " + zWire);
                System.out.println("swap options: " + wire1 + " or " + wire2);
                //find the x0i and y0i
                for (Wire wire : connections){
                    if(wire.isEnd()){
                        continue;
                    }
                    if(wire.isAnyA()){
                        if(wire.wire1.name.contains(String.valueOf(i)) && wire.operation == Wire.Operation.XOR){
                            System.out.println("with " + wire);
                        }
                    }
                }

            }


        }
    }

    private static String swapAll(ArrayList<Wire> connections, ArrayList<Wire> z) {
        System.out.println("PERFORMING SWAPPING OPERATION");
        for (int s11 = 0; s11 < connections.size(); s11++) {
            for (int s12 = s11 + 1; s12 < connections.size(); s12++) {
                for (int s21 = s11 + 1; s21 < connections.size(); s21++) {
                    for (int s22 = s21 + 1; s22 < connections.size(); s22++) {
                        for (int s31 = s21 + 1; s31 < connections.size(); s31++) {
                            for (int s32 = s31 + 1; s32 < connections.size(); s32++) {
                                for (int s41 = s31 + 1; s41 < connections.size(); s41++) {
                                    for (int s42 = s41 + 1; s42 < connections.size(); s42++) {
                                        ArrayList<Wire> adjusted = new ArrayList<>();
                                        adjusted.addAll(connections.get(s11).swap(connections.get(s12)));
                                        adjusted.addAll(connections.get(s21).swap(connections.get(s22)));
                                        adjusted.addAll(connections.get(s31).swap(connections.get(s32)));
                                        adjusted.addAll(connections.get(s41).swap(connections.get(s42)));
//                                        calculateZ(adjusted);
//                                        System.out.println(s11 + " " + s21 + " " + s31 + " " + s41 + " gives " + convertBinary(createBinary(z)));
//                                        System.out.println("count of z adjusted: " + adjusted.size());
                                    }

                                }
                                
                            }

                        }
                    }

                }
                
            }
        }
        return "bla";
    }

    private static ArrayList<Wire> swap(String w1, String w2, ArrayList<Wire> connections) {
        ArrayList<Wire> adjusted = new ArrayList<>();
        Wire wire1 = getWireByName(w1, connections);
        Wire wire2 = getWireByName(w2, connections);

        adjusted.addAll(wire1.swap(wire2));

        return adjusted;
    }

    private static void assertInfluence(ArrayList<Wire> zs) {
        for (Wire z : zs){
            z.assertInfluence();
        }
    }

    private static String createBinary(ArrayList<Wire> wires) {
        StringBuilder sb = new StringBuilder();

        for(int i = wires.size() - 1; i >= 0; i--){
            sb.append(wires.get(i).value ? "1" : "0");
        }
        return sb.toString();
    }

    private static void printZ(ArrayList<Wire> zs){
        for(Wire z : zs){
            System.out.println(z.toStringWithValue());
        }
    }

    private static void printZChained(ArrayList<Wire> zs){
        for(Wire z : zs){
            System.out.println(z.toStringChained());
        }
    }

    private static void printFull(ArrayList<Wire> wires){
        for(Wire wire : wires){
            System.out.println(wire.toStringFull());
        }
    }

    private static void calculateZ(ArrayList<Wire> zs) {
        for(Wire z : zs){
            z.calculate();
        }
    }

    private static ArrayList<Wire> getZ(ArrayList<Wire> wires, char token) {
        ArrayList<Wire> z = new ArrayList<>();
        for(Wire wire : wires){
            if(wire.name.charAt(0) == token){
                z.add(wire);
            }
        }

        z.sort(Comparator.comparing(w -> w.name));
        return z;
    }

    private static void setDefaults(ArrayList<Wire> wires) {
        for(Wire wire : wires){
            wire.setNormal();
        }
    }

    private static void resetToDefaults(ArrayList<Wire> wires) {
        for(Wire wire : wires){
            wire.reset();
        }
    }

    private static ArrayList<Wire> generateConnections(String[] input, ArrayList<Wire> xy) {
        int i = 0;
        while(!Objects.equals(input[i], "")){
            i++;
        }
        i++;

        ArrayList<Wire> connections = new ArrayList<>();

        for(; i < input.length; i++) {
            String[] split = input[i].split(" ");
            Wire newWire = getWireByName(split[4], xy, connections);


            Wire oldWire1 = getWireByName(split[0], xy, connections);
            Wire oldWire2 = getWireByName(split[2], xy, connections);
            //System.out.println("setting wire connections of " + newWire + " with " + oldWire1 + " and " + oldWire2 );
            newWire.setConnection(oldWire1, split[1], oldWire2); //= new Wire(split[4], oldWire1, split[1], oldWire2);
            //System.out.println("wiring connection set for: " + newWire.toStringFull());
        }

        connections.sort(Comparator.comparing(w -> w.name));
        return connections;
    }

    private static Wire getWireByName(String name, ArrayList<Wire> wires1, ArrayList<Wire> wires2){
        Wire foundWire = getWireByName(name, wires1);
        if(foundWire == null){
            foundWire = getWireByName(name,wires2);
        }
        if(foundWire == null){
            foundWire = new Wire(name);
            wires2.add(foundWire);
        }
        return foundWire;
    }

    private static Wire getWireByName(String name, ArrayList<Wire> wires) {
        for(Wire wire : wires){
            if(wire.name.equals(name)){
                return wire;
            }
        }
        return null;
    }

    private static ArrayList<Wire> generateXY(String[] input) {
        ArrayList<Wire> xy = new ArrayList<>();
        for (String line : input) {
            if (Objects.equals(line, "")) {
                break;
            }
            String[] wireInfo = line.split(": ");

            Wire newWire = new Wire(wireInfo[0], (Objects.equals(wireInfo[1], "1")));
            xy.add(newWire);
        }
        return xy;
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
