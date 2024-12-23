package Day23;

import AOCutil.AOC;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day23/input.txt");

        ArrayList<Computer> computers = generateComputers(input);

        generateConnections(computers, input);

        printComputers(computers);

        //int p1 = countTripples(computers);
        ArrayList<String> tripples = getTripples(computers);

        for (String tripple : tripples){
            System.out.println(tripple);
        }

        ArrayList<String> tripplesWithT = getTripplesWithT(tripples);

        System.out.println();
        for (String tripple : tripplesWithT){
            System.out.println(tripple);
        }


        System.out.println("Day 23 Part 1: " + tripplesWithT.size());

    }

    private static ArrayList<String> getTripplesWithT(ArrayList<String> tripples) {
        ArrayList<String> tripplesWithT = new ArrayList<>();

        for (String tripple : tripples){
            String[] split = tripple.split(",");
            if(startsWithT(split[0]) || startsWithT(split[1]) || startsWithT(split[2])){
                tripplesWithT.add(tripple);
            }
        }

        return tripplesWithT;
    }

    private static boolean startsWithT(String string){
        return string.charAt(0) == 't';
    }

    private static ArrayList<String> getTripples(ArrayList<Computer> computers){
        ArrayList<String> tripples = new ArrayList<>();

        for(Computer computer : computers){
            for(Computer c1 : computer.connections){
                for(Computer c2 : c1.connections){
                    if(c2.contains(computer)){
                        boolean exists = false;
                        for(String tripple : tripples) {
                            if(tripple.contains(computer.name) && tripple.contains(c1.name) && tripple.contains(c2.name)) {
                                exists = true;
                                break;
                            }
                        }
                        if(!exists){
                            tripples.add(computer.name + "," + c1.name + "," + c2.name);
                        }
                    }
                }
            }
        }
        return tripples;
    }

    private static int countTripples(ArrayList<Computer> computers) {
        int sum = 0;
        for(Computer computer : computers){
                sum += computer.isPartOfTripple();
        }
        return sum / 3;
    }

    private static void generateConnections(ArrayList<Computer> computers, String[] input) {
        for(String connection : input){
            String[] names = connection.split("-");
            Computer c1 = findComputerByName(computers, names[0]);
            Computer c2 = findComputerByName(computers, names[1]);
            c1.connect(c2);
            c2.connect(c1);
        }
    }

    private static ArrayList<Computer> generateComputers(String[] input) {

        ArrayList<Computer> computers = new ArrayList<>();

        for(String connection : input){
            String[] names = connection.split("-");
            createComputer(names[0], computers);
            createComputer(names[1], computers);
        }
        return computers;
    }

    private static void createComputer(String name, ArrayList<Computer> computers) {
        if(computers.stream().anyMatch(c -> c.name.equals(name))){
            return;
        }
        Computer newComputer = new Computer(name);
        computers.add(newComputer);
    }

    private static void printComputers(ArrayList<Computer> computers){
        for(Computer computer : computers){
            System.out.println(computer);
        }
    }

    public static Computer findComputerByName(ArrayList<Computer> computers, String computerName) {
        return computers.stream()
                .filter(computer -> computer.name.equals(computerName))
                .findFirst()
                .orElse(null);
    }
}
